import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ListProjectService } from '../../../../shared/services/project-service/list-project.service';
import { RoleAuthorities } from '../../../../shared/services/authentication/role-authorities';
import { Project } from '../../project.model';
import { EditProjectService } from '../../../../shared/services/project-service/edit-project.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ConnectorMgmtAddDialogComponent } from './add-connector/add-connector-dialog.component';
import { CommunicationChannels } from '../create-project/communication-channels.model';


@Component({
  selector: 'app-edit-project',
  templateUrl: './edit-project.component.html',
  styleUrls: ['./edit-project.component.scss']
})
export class EditProjectComponent implements OnInit {

  project: Project;
  isSaving: boolean;

  constructor(
    private route: ActivatedRoute,
    private listProjectService: ListProjectService,
    private editProjectService: EditProjectService,
    public modalService: NgbModal,
    public roleAuthorities: RoleAuthorities,
    private router: Router
  ) {
  }

  ngOnInit() {
    this.isSaving = false;
    this.loadAll();
  }

  loadAll() {
    if (!this.roleAuthorities.isCurrentTokenExpired()) {
      this.route.paramMap.subscribe(data => {
        this.listProjectService.getProject(data.get('id')).subscribe(
          (data) => {
            this.project = data;
            this.project.communicationChannels.forEach(
              (cc)=>{
                if (cc.url != undefined 
                  && cc.executionFrequency != undefined && cc.loginURL != undefined
                  && cc.username != undefined && cc.usernameFieldName != undefined
                  && cc.password != undefined && cc.passwordFieldName != undefined
                  ) {
                return cc.loginOption = "option2";
              } else {
                return cc.loginOption = "option1";
              }
              }
            );
            console.log(this.project)
          },
          (error) => {
            this.onSaveError(error);
          });
      });
    }
  }

  save() {
    this.editProjectService.editProject(this.project).subscribe(
      (resp) => {
        this.onSaveSuccess(resp);
      },
      (error) => {
        this.onSaveError(error);
      }
    )
    this.previousState();
  }

  addConnector(sourceInfo: string) {
    const modalRef = this.modalService.open(ConnectorMgmtAddDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.project = this.project;
    modalRef.componentInstance.sourceInfo = sourceInfo;
  }

  removeConnector(sourceInfo: string, target: number) {
    switch (sourceInfo) {
        case "vcs":
          this.project.vcsRepositories.splice(target, 1);
          break;
        case "bts":
          this.project.bugTrackingSystems.splice(target, 1);
          break;
        case "cc":
          this.project.communicationChannels.splice(target, 1);
          break;
        default:
          break;
      }
  }

  previousState() {
    this.router.navigate(['/project/configure/' + this.project.shortName]);
  }

  private onSaveSuccess(result: any) {
    this.isSaving = true;
    this.previousState();
  }

  private onSaveError(msg: string) {
    this.isSaving = false;
    console.log(msg);
  }
}