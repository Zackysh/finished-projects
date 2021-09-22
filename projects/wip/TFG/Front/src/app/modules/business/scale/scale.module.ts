import { NgModule } from '@angular/core';
import { MatMomentDateModule } from '@angular/material-moment-adapter';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { MatButtonModule } from '@angular/material/button';
import { MatButtonToggleModule } from '@angular/material/button-toggle';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatRippleModule, MAT_DATE_FORMATS } from '@angular/material/core';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatMenuModule } from '@angular/material/menu';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { MatSelectModule } from '@angular/material/select';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import { MatSortModule } from '@angular/material/sort';
import { MatTooltipModule } from '@angular/material/tooltip';
import { Route, RouterModule } from '@angular/router';
import { FuseNavigationModule } from '@fuse/components/navigation';
import { SharedModule } from 'app/shared/shared.module';
import moment from 'moment';
import { MultiScaleComponent } from './multiple/multi-scale.component';
import { BwScaleSidebarComponent } from './multiple/navigation/scale-navigation.component';
import { ScaleWrapperComponent } from './multiple/scale-wrapper/scale-wrapper.component';
import { ScaleComponent } from './scale.component';
import { BenchScaleComponent } from './single/bench-scale/bench-scale.component';
import { BwScaleComponent } from './single/bw-scale/bw-scale.component';
import { TruckScaleComponent } from './single/truck-scale/truck-scale.component';

const scaleRoutes: Route[] = [
  {
    path: '',
    component: MultiScaleComponent,
  },
  {
    path: ':id',
    component: ScaleComponent,
  },
];

@NgModule({
  declarations: [
    ScaleComponent,
    BwScaleComponent,
    TruckScaleComponent,
    BenchScaleComponent,
    BwScaleSidebarComponent,
    MultiScaleComponent,
    ScaleWrapperComponent,
  ],
  imports: [
    RouterModule.forChild(scaleRoutes),
    MatButtonModule,
    MatCheckboxModule,
    MatFormFieldModule,
    MatDatepickerModule,
    MatMomentDateModule,
    MatIconModule,
    MatInputModule,
    MatSidenavModule,
    MatMenuModule,
    MatButtonToggleModule,
    MatSelectModule,
    MatProgressBarModule,
    MatPaginatorModule,
    MatRippleModule,
    MatSortModule,
    MatSlideToggleModule,
    MatAutocompleteModule,
    MatTooltipModule,
    FuseNavigationModule,
    SharedModule,
  ],
  providers: [
    {
      provide: MAT_DATE_FORMATS,
      useValue: {
        parse: {
          dateInput: moment.ISO_8601,
        },
        display: {
          dateInput: 'LL',
          monthYearLabel: 'YYYY MMM',
          dateA11yLabel: 'LL',
          monthYearA11yLabel: 'YYYY MMM',
        },
      },
    },
  ],
})
export class ScaleModule {}
