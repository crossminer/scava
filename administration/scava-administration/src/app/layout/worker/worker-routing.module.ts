import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { WorkerComponent } from './worker.component';

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
