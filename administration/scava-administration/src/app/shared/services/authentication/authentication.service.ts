import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../../environments/environment';
import { ConfigService } from '../configuration/configuration-service';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  private resourceUrl = '/api';
  private authentication = 'authentication';

  constructor(
    private httpClient: HttpClient,
    private configService: ConfigService
  ) {
  }

  login(data) {
    return this.httpClient.post(`${this.configService.getSavedServerPath() + this.resourceUrl}/${this.authentication}`, data, { observe: 'response' });
  }

}
