import { BLANK_TEMPLATE_ID } from 'app/core/models/core.types';
import { UserGroupService } from 'app/core/models/user/group/userGroup.service';
import {
    MinPermissions, Permission, Resource, UserGroup
} from 'app/core/models/user/group/userGroup.types';
import {
    GroupListComponent
} from 'app/modules/settings-module/user/group/list/group-list.component';
import { Subject } from 'rxjs';
import { map, switchMap, tap } from 'rxjs/operators';

import { OverlayRef } from '@angular/cdk/overlay';
import {
    ChangeDetectionStrategy, ChangeDetectorRef, Component, OnDestroy, OnInit, ViewEncapsulation
} from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDrawerToggleResult } from '@angular/material/sidenav';
import { ActivatedRoute, Router } from '@angular/router';
import { fuseAnimations } from '@fuse/animations';

@Component({
  selector: 'group-details',
  templateUrl: './group-details.component.html',
  encapsulation: ViewEncapsulation.None,
  changeDetection: ChangeDetectionStrategy.OnPush,
  animations: fuseAnimations
})
export class GroupDetailsComponent implements OnInit, OnDestroy {
  // CONST - provide access to html template
  BLANK_TEMPLATE_ID = BLANK_TEMPLATE_ID;

  editMode: boolean = false;
  group: UserGroup;
  groupForm: FormGroup;
  groups: UserGroup[];
  permissions: Permission[];
  resources: Resource[] = [];
  private _tagsPanelOverlayRef: OverlayRef;
  private _unsubscribeAll: Subject<any> = new Subject<any>();

  /**
   * Constructor
   */
  constructor(
    private _changeDetectorRef: ChangeDetectorRef,
    private _groupListComponent: GroupListComponent,
    private _userGroupService: UserGroupService,
    private _formBuilder: FormBuilder,
    private _router: Router,
    private _activatedRoute: ActivatedRoute
  ) {
  }

  // -----------------------------------------------------------------------------------------------------
  // @ Lifecycle hooks
  // -----------------------------------------------------------------------------------------------------

  get createMode() {
    return this.group?.idGroup === BLANK_TEMPLATE_ID
  }

  /**
   * On init
   */
  ngOnInit(): void {
    this._userGroupService.group$.pipe(
      tap(group => this.group = group),
      switchMap(() => this._userGroupService.getPermissions()),
      tap(permissions => {
        // Update the permissions
        this.permissions = permissions.map(per => { per.checked = false; return per });
      }),
      switchMap(() => this._userGroupService.getResources()),
    ).pipe(map((resources => {
      // Patch form.idGroup
      this.groupForm.patchValue({ idGroup: this.group?.idGroup })
      // if (!this.group) return this.closeDrawer();
      if (this.createMode) {
        this.editMode = true
      };
      this.resources = resources;
      // Asign unique permissions for each resource
      this.resources.map(res => {
        // Initialize permissions
        res.permissions = [];
        this.permissions.forEach(per => {
          let permission = { ...per };
          permission.parentResource = res;
          res.permissions.push(permission);
        })
      })

      // Check selected group permissions
      this.resources.forEach(res => {
        res.permissions.forEach(per => {
          // If group.permissions contain this pair, check
          if (this.group.permissions.find(gp =>
            gp.resource.idResource === res.idResource
            && gp.permission.idPermission === per.idPermission
          )) per.checked = true;
          else per.checked = false // If not, uncheck
        })
        // If all permission of resource are checked ...
        if (this.checkResource(res)) res.checked = true;
      })

      // Mark for check
      this._changeDetectorRef.markForCheck();
    }))).subscribe()

    // Open the drawer
    this._groupListComponent.matDrawer.open();

    // Create the group form
    this.groupForm = this._formBuilder.group({
      idGroup: [''],
      name: ['', [Validators.maxLength(40)]],
    }, {
      validators: [this.customNameValidator]
    });
  }
  /**
   * On destroy
   */
  ngOnDestroy(): void {
    // Unsubscribe from all subscriptions
    this._unsubscribeAll.next();
    this._unsubscribeAll.complete();

    // Dispose the overlays if they are still on the DOM
    if (this._tagsPanelOverlayRef) {
      this._tagsPanelOverlayRef.dispose();
    }
  }

