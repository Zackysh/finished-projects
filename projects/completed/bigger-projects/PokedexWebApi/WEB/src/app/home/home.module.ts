import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { LandingComponent } from './landing/landing.component';
import { HomeRoutingModule } from './home-routing.module';
import { CreateEditComponent } from './create-edit/create-edit.component';
import { ReactiveFormsModule } from '@angular/forms';
import { ViewportComponent } from './viewport/viewport.component';

@NgModule({
  declarations: [LandingComponent, CreateEditComponent, ViewportComponent],
  imports: [CommonModule, FontAwesomeModule, HomeRoutingModule, ReactiveFormsModule],
})
export class HomeModule {}
