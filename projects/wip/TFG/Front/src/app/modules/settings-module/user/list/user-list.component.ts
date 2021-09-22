import { BLANK_TEMPLATE_ID, DEFAULT_COMPANY } from 'app/core/models/core.types';
import { UserGroupService } from 'app/core/models/user/group/userGroup.service';
import { GUEST_GROUP, UserGroup } from 'app/core/models/user/group/userGroup.types';
import { UserService } from 'app/core/models/user/user.service';
import { Company, User } from 'app/core/models/user/user.types';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import Swal from 'sweetalert2';

import { TitleCasePipe } from '@angular/common';
import {
    AfterViewInit, ChangeDetectionStrategy, ChangeDetectorRef, Component, OnDestroy, OnInit,
    ViewChild, ViewEncapsulation
} from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { fuseAnimations } from '@fuse/animations';

@Component({
  selector: 'user-list',
  templateUrl: './user-list.component.html',
  styles: [
    /* language=SCSS */
    `
      .inventory-grid {
        grid-template-columns: auto 40px;

        @screen sm {
          grid-template-columns: 96px 96px auto 40px;
        }

        @screen md {
          grid-template-columns: 48px 112px 112px auto 40px;
        }

        @screen lg {
          grid-template-columns: 48px 112px 112px 112px auto 26px;
        }
      }
    `,
  ],
  providers: [TitleCasePipe],
  encapsulation: ViewEncapsulation.None,
  changeDetection: ChangeDetectionStrategy.OnPush,
  animations: fuseAnimations,
})
export class UserListComponent implements OnInit, AfterViewInit, OnDestroy {
  @ViewChild(MatPaginator) private _paginator: MatPaginator;
  @ViewChild(MatSort) private _sort: MatSort;

  // CONST - provide access to html template
  BLANK_USER_ID = BLANK_TEMPLATE_ID;
  // in-memory lists
  displayedUsers: User[];
  users: User[];
  groups: UserGroup[];
  companies: Company[];

  flashMessage: 'success' | 'error' | null = null;
  isLoading: boolean = false;
  shouldAlert: boolean = true;
  searchInputControl = new FormControl();
  selectedUser: User | null = null;
  selectedUserForm: FormGroup;
  private _unsubscribeAll: Subject<any> = new Subject<any>();

  /**
   * Constructor
   */
  constructor(
    private _changeDetectorRef: ChangeDetectorRef,
    private _formBuilder: FormBuilder,
    private _userService: UserService,
    private _userGroupService: UserGroupService,
  ) {}

  // -----------------------------------------------------------------------------------------------------
  // @ Lifecycle hooks
  // -----------------------------------------------------------------------------------------------------

  get createMode() {
    return this.selectedUser?.idUser === BLANK_TEMPLATE_ID;
  }

  /**
   * On init
   */
  ngOnInit(): void {
    // Create the selected user form
    this.selectedUserForm = this._formBuilder.group(
      {
        idUser: [''],
        name: ['', [Validators.required, Validators.maxLength(40)]],
        username: ['', [Validators.required, Validators.maxLength(25)]],
        password: [''],
        idGroup: [1, [Validators.required]],
        idCompany: [1, [Validators.required]],
      },
      {
        validators: [this.customPasswordValidator],
      },
    );

    // Fetch users
    this._userService
      .getUsers()
      .pipe(takeUntil(this._unsubscribeAll))
      .subscribe(users => {
        // Update users and displayedUsers
        this.users = users;
        this.displayedUsers = users;

        // Mark for check
        this._changeDetectorRef.markForCheck();
      });

    // Fetch groups
    this._userGroupService
      .getGroups()
      .pipe(takeUntil(this._unsubscribeAll))
      .subscribe(groups => {
        // Update groups
        this.groups = groups;

        // Mark for check
        this._changeDetectorRef.markForCheck();
      });

    // Fetch companies
    this._userService
      .getCompanies()
      .pipe(takeUntil(this._unsubscribeAll))
      .subscribe(companies => {
        // Update companies
        this.companies = companies;

        // Mark for check
        this._changeDetectorRef.markForCheck();
      });

    // Subscribe to search input field value changes
    this.searchInputControl.valueChanges.subscribe(query => {
      // If creation is in process ...
      // if (this._isNewUser) return;
      // Close details
      this.closeDetails();
      // Filter users
      this.displayedUsers = this.filterUsers(query);
      // Mark for check
      this._changeDetectorRef.markForCheck();
    });
  }

  /**
   * Filter users by string query.
   *
   * @param query
   * @returns users which values match given query.
   */
  filterUsers(query: string): User[] {
    return this.users.filter(u => this.stringifyUser(u).includes(query.trim().toLocaleLowerCase()));
  }