  // -----------------------------------------------------------------------------------------------------
  // @ Public methods
  // -----------------------------------------------------------------------------------------------------

  /**
   * Close the drawer
   */
  closeDrawer(): Promise<MatDrawerToggleResult> {
    return this._groupListComponent.matDrawer.close();
  }

  /**
   * Toggle edit mode
   *
   * @param editMode
   */
  toggleEditMode(editMode: boolean | null = null): void {
    if (this.createMode) return;
    if (editMode === null) this.editMode = !this.editMode;
    else this.editMode = editMode;
    // Mark for check
    this._changeDetectorRef.markForCheck();
  }

  // Checkbox control
  checkResource(resource: Resource) {
    return resource.permissions.every(t => t.checked);
  }
  someComplete(resource: Resource): boolean {
    return resource.permissions.filter(per => per.checked).length > 0 && !this.checkResource(resource);
  }
  updateAllComplete(resource: Resource) {
    resource.checked = this.checkResource(resource)
  }
  setAll(resource: Resource, checked: boolean) {
    resource.permissions.forEach(per => per.checked = checked);
  }

  deleteGroup(): void {
    this._userGroupService.deleteGroup(this.group.idGroup);

    this._router.navigate(['../'], {relativeTo: this._activatedRoute});

    // Mark for check
    this._changeDetectorRef.markForCheck();
  }

  /**
   * Create the group
   */
  createGroup(): void {
    const name: string = this.groupForm.getRawValue().name.trim().toLowerCase();

    // mock group-permissions
    let checkedPermissions: MinPermissions[] = [];
    this.resources.forEach(res => {
      res.permissions.forEach(per => {
        if (per.checked) checkedPermissions.push(
          { idResource: res.idResource, idPermission: per.idPermission }
        )
      })
    })

    this.group.name = name;

    // Create group
    if (name.length > 0) this._userGroupService.createGroup(this.group, checkedPermissions);

    // Update view calling the resolver
    this._router.navigate(['../'], {relativeTo: this._activatedRoute});

    // Mark for check
    this._changeDetectorRef.markForCheck();
  }

  /**
   * Update the group
   */
  updateGroup(): void {
    const name: string = this.groupForm.getRawValue().name.trim().toLowerCase();

    // mock group-permissions
    let checkedPermissions: MinPermissions[] = [];
    this.resources.forEach(res => {
      res.permissions.forEach(per => {
        if (per.checked) checkedPermissions.push(
          { idResource: res.idResource, idPermission: per.idPermission }
        )
      })
    })

    // Update permissions on server
    this._userGroupService.updateGroupPermissions(this.group.idGroup, checkedPermissions)
      .subscribe();

    // Update name on server if exists
    if (name.length > 0) this._userGroupService.updateGroupName(this.group.idGroup, name);

    // Update view calling the resolver
    this._router.navigateByUrl(this._router.routerState.snapshot.url)

    // Mark for check
    this._changeDetectorRef.markForCheck();
  }

  /**
   * Track by function for ngFor loops
   *
   * @param index
   * @param permission
   */
  trackPermissionByFn(index: number, permission: Permission) {
    return permission;
  }
  /**
   * Track by function for ngFor loops
   *
   * @param index
   * @param resource
   */
  trackResourceByFn(index: number, resource: Resource) {
    return resource
  }

  private customNameValidator(formGroup: FormGroup): any {
    let errors: any = {};
    Object.assign(errors, Validators.maxLength(32)(formGroup.get('name')))
    if (formGroup.value?.idGroup == BLANK_TEMPLATE_ID) {
      Object.assign(errors, Validators.required(formGroup.get('name')))
    }
    formGroup.controls.name.setErrors(Object.keys(errors).length === 0 ? null : errors)
    return Object.keys(errors).length === 0 ? null : errors
  }
}
