import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { DeleteProjectService } from '../../../../shared/services/project-service/delete-project.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-project-delete-dialog',
  templateUrl: './delete-project-dialog.component.html'
})
export class ProjectMgmtDeleteDialogComponent {

  projectId: string;
  
  constructor(
    public activeModal: NgbActiveModal,
    private deleteProjectService: DeleteProjectService,
    private router: Router
  ) { }

  ngOnInit() {
  }

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(projectId: string) {
    this.deleteProjectService.deleteProject(projectId).subscribe((resp) => {
        this.activeModal.dismiss(true);
        //this.previousState();
    });
  }

  previousState() {
    this.router.navigate(['project']);
  }

}
