import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {MatTableModule} from '@angular/material/table';
import { StacktracesRoutingModule } from './stacktraces-routing.module';
import { StacktracesComponent } from './stacktraces.component';
import { MatFormFieldModule, MatSortModule, MatListModule, MatInputModule } from '@angular/material';

@NgModule({
  imports: [
    CommonModule,
    StacktracesRoutingModule,
    MatTableModule,
    MatFormFieldModule,
    MatInputModule,
    MatListModule,
    MatSortModule,
  ],
  declarations: [StacktracesComponent]
})
export class StacktracesModule { }
