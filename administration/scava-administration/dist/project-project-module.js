(window["webpackJsonp"] = window["webpackJsonp"] || []).push([["project-project-module"],{

/***/ "./src/app/layout/project/components/add-project/add-project.component.html":
/*!**********************************************************************************!*\
  !*** ./src/app/layout/project/components/add-project/add-project.component.html ***!
  \**********************************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "<div class=\"container-fluid\">\n    <div class=\"row\">\n        <div class=\"col-sm-12 col-md-12 col-lg-12\">\n            <h4>Register project</h4>\n            <hr>\n        </div>\n        <div class=\"col-sm-6 col-md-6 col-lg-6\">\n            <div>\n                <form #addProjectForm=\"ngForm\" name=\"addProjectForm\" novalidate (ngSubmit)=\"save(addProjectForm.value)\">\n                    <div class=\"form-group\">\n                        <label for=\"projectName\">Project Name</label>\n                        <input type=\"text\" class=\"form-control\" id=\"projectName\" name=\"projectName\" [(ngModel)]=\"project.projectName\" [ngModelOptions]=\"{standalone: true}\"\n                            aria-describedby=\"projectName\" placeholder=\"E.g. SCAVA\">\n                    </div>\n                    <div class=\"form-group\">\n                        <label for=\"description\">Description</label>\n                        <textarea class=\"form-control\" id=\"description\" name=\"description\" [(ngModel)]=\"project.description\" [ngModelOptions]=\"{standalone: true}\"\n                            rows=\"4\" placeholder=\"E.g.\"></textarea>\n                    </div>\n                    <div class=\"form-group\">\n                        <label for=\"homePage\">HomePage</label>\n                        <input type=\"text\" class=\"form-control\" id=\"homePage\" name=\"homePage\" [(ngModel)]=\"project.homePage\" [ngModelOptions]=\"{standalone: true}\"\n                            ngModel placeholder=\"E.g. www.scava.org\">\n                    </div>\n                    <div class=\"form-group\">\n                        <label for=\"informationSources\">Information Sources</label>\n                        <br/>\n                        <small>Click the button below to add new information sources to the project.</small>\n                        <br/>\n                        <br/>\n                        <small>Version Control Systems</small>\n                        <ul class=\"pagination\">\n                            <li>\n                                <button type=\"button\" class=\"btn btn-light\" (click)=\"addInformationSource('git')\">\n                                    <span>Git</span>\n                                </button>\n                            </li>\n                            <li>\n                                <button type=\"button\" class=\"btn btn-light\" (click)=\"addInformationSource('svn')\">\n                                    <span>SVN</span>\n                                </button>\n                            </li>\n                        </ul>\n                        <small>Issue Tracking Systems</small>\n                        <ul class=\"pagination\">\n                            <li>\n                                <button type=\"button\" class=\"btn btn-light\" (click)=\"addInformationSource('bugzilla')\">\n                                    <span>Bugzilla</span>\n                                </button>\n                            </li>\n                            <li>\n                                <button type=\"button\" class=\"btn btn-light\" (click)=\"addInformationSource('sourceforge')\">\n                                    <span>SourceForge</span>\n                                </button>\n                            </li>\n                            <li>\n                                <button type=\"button\" class=\"btn btn-light\" (click)=\"addInformationSource('redmine')\">\n                                    <span>Redmine</span>\n                                </button>\n                            </li>\n                        </ul>\n                        <small>Communication Channels</small>\n                        <ul class=\"pagination\">\n                            <li>\n                                <button type=\"button\" class=\"btn btn-light\" (click)=\"addInformationSource('nntp')\">\n                                    <span>NNTP</span>\n                                </button>\n                            </li>\n                        </ul>\n                    </div>\n                </form>\n            </div>\n        </div>\n        <div [formGroup]=\"form\" class=\"col-sm-6 col-md-offset-6 col-md-6 col-md-offset-6 col-lg-6 col-lg-offset-6\">\n            <div formArrayName=\"git\">\n                <div *ngFor=\"let elem of form.get('git')['controls'];let i=index;\">\n                    <div [formGroupName]=\"i\">\n                        <div class=\"card\">\n                            <div class=\"card-header\">\n                                <label>Git Repository</label>\n                                <span class=\"pull-right clickable close-icon\" data-effect=\"fadeOut\" (click)=\"removeInformationSource('git')\">\n                                    <i class=\"fa fa-times\"></i>\n                                </span>\n                            </div>\n                            <div class=\"card-body\">\n                                <div class=\"form-group\">\n                                    <label for=\"homePage\">URL</label>\n                                    <input type=\"text\" class=\"form-control\" id=\"url\" name=\"url\" formControlName=\"url\">\n                                </div>\n                            </div>\n                        </div>\n                        <br/>\n                    </div>\n                </div>\n            </div>\n\n            <div formArrayName=\"svn\">\n                <div *ngFor=\"let elem of form.get('svn')['controls'];let i=index;\">\n                    <div [formGroupName]=\"i\">\n                        <div class=\"card\">\n                            <div class=\"card-header\">\n                                <label>Subversion Repository</label>\n                                <span class=\"pull-right clickable close-icon\" data-effect=\"fadeOut\" (click)=\"removeInformationSource('svn')\">\n                                    <i class=\"fa fa-times\"></i>\n                                </span>\n                            </div>\n                            <div class=\"card-body\">\n                                <div class=\"form-group\">\n                                    <label for=\"homePage\">URL</label>\n                                    <input type=\"text\" class=\"form-control\" id=\"url\" name=\"url\" formControlName=\"url\">\n                                </div>\n                            </div>\n                        </div>\n                        <br/>\n                    </div>\n                </div>\n            </div>\n\n            <div formArrayName=\"bugzilla\">\n                <div *ngFor=\"let elem of form.get('bugzilla')['controls'];let i=index;\">\n                    <div [formGroupName]=\"i\">\n                        <div class=\"card\">\n                            <div class=\"card-header\">\n                                <label>Bugzilla</label>\n                                <span class=\"pull-right clickable close-icon\" data-effect=\"fadeOut\" (click)=\"removeInformationSource('bugzilla')\">\n                                    <i class=\"fa fa-times\"></i>\n                                </span>\n                            </div>\n                            <div class=\"card-body\">\n                                <div class=\"form-group\">\n                                    <label for=\"homePage\">URL</label>\n                                    <input type=\"text\" class=\"form-control\" id=\"url\" name=\"url\">\n                                </div>\n                                <div class=\"form-group\">\n                                    <label for=\"product\">Product</label>\n                                    <input type=\"text\" class=\"form-control\" id=\"product\" name=\"product\">\n                                </div>\n                                <div class=\"form-group\">\n                                    <label for=\"component\">Component</label>\n                                    <input type=\"text\" class=\"form-control\" id=\"component\" name=\"component\">\n                                </div>\n                            </div>\n                        </div>\n                        <br/>\n                    </div>\n                </div>\n            </div>\n\n            <div formArrayName=\"sourceforge\">\n                <div *ngFor=\"let elem of form.get('sourceforge')['controls'];let i=index;\">\n                    <div [formGroupName]=\"i\">\n                        <div class=\"card\">\n                            <div class=\"card-header\">\n                                <label>SourceForge Issues</label>\n                                <span class=\"pull-right clickable close-icon\" data-effect=\"fadeOut\" (click)=\"removeInformationSource('sourceforge')\">\n                                    <i class=\"fa fa-times\"></i>\n                                </span>\n                            </div>\n                            <div class=\"card-body\">\n                                <div class=\"form-group\">\n                                    <label for=\"url\">URL</label>\n                                    <input type=\"text\" class=\"form-control\" id=\"url\" name=\"url\">\n                                </div>\n                            </div>\n                        </div>\n                        <br/>\n                    </div>\n                </div>\n            </div>\n\n            <div formArrayName=\"redmine\">\n                <div *ngFor=\"let elem of form.get('redmine')['controls'];let i=index;\">\n                    <div [formGroupName]=\"i\">\n                        <div class=\"card\">\n                            <div class=\"card-header\">\n                                <label>Redmine</label>\n                                <span class=\"pull-right clickable close-icon\" data-effect=\"fadeOut\" (click)=\"removeInformationSource('redmine')\">\n                                    <i class=\"fa fa-times\"></i>\n                                </span>\n                            </div>\n                            <div class=\"card-body\">\n                                <div class=\"form-group\">\n                                    <label for=\"url\">URL</label>\n                                    <input type=\"text\" class=\"form-control\" id=\"url\" name=\"url\">\n                                </div>\n                                <div class=\"form-group\">\n                                    <label for=\"name\">Name</label>\n                                    <input type=\"text\" class=\"form-control\" id=\"name\" name=\"name\">\n                                </div>\n                                <div class=\"form-group\">\n                                    <label for=\"project\">Project</label>\n                                    <input type=\"text\" class=\"form-control\" id=\"project\" name=\"project\">\n                                </div>\n                            </div>\n                        </div>\n                        <br/>\n                    </div>\n                </div>\n            </div>\n\n            <div formArrayName=\"nntp\">\n                <div *ngFor=\"let elem of form.get('nntp')['controls'];let i=index;\">\n                    <div [formGroupName]=\"i\">\n                        <div class=\"card\">\n                            <div class=\"card-header\">\n                                <label>Communication Channels</label>\n                                <span class=\"pull-right clickable close-icon\" data-effect=\"fadeOut\" (click)=\"removeInformationSource('nntp')\">\n                                    <i class=\"fa fa-times\"></i>\n                                </span>\n                            </div>\n                            <div class=\"card-body\">\n                                <div class=\"form-group\">\n                                    <label for=\"url\">URL</label>\n                                    <input type=\"text\" class=\"form-control\" id=\"url\" name=\"url\">\n                                </div>\n                            </div>\n                        </div>\n                    </div>\n                </div>\n            </div>\n        </div>\n    </div>\n    <br/>\n    <div class=\"row\">\n        <button type=\"button\" class=\"block\">Register</button>\n    </div>\n</div>\n"

/***/ }),

/***/ "./src/app/layout/project/components/add-project/add-project.component.scss":
/*!**********************************************************************************!*\
  !*** ./src/app/layout/project/components/add-project/add-project.component.scss ***!
  \**********************************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = ".block {\n  display: block;\n  width: 100%;\n  border: none;\n  color: #fff;\n  background-color: #1074B2;\n  border-color: #357ebd;\n  padding: 14px 28px;\n  font-size: 16px;\n  cursor: pointer;\n  text-align: center; }\n"

/***/ }),

