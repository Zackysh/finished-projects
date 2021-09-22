import { BLANK_TEMPLATE_ID } from 'app/core/models/core.types';
import { UserGroupService } from 'app/core/models/user/group/userGroup.service';
import { map, switchMap } from 'rxjs/operators';

import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Resolve, Router, RouterStateSnapshot } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class SelectedGroupResolver implements Resolve<any>
{
  /**
   * Constructor
   */
  constructor(
    private _userGroupService: UserGroupService,
    private _router: Router
  ) { }

  // -----------------------------------------------------------------------------------------------------
  // @ Public methods
  // -----------------------------------------------------------------------------------------------------

  /**
   * Resolver, get id from params, then calls userGroupService.
   *
   * @param route
   * @param state
   */
  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    if (route.params.id != BLANK_TEMPLATE_ID)
      this._userGroupService.getGroupById(route.params.id).pipe(
        switchMap(group => this._userGroupService.getGroupPermissions(route.params.id)
          .pipe(
            map(permissions => { group.permissions = permissions; return group })
          ))
      ).subscribe(group => {
        this._userGroupService.setGroup$(group)
      }, error => {
        console.error(`Couldn't find group with ID ${route.params.id}`)
        // Get the parent url
        const parentUrl = state.url.split('/').slice(0, -1).join('/');

        // Navigate to there
        this._router.navigateByUrl(parentUrl);
      })
    else {
      this._userGroupService.getGroups().subscribe(groups => {
        this._userGroupService.setGroups$(groups);
        try {
          this._userGroupService.unshiftStoredTemplate();
        } catch (error) {
          this._userGroupService.unshiftTemplate();
          console.error(error);
        }
      })
    };

  }
}

@Injectable({
  providedIn: 'root'
})
export class GroupsResolver implements Resolve<any>
{
  /**
   * Constructor
   */
  constructor(private _userGroupService: UserGroupService) {
  }

  // -----------------------------------------------------------------------------------------------------
  // @ Public methods
  // -----------------------------------------------------------------------------------------------------

  /**
   * Resolver, get id from params, then calls userGroupService.
   *
   * @param route
   * @param state
   */
  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    this._userGroupService.getGroups().subscribe(groups => {
      this._userGroupService.setGroups$(groups);
    })
  }
}

