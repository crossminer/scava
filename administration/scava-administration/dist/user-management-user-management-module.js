(window["webpackJsonp"] = window["webpackJsonp"] || []).push([["user-management-user-management-module"],{

/***/ "./src/app/layout/user-management/user-management-delete-dialog/user-management-delete-dialog.component.html":
/*!*******************************************************************************************************************!*\
  !*** ./src/app/layout/user-management/user-management-delete-dialog/user-management-delete-dialog.component.html ***!
  \*******************************************************************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "<form name=\"deleteForm\" (ngSubmit)=\"confirmDelete(user.login)\">\n    <div class=\"modal-header\">\n        <h4 class=\"modal-title\">Confirm delete operation</h4>\n        <button type=\"button\" class=\"close\" data-dismiss=\"modal\" aria-hidden=\"true\"\n            (click)=\"clear()\">&times;</button>\n    </div>\n    <div class=\"modal-body\">\n        <p>Are you sure you want to delete this User?</p>\n    </div>\n    <div class=\"modal-footer\">\n        <button type=\"button\" class=\"btn btn-secondary\" data-dismiss=\"modal\" (click)=\"clear()\">\n            <i class=\"fa fa-ban\"></i>&nbsp;<span>Cancel</span>\n        </button>&nbsp;\n        <button type=\"submit\" class=\"btn btn-danger\">\n            <i class=\"fa fa-times\"></i>&nbsp;<span>Delete</span>\n        </button>\n    </div>\n</form>\n"

/***/ }),

/***/ "./src/app/layout/user-management/user-management-delete-dialog/user-management-delete-dialog.component.ts":
/*!*****************************************************************************************************************!*\
  !*** ./src/app/layout/user-management/user-management-delete-dialog/user-management-delete-dialog.component.ts ***!
  \*****************************************************************************************************************/
/*! exports provided: UserMgmtDeleteDialogComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "UserMgmtDeleteDialogComponent", function() { return UserMgmtDeleteDialogComponent; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _ng_bootstrap_ng_bootstrap__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @ng-bootstrap/ng-bootstrap */ "./node_modules/@ng-bootstrap/ng-bootstrap/index.js");
/* harmony import */ var _shared_services_user_management_user_management_service__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ../../../shared/services/user-management/user-management.service */ "./src/app/shared/services/user-management/user-management.service.ts");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (undefined && undefined.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};



var UserMgmtDeleteDialogComponent = /** @class */ (function () {
    function UserMgmtDeleteDialogComponent(userManagementService, activeModal) {
        this.userManagementService = userManagementService;
        this.activeModal = activeModal;
    }
    UserMgmtDeleteDialogComponent.prototype.clear = function () {
        this.activeModal.dismiss('cancel');
    };
    UserMgmtDeleteDialogComponent.prototype.confirmDelete = function (login) {
        var _this = this;
        this.userManagementService.delete(login).subscribe(function (response) {
            _this.activeModal.dismiss(true);
        });
    };
    UserMgmtDeleteDialogComponent = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Component"])({
            selector: 'jhi-user-mgmt-delete-dialog',
            template: __webpack_require__(/*! ./user-management-delete-dialog.component.html */ "./src/app/layout/user-management/user-management-delete-dialog/user-management-delete-dialog.component.html")
        }),
        __metadata("design:paramtypes", [_shared_services_user_management_user_management_service__WEBPACK_IMPORTED_MODULE_2__["UserManagementService"],
            _ng_bootstrap_ng_bootstrap__WEBPACK_IMPORTED_MODULE_1__["NgbActiveModal"]])
    ], UserMgmtDeleteDialogComponent);
    return UserMgmtDeleteDialogComponent;
}());



/***/ }),

/***/ "./src/app/layout/user-management/user-management-routing.module.ts":
/*!**************************************************************************!*\
  !*** ./src/app/layout/user-management/user-management-routing.module.ts ***!
  \**************************************************************************/