/***/ "./src/app/layout/project/components/add-project/add-project.component.ts":
/*!********************************************************************************!*\
  !*** ./src/app/layout/project/components/add-project/add-project.component.ts ***!
  \********************************************************************************/
/*! exports provided: AddProjectComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "AddProjectComponent", function() { return AddProjectComponent; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _angular_forms__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/forms */ "./node_modules/@angular/forms/fesm5/forms.js");
/* harmony import */ var _shared_services_api_server_add_project_service__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ../../../../shared/services/api-server/add-project.service */ "./src/app/shared/services/api-server/add-project.service.ts");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (undefined && undefined.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};



var AddProjectComponent = /** @class */ (function () {
    function AddProjectComponent(formBuilder, addProjectService) {
        this.formBuilder = formBuilder;
        this.addProjectService = addProjectService;
        this.project = {};
    }
    AddProjectComponent.prototype.ngOnInit = function () {
        this.buildForm();
    };
    AddProjectComponent.prototype.buildForm = function () {
        this.form = this.formBuilder.group({
            git: this.formBuilder.array([]),
            svn: this.formBuilder.array([]),
            bugzilla: this.formBuilder.array([]),
            sourceforge: this.formBuilder.array([]),
            redmine: this.formBuilder.array([]),
            nntp: this.formBuilder.array([]),
        });
    };
    AddProjectComponent.prototype.addInformationSource = function (source) {
        var control = this.form.get(source);
        switch (source) {
            case 'git':
                control.push(this.createGitRepository());
                break;
            case 'svn':
                control.push(this.createSVNRepository());
                break;
            case 'bugzilla':
                control.push(this.createBugzilla());
                break;
            case 'sourceforge':
                control.push(this.createSourceForgeIssues());
                break;
            case 'redmine':
                control.push(this.createRedmine());
                break;
            case 'nntp':
                control.push(this.createNNTP());
                break;
            default:
                break;
        }
    };
    AddProjectComponent.prototype.createGitRepository = function () {
        return this.formBuilder.group({
            'url': ['']
        });
    };
    AddProjectComponent.prototype.createSVNRepository = function () {
        return this.formBuilder.group({
            'url': ['']
        });
    };
    AddProjectComponent.prototype.createBugzilla = function () {
        return this.formBuilder.group({
            'url': [''],
            'product': [''],
            'component': [''],
        });
    };
    AddProjectComponent.prototype.createSourceForgeIssues = function () {
        return this.formBuilder.group({
            'url': ['']
        });
    };
    AddProjectComponent.prototype.createRedmine = function () {
        return this.formBuilder.group({
            'url': [''],
            'name': [''],
            'project': [''],
        });
    };
    AddProjectComponent.prototype.createNNTP = function () {
        return this.formBuilder.group({
            'url': ['']
        });
    };
    AddProjectComponent.prototype.removeInformationSource = function (sourceName, index) {
        var control = this.form.get(sourceName);
        control.removeAt(index);
    };
    AddProjectComponent.prototype.save = function () {
    };
    AddProjectComponent = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Component"])({
            selector: 'app-add-project',
            template: __webpack_require__(/*! ./add-project.component.html */ "./src/app/layout/project/components/add-project/add-project.component.html"),
            styles: [__webpack_require__(/*! ./add-project.component.scss */ "./src/app/layout/project/components/add-project/add-project.component.scss")]
        }),
        __metadata("design:paramtypes", [_angular_forms__WEBPACK_IMPORTED_MODULE_1__["FormBuilder"],
            _shared_services_api_server_add_project_service__WEBPACK_IMPORTED_MODULE_2__["AddProjectService"]])
    ], AddProjectComponent);
    return AddProjectComponent;
}());



