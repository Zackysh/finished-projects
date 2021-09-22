import {
    GroupDetailsComponent
} from 'app/modules/settings-module/user/group/details/group-details.component';
import { Observable } from 'rxjs';

import { Injectable } from '@angular/core';
import {
    ActivatedRouteSnapshot, CanDeactivate, RouterStateSnapshot, UrlTree
} from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class CanDeactivateGroupDetails implements CanDeactivate<GroupDetailsComponent>
{
  canDeactivate(
    component: GroupDetailsComponent,
    currentRoute: ActivatedRouteSnapshot,
    currentState: RouterStateSnapshot,
    nextState: RouterStateSnapshot
  ): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {

    // Get the next route
    let nextRoute: ActivatedRouteSnapshot = nextState.root;
    while (nextRoute.firstChild) {
      nextRoute = nextRoute.firstChild;
    }

    // If the next state doesn't contain '/group'
    // it means we are navigating away from the
    // group
    if (!nextState.url.includes('/group')) {
      // Let it navigate
      return true;
    }

    // If we are navigating to another group...
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
