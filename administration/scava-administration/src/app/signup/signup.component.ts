import { Component, OnInit } from '@angular/core';
import { routerTransition } from '../router.animations';
import { SignupService } from './signup.service';
import { Router } from '@angular/router';

@Component({
    selector: 'app-signup',
    templateUrl: './signup.component.html',
    styleUrls: ['./signup.component.scss'],
    animations: [routerTransition()]
})
export class SignupComponent implements OnInit {

    private success: boolean = false;
    private 

    constructor(
        private router: Router,
        private signupService: SignupService
    ) {}

    ngOnInit() {}

    register(data) {
        this.signupService.onSignedup(data).subscribe(
            (resp) => {
                this.success = true;
                console.log(this.success);
            },
            (error) => {
                this.success = false;
                console.log(this.success)
            }
        )
        this.router.navigateByUrl('/signup')

    }
}
