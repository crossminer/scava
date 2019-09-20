import { Component, OnInit } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { VersionControlSystems } from '../../create-project/version-control-system.model';
import { Project } from '../../../project.model';

@Component({
  selector: 'app-add-connector',
  templateUrl: './add-connector-dialog.component.html'
})
export class ConnectorMgmtAddDialogComponent implements OnInit {

  public sourceInfo: string;
  public target: number;
  public project: Project;
  public isSaving: boolean;
  public repository: any;  
  constructor(
    public activeModal: NgbActiveModal
  ) { }

  ngOnInit() {
    this.isSaving = true;
    this.repository = new Object();
    this.repository._type = "";
    this.repository._id = "";
  }

  clear() {
    this.activeModal.dismiss('cancel');
  }

  save(sourceInfo: string, project: Project) {
    this.isSaving = true;
    switch (sourceInfo) {
      case "vcs":
        project.vcsRepositories.push(this.repository);
        break;
      case "bts":
        project.bugTrackingSystems.push(this.repository);
        break;
      case "cc":
        project.communicationChannels.push(this.repository);
        break;
      default:
        break;
    }
    this.activeModal.dismiss(true);
  }

}
