import { Component, OnInit } from '@angular/core';
import { ListProjectService } from '../../shared/services/project-service/list-project.service';
import { AnalysisTaskService } from '../../shared/services/analysis-task/analysis-task.service';
import { Project } from './project.model';

@Component({
  selector: 'app-project',
  templateUrl: './project.component.html',
  styleUrls: ['./project.component.scss']
})

export class ProjectComponent implements OnInit {

  projectList: Project[];
  globalStatus: string;
  
  constructor(
    private listProjectService: ListProjectService,
    private analysisTaskService: AnalysisTaskService,
  ) { }

  ngOnInit() {
    this.listProjectService.listProjects().subscribe(
      (resp) => {
        this.projectList = resp as Project[];
        console.log(this.projectList)
        this.projectList.forEach(project => {
          this.analysisTaskService.getAnalysisTasksStatusByProject(project.shortName).subscribe(
            (status) => {
              project.globalStatus = status['value'];
            },
            (error) => {
              this.onShowMessage(error);
            })
        });
      },
      (error) => {
        this.onShowMessage(error);
      }
    );
  }

  getGlobalStatus(projectId: string) {
    debugger
    this.analysisTaskService.getAnalysisTasksStatusByProject(projectId).subscribe(
        (status) => {
            this.globalStatus = status['value'];
        },
        (error) => {
            this.onShowMessage(error);
        })
}

  onShowMessage(msg: any) {
    console.log(msg);
  }
}
