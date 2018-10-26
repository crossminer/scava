import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from '../../../../environments/environment';
import { LocalStorageService } from '../authentication/local-storage.service';
import { Worker } from '../../../layout/worker/worker.model';
import { ExecutionTask } from '../../../layout/project/components/configure-project/execution-task.model';
import { ConfigService } from '../configuration/configuration-service';

@Injectable({
    providedIn: 'root'
})
export class ListWorkerService {

    private administration = 'administration';
    private serviceUrl = 'analysis/workers';
    private jwtToken: string = null;

    constructor(
        private httpClient: HttpClient,
        private localStorageService: LocalStorageService,
        private configService: ConfigService
    ) { }

    getWorkers() {
        if (this.jwtToken == null) {
            this.jwtToken = this.localStorageService.loadToken();
        }
        return this.httpClient.get(`${this.configService.getSavedServerPath()}/${this.administration}/${this.serviceUrl}`,
            { headers: new HttpHeaders({ 'Authorization': this.jwtToken })});
    } 
}
