import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { UserManagementService } from '../../shared/services/user-management/user-management.service';
import { AccountService } from '../../shared/services/user-management/account.service';
import { Account } from './account-model';
import { TokenAuthoritiesMgmtDeleteDialogComponent } from './components/token-authorities.delete/token-authorities-delete-dialog.component';
import { TokenAuthorities } from './token-authorities.model';
import { TokenAuthoritiesService } from '../../shared/services/user-management/token-authorities.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';


@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit {

  public settingsAccount: any;
  public account: Account;
  public login: string;
  public showEdit: boolean = false;
  public listTokenAuthorities: TokenAuthorities[] = null;
  public tokenAuthorities: TokenAuthorities;
  public newTokenAuthorities: TokenAuthorities;
  public profilesAuthorities: any;
  public rightAccesses = ['Monitoring Authorities'];
  public generateNew: boolean;
  public successToken: boolean = false;

  constructor(
    private userManagementService: UserManagementService,
    private accountAccount: AccountService,
    private tokenAuthoritiesService: TokenAuthoritiesService,
    private route: ActivatedRoute,
    private router: Router,
    public modalService: NgbModal
  ) { }

  ngOnInit() {
    this.loadAll();
  }

  loadAll() {
    this.route.paramMap.subscribe(
      (data) => {
        this.login = data.get('login');
        this.userManagementService.find(this.login).subscribe(
          (data) => {
            this.account = data as Account;
          });
          this.tokenAuthoritiesService.getAllTokenAuthorities().subscribe(
            (resp) => {
              this.listTokenAuthorities = resp as TokenAuthorities[];
            },
            (error) => {
              this.onShowMessage(error);
            });
      });
  }

  onEdit() {
    this.showEdit = true;
    this.generateNew = false;
    this.successToken = false;
    this.userManagementService.find(this.login).subscribe(
      (data) => {
        this.settingsAccount = data;
    });
  }

  onNew() {
    this.tokenAuthorities = new TokenAuthorities();
    this.showEdit = false;
    this.generateNew = true;
    this.successToken = false;
  }

  save() {
    this.accountAccount.save(this.settingsAccount).subscribe(
      (success) => {
        this.showEdit = false;
        this.loadAll();
      },
      (error) => this.onShowMessage(error)
    )
  }

  save2() {
    this.profilesAuthorities.forEach(rightRight => {
      if (rightRight == "Monitoring Authorities"){
        this.tokenAuthorities.monitoringAuthorities = true;
      }
    });
    this.tokenAuthoritiesService.generateTokenAuthorities(this.tokenAuthorities).subscribe(
      (resp) => {
        this.generateNew = false;
        this.loadAll();
        this.tokenAuthoritiesService.getTokenAuthoritiesByLabel(this.tokenAuthorities.label).subscribe(
          (data) => {
            this.newTokenAuthorities = data as TokenAuthorities;
            this.successToken= true;
          }, (error) => {
            this.onShowMessage(error);
          }
        )
      },
      (error) => {
        this.onShowMessage(error);
      }
    )
  }

  onCancel() {
    this.showEdit = false;
  }

  onCancel2() {
    this.generateNew = false;
  }

  onDelete(label: string) {
    this.successToken = false;
    this.generateNew = false;
    this.showEdit = false;
    const modalRef = this.modalService.open(TokenAuthoritiesMgmtDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.label = label;
    modalRef.result.then(
      (result) => {
        this.onShowMessage('delete success');
        this.loadAll();
      },
      (reason) => {
        this.onShowMessage('delete failed');
        this.loadAll();
      }
    );
  }

  previousState() {
    this.router.navigate(['/home']);
  }

  onShowMessage(msg: any) {
    console.log(msg);
  }

}