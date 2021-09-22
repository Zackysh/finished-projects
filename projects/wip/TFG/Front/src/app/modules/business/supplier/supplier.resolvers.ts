import { LocationService } from 'app/core/models/business/locations/location.service';
import { SupplierService } from 'app/core/models/business/supplier/supplier.service';
import { DEFAULT_ORDER_BY } from 'app/core/models/business/supplier/supplier.types';
import { BLANK_TEMPLATE_ID } from 'app/core/models/core.types';
import { DEFAULT_PAGE, DEFAULT_SIZE } from 'app/core/models/pagination.types';
import { combineLatest } from 'rxjs';

import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Resolve, Router, RouterStateSnapshot } from '@angular/router';

@Injectable({
  providedIn: 'root',
})
export class SupplierDetailsResolver implements Resolve<any> {
  /**
   * Constructor
   */
  constructor(
    private _supplierService: SupplierService,
    private _locationService: LocationService,
    private _router: Router,
  ) {}

  // -----------------------------------------------------------------------------------------------------
  // @ Public methods
  // -----------------------------------------------------------------------------------------------------

  /**
   * Resolver.
   *
   * Fetch requested supplier, countries and provincias
   *
   * @param route
   * @param state
   */
  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    const obsv = combineLatest([
      /* null, 444, 15, DEFAULT_ORDER_BY */
      this._supplierService.getSuppliersRemote(
        null,
        DEFAULT_PAGE,
        DEFAULT_SIZE,
        DEFAULT_ORDER_BY,
      ) /* null, DEFAULT_PAGE, DEFAULT_SIZE */,
      this._locationService.getCountries(),
      this._locationService.getProvincias(),
    ]);
    obsv.subscribe(([suppliers, countries, provincias]) => {
      this._supplierService.updateSuppliers$(suppliers);
      this._locationService.setCountries$(countries);
      this._locationService.setProvincias$(provincias);
    });
  }
}
@Injectable({
  providedIn: 'root',
})
export class SelectedSupplierResolver implements Resolve<any> {
  /**
   * Constructor
   */
  constructor(private _supplierService: SupplierService, private _router: Router) {}

  // -----------------------------------------------------------------------------------------------------
  // @ Public methods
  // -----------------------------------------------------------------------------------------------------

  /**
   * Resolver, get id from params, then calls supplierService.
   *
   * @param route
   * @param state
   */
  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    // If requested supplier isn't a new one
    if (route.params.id != BLANK_TEMPLATE_ID) {
      // Try to insert requested supplier into service supplier$
      this._supplierService.getSupplierById$(route.params.id).subscribe(supplier => {
        if (supplier) this._supplierService.updateSupplier$(supplier, false);
        else {
          // If there's no supplier stored with given ID ...
          // Get the parent url
          const parentUrl = state.url.split('/').slice(0, -1).join('/');

          // Navigate to there
          this._router.navigateByUrl(parentUrl);
        }
      });
    } else {
      // If requested supplier is a new one ...
      try {
        // Try to fetch existing supplier-template from local storage ...
        this._supplierService.unshiftStoredTemplate();
      } catch (error) {
        // Or create new supplier-template
        this._supplierService.unshiftTemplate();
        console.error(error);
      }
    }
  }
}
