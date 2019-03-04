import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot } from '@angular/router';
import { RoleAuthorities } from '../services/authentication/role-authorities';

@Injectable()
export class JwtTokenGuard implements CanActivate {

    constructor(
        private roleAuthorities: RoleAuthorities
    ) {}

    canActivate(route: ActivatedRouteSnapshot): boolean {
        return !this.roleAuthorities.isCurrentTokenExpired();
    }

}