import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { JwtAuthenticationService } from '../authentication/jwt-authentication.service';
import { environment } from '../../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  private resourceUrl = environment.SERVER_API_URL;
  private authentication = 'api/authentication';
  private jwtToken: string = null;

  constructor(
      private httpClient: HttpClient,
      private jwtAuthenticationService: JwtAuthenticationService
  ) { }

  login(data) {
      return this.httpClient.post(`${this.resourceUrl}/${this.authentication}`, data, {observe: 'response'});
  }
}
