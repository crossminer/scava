(window["webpackJsonp"] = window["webpackJsonp"] || []).push([["project-project-module"],{

/***/ "./src/app/layout/project/components/configure-project/configure-project.component.html":
/*!**********************************************************************************************!*\
  !*** ./src/app/layout/project/components/configure-project/configure-project.component.html ***!
  \**********************************************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "<link rel=\"stylesheet\" href=\"https://fonts.googleapis.com/icon?family=Material+Icons\" />\n\n\n<div class=\"project-title\">\n    <div class=\" panel-profile\">\n        <div class=\"panel-body text-center\">\n            <i class=\"fa fa-4x fa-check-circle\"></i>\n            <h3 class=\"panel-title\">\n                <strong>up to date</strong>\n            </h3>\n        </div>\n    </div>\n    <h2 class=\"display-3\">{{project?.name}}</h2>\n\n    <h4>\n        <p>{{project?.description}}</p>\n    </h4>\n</div>\n\n\n\n\n\n<table class=\"table table-condensed table-responsive table-project-information\">\n    <tbody>\n        <tr>\n            <td>Project Type</td>\n            <td>{{project?._type}}</td>\n        </tr>\n        <tr>\n            <td>URL</td>\n            <td>\n                <a href=\"{{project?.html_url}}\">{{project?.html_url}}</a>\n            </td>\n\n\n        </tr>\n        <tr>\n            <td>Repository</td>\n            <td>\n                <a href=\"{{project?.svn_url}}\">{{project?.svn_url}}</a>\n            </td>\n        </tr>\n        <tr>\n            <td>Size</td>\n            <td>{{project?.size}}</td>\n        </tr>\n    </tbody>\n</table>"

/***/ }),

/***/ "./src/app/layout/project/components/configure-project/configure-project.component.scss":
/*!**********************************************************************************************!*\
  !*** ./src/app/layout/project/components/configure-project/configure-project.component.scss ***!
  \**********************************************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = ".project-title {\n  color: gray;\n  padding-bottom: 10px; }\n\n.table-project-information > tbody > tr {\n  border-top: 1px solid #dddddd; }\n\n.table-project-information > tbody > tr:first-child {\n  border-top: 0; }\n\n.table-project-information > tbody > tr > td {\n  border-top: 0; }\n\n.panel-profile .panel-heading {\n  height: 150px;\n  background-size: cover;\n  widows: 150px; }\n\n.panel-profile {\n  width: 200px;\n  border-radius: 25px;\n  padding: 5px;\n  float: right;\n  margin-top: 20px;\n  margin-right: 50px;\n  color: green; }\n\n.mb10 {\n  margin-bottom: 10px; }\n"

/***/ }),

/***/ "./src/app/layout/project/components/configure-project/configure-project.component.ts":
/*!********************************************************************************************!*\
  !*** ./src/app/layout/project/components/configure-project/configure-project.component.ts ***!
  \********************************************************************************************/
/*! exports provided: ConfigureProjectComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "ConfigureProjectComponent", function() { return ConfigureProjectComponent; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _angular_router__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/router */ "./node_modules/@angular/router/fesm5/router.js");
/* harmony import */ var _shared_services_project_service_list_project_service__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ../../../../shared/services/project-service/list-project.service */ "./src/app/shared/services/project-service/list-project.service.ts");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (undefined && undefined.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};



var ConfigureProjectComponent = /** @class */ (function () {
    function ConfigureProjectComponent(route, listProjectService) {
        this.route = route;
        this.listProjectService = listProjectService;
        this.project = null;
    }
    ConfigureProjectComponent.prototype.ngOnInit = function () {
        var _this = this;
        this.route.paramMap.subscribe(function (data) {
            // console.log(data.get('id'));
            _this.listProjectService.getProject(data.get('id')).subscribe(function (data) {
                _this.project = data;
                // console.log(this.project);
            }, function (error) {
                console.log(error);
            });
        });
    };
    ConfigureProjectComponent = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Component"])({
            selector: 'app-configure-project',
            template: __webpack_require__(/*! ./configure-project.component.html */ "./src/app/layout/project/components/configure-project/configure-project.component.html"),
            styles: [__webpack_require__(/*! ./configure-project.component.scss */ "./src/app/layout/project/components/configure-project/configure-project.component.scss")]
        }),
        __metadata("design:paramtypes", [_angular_router__WEBPACK_IMPORTED_MODULE_1__["ActivatedRoute"],
            _shared_services_project_service_list_project_service__WEBPACK_IMPORTED_MODULE_2__["ListProjectService"]])
    ], ConfigureProjectComponent);
    return ConfigureProjectComponent;
}());



/***/ }),

