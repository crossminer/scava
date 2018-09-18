import { Component, OnInit, OnDestroy } from '@angular/core';
import { ListWorkerService } from '../../shared/services/worker-service/list-worker.service';
import { AnalysisTaskService } from '../../shared/services/analysis-task/analysis-task.service';
import { ExecutionTask } from '../project/components/configure-project/execution-task.model';
import { Worker } from './worker.model';
import { ResponseWrapper } from '../../shared/models/response-wrapper.model';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { MetricInfoComponent } from './metrics-infos/metric-info.component';


@Component({
  selector: 'app-worker',
  templateUrl: './worker.component.html',
  styleUrls: ['./worker.component.scss']
})

export class WorkerComponent implements OnInit,OnDestroy {

  workerList: Worker[];
  interval : any;
  taskList:ExecutionTask[];
 
  constructor(
    private listWorkerService: ListWorkerService,
    private analysisTaskService : AnalysisTaskService,
    public modalService: NgbModal
  ) { }

  ngOnInit() {
    this.refreshData();
    this.interval = setInterval(() => { 
      this.refreshData(); 
    }, 1500);

  }

  refreshData(){
    this.listWorkerService.getWorkers().subscribe((resp) => {
      this.workerList = resp as Worker[];
    });
    this.analysisTaskService.getTasks().subscribe((resp) => {
      let allTasks = resp as ExecutionTask[];
      this.taskList = [];
      for(let task of allTasks){
        if(task.scheduling.status == 'PENDING_EXECUTION'){
          this.taskList.push(task);
        }    
      }
    });
  }

  setProgressStyles(worker:any){
    let styles = {
      'width': worker.currentTask.scheduling[0].progress +'%',
    };

    return styles;
   }

   computeTime(task:any){
    let estimatedTime : number = task.scheduling[0].lastDailyExecutionDuration;
    return Math.round(estimatedTime / 1000) + 's';
   }

   stopTask(analysisTaskId: string) {
    this.analysisTaskService.stopTask(analysisTaskId).subscribe(
        (resp) => {
            this.refreshData();
        }, 
        (error) => {
            this.onShowMessage('stop failed')
        })
   }

   promoteTask(analysisTaskId: string) {
    this.analysisTaskService.promoteTask(analysisTaskId).subscribe(
      (resp) => {
          this.onShowMessage('promote successed !');
          this.refreshData();
      }, 
      (error) => {
          this.onShowMessage('promote failed')
      })
   }

   demoteTask(analysisTaskId: string) {
    this.analysisTaskService.demoteTask(analysisTaskId).subscribe(
      (resp) => {
          this.onShowMessage('emote successed !');
          this.refreshData();
      }, 
      (error) => {
          this.onShowMessage('emote failed')
      })
   }

   pushOnWorker(analysisTaskId: string,wotkerId : string) {
    this.analysisTaskService.pushOnWorker(analysisTaskId,wotkerId).subscribe(
      (resp) => {
          this.onShowMessage('pushOnWorker successed !');
          this.refreshData();
      }, 
      (error) => {
          this.onShowMessage('pushOnWorker failed')
      })
   }

   showMetricProviderList(analysisTask: ExecutionTask) {
    const modalRef = this.modalService.open(MetricInfoComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.analysisTask = analysisTask;
  }

  ngOnDestroy() {
    if (this.interval) {
      clearInterval(this.interval);
    }
  }

  onShowMessage(msg: any) {
    console.log(msg);
  }

}
