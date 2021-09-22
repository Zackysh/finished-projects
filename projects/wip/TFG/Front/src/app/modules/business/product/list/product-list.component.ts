import { ProductService } from 'app/core/models/business/product/product.service';
import {
    DEFAULT_ORDER_BY, ean13Regex, Product, ProductType
} from 'app/core/models/business/product/product.types';
import { BLANK_TEMPLATE_ID } from 'app/core/models/core.types';
import {
    DEFAULT_PAGE, DEFAULT_SIZE, Pagination, PaginationSearch
} from 'app/core/models/pagination.types';
import { combineLatest, Subject } from 'rxjs';
import { map, switchMap, takeUntil } from 'rxjs/operators';
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
  selector: 'product-list',
  templateUrl: './product-list.component.html',
  styles: [
    /* language=SCSS */
    `
      .inventory-grid {
        grid-template-columns: 48px 120px auto 40px;

        @screen sm {
          grid-template-columns: 48px 120px 112px 130px auto 40px;
        }

        @screen md {
          grid-template-columns: 48px 120px 112px 112px 130px auto 40px;
        }

        @screen lg {
          grid-template-columns: 48px 120px 112px 112px 130px 80px 80px 48px auto 26px;
        }
      }
    `,
  ],
  providers: [TitleCasePipe],
  encapsulation: ViewEncapsulation.None,
  changeDetection: ChangeDetectionStrategy.OnPush,
  animations: fuseAnimations,
})
export class ProductListComponent implements OnInit, AfterViewInit, OnDestroy {
  @ViewChild(MatPaginator) private _paginator: MatPaginator;
  @ViewChild(MatSort) private _sort: MatSort;

  // CONST - provide access to html template
  BLANK_PRODUCT_ID = BLANK_TEMPLATE_ID;

  products: Product[];
  productTypes: ProductType[];
  pagination: Pagination;

  flashMessage: 'success' | 'error' | null = null;
  isLoading: boolean = false;
  shouldAlert: boolean = true;
  searchControl = new FormControl();
  selectedProduct: Product | null = null;
  productForm: FormGroup;
  private _unsubscribeAll: Subject<any> = new Subject<any>();

  logres() {
    console.log(this.selectedProduct);
  }

  /**
   * Constructor
   */
  constructor(
    private _changeDetectorRef: ChangeDetectorRef,
    private _formBuilder: FormBuilder,
    private _productService: ProductService,
  ) {}

  // -----------------------------------------------------------------------------------------------------
  // @ Accessors
  // -----------------------------------------------------------------------------------------------------

  get createMode() {
    return this.selectedProduct?.idProduct === BLANK_TEMPLATE_ID;
  }

  getProductType(idProductType: number) {
    return this.productTypes?.find(pt => pt.idProductType === idProductType);
  }

  // -----------------------------------------------------------------------------------------------------
  // @ Lifecycle hooks
  // -----------------------------------------------------------------------------------------------------