/*! exports provided: UserManagementRoutingModule */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "UserManagementRoutingModule", function() { return UserManagementRoutingModule; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _angular_router__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/router */ "./node_modules/@angular/router/fesm5/router.js");
/* harmony import */ var _user_management_component__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ./user-management.component */ "./src/app/layout/user-management/user-management.component.ts");
/* harmony import */ var _user_management_update_user_management_update_component__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! ./user-management-update/user-management-update.component */ "./src/app/layout/user-management/user-management-update/user-management-update.component.ts");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};




var routes = [
    {
        path: '',
        component: _user_management_component__WEBPACK_IMPORTED_MODULE_2__["UserManagementComponent"],
    },
    {
        path: ':login/edit',
        component: _user_management_update_user_management_update_component__WEBPACK_IMPORTED_MODULE_3__["UserManagementUpdateComponent"]
    }
];
var UserManagementRoutingModule = /** @class */ (function () {
    function UserManagementRoutingModule() {
    }
    UserManagementRoutingModule = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["NgModule"])({
            imports: [_angular_router__WEBPACK_IMPORTED_MODULE_1__["RouterModule"].forChild(routes)],
            exports: [_angular_router__WEBPACK_IMPORTED_MODULE_1__["RouterModule"]]
        })
    ], UserManagementRoutingModule);
    return UserManagementRoutingModule;
}());



/***/ }),

/***/ "./src/app/layout/user-management/user-management-update/user-management-update.component.html":
/*!*****************************************************************************************************!*\
  !*** ./src/app/layout/user-management/user-management-update/user-management-update.component.html ***!
  \*****************************************************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "<div class=\"container\">\n  <div class=\"row justify-content-center\">\n    <div class=\"col-8\">\n      \n      <form name=\"editForm\" role=\"form\" novalidate (ngSubmit)=\"save()\" #editForm=\"ngForm\">\n        <h2>Edit a user</h2>\n        <div>\n          <div class=\"form-group\">\n            <label class=\"form-control-label\">Login</label>\n            <input type=\"text\" class=\"form-control\" name=\"login\" [(ngModel)]=\"user.login\" required minlength=\"1\" \n                maxlength=\"50\" pattern=\"^[_.@A-Za-z0-9-]*$\">\n            <!--\n            <div *ngIf=\"loginInput.dirty && loginInput.invalid\">\n              <small class=\"form-text text-danger\" *ngIf=\"loginInput.errors.required\">\n                This field is required.\n              </small>\n\n              <small class=\"form-text text-danger\" *ngIf=\"loginInput.errors.maxlength\">\n                This field cannot be longer than 50 characters.\n              </small>\n\n              <small class=\"form-text text-danger\" *ngIf=\"loginInput.errors.pattern\">\n                This field can only contain letters, digits and e-mail addresses.\n              </small>\n            </div>\n            -->\n          </div>\n          <div class=\"form-group\">\n            <label class=\"form-control-label\">Email</label>\n            <input type=\"email\" class=\"form-control\" name=\"email\" [(ngModel)]=\"user.email\" minlength=\"5\" required\n              maxlength=\"254\" email>\n            <!--\n            <div *ngIf=\"emailInput.dirty && emailInput.invalid\">\n              <small class=\"form-text text-danger\" *ngIf=\"emailInput.errors.required\">\n                This field is required.\n              </small>\n\n              <small class=\"form-text text-danger\" *ngIf=\"emailInput.errors.maxlength\">\n                This field cannot be longer than 100 characters.\n              </small>\n\n              <small class=\"form-text text-danger\" *ngIf=\"emailInput.errors.minlength\">\n                This field is required to be at least 5 characters.\n              </small>\n\n              <small class=\"form-text text-danger\" *ngIf=\"emailInput.errors.email\">\n                Your email is invalid.\n              </small>\n            </div>\n            -->\n          </div>\n          <div class=\"form-check\">\n            <label class=\"form-check-label\" for=\"activated\">\n              <input class=\"form-check-input\" type=\"checkbox\" id=\"activated\" name=\"activated\" [(ngModel)]=\"user.activated\" >\n              <span>Activated</span>\n            </label>\n          </div>\n\n          <div class=\"form-group\">\n            <label>Profiles</label>\n            <select class=\"form-control\" multiple name=\"authority\" [(ngModel)]=\"user.authorities\">\n              <option *ngFor=\"let authority of authorities\" [value]=\"authority\">{{authority}}</option>\n              <option value=\"\"></option>\n            </select>\n          </div>\n        </div>\n        <div>\n          <button type=\"button\" class=\"btn btn-secondary\" (click)=\"previousState()\">\n            <i class=\"fa fa-ban\"></i>&nbsp;\n            <span>Cancel</span>\n          </button>&nbsp;\n          <button type=\"submit\" [disabled]=\"editForm.form.invalid || isSaving\" class=\"btn btn-primary\">\n            <i class=\"fa fa-save\"></i>&nbsp;\n            <span>Save</span>\n          </button>\n        </div>\n      </form>\n      \n    </div>\n  </div>\n</div>"

/***/ }),

