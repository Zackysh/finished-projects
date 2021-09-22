import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FuseDrawerComponent } from '@fuse/components/drawer/drawer.component';

@NgModule({
    declarations: [
        FuseDrawerComponent
    ],
    imports     : [
        CommonModule
    ],
    exports     : [
        FuseDrawerComponent
    ]
})
export class FuseDrawerModule
{
}
