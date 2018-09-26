import { Component, ViewChild } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { ExecutionTask, MetricExecutions, MetricProvider } from '../../../layout/project/components/configure-project/execution-task.model';
import { MatTableDataSource, MatPaginator, MatSort } from '@angular/material';
import { SelectionModel } from '@angular/cdk/collections';


@Component({
  selector: 'antlysis-task-metric-info',
  templateUrl: './metric-info.component.html',
  styleUrls: ['./metric-info.component.scss']
})
export class MetricInfoComponent {
  authorities = ['ROLE_ADMIN', 'ROLE_PROJECT_MANAGER'];
  analysisTask: ExecutionTask;

  dataSource: MatTableDataSource<MetricProvider> = new MatTableDataSource<MetricProvider>([]);
  selection: SelectionModel<MetricProvider> = new SelectionModel<MetricProvider>(true, []);

  displayedColumns: string[] = ['metricProviderId', 'lastExecutionDate'];

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  constructor(
    public activeModal: NgbActiveModal
  ) {
  }

  ngOnInit() {
    this.dataSource = new MatTableDataSource<MetricProvider>(this.analysisTask.metricExecutions);
  }

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }

  clear() {
    this.activeModal.dismiss('cancel');
  }


}