/***/ "./src/app/layout/user-management/user-management-update/user-management-update.component.scss":
/*!*****************************************************************************************************!*\
  !*** ./src/app/layout/user-management/user-management-update/user-management-update.component.scss ***!
  \*****************************************************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = ""

/***/ }),

/***/ "./src/app/layout/user-management/user-management-update/user-management-update.component.ts":
/*!***************************************************************************************************!*\
  !*** ./src/app/layout/user-management/user-management-update/user-management-update.component.ts ***!
  \***************************************************************************************************/
/*! exports provided: UserManagementUpdateComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "UserManagementUpdateComponent", function() { return UserManagementUpdateComponent; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _user_model__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ../user-model */ "./src/app/layout/user-management/user-model.ts");
/* harmony import */ var _angular_router__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! @angular/router */ "./node_modules/@angular/router/fesm5/router.js");
/* harmony import */ var _shared_services_user_management_user_management_service__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! ../../../shared/services/user-management/user-management.service */ "./src/app/shared/services/user-management/user-management.service.ts");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (undefined && undefined.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};




var UserManagementUpdateComponent = /** @class */ (function () {
    function UserManagementUpdateComponent(userManagementService, route, router) {
        this.userManagementService = userManagementService;
        this.route = route;
        this.router = router;
        this.authorities = ['ROLE_ADMIN', 'ROLE_PROJECT_MANAGER'];
    }
    UserManagementUpdateComponent.prototype.ngOnInit = function () {
        this.loadAll();
    };
    UserManagementUpdateComponent.prototype.loadAll = function () {
        var _this = this;
        this.isSaving = false;
        this.user = new _user_model__WEBPACK_IMPORTED_MODULE_1__["User"]();
        this.route.paramMap.subscribe(function (data) {
            _this.userManagementService.find(data.get('login')).subscribe(function (user) {
                _this.user = user;
            });
        });
    };
    UserManagementUpdateComponent.prototype.save = function () {
        var _this = this;
        this.isSaving = true;
        if (this.user.authorities.includes('')) {
            this.user.authorities.pop();
        }
        this.user.authorities.unshift('ROLE_USER');
        this.userManagementService.update(this.user).subscribe(function (success) { return _this.onSaveSuccess(success); }, function (error) { return _this.onSaveError(); });
    };
    UserManagementUpdateComponent.prototype.previousState = function () {
        this.router.navigate(['/user-management']);
    };
    UserManagementUpdateComponent.prototype.onSaveSuccess = function (result) {
        this.isSaving = true;
        this.previousState();
    };
    UserManagementUpdateComponent.prototype.onSaveError = function () {
        this.isSaving = false;
    };
    UserManagementUpdateComponent = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Component"])({
            selector: 'app-user-management-update',
            template: __webpack_require__(/*! ./user-management-update.component.html */ "./src/app/layout/user-management/user-management-update/user-management-update.component.html"),
            styles: [__webpack_require__(/*! ./user-management-update.component.scss */ "./src/app/layout/user-management/user-management-update/user-management-update.component.scss")]
        }),
        __metadata("design:paramtypes", [_shared_services_user_management_user_management_service__WEBPACK_IMPORTED_MODULE_3__["UserManagementService"],
            _angular_router__WEBPACK_IMPORTED_MODULE_2__["ActivatedRoute"],
            _angular_router__WEBPACK_IMPORTED_MODULE_2__["Router"]])
    ], UserManagementUpdateComponent);
    return UserManagementUpdateComponent;
}());



