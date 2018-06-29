import { Component, OnInit } from '@angular/core';
import { UserManagementService } from '../../shared/services/user-management/user-management.service';
import { User } from './user-model';
import { HttpResponse } from '@angular/common/http';

@Component({
  selector: 'app-user-management',
  templateUrl: './user-management.component.html',
  styleUrls: ['./user-management.component.scss']
})
export class UserManagementComponent implements OnInit {

  private users: User[];

  constructor(
    private userManagementService: UserManagementService
  ) { }

  ngOnInit() {
    this.loadAll();
  }

  loadAll() {
    this.userManagementService.get().subscribe(
      (resp) => this.onSuccess(resp), 
      (resp) => this.onError(resp)
    );
  }

  active() {
    
  }

  private onSuccess(data) {
    this.users = data;
    console.log(data);
  }

  private onError(error) {
    console.log(error);
  }

}
