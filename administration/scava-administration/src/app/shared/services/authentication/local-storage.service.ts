import { Injectable } from '@angular/core';
import { JwtHelperService } from '@auth0/angular-jwt';

@Injectable({
  providedIn: 'root'
})
export class LocalStorageService {

  private jwtHelper: JwtHelperService;
  
  constructor() {
    this.jwtHelper = new JwtHelperService();
   }

  saveLoginStatus(status: string) {
    localStorage.setItem('isLoggedin', status);
  }
  
  loadLoginStatus() {
    return localStorage.getItem('isLoggedin');
  }

  saveToken(token: string) {
    localStorage.setItem('jwtToken', token);
  }

  loadToken() {
    return localStorage.getItem('jwtToken');
  }

  getUserRoles() {
    let jwtToken: string = this.loadToken();
    if(jwtToken != null) {
      let roles: any = this.jwtHelper.decodeToken(jwtToken);
      return roles.authorities;
    }
  }

  getUsername() {
    let jwtToken: string = this.loadToken();
    if (jwtToken !== null) {
      return this.jwtHelper.decodeToken(jwtToken).sub;
    }
    return;
  }

}
