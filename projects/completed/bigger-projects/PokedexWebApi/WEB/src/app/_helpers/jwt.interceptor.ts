import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor,
} from '@angular/common/http';
import { Observable } from 'rxjs';
import { AccountService } from '../_services';
import { User } from '../_models';

@Injectable()
export class JwtInterceptor implements HttpInterceptor {
  constructor(private accountService: AccountService) {}

  intercept(
    request: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<User>> {
    const user = this.accountService.getUser;

    const isLoggedIn = user && user?.token != null;
    if (isLoggedIn) {
      console.log('JWT');
      request = request.clone({
        setHeaders: {
          Authorization: `Bearer ${user.token}`,
          myverga: 'eyey',
        },
      });
    }
    return next.handle(request);
  }
}
