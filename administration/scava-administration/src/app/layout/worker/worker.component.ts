import { Component, OnInit } from '@angular/core';
import { ListWorkerService } from '../../shared/services/worker-service/list-worker.service';
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
  constructor(private listWorkerService: ListWorkerService) { }

  ngOnInit() {
    this.listWorkerService.getWorkers().subscribe((resp) => {
        this.workerList = resp;
    });

    this.interval = setInterval(() => { 
      this.refreshData(); 
    }, 1000);

  }

  refreshData(){
    this.listWorkerService.getWorkers().subscribe((resp) => {
      this.workerList = resp;
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
    return estimatedTime;
   }
}