/***/ "./src/app/layout/project/components/create-project/create-project.component.html":
/*!****************************************************************************************!*\
  !*** ./src/app/layout/project/components/create-project/create-project.component.html ***!
  \****************************************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "<div class=\"container\">\n    <h4>Register project</h4>\n    <hr/>\n\n    <form [formGroup]=\"form\" #addProjectForm=\"ngForm\" name=\"addProjectForm\" novalidate (ngSubmit)=\"save(addProjectForm.value)\">\n        <div class=\"row\">\n            <div class=\"col-sm-6 col-md-6 col-lg-6\">\n                <div class=\"form-group\">\n                    <label for=\"projectName\">Project Name</label>\n                    <input type=\"text\" class=\"form-control\" id=\"projectName\" name=\"projectName\" [(ngModel)]=\"project.name\" [ngModelOptions]=\"{standalone: true}\"\n                        aria-describedby=\"projectName\" placeholder=\"E.g. SCAVA\">\n                </div>\n                <div class=\"form-group\">\n                    <label for=\"description\">Description</label>\n                    <textarea class=\"form-control\" id=\"description\" name=\"description\" [(ngModel)]=\"project.description\" [ngModelOptions]=\"{standalone: true}\"\n                        rows=\"4\" placeholder=\"E.g.\"></textarea>\n                </div>\n                <div class=\"form-group\">\n                    <label for=\"homePage\">HomePage</label>\n                    <input type=\"text\" class=\"form-control\" id=\"homePage\" name=\"homePage\" [(ngModel)]=\"project.homepage\" [ngModelOptions]=\"{standalone: true}\"\n                        ngModel placeholder=\"E.g. www.scava.org\">\n                </div>\n                <div class=\"form-group\">\n                    <label for=\"informationSources\">Information Sources</label>\n                    <br/>\n                    <small>Click the button below to add new information sources to the project.</small>\n                    <br/>\n                    <br/>\n                    <small>Version Control Systems</small>\n                    <ul class=\"pagination\">\n                        <li>\n                            <button type=\"button\" class=\"btn btn-light\" (click)=\"addInformationSource('vcs', 'git')\">\n                                <span>Git</span>\n                            </button>\n                        </li>\n                        <li>\n                            <button type=\"button\" class=\"btn btn-light\" (click)=\"addInformationSource('vcs', 'svn')\">\n                                <span>SVN</span>\n                            </button>\n                        </li>\n                    </ul>\n                    <small>Issue Tracking Systems</small>\n                    <ul class=\"pagination\">\n                        <li>\n                            <button type=\"button\" class=\"btn btn-light\" (click)=\"addInformationSource('bts', 'bugzilla')\">\n                                <span>Bugzilla</span>\n                            </button>\n                        </li>\n                        <li>\n                            <button type=\"button\" class=\"btn btn-light\" (click)=\"addInformationSource('bts', 'sourceforge')\">\n                                <span>SourceForge</span>\n                            </button>\n                        </li>\n                        <li>\n                            <button type=\"button\" class=\"btn btn-light\" (click)=\"addInformationSource('bts', 'redmine')\">\n                                <span>Redmine</span>\n                            </button>\n                        </li>\n                    </ul>\n                    <small>Communication Channels</small>\n                    <ul class=\"pagination\">\n                        <li>\n                            <button type=\"button\" class=\"btn btn-light\" (click)=\"addInformationSource('communication_channels', 'nntp')\">\n                                <span>NNTP</span>\n                            </button>\n                        </li>\n                    </ul>\n                </div>\n            </div>\n            <div class=\"col-sm-6 col-md-offset-6 col-md-6 col-md-offset-6 col-lg-6 col-lg-offset-6\">\n                <div formArrayName=\"vcs\">\n                    <div *ngFor=\"let elem of form.get('vcs')['controls'];let i=index;\">\n                        <div [formGroupName]=\"i\">\n                            <div class=\"card\">\n                                <div class=\"card-header\">\n                                    <label>{{form.get('vcs').value[i].type | uppercase}} Repository</label>\n                                    <span class=\"pull-right clickable close-icon\" data-effect=\"fadeOut\" (click)=\"removeInformationSource('vcs', i)\">\n                                        <i class=\"fa fa-times\"></i>\n                                    </span>\n                                </div>\n                                <div class=\"card-body\">\n                                    <div class=\"form-group\" [hidden]=true>\n                                        <input type=\"text\" class=\"form-control\" id=\"type\" name=\"type\" formControlName=\"type\">\n                                    </div>\n                                    <div class=\"form-group\">\n                                        <label for=\"url\">URL</label>\n                                        <input type=\"text\" class=\"form-control\" id=\"url\" name=\"url\" formControlName=\"url\">\n                                    </div>\n                                </div>\n                            </div>\n                            <br/>\n                        </div>\n                    </div>\n                </div>\n\n                <div formArrayName=\"bts\">\n                    <div *ngFor=\"let elem of form.get('bts')['controls'];let i=index;\">\n                        <div [formGroupName]=\"i\">\n                            <div class=\"card\">\n                                <div class=\"card-header\">\n                                    <label>{{form.get('bts').value[i].type | uppercase}}</label>\n                                    <span class=\"pull-right clickable close-icon\" data-effect=\"fadeOut\" (click)=\"removeInformationSource('bts', i)\">\n                                        <i class=\"fa fa-times\"></i>\n                                    </span>\n                                </div>\n                                <div class=\"card-body\">\n                                    <div class=\"form-group\" [hidden]=true>\n                                        <input type=\"text\" class=\"form-control\" id=\"type\" name=\"type\" formControlName=\"type\">\n                                    </div>\n                                    <div class=\"form-group\">\n                                        <label for=\"url\">URL</label>\n                                        <input type=\"text\" class=\"form-control\" id=\"url\" name=\"url\" formControlName=\"url\">\n                                    </div>\n                                    <div *ngIf=\"form.get('bts').value[i].type === 'bugzilla'\">\n                                        <div class=\"form-group\">\n                                            <label for=\"product\">Product</label>\n                                            <input type=\"text\" class=\"form-control\" id=\"product\" name=\"product\" formControlName=\"product\">\n                                        </div>\n                                        <div class=\"form-group\">\n                                            <label for=\"component\">Component</label>\n                                            <input type=\"text\" class=\"form-control\" id=\"component\" name=\"component\" formControlName=\"component\">\n                                        </div>\n                                    </div>\n                                    <div *ngIf=\"form.get('bts').value[i].type === 'redmine'\">\n                                        <div class=\"form-group\">\n                                            <label for=\"name\">Name</label>\n                                            <input type=\"text\" class=\"form-control\" id=\"name\" name=\"name\" formControlName=\"name\">\n                                        </div>\n                                        <div class=\"form-group\">\n                                            <label for=\"project\">Project</label>\n                                            <input type=\"text\" class=\"form-control\" id=\"project\" name=\"project\" formControlName=\"project\">\n                                        </div>\n                                    </div>\n                                </div>\n                            </div>\n                            <br/>\n                        </div>\n                    </div>\n                </div>\n\n                <div formArrayName=\"communication_channels\">\n                    <div *ngFor=\"let elem of form.get('communication_channels')['controls'];let i=index;\">\n                        <div [formGroupName]=\"i\">\n                            <div class=\"card\">\n                                <div class=\"card-header\">\n                                    <label>Communication Channels</label>\n                                    <span class=\"pull-right clickable close-icon\" data-effect=\"fadeOut\" (click)=\"removeInformationSource('communication_channels', i)\">\n                                        <i class=\"fa fa-times\"></i>\n                                    </span>\n                                </div>\n                                <div class=\"card-body\">\n                                    <div class=\"form-group\" [hidden]=true>\n                                        <input type=\"text\" class=\"form-control\" id=\"type\" name=\"type\" formControlName=\"type\">\n                                    </div>\n                                    <div class=\"form-group\" [hidden]=true>\n                                        <input type=\"text\" class=\"form-control\" id=\"name\" name=\"name\" formControlName=\"name\">\n                                    </div>\n                                    <div class=\"form-group\">\n                                        <label for=\"url\">URL</label>\n                                        <input type=\"text\" class=\"form-control\" id=\"url\" name=\"url\" formControlName=\"url\">\n                                    </div>\n                                </div>\n                            </div>\n                            <br/>\n                        </div>\n                    </div>\n                </div>\n            </div>\n        </div>\n        <div class=\"row\">\n            <button type=\"submit\" [disabled]=\"!addProjectForm.form.valid || isSaving\" class=\"btn btn-primary\" class=\"block\">Register project</button>\n        </div>\n    </form>\n</div>"

/***/ }),

