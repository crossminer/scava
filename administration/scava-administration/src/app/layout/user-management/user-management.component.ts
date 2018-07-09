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

  private users: User[];

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

  setActive(user, isActivated) {
    user.activated = isActivated;
    this.userManagementService.update(user).subscribe(
      (success) => this.onSuccess(success),
      (error) => this.onError(error)
    )
  }

  deleteUser(user: User) {
    const modalRef = this.modalService.open(UserMgmtDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.user = user;
    console.log(modalRef.result)
    modalRef.result.then(
      result => {
        // Left blank intentionally, nothing to do here
        console.log('delete success');
      },
      reason => {
        // Left blank intentionally, nothing to do here
        console.log('delete faild');
      }
    );
  }

  private onSuccess(data) {
    this.users = data;
    //console.log(this.users)
  }

  private onError(error) {
    console.log('error' + error);
  }

}
