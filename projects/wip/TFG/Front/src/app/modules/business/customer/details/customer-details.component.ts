import { CustomerService } from 'app/core/models/business/customer/customer.service';
import { Customer, PaymentMethod } from 'app/core/models/business/customer/customer.types';
import { LocationService } from 'app/core/models/business/locations/location.service';
import {
    Country, Municipio, Poblacion, Provincia
} from 'app/core/models/business/locations/location.types';
import { BLANK_TEMPLATE_ID } from 'app/core/models/core.types';
import { SessionService } from 'app/core/models/user/session.service';
import { ValidateNif } from 'app/util/forms/FormHelper';
import { combineLatest, Observable, Subject } from 'rxjs';
import { switchMap, takeUntil } from 'rxjs/operators';
import Swal from 'sweetalert2';

import {
    ChangeDetectionStrategy, ChangeDetectorRef, Component, OnDestroy, OnInit, ViewEncapsulation
} from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSelectChange } from '@angular/material/select';
import { MatDrawerToggleResult } from '@angular/material/sidenav';
import { Router } from '@angular/router';
import { fuseAnimations } from '@fuse/animations';

import { CustomerListComponent } from '../list/customer-list.component';

@Component({
  selector: 'customer-details',
  templateUrl: './customer-details.component.html',
  encapsulation: ViewEncapsulation.None,
  changeDetection: ChangeDetectionStrategy.OnPush,
  styleUrls: ['customer-details.component.scss'],
  animations: fuseAnimations,
})
export class CustomerDetailsComponent implements OnInit, OnDestroy {
  sendImage(file: File) {
    console.log(file);
    this._customerService.upload(file);
  }

  // CONST - provide access to html template
  BLANK_TEMPLATE_ID = BLANK_TEMPLATE_ID;
  editMode: boolean = false;
  customer: Customer;
  customerForm: FormGroup;
  paymentMethods: PaymentMethod[];
  countries: Country[];
  provincias: Provincia[];
  poblaciones: Poblacion[];
  municipios: Municipio[];

  // Auxiliary DOM handler
  idPoblacion: number;

  isLoading = false;
  private _unsubscribeAll: Subject<any> = new Subject<any>();
  private _unsubscribeCustomerRelated: Subject<any> = new Subject<any>();

  /**
   * Constructor
   */
  constructor(
    private _customerService: CustomerService,
    private _locationsService: LocationService,
    private _sessionService: SessionService,
    private _changeDetectorRef: ChangeDetectorRef,
    private _customerListComponent: CustomerListComponent,
    private _formBuilder: FormBuilder,
    private _router: Router,
  ) {}

  // -----------------------------------------------------------------------------------------------------
  // @ Public Accessors
  // -----------------------------------------------------------------------------------------------------
  /** Flag for template logic */
  get createMode() {
    return this.customer?.idInternal === BLANK_TEMPLATE_ID;
  }
  get viewMode() {
    return !(this.createMode || this.editMode);
  }

  get anyChange() {
    for (const key in this.customer) {
      if (this.customerForm.value.hasOwnProperty(key)) {
        if (this.customer[key] != this.customerForm.value[key]) {
          return true;
        }
      }
    }
    return false;
  }

  // ----------------------------------
  // @ Form control accessors
  // ----------------------------------
  get type$() {
    return this.customerForm.value.type;
  }
  set type$(value) {
    this.customerForm.patchValue({ type: value });
  }
  get newsletter$() {
    return this.customerForm.value.newsletter;
  }
  set newsletter$(value) {
    this.customerForm.patchValue({ newsletter: +value });
  }
  get surcharge$() {
    return this.customerForm.value.surcharge;
  }
  set surcharge$(value) {
    this.customerForm.patchValue({ surcharge: +value });
  }
  get idCountry$() {
    return this.customerForm.value.idCountry;
  }
  get customerPoblacion$() {
    if (!this.poblaciones) return '';
    let poblacion = this.poblaciones.find(p => p.idPoblacion === +this.customer.idPoblacion);
    return poblacion ? poblacion.poblacion + ' - ' + poblacion.postalCode : '';
  }
  get customerFormPoblacion$() {
    if (!this.poblaciones) return '';
    let poblacion = this.poblaciones.find(p => p.idPoblacion === +this.customerForm.value.idPoblacion);
    return poblacion ? poblacion.poblacion + ' - ' + poblacion.postalCode : '';
  }
  get isSpain() {
    return +this.customerForm.get('idCountry').value === 73;
  }