  /**
   * On init
   */
  ngOnInit(): void {
    // Create the selected product form
    this.productForm = this._formBuilder.group({
      name: ['', [Validators.required, Validators.maxLength(40)]],
      idProductType: ['', [Validators.required]],
      description: ['', [Validators.maxLength(75)]],
      descriptionShort: ['', [Validators.maxLength(35)]],
      reference: ['', [Validators.required, Validators.maxLength(30)]],
      alternativeReference: ['', [Validators.maxLength(30)]],
      ean13: ['', [Validators.required, Validators.pattern(ean13Regex)]],
      note: ['', [Validators.maxLength(45)]],
      onSale: ['', [Validators.required]],
      active: ['', [Validators.required]],
      quantity: [''],
      outOfStock: ['', [Validators.required]],
      createDate: [''],
      updateDate: [''],
    });

    this._productService.product$.pipe(takeUntil(this._unsubscribeAll)).subscribe(product => {
      this.selectedProduct = product;

      // Mark for check
      this._changeDetectorRef.markForCheck();
    });

    // Subscribe to product changes
    this._productService.products$.pipe(takeUntil(this._unsubscribeAll)).subscribe(products => {
      this.products = products;
      console.log('===============');
      console.log(products);

      // Mark for check
      this._changeDetectorRef.markForCheck();
    });

    const obsv = combineLatest([
      this._productService.getProductsRemote(null, 0, 15),
      this._productService.getProductTypes(),
    ]);

    // Fetch products and types
    obsv.pipe(takeUntil(this._unsubscribeAll)).subscribe(([products, productTypes]) => {
      this.productTypes = productTypes;
      this._productService.updateProducts$(products);

      // Mark for check
      this._changeDetectorRef.markForCheck();
    });

    // Subscribe to search input field value changes
    this.searchControl.valueChanges.subscribe(query => {
      // If creation is in process ...
      // if (this._isNewProduct) return;
      // Close details
      this.closeDetails();
      // Mark for check
      this._changeDetectorRef.markForCheck();
    });

    // Subscribe to pagination
    this._productService.pagination$
      .pipe(takeUntil(this._unsubscribeAll))
      .subscribe((pagination: Pagination) => {
        // Update the pagination
        this.pagination = pagination;

        // Mark for check
        this._changeDetectorRef.markForCheck();
      });

    // @ Search
    this.searchControl.valueChanges
      .pipe(
        takeUntil(this._unsubscribeAll),
        switchMap(input => {
          // Get Suppliers - without query
          if (!input)
            return this._productService.getProductsRemote(
              null,
              DEFAULT_PAGE,
              DEFAULT_SIZE,
              DEFAULT_ORDER_BY,
            );
          // Configure query
          let typedInput: string = input;
          let query: PaginationSearch = { field: 'name', value: typedInput.trim() };
          // Search - with query
          return this._productService.getProductsRemote(query, DEFAULT_PAGE, DEFAULT_SIZE);
        }),
      )
      .subscribe(products => {
        this._productService.updateProducts$(products);

        // Mark for check
        this._changeDetectorRef.markForCheck();
      });
  }

  /**
   * After view init
   */
  ngAfterViewInit(): void {
    // @ Sort logic

    // if (this.pagination) {
    // Set the initial sort
    // this._sort.sort({
    //   id: 'name',
    //   start: 'asc',
    //   disableClear: true,
    // });

    // If the product changes the sort order...
    // this._sort.sortChange.pipe(takeUntil(this._unsubscribeAll)).subscribe(() => {
    //   // Reset back to the first page
    //   this._paginator.pageIndex = 0;

    //   // Close the details
    //   this.closeDetails();
    // });

    // Get products if sort or page changes
    // merge(this._sort.sortChange, this._paginator.page).pipe(
    //   switchMap(() => {
    //     this.closeDetails();
    //     this.isLoading = true;
    //     return this._productService.getProducts(this._paginator.pageIndex, this._paginator.pageSize, this._sort.active, this._sort.direction);
    //   }),
    //   map(() => {
    //     this.isLoading = false;
    //   })
    // ).subscribe();
    // }

    // @ Pagination logic

    // Get suppliers if sort or page changes
    this._paginator.page
      .pipe(
        switchMap(() => {
          // Unselect current supplier
          this._productService.updateProduct$(null);
          this.isLoading = true;

          let search: PaginationSearch;
          if (this.searchControl.value) search = { field: 'name', value: this.searchControl.value };

          return this._productService.getProductsRemote(
            search,
            this._paginator.pageIndex,
            this._paginator.pageSize,
            DEFAULT_ORDER_BY,
          );
        }),
        map(products => {
          this._productService.updateProducts$(products);
          this.isLoading = false;

          // Mark for check
          this._changeDetectorRef.markForCheck();
        }),
      )
      .subscribe();

    // Mark for check
    this._changeDetectorRef.markForCheck();
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
   * Toggle product details
   *
   * @param idProduct
   */
  async toggleDetails(idProduct: number): Promise<void> {
    // If new product is selected ...
    if (this.createMode) {
      // Unable to close, just cancel or submitssssss
      return;
    }

    // If the product is already selected ...
    if (this.selectedProduct && this.selectedProduct.idProduct === idProduct) {
      // Close the details
      this.closeDetails();
      // reset select
      return;
    }

    // if no product is selected ...
    if (!this.selectedProduct)
      if (
        !(await Swal.fire({
          title: 'Edit this product?',
          text: "You won't be able to revert any change!",
          icon: 'question',
          showCancelButton: true,
          // Buttons text
          confirmButtonText: 'Edit',
          cancelButtonText: 'Cancel',
        }).then(result => result.isConfirmed))
      )
        return; // !isConfirmed

    // Select the product
    this._productService.getProductById$(idProduct).subscribe(product => {
      this._productService.updateProduct$(product);
    });
  }

  /**
   * Close the details
   */
  closeDetails(): void {
    this.selectedProduct = null;
  }

  // @ Form building

  /**
   * Delete existing row for product creation
   */
  shiftBlankProduct() {
    // If there is a blank product ...
    if (this.createMode) {
      // Close the details
      this.closeDetails();

      // shift blank-template from products
      this.products.shift();

      // Mark for check
      this._changeDetectorRef.markForCheck();
    }
  }

  /**
   * Create row for product creation
   */
  unshiftNewProduct(): void {
    // If there isn't already a blank product ...
    if (!this.createMode) {
      // @ load first products
      this._productService.getProductsRemote(null, 0, 15).subscribe(products => {
        // update
        this._productService.unshiftTemplate();

        // Mark for check
        this._changeDetectorRef.markForCheck();
      });

      return;
    }
    console.error("There's already a blank product to be filled");
  }

  // @ Form Actions

  /**
   * Create product
   */
  async createProduct(): Promise<void> {
    // Ask for confirmation
    if (
      !(await Swal.fire({
        title: 'Create product with this information?',
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
          Swal.fire('Product created!', '', 'success');
        } else if (result.isDenied) {
          Swal.fire('Product not created', '', 'info');
        }
        return result.isConfirmed;
      }))
    )
      return;

    // Get the PRODUCT object to parse necessary fields
    let product: Product = this.productForm.getRawValue();
    product.active = Boolean(product.active);
    product.onSale = Boolean(product.onSale);
    product.ean13 = `${product.ean13}`;
    delete product.updateDate;
    delete product.createDate;

    // Create the product
    this._productService.createProductRemote(product).subscribe(product => {
      // select created product
      this.selectedProduct = product;

      this._productService.getProductsRemote(null, this.pagination.count / 15, 15),
        // Mark for check
        this._changeDetectorRef.markForCheck();
    });
  }

