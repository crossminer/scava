import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Project } from '../../../layout/project/project.model';
import { environment } from '../../../../environments/environment';
import { LocalStorageService } from '../authentication/local-storage.service';
import { ConfigService } from '../configuration/configuration-service';

@Injectable({
    providedIn: 'root'
})
export class ImportProjectService {

    private resourceUrl =  '/administration';
    private projects = 'projects';
    private import = 'import';
    private jwtToken: string = null;

    constructor(
        private httpClient: HttpClient,
        private localStorageService: LocalStorageService,
        private configService: ConfigService
    ) { }

    importProject(project: Project) {
        if (this.jwtToken == null) {
            this.jwtToken = this.localStorageService.loadToken();
        }
        return this.httpClient.post(`${this.configService.getSavedServerPath() + this.resourceUrl}/${this.projects}/${this.import}`, project,
            { headers: new HttpHeaders({ 'Authorization': this.jwtToken }) });
    }
}
