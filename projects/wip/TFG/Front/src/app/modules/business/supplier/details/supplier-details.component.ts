import { CustomerService } from 'app/core/models/business/customer/customer.service';
import { LocationService } from 'app/core/models/business/locations/location.service';
import {
    Country, Municipio, Poblacion, Provincia
} from 'app/core/models/business/locations/location.types';
import { SupplierService } from 'app/core/models/business/supplier/supplier.service';
import { PaymentMethod, Supplier } from 'app/core/models/business/supplier/supplier.types';
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
import { MatDrawerToggleResult } from '@angular/material/sidenav';
import { Router } from '@angular/router';
import { fuseAnimations } from '@fuse/animations';

import { SupplierListComponent } from '../list/supplier-list.component';

@Component({
  selector: 'supplier-details',
  templateUrl: './supplier-details.component.html',
  encapsulation: ViewEncapsulation.None,
  changeDetection: ChangeDetectionStrategy.OnPush,
  styleUrls: ['supplier-details.component.scss'],
  animations: fuseAnimations,
})
export class SupplierDetailsComponent implements OnInit, OnDestroy {
  tlog() {}

  // CONST - provide access to html template
  BLANK_TEMPLATE_ID = BLANK_TEMPLATE_ID;
  editMode: boolean = false;
  supplier: Supplier;
  supplierForm: FormGroup;
  paymentMethods: PaymentMethod[];
  countries: Country[];
  provincias: Provincia[];
  poblaciones: Poblacion[];
  municipios: Municipio[];

  // Auxiliary DOM handler
  idPoblacion: number;

  isLoading = false;
  private _unsubscribeAll: Subject<any> = new Subject<any>();
  private _unsubscribeSupplierRelated: Subject<any> = new Subject<any>();

  /**
   * Constructor
   */
  constructor(
    private _supplierService: SupplierService,
    private _customerService: CustomerService,
    private _locationsService: LocationService,
    private _sessionService: SessionService,
    private _changeDetectorRef: ChangeDetectorRef,
    private _supplierListComponent: SupplierListComponent,
    private _formBuilder: FormBuilder,
    private _router: Router,
  ) {}

  // -----------------------------------------------------------------------------------------------------
  // @ Public Accessors
  // -----------------------------------------------------------------------------------------------------
  /** Flag for template logic */
  get createMode() {
    return this.supplier?.idSupplier === BLANK_TEMPLATE_ID;
  }
  get viewMode() {
    return !(this.createMode || this.editMode);
  }