  // -----------------------------------------------------------------------------------------------------
  // @ Lifecycle hooks
  // -----------------------------------------------------------------------------------------------------

  /**
   * On init
   */
  ngOnInit(): void {
    // Create the group form
    this.customerForm = this._formBuilder.group(
      {
        corporateName: ['', [Validators.required, Validators.maxLength(40)]],
        businessName: ['', [Validators.maxLength(40)]],
        type: [1, [Validators.required]],
        vat: ['', [Validators.required]], // TODO Validate NIF only Spain
        address: ['', [Validators.required, Validators.maxLength(55)]],
        postalCode: ['', [Validators.required]],
        idCountry: ['', [Validators.required]],
        idPoblacion: [''],
        idProvincia: [''],
        nombreMunicipio: [''],
        particularState: ['', [Validators.maxLength(45)]],
        particularCity: ['', [Validators.maxLength(45)]],
        particularTown: ['', [Validators.maxLength(45)]],
        sex: ['', [Validators.required, Validators.maxLength(1)]],
        birthDate: [new Date(), [Validators.maxLength(45)]],
        phone1: ['', [Validators.maxLength(12)]],
        phone2: ['', [Validators.maxLength(12)]],
        contact: ['', [Validators.maxLength(45)]],
        email: ['', [Validators.maxLength(128)]],
        web: ['', [Validators.maxLength(128)]],
        newsletter: [''],
        popupMessage: ['', [Validators.maxLength(128)]],
        observations: ['', [Validators.maxLength(500)]],
        codEconomicZone: [''],
        idPaymentMethod: [''],
        chargeAccount: [''],
        salesAccount: [''],
        usesTariff: [''],
        idTariff: [''],
        surcharge: [''],
        idUser: [''],
        creationDate: [''],
        modificationDate: [''],
      },
      {
        validators: [this.tariffValidator, this.nifValidator],
      },
    );

    // Fetch countries and provincias only onInit
    combineLatest([
      this._locationsService.countries$,
      this._locationsService.provincias$,
      this._customerService.getPaymentMethods(),
    ])
      .pipe(takeUntil(this._unsubscribeAll))
      .subscribe(([countries, provincias, paymentMethods]) => {
        this.countries = countries;
        this.provincias = provincias;
        this.paymentMethods = paymentMethods;
      });

    // Subscribe to customer$ changes
    this._customerService.customer$.pipe(takeUntil(this._unsubscribeAll)).subscribe(customer => {
      if (!customer) {
        // Navigate back
        this._router.navigateByUrl('home/customer');

        // Mark for check
        this._changeDetectorRef.markForCheck();
        return;
      }

      this._unsubscribeCustomerRelated.next();
      this.customer = undefined;
      this.customerForm.reset();
      this.idPoblacion = undefined;
      // Store customer ...
      this.customer = customer;

      // Disable idTariff if customer doesn't useTariff
      if (customer.usesTariff === 0) this.customerForm.controls['idTariff'].disable();

      // Initialize poblaciones if customer is from spain and from any provincia
      if (this.customer.idCountry === 73 && this.customer.idProvincia) {
        this._locationsService
          .getPoblacionesByProvincia(this.customer.idProvincia)
          .subscribe(poblaciones => {
            if (this.customer.idPoblacion) {
              let customerPob = poblaciones.find(p => +p.idPoblacion === +this.customer.idPoblacion);
              // If selected poblacion exists on new poblaciones reset it
              if (customerPob) {
                this.filterPoblaciones({ municipio: customerPob.municipio });
              } else {
                console.error(
                  "Customer poblacion couldn't be found " +
                    'in poblaciones which have same poblacion that customer',
                );
                // this.setFirstPoblacion();
              }
            }
            // Store poblaciones ...
            this.poblaciones = poblaciones;
            // Extract municipios ...
            this.municipios = this.extractMunicipios(poblaciones);
            // Patch customer data on form, only after poblaciones and municipios are fetched
            this.customerForm.patchValue(this.customer);
            // Store ngModel field
            this.idPoblacion = this.customer.idPoblacion;
            // Patch nombreMunicipio ...
            this.customerForm.patchValue({
              nombreMunicipio:
                this.poblaciones.find(p => p.idPoblacion == this.customer.idPoblacion)?.municipio || '',
            });
            // Parse necessary fields
            this.customerForm?.patchValue({
              type: `${customer.type}`,
              usesTariff: `${customer.usesTariff}`,
              idTariff: `${customer.idTariff}`,
            });
            // FIX BUG
            // this.customerForm.get('idPoblacion')
            //   .setValue(this.poblaciones[this.poblaciones.length - 1].idPoblacion)
            // this.idPoblacion = this.poblaciones[this.poblaciones.length - 1].idPoblacion
            setTimeout(() => {
              this.customerForm.get('idPoblacion').setValue(this.customer.idPoblacion);
              this.idPoblacion = this.customer.idPoblacion;
              // Mark for check
              this._changeDetectorRef.markForCheck();
            }, 800);

            this.startCustomerSubscriptions();

            // Mark for check
            this._changeDetectorRef.markForCheck();
          });
      } else {
        // Patch form value
        this.customerForm.patchValue(this.customer);
        this.startCustomerSubscriptions();
      }

      // Mark for check
      this._changeDetectorRef.markForCheck();
    });

    // Open the drawer
    this._customerListComponent.matDrawer.open();
  }

