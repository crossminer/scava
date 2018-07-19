import { Injectable } from '@angular/core';
import { CanActivate, Router, ActivatedRouteSnapshot } from '@angular/router';
import { LocalStorageService } from '../services/authentication/local-storage.service';

@Injectable({
  providedIn: 'root'
})
export class RoleGuard implements CanActivate {

  private roles: any;

  constructor(
    private router: Router,
    private localStorageService: LocalStorageService
  ) { }

  canActivate(route: ActivatedRouteSnapshot): boolean {
    const authorities = route.data['authorities'];
    let roles: Array<string> = this.localStorageService.getUserRoles();
    if (roles != null && authorities.some(role => roles.includes(role))) {
        return true;
    }
    this.router.navigate(['/home']);
    return false;
  }
}
