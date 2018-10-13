import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { UserManagementService } from '../../../shared/services/user-management/user-management.service';
import { User } from '../user-model';
import { Router } from '@angular/router';

@Component({
    selector: 'app-user-mgmt-delete-dialog',
    templateUrl: './user-management-delete-dialog.component.html'
})
export class UserMgmtDeleteDialogComponent {
    user: User;

    constructor(
        private router: Router,
        private userManagementService: UserManagementService, 
        public activeModal: NgbActiveModal
    ) { }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(login) {
        this.userManagementService.delete(login).subscribe(response => {
            this.activeModal.dismiss(true);
        });
        this.router.navigateByUrl('/user-management');
    }
}
