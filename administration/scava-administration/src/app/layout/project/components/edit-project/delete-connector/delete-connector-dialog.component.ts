import { Component, OnInit } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Project } from '../../../project.model';

@Component({
  selector: 'app-delete-connector',
  templateUrl: './delete-connector-dialog.component.html'
})
export class ConnectorMgmtDeleteDialogComponent implements OnInit {

  projectId: string;

  constructor(
    public activeModal: NgbActiveModal
  ) { }

  ngOnInit() {
  }

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(sourceInfo: string, target: number, project: Project) {
    switch (sourceInfo) {
      case "vcs":
        project.vcsRepositories.splice(target, 1);
        break;
      case "bts":
        project.bugTrackingSystems.splice(target, 1);
        break;
      case "cc":
        project.communicationChannels.splice(target, 1);
        break;
      default:
        break;
    }
    this.activeModal.dismiss(true);
  }

}
