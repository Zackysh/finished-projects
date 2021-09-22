import { Route } from '@angular/router';

import { SupplierDetailsComponent } from './details/supplier-details.component';
import { SupplierListComponent } from './list/supplier-list.component';
import { SupplierComponent } from './supplier.component';
import { CanDeactivateSupplierDetails } from './supplier.guards';
import { SelectedSupplierResolver, SupplierDetailsResolver } from './supplier.resolvers';

export const supplierRoutes: Route[] = [
  {
    path: '',
    component: SupplierComponent,
    children: [
      {
        path: '',
        component: SupplierListComponent,
        resolve: {
          suppliers: SupplierDetailsResolver,
        },
        children: [
          {
            path: ':id',
            component: SupplierDetailsComponent,
            runGuardsAndResolvers: 'always',
            resolve: {
              supplier: SelectedSupplierResolver,
            },
            canDeactivate: [CanDeactivateSupplierDetails],
          },
        ],
      },
    ],
  },
];
