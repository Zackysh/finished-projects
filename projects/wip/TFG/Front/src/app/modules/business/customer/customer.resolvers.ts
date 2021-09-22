import { CustomerService } from 'app/core/models/business/customer/customer.service';
import { DEFAULT_ORDER_BY } from 'app/core/models/business/customer/customer.types';
import { LocationService } from 'app/core/models/business/locations/location.service';
import { BLANK_TEMPLATE_ID } from 'app/core/models/core.types';
import { DEFAULT_PAGE, DEFAULT_SIZE } from 'app/core/models/pagination.types';
import { merge, zip } from 'lodash';
import { combineLatest, forkJoin } from 'rxjs';
import { switchMap } from 'rxjs/operators';

import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Resolve, Router, RouterStateSnapshot } from '@angular/router';

@Injectable({
  providedIn: 'root',
})
export class CustomerDetailsResolver implements Resolve<any> {
  /**
   * Constructor
   */
  constructor(
    private _customerService: CustomerService,
    private _locationService: LocationService,
    private _router: Router,
  ) {}

  // -----------------------------------------------------------------------------------------------------
  // @ Public methods
  // -----------------------------------------------------------------------------------------------------

  /**
   * Resolver.
   *
   * Fetchs requested customer, countries and provincias
   *
   * @param route
   * @param state
   */
  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    const obsv = combineLatest([
      /* null, 444, 15, DEFAULT_ORDER_BY */
      this._customerService.getCustomersRemote(
        null,
        DEFAULT_PAGE,
        DEFAULT_SIZE,
        DEFAULT_ORDER_BY,
      ) /* null, DEFAULT_PAGE, DEFAULT_SIZE */,
      this._locationService.getCountries(),
      this._locationService.getProvincias(),
    ]);
    obsv.subscribe(([customers, countries, provincias]) => {
      this._customerService.updateCustomers$(customers);
      this._locationService.setCountries$(countries);
      this._locationService.setProvincias$(provincias);
    });
  }
}
@Injectable({
  providedIn: 'root',
})
export class SelectedCustomerResolver implements Resolve<any> {
  /**
   * Constructor
   */
  constructor(private _customerService: CustomerService, private _router: Router) {}

  // -----------------------------------------------------------------------------------------------------
  // @ Public methods
  // -----------------------------------------------------------------------------------------------------

  /**
   * Resolver, get id from params, then calls customerService.
   *
   * @param route
   * @param state
   */
  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    // If requested customer isn't a new one
    if (route.params.id != BLANK_TEMPLATE_ID) {
      // Try to insert requested customer into serivce customer$
      this._customerService.getCustomerById$(route.params.id).subscribe(customer => {
        if (customer) this._customerService.updateCustomer$(customer, false);
        else {
          // If there's no customer stored with given ID ...
          // Get the parent url
          const parentUrl = state.url.split('/').slice(0, -1).join('/');

          // Navigate to there
          this._router.navigateByUrl(parentUrl);
        }
      });
    } else {
      // If requested customer is a new one ...
      try {
        // Try to fetch existing customer-template from local storage ...
        this._customerService.unshiftStoredTemplate();
      } catch (error) {
        // Or create new customer-template
        this._customerService.unshiftTemplate();
        console.error(error);
      }
    }
  }
}
