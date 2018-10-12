import { Component, OnInit } from '@angular/core';
import { ConfigService } from './shared/services/configuration/configuration-service';

@Component({
    selector: 'app-root',
    templateUrl: './app.component.html',
    styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {

    constructor(public configService:ConfigService) {
    }

    ngOnInit() {
        this.configService.getJSON().subscribe(
            data => {
                localStorage.setItem("SERVICE_URL", data["SERVICE_URL"]);
            },
            error => {
                this.onShowMessage(error)
            });
    }

    onShowMessage(msg: any){
        console.log(msg);
    }
}
