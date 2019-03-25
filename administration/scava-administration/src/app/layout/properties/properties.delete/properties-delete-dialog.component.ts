import { Component, OnInit } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { PropertiesService } from '../../../shared/services/properties-service/properties.service';

@Component({
  selector: 'app-properties-delete-dialog',
  templateUrl: './properties-delete-dialog.component.html'
})
export class PropertiesMgmtDeleteDialogComponent {

  key: string;

  constructor(
    public activeModal: NgbActiveModal,
    public propertiesService: PropertiesService
  ) { }

  ngOnInit() {
  }

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(key: string) {
    this.propertiesService.deleteProperties(key).subscribe((resp) => {
        this.activeModal.dismiss(true);
    });
  }

}