  /**
   * Stringify any user.
   *
   * @param user
   * @returns string with specific user values separated by one space
   */
  stringifyUser(user: User): string {
    if (!user) return '';
    let values = Object.values({
      name: user.name,
      username: user.username,
      group: this.groups.find(g => g.idGroup === user.idGroup)?.name ?? GUEST_GROUP,
      company: this.companies.find(c => c.idCompany === user.idCompany)?.businessName ?? DEFAULT_COMPANY,
    });

    return values.toString().toLocaleLowerCase().trim().replace(',', ' ');
  }

  /**
   * After view init
   */
  ngAfterViewInit(): void {
    if (this._sort && this._paginator) {
      // Set the initial sort
      this._sort.sort({
        id: 'name',
        start: 'asc',
        disableClear: true,
      });

      // Mark for check
      this._changeDetectorRef.markForCheck();

      // If the user changes the sort order...
      this._sort.sortChange.pipe(takeUntil(this._unsubscribeAll)).subscribe(() => {
        // Reset back to the first page
        this._paginator.pageIndex = 0;

        // Close the details
        this.closeDetails();
      });

      // Get users if sort or page changes
      // merge(this._sort.sortChange, this._paginator.page).pipe(
      //   switchMap(() => {
      //     this.closeDetails();
      //     this.isLoading = true;
      //     return this._userService.getProducts(this._paginator.pageIndex, this._paginator.pageSize, this._sort.active, this._sort.direction);
      //   }),
      //   map(() => {
      //     this.isLoading = false;
      //   })
      // ).subscribe();
    }
  }

  /**
   * On destroy
   */
  ngOnDestroy(): void {
    // Unsubscribe from all subscriptions
    this._unsubscribeAll.next();
    this._unsubscribeAll.complete();
  }

  // -----------------------------------------------------------------------------------------------------
  // @ Public methods
  // -----------------------------------------------------------------------------------------------------

  getUserGroup(idGroup: number) {
    return (
      this.groups?.find(g => g.idGroup === idGroup) ?? this.groups?.find(g => g.idGroup === GUEST_GROUP)
    );
  }
  getUserCompany(idCompany: number) {
    return (
      this.companies?.find(c => c.idCompany === idCompany) ??
      this.companies?.find(c => c.idCompany === DEFAULT_COMPANY)
    );
  }

  /**
   * Toggle user details
   *
   * @param idUser
   */
  async toggleDetails(idUser: number): Promise<void> {
    // if PARAMETER userID isn't BLANK_USER_ID
    // && there isn't selected user || PARAM userID is different to current selectedUser
    if (
      this.selectedUser?.idUser !== BLANK_TEMPLATE_ID &&
      (!this.selectedUser || this.selectedUser.idUser !== idUser)
    )
      if (
        !(await Swal.fire({
          title: 'Edit this user?',
          text: "You won't be able to revert any change!",
          icon: 'question',
          showCancelButton: true,
          // Buttons text
          confirmButtonText: 'Edit',
          cancelButtonText: 'Cancel',
        }).then(result => result.isConfirmed))
      )
        return;

    // If new user is selected ...
    if (this.createMode) {
      // Unable to close, just cancel or submitssssss
      return;
    }

    // If the user is already selected...
    if (this.selectedUser && this.selectedUser.idUser === idUser) {
      // Close the details
      this.closeDetails();
      // reset select
      return;
    }

    // Get the user by id
    this._userService.getUserById(idUser).subscribe(user => {
      // Set the selected user
      this.selectedUser = user;
      // Reset alert state
      this.shouldAlert = true;
      // Remove password if exists
      delete user.password;
      // Fill the form
      this.selectedUserForm.patchValue(user);
      this.selectedUserForm.patchValue({ idGroup: user.idGroup ?? GUEST_GROUP });
      this.selectedUserForm.patchValue({ idCompany: user.idCompany ?? DEFAULT_COMPANY });
      // Mark for check
      this._changeDetectorRef.markForCheck();
    });
  }

  /**
   * Close the details
   */
  closeDetails(): void {
    this.selectedUser = null;
  }

  /**
   * Delte existing row for user creation
   */
  shiftBlankUser() {
    // If there is a blank user ...
    if (this.displayedUsers[0] && this.createMode) {
      // Close the details
      this.closeDetails();

      // shift it from _users
      this.displayedUsers.shift();

      // Mark for check
      this._changeDetectorRef.markForCheck();
    }
  }

  /**
   * Create row for user creation
   */
  unshiftNewUser(): void {
    // If there isn't already a blank user ...
    if (this.displayedUsers[0] && this.displayedUsers[0]?.idUser !== BLANK_TEMPLATE_ID) {
      let newUser: User = {
        idUser: -1,
        name: '',
        username: '',
        password: '',
        idGroup: GUEST_GROUP,
        idCompany: DEFAULT_COMPANY,
      };
      // WARNING!!! - DONT MUTATE ORDER TO AVOID HARD-TO-DEBUG PROBLEMS
      // reset search
      this.searchInputControl.setValue('');
      // Insert blank user to be filled
      this.displayedUsers.unshift(newUser);
      // Fill the form
      this.selectedUserForm.patchValue(newUser);
      // toggle its form
      this.selectedUser = this.displayedUsers[0];
      // Mark for check
      this._changeDetectorRef.markForCheck();
      return;
    }
    console.error("There's already a blank user to be filled");
  }