/***/ }),

/***/ "./src/app/layout/user-management/user-management.component.html":
/*!***********************************************************************!*\
  !*** ./src/app/layout/user-management/user-management.component.html ***!
  \***********************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "<div class=\"container\">\n  <div class=\"table-responsive\">\n    <table class=\"table table-striped\">\n      <thead>\n        <tr>\n          <th>\n            <span>Login</span>\n          </th>\n          <th>\n            <span>Email</span>\n          </th>\n          <th>\n          </th>\n          <th>\n            <span>Profiles</span>\n          </th>\n          <th>\n          </th>\n        </tr>\n      </thead>\n      <tbody>\n        <tr *ngFor=\"let user of users\">\n          <td>{{user.login}}</td>\n          <td>{{user.email}}</td>\n          <td>\n            <button class=\"btn btn-danger btn-sm\" *ngIf=\"!user.activated\" (click)=\"setActive(user, true)\">Deactivated</button>\n            <button class=\"btn btn-success btn-sm\" *ngIf=\"user.activated\" (click)=\"setActive(user, false)\">Activated</button>\n\n          </td>\n          <td>\n            <div *ngFor=\"let authorithy of user.authorities\">\n              <div *ngIf=\"authorithy !== 'ROLE_USER'\">\n                {{authorithy}}\n              </div>\n              <div *ngIf=\"authorithy == 'ROLE_USER' && user.authorities.length == 1\">\n                  NO ROLES ARE ASSINGED TO THIS USER\n              </div>\n            </div>\n          </td>\n          <td class=\"text-right\">\n            <div class=\"btn-group flex-btn-group-container\">\n              <button type=\"submit\" [routerLink]=\"['./', user.login, 'edit']\" queryParamsHandling=\"merge\" class=\"btn btn-primary btn-sm\">\n                <i class=\"fa fa-pencil\" aria-hidden=\"true\"></i>&nbsp;\n                <span class=\"d-none d-md-inline\">Edit</span>\n              </button>\n              <button type=\"button\" (click)=\"deleteUser(user)\" class=\"btn btn-danger btn-sm\">\n                <i class=\"fa fa-times\" aria-hidden=\"true\"></i>&nbsp;\n                <span class=\"d-none d-md-inline\">Delete</span>\n              </button>\n            </div>\n          </td>\n        </tr>\n      </tbody>\n    </table>\n  </div>\n</div>"

/***/ }),

/***/ "./src/app/layout/user-management/user-management.component.scss":
/*!***********************************************************************!*\
  !*** ./src/app/layout/user-management/user-management.component.scss ***!
  \***********************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = ".switch input {\n  display: none; }\n\n.switch {\n  display: inline-block;\n  width: 50px;\n  height: 20px;\n  margin: 4px;\n  -webkit-transform: translateY(50%);\n          transform: translateY(50%);\n  position: relative; }\n\n.slider {\n  position: absolute;\n  top: 0;\n  bottom: 0;\n  left: 0;\n  right: 0;\n  border-radius: 30px;\n  box-shadow: 0 0 0 2px red, 0 0 4px red;\n  cursor: pointer;\n  border: 2px solid transparent;\n  overflow: hidden;\n  transition: .4s; }\n\n.slider:before {\n  position: absolute;\n  content: \"\";\n  width: 100%;\n  height: 100%;\n  background: red;\n  border-radius: 30px;\n  -webkit-transform: translateX(-30px);\n          transform: translateX(-30px);\n  transition: .4s; }\n\ninput:checked + .slider:before {\n  -webkit-transform: translateX(30px);\n          transform: translateX(30px);\n  background: #0069d9; }\n\ninput:checked + .slider {\n  box-shadow: 0 0 0 2px #0069d9, 0 0 2px #0069d9; }\n"

/***/ }),

