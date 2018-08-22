import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProjectRoutingModule } from './project-routing.module';
import { ProjectComponent } from './project.component';
import { ImportProjectComponent } from './components/import-project/import-project.component';
import { CreateProjectComponent } from './components/create-project/create-project.component';
import { ConfigureProjectComponent } from './components/configure-project/configure-project.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgbModal, NgbModalModule } from '@ng-bootstrap/ng-bootstrap';
import { NgbModalStack } from '@ng-bootstrap/ng-bootstrap/modal/modal-stack';
import { TreeviewModule } from 'ngx-treeview';
import { AnalysisTaskMgmtDeleteDialogComponent } from './components/configure-project/analysis-task-delete-dialog.component';
import { AnalysisTaskAddComponent } from './components/configure-project/analysis-task-add/analysis-task-add.component';
import {TableModule} from 'primeng/table';

@NgModule({
  imports: [
    NgbModalModule,
    CommonModule,
    ProjectRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    TreeviewModule.forRoot(),
    TableModule
  ],
  declarations: [
    ProjectComponent, 
    ImportProjectComponent, 
    CreateProjectComponent,
    ConfigureProjectComponent,
    AnalysisTaskAddComponent,
    AnalysisTaskMgmtDeleteDialogComponent, AnalysisTaskAddComponent
  ],
  entryComponents: [
    AnalysisTaskMgmtDeleteDialogComponent
  ],
  providers: [
    NgbModal,
    NgbModalStack
  ]
})
export class ProjectModule { }
