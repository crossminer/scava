import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ListProjectService } from '../../../../shared/services/project-service/list-project.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { AnalysisTaskService } from '../../../../shared/services/analysis-task/analysis-task.service';
import { ExecutionTask, MetricExecutions } from './execution-task.model';
import { AnalysisTaskMgmtDeleteDialogComponent } from './analysis-task-delete/analysis-task-delete-dialog.component';
import { Project } from '../../project.model';
import { MetricProvidersMgmtInfoDialogComponent } from './metrics-infos/metric-info.component';

@Component({
    selector: 'app-configure-project',
    templateUrl: './configure-project.component.html',
    styleUrls: ['./configure-project.component.scss']
})
export class ConfigureProjectComponent implements OnInit {

    project: Project = null;
    executionTasks: ExecutionTask[] = null;
    interval: any;
    globalStatus: string;

    constructor(
        private route: ActivatedRoute,
        private listProjectService: ListProjectService,
        private analysisTaskService: AnalysisTaskService,
        public modalService: NgbModal
    ) { }

    ngOnInit() {
        this.loadAll();
        // this.interval = setInterval(() => { 
        //     this.loadAll();
        //     this.getGlobalStatus();
        //   }, 1500);
        this.loadAll();
    }

    loadAll() {
        this.route.paramMap.subscribe(data => {
            this.listProjectService.getProject(data.get('id')).subscribe(
                (data) => {
                    debugger
                    this.project = data;
                    this.analysisTaskService.getTasksbyProject(this.project.shortName).subscribe(
                        (resp) => {
                            this.executionTasks = resp as ExecutionTask[];
                            console.log(this.executionTasks)
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
            } else {
                this.globalStatus = 'in-progress'
            }
        });
    }

    showMetricProviderList(metricExecutions: MetricExecutions[]) {
        const modalRef = this.modalService.open(MetricProvidersMgmtInfoDialogComponent, { size: 'lg', backdrop: 'static' });
        modalRef.componentInstance.metricExecutions = metricExecutions;
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

    ngOnDestroy() {
        if(this.interval) {
            clearInterval(this.interval);
        }
    }


}
