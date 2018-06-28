(window["webpackJsonp"] = window["webpackJsonp"] || []).push([["home-home-module"],{

/***/ "./src/app/layout/home/home-routing.module.ts":
/*!****************************************************!*\
  !*** ./src/app/layout/home/home-routing.module.ts ***!
  \****************************************************/
/*! exports provided: HomeRoutingModule */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "HomeRoutingModule", function() { return HomeRoutingModule; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _angular_router__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/router */ "./node_modules/@angular/router/fesm5/router.js");
/* harmony import */ var _home_component__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ./home.component */ "./src/app/layout/home/home.component.ts");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};



var routes = [
    {
        path: '', component: _home_component__WEBPACK_IMPORTED_MODULE_2__["HomeComponent"]
    }
];
var HomeRoutingModule = /** @class */ (function () {
    function HomeRoutingModule() {
    }
    HomeRoutingModule = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["NgModule"])({
            imports: [_angular_router__WEBPACK_IMPORTED_MODULE_1__["RouterModule"].forChild(routes)],
            exports: [_angular_router__WEBPACK_IMPORTED_MODULE_1__["RouterModule"]]
        })
    ], HomeRoutingModule);
    return HomeRoutingModule;
}());



/***/ }),

/***/ "./src/app/layout/home/home.component.html":
/*!*************************************************!*\
  !*** ./src/app/layout/home/home.component.html ***!
  \*************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "<div [@routerTransition]>\n    <section>\n        <div class=\"container box\">\n            <div class=\"row\">\n                <div class=\"col-md-12 center\">\n                    <img src=\"assets/images/SCAVA_logo_tagline_under.jpg\" alt=\"SCAVA logo\" class=\"logo\" width=\"500px\" height=\"250px\" />\n                    <p class=\"lead\">SCAVA is an open-source platform for automatically analysing the source code, bug tracking systems,\n                        and communication channels of open source software projects.</p>\n                </div>\n            </div>\n        </div>\n    </section>\n    <section>\n        <div class=\"banner\">\n            <div class=\"container blank\">\n                <div class=\"row\">\n                    <div class=\"col-md-3\">\n                        <img src=\"assets/images/search.png\" alt=\"search\">\n                        <h2>Discover</h2>\n                        <p>Search through thousands of open-source software projects to discover those that suit your needs</p>\n                    </div>\n                    <div class=\"col-md-3\">\n                        <img src=\"assets/images/compare.png\" alt=\"compare\">\n                        <h2>Compare</h2>\n                        <p>Explore the differences between similar projects, comparing projects on the metrics that are important\n                            to you</p>\n                    </div>\n                    <div class=\"col-md-3\">\n                        <img src=\"assets/images/adopt.png\" alt=\"adopt\">\n                        <h2>Adopt</h2>\n                        <p>Select the project that best fits your requirements and start using it</p>\n                    </div>\n                    <div class=\"col-md-3\">\n                        <img src=\"assets/images/monitor.png\" alt=\"monitor\">\n                        <h2>Monitor</h2>\n                        <p>Monitor adopted open-source software to ensure that it remains active and healthy</p>\n                    </div>\n                </div>\n            </div>\n        </div>\n    </section>\n    <section>\n        <div class=\"container box\">\n            <div class=\"row\">\n                <div class=\"col-md-4\">\n                    <h2>What is SCAVA?</h2>\n                    <!-- <p>SCAVA aims to extend the state-of-the-art in the field of automated analysis and measurement of open-source software (OSS) by developing a platform that will support decision makers in the process of discovering, comparing, assessing and monitoring the health, quality, impact and activity of open-source software.</p> -->\n                    <p>SCAVA computes trustworthy quality indicators of open-source software by performing advanced analysis\n                        and integration of information from diverse sources including the project metadata, source code repositories,\n                        communication channels and bug tracking systems of OSS projects.</p>\n                    <p>SCAVA is not another OSS forge but instead it provides a meta-platform for analysing existing OSS\n                        projects that are developed in existing OSS forges and foundations such as SourceForge, Google Code,\n                        GitHub, Eclipse, Mozilla and Apache.</p>\n                    <p>\n                        <a href=\"#\">Click here to find out more about SCAVA</a>\n                    </p>\n                </div>\n                <div class=\"col-md-4\">\n                    <h2>Featured Projects</h2>\n                    <p></p>\n                    <ul>\n\n                    </ul>\n                </div>\n                <div class=\"col-md-4\">\n                    <h2>Latest News</h2>\n\n                </div>\n            </div>\n        </div>\n    </section>\n</div>\n"

/***/ }),

/***/ "./src/app/layout/home/home.component.scss":
/*!*************************************************!*\
  !*** ./src/app/layout/home/home.component.scss ***!
  \*************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = ".container {\n  padding: 20px; }\n\n.box {\n  background-color: #ffffff;\n  border: 1px solid #ddd;\n  margin-top: 1.5em;\n  margin-bottom: 1.5em;\n  /* box-shadow: 0px 1px 1px #888888; */ }\n\n.center {\n  text-align: center; }\n\n.banner {\n  background-color: #1074B2;\n  padding: 20px;\n  color: white;\n  text-align: center;\n  margin-top: 20px;\n  margin-bottom: 20px; }\n\n.banner img {\n  height: 100px;\n  margin: 5px; }\n\n.blank {\n  background-color: inherit;\n  border: 0;\n  box-shadow: none; }\n\n.row {\n  margin: 0; }\n\n.banner h2 {\n  color: #ff9400; }\n\nh1, h2, h3, h4 {\n  font-family: 'Bree Serif', Georgia, serif;\n  color: #023E73; }\n\nh2 {\n  font-size: 28px; }\n"

/***/ }),

