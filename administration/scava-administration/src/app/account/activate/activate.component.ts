import { Component, OnInit } from '@angular/core';
import { ActivateService } from './activate.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-activate',
  templateUrl: './activate.component.html',
  styleUrls: ['./activate.component.scss']
})
export class ActivateComponent implements OnInit {

  success: string = null;
  error: string = null;

  constructor(
    private acivateservice: ActivateService,
    private route: ActivatedRoute
  ) { }

  ngOnInit() {
    this.route.queryParams.subscribe(params => {
      this.acivateservice.activateAccount(params['key']).subscribe(
        () => {
          this.error = null;
          this.success = 'OK';
        },
        () => {
          this.success = null;
          this.error = 'ERROR'
        });
    });
  }

}
