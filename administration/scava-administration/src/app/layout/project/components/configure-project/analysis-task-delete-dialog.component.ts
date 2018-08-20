import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { AnalysisTaskService } from '../../../../shared/services/analysis-task/analysis-task.service';

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

  confirmDelete(analysisTaskId) {
    this.analysisTaskService.deleteTask('QualityGuardAnalysis:Analysis1').subscribe((resp) => {
        this.activeModal.dismiss(true);
    });
  }

}
