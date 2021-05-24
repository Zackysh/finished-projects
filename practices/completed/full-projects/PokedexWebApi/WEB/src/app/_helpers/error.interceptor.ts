import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor,
} from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

import { AccountService } from '../_services';

@Injectable()
export class ErrorInterceptor implements HttpInterceptor {
  constructor(private accountService: AccountService) {}

  intercept(
    request: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    return next.handle(request).pipe(
      catchError((err) => {
        if ([401, 403].includes(err.status) && this.accountService.getUser) {
          window.alert('Your session has expired!');
          this.accountService.logout();
          return;
        }

        if ([401].includes(err.status)){
          window.alert('Wrong password!');
          return;
        }

        if ([404].includes(err.status) && !this.accountService.getUser){
          window.alert('User not found!');
          return;
        }

        window.alert(`Please, pay attetion:\n\nShorten error: ${JSON.stringify(err.error.errors)}\n\nFull error: ${JSON.stringify(err.error)}`);

        const error = err.error?.message || err.statusText;
        return throwError(error);
      })
    );
  }
}
