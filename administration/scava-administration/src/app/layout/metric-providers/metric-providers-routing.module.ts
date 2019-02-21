import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { MetricProvidersComponent } from './metric-providers.component';
import { RoleGuard, JwtTokenGuard } from '../../shared';

const routes: Routes = [
  {
    path: '',
    component: MetricProvidersComponent,
    canActivate: [RoleGuard, JwtTokenGuard],
    data: {
        authorities: ['ROLE_ADMIN', 'ROLE_PROJECT_MANAGER', 'ROLE_USER']
    }
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class MetricProvidersRoutingModule { }
