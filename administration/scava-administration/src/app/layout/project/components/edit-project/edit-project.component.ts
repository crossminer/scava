import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ListProjectService } from '../../../../shared/services/project-service/list-project.service';
import { RoleAuthorities } from '../../../../shared/services/authentication/role-authorities';
import { Project } from '../../project.model';
import { EditProjectService } from '../../../../shared/services/project-service/edit-project.service';


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
    console.log(this.project)
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