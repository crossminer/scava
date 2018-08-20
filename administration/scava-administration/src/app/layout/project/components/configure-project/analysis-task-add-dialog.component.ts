import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-analysis-task-add-dialog',
  templateUrl: './analysis-task-add-dialog.component.html'
})
export class AnalysisTaskMgmtAddDialogComponent {

  constructor(
    private router: Router,
    public activeModal: NgbActiveModal
  ) { }

  ngOnInit() {
  }

  clear() {
    this.activeModal.dismiss('cancel');
  }

  save(login) {
  }

}
