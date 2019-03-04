import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { LocalStorageService } from '../authentication/local-storage.service';
import { ConfigService } from '../configuration/configuration-service';
import { RoleAuthorities } from '../authentication/role-authorities';

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
        private configService: ConfigService,
        private roleAuthorities: RoleAuthorities
    ) { }

    getWorkers() {
        if (this.jwtToken == null  || this.roleAuthorities.tokenExpired(this.jwtToken)|| this.roleAuthorities.tokenExpired(this.jwtToken)) {
            this.jwtToken = this.localStorageService.loadToken();
        }
        return this.httpClient.get(`${this.configService.getSavedServerPath()}/${this.administration}/${this.serviceUrl}`,
            { headers: new HttpHeaders({ 'Authorization': this.jwtToken })});
    } 
}
