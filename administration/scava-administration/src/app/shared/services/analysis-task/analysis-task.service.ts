import { Injectable } from '@angular/core';
import { environment } from '../../../../environments/environment.prod';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { LocalStorageService } from '../authentication/local-storage.service';
import { THROW_IF_NOT_FOUND } from '@angular/core/src/di/injector';
import { Observable } from 'rxjs';
import { ExecutionTask } from '../../../layout/project/components/configure-project/execution-task.model';

@Injectable({
  providedIn: 'root'
})
export class AnalysisTaskService {

  private resourceUrl = environment.SERVER_API_URL + '/administration';
  private analysis = 'analysis';
  private tasks = 'tasks';
  private project = 'project'
  private jwtToken: string = null;

  constructor(
    private httpClient: HttpClient,
    private localStorageService: LocalStorageService
  ) { }

  getTasks(): Observable<ExecutionTask> {
      if (this.jwtToken == null) {
          this.jwtToken = this.localStorageService.loadToken();
      }
      return this.httpClient.get(`${this.resourceUrl}/${this.analysis}/${this.tasks}`,
          { headers: new HttpHeaders({ 'Authorization': this.jwtToken })});
  }

  getTasksbyProject(projectId: string): Observable<ExecutionTask> {
    if (this.jwtToken == null) {
      this.jwtToken = this.localStorageService.loadToken();
    }
    return this.httpClient.get(`${this.resourceUrl}/${this.analysis}/${this.tasks}/${this.project}/${projectId}`,
      { headers: new HttpHeaders({ 'Authorization': this.jwtToken }) });
  }

}
