import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-analysis-algorithm-add-dialog',
  templateUrl: './analysis-algorithm-add-dialog.component.html'
})
export class AnalysisAlgorithmMgmtAddDialogComponent {

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
