import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { ConfigService } from '../../shared/services/configuration/configuration-service';

@Injectable({
  providedIn: 'root'
})
export class ActivateService {
  
  private resourceUrl: string = '/api'
  private activate: string = 'activate';

  constructor(
    private httpClient: HttpClient,
    private configService: ConfigService
  ) { }

  activateAccount(key: string): Observable<any> {
    return this.httpClient.get(`${this.configService.getSavedServerPath() + this.resourceUrl}/${this.activate}`, 
      { params: new HttpParams().set('key', key)});
  }
}