/***/ "./src/app/layout/user-management/user-management.component.ts":
/*!*********************************************************************!*\
  !*** ./src/app/layout/user-management/user-management.component.ts ***!
  \*********************************************************************/
/*! exports provided: UserManagementComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "UserManagementComponent", function() { return UserManagementComponent; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _shared_services_user_management_user_management_service__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ../../shared/services/user-management/user-management.service */ "./src/app/shared/services/user-management/user-management.service.ts");
/* harmony import */ var _ng_bootstrap_ng_bootstrap__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! @ng-bootstrap/ng-bootstrap */ "./node_modules/@ng-bootstrap/ng-bootstrap/index.js");
/* harmony import */ var _user_management_delete_dialog_user_management_delete_dialog_component__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! ./user-management-delete-dialog/user-management-delete-dialog.component */ "./src/app/layout/user-management/user-management-delete-dialog/user-management-delete-dialog.component.ts");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (undefined && undefined.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};




var UserManagementComponent = /** @class */ (function () {
    function UserManagementComponent(userManagementService, modalService) {
        this.userManagementService = userManagementService;
        this.modalService = modalService;
    }
    UserManagementComponent.prototype.ngOnInit = function () {
        this.loadAll();
    };
    UserManagementComponent.prototype.loadAll = function () {
        var _this = this;
        this.userManagementService.query().subscribe(function (success) { return _this.onSuccess(success); }, function (error) { return _this.onError(error); });
    };
    UserManagementComponent.prototype.setActive = function (user, isActivated) {
        var _this = this;
        user.activated = isActivated;
        this.userManagementService.update(user).subscribe(function (success) { return _this.onSuccess(success); }, function (error) { return _this.onError(error); });
    };
    UserManagementComponent.prototype.deleteUser = function (user) {
        var modalRef = this.modalService.open(_user_management_delete_dialog_user_management_delete_dialog_component__WEBPACK_IMPORTED_MODULE_3__["UserMgmtDeleteDialogComponent"], { size: 'lg', backdrop: 'static' });
        modalRef.componentInstance.user = user;
        modalRef.result.then(function (result) {
            // Left blank intentionally, nothing to do here
        }, function (reason) {
            // Left blank intentionally, nothing to do here
        });
    };
    UserManagementComponent.prototype.onSuccess = function (data) {
        this.users = data;
    };
    UserManagementComponent.prototype.onError = function (error) {
        console.log(error);
    };
    UserManagementComponent = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Component"])({
            selector: 'app-user-management',
            template: __webpack_require__(/*! ./user-management.component.html */ "./src/app/layout/user-management/user-management.component.html"),
            styles: [__webpack_require__(/*! ./user-management.component.scss */ "./src/app/layout/user-management/user-management.component.scss")]
        }),
        __metadata("design:paramtypes", [_shared_services_user_management_user_management_service__WEBPACK_IMPORTED_MODULE_1__["UserManagementService"],
            _ng_bootstrap_ng_bootstrap__WEBPACK_IMPORTED_MODULE_2__["NgbModal"]])
    ], UserManagementComponent);
    return UserManagementComponent;
}());



/***/ }),

/***/ "./src/app/layout/user-management/user-management.module.ts":
/*!******************************************************************!*\
  !*** ./src/app/layout/user-management/user-management.module.ts ***!
  \******************************************************************/
