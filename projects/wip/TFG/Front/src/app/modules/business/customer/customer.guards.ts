import { Observable } from 'rxjs';

import { Injectable } from '@angular/core';
import {
    ActivatedRouteSnapshot, CanDeactivate, RouterStateSnapshot, UrlTree
} from '@angular/router';

import { CustomerDetailsComponent } from './details/customer-details.component';

@Injectable({
    providedIn: 'root'
})
export class CanDeactivateCustomerDetails implements CanDeactivate<CustomerDetailsComponent>
{
    canDeactivate(
        component: CustomerDetailsComponent,
        currentRoute: ActivatedRouteSnapshot,
        currentState: RouterStateSnapshot,
        nextState: RouterStateSnapshot
    ): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {


        // Get the next route
        let nextRoute: ActivatedRouteSnapshot = nextState.root;
        while (nextRoute.firstChild) {
            nextRoute = nextRoute.firstChild;
        }

        // If the next state doesn't contain '/customer'
        // it means we are navigating away from the
        // customer
        if (!nextState.url.includes('/customer')) {
            // Let it navigate
            return true;
        }

        // If we are navigating to another customer...
        if (nextRoute.paramMap.get('id')) {
            // Just navigate
            return true;
        }
        // Otherwise...
        else {
            // Close the drawer first, and then navigate
            return component.closeDrawer().then(() => true);
        }
    }
}
