import { Component, OnInit } from '@angular/core';
import { UserManagementService } from '../../shared/services/user-management/user-management.service';
import { User } from './user-model';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { UserMgmtDeleteDialogComponent } from './user-management-delete-dialog/user-management-delete-dialog.component';

@Component({
  selector: 'app-user-management',
  templateUrl: './user-management.component.html',
  styleUrls: ['./user-management.component.scss']
})
export class UserManagementComponent implements OnInit {

  users: Array<User> = [];

  constructor(
    private userManagementService: UserManagementService,
    public modalService: NgbModal
  ) { }

  ngOnInit() {
    this.loadAll();
  }

  loadAll() {
    this.userManagementService.query().subscribe(
      (success) => this.onSuccess(success),
      (error) => this.onError(error)
    );
  }

  setActive(user: User, isActivated: boolean) {
    user.activated = isActivated;
    this.userManagementService.update(user).subscribe(
      (success) => this.onSuccess(success),
      (error) => this.onError(error)
    )
  }

  deleteUser(user: User) {
    const modalRef = this.modalService.open(UserMgmtDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.user = user;
    modalRef.result.then(
      result => {
        console.log('delete success');
      },
      reason => {
        console.log('delete faild');
        this.loadAll();
      }
    );
  }

  private onSuccess(data) {
    this.users = data;
  }

  private onError(error) {
    console.log(error);
  }

}