/*! exports provided: UserManagementModule */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "UserManagementModule", function() { return UserManagementModule; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _angular_common__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/common */ "./node_modules/@angular/common/fesm5/common.js");
/* harmony import */ var _user_management_routing_module__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ./user-management-routing.module */ "./src/app/layout/user-management/user-management-routing.module.ts");
/* harmony import */ var _user_management_component__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! ./user-management.component */ "./src/app/layout/user-management/user-management.component.ts");
/* harmony import */ var _user_management_update_user_management_update_component__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! ./user-management-update/user-management-update.component */ "./src/app/layout/user-management/user-management-update/user-management-update.component.ts");
/* harmony import */ var _angular_forms__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! @angular/forms */ "./node_modules/@angular/forms/fesm5/forms.js");
/* harmony import */ var _ng_bootstrap_ng_bootstrap_modal_modal_stack__WEBPACK_IMPORTED_MODULE_6__ = __webpack_require__(/*! @ng-bootstrap/ng-bootstrap/modal/modal-stack */ "./node_modules/@ng-bootstrap/ng-bootstrap/modal/modal-stack.js");
/* harmony import */ var _user_management_delete_dialog_user_management_delete_dialog_component__WEBPACK_IMPORTED_MODULE_7__ = __webpack_require__(/*! ./user-management-delete-dialog/user-management-delete-dialog.component */ "./src/app/layout/user-management/user-management-delete-dialog/user-management-delete-dialog.component.ts");
/* harmony import */ var _ng_bootstrap_ng_bootstrap_modal_modal_backdrop__WEBPACK_IMPORTED_MODULE_8__ = __webpack_require__(/*! @ng-bootstrap/ng-bootstrap/modal/modal-backdrop */ "./node_modules/@ng-bootstrap/ng-bootstrap/modal/modal-backdrop.js");
/* harmony import */ var _ng_bootstrap_ng_bootstrap__WEBPACK_IMPORTED_MODULE_9__ = __webpack_require__(/*! @ng-bootstrap/ng-bootstrap */ "./node_modules/@ng-bootstrap/ng-bootstrap/index.js");
/* harmony import */ var _ng_bootstrap_ng_bootstrap_modal_modal_window__WEBPACK_IMPORTED_MODULE_10__ = __webpack_require__(/*! @ng-bootstrap/ng-bootstrap/modal/modal-window */ "./node_modules/@ng-bootstrap/ng-bootstrap/modal/modal-window.js");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};











var UserManagementModule = /** @class */ (function () {
    function UserManagementModule() {
    }
    UserManagementModule = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["NgModule"])({
            imports: [
                _angular_common__WEBPACK_IMPORTED_MODULE_1__["CommonModule"],
                _user_management_routing_module__WEBPACK_IMPORTED_MODULE_2__["UserManagementRoutingModule"],
                _angular_forms__WEBPACK_IMPORTED_MODULE_5__["FormsModule"]
            ],
            declarations: [
                _user_management_component__WEBPACK_IMPORTED_MODULE_3__["UserManagementComponent"],
                _user_management_update_user_management_update_component__WEBPACK_IMPORTED_MODULE_4__["UserManagementUpdateComponent"],
                _user_management_delete_dialog_user_management_delete_dialog_component__WEBPACK_IMPORTED_MODULE_7__["UserMgmtDeleteDialogComponent"],
                _ng_bootstrap_ng_bootstrap_modal_modal_backdrop__WEBPACK_IMPORTED_MODULE_8__["NgbModalBackdrop"],
                _ng_bootstrap_ng_bootstrap_modal_modal_window__WEBPACK_IMPORTED_MODULE_10__["NgbModalWindow"]
            ],
            entryComponents: [
                _user_management_delete_dialog_user_management_delete_dialog_component__WEBPACK_IMPORTED_MODULE_7__["UserMgmtDeleteDialogComponent"],
                _ng_bootstrap_ng_bootstrap_modal_modal_backdrop__WEBPACK_IMPORTED_MODULE_8__["NgbModalBackdrop"],
                _ng_bootstrap_ng_bootstrap_modal_modal_window__WEBPACK_IMPORTED_MODULE_10__["NgbModalWindow"]
            ],
            providers: [
                _ng_bootstrap_ng_bootstrap__WEBPACK_IMPORTED_MODULE_9__["NgbModal"],
                _ng_bootstrap_ng_bootstrap_modal_modal_stack__WEBPACK_IMPORTED_MODULE_6__["NgbModalStack"]
            ]
        })
    ], UserManagementModule);
    return UserManagementModule;
}());



/***/ }),

/***/ "./src/app/layout/user-management/user-model.ts":
/*!******************************************************!*\
  !*** ./src/app/layout/user-management/user-model.ts ***!
  \******************************************************/