  /**
   * On destroy
   */
  ngOnDestroy(): void {
    // Unsubscribe from all subscriptions
    this._unsubscribeAll.next();
    this._unsubscribeAll.complete();
    this._customerService.updateCustomer$(null, false);
  }

  // -----------------------------------------------------------------------------------------------------
  // @ Public methods
  // -----------------------------------------------------------------------------------------------------

  startCustomerSubscriptions(): void {
    // // Subscribe to municipio changes
    // // to handle poblaciones fetch ...
    this.customerForm
      .get('nombreMunicipio')
      .valueChanges.pipe(takeUntil(this._unsubscribeCustomerRelated))
      .subscribe(municipio => {
        if (municipio != '') {
          this.filterPoblaciones({ municipio });
        }
      });
    // // Subscribe to municipio changes
    // // to handle poblaciones fetch ...
    this.customerForm
      .get('idProvincia')
      .valueChanges.pipe(takeUntil(this._unsubscribeCustomerRelated))
      .subscribe(idProvincia => {
        if (idProvincia != '') {
          this.filterPoblaciones({ idProvincia });
        }
      });
    // // Subscribe to municipio changes
    // // to handle poblaciones fetch ...
    this.customerForm
      .get('idPoblacion')
      .valueChanges.pipe(takeUntil(this._unsubscribeCustomerRelated))
      .subscribe(idPoblacion => {
        if (idPoblacion) {
          if (this.customer.idPoblacion === +idPoblacion) {
            this.customerForm.patchValue({ postalCode: this.customer.postalCode });
          } else {
            let postalCode = this.poblaciones.find(p => p.idPoblacion === +idPoblacion).postalCode;
            this.customerForm.patchValue({ postalCode });
          }
          let newMunicipio = this.poblaciones.find(p => p.idPoblacion === +idPoblacion)?.municipio;
          // If newMunicipio exists and it's not currently selected
          if (newMunicipio && newMunicipio !== this.customerForm.value.nombreMunicipio) {
            this.customerForm.get('nombreMunicipio').setValue(newMunicipio);
          }
        }
      });
    // Subscribe to idCountry
    this.customerForm
      .get('idCountry')
      .valueChanges.pipe(takeUntil(this._unsubscribeCustomerRelated))
      .subscribe(newValue => {
        if (+newValue === 73) {
          if (this.customer.idProvincia) {
            this.filterPoblaciones({ idProvincia: this.customer.idProvincia });
          }
        }
      });
  }

