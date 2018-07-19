import { Injectable } from '@angular/core';
import { environment } from '../../../../environments/environment.prod';
import { LocalStorageService } from '../authentication/local-storage.service';
import { HttpClient, HttpResponse, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AccountService {

  private resourceUrl: string = environment.SERVER_API_URL + '/api';
  private account: string = 'account';
  private jwtToken: string = null;
  
  constructor(
    private httpClient: HttpClient,
    private localStorageService: LocalStorageService
  ) { }

  save(settingsAccount: any): Observable<Object> {
    if(this.jwtToken == null) {
      this.jwtToken = this.localStorageService.loadToken();
    }
    return this.httpClient.post(`${this.resourceUrl}/${this.account}`, settingsAccount, { 
      headers: new HttpHeaders({ 
        'Authorization': this.jwtToken 
      }) 
    });
  }

}