/*! exports provided: User */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "User", function() { return User; });
var User = /** @class */ (function () {
    function User(id, login, firstName, lastName, email, activated, langKey, authorities, createdBy, createdDate, lastModifiedBy, lastModifiedDate, password) {
        this.id = id;
        this.login = login;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.activated = activated;
        this.langKey = langKey;
        this.authorities = authorities;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.lastModifiedBy = lastModifiedBy;
        this.lastModifiedDate = lastModifiedDate;
        this.password = password;
        this.id = id ? id : null;
        this.login = login ? login : null;
        this.firstName = firstName ? firstName : null;
        this.lastName = lastName ? lastName : null;
        this.email = email ? email : null;
        this.activated = activated ? activated : false;
        this.langKey = langKey ? langKey : null;
        this.authorities = authorities ? authorities : null;
        this.createdBy = createdBy ? createdBy : null;
        this.createdDate = createdDate ? createdDate : null;
        this.lastModifiedBy = lastModifiedBy ? lastModifiedBy : null;
        this.lastModifiedDate = lastModifiedDate ? lastModifiedDate : null;
        this.password = password ? password : null;
    }
    return User;
}());



/***/ }),

/***/ "./src/app/shared/services/user-management/user-management.service.ts":
/*!****************************************************************************!*\
  !*** ./src/app/shared/services/user-management/user-management.service.ts ***!
  \****************************************************************************/
/*! exports provided: UserManagementService */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "UserManagementService", function() { return UserManagementService; });
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




var UserManagementService = /** @class */ (function () {
    function UserManagementService(httpClient, localStorageService) {
        this.httpClient = httpClient;
        this.localStorageService = localStorageService;
        this.resourceUrl = _environments_environment__WEBPACK_IMPORTED_MODULE_1__["environment"].SERVER_API_URL + '/api';
        this.users = 'users';
        this.jwtToken = null;
    }
    UserManagementService.prototype.update = function (user) {
        if (this.jwtToken == null) {
            this.jwtToken = this.localStorageService.loadToken();
        }
        return this.httpClient.put(this.resourceUrl + "/" + this.users, user, {
            headers: new _angular_common_http__WEBPACK_IMPORTED_MODULE_2__["HttpHeaders"]({
                'Authorization': this.jwtToken
            })
        });
    };
    UserManagementService.prototype.find = function (login) {
        if (this.jwtToken == null) {
            this.jwtToken = this.localStorageService.loadToken();
        }
        return this.httpClient.get(this.resourceUrl + "/" + this.users + "/" + login, {
            headers: new _angular_common_http__WEBPACK_IMPORTED_MODULE_2__["HttpHeaders"]({
                'Authorization': this.jwtToken
            })
        });
    };
    UserManagementService.prototype.query = function () {
        if (this.jwtToken == null) {
            this.jwtToken = this.localStorageService.loadToken();
        }
        return this.httpClient.get(this.resourceUrl + "/" + this.users, {
            headers: new _angular_common_http__WEBPACK_IMPORTED_MODULE_2__["HttpHeaders"]({
                'Authorization': this.jwtToken
            })
        });
    };
    UserManagementService.prototype.delete = function (login) {
        if (this.jwtToken == null) {
            this.jwtToken = this.localStorageService.loadToken();
        }
        return this.httpClient.delete(this.resourceUrl + "/" + this.users + "/" + login, {
            headers: new _angular_common_http__WEBPACK_IMPORTED_MODULE_2__["HttpHeaders"]({
                'Authorization': this.jwtToken
            })
        });
    };
    UserManagementService = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Injectable"])({
            providedIn: 'root'
        }),
        __metadata("design:paramtypes", [_angular_common_http__WEBPACK_IMPORTED_MODULE_2__["HttpClient"],
            _authentication_local_storage_service__WEBPACK_IMPORTED_MODULE_3__["LocalStorageService"]])
    ], UserManagementService);
    return UserManagementService;
}());



/***/ })

}]);
//# sourceMappingURL=user-management-user-management-module.js.map