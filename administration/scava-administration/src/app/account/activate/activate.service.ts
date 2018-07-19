import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ActivateService {
  
  private resourceUrl: string = environment.SERVER_API_URL + '/api'
  private activate: string = 'activate';

  constructor(
    private httpClient: HttpClient
  ) { }

  activateAccount(key: string): Observable<any> {
    return this.httpClient.get(`${this.resourceUrl}/${this.activate}`, 
      { params: new HttpParams().set('key', key)});
  }
}
