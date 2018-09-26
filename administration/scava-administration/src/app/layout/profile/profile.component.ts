import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { UserManagementService } from '../../shared/services/user-management/user-management.service';
import { AccountService } from '../../shared/services/user-management/account.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit {

  settingsAccount: any

  constructor(
    private userManagementService: UserManagementService,
    private accountAccount: AccountService,
    private route: ActivatedRoute,
    private router: Router
  ) { }

  ngOnInit() {
    this.loadAll();
  }

  loadAll() {
    this.route.paramMap.subscribe(
      (data) => {
        this.userManagementService.find(data.get('login')).subscribe(
          (settings) => {
            this.settingsAccount = settings;
          })
      });
  }

  save() {
    this.accountAccount.save(this.settingsAccount).subscribe(
      (success) => this.previousState(),
      (error) => this.onShowMessage(error)
    )
  }

  previousState() {
    this.router.navigate(['/home']);
  }

  onShowMessage(msg: any) {
    console.log(msg);
  }

}
