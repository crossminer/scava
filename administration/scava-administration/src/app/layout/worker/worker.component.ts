import { Component, OnInit } from '@angular/core';
import { ListWorkerService } from '../../shared/services/worker-service/list-worker.service';
import { AnalysisTaskService } from '../../shared/services/analysis-task/analysis-task.service';
import { ExecutionTask } from '../project/components/configure-project/execution-task.model';
import { Worker } from './worker.model';
import { ResponseWrapper } from '../../shared/models/response-wrapper.model';

@Component({
  selector: 'app-worker',
  templateUrl: './worker.component.html',
  styleUrls: ['./worker.component.scss']
})

export class WorkerComponent implements OnInit {

  workerList: any;
  interval : any;
  taskList:ExecutionTask[];


  constructor(private listWorkerService: ListWorkerService,private analysisTaskService : AnalysisTaskService) { }

  ngOnInit() {
    this.refreshData();
    this.interval = setInterval(() => { 
      this.refreshData(); 
    }, 5000);

  }

  refreshData(){
    this.listWorkerService.getWorkers().subscribe((resp) => {
      this.workerList = resp;
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
}
