import { InitialDataResolver } from 'app/app.resolvers';
import { AuthGuard } from 'app/core/auth/guards/auth.guard';
import { NoAuthGuard } from 'app/core/auth/guards/noAuth.guard';
import { LayoutComponent } from 'app/layout/layout.component';

import { Route } from '@angular/router';

// @formatter:off
// tslint:disable:max-line-length
export const appRoutes: Route[] = [
  // Redirect empty path to '/example'
  { path: '', pathMatch: 'full', redirectTo: 'home' },

  // Redirect signed in user to the '/example'
  //
  // After the user signs in, the sign in page will redirect the user to the 'signed-in-redirect'
  // path. Below is another redirection for that path to redirect the user to the desired
  // location. This is a small convenience to keep all main routes together here on this file.
  { path: 'signed-in-redirect', pathMatch: 'full', redirectTo: 'home' },

  // Auth routes for guests
  {
    path: '',
    canActivate: [NoAuthGuard],
    canActivateChild: [NoAuthGuard],
    component: LayoutComponent,
    data: {
      layout: 'empty',
    },
    children: [
      {
        path: 'confirmation-required',
        loadChildren: () =>
          import('app/modules/auth/confirmation-required/confirmation-required.module').then(
            m => m.AuthConfirmationRequiredModule,
          ),
      },
      {
        path: 'sign-in',
        loadChildren: () =>
          import('app/modules/auth/sign-in/sign-in.module').then(m => m.AuthSignInModule),
      },
      {
        path: 'sign-up',
        loadChildren: () =>
          import('app/modules/auth/sign-up/sign-up.module').then(m => m.AuthSignUpModule),
      },
    ],
  },

  // Auth routes for authenticated users
  {
    path: '',
    canActivate: [AuthGuard],
    canActivateChild: [AuthGuard],
    component: LayoutComponent,
    data: {
      layout: 'empty',
    },
    children: [
      {
        path: 'sign-out',
        loadChildren: () =>
          import('app/modules/auth/sign-out/sign-out.module').then(m => m.AuthSignOutModule),
      },
    ],
  },

  // Admin routes
  {
    path: 'home',
    canActivate: [AuthGuard],
    canActivateChild: [AuthGuard],
    component: LayoutComponent,
    resolve: {
      initialData: InitialDataResolver,
    },
    children: [
      {
        path: 'settings',
        loadChildren: () =>
          import('app/modules/settings-module/settings.module').then(m => m.SettingsModule),
      },
      {
        path: 'customer',
        loadChildren: () =>
          import('app/modules/business/customer/customer.module').then(m => m.CustomerModule),
      },
      {
        path: 'supplier',
        loadChildren: () =>
          import('app/modules/business/supplier/supplier.module').then(m => m.SupplierModule),
      },
      {
        path: 'product',
        loadChildren: () =>
          import('app/modules/business/product/product.module').then(m => m.ProductModule),
      },
      {
        path: 'scales',
        loadChildren: () => import('app/modules/business/scale/scale.module').then(m => m.ScaleModule),
      },
    ],
  },

  // Redirect empty path to '/example'
  { path: '**', redirectTo: 'home' },
];
