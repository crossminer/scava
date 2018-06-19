import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Project } from '../../../layout/project/project.model';
import { environment } from '../../../../environments/environment';
import { JwtAuthenticationService } from '../authentication/jwt-authentication.service';

@Injectable({
    providedIn: 'root'
})
export class ImportProjectService {

    private resourceUrl = environment.SERVER_API_URL + '/administration';
    private projects = 'projects';
    private import = 'import';
    private jwtToken: string = null;

    constructor(
        private httpClient: HttpClient,
        private jwtAuthenticationService: JwtAuthenticationService
    ) { }

    importProject(project: Project) {
        if (this.jwtToken == null) {
            this.jwtToken = this.jwtAuthenticationService.loadToken();
        }
        return this.httpClient.post(`${this.resourceUrl}/${this.projects}/${this.import}`, project,
            { headers: new HttpHeaders({ 'Authorization': this.jwtToken }) });
    }
}
