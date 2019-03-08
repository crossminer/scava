import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { TokenAuthoritiesService } from '../../../../shared/services/user-management/token-authorities.service';

@Component({
  selector: 'app-token-authorities-delete-dialog',
  templateUrl: './token-authorities-delete-dialog.component.html'
})
export class TokenAuthoritiesMgmtDeleteDialogComponent {

  label: string;

  constructor(
    public activeModal: NgbActiveModal,
    public tokenAuthoritiesService: TokenAuthoritiesService
  ) {
  }

  ngOnInit() {
  }

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(label: string) {
    this.tokenAuthoritiesService.deleteTokenAuthorities(label).subscribe(
      (resp) => {
        this.activeModal.dismiss(true);
      }
    );
  }

}
