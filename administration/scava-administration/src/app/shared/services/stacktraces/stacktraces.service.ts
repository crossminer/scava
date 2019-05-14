import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { LocalStorageService } from '../authentication/local-storage.service';
import { Observable } from 'rxjs';
import { Stacktraces } from '../../../layout/stacktraces/stacktraces.model';
import { ConfigService } from '../configuration/configuration-service';
import { RoleAuthorities } from '../authentication/role-authorities';

@Injectable({
  providedIn: 'root'
})
export class StacktracesService {

  private administration: string = "administration";
  private analysis: string = 'analysis';
  private stacktraces: string = 'stacktraces';
  private jwtToken: string = null;

  constructor(
    private httpClient: HttpClient,
    private localStorageService: LocalStorageService,
    private configService: ConfigService,
    private roleAuthorities: RoleAuthorities
  ) { }

  getStackTraces(): Observable<Stacktraces> {
    if (this.jwtToken == null || this.roleAuthorities.tokenExpired(this.jwtToken)) {
      this.jwtToken = this.localStorageService.loadToken();
    }
    return this.httpClient.get(`${this.configService.getSavedServerPath()}/${this.administration}/${this.analysis}/${this.stacktraces}`,
      { headers: new HttpHeaders({ 'Authorization': this.jwtToken }) });
  }
}
