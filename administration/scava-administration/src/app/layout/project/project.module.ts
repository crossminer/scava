import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProjectRoutingModule } from './project-routing.module';
import { ProjectComponent } from './project.component';
import { ImportProjectComponent } from './components/import-project/import-project.component';
import { CreateProjectComponent } from './components/create-project/create-project.component';
import { ConfigureProjectComponent } from './components/configure-project/configure-project.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AnalysisTaskMgmtAddDialogComponent } from './components/configure-project/analysis-task-add-dialog.component';
import { NgbModal, NgbModalModule } from '@ng-bootstrap/ng-bootstrap';
import { NgbModalStack } from '@ng-bootstrap/ng-bootstrap/modal/modal-stack';
import { TreeviewModule } from 'ngx-treeview';
import { AnalysisTaskMgmtDeleteDialogComponent } from './components/configure-project/analysis-task-delete-dialog.component';

@NgModule({
  imports: [
    NgbModalModule,
    CommonModule,
    ProjectRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    TreeviewModule.forRoot()
  ],
  declarations: [
    ProjectComponent, 
    ImportProjectComponent, 
    CreateProjectComponent,
    ConfigureProjectComponent, 
    AnalysisTaskMgmtAddDialogComponent, AnalysisTaskMgmtDeleteDialogComponent
  ],
  entryComponents: [
    AnalysisTaskMgmtAddDialogComponent,
    AnalysisTaskMgmtDeleteDialogComponent
  ],
  providers: [
    NgbModal,
    NgbModalStack
  ]
})
export class ProjectModule { }
