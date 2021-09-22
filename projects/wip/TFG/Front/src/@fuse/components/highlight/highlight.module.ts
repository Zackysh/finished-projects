import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FuseHighlightComponent } from '@fuse/components/highlight/highlight.component';

@NgModule({
    declarations: [
        FuseHighlightComponent
    ],
    imports     : [
        CommonModule
    ],
    exports     : [
        FuseHighlightComponent
    ]
})
export class FuseHighlightModule
{
}