/***/ "./src/app/layout/project/components/create-project/create-project.component.scss":
/*!****************************************************************************************!*\
  !*** ./src/app/layout/project/components/create-project/create-project.component.scss ***!
  \****************************************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = ".block {\n  display: block;\n  width: 100%;\n  border: none;\n  color: #fff;\n  background-color: #1074B2;\n  border-color: #357ebd;\n  padding: 14px 28px;\n  font-size: 16px;\n  cursor: pointer;\n  text-align: center; }\n"

/***/ }),

/***/ "./src/app/layout/project/components/create-project/create-project.component.ts":
/*!**************************************************************************************!*\
  !*** ./src/app/layout/project/components/create-project/create-project.component.ts ***!
  \**************************************************************************************/
/*! exports provided: CreateProjectComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "CreateProjectComponent", function() { return CreateProjectComponent; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _angular_forms__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/forms */ "./node_modules/@angular/forms/fesm5/forms.js");
/* harmony import */ var _shared_services_api_server_create_project_service__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ../../../../shared/services/api-server/create-project.service */ "./src/app/shared/services/api-server/create-project.service.ts");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (undefined && undefined.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};



var CreateProjectComponent = /** @class */ (function () {
    function CreateProjectComponent(formBuilder, createProjectService) {
        this.formBuilder = formBuilder;
        this.createProjectService = createProjectService;
        this.project = {};
    }
    CreateProjectComponent.prototype.ngOnInit = function () {
        this.buildForm();
        this.isSaving = false;
    };
    CreateProjectComponent.prototype.buildForm = function () {
        this.form = this.formBuilder.group({
            vcs: this.formBuilder.array([]),
            communication_channels: this.formBuilder.array([]),
            bts: this.formBuilder.array([])
        });
    };
    CreateProjectComponent.prototype.addInformationSource = function (source, type) {
        var control = this.form.get(source);
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
    };
    CreateProjectComponent.prototype.createVersionControlSystems = function (type) {
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
    };
    CreateProjectComponent.prototype.createCommunicationChannels = function (type) {
        return this.formBuilder.group({
            'type': [type],
            'name': ['NNTP'],
            'url': [''],
        });
    };
    CreateProjectComponent.prototype.createIssueTrackingSystems = function (type) {
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
    };
    CreateProjectComponent.prototype.removeInformationSource = function (sourceName, index) {
        var control = this.form.get(sourceName);
        control.removeAt(index);
    };
    CreateProjectComponent.prototype.save = function () {
        this.isSaving = true;
        this.project.vcs = this.saveInformationSources('vcs');
        this.project.bts = this.saveInformationSources('bts');
        this.project.communication_channels = this.saveInformationSources('communication_channels');
        this.createProjectService.createProject(this.project).subscribe(function (resp) {
            console.log(resp);
            debugger;
        }, function (error) {
            console.log(error);
        });
    };
    CreateProjectComponent.prototype.saveInformationSources = function (sourceName) {
        var control = this.form.get(sourceName);
        return control.value;
    };
    CreateProjectComponent = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Component"])({
            selector: 'app-create-project',
            template: __webpack_require__(/*! ./create-project.component.html */ "./src/app/layout/project/components/create-project/create-project.component.html"),
            styles: [__webpack_require__(/*! ./create-project.component.scss */ "./src/app/layout/project/components/create-project/create-project.component.scss")]
        }),
        __metadata("design:paramtypes", [_angular_forms__WEBPACK_IMPORTED_MODULE_1__["FormBuilder"],
            _shared_services_api_server_create_project_service__WEBPACK_IMPORTED_MODULE_2__["CreateProjectService"]])
    ], CreateProjectComponent);
    return CreateProjectComponent;
}());



