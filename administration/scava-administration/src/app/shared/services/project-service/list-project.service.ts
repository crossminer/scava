import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { LocalStorageService } from '../authentication/local-storage.service';
import { ConfigService } from '../configuration/configuration-service';
import { RoleAuthorities } from '../authentication/role-authorities';

@Injectable({
    providedIn: 'root'
})
export class ListProjectService {

    private resourceUrl = '/administration';
    private listServiceUrl = 'projects';
    private projectServiceUrl = 'projects/p';
    private jwtToken: string;

    constructor(
        private httpClient: HttpClient,
        private localStorageService: LocalStorageService,
        private configService: ConfigService,
        private roleAuthorities: RoleAuthorities
    ) {
        this.jwtToken = null;
     }

    listProjects() {
        if (this.jwtToken == null || this.roleAuthorities.tokenExpired(this.jwtToken)) {
            this.jwtToken = this.localStorageService.loadToken();
        }
        return this.httpClient.get(`${this.configService.getSavedServerPath() +  this.resourceUrl}/${this.listServiceUrl}`,
            { headers: new HttpHeaders({ 'Authorization': this.jwtToken })});
    }

    getProject(projectid:string){
        if (this.jwtToken == null || this.roleAuthorities.tokenExpired(this.jwtToken)) {
            this.jwtToken = this.localStorageService.loadToken();
        } 
        return this.httpClient.get(`${this.configService.getSavedServerPath() +  this.resourceUrl}/${this.projectServiceUrl}/${projectid}`, { headers: new HttpHeaders({ 'Authorization': this.jwtToken })});
    }

}
