import { AuthSignUpComponent } from 'app/modules/auth/sign-up/sign-up.component';

import { Route } from '@angular/router';

export const authSignupRoutes: Route[] = [
    {
        path     : '',
        component: AuthSignUpComponent
    }
];
