(window["webpackJsonp"] = window["webpackJsonp"] || []).push([["account-activate-activate-module"],{

/***/ "./src/app/account/activate/activate-routing.module.ts":
/*!*************************************************************!*\
  !*** ./src/app/account/activate/activate-routing.module.ts ***!
  \*************************************************************/
/*! exports provided: ActivateRoutingModule */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "ActivateRoutingModule", function() { return ActivateRoutingModule; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _angular_router__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/router */ "./node_modules/@angular/router/fesm5/router.js");
/* harmony import */ var _activate_component__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ./activate.component */ "./src/app/account/activate/activate.component.ts");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};



var routes = [
    {
        path: '', component: _activate_component__WEBPACK_IMPORTED_MODULE_2__["ActivateComponent"]
    }
];
var ActivateRoutingModule = /** @class */ (function () {
    function ActivateRoutingModule() {
    }
    ActivateRoutingModule = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["NgModule"])({
            imports: [_angular_router__WEBPACK_IMPORTED_MODULE_1__["RouterModule"].forChild(routes)],
            exports: [_angular_router__WEBPACK_IMPORTED_MODULE_1__["RouterModule"]]
        })
    ], ActivateRoutingModule);
    return ActivateRoutingModule;
}());



/***/ }),

/***/ "./src/app/account/activate/activate.component.html":
/*!**********************************************************!*\
  !*** ./src/app/account/activate/activate.component.html ***!
  \**********************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "<div>\n    <div class=\"row justify-content-center\">\n        <div class=\"col-sm-6 col-md-6 col-lg-6\">\n            <h1>Activation</h1>\n            \n            <div class=\"alert alert-success\" *ngIf=\"success\">\n                <span><strong>Your user account has been activated.</strong> Please </span>\n                <a class=\"alert-link\" [routerLink]=\"['/login']\" >sign in</a>.\n            </div>\n\n            <div class=\"alert alert-danger\" *ngIf=\"error\">\n                <strong>Your user could not be activated.</strong> Please use the registration form to \n                <a class=\"alert-link\" [routerLink]=\"['/signup']\" >sign up</a>.\n            </div>\n            \n        </div>\n    </div>\n</div>"

/***/ }),

/***/ "./src/app/account/activate/activate.component.scss":
/*!**********************************************************!*\
  !*** ./src/app/account/activate/activate.component.scss ***!
  \**********************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = ""

/***/ }),

/***/ "./src/app/account/activate/activate.component.ts":
/*!********************************************************!*\
  !*** ./src/app/account/activate/activate.component.ts ***!
  \********************************************************/
/*! exports provided: ActivateComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "ActivateComponent", function() { return ActivateComponent; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _activate_service__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ./activate.service */ "./src/app/account/activate/activate.service.ts");
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



var ActivateComponent = /** @class */ (function () {
    function ActivateComponent(acivateservice, route) {
        this.acivateservice = acivateservice;
        this.route = route;
        this.success = null;
        this.error = null;
    }
    ActivateComponent.prototype.ngOnInit = function () {
        var _this = this;
        this.route.queryParams.subscribe(function (params) {
            _this.acivateservice.activateAccount(params['key']).subscribe(function () {
                _this.error = null;
                _this.success = 'OK';
            }, function () {
                _this.success = null;
                _this.error = 'ERROR';
            });
        });
    };
    ActivateComponent = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Component"])({
            selector: 'app-activate',
            template: __webpack_require__(/*! ./activate.component.html */ "./src/app/account/activate/activate.component.html"),
            styles: [__webpack_require__(/*! ./activate.component.scss */ "./src/app/account/activate/activate.component.scss")]
        }),
        __metadata("design:paramtypes", [_activate_service__WEBPACK_IMPORTED_MODULE_1__["ActivateService"],
            _angular_router__WEBPACK_IMPORTED_MODULE_2__["ActivatedRoute"]])
    ], ActivateComponent);
    return ActivateComponent;
}());



/***/ }),

/***/ "./src/app/account/activate/activate.module.ts":
/*!*****************************************************!*\
  !*** ./src/app/account/activate/activate.module.ts ***!
  \*****************************************************/
/*! exports provided: ActivateModule */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "ActivateModule", function() { return ActivateModule; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _angular_common__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/common */ "./node_modules/@angular/common/fesm5/common.js");
/* harmony import */ var _activate_routing_module__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ./activate-routing.module */ "./src/app/account/activate/activate-routing.module.ts");
/* harmony import */ var _activate_component__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! ./activate.component */ "./src/app/account/activate/activate.component.ts");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};




var ActivateModule = /** @class */ (function () {
    function ActivateModule() {
    }
    ActivateModule = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["NgModule"])({
            imports: [
                _angular_common__WEBPACK_IMPORTED_MODULE_1__["CommonModule"],
                _activate_routing_module__WEBPACK_IMPORTED_MODULE_2__["ActivateRoutingModule"]
            ],
            declarations: [_activate_component__WEBPACK_IMPORTED_MODULE_3__["ActivateComponent"]]
        })
    ], ActivateModule);
    return ActivateModule;
}());



/***/ }),

/***/ "./src/app/account/activate/activate.service.ts":
/*!******************************************************!*\
  !*** ./src/app/account/activate/activate.service.ts ***!
  \******************************************************/
/*! exports provided: ActivateService */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "ActivateService", function() { return ActivateService; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _angular_common_http__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/common/http */ "./node_modules/@angular/common/fesm5/http.js");
/* harmony import */ var _environments_environment__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ../../../environments/environment */ "./src/environments/environment.ts");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (undefined && undefined.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};



var ActivateService = /** @class */ (function () {
    function ActivateService(httpClient) {
        this.httpClient = httpClient;
        this.resourceUrl = _environments_environment__WEBPACK_IMPORTED_MODULE_2__["environment"].SERVER_API_URL + '/api';
        this.activate = 'activate';
    }
    ActivateService.prototype.activateAccount = function (key) {
        return this.httpClient.get(this.resourceUrl + "/" + this.activate, { params: new _angular_common_http__WEBPACK_IMPORTED_MODULE_1__["HttpParams"]().set('key', key) });
    };
    ActivateService = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Injectable"])({
            providedIn: 'root'
        }),
        __metadata("design:paramtypes", [_angular_common_http__WEBPACK_IMPORTED_MODULE_1__["HttpClient"]])
    ], ActivateService);
    return ActivateService;
}());



/***/ })

}]);
//# sourceMappingURL=account-activate-activate-module.js.map