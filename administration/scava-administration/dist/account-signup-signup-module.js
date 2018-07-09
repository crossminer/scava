(window["webpackJsonp"] = window["webpackJsonp"] || []).push([["account-signup-signup-module"],{

/***/ "./src/app/account/signup/signup-routing.module.ts":
/*!*********************************************************!*\
  !*** ./src/app/account/signup/signup-routing.module.ts ***!
  \*********************************************************/
/*! exports provided: SignupRoutingModule */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "SignupRoutingModule", function() { return SignupRoutingModule; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _angular_router__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/router */ "./node_modules/@angular/router/fesm5/router.js");
/* harmony import */ var _signup_component__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ./signup.component */ "./src/app/account/signup/signup.component.ts");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};



var routes = [
    {
        path: '', component: _signup_component__WEBPACK_IMPORTED_MODULE_2__["SignupComponent"]
    }
];
var SignupRoutingModule = /** @class */ (function () {
    function SignupRoutingModule() {
    }
    SignupRoutingModule = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["NgModule"])({
            imports: [_angular_router__WEBPACK_IMPORTED_MODULE_1__["RouterModule"].forChild(routes)],
            exports: [_angular_router__WEBPACK_IMPORTED_MODULE_1__["RouterModule"]]
        })
    ], SignupRoutingModule);
    return SignupRoutingModule;
}());



/***/ }),

/***/ "./src/app/account/signup/signup.component.html":
/*!******************************************************!*\
  !*** ./src/app/account/signup/signup.component.html ***!
  \******************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "<div class=\"login-page\" [@routerTransition]>\n    <div class=\"row justify-content-md-center\">\n        <div class=\"col-sm-6 col-md-6 col-lg-6\">\n            <div>\n                <nav class=\"login-logo\">\n                    <img src=\"assets/images/CROSSMINER_logo_tagline_side.png\" width=\"600px\" />\n                </nav>\n                <h2>CROSSMINER is an open-source platform for automatically analysing the source code, bug tracking systems, and communication channels of open source software projects.</h2>\n            </div>\n            <div class=\"alert alert-success\" *ngIf=\"success\">\n                <strong>Registration saved!</strong> Please check your email for confirmation.\n            </div>\n        </div>\n    </div>\n    \n    <div class=\"row justify-content-md-center\">\n        <div class=\"col-md-4\">\n            <form #f=\"ngForm\" (ngSubmit)=\"register(f.value)\" *ngIf=\"!success\">\n                <div class=\"form-content\">\n                    <div class=\"form-group\">\n                        <input type=\"text\" name=\"login\" [(ngModel)]=\"login\" class=\"form-control input-underline input-lg\" id=\"login\" placeholder=\"User Name\">\n                    </div>\n                    <div class=\"form-group\">\n                        <input type=\"text\" name=\"email\" [(ngModel)]=\"email\" class=\"form-control input-underline input-lg\" id=\"email\" placeholder=\"Email\">\n                    </div>\n                    <div class=\"form-group\">\n                        <input type=\"password\" name=\"password\" [(ngModel)]=\"password\" class=\"form-control input-underline input-lg\" id=\"password\" placeholder=\"Password\">\n                    </div>\n                    <div class=\"form-group\">\n                        <input type=\"password\" name=\"confirmPassword\" [(ngModel)]=\"confirmPassword\" class=\"form-control input-underline input-lg\" id=\"confirmPassword\" placeholder=\"Repeat Password\">\n                    </div>\n                </div>\n                <button type=\"submit\" class=\"btn rounded-btn\" [disabled]=\"!f.valid\" > Register </button>&nbsp;\n                <a class=\"btn rounded-btn\" [routerLink]=\"['/login']\"> Log in </a>\n            </form>\n        </div>\n    </div>\n</div>\n"

/***/ }),

/***/ "./src/app/account/signup/signup.component.scss":
/*!******************************************************!*\
  !*** ./src/app/account/signup/signup.component.scss ***!
  \******************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = ":host {\n  display: block; }\n\n.login-page {\n  position: absolute;\n  top: 0;\n  left: 0;\n  right: 0;\n  bottom: 0;\n  overflow: auto;\n  background: #f8f8f8;\n  text-align: center;\n  color: #000;\n  padding: 3em; }\n\n.login-page nav.login-logo {\n    margin: 0px auto;\n    background-color: white;\n    float: none;\n    padding: 10px;\n    border: 5px solid whitesmoke;\n    border-radius: 30px;\n    position: relative;\n    z-index: 10; }\n\n.login-page nav.login-logo:after {\n    background-color: white;\n    content: '';\n    display: block;\n    position: absolute;\n    top: 10px;\n    left: 10px;\n    right: 10px;\n    bottom: 10px;\n    z-index: -1; }\n\n.login-page .col-lg-4 {\n    padding: 0; }\n\n.login-page .input-lg {\n    height: 46px;\n    padding: 10px 16px;\n    font-size: 18px;\n    line-height: 1.3333333;\n    border-radius: 0; }\n\n.login-page .input-underline {\n    background: 0 0;\n    border: none;\n    box-shadow: none;\n    border-bottom: 2px solid #000;\n    color: #000;\n    border-radius: 0; }\n\n.login-page .input-underline:focus {\n    border-bottom: 2px solid #000;\n    box-shadow: none; }\n\n.login-page .rounded-btn {\n    border-radius: 50px;\n    color: #000;\n    background: #f8f8f8;\n    border: 2px solid #000;\n    font-size: 18px;\n    line-height: 40px;\n    padding: 0 25px; }\n\n.login-page .rounded-btn:hover,\n  .login-page .rounded-btn:focus,\n  .login-page .rounded-btn:active,\n  .login-page .rounded-btn:visited {\n    color: #000;\n    border: 2px solid #000;\n    outline: none; }\n\n.login-page h2 {\n    font-weight: 300;\n    margin-top: 20px;\n    margin-bottom: 10px;\n    font-size: 36px; }\n\n.login-page h2 small {\n      color: #000; }\n\n.login-page .form-group {\n    padding: 8px 0; }\n\n.login-page .form-group input::-webkit-input-placeholder {\n      color: #000 !important; }\n\n.login-page .form-group input:-moz-placeholder {\n      /* Firefox 18- */\n      color: #000 !important; }\n\n.login-page .form-group input::-moz-placeholder {\n      /* Firefox 19+ */\n      color: #000 !important; }\n\n.login-page .form-group input:-ms-input-placeholder {\n      color: #000 !important; }\n\n.login-page .form-content {\n    padding: 40px 0; }\n"