/***/ }),

/***/ "./src/app/layout/project/components/import-project/import-project.component.html":
/*!****************************************************************************************!*\
  !*** ./src/app/layout/project/components/import-project/import-project.component.html ***!
  \****************************************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "<div class=\"container\">\n    <div class=\"row\">\n        <div class=\"col-sm-12 col-md-12 col-lg-12\">\n            <h4>Import project</h4>\n            <hr>\n            <div>\n                <span>If your project is hosted on Eclipse, SourceForge, or GitHub, you can simply paste the URL below and we'll\n                    add it.</span>\n                <br/>\n                <span>E.g.\n                    <code>https://projects.eclipse.org/projects/modeling.epsilon</code> or\n                    <code>https://github.com/jrwilliams/gif-hook</code>\n                </span>\n            </div>\n            <br/>\n            <div>\n                <form #importProjectForm=\"ngForm\" novalidate name=\"importProjectForm\" novalidate (ngSubmit)=\"save()\">\n                    <div class=\"form-group\">\n                        <label for=\"projectName\">URL</label>\n                        <input type=\"text\" class=\"form-control\" name=\"url\" id=\"url\" [(ngModel)]=\"project.url\" aria-describedby=\"url\" placeholder=\"URL\">\n                    </div>\n                    <div class=\"form-group\">\n                        <button type=\"submit\" [disabled]=\"!importProjectForm.form.valid || isSaving\" class=\"btn btn-primary\">Import project</button>\n                    </div>\n                </form>\n            </div>\n        </div>\n    </div>"

/***/ }),

/***/ "./src/app/layout/project/components/import-project/import-project.component.scss":
/*!****************************************************************************************!*\
  !*** ./src/app/layout/project/components/import-project/import-project.component.scss ***!
  \****************************************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = ""

/***/ }),

/***/ "./src/app/layout/project/components/import-project/import-project.component.ts":
/*!**************************************************************************************!*\
  !*** ./src/app/layout/project/components/import-project/import-project.component.ts ***!
  \**************************************************************************************/
