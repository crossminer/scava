import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../../environments/environment';
import { ConfigService } from '../configuration/configuration-service';

@Injectable({
  providedIn: 'root'
})
export class SignupService {

  private resourceUrl: string ='/api';
  private register = 'register';

  constructor(
    private httpClient: HttpClient,
    private configService: ConfigService
  ) { }

  onSignedup (data) {
    return this.httpClient.post(`${this.configService.getSavedServerPath() + this.resourceUrl}/${this.register}`, data, {observe: 'response'});
  }

}
