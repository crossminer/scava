import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class LocalStorageService {

  constructor() { }

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

}
