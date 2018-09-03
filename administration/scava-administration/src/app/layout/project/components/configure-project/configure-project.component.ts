import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ListProjectService } from '../../../../shared/services/project-service/list-project.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { AnalysisTaskService } from '../../../../shared/services/analysis-task/analysis-task.service';
import { ExecutionTask } from './execution-task.model';
import { AnalysisTaskMgmtDeleteDialogComponent } from './analysis-task-delete-dialog.component';

@Component({
    selector: 'app-configure-project',
    templateUrl: './configure-project.component.html',
    styleUrls: ['./configure-project.component.scss']
})
export class ConfigureProjectComponent implements OnInit {

    project: any = null;
    executionTasks: ExecutionTask[] = null;
    interval: any;
    globalStatus: any;

    constructor(
        private route: ActivatedRoute,
        private listProjectService: ListProjectService,
        private analysisTaskService: AnalysisTaskService,
        public modalService: NgbModal
    ) { }

    ngOnInit() {
        this.loadAll();
        this.interval = setInterval(() => { 
            this.loadAll();
            this.getGlobalStatus();
          }, 1000);
    }

    loadAll() {
        this.route.paramMap.subscribe(data => {
            this.listProjectService.getProject(data.get('id')).subscribe(
                (data) => {
                    this.project = data;
                    this.analysisTaskService.getTasksbyProject(this.project.name).subscribe(
                        (resp) => {
                            this.executionTasks = resp as ExecutionTask[];
                        },
                        (error) => {
                            this.onError(error);
                    });
                },
                (error) => {
                    this.onError(error);
                });
        });
    }

    getGlobalStatus() {
        this.executionTasks.forEach(task => {
            if(task.scheduling.status == 'COMPLETED' || task.scheduling.status == 'STOP' ){
                this.globalStatus = 'up-to-date';
                console.log('globalStatus' + this.globalStatus)
            } else {
                this.globalStatus = 'in-progress'
            }
        });
    }

    setProgressStyles(executionTask: any) {
        let styles = {
            'width': executionTask.scheduling.progress + '%',
        };
        return styles;
    }

    startTask(analysisTaskId: string) {
        this.analysisTaskService.startTask(analysisTaskId).subscribe(
            (resp) => {
                console.log('start successed !');
                this.loadAll();
            },
            (error) => {
                console.log('start failed')
            })
    }

    stopTask(analysisTaskId: string) {
        this.analysisTaskService.stopTask(analysisTaskId).subscribe(
            (resp) => {
                console.log('stop successed !');
                this.loadAll();
            },
            (error) => {
                console.log('stop failed')
            })
    }

    resetTask(analysisTaskId: string) {
        this.analysisTaskService.resetTask(analysisTaskId).subscribe(
            (resp) => {
                console.log('reset successed !');
                this.loadAll();
            },
            (error) => {
                console.log('reset failed')
            })
    }

    deleteTask(analysisTaskId: string) {
        const modalRef = this.modalService.open(AnalysisTaskMgmtDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
        modalRef.componentInstance.analysisTaskId = analysisTaskId;
        modalRef.result.then(
            result => {
                console.log('delete success');
                this.loadAll();
            },
            reason => {
                console.log('delete failed');
                this.loadAll();
            }
        );
    }

    private onError(error) {
        console.log(error)
    }


}
