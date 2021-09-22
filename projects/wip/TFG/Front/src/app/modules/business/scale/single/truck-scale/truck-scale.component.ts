import {
  ChangeDetectionStrategy,
  ChangeDetectorRef,
  Component,
  OnDestroy,
  OnInit,
  ViewEncapsulation,
} from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { CustomerService } from 'app/core/models/business/customer/customer.service';
import { Customer } from 'app/core/models/business/customer/customer.types';
import { LocationService } from 'app/core/models/business/locations/location.service';
import { Poblacion } from 'app/core/models/business/locations/location.types';
import { ProductService } from 'app/core/models/business/product/product.service';
import { Product, ProductType } from 'app/core/models/business/product/product.types';
import { ScaleService } from 'app/core/models/business/scale/scale.service';
import { Crate, Hopper, TruckScale } from 'app/core/models/business/scale/scale.types';
import { SupplierService } from 'app/core/models/business/supplier/supplier.service';
import { Supplier } from 'app/core/models/business/supplier/supplier.types';
import { PaginationSearch } from 'app/core/models/pagination.types';
import { SessionService } from 'app/core/models/user/session.service';
import { FormHelper } from 'app/util/forms/FormHelper';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';

@Component({
  selector: 'truck-scale',
  templateUrl: './truck-scale.component.html',
  encapsulation: ViewEncapsulation.None,
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class TruckScaleComponent implements OnInit, OnDestroy {
  scale: TruckScale;
  poblacion: Poblacion;

  products: Product[];
  productTypes: ProductType[];
  crates: Crate[];
  hoppers: Hopper[];
  customers: Customer[];
  suppliers: Supplier[];

  // selection
  scaleForm: FormGroup;

  clog() {
    console.log(this._formHelper.getFormValidationErrors(this.scaleForm));
  }

  private _unsubscribeAll: Subject<any> = new Subject<any>();
  isLoading = false;

  _IdInterval: number;

  /**
   * Constructor
   */
  constructor(
    private _route: ActivatedRoute,
    private _scaleService: ScaleService,
    private _customerService: CustomerService,
    private _supplierService: SupplierService,
    private _locationService: LocationService,
    private _productService: ProductService,
    private _changeDetectorRef: ChangeDetectorRef,
    private _formHelper: FormHelper,
    private _sessionService: SessionService,
    private _formBuilder: FormBuilder,
  ) {}

  // -----------------------------------------------------------------------------------------------------
  // @ Accessors
  // -----------------------------------------------------------------------------------------------------

  get product$() {
    return this.scaleForm.get('product').value;
  }
  get customer$() {
    return this.scaleForm.get('customer').value;
  }
  set customer$(customer: Customer) {
    this.scaleForm.get('customer').setValue(customer);
  }
  get supplier$() {
    return this.scaleForm.get('supplier').value;
  }
  set supplier$(supplier: Supplier) {
    this.scaleForm.get('supplier').setValue(supplier);
  }

  productType() {
    if (!this.product$ || this.product$ === '-1') return 0;
    const pType = this.productTypes.find(pt => pt.idProductType === this.product$.idProductType);
    return !pType || pType.name === 'SALE' ? 0 : 1;
  }

  // -----------------------------------------------------------------------------------------------------
  // @ Lifecycle hooks
  // -----------------------------------------------------------------------------------------------------

  /**
   * On init
   */
  ngOnInit(): void {
    // build form
    this.scaleForm = this._formBuilder.group({
      type: ['-1', [Validators.required, Validators.min(0), Validators.max(1)]],
      product: ['-1', [Validators.required, Validators.min(0)]],
      weight: ['', [Validators.required]],
      customer: [''],
      supplier: [''],
      registrationPlate: [''],
      trailer: [''],
      // search
      field: ['0'],
    });

    // fetch requested scale
    this._route.params.pipe(takeUntil(this._unsubscribeAll)).subscribe(
      params => {
        if (params.id) {
          this._scaleService.findById(params.id).subscribe(scale => {
            this.scale = scale;

            this._scaleService.findScaleProducts(params.id).subscribe(products => {
              this.products = products;
            });

            this.startWeighing();

            // Mark for check
            this._changeDetectorRef.markForCheck();
          });
        }
      },
      error => console.error('Failed to load scale'),
    );

    // fetch product-types
    this._productService.getProductTypes().subscribe(types => {
      this.productTypes = types;
    });

    // fetch crates
    this._scaleService.getAllCrates().subscribe(crates => {
      this.crates = crates;
    });

    // fetch hopper
    this._scaleService.getAllHoppers().subscribe(hoppers => {
      this.hoppers = hoppers;
    });

    // @ Form listeners
    this.scaleForm
      .get('customer')
      .valueChanges.pipe(takeUntil(this._unsubscribeAll))
      .subscribe((change: any) => {
        // input is valid
        if (change && typeof change === 'string' && change.length > 0) {
          const query: PaginationSearch = {
            field: +this.scaleForm.value.field === 0 ? 'corporateName' : 'idCustomer',
            value: change,
          };

          this._customerService.getCustomersRemote(query, 0, 15).subscribe(customers => {
            this.customers = customers;

            // Mark for check
            this._changeDetectorRef.markForCheck();
          });
        }
      });
    this.scaleForm
      .get('supplier')
      .valueChanges.pipe(takeUntil(this._unsubscribeAll))
      .subscribe((change: any) => {
        // input is valid
        if (change && typeof change === 'string' && change.length > 0) {
          const query: PaginationSearch = {
            field: +this.scaleForm.value.field === 0 ? 'corporateName' : 'idSupplier',
            value: change,
          };

          this._supplierService.getSuppliersRemote(query, 0, 15).subscribe(suppliers => {
            this.suppliers = suppliers;

            // Mark for check
            this._changeDetectorRef.markForCheck();
          });
        }
      });

    this.scaleForm
      .get('product')
      .valueChanges.pipe(takeUntil(this._unsubscribeAll))
      .subscribe((change: any) => {
        if (this.productType() === 0 && this.customer$) this.selectCustomer(this.customer$);
        else if (this.productType() === 1 && this.supplier$) this.selectSupplier(this.supplier$);
        else this.poblacion = null;
      });
  }

  /**
   * On destroy
   */
  ngOnDestroy(): void {
    // Unsubscribe from all subscriptions
    if (this._IdInterval) window.clearInterval(this._IdInterval);
    this._unsubscribeAll.next();
    this._unsubscribeAll.complete();
  }

  // -----------------------------------------------------------------------------------------------------
  // @ Public methods
  // -----------------------------------------------------------------------------------------------------

  /** form logic */
  public _isCustomerValid(): boolean {
    if (this.productType() === 0)
      // if customer is not empty string, it's not valid
      return !this.isString(this.customer$);
    return true;
  }
  /** form logic */
  public _isSupplierValid(): boolean {
    if (this.productType() === 1)
      // if supplier not empty is string, it's not valid
      return !this.isString(this.supplier$);
    return true;
  }

  /** return true only when selected customer is a customer, not search query  */
  public isSupplierSelected(): boolean {
    return this.supplier$ && typeof this.supplier$ !== 'string';
  }
  /** return true only when selected supplier is a supplier, not search query  */
  public isCustomerSelected(): boolean {
    return this.customer$ && typeof this.customer$ !== 'string';
  }

  weight(): void {
    const data = {
      idScale: this.scale.idScale,
      idProduct: this.product$.idProduct,
      idCustomer: this.customer$.idCustomer,
      idSupplier: this.supplier$.idSupplier,
      idUser: this._sessionService.user.idUser,
      weight: this.scaleForm.controls.weight.value,
      type: this.scaleForm.controls.type.value,
      registrationPlate: this.scaleForm.controls.registrationPlate.value,
      trailer: this.scaleForm.controls.trailer.value,
      // the remaining data will be handled by API
      // status, create/update dates,
    };

    // TODO send weighment to API
  }

  displayFn(customer: Customer) {
    if (customer) {
      return customer.corporateName;
    }
  }

  selectCustomer(customer: Customer) {
    this.customer$ = customer;
    if (typeof customer.idPoblacion === 'number') {
      this._locationService.findPoblacionById(customer.idPoblacion).subscribe(poblacion => {
        this.poblacion = poblacion;

        // Mark for check
        this._changeDetectorRef.markForCheck();
      });
    }

    // Mark for check
    this._changeDetectorRef.markForCheck();
  }

  selectSupplier(supplier: Supplier) {
    this.supplier$ = supplier;
    if (typeof supplier.idPoblacion === 'number') {
      this._locationService.findPoblacionById(supplier.idPoblacion).subscribe(poblacion => {
        this.poblacion = poblacion;

        // Mark for check
        this._changeDetectorRef.markForCheck();
      });
    }

    // Mark for check
    this._changeDetectorRef.markForCheck();
  }

  // warn the user if no customer is selected
  isString(obj: any) {
    return typeof obj === 'string' && obj.length > 0;
  }

  // -----------------------------------------------------------------------------------------------------
  // @ Private methods
  // -----------------------------------------------------------------------------------------------------

  private startWeighing(): void {
    if (this._IdInterval) window.clearInterval(this._IdInterval);
    this._IdInterval = window.setInterval(this.getWeight.bind(this), 1000);
  }

  private getWeight(): void {
    this._scaleService.getWeight(this.scale.idScale).subscribe(res => {
      this.scaleForm.patchValue({ weight: res });
      // Mark for check
      this._changeDetectorRef.markForCheck();
    });
  }
}
