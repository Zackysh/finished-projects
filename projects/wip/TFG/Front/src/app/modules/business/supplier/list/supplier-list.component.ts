import { SupplierService } from 'app/core/models/business/supplier/supplier.service';
import { DEFAULT_ORDER_BY, Supplier } from 'app/core/models/business/supplier/supplier.types';
import { BLANK_TEMPLATE_ID } from 'app/core/models/core.types';
import {
    DEFAULT_PAGE, DEFAULT_SIZE, Pagination, PaginationSearch
} from 'app/core/models/pagination.types';
import { ValidateNif } from 'app/util/forms/FormHelper';
import { Subject } from 'rxjs';
import { map, switchMap, takeUntil } from 'rxjs/operators';

import {
    ChangeDetectionStrategy, ChangeDetectorRef, Component, OnDestroy, OnInit, ViewChild,
    ViewEncapsulation
} from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { MatPaginator } from '@angular/material/paginator';
import { MatDrawer } from '@angular/material/sidenav';
import { ActivatedRoute, Router } from '@angular/router';
import { FuseMediaWatcherService } from '@fuse/services/media-watcher';

@Component({
  selector: 'supplier-list',
  templateUrl: './supplier-list.component.html',
  styleUrls: ['supplier-list.component.scss'],
  encapsulation: ViewEncapsulation.None,
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class SupplierListComponent implements OnInit, OnDestroy {
  @ViewChild('matDrawer', { static: true }) matDrawer: MatDrawer;
  @ViewChild(MatPaginator) private _paginator: MatPaginator;

  suppliers: Supplier[];
  searchForm: FormGroup;

  pagination: Pagination;
  supplierCount: number = 0;
  isLoading = false;
  drawerMode: 'side' | 'over';
  private _unsubscribeAll: Subject<any> = new Subject<any>();

  tlog() { }

  /**
   * Constructor
   */
  constructor(
    private _activatedRoute: ActivatedRoute,
    private _formBuilder: FormBuilder,
    private _changeDetectorRef: ChangeDetectorRef,
    private _supplierService: SupplierService,
    private _router: Router,
    private _fuseMediaWatcherService: FuseMediaWatcherService
  ) { }

  // -----------------------------------------------------------------------------------------------------
  // @ Accessors
  // -----------------------------------------------------------------------------------------------------

  get selectedSupplier() {
    return this._supplierService.supplier$;
  }

  // -----------------------------------------------------------------------------------------------------
  // @ Lifecycle hooks
  // -----------------------------------------------------------------------------------------------------

  /**
   * On init
   */
  ngOnInit(): void {
    // Initialize searchForm
    this.searchForm = this._formBuilder.group({
      searchInput: ['', Validators.required],
      searchByID: ['']
    }, {
      validators: [this.spainIdValidator]
    })

    // Subscribe to suppliers$ changes
    this._supplierService.suppliers$
      .subscribe(suppliers => {
        this.suppliers = suppliers

        // Mark for check
        this._changeDetectorRef.markForCheck();
      })

    // Get the pagination
    this._supplierService.pagination$
      .pipe(takeUntil(this._unsubscribeAll))
      .subscribe((pagination: Pagination) => {

        // Update the pagination
        this.pagination = pagination;

        // Mark for check
        this._changeDetectorRef.markForCheck();
      });

    // Subscribe to search input field value changes
    this.searchForm.get('searchInput').valueChanges
      .pipe(
        takeUntil(this._unsubscribeAll),
        switchMap(input => {
          // null, DEFAULT_PAGE, DEFAULT_SIZE
          // Get Suppliers - without query
          if (!input) return this._supplierService.getSuppliersRemote(null, DEFAULT_PAGE, DEFAULT_SIZE, DEFAULT_ORDER_BY);
          // Configure query
          let typedInput: string = input;
          let query: PaginationSearch;
          if (this.searchForm.get('searchByID').value)
            query = { field: 'vat', value: typedInput.trim() }
          else
            query = { field: 'corporateName', value: typedInput.trim() }
          // Search - with query
          return this._supplierService.getSuppliersRemote(query, DEFAULT_PAGE, DEFAULT_SIZE);
        })
      )
      .subscribe(
        suppliers => {
          this._supplierService.updateSuppliers$(suppliers);

          // Mark for check
          this._changeDetectorRef.markForCheck();
        }
      );

    // Subscribe to media changes
    this._fuseMediaWatcherService.onMediaChange$
      .pipe(takeUntil(this._unsubscribeAll))
      .subscribe(({ matchingAliases }) => {

        // Set the drawerMode if the given breakpoint is active
        if (matchingAliases.includes('xl')) {
          this.drawerMode = 'side';
        }
        else {
          this.drawerMode = 'over';
        }

        // Mark for check
        this._changeDetectorRef.markForCheck();
      });
  }

  ngAfterViewInit(): void {
    if (this._paginator) {
      // Get suppliers if sort or page changes
      this._paginator.page.pipe(
        switchMap(() => {
          // Unselect current supplier
          this._supplierService.updateSupplier$(null, false);
          this.isLoading = true;

          let search: PaginationSearch;
          if (this.searchForm.value.searchInput) {

            if (this.searchForm.value.searchByID) {
              search = { field: 'vat', value: this.searchForm.value.searchInput }
            } else {
              search = { field: 'corporateName', value: this.searchForm.value.searchInput }
            }
          }

          return this._supplierService.getSuppliersRemote(
            search, this._paginator.pageIndex, this._paginator.pageSize, DEFAULT_ORDER_BY);
        }),
        map((suppliers) => {
          this._supplierService.updateSuppliers$(suppliers);
          this.isLoading = false;

          // Mark for check
          this._changeDetectorRef.markForCheck();
        })
      ).subscribe();
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

  /**
   * On backdrop clicked
   */
  onBackdropClicked(): void {
    // Go back to the list
    this._router.navigate(['./'], { relativeTo: this._activatedRoute });

    // Mark for check
    this._changeDetectorRef.markForCheck();
  }

  /**
   * Create supplier
   */
  createSupplier(): void {
    this._router.navigate([`./${BLANK_TEMPLATE_ID}`], { relativeTo: this._activatedRoute });
  }

  private spainIdValidator(formGroup: FormGroup): any {
    let errors: any = {};
    if (formGroup.value?.searchByID) {
      Object.assign(errors, ValidateNif(formGroup.get('searchInput')))
    }
    formGroup.controls.searchInput.setErrors(Object.keys(errors).length === 0 ? null : errors)
    return Object.keys(errors).length === 0 ? null : errors
  }
}
