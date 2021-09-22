import { SharedModule } from 'app/shared/shared.module';

import { NgModule } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatRippleModule } from '@angular/material/core';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatMenuModule } from '@angular/material/menu';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { MatSelectModule } from '@angular/material/select';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import { MatSortModule } from '@angular/material/sort';
import { MatTooltipModule } from '@angular/material/tooltip';
import { Route, RouterModule } from '@angular/router';

import { UserListComponent } from './user/list/user-list.component';

const settingsRoutes: Route[] = [
  {
    path: 'user/list',
    component: UserListComponent
  },
  {
    path: 'user/group',
    loadChildren: () =>
      import('app/modules/settings-module/user/group/group.module').then(
        (m) => m.GroupModule
      ),
  }
];

@NgModule({
  declarations: [
    UserListComponent  
  ],
  imports: [
    RouterModule.forChild(settingsRoutes),
    MatButtonModule,
    MatCheckboxModule,
    MatFormFieldModule,
    MatIconModule,
    MatInputModule,
    MatMenuModule,
    MatSelectModule,
    MatProgressBarModule,
    MatPaginatorModule,
    MatRippleModule,
    MatSortModule,
    MatSlideToggleModule,
    MatTooltipModule,
    SharedModule
  ]
})
export class SettingsModule {
}
