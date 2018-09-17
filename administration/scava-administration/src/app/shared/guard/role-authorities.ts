import { Injectable } from '@angular/core';
import { LocalStorageService } from '../services/authentication/local-storage.service';

@Injectable({
  providedIn: 'root'
})
export class RoleAuthorities {

  constructor(
    public localStorageService: LocalStorageService
  ) { }

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
