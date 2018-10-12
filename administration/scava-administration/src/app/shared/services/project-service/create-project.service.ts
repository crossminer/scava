import { Injectable } from '@angular/core';
import { environment } from '../../../../environments/environment';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { IProject } from '../../../layout/project/project.model';
import { LocalStorageService } from '../authentication/local-storage.service';
import { ConfigService } from '../configuration/configuration-service';

@Injectable({
  providedIn: 'root'
})
export class CreateProjectService {

  private resourceUrl = '/administration';
  private projects = 'projects';
  private create = 'create';
  private jwtToken: string = null;
    
  constructor(
    private httpClient: HttpClient,
    private localStorageService: LocalStorageService,
    private configService: ConfigService
  ) { }

  createProject(project: IProject) {
    if (this.jwtToken == null) {
      this.jwtToken = this.localStorageService.loadToken();
    }
    return this.httpClient.post(`${this.configService.getSavedServerPath() +  this.resourceUrl}/${this.projects}/${this.create}`, project,
    { headers: new HttpHeaders({ 'Authorization': this.jwtToken }) });
  }
}
