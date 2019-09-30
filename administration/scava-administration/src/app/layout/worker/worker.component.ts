import { Component, OnInit, OnDestroy } from '@angular/core';
import { ListWorkerService } from '../../shared/services/worker-service/list-worker.service';
import { AnalysisTaskService } from '../../shared/services/analysis-task/analysis-task.service';
import { ExecutionTask, MetricExecutions } from '../project/components/configure-project/execution-task.model';
import { Worker } from './worker.model';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { MetricInfoComponent } from './metrics-infos/metric-info.component';
import { RoleAuthorities } from '../../shared/services/authentication/role-authorities';

@Component({
  selector: 'app-worker',
  templateUrl: './worker.component.html',
  styleUrls: ['./worker.component.scss']
})

export class WorkerComponent implements OnInit, OnDestroy {

  workerList: Worker[];
  interval: any;
  pendingTaskList: ExecutionTask[];
  completedTaskList: ExecutionTask[];
  errorTaskList: ExecutionTask[];

  constructor(
    private listWorkerService: ListWorkerService,
    private analysisTaskService: AnalysisTaskService,
    private roleAuthorities: RoleAuthorities,
    public modalService: NgbModal
  ) { }

  ngOnInit() {
    this.refreshData();
    this.interval = setInterval(() => {
      this.refreshData();
    }, 3000);

  }

  refreshData() {
    if (!this.roleAuthorities.isCurrentTokenExpired()) {
      this.listWorkerService.getWorkers().subscribe((resp) => {
        this.workerList = resp as Worker[];
        this.workerList.forEach(worker => {
          let filteredMetricExecutions: MetricExecutions[] = [];
          if (worker.currentTask != null) {
            worker.currentTask.metricExecutions.forEach(metricExecution => {
              if (metricExecution.hasVisualisation == "true") {
                filteredMetricExecutions.push(metricExecution);
              }
            });
            worker.currentTask.metricExecutions = filteredMetricExecutions;
          }
        });
      });
      this.analysisTaskService.getTasks().subscribe((resp) => {
        let allTasks = resp as ExecutionTask[];
        this.pendingTaskList = [];
        this.completedTaskList = [];
        this.errorTaskList = [];
        allTasks.forEach(task => {
          if (task.scheduling.status == 'PENDING_EXECUTION') {
            this.pendingTaskList.push(task);
          } else if (task.scheduling.status == 'COMPLETED') {
            this.completedTaskList.push(task);
          } else if (task.scheduling.status == 'ERROR') {
            this.errorTaskList.push(task);
          }
          let filteredMetricExecutions: MetricExecutions[] = [];
          task.metricExecutions.forEach(metricExecution => {
            if (metricExecution.hasVisualisation == "true") {
              filteredMetricExecutions.push(metricExecution);
            }
          });
          task.metricExecutions = filteredMetricExecutions;
        });
      });
    }
  }

  setProgressStyles(worker: any) {
    let styles = {
      'width': worker.currentTask.scheduling[0].progress + '%',
    };

    return styles;
  }

  computeTime(task: any) {
    let estimatedTime: number = task.scheduling[0].lastDailyExecutionDuration;
    return Math.round(estimatedTime / 1000) + 's';
  }

  displayTime(millisec: number) {
    const normalizeTime = (time: string): string => (time.length === 1) ? time.padStart(2, '0') : time;
   
    let seconds: string = (millisec / 1000).toFixed(0);
    let minutes: string = Math.floor(parseInt(seconds) / 60).toString();
    let hours: string = '';
   
    if (parseInt(minutes) > 59) {
      hours = normalizeTime(Math.floor(parseInt(minutes) / 60).toString());
      minutes = normalizeTime((parseInt(minutes) - (parseInt(hours) * 60)).toString());
    }
    seconds = normalizeTime(Math.floor(parseInt(seconds) % 60).toString());
   
    if (hours !== '') {
       return `${hours} h : ${minutes} m : ${seconds} s`;
    }
      return `${minutes} m : ${seconds} s`;
   }

  stopTask(analysisTaskId: string) {
    this.analysisTaskService.stopTask(analysisTaskId).subscribe(
      (resp) => {
        this.refreshData();
      },
      (error) => {
        this.onShowMessage('stop failed')
      })
  }

  promoteTask(analysisTaskId: string) {
    this.analysisTaskService.promoteTask(analysisTaskId).subscribe(
      (resp) => {
        this.onShowMessage('promote successed !');
        this.refreshData();
      },
      (error) => {
        this.onShowMessage('promote failed')
      })
  }

  demoteTask(analysisTaskId: string) {
    this.analysisTaskService.demoteTask(analysisTaskId).subscribe(
      (resp) => {
        this.onShowMessage('emote successed !');
        this.refreshData();
      },
      (error) => {
        this.onShowMessage('emote failed')
      })
  }

  pushOnWorker(analysisTaskId: string, wotkerId: string) {
    this.analysisTaskService.pushOnWorker(analysisTaskId, wotkerId).subscribe(
      (resp) => {
        this.onShowMessage('pushOnWorker successed !');
        this.refreshData();
      },
      (error) => {
        this.onShowMessage('pushOnWorker failed')
      })
  }

  showMetricProviderList(analysisTask: ExecutionTask) {
    const modalRef = this.modalService.open(MetricInfoComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.analysisTask = analysisTask;
  }

  ngOnDestroy() {
    if (this.interval) {
      clearInterval(this.interval);
    }
  }

  onShowMessage(msg: any) {
    console.log(msg);
  }

}
