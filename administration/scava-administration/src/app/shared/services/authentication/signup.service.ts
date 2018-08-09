import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class SignupService {

  private resourceUrl: string = environment.SERVER_API_URL + '/api';
  private register = 'register';

  constructor(
    private httpClient: HttpClient
  ) { }

  onSignedup (data) {
    return this.httpClient.post(`${this.resourceUrl}/${this.register}`, data, {observe: 'response'});
  }

}
