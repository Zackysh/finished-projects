import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CreateEditComponent } from './create-edit/create-edit.component';
import { LandingComponent } from './landing/landing.component';
import { ManageComponent } from './manage/manage.component';
import { ViewportComponent } from './viewport/viewport.component';

const routes: Routes = [
  { path: '', component: LandingComponent },
  { path: 'manage', component: ManageComponent },
  { path: 'create-edit', component: CreateEditComponent },
  { path: 'viewport', component: ViewportComponent },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class HomeRoutingModule {}
