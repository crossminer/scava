import { Component, OnInit } from '@angular/core';
import { FormArray, FormGroup, FormBuilder } from '@angular/forms';
import { Project,IProject } from '../../project.model';
import { CreateProjectService } from '../../../../shared/services/project-service/create-project.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-create-project',
  templateUrl: './create-project.component.html',
  styleUrls: ['./create-project.component.scss']
})
export class CreateProjectComponent implements OnInit {

  form: FormGroup;
  project: Project;
  isSaving: boolean;

  constructor(
    private router: Router,
    private formBuilder: FormBuilder,
    private createProjectService: CreateProjectService,
  ) {
  }

  ngOnInit() {
    this.project = new Project();
    this.buildForm();
    this.isSaving = false;
  }

  buildForm() {
    this.form = this.formBuilder.group({
      vcs: this.formBuilder.array([]),
      communication_channels: this.formBuilder.array([]),
      bts: this.formBuilder.array([])
    });
  }

  addInformationSource(source: string, type: string) {
    const control = <FormArray>this.form.get(source);
    switch (source) {
      case 'vcs':
        control.push(this.createVersionControlSystems(type));
        break;
      case 'bts':
        control.push(this.createIssueTrackingSystems(type));
        break;
      case 'communication_channels':
        control.push(this.createCommunicationChannels(type));
      default:
        break;
    }
  }

  createVersionControlSystems(type: string) {
    switch (type) {
      case 'git':
        return this.formBuilder.group({
          'type': [type],
          'url': ['']
        });
      case 'svn':
        return this.formBuilder.group({
          'type': [type],
          'url': ['']
        });
      default:
        break;
    }

  }

  createCommunicationChannels(type: string) {
    return this.formBuilder.group({
      'type': [type],
      'name': ['NNTP'],
      'url': [''],
    });
  }

  createIssueTrackingSystems(type: string) {
    switch (type) {
      case 'bugzilla':
        return this.formBuilder.group({
          'type': [type],
          'product': [''],
          'component': [''],
          'url': [''],
        });
      case 'sourceforge':
        return this.formBuilder.group({
          'type': [type],
          'url': ['']
        });
      case 'redmine':
        return this.formBuilder.group({
          'type': [type],
          'name': [''],
          'project': [''],
          'url': ['']
        });
      default:
        break;
    }
  }

  removeInformationSource(sourceName: string, index: number) {
    const control = <FormArray>this.form.get(sourceName);
    control.removeAt(index);
  }

  save() {
    this.isSaving = true;
    this.project.vcsRepositories = this.saveInformationSources('vcs');
    this.project.bts = this.saveInformationSources('bts');
    this.project.communication_channels = this.saveInformationSources('communication_channels');
    this.createProjectService.createProject(this.project).subscribe(resp => {
      this.onShowMessage(resp)
      let project : IProject = resp as IProject;
      this.router.navigate(['/project']);
    }, error => {
      this.onShowMessage(error)
    })

  }

  saveInformationSources(sourceName: string) {
    const control = <FormArray>this.form.get(sourceName);
    return control.value;
  }

  previousState() {
    this.router.navigate(['project']);
  }

  onShowMessage(msg: any){
    console.log(msg);
  }

}