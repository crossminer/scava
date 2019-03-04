import { Component, OnInit } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { DeleteProjectService } from '../../../../shared/services/project-service/delete-project.service';

@Component({
  selector: 'app-project-delete-dialog',
  templateUrl: './project-delete-dialog.component.html'
})
export class ProjectMgmtDeleteDialogComponent {

  projectId: string;
  
  constructor(
    public activeModal: NgbActiveModal,
    private deleteProjectService: DeleteProjectService
  ) { }

  ngOnInit() {
  }

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(projectId: string) {
    this.deleteProjectService.deleteProject(projectId).subscribe((resp) => {
        this.activeModal.dismiss(true);
    });
  }

}
