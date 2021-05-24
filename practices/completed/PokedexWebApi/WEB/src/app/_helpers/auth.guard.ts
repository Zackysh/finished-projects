import { Injectable } from '@angular/core';
import {
  Router,
  CanActivate,
  ActivatedRouteSnapshot,
  RouterStateSnapshot,
} from '@angular/router';

import { AccountService } from '../_services';

@Injectable({ providedIn: 'root' })
export class AuthGuard implements CanActivate {
  constructor(private router: Router, private accountService: AccountService) {}

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    const user = this.accountService.getUser;

    console.log("AuthGuard");
    
    console.log(user);
    
    if (user) return true;

    this.router.navigate(['unauthorized'], {
      queryParams: { returnUrl: state.url },
    });
    return false;
  }
}
