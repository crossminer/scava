import { Injectable } from '@angular/core';
import { environment } from '../../../../environments/environment.prod';
import { LocalStorageService } from '../authentication/local-storage.service';
import { HttpClient, HttpResponse, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ConfigService } from '../configuration/configuration-service';

@Injectable({
  providedIn: 'root'
})
export class AccountService {

  private resourceUrl: string =  '/api';
  private account: string = 'account';
  private jwtToken: string = null;
  
  constructor(
    private httpClient: HttpClient,
    private localStorageService: LocalStorageService,
    private configService: ConfigService
  ) { }

  save(settingsAccount: any): Observable<Object> {
    if(this.jwtToken == null) {
      this.jwtToken = this.localStorageService.loadToken();
    }
    return this.httpClient.post(`${this.configService.getSavedServerPath() +  this.resourceUrl}/${this.account}`, settingsAccount, { 
      headers: new HttpHeaders({ 
        'Authorization': this.jwtToken 
      }) 
    });
  }

}
