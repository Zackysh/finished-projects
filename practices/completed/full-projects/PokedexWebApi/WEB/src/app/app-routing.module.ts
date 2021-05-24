import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthGuard } from './_helpers/auth.guard';
import { HomeComponent } from './home/home.component';
import { UnauthorizedComponent } from './unauthorized/unauthorized.component';
import { LoginComponent } from './account/login/login.component';

const routes: Routes = [
  {
    path: 'home',
    component: HomeComponent,
    loadChildren: () => import('./home/home.module').then((x) => x.HomeModule),
    canActivate: [AuthGuard],
  },

  // prettier-ignore
  { 
    path: 'account', 
    loadChildren: () => import('./account/account.module').then((x) => x.AccountModule),
  },
  { path: 'unauthorized', component: UnauthorizedComponent },
  { path: '**', redirectTo: 'account/login' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
