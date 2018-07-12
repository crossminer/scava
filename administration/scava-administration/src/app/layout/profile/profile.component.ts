import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { User } from '../user-management/user-model';
import { UserManagementService } from '../../shared/services/user-management/user-management.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit {

  private user: User;

  constructor(
    private userManagementService: UserManagementService,
    private route: ActivatedRoute,
    private router: Router
  ) { }

  ngOnInit() {
    this.loadAll();
  }

  loadAll() {
    this.user = new User();
    this.route.paramMap.subscribe(
      (data) => {
        this.userManagementService.find(data.get('login')).subscribe(
          (user) => {
            this.user = user;
          })
      });
  }

  save() {
    this.userManagementService.update(this.user).subscribe(
      (success) => this.previousState(),
      (error) => console.log(error)
    )
  }

  previousState() {
    this.router.navigate(['/home']);
  }

}
