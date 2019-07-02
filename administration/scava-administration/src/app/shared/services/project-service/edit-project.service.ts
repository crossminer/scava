import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { LocalStorageService } from '../authentication/local-storage.service';
import { ConfigService } from '../configuration/configuration-service';
import { RoleAuthorities } from '../authentication/role-authorities';

@Injectable({
  providedIn: 'root'
})
export class EditProjectService {

  private resourceUrl = '/administration';
  private projects = 'projects';
  private edit = 'edit';
  private jwtToken: string = null;
    
  constructor(
    private httpClient: HttpClient,
    private localStorageService: LocalStorageService,
    private configService: ConfigService,
    private roleAuthorities: RoleAuthorities
  ) { }

  editProject(project: any) {
    if (this.jwtToken == null  || this.roleAuthorities.tokenExpired(this.jwtToken)) {
      this.jwtToken = this.localStorageService.loadToken();
    }
    return this.httpClient.put(`${this.configService.getSavedServerPath() +  this.resourceUrl}/${this.projects}/${this.edit}`, project,
    { headers: new HttpHeaders({ 'Authorization': this.jwtToken }) });
  }
}
