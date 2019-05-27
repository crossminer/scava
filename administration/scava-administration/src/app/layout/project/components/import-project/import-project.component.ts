import { Component, OnInit } from '@angular/core';
import { Project, IProject } from '../../project.model';
import { ImportProjectService } from '../../../../shared/services/project-service/import-project.service';
import { Router } from '@angular/router';
import { PropertiesService } from '../../../../shared/services/properties-service/properties.service';
import { Properties } from '../../../properties/properties.model';

@Component({
    selector: 'app-import-project',
    templateUrl: './import-project.component.html',
    styleUrls: ['./import-project.component.scss']
})
export class ImportProjectComponent implements OnInit {

    project: Project;
    isSaving: boolean;
    githubTokenFount: boolean;
    gitlabTokenFount: boolean;

    constructor(
        private router: Router,
        private importProjectService: ImportProjectService,
        public propertiesService: PropertiesService,
    ) { }

    ngOnInit() {
        this.isSaving = false;
        this.project = new Project();
    }

    save() {
        this.isSaving = true;
        this.importProjectService.importProject(this.project).subscribe(resp => {
            this.router.navigate(['/project']);
        }, (error) => {
            this.isSaving = false;
            this.onShowMessage(error);
        });
    }

    checkPropertyExist(){
        this.githubTokenFount = true;
        this.gitlabTokenFount = true;
        this.propertiesService.getProperties().subscribe(
            (resp) => {
                let properties: Properties[] = resp as Properties[];
                if (this.project.url.startsWith("https://github")) {
                    this.githubTokenFount = false;
                    for (let property of properties) {
                        if (property.key == "githubToken") {
                            this.githubTokenFount = true;
                            break;
                        }
                    };
                    if (!this.githubTokenFount) {
                        console.log("github token not-fount");
                    }
                } else if (this.project.url.startsWith("https://gitlab")) {
                    this.gitlabTokenFount = false;
                    for (let property of properties) {
                        if (property.key == "gitlabToken") {
                            this.gitlabTokenFount = true;
                            break;
                        }
                    };
                    if (!this.gitlabTokenFount) {
                        console.log("gitlab token not-fount");
                    }
                }

            },
            (error) => {
                this.onShowMessage(error);
            });
    }

    previousState() {
        this.router.navigate(['project']);
    }

    onShowMessage(msg: any) {
        console.log(msg);
    }

}
