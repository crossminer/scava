import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LocalStorageService } from '../../shared/services/authentication/local-storage.service';
import { AuthenticationService } from '../../shared/services/authentication/authentication.service';

@Component({
    selector: 'app-login',
    templateUrl: './login.component.html',
    styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

    mode: Number = 0;
    username: string;
    password: string;

    constructor(
        private router: Router,
        private localStorageService: LocalStorageService,
        private authenticationService: AuthenticationService
    ) { }

    ngOnInit() {
    }

    onLoggedin(data) {
        this.authenticationService.login(data).subscribe(
            (resp) => {
                const jwtToken = resp.headers.get('Authorization');
                this.localStorageService.saveToken(jwtToken);
                this.localStorageService.saveLoginStatus('true');
                this.router.navigateByUrl('/home');
            }, (error) => {
                this.mode = 1;
            }
        );
    }

}
