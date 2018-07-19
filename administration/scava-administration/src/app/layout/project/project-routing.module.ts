import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ProjectComponent } from './project.component';
import { ImportProjectComponent } from './components/import-project/import-project.component';
import { CreateProjectComponent } from './components/create-project/create-project.component';
import { ConfigureProjectComponent } from './components/configure-project/configure-project.component';
import { RoleGuard } from '../../shared/guard/role.guard';


const routes: Routes = [
    {
        path: '', 
        component: ProjectComponent
    },
    {
        path: 'create', 
        component: CreateProjectComponent,
        canActivate: [RoleGuard],
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_PROJECT_MANAGER']
        }
    },
    {
        path: 'import', 
        component: ImportProjectComponent,
        canActivate: [RoleGuard],
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_PROJECT_MANAGER']
        }
    },
    {
        path: 'configure/:id', 
        component: ConfigureProjectComponent,
        canActivate: [RoleGuard],
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_PROJECT_MANAGER', 'ROLE_USER']
        }
    }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ProjectRoutingModule { }
