import { Component, OnInit } from '@angular/core';
import { FormArray, FormGroup, FormBuilder, Validators } from '@angular/forms';
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
  infosSouceExist: boolean;

  constructor(
    private router: Router,
    private formBuilder: FormBuilder,
    private createProjectService: CreateProjectService,
  ) {
  }

  ngOnInit() {
    this.isSaving = false;
    this.infosSouceExist = false;
    this.project = new Project();
    this.buildForm();
  }

  buildForm() {
    this.form = this.formBuilder.group({
      name: this.addControlProject(),
      description: this.addControlProject(),
      homePage: this.addControlProject(),
      vcs: this.formBuilder.array([]),
      communication_channels: this.formBuilder.array([]),
      bts: this.formBuilder.array([])
    });
  }

  addControlProject(){
    return ['', Validators.required];
  }

  addInformationSource(source: string, type: string) {
    const formArray = <FormArray>this.form.get(source);
    switch (source) {
      case 'vcs':
        formArray.push(this.createVersionControlSystems(type));
        this.infosSouceExist = true;
        break;
      case 'bts':
        formArray.push(this.createIssueTrackingSystems(type));
        this.infosSouceExist = true;
        break;
      case 'communication_channels':
        formArray.push(this.createCommunicationChannels(type));
        this.infosSouceExist = true;
      default:
        break;
    }
  }

  createVersionControlSystems(type: string) {
    switch (type) {
      case 'git':
        return this.formBuilder.group({
          'type': [type],
          'url': ['', Validators.required]
        });
      case 'svn':
        return this.formBuilder.group({
          'type': [type],
          'url': ['', Validators.required]
        });
      default:
        break;
    }

  }

  createCommunicationChannels(type: string) {
    return this.formBuilder.group({
      'type': [type],
      'name': ['NNTP'],
      'url': ['', Validators.required],
    });
  }

  createIssueTrackingSystems(type: string) {
    switch (type) {
      case 'bugzilla':
        return this.formBuilder.group({
          'type': [type],
          'product': ['', Validators.required],
          'component': ['', Validators.required],
          'url': ['', Validators.required],
        });
      case 'sourceforge':
        return this.formBuilder.group({
          'type': [type],
          'url': ['', Validators.required]
        });
      case 'redmine':
        return this.formBuilder.group({
          'type': [type],
          'name': ['', Validators.required],
          'project': ['', Validators.required],
          'url': ['', Validators.required]
        });
      default:
        break;
    }
  }

  removeInformationSource(sourceName: string, index: number) {
    const formArray = <FormArray>this.form.get(sourceName);
    formArray.removeAt(index);
    const formValue = this.form.value;
    if(formValue.vcs.length == 0 && formValue.bts.length == 0 && formValue.communication_channels.length == 0){
      this.infosSouceExist = false;
    }
  }

  save() {
    this.isSaving = true;
    this.project.name= this.saveControlProject('name');
    this.project.description= this.saveControlProject('description');
    this.project.homePage= this.saveControlProject('homePage');
    this.project.vcsRepositories = this.saveInformationSources('vcs');
    this.project.bts = this.saveInformationSources('bts');
    this.project.communication_channels = this.saveInformationSources('communication_channels');
    this.createProjectService.createProject(this.project).subscribe(resp => {
      let project : IProject = resp as IProject;
      this.onShowMessage(project)
      this.router.navigate(['/project']);
    }, error => {
      this.onShowMessage(error)
    })

  }

  saveControlProject(elem: string) {
    const control = <FormArray>this.form.get(elem);
    return control.value;
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