import { Component, OnInit, Input } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { ListProjectService } from '../../../../shared/services/project-service/list-project.service';

@Component({
    selector: 'app-configure-project',
    templateUrl: './configure-project.component.html',
    styleUrls: ['./configure-project.component.scss']
})
export class ConfigureProjectComponent implements OnInit {

    project: any = null;

    constructor(
        private route: ActivatedRoute,
        private listProjectService: ListProjectService
    ) { }

    ngOnInit() {
        this.route.paramMap.subscribe(data => {
            // console.log(data.get('id'));
            this.listProjectService.getProject(data.get('id')).subscribe(data => {
                this.project = data;
                // console.log(this.project);
            }, error => {
                console.log(error);
            });
        }
        );
    }

}
