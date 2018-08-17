import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { WorkerComponent } from './worker.component';
import { RoleGuard } from '../../shared/guard/role.guard';


const routes: Routes = [
    {
        path: '', 
        component: WorkerComponent
    }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class WorkerRoutingModule { }
