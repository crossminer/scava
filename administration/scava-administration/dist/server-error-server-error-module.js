(window["webpackJsonp"] = window["webpackJsonp"] || []).push([["server-error-server-error-module"],{

/***/ "./src/app/server-error/server-error-routing.module.ts":
/*!*************************************************************!*\
  !*** ./src/app/server-error/server-error-routing.module.ts ***!
  \*************************************************************/
/*! exports provided: ServerErrorRoutingModule */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "ServerErrorRoutingModule", function() { return ServerErrorRoutingModule; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _angular_router__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/router */ "./node_modules/@angular/router/fesm5/router.js");
/* harmony import */ var _server_error_component__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ./server-error.component */ "./src/app/server-error/server-error.component.ts");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};



var routes = [
    {
        path: '', component: _server_error_component__WEBPACK_IMPORTED_MODULE_2__["ServerErrorComponent"]
    }
];
var ServerErrorRoutingModule = /** @class */ (function () {
    function ServerErrorRoutingModule() {
    }
    ServerErrorRoutingModule = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["NgModule"])({
            imports: [_angular_router__WEBPACK_IMPORTED_MODULE_1__["RouterModule"].forChild(routes)],
            exports: [_angular_router__WEBPACK_IMPORTED_MODULE_1__["RouterModule"]]
        })
    ], ServerErrorRoutingModule);
    return ServerErrorRoutingModule;
}());



/***/ }),

/***/ "./src/app/server-error/server-error.component.html":
/*!**********************************************************!*\
  !*** ./src/app/server-error/server-error.component.html ***!
  \**********************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "<p>\n  server-error works!\n</p>\n"

/***/ }),

/***/ "./src/app/server-error/server-error.component.scss":
/*!**********************************************************!*\
  !*** ./src/app/server-error/server-error.component.scss ***!
  \**********************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = ""

/***/ }),

/***/ "./src/app/server-error/server-error.component.ts":
/*!********************************************************!*\
  !*** ./src/app/server-error/server-error.component.ts ***!
  \********************************************************/
/*! exports provided: ServerErrorComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "ServerErrorComponent", function() { return ServerErrorComponent; });
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

var ServerErrorComponent = /** @class */ (function () {
    function ServerErrorComponent() {
    }
    ServerErrorComponent.prototype.ngOnInit = function () {
    };
    ServerErrorComponent = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Component"])({
            selector: 'app-server-error',
            template: __webpack_require__(/*! ./server-error.component.html */ "./src/app/server-error/server-error.component.html"),
            styles: [__webpack_require__(/*! ./server-error.component.scss */ "./src/app/server-error/server-error.component.scss")]
        }),
        __metadata("design:paramtypes", [])
    ], ServerErrorComponent);
    return ServerErrorComponent;
}());



/***/ }),

/***/ "./src/app/server-error/server-error.module.ts":
/*!*****************************************************!*\
  !*** ./src/app/server-error/server-error.module.ts ***!
  \*****************************************************/
/*! exports provided: ServerErrorModule */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "ServerErrorModule", function() { return ServerErrorModule; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _angular_common__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/common */ "./node_modules/@angular/common/fesm5/common.js");
/* harmony import */ var _server_error_routing_module__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ./server-error-routing.module */ "./src/app/server-error/server-error-routing.module.ts");
/* harmony import */ var _server_error_component__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! ./server-error.component */ "./src/app/server-error/server-error.component.ts");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};




var ServerErrorModule = /** @class */ (function () {
    function ServerErrorModule() {
    }
    ServerErrorModule = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["NgModule"])({
            imports: [
                _angular_common__WEBPACK_IMPORTED_MODULE_1__["CommonModule"],
                _server_error_routing_module__WEBPACK_IMPORTED_MODULE_2__["ServerErrorRoutingModule"]
            ],
            declarations: [_server_error_component__WEBPACK_IMPORTED_MODULE_3__["ServerErrorComponent"]]
        })
    ], ServerErrorModule);
    return ServerErrorModule;
}());



/***/ })

}]);
//# sourceMappingURL=server-error-server-error-module.js.map