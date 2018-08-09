import { Component, OnInit } from '@angular/core';
import { FormArray, FormGroup, FormBuilder } from '@angular/forms';
import { Project } from '../../project.model';
import { CreateProjectService } from '../../../../shared/services/project-service/create-project.service';

@Component({
  selector: 'app-create-project',
  templateUrl: './create-project.component.html',
  styleUrls: ['./create-project.component.scss']
})
export class CreateProjectComponent implements OnInit {

  form: FormGroup;
  project: Project = {};
  isSaving: boolean;

  constructor(
    private formBuilder: FormBuilder,
    private createProjectService: CreateProjectService,
  ) {
  }

  ngOnInit() {
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
    this.project.vcs = this.saveInformationSources('vcs');
    this.project.bts = this.saveInformationSources('bts');
    this.project.communication_channels = this.saveInformationSources('communication_channels');
    this.createProjectService.createProject(this.project).subscribe(resp => {
      console.log(resp)
      debugger
    }, error => {
      console.log(error)
    })

  }

  saveInformationSources(sourceName: string) {
    const control = <FormArray>this.form.get(sourceName);
    return control.value;
  }

}