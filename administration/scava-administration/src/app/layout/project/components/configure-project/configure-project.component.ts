import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ListProjectService } from '../../../../shared/services/project-service/list-project.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { AnalysisTaskService } from '../../../../shared/services/analysis-task/analysis-task.service';
import { ExecutionTask, MetricExecutions } from './execution-task.model';
import { AnalysisTaskMgmtDeleteDialogComponent } from './analysis-task-delete/analysis-task-delete-dialog.component';
import { Project } from '../../project.model';
import { MetricProvidersMgmtInfoDialogComponent } from './metrics-infos/metric-info.component';
import { RoleAuthorities } from '../../../../shared/guard/role-authorities';

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
    hasAuthorities: boolean;

    constructor(
        private route: ActivatedRoute,
        private listProjectService: ListProjectService,
        private analysisTaskService: AnalysisTaskService,
        public modalService: NgbModal,
        public roleAuthorities: RoleAuthorities
    ) { }

    ngOnInit() {
        this.loadAll();
        this.interval = setInterval(() => {
            this.loadAll();
        }, 2000);
    }

    loadAll() {
        this.route.paramMap.subscribe(data => {
            this.listProjectService.getProject(data.get('id')).subscribe(
                (data) => {
                    this.project = data;
                    this.analysisTaskService.getTasksbyProject(this.project.shortName).subscribe(
                        (resp) => {
                            this.executionTasks = resp as ExecutionTask[];
                        },
                        (error) => {
                            this.onShowMessage(error);
                        });
                    this.getGlobalStatus(this.project.shortName);
                    this.hasAuthorities = this.roleAuthorities.showCommands();
                },
                (error) => {
                    this.onShowMessage(error);
                });
        });
    }

    getGlobalStatus(projectId: string) {
        this.analysisTaskService.getAnalysisTasksStatusByProject(projectId).subscribe(
            (status) => {
                this.globalStatus = status['value'];
            },
            (error) => {
                this.onShowMessage(error);
            })

    }

    showMetricProviderList(metricExecutions: MetricExecutions[]) {
        const modalRef = this.modalService.open(MetricProvidersMgmtInfoDialogComponent, { size: 'lg', backdrop: 'static' });
        modalRef.componentInstance.metricExecutions = metricExecutions;
    }


    setProgressStyles(executionTask: ExecutionTask) {
        let styles = {
            'width': executionTask.scheduling.progress + '%',
        };
        return styles;
    }

    startTask(analysisTaskId: string) {
        this.analysisTaskService.startTask(analysisTaskId).subscribe(
            (resp) => {
                this.onShowMessage('start successed !');
            },
            (error) => {
                this.onShowMessage('start failed')
            });
        this.loadAll();
    }

    stopTask(analysisTaskId: string) {
        this.analysisTaskService.stopTask(analysisTaskId).subscribe(
            (resp) => {
                this.onShowMessage('stop successed !');
            },
            (error) => {
                this.onShowMessage('stop failed')
            });
        this.loadAll();
    }

    resetTask(analysisTaskId: string) {
        this.analysisTaskService.resetTask(analysisTaskId).subscribe(
            (resp) => {
                this.onShowMessage('reset successed !');
            },
            (error) => {
                this.onShowMessage('reset failed')
            })
        this.loadAll();
    }

    deleteTask(analysisTaskId: string) {
        const modalRef = this.modalService.open(AnalysisTaskMgmtDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
        modalRef.componentInstance.analysisTaskId = analysisTaskId;
        modalRef.result.then(
            (result) => {
                this.onShowMessage('delete success');
            },
            (reason) => {
                this.onShowMessage('delete failed');
            }
        );
        this.loadAll();
    }

    ngOnDestroy() {
        if (this.interval) {
            clearInterval(this.interval);
        }
    }

    onShowMessage(msg) {
        console.log(msg)
    }

}
