import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from '../../../../environments/environment';
import { LocalStorageService } from '../authentication/local-storage.service';
import { Worker } from '../../../layout/worker/worker.model';
import { ExecutionTask } from '../../../layout/project/components/configure-project/execution-task.model';

@Injectable({
    providedIn: 'root'
})
export class ListWorkerService {

    private resourceUrl = environment.SERVER_API_URL + '/administration';
    private serviceUrl = '';
    private jwtToken: string = null;
    private worker: Worker;

    constructor(
        private httpClient: HttpClient,
        private localStorageService: LocalStorageService
    ) { }

    getWorkers() {
 /**       if (this.jwtToken == null) {
            this.jwtToken = this.localStorageService.loadToken();
        }
        return this.httpClient.get(`${this.resourceUrl}/${this.serviceUrl}`,
            { headers: new HttpHeaders({ 'Authorization': this.jwtToken })}); **/
            let task = new ExecutionTask();
            task.analysisTaskId = "Task1";
            let worker1 = new Worker("work1",task);
            let worker2 = new Worker("work2",task);
            return [worker1,worker2];
    } 
}
