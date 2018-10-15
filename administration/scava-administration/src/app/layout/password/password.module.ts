import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { PasswordRoutingModule } from './password-routing.module';
import { FormsModule } from '@angular/forms';
import { PasswordComponent } from './password.component';

@NgModule({
  imports: [
    CommonModule,
    PasswordRoutingModule,
    FormsModule
  ],
  declarations: [PasswordComponent]
})
export class PasswordModule { }
