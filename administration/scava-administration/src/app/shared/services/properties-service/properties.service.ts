import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { LocalStorageService } from '../authentication/local-storage.service';
import { ConfigService } from '../configuration/configuration-service';
import { Properties } from '../../../layout/properties/properties.model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PropertiesService {

  private resourceUrl = '/administration';
  private platform = 'platform';
  private properties = 'properties';
  private create = 'create';
  private update = 'update';
  private delete = 'delete';
  private jwtToken: string = null;

  constructor(
    public httpClient: HttpClient,
    private localStorageService: LocalStorageService,
    private configService: ConfigService
  ) { }

  createProperties(properties: Properties): Observable<Properties>{
    if (this.jwtToken == null) {
      this.jwtToken = this.localStorageService.loadToken();
    }
    return this.httpClient.post(`${this.configService.getSavedServerPath() + this.resourceUrl}/${this.platform}/${this.properties}/${this.create}`, properties,
    { headers: new HttpHeaders({ 'Authorization': this.jwtToken }) });
  }

  updateProperties(properties: Properties): Observable<Properties>{
    if (this.jwtToken == null) {
      this.jwtToken = this.localStorageService.loadToken();
    }
    return this.httpClient.put(`${this.configService.getSavedServerPath() + this.resourceUrl}/${this.platform}/${this.properties}/${this.update}`, properties,
    { headers: new HttpHeaders({ 'Authorization': this.jwtToken }) });
  }

  getProperties(): Observable<Properties>{
    if (this.jwtToken == null) {
      this.jwtToken = this.localStorageService.loadToken();
    }
    return this.httpClient.get(`${this.configService.getSavedServerPath() + this.resourceUrl}/${this.platform}/${this.properties}`,
    { headers: new HttpHeaders({ 'Authorization': this.jwtToken }) });
  }

  getPropertiesByKey(key: string): Observable<Properties>{
    if (this.jwtToken == null) {
      this.jwtToken = this.localStorageService.loadToken();
    }
    return this.httpClient.get(`${this.configService.getSavedServerPath() + this.resourceUrl}/${this.platform}/${this.properties}/${key}`,
    { headers: new HttpHeaders({ 'Authorization': this.jwtToken }) });
  }

  deleteProperties(key: String): Observable<Properties>{
    if (this.jwtToken == null) {
      this.jwtToken = this.localStorageService.loadToken();
    }
    return this.httpClient.delete(`${this.configService.getSavedServerPath() + this.resourceUrl}/${this.platform}/${this.properties}/${this.delete}/${key}`,
    { headers: new HttpHeaders({ 'Authorization': this.jwtToken }) });
  }
}
