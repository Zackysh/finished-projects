import {
    AuthConfirmationRequiredComponent
} from 'app/modules/auth/confirmation-required/confirmation-required.component';
import {
    authConfirmationRequiredRoutes
} from 'app/modules/auth/confirmation-required/confirmation-required.routing';
import { SharedModule } from 'app/shared/shared.module';

import { NgModule } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { RouterModule } from '@angular/router';
import { FuseCardModule } from '@fuse/components/card';

@NgModule({
    declarations: [
        AuthConfirmationRequiredComponent
    ],
    imports     : [
        RouterModule.forChild(authConfirmationRequiredRoutes),
        MatButtonModule,
        FuseCardModule,
        SharedModule
    ]
})
export class AuthConfirmationRequiredModule
{
}
