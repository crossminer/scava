import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  private resourceUrl = environment.SERVER_API_URL + '/api';
  private authentication = 'authentication';

  constructor(
    private httpClient: HttpClient
  ) {
  }

  login(data) {
    return this.httpClient.post(`${this.resourceUrl}/${this.authentication}`, data, { observe: 'response' });
  }

}