  /**
   * Update the selected product using the form data
   */
  async updateProduct(): Promise<void> {
    // Ask for confirmation
    const res = await Swal.fire({
      title: 'Do you want to update the product with this information?',
      text: 'Check all fields before submit!',
      icon: 'warning',
      showCancelButton: true,
      showDenyButton: true,
      denyButtonText: `Don't update`,
      // Buttons text
      confirmButtonText: 'Update',
      cancelButtonText: 'Cancel',
    }).then(result => {
      if (result.isDenied) {
        Swal.fire('Changes have not been saved', '', 'info');
      }
      return result.isConfirmed;
    });

    if (!res) return;

    // Get the PRODUCT object to parse necessary fields
    let product: Product = this.productForm.getRawValue();
    product.active = Boolean(product.active);
    product.onSale = Boolean(product.onSale);
    product.ean13 = `${product.ean13}`;
    delete product.updateDate;
    delete product.createDate;

    // Update the product on the server
    this._productService.updateProductRemote(this.selectedProduct.idProduct, product).subscribe(
      updatedProduct => {
        // update product on list
        this._productService.updateProduct$(updatedProduct, true);
        // Show success message
        Swal.fire('Updated!', '', 'success');
        this.showFlashMessage('success');
      },
      error => {
        Swal.fire({
          title: 'Error during edition',
          text:
            'An unexpected error has occurred, please, contact technical support, ' +
            'you can send this info: ',
          showCancelButton: true,
          confirmButtonText: 'Download error log',
          icon: 'error',
        }).then(result => {
          if (result.isConfirmed) {
            const url = window.URL.createObjectURL(new Blob([JSON.stringify(error)]));
            const link = document.createElement('a');
            link.href = url;
            link.setAttribute('download', `error-log-${new Date().toISOString()}.json`);
            document.body.appendChild(link);
            link.click();
          }
        });
      },
    );
  }

  /**
   * Delete the selected product using the form data
   */
  async deleteSelectedProduct(idProduct: number): Promise<void> {
    // Ask for confirmation
    if (
      !(await Swal.fire({
        title: 'Delete this product?',
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

    // Get the product object
    const product = this.productForm.getRawValue();

    // Delete the product on the server
    this._productService.deleteProductRemote(product.idProduct).subscribe(() => {
      // Find deleted product index
      let index = this.products.findIndex(
        product => product.idProduct === this.selectedProduct.idProduct,
      );
      // reset search
      this.searchControl.setValue('');
      // Update products
      this.products.splice(index, 1);
      // Mark for check
      this._changeDetectorRef.markForCheck();
    });
  }

  // @ Util

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
}
