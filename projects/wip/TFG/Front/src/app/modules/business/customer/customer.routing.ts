import { Route } from '@angular/router';

import { CustomerComponent } from './customer.component';
import { CanDeactivateCustomerDetails } from './customer.guards';
import { CustomerDetailsResolver, SelectedCustomerResolver } from './customer.resolvers';
import { CustomerDetailsComponent } from './details/customer-details.component';
import { CustomerListComponent } from './list/customer-list.component';

export const customerRoutes: Route[] = [
  {
    path: '',
    component: CustomerComponent,
    children: [
      {
        path: '',
        component: CustomerListComponent,
        resolve: {
          customers: CustomerDetailsResolver,
        },
        children: [
          {
            path: ':id',
            component: CustomerDetailsComponent,
            runGuardsAndResolvers: 'always',
            resolve: {
              customer: SelectedCustomerResolver,
            },
            canDeactivate: [CanDeactivateCustomerDetails],
          },
        ],
      },
    ],
  },
];