/*! exports provided: ImportProjectComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "ImportProjectComponent", function() { return ImportProjectComponent; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _shared_services_api_server_import_project_service__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ../../../../shared/services/api-server/import-project.service */ "./src/app/shared/services/api-server/import-project.service.ts");
/* harmony import */ var _angular_router__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! @angular/router */ "./node_modules/@angular/router/fesm5/router.js");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (undefined && undefined.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};



var ImportProjectComponent = /** @class */ (function () {
    function ImportProjectComponent(router, importProjectService) {
        this.router = router;
        this.importProjectService = importProjectService;
        this.project = {};
    }
    ImportProjectComponent.prototype.ngOnInit = function () {
        this.isSaving = false;
    };
    ImportProjectComponent.prototype.save = function () {
        this.isSaving = true;
        this.importProjectService.importProject(this.project).subscribe(function (resp) {
            console.log(resp);
        }, function (error) {
            console.log(error);
        });
    };
    ImportProjectComponent = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Component"])({
            selector: 'app-import-project',
            template: __webpack_require__(/*! ./import-project.component.html */ "./src/app/layout/project/components/import-project/import-project.component.html"),
            styles: [__webpack_require__(/*! ./import-project.component.scss */ "./src/app/layout/project/components/import-project/import-project.component.scss")]
        }),
        __metadata("design:paramtypes", [_angular_router__WEBPACK_IMPORTED_MODULE_2__["Router"],
            _shared_services_api_server_import_project_service__WEBPACK_IMPORTED_MODULE_1__["ImportProjectService"]])
    ], ImportProjectComponent);
    return ImportProjectComponent;
}());



/***/ }),

/***/ "./src/app/layout/project/project-routing.module.ts":
/*!**********************************************************!*\
  !*** ./src/app/layout/project/project-routing.module.ts ***!
  \**********************************************************/
/*! exports provided: ProjectRoutingModule */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "ProjectRoutingModule", function() { return ProjectRoutingModule; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _angular_router__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/router */ "./node_modules/@angular/router/fesm5/router.js");
/* harmony import */ var _project_component__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ./project.component */ "./src/app/layout/project/project.component.ts");
/* harmony import */ var _components_import_project_import_project_component__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! ./components/import-project/import-project.component */ "./src/app/layout/project/components/import-project/import-project.component.ts");
/* harmony import */ var _components_create_project_create_project_component__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! ./components/create-project/create-project.component */ "./src/app/layout/project/components/create-project/create-project.component.ts");
/* harmony import */ var _components_configure_project_configure_project_component__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! ./components/configure-project/configure-project.component */ "./src/app/layout/project/components/configure-project/configure-project.component.ts");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};






var routes = [
    {
        path: '', component: _project_component__WEBPACK_IMPORTED_MODULE_2__["ProjectComponent"],
    },
    {
        path: 'create', component: _components_create_project_create_project_component__WEBPACK_IMPORTED_MODULE_4__["CreateProjectComponent"]
    },
    {
        path: 'import', component: _components_import_project_import_project_component__WEBPACK_IMPORTED_MODULE_3__["ImportProjectComponent"]
    },
    {
        path: 'configure/:id', component: _components_configure_project_configure_project_component__WEBPACK_IMPORTED_MODULE_5__["ConfigureProjectComponent"]
    }
];
var ProjectRoutingModule = /** @class */ (function () {
    function ProjectRoutingModule() {
    }
    ProjectRoutingModule = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["NgModule"])({
            imports: [_angular_router__WEBPACK_IMPORTED_MODULE_1__["RouterModule"].forChild(routes)],
            exports: [_angular_router__WEBPACK_IMPORTED_MODULE_1__["RouterModule"]]
        })
    ], ProjectRoutingModule);
    return ProjectRoutingModule;
}());



/***/ }),

/***/ "./src/app/layout/project/project.component.html":
/*!*******************************************************!*\
  !*** ./src/app/layout/project/project.component.html ***!
  \*******************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "<link rel=\"stylesheet\" href=\"https://fonts.googleapis.com/icon?family=Material+Icons\" />\n\n<div class=\"pricing-table\">\n  <div *ngFor=\"let project of projectList\" class=\"pricing-option\">\n    <i class=\"material-icons\">layers</i>\n    <h1> {{project.name}}</h1>\n    <hr />\n    <div class=\"price-description\">\n      <p>{{project.description}}</p>\n    </div>\n    <hr />\n    <div class=\"price\">\n      <div class=\"front\">\n        <h4>\n          <span class=\"badge badge-dark\">100%</span>\n        </h4>\n      </div>\n      <div class=\"back\">7\n        <a [routerLink]=\"['/project/configure/', project.shortName]\"  class=\"button\">Configure</a>\n      </div>\n    </div>\n  </div>\n</div>"

/***/ }),

