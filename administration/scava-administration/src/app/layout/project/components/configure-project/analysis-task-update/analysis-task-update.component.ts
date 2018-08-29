import { Component, OnInit } from '@angular/core';
import { ExecutionTask, MetricProvider } from '../execution-task.model';
import { MatTableDataSource, DateAdapter, MAT_DATE_FORMATS } from '@angular/material';
import { SelectionModel, SelectionChange } from '@angular/cdk/collections';
import { ActivatedRoute, Router } from '@angular/router';
import { AnalysisTaskService } from '../../../../../shared/services/analysis-task/analysis-task.service';

@Component({
  selector: 'app-analysis-task-update',
  templateUrl: './analysis-task-update.component.html',
  styleUrls: ['./analysis-task-update.component.scss']
})
export class AnalysisTaskUpdateComponent implements OnInit {

  isSaving: boolean;
  executionTask: ExecutionTask;
  projectId: string;
  metricProviders: MetricProvider[];

  dataSource: MatTableDataSource<MetricProvider> = new MatTableDataSource<MetricProvider>([]);
  selection: SelectionModel<MetricProvider> = new SelectionModel<MetricProvider>(true, []);

  startDate: Date;
  endDate: Date;

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

  loadAll() {
    this.isSaving = false;
    this.route.paramMap.subscribe(
      (params) => {
        this.analysisTaskService.getTaskByAnalysisTaskId(params.get('id') + ':' + params.get('label')).subscribe(
          (analysiTask) => {
            debugger
            this.executionTask = analysiTask;
            this.executionTask.startDate = this.convertDate(new Date(this.executionTask.startDate['$date']).toString());
            this.executionTask.endDate = this.convertDate(new Date(this.executionTask.endDate['$date']).toString());
            console.log(this.executionTask)
            console.log(this.executionTask.metricExecutions)
            this.analysisTaskService.getMetricProviders().subscribe(
              (resp) => {
                debugger
                this.metricProviders = resp as MetricProvider[];
                this.dataSource = new MatTableDataSource<MetricProvider>(this.metricProviders);
                this.selection = new SelectionModel<MetricProvider>(true, []);
                // for(let metricExecution of this.executionTask.metricExecutions) {
                //   this.selection.select(metricExecution);
                // }
                console.log(this.selection)
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
      this.onRowSelect(data.added[0]);
    }
    if (data.removed.length !== 0) {
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
    console.log(this.selection)

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
    let dependencies: MetricProvider[] = this.serchInversDependency(row);

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

  serchInversDependency(ownerMP: MetricProvider): MetricProvider[] {
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
    this.executionTask.analysisTaskId = this.executionTask.projectId + ':' + this.executionTask.label;
    this.executionTask.startDate = this.convertDate(this.executionTask.startDate);
    this.executionTask.endDate = this.convertDate(this.executionTask.endDate);
    this.executionTask.metricProviders = this.selection.selected;
    this.analysisTaskService.createTask(this.executionTask).subscribe(
      (resp) => {
        console.log('executiontask created successfuly !');
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
