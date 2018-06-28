import { Injectable } from '@angular/core';
import { environment } from '../../../../environments/environment';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Project } from '../../../layout/project/project.model';
import { LocalStorageService } from '../authentication/local-storage.service';

@Injectable({
  providedIn: 'root'
})
export class CreateProjectService {

  private resourceUrl = environment.SERVER_API_URL + '/administration';
  private projects = 'projects';
  private create = 'create';
  private jwtToken: string = null;
    
  constructor(
    private httpClient: HttpClient,
    private localStorageService: LocalStorageService,
  ) { }

  createProject(project: Project) {
    if (this.jwtToken == null) {
      this.jwtToken = this.localStorageService.loadToken();
    }
    // console.log(project);
    return this.httpClient.post(`${this.resourceUrl}/${this.projects}/${this.create}`, project,
    { headers: new HttpHeaders({ 'Authorization': this.jwtToken }) });
  }
}