/***/ "./src/app/layout/project/project.component.scss":
/*!*******************************************************!*\
  !*** ./src/app/layout/project/project.component.scss ***!
  \*******************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "@import url(https://fonts.googleapis.com/css?family=Josefin+Sans:400,300,300italic,400italic,600,700,600italic,700italic);\nbody {\n  font-family: \"Josefin Sans\", sans-serif;\n  line-height: 1;\n  height: 100%;\n  background: #eee; }\n.pricing-table {\n  display: table;\n  width: 100%;\n  padding: 50px; }\n.pricing-table .pricing-option {\n  width: 30%;\n  background: white;\n  float: left;\n  padding: 2%;\n  text-align: center;\n  transition: all .3s ease-in-out;\n  box-shadow: 0px 2px 30px rgba(0, 0, 0, 0.3);\n  height: 350px;\n  margin: 1%; }\n.pricing-table .pricing-option:hover {\n  cursor: pointer;\n  box-shadow: 0px 2px 30px rgba(0, 0, 0, 0.3);\n  -webkit-transform: scale(1.04);\n  transform: scale(1.04); }\n.pricing-table .pricing-option:hover i, .pricing-table .pricing-option:hover h1, .pricing-table .pricing-option:hover span, .pricing-table .pricing-option:hover b {\n  color: #76a2ed; }\n.pricing-table .pricing-option:hover .front {\n  opacity: 0;\n  visibility: hidden; }\n.pricing-table .pricing-option:hover .back {\n  opacity: 1 !important;\n  visibility: visible !important; }\n.pricing-table .pricing-option:hover .back a.button {\n  -webkit-transform: translateY(0px) !important;\n  transform: translateY(0px) !important; }\n.pricing-table .pricing-option hr {\n  border: none;\n  border-bottom: 1px solid #F0F0F0; }\n.pricing-table .pricing-option i {\n  font-size: 3rem;\n  color: #D8D8D8;\n  transition: all .3s ease-in-out; }\n.pricing-table .pricing-option h1 {\n  margin: 10px 0;\n  color: #212121;\n  transition: all .3s ease-in-out; }\n.pricing-table .pricing-option p {\n  color: #999;\n  padding: 0 10px;\n  line-height: 1.3; }\n.pricing-table .pricing-option .price {\n  position: relative; }\n.price-description {\n  min-height: 80px; }\n.pricing-table .pricing-option .price .front span.price {\n  font-size: 2rem;\n  text-transform: uppercase;\n  margin-top: 20px;\n  display: block;\n  font-weight: 700;\n  position: relative; }\n.pricing-table .pricing-option .price .front span.price b {\n  position: absolute;\n  font-size: 1rem;\n  margin-left: 2px;\n  font-weight: 600; }\n.pricing-table .pricing-option .price .back {\n  opacity: 0;\n  visibility: hidden;\n  transition: all .3s ease-in-out; }\n.pricing-table .pricing-option .price .back a.button {\n  background: #76a2ed;\n  padding: 15px 20px;\n  display: inline-block;\n  text-decoration: none;\n  color: white;\n  position: absolute;\n  font-size: 13px;\n  top: -5px;\n  left: 0;\n  right: 0;\n  width: 150px;\n  margin: auto;\n  text-transform: uppercase;\n  -webkit-transform: translateY(20px);\n  transform: translateY(20px);\n  transition: all .3s ease-in-out; }\n.pricing-table .pricing-option .price .back a.button:hover {\n  background: #76a2ed; }\n@media screen and (max-width: 600px) {\n  .pricing-table .pricing-option {\n    padding: 5%;\n    width: 90%; }\n  .pricing-table .pricing-option:nth-child(even) {\n    margin: 30px 0 !important; } }\n"

/***/ }),

/***/ "./src/app/layout/project/project.component.ts":
/*!*****************************************************!*\
  !*** ./src/app/layout/project/project.component.ts ***!
  \*****************************************************/
/*! exports provided: ProjectComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "ProjectComponent", function() { return ProjectComponent; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _shared_services_project_service_list_project_service__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ../../shared/services/project-service/list-project.service */ "./src/app/shared/services/project-service/list-project.service.ts");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (undefined && undefined.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};


var ProjectComponent = /** @class */ (function () {
    function ProjectComponent(listProjectService) {
        this.listProjectService = listProjectService;
    }
    ProjectComponent.prototype.ngOnInit = function () {
        var _this = this;
        this.listProjectService.listProjects().subscribe(function (resp) {
            _this.projectList = resp;
        });
    };
    ProjectComponent = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Component"])({
            selector: 'app-project',
            template: __webpack_require__(/*! ./project.component.html */ "./src/app/layout/project/project.component.html"),
            styles: [__webpack_require__(/*! ./project.component.scss */ "./src/app/layout/project/project.component.scss")]
        }),
        __metadata("design:paramtypes", [_shared_services_project_service_list_project_service__WEBPACK_IMPORTED_MODULE_1__["ListProjectService"]])
    ], ProjectComponent);
    return ProjectComponent;
}());