/***/ }),

/***/ "./src/app/layout/project/components/import-project/import-project.component.html":
/*!****************************************************************************************!*\
  !*** ./src/app/layout/project/components/import-project/import-project.component.html ***!
  \****************************************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "<div class=\"container\">\n    <div class=\"row\">\n        <div class=\"col-sm-12 col-md-12 col-lg-12\">\n            <h4>Import project</h4>\n            <hr>\n            <div>\n                <span>If your project is hosted on Eclipse, SourceForge, or GitHub, you can simply paste the URL below and we'll\n                    add it.</span>\n                <br/>\n                <span>E.g.\n                    <code>https://projects.eclipse.org/projects/modeling.epsilon</code> or\n                    <code>https://github.com/jrwilliams/gif-hook</code>\n                </span>\n            </div>\n            <br/>\n            <div>\n                <form #importProjectForm=\"ngForm\" novalidate name=\"importProjectForm\" novalidate (ngSubmit)=\"save()\">\n                    <div class=\"form-group\">\n                        <label for=\"projectName\">URL</label>\n                        <input type=\"text\" class=\"form-control\" name=\"url\" id=\"url\" [(ngModel)]=\"project.url\" aria-describedby=\"url\" placeholder=\"URL\">\n                    </div>\n                    <div class=\"form-group\">\n                        <button type=\"submit\" [disabled]=\"!importProjectForm.form.valid || isSaving\" class=\"btn btn-primary\">Import project</button>\n                    </div>\n                </form>\n            </div>\n        </div>\n    </div>\n</div>\n"

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
        this.isSaving = false;
    }
    ImportProjectComponent.prototype.ngOnInit = function () {
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
/* harmony import */ var _components_add_project_add_project_component__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! ./components/add-project/add-project.component */ "./src/app/layout/project/components/add-project/add-project.component.ts");
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
        path: 'add', component: _components_add_project_add_project_component__WEBPACK_IMPORTED_MODULE_4__["AddProjectComponent"]
    },
    {
        path: 'import', component: _components_import_project_import_project_component__WEBPACK_IMPORTED_MODULE_3__["ImportProjectComponent"]
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

module.exports = "<p>\n  projects works!\n</p>\n"

/***/ }),