/***/ "./src/app/layout/home/home.component.ts":
/*!***********************************************!*\
  !*** ./src/app/layout/home/home.component.ts ***!
  \***********************************************/
/*! exports provided: HomeComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "HomeComponent", function() { return HomeComponent; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _router_animations__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ../../router.animations */ "./src/app/router.animations.ts");
/* harmony import */ var _angular_router__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! @angular/router */ "./node_modules/@angular/router/fesm5/router.js");
/* harmony import */ var _shared_services_api_server_ping_service__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! ../../shared/services/api-server/ping.service */ "./src/app/shared/services/api-server/ping.service.ts");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (undefined && undefined.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};




var HomeComponent = /** @class */ (function () {
    function HomeComponent(router, pingService) {
        this.router = router;
        this.pingService = pingService;
    }
    HomeComponent.prototype.ngOnInit = function () {
        // this.testPing();
    };
    HomeComponent.prototype.testPing = function () {
        this.pingService.testPing().subscribe(function (resp) {
            console.log(resp);
        }, function (error) {
            console.log(error);
        });
    };
    HomeComponent = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Component"])({
            selector: 'app-home',
            template: __webpack_require__(/*! ./home.component.html */ "./src/app/layout/home/home.component.html"),
            styles: [__webpack_require__(/*! ./home.component.scss */ "./src/app/layout/home/home.component.scss")],
            animations: [Object(_router_animations__WEBPACK_IMPORTED_MODULE_1__["routerTransition"])()]
        }),
        __metadata("design:paramtypes", [_angular_router__WEBPACK_IMPORTED_MODULE_2__["Router"],
            _shared_services_api_server_ping_service__WEBPACK_IMPORTED_MODULE_3__["PingService"]])
    ], HomeComponent);
    return HomeComponent;
}());



/***/ }),

/***/ "./src/app/layout/home/home.module.ts":
/*!********************************************!*\
  !*** ./src/app/layout/home/home.module.ts ***!
  \********************************************/
/*! exports provided: HomeModule */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "HomeModule", function() { return HomeModule; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _angular_common__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/common */ "./node_modules/@angular/common/fesm5/common.js");
/* harmony import */ var _home_routing_module__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ./home-routing.module */ "./src/app/layout/home/home-routing.module.ts");
/* harmony import */ var _home_component__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! ./home.component */ "./src/app/layout/home/home.component.ts");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};




var HomeModule = /** @class */ (function () {
    function HomeModule() {
    }
    HomeModule = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["NgModule"])({
            imports: [
                _angular_common__WEBPACK_IMPORTED_MODULE_1__["CommonModule"],
                _home_routing_module__WEBPACK_IMPORTED_MODULE_2__["HomeRoutingModule"],
            ],
            declarations: [_home_component__WEBPACK_IMPORTED_MODULE_3__["HomeComponent"]]
        })
    ], HomeModule);
    return HomeModule;
}());



/***/ }),

/***/ "./src/app/shared/services/api-server/ping.service.ts":
/*!************************************************************!*\
  !*** ./src/app/shared/services/api-server/ping.service.ts ***!
  \************************************************************/
/*! exports provided: PingService */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "PingService", function() { return PingService; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _angular_common_http__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/common/http */ "./node_modules/@angular/common/fesm5/http.js");
/* harmony import */ var _environments_environment__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ../../../../environments/environment */ "./src/environments/environment.ts");
/* harmony import */ var _authentication_jwt_authentication_service__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! ../authentication/jwt-authentication.service */ "./src/app/shared/services/authentication/jwt-authentication.service.ts");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (undefined && undefined.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};




var PingService = /** @class */ (function () {
    function PingService(httpClient, jwtAuthenticationService) {
        this.httpClient = httpClient;
        this.jwtAuthenticationService = jwtAuthenticationService;
        this.resourceUrl = _environments_environment__WEBPACK_IMPORTED_MODULE_2__["environment"].SERVER_API_URL + 'administration';
        this.ping = 'ping';
        this.jwtToken = null;
    }
    PingService.prototype.testPing = function () {
        if (this.jwtToken == null) {
            this.jwtToken = this.jwtAuthenticationService.loadToken();
        }
        console.log(this.resourceUrl + "/" + this.ping);
        return this.httpClient.get(this.resourceUrl + "/" + this.ping, { headers: new _angular_common_http__WEBPACK_IMPORTED_MODULE_1__["HttpHeaders"]({ 'Authorization': this.jwtToken }) });
    };
    PingService = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Injectable"])({
            providedIn: 'root'
        }),
        __metadata("design:paramtypes", [_angular_common_http__WEBPACK_IMPORTED_MODULE_1__["HttpClient"],
            _authentication_jwt_authentication_service__WEBPACK_IMPORTED_MODULE_3__["JwtAuthenticationService"]])
    ], PingService);
    return PingService;
}());



/***/ })

}]);
//# sourceMappingURL=home-home-module.js.map