import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { UserManagementComponent } from './user-management.component';
import { UserManagementUpdateComponent } from './user-management-update/user-management-update.component';


const routes: Routes = [
  {
    path: '', 
    component: UserManagementComponent,
  },
  {
    path: ':email/edit',
    component: UserManagementUpdateComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class UserManagementRoutingModule { }
