import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { Properties } from './properties.model';
import { PropertiesService } from '../../shared/services/properties-service/properties.service';
import { Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { PropertiesMgmtDeleteDialogComponent } from './properties.delete/properties-delete-dialog.component';

@Component({
  selector: 'app-properties',
  templateUrl: './properties.component.html',
  styleUrls: ['./properties.component.scss']
})
export class PropertiesComponent implements OnInit {

  listProperties: Properties[];
  properties: Properties;
  showNew: Boolean = false;
  operation: string;

  constructor(
    public propertiesService: PropertiesService,
    private router: Router,
    public modalService: NgbModal,
  ) { }

  ngOnInit() {
    this.loadAll();
  }

  loadAll() {
    this.propertiesService.getProperties().subscribe(
      (resp) => {
        this.listProperties = resp as Properties[];
      }, (error) => {
        this.onShowMessage(error);
      }
    )
  }

  onNew() {
    this.properties = new Properties();
    this.showNew = true;
    this.operation = 'add';
  }

  onEdit(key: string) {
    this.showNew = true;
    this.operation = 'edit';
    this.propertiesService.getPropertiesByKey(key).subscribe(
      (resp) => {
        this.properties = resp;
        this.properties.oldkey = this.properties.key;
      }, (error) => {
        this.onShowMessage(error);
      }
    )
  }

  save() {
    if (this.operation === 'add') {
      this.propertiesService.createProperties(this.properties).subscribe(
        (resp) => {
          this.showNew = false;
        }, (error) => {
          this.onShowMessage(error);
        }
      );
    } else {
      this.propertiesService.updateProperties(this.properties).subscribe(
        (resp) => {
          this.showNew = false;
        }, (error) => {
          this.onShowMessage(error);
        }
      )
    }
    this.loadAll();
  }

  onCancel() {
    this.showNew = false;
  }

  onDelete(key: string) {
    const modalRef = this.modalService.open(PropertiesMgmtDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.key = key;
    modalRef.result.then(
      (result) => {
        this.onShowMessage('delete success');
        this.loadAll();
      },
      (reason) => {
        this.onShowMessage('delete failed');
        this.loadAll();
      }
    );
  }

  previousState() {
    this.router.navigate(['/home']);
  }

  onShowMessage(msg: any) {
    console.log(msg);
  }
}