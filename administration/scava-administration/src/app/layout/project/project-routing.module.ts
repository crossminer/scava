import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ProjectComponent } from './project.component';
import { ImportProjectComponent } from './components/import-project/import-project.component';
import { CreateProjectComponent } from './components/create-project/create-project.component';
import { ConfigureProjectComponent } from './components/configure-project/configure-project.component';


const routes: Routes = [
    {
        path: '', component: ProjectComponent,
    },
    {
        path: 'create', component: CreateProjectComponent
    },
    {
        path: 'import', component: ImportProjectComponent
    },
    {
        path: 'configure/:id', component: ConfigureProjectComponent
    }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ProjectRoutingModule { }