/***/ "./src/app/layout/project/project.component.scss":
/*!*******************************************************!*\
  !*** ./src/app/layout/project/project.component.scss ***!
  \*******************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = ""

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
    function ProjectComponent() {
    }
    ProjectComponent.prototype.ngOnInit = function () {
    };
    ProjectComponent = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Component"])({
            selector: 'app-project',
            template: __webpack_require__(/*! ./project.component.html */ "./src/app/layout/project/project.component.html"),
            styles: [__webpack_require__(/*! ./project.component.scss */ "./src/app/layout/project/project.component.scss")]
        }),
        __metadata("design:paramtypes", [])
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
/* harmony import */ var _components_add_project_add_project_component__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! ./components/add-project/add-project.component */ "./src/app/layout/project/components/add-project/add-project.component.ts");
/* harmony import */ var _angular_forms__WEBPACK_IMPORTED_MODULE_6__ = __webpack_require__(/*! @angular/forms */ "./node_modules/@angular/forms/fesm5/forms.js");
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
                _angular_forms__WEBPACK_IMPORTED_MODULE_6__["FormsModule"],
                _angular_forms__WEBPACK_IMPORTED_MODULE_6__["ReactiveFormsModule"]
            ],
            declarations: [_project_component__WEBPACK_IMPORTED_MODULE_3__["ProjectComponent"], _components_import_project_import_project_component__WEBPACK_IMPORTED_MODULE_4__["ImportProjectComponent"], _components_add_project_add_project_component__WEBPACK_IMPORTED_MODULE_5__["AddProjectComponent"]]
        })
    ], ProjectModule);
    return ProjectModule;
}());



