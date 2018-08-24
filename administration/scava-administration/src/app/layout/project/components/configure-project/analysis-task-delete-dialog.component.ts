import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { AnalysisTaskService } from '../../../../shared/services/analysis-task/analysis-task.service';
import { debug } from 'util';

@Component({
  selector: 'app-analysis-task-delete-dialog',
  templateUrl: './analysis-task-delete-dialog.component.html'
})
export class AnalysisTaskMgmtDeleteDialogComponent {

  constructor(
    private router: Router,
    public activeModal: NgbActiveModal,
    public analysisTaskService: AnalysisTaskService
  ) { }

  ngOnInit() {
  }

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(analysisTaskId: string) {
    this.analysisTaskService.deleteTask(analysisTaskId).subscribe((resp) => {
        this.activeModal.dismiss(true);
    });
  }

}
