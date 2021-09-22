import { SearchComponent } from 'app/layout/common/search/search.component';
import { SharedModule } from 'app/shared/shared.module';

import { BlockScrollStrategy, Overlay } from '@angular/cdk/overlay';
import { NgModule } from '@angular/core';
import {
    MAT_AUTOCOMPLETE_SCROLL_STRATEGY, MatAutocompleteModule
} from '@angular/material/autocomplete';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { RouterModule } from '@angular/router';

@NgModule({
    declarations: [
        SearchComponent
    ],
    imports     : [
        RouterModule.forChild([]),
        MatAutocompleteModule,
        MatButtonModule,
        MatFormFieldModule,
        MatIconModule,
        MatInputModule,
        SharedModule
    ],
    exports     : [
        SearchComponent
    ],
    providers   : [
        {
            provide   : MAT_AUTOCOMPLETE_SCROLL_STRATEGY,
            useFactory: (overlay: Overlay) => (): BlockScrollStrategy => overlay.scrollStrategies.block(),
            deps      : [Overlay]
        }
    ]
})
export class SearchModule
{
}
