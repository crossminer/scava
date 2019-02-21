import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ListProjectService } from '../../../../shared/services/project-service/list-project.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { AnalysisTaskService } from '../../../../shared/services/analysis-task/analysis-task.service';
import { ExecutionTask, MetricExecutions } from './execution-task.model';
import { AnalysisTaskMgmtDeleteDialogComponent } from './analysis-task-delete/analysis-task-delete-dialog.component';
import { Project } from '../../project.model';
import { MetricProvidersMgmtInfoDialogComponent } from './metrics-infos/metric-info.component';
import { RoleAuthorities } from '../../../../shared/services/authentication/role-authorities';
import { FlatTreeControl } from '@angular/cdk/tree';
import { MatTreeFlattener, MatTreeFlatDataSource } from '@angular/material';

/**
 * Food data with nested structure.
 * Each node has a name and an optiona list of children.
 */
interface FoodNode {
    name: string;
    children?: FoodNode[];
}

const TREE_DATA: FoodNode[] = [
    {
        name: 'Fruit',
        children: [
            { name: 'Apple' },
            { name: 'Banana' },
            { name: 'Fruit loops' },
        ]
    }, {
        name: 'Vegetables',
        children: [
            {
                name: 'Green',
                children: [
                    { name: 'Broccoli' },
                    { name: 'Brussel sprouts' },
                ]
            }, {
                name: 'Orange',
                children: [
                    { name: 'Pumpkins' },
                    { name: 'Carrots' },
                ]
            },
        ]
    },
];

/** Flat node with expandable and level information */
interface ExampleFlatNode {
    expandable: boolean;
    name: string;
    level: number;
}

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
    
    private transformer = (node: FoodNode, level: number) => {
        return {
          expandable: !!node.children && node.children.length > 0,
          name: node.name,
          level: level,
        };
      }
    
      treeControl = new FlatTreeControl<ExampleFlatNode>(
          node => node.level, node => node.expandable);
    
      treeFlattener = new MatTreeFlattener(
          this.transformer, node => node.level, node => node.expandable, node => node.children);
    
      dataSource = new MatTreeFlatDataSource(this.treeControl, this.treeFlattener);

    constructor(
        private route: ActivatedRoute,
        private listProjectService: ListProjectService,
        private analysisTaskService: AnalysisTaskService,
        public modalService: NgbModal,
        public roleAuthorities: RoleAuthorities,
    ) { 
        this.dataSource.data = TREE_DATA;
    }

    hasChild = (_: number, node: ExampleFlatNode) => node.expandable;

    ngOnInit() {
        this.loadAll();
        this.interval = setInterval(() => {
            this.loadAll();
        }, 3000);
    }

    loadAll() {
        if (!this.roleAuthorities.isCurrentTokenExpired()) {
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