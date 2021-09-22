import { BehaviorSubject, Observable } from 'rxjs';
import { map, switchMap } from 'rxjs/operators';

import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

import { APIRes, BLANK_TEMPLATE_ID } from '../../core.types';
import {
    GroupPermission, MinPermissions, Permission, Resource, UserGroup
} from './userGroup.types';

@Injectable({
  providedIn: 'root',
})
export class UserGroupService {
  private group = new BehaviorSubject<UserGroup | null>(null);
  private groups = new BehaviorSubject<UserGroup[] | null>(null);

  /**
   * Constructor
   */
  constructor(private _httpClient: HttpClient) {}

  // -----------------------------------------------------------------------------------------------------
  // @ Public methods
  // -----------------------------------------------------------------------------------------------------

  get group$() {
    return this.group.asObservable();
  }
  setGroup$(group: UserGroup) {
    this.group.next(group);
  }

  get groups$() {
    return this.groups.asObservable();
  }
  setGroups$(groups: UserGroup[]) {
    this.groups.next(groups);
  }

  unshiftStoredTemplate(): void {
    let lsGroup: UserGroup;
    try {
      lsGroup = JSON.parse(localStorage.getItem('blank-group'));
    } catch (error) {
      throw 'Failed to load blank-group from local storage';
    }
    if (!lsGroup) {
      throw "There's no blank-group stored";
    }

    this.groups.next([lsGroup, ...this.groups.value]);
    this.group.next(lsGroup);
  }

  unshiftTemplate(): void {
    // If there isn't already a blank group ...
    if (this.groups?.value[0]?.idGroup != BLANK_TEMPLATE_ID) {
      let newGroup: UserGroup = { idGroup: -1, name: 'New group', permissions: [] };
      // Insert blank user to be filled
      this.groups.next([newGroup, ...this.groups.value]);
      this.group.next(newGroup);
      localStorage.setItem('blank-group', JSON.stringify(newGroup));
      return;
    }
    console.error("There's already a group to be filled");
  }

  updateGroupName(idGroup: number, name: string): void {
    this._httpClient.put<APIRes<UserGroup>>(`core/group/${idGroup}`, { name }).subscribe(group => {
      this.group.next({ ...this.group.value, name });
      this.groups.next(this.groups.value.map(g => (g.idGroup === idGroup ? { ...g, name } : g)));
    });
  }

  createGroup(newGroup: UserGroup, minPermissions: MinPermissions[]): void {
    this._httpClient
      .post<{ response: UserGroup }>(`core/group`, newGroup)
      .pipe(
        switchMap(res =>
          this.updateGroupPermissions(res.response.idGroup, minPermissions).pipe(
            switchMap(() =>
              this.getGroupPermissions(res.response.idGroup).pipe(
                map(permissions => {
                  res.response.permissions = permissions;
                  this.groups.next([...this.groups.value, res.response]);
                  this.group.next(res.response);
                }),
              ),
            ),
          ),
        ),
      )
      .subscribe();
  }

  /**
   * Update group permissions
   *
   * @param idGroup
   * @param group group with new permissions
   */
  updateGroupPermissions(idGroup: number, permissions: MinPermissions[]): Observable<UserGroup> {
    return this._httpClient
      .post<APIRes<UserGroup>>(`core/group/permission/grant/list/${idGroup}`, permissions)
      .pipe(map(res => res.data));
  }

  /**
   * Delete group
   *
   * @param idGroup
   */
  deleteGroup(idGroup: number): void {
    this._httpClient.delete<APIRes<UserGroup>>(`core/group/${idGroup}`).subscribe(res => {
      this.groups.next(this.groups.value.filter(g => g.idGroup !== idGroup));
      this.group.next(null);
    });
  }

  getGroups(): Observable<UserGroup[]> {
    return this._httpClient.get<APIRes<UserGroup[]>>('core/group').pipe(map(res => res.data));
  }

  getGroupById(idGroup: number): Observable<UserGroup> {
    return this._httpClient.get<APIRes<UserGroup>>(`core/group/${idGroup}`).pipe(map(res => res.data));
  }

  getGroupPermissions(idGroup: number): Observable<GroupPermission[]> {
    return this._httpClient
      .get<APIRes<GroupPermission[]>>(`core/group/permission/${idGroup}`)
      .pipe(map(res => res.data));
  }

  getResources(): Observable<Resource[]> {
    return this._httpClient.get<APIRes<Resource[]>>('core/group/resource').pipe(map(res => res.data));
  }

  getPermissions(): Observable<Permission[]> {
    return this._httpClient
      .get<APIRes<Permission[]>>('core/group/permission')
      .pipe(map(res => res.data));
  }
}
