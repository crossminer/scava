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
  
  constructor(private listWorkerService: ListWorkerService) { }

  ngOnInit() {
    // this.listWorkerService.getWorkers().subscribe((resp) => {
    //     this.workerList = resp;
    //   }
 // );
     this.workerList = this.listWorkerService.getWorkers();
   
  }
}