  get anyChange() {
    for (const key in this.supplier) {
      if (this.supplierForm.value.hasOwnProperty(key)) {
        if (this.supplier[key] != this.supplierForm.value[key]) {
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
    return this.supplierForm.value.type;
  }
  set type$(value) {
    this.supplierForm.patchValue({ type: value });
  }
  get newsletter$() {
    return this.supplierForm.value.newsletter;
  }
  set newsletter$(value) {
    this.supplierForm.patchValue({ newsletter: +value });
  }
  get surcharge$() {
    return this.supplierForm.value.surcharge;
  }
  set surcharge$(value) {
    this.supplierForm.patchValue({ surcharge: +value });
  }
  get idCountry$() {
    return this.supplierForm.value.idCountry;
  }
  get supplierPoblacion$() {
    if (!this.poblaciones) return '';
    let poblacion = this.poblaciones.find(p => p.idPoblacion === +this.supplier.idPoblacion);
    return poblacion ? poblacion.poblacion + ' - ' + poblacion.postalCode : '';
  }
  get supplierFormPoblacion$() {
    if (!this.poblaciones) return '';
    let poblacion = this.poblaciones.find(p => p.idPoblacion === +this.supplierForm.value.idPoblacion);
    return poblacion ? poblacion.poblacion + ' - ' + poblacion.postalCode : '';
  }
  get isSpain() {
    return +this.supplierForm.get('idCountry').value === 73;
  }

  // -----------------------------------------------------------------------------------------------------
  // @ Lifecycle hooks
  // -----------------------------------------------------------------------------------------------------

  /**
   * On init
   */
  ngOnInit(): void {
    // Create the group form
    this.supplierForm = this._formBuilder.group(
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
        sex: ['', [Validators.required, Validators.maxLength(1)]], // M, F, E (empresa), A (administración pública)
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
        surcharge: [''],
        holdback: [''],
        idUser: [''],
        creationDate: [''],
        modificationDate: [''],
      },
      {
        validators: [this.nifValidator],
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

    // Subscribe to supplier$ changes
    this._supplierService.supplier$.pipe(takeUntil(this._unsubscribeAll)).subscribe(supplier => {
      if (!supplier) {
        // Navigate back
        this._router.navigateByUrl('home/supplier');

        // Mark for check
        this._changeDetectorRef.markForCheck();
        return;
      }

      this._unsubscribeSupplierRelated.next();
      this.supplier = undefined;
      this.supplierForm.reset();
      this.idPoblacion = undefined;
      // Store supplier ...
      this.supplier = supplier;

      // Initialize poblaciones if supplier is from spain and from any provincia
      if (this.supplier.idCountry === 73 && this.supplier.idProvincia) {
        this._locationsService
          .getPoblacionesByProvincia(this.supplier.idProvincia)
          .subscribe(poblaciones => {
            if (this.supplier.idPoblacion) {
              let supplierPob = poblaciones.find(p => +p.idPoblacion === +this.supplier.idPoblacion);
              // If selected poblacion exists on new poblaciones reset it
              if (supplierPob) {
                this.filterPoblaciones({ municipio: supplierPob.municipio });
              } else {
                console.error(
                  "Supplier poblacion couldn't be found " +
                    'in poblaciones which have same poblacion that supplier',
                );
                // this.setFirstPoblacion();
              }
            }
            // Store poblaciones ...
            this.poblaciones = poblaciones;
            // Extract municipios ...
            this.municipios = this.extractMunicipios(poblaciones);
            // Patch supplier data on form, only after poblaciones and municipios are fetched
            this.supplierForm.patchValue(this.supplier);
            // Store ngModel field
            this.idPoblacion = this.supplier.idPoblacion;
            // Patch nombreMunicipio ...
            this.supplierForm.patchValue({
              nombreMunicipio:
                this.poblaciones.find(p => p.idPoblacion == this.supplier.idPoblacion)?.municipio || '',
            });
            // Parse necessary fields
            this.supplierForm?.patchValue({
              type: `${supplier.type}`,
              holdback: `${supplier.holdback}`,
            });
            // FIX BUG
            // this.supplierForm.get('idPoblacion')
            //   .setValue(this.poblaciones[this.poblaciones.length - 1].idPoblacion)
            // this.idPoblacion = this.poblaciones[this.poblaciones.length - 1].idPoblacion
            setTimeout(() => {
              this.supplierForm.get('idPoblacion').setValue(this.supplier.idPoblacion);
              this.idPoblacion = this.supplier.idPoblacion;
              // Mark for check
              this._changeDetectorRef.markForCheck();
            }, 800);

            this.startSupplierSubscriptions();

            // Mark for check
            this._changeDetectorRef.markForCheck();
          });
      } else {
        // Patch form value
        this.supplierForm.patchValue(this.supplier);
        this.startSupplierSubscriptions();
      }

      // Mark for check
      this._changeDetectorRef.markForCheck();
    });

    // Open the drawer
    this._supplierListComponent.matDrawer.open();
  }

  /**
   * On destroy
   */
  ngOnDestroy(): void {
    // Unsubscribe from all subscriptions
    this._unsubscribeAll.next();
    this._unsubscribeAll.complete();
    this._supplierService.updateSupplier$(null, false);
  }

  // -----------------------------------------------------------------------------------------------------
  // @ Public methods
  // -----------------------------------------------------------------------------------------------------

  startSupplierSubscriptions(): void {
    // // Subscribe to municipio changes
    // // to handle poblaciones fetch ...
    this.supplierForm
      .get('nombreMunicipio')
      .valueChanges.pipe(takeUntil(this._unsubscribeSupplierRelated))
      .subscribe(municipio => {
        if (municipio != '') {
          this.filterPoblaciones({ municipio });
        }
      });
    // // Subscribe to municipio changes
    // // to handle poblaciones fetch ...
    this.supplierForm
      .get('idProvincia')
      .valueChanges.pipe(takeUntil(this._unsubscribeSupplierRelated))
      .subscribe(idProvincia => {
        if (idProvincia != '') {
          this.filterPoblaciones({ idProvincia });
        }
      });
    // // Subscribe to municipio changes
    // // to handle poblaciones fetch ...
    this.supplierForm
      .get('idPoblacion')
      .valueChanges.pipe(takeUntil(this._unsubscribeSupplierRelated))
      .subscribe(idPoblacion => {
        if (idPoblacion) {
          if (this.supplier.idPoblacion === +idPoblacion) {
            this.supplierForm.patchValue({ postalCode: this.supplier.postalCode });
          } else {
            let postalCode = this.poblaciones.find(p => p.idPoblacion === +idPoblacion).postalCode;
            this.supplierForm.patchValue({ postalCode });
          }
          let newMunicipio = this.poblaciones.find(p => p.idPoblacion === +idPoblacion)?.municipio;
          // If newMunicipio exists and it's not currently selected
          if (newMunicipio && newMunicipio !== this.supplierForm.value.nombreMunicipio) {
            this.supplierForm.get('nombreMunicipio').setValue(newMunicipio);
          }
        }
      });
    // Subscribe to idCountry
    this.supplierForm
      .get('idCountry')
      .valueChanges.pipe(takeUntil(this._unsubscribeSupplierRelated))
      .subscribe(newValue => {
        if (+newValue === 73) {
          if (this.supplier.idProvincia) {
            this.filterPoblaciones({ idProvincia: this.supplier.idProvincia });
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
    this.supplierForm.patchValue({ idPoblacion: e });
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
    return this._supplierListComponent.matDrawer.close();
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
          this.supplierForm.get('type').disable();
          this.supplierForm.get('type').disable();
          this.supplierForm.get('type').disable();
        } */
    // Mark for check
    this._changeDetectorRef.markForCheck();
  }

  // ----------------------------------
  // @ Form control methods
  // ----------------------------------

  deleteSupplier(): void {
    this._supplierService.deleteSupplierRemote(this.supplier.idSupplier).subscribe(
      success => {
        // Set supplier$ to null and remove it from suppliers$
        this._supplierService.deleteSupplier$();
        // Navigate back
        this._router.navigateByUrl('home/supplier');
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
   * Create the supplier
   */
  createSupplier(): void {
    // Parse necessary fields
    this.parseNumberValues();
    // Get current logged user
    this._sessionService.user$
      .pipe(
        switchMap(user => {
          let toSend: any = {};
          Object.assign(toSend, this.supplierForm.value);
          toSend.idUser = user.idUser;
          Object.keys(toSend).forEach(k => toSend[k] === null && delete toSend[k]);

          return this._supplierService.createSupplierRemote(toSend);
        }),
      )
      .subscribe(
        supplier => {
          // Push supplier to the service
          this._supplierService.createSupplier$(supplier);

          // Navigate back
          this._router.navigateByUrl('home/supplier');

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
   * Update the supplier
   */
  updateSupplier(): void {
    // Parse necessary fields
    this.parseNumberValues();
    // Get current logged user
    this._sessionService.user$
      .pipe(
        switchMap(user => {
          let toSend: any = {};
          Object.assign(toSend, this.supplierForm.value);
          toSend.idUser = user.idUser;
          Object.keys(toSend).forEach(k => toSend[k] === null && delete toSend[k]);

          return this._supplierService.updateSupplierRemote(this.supplier.idSupplier, toSend);
        }),
      )
      .subscribe(
        supplier => {
          this._supplierService.updateSupplier$(supplier, true);
          // Navigate back
          this._router.navigateByUrl('home/supplier');

          // Mark for check
          this._changeDetectorRef.markForCheck();
        },
        error => {
          console.error(error);

          if (error.status === 422) {
            Swal.fire({
              title: 'Error during creation',
              text: 'There is already a supplier with this ID: ' + this.supplierForm.value.vat,
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
   * Parse supplierForm values that must be number but has been set
   * to string, since there's inputs that can't be configured to produce numbers.
   */
  private parseNumberValues() {
    let val = this.supplierForm.value;
    // Parse necessary fields
    this.supplierForm.patchValue({
      idPoblacion: +val.idPoblacion,
      type: +val.type,
      postalCode: +val.postalCode,
      chargeAccount: +val.chargeAccount,
      salesAccount: +val.salesAccount,
      phone1: +val.phone1,
      phone2: +val.phone2,
      holdback: +val.holdback,
    });
  }

  private setPoblaciones(poblaciones: Observable<Poblacion[]>, extractMunicipios: boolean): void {
    poblaciones.subscribe(poblaciones => {
      // Store new poblaciones
      this.poblaciones = poblaciones;

      // If supplier have poblacion, reset it to FIX BUG
      if (this.supplierForm.value.idPoblacion) {
        let supplierPob = this.poblaciones.find(
          p => p.idPoblacion === +this.supplierForm.value.idPoblacion,
        );
        // If selected poblacion exists on new poblaciones reset it
        if (supplierPob) {
          let original = this.supplierForm.value.idPoblacion;
          // FIX BUG
          this.setFirstPoblacion();
          setTimeout(() => {
            this.supplierForm.get('idPoblacion').setValue(original);
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
    this.supplierForm.get('idPoblacion').setValue(this.poblaciones[0].idPoblacion);
    this.idPoblacion = this.poblaciones[0].idPoblacion;
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
