import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FuseMasonryComponent } from '@fuse/components/masonry/masonry.component';

@NgModule({
    declarations: [
        FuseMasonryComponent
    ],
    imports     : [
        CommonModule
    ],
    exports     : [
        FuseMasonryComponent
    ]
})
export class FuseMasonryModule
{
}