/***/ }),

/***/ "./src/app/layout/project/project.module.ts":
/*!**************************************************!*\
  !*** ./src/app/layout/project/project.module.ts ***!
  \**************************************************/
/*! exports provided: ProjectModule */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "ProjectModule", function() { return ProjectModule; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _angular_common__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/common */ "./node_modules/@angular/common/fesm5/common.js");
/* harmony import */ var _project_routing_module__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ./project-routing.module */ "./src/app/layout/project/project-routing.module.ts");
/* harmony import */ var _project_component__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! ./project.component */ "./src/app/layout/project/project.component.ts");
/* harmony import */ var _components_import_project_import_project_component__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! ./components/import-project/import-project.component */ "./src/app/layout/project/components/import-project/import-project.component.ts");
/* harmony import */ var _components_create_project_create_project_component__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! ./components/create-project/create-project.component */ "./src/app/layout/project/components/create-project/create-project.component.ts");
/* harmony import */ var _components_configure_project_configure_project_component__WEBPACK_IMPORTED_MODULE_6__ = __webpack_require__(/*! ./components/configure-project/configure-project.component */ "./src/app/layout/project/components/configure-project/configure-project.component.ts");
/* harmony import */ var _angular_forms__WEBPACK_IMPORTED_MODULE_7__ = __webpack_require__(/*! @angular/forms */ "./node_modules/@angular/forms/fesm5/forms.js");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};








var ProjectModule = /** @class */ (function () {
    function ProjectModule() {
    }
    ProjectModule = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["NgModule"])({
            imports: [
                _angular_common__WEBPACK_IMPORTED_MODULE_1__["CommonModule"],
                _project_routing_module__WEBPACK_IMPORTED_MODULE_2__["ProjectRoutingModule"],
                _angular_forms__WEBPACK_IMPORTED_MODULE_7__["FormsModule"],
                _angular_forms__WEBPACK_IMPORTED_MODULE_7__["ReactiveFormsModule"]
            ],
            declarations: [_project_component__WEBPACK_IMPORTED_MODULE_3__["ProjectComponent"], _components_import_project_import_project_component__WEBPACK_IMPORTED_MODULE_4__["ImportProjectComponent"], _components_create_project_create_project_component__WEBPACK_IMPORTED_MODULE_5__["CreateProjectComponent"], _components_configure_project_configure_project_component__WEBPACK_IMPORTED_MODULE_6__["ConfigureProjectComponent"]]
        })
    ], ProjectModule);
    return ProjectModule;
}());



/***/ }),

/***/ "./src/app/shared/services/api-server/create-project.service.ts":
/*!**********************************************************************!*\
  !*** ./src/app/shared/services/api-server/create-project.service.ts ***!
  \**********************************************************************/
/*! exports provided: CreateProjectService */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "CreateProjectService", function() { return CreateProjectService; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _environments_environment__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ../../../../environments/environment */ "./src/environments/environment.ts");
/* harmony import */ var _angular_common_http__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! @angular/common/http */ "./node_modules/@angular/common/fesm5/http.js");
/* harmony import */ var _authentication_local_storage_service__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! ../authentication/local-storage.service */ "./src/app/shared/services/authentication/local-storage.service.ts");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (undefined && undefined.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};




var CreateProjectService = /** @class */ (function () {
    function CreateProjectService(httpClient, localStorageService) {
        this.httpClient = httpClient;
        this.localStorageService = localStorageService;
        this.resourceUrl = _environments_environment__WEBPACK_IMPORTED_MODULE_1__["environment"].SERVER_API_URL + '/administration';
        this.projects = 'projects';
        this.create = 'create';
        this.jwtToken = null;
    }
    CreateProjectService.prototype.createProject = function (project) {
        if (this.jwtToken == null) {
            this.jwtToken = this.localStorageService.loadToken();
        }
        // console.log(project);
        return this.httpClient.post(this.resourceUrl + "/" + this.projects + "/" + this.create, project, { headers: new _angular_common_http__WEBPACK_IMPORTED_MODULE_2__["HttpHeaders"]({ 'Authorization': this.jwtToken }) });
    };
    CreateProjectService = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Injectable"])({
            providedIn: 'root'
        }),
        __metadata("design:paramtypes", [_angular_common_http__WEBPACK_IMPORTED_MODULE_2__["HttpClient"],
            _authentication_local_storage_service__WEBPACK_IMPORTED_MODULE_3__["LocalStorageService"]])
    ], CreateProjectService);
    return CreateProjectService;
}());



