import {
    GroupDetailsComponent
} from 'app/modules/settings-module/user/group/details/group-details.component';
import { GroupComponent } from 'app/modules/settings-module/user/group/group.component';
import { CanDeactivateGroupDetails } from 'app/modules/settings-module/user/group/group.guards';
import {
    GroupsResolver, SelectedGroupResolver
} from 'app/modules/settings-module/user/group/group.resolvers';
import {
    GroupListComponent
} from 'app/modules/settings-module/user/group/list/group-list.component';

import { Route } from '@angular/router';

export const groupRoutes: Route[] = [
  {
    path: '',
    component: GroupComponent,
    resolve: {
    },
    children: [
      {
        path: '',
        component: GroupListComponent,
        resolve: {
          groups: GroupsResolver
        },
        children: [
          {
            path: ':id',
            component: GroupDetailsComponent,
            runGuardsAndResolvers: "always",
            resolve: {
              group: SelectedGroupResolver
            },
            canDeactivate: [CanDeactivateGroupDetails]
          }
        ]
      }
    ]
  }
];
