import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { JwtTokenGuard } from '../../shared';
import { StacktracesComponent } from './stacktraces.component';

const routes: Routes = [
  {
    path: '',
    canActivate: [JwtTokenGuard],
    component: StacktracesComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class StacktracesRoutingModule { }
