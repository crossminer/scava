import { Component, OnInit } from '@angular/core';
import { PasswordService } from '../../shared/services/user-management/password.service';
import { LocalStorageService } from '../../shared/services/authentication/local-storage.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-password',
  templateUrl: './password.component.html',
  styleUrls: ['./password.component.scss']
})
export class PasswordComponent implements OnInit {

  doNotMatch: string;
  isChanging: boolean;
  error: string;
  success: string;
  currentLogin: string;
  currentPassword: string;
  newPassword: string;
  confirmPassword: string;

  constructor(
    private router: Router,
    private passwordService: PasswordService,
    private localStorageService: LocalStorageService
  ) { }

  ngOnInit() {
    this.isChanging = false;
  }

  changePassword() {
    if (this.newPassword !== this.confirmPassword) {
      this.error = null;
      this.success = null;
      this.doNotMatch = 'ERROR';
    } else {
      this.doNotMatch = null;
      this.currentLogin = this.localStorageService.getUsername();
      this.passwordService.change(this.currentLogin, this.currentPassword, this.newPassword).subscribe(
        () => {
          this.error = null;
          this.success = 'OK';
          this.isChanging = true;
        },
        () => {
          this.success = null;
          this.error = 'ERROR';
        }
      )
    }
  }

  previousState() {
    this.router.navigate(['/home']);
  }

}
