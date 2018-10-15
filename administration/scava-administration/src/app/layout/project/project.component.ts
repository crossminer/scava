import { Component, OnInit } from '@angular/core';
import { ListProjectService } from '../../shared/services/project-service/list-project.service';
import { AnalysisTaskService } from '../../shared/services/analysis-task/analysis-task.service';
import { Project } from './project.model';
import { ExecutionTask } from './components/configure-project/execution-task.model';
import { RoleAuthorities } from '../../shared/guard/role-authorities';

@Component({
  selector: 'app-project',
  templateUrl: './project.component.html',
  styleUrls: ['./project.component.scss']
})

export class ProjectComponent implements OnInit {

  projectList: Project[];
  globalStatus: string;
  hasAuthorities: boolean;

  
  constructor(
    private listProjectService: ListProjectService,
    private analysisTaskService: AnalysisTaskService,
    private roleAuthorities: RoleAuthorities
  ) { }

  ngOnInit() {
    this.listProjectService.listProjects().subscribe(
      (resp) => {
        this.hasAuthorities = this.roleAuthorities.showCommands();
        this.projectList = resp as Project[];
        console.log(this.projectList)
        this.projectList.forEach(project => {
          this.analysisTaskService.getAnalysisTasksStatusByProject(project.shortName).subscribe(
            (status) => {
              project.globalStatus = status['value'];
              this.analysisTaskService.getTasksbyProject(project.shortName).subscribe(
                (resp) => {
                    let executionTasks= resp as ExecutionTask[];
                   if(executionTasks.length !== 0){
                     project.hasTasks = true;
                   } else {
                     project.hasTasks = false;
                   }
                },
                (error) => {
                    this.onShowMessage(error);
            });
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

  onShowMessage(msg: any) {
    console.log(msg);
  }
}
