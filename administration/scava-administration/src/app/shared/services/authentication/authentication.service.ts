import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ConfigService } from '../configuration/configuration-service';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  private api = 'api';
  private authentication = 'authentication';

  constructor(
    private httpClient: HttpClient,
    private configService: ConfigService
  ) {
  }

  login(data) {
    return this.httpClient.post(`${this.configService.getSavedServerPath()}/${this.api}/${this.authentication}`, data, { observe: 'response' });
  }

}
