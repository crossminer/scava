import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { ExecutionTask } from '../../../layout/project/components/configure-project/execution-task.model';


@Component({
  selector: 'antlysis-task-metric-info',
  templateUrl: './metric-info.component.html',
  styleUrls: ['./metric-info.component.scss']
})
export class MetricInfoComponent {
  authorities = ['ROLE_ADMIN', 'ROLE_PROJECT_MANAGER'];
  analysisTask : ExecutionTask;
  constructor(
    public activeModal: NgbActiveModal
  ) { }


  clear() {
    this.activeModal.dismiss('cancel');
  }


}
