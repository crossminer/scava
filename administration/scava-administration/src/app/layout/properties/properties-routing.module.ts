import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { PropertiesComponent } from './properties.component';
import { JwtTokenGuard } from '../../shared';

const routes: Routes = [
  {
    path: '',
    canActivate: [JwtTokenGuard],
    component: PropertiesComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class PropertiesRoutingModule { }
