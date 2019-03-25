import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { WorkerRoutingModule } from './worker-routing.module';
import { WorkerComponent } from './worker.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgbModal, NgbModalModule } from '@ng-bootstrap/ng-bootstrap';
import { NgbModalStack } from '@ng-bootstrap/ng-bootstrap/modal/modal-stack';
import { TreeviewModule } from 'ngx-treeview';
import { MetricInfoComponent } from './metrics-infos/metric-info.component';
import {TableModule} from 'primeng/table';
import { MatFormFieldModule, MatNativeDateModule, MatInputModule, MatDatepickerModule, MatTableModule, MatCheckboxModule, MatListModule, MatRadioModule, MatSortModule, MatPaginatorModule } from '@angular/material';

@NgModule({
  imports: [
    NgbModalModule,
    CommonModule,
    WorkerRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    TreeviewModule.forRoot(),
    TableModule,
    MatFormFieldModule,
    MatNativeDateModule,
    MatInputModule,
    MatDatepickerModule,
    MatTableModule,
    MatCheckboxModule,
    MatListModule,
    MatRadioModule,
    MatSortModule,
    MatPaginatorModule,
  ],
  declarations: [
    WorkerComponent,
    MetricInfoComponent
  ],
  entryComponents: [
    MetricInfoComponent
  ],
  providers: [
    NgbModal,
    NgbModalStack
  ]
})
export class WorkerModule { }
