import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ProfileRoutingModule } from './profile-routing.module';
import { ProfileComponent } from './profile.component';
import { FormsModule } from '@angular/forms';
import { TokenAuthoritiesMgmtDeleteDialogComponent } from './components/token-authorities.delete/token-authorities-delete-dialog.component';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { NgbModalStack } from '@ng-bootstrap/ng-bootstrap/modal/modal-stack';

@NgModule({
  imports: [
    CommonModule,
    ProfileRoutingModule,
    FormsModule
  ],
  declarations: [
    ProfileComponent,
    TokenAuthoritiesMgmtDeleteDialogComponent
  ],
  entryComponents: [
    TokenAuthoritiesMgmtDeleteDialogComponent
  ],
  providers: [
    NgbModal,
    NgbModalStack
  ]
})
export class ProfileModule { }