  filterPoblaciones(query: { idProvincia?: number; municipio?: string }): void {
    if (!query || (!query.idProvincia && !query.municipio)) {
      return;
    }
    if (query.municipio)
      this.setPoblaciones(this._locationsService.getPoblacionesByMunicipio(query.municipio), false);
    else this.setPoblaciones(this._locationsService.getPoblacionesByProvincia(query.idProvincia), true);
  }

  onPoblacionChange(e: MouseEvent) {
    this.customerForm.patchValue({ idPoblacion: e });
  }

  /**
   * Extract municipio for each poblacion in a collection
   * and return within a array.
   *
   * @param poblaciones
   * @returns municipios
   */
  extractMunicipios(poblaciones: Poblacion[]): Municipio[] {
    let municipios = poblaciones.map(p => {
      return { name: p.municipio, idPoblacion: p.idPoblacion, idProvincia: p.idProvincia };
    });
    // Remove duplicate objects ...
    return municipios.filter(
      (municipio, index, self) =>
        index === self.findIndex(t => t.name === municipio.name && t.name === municipio.name),
    );
  }

  /**
   * Close the drawer
   */
  closeDrawer(): Promise<MatDrawerToggleResult> {
    return this._customerListComponent.matDrawer.close();
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
    /*     if (this.editMode) {
          this.customerForm.get('type').disable();
          this.customerForm.get('type').disable();
          this.customerForm.get('type').disable();
        } */
    // Mark for check
    this._changeDetectorRef.markForCheck();
  }

  // ----------------------------------
  // @ Form control methods
  // ----------------------------------

  /**
   * Disable/Enable and reset idTariff when it's necessary.
   *
   * @param event event with usesTariff value
   */
  usesTariff(event: MatSelectChange) {
    // If usesTariff is un-checked ...
    if (event.value == 0) {
      this.customerForm.controls['idTariff'].reset('');
      this.customerForm.controls['idTariff'].disable();
      return;
    }
    this.customerForm.controls['idTariff'].reset(`${this.customer.idTariff}`);
    this.customerForm.controls['idTariff'].enable();
  }

  deleteCustomer(): void {
    this._customerService.deleteCustomerRemote(this.customer.idInternal).subscribe(
      success => {
        // Set customer$ to null and remove it from customers$
        this._customerService.deleteCustomer$();
        // Navigate back
        this._router.navigateByUrl('home/customer');
        // Mark for check
        this._changeDetectorRef.markForCheck();
      },
      async error => {
        Swal.fire({
          title: 'Error during deletion',
          text: 'An unexpected error has occurred, please, contact technical support',
          icon: 'error',
          confirmButtonText: 'Ok',
        });
      },
    );
  }

