import { defaultNavigation, NavigationLoader } from 'app/mock-api/common/navigation/nav-data';
import { cloneDeep } from 'lodash-es';

import { Injectable } from '@angular/core';
import { FuseNavigationItem } from '@fuse/components/navigation';
import { FuseMockApiService } from '@fuse/lib/mock-api';

@Injectable({
  providedIn: 'root',
})
export class NavigationMockApi {
  private _defaultNavigation: FuseNavigationItem[] = defaultNavigation;

  /**
   * Constructor
   */
  constructor(
    private _fuseMockApiService: FuseMockApiService,
    private _navigationLoader: NavigationLoader,
  ) {
    // Register Mock API handlers
    this._navigationLoader.getDefaultNavigation().subscribe(res => {
			this._defaultNavigation = res;
			this.registerHandlers();
		});
  }

  // -----------------------------------------------------------------------------------------------------
  // @ Public methods
  // -----------------------------------------------------------------------------------------------------

  /**
   * Register Mock API handlers
   */
  registerHandlers(): void {
    // -----------------------------------------------------------------------------------------------------
    // @ Navigation - GET
    // -----------------------------------------------------------------------------------------------------
    this._fuseMockApiService.onGet('api/common/navigation').reply(() => {
      // Return the response
      return [200, { default: cloneDeep(this._defaultNavigation) }];
    });
  }
}
