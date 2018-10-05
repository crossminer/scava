import { Component, OnInit, ViewChild } from '@angular/core';
import { ExecutionTask, MetricProvider } from '../execution-task.model';
import { MatTableDataSource, DateAdapter, MAT_DATE_FORMATS, MatPaginator, MatSort } from '@angular/material';
import { SelectionModel, SelectionChange } from '@angular/cdk/collections';
import { ActivatedRoute, Router } from '@angular/router';
import { AnalysisTaskService } from '../../../../../shared/services/analysis-task/analysis-task.service';
import { CustomDateAdapter, CUSTOM_DATE_FORMATS } from '../custom-date-adapter';

@Component({
  selector: 'app-analysis-task-update',
  templateUrl: './analysis-task-update.component.html',
  styleUrls: ['./analysis-task-update.component.scss'],
  providers: [
    {
      provide: DateAdapter, useClass: CustomDateAdapter
    },
    {
      provide: MAT_DATE_FORMATS, useValue: CUSTOM_DATE_FORMATS
    }
  ]
})
export class AnalysisTaskUpdateComponent implements OnInit {

  isSaving: boolean;
  executionTask: ExecutionTask;
  projectId: string;
  metricProviders: MetricProvider[];
  oldAnalysisTaskId: string;

  startDate : Date;
  endDate : Date;
  maxStartDate: Date;
  maxEndDate: Date;

  mpoption : string;

  dataSource: MatTableDataSource<MetricProvider> = new MatTableDataSource<MetricProvider>([]);
  selection: SelectionModel<MetricProvider> = new SelectionModel<MetricProvider>(true, []);

  @ViewChild(MatSort) sort: MatSort;

