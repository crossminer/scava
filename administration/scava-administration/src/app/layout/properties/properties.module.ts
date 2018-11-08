import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { PropertiesRoutingModule } from './properties-routing.module';
import { PropertiesComponent } from './properties.component';
import { FormsModule } from '@angular/forms';
import { NgbModalStack } from '@ng-bootstrap/ng-bootstrap/modal/modal-stack';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { PropertiesMgmtDeleteDialogComponent } from './properties.delete/properties-delete-dialog.component';

@NgModule({
  imports: [
    CommonModule,
    PropertiesRoutingModule,
    FormsModule,  
  ],
  declarations: [
    PropertiesComponent,
    PropertiesMgmtDeleteDialogComponent
  ],
  entryComponents: [
    PropertiesMgmtDeleteDialogComponent
  ],
  providers: [
    NgbModal,
    NgbModalStack
  ]
})
export class PropertiesModule { }
