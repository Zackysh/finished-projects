import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FuseCardComponent } from '@fuse/components/card/card.component';

@NgModule({
    declarations: [
        FuseCardComponent
    ],
    imports     : [
        CommonModule
    ],
    exports     : [
        FuseCardComponent
    ]
})
export class FuseCardModule
{
}
