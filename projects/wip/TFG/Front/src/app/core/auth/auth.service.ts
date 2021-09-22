import { SessionService } from 'app/core/models/user/session.service';
import { Observable, of, throwError } from 'rxjs';
import { catchError, switchMap } from 'rxjs/operators';

import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable()
export class AuthService {
  private _authenticated: boolean = false;

  /**
   * Constructor
   */
  constructor(private _httpClient: HttpClient, private _sessionService: SessionService) {}

  // -----------------------------------------------------------------------------------------------------
  // @ Accessors
  // -----------------------------------------------------------------------------------------------------

  /**
   * Setter & getter for access token
   */
  set accessToken(token: string) {
    localStorage.setItem('accessToken', token);
  }
  get accessToken(): string {
    return localStorage.getItem('accessToken') ?? '';
  }

  /**
   * Setter & getter for refresh token
   */
  set refreshToken(token: string) {
    localStorage.setItem('refreshToken', token);
  }
  get refreshToken(): string {
    return localStorage.getItem('refreshToken') ?? '';
  }

  // -----------------------------------------------------------------------------------------------------
  // @ Public methods
  // -----------------------------------------------------------------------------------------------------

  /**
   * Sign in
   *
   * @param credentials
   */
  signIn(credentials: { username: string; password: string }): Observable<any> {
    // Throw error, if the user is already logged in
    if (this._authenticated) {
      return throwError('User is already logged in.');
    }

    return this._httpClient.post('core/user/login', credentials).pipe(
      switchMap((response: any) => {
        // Store the access token in the local storage
        this.accessToken = response.accessToken;

        // Store the refresh token in the local storage
        this.refreshToken = response.refreshToken;

        // Set the authenticated flag to true
        this._authenticated = true;

        // Store the user on the user service
        this._sessionService.user = response.user;

        // Return a new observable with the response
        return of(response);
      }),
    );
  }

  /**
   * Refresh Access Token
   *
   * Sign in using the access token
   */
  signInUsingToken(): Observable<any> {
    // Renew token
    return this._httpClient
      .post(
        'core/user/refreshtoken',
        {},
        {
          headers: {
            'x-refresh-token': this.refreshToken,
          },
        },
      )
      .pipe(
        switchMap((response: any) => {
          // Store the access token in the local storage
          this.accessToken = response.accessToken;

          // Set the authenticated flag to true
          this._authenticated = true;

          // Store the user on the user service
          this._sessionService.user = response.user;

          // Return true
          return of(true);
        }),
      );
  }

  /**
   * Sign out
   */
  signOut(): Observable<any> {
    // Remove the access token from the local storage
    localStorage.removeItem('accessToken');
    localStorage.removeItem('refreshToken');

    this._httpClient
      .post(
        'core/user/logout',
        {},
        {
          headers: {
            'x-access-token': this.accessToken,
          },
        },
      )
      .pipe(
        catchError(() =>
          // Return false - failed logout
          of(false),
        ),
        switchMap(() =>
          // Return true - successful logout
          of(true),
        ),
      );

    // Set the authenticated flag to false
    this._authenticated = false;

    // Return the observable
    return of(true);
  }

  /**
   * Check the authentication status
   */
  check(): Observable<boolean> {
    // Check if the user is logged in
    if (this._authenticated) {
      return of(true);
    }

    // Check the access token availability
    if (!this.accessToken) {
      console.info('no access token');
      return of(false);
    }

    // If the access token exists and it didn't expire, sign in using it
    this.signInUsingToken().subscribe(
      res => console.log(),
      err => this.signOut(),
    );

    return this.signInUsingToken();
  }
}
