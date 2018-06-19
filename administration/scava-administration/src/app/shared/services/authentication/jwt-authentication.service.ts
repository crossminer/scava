import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class JwtAuthenticationService {

  constructor() { }

  saveToken(token: string) {
    localStorage.setItem('jwtToken', token);
  }

  loadToken() {
    return localStorage.getItem('jwtToken');
  }
}