  /**
   * Create user
   */
  async createUser(): Promise<void> {
    // Ask for confirmation
    if (
      !(await Swal.fire({
        title: 'Create user with this information?',
        text: 'Check all fields before submit!',
        icon: 'warning',
        showCancelButton: true,
        showDenyButton: true,
        denyButtonText: `Don't create yet`,
        // Buttons text
        confirmButtonText: 'Create',
        cancelButtonText: 'Cancel',
      }).then(result => {
        if (result.isConfirmed) {
          Swal.fire('User created!', '', 'success');
        } else if (result.isDenied) {
          Swal.fire('User not created', '', 'info');
        }
        return result.isConfirmed;
      }))
    )
      return;

    // Create the user
    this._userService.createUser(this.selectedUserForm.getRawValue()).subscribe(user => {
      // Delete template user
      this.shiftBlankUser();

      // Update users with new user
      this.users.push(user);
      this.displayedUsers.push(user);

      // Go to new user
      this.selectedUser = user;

      // Fill the form
      this.selectedUserForm.patchValue(user);

      // Mark for check
      this._changeDetectorRef.markForCheck();
    });
  }

  /**
   * Update the selected user using the form data
   */
  async updateSelectedUser(): Promise<void> {
    // Ask for confirmation
    if (
      !(await Swal.fire({
        title: 'Do you want to update the user with this information?',
        text: 'Check all fields before submit!',
        icon: 'warning',
        showCancelButton: true,
        showDenyButton: true,
        denyButtonText: `Don't update`,
        // Buttons text
        confirmButtonText: 'Update',
        cancelButtonText: 'Cancel',
      }).then(result => {
        if (result.isConfirmed) {
          Swal.fire('Updated!', '', 'success');
        } else if (result.isDenied) {
          Swal.fire('Changes have not been saved', '', 'info');
        }
        return result.isConfirmed;
      }))
    )
      return;

    // Get the USER object
    let user = this.selectedUserForm.getRawValue();
    // remove password if empty
    if (user.password.length === 0) delete user.password;

    // Update the user on the server
    this._userService.updateUser(this.selectedUser.idUser, user).subscribe(updatedUser => {
      // Show a success message
      this.showFlashMessage('success');
    });
  }

  /**
   * Delete the selected user using the form data
   */
  async deleteSelectedUser(idUser): Promise<void> {
    // Ask for confirmation
    if (
      !(await Swal.fire({
        title: 'Delete this user?',
        text: "You won't be able to revert this!",
        icon: 'warning',
        showCancelButton: true,
        cancelButtonColor: '#EF4444',
        // Buttons text
        confirmButtonText: 'Delete',
        cancelButtonText: 'Cancel',
      }).then(result => {
        if (result.isConfirmed) Swal.fire('Successfully deleted!', '', 'success');
        return result.isConfirmed;
      }))
    )
      return;

    // Get the user object
    const user = this.selectedUserForm.getRawValue();

    // Delete the user on the server
    this._userService.deleteUser(user.idUser).subscribe(() => {
      // Find deleted user index
      let index = this.users.findIndex(user => user.idUser === this.selectedUser.idUser);
      // reset search
      this.searchInputControl.setValue('');
      // Update users
      this.users.splice(index, 1);
      this.displayedUsers = this.users;
      // Mark for check
      this._changeDetectorRef.markForCheck();
    });
  }

  /**
   * Show flash message
   */
  showFlashMessage(type: 'success' | 'error'): void {
    // Show the message
    this.flashMessage = type;

    // Mark for check
    this._changeDetectorRef.markForCheck();

    // Hide it after 3 seconds
    setTimeout(() => {
      this.flashMessage = null;
      // Mark for check
      this._changeDetectorRef.markForCheck();
    }, 3000);
  }

  // -----------------------------------------------------------------------------------------------------
  // @ Private methods
  // -----------------------------------------------------------------------------------------------------

  private customPasswordValidator(formGroup: FormGroup): any {
    let errors: any = {};
    Object.assign(errors, Validators.minLength(6)(formGroup.get('password')));
    Object.assign(errors, Validators.maxLength(32)(formGroup.get('password')));
    if (formGroup.value?.idUser === BLANK_TEMPLATE_ID) {
      Object.assign(errors, Validators.required(formGroup.get('password')));
    }
    formGroup.controls.password.setErrors(Object.keys(errors).length === 0 ? null : errors);
    return Object.keys(errors).length === 0 ? null : errors;
  }
}
