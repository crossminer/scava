import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UserManagementRoutingModule } from './user-management-routing.module';
import { UserManagementComponent } from './user-management.component';
import { UserManagementUpdateComponent } from './user-management-update/user-management-update.component';
import { FormsModule } from '@angular/forms';
import { NgbModalStack } from '@ng-bootstrap/ng-bootstrap/modal/modal-stack';
import { UserMgmtDeleteDialogComponent } from './user-management-delete-dialog/user-management-delete-dialog.component';
import { NgbModal, NgbModalModule } from '@ng-bootstrap/ng-bootstrap';

@NgModule({
  imports: [
    NgbModalModule,
    CommonModule,
    UserManagementRoutingModule,
    FormsModule
  ],
  declarations: [
    UserManagementComponent, 
    UserManagementUpdateComponent, 
    UserMgmtDeleteDialogComponent
  ],
  entryComponents: [
    UserMgmtDeleteDialogComponent,
  ],
  providers: [
    NgbModal,
    NgbModalStack
  ]
})
export class UserManagementModule { }
