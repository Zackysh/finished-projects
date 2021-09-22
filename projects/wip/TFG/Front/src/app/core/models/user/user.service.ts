import { Company, User } from 'app/core/models/user/user.types';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

import { APIRes } from '../core.types';
import { SessionService } from './session.service';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  /**
   * Constructor
   */
  constructor(private _httpClient: HttpClient, private sessionService: SessionService) {}

  // -----------------------------------------------------------------------------------------------------
  // @ Public methods
  // -----------------------------------------------------------------------------------------------------

  getCompanies(): Observable<Company[]> {
    return this._httpClient.get<APIRes<Company[]>>('core/company').pipe(map(res => res.data));
  }

  getUsers(): Observable<User[]> {
    return this._httpClient.get<APIRes<User[]>>('core/user').pipe(map(res => res.data));
  }

  getUserById(idUser: number): Observable<User> {
    return this._httpClient.get<APIRes<User>>(`core/user/${idUser}`).pipe(map(res => res.data));
  }

  getCompanyById(idCompany: number): Observable<Company> {
    return this._httpClient.get<APIRes<Company>>(`core/group/${idCompany}`).pipe(map(res => res.data));
  }

  createUser(user: User): Observable<User> {
    return this._httpClient
      .post<APIRes<User>>('core/user/create', { ...user })
      .pipe(map(res => res.data));
  }

  updateUser(idUser: number, user: User): Observable<User> {
    return this._httpClient
      .put<APIRes<User>>(`core/user/${idUser}`, { ...user })
      .pipe(map(res => res.data));
  }

  deleteUser(idUser: number): Observable<User> {
    return this._httpClient.delete<APIRes<User>>(`core/user/${idUser}`).pipe(map(res => res.data));
  }
}
