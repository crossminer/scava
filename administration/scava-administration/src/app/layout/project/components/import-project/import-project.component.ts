import { Component, OnInit } from '@angular/core';
import { Project, IProject } from '../../project.model';
import { ImportProjectService } from '../../../../shared/services/project-service/import-project.service';
import { Router } from '@angular/router';

@Component({
    selector: 'app-import-project',
    templateUrl: './import-project.component.html',
    styleUrls: ['./import-project.component.scss']
})
export class ImportProjectComponent implements OnInit {

    project: Project = {};
    isSaving: boolean;

    constructor(
        private router: Router,
        private importProjectService: ImportProjectService
    ) { }

    ngOnInit() {
        this.isSaving = false;
    }

    save() {
        this.isSaving = true;
        debugger;
        this.importProjectService.importProject(this.project).subscribe(resp => {
            var project : IProject = resp as IProject;
            console.log(resp);
            this.router.navigate(['/project']);
        }, error => {
            console.log(error);
        });
    }

}
