import { AuthSignInComponent } from 'app/modules/auth/sign-in/sign-in.component';

import { Route } from '@angular/router';

export const authSignInRoutes: Route[] = [
    {
        path     : '',
        component: AuthSignInComponent
    }
];
