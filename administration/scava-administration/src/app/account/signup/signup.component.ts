import { Component, OnInit } from '@angular/core';
import { SignupService } from '../../shared/services/authentication/signup.service';
import { Router } from '@angular/router';

@Component({
    selector: 'app-signup',
    templateUrl: './signup.component.html',
    styleUrls: ['./signup.component.scss']
})
export class SignupComponent implements OnInit {
 
    success: boolean = null;
    match: boolean = true;
    password: string = null;
    confirmPassword : string = null;

    constructor(
        private router: Router,
        private signupService: SignupService
    ) { }

    ngOnInit() { }

    register(data) {
        if (data.password != data.confirmPassword) {
            this.match = false;
            this.onShowMessage(this.match);
        } else {
            this.signupService.onSignedup(data).subscribe(
                (resp) => {
                    this.success = true;
                    this.match = true;
                    this.onShowMessage(this.success);
                },
                (error) => {
                    this.success = false;
                    this.onShowMessage(this.success);
                }
            )
        }
        this.router.navigateByUrl('/signup')
    }

    onShowMessage(msg: any){
        console.log(msg);
    }
}
