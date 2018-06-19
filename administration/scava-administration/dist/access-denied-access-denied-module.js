(window["webpackJsonp"] = window["webpackJsonp"] || []).push([["access-denied-access-denied-module"],{

/***/ "./src/app/access-denied/access-denied-routing.module.ts":
/*!***************************************************************!*\
  !*** ./src/app/access-denied/access-denied-routing.module.ts ***!
  \***************************************************************/
/*! exports provided: AccessDeniedRoutingModule */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "AccessDeniedRoutingModule", function() { return AccessDeniedRoutingModule; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _angular_router__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/router */ "./node_modules/@angular/router/fesm5/router.js");
/* harmony import */ var _access_denied_component__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ./access-denied.component */ "./src/app/access-denied/access-denied.component.ts");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};



var routes = [
    {
        path: '', component: _access_denied_component__WEBPACK_IMPORTED_MODULE_2__["AccessDeniedComponent"]
    }
];
var AccessDeniedRoutingModule = /** @class */ (function () {
    function AccessDeniedRoutingModule() {
    }
    AccessDeniedRoutingModule = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["NgModule"])({
            imports: [_angular_router__WEBPACK_IMPORTED_MODULE_1__["RouterModule"].forChild(routes)],
            exports: [_angular_router__WEBPACK_IMPORTED_MODULE_1__["RouterModule"]]
        })
    ], AccessDeniedRoutingModule);
    return AccessDeniedRoutingModule;
}());



/***/ }),

/***/ "./src/app/access-denied/access-denied.component.html":
/*!************************************************************!*\
  !*** ./src/app/access-denied/access-denied.component.html ***!
  \************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "<p>\n  access-denied works!\n</p>\n"

/***/ }),

/***/ "./src/app/access-denied/access-denied.component.scss":
/*!************************************************************!*\
  !*** ./src/app/access-denied/access-denied.component.scss ***!
  \************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = ""

/***/ }),

/***/ "./src/app/access-denied/access-denied.component.ts":
/*!**********************************************************!*\
  !*** ./src/app/access-denied/access-denied.component.ts ***!
  \**********************************************************/
/*! exports provided: AccessDeniedComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "AccessDeniedComponent", function() { return AccessDeniedComponent; });
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

var AccessDeniedComponent = /** @class */ (function () {
    function AccessDeniedComponent() {
    }
    AccessDeniedComponent.prototype.ngOnInit = function () {
    };
    AccessDeniedComponent = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Component"])({
            selector: 'app-access-denied',
            template: __webpack_require__(/*! ./access-denied.component.html */ "./src/app/access-denied/access-denied.component.html"),
            styles: [__webpack_require__(/*! ./access-denied.component.scss */ "./src/app/access-denied/access-denied.component.scss")]
        }),
        __metadata("design:paramtypes", [])
    ], AccessDeniedComponent);
    return AccessDeniedComponent;
}());



/***/ }),

/***/ "./src/app/access-denied/access-denied.module.ts":
/*!*******************************************************!*\
  !*** ./src/app/access-denied/access-denied.module.ts ***!
  \*******************************************************/
/*! exports provided: AccessDeniedModule */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "AccessDeniedModule", function() { return AccessDeniedModule; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _angular_common__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/common */ "./node_modules/@angular/common/fesm5/common.js");
/* harmony import */ var _access_denied_routing_module__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ./access-denied-routing.module */ "./src/app/access-denied/access-denied-routing.module.ts");
/* harmony import */ var _access_denied_component__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! ./access-denied.component */ "./src/app/access-denied/access-denied.component.ts");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};




var AccessDeniedModule = /** @class */ (function () {
    function AccessDeniedModule() {
    }
    AccessDeniedModule = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["NgModule"])({
            imports: [
                _angular_common__WEBPACK_IMPORTED_MODULE_1__["CommonModule"],
                _access_denied_routing_module__WEBPACK_IMPORTED_MODULE_2__["AccessDeniedRoutingModule"]
            ],
            declarations: [_access_denied_component__WEBPACK_IMPORTED_MODULE_3__["AccessDeniedComponent"]]
        })
    ], AccessDeniedModule);
    return AccessDeniedModule;
}());



/***/ })

}]);
//# sourceMappingURL=access-denied-access-denied-module.js.map