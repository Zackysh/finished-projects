import { AuthSignOutComponent } from 'app/modules/auth/sign-out/sign-out.component';

import { Route } from '@angular/router';

export const authSignOutRoutes: Route[] = [
    {
        path     : '',
        component: AuthSignOutComponent
    }
];