  /**
   * Create the customer
   */
  createCustomer(): void {
    // Parse necessary fields
    this.parseNumberValues();
    // Get current logged user
    this._sessionService.user$
      .pipe(
        switchMap(user => {
          let toSend: any = {};
          Object.assign(toSend, this.customerForm.value);
          toSend.idUser = user.idUser;
          Object.keys(toSend).forEach(k => toSend[k] === null && delete toSend[k]);

          return this._customerService.createCustomerRemote(toSend);
        }),
      )
      .subscribe(
        customer => {
          // Push customer to the service
          this._customerService.createCustomer$(customer);

          // Navigate back
          this._router.navigateByUrl('home/customer');

          // Mark for check
          this._changeDetectorRef.markForCheck();
        },
        error => {
          Swal.fire({
            title: 'Error during creation',
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
              link.setAttribute('download', 'favorites.json'); //or any other extension
              document.body.appendChild(link);
              link.click();
            }
          });
        },
      );
  }

  /**
   * Update the customer
   */
  updateCustomer(): void {
    // Parse necessary fields
    this.parseNumberValues();
    // Get current logged user
    this._sessionService.user$
      .pipe(
        switchMap(user => {
          let toSend: any = {};
          Object.assign(toSend, this.customerForm.value);
          toSend.idUser = user.idUser;
          Object.keys(toSend).forEach(k => toSend[k] === null && delete toSend[k]);

          return this._customerService.updateCustomerRemote(this.customer.idInternal, toSend);
        }),
      )
      .subscribe(
        customer => {
          this._customerService.updateCustomer$(customer, true);
          // Navigate back
          this._router.navigateByUrl('home/customer');

          // Mark for check
          this._changeDetectorRef.markForCheck();
        },
        error => {
          console.error(error);

          if (error.status === 422) {
            Swal.fire({
              title: 'Error during creation',
              text: 'There is already a customer with this ID: ' + this.customerForm.value.vat,
              confirmButtonText: 'Try again',
              icon: 'warning',
            });
            return;
          }
          Swal.fire({
            title: 'Error during creation',
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
              link.setAttribute('download', 'favorites.json'); //or any other extension
              document.body.appendChild(link);
              link.click();
            }
          });
        },
      );
  }

  /**
   * Parse customerForm values that must be number but has been set
   * to string, since there's inputs that can't be configured to produce numbers.
   */
  private parseNumberValues() {
    let val = this.customerForm.value;
    // Parse necessary fields
    this.customerForm.patchValue({
      idPoblacion: +val.idPoblacion,
      type: +val.type,
      postalCode: +val.postalCode,
      chargeAccount: +val.chargeAccount,
      salesAccount: +val.salesAccount,
      phone1: +val.phone1,
      phone2: +val.phone2,
      idTariff: +val.idTariff,
      usesTariff: +val.usesTariff,
    });
  }

  private setPoblaciones(poblaciones: Observable<Poblacion[]>, extractMunicipios: boolean): void {
    poblaciones.subscribe(poblaciones => {
      // Store new poblaciones
      this.poblaciones = poblaciones;

      // If customer have poblacion, reset it to FIX BUG
      if (this.customerForm.value.idPoblacion) {
        let customerPob = this.poblaciones.find(
          p => p.idPoblacion === +this.customerForm.value.idPoblacion,
        );
        // If selected poblacion exists on new poblaciones reset it
        if (customerPob) {
          let original = this.customerForm.value.idPoblacion;
          // FIX BUG
          this.setFirstPoblacion();
          setTimeout(() => {
            this.customerForm.get('idPoblacion').setValue(original);
            this.idPoblacion = original;
            // Mark for check
            this._changeDetectorRef.markForCheck();
          }, 800);
        } else {
          // this.setFirstPoblacion();
        }
      } else {
        // this.setFirstPoblacion();
      }
      if (extractMunicipios) {
        this.municipios = this.extractMunicipios(poblaciones);
      }
      // Mark for check
      this._changeDetectorRef.markForCheck();
    });
  }

  private setFirstPoblacion(): void {
    this.customerForm.get('idPoblacion').setValue(this.poblaciones[0].idPoblacion);
    this.idPoblacion = this.poblaciones[0].idPoblacion;
  }

  private tariffValidator(formGroup: FormGroup): any {
    let errors: any = {};
    if (formGroup.value?.usesTariff == 1) {
      Object.assign(errors, Validators.required(formGroup.get('idTariff')));
    }
    formGroup.controls.idTariff.setErrors(Object.keys(errors).length === 0 ? null : errors);
    return Object.keys(errors).length === 0 ? null : errors;
  }
  private nifValidator(formGroup: FormGroup): any {
    let errors: any = {};
    if (formGroup.value?.idCountry == '73') {
      Object.assign(errors, ValidateNif(formGroup.get('vat')));
    }
    formGroup.controls.vat.setErrors(Object.keys(errors).length === 0 ? null : errors);
    return Object.keys(errors).length === 0 ? null : errors;
  }
}
