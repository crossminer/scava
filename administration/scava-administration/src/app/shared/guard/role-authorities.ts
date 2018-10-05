import { Injectable } from '@angular/core';
import { LocalStorageService } from '../services/authentication/local-storage.service';
import { JwtHelperService } from '@auth0/angular-jwt';

@Injectable({
  providedIn: 'root'
})
export class RoleAuthorities {

  private jwtHelper: JwtHelperService

  constructor(
    private localStorageService: LocalStorageService,
  ) {
    this.jwtHelper = new JwtHelperService();
  }

  hasRole(role: string) {
    let jwtToken: string = this.localStorageService.loadToken();
    if (jwtToken != null) {
      let roles = this.jwtHelper.decodeToken(jwtToken);
      for (let r of roles.authorities) {
        if (r == role)
          return true;
      }
    }
    return false;
  }

  hasAdminRoleOnly() {
    let jwtToken: string = this.localStorageService.loadToken();
    if (jwtToken != null) {
      let roles = this.jwtHelper.decodeToken(jwtToken);
      if (roles.authorities.includes('ROLE_ADMIN') && roles.sub == 'admin') {
        return true;
      }
    }
    return false;
  }

  showCommands(): boolean {
    const authorities = ['ROLE_ADMIN', 'ROLE_PROJECT_MANAGER'];
    let roles: Array<string> = this.localStorageService.getUserRoles();
    if (roles != null && authorities.some(role => roles.includes(role))) {
      return true;
    } else {
      return false;
    }
  }
}
