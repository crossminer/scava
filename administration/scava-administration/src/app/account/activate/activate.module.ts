import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ActivateRoutingModule } from './activate-routing.module';
import { ActivateComponent } from './activate.component';

@NgModule({
  imports: [
    CommonModule,
    ActivateRoutingModule
  ],
  declarations: [ActivateComponent]
})
export class ActivateModule { }
