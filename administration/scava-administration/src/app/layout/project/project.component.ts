import { Component, OnInit } from '@angular/core';
import { ListProjectService } from '../../shared/services/project-service/list-project.service';
import { Project } from './project.model';
import { ResponseWrapper } from '../../shared/models/response-wrapper.model';

@Component({
  selector: 'app-project',
  templateUrl: './project.component.html',
  styleUrls: ['./project.component.scss']
})

export class ProjectComponent implements OnInit {

  projectList: any;

  
  constructor(private listProjectService: ListProjectService) { }

  ngOnInit() {
    this.listProjectService.listProjects().subscribe((resp) => {
        this.projectList = resp;
      }
    );
  }
}
