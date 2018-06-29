import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { UserManagementRoutingModule } from './user-management-routing.module';
import { UserManagementComponent } from './user-management.component';
import { UserManagementUpdateComponent } from './user-management-update/user-management-update.component';
import { UserManagementDeleteComponent } from './user-management-delete/user-management-delete.component';
@NgModule({
  imports: [
    CommonModule,
    UserManagementRoutingModule
  ],
  declarations: [UserManagementComponent, UserManagementUpdateComponent, UserManagementDeleteComponent]
})
export class UserManagementModule { }
