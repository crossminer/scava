import { Component, OnInit, ViewChild } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { AnalysisTaskService } from '../../../../../shared/services/analysis-task/analysis-task.service';
import { MetricProvider, ExecutionTask } from '../execution-task.model';
import { MatTableDataSource } from '@angular/material/table';
import { SelectionModel, SelectionChange } from '@angular/cdk/collections';
import { DateAdapter, MAT_DATE_FORMATS } from '@angular/material/core';
import { CustomDateAdapter, CUSTOM_DATE_FORMATS } from '../custom-date-adapter';
import { MatSort } from '@angular/material';


@Component({
  selector: 'app-analysis-task-add',
  templateUrl: './analysis-task-add.component.html',
  styleUrls: ['./analysis-task-add.component.scss'],
  providers: [
    {
      provide: DateAdapter, useClass: CustomDateAdapter
    },
    {
      provide: MAT_DATE_FORMATS, useValue: CUSTOM_DATE_FORMATS
    }
  ]
})
export class AnalysisTaskAddComponent implements OnInit {

  isSaving: boolean;
  executionTask: ExecutionTask;
  //projectId: string;
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
        this.executionTask.projectId = data.get('id');
        //this.mpoption  = "mpoption-all";
      });
  }

  ngAfterViewInit() {
    // this.dataSource.sort = this.sort;
  }

  loadAll() {
    this.isSaving = false;
    this.maxStartDate = new Date();
    this.maxEndDate = this.maxStartDate;
    this.analysisTaskService.getMetricProviders().subscribe(
      (resp) => {
        //this.metricProviders = resp as any[];
        //this.groupByKind = this.sortByGroup(this.metricProviders);
        //console.log(this.metricProviders)

        this.dataSource = new MatTableDataSource<MetricProvider>(resp as MetricProvider[]);
        //console.log(this.dataSource);
        this.selection = new SelectionModel<MetricProvider>(true, []);
        this.dataSource.sort = this.sort;
        
        this.selection.onChange.subscribe(data => this.getSelectedData(data));
        //this.selection.onChange.unsubscribe();


        //  this.selection.onChange.subscribe(data => console.log(data));

      },
      (error) => {
        this.onError(error);
      })
  }

  applyFilter(filterValue: string) {
    filterValue = filterValue.trim().toLowerCase();
    this.dataSource.filter = filterValue;
  }

  getSelectedData(data: SelectionChange<MetricProvider>) {
    //console.log("event !!!!!!")
    if (data.added.length !== 0) {
      console.log('selected');
      console.log(data);
      this.onRowSelect(data.added[0]);
    }
    if (data.removed.length !== 0) {
      console.log('unselected');
      console.log(data);
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
    this.executionTask.analysisTaskId = this.executionTask.projectId + ':' + this.executionTask.label;
    this.executionTask.startDate = this.convertDate(this.executionTask.startDate);
    this.executionTask.endDate = this.convertDate(this.executionTask.endDate);
    let metrics: string[] = [];
    console.log(this.selection.selected);
    this.selection.selected.forEach((mp) => metrics.push(mp.metricProviderId));
    this.executionTask.metricProviders = metrics;
    this.analysisTaskService.createTask(this.executionTask).subscribe(
      (resp) => {
        console.log('executiontask created successfuly !');
        this.router.navigate(['project/configure/' + this.executionTask.projectId]);
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
      this.router.navigate(['project/configure/' + this.executionTask.projectId]);
  }

  onError(error) {
    console.log(error);
  }

}

