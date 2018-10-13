import { Injectable } from '@angular/core';
import { environment } from '../../../../environments/environment';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { LocalStorageService } from '../authentication/local-storage.service';
import { ConfigService } from '../configuration/configuration-service';

@Injectable({
  providedIn: 'root'
})
export class PasswordService {

  private resourceUrl: string = "/api";
  private account: string = "account";
  private password: string = "change-password";
  private jwtToken: string = null;

  constructor(
    private httpClient: HttpClient,
    private localStorageService: LocalStorageService,
    private configService: ConfigService
  ) { }

  change(currentLogin: string, currentPassword: string, newPassword: string) {
    if (this.jwtToken == null) {
      this.jwtToken = this.localStorageService.loadToken();
    }
    return this.httpClient.post(`${this.configService.getSavedServerPath() +  this.resourceUrl}/${this.account}/${this.password}`,
    {
      "currentLogin": currentLogin,
      "currentPassword": currentPassword,
      "newPassword": newPassword
    },
    { 
      headers: new HttpHeaders({ 
        'Authorization': this.jwtToken
      }) 
    });
  }
}
