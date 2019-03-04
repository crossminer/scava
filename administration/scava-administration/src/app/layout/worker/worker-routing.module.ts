import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { WorkerComponent } from './worker.component';
import { JwtTokenGuard } from '../../shared';

const routes: Routes = [
    {
        path: '',
        canActivate: [JwtTokenGuard],
        component: WorkerComponent
    }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class WorkerRoutingModule { }
