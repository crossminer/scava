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
import { AnalysisTaskMgmtDeleteDialogComponent } from './components/configure-project/analysis-task-delete/analysis-task-delete-dialog.component';
import { AnalysisTaskAddComponent } from './components/configure-project/analysis-task-add/analysis-task-add.component';
import { AnalysisTaskUpdateComponent } from './components/configure-project/analysis-task-update/analysis-task-update.component';
import { MatNativeDateModule, MatRippleModule } from '@angular/material/core';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule} from '@angular/material/input';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatTableModule } from '@angular/material/table';
import { MatCheckboxModule } from '@angular/material/checkbox';
import {MatListModule} from '@angular/material/list';
import {MatRadioModule} from '@angular/material/radio';
import {MatSortModule} from '@angular/material/sort';
import { MetricProvidersMgmtInfoDialogComponent } from './components/configure-project/metrics-infos/metric-info.component';
import {MatPaginatorModule} from '@angular/material/paginator';
import { MatTreeModule } from '@angular/material/tree';
import {MatIconModule} from '@angular/material/icon';
import { MatButtonModule, MatAutocompleteModule, MatBadgeModule, MatBottomSheetModule, MatButtonToggleModule, MatCardModule, MatChipsModule, MatDialogModule, MatDividerModule, MatExpansionModule, MatGridListModule, MatMenuModule, MatProgressBarModule, MatProgressSpinnerModule, MatSelectModule, MatSidenavModule, MatSliderModule, MatSlideToggleModule, MatSnackBarModule, MatStepperModule, MatTabsModule, MatToolbarModule, MatTooltipModule } from '@angular/material';
import { EditProjectComponent } from './components/edit-project/edit-project.component';
import { CdkTableModule } from '@angular/cdk/table';
import { CdkTreeModule } from '@angular/cdk/tree';
import { ProjectMgmtDeleteDialogComponent } from './components/delete-project/delete-project-dialog.component';

@NgModule({
  imports: [
    CdkTableModule,
    CdkTreeModule,
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
    MatListModule,
    MatRadioModule,
    MatSortModule,
    MatPaginatorModule,
    MatAutocompleteModule,
    MatBadgeModule,
    MatBottomSheetModule,
    MatButtonModule,
    MatButtonToggleModule,
    MatCardModule,
    MatChipsModule,
    MatDialogModule,
    MatDividerModule,
    MatExpansionModule,
    MatGridListModule,
    MatIconModule,
    MatMenuModule,
    MatProgressBarModule,
    MatProgressSpinnerModule,
    MatRippleModule,
    MatSelectModule,
    MatSidenavModule,
    MatSliderModule,
    MatSlideToggleModule,
    MatSnackBarModule,
    MatStepperModule,
    MatTabsModule,
    MatToolbarModule,
    MatTooltipModule,
    MatTreeModule,
    MatProgressSpinnerModule
  ],
  declarations: [
    ProjectComponent, 
    ImportProjectComponent, 
    CreateProjectComponent,
    ConfigureProjectComponent,
    AnalysisTaskAddComponent,
    AnalysisTaskUpdateComponent,
    AnalysisTaskMgmtDeleteDialogComponent,
    MetricProvidersMgmtInfoDialogComponent,
    EditProjectComponent,
    ProjectMgmtDeleteDialogComponent
  ],
  entryComponents: [
    AnalysisTaskMgmtDeleteDialogComponent,
    MetricProvidersMgmtInfoDialogComponent,
    ProjectMgmtDeleteDialogComponent
  ],
  providers: [
    NgbModal,
    NgbModalStack
  ]
})
export class ProjectModule { }
