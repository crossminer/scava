import { Injectable } from '@angular/core';
import { environment } from '../../../../environments/environment';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { LocalStorageService } from '../authentication/local-storage.service';

@Injectable({
  providedIn: 'root'
})
export class PasswordService {

  private resourceUrl: string = environment.SERVER_API_URL + "/api";
  private account: string = "account";
  private password: string = "change-password";
  private jwtToken: string = null;

  constructor(
    private httpClient: HttpClient,
    private localStorageService: LocalStorageService
  ) { }

  changePassword(currentPassword: string, newPassword: string) {
    if (this.jwtToken == null) {
      this.jwtToken = this.localStorageService.loadToken();
    }
    return this.httpClient.post('localhost:8086/api/account/change-password', { currentPassword, newPassword },
      {
        headers: new HttpHeaders({
          'Authorization': this.jwtToken
        })
      }
    );
  }
}
