import {
    AuthConfirmationRequiredComponent
} from 'app/modules/auth/confirmation-required/confirmation-required.component';

import { Route } from '@angular/router';

export const authConfirmationRequiredRoutes: Route[] = [
    {
        path     : '',
        component: AuthConfirmationRequiredComponent
    }
];