/***/ }),

/***/ "./src/app/account/signup/signup.component.ts":
/*!****************************************************!*\
  !*** ./src/app/account/signup/signup.component.ts ***!
  \****************************************************/
/*! exports provided: SignupComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "SignupComponent", function() { return SignupComponent; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _router_animations__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ../../router.animations */ "./src/app/router.animations.ts");
/* harmony import */ var _signup_service__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ./signup.service */ "./src/app/account/signup/signup.service.ts");
/* harmony import */ var _angular_router__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! @angular/router */ "./node_modules/@angular/router/fesm5/router.js");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (undefined && undefined.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};




var SignupComponent = /** @class */ (function () {
    function SignupComponent(router, signupService) {
        this.router = router;
        this.signupService = signupService;
        this.success = false;
    }
    SignupComponent.prototype.ngOnInit = function () { };
    SignupComponent.prototype.register = function (data) {
        var _this = this;
        this.signupService.onSignedup(data).subscribe(function (resp) {
            _this.success = true;
            console.log(_this.success);
        }, function (error) {
            _this.success = false;
            console.log(_this.success);
        });
        this.router.navigateByUrl('/signup');
    };
    SignupComponent = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Component"])({
            selector: 'app-signup',
            template: __webpack_require__(/*! ./signup.component.html */ "./src/app/account/signup/signup.component.html"),
            styles: [__webpack_require__(/*! ./signup.component.scss */ "./src/app/account/signup/signup.component.scss")],
            animations: [Object(_router_animations__WEBPACK_IMPORTED_MODULE_1__["routerTransition"])()]
        }),
        __metadata("design:paramtypes", [_angular_router__WEBPACK_IMPORTED_MODULE_3__["Router"],
            _signup_service__WEBPACK_IMPORTED_MODULE_2__["SignupService"]])
    ], SignupComponent);
    return SignupComponent;
}());



/***/ }),

/***/ "./src/app/account/signup/signup.module.ts":
/*!*************************************************!*\
  !*** ./src/app/account/signup/signup.module.ts ***!
  \*************************************************/
/*! exports provided: SignupModule */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "SignupModule", function() { return SignupModule; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _angular_common__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/common */ "./node_modules/@angular/common/fesm5/common.js");
/* harmony import */ var _signup_routing_module__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ./signup-routing.module */ "./src/app/account/signup/signup-routing.module.ts");
/* harmony import */ var _signup_component__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! ./signup.component */ "./src/app/account/signup/signup.component.ts");
/* harmony import */ var _angular_forms__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! @angular/forms */ "./node_modules/@angular/forms/fesm5/forms.js");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};





var SignupModule = /** @class */ (function () {
    function SignupModule() {
    }
    SignupModule = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["NgModule"])({
            imports: [
                _angular_common__WEBPACK_IMPORTED_MODULE_1__["CommonModule"],
                _signup_routing_module__WEBPACK_IMPORTED_MODULE_2__["SignupRoutingModule"],
                _angular_forms__WEBPACK_IMPORTED_MODULE_4__["FormsModule"]
            ],
            declarations: [_signup_component__WEBPACK_IMPORTED_MODULE_3__["SignupComponent"]]
        })
    ], SignupModule);
    return SignupModule;
}());



/***/ }),

/***/ "./src/app/account/signup/signup.service.ts":
/*!**************************************************!*\
  !*** ./src/app/account/signup/signup.service.ts ***!
  \**************************************************/
/*! exports provided: SignupService */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "SignupService", function() { return SignupService; });
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



var SignupService = /** @class */ (function () {
    function SignupService(httpClient) {
        this.httpClient = httpClient;
        this.resourceUrl = _environments_environment__WEBPACK_IMPORTED_MODULE_2__["environment"].SERVER_API_URL + '/api';
        this.register = 'register';
    }
    SignupService.prototype.onSignedup = function (data) {
        return this.httpClient.post(this.resourceUrl + "/" + this.register, data, { observe: 'response' });
    };
    SignupService = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Injectable"])({
            providedIn: 'root'
        }),
        __metadata("design:paramtypes", [_angular_common_http__WEBPACK_IMPORTED_MODULE_1__["HttpClient"]])
    ], SignupService);
    return SignupService;
}());



/***/ })

}]);
//# sourceMappingURL=account-signup-signup-module.js.map