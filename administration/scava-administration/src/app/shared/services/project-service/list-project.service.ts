import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from '../../../../environments/environment';
import { LocalStorageService } from '../authentication/local-storage.service';
import { Project } from '../../../layout/project/project.model';

@Injectable({
    providedIn: 'root'
})
export class ListProjectService {

    private resourceUrl = environment.SERVER_API_URL + '/administration';
    private listServiceUrl = 'projects';
    private projectServiceUrl = 'projects/p';
    private jwtToken: string = null;
    private project: Project;

    constructor(
        private httpClient: HttpClient,
        private localStorageService: LocalStorageService
    ) { }

    listProjects() {
        if (this.jwtToken == null) {
            this.jwtToken = this.localStorageService.loadToken();
        }
        return this.httpClient.get(`${this.resourceUrl}/${this.listServiceUrl}`,
            { headers: new HttpHeaders({ 'Authorization': this.jwtToken })});
    }

    getProject(projectid:string){
        if (this.jwtToken == null) {
            this.jwtToken = this.localStorageService.loadToken();
        } 
        return this.httpClient.get(`${this.resourceUrl}/${this.projectServiceUrl}/${projectid}`, { headers: new HttpHeaders({ 'Authorization': this.jwtToken })});
    }

}
