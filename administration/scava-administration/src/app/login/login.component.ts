import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { routerTransition } from '../router.animations';
import { AuthenticationService } from '../shared/services/authentication/authentication.service';
import { LocalStorageService } from '../shared/services/authentication/local-storage.service';

@Component({
    selector: 'app-login',
    templateUrl: './login.component.html',
    styleUrls: ['./login.component.scss'],
    animations: [routerTransition()]
})
export class LoginComponent implements OnInit {

    private mode: Number = 0;

    constructor(
        private router: Router,
        private localStorageService: LocalStorageService,
        private authenticationService: AuthenticationService
    ) {}

    ngOnInit() {
    }

    onLoggedin(data) {
        this.authenticationService.login(data).subscribe(
            (resp) => {
                const jwtToken = resp.headers.get('Authorization');
                // console.log(jwtToken);
                // localStorage.clear();
                this.localStorageService.saveToken(jwtToken);
                //localStorage.setItem('isLoggedin', 'true');
                this.localStorageService.saveLoginStatus('true');
                //sessionStorage.setItem('username', data.username);
                this.localStorageService.saveUsername(data.username);
                this.router.navigateByUrl('/home');
            }, (error) => {
                this.mode = 1;
            }
        );
    }

}
