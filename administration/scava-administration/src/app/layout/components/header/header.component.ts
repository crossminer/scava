import { Component, OnInit } from '@angular/core';
import { Router, NavigationEnd } from '@angular/router';
import { LocalStorageService } from '../../../shared/services/authentication/local-storage.service';
import { RoleAuthorities } from '../../../shared/guard/role-authorities';

@Component({
    selector: 'app-header',
    templateUrl: './header.component.html',
    styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {
    
    username: any;
    private pushRightClass: string = 'push-right';

    constructor(
        private router: Router,
        private localStorageService: LocalStorageService,
        public roleAuthorities: RoleAuthorities
    ) {
        this.router.events.subscribe(val => {
            if (
                val instanceof NavigationEnd &&
                window.innerWidth <= 992 &&
                this.isToggled()
            ) {
                this.toggleSidebar();
            }
        });
    }

    ngOnInit() {
        if(this.username == null) {
            this.username = this.localStorageService.getUsername();
        }
    }

    isToggled(): boolean {
        const dom: Element = document.querySelector('body');
        return dom.classList.contains(this.pushRightClass);
    }

    toggleSidebar() {
        const dom: any = document.querySelector('body');
        dom.classList.toggle(this.pushRightClass);
    }

    rltAndLtr() {
        const dom: any = document.querySelector('body');
        dom.classList.toggle('rtl');
    }

    onLoggedout() {
        localStorage.removeItem("isLoggedin");
        localStorage.removeItem("jwtToken")
    }
}
