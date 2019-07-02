import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { IProject } from '../../../layout/project/project.model';
import { LocalStorageService } from '../authentication/local-storage.service';
import { ConfigService } from '../configuration/configuration-service';
import { RoleAuthorities } from '../authentication/role-authorities';

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
    private configService: ConfigService,
    private roleAuthorities: RoleAuthorities
  ) { }

  createProject(project: IProject) {
    if (this.jwtToken == null  || this.roleAuthorities.tokenExpired(this.jwtToken)) {
      this.jwtToken = this.localStorageService.loadToken();
    }
    return this.httpClient.post(`${this.configService.getSavedServerPath() +  this.resourceUrl}/${this.projects}/${this.create}`, project,
    { headers: new HttpHeaders({ 'Authorization': this.jwtToken }) });
  }
  
}
