import { Component, OnInit } from '@angular/core';
import { User } from '../user-model';
import { ActivatedRoute, Router } from '@angular/router';
import { UserManagementService } from '../../../shared/services/user-management/user-management.service';

@Component({
  selector: 'app-user-management-update',
  templateUrl: './user-management-update.component.html',
  styleUrls: ['./user-management-update.component.scss']
})
export class UserManagementUpdateComponent implements OnInit {

  user: User;
  authorities = ['ROLE_ADMIN', 'ROLE_PROJECT_MANAGER'];
  isSaving: boolean;

  constructor(
    private userManagementService: UserManagementService,
    private route: ActivatedRoute,
    private router: Router
  ) { }

  ngOnInit() {
    this.loadAll();
  }

  loadAll() {
    this.isSaving = false;
    this.user= new User();
    this.route.paramMap.subscribe(
      (data) => {
        this.userManagementService.find(data.get('login')).subscribe(
          (user) => {
            this.user = user;
          })
      });
  }

  save() {
    this.isSaving = true;
    if(this.user.authorities.includes('')) {
      this.user.authorities.pop();
    }
    this.user.authorities.unshift('ROLE_USER');
    this.userManagementService.update(this.user).subscribe(
      (success) => this.onSaveSuccess(success),
      (error) => this.onSaveError()
    )
  }

  previousState() {
    this.router.navigate(['/user-management']);
  }

  private onSaveSuccess(result) {
    this.isSaving = true;
    this.previousState();
  }

  private onSaveError() {
    this.isSaving = false;
  }

}
