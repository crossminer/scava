import { Component, OnInit } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { VersionControlSystems } from '../../create-project/version-control-system.model';
import { Project } from '../../../project.model';

@Component({
  selector: 'app-add-connector',
  templateUrl: './add-connector-dialog.component.html'
})
export class ConnectorMgmtAddDialogComponent implements OnInit {

  public isSaving: boolean;
  public gitRepository: any;
  constructor(
    public activeModal: NgbActiveModal
  ) { }

  ngOnInit() {
    this.isSaving = true;
    this.gitRepository = new Object();
    this.gitRepository._id = null;
  }

  clear() {
    this.activeModal.dismiss('cancel');
  }

  save(sourceInfo: string, project: Project) {
    this.isSaving = true;
    switch (sourceInfo) {
      case "vcs":
        project.vcsRepositories.push(this.gitRepository);
        break;
      case "bts":
        project.bugTrackingSystems.push();
        break;
      case "cc":
        project.communicationChannels.push();
        break;
      default:
        break;
    }
    this.activeModal.dismiss(true);
  }

}
