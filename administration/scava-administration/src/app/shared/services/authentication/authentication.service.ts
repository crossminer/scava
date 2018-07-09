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
    this.roles = this.jwtHelper.decodeToken(this.localStorageService.loadToken());
    // console.log(this.roles)
    for (let role of this.roles.authorities) {
      if (role == "ROLE_ADMIN")
        return true;
    }
    return false;
  }

  getUsername() {
    return this.jwtHelper.decodeToken(this.localStorageService.loadToken()).sub;
  }

}
