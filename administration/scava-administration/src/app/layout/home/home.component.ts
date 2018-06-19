import { Component, OnInit } from '@angular/core';
import { routerTransition } from '../../router.animations';
import { AuthenticationService } from '../../shared/services/authentication/authentication.service';
import { Router } from '@angular/router';
import { ImportProjectService } from '../../shared/services/api-server/import-project.service';
import { PingService } from '../../shared/services/api-server/ping.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss'],
  animations: [routerTransition()]
})
export class HomeComponent implements OnInit {

  constructor(
    private router: Router,
    private pingService: PingService
  ) {
   }

  ngOnInit() {
      // this.testPing();
  }

  testPing() {
      this.pingService.testPing().subscribe(resp => {
        console.log(resp);
      }, error => {
        console.log(error);
      });
  }

}