  displayedColumns: string[] = ['select', 'kind', 'label', 'description', 'dependOf'];

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private analysisTaskService: AnalysisTaskService,
  ) {
    this.executionTask = new ExecutionTask();
  }

  ngOnInit() {
    this.loadAll();
    this.route.paramMap.subscribe(
      (data) => {
        this.projectId = data.get('id');
      });
  }

  ngAfterViewInit() {
    this.dataSource.sort = this.sort;
  }

  loadAll() {
    this.isSaving = false;
    this.maxStartDate = new Date();
    this.maxEndDate = this.maxStartDate;
    this.route.paramMap.subscribe(
      (params) => {
        this.analysisTaskService.getTaskByAnalysisTaskId(params.get('id') + ':' + params.get('label')).subscribe(
          (analysiTask) => {
            this.oldAnalysisTaskId = params.get('id') + ':' + params.get('label');
            this.executionTask = analysiTask;
            this.startDate = new Date(this.executionTask.startDate['$date']);
            if(this.executionTask.type == 'SINGLE_EXECUTION') {
              this.endDate = new Date(this.executionTask.endDate['$date']);
            }
            this.analysisTaskService.getMetricProviders().subscribe(
              (resp) => {
                this.metricProviders = resp as MetricProvider[];
                this.dataSource = new MatTableDataSource<MetricProvider>(this.metricProviders);
                this.selection = new SelectionModel<MetricProvider>(true, []);
                this.executionTask.metricExecutions.forEach((me) => this.selection.select(this.metricProviders.find((mp) => mp.metricProviderId == me.metricProviderId)));
                this.dataSource.sort = this.sort;

                //console.log(this.selection)
                this.selection.onChange.subscribe(data => this.getSelectedData(data));

                if(this.isAllSelected()){
                  this.mpoption = "mpoption-all";
                }else{
                  this.mpoption = "mpoption-selected";
                }
              },
              (error) => {
                this.onError(error);
              })
          },
          (error) => {
            this.onError(error);
          }
        )
      }
    );
  }

  applyFilter(filterValue: string) {
    filterValue = filterValue.trim().toLowerCase();
    this.dataSource.filter = filterValue;
  }

  getSelectedData(data: SelectionChange<MetricProvider>) {
    if (data.added.length !== 0) {
      console.log('selected');
      //console.log(data);
      this.onRowSelect(data.added[0]);
    }
    if (data.removed.length !== 0) {
      console.log('unselected');
      //console.log(data);
      this.onRowUnselect(data.removed[0]);
    }
  }

  /** Whether the number of selected elements matches the total number of rows. */
  isAllSelected() {
    const numSelected = this.selection.selected.length;
    const numRows = this.dataSource.data.length;
    return numSelected === numRows;
  }

  /** Selects all rows if they are not all selected; otherwise clear selection. */
  masterToggle() {
    this.isAllSelected() ?
      this.selection.clear() :
      this.dataSource.data.forEach(row => this.selection.select(row));
  }

  onRowSelect(row) {
    //console.log(row)
    this.selectDependencies(row);

    //  this.selection = new SelectionModel<MetricProvider>(true,this.selection.selected);    
    //console.log(this.selection)

  }

  selectDependencies(ownerMP: MetricProvider) {
    for (let obj of ownerMP.dependOf) {
      //console.log(obj)
      let mp = this.dataSource.data.find(mp => mp.metricProviderId == obj.metricProviderId);
      //console.log(mp)
      if (!this.selection.isSelected(mp)) {
        this.selection.select(mp);
      }
    }
  }

  onRowUnselect(row: MetricProvider) {
    let dependencies: MetricProvider[] = this.searchInversDependency(row);

    for (let oldMP of dependencies) {
      if (this.selection.isSelected(oldMP)) {
        this.selection.deselect(oldMP);
      }
    }
    // let newselectedMetricProviders : MetricProvider [] = [];

    // if(this.selection.isSelected)
    // for(let oldMP of this.selection.selected){
    //  if(dependencies.find(mp =>mp.metricProviderId == oldMP.metricProviderId) == undefined){
    //   newselectedMetricProviders.push(oldMP);
    //  }
    // }
    // this.selection = new SelectionModel<MetricProvider>(true,newselectedMetricProviders);
    // console.log(this.selection)
  }

  searchInversDependency(ownerMP: MetricProvider): MetricProvider[] {
    let dependencies: MetricProvider[] = [];
    for (let mp of this.dataSource.data) {
      let findMp: MetricProvider = mp.dependOf.find(fmp => fmp.metricProviderId == ownerMP.metricProviderId);

      if (findMp != undefined) {

        dependencies.push(mp);
        //  for(let submp of this.serchInversDependency(mp)){
        //    dependencies.push(submp);
        //  }   
      }
    }
    return dependencies;
  }

  save() {
    this.isSaving = true;
    this.executionTask.oldAnalysisTaskId = this.oldAnalysisTaskId;
    this.executionTask.analysisTaskId = this.executionTask.projectId + ':' + this.executionTask.label;
    this.executionTask.startDate = this.convertDate(this.startDate.toString());
    if(this.executionTask.type == 'SINGLE_EXECUTION') {
      this.executionTask.endDate = this.convertDate(this.endDate.toString());
    }
    let metrics: string[] = [];
    //console.log(this.selection.selected);
    this.selection.selected.forEach((mp) => metrics.push(mp.metricProviderId));
    this.executionTask.metricProviders = metrics;
    this.analysisTaskService.updateTask(this.executionTask).subscribe(
      (resp) => {
        console.log('executiontask updated successfuly !');
        this.router.navigate(['project/configure/' + this.projectId]);
      },
      (error) => {
        console.log(error);
      }
    )
  }

  convertDate(inputFormat: string): string {
    function pad(s) { return (s < 10) ? '0' + s : s; }
    var d = new Date(inputFormat);
    return [pad(d.getDate()), pad(d.getMonth() + 1), d.getFullYear()].join('/');
  }

  previousState() {
    this.router.navigate(['project/configure/' + this.projectId]);
  }

  onError(error) {
    console.log(error);
  }

}
