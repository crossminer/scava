import { Component, OnInit, Input } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ListProjectService } from '../../../../shared/services/project-service/list-project.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { AnalysisAlgorithmMgmtAddDialogComponent } from './analysis-algorithm-add-dialog.component';

@Component({
    selector: 'app-configure-project',
    templateUrl: './configure-project.component.html',
    styleUrls: ['./configure-project.component.scss']
})
export class ConfigureProjectComponent implements OnInit {

    project: any = null;

    constructor(
        private route: ActivatedRoute,
        private listProjectService: ListProjectService,
        public modalService: NgbModal
    ) { }

    ngOnInit() {
        this.loadAll();
    }

    loadAll() {
        this.route.paramMap.subscribe(data => {
            // console.log(data.get('id'));
            this.listProjectService.getProject(data.get('id')).subscribe(data => {
                this.project = data;
                // console.log(this.project);
            }, error => {
                //console.log(error);
            });
        }
        );
    }

    addAnalysisAlgorithm() {
        const modalRef = this.modalService.open(AnalysisAlgorithmMgmtAddDialogComponent, { size: 'lg', backdrop: 'static' });
        modalRef.result.then(
            result => {
                console.log('delete success');
                this.loadAll();
            },
            reason => {
                console.log('delete faild');
                this.loadAll();
            }
        );
    }

}
