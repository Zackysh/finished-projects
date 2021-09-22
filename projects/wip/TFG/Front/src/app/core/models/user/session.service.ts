import { User } from 'app/core/models/user/user.types';
import { Observable, ReplaySubject } from 'rxjs';

import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class SessionService {
  private _user: ReplaySubject<User> = new ReplaySubject<User>(1);

  /**
   * Constructor
   */
  constructor() {}

  // -----------------------------------------------------------------------------------------------------
  // @ Accessors
  // -----------------------------------------------------------------------------------------------------

  set user(value: User) {
    this._user.next(value);
  }
  get user$(): Observable<User> {
    return this._user.asObservable();
  }
}
