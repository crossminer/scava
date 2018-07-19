import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ProjectRoutingModule } from './project-routing.module';
import { ProjectComponent } from './project.component';
import { ImportProjectComponent } from './components/import-project/import-project.component';
import { CreateProjectComponent } from './components/create-project/create-project.component';
import { ConfigureProjectComponent } from './components/configure-project/configure-project.component';


import { FormsModule, ReactiveFormsModule } from '@angular/forms';

@NgModule({
  imports: [
    CommonModule,
    ProjectRoutingModule,
    FormsModule,
    ReactiveFormsModule
  ],
  declarations: [ProjectComponent, ImportProjectComponent, CreateProjectComponent,ConfigureProjectComponent]
})
export class ProjectModule { }
