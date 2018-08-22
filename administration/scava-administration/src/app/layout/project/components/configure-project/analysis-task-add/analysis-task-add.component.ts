import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { AnalysisTaskService } from '../../../../../shared/services/analysis-task/analysis-task.service';
import { MetricProviders, IMetricProviders } from './metric-providers-model';

@Component({
  selector: 'app-analysis-task-add',
  templateUrl: './analysis-task-add.component.html',
  styleUrls: ['./analysis-task-add.component.scss']
})
export class AnalysisTaskAddComponent implements OnInit {

  metricProviders: MetricProviders[];
  groupByKind: {};
  selectedMetricProviders: MetricProviders[];
  kinds: string[];
  cols: any[];

  startDate: Date;
  endDate: Date;
  minStartDate: Date;
  minEndDate: Date;
  
  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private analysisTaskService: AnalysisTaskService,
  ) { }

  ngOnInit() {
    this.loadAll();

    this.minStartDate = new Date();
    this.minEndDate = this.minStartDate;
    
    this.kinds = [
      'HISTORIC', 'FACTOID', 'TRANSIENT'
    ];

    this.cols = [
      { field: 'kind', header: 'Kind' },
      { field: 'label', header: 'Label' },
      { field: 'description', header: 'Description' },
      { field: 'dependOf', header: 'Depend Of' }
    ];
  }

  loadAll() {
    this.analysisTaskService.getMetricProviders().subscribe(
      (resp) => {
        this.metricProviders = resp as any[];
        //this.groupByKind = this.sortByGroup(this.metricProviders);
        console.log(this.metricProviders)
      },
      (error) => {
        this.onError(error);
      })
  }

  onRowSelect(event){
    debugger
    this.selectDependencies(event.data);
    console.log(this.selectedMetricProviders)
    this.selectedMetricProviders = this.selectedMetricProviders;

  }

  selectDependencies(ownerMP : MetricProviders){
    for( let obj of ownerMP.dependOf){
      let mp = this.metricProviders.find(mp=> mp.metricProviderId == obj.metricProviderId);
      this.selectedMetricProviders.push(mp);  
      this.selectDependencies(mp)
    }
  }

  onRowUnselect(event) {
    debugger;
    let dependencies : MetricProviders []  = this.serchInversDependency(event.data);

    let newselectedMetricProviders : MetricProviders [] = [];
    for(let oldMP of this.selectedMetricProviders){
     if(dependencies.find(mp =>mp.metricProviderId == oldMP.metricProviderId) == undefined){
      newselectedMetricProviders.push(oldMP);
     }
    }
    this.selectedMetricProviders = newselectedMetricProviders;

    console.log(this.selectedMetricProviders)
  }

  serchInversDependency(ownerMP: MetricProviders) :MetricProviders []  {
    let dependencies : MetricProviders []  = [];
    for(let mp of this.metricProviders){
      let findMp : MetricProviders    = mp.dependOf.find(fmp=> fmp.metricProviderId == ownerMP.metricProviderId);
      
      if(findMp != undefined){

        dependencies.push(mp);
         for(let submp of this.serchInversDependency(mp)){
           dependencies.push(submp);
         }   
      }
    }
    return dependencies;
  }

  sortByGroup(metricProviders: MetricProviders[]){
    let groupByKind = {};
    metricProviders.forEach((a) => {
      groupByKind[a.kind] = groupByKind[a.kind] || [];
      groupByKind[a.kind].push({'metricProviderId': a.metricProviderId, 'label': a.label, 'description': a.description, 'kind': a.kind, 'dependOf': a.dependOf})      
    })
    return groupByKind;
  }

  save(login) {
  }

  previousState() {
    this.route.paramMap.subscribe(data => {
      console.log(data)
      this.router.navigate(['project/configure/' + data.get('id')]);
    });
  }

  isStringArray(value: any): boolean {
    if (Object.prototype.toString.call(value) === '[object Array]') {
       if (value.length < 1) {
         return false;
       } else {
         return true;
       }
    }
    return false;
  }

  onError(error) {
    console.log(error);
  }

}
