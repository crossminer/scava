import { Component, OnInit, ViewChild } from '@angular/core';
import { AnalysisTaskService } from '../../../../shared/services/analysis-task/analysis-task.service';
import { MatTableDataSource, MatSort } from '@angular/material';
import { MetricProvider } from '../configure-project/execution-task.model';
import { SelectionModel } from '@angular/cdk/collections';

@Component({
  selector: 'app-metric-providers',
  templateUrl: './metric-providers.component.html',
  styleUrls: ['./metric-providers.component.scss']
})
export class MetricProvidersComponent implements OnInit {

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
    this.analysisTaskService.getMetricProviders().subscribe(
      (resp) => {
        this.dataSource = new MatTableDataSource<MetricProvider>(resp as MetricProvider[]);
        console.log(this.dataSource);
        this.selection = new SelectionModel<MetricProvider>(true, []);
        this.dataSource.sort = this.sort;
      },
      (error) => {

      }
    )
  }

  applyFilter(filterValue: string) {
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

}
