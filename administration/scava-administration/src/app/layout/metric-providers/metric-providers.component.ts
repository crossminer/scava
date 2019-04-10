import { Component, OnInit, ViewChild } from '@angular/core';
import { MatTableDataSource, MatSort } from '@angular/material';
import { SelectionModel } from '@angular/cdk/collections';
import { MetricProvider } from '../project/components/configure-project/execution-task.model';
import { AnalysisTaskService } from '../../shared/services/analysis-task/analysis-task.service';

@Component({
  selector: 'app-metric-providers',
  templateUrl: './metric-providers.component.html',
  styleUrls: ['./metric-providers.component.scss']
})
export class MetricProvidersComponent implements OnInit {

  showSpinner: boolean;
  dataSource: MatTableDataSource<MetricProvider> = new MatTableDataSource<MetricProvider>([]);
  selection: SelectionModel<MetricProvider> = new SelectionModel<MetricProvider>(true, []);

  @ViewChild(MatSort) sort: MatSort;
  displayedColumns: string[] = ['kind', 'label', 'description', 'dependOf'];

  constructor(
    private analysisTaskService: AnalysisTaskService,
  ) { }

  ngOnInit() {
    this.loadAll();
  }
  
  loadAll() {
    this.showSpinner = true;
    this.analysisTaskService.getMetricProviders().subscribe(
      (resp) => {
        this.dataSource = new MatTableDataSource<MetricProvider>(resp as MetricProvider[]);
        this.selection = new SelectionModel<MetricProvider>(true, []);
        this.dataSource.sort = this.sort;
        this.showSpinner=false;
      },
      (error) => {

      }
    )
  }

  applyFilter(filterValue: string) {
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

}
