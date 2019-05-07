import { Component, OnInit } from '@angular/core';
import { StacktracesService } from '../../shared/services/stacktraces/stacktraces.service';
import { Stacktraces } from './stacktraces.model';
import { MatSort } from '@angular/material/sort';
import { ViewChild } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';

@Component({
  selector: 'app-stacktraces',
  templateUrl: './stacktraces.component.html',
  styleUrls: ['./stacktraces.component.scss']
})
export class StacktracesComponent implements OnInit {

  displayedColumns: string[] = ['projectName', 'clazz', 'stackTrace', 'workerIdentifier', 'executionErrorDate', 'analysisRangeErrorDate'];
  dataSource: MatTableDataSource<Stacktraces> = new MatTableDataSource<Stacktraces>([]);

  @ViewChild(MatSort) sort: MatSort;

  constructor(
    private stacktracesService: StacktracesService,
  ) { }

  ngOnInit() {
    this.loadAll();
  }

  loadAll() {
    this.stacktracesService.getStackTraces().subscribe(
      (resp) => {
        this.dataSource = new MatTableDataSource(resp as Array<Stacktraces>);
        this.dataSource.filterPredicate = function(data, filter: string): boolean {
          return data.projectName.toLowerCase().includes(filter);
        };
        this.dataSource.sort = this.sort;
      },
      (error) => {
        this.onShowMessage(error);
      }
    )
  }

  applyFilter(filterValue: string) {
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }
  

  onShowMessage(msg: any) {
    console.log(msg);
  }

}