/***/ }),

/***/ "./src/app/shared/services/api-server/import-project.service.ts":
/*!**********************************************************************!*\
  !*** ./src/app/shared/services/api-server/import-project.service.ts ***!
  \**********************************************************************/
/*! exports provided: ImportProjectService */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "ImportProjectService", function() { return ImportProjectService; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _angular_common_http__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/common/http */ "./node_modules/@angular/common/fesm5/http.js");
/* harmony import */ var _environments_environment__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ../../../../environments/environment */ "./src/environments/environment.ts");
/* harmony import */ var _authentication_local_storage_service__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! ../authentication/local-storage.service */ "./src/app/shared/services/authentication/local-storage.service.ts");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (undefined && undefined.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};




var ImportProjectService = /** @class */ (function () {
    function ImportProjectService(httpClient, localStorageService) {
        this.httpClient = httpClient;
        this.localStorageService = localStorageService;
        this.resourceUrl = _environments_environment__WEBPACK_IMPORTED_MODULE_2__["environment"].SERVER_API_URL + '/administration';
        this.projects = 'projects';
        this.import = 'import';
        this.jwtToken = null;
    }
    ImportProjectService.prototype.importProject = function (project) {
        if (this.jwtToken == null) {
            this.jwtToken = this.localStorageService.loadToken();
        }
        return this.httpClient.post(this.resourceUrl + "/" + this.projects + "/" + this.import, project, { headers: new _angular_common_http__WEBPACK_IMPORTED_MODULE_1__["HttpHeaders"]({ 'Authorization': this.jwtToken }) });
    };
    ImportProjectService = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Injectable"])({
            providedIn: 'root'
        }),
        __metadata("design:paramtypes", [_angular_common_http__WEBPACK_IMPORTED_MODULE_1__["HttpClient"],
            _authentication_local_storage_service__WEBPACK_IMPORTED_MODULE_3__["LocalStorageService"]])
    ], ImportProjectService);
    return ImportProjectService;
}());



/***/ }),

/***/ "./src/app/shared/services/project-service/list-project.service.ts":
/*!*************************************************************************!*\
  !*** ./src/app/shared/services/project-service/list-project.service.ts ***!
  \*************************************************************************/
/*! exports provided: ListProjectService */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "ListProjectService", function() { return ListProjectService; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _angular_common_http__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/common/http */ "./node_modules/@angular/common/fesm5/http.js");
/* harmony import */ var _environments_environment__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ../../../../environments/environment */ "./src/environments/environment.ts");
/* harmony import */ var _authentication_local_storage_service__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! ../authentication/local-storage.service */ "./src/app/shared/services/authentication/local-storage.service.ts");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (undefined && undefined.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};




var ListProjectService = /** @class */ (function () {
    function ListProjectService(httpClient, localStorageService) {
        this.httpClient = httpClient;
        this.localStorageService = localStorageService;
        this.resourceUrl = _environments_environment__WEBPACK_IMPORTED_MODULE_2__["environment"].SERVER_API_URL + '/administration';
        this.listServiceUrl = 'projects';
        this.projectServiceUrl = 'projects/p';
        this.jwtToken = null;
    }
    ListProjectService.prototype.listProjects = function () {
        if (this.jwtToken == null) {
            this.jwtToken = this.localStorageService.loadToken();
        }
        // console.log(`${this.resourceUrl}/${this.listServiceUrl}`);
        // console.log(this.jwtToken);
        return this.httpClient.get(this.resourceUrl + "/" + this.listServiceUrl, { headers: new _angular_common_http__WEBPACK_IMPORTED_MODULE_1__["HttpHeaders"]({ 'Authorization': this.jwtToken }) });
    };
    ListProjectService.prototype.getProject = function (projectid) {
        if (this.jwtToken == null) {
            this.jwtToken = this.localStorageService.loadToken();
        }
        // console.log(`${this.resourceUrl}/${this.projectServiceUrl}/${projectid}`);   
        return this.httpClient.get(this.resourceUrl + "/" + this.projectServiceUrl + "/" + projectid, { headers: new _angular_common_http__WEBPACK_IMPORTED_MODULE_1__["HttpHeaders"]({ 'Authorization': this.jwtToken }) });
    };
    ListProjectService = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Injectable"])({
            providedIn: 'root'
        }),
        __metadata("design:paramtypes", [_angular_common_http__WEBPACK_IMPORTED_MODULE_1__["HttpClient"],
            _authentication_local_storage_service__WEBPACK_IMPORTED_MODULE_3__["LocalStorageService"]])
    ], ListProjectService);
    return ListProjectService;
}());



/***/ })

}]);
//# sourceMappingURL=project-project-module.js.map