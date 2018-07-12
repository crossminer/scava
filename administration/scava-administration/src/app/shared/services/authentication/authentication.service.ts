import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../../environments/environment';
import { LocalStorageService } from './local-storage.service';
import { JwtHelper } from 'angular2-jwt'

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  private resourceUrl = environment.SERVER_API_URL + '/api';
  private authentication = 'authentication';
  private jwtHelper: JwtHelper;
  private roles: any;

  constructor(
    private httpClient: HttpClient,
    private localStorageService: LocalStorageService
  ) {
    this.jwtHelper = new JwtHelper();
  }

  login(data) {
    return this.httpClient.post(`${this.resourceUrl}/${this.authentication}`, data, { observe: 'response' });
  }

  hasAdminRole() {
    let jwtToken: string = this.localStorageService.loadToken();
    if (jwtToken !== null) {
      this.roles = this.jwtHelper.decodeToken(jwtToken);
      for (let role of this.roles.authorities) {
        if (role == "ROLE_ADMIN")
          return true;
      }
      return false;
    }
    return false;
  }

  getUsername() {
    let jwtToken: string = this.localStorageService.loadToken();
    if (jwtToken !== null) {
      return this.jwtHelper.decodeToken(jwtToken).sub;
    }
    return;
  }

}
