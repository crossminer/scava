import { Component, OnInit } from '@angular/core';
import { PasswordService } from '../../shared/services/user-management/password.service';

@Component({
  selector: 'app-password',
  templateUrl: './password.component.html',
  styleUrls: ['./password.component.scss']
})
export class PasswordComponent implements OnInit {

  doNotMatch: string;
  error: string;
  success: string;
  currentPassword: string;
  newPassword: string;
  confirmPassword: string;

  constructor(
    private passwordService: PasswordService
  ) { }

  ngOnInit() {
  }

  changePassword() {
    debugger
    if (this.newPassword !== this.confirmPassword) {
      this.error = null;
      this.success = null;
      this.doNotMatch = 'ERROR';
    } else {
      this.doNotMatch = null;
      this.passwordService.changePassword(this.currentPassword, this.confirmPassword).subscribe(
        () => {
          this.error = null;
          this.success = 'OK';
        },
        () => {
          this.success = null;
          this.error = 'ERROR';
        }
      )
    }
  }

}
