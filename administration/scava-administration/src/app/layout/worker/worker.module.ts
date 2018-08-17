import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { WorkerRoutingModule } from './worker-routing.module';
import { WorkerComponent } from './worker.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgbModal, NgbModalModule } from '@ng-bootstrap/ng-bootstrap';
import { NgbModalStack } from '@ng-bootstrap/ng-bootstrap/modal/modal-stack';
import { TreeviewModule } from 'ngx-treeview';

@NgModule({
  imports: [
    NgbModalModule,
    CommonModule,
    WorkerRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    TreeviewModule.forRoot()
  ],
  declarations: [
    WorkerComponent
  ],
  entryComponents: [

  ],
  providers: [
    NgbModal,
    NgbModalStack
  ]
})
export class WorkerModule { }