/***/ }),

/***/ "./src/app/shared/services/api-server/add-project.service.ts":
/*!*******************************************************************!*\
  !*** ./src/app/shared/services/api-server/add-project.service.ts ***!
  \*******************************************************************/
/*! exports provided: AddProjectService */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "AddProjectService", function() { return AddProjectService; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _environments_environment__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ../../../../environments/environment */ "./src/environments/environment.ts");
/* harmony import */ var _angular_common_http__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! @angular/common/http */ "./node_modules/@angular/common/fesm5/http.js");
/* harmony import */ var _shared_shared_data_service__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! ../shared/shared-data.service */ "./src/app/shared/services/shared/shared-data.service.ts");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (undefined && undefined.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};




var AddProjectService = /** @class */ (function () {
    function AddProjectService(httpClient, sharedDataService) {
        this.httpClient = httpClient;
        this.sharedDataService = sharedDataService;
        this.resourceUrl = _environments_environment__WEBPACK_IMPORTED_MODULE_1__["environment"].SERVER_API_URL + '/administration';
        this.projects = 'projects';
        this.add = 'add';
        this.jwtToken = null;
    }
    AddProjectService.prototype.addProject = function (project) {
        if (this.jwtToken == null) {
            this.jwtToken = this.sharedDataService.loadToken();
        }
        return this.httpClient.post(this.resourceUrl + "/" + this.projects + "/" + this.add, project, { headers: new _angular_common_http__WEBPACK_IMPORTED_MODULE_2__["HttpHeaders"]({ 'Authorization': this.jwtToken }) });
    };
    AddProjectService = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Injectable"])({
            providedIn: 'root'
        }),
        __metadata("design:paramtypes", [_angular_common_http__WEBPACK_IMPORTED_MODULE_2__["HttpClient"],
            _shared_shared_data_service__WEBPACK_IMPORTED_MODULE_3__["SharedDataService"]])
    ], AddProjectService);
    return AddProjectService;
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
/* harmony import */ var _shared_shared_data_service__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ../shared/shared-data.service */ "./src/app/shared/services/shared/shared-data.service.ts");
/* harmony import */ var _environments_environment__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! ../../../../environments/environment */ "./src/environments/environment.ts");
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
    function ImportProjectService(httpClient, sharedDataService) {
        this.httpClient = httpClient;
        this.sharedDataService = sharedDataService;
        this.resourceUrl = _environments_environment__WEBPACK_IMPORTED_MODULE_3__["environment"].SERVER_API_URL + '/administration';
        this.projects = 'projects';
        this.import = 'import';
        this.jwtToken = null;
    }
    ImportProjectService.prototype.importProject = function (project) {
        if (this.jwtToken == null) {
            this.jwtToken = this.sharedDataService.loadToken();
        }
        return this.httpClient.post(this.resourceUrl + "/" + this.projects + "/" + this.import, project, { headers: new _angular_common_http__WEBPACK_IMPORTED_MODULE_1__["HttpHeaders"]({ 'Authorization': this.jwtToken }) });
    };
    ImportProjectService = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Injectable"])({
            providedIn: 'root'
        }),
        __metadata("design:paramtypes", [_angular_common_http__WEBPACK_IMPORTED_MODULE_1__["HttpClient"],
            _shared_shared_data_service__WEBPACK_IMPORTED_MODULE_2__["SharedDataService"]])
    ], ImportProjectService);
    return ImportProjectService;
}());



/***/ })

}]);
//# sourceMappingURL=project-project-module.js.map