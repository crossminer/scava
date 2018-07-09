import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { UserManagementRoutingModule } from './user-management-routing.module';
import { UserManagementComponent } from './user-management.component';
import { UserManagementUpdateComponent } from './user-management-update/user-management-update.component';
import { FormsModule } from '@angular/forms';
import { NgbModalStack } from '@ng-bootstrap/ng-bootstrap/modal/modal-stack';
import { UserMgmtDeleteDialogComponent } from './user-management-delete-dialog/user-management-delete-dialog.component';
import { NgbModalBackdrop } from '@ng-bootstrap/ng-bootstrap/modal/modal-backdrop';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { NgbModalWindow } from '@ng-bootstrap/ng-bootstrap/modal/modal-window';

@NgModule({
  imports: [
    CommonModule,
    UserManagementRoutingModule,
    FormsModule
  ],
  declarations: [
    UserManagementComponent, 
    UserManagementUpdateComponent, 
    UserMgmtDeleteDialogComponent,
    NgbModalBackdrop,
    NgbModalWindow
  ],
  entryComponents: [
    UserMgmtDeleteDialogComponent,
    NgbModalBackdrop,
    NgbModalWindow
  ],
  providers: [
    NgbModal,
    NgbModalStack
  ]
})
export class UserManagementModule { }
