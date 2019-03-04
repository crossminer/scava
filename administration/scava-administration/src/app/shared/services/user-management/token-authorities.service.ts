import { Injectable } from '@angular/core';
import { ConfigService } from '../configuration/configuration-service';
import { LocalStorageService } from '../authentication/local-storage.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { TokenAuthorities } from '../../../layout/profile/token-authorities.model';
import { RoleAuthorities } from '../authentication/role-authorities';

@Injectable({
  providedIn: 'root'
})
export class TokenAuthoritiesService {

  private resourceUrl: string = "/api";
  private tokenAuthorities: string = "token-authorities";
  private generateToken: string = "generate-token";
  private jwtToken: string = null;

  constructor(
    private httpClient: HttpClient,
    private localStorageService: LocalStorageService,
    private configService: ConfigService,
    private roleAuthorities: RoleAuthorities
  ) { }

  generateTokenAuthorities(tokenAuthorites: TokenAuthorities): Observable<Object> {
    if(this.jwtToken == null || this.roleAuthorities.tokenExpired(this.jwtToken)) {
      this.jwtToken = this.localStorageService.loadToken();
    }
    return this.httpClient.post(`${this.configService.getSavedServerPath() +  this.resourceUrl}/${this.tokenAuthorities}/${this.generateToken}`, tokenAuthorites, { 
      headers: new HttpHeaders({ 
        'Authorization': this.jwtToken
      })
    });
  }

  getAllTokenAuthorities(): Observable<Object> {
    if(this.jwtToken == null || this.roleAuthorities.tokenExpired(this.jwtToken)) {
      this.jwtToken = this.localStorageService.loadToken();
    }
    return this.httpClient.get(`${this.configService.getSavedServerPath() +  this.resourceUrl}/${this.tokenAuthorities}`, { 
      headers: new HttpHeaders({ 
        'Authorization': this.jwtToken
      })
    });
  }

  getTokenAuthoritiesByLabel(label: string): Observable<Object> {
    if(this.jwtToken == null || this.roleAuthorities.tokenExpired(this.jwtToken)) {
      this.jwtToken = this.localStorageService.loadToken();
    }
    return this.httpClient.get(`${this.configService.getSavedServerPath() +  this.resourceUrl}/${this.tokenAuthorities}/${label}`, { 
      headers: new HttpHeaders({ 
        'Authorization': this.jwtToken
      })
    });
  }


  deleteTokenAuthorities(label: string): Observable<Object> {
    if(this.jwtToken == null || this.roleAuthorities.tokenExpired(this.jwtToken)) {
      this.jwtToken = this.localStorageService.loadToken();
    }
    return this.httpClient.delete(`${this.configService.getSavedServerPath() +  this.resourceUrl}/${this.tokenAuthorities}/${label}`, { 
      headers: new HttpHeaders({ 
        'Authorization': this.jwtToken
      })
    });
  }

}
