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
import { MatNativeDateModule } from '@angular/material/core';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule} from '@angular/material/input';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatTableModule } from '@angular/material/table';
import { MatCheckboxModule } from '@angular/material/checkbox';
import {MatListModule} from '@angular/material/list';


@NgModule({
  imports: [
    NgbModalModule,
    CommonModule,
    ProjectRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    TreeviewModule.forRoot(),
    MatFormFieldModule,
    MatNativeDateModule,
    MatInputModule,
    MatDatepickerModule,
    MatTableModule,
    MatCheckboxModule,
    MatListModule

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
