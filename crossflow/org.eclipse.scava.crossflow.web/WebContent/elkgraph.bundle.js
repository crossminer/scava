/******/ (function(modules) { // webpackBootstrap
/******/ 	// The module cache
/******/ 	var installedModules = {};
/******/
/******/ 	// The require function
/******/ 	function __webpack_require__(moduleId) {
/******/
/******/ 		// Check if module is in cache
/******/ 		if(installedModules[moduleId]) {
/******/ 			return installedModules[moduleId].exports;
/******/ 		}
/******/ 		// Create a new module (and put it into the cache)
/******/ 		var module = installedModules[moduleId] = {
/******/ 			i: moduleId,
/******/ 			l: false,
/******/ 			exports: {}
/******/ 		};
/******/
/******/ 		// Execute the module function
/******/ 		modules[moduleId].call(module.exports, module, module.exports, __webpack_require__);
/******/
/******/ 		// Flag the module as loaded
/******/ 		module.l = true;
/******/
/******/ 		// Return the exports of the module
/******/ 		return module.exports;
/******/ 	}
/******/
/******/
/******/ 	// expose the modules object (__webpack_modules__)
/******/ 	__webpack_require__.m = modules;
/******/
/******/ 	// expose the module cache
/******/ 	__webpack_require__.c = installedModules;
/******/
/******/ 	// identity function for calling harmony imports with the correct context
/******/ 	__webpack_require__.i = function(value) { return value; };
/******/
/******/ 	// define getter function for harmony exports
/******/ 	__webpack_require__.d = function(exports, name, getter) {
/******/ 		if(!__webpack_require__.o(exports, name)) {
/******/ 			Object.defineProperty(exports, name, {
/******/ 				configurable: false,
/******/ 				enumerable: true,
/******/ 				get: getter
/******/ 			});
/******/ 		}
/******/ 	};
/******/
/******/ 	// getDefaultExport function for compatibility with non-harmony modules
/******/ 	__webpack_require__.n = function(module) {
/******/ 		var getter = module && module.__esModule ?
/******/ 			function getDefault() { return module['default']; } :
/******/ 			function getModuleExports() { return module; };
/******/ 		__webpack_require__.d(getter, 'a', getter);
/******/ 		return getter;
/******/ 	};
/******/
/******/ 	// Object.prototype.hasOwnProperty.call
/******/ 	__webpack_require__.o = function(object, property) { return Object.prototype.hasOwnProperty.call(object, property); };
/******/
/******/ 	// __webpack_public_path__
/******/ 	__webpack_require__.p = "";
/******/
/******/ 	// Load entry module and return exports
/******/ 	return __webpack_require__(__webpack_require__.s = 234);
/******/ })
/************************************************************************/
/******/ ([
/* 0 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
Object.defineProperty(exports,"__esModule",{value:!0});var container_1=__webpack_require__(122);exports.Container=container_1.Container;var container_module_1=__webpack_require__(123);exports.ContainerModule=container_module_1.ContainerModule;var injectable_1=__webpack_require__(112);exports.injectable=injectable_1.injectable;var tagged_1=__webpack_require__(117);exports.tagged=tagged_1.tagged;var named_1=__webpack_require__(114);exports.named=named_1.named;var inject_1=__webpack_require__(111);exports.inject=inject_1.inject;var optional_1=__webpack_require__(115);exports.optional=optional_1.optional;var unmanaged_1=__webpack_require__(119);exports.unmanaged=unmanaged_1.unmanaged;var multi_inject_1=__webpack_require__(113);exports.multiInject=multi_inject_1.multiInject;var target_name_1=__webpack_require__(118);exports.targetName=target_name_1.targetName;var post_construct_1=__webpack_require__(116);exports.postConstruct=post_construct_1.postConstruct;var metadata_reader_1=__webpack_require__(74);exports.MetadataReader=metadata_reader_1.MetadataReader;var guid_1=__webpack_require__(17);exports.guid=guid_1.guid;var decorator_utils_1=__webpack_require__(13);exports.decorate=decorator_utils_1.decorate;var constraint_helpers_1=__webpack_require__(77);exports.traverseAncerstors=constraint_helpers_1.traverseAncerstors,exports.taggedConstraint=constraint_helpers_1.taggedConstraint,exports.namedConstraint=constraint_helpers_1.namedConstraint,exports.typeConstraint=constraint_helpers_1.typeConstraint;var serialization_1=__webpack_require__(29);exports.getServiceIdentifierAsString=serialization_1.getServiceIdentifierAsString;

/***/ }),
/* 1 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
Object.defineProperty(exports,"__esModule",{value:!0}),exports.TYPES={IActionDispatcher:Symbol("IActionDispatcher"),IActionDispatcherProvider:Symbol("IActionDispatcherProvider"),IActionHandlerInitializer:Symbol("IActionHandlerInitializer"),ActionHandlerRegistry:Symbol("ActionHandlerRegistry"),AnimationFrameSyncer:Symbol("AnimationFrameSyncer"),CommandStackOptions:Symbol("CommandStackOptions"),IButtonHandler:Symbol("IButtonHandler"),ICommand:Symbol("ICommand"),ICommandStack:Symbol("ICommandStack"),ICommandStackProvider:Symbol("ICommandStackProvider"),DOMHelper:Symbol("DOMHelper"),HiddenVNodeDecorator:Symbol("HiddenVNodeDecorator"),HoverState:Symbol("HoverState"),KeyListener:Symbol("KeyListener"),Layouter:Symbol("Layouter"),LayoutRegistry:Symbol("LayoutRegistry"),ILogger:Symbol("ILogger"),LogLevel:Symbol("LogLevel"),IModelFactory:Symbol("IModelFactory"),IModelLayoutEngine:Symbol("IModelLayoutEngine"),ModelRendererFactory:Symbol("ModelRendererFactory"),ModelSource:Symbol("ModelSource"),ModelSourceProvider:Symbol("ModelSourceProvider"),MouseListener:Symbol("MouseListener"),PopupModelFactory:Symbol("PopupModelFactory"),IPopupModelProvider:Symbol("IPopupModelProvider"),PopupMouseListener:Symbol("PopupMouseListener"),PopupVNodeDecorator:Symbol("PopupVNodeDecorator"),SModelElementRegistration:Symbol("SModelElementRegistration"),SModelRegistry:Symbol("SModelRegistry"),SModelStorage:Symbol("SModelStorage"),StateAwareModelProvider:Symbol("StateAwareModelProvider"),SvgExporter:Symbol("SvgExporter"),IViewer:Symbol("IViewer"),ViewerOptions:Symbol("ViewerOptions"),IViewerProvider:Symbol("IViewerProvider"),ViewRegistration:Symbol("ViewRegistration"),ViewRegistry:Symbol("ViewRegistry"),IVNodeDecorator:Symbol("IVNodeDecorator")};

/***/ }),
/* 2 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
function isParent(e){var t=e.children;return void 0!==t&&t.constructor===Array}function createRandomId(e){void 0===e&&(e=8);for(var t="",n=0;n<e;n++)t+=ID_CHARS.charAt(Math.floor(Math.random()*ID_CHARS.length));return t}var __extends=this&&this.__extends||function(){var e=Object.setPrototypeOf||{__proto__:[]}instanceof Array&&function(e,t){e.__proto__=t}||function(e,t){for(var n in t)t.hasOwnProperty(n)&&(e[n]=t[n])};return function(t,n){function r(){this.constructor=t}e(t,n),t.prototype=null===n?Object.create(n):(r.prototype=n.prototype,new r)}}();Object.defineProperty(exports,"__esModule",{value:!0});var geometry_1=__webpack_require__(3),iterable_1=__webpack_require__(70),SModelElement=function(){function e(){}return Object.defineProperty(e.prototype,"root",{get:function(){for(var e=this;e;){if(e instanceof SModelRoot)return e;e=e instanceof SChildElement?e.parent:void 0}throw new Error("Element has no root")},enumerable:!0,configurable:!0}),Object.defineProperty(e.prototype,"index",{get:function(){return this.root.index},enumerable:!0,configurable:!0}),e.prototype.hasFeature=function(e){return!1},e}();exports.SModelElement=SModelElement,exports.isParent=isParent;var SParentElement=function(e){function t(){var t=null!==e&&e.apply(this,arguments)||this;return t.children=[],t}return __extends(t,e),t.prototype.add=function(e,t){var n=this.children;if(void 0===t)n.push(e);else{if(t<0||t>this.children.length)throw new Error("Child index "+t+" out of bounds (0.."+n.length+")");n.splice(t,0,e)}e.parent=this,this.index.add(e)},t.prototype.remove=function(e){var t=this.children,n=t.indexOf(e);if(n<0)throw new Error("No such child "+e.id);t.splice(n,1),delete e.parent,this.index.remove(e)},t.prototype.removeAll=function(e){var t=this,n=this.children;if(void 0!==e){for(var r=n.length-1;r>=0;r--)if(e(n[r])){var o=n.splice(r,1)[0];delete o.parent,this.index.remove(o)}}else n.forEach(function(e){delete e.parent,t.index.remove(e)}),n.splice(0,n.length)},t.prototype.move=function(e,t){var n=this.children,r=n.indexOf(e);if(-1===r)throw new Error("No such child "+e.id);if(t<0||t>n.length-1)throw new Error("Child index "+t+" out of bounds (0.."+n.length+")");n.splice(r,1),n.splice(t,0,e)},t.prototype.localToParent=function(e){return geometry_1.isBounds(e)?e:{x:e.x,y:e.y,width:-1,height:-1}},t.prototype.parentToLocal=function(e){return geometry_1.isBounds(e)?e:{x:e.x,y:e.y,width:-1,height:-1}},t}(SModelElement);exports.SParentElement=SParentElement;var SChildElement=function(e){function t(){return null!==e&&e.apply(this,arguments)||this}return __extends(t,e),t}(SParentElement);exports.SChildElement=SChildElement;var SModelRoot=function(e){function t(t){void 0===t&&(t=new SModelIndex);var n=e.call(this)||this;return n.canvasBounds=geometry_1.EMPTY_BOUNDS,Object.defineProperty(n,"index",{value:t,writable:!1}),n}return __extends(t,e),t}(SParentElement);exports.SModelRoot=SModelRoot;var ID_CHARS="0123456789abcdefghijklmnopqrstuvwxyz";exports.createRandomId=createRandomId;var SModelIndex=function(){function e(){this.id2element=new Map}return e.prototype.add=function(e){if(e.id){if(this.contains(e))throw new Error("Duplicate ID in model: "+e.id)}else do{e.id=createRandomId()}while(this.contains(e));if(this.id2element.set(e.id,e),void 0!==e.children&&e.children.constructor===Array)for(var t=0,n=e.children;t<n.length;t++){var r=n[t];this.add(r)}},e.prototype.remove=function(e){if(this.id2element.delete(e.id),void 0!==e.children&&e.children.constructor===Array)for(var t=0,n=e.children;t<n.length;t++){var r=n[t];this.remove(r)}},e.prototype.contains=function(e){return this.id2element.has(e.id)},e.prototype.getById=function(e){return this.id2element.get(e)},e.prototype.getAttachedElements=function(e){return[]},e.prototype.all=function(){return iterable_1.mapIterable(this.id2element,function(e){e[0];return e[1]})},e}();exports.SModelIndex=SModelIndex;

/***/ }),
/* 3 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
function add(t,e){return{x:t.x+e.x,y:t.y+e.y}}function subtract(t,e){return{x:t.x-e.x,y:t.y-e.y}}function isValidDimension(t){return t.width>=0&&t.height>=0}function isBounds(t){return"x"in t&&"y"in t&&"width"in t&&"height"in t}function combine(t,e){var n=Math.min(t.x,e.x),i=Math.min(t.y,e.y);return{x:n,y:i,width:Math.max(t.x+(t.width>=0?t.width:0),e.x+(e.width>=0?e.width:0))-n,height:Math.max(t.y+(t.height>=0?t.height:0),e.y+(e.height>=0?e.height:0))-i}}function translate(t,e){return{x:t.x+e.x,y:t.y+e.y,width:t.width,height:t.height}}function center(t){return{x:t.x+(t.width>=0?.5*t.width:0),y:t.y+(t.height>=0?.5*t.height:0)}}function centerOfLine(t,e){return center({x:t.x>e.x?e.x:t.x,y:t.y>e.y?e.y:t.y,width:Math.abs(e.x-t.x),height:Math.abs(e.y-t.y)})}function includes(t,e){return e.x>=t.x&&e.x<=t.x+t.width&&e.y>=t.y&&e.y<=t.y+t.height}function euclideanDistance(t,e){var n=e.x-t.x,i=e.y-t.y;return Math.sqrt(n*n+i*i)}function manhattanDistance(t,e){return Math.abs(e.x-t.x)+Math.abs(e.y-t.y)}function maxDistance(t,e){return Math.max(Math.abs(e.x-t.x),Math.abs(e.y-t.y))}function angleOfPoint(t){return Math.atan2(t.y,t.x)}function angleBetweenPoints(t,e){var n=Math.sqrt((t.x*t.x+t.y*t.y)*(e.x*e.x+e.y*e.y));if(isNaN(n)||0===n)return NaN;var i=t.x*e.x+t.y*e.y;return Math.acos(i/n)}function toDegrees(t){return 180*t/Math.PI}function toRadians(t){return t*Math.PI/180}function almostEquals(t,e){return Math.abs(t-e)<.001}Object.defineProperty(exports,"__esModule",{value:!0}),exports.ORIGIN_POINT=Object.freeze({x:0,y:0}),exports.add=add,exports.subtract=subtract,exports.EMPTY_DIMENSION=Object.freeze({width:-1,height:-1}),exports.isValidDimension=isValidDimension,exports.EMPTY_BOUNDS=Object.freeze({x:0,y:0,width:-1,height:-1}),exports.isBounds=isBounds,exports.combine=combine,exports.translate=translate,exports.center=center,exports.centerOfLine=centerOfLine,exports.includes=includes;var Direction;!function(t){t[t.left=0]="left",t[t.right=1]="right",t[t.up=2]="up",t[t.down=3]="down"}(Direction=exports.Direction||(exports.Direction={})),exports.euclideanDistance=euclideanDistance,exports.manhattanDistance=manhattanDistance,exports.maxDistance=maxDistance,exports.angleOfPoint=angleOfPoint,exports.angleBetweenPoints=angleBetweenPoints,exports.toDegrees=toDegrees,exports.toRadians=toRadians,exports.almostEquals=almostEquals;

/***/ }),
/* 4 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
Object.defineProperty(exports,"__esModule",{value:!0}),exports.NAMED_TAG="named",exports.NAME_TAG="name",exports.UNMANAGED_TAG="unmanaged",exports.OPTIONAL_TAG="optional",exports.INJECT_TAG="inject",exports.MULTI_INJECT_TAG="multi_inject",exports.TAGGED="inversify:tagged",exports.TAGGED_PROP="inversify:tagged_props",exports.PARAM_TYPES="inversify:paramtypes",exports.DESIGN_PARAM_TYPES="design:paramtypes",exports.POST_CONSTRUCT="post_construct";

/***/ }),
/* 5 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
var __extends=this&&this.__extends||function(){var t=Object.setPrototypeOf||{__proto__:[]}instanceof Array&&function(t,n){t.__proto__=n}||function(t,n){for(var e in n)n.hasOwnProperty(e)&&(t[e]=n[e])};return function(n,e){function o(){this.constructor=n}t(n,e),n.prototype=null===e?Object.create(e):(o.prototype=e.prototype,new o)}}(),__decorate=this&&this.__decorate||function(t,n,e,o){var r,a=arguments.length,i=a<3?n:null===o?o=Object.getOwnPropertyDescriptor(n,e):o;if("object"==typeof Reflect&&"function"==typeof Reflect.decorate)i=Reflect.decorate(t,n,e,o);else for(var m=t.length-1;m>=0;m--)(r=t[m])&&(i=(a<3?r(i):a>3?r(n,e,i):r(n,e))||i);return a>3&&i&&Object.defineProperty(n,e,i),i},__metadata=this&&this.__metadata||function(t,n){if("object"==typeof Reflect&&"function"==typeof Reflect.metadata)return Reflect.metadata(t,n)},__param=this&&this.__param||function(t,n){return function(e,o){n(e,o,t)}};Object.defineProperty(exports,"__esModule",{value:!0});var inversify_1=__webpack_require__(0),types_1=__webpack_require__(1),Command=function(){function t(){}return t}();exports.Command=Command;var MergeableCommand=function(t){function n(){return null!==t&&t.apply(this,arguments)||this}return __extends(n,t),n.prototype.merge=function(t,n){return!1},n}(Command);exports.MergeableCommand=MergeableCommand;var HiddenCommand=function(t){function n(){return null!==t&&t.apply(this,arguments)||this}return __extends(n,t),n.prototype.undo=function(t){return t.logger.error(this,"Cannot undo a hidden command"),t.root},n.prototype.redo=function(t){return t.logger.error(this,"Cannot redo a hidden command"),t.root},n}(Command);exports.HiddenCommand=HiddenCommand;var PopupCommand=function(t){function n(){return null!==t&&t.apply(this,arguments)||this}return __extends(n,t),n}(Command);exports.PopupCommand=PopupCommand;var SystemCommand=function(t){function n(){return null!==t&&t.apply(this,arguments)||this}return __extends(n,t),n}(Command);exports.SystemCommand=SystemCommand;var CommandActionHandler=function(){function t(t){this.commandType=t}return t.prototype.handle=function(t){return new this.commandType(t)},t}();exports.CommandActionHandler=CommandActionHandler;var CommandActionHandlerInitializer=function(){function t(t){this.commandCtrs=t}return t.prototype.initialize=function(t){this.commandCtrs.forEach(function(n){return t.registerCommand(n)})},t=__decorate([inversify_1.injectable(),__param(0,inversify_1.multiInject(types_1.TYPES.ICommand)),__param(0,inversify_1.optional()),__metadata("design:paramtypes",[Array])],t)}();exports.CommandActionHandlerInitializer=CommandActionHandlerInitializer;

/***/ }),
/* 6 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
function getBasicType(e){if(!e.type)return"";var t=e.type.indexOf(":");return t>=0?e.type.substring(0,t):e.type}function getSubType(e){if(!e.type)return"";var t=e.type.indexOf(":");return t>=0?e.type.substring(t+1):e.type}function findElement(e,t){if(e.id===t)return e;if(void 0!==e.children)for(var n=0,r=e.children;n<r.length;n++){var i=r[n],o=findElement(i,t);if(void 0!==o)return o}}function findParent(e,t){for(var n=e;void 0!==n;){if(t(n))return n;n=n instanceof smodel_1.SChildElement?n.parent:void 0}return n}function findParentByFeature(e,t){for(var n=e;void 0!==n;){if(t(n))return n;n=n instanceof smodel_1.SChildElement?n.parent:void 0}return n}function translatePoint(e,t,n){if(t!==n){for(;t instanceof smodel_1.SChildElement;)if(e=t.localToParent(e),(t=t.parent)===n)return e;for(var r=[];n instanceof smodel_1.SChildElement;)r.push(n),n=n.parent;if(t!==n)throw new Error("Incompatible source and target: "+t.id+", "+n.id);for(var i=r.length-1;i>=0;i--)e=r[i].parentToLocal(e)}return e}Object.defineProperty(exports,"__esModule",{value:!0});var smodel_1=__webpack_require__(2);exports.getBasicType=getBasicType,exports.getSubType=getSubType,exports.findElement=findElement,exports.findParent=findParent,exports.findParentByFeature=findParentByFeature,exports.translatePoint=translatePoint;

/***/ }),
/* 7 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
function isBoundsAware(e){return"bounds"in e}function isLayoutContainer(e){return isBoundsAware(e)&&e.hasFeature(exports.layoutContainerFeature)&&"layout"in e}function isLayoutableChild(e){return isBoundsAware(e)&&e.hasFeature(exports.layoutableChildFeature)}function isSizeable(e){return e.hasFeature(exports.boundsFeature)&&isBoundsAware(e)}function isAlignable(e){return e.hasFeature(exports.alignFeature)&&"alignment"in e}function getAbsoluteBounds(e){var t=smodel_utils_1.findParentByFeature(e,isBoundsAware);if(void 0!==t){for(var o=t.bounds,i=t;i instanceof smodel_1.SChildElement;){var n=i.parent;o=n.localToParent(o),i=n}return o}if(e instanceof smodel_1.SModelRoot){var r=e.canvasBounds;return{x:0,y:0,width:r.width,height:r.height}}return geometry_1.EMPTY_BOUNDS}var __extends=this&&this.__extends||function(){var e=Object.setPrototypeOf||{__proto__:[]}instanceof Array&&function(e,t){e.__proto__=t}||function(e,t){for(var o in t)t.hasOwnProperty(o)&&(e[o]=t[o])};return function(t,o){function i(){this.constructor=t}e(t,o),t.prototype=null===o?Object.create(o):(i.prototype=o.prototype,new i)}}();Object.defineProperty(exports,"__esModule",{value:!0});var geometry_1=__webpack_require__(3),smodel_1=__webpack_require__(2),smodel_utils_1=__webpack_require__(6);exports.boundsFeature=Symbol("boundsFeature"),exports.layoutContainerFeature=Symbol("layoutContainerFeature"),exports.layoutableChildFeature=Symbol("layoutableChildFeature"),exports.alignFeature=Symbol("alignFeature"),exports.isBoundsAware=isBoundsAware,exports.isLayoutContainer=isLayoutContainer,exports.isLayoutableChild=isLayoutableChild,exports.isSizeable=isSizeable,exports.isAlignable=isAlignable,exports.getAbsoluteBounds=getAbsoluteBounds;var SShapeElement=function(e){function t(){var t=null!==e&&e.apply(this,arguments)||this;return t.position=geometry_1.ORIGIN_POINT,t.size=geometry_1.EMPTY_DIMENSION,t}return __extends(t,e),Object.defineProperty(t.prototype,"bounds",{get:function(){return{x:this.position.x,y:this.position.y,width:this.size.width,height:this.size.height}},set:function(e){this.position={x:e.x,y:e.y},this.size={width:e.width,height:e.height}},enumerable:!0,configurable:!0}),t.prototype.localToParent=function(e){var t={x:e.x+this.position.x,y:e.y+this.position.y,width:-1,height:-1};return geometry_1.isBounds(e)&&(t.width=e.width,t.height=e.height),t},t.prototype.parentToLocal=function(e){var t={x:e.x-this.position.x,y:e.y-this.position.y,width:-1,height:-1};return geometry_1.isBounds(e)&&(t.width=e.width,t.height=e.height),t},t}(smodel_1.SChildElement);exports.SShapeElement=SShapeElement;

/***/ }),
/* 8 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
Object.defineProperty(exports,"__esModule",{value:!0});var template_1=__webpack_require__(137);exports.DUPLICATED_INJECTABLE_DECORATOR="Cannot apply @injectable decorator multiple times.",exports.DUPLICATED_METADATA="Metadata key was used more than once in a parameter:",exports.NULL_ARGUMENT="NULL argument",exports.KEY_NOT_FOUND="Key Not Found",exports.AMBIGUOUS_MATCH="Ambiguous match found for serviceIdentifier:",exports.CANNOT_UNBIND="Could not unbind serviceIdentifier:",exports.NOT_REGISTERED="No matching bindings found for serviceIdentifier:",exports.MISSING_INJECTABLE_ANNOTATION="Missing required @injectable annotation in:",exports.MISSING_INJECT_ANNOTATION="Missing required @inject or @multiInject annotation in:",exports.CIRCULAR_DEPENDENCY="Circular dependency found:",exports.NOT_IMPLEMENTED="Sorry, this feature is not fully implemented yet.",exports.INVALID_BINDING_TYPE="Invalid binding type:",exports.NO_MORE_SNAPSHOTS_AVAILABLE="No snapshot available to restore.",exports.INVALID_MIDDLEWARE_RETURN="Invalid return type in middleware. Middleware must return!",exports.INVALID_FUNCTION_BINDING="Value provided to function binding must be a function!",exports.INVALID_TO_SELF_VALUE="The toSelf function can only be applied when a constructor is used as service identifier",exports.INVALID_DECORATOR_OPERATION="The @inject @multiInject @tagged and @named decorators must be applied to the parameters of a class constructor or a class property.",exports.ARGUMENTS_LENGTH_MISMATCH_1="The number of constructor arguments in the derived class ",exports.ARGUMENTS_LENGTH_MISMATCH_2=" must be >= than the number of constructor arguments of its base class.",exports.CONTAINER_OPTIONS_MUST_BE_AN_OBJECT="Invalid Container constructor argument. Container options must be an object.",exports.CONTAINER_OPTIONS_INVALID_DEFAULT_SCOPE="Invalid Container option. Default scope must be a string ('singleton' or 'transient').",exports.MULTIPLE_POST_CONSTRUCT_METHODS="Cannot apply @postConstruct decorator multiple times in the same class",exports.POST_CONSTRUCT_ERROR=(_a=["@postConstruct error in class ",": ",""],_a.raw=["@postConstruct error in class ",": ",""],template_1.template(_a,0,1));var _a;

/***/ }),
/* 9 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
Object.defineProperty(exports,"__esModule",{value:!0});var METADATA_KEY=__webpack_require__(4),Metadata=function(){function t(t,e){this.key=t,this.value=e}return t.prototype.toString=function(){return this.key===METADATA_KEY.NAMED_TAG?"named: "+this.value.toString()+" ":"tagged: { key:"+this.key.toString()+", value: "+this.value+" }"},t}();exports.Metadata=Metadata;

/***/ }),
/* 10 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
var __extends=this&&this.__extends||function(){var e=Object.setPrototypeOf||{__proto__:[]}instanceof Array&&function(e,t){e.__proto__=t}||function(e,t){for(var r in t)t.hasOwnProperty(r)&&(e[r]=t[r])};return function(t,r){function i(){this.constructor=t}e(t,r),t.prototype=null===r?Object.create(r):(i.prototype=r.prototype,new i)}}(),__decorate=this&&this.__decorate||function(e,t,r,i){var n,o=arguments.length,a=o<3?t:null===i?i=Object.getOwnPropertyDescriptor(t,r):i;if("object"==typeof Reflect&&"function"==typeof Reflect.decorate)a=Reflect.decorate(e,t,r,i);else for(var s=e.length-1;s>=0;s--)(n=e[s])&&(a=(o<3?n(a):o>3?n(t,r,a):n(t,r))||a);return o>3&&a&&Object.defineProperty(t,r,a),a},__metadata=this&&this.__metadata||function(e,t){if("object"==typeof Reflect&&"function"==typeof Reflect.metadata)return Reflect.metadata(e,t)},__param=this&&this.__param||function(e,t){return function(r,i){t(r,i,e)}};Object.defineProperty(exports,"__esModule",{value:!0});var inversify_1=__webpack_require__(0),types_1=__webpack_require__(1),registry_1=__webpack_require__(26),smodel_1=__webpack_require__(2),SModelFactory=function(){function e(e){this.registry=e}return e.prototype.createElement=function(e,t){var r;if(this.registry.hasKey(e.type)){var i=this.registry.get(e.type,void 0);if(!(i instanceof smodel_1.SChildElement))throw new Error("Element with type "+e.type+" was expected to be an SChildElement.");r=i}else r=new smodel_1.SChildElement;return this.initializeChild(r,e,t)},e.prototype.createRoot=function(e){var t;if(this.registry.hasKey(e.type)){var r=this.registry.get(e.type,void 0);if(!(r instanceof smodel_1.SModelRoot))throw new Error("Element with type "+e.type+" was expected to be an SModelRoot.");t=r}else t=new smodel_1.SModelRoot;return this.initializeRoot(t,e)},e.prototype.createSchema=function(e){var t=this,r={};for(var i in e)if(!this.isReserved(e,i)){var n=e[i];"function"!=typeof n&&(r[i]=n)}return e instanceof smodel_1.SParentElement&&(r.children=e.children.map(function(e){return t.createSchema(e)})),r},e.prototype.initializeElement=function(e,t){for(var r in t)if(!this.isReserved(e,r)){var i=t[r];"function"!=typeof i&&(e[r]=i)}return e},e.prototype.isReserved=function(e,t){if(["children","parent","index"].indexOf(t)>=0)return!0;var r=e;do{var i=Object.getOwnPropertyDescriptor(r,t);if(void 0!==i)return void 0!==i.get;r=Object.getPrototypeOf(r)}while(r);return!1},e.prototype.initializeParent=function(e,t){var r=this;return this.initializeElement(e,t),smodel_1.isParent(t)&&(e.children=t.children.map(function(t){return r.createElement(t,e)})),e},e.prototype.initializeChild=function(e,t,r){return this.initializeParent(e,t),void 0!==r&&(e.parent=r),e},e.prototype.initializeRoot=function(e,t){return this.initializeParent(e,t),e.index.add(e),e},e=__decorate([inversify_1.injectable(),__param(0,inversify_1.inject(types_1.TYPES.SModelRegistry)),__metadata("design:paramtypes",[SModelRegistry])],e)}();exports.SModelFactory=SModelFactory,exports.EMPTY_ROOT=Object.freeze({type:"NONE",id:"EMPTY"});var SModelRegistry=function(e){function t(t){var r=e.call(this)||this;return t.forEach(function(e){return r.register(e.type,e.constr)}),r}return __extends(t,e),t=__decorate([inversify_1.injectable(),__param(0,inversify_1.multiInject(types_1.TYPES.SModelElementRegistration)),__param(0,inversify_1.optional()),__metadata("design:paramtypes",[Array])],t)}(registry_1.ProviderRegistry);exports.SModelRegistry=SModelRegistry;

/***/ }),
/* 11 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
function setAttr(t,e,s){getAttrs(t)[e]=s}function setClass(t,e,s){getClass(t)[e]=s}function copyClassesFromVNode(t,e){var s=getClass(t);for(var r in s)s.hasOwnProperty(r)&&setClass(e,r,!0)}function copyClassesFromElement(t,e){for(var s=t.classList,r=0;r<s.length;r++){var a=s.item(r);a&&setClass(e,a,!0)}}function mergeStyle(t,e){getData(t).style=__assign({},getData(t).style||{},e)}function on(t,e,s,r){var a=getOn(t);if(a[e])throw new Error("EventListener for "+e+" already registered on VNode");a[e]=[s,r]}function getAttrs(t){var e=getData(t);return e.attrs||(e.attrs={}),e.attrs}function getData(t){return t.data||(t.data={}),t.data}function getClass(t){var e=getData(t);return e.class||(e.class={}),e.class}function getOn(t){var e=getData(t);return e.on||(e.on={}),e.on}var __assign=this&&this.__assign||Object.assign||function(t){for(var e,s=1,r=arguments.length;s<r;s++){e=arguments[s];for(var a in e)Object.prototype.hasOwnProperty.call(e,a)&&(t[a]=e[a])}return t};Object.defineProperty(exports,"__esModule",{value:!0}),exports.setAttr=setAttr,exports.setClass=setClass,exports.copyClassesFromVNode=copyClassesFromVNode,exports.copyClassesFromElement=copyClassesFromElement,exports.mergeStyle=mergeStyle,exports.on=on,exports.getAttrs=getAttrs;

/***/ }),
/* 12 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
function isSelectable(e){return e.hasFeature(exports.selectFeature)}Object.defineProperty(exports,"__esModule",{value:!0}),exports.selectFeature=Symbol("selectFeature"),exports.isSelectable=isSelectable;

/***/ }),
/* 13 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
function tagParameter(e,r,t,a){_tagParameterOrProperty(METADATA_KEY.TAGGED,e,r,a,t)}function tagProperty(e,r,t){_tagParameterOrProperty(METADATA_KEY.TAGGED_PROP,e.constructor,r,t)}function _tagParameterOrProperty(e,r,t,a,o){var n={},c="number"==typeof o,_=void 0!==o&&c?o.toString():t;if(!0===c&&void 0!==t)throw new Error(ERROR_MSGS.INVALID_DECORATOR_OPERATION);!0===Reflect.hasOwnMetadata(e,r)&&(n=Reflect.getMetadata(e,r));var f=n[_];if(!0!==Array.isArray(f))f=[];else for(var i=0;i<f.length;i++){var s=f[i];if(s.key===a.key)throw new Error(ERROR_MSGS.DUPLICATED_METADATA+" "+s.key)}f.push(a),n[_]=f,Reflect.defineMetadata(e,n,r)}function _decorate(e,r){Reflect.decorate(e,r)}function _param(e,r){return function(t,a){r(t,a,e)}}function decorate(e,r,t){"number"==typeof t?_decorate([_param(t,e)],r):"string"==typeof t?Reflect.decorate([e],r,t):_decorate([e],r)}Object.defineProperty(exports,"__esModule",{value:!0});var METADATA_KEY=__webpack_require__(4),ERROR_MSGS=__webpack_require__(8);exports.tagParameter=tagParameter,exports.tagProperty=tagProperty,exports.decorate=decorate;

/***/ }),
/* 14 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
Object.defineProperty(exports,"__esModule",{value:!0});var BindingScopeEnum={Singleton:"Singleton",Transient:"Transient"};exports.BindingScopeEnum=BindingScopeEnum;var BindingTypeEnum={ConstantValue:"ConstantValue",Constructor:"Constructor",DynamicValue:"DynamicValue",Factory:"Factory",Function:"Function",Instance:"Instance",Invalid:"Invalid",Provider:"Provider"};exports.BindingTypeEnum=BindingTypeEnum;var TargetTypeEnum={ClassProperty:"ClassProperty",ConstructorArgument:"ConstructorArgument",Variable:"Variable"};exports.TargetTypeEnum=TargetTypeEnum;

/***/ }),
/* 15 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
var __extends=this&&this.__extends||function(){var e=Object.setPrototypeOf||{__proto__:[]}instanceof Array&&function(e,t){e.__proto__=t}||function(e,t){for(var o in t)t.hasOwnProperty(o)&&(e[o]=t[o])};return function(t,o){function n(){this.constructor=t}e(t,o),t.prototype=null===o?Object.create(o):(n.prototype=o.prototype,new n)}}(),__decorate=this&&this.__decorate||function(e,t,o,n){var i,r=arguments.length,s=r<3?t:null===n?n=Object.getOwnPropertyDescriptor(t,o):n;if("object"==typeof Reflect&&"function"==typeof Reflect.decorate)s=Reflect.decorate(e,t,o,n);else for(var u=e.length-1;u>=0;u--)(i=e[u])&&(s=(r<3?i(s):r>3?i(t,o,s):i(t,o))||s);return r>3&&s&&Object.defineProperty(t,o,s),s},__metadata=this&&this.__metadata||function(e,t){if("object"==typeof Reflect&&"function"==typeof Reflect.metadata)return Reflect.metadata(e,t)},__param=this&&this.__param||function(e,t){return function(o,n){t(o,n,e)}};Object.defineProperty(exports,"__esModule",{value:!0});var inversify_1=__webpack_require__(0),types_1=__webpack_require__(1),smodel_1=__webpack_require__(2),action_1=__webpack_require__(53),vnode_utils_1=__webpack_require__(11),dom_helper_1=__webpack_require__(55),MouseTool=function(){function e(e,t,o){void 0===o&&(o=[]),this.actionDispatcher=e,this.domHelper=t,this.mouseListeners=o}return e.prototype.register=function(e){this.mouseListeners.push(e)},e.prototype.deregister=function(e){var t=this.mouseListeners.indexOf(e);t>=0&&this.mouseListeners.splice(t,1)},e.prototype.getTargetElement=function(e,t){for(var o=t.target,n=e.index;o;){if(o.id){var i=n.getById(this.domHelper.findSModelIdByDOMElement(o));if(void 0!==i)return i}o=o.parentNode}},e.prototype.handleEvent=function(e,t,o){var n=this;this.focusOnMouseEvent(e,t);var i=this.getTargetElement(t,o);if(i){var r=this.mouseListeners.map(function(t){return t[e].apply(t,[i,o])}).reduce(function(e,t){return e.concat(t)});if(r.length>0){o.preventDefault();for(var s=0,u=r;s<u.length;s++){var p=u[s];action_1.isAction(p)?this.actionDispatcher.dispatch(p):p.then(function(e){n.actionDispatcher.dispatch(e)})}}}},e.prototype.focusOnMouseEvent=function(e,t){if(document){var o=document.getElementById(this.domHelper.createUniqueDOMElementId(t));"mouseDown"===e&&null!==o&&"function"==typeof o.focus&&o.focus()}},e.prototype.mouseOver=function(e,t){this.handleEvent("mouseOver",e,t)},e.prototype.mouseOut=function(e,t){this.handleEvent("mouseOut",e,t)},e.prototype.mouseEnter=function(e,t){this.handleEvent("mouseEnter",e,t)},e.prototype.mouseLeave=function(e,t){this.handleEvent("mouseLeave",e,t)},e.prototype.mouseDown=function(e,t){this.handleEvent("mouseDown",e,t)},e.prototype.mouseMove=function(e,t){this.handleEvent("mouseMove",e,t)},e.prototype.mouseUp=function(e,t){this.handleEvent("mouseUp",e,t)},e.prototype.wheel=function(e,t){this.handleEvent("wheel",e,t)},e.prototype.doubleClick=function(e,t){this.handleEvent("doubleClick",e,t)},e.prototype.decorate=function(e,t){return t instanceof smodel_1.SModelRoot&&(vnode_utils_1.on(e,"mouseover",this.mouseOver.bind(this),t),vnode_utils_1.on(e,"mouseout",this.mouseOut.bind(this),t),vnode_utils_1.on(e,"mouseenter",this.mouseEnter.bind(this),t),vnode_utils_1.on(e,"mouseleave",this.mouseLeave.bind(this),t),vnode_utils_1.on(e,"mousedown",this.mouseDown.bind(this),t),vnode_utils_1.on(e,"mouseup",this.mouseUp.bind(this),t),vnode_utils_1.on(e,"mousemove",this.mouseMove.bind(this),t),vnode_utils_1.on(e,"wheel",this.wheel.bind(this),t),vnode_utils_1.on(e,"contextmenu",function(e,t){t.preventDefault()},t),vnode_utils_1.on(e,"dblclick",this.doubleClick.bind(this),t)),e=this.mouseListeners.reduce(function(e,o){return o.decorate(e,t)},e)},e.prototype.postUpdate=function(){},e=__decorate([inversify_1.injectable(),__param(0,inversify_1.inject(types_1.TYPES.IActionDispatcher)),__param(1,inversify_1.inject(types_1.TYPES.DOMHelper)),__param(2,inversify_1.multiInject(types_1.TYPES.MouseListener)),__param(2,inversify_1.optional()),__metadata("design:paramtypes",[Object,dom_helper_1.DOMHelper,Array])],e)}();exports.MouseTool=MouseTool;var PopupMouseTool=function(e){function t(t,o,n){void 0===n&&(n=[]);var i=e.call(this,t,o,n)||this;return i.actionDispatcher=t,i.domHelper=o,i.mouseListeners=n,i}return __extends(t,e),t=__decorate([inversify_1.injectable(),__param(0,inversify_1.inject(types_1.TYPES.IActionDispatcher)),__param(1,inversify_1.inject(types_1.TYPES.DOMHelper)),__param(2,inversify_1.multiInject(types_1.TYPES.PopupMouseListener)),__param(2,inversify_1.optional()),__metadata("design:paramtypes",[Object,dom_helper_1.DOMHelper,Array])],t)}(MouseTool);exports.PopupMouseTool=PopupMouseTool;var MouseListener=function(){function e(){}return e.prototype.mouseOver=function(e,t){return[]},e.prototype.mouseOut=function(e,t){return[]},e.prototype.mouseEnter=function(e,t){return[]},e.prototype.mouseLeave=function(e,t){return[]},e.prototype.mouseDown=function(e,t){return[]},e.prototype.mouseMove=function(e,t){return[]},e.prototype.mouseUp=function(e,t){return[]},e.prototype.wheel=function(e,t){return[]},e.prototype.doubleClick=function(e,t){return[]},e.prototype.decorate=function(e,t){return e},e=__decorate([inversify_1.injectable()],e)}();exports.MouseListener=MouseListener;

/***/ }),
/* 16 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
function isViewport(e){return e instanceof smodel_1.SModelRoot&&e.hasFeature(exports.viewportFeature)&&"zoom"in e&&"scroll"in e}Object.defineProperty(exports,"__esModule",{value:!0});var smodel_1=__webpack_require__(2);exports.viewportFeature=Symbol("viewportFeature"),exports.isViewport=isViewport;

/***/ }),
/* 17 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
function guid(){function t(){return Math.floor(65536*(1+Math.random())).toString(16).substring(1)}return t()+t()+"-"+t()+"-"+t()+"-"+t()+"-"+t()+t()+t()}Object.defineProperty(exports,"__esModule",{value:!0}),exports.guid=guid;

/***/ }),
/* 18 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
function isPrimitive(e){return"string"==typeof e||"number"==typeof e||"boolean"==typeof e||"symbol"==typeof e||null===e||void 0===e}function normalizeAttrs(e,r,t,n){function i(e,r,t){(o[e]||(o[e]={}))[r]=t}for(var o={ns:r},a=0,s=n.length;a<s;a++){var l=n[a];e[l]&&(o[l]=e[l])}for(var f in e)if("key"!==f&&"classNames"!==f&&"selector"!==f){var u=f.indexOf("-");u>0?i(f.slice(0,u),f.slice(u+1),e[f]):o[f]||i(t,f,e[f])}return o}function buildFromStringTag(e,r,t,n,i,o){if(i.selector&&(n+=i.selector),i.classNames){var a=i.classNames;n=n+"."+(Array.isArray(a)?a.join("."):a.replace(/\s+/g,"."))}return{sel:n,data:normalizeAttrs(i,e,r,t),children:o.map(function(e){return isPrimitive(e)?{text:e}:e}),key:i.key}}function buildFromComponent(e,r,t,n,i,o){var a;if("function"==typeof n)a=n(i,o);else if(n&&"function"==typeof n.view)a=n.view(i,o);else{if(!n||"function"!=typeof n.render)throw"JSX tag must be either a string, a function or an object with 'view' or 'render' methods";a=n.render(i,o)}return a.key=i.key,a}function flatten(e,r,t){for(var n=r,i=e.length;n<i;n++){var o=e[n];Array.isArray(o)?flatten(o,0,t):t.push(o)}}function maybeFlatten(e){if(e)for(var r=0,t=e.length;r<t;r++)if(Array.isArray(e[r])){var n=e.slice(0,r);flatten(e,r,n),e=n;break}return e}function buildVnode(e,r,t,n,i,o){return i=i||{},o=maybeFlatten(o),"string"==typeof n?buildFromStringTag(e,r,t,n,i,o):buildFromComponent(e,r,t,n,i,o)}function JSX(e,r,t){return function(n,i,o){return(arguments.length>3||!Array.isArray(o))&&(o=slice.call(arguments,2)),buildVnode(e,r||"props",t||modulesNS,n,i,o)}}var SVGNS="http://www.w3.org/2000/svg",modulesNS=["hook","on","style","class","props","attrs","dataset"],slice=Array.prototype.slice;module.exports={html:JSX(void 0),svg:JSX(SVGNS,"attrs"),JSX:JSX};

/***/ }),
/* 19 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
var __extends=this&&this.__extends||function(){var t=Object.setPrototypeOf||{__proto__:[]}instanceof Array&&function(t,n){t.__proto__=n}||function(t,n){for(var e in n)n.hasOwnProperty(e)&&(t[e]=n[e])};return function(n,e){function o(){this.constructor=n}t(n,e),n.prototype=null===e?Object.create(e):(o.prototype=e.prototype,new o)}}();Object.defineProperty(exports,"__esModule",{value:!0});var easing_1=__webpack_require__(80),Animation=function(){function t(t,n){void 0===n&&(n=easing_1.easeInOut),this.context=t,this.ease=n}return t.prototype.start=function(){var t=this;return new Promise(function(n,e){var o=void 0,i=0,r=function(e){i++;var s;void 0===o?(o=e,s=0):s=e-o;var a=Math.min(1,s/t.context.duration),c=t.tween(t.ease(a),t.context);t.context.modelChanged.update(c),1===a?(t.context.logger.log(t,1e3*i/t.context.duration+" fps"),n(c)):t.context.syncer.onNextFrame(r)};if(t.context.syncer.isAvailable())t.context.syncer.onNextFrame(r);else{var s=t.tween(1,t.context);n(s)}})},t}();exports.Animation=Animation;var CompoundAnimation=function(t){function n(n,e,o,i){void 0===o&&(o=[]),void 0===i&&(i=easing_1.easeInOut);var r=t.call(this,e,i)||this;return r.model=n,r.context=e,r.components=o,r.ease=i,r}return __extends(n,t),n.prototype.include=function(t){return this.components.push(t),this},n.prototype.tween=function(t,n){for(var e=0,o=this.components;e<o.length;e++){o[e].tween(t,n)}return this.model},n}(Animation);exports.CompoundAnimation=CompoundAnimation;

/***/ }),
/* 20 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
var __decorate=this&&this.__decorate||function(e,t,n,i){var o,r=arguments.length,s=r<3?t:null===i?i=Object.getOwnPropertyDescriptor(t,n):i;if("object"==typeof Reflect&&"function"==typeof Reflect.decorate)s=Reflect.decorate(e,t,n,i);else for(var a=e.length-1;a>=0;a--)(o=e[a])&&(s=(r<3?o(s):r>3?o(t,n,s):o(t,n))||s);return r>3&&s&&Object.defineProperty(t,n,s),s},__metadata=this&&this.__metadata||function(e,t){if("object"==typeof Reflect&&"function"==typeof Reflect.metadata)return Reflect.metadata(e,t)},__param=this&&this.__param||function(e,t){return function(n,i){t(n,i,e)}};Object.defineProperty(exports,"__esModule",{value:!0});var inversify_1=__webpack_require__(0),types_1=__webpack_require__(1),smodel_1=__webpack_require__(2),vnode_utils_1=__webpack_require__(11),KeyTool=function(){function e(e,t){void 0===t&&(t=[]),this.actionDispatcher=e,this.keyListeners=t}return e.prototype.register=function(e){this.keyListeners.push(e)},e.prototype.deregister=function(e){var t=this.keyListeners.indexOf(e);t>=0&&this.keyListeners.splice(t,1)},e.prototype.handleEvent=function(e,t,n){var i=this.keyListeners.map(function(i){return i[e].apply(i,[t,n])}).reduce(function(e,t){return e.concat(t)});i.length>0&&(n.preventDefault(),this.actionDispatcher.dispatchAll(i))},e.prototype.keyDown=function(e,t){this.handleEvent("keyDown",e,t)},e.prototype.focus=function(){},e.prototype.decorate=function(e,t){return t instanceof smodel_1.SModelRoot&&(vnode_utils_1.on(e,"focus",this.focus.bind(this),t),vnode_utils_1.on(e,"keydown",this.keyDown.bind(this),t)),e},e.prototype.postUpdate=function(){},e=__decorate([inversify_1.injectable(),__param(0,inversify_1.inject(types_1.TYPES.IActionDispatcher)),__param(1,inversify_1.multiInject(types_1.TYPES.KeyListener)),__param(1,inversify_1.optional()),__metadata("design:paramtypes",[Object,Array])],e)}();exports.KeyTool=KeyTool;var KeyListener=function(){function e(){}return e.prototype.keyDown=function(e,t){return[]},e=__decorate([inversify_1.injectable()],e)}();exports.KeyListener=KeyListener;

/***/ }),
/* 21 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
function isRoutable(e){return void 0!==e.routingPoints&&"function"==typeof e.route}function canEditRouting(e){return isRoutable(e)&&e.hasFeature(exports.editFeature)}function filterEditModeHandles(e,t){if(0===t.children.length)return e;for(var o=0;o<e.length;)!function(){var n=e[o];if(void 0!==n.pointIndex){var r=t.children.find(function(e){return e instanceof SRoutingHandle&&"junction"===e.kind&&e.pointIndex===n.pointIndex});if(void 0!==r&&r.editMode&&o>0&&o<e.length-1){var i=e[o-1],u=e[o+1],d={x:i.x-n.x,y:i.y-n.y},a={x:u.x-n.x,y:u.y-n.y},l=geometry_1.angleBetweenPoints(d,a);if(Math.abs(Math.PI-l)<HANDLE_REMOVE_THRESHOLD)return e.splice(o,1),"continue"}}o++}();return e}var __extends=this&&this.__extends||function(){var e=Object.setPrototypeOf||{__proto__:[]}instanceof Array&&function(e,t){e.__proto__=t}||function(e,t){for(var o in t)t.hasOwnProperty(o)&&(e[o]=t[o])};return function(t,o){function n(){this.constructor=t}e(t,o),t.prototype=null===o?Object.create(o):(n.prototype=o.prototype,new n)}}();Object.defineProperty(exports,"__esModule",{value:!0});var geometry_1=__webpack_require__(3),smodel_1=__webpack_require__(2),model_1=__webpack_require__(12),model_2=__webpack_require__(22),model_3=__webpack_require__(35);exports.editFeature=Symbol("editFeature"),exports.isRoutable=isRoutable,exports.canEditRouting=canEditRouting;var SRoutingHandle=function(e){function t(){var t=null!==e&&e.apply(this,arguments)||this;return t.editMode=!1,t.hoverFeedback=!1,t.selected=!1,t}return __extends(t,e),t.prototype.hasFeature=function(e){return e===model_1.selectFeature||e===model_2.moveFeature||e===model_3.hoverFeedbackFeature},t}(smodel_1.SChildElement);exports.SRoutingHandle=SRoutingHandle;var HANDLE_REMOVE_THRESHOLD=.1;exports.filterEditModeHandles=filterEditModeHandles;

/***/ }),
/* 22 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
function isLocateable(e){return void 0!==e.position}function isMoveable(e){return e.hasFeature(exports.moveFeature)&&isLocateable(e)}Object.defineProperty(exports,"__esModule",{value:!0}),exports.moveFeature=Symbol("moveFeature"),exports.isLocateable=isLocateable,exports.isMoveable=isMoveable;

/***/ }),
/* 23 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
var __extends=this&&this.__extends||function(){var t=Object.setPrototypeOf||{__proto__:[]}instanceof Array&&function(t,e){t.__proto__=e}||function(t,e){for(var r in e)e.hasOwnProperty(r)&&(t[r]=e[r])};return function(e,r){function n(){this.constructor=e}t(e,r),e.prototype=null===r?Object.create(r):(n.prototype=r.prototype,new n)}}(),__decorate=this&&this.__decorate||function(t,e,r,n){var i,o=arguments.length,a=o<3?e:null===n?n=Object.getOwnPropertyDescriptor(e,r):n;if("object"==typeof Reflect&&"function"==typeof Reflect.decorate)a=Reflect.decorate(t,e,r,n);else for(var c=t.length-1;c>=0;c--)(i=t[c])&&(a=(o<3?i(a):o>3?i(e,r,a):i(e,r))||a);return o>3&&a&&Object.defineProperty(e,r,a),a},__metadata=this&&this.__metadata||function(t,e){if("object"==typeof Reflect&&"function"==typeof Reflect.metadata)return Reflect.metadata(t,e)},__param=this&&this.__param||function(t,e){return function(r,n){e(r,n,t)}};Object.defineProperty(exports,"__esModule",{value:!0});var inversify_1=__webpack_require__(0),types_1=__webpack_require__(1),registry_1=__webpack_require__(26),command_1=__webpack_require__(5),ActionHandlerRegistry=function(t){function e(e){var r=t.call(this)||this;return e.forEach(function(t){return r.initializeActionHandler(t)}),r}return __extends(e,t),e.prototype.registerCommand=function(t){this.register(t.KIND,new command_1.CommandActionHandler(t))},e.prototype.initializeActionHandler=function(t){t.initialize(this)},e=__decorate([inversify_1.injectable(),__param(0,inversify_1.multiInject(types_1.TYPES.IActionHandlerInitializer)),__param(0,inversify_1.optional()),__metadata("design:paramtypes",[Array])],e)}(registry_1.MultiInstanceRegistry);exports.ActionHandlerRegistry=ActionHandlerRegistry;

/***/ }),
/* 24 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
var __decorate=this&&this.__decorate||function(e,t,r,i){var o,n=arguments.length,a=n<3?t:null===i?i=Object.getOwnPropertyDescriptor(t,r):i;if("object"==typeof Reflect&&"function"==typeof Reflect.decorate)a=Reflect.decorate(e,t,r,i);else for(var c=e.length-1;c>=0;c--)(o=e[c])&&(a=(n<3?o(a):n>3?o(t,r,a):o(t,r))||a);return n>3&&a&&Object.defineProperty(t,r,a),a},__metadata=this&&this.__metadata||function(e,t){if("object"==typeof Reflect&&"function"==typeof Reflect.metadata)return Reflect.metadata(e,t)},__param=this&&this.__param||function(e,t){return function(r,i){t(r,i,e)}};Object.defineProperty(exports,"__esModule",{value:!0});var model_1=__webpack_require__(7),action_dispatcher_1=__webpack_require__(52),types_1=__webpack_require__(1),geometry_1=__webpack_require__(3),inversify_1=__webpack_require__(0),ExportSvgAction=function(){function e(t){this.svg=t,this.kind=e.KIND}return e.KIND="exportSvg",e}();exports.ExportSvgAction=ExportSvgAction;var SvgExporter=function(){function e(e,t,r){this.options=e,this.actionDispatcher=t,this.log=r}return e.prototype.export=function(e){if("undefined"!=typeof document){var t=document.getElementById(this.options.hiddenDiv);if(null!==t&&t.firstElementChild&&"svg"===t.firstElementChild.tagName){var r=t.firstElementChild,i=this.createSvg(r,e);this.actionDispatcher.dispatch(new ExportSvgAction(i))}}},e.prototype.createSvg=function(e,t){var r=new XMLSerializer,i=r.serializeToString(e),o=document.createElement("iframe");if(document.body.appendChild(o),!o.contentWindow)throw new Error("IFrame has no contentWindow");var n=o.contentWindow.document;n.open(),n.write(i),n.close();var a=n.getElementById(e.id);a.removeAttribute("opacity"),this.copyStyles(e,a,["width","height","opacity"]),a.setAttribute("version","1.1");var c=this.getBounds(t);a.setAttribute("viewBox",c.x+" "+c.y+" "+c.width+" "+c.height);var s=r.serializeToString(a);return document.body.removeChild(o),s},e.prototype.copyStyles=function(e,t,r){for(var i=getComputedStyle(e),o=getComputedStyle(t),n="",a=0;a<i.length;a++){var c=i[a];if(-1===r.indexOf(c)){var s=i.getPropertyValue(c);o.getPropertyValue(c)!==s&&(n+=c+":"+s+";")}}""!==n&&t.setAttribute("style",n);for(var a=0;a<e.childNodes.length;++a){var p=e.childNodes[a],d=t.childNodes[a];p instanceof Element&&this.copyStyles(p,d,[])}},e.prototype.getBounds=function(e){var t=[geometry_1.EMPTY_BOUNDS];return e.children.forEach(function(e){model_1.isBoundsAware(e)&&t.push(e.bounds)}),t.reduce(function(e,t){return geometry_1.combine(e,t)})},e=__decorate([inversify_1.injectable(),__param(0,inversify_1.inject(types_1.TYPES.ViewerOptions)),__param(1,inversify_1.inject(types_1.TYPES.IActionDispatcher)),__param(2,inversify_1.inject(types_1.TYPES.ILogger)),__metadata("design:paramtypes",[Object,action_dispatcher_1.ActionDispatcher,Object])],e)}();exports.SvgExporter=SvgExporter;

/***/ }),
/* 25 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
var __extends=this&&this.__extends||function(){var t=Object.setPrototypeOf||{__proto__:[]}instanceof Array&&function(t,e){t.__proto__=e}||function(t,e){for(var o in e)e.hasOwnProperty(o)&&(t[o]=e[o])};return function(e,o){function i(){this.constructor=e}t(e,o),e.prototype=null===o?Object.create(o):(i.prototype=o.prototype,new i)}}();Object.defineProperty(exports,"__esModule",{value:!0});var command_1=__webpack_require__(5),animation_1=__webpack_require__(19),model_1=__webpack_require__(16),ViewportAction=function(){function t(t,e,o){this.elementId=t,this.newViewport=e,this.animate=o,this.kind=ViewportCommand.KIND}return t}();exports.ViewportAction=ViewportAction;var ViewportCommand=function(t){function e(e){var o=t.call(this)||this;return o.action=e,o.newViewport=e.newViewport,o}return __extends(e,t),e.prototype.execute=function(t){var e=t.root,o=e.index.getById(this.action.elementId);if(o&&model_1.isViewport(o)){if(this.element=o,this.oldViewport={scroll:this.element.scroll,zoom:this.element.zoom},this.action.animate)return new ViewportAnimation(this.element,this.oldViewport,this.newViewport,t).start();this.element.scroll=this.newViewport.scroll,this.element.zoom=this.newViewport.zoom}return e},e.prototype.undo=function(t){return new ViewportAnimation(this.element,this.newViewport,this.oldViewport,t).start()},e.prototype.redo=function(t){return new ViewportAnimation(this.element,this.oldViewport,this.newViewport,t).start()},e.prototype.merge=function(t,o){return!this.action.animate&&t instanceof e&&this.element===t.element&&(this.newViewport=t.newViewport,!0)},e.KIND="viewport",e}(command_1.MergeableCommand);exports.ViewportCommand=ViewportCommand;var ViewportAnimation=function(t){function e(e,o,i,n){var r=t.call(this,n)||this;return r.element=e,r.oldViewport=o,r.newViewport=i,r.context=n,r.zoomFactor=Math.log(i.zoom/o.zoom),r}return __extends(e,t),e.prototype.tween=function(t,e){return this.element.scroll={x:(1-t)*this.oldViewport.scroll.x+t*this.newViewport.scroll.x,y:(1-t)*this.oldViewport.scroll.y+t*this.newViewport.scroll.y},this.element.zoom=this.oldViewport.zoom*Math.exp(t*this.zoomFactor),e.root},e}(animation_1.Animation);exports.ViewportAnimation=ViewportAnimation;

/***/ }),
/* 26 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
var __decorate=this&&this.__decorate||function(e,t,r,n){var i,s=arguments.length,o=s<3?t:null===n?n=Object.getOwnPropertyDescriptor(t,r):n;if("object"==typeof Reflect&&"function"==typeof Reflect.decorate)o=Reflect.decorate(e,t,r,n);else for(var y=e.length-1;y>=0;y--)(i=e[y])&&(o=(s<3?i(o):s>3?i(t,r,o):i(t,r))||o);return s>3&&o&&Object.defineProperty(t,r,o),o};Object.defineProperty(exports,"__esModule",{value:!0});var inversify_1=__webpack_require__(0),ProviderRegistry=function(){function e(){this.elements=new Map}return e.prototype.register=function(e,t){if(void 0===e)throw new Error("Key is undefined");if(this.hasKey(e))throw new Error("Key is already registered: "+e);this.elements.set(e,t)},e.prototype.deregister=function(e){if(void 0===e)throw new Error("Key is undefined");this.elements.delete(e)},e.prototype.hasKey=function(e){return this.elements.has(e)},e.prototype.get=function(e,t){var r=this.elements.get(e);return r?new r(t):this.missing(e,t)},e.prototype.missing=function(e,t){throw new Error("Unknown registry key: "+e)},e=__decorate([inversify_1.injectable()],e)}();exports.ProviderRegistry=ProviderRegistry;var InstanceRegistry=function(){function e(){this.elements=new Map}return e.prototype.register=function(e,t){if(void 0===e)throw new Error("Key is undefined");if(this.hasKey(e))throw new Error("Key is already registered: "+e);this.elements.set(e,t)},e.prototype.deregister=function(e){if(void 0===e)throw new Error("Key is undefined");this.elements.delete(e)},e.prototype.hasKey=function(e){return this.elements.has(e)},e.prototype.get=function(e){var t=this.elements.get(e);return t||this.missing(e)},e.prototype.missing=function(e){throw new Error("Unknown registry key: "+e)},e=__decorate([inversify_1.injectable()],e)}();exports.InstanceRegistry=InstanceRegistry;var MultiInstanceRegistry=function(){function e(){this.elements=new Map}return e.prototype.register=function(e,t){if(void 0===e)throw new Error("Key is undefined");var r=this.elements.get(e);void 0!==r?r.push(t):this.elements.set(e,[t])},e.prototype.deregisterAll=function(e){if(void 0===e)throw new Error("Key is undefined");this.elements.delete(e)},e.prototype.get=function(e){var t=this.elements.get(e);return void 0!==t?t:[]},e=__decorate([inversify_1.injectable()],e)}();exports.MultiInstanceRegistry=MultiInstanceRegistry;

/***/ }),
/* 27 */
/***/ (function(module, exports) {

var g;g=function(){return this}();try{g=g||Function("return this")()||(0,eval)("this")}catch(t){"object"==typeof window&&(g=window)}module.exports=g;

/***/ }),
/* 28 */
/***/ (function(module, exports) {

function defaultSetTimout(){throw new Error("setTimeout has not been defined")}function defaultClearTimeout(){throw new Error("clearTimeout has not been defined")}function runTimeout(e){if(cachedSetTimeout===setTimeout)return setTimeout(e,0);if((cachedSetTimeout===defaultSetTimout||!cachedSetTimeout)&&setTimeout)return cachedSetTimeout=setTimeout,setTimeout(e,0);try{return cachedSetTimeout(e,0)}catch(t){try{return cachedSetTimeout.call(null,e,0)}catch(t){return cachedSetTimeout.call(this,e,0)}}}function runClearTimeout(e){if(cachedClearTimeout===clearTimeout)return clearTimeout(e);if((cachedClearTimeout===defaultClearTimeout||!cachedClearTimeout)&&clearTimeout)return cachedClearTimeout=clearTimeout,clearTimeout(e);try{return cachedClearTimeout(e)}catch(t){try{return cachedClearTimeout.call(null,e)}catch(t){return cachedClearTimeout.call(this,e)}}}function cleanUpNextTick(){draining&&currentQueue&&(draining=!1,currentQueue.length?queue=currentQueue.concat(queue):queueIndex=-1,queue.length&&drainQueue())}function drainQueue(){if(!draining){var e=runTimeout(cleanUpNextTick);draining=!0;for(var t=queue.length;t;){for(currentQueue=queue,queue=[];++queueIndex<t;)currentQueue&&currentQueue[queueIndex].run();queueIndex=-1,t=queue.length}currentQueue=null,draining=!1,runClearTimeout(e)}}function Item(e,t){this.fun=e,this.array=t}function noop(){}var process=module.exports={},cachedSetTimeout,cachedClearTimeout;!function(){try{cachedSetTimeout="function"==typeof setTimeout?setTimeout:defaultSetTimout}catch(e){cachedSetTimeout=defaultSetTimout}try{cachedClearTimeout="function"==typeof clearTimeout?clearTimeout:defaultClearTimeout}catch(e){cachedClearTimeout=defaultClearTimeout}}();var queue=[],draining=!1,currentQueue,queueIndex=-1;process.nextTick=function(e){var t=new Array(arguments.length-1);if(arguments.length>1)for(var r=1;r<arguments.length;r++)t[r-1]=arguments[r];queue.push(new Item(e,t)),1!==queue.length||draining||runTimeout(drainQueue)},Item.prototype.run=function(){this.fun.apply(null,this.array)},process.title="browser",process.browser=!0,process.env={},process.argv=[],process.version="",process.versions={},process.on=noop,process.addListener=noop,process.once=noop,process.off=noop,process.removeListener=noop,process.removeAllListeners=noop,process.emit=noop,process.prependListener=noop,process.prependOnceListener=noop,process.listeners=function(e){return[]},process.binding=function(e){throw new Error("process.binding is not supported")},process.cwd=function(){return"/"},process.chdir=function(e){throw new Error("process.chdir is not supported")},process.umask=function(){return 0};

/***/ }),
/* 29 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
function getServiceIdentifierAsString(e){if("function"==typeof e){var t=e;return t.name}if("symbol"==typeof e)return e.toString();var t=e;return t}function listRegisteredBindingsForServiceIdentifier(e,t,n){var r="",i=n(e,t);return 0!==i.length&&(r="\nRegistered bindings:",i.forEach(function(e){var t="Object";null!==e.implementationType&&(t=getFunctionName(e.implementationType)),r=r+"\n "+t,e.constraint.metaData&&(r=r+" - "+e.constraint.metaData)})),r}function circularDependencyToException(e,t){void 0===t&&(t=[]);var n=getServiceIdentifierAsString(e.serviceIdentifier);t.push(n),e.childRequests.forEach(function(e){var n=getServiceIdentifierAsString(e.serviceIdentifier);if(-1!==t.indexOf(n)){t.push(n);var r=t.reduce(function(e,t){return""!==e?e+" -> "+t:""+t},"");throw new Error(ERROR_MSGS.CIRCULAR_DEPENDENCY+" "+r)}e.childRequests.length>0?circularDependencyToException(e,t):t.push(n)})}function listMetadataForTarget(e,t){if(t.isTagged()||t.isNamed()){var n="",r=t.getNamedTag(),i=t.getCustomTags();return null!==r&&(n+=r.toString()+"\n"),null!==i&&i.forEach(function(e){n+=e.toString()+"\n"})," "+e+"\n "+e+" - "+n}return" "+e}function getFunctionName(e){if(e.name)return e.name;var t=e.toString(),n=t.match(/^function\s*([^\s(]+)/);return n?n[1]:"Anonymous function: "+t}Object.defineProperty(exports,"__esModule",{value:!0});var ERROR_MSGS=__webpack_require__(8);exports.getServiceIdentifierAsString=getServiceIdentifierAsString,exports.listRegisteredBindingsForServiceIdentifier=listRegisteredBindingsForServiceIdentifier,exports.circularDependencyToException=circularDependencyToException,exports.listMetadataForTarget=listMetadataForTarget,exports.getFunctionName=getFunctionName;

/***/ }),
/* 30 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
var __decorate=this&&this.__decorate||function(t,e,r,i){var n,o=arguments.length,s=o<3?e:null===i?i=Object.getOwnPropertyDescriptor(e,r):i;if("object"==typeof Reflect&&"function"==typeof Reflect.decorate)s=Reflect.decorate(t,e,r,i);else for(var a=t.length-1;a>=0;a--)(n=t[a])&&(s=(o<3?n(s):o>3?n(e,r,s):n(e,r))||s);return o>3&&s&&Object.defineProperty(e,r,s),s};Object.defineProperty(exports,"__esModule",{value:!0});var inversify_1=__webpack_require__(0),AnimationFrameSyncer=function(){function t(){this.tasks=[],this.endTasks=[],this.triggered=!1}return t.prototype.isAvailable=function(){return"function"==typeof requestAnimationFrame},t.prototype.onNextFrame=function(t){this.tasks.push(t),this.trigger()},t.prototype.onEndOfNextFrame=function(t){this.endTasks.push(t),this.trigger()},t.prototype.trigger=function(){var t=this;this.triggered||(this.triggered=!0,this.isAvailable()?requestAnimationFrame(function(e){return t.run(e)}):setTimeout(function(e){return t.run(e)}))},t.prototype.run=function(t){var e=this.tasks,r=this.endTasks;this.triggered=!1,this.tasks=[],this.endTasks=[],e.forEach(function(e){return e.call(void 0,t)}),r.forEach(function(e){return e.call(void 0,t)})},t=__decorate([inversify_1.injectable()],t)}();exports.AnimationFrameSyncer=AnimationFrameSyncer;

/***/ }),
/* 31 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
var __extends=this&&this.__extends||function(){var t=Object.setPrototypeOf||{__proto__:[]}instanceof Array&&function(t,e){t.__proto__=e}||function(t,e){for(var o in e)e.hasOwnProperty(o)&&(t[o]=e[o])};return function(e,o){function n(){this.constructor=e}t(e,o),e.prototype=null===o?Object.create(o):(n.prototype=o.prototype,new n)}}(),__decorate=this&&this.__decorate||function(t,e,o,n){var r,i=arguments.length,c=i<3?e:null===n?n=Object.getOwnPropertyDescriptor(e,o):n;if("object"==typeof Reflect&&"function"==typeof Reflect.decorate)c=Reflect.decorate(t,e,o,n);else for(var a=t.length-1;a>=0;a--)(r=t[a])&&(c=(i<3?r(c):i>3?r(e,o,c):r(e,o))||c);return i>3&&c&&Object.defineProperty(e,o,c),c},__metadata=this&&this.__metadata||function(t,e){if("object"==typeof Reflect&&"function"==typeof Reflect.metadata)return Reflect.metadata(t,e)};Object.defineProperty(exports,"__esModule",{value:!0});var inversify_1=__webpack_require__(0),command_1=__webpack_require__(5),initialize_canvas_1=__webpack_require__(41),RequestModelAction=function(){function t(e){this.options=e,this.kind=t.KIND}return t.KIND="requestModel",t}();exports.RequestModelAction=RequestModelAction;var SetModelAction=function(){function t(t){this.newRoot=t,this.kind=SetModelCommand.KIND}return t}();exports.SetModelAction=SetModelAction;var SetModelCommand=function(t){function e(e){var o=t.call(this)||this;return o.action=e,o}return __extends(e,t),e.prototype.execute=function(t){return this.oldRoot=t.modelFactory.createRoot(t.root),this.newRoot=t.modelFactory.createRoot(this.action.newRoot),this.newRoot},e.prototype.undo=function(t){return this.oldRoot},e.prototype.redo=function(t){return this.newRoot},Object.defineProperty(e.prototype,"blockUntil",{get:function(){return function(t){return t.kind===initialize_canvas_1.InitializeCanvasBoundsCommand.KIND}},enumerable:!0,configurable:!0}),e.KIND="setModel",e=__decorate([inversify_1.injectable(),__metadata("design:paramtypes",[SetModelAction])],e)}(command_1.Command);exports.SetModelCommand=SetModelCommand;

/***/ }),
/* 32 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
var __extends=this&&this.__extends||function(){var n=Object.setPrototypeOf||{__proto__:[]}instanceof Array&&function(n,o){n.__proto__=o}||function(n,o){for(var t in o)o.hasOwnProperty(t)&&(n[t]=o[t])};return function(o,t){function e(){this.constructor=o}n(o,t),o.prototype=null===t?Object.create(t):(e.prototype=t.prototype,new e)}}();Object.defineProperty(exports,"__esModule",{value:!0});var command_1=__webpack_require__(5),model_1=__webpack_require__(7),SetBoundsAction=function(){function n(n){this.bounds=n,this.kind=SetBoundsCommand.KIND}return n}();exports.SetBoundsAction=SetBoundsAction;var RequestBoundsAction=function(){function n(n){this.newRoot=n,this.kind=RequestBoundsCommand.KIND}return n}();exports.RequestBoundsAction=RequestBoundsAction;var ComputedBoundsAction=function(){function n(o,t,e){this.bounds=o,this.revision=t,this.alignments=e,this.kind=n.KIND}return n.KIND="computedBounds",n}();exports.ComputedBoundsAction=ComputedBoundsAction;var SetBoundsCommand=function(n){function o(o){var t=n.call(this)||this;return t.action=o,t.bounds=[],t}return __extends(o,n),o.prototype.execute=function(n){var o=this;return this.action.bounds.forEach(function(t){var e=n.root.index.getById(t.elementId);e&&model_1.isBoundsAware(e)&&o.bounds.push({element:e,oldBounds:e.bounds,newBounds:t.newBounds})}),this.redo(n)},o.prototype.undo=function(n){return this.bounds.forEach(function(n){return n.element.bounds=n.oldBounds}),n.root},o.prototype.redo=function(n){return this.bounds.forEach(function(n){return n.element.bounds=n.newBounds}),n.root},o.KIND="setBounds",o}(command_1.SystemCommand);exports.SetBoundsCommand=SetBoundsCommand;var RequestBoundsCommand=function(n){function o(o){var t=n.call(this)||this;return t.action=o,t}return __extends(o,n),o.prototype.execute=function(n){return n.modelFactory.createRoot(this.action.newRoot)},Object.defineProperty(o.prototype,"blockUntil",{get:function(){return function(n){return n.kind===ComputedBoundsAction.KIND}},enumerable:!0,configurable:!0}),o.KIND="requestBounds",o}(command_1.HiddenCommand);exports.RequestBoundsCommand=RequestBoundsCommand;

/***/ }),
/* 33 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
function isFadeable(e){return e.hasFeature(exports.fadeFeature)&&void 0!==e.opacity}Object.defineProperty(exports,"__esModule",{value:!0}),exports.fadeFeature=Symbol("fadeFeature"),exports.isFadeable=isFadeable;

/***/ }),
/* 34 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
var __extends=this&&this.__extends||function(){var e=Object.setPrototypeOf||{__proto__:[]}instanceof Array&&function(e,t){e.__proto__=t}||function(e,t){for(var o in t)t.hasOwnProperty(o)&&(e[o]=t[o])};return function(t,o){function r(){this.constructor=t}e(t,o),t.prototype=null===o?Object.create(o):(r.prototype=o.prototype,new r)}}(),__decorate=this&&this.__decorate||function(e,t,o,r){var s,i=arguments.length,n=i<3?t:null===r?r=Object.getOwnPropertyDescriptor(t,o):r;if("object"==typeof Reflect&&"function"==typeof Reflect.decorate)n=Reflect.decorate(e,t,o,r);else for(var u=e.length-1;u>=0;u--)(s=e[u])&&(n=(i<3?s(n):i>3?s(t,o,n):s(t,o))||n);return i>3&&n&&Object.defineProperty(t,o,n),n},__metadata=this&&this.__metadata||function(e,t){if("object"==typeof Reflect&&"function"==typeof Reflect.metadata)return Reflect.metadata(e,t)},__param=this&&this.__param||function(e,t){return function(o,r){t(o,r,e)}};Object.defineProperty(exports,"__esModule",{value:!0});var inversify_1=__webpack_require__(0),keyboard_1=__webpack_require__(36),geometry_1=__webpack_require__(3),types_1=__webpack_require__(1),smodel_1=__webpack_require__(2),mouse_tool_1=__webpack_require__(15),command_1=__webpack_require__(5),smodel_factory_1=__webpack_require__(10),key_tool_1=__webpack_require__(20),smodel_utils_1=__webpack_require__(6),model_1=__webpack_require__(7),model_2=__webpack_require__(35),HoverFeedbackAction=function(){function e(e,t){this.mouseoverElement=e,this.mouseIsOver=t,this.kind=HoverFeedbackCommand.KIND}return e}();exports.HoverFeedbackAction=HoverFeedbackAction;var HoverFeedbackCommand=function(e){function t(t){var o=e.call(this)||this;return o.action=t,o}return __extends(t,e),t.prototype.execute=function(e){var t=e.root,o=t.index.getById(this.action.mouseoverElement);return o&&model_2.isHoverable(o)&&(o.hoverFeedback=this.action.mouseIsOver),this.redo(e)},t.prototype.undo=function(e){return e.root},t.prototype.redo=function(e){return e.root},t.KIND="hoverFeedback",t}(command_1.Command);exports.HoverFeedbackCommand=HoverFeedbackCommand;var RequestPopupModelAction=function(){function e(t,o){this.elementId=t,this.bounds=o,this.kind=e.KIND}return e.KIND="requestPopupModel",e}();exports.RequestPopupModelAction=RequestPopupModelAction;var SetPopupModelAction=function(){function e(e){this.newRoot=e,this.kind=SetPopupModelCommand.KIND}return e}();exports.SetPopupModelAction=SetPopupModelAction;var SetPopupModelCommand=function(e){function t(t){var o=e.call(this)||this;return o.action=t,o}return __extends(t,e),t.prototype.execute=function(e){return this.oldRoot=e.root,this.newRoot=e.modelFactory.createRoot(this.action.newRoot),this.newRoot},t.prototype.undo=function(e){return this.oldRoot},t.prototype.redo=function(e){return this.newRoot},t.KIND="setPopupModel",t}(command_1.PopupCommand);exports.SetPopupModelCommand=SetPopupModelCommand;var AbstractHoverMouseListener=function(e){function t(t,o){var r=e.call(this)||this;return r.options=t,r.state=o,r}return __extends(t,e),t.prototype.stopMouseOutTimer=function(){void 0!==this.state.mouseOutTimer&&(window.clearTimeout(this.state.mouseOutTimer),this.state.mouseOutTimer=void 0)},t.prototype.startMouseOutTimer=function(){var e=this;return this.stopMouseOutTimer(),new Promise(function(t){e.state.mouseOutTimer=window.setTimeout(function(){e.state.popupOpen=!1,e.state.previousPopupElement=void 0,t(new SetPopupModelAction({type:smodel_factory_1.EMPTY_ROOT.type,id:smodel_factory_1.EMPTY_ROOT.id}))},e.options.popupCloseDelay)})},t.prototype.stopMouseOverTimer=function(){void 0!==this.state.mouseOverTimer&&(window.clearTimeout(this.state.mouseOverTimer),this.state.mouseOverTimer=void 0)},t=__decorate([__param(0,inversify_1.inject(types_1.TYPES.ViewerOptions)),__param(1,inversify_1.inject(types_1.TYPES.HoverState)),__metadata("design:paramtypes",[Object,Object])],t)}(mouse_tool_1.MouseListener);exports.AbstractHoverMouseListener=AbstractHoverMouseListener;var HoverMouseListener=function(e){function t(){return null!==e&&e.apply(this,arguments)||this}return __extends(t,e),t.prototype.computePopupBounds=function(e,t){var o={x:-5,y:20},r=model_1.getAbsoluteBounds(e),s=e.root.canvasBounds,i=geometry_1.translate(r,s),n=i.x+i.width-t.x,u=i.y+i.height-t.y;u<=n&&this.allowSidePosition(e,"below",u)?o={x:-5,y:Math.round(u+5)}:n<=u&&this.allowSidePosition(e,"right",n)&&(o={x:Math.round(n+5),y:-5});var p=t.x+o.x,a=s.x+s.width;p>a&&(p=a);var d=t.y+o.y,c=s.y+s.height;return d>c&&(d=c),{x:p,y:d,width:-1,height:-1}},t.prototype.allowSidePosition=function(e,t,o){return!(e instanceof smodel_1.SModelRoot)&&o<=150},t.prototype.startMouseOverTimer=function(e,t){var o=this;return this.stopMouseOverTimer(),new Promise(function(r){o.state.mouseOverTimer=window.setTimeout(function(){var s=o.computePopupBounds(e,{x:t.pageX,y:t.pageY});r(new RequestPopupModelAction(e.id,s)),o.state.popupOpen=!0,o.state.previousPopupElement=e},o.options.popupOpenDelay)})},t.prototype.mouseOver=function(e,t){var o=[],r=smodel_utils_1.findParent(e,model_2.hasPopupFeature);this.state.popupOpen&&(void 0===r||void 0!==this.state.previousPopupElement&&this.state.previousPopupElement.id!==r.id)?o.push(this.startMouseOutTimer()):(this.stopMouseOverTimer(),this.stopMouseOutTimer()),void 0===r||void 0!==this.state.previousPopupElement&&this.state.previousPopupElement.id===r.id||o.push(this.startMouseOverTimer(r,t));var s=smodel_utils_1.findParentByFeature(e,model_2.isHoverable);return void 0!==s&&o.push(new HoverFeedbackAction(s.id,!0)),o},t.prototype.mouseOut=function(e,t){var o=[];if(this.state.popupOpen){var r=smodel_utils_1.findParent(e,model_2.hasPopupFeature);void 0!==this.state.previousPopupElement&&void 0!==r&&this.state.previousPopupElement.id===r.id&&o.push(this.startMouseOutTimer())}this.stopMouseOverTimer();var s=smodel_utils_1.findParentByFeature(e,model_2.isHoverable);return void 0!==s&&o.push(new HoverFeedbackAction(s.id,!1)),o},t.prototype.mouseMove=function(e,t){var o=[];void 0!==this.state.previousPopupElement&&this.closeOnMouseMove(this.state.previousPopupElement,t)&&o.push(this.startMouseOutTimer());var r=smodel_utils_1.findParent(e,model_2.hasPopupFeature);return void 0===r||void 0!==this.state.previousPopupElement&&this.state.previousPopupElement.id===r.id||o.push(this.startMouseOverTimer(r,t)),o},t.prototype.closeOnMouseMove=function(e,t){return e instanceof smodel_1.SModelRoot},t=__decorate([inversify_1.injectable()],t)}(AbstractHoverMouseListener);exports.HoverMouseListener=HoverMouseListener;var PopupHoverMouseListener=function(e){function t(){return null!==e&&e.apply(this,arguments)||this}return __extends(t,e),t.prototype.mouseOut=function(e,t){return[this.startMouseOutTimer()]},t.prototype.mouseOver=function(e,t){return this.stopMouseOutTimer(),this.stopMouseOverTimer(),[]},t=__decorate([inversify_1.injectable()],t)}(AbstractHoverMouseListener);exports.PopupHoverMouseListener=PopupHoverMouseListener;var HoverKeyListener=function(e){function t(){return null!==e&&e.apply(this,arguments)||this}return __extends(t,e),t.prototype.keyDown=function(e,t){return keyboard_1.matchesKeystroke(t,"Escape")?[new SetPopupModelAction({type:smodel_factory_1.EMPTY_ROOT.type,id:smodel_factory_1.EMPTY_ROOT.id})]:[]},t}(key_tool_1.KeyListener);exports.HoverKeyListener=HoverKeyListener;

/***/ }),
/* 35 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
function isHoverable(e){return e.hasFeature(exports.hoverFeedbackFeature)}function hasPopupFeature(e){return e.hasFeature(exports.popupFeature)}Object.defineProperty(exports,"__esModule",{value:!0}),exports.hoverFeedbackFeature=Symbol("hoverFeedbackFeature"),exports.isHoverable=isHoverable,exports.popupFeature=Symbol("popupFeature"),exports.hasPopupFeature=hasPopupFeature;

/***/ }),
/* 36 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
function matchesKeystroke(e,t){for(var r=[],i=2;i<arguments.length;i++)r[i-2]=arguments[i];if(getActualCode(e)!==t)return!1;if(browser_1.isMac()){if(e.ctrlKey!==r.findIndex(function(e){return"ctrl"===e})>=0)return!1;if(e.metaKey!==r.findIndex(function(e){return"meta"===e||"ctrlCmd"===e})>=0)return!1}else{if(e.ctrlKey!==r.findIndex(function(e){return"ctrl"===e||"ctrlCmd"===e})>=0)return!1;if(e.metaKey!==r.findIndex(function(e){return"meta"===e})>=0)return!1}return e.altKey===r.findIndex(function(e){return"alt"===e})>=0&&e.shiftKey===r.findIndex(function(e){return"shift"===e})>=0}function getActualCode(e){if(e.keyCode){var t=STRING_CODE[e.keyCode];if(void 0!==t)return t}return e.code}Object.defineProperty(exports,"__esModule",{value:!0});var browser_1=__webpack_require__(69);exports.matchesKeystroke=matchesKeystroke,exports.getActualCode=getActualCode;var STRING_CODE=new Array(256);!function(){function e(e,t){void 0===STRING_CODE[t]&&(STRING_CODE[t]=e)}e("Pause",3),e("Backspace",8),e("Tab",9),e("Enter",13),e("ShiftLeft",16),e("ShiftRight",16),e("ControlLeft",17),e("ControlRight",17),e("AltLeft",18),e("AltRight",18),e("CapsLock",20),e("Escape",27),e("Space",32),e("PageUp",33),e("PageDown",34),e("End",35),e("Home",36),e("ArrowLeft",37),e("ArrowUp",38),e("ArrowRight",39),e("ArrowDown",40),e("Insert",45),e("Delete",46),e("Digit1",49),e("Digit2",50),e("Digit3",51),e("Digit4",52),e("Digit5",53),e("Digit6",54),e("Digit7",55),e("Digit8",56),e("Digit9",57),e("Digit0",48),e("KeyA",65),e("KeyB",66),e("KeyC",67),e("KeyD",68),e("KeyE",69),e("KeyF",70),e("KeyG",71),e("KeyH",72),e("KeyI",73),e("KeyJ",74),e("KeyK",75),e("KeyL",76),e("KeyM",77),e("KeyN",78),e("KeyO",79),e("KeyP",80),e("KeyQ",81),e("KeyR",82),e("KeyS",83),e("KeyT",84),e("KeyU",85),e("KeyV",86),e("KeyW",87),e("KeyX",88),e("KeyY",89),e("KeyZ",90),e("OSLeft",91),e("MetaLeft",91),e("OSRight",92),e("MetaRight",92),e("ContextMenu",93),e("Numpad0",96),e("Numpad1",97),e("Numpad2",98),e("Numpad3",99),e("Numpad4",100),e("Numpad5",101),e("Numpad6",102),e("Numpad7",103),e("Numpad8",104),e("Numpad9",105),e("NumpadMultiply",106),e("NumpadAdd",107),e("NumpadSeparator",108),e("NumpadSubtract",109),e("NumpadDecimal",110),e("NumpadDivide",111),e("F1",112),e("F2",113),e("F3",114),e("F4",115),e("F5",116),e("F6",117),e("F7",118),e("F8",119),e("F9",120),e("F10",121),e("F11",122),e("F12",123),e("F13",124),e("F14",125),e("F15",126),e("F16",127),e("F17",128),e("F18",129),e("F19",130),e("F20",131),e("F21",132),e("F22",133),e("F23",134),e("F24",135),e("NumLock",144),e("ScrollLock",145),e("Semicolon",186),e("Equal",187),e("Comma",188),e("Minus",189),e("Period",190),e("Slash",191),e("Backquote",192),e("IntlRo",193),e("BracketLeft",219),e("Backslash",220),e("BracketRight",221),e("Quote",222),e("IntlYen",255)}();

/***/ }),
/* 37 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
function __export(e){for(var r in e)exports.hasOwnProperty(r)||(exports[r]=e[r])}Object.defineProperty(exports,"__esModule",{value:!0}),__export(__webpack_require__(53)),__export(__webpack_require__(52)),__export(__webpack_require__(23)),__export(__webpack_require__(30)),__export(__webpack_require__(19)),__export(__webpack_require__(80)),__export(__webpack_require__(153)),__export(__webpack_require__(81)),__export(__webpack_require__(5)),__export(__webpack_require__(41)),__export(__webpack_require__(31)),__export(__webpack_require__(10)),__export(__webpack_require__(54)),__export(__webpack_require__(6)),__export(__webpack_require__(2)),__export(__webpack_require__(20)),__export(__webpack_require__(15)),__export(__webpack_require__(82)),__export(__webpack_require__(83)),__export(__webpack_require__(84)),__export(__webpack_require__(85)),__export(__webpack_require__(86)),__export(__webpack_require__(87)),__export(__webpack_require__(11)),__export(__webpack_require__(1));var di_config_1=__webpack_require__(154);exports.defaultModule=di_config_1.default,__export(__webpack_require__(32)),__export(__webpack_require__(57)),__export(__webpack_require__(7)),__export(__webpack_require__(90)),__export(__webpack_require__(88)),__export(__webpack_require__(89)),__export(__webpack_require__(58)),__export(__webpack_require__(59)),__export(__webpack_require__(42)),__export(__webpack_require__(21)),__export(__webpack_require__(43)),__export(__webpack_require__(60)),__export(__webpack_require__(162)),__export(__webpack_require__(91)),__export(__webpack_require__(44)),__export(__webpack_require__(24)),__export(__webpack_require__(61)),__export(__webpack_require__(33)),__export(__webpack_require__(34)),__export(__webpack_require__(35)),__export(__webpack_require__(22)),__export(__webpack_require__(45)),__export(__webpack_require__(62)),__export(__webpack_require__(92)),__export(__webpack_require__(12)),__export(__webpack_require__(93)),__export(__webpack_require__(63)),__export(__webpack_require__(64)),__export(__webpack_require__(65)),__export(__webpack_require__(66)),__export(__webpack_require__(16)),__export(__webpack_require__(94)),__export(__webpack_require__(67)),__export(__webpack_require__(25)),__export(__webpack_require__(95));var di_config_2=__webpack_require__(168);exports.moveModule=di_config_2.default;var di_config_3=__webpack_require__(156);exports.boundsModule=di_config_3.default;var di_config_4=__webpack_require__(164);exports.fadeModule=di_config_4.default;var di_config_5=__webpack_require__(170);exports.selectModule=di_config_5.default;var di_config_6=__webpack_require__(171);exports.undoRedoModule=di_config_6.default;var di_config_7=__webpack_require__(172);exports.viewportModule=di_config_7.default;var di_config_8=__webpack_require__(165);exports.hoverModule=di_config_8.default;var di_config_9=__webpack_require__(160);exports.edgeEditModule=di_config_9.default;var di_config_10=__webpack_require__(163);exports.exportModule=di_config_10.default;var di_config_11=__webpack_require__(161);exports.expandModule=di_config_11.default;var di_config_12=__webpack_require__(169);exports.openModule=di_config_12.default;var di_config_13=__webpack_require__(159);exports.buttonModule=di_config_13.default,__export(__webpack_require__(174)),__export(__webpack_require__(46)),__export(__webpack_require__(175)),__export(__webpack_require__(176)),__export(__webpack_require__(177)),__export(__webpack_require__(178)),__export(__webpack_require__(179)),__export(__webpack_require__(96)),__export(__webpack_require__(97)),__export(__webpack_require__(181)),__export(__webpack_require__(182)),__export(__webpack_require__(68)),__export(__webpack_require__(183));var di_config_14=__webpack_require__(180);exports.modelSourceModule=di_config_14.default,__export(__webpack_require__(98)),__export(__webpack_require__(69)),__export(__webpack_require__(185)),__export(__webpack_require__(3)),__export(__webpack_require__(71)),__export(__webpack_require__(26));

/***/ }),
/* 38 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
/* WEBPACK VAR INJECTION */(function(global) {function typedArraySupport(){try{var t=new Uint8Array(1);return t.__proto__={__proto__:Uint8Array.prototype,foo:function(){return 42}},42===t.foo()&&"function"==typeof t.subarray&&0===t.subarray(1,1).byteLength}catch(t){return!1}}function kMaxLength(){return Buffer.TYPED_ARRAY_SUPPORT?2147483647:1073741823}function createBuffer(t,e){if(kMaxLength()<e)throw new RangeError("Invalid typed array length");return Buffer.TYPED_ARRAY_SUPPORT?(t=new Uint8Array(e),t.__proto__=Buffer.prototype):(null===t&&(t=new Buffer(e)),t.length=e),t}function Buffer(t,e,r){if(!(Buffer.TYPED_ARRAY_SUPPORT||this instanceof Buffer))return new Buffer(t,e,r);if("number"==typeof t){if("string"==typeof e)throw new Error("If encoding is specified then the first argument must be a string");return allocUnsafe(this,t)}return from(this,t,e,r)}function from(t,e,r,n){if("number"==typeof e)throw new TypeError('"value" argument must not be a number');return"undefined"!=typeof ArrayBuffer&&e instanceof ArrayBuffer?fromArrayBuffer(t,e,r,n):"string"==typeof e?fromString(t,e,r):fromObject(t,e)}function assertSize(t){if("number"!=typeof t)throw new TypeError('"size" argument must be a number');if(t<0)throw new RangeError('"size" argument must not be negative')}function alloc(t,e,r,n){return assertSize(e),e<=0?createBuffer(t,e):void 0!==r?"string"==typeof n?createBuffer(t,e).fill(r,n):createBuffer(t,e).fill(r):createBuffer(t,e)}function allocUnsafe(t,e){if(assertSize(e),t=createBuffer(t,e<0?0:0|checked(e)),!Buffer.TYPED_ARRAY_SUPPORT)for(var r=0;r<e;++r)t[r]=0;return t}function fromString(t,e,r){if("string"==typeof r&&""!==r||(r="utf8"),!Buffer.isEncoding(r))throw new TypeError('"encoding" must be a valid string encoding');var n=0|byteLength(e,r);t=createBuffer(t,n);var f=t.write(e,r);return f!==n&&(t=t.slice(0,f)),t}function fromArrayLike(t,e){var r=e.length<0?0:0|checked(e.length);t=createBuffer(t,r);for(var n=0;n<r;n+=1)t[n]=255&e[n];return t}function fromArrayBuffer(t,e,r,n){if(e.byteLength,r<0||e.byteLength<r)throw new RangeError("'offset' is out of bounds");if(e.byteLength<r+(n||0))throw new RangeError("'length' is out of bounds");return e=void 0===r&&void 0===n?new Uint8Array(e):void 0===n?new Uint8Array(e,r):new Uint8Array(e,r,n),Buffer.TYPED_ARRAY_SUPPORT?(t=e,t.__proto__=Buffer.prototype):t=fromArrayLike(t,e),t}function fromObject(t,e){if(Buffer.isBuffer(e)){var r=0|checked(e.length);return t=createBuffer(t,r),0===t.length?t:(e.copy(t,0,0,r),t)}if(e){if("undefined"!=typeof ArrayBuffer&&e.buffer instanceof ArrayBuffer||"length"in e)return"number"!=typeof e.length||isnan(e.length)?createBuffer(t,0):fromArrayLike(t,e);if("Buffer"===e.type&&isArray(e.data))return fromArrayLike(t,e.data)}throw new TypeError("First argument must be a string, Buffer, ArrayBuffer, Array, or array-like object.")}function checked(t){if(t>=kMaxLength())throw new RangeError("Attempt to allocate Buffer larger than maximum size: 0x"+kMaxLength().toString(16)+" bytes");return 0|t}function SlowBuffer(t){return+t!=t&&(t=0),Buffer.alloc(+t)}function byteLength(t,e){if(Buffer.isBuffer(t))return t.length;if("undefined"!=typeof ArrayBuffer&&"function"==typeof ArrayBuffer.isView&&(ArrayBuffer.isView(t)||t instanceof ArrayBuffer))return t.byteLength;"string"!=typeof t&&(t=""+t);var r=t.length;if(0===r)return 0;for(var n=!1;;)switch(e){case"ascii":case"latin1":case"binary":return r;case"utf8":case"utf-8":case void 0:return utf8ToBytes(t).length;case"ucs2":case"ucs-2":case"utf16le":case"utf-16le":return 2*r;case"hex":return r>>>1;case"base64":return base64ToBytes(t).length;default:if(n)return utf8ToBytes(t).length;e=(""+e).toLowerCase(),n=!0}}function slowToString(t,e,r){var n=!1;if((void 0===e||e<0)&&(e=0),e>this.length)return"";if((void 0===r||r>this.length)&&(r=this.length),r<=0)return"";if(r>>>=0,e>>>=0,r<=e)return"";for(t||(t="utf8");;)switch(t){case"hex":return hexSlice(this,e,r);case"utf8":case"utf-8":return utf8Slice(this,e,r);case"ascii":return asciiSlice(this,e,r);case"latin1":case"binary":return latin1Slice(this,e,r);case"base64":return base64Slice(this,e,r);case"ucs2":case"ucs-2":case"utf16le":case"utf-16le":return utf16leSlice(this,e,r);default:if(n)throw new TypeError("Unknown encoding: "+t);t=(t+"").toLowerCase(),n=!0}}function swap(t,e,r){var n=t[e];t[e]=t[r],t[r]=n}function bidirectionalIndexOf(t,e,r,n,f){if(0===t.length)return-1;if("string"==typeof r?(n=r,r=0):r>2147483647?r=2147483647:r<-2147483648&&(r=-2147483648),r=+r,isNaN(r)&&(r=f?0:t.length-1),r<0&&(r=t.length+r),r>=t.length){if(f)return-1;r=t.length-1}else if(r<0){if(!f)return-1;r=0}if("string"==typeof e&&(e=Buffer.from(e,n)),Buffer.isBuffer(e))return 0===e.length?-1:arrayIndexOf(t,e,r,n,f);if("number"==typeof e)return e&=255,Buffer.TYPED_ARRAY_SUPPORT&&"function"==typeof Uint8Array.prototype.indexOf?f?Uint8Array.prototype.indexOf.call(t,e,r):Uint8Array.prototype.lastIndexOf.call(t,e,r):arrayIndexOf(t,[e],r,n,f);throw new TypeError("val must be string, number or Buffer")}function arrayIndexOf(t,e,r,n,f){function i(t,e){return 1===o?t[e]:t.readUInt16BE(e*o)}var o=1,u=t.length,s=e.length;if(void 0!==n&&("ucs2"===(n=String(n).toLowerCase())||"ucs-2"===n||"utf16le"===n||"utf-16le"===n)){if(t.length<2||e.length<2)return-1;o=2,u/=2,s/=2,r/=2}var a;if(f){var h=-1;for(a=r;a<u;a++)if(i(t,a)===i(e,-1===h?0:a-h)){if(-1===h&&(h=a),a-h+1===s)return h*o}else-1!==h&&(a-=a-h),h=-1}else for(r+s>u&&(r=u-s),a=r;a>=0;a--){for(var c=!0,l=0;l<s;l++)if(i(t,a+l)!==i(e,l)){c=!1;break}if(c)return a}return-1}function hexWrite(t,e,r,n){r=Number(r)||0;var f=t.length-r;n?(n=Number(n))>f&&(n=f):n=f;var i=e.length;if(i%2!=0)throw new TypeError("Invalid hex string");n>i/2&&(n=i/2);for(var o=0;o<n;++o){var u=parseInt(e.substr(2*o,2),16);if(isNaN(u))return o;t[r+o]=u}return o}function utf8Write(t,e,r,n){return blitBuffer(utf8ToBytes(e,t.length-r),t,r,n)}function asciiWrite(t,e,r,n){return blitBuffer(asciiToBytes(e),t,r,n)}function latin1Write(t,e,r,n){return asciiWrite(t,e,r,n)}function base64Write(t,e,r,n){return blitBuffer(base64ToBytes(e),t,r,n)}function ucs2Write(t,e,r,n){return blitBuffer(utf16leToBytes(e,t.length-r),t,r,n)}function base64Slice(t,e,r){return 0===e&&r===t.length?base64.fromByteArray(t):base64.fromByteArray(t.slice(e,r))}function utf8Slice(t,e,r){r=Math.min(t.length,r);for(var n=[],f=e;f<r;){var i=t[f],o=null,u=i>239?4:i>223?3:i>191?2:1;if(f+u<=r){var s,a,h,c;switch(u){case 1:i<128&&(o=i);break;case 2:s=t[f+1],128==(192&s)&&(c=(31&i)<<6|63&s)>127&&(o=c);break;case 3:s=t[f+1],a=t[f+2],128==(192&s)&&128==(192&a)&&(c=(15&i)<<12|(63&s)<<6|63&a)>2047&&(c<55296||c>57343)&&(o=c);break;case 4:s=t[f+1],a=t[f+2],h=t[f+3],128==(192&s)&&128==(192&a)&&128==(192&h)&&(c=(15&i)<<18|(63&s)<<12|(63&a)<<6|63&h)>65535&&c<1114112&&(o=c)}}null===o?(o=65533,u=1):o>65535&&(o-=65536,n.push(o>>>10&1023|55296),o=56320|1023&o),n.push(o),f+=u}return decodeCodePointsArray(n)}function decodeCodePointsArray(t){var e=t.length;if(e<=MAX_ARGUMENTS_LENGTH)return String.fromCharCode.apply(String,t);for(var r="",n=0;n<e;)r+=String.fromCharCode.apply(String,t.slice(n,n+=MAX_ARGUMENTS_LENGTH));return r}function asciiSlice(t,e,r){var n="";r=Math.min(t.length,r);for(var f=e;f<r;++f)n+=String.fromCharCode(127&t[f]);return n}function latin1Slice(t,e,r){var n="";r=Math.min(t.length,r);for(var f=e;f<r;++f)n+=String.fromCharCode(t[f]);return n}function hexSlice(t,e,r){var n=t.length;(!e||e<0)&&(e=0),(!r||r<0||r>n)&&(r=n);for(var f="",i=e;i<r;++i)f+=toHex(t[i]);return f}function utf16leSlice(t,e,r){for(var n=t.slice(e,r),f="",i=0;i<n.length;i+=2)f+=String.fromCharCode(n[i]+256*n[i+1]);return f}function checkOffset(t,e,r){if(t%1!=0||t<0)throw new RangeError("offset is not uint");if(t+e>r)throw new RangeError("Trying to access beyond buffer length")}function checkInt(t,e,r,n,f,i){if(!Buffer.isBuffer(t))throw new TypeError('"buffer" argument must be a Buffer instance');if(e>f||e<i)throw new RangeError('"value" argument is out of bounds');if(r+n>t.length)throw new RangeError("Index out of range")}function objectWriteUInt16(t,e,r,n){e<0&&(e=65535+e+1);for(var f=0,i=Math.min(t.length-r,2);f<i;++f)t[r+f]=(e&255<<8*(n?f:1-f))>>>8*(n?f:1-f)}function objectWriteUInt32(t,e,r,n){e<0&&(e=4294967295+e+1);for(var f=0,i=Math.min(t.length-r,4);f<i;++f)t[r+f]=e>>>8*(n?f:3-f)&255}function checkIEEE754(t,e,r,n,f,i){if(r+n>t.length)throw new RangeError("Index out of range");if(r<0)throw new RangeError("Index out of range")}function writeFloat(t,e,r,n,f){return f||checkIEEE754(t,e,r,4,3.4028234663852886e38,-3.4028234663852886e38),ieee754.write(t,e,r,n,23,4),r+4}function writeDouble(t,e,r,n,f){return f||checkIEEE754(t,e,r,8,1.7976931348623157e308,-1.7976931348623157e308),ieee754.write(t,e,r,n,52,8),r+8}function base64clean(t){if(t=stringtrim(t).replace(INVALID_BASE64_RE,""),t.length<2)return"";for(;t.length%4!=0;)t+="=";return t}function stringtrim(t){return t.trim?t.trim():t.replace(/^\s+|\s+$/g,"")}function toHex(t){return t<16?"0"+t.toString(16):t.toString(16)}function utf8ToBytes(t,e){e=e||1/0;for(var r,n=t.length,f=null,i=[],o=0;o<n;++o){if((r=t.charCodeAt(o))>55295&&r<57344){if(!f){if(r>56319){(e-=3)>-1&&i.push(239,191,189);continue}if(o+1===n){(e-=3)>-1&&i.push(239,191,189);continue}f=r;continue}if(r<56320){(e-=3)>-1&&i.push(239,191,189),f=r;continue}r=65536+(f-55296<<10|r-56320)}else f&&(e-=3)>-1&&i.push(239,191,189);if(f=null,r<128){if((e-=1)<0)break;i.push(r)}else if(r<2048){if((e-=2)<0)break;i.push(r>>6|192,63&r|128)}else if(r<65536){if((e-=3)<0)break;i.push(r>>12|224,r>>6&63|128,63&r|128)}else{if(!(r<1114112))throw new Error("Invalid code point");if((e-=4)<0)break;i.push(r>>18|240,r>>12&63|128,r>>6&63|128,63&r|128)}}return i}function asciiToBytes(t){for(var e=[],r=0;r<t.length;++r)e.push(255&t.charCodeAt(r));return e}function utf16leToBytes(t,e){for(var r,n,f,i=[],o=0;o<t.length&&!((e-=2)<0);++o)r=t.charCodeAt(o),n=r>>8,f=r%256,i.push(f),i.push(n);return i}function base64ToBytes(t){return base64.toByteArray(base64clean(t))}function blitBuffer(t,e,r,n){for(var f=0;f<n&&!(f+r>=e.length||f>=t.length);++f)e[f+r]=t[f];return f}function isnan(t){return t!==t}var base64=__webpack_require__(199),ieee754=__webpack_require__(200),isArray=__webpack_require__(138);exports.Buffer=Buffer,exports.SlowBuffer=SlowBuffer,exports.INSPECT_MAX_BYTES=50,Buffer.TYPED_ARRAY_SUPPORT=void 0!==global.TYPED_ARRAY_SUPPORT?global.TYPED_ARRAY_SUPPORT:typedArraySupport(),exports.kMaxLength=kMaxLength(),Buffer.poolSize=8192,Buffer._augment=function(t){return t.__proto__=Buffer.prototype,t},Buffer.from=function(t,e,r){return from(null,t,e,r)},Buffer.TYPED_ARRAY_SUPPORT&&(Buffer.prototype.__proto__=Uint8Array.prototype,Buffer.__proto__=Uint8Array,"undefined"!=typeof Symbol&&Symbol.species&&Buffer[Symbol.species]===Buffer&&Object.defineProperty(Buffer,Symbol.species,{value:null,configurable:!0})),Buffer.alloc=function(t,e,r){return alloc(null,t,e,r)},Buffer.allocUnsafe=function(t){return allocUnsafe(null,t)},Buffer.allocUnsafeSlow=function(t){return allocUnsafe(null,t)},Buffer.isBuffer=function(t){return!(null==t||!t._isBuffer)},Buffer.compare=function(t,e){if(!Buffer.isBuffer(t)||!Buffer.isBuffer(e))throw new TypeError("Arguments must be Buffers");if(t===e)return 0;for(var r=t.length,n=e.length,f=0,i=Math.min(r,n);f<i;++f)if(t[f]!==e[f]){r=t[f],n=e[f];break}return r<n?-1:n<r?1:0},Buffer.isEncoding=function(t){switch(String(t).toLowerCase()){case"hex":case"utf8":case"utf-8":case"ascii":case"latin1":case"binary":case"base64":case"ucs2":case"ucs-2":case"utf16le":case"utf-16le":return!0;default:return!1}},Buffer.concat=function(t,e){if(!isArray(t))throw new TypeError('"list" argument must be an Array of Buffers');if(0===t.length)return Buffer.alloc(0);var r;if(void 0===e)for(e=0,r=0;r<t.length;++r)e+=t[r].length;var n=Buffer.allocUnsafe(e),f=0;for(r=0;r<t.length;++r){var i=t[r];if(!Buffer.isBuffer(i))throw new TypeError('"list" argument must be an Array of Buffers');i.copy(n,f),f+=i.length}return n},Buffer.byteLength=byteLength,Buffer.prototype._isBuffer=!0,Buffer.prototype.swap16=function(){var t=this.length;if(t%2!=0)throw new RangeError("Buffer size must be a multiple of 16-bits");for(var e=0;e<t;e+=2)swap(this,e,e+1);return this},Buffer.prototype.swap32=function(){var t=this.length;if(t%4!=0)throw new RangeError("Buffer size must be a multiple of 32-bits");for(var e=0;e<t;e+=4)swap(this,e,e+3),swap(this,e+1,e+2);return this},Buffer.prototype.swap64=function(){var t=this.length;if(t%8!=0)throw new RangeError("Buffer size must be a multiple of 64-bits");for(var e=0;e<t;e+=8)swap(this,e,e+7),swap(this,e+1,e+6),swap(this,e+2,e+5),swap(this,e+3,e+4);return this},Buffer.prototype.toString=function(){var t=0|this.length;return 0===t?"":0===arguments.length?utf8Slice(this,0,t):slowToString.apply(this,arguments)},Buffer.prototype.equals=function(t){if(!Buffer.isBuffer(t))throw new TypeError("Argument must be a Buffer");return this===t||0===Buffer.compare(this,t)},Buffer.prototype.inspect=function(){var t="",e=exports.INSPECT_MAX_BYTES;return this.length>0&&(t=this.toString("hex",0,e).match(/.{2}/g).join(" "),this.length>e&&(t+=" ... ")),"<Buffer "+t+">"},Buffer.prototype.compare=function(t,e,r,n,f){if(!Buffer.isBuffer(t))throw new TypeError("Argument must be a Buffer");if(void 0===e&&(e=0),void 0===r&&(r=t?t.length:0),void 0===n&&(n=0),void 0===f&&(f=this.length),e<0||r>t.length||n<0||f>this.length)throw new RangeError("out of range index");if(n>=f&&e>=r)return 0;if(n>=f)return-1;if(e>=r)return 1;if(e>>>=0,r>>>=0,n>>>=0,f>>>=0,this===t)return 0;for(var i=f-n,o=r-e,u=Math.min(i,o),s=this.slice(n,f),a=t.slice(e,r),h=0;h<u;++h)if(s[h]!==a[h]){i=s[h],o=a[h];break}return i<o?-1:o<i?1:0},Buffer.prototype.includes=function(t,e,r){return-1!==this.indexOf(t,e,r)},Buffer.prototype.indexOf=function(t,e,r){return bidirectionalIndexOf(this,t,e,r,!0)},Buffer.prototype.lastIndexOf=function(t,e,r){return bidirectionalIndexOf(this,t,e,r,!1)},Buffer.prototype.write=function(t,e,r,n){if(void 0===e)n="utf8",r=this.length,e=0;else if(void 0===r&&"string"==typeof e)n=e,r=this.length,e=0;else{if(!isFinite(e))throw new Error("Buffer.write(string, encoding, offset[, length]) is no longer supported");e|=0,isFinite(r)?(r|=0,void 0===n&&(n="utf8")):(n=r,r=void 0)}var f=this.length-e;if((void 0===r||r>f)&&(r=f),t.length>0&&(r<0||e<0)||e>this.length)throw new RangeError("Attempt to write outside buffer bounds");n||(n="utf8");for(var i=!1;;)switch(n){case"hex":return hexWrite(this,t,e,r);case"utf8":case"utf-8":return utf8Write(this,t,e,r);case"ascii":return asciiWrite(this,t,e,r);case"latin1":case"binary":return latin1Write(this,t,e,r);case"base64":return base64Write(this,t,e,r);case"ucs2":case"ucs-2":case"utf16le":case"utf-16le":return ucs2Write(this,t,e,r);default:if(i)throw new TypeError("Unknown encoding: "+n);n=(""+n).toLowerCase(),i=!0}},Buffer.prototype.toJSON=function(){return{type:"Buffer",data:Array.prototype.slice.call(this._arr||this,0)}};var MAX_ARGUMENTS_LENGTH=4096;Buffer.prototype.slice=function(t,e){var r=this.length;t=~~t,e=void 0===e?r:~~e,t<0?(t+=r)<0&&(t=0):t>r&&(t=r),e<0?(e+=r)<0&&(e=0):e>r&&(e=r),e<t&&(e=t);var n;if(Buffer.TYPED_ARRAY_SUPPORT)n=this.subarray(t,e),n.__proto__=Buffer.prototype;else{var f=e-t;n=new Buffer(f,void 0);for(var i=0;i<f;++i)n[i]=this[i+t]}return n},Buffer.prototype.readUIntLE=function(t,e,r){t|=0,e|=0,r||checkOffset(t,e,this.length);for(var n=this[t],f=1,i=0;++i<e&&(f*=256);)n+=this[t+i]*f;return n},Buffer.prototype.readUIntBE=function(t,e,r){t|=0,e|=0,r||checkOffset(t,e,this.length);for(var n=this[t+--e],f=1;e>0&&(f*=256);)n+=this[t+--e]*f;return n},Buffer.prototype.readUInt8=function(t,e){return e||checkOffset(t,1,this.length),this[t]},Buffer.prototype.readUInt16LE=function(t,e){return e||checkOffset(t,2,this.length),this[t]|this[t+1]<<8},Buffer.prototype.readUInt16BE=function(t,e){return e||checkOffset(t,2,this.length),this[t]<<8|this[t+1]},Buffer.prototype.readUInt32LE=function(t,e){return e||checkOffset(t,4,this.length),(this[t]|this[t+1]<<8|this[t+2]<<16)+16777216*this[t+3]},Buffer.prototype.readUInt32BE=function(t,e){return e||checkOffset(t,4,this.length),16777216*this[t]+(this[t+1]<<16|this[t+2]<<8|this[t+3])},Buffer.prototype.readIntLE=function(t,e,r){t|=0,e|=0,r||checkOffset(t,e,this.length);for(var n=this[t],f=1,i=0;++i<e&&(f*=256);)n+=this[t+i]*f;return f*=128,n>=f&&(n-=Math.pow(2,8*e)),n},Buffer.prototype.readIntBE=function(t,e,r){t|=0,e|=0,r||checkOffset(t,e,this.length);for(var n=e,f=1,i=this[t+--n];n>0&&(f*=256);)i+=this[t+--n]*f;return f*=128,i>=f&&(i-=Math.pow(2,8*e)),i},Buffer.prototype.readInt8=function(t,e){return e||checkOffset(t,1,this.length),128&this[t]?-1*(255-this[t]+1):this[t]},Buffer.prototype.readInt16LE=function(t,e){e||checkOffset(t,2,this.length);var r=this[t]|this[t+1]<<8;return 32768&r?4294901760|r:r},Buffer.prototype.readInt16BE=function(t,e){e||checkOffset(t,2,this.length);var r=this[t+1]|this[t]<<8;return 32768&r?4294901760|r:r},Buffer.prototype.readInt32LE=function(t,e){return e||checkOffset(t,4,this.length),this[t]|this[t+1]<<8|this[t+2]<<16|this[t+3]<<24},Buffer.prototype.readInt32BE=function(t,e){return e||checkOffset(t,4,this.length),this[t]<<24|this[t+1]<<16|this[t+2]<<8|this[t+3]},Buffer.prototype.readFloatLE=function(t,e){return e||checkOffset(t,4,this.length),ieee754.read(this,t,!0,23,4)},Buffer.prototype.readFloatBE=function(t,e){return e||checkOffset(t,4,this.length),ieee754.read(this,t,!1,23,4)},Buffer.prototype.readDoubleLE=function(t,e){return e||checkOffset(t,8,this.length),ieee754.read(this,t,!0,52,8)},Buffer.prototype.readDoubleBE=function(t,e){return e||checkOffset(t,8,this.length),ieee754.read(this,t,!1,52,8)},Buffer.prototype.writeUIntLE=function(t,e,r,n){if(t=+t,e|=0,r|=0,!n){checkInt(this,t,e,r,Math.pow(2,8*r)-1,0)}var f=1,i=0;for(this[e]=255&t;++i<r&&(f*=256);)this[e+i]=t/f&255;return e+r},Buffer.prototype.writeUIntBE=function(t,e,r,n){if(t=+t,e|=0,r|=0,!n){checkInt(this,t,e,r,Math.pow(2,8*r)-1,0)}var f=r-1,i=1;for(this[e+f]=255&t;--f>=0&&(i*=256);)this[e+f]=t/i&255;return e+r},Buffer.prototype.writeUInt8=function(t,e,r){return t=+t,e|=0,r||checkInt(this,t,e,1,255,0),Buffer.TYPED_ARRAY_SUPPORT||(t=Math.floor(t)),this[e]=255&t,e+1},Buffer.prototype.writeUInt16LE=function(t,e,r){return t=+t,e|=0,r||checkInt(this,t,e,2,65535,0),Buffer.TYPED_ARRAY_SUPPORT?(this[e]=255&t,this[e+1]=t>>>8):objectWriteUInt16(this,t,e,!0),e+2},Buffer.prototype.writeUInt16BE=function(t,e,r){return t=+t,e|=0,r||checkInt(this,t,e,2,65535,0),Buffer.TYPED_ARRAY_SUPPORT?(this[e]=t>>>8,this[e+1]=255&t):objectWriteUInt16(this,t,e,!1),e+2},Buffer.prototype.writeUInt32LE=function(t,e,r){return t=+t,e|=0,r||checkInt(this,t,e,4,4294967295,0),Buffer.TYPED_ARRAY_SUPPORT?(this[e+3]=t>>>24,this[e+2]=t>>>16,this[e+1]=t>>>8,this[e]=255&t):objectWriteUInt32(this,t,e,!0),e+4},Buffer.prototype.writeUInt32BE=function(t,e,r){return t=+t,e|=0,r||checkInt(this,t,e,4,4294967295,0),Buffer.TYPED_ARRAY_SUPPORT?(this[e]=t>>>24,this[e+1]=t>>>16,this[e+2]=t>>>8,this[e+3]=255&t):objectWriteUInt32(this,t,e,!1),e+4},Buffer.prototype.writeIntLE=function(t,e,r,n){if(t=+t,e|=0,!n){var f=Math.pow(2,8*r-1);checkInt(this,t,e,r,f-1,-f)}var i=0,o=1,u=0;for(this[e]=255&t;++i<r&&(o*=256);)t<0&&0===u&&0!==this[e+i-1]&&(u=1),this[e+i]=(t/o>>0)-u&255;return e+r},Buffer.prototype.writeIntBE=function(t,e,r,n){if(t=+t,e|=0,!n){var f=Math.pow(2,8*r-1);checkInt(this,t,e,r,f-1,-f)}var i=r-1,o=1,u=0;for(this[e+i]=255&t;--i>=0&&(o*=256);)t<0&&0===u&&0!==this[e+i+1]&&(u=1),this[e+i]=(t/o>>0)-u&255;return e+r},Buffer.prototype.writeInt8=function(t,e,r){return t=+t,e|=0,r||checkInt(this,t,e,1,127,-128),Buffer.TYPED_ARRAY_SUPPORT||(t=Math.floor(t)),t<0&&(t=255+t+1),this[e]=255&t,e+1},Buffer.prototype.writeInt16LE=function(t,e,r){return t=+t,e|=0,r||checkInt(this,t,e,2,32767,-32768),Buffer.TYPED_ARRAY_SUPPORT?(this[e]=255&t,this[e+1]=t>>>8):objectWriteUInt16(this,t,e,!0),e+2},Buffer.prototype.writeInt16BE=function(t,e,r){return t=+t,e|=0,r||checkInt(this,t,e,2,32767,-32768),Buffer.TYPED_ARRAY_SUPPORT?(this[e]=t>>>8,this[e+1]=255&t):objectWriteUInt16(this,t,e,!1),e+2},Buffer.prototype.writeInt32LE=function(t,e,r){return t=+t,e|=0,r||checkInt(this,t,e,4,2147483647,-2147483648),Buffer.TYPED_ARRAY_SUPPORT?(this[e]=255&t,this[e+1]=t>>>8,this[e+2]=t>>>16,this[e+3]=t>>>24):objectWriteUInt32(this,t,e,!0),e+4},Buffer.prototype.writeInt32BE=function(t,e,r){return t=+t,e|=0,r||checkInt(this,t,e,4,2147483647,-2147483648),t<0&&(t=4294967295+t+1),Buffer.TYPED_ARRAY_SUPPORT?(this[e]=t>>>24,this[e+1]=t>>>16,this[e+2]=t>>>8,this[e+3]=255&t):objectWriteUInt32(this,t,e,!1),e+4},Buffer.prototype.writeFloatLE=function(t,e,r){return writeFloat(this,t,e,!0,r)},Buffer.prototype.writeFloatBE=function(t,e,r){return writeFloat(this,t,e,!1,r)},Buffer.prototype.writeDoubleLE=function(t,e,r){return writeDouble(this,t,e,!0,r)},Buffer.prototype.writeDoubleBE=function(t,e,r){return writeDouble(this,t,e,!1,r)},Buffer.prototype.copy=function(t,e,r,n){if(r||(r=0),n||0===n||(n=this.length),e>=t.length&&(e=t.length),e||(e=0),n>0&&n<r&&(n=r),n===r)return 0;if(0===t.length||0===this.length)return 0;if(e<0)throw new RangeError("targetStart out of bounds");if(r<0||r>=this.length)throw new RangeError("sourceStart out of bounds");if(n<0)throw new RangeError("sourceEnd out of bounds");n>this.length&&(n=this.length),t.length-e<n-r&&(n=t.length-e+r);var f,i=n-r;if(this===t&&r<e&&e<n)for(f=i-1;f>=0;--f)t[f+e]=this[f+r];else if(i<1e3||!Buffer.TYPED_ARRAY_SUPPORT)for(f=0;f<i;++f)t[f+e]=this[f+r];else Uint8Array.prototype.set.call(t,this.subarray(r,r+i),e);return i},Buffer.prototype.fill=function(t,e,r,n){if("string"==typeof t){if("string"==typeof e?(n=e,e=0,r=this.length):"string"==typeof r&&(n=r,r=this.length),1===t.length){var f=t.charCodeAt(0);f<256&&(t=f)}if(void 0!==n&&"string"!=typeof n)throw new TypeError("encoding must be a string");if("string"==typeof n&&!Buffer.isEncoding(n))throw new TypeError("Unknown encoding: "+n)}else"number"==typeof t&&(t&=255);if(e<0||this.length<e||this.length<r)throw new RangeError("Out of range index");if(r<=e)return this;e>>>=0,r=void 0===r?this.length:r>>>0,t||(t=0);var i;if("number"==typeof t)for(i=e;i<r;++i)this[i]=t;else{var o=Buffer.isBuffer(t)?t:utf8ToBytes(new Buffer(t,n).toString()),u=o.length;for(i=0;i<r-e;++i)this[i+e]=o[i%u]}return this};var INVALID_BASE64_RE=/[^+\/0-9A-Za-z-_]/g;
/* WEBPACK VAR INJECTION */}.call(exports, __webpack_require__(27)))

/***/ }),
/* 39 */
/***/ (function(module, exports) {

"function"==typeof Object.create?module.exports=function(t,e){t.super_=e,t.prototype=Object.create(e.prototype,{constructor:{value:t,enumerable:!1,writable:!0,configurable:!0}})}:module.exports=function(t,e){t.super_=e;var o=function(){};o.prototype=e.prototype,t.prototype=new o,t.prototype.constructor=t};

/***/ }),
/* 40 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
function Duplex(e){if(!(this instanceof Duplex))return new Duplex(e);Readable.call(this,e),Writable.call(this,e),e&&!1===e.readable&&(this.readable=!1),e&&!1===e.writable&&(this.writable=!1),this.allowHalfOpen=!0,e&&!1===e.allowHalfOpen&&(this.allowHalfOpen=!1),this.once("end",onend)}function onend(){this.allowHalfOpen||this._writableState.ended||processNextTick(onEndNT,this)}function onEndNT(e){e.end()}function forEach(e,t){for(var r=0,i=e.length;r<i;r++)t(e[r],r)}var processNextTick=__webpack_require__(72),objectKeys=Object.keys||function(e){var t=[];for(var r in e)t.push(r);return t};module.exports=Duplex;var util=__webpack_require__(47);util.inherits=__webpack_require__(39);var Readable=__webpack_require__(139),Writable=__webpack_require__(102);util.inherits(Duplex,Readable);for(var keys=objectKeys(Writable.prototype),v=0;v<keys.length;v++){var method=keys[v];Duplex.prototype[method]||(Duplex.prototype[method]=Writable.prototype[method])}Object.defineProperty(Duplex.prototype,"destroyed",{get:function(){return void 0!==this._readableState&&void 0!==this._writableState&&(this._readableState.destroyed&&this._writableState.destroyed)},set:function(e){void 0!==this._readableState&&void 0!==this._writableState&&(this._readableState.destroyed=e,this._writableState.destroyed=e)}}),Duplex.prototype._destroy=function(e,t){this.push(null),this.end(),processNextTick(t,e)};

/***/ }),
/* 41 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
var __extends=this&&this.__extends||function(){var t=Object.setPrototypeOf||{__proto__:[]}instanceof Array&&function(t,e){t.__proto__=e}||function(t,e){for(var n in e)e.hasOwnProperty(n)&&(t[n]=e[n])};return function(e,n){function o(){this.constructor=e}t(e,n),e.prototype=null===n?Object.create(n):(o.prototype=n.prototype,new o)}}(),__decorate=this&&this.__decorate||function(t,e,n,o){var i,a=arguments.length,r=a<3?e:null===o?o=Object.getOwnPropertyDescriptor(e,n):o;if("object"==typeof Reflect&&"function"==typeof Reflect.decorate)r=Reflect.decorate(t,e,n,o);else for(var s=t.length-1;s>=0;s--)(i=t[s])&&(r=(a<3?i(r):a>3?i(e,n,r):i(e,n))||r);return a>3&&r&&Object.defineProperty(e,n,r),r},__metadata=this&&this.__metadata||function(t,e){if("object"==typeof Reflect&&"function"==typeof Reflect.metadata)return Reflect.metadata(t,e)},__param=this&&this.__param||function(t,e){return function(n,o){e(n,o,t)}};Object.defineProperty(exports,"__esModule",{value:!0});var inversify_1=__webpack_require__(0),types_1=__webpack_require__(1),geometry_1=__webpack_require__(3),smodel_1=__webpack_require__(2),command_1=__webpack_require__(5),CanvasBoundsInitializer=function(){function t(t){this.actionDispatcher=t}return t.prototype.decorate=function(t,e){return e instanceof smodel_1.SModelRoot&&!geometry_1.isValidDimension(e.canvasBounds)&&(this.rootAndVnode=[e,t]),t},t.prototype.postUpdate=function(){if(void 0!==this.rootAndVnode){var t=this.rootAndVnode[1].elm,e=this.rootAndVnode[0].canvasBounds;if(void 0!==t){var n=this.getBoundsInPage(t);geometry_1.almostEquals(n.x,e.x)&&geometry_1.almostEquals(n.y,e.y)&&geometry_1.almostEquals(n.width,e.width)&&geometry_1.almostEquals(n.height,e.width)||this.actionDispatcher.dispatch(new InitializeCanvasBoundsAction(n))}this.rootAndVnode=void 0}},t.prototype.getBoundsInPage=function(t){var e=t.getBoundingClientRect(),n="undefined"!=typeof window?{x:window.scrollX,y:window.scrollY}:geometry_1.ORIGIN_POINT;return{x:e.left+n.x,y:e.top+n.y,width:e.width,height:e.height}},t=__decorate([inversify_1.injectable(),__param(0,inversify_1.inject(types_1.TYPES.IActionDispatcher)),__metadata("design:paramtypes",[Object])],t)}();exports.CanvasBoundsInitializer=CanvasBoundsInitializer;var InitializeCanvasBoundsAction=function(){function t(t){this.newCanvasBounds=t,this.kind=InitializeCanvasBoundsCommand.KIND}return t}();exports.InitializeCanvasBoundsAction=InitializeCanvasBoundsAction;var InitializeCanvasBoundsCommand=function(t){function e(e){var n=t.call(this)||this;return n.action=e,n}return __extends(e,t),e.prototype.execute=function(t){return this.newCanvasBounds=this.action.newCanvasBounds,t.root.canvasBounds=this.newCanvasBounds,t.root},e.prototype.undo=function(t){return t.root},e.prototype.redo=function(t){return t.root},e.KIND="initializeCanvasBounds",e}(command_1.SystemCommand);exports.InitializeCanvasBoundsCommand=InitializeCanvasBoundsCommand;

/***/ }),
/* 42 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
function createRoutingHandle(e,t,n){var o=new model_1.SRoutingHandle;return o.type="junction"===e?"routing-point":"volatile-routing-point",o.kind=e,o.pointIndex=n,o}function createRoutingHandles(e){var t=e.routingPoints.length,n=e.id;e.add(createRoutingHandle("line",n,-1));for(var o=0;o<t;o++)e.add(createRoutingHandle("junction",n,o)),e.add(createRoutingHandle("line",n,o))}var __extends=this&&this.__extends||function(){var e=Object.setPrototypeOf||{__proto__:[]}instanceof Array&&function(e,t){e.__proto__=t}||function(e,t){for(var n in t)t.hasOwnProperty(n)&&(e[n]=t[n])};return function(t,n){function o(){this.constructor=t}e(t,n),t.prototype=null===n?Object.create(n):(o.prototype=n.prototype,new o)}}(),__decorate=this&&this.__decorate||function(e,t,n,o){var i,a=arguments.length,r=a<3?t:null===o?o=Object.getOwnPropertyDescriptor(t,n):o;if("object"==typeof Reflect&&"function"==typeof Reflect.decorate)r=Reflect.decorate(e,t,n,o);else for(var d=e.length-1;d>=0;d--)(i=e[d])&&(r=(a<3?i(r):a>3?i(t,n,r):i(t,n))||r);return a>3&&r&&Object.defineProperty(t,n,r),r},__metadata=this&&this.__metadata||function(e,t){if("object"==typeof Reflect&&"function"==typeof Reflect.metadata)return Reflect.metadata(e,t)};Object.defineProperty(exports,"__esModule",{value:!0});var inversify_1=__webpack_require__(0),model_1=__webpack_require__(21),command_1=__webpack_require__(5),smodel_1=__webpack_require__(2),animation_1=__webpack_require__(19);exports.createRoutingHandle=createRoutingHandle,exports.createRoutingHandles=createRoutingHandles;var SwitchEditModeAction=function(){function e(e,t){void 0===e&&(e=[]),void 0===t&&(t=[]),this.elementsToActivate=e,this.elementsToDeactivate=t,this.kind=SwitchEditModeCommand.KIND}return e}();exports.SwitchEditModeAction=SwitchEditModeAction;var SwitchEditModeCommand=function(e){function t(t){var n=e.call(this)||this;return n.action=t,n.elementsToActivate=[],n.elementsToDeactivate=[],n.handlesToRemove=[],n}return __extends(t,e),t.prototype.execute=function(e){var t=this,n=e.root.index;return this.action.elementsToActivate.forEach(function(e){var o=n.getById(e);void 0!==o&&t.elementsToActivate.push(o)}),this.action.elementsToDeactivate.forEach(function(e){var o=n.getById(e);if(void 0!==o&&t.elementsToDeactivate.push(o),o instanceof model_1.SRoutingHandle&&model_1.isRoutable(o.parent)){var i=o.parent;t.shouldRemoveHandle(o,i)&&(t.handlesToRemove.push({handle:o,parent:i}),t.elementsToDeactivate.push(i),t.elementsToActivate.push(i))}}),this.doExecute(e)},t.prototype.doExecute=function(e){return this.handlesToRemove.forEach(function(e){e.point=e.parent.routingPoints.splice(e.handle.pointIndex,1)[0]}),this.elementsToDeactivate.forEach(function(e){model_1.isRoutable(e)&&e instanceof smodel_1.SParentElement?e.removeAll(function(e){return e instanceof model_1.SRoutingHandle}):e instanceof model_1.SRoutingHandle&&(e.editMode=!1)}),this.elementsToActivate.forEach(function(e){model_1.canEditRouting(e)&&e instanceof smodel_1.SParentElement?createRoutingHandles(e):e instanceof model_1.SRoutingHandle&&(e.editMode=!0)}),e.root},t.prototype.shouldRemoveHandle=function(e,t){if("junction"===e.kind){return void 0===t.route().find(function(t){return t.pointIndex===e.pointIndex})}return!1},t.prototype.undo=function(e){return this.handlesToRemove.forEach(function(e){void 0!==e.point&&e.parent.routingPoints.splice(e.handle.pointIndex,0,e.point)}),this.elementsToActivate.forEach(function(e){model_1.isRoutable(e)&&e instanceof smodel_1.SParentElement?e.removeAll(function(e){return e instanceof model_1.SRoutingHandle}):e instanceof model_1.SRoutingHandle&&(e.editMode=!1)}),this.elementsToDeactivate.forEach(function(e){model_1.canEditRouting(e)&&e instanceof smodel_1.SParentElement?createRoutingHandles(e):e instanceof model_1.SRoutingHandle&&(e.editMode=!0)}),e.root},t.prototype.redo=function(e){return this.doExecute(e)},t.KIND="switchEditMode",t=__decorate([inversify_1.injectable(),__metadata("design:paramtypes",[SwitchEditModeAction])],t)}(command_1.Command);exports.SwitchEditModeCommand=SwitchEditModeCommand;var MoveRoutingHandleAction=function(){function e(e,t){void 0===t&&(t=!0),this.moves=e,this.animate=t,this.kind=MoveRoutingHandleCommand.KIND}return e}();exports.MoveRoutingHandleAction=MoveRoutingHandleAction;var MoveRoutingHandleCommand=function(e){function t(t){var n=e.call(this)||this;return n.action=t,n.resolvedMoves=new Map,n.originalRoutingPoints=new Map,n}return __extends(t,e),t.prototype.execute=function(e){var t=this,n=e.root;return this.action.moves.forEach(function(e){var o=t.resolve(e,n.index);if(void 0!==o){t.resolvedMoves.set(o.elementId,o);var i=o.parent;model_1.isRoutable(i)&&t.originalRoutingPoints.set(i.id,i.routingPoints.slice())}}),this.action.animate?new MoveHandlesAnimation(n,this.resolvedMoves,this.originalRoutingPoints,e).start():this.doMove(e)},t.prototype.resolve=function(e,t){var n=t.getById(e.elementId);if(n instanceof model_1.SRoutingHandle)return{elementId:e.elementId,element:n,parent:n.parent,fromPosition:e.fromPosition,toPosition:e.toPosition}},t.prototype.doMove=function(e){return this.resolvedMoves.forEach(function(e){var t=e.element,n=e.parent;if(model_1.isRoutable(n)){var o=n.routingPoints,i=t.pointIndex;"line"===t.kind&&(t.kind="junction",t.type="routing-point",o.splice(i+1,0,e.fromPosition||o[Math.max(i,0)]),n.children.forEach(function(e){e instanceof model_1.SRoutingHandle&&(e===t||e.pointIndex>i)&&e.pointIndex++}),n.add(createRoutingHandle("line",n.id,i)),n.add(createRoutingHandle("line",n.id,i+1)),i++),i>=0&&i<o.length&&(o[i]=e.toPosition)}}),e.root},t.prototype.undo=function(e){var t=this;return this.action.animate?new MoveHandlesAnimation(e.root,this.resolvedMoves,this.originalRoutingPoints,e,!0).start():(this.resolvedMoves.forEach(function(e){var n=e.parent,o=t.originalRoutingPoints.get(n.id);void 0!==o&&model_1.isRoutable(n)&&(n.routingPoints=o,n.removeAll(function(e){return e instanceof model_1.SRoutingHandle}),createRoutingHandles(n))}),e.root)},t.prototype.redo=function(e){return this.action.animate?new MoveHandlesAnimation(e.root,this.resolvedMoves,this.originalRoutingPoints,e,!1).start():this.doMove(e)},t.KIND="moveHandle",t=__decorate([inversify_1.injectable(),__metadata("design:paramtypes",[MoveRoutingHandleAction])],t)}(command_1.Command);exports.MoveRoutingHandleCommand=MoveRoutingHandleCommand;var MoveHandlesAnimation=function(e){function t(t,n,o,i,a){void 0===a&&(a=!1);var r=e.call(this,i)||this;return r.model=t,r.handleMoves=n,r.originalRoutingPoints=o,r.reverse=a,r}return __extends(t,e),t.prototype.tween=function(e){var t=this;return this.handleMoves.forEach(function(n){var o=n.parent;if(model_1.isRoutable(o)&&void 0!==n.fromPosition){if(t.reverse&&1===e){var i=t.originalRoutingPoints.get(o.id);if(void 0!==i)return o.routingPoints=i,o.removeAll(function(e){return e instanceof model_1.SRoutingHandle}),void createRoutingHandles(o)}var a=o.routingPoints,r=n.element.pointIndex;r>=0&&r<a.length&&(t.reverse?a[r]={x:(1-e)*n.toPosition.x+e*n.fromPosition.x,y:(1-e)*n.toPosition.y+e*n.fromPosition.y}:a[r]={x:(1-e)*n.fromPosition.x+e*n.toPosition.x,y:(1-e)*n.fromPosition.y+e*n.toPosition.y})}}),this.model},t}(animation_1.Animation);exports.MoveHandlesAnimation=MoveHandlesAnimation;

/***/ }),
/* 43 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
var __decorate=this&&this.__decorate||function(e,t,n,o){var l,r=arguments.length,a=r<3?t:null===o?o=Object.getOwnPropertyDescriptor(t,n):o;if("object"==typeof Reflect&&"function"==typeof Reflect.decorate)a=Reflect.decorate(e,t,n,o);else for(var i=e.length-1;i>=0;i--)(l=e[i])&&(a=(r<3?l(a):r>3?l(t,n,a):l(t,n))||a);return r>3&&a&&Object.defineProperty(t,n,a),a};Object.defineProperty(exports,"__esModule",{value:!0});var smodel_utils_1=__webpack_require__(6),model_1=__webpack_require__(60),inversify_1=__webpack_require__(0),CollapseExpandAction=function(){function e(t,n){this.expandIds=t,this.collapseIds=n,this.kind=e.KIND}return e.KIND="collapseExpand",e}();exports.CollapseExpandAction=CollapseExpandAction;var CollapseExpandAllAction=function(){function e(t){void 0===t&&(t=!0),this.expand=t,this.kind=e.KIND}return e.KIND="collapseExpandAll",e}();exports.CollapseExpandAllAction=CollapseExpandAllAction;var ExpandButtonHandler=function(){function e(){}return e.prototype.buttonPressed=function(e){var t=smodel_utils_1.findParentByFeature(e,model_1.isExpandable);return void 0!==t?[new CollapseExpandAction(t.expanded?[]:[t.id],t.expanded?[t.id]:[])]:[]},e.TYPE="button:expand",e=__decorate([inversify_1.injectable()],e)}();exports.ExpandButtonHandler=ExpandButtonHandler;

/***/ }),
/* 44 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
function isExportable(e){return e.hasFeature(exports.exportFeature)&&void 0!==e.export}Object.defineProperty(exports,"__esModule",{value:!0}),exports.exportFeature=Symbol("exportFeature"),exports.isExportable=isExportable;

/***/ }),
/* 45 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
var __extends=this&&this.__extends||function(){var e=Object.setPrototypeOf||{__proto__:[]}instanceof Array&&function(e,o){e.__proto__=o}||function(e,o){for(var t in o)o.hasOwnProperty(t)&&(e[t]=o[t])};return function(o,t){function i(){this.constructor=o}e(o,t),o.prototype=null===t?Object.create(t):(i.prototype=t.prototype,new i)}}(),__decorate=this&&this.__decorate||function(e,o,t,i){var n,r=arguments.length,s=r<3?o:null===i?i=Object.getOwnPropertyDescriptor(o,t):i;if("object"==typeof Reflect&&"function"==typeof Reflect.decorate)s=Reflect.decorate(e,o,t,i);else for(var a=e.length-1;a>=0;a--)(n=e[a])&&(s=(r<3?n(s):r>3?n(o,t,s):n(o,t))||s);return r>3&&s&&Object.defineProperty(o,t,s),s};Object.defineProperty(exports,"__esModule",{value:!0});var inversify_1=__webpack_require__(0),geometry_1=__webpack_require__(3),smodel_1=__webpack_require__(2),smodel_2=__webpack_require__(2),smodel_utils_1=__webpack_require__(6),command_1=__webpack_require__(5),animation_1=__webpack_require__(19),mouse_tool_1=__webpack_require__(15),vnode_utils_1=__webpack_require__(11),model_1=__webpack_require__(16),model_2=__webpack_require__(12),model_3=__webpack_require__(7),model_4=__webpack_require__(21),edit_routing_1=__webpack_require__(42),model_5=__webpack_require__(22),MoveAction=function(){function e(e,o){void 0===o&&(o=!0),this.moves=e,this.animate=o,this.kind=MoveCommand.KIND}return e}();exports.MoveAction=MoveAction;var MoveCommand=function(e){function o(o){var t=e.call(this)||this;return t.action=o,t.resolvedMoves=new Map,t.resolvedRoutes=new Map,t}return __extends(o,e),o.prototype.execute=function(e){var o=this,t=e.root,i=new Set;return this.action.moves.forEach(function(e){var n=o.resolve(e,t.index);void 0!==n&&(o.resolvedMoves.set(n.elementId,n),t.index.getAttachedElements(n.element).forEach(function(e){return i.add(e)}))}),i.forEach(function(e){return o.handleAttachedElement(e)}),this.action.animate?new MoveAnimation(t,this.resolvedMoves,this.resolvedRoutes,e).start():this.doMove(e)},o.prototype.resolve=function(e,o){var t=o.getById(e.elementId);if(void 0!==t&&model_5.isLocateable(t)){var i=e.fromPosition||{x:t.position.x,y:t.position.y};return{elementId:e.elementId,element:t,fromPosition:i,toPosition:e.toPosition}}},o.prototype.handleAttachedElement=function(e){if(model_4.isRoutable(e)){var o=e.source,t=o?this.resolvedMoves.get(o.id):void 0,i=e.target,n=i?this.resolvedMoves.get(i.id):void 0;if(void 0!==t&&void 0!==n){var r=n.toPosition.x-n.fromPosition.x,s=n.toPosition.y-n.fromPosition.y;this.resolvedRoutes.set(e.id,{elementId:e.id,element:e,fromRoute:e.routingPoints,toRoute:e.routingPoints.map(function(e){return{x:e.x+r,y:e.y+s}})})}}},o.prototype.doMove=function(e,o){return this.resolvedMoves.forEach(function(e){e.element.position=o?e.fromPosition:e.toPosition}),this.resolvedRoutes.forEach(function(e){e.element.routingPoints=o?e.fromRoute:e.toRoute}),e.root},o.prototype.undo=function(e){return new MoveAnimation(e.root,this.resolvedMoves,this.resolvedRoutes,e,!0).start()},o.prototype.redo=function(e){return new MoveAnimation(e.root,this.resolvedMoves,this.resolvedRoutes,e,!1).start()},o.prototype.merge=function(e,t){var i=this;return!this.action.animate&&e instanceof o&&(e.action.moves.forEach(function(e){var o=i.resolvedMoves.get(e.elementId);if(o)o.toPosition=e.toPosition;else{var n=i.resolve(e,t.root.index);n&&i.resolvedMoves.set(n.elementId,n)}}),!0)},o.KIND="move",o}(command_1.MergeableCommand);exports.MoveCommand=MoveCommand;var MoveAnimation=function(e){function o(o,t,i,n,r){void 0===r&&(r=!1);var s=e.call(this,n)||this;return s.model=o,s.elementMoves=t,s.elementRoutes=i,s.reverse=r,s}return __extends(o,e),o.prototype.tween=function(e){var o=this;return this.elementMoves.forEach(function(t){o.reverse?t.element.position={x:(1-e)*t.toPosition.x+e*t.fromPosition.x,y:(1-e)*t.toPosition.y+e*t.fromPosition.y}:t.element.position={x:(1-e)*t.fromPosition.x+e*t.toPosition.x,y:(1-e)*t.fromPosition.y+e*t.toPosition.y}}),this.elementRoutes.forEach(function(t){for(var i=[],n=0;n<t.fromRoute.length&&n<t.toRoute.length;n++){var r=t.fromRoute[n],s=t.toRoute[n];o.reverse?i.push({x:(1-e)*s.x+e*r.x,y:(1-e)*s.y+e*r.y}):i.push({x:(1-e)*r.x+e*s.x,y:(1-e)*r.y+e*s.y})}t.element.routingPoints=i}),this.model},o}(animation_1.Animation);exports.MoveAnimation=MoveAnimation;var MoveMouseListener=function(e){function o(){var o=null!==e&&e.apply(this,arguments)||this;return o.hasDragged=!1,o}return __extends(o,e),o.prototype.mouseDown=function(e,o){var t=[];if(0===o.button){var i=smodel_utils_1.findParentByFeature(e,model_5.isMoveable),n=e instanceof model_4.SRoutingHandle;this.lastDragPosition=void 0!==i||n?{x:o.pageX,y:o.pageY}:void 0,this.hasDragged=!1,n&&t.push(new edit_routing_1.SwitchEditModeAction([e.id],[]))}return t},o.prototype.mouseMove=function(e,o){var t=this,i=[];if(0===o.buttons)this.mouseUp(e,o);else if(this.lastDragPosition){var n=smodel_utils_1.findParentByFeature(e,model_1.isViewport);this.hasDragged=!0;var r=n?n.zoom:1,s=(o.pageX-this.lastDragPosition.x)/r,a=(o.pageY-this.lastDragPosition.y)/r,d=[],u=[];e.root.index.all().filter(function(e){return model_2.isSelectable(e)&&e.selected}).forEach(function(e){if(model_5.isMoveable(e))d.push({elementId:e.id,fromPosition:{x:e.position.x,y:e.position.y},toPosition:{x:e.position.x+s,y:e.position.y+a}});else if(e instanceof model_4.SRoutingHandle){var o=t.getHandlePosition(e);void 0!==o&&u.push({elementId:e.id,fromPosition:o,toPosition:{x:o.x+s,y:o.y+a}})}}),this.lastDragPosition={x:o.pageX,y:o.pageY},d.length>0&&i.push(new MoveAction(d,!1)),u.length>0&&i.push(new edit_routing_1.MoveRoutingHandleAction(u,!1))}return i},o.prototype.getHandlePosition=function(e){var o=e.parent;if(model_4.isRoutable(o))if("line"===e.kind){for(var t=function(e){return void 0!==e.pointIndex?e.pointIndex:"target"===e.kind?o.routingPoints.length:-1},i=o.route(),n=void 0,r=void 0,s=0,a=i;s<a.length;s++){var d=a[s],u=t(d);u<=e.pointIndex&&(void 0===n||u>t(n))&&(n=d),u>e.pointIndex&&(void 0===r||u<t(r))&&(r=d)}if(void 0!==n&&void 0!==r)return geometry_1.centerOfLine(n,r)}else if(e.pointIndex>=0)return o.routingPoints[e.pointIndex]},o.prototype.mouseEnter=function(e,o){return e instanceof smodel_2.SModelRoot&&0===o.buttons&&this.mouseUp(e,o),[]},o.prototype.mouseUp=function(e,o){var t=[];return this.lastDragPosition&&e.root.index.all().forEach(function(e){e instanceof model_4.SRoutingHandle&&e.editMode&&t.push(new edit_routing_1.SwitchEditModeAction([],[e.id]))}),this.hasDragged=!1,this.lastDragPosition=void 0,t},o.prototype.decorate=function(e,o){return e},o}(mouse_tool_1.MouseListener);exports.MoveMouseListener=MoveMouseListener;var LocationDecorator=function(){function e(){}return e.prototype.decorate=function(e,o){var t="";return model_5.isLocateable(o)&&o instanceof smodel_1.SChildElement&&void 0!==o.parent&&(t="translate("+o.position.x+", "+o.position.y+")"),model_3.isAlignable(o)&&(t.length>0&&(t+=" "),t+="translate("+o.alignment.x+", "+o.alignment.y+")"),t.length>0&&vnode_utils_1.setAttr(e,"transform",t),e},e.prototype.postUpdate=function(){},e=__decorate([inversify_1.injectable()],e)}();exports.LocationDecorator=LocationDecorator;

/***/ }),
/* 46 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
var __extends=this&&this.__extends||function(){var e=Object.setPrototypeOf||{__proto__:[]}instanceof Array&&function(e,t){e.__proto__=t}||function(e,t){for(var o in t)t.hasOwnProperty(o)&&(e[o]=t[o])};return function(t,o){function r(){this.constructor=t}e(t,o),t.prototype=null===o?Object.create(o):(r.prototype=o.prototype,new r)}}();Object.defineProperty(exports,"__esModule",{value:!0});var iterable_1=__webpack_require__(70),smodel_1=__webpack_require__(2),model_1=__webpack_require__(7),model_2=__webpack_require__(33),model_3=__webpack_require__(35),model_4=__webpack_require__(22),model_5=__webpack_require__(12),viewport_root_1=__webpack_require__(67),geometry_1=__webpack_require__(3),model_6=__webpack_require__(7),model_7=__webpack_require__(21),smodel_utils_1=__webpack_require__(6),routing_1=__webpack_require__(173),SGraph=function(e){function t(t){return void 0===t&&(t=new SGraphIndex),e.call(this,t)||this}return __extends(t,e),t}(viewport_root_1.ViewportRootElement);exports.SGraph=SGraph;var SConnectableElement=function(e){function t(){return null!==e&&e.apply(this,arguments)||this}return __extends(t,e),Object.defineProperty(t.prototype,"incomingEdges",{get:function(){return this.index.getIncomingEdges(this)},enumerable:!0,configurable:!0}),Object.defineProperty(t.prototype,"outgoingEdges",{get:function(){return this.index.getOutgoingEdges(this)},enumerable:!0,configurable:!0}),t.prototype.getAnchor=function(e,t){return geometry_1.center(this.bounds)},t.prototype.getTranslatedAnchor=function(e,t,o,r){var n=smodel_utils_1.translatePoint(e,t,this.parent),i=this.getAnchor(n,r);return smodel_utils_1.translatePoint(i,this.parent,o.parent)},t}(model_6.SShapeElement);exports.SConnectableElement=SConnectableElement;var SNode=function(e){function t(){var t=null!==e&&e.apply(this,arguments)||this;return t.selected=!1,t.hoverFeedback=!1,t.opacity=1,t}return __extends(t,e),t.prototype.hasFeature=function(e){return e===model_5.selectFeature||e===model_4.moveFeature||e===model_1.boundsFeature||e===model_1.layoutContainerFeature||e===model_2.fadeFeature||e===model_3.hoverFeedbackFeature||e===model_3.popupFeature},t}(SConnectableElement);exports.SNode=SNode;var SPort=function(e){function t(){var t=null!==e&&e.apply(this,arguments)||this;return t.selected=!1,t.hoverFeedback=!1,t.opacity=1,t}return __extends(t,e),t.prototype.hasFeature=function(e){return e===model_5.selectFeature||e===model_1.boundsFeature||e===model_2.fadeFeature||e===model_3.hoverFeedbackFeature},t}(SConnectableElement);exports.SPort=SPort;var SEdge=function(e){function t(){var t=null!==e&&e.apply(this,arguments)||this;return t.routingPoints=[],t.selected=!1,t.hoverFeedback=!1,t.opacity=1,t}return __extends(t,e),Object.defineProperty(t.prototype,"source",{get:function(){return this.index.getById(this.sourceId)},enumerable:!0,configurable:!0}),Object.defineProperty(t.prototype,"target",{get:function(){return this.index.getById(this.targetId)},enumerable:!0,configurable:!0}),t.prototype.route=function(){void 0===this.router&&(this.router=new routing_1.LinearEdgeRouter);var e=this.router.route(this);return model_7.filterEditModeHandles(e,this)},t.prototype.hasFeature=function(e){return e===model_2.fadeFeature||e===model_5.selectFeature||e===model_7.editFeature||e===model_3.hoverFeedbackFeature},t}(smodel_1.SChildElement);exports.SEdge=SEdge;var SLabel=function(e){function t(){var t=null!==e&&e.apply(this,arguments)||this;return t.selected=!1,t.alignment=geometry_1.ORIGIN_POINT,t.opacity=1,t}return __extends(t,e),t.prototype.hasFeature=function(e){return e===model_1.boundsFeature||e===model_1.alignFeature||e===model_2.fadeFeature||e===model_1.layoutableChildFeature},t}(model_6.SShapeElement);exports.SLabel=SLabel;var SCompartment=function(e){function t(){var t=null!==e&&e.apply(this,arguments)||this;return t.opacity=1,t}return __extends(t,e),t.prototype.hasFeature=function(e){return e===model_1.boundsFeature||e===model_1.layoutContainerFeature||e===model_1.layoutableChildFeature||e===model_2.fadeFeature},t}(model_6.SShapeElement);exports.SCompartment=SCompartment;var SGraphIndex=function(e){function t(){var t=null!==e&&e.apply(this,arguments)||this;return t.outgoing=new Map,t.incoming=new Map,t}return __extends(t,e),t.prototype.add=function(t){if(e.prototype.add.call(this,t),t instanceof SEdge){if(t.sourceId){var o=this.outgoing.get(t.sourceId);void 0===o?this.outgoing.set(t.sourceId,[t]):o.push(t)}if(t.targetId){var r=this.incoming.get(t.targetId);void 0===r?this.incoming.set(t.targetId,[t]):r.push(t)}}},t.prototype.remove=function(t){if(e.prototype.remove.call(this,t),t instanceof SEdge){var o=this.outgoing.get(t.sourceId);if(void 0!==o){var r=o.indexOf(t);r>=0&&(1===o.length?this.outgoing.delete(t.sourceId):o.splice(r,1))}var n=this.incoming.get(t.targetId);if(void 0!==n){var r=n.indexOf(t);r>=0&&(1===n.length?this.incoming.delete(t.targetId):n.splice(r,1))}}},t.prototype.getAttachedElements=function(e){var t=this;return new iterable_1.FluentIterableImpl(function(){return{outgoing:t.outgoing.get(e.id),incoming:t.incoming.get(e.id),nextOutgoingIndex:0,nextIncomingIndex:0}},function(e){var t=e.nextOutgoingIndex;if(void 0!==e.outgoing&&t<e.outgoing.length)return e.nextOutgoingIndex=t+1,{done:!1,value:e.outgoing[t]};if(t=e.nextIncomingIndex,void 0!==e.incoming)for(;t<e.incoming.length;){var o=e.incoming[t];if(o.sourceId!==o.targetId)return e.nextIncomingIndex=t+1,{done:!1,value:o};t++}return{done:!0,value:void 0}})},t.prototype.getIncomingEdges=function(e){return this.incoming.get(e.id)||[]},t.prototype.getOutgoingEdges=function(e){return this.outgoing.get(e.id)||[]},t}(smodel_1.SModelIndex);exports.SGraphIndex=SGraphIndex;

/***/ }),
/* 47 */
/***/ (function(module, exports, __webpack_require__) {

/* WEBPACK VAR INJECTION */(function(Buffer) {function isArray(r){return Array.isArray?Array.isArray(r):"[object Array]"===objectToString(r)}function isBoolean(r){return"boolean"==typeof r}function isNull(r){return null===r}function isNullOrUndefined(r){return null==r}function isNumber(r){return"number"==typeof r}function isString(r){return"string"==typeof r}function isSymbol(r){return"symbol"==typeof r}function isUndefined(r){return void 0===r}function isRegExp(r){return"[object RegExp]"===objectToString(r)}function isObject(r){return"object"==typeof r&&null!==r}function isDate(r){return"[object Date]"===objectToString(r)}function isError(r){return"[object Error]"===objectToString(r)||r instanceof Error}function isFunction(r){return"function"==typeof r}function isPrimitive(r){return null===r||"boolean"==typeof r||"number"==typeof r||"string"==typeof r||"symbol"==typeof r||void 0===r}function objectToString(r){return Object.prototype.toString.call(r)}exports.isArray=isArray,exports.isBoolean=isBoolean,exports.isNull=isNull,exports.isNullOrUndefined=isNullOrUndefined,exports.isNumber=isNumber,exports.isString=isString,exports.isSymbol=isSymbol,exports.isUndefined=isUndefined,exports.isRegExp=isRegExp,exports.isObject=isObject,exports.isDate=isDate,exports.isError=isError,exports.isFunction=isFunction,exports.isPrimitive=isPrimitive,exports.isBuffer=Buffer.isBuffer;
/* WEBPACK VAR INJECTION */}.call(exports, __webpack_require__(38).Buffer))

/***/ }),
/* 48 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
Object.defineProperty(exports,"__esModule",{value:!0});var binding_when_syntax_1=__webpack_require__(49),BindingOnSyntax=function(){function n(n){this._binding=n}return n.prototype.onActivation=function(n){return this._binding.onActivation=n,new binding_when_syntax_1.BindingWhenSyntax(this._binding)},n}();exports.BindingOnSyntax=BindingOnSyntax;

/***/ }),
/* 49 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
Object.defineProperty(exports,"__esModule",{value:!0});var binding_on_syntax_1=__webpack_require__(48),constraint_helpers_1=__webpack_require__(77),BindingWhenSyntax=function(){function n(n){this._binding=n}return n.prototype.when=function(n){return this._binding.constraint=n,new binding_on_syntax_1.BindingOnSyntax(this._binding)},n.prototype.whenTargetNamed=function(n){return this._binding.constraint=constraint_helpers_1.namedConstraint(n),new binding_on_syntax_1.BindingOnSyntax(this._binding)},n.prototype.whenTargetIsDefault=function(){return this._binding.constraint=function(n){return null!==n.target&&!1===n.target.isNamed()&&!1===n.target.isTagged()},new binding_on_syntax_1.BindingOnSyntax(this._binding)},n.prototype.whenTargetTagged=function(n,t){return this._binding.constraint=constraint_helpers_1.taggedConstraint(n)(t),new binding_on_syntax_1.BindingOnSyntax(this._binding)},n.prototype.whenInjectedInto=function(n){return this._binding.constraint=function(t){return constraint_helpers_1.typeConstraint(n)(t.parentRequest)},new binding_on_syntax_1.BindingOnSyntax(this._binding)},n.prototype.whenParentNamed=function(n){return this._binding.constraint=function(t){return constraint_helpers_1.namedConstraint(n)(t.parentRequest)},new binding_on_syntax_1.BindingOnSyntax(this._binding)},n.prototype.whenParentTagged=function(n,t){return this._binding.constraint=function(i){return constraint_helpers_1.taggedConstraint(n)(t)(i.parentRequest)},new binding_on_syntax_1.BindingOnSyntax(this._binding)},n.prototype.whenAnyAncestorIs=function(n){return this._binding.constraint=function(t){return constraint_helpers_1.traverseAncerstors(t,constraint_helpers_1.typeConstraint(n))},new binding_on_syntax_1.BindingOnSyntax(this._binding)},n.prototype.whenNoAncestorIs=function(n){return this._binding.constraint=function(t){return!constraint_helpers_1.traverseAncerstors(t,constraint_helpers_1.typeConstraint(n))},new binding_on_syntax_1.BindingOnSyntax(this._binding)},n.prototype.whenAnyAncestorNamed=function(n){return this._binding.constraint=function(t){return constraint_helpers_1.traverseAncerstors(t,constraint_helpers_1.namedConstraint(n))},new binding_on_syntax_1.BindingOnSyntax(this._binding)},n.prototype.whenNoAncestorNamed=function(n){return this._binding.constraint=function(t){return!constraint_helpers_1.traverseAncerstors(t,constraint_helpers_1.namedConstraint(n))},new binding_on_syntax_1.BindingOnSyntax(this._binding)},n.prototype.whenAnyAncestorTagged=function(n,t){return this._binding.constraint=function(i){return constraint_helpers_1.traverseAncerstors(i,constraint_helpers_1.taggedConstraint(n)(t))},new binding_on_syntax_1.BindingOnSyntax(this._binding)},n.prototype.whenNoAncestorTagged=function(n,t){return this._binding.constraint=function(i){return!constraint_helpers_1.traverseAncerstors(i,constraint_helpers_1.taggedConstraint(n)(t))},new binding_on_syntax_1.BindingOnSyntax(this._binding)},n.prototype.whenAnyAncestorMatches=function(n){return this._binding.constraint=function(t){return constraint_helpers_1.traverseAncerstors(t,n)},new binding_on_syntax_1.BindingOnSyntax(this._binding)},n.prototype.whenNoAncestorMatches=function(n){return this._binding.constraint=function(t){return!constraint_helpers_1.traverseAncerstors(t,n)},new binding_on_syntax_1.BindingOnSyntax(this._binding)},n}();exports.BindingWhenSyntax=BindingWhenSyntax;

/***/ }),
/* 50 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
function addNS(e,i,r){if(e.ns="http://www.w3.org/2000/svg","foreignObject"!==r&&void 0!==i)for(var d=0;d<i.length;++d){var o=i[d].data;void 0!==o&&addNS(o,i[d].children,i[d].sel)}}function h(e,i,r){var d,o,v,t={};if(void 0!==r?(t=i,is.array(r)?d=r:is.primitive(r)?o=r:r&&r.sel&&(d=[r])):void 0!==i&&(is.array(i)?d=i:is.primitive(i)?o=i:i&&i.sel?d=[i]:t=i),is.array(d))for(v=0;v<d.length;++v)is.primitive(d[v])&&(d[v]=vnode_1.vnode(void 0,void 0,void 0,d[v]));return"s"!==e[0]||"v"!==e[1]||"g"!==e[2]||3!==e.length&&"."!==e[3]&&"#"!==e[3]||addNS(t,d,e),vnode_1.vnode(e,t,d,o,void 0)}Object.defineProperty(exports,"__esModule",{value:!0});var vnode_1=__webpack_require__(51),is=__webpack_require__(78);exports.h=h,exports.default=h;

/***/ }),
/* 51 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
function vnode(e,t,o,d,n){return{sel:e,data:t,children:o,text:d,elm:n,key:void 0===t?void 0:t.key}}Object.defineProperty(exports,"__esModule",{value:!0}),exports.vnode=vnode,exports.default=vnode;

/***/ }),
/* 52 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
var __decorate=this&&this.__decorate||function(t,e,n,i){var o,r=arguments.length,a=r<3?e:null===i?i=Object.getOwnPropertyDescriptor(e,n):i;if("object"==typeof Reflect&&"function"==typeof Reflect.decorate)a=Reflect.decorate(t,e,n,i);else for(var c=t.length-1;c>=0;c--)(o=t[c])&&(a=(r<3?o(a):r>3?o(e,n,a):o(e,n))||a);return r>3&&a&&Object.defineProperty(e,n,a),a},__metadata=this&&this.__metadata||function(t,e){if("object"==typeof Reflect&&"function"==typeof Reflect.metadata)return Reflect.metadata(t,e)},__param=this&&this.__param||function(t,e){return function(n,i){e(n,i,t)}};Object.defineProperty(exports,"__esModule",{value:!0});var inversify_1=__webpack_require__(0),types_1=__webpack_require__(1),smodel_factory_1=__webpack_require__(10),animation_frame_syncer_1=__webpack_require__(30),set_model_1=__webpack_require__(31),undo_redo_1=__webpack_require__(63),action_1=__webpack_require__(53),action_handler_1=__webpack_require__(23),ActionDispatcher=function(){function t(t,e,n,i){this.actionHandlerRegistry=t,this.commandStack=e,this.logger=n,this.syncer=i,this.postponedActions=[],this.postponedActions=[];var o=new set_model_1.SetModelCommand(new set_model_1.SetModelAction(smodel_factory_1.EMPTY_ROOT));this.blockUntil=o.blockUntil,this.commandStack.execute(o)}return t.prototype.dispatchAll=function(t){var e=this;return Promise.all(t.map(function(t){return e.dispatch(t)}))},t.prototype.dispatch=function(t){return void 0!==this.blockUntil?this.handleBlocked(t,this.blockUntil):t.kind===undo_redo_1.UndoAction.KIND?this.commandStack.undo().then(function(){}):t.kind===undo_redo_1.RedoAction.KIND?this.commandStack.redo().then(function(){}):this.handleAction(t)},t.prototype.handleAction=function(t){this.logger.log(this,"handle",t);var e=this.actionHandlerRegistry.get(t.kind);if(e.length>0){for(var n=[],i=0,o=e;i<o.length;i++){var r=o[i],a=r.handle(t);action_1.isAction(a)?n.push(this.dispatch(a)):void 0!==a&&(n.push(this.commandStack.execute(a)),this.blockUntil=a.blockUntil)}return Promise.all(n)}return this.logger.warn(this,"Missing handler for action",t),Promise.reject("Missing handler for action '"+t.kind+"'")},t.prototype.handleBlocked=function(t,e){var n=this;if(e(t)){this.blockUntil=void 0;var i=this.handleAction(t),o=this.postponedActions;this.postponedActions=[];for(var r=0,a=o;r<a.length;r++){var c=a[r];this.dispatch(c.action).then(c.resolve,c.reject)}return i}return this.logger.log(this,"Action is postponed due to block condition",t),new Promise(function(e,i){n.postponedActions.push({action:t,resolve:e,reject:i})})},t=__decorate([inversify_1.injectable(),__param(0,inversify_1.inject(types_1.TYPES.ActionHandlerRegistry)),__param(1,inversify_1.inject(types_1.TYPES.ICommandStack)),__param(2,inversify_1.inject(types_1.TYPES.ILogger)),__param(3,inversify_1.inject(types_1.TYPES.AnimationFrameSyncer)),__metadata("design:paramtypes",[action_handler_1.ActionHandlerRegistry,Object,Object,animation_frame_syncer_1.AnimationFrameSyncer])],t)}();exports.ActionDispatcher=ActionDispatcher;

/***/ }),
/* 53 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
function isAction(t){return void 0!==t&&t.hasOwnProperty("kind")&&"string"==typeof t.kind}Object.defineProperty(exports,"__esModule",{value:!0}),exports.isAction=isAction;

/***/ }),
/* 54 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
var __decorate=this&&this.__decorate||function(e,t,o,r){var a,i=arguments.length,c=i<3?t:null===r?r=Object.getOwnPropertyDescriptor(t,o):r;if("object"==typeof Reflect&&"function"==typeof Reflect.decorate)c=Reflect.decorate(e,t,o,r);else for(var n=e.length-1;n>=0;n--)(a=e[n])&&(c=(i<3?a(c):i>3?a(t,o,c):a(t,o))||c);return i>3&&c&&Object.defineProperty(t,o,c),c},__metadata=this&&this.__metadata||function(e,t){if("object"==typeof Reflect&&"function"==typeof Reflect.metadata)return Reflect.metadata(e,t)};Object.defineProperty(exports,"__esModule",{value:!0});var inversify_1=__webpack_require__(0),types_1=__webpack_require__(1),smodel_factory_1=__webpack_require__(10),SModelStorage=function(){function e(){this.localCache=new Map}return e.prototype.store=function(e){this.isLocalStorageAvailable()?localStorage.setItem(this.key,JSON.stringify(e)):this.localCache.set(this.key,JSON.stringify(e))},e.prototype.load=function(){var e=this.isLocalStorageAvailable()?localStorage.getItem(this.key):this.localCache.get(this.key);return e?JSON.parse(e):smodel_factory_1.EMPTY_ROOT},e.prototype.isLocalStorageAvailable=function(){try{return"object"==typeof localStorage&&null!==localStorage}catch(e){return!1}},Object.defineProperty(e.prototype,"key",{get:function(){return this.viewerOptions.baseDiv},enumerable:!0,configurable:!0}),__decorate([inversify_1.inject(types_1.TYPES.ViewerOptions),__metadata("design:type",Object)],e.prototype,"viewerOptions",void 0),e=__decorate([inversify_1.injectable()],e)}();exports.SModelStorage=SModelStorage;

/***/ }),
/* 55 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
var __decorate=this&&this.__decorate||function(e,t,i,r){var n,o=arguments.length,a=o<3?t:null===r?r=Object.getOwnPropertyDescriptor(t,i):r;if("object"==typeof Reflect&&"function"==typeof Reflect.decorate)a=Reflect.decorate(e,t,i,r);else for(var s=e.length-1;s>=0;s--)(n=e[s])&&(a=(o<3?n(a):o>3?n(t,i,a):n(t,i))||a);return o>3&&a&&Object.defineProperty(t,i,a),a},__metadata=this&&this.__metadata||function(e,t){if("object"==typeof Reflect&&"function"==typeof Reflect.metadata)return Reflect.metadata(e,t)},__param=this&&this.__param||function(e,t){return function(i,r){t(i,r,e)}};Object.defineProperty(exports,"__esModule",{value:!0});var inversify_1=__webpack_require__(0),types_1=__webpack_require__(1),DOMHelper=function(){function e(e){this.viewerOptions=e}return e.prototype.getPrefix=function(){return void 0!==this.viewerOptions&&void 0!==this.viewerOptions.baseDiv?this.viewerOptions.baseDiv+"_":""},e.prototype.createUniqueDOMElementId=function(e){return this.getPrefix()+e.id},e.prototype.findSModelIdByDOMElement=function(e){return e.id.replace(this.getPrefix(),"")},e=__decorate([inversify_1.injectable(),__param(0,inversify_1.inject(types_1.TYPES.ViewerOptions)),__metadata("design:paramtypes",[Object])],e)}();exports.DOMHelper=DOMHelper;

/***/ }),
/* 56 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
Object.defineProperty(exports,"__esModule",{value:!0});var geometry_1=__webpack_require__(3),smodel_1=__webpack_require__(2),model_1=__webpack_require__(7),AbstractLayout=function(){function t(){}return t.prototype.layout=function(t,e){var o=e.getBoundsData(t),i=this.getLayoutOptions(t),n=this.getChildrenSize(t,i,e),r=i.paddingFactor*(i.resizeContainer?n.width:Math.max(0,this.getFixedContainerBounds(t,i,e).width)-i.paddingLeft-i.paddingRight),a=i.paddingFactor*(i.resizeContainer?n.height:Math.max(0,this.getFixedContainerBounds(t,i,e).height)-i.paddingTop-i.paddingBottom);if(r>0&&a>0){var d=this.layoutChildren(t,e,i,r,a);o.bounds=this.getFinalContainerBounds(t,d,i,r,a),o.boundsChanged=!0}},t.prototype.getFinalContainerBounds=function(t,e,o,i,n){return{x:t.bounds.x,y:t.bounds.y,width:i+o.paddingLeft+o.paddingRight,height:n+o.paddingTop+o.paddingBottom}},t.prototype.getFixedContainerBounds=function(t,e,o){for(var i=t;;){if(model_1.isBoundsAware(i)){var n=i.bounds;if(model_1.isLayoutContainer(i)&&e.resizeContainer&&o.log.error(i,"Resizable container found while detecting fixed bounds"),geometry_1.isValidDimension(n))return n}if(!(i instanceof smodel_1.SChildElement))return o.log.error(i,"Cannot detect fixed bounds"),geometry_1.EMPTY_BOUNDS;i=i.parent}},t.prototype.layoutChildren=function(t,e,o,i,n){var r=this,a={x:o.paddingLeft+.5*(i-i/o.paddingFactor),y:o.paddingTop+.5*(n-n/o.paddingFactor)};return t.children.forEach(function(t){if(model_1.isLayoutableChild(t)){var d=e.getBoundsData(t),s=d.bounds,u=r.getChildLayoutOptions(t,o);void 0!==s&&geometry_1.isValidDimension(s)&&(a=r.layoutChild(t,d,s,u,o,a,i,n))}}),a},t.prototype.getDx=function(t,e,o){switch(t){case"left":return 0;case"center":return.5*(o-e.width);case"right":return o-e.width}},t.prototype.getDy=function(t,e,o){switch(t){case"top":return 0;case"center":return.5*(o-e.height);case"bottom":return o-e.height}},t.prototype.getChildLayoutOptions=function(t,e){var o=t.layoutOptions;return void 0===o?e:this.spread(e,o)},t.prototype.getLayoutOptions=function(t){for(var e=this,o=t,i=[];void 0!==o;){var n=o.layoutOptions;if(void 0!==n&&i.push(n),!(o instanceof smodel_1.SChildElement))break;o=o.parent}return i.reverse().reduce(function(t,o){return e.spread(t,o)},this.getDefaultLayoutOptions())},t}();exports.AbstractLayout=AbstractLayout;

/***/ }),
/* 57 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
var __extends=this&&this.__extends||function(){var t=Object.setPrototypeOf||{__proto__:[]}instanceof Array&&function(t,e){t.__proto__=e}||function(t,e){for(var o in e)e.hasOwnProperty(o)&&(t[o]=e[o])};return function(e,o){function r(){this.constructor=e}t(e,o),e.prototype=null===o?Object.create(o):(r.prototype=o.prototype,new r)}}(),__decorate=this&&this.__decorate||function(t,e,o,r){var a,u=arguments.length,i=u<3?e:null===r?r=Object.getOwnPropertyDescriptor(e,o):r;if("object"==typeof Reflect&&"function"==typeof Reflect.decorate)i=Reflect.decorate(t,e,o,r);else for(var n=t.length-1;n>=0;n--)(a=t[n])&&(i=(u<3?a(i):u>3?a(e,o,i):a(e,o))||i);return u>3&&i&&Object.defineProperty(e,o,i),i},__metadata=this&&this.__metadata||function(t,e){if("object"==typeof Reflect&&"function"==typeof Reflect.metadata)return Reflect.metadata(t,e)},__param=this&&this.__param||function(t,e){return function(o,r){e(o,r,t)}};Object.defineProperty(exports,"__esModule",{value:!0});var inversify_1=__webpack_require__(0),types_1=__webpack_require__(1),registry_1=__webpack_require__(26),geometry_1=__webpack_require__(3),model_1=__webpack_require__(7),vbox_layout_1=__webpack_require__(90),hbox_layout_1=__webpack_require__(88),stack_layout_1=__webpack_require__(89),LayoutRegistry=function(t){function e(){var e=t.call(this)||this;return e.register(vbox_layout_1.VBoxLayouter.KIND,new vbox_layout_1.VBoxLayouter),e.register(hbox_layout_1.HBoxLayouter.KIND,new hbox_layout_1.HBoxLayouter),e.register(stack_layout_1.StackLayouter.KIND,new stack_layout_1.StackLayouter),e}return __extends(e,t),e}(registry_1.InstanceRegistry);exports.LayoutRegistry=LayoutRegistry;var Layouter=function(){function t(t,e){this.layoutRegistry=t,this.logger=e}return t.prototype.layout=function(t){new StatefulLayouter(t,this.layoutRegistry,this.logger).layout()},t=__decorate([inversify_1.injectable(),__param(0,inversify_1.inject(types_1.TYPES.LayoutRegistry)),__param(1,inversify_1.inject(types_1.TYPES.ILogger)),__metadata("design:paramtypes",[LayoutRegistry,Object])],t)}();exports.Layouter=Layouter;var StatefulLayouter=function(){function t(t,e,o){var r=this;this.element2boundsData=t,this.layoutRegistry=e,this.log=o,this.toBeLayouted=[],t.forEach(function(t,e){model_1.isLayoutContainer(e)&&r.toBeLayouted.push(e)})}return t.prototype.getBoundsData=function(t){var e=this.element2boundsData.get(t),o=t.bounds;return model_1.isLayoutContainer(t)&&this.toBeLayouted.indexOf(t)>=0&&(o=this.doLayout(t)),e||(e={bounds:o,boundsChanged:!1,alignmentChanged:!1},this.element2boundsData.set(t,e)),e},t.prototype.layout=function(){for(;this.toBeLayouted.length>0;){var t=this.toBeLayouted[0];this.doLayout(t)}},t.prototype.doLayout=function(t){var e=this.toBeLayouted.indexOf(t);e>=0&&this.toBeLayouted.splice(e,1);var o=this.layoutRegistry.get(t.layout);o&&o.layout(t,this);var r=this.element2boundsData.get(t);return void 0!==r&&void 0!==r.bounds?r.bounds:(this.log.error(t,"Layout failed"),geometry_1.EMPTY_BOUNDS)},t}();exports.StatefulLayouter=StatefulLayouter;

/***/ }),
/* 58 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
var __extends=this&&this.__extends||function(){var t=Object.setPrototypeOf||{__proto__:[]}instanceof Array&&function(t,e){t.__proto__=e}||function(t,e){for(var r in e)e.hasOwnProperty(r)&&(t[r]=e[r])};return function(e,r){function n(){this.constructor=e}t(e,r),e.prototype=null===r?Object.create(r):(n.prototype=r.prototype,new n)}}(),__decorate=this&&this.__decorate||function(t,e,r,n){var i,o=arguments.length,a=o<3?e:null===n?n=Object.getOwnPropertyDescriptor(e,r):n;if("object"==typeof Reflect&&"function"==typeof Reflect.decorate)a=Reflect.decorate(t,e,r,n);else for(var s=t.length-1;s>=0;s--)(i=t[s])&&(a=(o<3?i(a):o>3?i(e,r,a):i(e,r))||a);return o>3&&a&&Object.defineProperty(e,r,a),a},__metadata=this&&this.__metadata||function(t,e){if("object"==typeof Reflect&&"function"==typeof Reflect.metadata)return Reflect.metadata(t,e)},__param=this&&this.__param||function(t,e){return function(r,n){e(r,n,t)}};Object.defineProperty(exports,"__esModule",{value:!0});var registry_1=__webpack_require__(26),inversify_1=__webpack_require__(0),types_1=__webpack_require__(1),ButtonHandlerRegistry=function(t){function e(e){var r=t.call(this)||this;return e.forEach(function(t){return r.register(t.TYPE,new t)}),r}return __extends(e,t),e=__decorate([inversify_1.injectable(),__param(0,inversify_1.multiInject(types_1.TYPES.IButtonHandler)),__param(0,inversify_1.optional()),__metadata("design:paramtypes",[Array])],e)}(registry_1.InstanceRegistry);exports.ButtonHandlerRegistry=ButtonHandlerRegistry;

/***/ }),
/* 59 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
var __extends=this&&this.__extends||function(){var e=Object.setPrototypeOf||{__proto__:[]}instanceof Array&&function(e,t){e.__proto__=t}||function(e,t){for(var o in t)t.hasOwnProperty(o)&&(e[o]=t[o])};return function(t,o){function r(){this.constructor=t}e(t,o),t.prototype=null===o?Object.create(o):(r.prototype=o.prototype,new r)}}();Object.defineProperty(exports,"__esModule",{value:!0});var model_1=__webpack_require__(7),model_2=__webpack_require__(33),SButton=function(e){function t(){var t=null!==e&&e.apply(this,arguments)||this;return t.enabled=!0,t}return __extends(t,e),t.prototype.hasFeature=function(e){return e===model_1.boundsFeature||e===model_2.fadeFeature||e===model_1.layoutableChildFeature},t}(model_1.SShapeElement);exports.SButton=SButton;

/***/ }),
/* 60 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
function isExpandable(e){return e.hasFeature(exports.expandFeature)&&"expanded"in e}Object.defineProperty(exports,"__esModule",{value:!0}),exports.expandFeature=Symbol("expandFeature"),exports.isExpandable=isExpandable;

/***/ }),
/* 61 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
var __extends=this&&this.__extends||function(){var e=Object.setPrototypeOf||{__proto__:[]}instanceof Array&&function(e,t){e.__proto__=t}||function(e,t){for(var n in t)t.hasOwnProperty(n)&&(e[n]=t[n])};return function(t,n){function o(){this.constructor=t}e(t,n),t.prototype=null===n?Object.create(n):(o.prototype=n.prototype,new o)}}(),__decorate=this&&this.__decorate||function(e,t,n,o){var r,i=arguments.length,a=i<3?t:null===o?o=Object.getOwnPropertyDescriptor(t,n):o;if("object"==typeof Reflect&&"function"==typeof Reflect.decorate)a=Reflect.decorate(e,t,n,o);else for(var s=e.length-1;s>=0;s--)(r=e[s])&&(a=(i<3?r(a):i>3?r(t,n,a):r(t,n))||a);return i>3&&a&&Object.defineProperty(t,n,a),a};Object.defineProperty(exports,"__esModule",{value:!0});var inversify_1=__webpack_require__(0),animation_1=__webpack_require__(19),smodel_1=__webpack_require__(2),vnode_utils_1=__webpack_require__(11),model_1=__webpack_require__(33),FadeAnimation=function(e){function t(t,n,o,r){void 0===r&&(r=!1);var i=e.call(this,o)||this;return i.model=t,i.elementFades=n,i.removeAfterFadeOut=r,i}return __extends(t,e),t.prototype.tween=function(e,t){for(var n=0,o=this.elementFades;n<o.length;n++){var r=o[n],i=r.element;"in"===r.type?i.opacity=e:"out"===r.type&&(i.opacity=1-e,1===e&&this.removeAfterFadeOut&&i instanceof smodel_1.SChildElement&&i.parent.remove(i))}return this.model},t}(animation_1.Animation);exports.FadeAnimation=FadeAnimation;var ElementFader=function(){function e(){}return e.prototype.decorate=function(e,t){return model_1.isFadeable(t)&&vnode_utils_1.setAttr(e,"opacity",t.opacity),e},e.prototype.postUpdate=function(){},e=__decorate([inversify_1.injectable()],e)}();exports.ElementFader=ElementFader;

/***/ }),
/* 62 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
var __extends=this&&this.__extends||function(){var e=Object.setPrototypeOf||{__proto__:[]}instanceof Array&&function(e,t){e.__proto__=t}||function(e,t){for(var o in t)t.hasOwnProperty(o)&&(e[o]=t[o])};return function(t,o){function n(){this.constructor=t}e(t,o),t.prototype=null===o?Object.create(o):(n.prototype=o.prototype,new n)}}();Object.defineProperty(exports,"__esModule",{value:!0});var mouse_tool_1=__webpack_require__(15),smodel_utils_1=__webpack_require__(6),model_1=__webpack_require__(92),OpenAction=function(){function e(t){this.elementId=t,this.kind=e.KIND}return e.KIND="open",e}();exports.OpenAction=OpenAction;var OpenMouseListener=function(e){function t(){return null!==e&&e.apply(this,arguments)||this}return __extends(t,e),t.prototype.doubleClick=function(e,t){var o=smodel_utils_1.findParentByFeature(e,model_1.isOpenable);return void 0!==o?[new OpenAction(o.id)]:[]},t}(mouse_tool_1.MouseListener);exports.OpenMouseListener=OpenMouseListener;

/***/ }),
/* 63 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
var __extends=this&&this.__extends||function(){var e=Object.setPrototypeOf||{__proto__:[]}instanceof Array&&function(e,t){e.__proto__=t}||function(e,t){for(var o in t)t.hasOwnProperty(o)&&(e[o]=t[o])};return function(t,o){function n(){this.constructor=t}e(t,o),t.prototype=null===o?Object.create(o):(n.prototype=o.prototype,new n)}}();Object.defineProperty(exports,"__esModule",{value:!0});var keyboard_1=__webpack_require__(36),key_tool_1=__webpack_require__(20),UndoAction=function(){function e(){this.kind=e.KIND}return e.KIND="undo",e}();exports.UndoAction=UndoAction;var RedoAction=function(){function e(){this.kind=e.KIND}return e.KIND="redo",e}();exports.RedoAction=RedoAction;var UndoRedoKeyListener=function(e){function t(){return null!==e&&e.apply(this,arguments)||this}return __extends(t,e),t.prototype.keyDown=function(e,t){return keyboard_1.matchesKeystroke(t,"KeyZ","ctrlCmd")?[new UndoAction]:keyboard_1.matchesKeystroke(t,"KeyZ","ctrlCmd","shift")?[new RedoAction]:[]},t}(key_tool_1.KeyListener);exports.UndoRedoKeyListener=UndoRedoKeyListener;

/***/ }),
/* 64 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
function forEachMatch(t,e){for(var r in t)t.hasOwnProperty(r)&&e(r,t[r])}function applyMatches(t,e){var r;t instanceof smodel_1.SModelRoot?r=t.index:(r=new smodel_1.SModelIndex,r.add(t));for(var i=0,d=e;i<d.length;i++){var a=d[i],o=!1;if(void 0!==a.left&&void 0!==a.leftParentId){var h=r.getById(a.leftParentId);if(void 0!==h&&void 0!==h.children){var n=h.children.indexOf(a.left);n>=0&&(void 0!==a.right&&a.leftParentId===a.rightParentId?(h.children.splice(n,1,a.right),o=!0):h.children.splice(n,1)),r.remove(a.left)}}if(!o&&void 0!==a.right&&void 0!==a.rightParentId){var l=r.getById(a.rightParentId);void 0!==l&&(void 0===l.children&&(l.children=[]),l.children.push(a.right))}}}Object.defineProperty(exports,"__esModule",{value:!0});var smodel_1=__webpack_require__(2);exports.forEachMatch=forEachMatch;var ModelMatcher=function(){function t(){}return t.prototype.match=function(t,e){var r={};return this.matchLeft(t,r),this.matchRight(e,r),r},t.prototype.matchLeft=function(t,e,r){var i=e[t.id];if(void 0!==i?(i.left=t,i.leftParentId=r):(i={left:t,leftParentId:r},e[t.id]=i),smodel_1.isParent(t))for(var d=0,a=t.children;d<a.length;d++){var o=a[d];this.matchLeft(o,e,t.id)}},t.prototype.matchRight=function(t,e,r){var i=e[t.id];if(void 0!==i?(i.right=t,i.rightParentId=r):(i={right:t,rightParentId:r},e[t.id]=i),smodel_1.isParent(t))for(var d=0,a=t.children;d<a.length;d++){var o=a[d];this.matchRight(o,e,t.id)}},t}();exports.ModelMatcher=ModelMatcher,exports.applyMatches=applyMatches;

/***/ }),
/* 65 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
var __extends=this&&this.__extends||function(){var e=Object.setPrototypeOf||{__proto__:[]}instanceof Array&&function(e,t){e.__proto__=t}||function(e,t){for(var o in t)t.hasOwnProperty(o)&&(e[o]=t[o])};return function(t,o){function i(){this.constructor=t}e(t,o),t.prototype=null===o?Object.create(o):(i.prototype=o.prototype,new i)}}(),__decorate=this&&this.__decorate||function(e,t,o,i){var n,a=arguments.length,r=a<3?t:null===i?i=Object.getOwnPropertyDescriptor(t,o):i;if("object"==typeof Reflect&&"function"==typeof Reflect.decorate)r=Reflect.decorate(e,t,o,i);else for(var d=e.length-1;d>=0;d--)(n=e[d])&&(r=(a<3?n(r):a>3?n(t,o,r):n(t,o))||r);return a>3&&r&&Object.defineProperty(t,o,r),r},__metadata=this&&this.__metadata||function(e,t){if("object"==typeof Reflect&&"function"==typeof Reflect.metadata)return Reflect.metadata(e,t)};Object.defineProperty(exports,"__esModule",{value:!0});var inversify_1=__webpack_require__(0),geometry_1=__webpack_require__(3),animation_1=__webpack_require__(19),command_1=__webpack_require__(5),fade_1=__webpack_require__(61),smodel_1=__webpack_require__(2),move_1=__webpack_require__(45),model_1=__webpack_require__(33),model_2=__webpack_require__(22),model_3=__webpack_require__(7),viewport_root_1=__webpack_require__(67),model_4=__webpack_require__(12),model_matching_1=__webpack_require__(64),resize_1=__webpack_require__(158),UpdateModelAction=function(){function e(e,t){void 0===t&&(t=!0),this.animate=t,this.kind=UpdateModelCommand.KIND,void 0!==e.id?this.newRoot=e:this.matches=e}return e}();exports.UpdateModelAction=UpdateModelAction;var UpdateModelCommand=function(e){function t(t){var o=e.call(this)||this;return o.action=t,o}return __extends(t,e),t.prototype.execute=function(e){var t;return void 0!==this.action.newRoot?t=e.modelFactory.createRoot(this.action.newRoot):(t=e.modelFactory.createRoot(e.root),void 0!==this.action.matches&&this.applyMatches(t,this.action.matches,e)),this.oldRoot=e.root,this.newRoot=t,this.performUpdate(this.oldRoot,this.newRoot,e)},t.prototype.performUpdate=function(e,t,o){if(void 0!==this.action.animate&&!this.action.animate||e.id!==t.id)return e.type===t.type&&geometry_1.isValidDimension(e.canvasBounds)&&(t.canvasBounds=e.canvasBounds),t;var i=void 0;if(void 0===this.action.matches){i=(new model_matching_1.ModelMatcher).match(e,t)}else i=this.convertToMatchResult(this.action.matches,e,t);var n=this.computeAnimation(t,i,o);return n instanceof animation_1.Animation?n.start():n},t.prototype.applyMatches=function(e,t,o){for(var i=e.index,n=0,a=t;n<a.length;n++){var r=a[n];if(void 0!==r.left){var d=i.getById(r.left.id);d instanceof smodel_1.SChildElement&&d.parent.remove(d)}if(void 0!==r.right){var d=o.modelFactory.createElement(r.right),s=void 0;void 0!==r.rightParentId&&(s=i.getById(r.rightParentId)),s instanceof smodel_1.SParentElement?s.add(d):e.add(d)}}},t.prototype.convertToMatchResult=function(e,t,o){for(var i={},n=0,a=e;n<a.length;n++){var r=a[n],d={},s=void 0;void 0!==r.left&&(s=r.left.id,d.left=t.index.getById(s),d.leftParentId=r.leftParentId),void 0!==r.right&&(s=r.right.id,d.right=o.index.getById(s),d.rightParentId=r.rightParentId),void 0!==s&&(i[s]=d)}return i},t.prototype.computeAnimation=function(e,t,o){var i=this,n={fades:[]};model_matching_1.forEachMatch(t,function(t,a){if(void 0!==a.left&&void 0!==a.right)i.updateElement(a.left,a.right,n);else if(void 0!==a.right){var r=a.right;model_1.isFadeable(r)&&(r.opacity=0,n.fades.push({element:r,type:"in"}))}else if(a.left instanceof smodel_1.SChildElement){var d=a.left;if(model_1.isFadeable(d)&&void 0!==a.leftParentId&&void 0===e.index.getById(d.id)){var s=e.index.getById(a.leftParentId);if(s instanceof smodel_1.SParentElement){var l=o.modelFactory.createElement(d);s.add(l),n.fades.push({element:l,type:"out"})}}}});var a=this.createAnimations(n,e,o);return a.length>=2?new animation_1.CompoundAnimation(e,o,a):1===a.length?a[0]:e},t.prototype.updateElement=function(e,t,o){if(model_2.isLocateable(e)&&model_2.isLocateable(t)){var i=e.position,n=t.position;geometry_1.almostEquals(i.x,n.x)&&geometry_1.almostEquals(i.y,n.y)||(void 0===o.moves&&(o.moves=[]),o.moves.push({element:t,elementId:t.id,fromPosition:i,toPosition:n}),t.position=i)}model_3.isBoundsAware(e)&&model_3.isBoundsAware(t)&&(geometry_1.isValidDimension(t.bounds)?geometry_1.almostEquals(e.bounds.width,t.bounds.width)&&geometry_1.almostEquals(e.bounds.height,t.bounds.height)||(void 0===o.resizes&&(o.resizes=[]),o.resizes.push({element:t,fromDimension:{width:e.bounds.width,height:e.bounds.height},toDimension:{width:t.bounds.width,height:t.bounds.height}})):t.bounds={x:t.bounds.x,y:t.bounds.y,width:e.bounds.width,height:e.bounds.height}),model_4.isSelectable(e)&&model_4.isSelectable(t)&&(t.selected=e.selected),e instanceof smodel_1.SModelRoot&&t instanceof smodel_1.SModelRoot&&(t.canvasBounds=e.canvasBounds),e instanceof viewport_root_1.ViewportRootElement&&t instanceof viewport_root_1.ViewportRootElement&&(t.scroll=e.scroll,t.zoom=e.zoom)},t.prototype.createAnimations=function(e,t,o){var i=[];if(e.fades.length>0&&i.push(new fade_1.FadeAnimation(t,e.fades,o,!0)),void 0!==e.moves&&e.moves.length>0){for(var n=new Map,a=0,r=e.moves;a<r.length;a++){var d=r[a];n.set(d.elementId,d)}i.push(new move_1.MoveAnimation(t,n,new Map,o,!1))}if(void 0!==e.resizes&&e.resizes.length>0){for(var s=new Map,l=0,m=e.resizes;l<m.length;l++){var c=m[l];s.set(c.element.id,c)}i.push(new resize_1.ResizeAnimation(t,s,o,!1))}return i},t.prototype.undo=function(e){return this.performUpdate(this.newRoot,this.oldRoot,e)},t.prototype.redo=function(e){return this.performUpdate(this.oldRoot,this.newRoot,e)},t.KIND="updateModel",t=__decorate([inversify_1.injectable(),__metadata("design:paramtypes",[UpdateModelAction])],t)}(command_1.Command);exports.UpdateModelCommand=UpdateModelCommand;

/***/ }),
/* 66 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
var __extends=this&&this.__extends||function(){var e=Object.setPrototypeOf||{__proto__:[]}instanceof Array&&function(e,t){e.__proto__=t}||function(e,t){for(var o in t)t.hasOwnProperty(o)&&(e[o]=t[o])};return function(t,o){function n(){this.constructor=t}e(t,o),t.prototype=null===o?Object.create(o):(n.prototype=o.prototype,new n)}}();Object.defineProperty(exports,"__esModule",{value:!0});var geometry_1=__webpack_require__(3),keyboard_1=__webpack_require__(36),smodel_1=__webpack_require__(2),command_1=__webpack_require__(5),key_tool_1=__webpack_require__(20),model_1=__webpack_require__(7),model_2=__webpack_require__(12),viewport_1=__webpack_require__(25),model_3=__webpack_require__(16),CenterAction=function(){function e(e,t){void 0===t&&(t=!0),this.elementIds=e,this.animate=t,this.kind=CenterCommand.KIND}return e}();exports.CenterAction=CenterAction;var FitToScreenAction=function(){function e(e,t,o,n){void 0===n&&(n=!0),this.elementIds=e,this.padding=t,this.maxZoom=o,this.animate=n,this.kind=FitToScreenCommand.KIND}return e}();exports.FitToScreenAction=FitToScreenAction;var BoundsAwareViewportCommand=function(e){function t(t){var o=e.call(this)||this;return o.animate=t,o}return __extends(t,e),t.prototype.initialize=function(e){var t=this;if(model_3.isViewport(e)){this.oldViewport={scroll:e.scroll,zoom:e.zoom};var o=[];if(this.getElementIds().forEach(function(n){var i=e.index.getById(n);i&&model_1.isBoundsAware(i)&&o.push(t.boundsInViewport(i,i.bounds,e))}),0===o.length&&e.index.all().forEach(function(n){model_2.isSelectable(n)&&n.selected&&model_1.isBoundsAware(n)&&o.push(t.boundsInViewport(n,n.bounds,e))}),0===o.length&&e.index.all().forEach(function(n){model_1.isBoundsAware(n)&&o.push(t.boundsInViewport(n,n.bounds,e))}),0!==o.length){var n=o.reduce(function(e,t){return geometry_1.combine(e,t)});geometry_1.isValidDimension(n)&&(this.newViewport=this.getNewViewport(n,e))}}},t.prototype.boundsInViewport=function(e,t,o){return e instanceof smodel_1.SChildElement&&e.parent!==o?this.boundsInViewport(e.parent,e.parent.localToParent(t),o):t},t.prototype.execute=function(e){return this.initialize(e.root),this.redo(e)},t.prototype.undo=function(e){var t=e.root;if(model_3.isViewport(t)&&void 0!==this.newViewport&&!this.equal(this.newViewport,this.oldViewport)){if(this.animate)return new viewport_1.ViewportAnimation(t,this.newViewport,this.oldViewport,e).start();t.scroll=this.oldViewport.scroll,t.zoom=this.oldViewport.zoom}return t},t.prototype.redo=function(e){var t=e.root;if(model_3.isViewport(t)&&void 0!==this.newViewport&&!this.equal(this.newViewport,this.oldViewport)){if(this.animate)return new viewport_1.ViewportAnimation(t,this.oldViewport,this.newViewport,e).start();t.scroll=this.newViewport.scroll,t.zoom=this.newViewport.zoom}return t},t.prototype.equal=function(e,t){return e.zoom===t.zoom&&e.scroll.x===t.scroll.x&&e.scroll.y===t.scroll.y},t}(command_1.Command);exports.BoundsAwareViewportCommand=BoundsAwareViewportCommand;var CenterCommand=function(e){function t(t){var o=e.call(this,t.animate)||this;return o.action=t,o}return __extends(t,e),t.prototype.getElementIds=function(){return this.action.elementIds},t.prototype.getNewViewport=function(e,t){if(geometry_1.isValidDimension(t.canvasBounds)){var o=geometry_1.center(e);return{scroll:{x:o.x-.5*t.canvasBounds.width,y:o.y-.5*t.canvasBounds.height},zoom:1}}},t.KIND="center",t}(BoundsAwareViewportCommand);exports.CenterCommand=CenterCommand;var FitToScreenCommand=function(e){function t(t){var o=e.call(this,t.animate)||this;return o.action=t,o}return __extends(t,e),t.prototype.getElementIds=function(){return this.action.elementIds},t.prototype.getNewViewport=function(e,t){if(geometry_1.isValidDimension(t.canvasBounds)){var o=geometry_1.center(e),n=void 0===this.action.padding?0:2*this.action.padding,i=Math.min(t.canvasBounds.width/(e.width+n),t.canvasBounds.height/e.height+n);return void 0!==this.action.maxZoom&&(i=Math.min(i,this.action.maxZoom)),{scroll:{x:o.x-.5*t.canvasBounds.width/i,y:o.y-.5*t.canvasBounds.height/i},zoom:i}}},t.KIND="fit",t}(BoundsAwareViewportCommand);exports.FitToScreenCommand=FitToScreenCommand;var CenterKeyboardListener=function(e){function t(){return null!==e&&e.apply(this,arguments)||this}return __extends(t,e),t.prototype.keyDown=function(e,t){return keyboard_1.matchesKeystroke(t,"KeyC","ctrlCmd","shift")?[new CenterAction([])]:keyboard_1.matchesKeystroke(t,"KeyF","ctrlCmd","shift")?[new FitToScreenAction([])]:[]},t}(key_tool_1.KeyListener);exports.CenterKeyboardListener=CenterKeyboardListener;

/***/ }),
/* 67 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
var __extends=this&&this.__extends||function(){var t=Object.setPrototypeOf||{__proto__:[]}instanceof Array&&function(t,o){t.__proto__=o}||function(t,o){for(var e in o)o.hasOwnProperty(e)&&(t[e]=o[e])};return function(o,e){function r(){this.constructor=o}t(o,e),o.prototype=null===e?Object.create(e):(r.prototype=e.prototype,new r)}}();Object.defineProperty(exports,"__esModule",{value:!0});var geometry_1=__webpack_require__(3),smodel_1=__webpack_require__(2),model_1=__webpack_require__(16),model_2=__webpack_require__(44),ViewportRootElement=function(t){function o(o){var e=t.call(this,o)||this;return e.scroll={x:0,y:0},e.zoom=1,e.export=!1,e}return __extends(o,t),o.prototype.hasFeature=function(t){return t===model_1.viewportFeature||t===model_2.exportFeature},o.prototype.localToParent=function(t){var o={x:(t.x-this.scroll.x)*this.zoom,y:(t.y-this.scroll.y)*this.zoom,width:-1,height:-1};return geometry_1.isBounds(t)&&(o.width=t.width*this.zoom,o.height=t.height*this.zoom),o},o.prototype.parentToLocal=function(t){var o={x:t.x/this.zoom+this.scroll.x,y:t.y/this.zoom+this.scroll.y,width:-1,height:-1};return geometry_1.isBounds(t)&&geometry_1.isValidDimension(t)&&(o.width=t.width/this.zoom,o.height=t.height/this.zoom),o},o}(smodel_1.SModelRoot);exports.ViewportRootElement=ViewportRootElement;

/***/ }),
/* 68 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
var __decorate=this&&this.__decorate||function(e,t,r,i){var o,n=arguments.length,a=n<3?t:null===i?i=Object.getOwnPropertyDescriptor(t,r):i;if("object"==typeof Reflect&&"function"==typeof Reflect.decorate)a=Reflect.decorate(e,t,r,i);else for(var s=e.length-1;s>=0;s--)(o=e[s])&&(a=(n<3?o(a):n>3?o(t,r,a):o(t,r))||a);return n>3&&a&&Object.defineProperty(t,r,a),a},__metadata=this&&this.__metadata||function(e,t){if("object"==typeof Reflect&&"function"==typeof Reflect.metadata)return Reflect.metadata(e,t)},__param=this&&this.__param||function(e,t){return function(r,i){t(r,i,e)}};Object.defineProperty(exports,"__esModule",{value:!0});var inversify_1=__webpack_require__(0),types_1=__webpack_require__(1),action_handler_1=__webpack_require__(23),set_model_1=__webpack_require__(31),svg_exporter_1=__webpack_require__(24),ModelSource=function(){function e(e,t,r){this.actionDispatcher=e,this.viewerOptions=r,this.initialize(t)}return e.prototype.initialize=function(e){e.registerCommand(set_model_1.SetModelCommand),e.register(set_model_1.RequestModelAction.KIND,this),e.register(svg_exporter_1.ExportSvgAction.KIND,this)},e=__decorate([inversify_1.injectable(),__param(0,inversify_1.inject(types_1.TYPES.IActionDispatcher)),__param(1,inversify_1.inject(types_1.TYPES.ActionHandlerRegistry)),__param(2,inversify_1.inject(types_1.TYPES.ViewerOptions)),__metadata("design:paramtypes",[Object,action_handler_1.ActionHandlerRegistry,Object])],e)}();exports.ModelSource=ModelSource;

/***/ }),
/* 69 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
function isCtrlOrCmd(o){return isMac()?o.metaKey:o.ctrlKey}function isMac(){return-1!==window.navigator.userAgent.indexOf("Mac")}function isCrossSite(o){if(o&&"undefined"!=typeof window&&window.location){var t="";return window.location.protocol&&(t+=window.location.protocol+"//"),window.location.host&&(t+=window.location.host),t.length>0&&!o.startsWith(t)}return!1}Object.defineProperty(exports,"__esModule",{value:!0}),exports.isCtrlOrCmd=isCtrlOrCmd,exports.isMac=isMac,exports.isCrossSite=isCrossSite;

/***/ }),
/* 70 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
function toArray(t){if(t.constructor===Array)return t;var r=[];return t.forEach(function(t){return r.push(t)}),r}function filterIterable(t,r){return new FluentIterableImpl(function(){return createIterator(t)},function(t){var e;do{e=t.next()}while(!e.done&&!r(e.value));return e})}function mapIterable(t,r){return new FluentIterableImpl(function(){return createIterator(t)},function(t){var e=t.next(),n=e.done,o=e.value;return n?exports.DONE_RESULT:{done:!1,value:r(o)}})}function createIterator(t){var r=t[Symbol.iterator];if("function"==typeof r)return r.call(t);var e=t.length;return"number"==typeof e&&e>=0?new ArrayIterator(t):{next:function(){return exports.DONE_RESULT}}}Object.defineProperty(exports,"__esModule",{value:!0});var FluentIterableImpl=function(){function t(t,r){this.startFn=t,this.nextFn=r}return t.prototype[Symbol.iterator]=function(){var t=this,r=(e={state:this.startFn(),next:function(){return t.nextFn(r.state)}},e[Symbol.iterator]=function(){return r},e);return r;var e},t.prototype.filter=function(t){return filterIterable(this,t)},t.prototype.map=function(t){return mapIterable(this,t)},t.prototype.forEach=function(t){var r,e=this[Symbol.iterator](),n=0;do{r=e.next(),void 0!==r.value&&t(r.value,n),n++}while(!r.done)},t}();exports.FluentIterableImpl=FluentIterableImpl,exports.toArray=toArray,exports.DONE_RESULT=Object.freeze({done:!0,value:void 0}),exports.filterIterable=filterIterable,exports.mapIterable=mapIterable;var ArrayIterator=function(){function t(t){this.array=t,this.index=0}return t.prototype.next=function(){return this.index<this.array.length?{done:!1,value:this.array[this.index++]}:exports.DONE_RESULT},t.prototype[Symbol.iterator]=function(){return this},t}();

/***/ }),
/* 71 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
var __decorate=this&&this.__decorate||function(e,o,t,r){var n,i=arguments.length,l=i<3?o:null===r?r=Object.getOwnPropertyDescriptor(o,t):r;if("object"==typeof Reflect&&"function"==typeof Reflect.decorate)l=Reflect.decorate(e,o,t,r);else for(var a=e.length-1;a>=0;a--)(n=e[a])&&(l=(i<3?n(l):i>3?n(o,t,l):n(o,t))||l);return i>3&&l&&Object.defineProperty(o,t,l),l},__metadata=this&&this.__metadata||function(e,o){if("object"==typeof Reflect&&"function"==typeof Reflect.metadata)return Reflect.metadata(e,o)},__param=this&&this.__param||function(e,o){return function(t,r){o(t,r,e)}};Object.defineProperty(exports,"__esModule",{value:!0});var inversify_1=__webpack_require__(0),types_1=__webpack_require__(1),LogLevel;!function(e){e[e.none=0]="none",e[e.error=1]="error",e[e.warn=2]="warn",e[e.info=3]="info",e[e.log=4]="log"}(LogLevel=exports.LogLevel||(exports.LogLevel={}));var NullLogger=function(){function e(){this.logLevel=LogLevel.none}return e.prototype.error=function(e,o){for(var t=[],r=2;r<arguments.length;r++)t[r-2]=arguments[r]},e.prototype.warn=function(e,o){for(var t=[],r=2;r<arguments.length;r++)t[r-2]=arguments[r]},e.prototype.info=function(e,o){for(var t=[],r=2;r<arguments.length;r++)t[r-2]=arguments[r]},e.prototype.log=function(e,o){for(var t=[],r=2;r<arguments.length;r++)t[r-2]=arguments[r]},e=__decorate([inversify_1.injectable()],e)}();exports.NullLogger=NullLogger;var ConsoleLogger=function(){function e(e,o){void 0===e&&(e=LogLevel.log),void 0===o&&(o={baseDiv:""}),this.logLevel=e,this.viewOptions=o}return e.prototype.error=function(e,o){for(var t=[],r=2;r<arguments.length;r++)t[r-2]=arguments[r];if(this.logLevel>=LogLevel.error)try{console.error.apply(e,this.consoleArguments(e,o,t))}catch(e){}},e.prototype.warn=function(e,o){for(var t=[],r=2;r<arguments.length;r++)t[r-2]=arguments[r];if(this.logLevel>=LogLevel.warn)try{console.warn.apply(e,this.consoleArguments(e,o,t))}catch(e){}},e.prototype.info=function(e,o){for(var t=[],r=2;r<arguments.length;r++)t[r-2]=arguments[r];if(this.logLevel>=LogLevel.info)try{console.info.apply(e,this.consoleArguments(e,o,t))}catch(e){}},e.prototype.log=function(e,o){for(var t=[],r=2;r<arguments.length;r++)t[r-2]=arguments[r];if(this.logLevel>=LogLevel.log)try{console.log.apply(e,this.consoleArguments(e,o,t))}catch(e){}},e.prototype.consoleArguments=function(e,o,t){var r;return r="object"==typeof e?e.constructor.name:e,[(new Date).toLocaleTimeString()+" "+this.viewOptions.baseDiv+" "+r+": "+o].concat(t)},e=__decorate([inversify_1.injectable(),__param(0,inversify_1.inject(types_1.TYPES.LogLevel)),__param(1,inversify_1.inject(types_1.TYPES.ViewerOptions)),__metadata("design:paramtypes",[Number,Object])],e)}();exports.ConsoleLogger=ConsoleLogger;

/***/ }),
/* 72 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
/* WEBPACK VAR INJECTION */(function(process) {function nextTick(e,n,c,r){if("function"!=typeof e)throw new TypeError('"callback" argument must be a function');var s,t,o=arguments.length;switch(o){case 0:case 1:return process.nextTick(e);case 2:return process.nextTick(function(){e.call(null,n)});case 3:return process.nextTick(function(){e.call(null,n,c)});case 4:return process.nextTick(function(){e.call(null,n,c,r)});default:for(s=new Array(o-1),t=0;t<s.length;)s[t++]=arguments[t];return process.nextTick(function(){e.apply(null,s)})}}!process.version||0===process.version.indexOf("v0.")||0===process.version.indexOf("v1.")&&0!==process.version.indexOf("v1.8.")?module.exports=nextTick:module.exports=process.nextTick;
/* WEBPACK VAR INJECTION */}.call(exports, __webpack_require__(28)))

/***/ }),
/* 73 */
/***/ (function(module, exports, __webpack_require__) {

var __WEBPACK_AMD_DEFINE_RESULT__;var saveAs=saveAs||function(e){"use strict";if(!(void 0===e||"undefined"!=typeof navigator&&/MSIE [1-9]\./.test(navigator.userAgent))){var t=e.document,n=function(){return e.URL||e.webkitURL||e},o=t.createElementNS("http://www.w3.org/1999/xhtml","a"),r="download"in o,a=function(e){var t=new MouseEvent("click");e.dispatchEvent(t)},i=/constructor/i.test(e.HTMLElement)||e.safari,d=/CriOS\/[\d]+/.test(navigator.userAgent),f=function(t){(e.setImmediate||e.setTimeout)(function(){throw t},0)},s=function(e){var t=function(){"string"==typeof e?n().revokeObjectURL(e):e.remove()};setTimeout(t,4e4)},u=function(e,t,n){t=[].concat(t);for(var o=t.length;o--;){var r=e["on"+t[o]];if("function"==typeof r)try{r.call(e,n||e)}catch(e){f(e)}}},c=function(e){return/^\s*(?:text\/\S*|application\/xml|\S*\/\S*\+xml)\s*;.*charset\s*=\s*utf-8/i.test(e.type)?new Blob([String.fromCharCode(65279),e],{type:e.type}):e},l=function(t,f,l){l||(t=c(t));var v,p=this,w=t.type,m="application/octet-stream"===w,y=function(){u(p,"writestart progress write writeend".split(" "))};if(p.readyState=p.INIT,r)return v=n().createObjectURL(t),void setTimeout(function(){o.href=v,o.download=f,a(o),y(),s(v),p.readyState=p.DONE});!function(){if((d||m&&i)&&e.FileReader){var o=new FileReader;return o.onloadend=function(){var t=d?o.result:o.result.replace(/^data:[^;]*;/,"data:attachment/file;");e.open(t,"_blank")||(e.location.href=t),t=void 0,p.readyState=p.DONE,y()},o.readAsDataURL(t),void(p.readyState=p.INIT)}if(v||(v=n().createObjectURL(t)),m)e.location.href=v;else{e.open(v,"_blank")||(e.location.href=v)}p.readyState=p.DONE,y(),s(v)}()},v=l.prototype,p=function(e,t,n){return new l(e,t||e.name||"download",n)};return"undefined"!=typeof navigator&&navigator.msSaveOrOpenBlob?function(e,t,n){return t=t||e.name||"download",n||(e=c(e)),navigator.msSaveOrOpenBlob(e,t)}:(v.abort=function(){},v.readyState=v.INIT=0,v.WRITING=1,v.DONE=2,v.error=v.onwritestart=v.onprogress=v.onwrite=v.onabort=v.onerror=v.onwriteend=null,p)}}("undefined"!=typeof self&&self||"undefined"!=typeof window&&window||this.content);"undefined"!=typeof module&&module.exports?module.exports.saveAs=saveAs:"undefined"!="function"&&null!==__webpack_require__(192)&&null!==__webpack_require__(99)&&!(__WEBPACK_AMD_DEFINE_RESULT__ = function(){return saveAs}.call(exports, __webpack_require__, exports, module),
				__WEBPACK_AMD_DEFINE_RESULT__ !== undefined && (module.exports = __WEBPACK_AMD_DEFINE_RESULT__));

/***/ }),
/* 74 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
Object.defineProperty(exports,"__esModule",{value:!0});var METADATA_KEY=__webpack_require__(4),MetadataReader=function(){function e(){}return e.prototype.getConstructorMetadata=function(e){return{compilerGeneratedMetadata:Reflect.getMetadata(METADATA_KEY.PARAM_TYPES,e),userGeneratedMetadata:Reflect.getMetadata(METADATA_KEY.TAGGED,e)||{}}},e.prototype.getPropertiesMetadata=function(e){return Reflect.getMetadata(METADATA_KEY.TAGGED_PROP,e)||[]},e}();exports.MetadataReader=MetadataReader;

/***/ }),
/* 75 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
Object.defineProperty(exports,"__esModule",{value:!0});var metadata_1=__webpack_require__(9),queryable_string_1=__webpack_require__(129),guid_1=__webpack_require__(17),METADATA_KEY=__webpack_require__(4),Target=function(){function t(t,e,a,r){this.guid=guid_1.guid(),this.type=t,this.serviceIdentifier=a,this.name=new queryable_string_1.QueryableString(e||""),this.metadata=new Array;var A=null;"string"==typeof r?A=new metadata_1.Metadata(METADATA_KEY.NAMED_TAG,r):r instanceof metadata_1.Metadata&&(A=r),null!==A&&this.metadata.push(A)}return t.prototype.hasTag=function(t){for(var e=0;e<this.metadata.length;e++){if(this.metadata[e].key===t)return!0}return!1},t.prototype.isArray=function(){return this.hasTag(METADATA_KEY.MULTI_INJECT_TAG)},t.prototype.matchesArray=function(t){return this.matchesTag(METADATA_KEY.MULTI_INJECT_TAG)(t)},t.prototype.isNamed=function(){return this.hasTag(METADATA_KEY.NAMED_TAG)},t.prototype.isTagged=function(){return this.metadata.some(function(t){return t.key!==METADATA_KEY.INJECT_TAG&&t.key!==METADATA_KEY.MULTI_INJECT_TAG&&t.key!==METADATA_KEY.NAME_TAG&&t.key!==METADATA_KEY.UNMANAGED_TAG&&t.key!==METADATA_KEY.NAMED_TAG})},t.prototype.isOptional=function(){return this.matchesTag(METADATA_KEY.OPTIONAL_TAG)(!0)},t.prototype.getNamedTag=function(){return this.isNamed()?this.metadata.filter(function(t){return t.key===METADATA_KEY.NAMED_TAG})[0]:null},t.prototype.getCustomTags=function(){return this.isTagged()?this.metadata.filter(function(t){return t.key!==METADATA_KEY.INJECT_TAG&&t.key!==METADATA_KEY.MULTI_INJECT_TAG&&t.key!==METADATA_KEY.NAME_TAG&&t.key!==METADATA_KEY.UNMANAGED_TAG&&t.key!==METADATA_KEY.NAMED_TAG}):null},t.prototype.matchesNamedTag=function(t){return this.matchesTag(METADATA_KEY.NAMED_TAG)(t)},t.prototype.matchesTag=function(t){var e=this;return function(a){for(var r=0;r<e.metadata.length;r++){var A=e.metadata[r];if(A.key===t&&A.value===a)return!0}return!1}},t}();exports.Target=Target;

/***/ }),
/* 76 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
Object.defineProperty(exports,"__esModule",{value:!0});var binding_when_syntax_1=__webpack_require__(49),binding_on_syntax_1=__webpack_require__(48),BindingWhenOnSyntax=function(){function n(n){this._binding=n,this._bindingWhenSyntax=new binding_when_syntax_1.BindingWhenSyntax(this._binding),this._bindingOnSyntax=new binding_on_syntax_1.BindingOnSyntax(this._binding)}return n.prototype.when=function(n){return this._bindingWhenSyntax.when(n)},n.prototype.whenTargetNamed=function(n){return this._bindingWhenSyntax.whenTargetNamed(n)},n.prototype.whenTargetIsDefault=function(){return this._bindingWhenSyntax.whenTargetIsDefault()},n.prototype.whenTargetTagged=function(n,t){return this._bindingWhenSyntax.whenTargetTagged(n,t)},n.prototype.whenInjectedInto=function(n){return this._bindingWhenSyntax.whenInjectedInto(n)},n.prototype.whenParentNamed=function(n){return this._bindingWhenSyntax.whenParentNamed(n)},n.prototype.whenParentTagged=function(n,t){return this._bindingWhenSyntax.whenParentTagged(n,t)},n.prototype.whenAnyAncestorIs=function(n){return this._bindingWhenSyntax.whenAnyAncestorIs(n)},n.prototype.whenNoAncestorIs=function(n){return this._bindingWhenSyntax.whenNoAncestorIs(n)},n.prototype.whenAnyAncestorNamed=function(n){return this._bindingWhenSyntax.whenAnyAncestorNamed(n)},n.prototype.whenAnyAncestorTagged=function(n,t){return this._bindingWhenSyntax.whenAnyAncestorTagged(n,t)},n.prototype.whenNoAncestorNamed=function(n){return this._bindingWhenSyntax.whenNoAncestorNamed(n)},n.prototype.whenNoAncestorTagged=function(n,t){return this._bindingWhenSyntax.whenNoAncestorTagged(n,t)},n.prototype.whenAnyAncestorMatches=function(n){return this._bindingWhenSyntax.whenAnyAncestorMatches(n)},n.prototype.whenNoAncestorMatches=function(n){return this._bindingWhenSyntax.whenNoAncestorMatches(n)},n.prototype.onActivation=function(n){return this._bindingOnSyntax.onActivation(n)},n}();exports.BindingWhenOnSyntax=BindingWhenOnSyntax;

/***/ }),
/* 77 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
Object.defineProperty(exports,"__esModule",{value:!0});var metadata_1=__webpack_require__(9),METADATA_KEY=__webpack_require__(4),traverseAncerstors=function(t,e){var n=t.parentRequest;return null!==n&&(!!e(n)||traverseAncerstors(n,e))};exports.traverseAncerstors=traverseAncerstors;var taggedConstraint=function(t){return function(e){var n=function(n){return null!==n&&null!==n.target&&n.target.matchesTag(t)(e)};return n.metaData=new metadata_1.Metadata(t,e),n}};exports.taggedConstraint=taggedConstraint;var namedConstraint=taggedConstraint(METADATA_KEY.NAMED_TAG);exports.namedConstraint=namedConstraint;var typeConstraint=function(t){return function(e){var n=null;if(null!==e){if(n=e.bindings[0],"string"==typeof t){return n.serviceIdentifier===t}var r=e.bindings[0].implementationType;return t===r}return!1}};exports.typeConstraint=typeConstraint;

/***/ }),
/* 78 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
function primitive(r){return"string"==typeof r||"number"==typeof r}Object.defineProperty(exports,"__esModule",{value:!0}),exports.array=Array.isArray,exports.primitive=primitive;

/***/ }),
/* 79 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
function isUndef(e){return void 0===e}function isDef(e){return void 0!==e}function sameVnode(e,t){return e.key===t.key&&e.sel===t.sel}function isVnode(e){return void 0!==e.sel}function createKeyToOldIdx(e,t,i){var n,o,r,l={};for(n=t;n<=i;++n)null!=(r=e[n])&&void 0!==(o=r.key)&&(l[o]=n);return l}function init(e,t){function i(e){var t=e.id?"#"+e.id:"",i=e.className?"."+e.className.split(" ").join("."):"";return vnode_1.default(m.tagName(e).toLowerCase()+t+i,{},[],void 0,e)}function n(e,t){return function(){if(0==--t){var i=m.parentNode(e);m.removeChild(i,e)}}}function o(e,t){var i,n=e.data;void 0!==n&&isDef(i=n.hook)&&isDef(i=i.init)&&(i(e),n=e.data);var r=e.children,l=e.sel;if("!"===l)isUndef(e.text)&&(e.text=""),e.elm=m.createComment(e.text);else if(void 0!==l){var s=l.indexOf("#"),d=l.indexOf(".",s),f=s>0?s:l.length,a=d>0?d:l.length,u=-1!==s||-1!==d?l.slice(0,Math.min(f,a)):l,v=e.elm=isDef(n)&&isDef(i=n.ns)?m.createElementNS(i,u):m.createElement(u);for(f<a&&v.setAttribute("id",l.slice(f+1,a)),d>0&&v.setAttribute("class",l.slice(a+1).replace(/\./g," ")),i=0;i<h.create.length;++i)h.create[i](emptyNode,e);if(is.array(r))for(i=0;i<r.length;++i){var c=r[i];null!=c&&m.appendChild(v,o(c,t))}else is.primitive(e.text)&&m.appendChild(v,m.createTextNode(e.text));i=e.data.hook,isDef(i)&&(i.create&&i.create(emptyNode,e),i.insert&&t.push(e))}else e.elm=m.createTextNode(e.text);return e.elm}function r(e,t,i,n,r,l){for(;n<=r;++n){var s=i[n];null!=s&&m.insertBefore(e,o(s,l),t)}}function l(e){var t,i,n=e.data;if(void 0!==n){for(isDef(t=n.hook)&&isDef(t=t.destroy)&&t(e),t=0;t<h.destroy.length;++t)h.destroy[t](e);if(void 0!==e.children)for(i=0;i<e.children.length;++i)null!=(t=e.children[i])&&"string"!=typeof t&&l(t)}}function s(e,t,i,o){for(;i<=o;++i){var r=void 0,s=void 0,d=void 0,f=t[i];if(null!=f)if(isDef(f.sel)){for(l(f),s=h.remove.length+1,d=n(f.elm,s),r=0;r<h.remove.length;++r)h.remove[r](f,d);isDef(r=f.data)&&isDef(r=r.hook)&&isDef(r=r.remove)?r(f,d):d()}else m.removeChild(e,f.elm)}}function d(e,t,i,n){for(var l,d,a,u,h=0,v=0,c=t.length-1,p=t[0],x=t[c],D=i.length-1,g=i[0],k=i[D];h<=c&&v<=D;)null==p?p=t[++h]:null==x?x=t[--c]:null==g?g=i[++v]:null==k?k=i[--D]:sameVnode(p,g)?(f(p,g,n),p=t[++h],g=i[++v]):sameVnode(x,k)?(f(x,k,n),x=t[--c],k=i[--D]):sameVnode(p,k)?(f(p,k,n),m.insertBefore(e,p.elm,m.nextSibling(x.elm)),p=t[++h],k=i[--D]):sameVnode(x,g)?(f(x,g,n),m.insertBefore(e,x.elm,p.elm),x=t[--c],g=i[++v]):(void 0===l&&(l=createKeyToOldIdx(t,h,c)),d=l[g.key],isUndef(d)?(m.insertBefore(e,o(g,n),p.elm),g=i[++v]):(a=t[d],a.sel!==g.sel?m.insertBefore(e,o(g,n),p.elm):(f(a,g,n),t[d]=void 0,m.insertBefore(e,a.elm,p.elm)),g=i[++v]));h>c?(u=null==i[D+1]?null:i[D+1].elm,r(e,u,i,v,D,n)):v>D&&s(e,t,h,c)}function f(e,t,i){var n,o;isDef(n=t.data)&&isDef(o=n.hook)&&isDef(n=o.prepatch)&&n(e,t);var l=t.elm=e.elm,f=e.children,a=t.children;if(e!==t){if(void 0!==t.data){for(n=0;n<h.update.length;++n)h.update[n](e,t);n=t.data.hook,isDef(n)&&isDef(n=n.update)&&n(e,t)}isUndef(t.text)?isDef(f)&&isDef(a)?f!==a&&d(l,f,a,i):isDef(a)?(isDef(e.text)&&m.setTextContent(l,""),r(l,null,a,0,a.length-1,i)):isDef(f)?s(l,f,0,f.length-1):isDef(e.text)&&m.setTextContent(l,""):e.text!==t.text&&m.setTextContent(l,t.text),isDef(o)&&isDef(n=o.postpatch)&&n(e,t)}}var a,u,h={},m=void 0!==t?t:htmldomapi_1.default;for(a=0;a<hooks.length;++a)for(h[hooks[a]]=[],u=0;u<e.length;++u){var v=e[u][hooks[a]];void 0!==v&&h[hooks[a]].push(v)}return function(e,t){var n,r,l,d=[];for(n=0;n<h.pre.length;++n)h.pre[n]();for(isVnode(e)||(e=i(e)),sameVnode(e,t)?f(e,t,d):(r=e.elm,l=m.parentNode(r),o(t,d),null!==l&&(m.insertBefore(l,t.elm,m.nextSibling(r)),s(l,[e],0,0))),n=0;n<d.length;++n)d[n].data.hook.insert(d[n]);for(n=0;n<h.post.length;++n)h.post[n]();return t}}Object.defineProperty(exports,"__esModule",{value:!0});var vnode_1=__webpack_require__(51),is=__webpack_require__(78),htmldomapi_1=__webpack_require__(146),emptyNode=vnode_1.default("",{},[],void 0,void 0),hooks=["create","update","remove","destroy","pre","post"],h_1=__webpack_require__(50);exports.h=h_1.h;var thunk_1=__webpack_require__(152);exports.thunk=thunk_1.thunk,exports.init=init;

/***/ }),
/* 80 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
function easeInOut(e){return e<.5?e*e*2:1-(1-e)*(1-e)*2}Object.defineProperty(exports,"__esModule",{value:!0}),exports.easeInOut=easeInOut;

/***/ }),
/* 81 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
var __assign=this&&this.__assign||Object.assign||function(t){for(var e,o=1,n=arguments.length;o<n;o++){e=arguments[o];for(var i in e)Object.prototype.hasOwnProperty.call(e,i)&&(t[i]=e[i])}return t},__decorate=this&&this.__decorate||function(t,e,o,n){var i,r=arguments.length,a=r<3?e:null===n?n=Object.getOwnPropertyDescriptor(e,o):n;if("object"==typeof Reflect&&"function"==typeof Reflect.decorate)a=Reflect.decorate(t,e,o,n);else for(var s=t.length-1;s>=0;s--)(i=t[s])&&(a=(r<3?i(a):r>3?i(e,o,a):i(e,o))||a);return r>3&&a&&Object.defineProperty(e,o,a),a},__metadata=this&&this.__metadata||function(t,e){if("object"==typeof Reflect&&"function"==typeof Reflect.metadata)return Reflect.metadata(t,e)},__param=this&&this.__param||function(t,e){return function(o,n){e(o,n,t)}},__awaiter=this&&this.__awaiter||function(t,e,o,n){return new(o||(o=Promise))(function(i,r){function a(t){try{c(n.next(t))}catch(t){r(t)}}function s(t){try{c(n.throw(t))}catch(t){r(t)}}function c(t){t.done?i(t.value):new o(function(e){e(t.value)}).then(a,s)}c((n=n.apply(t,e||[])).next())})},__generator=this&&this.__generator||function(t,e){function o(t){return function(e){return n([t,e])}}function n(o){if(i)throw new TypeError("Generator is already executing.");for(;c;)try{if(i=1,r&&(a=r[2&o[0]?"return":o[0]?"throw":"next"])&&!(a=a.call(r,o[1])).done)return a;switch(r=0,a&&(o=[0,a.value]),o[0]){case 0:case 1:a=o;break;case 4:return c.label++,{value:o[1],done:!1};case 5:c.label++,r=o[1],o=[0];continue;case 7:o=c.ops.pop(),c.trys.pop();continue;default:if(a=c.trys,!(a=a.length>0&&a[a.length-1])&&(6===o[0]||2===o[0])){c=0;continue}if(3===o[0]&&(!a||o[1]>a[0]&&o[1]<a[3])){c.label=o[1];break}if(6===o[0]&&c.label<a[1]){c.label=a[1],a=o;break}if(a&&c.label<a[2]){c.label=a[2],c.ops.push(o);break}a[2]&&c.ops.pop(),c.trys.pop();continue}o=e.call(t,c)}catch(t){o=[6,t],r=0}finally{i=a=0}if(5&o[0])throw o[1];return{value:o[0]?o[1]:void 0,done:!0}}var i,r,a,s,c={label:0,sent:function(){if(1&a[0])throw a[1];return a[1]},trys:[],ops:[]};return s={next:o(0),throw:o(1),return:o(2)},"function"==typeof Symbol&&(s[Symbol.iterator]=function(){return this}),s};Object.defineProperty(exports,"__esModule",{value:!0});var inversify_1=__webpack_require__(0),types_1=__webpack_require__(1),smodel_factory_1=__webpack_require__(10),animation_frame_syncer_1=__webpack_require__(30),command_1=__webpack_require__(5),CommandStack=function(){function t(t,e,o,n,i){this.modelFactory=t,this.viewerProvider=e,this.logger=o,this.syncer=n,this.options=i,this.undoStack=[],this.redoStack=[],this.offStack=[],this.currentPromise=Promise.resolve({root:t.createRoot(smodel_factory_1.EMPTY_ROOT),hiddenRoot:void 0,popupRoot:void 0,rootChanged:!1,hiddenRootChanged:!1,popupChanged:!1})}return Object.defineProperty(t.prototype,"currentModel",{get:function(){return this.currentPromise.then(function(t){return t.root})},enumerable:!0,configurable:!0}),t.prototype.executeAll=function(t){var e=this;return t.forEach(function(t){e.logger.log(e,"Executing",t),e.handleCommand(t,t.execute,e.mergeOrPush)}),this.thenUpdate()},t.prototype.execute=function(t){return this.logger.log(this,"Executing",t),this.handleCommand(t,t.execute,this.mergeOrPush),this.thenUpdate()},t.prototype.undo=function(){var t=this;this.undoOffStackSystemCommands(),this.undoPreceedingSystemCommands();var e=this.undoStack.pop();return void 0!==e&&(this.logger.log(this,"Undoing",e),this.handleCommand(e,e.undo,function(e,o){t.redoStack.push(e)})),this.thenUpdate()},t.prototype.redo=function(){var t=this;this.undoOffStackSystemCommands();var e=this.redoStack.pop();return void 0!==e&&(this.logger.log(this,"Redoing",e),this.handleCommand(e,e.redo,function(e,o){t.pushToUndoStack(e)})),this.redoFollowingSystemCommands(),this.thenUpdate()},t.prototype.handleCommand=function(t,e,o){var n=this;this.currentPromise=this.currentPromise.then(function(i){return new Promise(function(r,a){var s,c=n.createContext(i.root);try{s=e.call(t,c)}catch(t){n.logger.error(n,"Failed to execute command:",t),s=i.root}t instanceof command_1.HiddenCommand?r(__assign({},i,{hiddenRoot:s,hiddenRootChanged:!0})):t instanceof command_1.PopupCommand?r(__assign({},i,{popupRoot:s,popupChanged:!0})):s instanceof Promise?s.then(function(e){o.call(n,t,c),r(__assign({},i,{root:e,rootChanged:!0}))}):(o.call(n,t,c),r(__assign({},i,{root:s,rootChanged:!0})))})})},t.prototype.pushToUndoStack=function(t){this.undoStack.push(t),this.options.undoHistoryLimit>=0&&this.undoStack.length>this.options.undoHistoryLimit&&this.undoStack.splice(0,this.undoStack.length-this.options.undoHistoryLimit)},t.prototype.thenUpdate=function(){var t=this;return this.currentPromise=this.currentPromise.then(function(e){return __awaiter(t,void 0,void 0,function(){return __generator(this,function(t){switch(t.label){case 0:return e.hiddenRootChanged&&void 0!==e.hiddenRoot?[4,this.updateHidden(e.hiddenRoot)]:[3,2];case 1:t.sent(),t.label=2;case 2:return e.rootChanged?[4,this.update(e.root)]:[3,4];case 3:t.sent(),t.label=4;case 4:return e.popupChanged&&void 0!==e.popupRoot?[4,this.updatePopup(e.popupRoot)]:[3,6];case 5:t.sent(),t.label=6;case 6:return[2,{root:e.root,hiddenRoot:void 0,popupRoot:void 0,rootChanged:!1,hiddenRootChanged:!1,popupChanged:!1}]}})})}),this.currentModel},t.prototype.update=function(t){return __awaiter(this,void 0,void 0,function(){var e;return __generator(this,function(o){switch(o.label){case 0:return void 0!==this.viewer?[3,2]:(e=this,[4,this.viewerProvider()]);case 1:e.viewer=o.sent(),o.label=2;case 2:return this.viewer.update(t),[2]}})})},t.prototype.updateHidden=function(t){return __awaiter(this,void 0,void 0,function(){var e;return __generator(this,function(o){switch(o.label){case 0:return void 0!==this.viewer?[3,2]:(e=this,[4,this.viewerProvider()]);case 1:e.viewer=o.sent(),o.label=2;case 2:return this.viewer.updateHidden(t),[2]}})})},t.prototype.updatePopup=function(t){return __awaiter(this,void 0,void 0,function(){var e;return __generator(this,function(o){switch(o.label){case 0:return void 0!==this.viewer?[3,2]:(e=this,[4,this.viewerProvider()]);case 1:e.viewer=o.sent(),o.label=2;case 2:return this.viewer.updatePopup(t),[2]}})})},t.prototype.mergeOrPush=function(t,e){var o=this;if(!(t instanceof command_1.HiddenCommand))if(t instanceof command_1.SystemCommand&&this.redoStack.length>0)this.offStack.push(t);else{if(this.offStack.forEach(function(t){return o.undoStack.push(t)}),this.offStack=[],this.redoStack=[],this.undoStack.length>0){var n=this.undoStack[this.undoStack.length-1];if(n instanceof command_1.MergeableCommand&&n.merge(t,e))return}this.pushToUndoStack(t)}},t.prototype.undoOffStackSystemCommands=function(){for(var t=this.offStack.pop();void 0!==t;)this.logger.log(this,"Undoing off-stack",t),this.handleCommand(t,t.undo,function(){}),t=this.offStack.pop()},t.prototype.undoPreceedingSystemCommands=function(){for(var t=this,e=this.undoStack[this.undoStack.length-1];void 0!==e&&e instanceof command_1.SystemCommand;)this.undoStack.pop(),this.logger.log(this,"Undoing",e),this.handleCommand(e,e.undo,function(e,o){t.redoStack.push(e)}),e=this.undoStack[this.undoStack.length-1]},t.prototype.redoFollowingSystemCommands=function(){for(var t=this,e=this.redoStack[this.redoStack.length-1];void 0!==e&&e instanceof command_1.SystemCommand;)this.redoStack.pop(),this.logger.log(this,"Redoing ",e),this.handleCommand(e,e.redo,function(e,o){t.pushToUndoStack(e)}),e=this.redoStack[this.redoStack.length-1]},t.prototype.createContext=function(t){return{root:t,modelChanged:this,modelFactory:this.modelFactory,duration:this.options.defaultDuration,logger:this.logger,syncer:this.syncer}},t=__decorate([inversify_1.injectable(),__param(0,inversify_1.inject(types_1.TYPES.IModelFactory)),__param(1,inversify_1.inject(types_1.TYPES.IViewerProvider)),__param(2,inversify_1.inject(types_1.TYPES.ILogger)),__param(3,inversify_1.inject(types_1.TYPES.AnimationFrameSyncer)),__param(4,inversify_1.inject(types_1.TYPES.CommandStackOptions)),__metadata("design:paramtypes",[Object,Function,Object,animation_frame_syncer_1.AnimationFrameSyncer,Object])],t)}();exports.CommandStack=CommandStack;

/***/ }),
/* 82 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
function isThunk(t){return"thunk"in t}Object.defineProperty(exports,"__esModule",{value:!0});var snabbdom_1=__webpack_require__(79),ThunkView=function(){function t(){}return t.prototype.render=function(t,e){var r=this;return snabbdom_1.h(this.selector(t),{key:t.id,hook:{init:this.init.bind(this),prepatch:this.prepatch.bind(this)},fn:function(){return r.renderAndDecorate(t,e)},args:this.watchedArgs(t),thunk:!0})},t.prototype.renderAndDecorate=function(t,e){var r=this.doRender(t,e);return e.decorate(r,t),r},t.prototype.copyToThunk=function(t,e){e.elm=t.elm,t.data.fn=e.data.fn,t.data.args=e.data.args,e.data=t.data,e.children=t.children,e.text=t.text,e.elm=t.elm},t.prototype.init=function(t){var e=t.data,r=e.fn.apply(void 0,e.args);this.copyToThunk(r,t)},t.prototype.prepatch=function(t,e){var r=t.data,n=e.data;this.equals(r.args,n.args)?this.copyToThunk(t,e):this.copyToThunk(n.fn.apply(void 0,n.args),e)},t.prototype.equals=function(t,e){if(Array.isArray(t)&&Array.isArray(e)){if(t.length!==e.length)return!1;for(var r=0;r<e.length;++r)if(!this.equals(t[r],e[r]))return!1}else if("object"==typeof t&&"object"==typeof e){if(Object.keys(t).length!==Object.keys(e).length)return!1;for(var n in t)if(!("parent"===n||"root"===n||n in e&&this.equals(t[n],e[n])))return!1}else if(t!==e)return!1;return!0},t}();exports.ThunkView=ThunkView,exports.isThunk=isThunk;

/***/ }),
/* 83 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
function configureModelElement(e,t,r,n){e.bind(types_1.TYPES.SModelElementRegistration).toConstantValue({type:t,constr:r}),e.bind(types_1.TYPES.ViewRegistration).toConstantValue({type:t,constr:n})}var __extends=this&&this.__extends||function(){var e=Object.setPrototypeOf||{__proto__:[]}instanceof Array&&function(e,t){e.__proto__=t}||function(e,t){for(var r in t)t.hasOwnProperty(r)&&(e[r]=t[r])};return function(t,r){function n(){this.constructor=t}e(t,r),t.prototype=null===r?Object.create(r):(n.prototype=r.prototype,new n)}}(),__decorate=this&&this.__decorate||function(e,t,r,n){var i,o=arguments.length,s=o<3?t:null===n?n=Object.getOwnPropertyDescriptor(t,r):n;if("object"==typeof Reflect&&"function"==typeof Reflect.decorate)s=Reflect.decorate(e,t,r,n);else for(var a=e.length-1;a>=0;a--)(i=e[a])&&(s=(o<3?i(s):o>3?i(t,r,s):i(t,r))||s);return o>3&&s&&Object.defineProperty(t,r,s),s},__metadata=this&&this.__metadata||function(e,t){if("object"==typeof Reflect&&"function"==typeof Reflect.metadata)return Reflect.metadata(e,t)},__param=this&&this.__param||function(e,t){return function(r,n){t(r,n,e)}};Object.defineProperty(exports,"__esModule",{value:!0});var snabbdom=__webpack_require__(18),inversify_1=__webpack_require__(0),types_1=__webpack_require__(1),smodel_factory_1=__webpack_require__(10),registry_1=__webpack_require__(26),geometry_1=__webpack_require__(3),JSX={createElement:snabbdom.svg},ViewRegistry=function(e){function t(t){var r=e.call(this)||this;return r.registerDefaults(),t.forEach(function(e){return r.register(e.type,e.constr)}),r}return __extends(t,e),t.prototype.registerDefaults=function(){this.register(smodel_factory_1.EMPTY_ROOT.type,EmptyView)},t.prototype.missing=function(e){return new MissingView},t=__decorate([inversify_1.injectable(),__param(0,inversify_1.multiInject(types_1.TYPES.ViewRegistration)),__param(0,inversify_1.optional()),__metadata("design:paramtypes",[Array])],t)}(registry_1.ProviderRegistry);exports.ViewRegistry=ViewRegistry,exports.configureModelElement=configureModelElement;var EmptyView=function(){function e(){}return e.prototype.render=function(e,t){return JSX.createElement("svg",{"class-sprotty-empty":!0})},e}();exports.EmptyView=EmptyView;var MissingView=function(){function e(){}return e.prototype.render=function(e,t){var r=e.position||geometry_1.ORIGIN_POINT;return JSX.createElement("text",{"class-sprotty-missing":!0,x:r.x,y:r.y},"?",e.id,"?")},e}();exports.MissingView=MissingView;

/***/ }),
/* 84 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
var __decorate=this&&this.__decorate||function(e,t,a,i){var o,c=arguments.length,d=c<3?t:null===i?i=Object.getOwnPropertyDescriptor(t,a):i;if("object"==typeof Reflect&&"function"==typeof Reflect.decorate)d=Reflect.decorate(e,t,a,i);else for(var n=e.length-1;n>=0;n--)(o=e[n])&&(d=(c<3?o(d):c>3?o(t,a,d):o(t,a))||d);return c>3&&d&&Object.defineProperty(t,a,d),d},__metadata=this&&this.__metadata||function(e,t){if("object"==typeof Reflect&&"function"==typeof Reflect.metadata)return Reflect.metadata(e,t)},__param=this&&this.__param||function(e,t){return function(a,i){t(a,i,e)}};Object.defineProperty(exports,"__esModule",{value:!0});var inversify_1=__webpack_require__(0),types_1=__webpack_require__(1),animation_frame_syncer_1=__webpack_require__(30),ViewerCache=function(){function e(e,t){this.delegate=e,this.syncer=t}return e.prototype.isCacheEmpty=function(){return void 0===this.cachedModelRoot&&void 0===this.cachedHiddenModelRoot&&void 0===this.cachedPopup},e.prototype.updatePopup=function(e){var t=this.isCacheEmpty();this.cachedPopup=e,t&&this.scheduleUpdate()},e.prototype.update=function(e){var t=this.isCacheEmpty();this.cachedModelRoot=e,t&&this.scheduleUpdate()},e.prototype.updateHidden=function(e){var t=this.isCacheEmpty();this.cachedHiddenModelRoot=e,t&&this.scheduleUpdate()},e.prototype.scheduleUpdate=function(){var e=this;this.syncer.onEndOfNextFrame(function(){if(e.cachedHiddenModelRoot){var t=e.cachedHiddenModelRoot;e.delegate.updateHidden(t),e.cachedHiddenModelRoot=void 0}if(e.cachedModelRoot){var a=e.cachedModelRoot;e.delegate.update(a),e.cachedModelRoot=void 0}if(e.cachedPopup){var a=e.cachedPopup;e.delegate.updatePopup(a),e.cachedPopup=void 0}})},e=__decorate([inversify_1.injectable(),__param(0,inversify_1.inject(types_1.TYPES.IViewer)),__param(0,inversify_1.named("delegate")),__param(1,inversify_1.inject(types_1.TYPES.AnimationFrameSyncer)),__metadata("design:paramtypes",[Object,animation_frame_syncer_1.AnimationFrameSyncer])],e)}();exports.ViewerCache=ViewerCache;

/***/ }),
/* 85 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
function configureViewerOptions(e,t){var s=__assign({},exports.defaultViewerOptions(),t);e.isBound(types_1.TYPES.ViewerOptions)?e.rebind(types_1.TYPES.ViewerOptions).toConstantValue(s):e.bind(types_1.TYPES.ViewerOptions).toConstantValue(s)}function overrideViewerOptions(e,t){var s=e.get(types_1.TYPES.ViewerOptions);for(var r in t)t.hasOwnProperty(r)&&(s[r]=t[r]);return s}var __assign=this&&this.__assign||Object.assign||function(e){for(var t,s=1,r=arguments.length;s<r;s++){t=arguments[s];for(var o in t)Object.prototype.hasOwnProperty.call(t,o)&&(e[o]=t[o])}return e};Object.defineProperty(exports,"__esModule",{value:!0});var types_1=__webpack_require__(1);exports.defaultViewerOptions=function(){return{baseDiv:"sprotty",baseClass:"sprotty",hiddenDiv:"sprotty-hidden",hiddenClass:"sprotty-hidden",popupDiv:"sprotty-popup",popupClass:"sprotty-popup",popupClosedClass:"sprotty-popup-closed",needsClientLayout:!0,needsServerLayout:!1,popupOpenDelay:1e3,popupCloseDelay:300}},exports.configureViewerOptions=configureViewerOptions,exports.overrideViewerOptions=overrideViewerOptions;

/***/ }),
/* 86 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
var __decorate=this&&this.__decorate||function(e,t,i,s){var o,n=arguments.length,r=n<3?t:null===s?s=Object.getOwnPropertyDescriptor(t,i):s;if("object"==typeof Reflect&&"function"==typeof Reflect.decorate)r=Reflect.decorate(e,t,i,s);else for(var a=e.length-1;a>=0;a--)(o=e[a])&&(r=(n<3?o(r):n>3?o(t,i,r):o(t,i))||r);return n>3&&r&&Object.defineProperty(t,i,r),r},__metadata=this&&this.__metadata||function(e,t){if("object"==typeof Reflect&&"function"==typeof Reflect.metadata)return Reflect.metadata(e,t)},__param=this&&this.__param||function(e,t){return function(i,s){t(i,s,e)}};Object.defineProperty(exports,"__esModule",{value:!0});var snabbdom=__webpack_require__(18),snabbdom_1=__webpack_require__(79),props_1=__webpack_require__(150),attributes_1=__webpack_require__(147),style_1=__webpack_require__(151),eventlisteners_1=__webpack_require__(149),class_1=__webpack_require__(148),inversify_1=__webpack_require__(0),types_1=__webpack_require__(1),geometry_1=__webpack_require__(3),initialize_canvas_1=__webpack_require__(41),vnode_utils_1=__webpack_require__(11),thunk_view_1=__webpack_require__(82),smodel_factory_1=__webpack_require__(10),JSX={createElement:snabbdom.html},ModelRenderer=function(){function e(e,t){this.viewRegistry=e,this.decorators=t}return e.prototype.decorate=function(e,t){return thunk_view_1.isThunk(e)?e:this.decorators.reduce(function(e,i){return i.decorate(e,t)},e)},e.prototype.renderElement=function(e,t){var i=this.viewRegistry.get(e.type,void 0).render(e,this,t);return this.decorate(i,e)},e.prototype.renderChildren=function(e,t){var i=this;return e.children.map(function(e){return i.renderElement(e,t)})},e.prototype.postUpdate=function(){this.decorators.forEach(function(e){return e.postUpdate()})},e}();exports.ModelRenderer=ModelRenderer;var Viewer=function(){function e(e,t,i,s,o,n,r){var a=this;this.decorators=t,this.hiddenDecorators=i,this.popupDecorators=s,this.options=o,this.logger=n,this.actiondispatcher=r,this.onWindowResize=function(e){var t=document.getElementById(a.options.baseDiv);if(null!==t){var i=a.getBoundsInPage(t);a.actiondispatcher.dispatch(new initialize_canvas_1.InitializeCanvasBoundsAction(i))}},this.patcher=this.createPatcher(),this.renderer=e(t),this.hiddenRenderer=e(i),this.popupRenderer=e(s)}return e.prototype.createModules=function(){return[props_1.propsModule,attributes_1.attributesModule,class_1.classModule,style_1.styleModule,eventlisteners_1.eventListenersModule]},e.prototype.createPatcher=function(){return snabbdom_1.init(this.createModules())},e.prototype.getBoundsInPage=function(e){var t=e.getBoundingClientRect(),i="undefined"!=typeof window?{x:window.scrollX,y:window.scrollY}:geometry_1.ORIGIN_POINT;return{x:t.left+i.x,y:t.top+i.y,width:t.width,height:t.height}},e.prototype.update=function(e){var t=this;this.logger.log(this,"rendering",e);var i=JSX.createElement("div",{id:this.options.baseDiv},this.renderer.renderElement(e));if(void 0!==this.lastVDOM){var s=this.hasFocus();vnode_utils_1.copyClassesFromVNode(this.lastVDOM,i),this.lastVDOM=this.patcher.call(this,this.lastVDOM,i),this.restoreFocus(s)}else if("undefined"!=typeof document){var o=document.getElementById(this.options.baseDiv);null!==o?("undefined"!=typeof window&&window.addEventListener("resize",function(){t.onWindowResize(i)}),vnode_utils_1.copyClassesFromElement(o,i),vnode_utils_1.setClass(i,this.options.baseClass,!0),this.lastVDOM=this.patcher.call(this,o,i)):this.logger.error(this,"element not in DOM:",this.options.baseDiv)}this.renderer.postUpdate()},e.prototype.hasFocus=function(){if("undefined"!=typeof document&&document.activeElement&&this.lastVDOM.children&&this.lastVDOM.children.length>0){var e=this.lastVDOM.children[0];if("object"==typeof e){var t=e.elm;return document.activeElement===t}}return!1},e.prototype.restoreFocus=function(e){if(e&&this.lastVDOM.children&&this.lastVDOM.children.length>0){var t=this.lastVDOM.children[0];if("object"==typeof t){var i=t.elm;i&&"function"==typeof i.focus&&i.focus()}}},e.prototype.updateHidden=function(e){this.logger.log(this,"rendering hidden");var t;if(e.type===smodel_factory_1.EMPTY_ROOT.type)t=JSX.createElement("div",{id:this.options.hiddenDiv});else{var i=this.hiddenRenderer.renderElement(e);vnode_utils_1.setAttr(i,"opacity",0),t=JSX.createElement("div",{id:this.options.hiddenDiv},i)}if(void 0!==this.lastHiddenVDOM)vnode_utils_1.copyClassesFromVNode(this.lastHiddenVDOM,t),this.lastHiddenVDOM=this.patcher.call(this,this.lastHiddenVDOM,t);else{var s=document.getElementById(this.options.hiddenDiv);null===s?(s=document.createElement("div"),document.body.appendChild(s)):vnode_utils_1.copyClassesFromElement(s,t),vnode_utils_1.setClass(t,this.options.baseClass,!0),vnode_utils_1.setClass(t,this.options.hiddenClass,!0),this.lastHiddenVDOM=this.patcher.call(this,s,t)}this.hiddenRenderer.postUpdate()},e.prototype.updatePopup=function(e){this.logger.log(this,"rendering popup",e);var t,i=e.type===smodel_factory_1.EMPTY_ROOT.type;if(i)t=JSX.createElement("div",{id:this.options.popupDiv});else{var s=e.canvasBounds,o={top:s.y+"px",left:s.x+"px"};t=JSX.createElement("div",{id:this.options.popupDiv,style:o},this.popupRenderer.renderElement(e))}if(void 0!==this.lastPopupVDOM)vnode_utils_1.copyClassesFromVNode(this.lastPopupVDOM,t),vnode_utils_1.setClass(t,this.options.popupClosedClass,i),this.lastPopupVDOM=this.patcher.call(this,this.lastPopupVDOM,t);else if("undefined"!=typeof document){var n=document.getElementById(this.options.popupDiv);null===n?(n=document.createElement("div"),document.body.appendChild(n)):vnode_utils_1.copyClassesFromElement(n,t),vnode_utils_1.setClass(t,this.options.popupClass,!0),vnode_utils_1.setClass(t,this.options.popupClosedClass,i),this.lastPopupVDOM=this.patcher.call(this,n,t)}this.popupRenderer.postUpdate()},e=__decorate([inversify_1.injectable(),__param(0,inversify_1.inject(types_1.TYPES.ModelRendererFactory)),__param(1,inversify_1.multiInject(types_1.TYPES.IVNodeDecorator)),__param(1,inversify_1.optional()),__param(2,inversify_1.multiInject(types_1.TYPES.HiddenVNodeDecorator)),__param(2,inversify_1.optional()),__param(3,inversify_1.multiInject(types_1.TYPES.PopupVNodeDecorator)),__param(3,inversify_1.optional()),__param(4,inversify_1.inject(types_1.TYPES.ViewerOptions)),__param(5,inversify_1.inject(types_1.TYPES.ILogger)),__param(6,inversify_1.inject(types_1.TYPES.IActionDispatcher)),__metadata("design:paramtypes",[Function,Array,Array,Array,Object,Object,Object])],e)}();exports.Viewer=Viewer;

/***/ }),
/* 87 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
var __decorate=this&&this.__decorate||function(e,t,r,o){var n,i=arguments.length,c=i<3?t:null===o?o=Object.getOwnPropertyDescriptor(t,r):o;if("object"==typeof Reflect&&"function"==typeof Reflect.decorate)c=Reflect.decorate(e,t,r,o);else for(var s=e.length-1;s>=0;s--)(n=e[s])&&(c=(i<3?n(c):i>3?n(t,r,c):n(t,r))||c);return i>3&&c&&Object.defineProperty(t,r,c),c};Object.defineProperty(exports,"__esModule",{value:!0});var inversify_1=__webpack_require__(0),vnode_utils_1=__webpack_require__(11),FocusFixDecorator=function(){function e(){}return t=e,e.prototype.decorate=function(e,r){return e.sel&&e.sel.startsWith("svg")&&vnode_utils_1.setAttr(e,"tabindex",++t.tabIndex),e},e.prototype.postUpdate=function(){},e.tabIndex=1e3,e=t=__decorate([inversify_1.injectable()],e);var t}();exports.FocusFixDecorator=FocusFixDecorator;

/***/ }),
/* 88 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
var __extends=this&&this.__extends||function(){var t=Object.setPrototypeOf||{__proto__:[]}instanceof Array&&function(t,e){t.__proto__=e}||function(t,e){for(var o in e)e.hasOwnProperty(o)&&(t[o]=e[o])};return function(e,o){function n(){this.constructor=e}t(e,o),e.prototype=null===o?Object.create(o):(n.prototype=o.prototype,new n)}}(),__assign=this&&this.__assign||Object.assign||function(t){for(var e,o=1,n=arguments.length;o<n;o++){e=arguments[o];for(var r in e)Object.prototype.hasOwnProperty.call(e,r)&&(t[r]=e[r])}return t};Object.defineProperty(exports,"__esModule",{value:!0});var geometry_1=__webpack_require__(3),abstract_layout_1=__webpack_require__(56),HBoxLayouter=function(t){function e(){return null!==t&&t.apply(this,arguments)||this}return __extends(e,t),e.prototype.getChildrenSize=function(t,e,o){var n=0,r=-1,i=!0;return t.children.forEach(function(t){var a=o.getBoundsData(t).bounds;void 0!==a&&geometry_1.isValidDimension(a)&&(i?i=!1:n+=e.hGap,n+=a.width,r=Math.max(r,a.height))}),{width:n,height:r}},e.prototype.layoutChild=function(t,e,o,n,r,i,a,u){var s=this.getDy(n.vAlign,o,u);return e.bounds={x:i.x+t.bounds.x-o.x,y:r.paddingTop+t.bounds.y-o.y+s,width:o.width,height:o.height},e.boundsChanged=!0,{x:i.x+o.width+r.hGap,y:i.y}},e.prototype.getDefaultLayoutOptions=function(){return{resizeContainer:!0,paddingTop:5,paddingBottom:5,paddingLeft:5,paddingRight:5,paddingFactor:1,hGap:1,vAlign:"center"}},e.prototype.spread=function(t,e){return __assign({},t,e)},e.KIND="hbox",e}(abstract_layout_1.AbstractLayout);exports.HBoxLayouter=HBoxLayouter;

/***/ }),
/* 89 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
var __extends=this&&this.__extends||function(){var t=Object.setPrototypeOf||{__proto__:[]}instanceof Array&&function(t,e){t.__proto__=e}||function(t,e){for(var n in e)e.hasOwnProperty(n)&&(t[n]=e[n])};return function(e,n){function r(){this.constructor=e}t(e,n),e.prototype=null===n?Object.create(n):(r.prototype=n.prototype,new r)}}(),__assign=this&&this.__assign||Object.assign||function(t){for(var e,n=1,r=arguments.length;n<r;n++){e=arguments[n];for(var o in e)Object.prototype.hasOwnProperty.call(e,o)&&(t[o]=e[o])}return t};Object.defineProperty(exports,"__esModule",{value:!0});var geometry_1=__webpack_require__(3),abstract_layout_1=__webpack_require__(56),StackLayouter=function(t){function e(){return null!==t&&t.apply(this,arguments)||this}return __extends(e,t),e.prototype.getChildrenSize=function(t,e,n){var r=-1,o=-1;return t.children.forEach(function(t){var e=n.getBoundsData(t).bounds;void 0!==e&&geometry_1.isValidDimension(e)&&(r=Math.max(r,e.width),o=Math.max(o,e.height))}),{width:r,height:o}},e.prototype.layoutChild=function(t,e,n,r,o,i,a,s){var u=this.getDx(r.hAlign,n,a),c=this.getDy(r.vAlign,n,s);return e.bounds={x:o.paddingLeft+t.bounds.x-n.x+u,y:o.paddingTop+t.bounds.y-n.y+c,width:n.width,height:n.height},e.boundsChanged=!0,i},e.prototype.getDefaultLayoutOptions=function(){return{resizeContainer:!0,paddingTop:5,paddingBottom:5,paddingLeft:5,paddingRight:5,paddingFactor:1,hAlign:"center",vAlign:"center"}},e.prototype.spread=function(t,e){return __assign({},t,e)},e.KIND="stack",e}(abstract_layout_1.AbstractLayout);exports.StackLayouter=StackLayouter;

/***/ }),
/* 90 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
var __extends=this&&this.__extends||function(){var t=Object.setPrototypeOf||{__proto__:[]}instanceof Array&&function(t,e){t.__proto__=e}||function(t,e){for(var o in e)e.hasOwnProperty(o)&&(t[o]=e[o])};return function(e,o){function n(){this.constructor=e}t(e,o),e.prototype=null===o?Object.create(o):(n.prototype=o.prototype,new n)}}(),__assign=this&&this.__assign||Object.assign||function(t){for(var e,o=1,n=arguments.length;o<n;o++){e=arguments[o];for(var r in e)Object.prototype.hasOwnProperty.call(e,r)&&(t[r]=e[r])}return t};Object.defineProperty(exports,"__esModule",{value:!0});var geometry_1=__webpack_require__(3),abstract_layout_1=__webpack_require__(56),VBoxLayouter=function(t){function e(){return null!==t&&t.apply(this,arguments)||this}return __extends(e,t),e.prototype.getChildrenSize=function(t,e,o){var n=-1,r=0,i=!0;return t.children.forEach(function(t){var a=o.getBoundsData(t).bounds;void 0!==a&&geometry_1.isValidDimension(a)&&(r+=a.height,i?i=!1:r+=e.vGap,n=Math.max(n,a.width))}),{width:n,height:r}},e.prototype.layoutChild=function(t,e,o,n,r,i,a,u){var s=this.getDx(n.hAlign,o,a);return e.bounds={x:r.paddingLeft+t.bounds.x-o.x+s,y:i.y+t.bounds.y-o.y,width:o.width,height:o.height},e.boundsChanged=!0,{x:i.x,y:i.y+o.height+r.vGap}},e.prototype.getDefaultLayoutOptions=function(){return{resizeContainer:!0,paddingTop:5,paddingBottom:5,paddingLeft:5,paddingRight:5,paddingFactor:1,vGap:1,hAlign:"center"}},e.prototype.spread=function(t,e){return __assign({},t,e)},e.KIND="vbox",e}(abstract_layout_1.AbstractLayout);exports.VBoxLayouter=VBoxLayouter;

/***/ }),
/* 91 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
var __extends=this&&this.__extends||function(){var e=Object.setPrototypeOf||{__proto__:[]}instanceof Array&&function(e,t){e.__proto__=t}||function(e,t){for(var o in t)t.hasOwnProperty(o)&&(e[o]=t[o])};return function(t,o){function r(){this.constructor=t}e(t,o),t.prototype=null===o?Object.create(o):(r.prototype=o.prototype,new r)}}(),__decorate=this&&this.__decorate||function(e,t,o,r){var n,i=arguments.length,s=i<3?t:null===r?r=Object.getOwnPropertyDescriptor(t,o):r;if("object"==typeof Reflect&&"function"==typeof Reflect.decorate)s=Reflect.decorate(e,t,o,r);else for(var a=e.length-1;a>=0;a--)(n=e[a])&&(s=(i<3?n(s):i>3?n(t,o,s):n(t,o))||s);return i>3&&s&&Object.defineProperty(t,o,s),s},__metadata=this&&this.__metadata||function(e,t){if("object"==typeof Reflect&&"function"==typeof Reflect.metadata)return Reflect.metadata(e,t)},__param=this&&this.__param||function(e,t){return function(o,r){t(o,r,e)}};Object.defineProperty(exports,"__esModule",{value:!0});var inversify_1=__webpack_require__(0),command_1=__webpack_require__(5),model_1=__webpack_require__(12),smodel_1=__webpack_require__(2),key_tool_1=__webpack_require__(20),keyboard_1=__webpack_require__(36),model_2=__webpack_require__(44),svg_exporter_1=__webpack_require__(24),smodel_factory_1=__webpack_require__(10),model_3=__webpack_require__(16),model_4=__webpack_require__(35),types_1=__webpack_require__(1),ExportSvgKeyListener=function(e){function t(){return null!==e&&e.apply(this,arguments)||this}return __extends(t,e),t.prototype.keyDown=function(e,t){return keyboard_1.matchesKeystroke(t,"KeyE","ctrlCmd","shift")?[new RequestExportSvgAction]:[]},t=__decorate([inversify_1.injectable()],t)}(key_tool_1.KeyListener);exports.ExportSvgKeyListener=ExportSvgKeyListener;var RequestExportSvgAction=function(){function e(){this.kind=ExportSvgCommand.KIND}return e}();exports.RequestExportSvgAction=RequestExportSvgAction;var ExportSvgCommand=function(e){function t(){return null!==e&&e.apply(this,arguments)||this}return __extends(t,e),t.prototype.execute=function(e){if(model_2.isExportable(e.root)){var t=e.modelFactory.createRoot(e.modelFactory.createSchema(e.root));if(model_2.isExportable(t))return t.export=!0,model_3.isViewport(t)&&(t.zoom=1,t.scroll={x:0,y:0}),t.index.all().forEach(function(e){model_1.isSelectable(e)&&e.selected&&(e.selected=!1),model_4.isHoverable(e)&&e.hoverFeedback&&(e.hoverFeedback=!1)}),t}return e.modelFactory.createRoot(smodel_factory_1.EMPTY_ROOT)},t.KIND="requestExportSvg",t}(command_1.HiddenCommand);exports.ExportSvgCommand=ExportSvgCommand;var ExportSvgDecorator=function(){function e(e){this.svgExporter=e}return e.prototype.decorate=function(e,t){return t instanceof smodel_1.SModelRoot&&(this.root=t),e},e.prototype.postUpdate=function(){this.root&&model_2.isExportable(this.root)&&this.root.export&&this.svgExporter.export(this.root)},e=__decorate([inversify_1.injectable(),__param(0,inversify_1.inject(types_1.TYPES.SvgExporter)),__metadata("design:paramtypes",[svg_exporter_1.SvgExporter])],e)}();exports.ExportSvgDecorator=ExportSvgDecorator;

/***/ }),
/* 92 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
function isOpenable(e){return e.hasFeature(exports.openFeature)}Object.defineProperty(exports,"__esModule",{value:!0}),exports.openFeature=Symbol("openFeature"),exports.isOpenable=isOpenable;

/***/ }),
/* 93 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
var __extends=this&&this.__extends||function(){var e=Object.setPrototypeOf||{__proto__:[]}instanceof Array&&function(e,t){e.__proto__=t}||function(e,t){for(var o in t)t.hasOwnProperty(o)&&(e[o]=t[o])};return function(t,o){function n(){this.constructor=t}e(t,o),t.prototype=null===o?Object.create(o):(n.prototype=o.prototype,new n)}}(),__decorate=this&&this.__decorate||function(e,t,o,n){var r,i=arguments.length,l=i<3?t:null===n?n=Object.getOwnPropertyDescriptor(t,o):n;if("object"==typeof Reflect&&"function"==typeof Reflect.decorate)l=Reflect.decorate(e,t,o,n);else for(var s=e.length-1;s>=0;s--)(r=e[s])&&(l=(i<3?r(l):i>3?r(t,o,l):r(t,o))||l);return i>3&&l&&Object.defineProperty(t,o,l),l},__metadata=this&&this.__metadata||function(e,t){if("object"==typeof Reflect&&"function"==typeof Reflect.metadata)return Reflect.metadata(e,t)},__param=this&&this.__param||function(e,t){return function(o,n){t(o,n,e)}};Object.defineProperty(exports,"__esModule",{value:!0});var inversify_1=__webpack_require__(0),browser_1=__webpack_require__(69),keyboard_1=__webpack_require__(36),iterable_1=__webpack_require__(70),smodel_1=__webpack_require__(2),smodel_utils_1=__webpack_require__(6),command_1=__webpack_require__(5),mouse_tool_1=__webpack_require__(15),key_tool_1=__webpack_require__(20),vnode_utils_1=__webpack_require__(11),button_handler_1=__webpack_require__(58),model_1=__webpack_require__(59),model_2=__webpack_require__(21),edit_routing_1=__webpack_require__(42),model_3=__webpack_require__(12),SelectAction=function(){function e(e,t){void 0===e&&(e=[]),void 0===t&&(t=[]),this.selectedElementsIDs=e,this.deselectedElementsIDs=t,this.kind=SelectCommand.KIND}return e}();exports.SelectAction=SelectAction;var SelectAllAction=function(){function e(e){void 0===e&&(e=!0),this.select=e,this.kind=SelectAllCommand.KIND}return e}();exports.SelectAllAction=SelectAllAction;var SelectCommand=function(e){function t(t){var o=e.call(this)||this;return o.action=t,o.selected=[],o.deselected=[],o}return __extends(t,e),t.prototype.execute=function(e){var t=this,o=e.root;return this.action.selectedElementsIDs.forEach(function(e){var n=o.index.getById(e);n instanceof smodel_1.SChildElement&&model_3.isSelectable(n)&&t.selected.push({element:n,parent:n.parent,index:n.parent.children.indexOf(n)})}),this.action.deselectedElementsIDs.forEach(function(e){var n=o.index.getById(e);n instanceof smodel_1.SChildElement&&model_3.isSelectable(n)&&t.deselected.push({element:n,parent:n.parent,index:n.parent.children.indexOf(n)})}),this.redo(e)},t.prototype.undo=function(e){for(var t=this.selected.length-1;t>=0;--t){var o=this.selected[t],n=o.element;model_3.isSelectable(n)&&(n.selected=!1),o.parent.move(n,o.index)}return this.deselected.reverse().forEach(function(e){model_3.isSelectable(e.element)&&(e.element.selected=!0)}),e.root},t.prototype.redo=function(e){for(var t=0;t<this.selected.length;++t){var o=this.selected[t],n=o.element,r=o.parent.children.length;o.parent.move(n,r-1)}return this.deselected.forEach(function(e){model_3.isSelectable(e.element)&&(e.element.selected=!1)}),this.selected.forEach(function(e){model_3.isSelectable(e.element)&&(e.element.selected=!0)}),e.root},t.KIND="elementSelected",t}(command_1.Command);exports.SelectCommand=SelectCommand;var SelectAllCommand=function(e){function t(t){var o=e.call(this)||this;return o.action=t,o.previousSelection={},o}return __extends(t,e),t.prototype.execute=function(e){return this.selectAll(e.root,this.action.select),e.root},t.prototype.selectAll=function(e,t){model_3.isSelectable(e)&&(this.previousSelection[e.id]=e.selected,e.selected=t);for(var o=0,n=e.children;o<n.length;o++){var r=n[o];this.selectAll(r,t)}},t.prototype.undo=function(e){var t=e.root.index;for(var o in this.previousSelection)if(this.previousSelection.hasOwnProperty(o)){var n=t.getById(o);void 0!==n&&model_3.isSelectable(n)&&(n.selected=this.previousSelection[o])}return e.root},t.prototype.redo=function(e){return this.selectAll(e.root,this.action.select),e.root},t.KIND="allSelected",t}(command_1.Command);exports.SelectAllCommand=SelectAllCommand;var SelectMouseListener=function(e){function t(t){var o=e.call(this)||this;return o.buttonHandlerRegistry=t,o.wasSelected=!1,o.hasDragged=!1,o}return __extends(t,e),t.prototype.mouseDown=function(e,t){var o=[];if(0===t.button){if(void 0!==this.buttonHandlerRegistry&&e instanceof model_1.SButton&&e.enabled){var n=this.buttonHandlerRegistry.get(e.type);if(void 0!==n)return n.buttonPressed(e)}var r=smodel_utils_1.findParentByFeature(e,model_3.isSelectable);if(void 0!==r||e instanceof smodel_1.SModelRoot){this.hasDragged=!1;var i=[];if(browser_1.isCtrlOrCmd(t)||(i=iterable_1.toArray(e.root.index.all().filter(function(e){return model_3.isSelectable(e)&&e.selected&&!(r instanceof model_2.SRoutingHandle&&e===r.parent)}))),void 0!==r)if(r.selected)browser_1.isCtrlOrCmd(t)?(this.wasSelected=!1,o.push(new SelectAction([],[r.id])),model_2.isRoutable(r)&&o.push(new edit_routing_1.SwitchEditModeAction([],[r.id]))):this.wasSelected=!0;else{this.wasSelected=!1,o.push(new SelectAction([r.id],i.map(function(e){return e.id})));var l=i.filter(function(e){return model_2.isRoutable(e)}).map(function(e){return e.id});model_2.isRoutable(r)?o.push(new edit_routing_1.SwitchEditModeAction([r.id],l)):l.length>0&&o.push(new edit_routing_1.SwitchEditModeAction([],l))}else{o.push(new SelectAction([],i.map(function(e){return e.id})));var l=i.filter(function(e){return model_2.isRoutable(e)}).map(function(e){return e.id});l.length>0&&o.push(new edit_routing_1.SwitchEditModeAction([],l))}}}return o},t.prototype.mouseMove=function(e,t){return this.hasDragged=!0,[]},t.prototype.mouseUp=function(e,t){if(0===t.button&&!this.hasDragged){var o=smodel_utils_1.findParentByFeature(e,model_3.isSelectable);if(void 0!==o&&this.wasSelected)return[new SelectAction([o.id],[])]}return this.hasDragged=!1,[]},t.prototype.decorate=function(e,t){var o=smodel_utils_1.findParentByFeature(t,model_3.isSelectable);return void 0!==o&&vnode_utils_1.setClass(e,"selected",o.selected),e},t=__decorate([__param(0,inversify_1.inject(button_handler_1.ButtonHandlerRegistry)),__param(0,inversify_1.optional()),__metadata("design:paramtypes",[button_handler_1.ButtonHandlerRegistry])],t)}(mouse_tool_1.MouseListener);exports.SelectMouseListener=SelectMouseListener;var SelectKeyboardListener=function(e){function t(){return null!==e&&e.apply(this,arguments)||this}return __extends(t,e),t.prototype.keyDown=function(e,t){if(keyboard_1.matchesKeystroke(t,"KeyA","ctrlCmd")){var o=iterable_1.toArray(e.root.index.all().filter(function(e){return model_3.isSelectable(e)}).map(function(e){return e.id}));return[new SelectAction(o,[])]}return[]},t}(key_tool_1.KeyListener);exports.SelectKeyboardListener=SelectKeyboardListener;

/***/ }),
/* 94 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
function isScrollable(o){return"scroll"in o}var __extends=this&&this.__extends||function(){var o=Object.setPrototypeOf||{__proto__:[]}instanceof Array&&function(o,e){o.__proto__=e}||function(o,e){for(var t in e)e.hasOwnProperty(t)&&(o[t]=e[t])};return function(e,t){function r(){this.constructor=e}o(e,t),e.prototype=null===t?Object.create(t):(r.prototype=t.prototype,new r)}}();Object.defineProperty(exports,"__esModule",{value:!0});var smodel_1=__webpack_require__(2),mouse_tool_1=__webpack_require__(15),smodel_utils_1=__webpack_require__(6),viewport_1=__webpack_require__(25),model_1=__webpack_require__(16),model_2=__webpack_require__(22),model_3=__webpack_require__(21);exports.isScrollable=isScrollable;var ScrollMouseListener=function(o){function e(){return null!==o&&o.apply(this,arguments)||this}return __extends(e,o),e.prototype.mouseDown=function(o,e){if(void 0===smodel_utils_1.findParentByFeature(o,model_2.isMoveable)&&!(o instanceof model_3.SRoutingHandle)){var t=smodel_utils_1.findParentByFeature(o,model_1.isViewport);this.lastScrollPosition=t?{x:e.pageX,y:e.pageY}:void 0}return[]},e.prototype.mouseMove=function(o,e){if(0===e.buttons)this.mouseUp(o,e);else if(this.lastScrollPosition){var t=smodel_utils_1.findParentByFeature(o,model_1.isViewport);if(t){var r=(e.pageX-this.lastScrollPosition.x)/t.zoom,i=(e.pageY-this.lastScrollPosition.y)/t.zoom,s={scroll:{x:t.scroll.x-r,y:t.scroll.y-i},zoom:t.zoom};return this.lastScrollPosition={x:e.pageX,y:e.pageY},[new viewport_1.ViewportAction(t.id,s,!1)]}}return[]},e.prototype.mouseEnter=function(o,e){return o instanceof smodel_1.SModelRoot&&0===e.buttons&&this.mouseUp(o,e),[]},e.prototype.mouseUp=function(o,e){return this.lastScrollPosition=void 0,[]},e}(mouse_tool_1.MouseListener);exports.ScrollMouseListener=ScrollMouseListener;

/***/ }),
/* 95 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
function isZoomable(e){return"zoom"in e}var __extends=this&&this.__extends||function(){var e=Object.setPrototypeOf||{__proto__:[]}instanceof Array&&function(e,o){e.__proto__=o}||function(e,o){for(var t in o)o.hasOwnProperty(t)&&(e[t]=o[t])};return function(o,t){function r(){this.constructor=o}e(o,t),o.prototype=null===t?Object.create(t):(r.prototype=t.prototype,new r)}}();Object.defineProperty(exports,"__esModule",{value:!0});var mouse_tool_1=__webpack_require__(15),smodel_utils_1=__webpack_require__(6),viewport_1=__webpack_require__(25),model_1=__webpack_require__(16);exports.isZoomable=isZoomable;var ZoomMouseListener=function(e){function o(){return null!==e&&e.apply(this,arguments)||this}return __extends(o,e),o.prototype.wheel=function(e,o){var t=smodel_utils_1.findParentByFeature(e,model_1.isViewport);if(t){var r=Math.exp(.005*-o.deltaY),s=1/(r*t.zoom)-1/t.zoom,n={scroll:{x:-(s*o.offsetX-t.scroll.x),y:-(s*o.offsetY-t.scroll.y)},zoom:t.zoom*r};return[new viewport_1.ViewportAction(t.id,n,!1)]}return[]},o}(mouse_tool_1.MouseListener);exports.ZoomMouseListener=ZoomMouseListener;

/***/ }),
/* 96 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
function isActionMessage(e){return void 0!==e&&e.hasOwnProperty("clientId")&&e.hasOwnProperty("action")}var __extends=this&&this.__extends||function(){var e=Object.setPrototypeOf||{__proto__:[]}instanceof Array&&function(e,t){e.__proto__=t}||function(e,t){for(var r in t)t.hasOwnProperty(r)&&(e[r]=t[r])};return function(t,r){function o(){this.constructor=t}e(t,r),t.prototype=null===r?Object.create(r):(o.prototype=r.prototype,new o)}}(),__decorate=this&&this.__decorate||function(e,t,r,o){var n,i=arguments.length,s=i<3?t:null===o?o=Object.getOwnPropertyDescriptor(t,r):o;if("object"==typeof Reflect&&"function"==typeof Reflect.decorate)s=Reflect.decorate(e,t,r,o);else for(var a=e.length-1;a>=0;a--)(n=e[a])&&(s=(i<3?n(s):i>3?n(t,r,s):n(t,r))||s);return i>3&&s&&Object.defineProperty(t,r,s),s},__metadata=this&&this.__metadata||function(e,t){if("object"==typeof Reflect&&"function"==typeof Reflect.metadata)return Reflect.metadata(e,t)},__param=this&&this.__param||function(e,t){return function(r,o){t(r,o,e)}};Object.defineProperty(exports,"__esModule",{value:!0});var inversify_1=__webpack_require__(0),types_1=__webpack_require__(1),smodel_1=__webpack_require__(2),smodel_storage_1=__webpack_require__(54),action_handler_1=__webpack_require__(23),set_model_1=__webpack_require__(31),update_model_1=__webpack_require__(65),bounds_manipulation_1=__webpack_require__(32),hover_1=__webpack_require__(34),model_source_1=__webpack_require__(68),svg_exporter_1=__webpack_require__(24),file_saver_1=__webpack_require__(73),expand_1=__webpack_require__(43),open_1=__webpack_require__(62);exports.isActionMessage=isActionMessage;var ServerStatusAction=function(){function e(){this.kind=e.KIND}return e.KIND="serverStatus",e}();exports.ServerStatusAction=ServerStatusAction;var receivedFromServerProperty="__receivedFromServer",DiagramServer=function(e){function t(t,r,o,n,i){var s=e.call(this,t,r,o)||this;return s.storage=n,s.logger=i,s.currentRoot={type:"NONE",id:"ROOT"},s.clientId=s.viewerOptions.baseDiv,s}return __extends(t,e),t.prototype.initialize=function(t){e.prototype.initialize.call(this,t),t.registerCommand(update_model_1.UpdateModelCommand),t.register(bounds_manipulation_1.ComputedBoundsAction.KIND,this),t.register(bounds_manipulation_1.RequestBoundsCommand.KIND,this),t.register(hover_1.RequestPopupModelAction.KIND,this),t.register(expand_1.CollapseExpandAction.KIND,this),t.register(expand_1.CollapseExpandAllAction.KIND,this),t.register(open_1.OpenAction.KIND,this),t.register(ServerStatusAction.KIND,this)},t.prototype.handle=function(e){if(this.handleLocally(e)){var t={clientId:this.clientId,action:e};this.logger.log(this,"sending",t),this.sendMessage(t)}},t.prototype.messageReceived=function(e){var t=this,r="string"==typeof e?JSON.parse(e):e;isActionMessage(r)&&r.action?r.clientId&&r.clientId!==this.clientId||(r.action[receivedFromServerProperty]=!0,this.logger.log(this,"receiving",r),this.actionDispatcher.dispatch(r.action).then(function(){t.storeNewModel(r.action)})):this.logger.error(this,"received data is not an action message",r)},t.prototype.handleLocally=function(e){switch(this.storeNewModel(e),e.kind){case bounds_manipulation_1.ComputedBoundsAction.KIND:return this.handleComputedBounds(e);case bounds_manipulation_1.RequestBoundsCommand.KIND:return!1;case svg_exporter_1.ExportSvgAction.KIND:return this.handleExportSvgAction(e);case ServerStatusAction.KIND:return this.handleServerStateAction(e)}return!e[receivedFromServerProperty]},t.prototype.storeNewModel=function(e){if(e.kind===set_model_1.SetModelCommand.KIND||e.kind===update_model_1.UpdateModelCommand.KIND||e.kind===bounds_manipulation_1.RequestBoundsCommand.KIND){var t=e.newRoot;t&&(this.currentRoot=t,e.kind!==set_model_1.SetModelCommand.KIND&&e.kind!==update_model_1.UpdateModelCommand.KIND||(this.lastSubmittedModelType=t.type),this.storage.store(this.currentRoot))}},t.prototype.handleComputedBounds=function(e){if(this.viewerOptions.needsServerLayout)return!0;var t=new smodel_1.SModelIndex,r=this.currentRoot;t.add(r);for(var o=0,n=e.bounds;o<n.length;o++){var i=n[o],s=t.getById(i.elementId);void 0!==s&&this.applyBounds(s,i.newBounds)}if(void 0!==e.alignments)for(var a=0,d=e.alignments;a<d.length;a++){var p=d[a],s=t.getById(p.elementId);void 0!==s&&this.applyAlignment(s,p.newAlignment)}return r.type===this.lastSubmittedModelType?this.actionDispatcher.dispatch(new update_model_1.UpdateModelAction(r)):this.actionDispatcher.dispatch(new set_model_1.SetModelAction(r)),this.lastSubmittedModelType=r.type,!1},t.prototype.applyBounds=function(e,t){var r=e;r.position={x:t.x,y:t.y},r.size={width:t.width,height:t.height}},t.prototype.applyAlignment=function(e,t){e.alignment={x:t.x,y:t.y}},t.prototype.handleExportSvgAction=function(e){var t=new Blob([e.svg],{type:"text/plain;charset=utf-8"});return file_saver_1.saveAs(t,"diagram.svg"),!1},t.prototype.handleServerStateAction=function(e){return!1},t=__decorate([inversify_1.injectable(),__param(0,inversify_1.inject(types_1.TYPES.IActionDispatcher)),__param(1,inversify_1.inject(types_1.TYPES.ActionHandlerRegistry)),__param(2,inversify_1.inject(types_1.TYPES.ViewerOptions)),__param(3,inversify_1.inject(types_1.TYPES.SModelStorage)),__param(4,inversify_1.inject(types_1.TYPES.ILogger)),__metadata("design:paramtypes",[Object,action_handler_1.ActionHandlerRegistry,Object,smodel_storage_1.SModelStorage,Object])],t)}(model_source_1.ModelSource);exports.DiagramServer=DiagramServer;

/***/ }),
/* 97 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
Object.defineProperty(exports,"__esModule",{value:!0});var ExpansionState=function(){function e(e){this.expandedElementIds=[],this.initialize(e)}return e.prototype.initialize=function(e){var t=this;e.expanded&&this.expandedElementIds.push(e.id),void 0!==e.children&&e.children.forEach(function(e){return t.initialize(e)})},e.prototype.apply=function(e){for(var t=0,n=e.collapseIds;t<n.length;t++){var i=n[t],d=this.expandedElementIds.indexOf(i);-1!==d&&this.expandedElementIds.splice(d,1)}for(var s=0,a=e.expandIds;s<a.length;s++){var p=a[s];this.expandedElementIds.push(p)}},e.prototype.collapseAll=function(){this.expandedElementIds=[]},e}();exports.ExpansionState=ExpansionState;

/***/ }),
/* 98 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
function computeCircleAnchor(t,e,r,n){void 0===n&&(n=0);var i=t.x+e,o=t.y+e,c=i-r.x,s=o-r.y,a=Math.sqrt(c*c+s*s);return{x:i-(c/a||0)*(e+n),y:o-(s/a||0)*(e+n)}}function getXIntersection(t,e,r){var n=(t-e.y)/(r.y-e.y);return(r.x-e.x)*n+e.x}function getYIntersection(t,e,r){var n=(t-e.x)/(r.x-e.x);return(r.y-e.y)*n+e.y}function computeRectangleAnchor(t,e,r){var n=geometry_1.center(t),i=new NearestPointFinder(n,e);if(!geometry_1.almostEquals(n.y,e.y)){var o=getXIntersection(t.y,n,e);o>=t.x&&o<=t.x+t.width&&i.addCandidate(o,t.y-r);var c=getXIntersection(t.y+t.height,n,e);c>=t.x&&c<=t.x+t.width&&i.addCandidate(c,t.y+t.height+r)}if(!geometry_1.almostEquals(n.x,e.x)){var s=getYIntersection(t.x,n,e);s>=t.y&&s<=t.y+t.height&&i.addCandidate(t.x-r,s);var a=getYIntersection(t.x+t.width,n,e);a>=t.y&&a<=t.y+t.height&&i.addCandidate(t.x+t.width+r,a)}return i.best}Object.defineProperty(exports,"__esModule",{value:!0});var geometry_1=__webpack_require__(3);exports.computeCircleAnchor=computeCircleAnchor;var NearestPointFinder=function(){function t(t,e){this.centerPoint=t,this.refPoint=e,this.currentDist=-1}return t.prototype.addCandidate=function(t,e){var r=this.refPoint.x-t,n=this.refPoint.y-e,i=r*r+n*n;(this.currentDist<0||i<this.currentDist)&&(this.currentBest={x:t,y:e},this.currentDist=i)},Object.defineProperty(t.prototype,"best",{get:function(){return void 0===this.currentBest?this.centerPoint:this.currentBest},enumerable:!0,configurable:!0}),t}();exports.computeRectangleAnchor=computeRectangleAnchor;

/***/ }),
/* 99 */
/***/ (function(module, exports) {

/* WEBPACK VAR INJECTION */(function(__webpack_amd_options__) {/* globals __webpack_amd_options__ */
module.exports = __webpack_amd_options__;

/* WEBPACK VAR INJECTION */}.call(exports, {}))

/***/ }),
/* 100 */
/***/ (function(module, exports, __webpack_require__) {

function copyProps(f,r){for(var e in f)r[e]=f[e]}function SafeBuffer(f,r,e){return Buffer(f,r,e)}var buffer=__webpack_require__(38),Buffer=buffer.Buffer;Buffer.from&&Buffer.alloc&&Buffer.allocUnsafe&&Buffer.allocUnsafeSlow?module.exports=buffer:(copyProps(buffer,exports),exports.Buffer=SafeBuffer),copyProps(Buffer,SafeBuffer),SafeBuffer.from=function(f,r,e){if("number"==typeof f)throw new TypeError("Argument must not be a number");return Buffer(f,r,e)},SafeBuffer.alloc=function(f,r,e){if("number"!=typeof f)throw new TypeError("Argument must be a number");var u=Buffer(f);return void 0!==r?"string"==typeof e?u.fill(r,e):u.fill(r):u.fill(0),u},SafeBuffer.allocUnsafe=function(f){if("number"!=typeof f)throw new TypeError("Argument must be a number");return Buffer(f)},SafeBuffer.allocUnsafeSlow=function(f){if("number"!=typeof f)throw new TypeError("Argument must be a number");return buffer.SlowBuffer(f)};

/***/ }),
/* 101 */
/***/ (function(module, exports) {

function EventEmitter(){this._events=this._events||{},this._maxListeners=this._maxListeners||void 0}function isFunction(e){return"function"==typeof e}function isNumber(e){return"number"==typeof e}function isObject(e){return"object"==typeof e&&null!==e}function isUndefined(e){return void 0===e}module.exports=EventEmitter,EventEmitter.EventEmitter=EventEmitter,EventEmitter.prototype._events=void 0,EventEmitter.prototype._maxListeners=void 0,EventEmitter.defaultMaxListeners=10,EventEmitter.prototype.setMaxListeners=function(e){if(!isNumber(e)||e<0||isNaN(e))throw TypeError("n must be a positive number");return this._maxListeners=e,this},EventEmitter.prototype.emit=function(e){var t,i,n,s,r,o;if(this._events||(this._events={}),"error"===e&&(!this._events.error||isObject(this._events.error)&&!this._events.error.length)){if((t=arguments[1])instanceof Error)throw t;var h=new Error('Uncaught, unspecified "error" event. ('+t+")");throw h.context=t,h}if(i=this._events[e],isUndefined(i))return!1;if(isFunction(i))switch(arguments.length){case 1:i.call(this);break;case 2:i.call(this,arguments[1]);break;case 3:i.call(this,arguments[1],arguments[2]);break;default:s=Array.prototype.slice.call(arguments,1),i.apply(this,s)}else if(isObject(i))for(s=Array.prototype.slice.call(arguments,1),o=i.slice(),n=o.length,r=0;r<n;r++)o[r].apply(this,s);return!0},EventEmitter.prototype.addListener=function(e,t){var i;if(!isFunction(t))throw TypeError("listener must be a function");return this._events||(this._events={}),this._events.newListener&&this.emit("newListener",e,isFunction(t.listener)?t.listener:t),this._events[e]?isObject(this._events[e])?this._events[e].push(t):this._events[e]=[this._events[e],t]:this._events[e]=t,isObject(this._events[e])&&!this._events[e].warned&&(i=isUndefined(this._maxListeners)?EventEmitter.defaultMaxListeners:this._maxListeners)&&i>0&&this._events[e].length>i&&(this._events[e].warned=!0,console.error("(node) warning: possible EventEmitter memory leak detected. %d listeners added. Use emitter.setMaxListeners() to increase limit.",this._events[e].length),"function"==typeof console.trace&&console.trace()),this},EventEmitter.prototype.on=EventEmitter.prototype.addListener,EventEmitter.prototype.once=function(e,t){function i(){this.removeListener(e,i),n||(n=!0,t.apply(this,arguments))}if(!isFunction(t))throw TypeError("listener must be a function");var n=!1;return i.listener=t,this.on(e,i),this},EventEmitter.prototype.removeListener=function(e,t){var i,n,s,r;if(!isFunction(t))throw TypeError("listener must be a function");if(!this._events||!this._events[e])return this;if(i=this._events[e],s=i.length,n=-1,i===t||isFunction(i.listener)&&i.listener===t)delete this._events[e],this._events.removeListener&&this.emit("removeListener",e,t);else if(isObject(i)){for(r=s;r-- >0;)if(i[r]===t||i[r].listener&&i[r].listener===t){n=r;break}if(n<0)return this;1===i.length?(i.length=0,delete this._events[e]):i.splice(n,1),this._events.removeListener&&this.emit("removeListener",e,t)}return this},EventEmitter.prototype.removeAllListeners=function(e){var t,i;if(!this._events)return this;if(!this._events.removeListener)return 0===arguments.length?this._events={}:this._events[e]&&delete this._events[e],this;if(0===arguments.length){for(t in this._events)"removeListener"!==t&&this.removeAllListeners(t);return this.removeAllListeners("removeListener"),this._events={},this}if(i=this._events[e],isFunction(i))this.removeListener(e,i);else if(i)for(;i.length;)this.removeListener(e,i[i.length-1]);return delete this._events[e],this},EventEmitter.prototype.listeners=function(e){return this._events&&this._events[e]?isFunction(this._events[e])?[this._events[e]]:this._events[e].slice():[]},EventEmitter.prototype.listenerCount=function(e){if(this._events){var t=this._events[e];if(isFunction(t))return 1;if(t)return t.length}return 0},EventEmitter.listenerCount=function(e,t){return e.listenerCount(t)};

/***/ }),
/* 102 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
/* WEBPACK VAR INJECTION */(function(process, setImmediate, global) {function WriteReq(e,t,r){this.chunk=e,this.encoding=t,this.callback=r,this.next=null}function CorkedRequest(e){var t=this;this.next=null,this.entry=null,this.finish=function(){onCorkedFinish(t,e)}}function _uint8ArrayToBuffer(e){return Buffer.from(e)}function _isUint8Array(e){return Buffer.isBuffer(e)||e instanceof OurUint8Array}function nop(){}function WritableState(e,t){Duplex=Duplex||__webpack_require__(40),e=e||{},this.objectMode=!!e.objectMode,t instanceof Duplex&&(this.objectMode=this.objectMode||!!e.writableObjectMode);var r=e.highWaterMark,i=this.objectMode?16:16384;this.highWaterMark=r||0===r?r:i,this.highWaterMark=Math.floor(this.highWaterMark),this.finalCalled=!1,this.needDrain=!1,this.ending=!1,this.ended=!1,this.finished=!1,this.destroyed=!1;var n=!1===e.decodeStrings;this.decodeStrings=!n,this.defaultEncoding=e.defaultEncoding||"utf8",this.length=0,this.writing=!1,this.corked=0,this.sync=!0,this.bufferProcessing=!1,this.onwrite=function(e){onwrite(t,e)},this.writecb=null,this.writelen=0,this.bufferedRequest=null,this.lastBufferedRequest=null,this.pendingcb=0,this.prefinished=!1,this.errorEmitted=!1,this.bufferedRequestCount=0,this.corkedRequestsFree=new CorkedRequest(this)}function Writable(e){if(Duplex=Duplex||__webpack_require__(40),!(realHasInstance.call(Writable,this)||this instanceof Duplex))return new Writable(e);this._writableState=new WritableState(e,this),this.writable=!0,e&&("function"==typeof e.write&&(this._write=e.write),"function"==typeof e.writev&&(this._writev=e.writev),"function"==typeof e.destroy&&(this._destroy=e.destroy),"function"==typeof e.final&&(this._final=e.final)),Stream.call(this)}function writeAfterEnd(e,t){var r=new Error("write after end");e.emit("error",r),processNextTick(t,r)}function validChunk(e,t,r,i){var n=!0,o=!1;return null===r?o=new TypeError("May not write null values to stream"):"string"==typeof r||void 0===r||t.objectMode||(o=new TypeError("Invalid non-string/buffer chunk")),o&&(e.emit("error",o),processNextTick(i,o),n=!1),n}function decodeChunk(e,t,r){return e.objectMode||!1===e.decodeStrings||"string"!=typeof t||(t=Buffer.from(t,r)),t}function writeOrBuffer(e,t,r,i,n,o){if(!r){var s=decodeChunk(t,i,n);i!==s&&(r=!0,n="buffer",i=s)}var a=t.objectMode?1:i.length;t.length+=a;var f=t.length<t.highWaterMark;if(f||(t.needDrain=!0),t.writing||t.corked){var u=t.lastBufferedRequest;t.lastBufferedRequest={chunk:i,encoding:n,isBuf:r,callback:o,next:null},u?u.next=t.lastBufferedRequest:t.bufferedRequest=t.lastBufferedRequest,t.bufferedRequestCount+=1}else doWrite(e,t,!1,a,i,n,o);return f}function doWrite(e,t,r,i,n,o,s){t.writelen=i,t.writecb=s,t.writing=!0,t.sync=!0,r?e._writev(n,t.onwrite):e._write(n,o,t.onwrite),t.sync=!1}function onwriteError(e,t,r,i,n){--t.pendingcb,r?(processNextTick(n,i),processNextTick(finishMaybe,e,t),e._writableState.errorEmitted=!0,e.emit("error",i)):(n(i),e._writableState.errorEmitted=!0,e.emit("error",i),finishMaybe(e,t))}function onwriteStateUpdate(e){e.writing=!1,e.writecb=null,e.length-=e.writelen,e.writelen=0}function onwrite(e,t){var r=e._writableState,i=r.sync,n=r.writecb;if(onwriteStateUpdate(r),t)onwriteError(e,r,i,t,n);else{var o=needFinish(r);o||r.corked||r.bufferProcessing||!r.bufferedRequest||clearBuffer(e,r),i?asyncWrite(afterWrite,e,r,o,n):afterWrite(e,r,o,n)}}function afterWrite(e,t,r,i){r||onwriteDrain(e,t),t.pendingcb--,i(),finishMaybe(e,t)}function onwriteDrain(e,t){0===t.length&&t.needDrain&&(t.needDrain=!1,e.emit("drain"))}function clearBuffer(e,t){t.bufferProcessing=!0;var r=t.bufferedRequest;if(e._writev&&r&&r.next){var i=t.bufferedRequestCount,n=new Array(i),o=t.corkedRequestsFree;o.entry=r;for(var s=0,a=!0;r;)n[s]=r,r.isBuf||(a=!1),r=r.next,s+=1;n.allBuffers=a,doWrite(e,t,!0,t.length,n,"",o.finish),t.pendingcb++,t.lastBufferedRequest=null,o.next?(t.corkedRequestsFree=o.next,o.next=null):t.corkedRequestsFree=new CorkedRequest(t)}else{for(;r;){var f=r.chunk,u=r.encoding,l=r.callback;if(doWrite(e,t,!1,t.objectMode?1:f.length,f,u,l),r=r.next,t.writing)break}null===r&&(t.lastBufferedRequest=null)}t.bufferedRequestCount=0,t.bufferedRequest=r,t.bufferProcessing=!1}function needFinish(e){return e.ending&&0===e.length&&null===e.bufferedRequest&&!e.finished&&!e.writing}function callFinal(e,t){e._final(function(r){t.pendingcb--,r&&e.emit("error",r),t.prefinished=!0,e.emit("prefinish"),finishMaybe(e,t)})}function prefinish(e,t){t.prefinished||t.finalCalled||("function"==typeof e._final?(t.pendingcb++,t.finalCalled=!0,processNextTick(callFinal,e,t)):(t.prefinished=!0,e.emit("prefinish")))}function finishMaybe(e,t){var r=needFinish(t);return r&&(prefinish(e,t),0===t.pendingcb&&(t.finished=!0,e.emit("finish"))),r}function endWritable(e,t,r){t.ending=!0,finishMaybe(e,t),r&&(t.finished?processNextTick(r):e.once("finish",r)),t.ended=!0,e.writable=!1}function onCorkedFinish(e,t,r){var i=e.entry;for(e.entry=null;i;){var n=i.callback;t.pendingcb--,n(r),i=i.next}t.corkedRequestsFree?t.corkedRequestsFree.next=e:t.corkedRequestsFree=e}var processNextTick=__webpack_require__(72);module.exports=Writable;var asyncWrite=!process.browser&&["v0.10","v0.9."].indexOf(process.version.slice(0,5))>-1?setImmediate:processNextTick,Duplex;Writable.WritableState=WritableState;var util=__webpack_require__(47);util.inherits=__webpack_require__(39);var internalUtil={deprecate:__webpack_require__(206)},Stream=__webpack_require__(142),Buffer=__webpack_require__(100).Buffer,OurUint8Array=global.Uint8Array||function(){},destroyImpl=__webpack_require__(141);util.inherits(Writable,Stream),WritableState.prototype.getBuffer=function(){for(var e=this.bufferedRequest,t=[];e;)t.push(e),e=e.next;return t},function(){try{Object.defineProperty(WritableState.prototype,"buffer",{get:internalUtil.deprecate(function(){return this.getBuffer()},"_writableState.buffer is deprecated. Use _writableState.getBuffer instead.","DEP0003")})}catch(e){}}();var realHasInstance;"function"==typeof Symbol&&Symbol.hasInstance&&"function"==typeof Function.prototype[Symbol.hasInstance]?(realHasInstance=Function.prototype[Symbol.hasInstance],Object.defineProperty(Writable,Symbol.hasInstance,{value:function(e){return!!realHasInstance.call(this,e)||e&&e._writableState instanceof WritableState}})):realHasInstance=function(e){return e instanceof this},Writable.prototype.pipe=function(){this.emit("error",new Error("Cannot pipe, not readable"))},Writable.prototype.write=function(e,t,r){var i=this._writableState,n=!1,o=_isUint8Array(e)&&!i.objectMode;return o&&!Buffer.isBuffer(e)&&(e=_uint8ArrayToBuffer(e)),"function"==typeof t&&(r=t,t=null),o?t="buffer":t||(t=i.defaultEncoding),"function"!=typeof r&&(r=nop),i.ended?writeAfterEnd(this,r):(o||validChunk(this,i,e,r))&&(i.pendingcb++,n=writeOrBuffer(this,i,o,e,t,r)),n},Writable.prototype.cork=function(){this._writableState.corked++},Writable.prototype.uncork=function(){var e=this._writableState;e.corked&&(e.corked--,e.writing||e.corked||e.finished||e.bufferProcessing||!e.bufferedRequest||clearBuffer(this,e))},Writable.prototype.setDefaultEncoding=function(e){if("string"==typeof e&&(e=e.toLowerCase()),!(["hex","utf8","utf-8","ascii","binary","base64","ucs2","ucs-2","utf16le","utf-16le","raw"].indexOf((e+"").toLowerCase())>-1))throw new TypeError("Unknown encoding: "+e);return this._writableState.defaultEncoding=e,this},Writable.prototype._write=function(e,t,r){r(new Error("_write() is not implemented"))},Writable.prototype._writev=null,Writable.prototype.end=function(e,t,r){var i=this._writableState;"function"==typeof e?(r=e,e=null,t=null):"function"==typeof t&&(r=t,t=null),null!==e&&void 0!==e&&this.write(e,t),i.corked&&(i.corked=1,this.uncork()),i.ending||i.finished||endWritable(this,i,r)},Object.defineProperty(Writable.prototype,"destroyed",{get:function(){return void 0!==this._writableState&&this._writableState.destroyed},set:function(e){this._writableState&&(this._writableState.destroyed=e)}}),Writable.prototype.destroy=destroyImpl.destroy,Writable.prototype._undestroy=destroyImpl.undestroy,Writable.prototype._destroy=function(e,t){this.end(),t(e)};
/* WEBPACK VAR INJECTION */}.call(exports, __webpack_require__(28), __webpack_require__(193).setImmediate, __webpack_require__(27)))

/***/ }),
/* 103 */
/***/ (function(module, exports, __webpack_require__) {

exports=module.exports=__webpack_require__(139),exports.Stream=exports,exports.Readable=exports,exports.Writable=__webpack_require__(102),exports.Duplex=__webpack_require__(40),exports.Transform=__webpack_require__(140),exports.PassThrough=__webpack_require__(203);

/***/ }),
/* 104 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
var __extends=this&&this.__extends||function(){var e=Object.setPrototypeOf||{__proto__:[]}instanceof Array&&function(e,o){e.__proto__=o}||function(e,o){for(var i in o)o.hasOwnProperty(i)&&(e[i]=o[i])};return function(o,i){function t(){this.constructor=o}e(o,i),o.prototype=null===i?Object.create(i):(t.prototype=i.prototype,new t)}}();Object.defineProperty(exports,"__esModule",{value:!0});var inversify_1=__webpack_require__(0),lib_1=__webpack_require__(37),views_1=__webpack_require__(108),sprotty_model_1=__webpack_require__(107),FilteringSvgExporter=function(e){function o(){return null!==e&&e.apply(this,arguments)||this}return __extends(o,e),o.prototype.isExported=function(e){return null!==e.href&&(e.href.endsWith("diagram.css")||e.href.endsWith("sprotty.css"))},o}(lib_1.SvgExporter);exports.default=function(){var e=new inversify_1.ContainerModule(function(e,o,i,t){t(lib_1.TYPES.ILogger).to(lib_1.ConsoleLogger).inSingletonScope(),t(lib_1.TYPES.LogLevel).toConstantValue(lib_1.LogLevel.warn),t(lib_1.TYPES.IModelFactory).to(lib_1.SGraphFactory).inSingletonScope(),t(lib_1.TYPES.SvgExporter).to(FilteringSvgExporter).inSingletonScope();var r={bind:e,unbind:o,isBound:i,rebind:t};lib_1.configureModelElement(r,"graph",lib_1.SGraph,lib_1.SGraphView),lib_1.configureModelElement(r,"node",sprotty_model_1.ElkNode,views_1.ElkNodeView),lib_1.configureModelElement(r,"port",sprotty_model_1.ElkPort,views_1.ElkPortView),lib_1.configureModelElement(r,"edge",sprotty_model_1.ElkEdge,views_1.ElkEdgeView),lib_1.configureModelElement(r,"label",lib_1.SLabel,views_1.ElkLabelView),lib_1.configureModelElement(r,"junction",sprotty_model_1.ElkJunction,views_1.JunctionView),lib_1.configureViewerOptions(r,{needsClientLayout:!1})}),o=new inversify_1.Container;return o.load(lib_1.defaultModule,lib_1.selectModule,lib_1.boundsModule,lib_1.moveModule,lib_1.fadeModule,lib_1.hoverModule,lib_1.viewportModule,lib_1.exportModule,lib_1.edgeEditModule,e),o};

/***/ }),
/* 105 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
function getParameters(){for(var e=window.location.search.substring(1),r={};e.length>0;){var t=e.indexOf("&"),n=void 0;t<0?(n=e,e=""):(n=e.substring(0,t),e=e.substring(t+1));var s=n.indexOf("=");s>0&&s<n.length-1&&(r[n.substring(0,s)]=n.substring(s+1))}return r}function setupModelLink(e,r){var t,n=function(e){void 0!==t&&window.clearTimeout(t),t=window.setTimeout(function(){var t=document.getElementById("model-link");if(null!==t){var n=r(e),s=assembleHref(n);t.setAttribute("href",s)}},400)};e.onDidChangeModelContent(n),n({})}function assembleHref(e){var r=window.location.href,t=r.indexOf("?");return t>0&&(r=r.substring(0,t)),r+=combineParameters(e)}function combineParameters(e){var r="",t=0;for(var n in e)r+=(0===t?"?":"&")+n+"="+e[n],t++;return r}Object.defineProperty(exports,"__esModule",{value:!0}),exports.getParameters=getParameters,exports.setupModelLink=setupModelLink,exports.assembleHref=assembleHref,exports.combineParameters=combineParameters;

/***/ }),
/* 106 */
/***/ (function(module, exports, __webpack_require__) {

/* WEBPACK VAR INJECTION */(function(process, global) {var Reflect;!function(t){"use strict";function e(t,e,r,n){if(m(r)){if(!P(t))throw new TypeError;if(!I(e))throw new TypeError;return h(t,e)}if(!P(t))throw new TypeError;if(!O(e))throw new TypeError;if(!O(n)&&!m(n)&&!E(n))throw new TypeError;return E(n)&&(n=void 0),r=S(r),p(t,e,r,n)}function r(t,e){function r(r,n){if(!O(r))throw new TypeError;if(!m(n)&&!K(n))throw new TypeError;w(t,e,r,n)}return r}function n(t,e,r,n){if(!O(r))throw new TypeError;return m(n)||(n=S(n)),w(t,e,r,n)}function i(t,e,r){if(!O(e))throw new TypeError;return m(r)||(r=S(r)),l(t,e,r)}function o(t,e,r){if(!O(e))throw new TypeError;return m(r)||(r=S(r)),v(t,e,r)}function u(t,e,r){if(!O(e))throw new TypeError;return m(r)||(r=S(r)),_(t,e,r)}function f(t,e,r){if(!O(e))throw new TypeError;return m(r)||(r=S(r)),d(t,e,r)}function a(t,e){if(!O(t))throw new TypeError;return m(e)||(e=S(e)),g(t,e)}function c(t,e){if(!O(t))throw new TypeError;return m(e)||(e=S(e)),k(t,e)}function s(t,e,r){if(!O(e))throw new TypeError;m(r)||(r=S(r));var n=y(e,r,!1);if(m(n))return!1;if(!n.delete(t))return!1;if(n.size>0)return!0;var i=Z.get(e);return i.delete(r),i.size>0||(Z.delete(e),!0)}function h(t,e){for(var r=t.length-1;r>=0;--r){var n=t[r],i=n(e);if(!m(i)&&!E(i)){if(!I(i))throw new TypeError;e=i}}return e}function p(t,e,r,n){for(var i=t.length-1;i>=0;--i){var o=t[i],u=o(e,r,n);if(!m(u)&&!E(u)){if(!O(u))throw new TypeError;n=u}}return n}function y(t,e,r){var n=Z.get(t);if(m(n)){if(!r)return;n=new N,Z.set(t,n)}var i=n.get(e);if(m(i)){if(!r)return;i=new N,n.set(e,i)}return i}function l(t,e,r){if(v(t,e,r))return!0;var n=W(e);return!E(n)&&l(t,n,r)}function v(t,e,r){var n=y(e,r,!1);return!m(n)&&j(n.has(t))}function _(t,e,r){if(v(t,e,r))return d(t,e,r);var n=W(e);return E(n)?void 0:_(t,n,r)}function d(t,e,r){var n=y(e,r,!1);if(!m(n))return n.get(t)}function w(t,e,r,n){y(r,n,!0).set(t,e)}function g(t,e){var r=k(t,e),n=W(t);if(null===n)return r;var i=g(n,e);if(i.length<=0)return r;if(r.length<=0)return i;for(var o=new Q,u=[],f=0,a=r;f<a.length;f++){var c=a[f],s=o.has(c);s||(o.add(c),u.push(c))}for(var h=0,p=i;h<p.length;h++){var c=p[h],s=o.has(c);s||(o.add(c),u.push(c))}return u}function k(t,e){var r=[],n=y(t,e,!1);if(m(n))return r;for(var i=n.keys(),o=L(i),u=0;;){var f=C(o);if(!f)return r.length=u,r;var a=U(f);try{r[u]=a}catch(t){try{F(o)}finally{throw t}}u++}}function b(t){if(null===t)return 1;switch(typeof t){case"undefined":return 0;case"boolean":return 2;case"string":return 3;case"symbol":return 4;case"number":return 5;case"object":return null===t?1:6;default:return 6}}function m(t){return void 0===t}function E(t){return null===t}function T(t){return"symbol"==typeof t}function O(t){return"object"==typeof t?null!==t:"function"==typeof t}function x(t,e){switch(b(t)){case 0:case 1:case 2:case 3:case 4:case 5:return t}var r=3===e?"string":5===e?"number":"default",n=z(t,B);if(void 0!==n){var i=n.call(t,r);if(O(i))throw new TypeError;return i}return M(t,"default"===r?"number":r)}function M(t,e){if("string"===e){var r=t.toString;if(R(r)){var n=r.call(t);if(!O(n))return n}var i=t.valueOf;if(R(i)){var n=i.call(t);if(!O(n))return n}}else{var i=t.valueOf;if(R(i)){var n=i.call(t);if(!O(n))return n}var o=t.toString;if(R(o)){var n=o.call(t);if(!O(n))return n}}throw new TypeError}function j(t){return!!t}function A(t){return""+t}function S(t){var e=x(t,3);return T(e)?e:A(e)}function P(t){return Array.isArray?Array.isArray(t):t instanceof Object?t instanceof Array:"[object Array]"===Object.prototype.toString.call(t)}function R(t){return"function"==typeof t}function I(t){return"function"==typeof t}function K(t){switch(b(t)){case 3:case 4:return!0;default:return!1}}function z(t,e){var r=t[e];if(void 0!==r&&null!==r){if(!R(r))throw new TypeError;return r}}function L(t){var e=z(t,G);if(!R(e))throw new TypeError;var r=e.call(t);if(!O(r))throw new TypeError;return r}function U(t){return t.value}function C(t){var e=t.next();return!e.done&&e}function F(t){var e=t.return;e&&e.call(t)}function W(t){var e=Object.getPrototypeOf(t);if("function"!=typeof t||t===H)return e;if(e!==H)return e;var r=t.prototype,n=r&&Object.getPrototypeOf(r);if(null==n||n===Object.prototype)return e;var i=n.constructor;return"function"!=typeof i?e:i===t?e:i}function V(t){return t.__=void 0,delete t.__,t}var D,Y=Object.prototype.hasOwnProperty,q="function"==typeof Symbol,B=q&&void 0!==Symbol.toPrimitive?Symbol.toPrimitive:"@@toPrimitive",G=q&&void 0!==Symbol.iterator?Symbol.iterator:"@@iterator";!function(t){var e="function"==typeof Object.create,r={__proto__:[]}instanceof Array,n=!e&&!r;t.create=e?function(){return V(Object.create(null))}:r?function(){return V({__proto__:null})}:function(){return V({})},t.has=n?function(t,e){return Y.call(t,e)}:function(t,e){return e in t},t.get=n?function(t,e){return Y.call(t,e)?t[e]:void 0}:function(t,e){return t[e]}}(D||(D={}));var H=Object.getPrototypeOf(Function),J="object"==typeof process&&process.env&&"true"===process.env.REFLECT_METADATA_USE_MAP_POLYFILL,N=J||"function"!=typeof Map||"function"!=typeof Map.prototype.entries?function(){function t(t,e){return t}function e(t,e){return e}function r(t,e){return[t,e]}var n={},i=[],o=function(){function t(t,e,r){this._index=0,this._keys=t,this._values=e,this._selector=r}return t.prototype["@@iterator"]=function(){return this},t.prototype[G]=function(){return this},t.prototype.next=function(){var t=this._index;if(t>=0&&t<this._keys.length){var e=this._selector(this._keys[t],this._values[t]);return t+1>=this._keys.length?(this._index=-1,this._keys=i,this._values=i):this._index++,{value:e,done:!1}}return{value:void 0,done:!0}},t.prototype.throw=function(t){throw this._index>=0&&(this._index=-1,this._keys=i,this._values=i),t},t.prototype.return=function(t){return this._index>=0&&(this._index=-1,this._keys=i,this._values=i),{value:t,done:!0}},t}();return function(){function i(){this._keys=[],this._values=[],this._cacheKey=n,this._cacheIndex=-2}return Object.defineProperty(i.prototype,"size",{get:function(){return this._keys.length},enumerable:!0,configurable:!0}),i.prototype.has=function(t){return this._find(t,!1)>=0},i.prototype.get=function(t){var e=this._find(t,!1);return e>=0?this._values[e]:void 0},i.prototype.set=function(t,e){var r=this._find(t,!0);return this._values[r]=e,this},i.prototype.delete=function(t){var e=this._find(t,!1);if(e>=0){for(var r=this._keys.length,i=e+1;i<r;i++)this._keys[i-1]=this._keys[i],this._values[i-1]=this._values[i];return this._keys.length--,this._values.length--,t===this._cacheKey&&(this._cacheKey=n,this._cacheIndex=-2),!0}return!1},i.prototype.clear=function(){this._keys.length=0,this._values.length=0,this._cacheKey=n,this._cacheIndex=-2},i.prototype.keys=function(){return new o(this._keys,this._values,t)},i.prototype.values=function(){return new o(this._keys,this._values,e)},i.prototype.entries=function(){return new o(this._keys,this._values,r)},i.prototype["@@iterator"]=function(){return this.entries()},i.prototype[G]=function(){return this.entries()},i.prototype._find=function(t,e){return this._cacheKey!==t&&(this._cacheIndex=this._keys.indexOf(this._cacheKey=t)),this._cacheIndex<0&&e&&(this._cacheIndex=this._keys.length,this._keys.push(t),this._values.push(void 0)),this._cacheIndex},i}()}():Map,Q=J||"function"!=typeof Set||"function"!=typeof Set.prototype.entries?function(){return function(){function t(){this._map=new N}return Object.defineProperty(t.prototype,"size",{get:function(){return this._map.size},enumerable:!0,configurable:!0}),t.prototype.has=function(t){return this._map.has(t)},t.prototype.add=function(t){return this._map.set(t,t),this},t.prototype.delete=function(t){return this._map.delete(t)},t.prototype.clear=function(){this._map.clear()},t.prototype.keys=function(){return this._map.keys()},t.prototype.values=function(){return this._map.values()},t.prototype.entries=function(){return this._map.entries()},t.prototype["@@iterator"]=function(){return this.keys()},t.prototype[G]=function(){return this.keys()},t}()}():Set,X=J||"function"!=typeof WeakMap?function(){function t(){var t;do{t="@@WeakMap@@"+i()}while(D.has(u,t));return u[t]=!0,t}function e(t,e){if(!Y.call(t,f)){if(!e)return;Object.defineProperty(t,f,{value:D.create()})}return t[f]}function r(t,e){for(var r=0;r<e;++r)t[r]=255*Math.random()|0;return t}function n(t){return"function"==typeof Uint8Array?"undefined"!=typeof crypto?crypto.getRandomValues(new Uint8Array(t)):"undefined"!=typeof msCrypto?msCrypto.getRandomValues(new Uint8Array(t)):r(new Uint8Array(t),t):r(new Array(t),t)}function i(){var t=n(o);t[6]=79&t[6]|64,t[8]=191&t[8]|128;for(var e="",r=0;r<o;++r){var i=t[r];4!==r&&6!==r&&8!==r||(e+="-"),i<16&&(e+="0"),e+=i.toString(16).toLowerCase()}return e}var o=16,u=D.create(),f=t();return function(){function r(){this._key=t()}return r.prototype.has=function(t){var r=e(t,!1);return void 0!==r&&D.has(r,this._key)},r.prototype.get=function(t){var r=e(t,!1);return void 0!==r?D.get(r,this._key):void 0},r.prototype.set=function(t,r){return e(t,!0)[this._key]=r,this},r.prototype.delete=function(t){var r=e(t,!1);return void 0!==r&&delete r[this._key]},r.prototype.clear=function(){this._key=t()},r}()}():WeakMap,Z=new X;t.decorate=e,t.metadata=r,t.defineMetadata=n,t.hasMetadata=i,t.hasOwnMetadata=o,t.getMetadata=u,t.getOwnMetadata=f,t.getMetadataKeys=a,t.getOwnMetadataKeys=c,t.deleteMetadata=s,function(e){if(void 0!==e.Reflect){if(e.Reflect!==t)for(var r in t)Y.call(t,r)&&(e.Reflect[r]=t[r])}else e.Reflect=t}("undefined"!=typeof global?global:"undefined"!=typeof self?self:Function("return this;")())}(Reflect||(Reflect={}));
/* WEBPACK VAR INJECTION */}.call(exports, __webpack_require__(28), __webpack_require__(27)))

/***/ }),
/* 107 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
var __extends=this&&this.__extends||function(){var t=Object.setPrototypeOf||{__proto__:[]}instanceof Array&&function(t,e){t.__proto__=e}||function(t,e){for(var r in e)e.hasOwnProperty(r)&&(t[r]=e[r])};return function(e,r){function o(){this.constructor=e}t(e,r),e.prototype=null===r?Object.create(r):(o.prototype=r.prototype,new o)}}();Object.defineProperty(exports,"__esModule",{value:!0});var lib_1=__webpack_require__(37),ElkNode=function(t){function e(){return null!==t&&t.apply(this,arguments)||this}return __extends(e,t),e.prototype.hasFeature=function(e){return e!==lib_1.moveFeature&&t.prototype.hasFeature.call(this,e)},e}(lib_1.RectangularNode);exports.ElkNode=ElkNode;var ElkPort=function(t){function e(){return null!==t&&t.apply(this,arguments)||this}return __extends(e,t),e.prototype.hasFeature=function(e){return e!==lib_1.moveFeature&&t.prototype.hasFeature.call(this,e)},e}(lib_1.RectangularPort);exports.ElkPort=ElkPort;var ElkEdge=function(t){function e(){return null!==t&&t.apply(this,arguments)||this}return __extends(e,t),e.prototype.hasFeature=function(e){return e!==lib_1.editFeature&&t.prototype.hasFeature.call(this,e)},e}(lib_1.SEdge);exports.ElkEdge=ElkEdge;var ElkJunction=function(t){function e(){return null!==t&&t.apply(this,arguments)||this}return __extends(e,t),e.prototype.hasFeature=function(e){return e!==lib_1.moveFeature&&e!==lib_1.selectFeature&&e!==lib_1.hoverFeedbackFeature&&t.prototype.hasFeature.call(this,e)},e}(lib_1.SNode);exports.ElkJunction=ElkJunction;

/***/ }),
/* 108 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
var __extends=this&&this.__extends||function(){var e=Object.setPrototypeOf||{__proto__:[]}instanceof Array&&function(e,t){e.__proto__=t}||function(e,t){for(var r in t)t.hasOwnProperty(r)&&(e[r]=t[r])};return function(t,r){function n(){this.constructor=t}e(t,r),t.prototype=null===r?Object.create(r):(n.prototype=r.prototype,new n)}}();Object.defineProperty(exports,"__esModule",{value:!0});var snabbdom=__webpack_require__(18),lib_1=__webpack_require__(37),JSX={createElement:snabbdom.svg},ElkNodeView=function(e){function t(){return null!==e&&e.apply(this,arguments)||this}return __extends(t,e),t.prototype.render=function(e,t){return JSX.createElement("g",null,JSX.createElement("rect",{"class-elknode":!0,"class-mouseover":e.hoverFeedback,"class-selected":e.selected,x:"0",y:"0",width:e.bounds.width,height:e.bounds.height}),t.renderChildren(e))},t}(lib_1.RectangularNodeView);exports.ElkNodeView=ElkNodeView;var ElkPortView=function(e){function t(){return null!==e&&e.apply(this,arguments)||this}return __extends(t,e),t.prototype.render=function(e,t){return JSX.createElement("g",null,JSX.createElement("rect",{"class-elkport":!0,"class-mouseover":e.hoverFeedback,"class-selected":e.selected,x:"0",y:"0",width:e.bounds.width,height:e.bounds.height}),t.renderChildren(e))},t}(lib_1.RectangularNodeView);exports.ElkPortView=ElkPortView;var ElkEdgeView=function(e){function t(){return null!==e&&e.apply(this,arguments)||this}return __extends(t,e),t.prototype.renderLine=function(e,t,r){for(var n=t[0],o="M "+n.x+","+n.y,l=1;l<t.length;l++){var i=t[l];o+=" L "+i.x+","+i.y}return JSX.createElement("path",{"class-elkedge":!0,d:o})},t.prototype.renderAdditionals=function(e,t,r){var n=t[t.length-2],o=t[t.length-1];return[JSX.createElement("path",{"class-edge":!0,"class-arrow":!0,d:"M 0,0 L 8,-3 L 8,3 Z",transform:"rotate("+lib_1.toDegrees(lib_1.angleOfPoint({x:n.x-o.x,y:n.y-o.y}))+" "+o.x+" "+o.y+") translate("+o.x+" "+o.y+")"})]},t}(lib_1.PolylineEdgeView);exports.ElkEdgeView=ElkEdgeView;var ElkLabelView=function(){function e(){}return e.prototype.render=function(e,t){return JSX.createElement("text",{"class-elklabel":!0},e.text)},e}();exports.ElkLabelView=ElkLabelView;var JunctionView=function(e){function t(){return null!==e&&e.apply(this,arguments)||this}return __extends(t,e),t.prototype.render=function(e,t){var r=this.getRadius(e);return JSX.createElement("g",null,JSX.createElement("circle",{"class-elkjunction":!0,r:r}))},t.prototype.getRadius=function(e){return 2},t}(lib_1.CircularNodeView);exports.JunctionView=JunctionView;

/***/ }),
/* 109 */
/***/ (function(module, exports, __webpack_require__) {

var attrRE=/([\w-]+)|=|(['"])([.\s\S]*?)\2/g,voidElements=__webpack_require__(187);module.exports=function(e){var t,r=0,n=!0,a={type:"tag",name:"",voidElement:!1,attrs:{},children:[]};return e.replace(attrRE,function(i){if("="===i)return n=!0,void r++;n?0===r?((voidElements[i]||"/"===e.charAt(e.length-2))&&(a.voidElement=!0),a.name=i):(a.attrs[t]=i.replace(/^['"]|['"]$/g,""),t=void 0):(t&&(a.attrs[t]=t),t=i),r++,n=!1}),a};

/***/ }),
/* 110 */
/***/ (function(module, exports, __webpack_require__) {

function pushTextNode(e,t,n,a,r){var o=t.indexOf("<",a),p=t.slice(a,-1===o?void 0:o);/^\s*$/.test(p)&&(p=" "),(!r&&o>-1&&n+e.length>=0||" "!==p)&&e.push({type:"text",content:p})}var tagRE=/(?:<!--[\S\s]*?-->|<(?:"[^"]*"['"]*|'[^']*'['"]*|[^'">])+>)/g,parseTag=__webpack_require__(109),empty=Object.create?Object.create(null):{};module.exports=function(e,t){t||(t={}),t.components||(t.components=empty);var n,a=[],r=-1,o=[],p={},c=!1;return e.replace(tagRE,function(i,s){if(c){if(i!=="</"+n.name+">")return;c=!1}var h,g="/"!==i.charAt(1),u=0===i.indexOf("\x3c!--"),l=s+i.length,d=e.charAt(l);g&&!u&&(r++,n=parseTag(i),"tag"===n.type&&t.components[n.name]&&(n.type="component",c=!0),n.voidElement||c||!d||"<"===d||pushTextNode(n.children,e,r,l,t.ignoreWhitespace),p[n.tagName]=n,0===r&&a.push(n),h=o[r-1],h&&h.children.push(n),o[r]=n),(u||!g||n.voidElement)&&(u||r--,!c&&"<"!==d&&d&&(h=-1===r?a:o[r].children,pushTextNode(h,e,r,l,t.ignoreWhitespace)))}),!a.length&&e.length&&pushTextNode(a,e,0,0,t.ignoreWhitespace),a};

/***/ }),
/* 111 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
function inject(t){return function(e,a,r){var o=new metadata_1.Metadata(METADATA_KEY.INJECT_TAG,t);"number"==typeof r?decorator_utils_1.tagParameter(e,a,r,o):decorator_utils_1.tagProperty(e,a,o)}}Object.defineProperty(exports,"__esModule",{value:!0});var metadata_1=__webpack_require__(9),decorator_utils_1=__webpack_require__(13),METADATA_KEY=__webpack_require__(4);exports.inject=inject;

/***/ }),
/* 112 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
function injectable(){return function(e){if(!0===Reflect.hasOwnMetadata(METADATA_KEY.PARAM_TYPES,e))throw new Error(ERRORS_MSGS.DUPLICATED_INJECTABLE_DECORATOR);var t=Reflect.getMetadata(METADATA_KEY.DESIGN_PARAM_TYPES,e)||[];return Reflect.defineMetadata(METADATA_KEY.PARAM_TYPES,t,e),e}}Object.defineProperty(exports,"__esModule",{value:!0});var METADATA_KEY=__webpack_require__(4),ERRORS_MSGS=__webpack_require__(8);exports.injectable=injectable;

/***/ }),
/* 113 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
function multiInject(t){return function(e,a,r){var o=new metadata_1.Metadata(METADATA_KEY.MULTI_INJECT_TAG,t);"number"==typeof r?decorator_utils_1.tagParameter(e,a,r,o):decorator_utils_1.tagProperty(e,a,o)}}Object.defineProperty(exports,"__esModule",{value:!0});var metadata_1=__webpack_require__(9),decorator_utils_1=__webpack_require__(13),METADATA_KEY=__webpack_require__(4);exports.multiInject=multiInject;

/***/ }),
/* 114 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
function named(e){return function(t,a,r){var o=new metadata_1.Metadata(METADATA_KEY.NAMED_TAG,e);"number"==typeof r?decorator_utils_1.tagParameter(t,a,r,o):decorator_utils_1.tagProperty(t,a,o)}}Object.defineProperty(exports,"__esModule",{value:!0});var metadata_1=__webpack_require__(9),decorator_utils_1=__webpack_require__(13),METADATA_KEY=__webpack_require__(4);exports.named=named;

/***/ }),
/* 115 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
function optional(){return function(t,e,a){var r=new metadata_1.Metadata(METADATA_KEY.OPTIONAL_TAG,!0);"number"==typeof a?decorator_utils_1.tagParameter(t,e,a,r):decorator_utils_1.tagProperty(t,e,r)}}Object.defineProperty(exports,"__esModule",{value:!0});var metadata_1=__webpack_require__(9),decorator_utils_1=__webpack_require__(13),METADATA_KEY=__webpack_require__(4);exports.optional=optional;

/***/ }),
/* 116 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
function postConstruct(){return function(t,e,r){var a=new metadata_1.Metadata(METADATA_KEY.POST_CONSTRUCT,e);if(Reflect.hasOwnMetadata(METADATA_KEY.POST_CONSTRUCT,t.constructor))throw new Error(ERRORS_MSGS.MULTIPLE_POST_CONSTRUCT_METHODS);Reflect.defineMetadata(METADATA_KEY.POST_CONSTRUCT,a,t.constructor)}}Object.defineProperty(exports,"__esModule",{value:!0});var metadata_1=__webpack_require__(9),METADATA_KEY=__webpack_require__(4),ERRORS_MSGS=__webpack_require__(8);exports.postConstruct=postConstruct;

/***/ }),
/* 117 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
function tagged(t,e){return function(a,r,o){var d=new metadata_1.Metadata(t,e);"number"==typeof o?decorator_utils_1.tagParameter(a,r,o,d):decorator_utils_1.tagProperty(a,r,d)}}Object.defineProperty(exports,"__esModule",{value:!0});var metadata_1=__webpack_require__(9),decorator_utils_1=__webpack_require__(13);exports.tagged=tagged;

/***/ }),
/* 118 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
function targetName(e){return function(t,a,r){var o=new metadata_1.Metadata(METADATA_KEY.NAME_TAG,e);decorator_utils_1.tagParameter(t,a,r,o)}}Object.defineProperty(exports,"__esModule",{value:!0});var metadata_1=__webpack_require__(9),decorator_utils_1=__webpack_require__(13),METADATA_KEY=__webpack_require__(4);exports.targetName=targetName;

/***/ }),
/* 119 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
function unmanaged(){return function(a,e,t){var r=new metadata_1.Metadata(METADATA_KEY.UNMANAGED_TAG,!0);decorator_utils_1.tagParameter(a,e,t,r)}}Object.defineProperty(exports,"__esModule",{value:!0});var metadata_1=__webpack_require__(9),decorator_utils_1=__webpack_require__(13),METADATA_KEY=__webpack_require__(4);exports.unmanaged=unmanaged;

/***/ }),
/* 120 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
Object.defineProperty(exports,"__esModule",{value:!0});var guid_1=__webpack_require__(17),literal_types_1=__webpack_require__(14),Binding=function(){function i(i,t){this.guid=guid_1.guid(),this.activated=!1,this.serviceIdentifier=i,this.scope=t,this.type=literal_types_1.BindingTypeEnum.Invalid,this.constraint=function(i){return!0},this.implementationType=null,this.cache=null,this.factory=null,this.provider=null,this.onActivation=null,this.dynamicValue=null}return i.prototype.clone=function(){var t=new i(this.serviceIdentifier,this.scope);return t.activated=!1,t.implementationType=this.implementationType,t.dynamicValue=this.dynamicValue,t.scope=this.scope,t.type=this.type,t.factory=this.factory,t.provider=this.provider,t.constraint=this.constraint,t.onActivation=this.onActivation,t.cache=this.cache,t},i}();exports.Binding=Binding;

/***/ }),
/* 121 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
Object.defineProperty(exports,"__esModule",{value:!0});var BindingCount={MultipleBindingsAvailable:2,NoBindingsAvailable:0,OnlyOneBindingAvailable:1};exports.BindingCount=BindingCount;

/***/ }),
/* 122 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
Object.defineProperty(exports,"__esModule",{value:!0});var binding_1=__webpack_require__(120),lookup_1=__webpack_require__(125),planner_1=__webpack_require__(128),resolver_1=__webpack_require__(133),binding_to_syntax_1=__webpack_require__(136),serialization_1=__webpack_require__(29),container_snapshot_1=__webpack_require__(124),guid_1=__webpack_require__(17),ERROR_MSGS=__webpack_require__(8),METADATA_KEY=__webpack_require__(4),literal_types_1=__webpack_require__(14),metadata_reader_1=__webpack_require__(74),Container=function(){function t(t){if(void 0!==t){if("object"!=typeof t)throw new Error(""+ERROR_MSGS.CONTAINER_OPTIONS_MUST_BE_AN_OBJECT);if(void 0===t.defaultScope)throw new Error(""+ERROR_MSGS.CONTAINER_OPTIONS_INVALID_DEFAULT_SCOPE);if(t.defaultScope!==literal_types_1.BindingScopeEnum.Singleton&&t.defaultScope!==literal_types_1.BindingScopeEnum.Transient)throw new Error(""+ERROR_MSGS.CONTAINER_OPTIONS_INVALID_DEFAULT_SCOPE);this.options={defaultScope:t.defaultScope}}else this.options={defaultScope:literal_types_1.BindingScopeEnum.Transient};this.guid=guid_1.guid(),this._bindingDictionary=new lookup_1.Lookup,this._snapshots=[],this._middleware=null,this.parent=null,this._metadataReader=new metadata_reader_1.MetadataReader}return t.merge=function(n,e){function i(t,n){t.traverse(function(t,e){e.forEach(function(t){n.add(t.serviceIdentifier,t.clone())})})}var r=new t,o=planner_1.getBindingDictionary(r),a=planner_1.getBindingDictionary(n),u=planner_1.getBindingDictionary(e);return i(a,o),i(u,o),r},t.prototype.load=function(){for(var t=this,n=[],e=0;e<arguments.length;e++)n[e]=arguments[e];var i=function(t,n){t._binding.moduleId=n},r=function(n){return function(e){var r=t.bind.call(t,e);return i(r,n),r}},o=function(n){return function(n){t.unbind.bind(t)(n)}},a=function(n){return function(n){return t.isBound.bind(t)(n)}},u=function(n){return function(e){var r=t.rebind.call(t,e);return i(r,n),r}};n.forEach(function(t){var n=r(t.guid),e=o(t.guid),i=a(t.guid),s=u(t.guid);t.registry(n,e,i,s)})},t.prototype.unload=function(){for(var t=this,n=[],e=0;e<arguments.length;e++)n[e]=arguments[e];var i=function(t){return function(n){return n.moduleId===t}};n.forEach(function(n){var e=i(n.guid);t._bindingDictionary.removeByCondition(e)})},t.prototype.bind=function(t){var n=literal_types_1.BindingScopeEnum.Transient;n=this.options.defaultScope===n?n:literal_types_1.BindingScopeEnum.Singleton;var e=new binding_1.Binding(t,n);return this._bindingDictionary.add(t,e),new binding_to_syntax_1.BindingToSyntax(e)},t.prototype.rebind=function(t){return this.unbind(t),this.bind(t)},t.prototype.unbind=function(t){try{this._bindingDictionary.remove(t)}catch(n){throw new Error(ERROR_MSGS.CANNOT_UNBIND+" "+serialization_1.getServiceIdentifierAsString(t))}},t.prototype.unbindAll=function(){this._bindingDictionary=new lookup_1.Lookup},t.prototype.isBound=function(t){var n=this._bindingDictionary.hasKey(t);return!n&&this.parent&&(n=this.parent.isBound(t)),n},t.prototype.isBoundNamed=function(t,n){return this.isBoundTagged(t,METADATA_KEY.NAMED_TAG,n)},t.prototype.isBoundTagged=function(t,n,e){var i=!1;if(this._bindingDictionary.hasKey(t)){var r=this._bindingDictionary.get(t),o=planner_1.createMockRequest(this,t,n,e);i=r.some(function(t){return t.constraint(o)})}return!i&&this.parent&&(i=this.parent.isBoundTagged(t,n,e)),i},t.prototype.snapshot=function(){this._snapshots.push(container_snapshot_1.ContainerSnapshot.of(this._bindingDictionary.clone(),this._middleware))},t.prototype.restore=function(){var t=this._snapshots.pop();if(void 0===t)throw new Error(ERROR_MSGS.NO_MORE_SNAPSHOTS_AVAILABLE);this._bindingDictionary=t.bindings,this._middleware=t.middleware},t.prototype.createChild=function(){var n=new t;return n.parent=this,n},t.prototype.applyMiddleware=function(){for(var t=[],n=0;n<arguments.length;n++)t[n]=arguments[n];var e=this._middleware?this._middleware:this._planAndResolve();this._middleware=t.reduce(function(t,n){return n(t)},e)},t.prototype.applyCustomMetadataReader=function(t){this._metadataReader=t},t.prototype.get=function(t){return this._get(!1,!1,literal_types_1.TargetTypeEnum.Variable,t)},t.prototype.getTagged=function(t,n,e){return this._get(!1,!1,literal_types_1.TargetTypeEnum.Variable,t,n,e)},t.prototype.getNamed=function(t,n){return this.getTagged(t,METADATA_KEY.NAMED_TAG,n)},t.prototype.getAll=function(t){return this._get(!0,!0,literal_types_1.TargetTypeEnum.Variable,t)},t.prototype.getAllTagged=function(t,n,e){return this._get(!1,!0,literal_types_1.TargetTypeEnum.Variable,t,n,e)},t.prototype.getAllNamed=function(t,n){return this.getAllTagged(t,METADATA_KEY.NAMED_TAG,n)},t.prototype.resolve=function(n){var e=new t;return e.bind(n).toSelf(),e.parent=this,e.get(n)},t.prototype._get=function(t,n,e,i,r,o){var a=null,u={avoidConstraints:t,contextInterceptor:function(t){return t},isMultiInject:n,key:r,serviceIdentifier:i,targetType:e,value:o};if(this._middleware){if(void 0===(a=this._middleware(u))||null===a)throw new Error(ERROR_MSGS.INVALID_MIDDLEWARE_RETURN)}else a=this._planAndResolve()(u);return a},t.prototype._planAndResolve=function(){var t=this;return function(n){var e=planner_1.plan(t._metadataReader,t,n.isMultiInject,n.targetType,n.serviceIdentifier,n.key,n.value,n.avoidConstraints);return e=n.contextInterceptor(e),resolver_1.resolve(e)}},t}();exports.Container=Container;

/***/ }),
/* 123 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
Object.defineProperty(exports,"__esModule",{value:!0});var guid_1=__webpack_require__(17),ContainerModule=function(){function e(e){this.guid=guid_1.guid(),this.registry=e}return e}();exports.ContainerModule=ContainerModule;

/***/ }),
/* 124 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
Object.defineProperty(exports,"__esModule",{value:!0});var ContainerSnapshot=function(){function n(){}return n.of=function(e,t){var r=new n;return r.bindings=e,r.middleware=t,r},n}();exports.ContainerSnapshot=ContainerSnapshot;

/***/ }),
/* 125 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
Object.defineProperty(exports,"__esModule",{value:!0});var ERROR_MSGS=__webpack_require__(8),Lookup=function(){function t(){this._map=new Map}return t.prototype.getMap=function(){return this._map},t.prototype.add=function(t,r){if(null===t||void 0===t)throw new Error(ERROR_MSGS.NULL_ARGUMENT);if(null===r||void 0===r)throw new Error(ERROR_MSGS.NULL_ARGUMENT);var o=this._map.get(t);void 0!==o?(o.push(r),this._map.set(t,o)):this._map.set(t,[r])},t.prototype.get=function(t){if(null===t||void 0===t)throw new Error(ERROR_MSGS.NULL_ARGUMENT);var r=this._map.get(t);if(void 0!==r)return r;throw new Error(ERROR_MSGS.KEY_NOT_FOUND)},t.prototype.remove=function(t){if(null===t||void 0===t)throw new Error(ERROR_MSGS.NULL_ARGUMENT);if(!this._map.delete(t))throw new Error(ERROR_MSGS.KEY_NOT_FOUND)},t.prototype.removeByCondition=function(t){var r=this;this._map.forEach(function(o,e){var n=o.filter(function(r){return!t(r)});n.length>0?r._map.set(e,n):r._map.delete(e)})},t.prototype.hasKey=function(t){if(null===t||void 0===t)throw new Error(ERROR_MSGS.NULL_ARGUMENT);return this._map.has(t)},t.prototype.clone=function(){var r=new t;return this._map.forEach(function(t,o){t.forEach(function(t){return r.add(o,t.clone())})}),r},t.prototype.traverse=function(t){this._map.forEach(function(r,o){t(o,r)})},t}();exports.Lookup=Lookup;

/***/ }),
/* 126 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
Object.defineProperty(exports,"__esModule",{value:!0});var guid_1=__webpack_require__(17),Context=function(){function t(t){this.guid=guid_1.guid(),this.container=t}return t.prototype.addPlan=function(t){this.plan=t},t}();exports.Context=Context;

/***/ }),
/* 127 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
Object.defineProperty(exports,"__esModule",{value:!0});var Plan=function(){function t(t,e){this.parentContext=t,this.rootRequest=e}return t}();exports.Plan=Plan;

/***/ }),
/* 128 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
function getBindingDictionary(e){return e._bindingDictionary}function _createTarget(e,t,i,n,r,a){var s=e?METADATA_KEY.MULTI_INJECT_TAG:METADATA_KEY.INJECT_TAG,o=new metadata_1.Metadata(s,i),u=new target_1.Target(t,n,i,o);if(void 0!==r){var c=new metadata_1.Metadata(r,a);u.metadata.push(c)}return u}function _getActiveBindings(e,t,i,n){var r=getBindings(t.container,n.serviceIdentifier),a=[];return a=!1===e?r.filter(function(e){var r=new request_1.Request(e.serviceIdentifier,t,i,e,n);return e.constraint(r)}):r,_validateActiveBindingCount(n.serviceIdentifier,a,n,t.container),a}function _validateActiveBindingCount(e,t,i,n){switch(t.length){case binding_count_1.BindingCount.NoBindingsAvailable:if(!0===i.isOptional())return t;var r=serialization_1.getServiceIdentifierAsString(e),a=ERROR_MSGS.NOT_REGISTERED;throw a+=serialization_1.listMetadataForTarget(r,i),a+=serialization_1.listRegisteredBindingsForServiceIdentifier(n,r,getBindings),new Error(a);case binding_count_1.BindingCount.OnlyOneBindingAvailable:if(!1===i.isArray())return t;case binding_count_1.BindingCount.MultipleBindingsAvailable:default:if(!1===i.isArray()){var r=serialization_1.getServiceIdentifierAsString(e),a=ERROR_MSGS.AMBIGUOUS_MATCH+" "+r;throw a+=serialization_1.listRegisteredBindingsForServiceIdentifier(n,r,getBindings),new Error(a)}return t}}function _createSubRequests(e,t,i,n,r,a){try{var s,o=void 0;if(null===r){o=_getActiveBindings(t,n,null,a),s=new request_1.Request(i,n,null,o,a);var u=new plan_1.Plan(n,s);n.addPlan(u)}else o=_getActiveBindings(t,n,r,a),s=r.addChildRequest(a.serviceIdentifier,o,a);o.forEach(function(t){var i=null;if(i=a.isArray()?s.addChildRequest(t.serviceIdentifier,t,a):s,t.type===literal_types_1.BindingTypeEnum.Instance&&null!==t.implementationType){reflection_utils_1.getDependencies(e,t.implementationType).forEach(function(t){_createSubRequests(e,!1,t.serviceIdentifier,n,i,t)})}})}catch(e){if(!(e instanceof RangeError&&null!==r))throw new Error(e.message);serialization_1.circularDependencyToException(r.parentContext.plan.rootRequest)}}function getBindings(e,t){var i=[],n=getBindingDictionary(e);return n.hasKey(t)?i=n.get(t):null!==e.parent&&(i=getBindings(e.parent,t)),i}function plan(e,t,i,n,r,a,s,o){void 0===o&&(o=!1);var u=new context_1.Context(t);return _createSubRequests(e,o,r,u,null,_createTarget(i,n,r,"",a,s)),u}function createMockRequest(e,t,i,n){var r=new target_1.Target(literal_types_1.TargetTypeEnum.Variable,"",t,new metadata_1.Metadata(i,n)),a=new context_1.Context(e);return new request_1.Request(t,a,null,[],r)}Object.defineProperty(exports,"__esModule",{value:!0});var plan_1=__webpack_require__(127),context_1=__webpack_require__(126),request_1=__webpack_require__(131),target_1=__webpack_require__(75),binding_count_1=__webpack_require__(121),reflection_utils_1=__webpack_require__(130),metadata_1=__webpack_require__(9),ERROR_MSGS=__webpack_require__(8),METADATA_KEY=__webpack_require__(4),literal_types_1=__webpack_require__(14),serialization_1=__webpack_require__(29);exports.getBindingDictionary=getBindingDictionary,exports.plan=plan,exports.createMockRequest=createMockRequest;

/***/ }),
/* 129 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
Object.defineProperty(exports,"__esModule",{value:!0});var QueryableString=function(){function t(t){this.str=t}return t.prototype.startsWith=function(t){return 0===this.str.indexOf(t)},t.prototype.endsWith=function(t){var r="",e=t.split("").reverse().join("");return r=this.str.split("").reverse().join(""),this.startsWith.call({str:r},e)},t.prototype.contains=function(t){return-1!==this.str.indexOf(t)},t.prototype.equals=function(t){return this.str===t},t.prototype.value=function(){return this.str},t}();exports.QueryableString=QueryableString;

/***/ }),
/* 130 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
function getDependencies(t,e){return getTargets(t,serialization_1.getFunctionName(e),e,!1)}function getTargets(t,e,r,a){var n=t.getConstructorMetadata(r),s=n.compilerGeneratedMetadata;if(void 0===s){var o=ERROR_MSGS.MISSING_INJECTABLE_ANNOTATION+" "+e+".";throw new Error(o)}var g=n.userGeneratedMetadata,i=Object.keys(g),u=0===r.length&&i.length>0,c=u?i.length:r.length,T=getConstructorArgsAsTargets(a,e,s,g,c),A=getClassPropsAsTargets(t,r),_=T.concat(A),l=getBaseClassDependencyCount(t,r);if(_.length<l){var E=ERROR_MSGS.ARGUMENTS_LENGTH_MISMATCH_1+e+ERROR_MSGS.ARGUMENTS_LENGTH_MISMATCH_2;throw new Error(E)}return _}function getConstructorArgsAsTarget(t,e,r,a,n){var s=n[t.toString()]||[],o=formatTargetMetadata(s),g=!0!==o.unmanaged,i=a[t],u=o.inject||o.multiInject;if(i=u||i,!0===g){var c=i===Object,T=i===Function,A=void 0===i,_=c||T||A;if(!1===e&&_){var l=ERROR_MSGS.MISSING_INJECT_ANNOTATION+" argument "+t+" in class "+r+".";throw new Error(l)}var E=new target_1.Target(literal_types_1.TargetTypeEnum.ConstructorArgument,o.targetName,i);return E.metadata=s,E}return null}function getConstructorArgsAsTargets(t,e,r,a,n){for(var s=[],o=0;o<n;o++){var g=o,i=getConstructorArgsAsTarget(g,t,e,r,a);null!==i&&s.push(i)}return s}function getClassPropsAsTargets(t,e){for(var r=t.getPropertiesMetadata(e),a=[],n=Object.keys(r),s=0;s<n.length;s++){var o=n[s],g=r[o],i=formatTargetMetadata(r[o]),u=i.targetName||o,c=i.inject||i.multiInject,T=new target_1.Target(literal_types_1.TargetTypeEnum.ClassProperty,u,c);T.metadata=g,a.push(T)}var A=Object.getPrototypeOf(e.prototype).constructor;if(A!==Object){var _=getClassPropsAsTargets(t,A);a=a.concat(_)}return a}function getBaseClassDependencyCount(t,e){var r=Object.getPrototypeOf(e.prototype).constructor;if(r!==Object){var a=serialization_1.getFunctionName(r),n=getTargets(t,a,r,!0),s=n.map(function(t){return t.metadata.filter(function(t){return t.key===METADATA_KEY.UNMANAGED_TAG})}),o=[].concat.apply([],s).length,g=n.length-o;return g>0?g:getBaseClassDependencyCount(t,r)}return 0}function formatTargetMetadata(t){var e={};return t.forEach(function(t){e[t.key.toString()]=t.value}),{inject:e[METADATA_KEY.INJECT_TAG],multiInject:e[METADATA_KEY.MULTI_INJECT_TAG],targetName:e[METADATA_KEY.NAME_TAG],unmanaged:e[METADATA_KEY.UNMANAGED_TAG]}}Object.defineProperty(exports,"__esModule",{value:!0});var serialization_1=__webpack_require__(29),target_1=__webpack_require__(75),ERROR_MSGS=__webpack_require__(8),METADATA_KEY=__webpack_require__(4),literal_types_1=__webpack_require__(14);exports.getDependencies=getDependencies;

/***/ }),
/* 131 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
Object.defineProperty(exports,"__esModule",{value:!0});var guid_1=__webpack_require__(17),Request=function(){function t(t,e,i,s,r){this.guid=guid_1.guid(),this.serviceIdentifier=t,this.parentContext=e,this.parentRequest=i,this.target=r,this.childRequests=[],this.bindings=Array.isArray(s)?s:[s]}return t.prototype.addChildRequest=function(e,i,s){var r=new t(e,this.parentContext,this,i,s);return this.childRequests.push(r),r},t}();exports.Request=Request;

/***/ }),
/* 132 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
function _injectProperties(e,t,r){var n=t.filter(function(e){return null!==e.target&&e.target.type===literal_types_1.TargetTypeEnum.ClassProperty}),a=n.map(function(e){return r(e)});return n.forEach(function(t,r){var n="";n=t.target.name.value();var s=a[r];e[n]=s}),e}function _createInstance(e,t){return new(e.bind.apply(e,[void 0].concat(t)))}function _postConstruct(e,t){if(Reflect.hasMetadata(METADATA_KEY.POST_CONSTRUCT,e)){var r=Reflect.getMetadata(METADATA_KEY.POST_CONSTRUCT,e);try{t[r.value]()}catch(t){throw new Error(error_msgs_1.POST_CONSTRUCT_ERROR(e.name,t.message))}}}function resolveInstance(e,t,r){var n=null;if(t.length>0){n=_createInstance(e,t.filter(function(e){return null!==e.target&&e.target.type===literal_types_1.TargetTypeEnum.ConstructorArgument}).map(function(e){return r(e)})),n=_injectProperties(n,t,r)}else n=new e;return _postConstruct(e,n),n}Object.defineProperty(exports,"__esModule",{value:!0});var literal_types_1=__webpack_require__(14),METADATA_KEY=__webpack_require__(4),error_msgs_1=__webpack_require__(8);exports.resolveInstance=resolveInstance;

/***/ }),
/* 133 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
function _resolveRequest(e){var t=e.bindings,n=e.childRequests,i=e.target&&e.target.isArray(),r=!(e.parentRequest&&e.parentRequest.target&&e.target&&e.parentRequest.target.matchesArray(e.target.serviceIdentifier));if(i&&r)return n.map(function(e){return _resolveRequest(e)});var a=null;if(!0!==e.target.isOptional()||0!==t.length){var s=t[0],l=s.scope===literal_types_1.BindingScopeEnum.Singleton;if(l&&!0===s.activated)return s.cache;if(s.type===literal_types_1.BindingTypeEnum.ConstantValue)a=s.cache;else if(s.type===literal_types_1.BindingTypeEnum.Function)a=s.cache;else if(s.type===literal_types_1.BindingTypeEnum.Constructor)a=s.implementationType;else if(s.type===literal_types_1.BindingTypeEnum.DynamicValue&&null!==s.dynamicValue)a=s.dynamicValue(e.parentContext);else if(s.type===literal_types_1.BindingTypeEnum.Factory&&null!==s.factory)a=s.factory(e.parentContext);else if(s.type===literal_types_1.BindingTypeEnum.Provider&&null!==s.provider)a=s.provider(e.parentContext);else{if(s.type!==literal_types_1.BindingTypeEnum.Instance||null===s.implementationType){var o=serialization_1.getServiceIdentifierAsString(e.serviceIdentifier);throw new Error(ERROR_MSGS.INVALID_BINDING_TYPE+" "+o)}a=instantiation_1.resolveInstance(s.implementationType,n,_resolveRequest)}return"function"==typeof s.onActivation&&(a=s.onActivation(e.parentContext,a)),l&&(s.cache=a,s.activated=!0),a}}function resolve(e){return _resolveRequest(e.plan.rootRequest)}Object.defineProperty(exports,"__esModule",{value:!0});var literal_types_1=__webpack_require__(14),serialization_1=__webpack_require__(29),instantiation_1=__webpack_require__(132),ERROR_MSGS=__webpack_require__(8);exports.resolve=resolve;

/***/ }),
/* 134 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
Object.defineProperty(exports,"__esModule",{value:!0});var literal_types_1=__webpack_require__(14),binding_when_on_syntax_1=__webpack_require__(76),BindingInSyntax=function(){function n(n){this._binding=n}return n.prototype.inSingletonScope=function(){return this._binding.scope=literal_types_1.BindingScopeEnum.Singleton,new binding_when_on_syntax_1.BindingWhenOnSyntax(this._binding)},n.prototype.inTransientScope=function(){return this._binding.scope=literal_types_1.BindingScopeEnum.Transient,new binding_when_on_syntax_1.BindingWhenOnSyntax(this._binding)},n}();exports.BindingInSyntax=BindingInSyntax;

/***/ }),
/* 135 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
Object.defineProperty(exports,"__esModule",{value:!0});var binding_in_syntax_1=__webpack_require__(134),binding_when_syntax_1=__webpack_require__(49),binding_on_syntax_1=__webpack_require__(48),BindingInWhenOnSyntax=function(){function n(n){this._binding=n,this._bindingWhenSyntax=new binding_when_syntax_1.BindingWhenSyntax(this._binding),this._bindingOnSyntax=new binding_on_syntax_1.BindingOnSyntax(this._binding),this._bindingInSyntax=new binding_in_syntax_1.BindingInSyntax(n)}return n.prototype.inSingletonScope=function(){return this._bindingInSyntax.inSingletonScope()},n.prototype.inTransientScope=function(){return this._bindingInSyntax.inTransientScope()},n.prototype.when=function(n){return this._bindingWhenSyntax.when(n)},n.prototype.whenTargetNamed=function(n){return this._bindingWhenSyntax.whenTargetNamed(n)},n.prototype.whenTargetIsDefault=function(){return this._bindingWhenSyntax.whenTargetIsDefault()},n.prototype.whenTargetTagged=function(n,t){return this._bindingWhenSyntax.whenTargetTagged(n,t)},n.prototype.whenInjectedInto=function(n){return this._bindingWhenSyntax.whenInjectedInto(n)},n.prototype.whenParentNamed=function(n){return this._bindingWhenSyntax.whenParentNamed(n)},n.prototype.whenParentTagged=function(n,t){return this._bindingWhenSyntax.whenParentTagged(n,t)},n.prototype.whenAnyAncestorIs=function(n){return this._bindingWhenSyntax.whenAnyAncestorIs(n)},n.prototype.whenNoAncestorIs=function(n){return this._bindingWhenSyntax.whenNoAncestorIs(n)},n.prototype.whenAnyAncestorNamed=function(n){return this._bindingWhenSyntax.whenAnyAncestorNamed(n)},n.prototype.whenAnyAncestorTagged=function(n,t){return this._bindingWhenSyntax.whenAnyAncestorTagged(n,t)},n.prototype.whenNoAncestorNamed=function(n){return this._bindingWhenSyntax.whenNoAncestorNamed(n)},n.prototype.whenNoAncestorTagged=function(n,t){return this._bindingWhenSyntax.whenNoAncestorTagged(n,t)},n.prototype.whenAnyAncestorMatches=function(n){return this._bindingWhenSyntax.whenAnyAncestorMatches(n)},n.prototype.whenNoAncestorMatches=function(n){return this._bindingWhenSyntax.whenNoAncestorMatches(n)},n.prototype.onActivation=function(n){return this._bindingOnSyntax.onActivation(n)},n}();exports.BindingInWhenOnSyntax=BindingInWhenOnSyntax;

/***/ }),
/* 136 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
Object.defineProperty(exports,"__esModule",{value:!0});var binding_in_when_on_syntax_1=__webpack_require__(135),binding_when_on_syntax_1=__webpack_require__(76),literal_types_1=__webpack_require__(14),ERROR_MSGS=__webpack_require__(8),BindingToSyntax=function(){function n(n){this._binding=n}return n.prototype.to=function(n){return this._binding.type=literal_types_1.BindingTypeEnum.Instance,this._binding.implementationType=n,new binding_in_when_on_syntax_1.BindingInWhenOnSyntax(this._binding)},n.prototype.toSelf=function(){if("function"!=typeof this._binding.serviceIdentifier)throw new Error(""+ERROR_MSGS.INVALID_TO_SELF_VALUE);var n=this._binding.serviceIdentifier;return this.to(n)},n.prototype.toConstantValue=function(n){return this._binding.type=literal_types_1.BindingTypeEnum.ConstantValue,this._binding.cache=n,this._binding.dynamicValue=null,this._binding.implementationType=null,new binding_when_on_syntax_1.BindingWhenOnSyntax(this._binding)},n.prototype.toDynamicValue=function(n){return this._binding.type=literal_types_1.BindingTypeEnum.DynamicValue,this._binding.cache=null,this._binding.dynamicValue=n,this._binding.implementationType=null,new binding_in_when_on_syntax_1.BindingInWhenOnSyntax(this._binding)},n.prototype.toConstructor=function(n){return this._binding.type=literal_types_1.BindingTypeEnum.Constructor,this._binding.implementationType=n,new binding_when_on_syntax_1.BindingWhenOnSyntax(this._binding)},n.prototype.toFactory=function(n){return this._binding.type=literal_types_1.BindingTypeEnum.Factory,this._binding.factory=n,new binding_when_on_syntax_1.BindingWhenOnSyntax(this._binding)},n.prototype.toFunction=function(n){if("function"!=typeof n)throw new Error(ERROR_MSGS.INVALID_FUNCTION_BINDING);var i=this.toConstantValue(n);return this._binding.type=literal_types_1.BindingTypeEnum.Function,i},n.prototype.toAutoFactory=function(n){return this._binding.type=literal_types_1.BindingTypeEnum.Factory,this._binding.factory=function(i){return function(){return i.container.get(n)}},new binding_when_on_syntax_1.BindingWhenOnSyntax(this._binding)},n.prototype.toProvider=function(n){return this._binding.type=literal_types_1.BindingTypeEnum.Provider,this._binding.provider=n,new binding_when_on_syntax_1.BindingWhenOnSyntax(this._binding)},n}();exports.BindingToSyntax=BindingToSyntax;

/***/ }),
/* 137 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
function template(e){for(var t=[],r=1;r<arguments.length;r++)t[r-1]=arguments[r];return function(){for(var r=[],n=0;n<arguments.length;n++)r[n]=arguments[n];var o=[e[0]];return t.forEach(function(n,u){var a=Number.isInteger(n)?r[n]:r[t.indexOf(n)];o.push(a,e[u+1])}),o.join("")}}Object.defineProperty(exports,"__esModule",{value:!0}),exports.template=template;

/***/ }),
/* 138 */
/***/ (function(module, exports) {

var toString={}.toString;module.exports=Array.isArray||function(r){return"[object Array]"==toString.call(r)};

/***/ }),
/* 139 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
/* WEBPACK VAR INJECTION */(function(global, process) {function _uint8ArrayToBuffer(e){return Buffer.from(e)}function _isUint8Array(e){return Buffer.isBuffer(e)||e instanceof OurUint8Array}function prependListener(e,t,r){if("function"==typeof e.prependListener)return e.prependListener(t,r);e._events&&e._events[t]?isArray(e._events[t])?e._events[t].unshift(r):e._events[t]=[r,e._events[t]]:e.on(t,r)}function ReadableState(e,t){Duplex=Duplex||__webpack_require__(40),e=e||{},this.objectMode=!!e.objectMode,t instanceof Duplex&&(this.objectMode=this.objectMode||!!e.readableObjectMode);var r=e.highWaterMark,n=this.objectMode?16:16384;this.highWaterMark=r||0===r?r:n,this.highWaterMark=Math.floor(this.highWaterMark),this.buffer=new BufferList,this.length=0,this.pipes=null,this.pipesCount=0,this.flowing=null,this.ended=!1,this.endEmitted=!1,this.reading=!1,this.sync=!0,this.needReadable=!1,this.emittedReadable=!1,this.readableListening=!1,this.resumeScheduled=!1,this.destroyed=!1,this.defaultEncoding=e.defaultEncoding||"utf8",this.awaitDrain=0,this.readingMore=!1,this.decoder=null,this.encoding=null,e.encoding&&(StringDecoder||(StringDecoder=__webpack_require__(186).StringDecoder),this.decoder=new StringDecoder(e.encoding),this.encoding=e.encoding)}function Readable(e){if(Duplex=Duplex||__webpack_require__(40),!(this instanceof Readable))return new Readable(e);this._readableState=new ReadableState(e,this),this.readable=!0,e&&("function"==typeof e.read&&(this._read=e.read),"function"==typeof e.destroy&&(this._destroy=e.destroy)),Stream.call(this)}function readableAddChunk(e,t,r,n,a){var i=e._readableState;if(null===t)i.reading=!1,onEofChunk(e,i);else{var d;a||(d=chunkInvalid(i,t)),d?e.emit("error",d):i.objectMode||t&&t.length>0?("string"==typeof t||i.objectMode||Object.getPrototypeOf(t)===Buffer.prototype||(t=_uint8ArrayToBuffer(t)),n?i.endEmitted?e.emit("error",new Error("stream.unshift() after end event")):addChunk(e,i,t,!0):i.ended?e.emit("error",new Error("stream.push() after EOF")):(i.reading=!1,i.decoder&&!r?(t=i.decoder.write(t),i.objectMode||0!==t.length?addChunk(e,i,t,!1):maybeReadMore(e,i)):addChunk(e,i,t,!1))):n||(i.reading=!1)}return needMoreData(i)}function addChunk(e,t,r,n){t.flowing&&0===t.length&&!t.sync?(e.emit("data",r),e.read(0)):(t.length+=t.objectMode?1:r.length,n?t.buffer.unshift(r):t.buffer.push(r),t.needReadable&&emitReadable(e)),maybeReadMore(e,t)}function chunkInvalid(e,t){var r;return _isUint8Array(t)||"string"==typeof t||void 0===t||e.objectMode||(r=new TypeError("Invalid non-string/buffer chunk")),r}function needMoreData(e){return!e.ended&&(e.needReadable||e.length<e.highWaterMark||0===e.length)}function computeNewHighWaterMark(e){return e>=MAX_HWM?e=MAX_HWM:(e--,e|=e>>>1,e|=e>>>2,e|=e>>>4,e|=e>>>8,e|=e>>>16,e++),e}function howMuchToRead(e,t){return e<=0||0===t.length&&t.ended?0:t.objectMode?1:e!==e?t.flowing&&t.length?t.buffer.head.data.length:t.length:(e>t.highWaterMark&&(t.highWaterMark=computeNewHighWaterMark(e)),e<=t.length?e:t.ended?t.length:(t.needReadable=!0,0))}function onEofChunk(e,t){if(!t.ended){if(t.decoder){var r=t.decoder.end();r&&r.length&&(t.buffer.push(r),t.length+=t.objectMode?1:r.length)}t.ended=!0,emitReadable(e)}}function emitReadable(e){var t=e._readableState;t.needReadable=!1,t.emittedReadable||(debug("emitReadable",t.flowing),t.emittedReadable=!0,t.sync?processNextTick(emitReadable_,e):emitReadable_(e))}function emitReadable_(e){debug("emit readable"),e.emit("readable"),flow(e)}function maybeReadMore(e,t){t.readingMore||(t.readingMore=!0,processNextTick(maybeReadMore_,e,t))}function maybeReadMore_(e,t){for(var r=t.length;!t.reading&&!t.flowing&&!t.ended&&t.length<t.highWaterMark&&(debug("maybeReadMore read 0"),e.read(0),r!==t.length);)r=t.length;t.readingMore=!1}function pipeOnDrain(e){return function(){var t=e._readableState;debug("pipeOnDrain",t.awaitDrain),t.awaitDrain&&t.awaitDrain--,0===t.awaitDrain&&EElistenerCount(e,"data")&&(t.flowing=!0,flow(e))}}function nReadingNextTick(e){debug("readable nexttick read 0"),e.read(0)}function resume(e,t){t.resumeScheduled||(t.resumeScheduled=!0,processNextTick(resume_,e,t))}function resume_(e,t){t.reading||(debug("resume read 0"),e.read(0)),t.resumeScheduled=!1,t.awaitDrain=0,e.emit("resume"),flow(e),t.flowing&&!t.reading&&e.read(0)}function flow(e){var t=e._readableState;for(debug("flow",t.flowing);t.flowing&&null!==e.read(););}function fromList(e,t){if(0===t.length)return null;var r;return t.objectMode?r=t.buffer.shift():!e||e>=t.length?(r=t.decoder?t.buffer.join(""):1===t.buffer.length?t.buffer.head.data:t.buffer.concat(t.length),t.buffer.clear()):r=fromListPartial(e,t.buffer,t.decoder),r}function fromListPartial(e,t,r){var n;return e<t.head.data.length?(n=t.head.data.slice(0,e),t.head.data=t.head.data.slice(e)):n=e===t.head.data.length?t.shift():r?copyFromBufferString(e,t):copyFromBuffer(e,t),n}function copyFromBufferString(e,t){var r=t.head,n=1,a=r.data;for(e-=a.length;r=r.next;){var i=r.data,d=e>i.length?i.length:e;if(d===i.length?a+=i:a+=i.slice(0,e),0===(e-=d)){d===i.length?(++n,r.next?t.head=r.next:t.head=t.tail=null):(t.head=r,r.data=i.slice(d));break}++n}return t.length-=n,a}function copyFromBuffer(e,t){var r=Buffer.allocUnsafe(e),n=t.head,a=1;for(n.data.copy(r),e-=n.data.length;n=n.next;){var i=n.data,d=e>i.length?i.length:e;if(i.copy(r,r.length-e,0,d),0===(e-=d)){d===i.length?(++a,n.next?t.head=n.next:t.head=t.tail=null):(t.head=n,n.data=i.slice(d));break}++a}return t.length-=a,r}function endReadable(e){var t=e._readableState;if(t.length>0)throw new Error('"endReadable()" called on non-empty stream');t.endEmitted||(t.ended=!0,processNextTick(endReadableNT,t,e))}function endReadableNT(e,t){e.endEmitted||0!==e.length||(e.endEmitted=!0,t.readable=!1,t.emit("end"))}function forEach(e,t){for(var r=0,n=e.length;r<n;r++)t(e[r],r)}function indexOf(e,t){for(var r=0,n=e.length;r<n;r++)if(e[r]===t)return r;return-1}var processNextTick=__webpack_require__(72);module.exports=Readable;var isArray=__webpack_require__(138),Duplex;Readable.ReadableState=ReadableState;var EE=__webpack_require__(101).EventEmitter,EElistenerCount=function(e,t){return e.listeners(t).length},Stream=__webpack_require__(142),Buffer=__webpack_require__(100).Buffer,OurUint8Array=global.Uint8Array||function(){},util=__webpack_require__(47);util.inherits=__webpack_require__(39);var debugUtil=__webpack_require__(212),debug=void 0;debug=debugUtil&&debugUtil.debuglog?debugUtil.debuglog("stream"):function(){};var BufferList=__webpack_require__(204),destroyImpl=__webpack_require__(141),StringDecoder;util.inherits(Readable,Stream);var kProxyEvents=["error","close","destroy","pause","resume"];Object.defineProperty(Readable.prototype,"destroyed",{get:function(){return void 0!==this._readableState&&this._readableState.destroyed},set:function(e){this._readableState&&(this._readableState.destroyed=e)}}),Readable.prototype.destroy=destroyImpl.destroy,Readable.prototype._undestroy=destroyImpl.undestroy,Readable.prototype._destroy=function(e,t){this.push(null),t(e)},Readable.prototype.push=function(e,t){var r,n=this._readableState;return n.objectMode?r=!0:"string"==typeof e&&(t=t||n.defaultEncoding,t!==n.encoding&&(e=Buffer.from(e,t),t=""),r=!0),readableAddChunk(this,e,t,!1,r)},Readable.prototype.unshift=function(e){return readableAddChunk(this,e,null,!0,!1)},Readable.prototype.isPaused=function(){return!1===this._readableState.flowing},Readable.prototype.setEncoding=function(e){return StringDecoder||(StringDecoder=__webpack_require__(186).StringDecoder),this._readableState.decoder=new StringDecoder(e),this._readableState.encoding=e,this};var MAX_HWM=8388608;Readable.prototype.read=function(e){debug("read",e),e=parseInt(e,10);var t=this._readableState,r=e;if(0!==e&&(t.emittedReadable=!1),0===e&&t.needReadable&&(t.length>=t.highWaterMark||t.ended))return debug("read: emitReadable",t.length,t.ended),0===t.length&&t.ended?endReadable(this):emitReadable(this),null;if(0===(e=howMuchToRead(e,t))&&t.ended)return 0===t.length&&endReadable(this),null;var n=t.needReadable;debug("need readable",n),(0===t.length||t.length-e<t.highWaterMark)&&(n=!0,debug("length less than watermark",n)),t.ended||t.reading?(n=!1,debug("reading or ended",n)):n&&(debug("do read"),t.reading=!0,t.sync=!0,0===t.length&&(t.needReadable=!0),this._read(t.highWaterMark),t.sync=!1,t.reading||(e=howMuchToRead(r,t)));var a;return a=e>0?fromList(e,t):null,null===a?(t.needReadable=!0,e=0):t.length-=e,0===t.length&&(t.ended||(t.needReadable=!0),r!==e&&t.ended&&endReadable(this)),null!==a&&this.emit("data",a),a},Readable.prototype._read=function(e){this.emit("error",new Error("_read() is not implemented"))},Readable.prototype.pipe=function(e,t){function r(e,t){debug("onunpipe"),e===l&&t&&!1===t.hasUnpiped&&(t.hasUnpiped=!0,a())}function n(){debug("onend"),e.end()}function a(){debug("cleanup"),e.removeListener("close",o),e.removeListener("finish",u),e.removeListener("drain",c),e.removeListener("error",d),e.removeListener("unpipe",r),l.removeListener("end",n),l.removeListener("end",s),l.removeListener("data",i),b=!0,!h.awaitDrain||e._writableState&&!e._writableState.needDrain||c()}function i(t){debug("ondata"),g=!1,!1!==e.write(t)||g||((1===h.pipesCount&&h.pipes===e||h.pipesCount>1&&-1!==indexOf(h.pipes,e))&&!b&&(debug("false write response, pause",l._readableState.awaitDrain),l._readableState.awaitDrain++,g=!0),l.pause())}function d(t){debug("onerror",t),s(),e.removeListener("error",d),0===EElistenerCount(e,"error")&&e.emit("error",t)}function o(){e.removeListener("finish",u),s()}function u(){debug("onfinish"),e.removeListener("close",o),s()}function s(){debug("unpipe"),l.unpipe(e)}var l=this,h=this._readableState;switch(h.pipesCount){case 0:h.pipes=e;break;case 1:h.pipes=[h.pipes,e];break;default:h.pipes.push(e)}h.pipesCount+=1,debug("pipe count=%d opts=%j",h.pipesCount,t);var f=(!t||!1!==t.end)&&e!==process.stdout&&e!==process.stderr,p=f?n:s;h.endEmitted?processNextTick(p):l.once("end",p),e.on("unpipe",r);var c=pipeOnDrain(l);e.on("drain",c);var b=!1,g=!1;return l.on("data",i),prependListener(e,"error",d),e.once("close",o),e.once("finish",u),e.emit("pipe",l),h.flowing||(debug("pipe resume"),l.resume()),e},Readable.prototype.unpipe=function(e){var t=this._readableState,r={hasUnpiped:!1};if(0===t.pipesCount)return this;if(1===t.pipesCount)return e&&e!==t.pipes?this:(e||(e=t.pipes),t.pipes=null,t.pipesCount=0,t.flowing=!1,e&&e.emit("unpipe",this,r),this);if(!e){var n=t.pipes,a=t.pipesCount;t.pipes=null,t.pipesCount=0,t.flowing=!1;for(var i=0;i<a;i++)n[i].emit("unpipe",this,r);return this}var d=indexOf(t.pipes,e);return-1===d?this:(t.pipes.splice(d,1),t.pipesCount-=1,1===t.pipesCount&&(t.pipes=t.pipes[0]),e.emit("unpipe",this,r),this)},Readable.prototype.on=function(e,t){var r=Stream.prototype.on.call(this,e,t);if("data"===e)!1!==this._readableState.flowing&&this.resume();else if("readable"===e){var n=this._readableState;n.endEmitted||n.readableListening||(n.readableListening=n.needReadable=!0,n.emittedReadable=!1,n.reading?n.length&&emitReadable(this):processNextTick(nReadingNextTick,this))}return r},Readable.prototype.addListener=Readable.prototype.on,Readable.prototype.resume=function(){var e=this._readableState;return e.flowing||(debug("resume"),e.flowing=!0,resume(this,e)),this},Readable.prototype.pause=function(){return debug("call pause flowing=%j",this._readableState.flowing),!1!==this._readableState.flowing&&(debug("pause"),this._readableState.flowing=!1,this.emit("pause")),this},Readable.prototype.wrap=function(e){var t=this._readableState,r=!1,n=this;e.on("end",function(){if(debug("wrapped end"),t.decoder&&!t.ended){var e=t.decoder.end();e&&e.length&&n.push(e)}n.push(null)}),e.on("data",function(a){if(debug("wrapped data"),t.decoder&&(a=t.decoder.write(a)),(!t.objectMode||null!==a&&void 0!==a)&&(t.objectMode||a&&a.length)){n.push(a)||(r=!0,e.pause())}});for(var a in e)void 0===this[a]&&"function"==typeof e[a]&&(this[a]=function(t){return function(){return e[t].apply(e,arguments)}}(a));for(var i=0;i<kProxyEvents.length;i++)e.on(kProxyEvents[i],n.emit.bind(n,kProxyEvents[i]));return n._read=function(t){debug("wrapped _read",t),r&&(r=!1,e.resume())},n},Readable._fromList=fromList;
/* WEBPACK VAR INJECTION */}.call(exports, __webpack_require__(27), __webpack_require__(28)))

/***/ }),
/* 140 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
function TransformState(r){this.afterTransform=function(t,n){return afterTransform(r,t,n)},this.needTransform=!1,this.transforming=!1,this.writecb=null,this.writechunk=null,this.writeencoding=null}function afterTransform(r,t,n){var e=r._transformState;e.transforming=!1;var i=e.writecb;if(!i)return r.emit("error",new Error("write callback called multiple times"));e.writechunk=null,e.writecb=null,null!==n&&void 0!==n&&r.push(n),i(t);var a=r._readableState;a.reading=!1,(a.needReadable||a.length<a.highWaterMark)&&r._read(a.highWaterMark)}function Transform(r){if(!(this instanceof Transform))return new Transform(r);Duplex.call(this,r),this._transformState=new TransformState(this);var t=this;this._readableState.needReadable=!0,this._readableState.sync=!1,r&&("function"==typeof r.transform&&(this._transform=r.transform),"function"==typeof r.flush&&(this._flush=r.flush)),this.once("prefinish",function(){"function"==typeof this._flush?this._flush(function(r,n){done(t,r,n)}):done(t)})}function done(r,t,n){if(t)return r.emit("error",t);null!==n&&void 0!==n&&r.push(n);var e=r._writableState,i=r._transformState;if(e.length)throw new Error("Calling transform done when ws.length != 0");if(i.transforming)throw new Error("Calling transform done when still transforming");return r.push(null)}module.exports=Transform;var Duplex=__webpack_require__(40),util=__webpack_require__(47);util.inherits=__webpack_require__(39),util.inherits(Transform,Duplex),Transform.prototype.push=function(r,t){return this._transformState.needTransform=!1,Duplex.prototype.push.call(this,r,t)},Transform.prototype._transform=function(r,t,n){throw new Error("_transform() is not implemented")},Transform.prototype._write=function(r,t,n){var e=this._transformState;if(e.writecb=n,e.writechunk=r,e.writeencoding=t,!e.transforming){var i=this._readableState;(e.needTransform||i.needReadable||i.length<i.highWaterMark)&&this._read(i.highWaterMark)}},Transform.prototype._read=function(r){var t=this._transformState;null!==t.writechunk&&t.writecb&&!t.transforming?(t.transforming=!0,this._transform(t.writechunk,t.writeencoding,t.afterTransform)):t.needTransform=!0},Transform.prototype._destroy=function(r,t){var n=this;Duplex.prototype._destroy.call(this,r,function(r){t(r),n.emit("close")})};

/***/ }),
/* 141 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
function destroy(t,e){var r=this,i=this._readableState&&this._readableState.destroyed,a=this._writableState&&this._writableState.destroyed;if(i||a)return void(e?e(t):!t||this._writableState&&this._writableState.errorEmitted||processNextTick(emitErrorNT,this,t));this._readableState&&(this._readableState.destroyed=!0),this._writableState&&(this._writableState.destroyed=!0),this._destroy(t||null,function(t){!e&&t?(processNextTick(emitErrorNT,r,t),r._writableState&&(r._writableState.errorEmitted=!0)):e&&e(t)})}function undestroy(){this._readableState&&(this._readableState.destroyed=!1,this._readableState.reading=!1,this._readableState.ended=!1,this._readableState.endEmitted=!1),this._writableState&&(this._writableState.destroyed=!1,this._writableState.ended=!1,this._writableState.ending=!1,this._writableState.finished=!1,this._writableState.errorEmitted=!1)}function emitErrorNT(t,e){t.emit("error",e)}var processNextTick=__webpack_require__(72);module.exports={destroy:destroy,undestroy:undestroy};

/***/ }),
/* 142 */
/***/ (function(module, exports, __webpack_require__) {

module.exports=__webpack_require__(101).EventEmitter;

/***/ }),
/* 143 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
function _interopRequireDefault(e){return e&&e.__esModule?e:{default:e}}function _defineProperty(e,t,r){return t in e?Object.defineProperty(e,t,{value:r,enumerable:!0,configurable:!0,writable:!0}):e[t]=r,e}function convertNodes(e,t,r){return e instanceof Array&&e.length>0?e.map(function(e){return toVNode(e,t,r)}):void 0}function toVNode(e,t,r){var n=void 0;return n="text"===e.type?(0,_utils.createTextVNode)(e.content,r):(0,_h2.default)(e.name,buildVNodeData(e,r),convertNodes(e.children,t,r)),t.push(n),n}function buildVNodeData(e,t){var r={};if(!e.attrs)return r;var n=Object.keys(e.attrs).reduce(function(r,n){if("style"!==n&&"class"!==n){var u=(0,_utils.unescapeEntities)(e.attrs[n],t);r?r[n]=u:r=_defineProperty({},n,u)}return r},null);n&&(r.attrs=n);var u=parseStyle(e);u&&(r.style=u);var a=parseClass(e);return a&&(r.class=a),r}function parseStyle(e){try{return e.attrs.style.split(";").reduce(function(e,t){var r=t.split(":"),n=(0,_utils.transformName)(r[0].trim());if(n){var u=r[1].replace("!important","").trim();e?e[n]=u:e=_defineProperty({},n,u)}return e},null)}catch(e){return null}}function parseClass(e){try{return e.attrs.class.split(" ").reduce(function(e,t){return t=t.trim(),t&&(e?e[t]=!0:e=_defineProperty({},t,!0)),e},null)}catch(e){return null}}Object.defineProperty(exports,"__esModule",{value:!0}),exports.default=function(e){var t=arguments.length<=1||void 0===arguments[1]?{}:arguments[1],r=t.context||document;if(!e)return null;var n=[],u=convertNodes((0,_parse2.default)(e),n,r),a=void 0;return a=u?1===u.length?u[0]:u:toVNode({type:"text",content:e},n,r),t.hooks&&t.hooks.create&&n.forEach(function(e){t.hooks.create(e)}),a};var _parse=__webpack_require__(110),_parse2=_interopRequireDefault(_parse),_h=__webpack_require__(50),_h2=_interopRequireDefault(_h),_utils=__webpack_require__(144);

/***/ }),
/* 144 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
function _interopRequireDefault(e){return e&&e.__esModule?e:{default:e}}function createTextVNode(e,t){return(0,_vnode2.default)(void 0,void 0,void 0,unescapeEntities(e,t))}function transformName(e){return e=e.replace(/-(\w)/g,function(e,t){return t.toUpperCase()}),""+e.charAt(0).toLowerCase()+e.substring(1)}function unescapeEntities(e,t){return el||(el=t.createElement("div")),e.replace(entityRegex,function(e){return el.innerHTML=e,el.textContent})}Object.defineProperty(exports,"__esModule",{value:!0}),exports.createTextVNode=createTextVNode,exports.transformName=transformName,exports.unescapeEntities=unescapeEntities;var _vnode=__webpack_require__(51),_vnode2=_interopRequireDefault(_vnode),entityRegex=new RegExp("&[a-z0-9#]+;","gi"),el=null;

/***/ }),
/* 145 */
/***/ (function(module, exports, __webpack_require__) {

module.exports=__webpack_require__(143);

/***/ }),
/* 146 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
function createElement(e){return document.createElement(e)}function createElementNS(e,t){return document.createElementNS(e,t)}function createTextNode(e){return document.createTextNode(e)}function createComment(e){return document.createComment(e)}function insertBefore(e,t,n){e.insertBefore(t,n)}function removeChild(e,t){e.removeChild(t)}function appendChild(e,t){e.appendChild(t)}function parentNode(e){return e.parentNode}function nextSibling(e){return e.nextSibling}function tagName(e){return e.tagName}function setTextContent(e,t){e.textContent=t}function getTextContent(e){return e.textContent}function isElement(e){return 1===e.nodeType}function isText(e){return 3===e.nodeType}function isComment(e){return 8===e.nodeType}Object.defineProperty(exports,"__esModule",{value:!0}),exports.htmlDomApi={createElement:createElement,createElementNS:createElementNS,createTextNode:createTextNode,createComment:createComment,insertBefore:insertBefore,removeChild:removeChild,appendChild:appendChild,parentNode:parentNode,nextSibling:nextSibling,tagName:tagName,setTextContent:setTextContent,getTextContent:getTextContent,isElement:isElement,isText:isText,isComment:isComment},exports.default=exports.htmlDomApi;

/***/ }),
/* 147 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
function updateAttrs(t,e){var r,a=e.elm,o=t.data.attrs,s=e.data.attrs;if((o||s)&&o!==s){o=o||{},s=s||{};for(r in s){var u=s[r];o[r]!==u&&(!0===u?a.setAttribute(r,""):!1===u?a.removeAttribute(r):r.charCodeAt(0)!==xChar?a.setAttribute(r,u):r.charCodeAt(3)===colonChar?a.setAttributeNS(xmlNS,r,u):r.charCodeAt(5)===colonChar?a.setAttributeNS(xlinkNS,r,u):a.setAttribute(r,u))}for(r in o)r in s||a.removeAttribute(r)}}Object.defineProperty(exports,"__esModule",{value:!0});var xlinkNS="http://www.w3.org/1999/xlink",xmlNS="http://www.w3.org/XML/1998/namespace",colonChar=58,xChar=120;exports.attributesModule={create:updateAttrs,update:updateAttrs},exports.default=exports.attributesModule;

/***/ }),
/* 148 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
function updateClass(s,e){var a,t,l=e.elm,d=s.data.class,o=e.data.class;if((d||o)&&d!==o){d=d||{},o=o||{};for(t in d)o[t]||l.classList.remove(t);for(t in o)(a=o[t])!==d[t]&&l.classList[a?"add":"remove"](t)}}Object.defineProperty(exports,"__esModule",{value:!0}),exports.classModule={create:updateClass,update:updateClass},exports.default=exports.classModule;

/***/ }),
/* 149 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
function invokeHandler(e,t,n){if("function"==typeof e)e.call(t,n,t);else if("object"==typeof e)if("function"==typeof e[0])if(2===e.length)e[0].call(t,e[1],n,t);else{var r=e.slice(1);r.push(n),r.push(t),e[0].apply(t,r)}else for(var i=0;i<e.length;i++)invokeHandler(e[i])}function handleEvent(e,t){var n=e.type,r=t.data.on;r&&r[n]&&invokeHandler(r[n],t,e)}function createListener(){return function e(t){handleEvent(t,e.vnode)}}function updateEventListeners(e,t){var n,r=e.data.on,i=e.listener,s=e.elm,o=t&&t.data.on,a=t&&t.elm;if(r!==o){if(r&&i)if(o)for(n in r)o[n]||s.removeEventListener(n,i,!1);else for(n in r)s.removeEventListener(n,i,!1);if(o){var l=t.listener=e.listener||createListener();if(l.vnode=t,r)for(n in o)r[n]||a.addEventListener(n,l,!1);else for(n in o)a.addEventListener(n,l,!1)}}}Object.defineProperty(exports,"__esModule",{value:!0}),exports.eventListenersModule={create:updateEventListeners,update:updateEventListeners,destroy:updateEventListeners},exports.default=exports.eventListenersModule;

/***/ }),
/* 150 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
function updateProps(e,p){var o,r,t=p.elm,s=e.data.props,a=p.data.props;if((s||a)&&s!==a){s=s||{},a=a||{};for(o in s)a[o]||delete t[o];for(o in a)r=a[o],s[o]===r||"value"===o&&t[o]===r||(t[o]=r)}}Object.defineProperty(exports,"__esModule",{value:!0}),exports.propsModule={create:updateProps,update:updateProps},exports.default=exports.propsModule;

/***/ }),
/* 151 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
function setNextFrame(e,t,r){nextFrame(function(){e[t]=r})}function updateStyle(e,t){var r,a,o=t.elm,l=e.data.style,n=t.data.style;if((l||n)&&l!==n){l=l||{},n=n||{};var y="delayed"in l;for(a in l)n[a]||("-"===a[0]&&"-"===a[1]?o.style.removeProperty(a):o.style[a]="");for(a in n)if(r=n[a],"delayed"===a&&n.delayed)for(var d in n.delayed)r=n.delayed[d],y&&r===l.delayed[d]||setNextFrame(o.style,d,r);else"remove"!==a&&r!==l[a]&&("-"===a[0]&&"-"===a[1]?o.style.setProperty(a,r):o.style[a]=r)}}function applyDestroyStyle(e){var t,r,a=e.elm,o=e.data.style;if(o&&(t=o.destroy))for(r in t)a.style[r]=t[r]}function applyRemoveStyle(e,t){var r=e.data.style;if(!r||!r.remove)return void t();var a,o,l=e.elm,n=0,y=r.remove,d=0,s=[];for(a in y)s.push(a),l.style[a]=y[a];o=getComputedStyle(l);for(var i=o["transition-property"].split(", ");n<i.length;++n)-1!==s.indexOf(i[n])&&d++;l.addEventListener("transitionend",function(e){e.target===l&&--d,0===d&&t()})}Object.defineProperty(exports,"__esModule",{value:!0});var raf="undefined"!=typeof window&&window.requestAnimationFrame||setTimeout,nextFrame=function(e){raf(function(){raf(e)})};exports.styleModule={create:updateStyle,update:updateStyle,destroy:applyDestroyStyle,remove:applyRemoveStyle},exports.default=exports.styleModule;

/***/ }),
/* 152 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
function copyToThunk(t,a){a.elm=t.elm,t.data.fn=a.data.fn,t.data.args=a.data.args,a.data=t.data,a.children=t.children,a.text=t.text,a.elm=t.elm}function init(t){var a=t.data;copyToThunk(a.fn.apply(void 0,a.args),t)}function prepatch(t,a){var n,e=t.data,o=a.data,r=e.args,i=o.args;if(e.fn!==o.fn||r.length!==i.length)return void copyToThunk(o.fn.apply(void 0,i),a);for(n=0;n<i.length;++n)if(r[n]!==i[n])return void copyToThunk(o.fn.apply(void 0,i),a);copyToThunk(t,a)}Object.defineProperty(exports,"__esModule",{value:!0});var h_1=__webpack_require__(50);exports.thunk=function(t,a,n,e){return void 0===e&&(e=n,n=a,a=void 0),h_1.h(t,{key:a,hook:{init:init,prepatch:prepatch},fn:n,args:e})},exports.default=exports.thunk;

/***/ }),
/* 153 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
function overrideCommandStackOptions(e,r){var t=e.get(types_1.TYPES.CommandStackOptions);for(var o in r)r.hasOwnProperty(o)&&(t[o]=r[o]);return t}Object.defineProperty(exports,"__esModule",{value:!0});var types_1=__webpack_require__(1);exports.overrideCommandStackOptions=overrideCommandStackOptions;

/***/ }),
/* 154 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
Object.defineProperty(exports,"__esModule",{value:!0});var inversify_1=__webpack_require__(0),smodel_storage_1=__webpack_require__(54),types_1=__webpack_require__(1),initialize_canvas_1=__webpack_require__(41),logging_1=__webpack_require__(71),action_dispatcher_1=__webpack_require__(52),action_handler_1=__webpack_require__(23),command_stack_1=__webpack_require__(81),smodel_factory_1=__webpack_require__(10),animation_frame_syncer_1=__webpack_require__(30),viewer_1=__webpack_require__(86),viewer_options_1=__webpack_require__(85),mouse_tool_1=__webpack_require__(15),key_tool_1=__webpack_require__(20),vnode_decorators_1=__webpack_require__(87),view_1=__webpack_require__(83),viewer_cache_1=__webpack_require__(84),dom_helper_1=__webpack_require__(55),id_decorator_1=__webpack_require__(155),command_1=__webpack_require__(5),defaultContainerModule=new inversify_1.ContainerModule(function(e){e(types_1.TYPES.ILogger).to(logging_1.NullLogger).inSingletonScope(),e(types_1.TYPES.LogLevel).toConstantValue(logging_1.LogLevel.warn),e(types_1.TYPES.SModelRegistry).to(smodel_factory_1.SModelRegistry).inSingletonScope(),e(types_1.TYPES.ActionHandlerRegistry).to(action_handler_1.ActionHandlerRegistry).inSingletonScope(),e(types_1.TYPES.ViewRegistry).to(view_1.ViewRegistry).inSingletonScope(),e(types_1.TYPES.IModelFactory).to(smodel_factory_1.SModelFactory).inSingletonScope(),e(types_1.TYPES.IActionDispatcher).to(action_dispatcher_1.ActionDispatcher).inSingletonScope(),e(types_1.TYPES.IActionDispatcherProvider).toProvider(function(e){return function(){return new Promise(function(o){o(e.container.get(types_1.TYPES.IActionDispatcher))})}}),e(types_1.TYPES.IActionHandlerInitializer).to(command_1.CommandActionHandlerInitializer),e(types_1.TYPES.ICommandStack).to(command_stack_1.CommandStack).inSingletonScope(),e(types_1.TYPES.ICommandStackProvider).toProvider(function(e){return function(){return new Promise(function(o){o(e.container.get(types_1.TYPES.ICommandStack))})}}),e(types_1.TYPES.CommandStackOptions).toConstantValue({defaultDuration:250,undoHistoryLimit:50}),e(viewer_1.Viewer).toSelf().inSingletonScope(),e(types_1.TYPES.IViewer).toDynamicValue(function(e){return e.container.get(viewer_1.Viewer)}).inSingletonScope().whenTargetNamed("delegate"),e(viewer_cache_1.ViewerCache).toSelf().inSingletonScope(),e(types_1.TYPES.IViewer).toDynamicValue(function(e){return e.container.get(viewer_cache_1.ViewerCache)}).inSingletonScope().whenTargetIsDefault(),e(types_1.TYPES.IViewerProvider).toProvider(function(e){return function(){return new Promise(function(o){o(e.container.get(types_1.TYPES.IViewer))})}}),e(types_1.TYPES.ViewerOptions).toConstantValue(viewer_options_1.defaultViewerOptions()),e(types_1.TYPES.DOMHelper).to(dom_helper_1.DOMHelper).inSingletonScope(),e(types_1.TYPES.ModelRendererFactory).toFactory(function(e){return function(o){var t=e.container.get(types_1.TYPES.ViewRegistry);return new viewer_1.ModelRenderer(t,o)}}),e(id_decorator_1.IdDecorator).toSelf().inSingletonScope(),e(types_1.TYPES.IVNodeDecorator).toDynamicValue(function(e){return e.container.get(id_decorator_1.IdDecorator)}).inSingletonScope(),e(mouse_tool_1.MouseTool).toSelf().inSingletonScope(),e(types_1.TYPES.IVNodeDecorator).toDynamicValue(function(e){return e.container.get(mouse_tool_1.MouseTool)}).inSingletonScope(),e(key_tool_1.KeyTool).toSelf().inSingletonScope(),e(types_1.TYPES.IVNodeDecorator).toDynamicValue(function(e){return e.container.get(key_tool_1.KeyTool)}).inSingletonScope(),e(vnode_decorators_1.FocusFixDecorator).toSelf().inSingletonScope(),e(types_1.TYPES.IVNodeDecorator).toDynamicValue(function(e){return e.container.get(vnode_decorators_1.FocusFixDecorator)}).inSingletonScope(),e(types_1.TYPES.PopupVNodeDecorator).toDynamicValue(function(e){return e.container.get(id_decorator_1.IdDecorator)}).inSingletonScope(),e(mouse_tool_1.PopupMouseTool).toSelf().inSingletonScope(),e(types_1.TYPES.PopupVNodeDecorator).toDynamicValue(function(e){return e.container.get(mouse_tool_1.PopupMouseTool)}).inSingletonScope(),e(types_1.TYPES.HiddenVNodeDecorator).toDynamicValue(function(e){return e.container.get(id_decorator_1.IdDecorator)}).inSingletonScope(),e(types_1.TYPES.AnimationFrameSyncer).to(animation_frame_syncer_1.AnimationFrameSyncer).inSingletonScope(),e(types_1.TYPES.ICommand).toConstructor(initialize_canvas_1.InitializeCanvasBoundsCommand),e(initialize_canvas_1.CanvasBoundsInitializer).toSelf().inSingletonScope(),e(types_1.TYPES.IVNodeDecorator).toDynamicValue(function(e){return e.container.get(initialize_canvas_1.CanvasBoundsInitializer)}).inSingletonScope(),e(types_1.TYPES.SModelStorage).to(smodel_storage_1.SModelStorage).inSingletonScope()});exports.default=defaultContainerModule;

/***/ }),
/* 155 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
var __decorate=this&&this.__decorate||function(e,t,r,i){var o,n=arguments.length,a=n<3?t:null===i?i=Object.getOwnPropertyDescriptor(t,r):i;if("object"==typeof Reflect&&"function"==typeof Reflect.decorate)a=Reflect.decorate(e,t,r,i);else for(var c=e.length-1;c>=0;c--)(o=e[c])&&(a=(n<3?o(a):n>3?o(t,r,a):o(t,r))||a);return n>3&&a&&Object.defineProperty(t,r,a),a},__metadata=this&&this.__metadata||function(e,t){if("object"==typeof Reflect&&"function"==typeof Reflect.metadata)return Reflect.metadata(e,t)},__param=this&&this.__param||function(e,t){return function(r,i){t(r,i,e)}};Object.defineProperty(exports,"__esModule",{value:!0});var inversify_1=__webpack_require__(0),types_1=__webpack_require__(1),dom_helper_1=__webpack_require__(55),vnode_utils_1=__webpack_require__(11),IdDecorator=function(){function e(e,t){this.logger=e,this.domHelper=t}return e.prototype.decorate=function(e,t){var r=vnode_utils_1.getAttrs(e);return void 0!==r.id&&this.logger.warn(e,"Overriding id of vnode ("+r.id+"). Make sure not to set it manually in view."),r.id=this.domHelper.createUniqueDOMElementId(t),e.key||(e.key=t.id),e},e.prototype.postUpdate=function(){},e=__decorate([inversify_1.injectable(),__param(0,inversify_1.inject(types_1.TYPES.ILogger)),__param(1,inversify_1.inject(types_1.TYPES.DOMHelper)),__metadata("design:paramtypes",[Object,dom_helper_1.DOMHelper])],e)}();exports.IdDecorator=IdDecorator;

/***/ }),
/* 156 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
Object.defineProperty(exports,"__esModule",{value:!0});var inversify_1=__webpack_require__(0),types_1=__webpack_require__(1),bounds_manipulation_1=__webpack_require__(32),hidden_bounds_updater_1=__webpack_require__(157),layout_1=__webpack_require__(57),boundsModule=new inversify_1.ContainerModule(function(e){e(types_1.TYPES.ICommand).toConstructor(bounds_manipulation_1.SetBoundsCommand),e(types_1.TYPES.ICommand).toConstructor(bounds_manipulation_1.RequestBoundsCommand),e(types_1.TYPES.HiddenVNodeDecorator).to(hidden_bounds_updater_1.HiddenBoundsUpdater).inSingletonScope(),e(types_1.TYPES.Layouter).to(layout_1.Layouter).inSingletonScope(),e(types_1.TYPES.LayoutRegistry).to(layout_1.LayoutRegistry).inSingletonScope()});exports.default=boundsModule;

/***/ }),
/* 157 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
var __decorate=this&&this.__decorate||function(e,t,o,n){var i,a=arguments.length,s=a<3?t:null===n?n=Object.getOwnPropertyDescriptor(t,o):n;if("object"==typeof Reflect&&"function"==typeof Reflect.decorate)s=Reflect.decorate(e,t,o,n);else for(var r=e.length-1;r>=0;r--)(i=e[r])&&(s=(a<3?i(s):a>3?i(t,o,s):i(t,o))||s);return a>3&&s&&Object.defineProperty(t,o,s),s},__metadata=this&&this.__metadata||function(e,t){if("object"==typeof Reflect&&"function"==typeof Reflect.metadata)return Reflect.metadata(e,t)},__param=this&&this.__param||function(e,t){return function(o,n){t(o,n,e)}};Object.defineProperty(exports,"__esModule",{value:!0});var inversify_1=__webpack_require__(0),types_1=__webpack_require__(1),geometry_1=__webpack_require__(3),smodel_1=__webpack_require__(2),bounds_manipulation_1=__webpack_require__(32),model_1=__webpack_require__(7),layout_1=__webpack_require__(57),model_2=__webpack_require__(44),BoundsData=function(){function e(){}return e}();exports.BoundsData=BoundsData;var HiddenBoundsUpdater=function(){function e(e,t){this.actionDispatcher=e,this.layouter=t,this.element2boundsData=new Map}return e.prototype.decorate=function(e,t){return(model_1.isSizeable(t)||model_1.isLayoutContainer(t))&&this.element2boundsData.set(t,{vnode:e,bounds:t.bounds,boundsChanged:!1,alignmentChanged:!1}),t instanceof smodel_1.SModelRoot&&(this.root=t),e},e.prototype.postUpdate=function(){if(void 0===this.root||!model_2.isExportable(this.root)||!this.root.export){this.getBoundsFromDOM(),this.layouter.layout(this.element2boundsData);var e=[],t=[];this.element2boundsData.forEach(function(o,n){o.boundsChanged&&void 0!==o.bounds&&e.push({elementId:n.id,newBounds:o.bounds}),o.alignmentChanged&&void 0!==o.alignment&&t.push({elementId:n.id,newAlignment:o.alignment})});var o=void 0!==this.root?this.root.revision:void 0;this.actionDispatcher.dispatch(new bounds_manipulation_1.ComputedBoundsAction(e,o,t)),this.element2boundsData.clear()}},e.prototype.getBoundsFromDOM=function(){var e=this;this.element2boundsData.forEach(function(t,o){if(t.bounds&&model_1.isSizeable(o)){var n=t.vnode;if(n&&n.elm){var i=e.getBounds(n.elm,o);!model_1.isAlignable(o)||geometry_1.almostEquals(i.x,0)&&geometry_1.almostEquals(i.y,0)||(t.alignment={x:-i.x,y:-i.y},t.alignmentChanged=!0);var a={x:o.bounds.x,y:o.bounds.y,width:i.width,height:i.height};geometry_1.almostEquals(a.x,o.bounds.x)&&geometry_1.almostEquals(a.y,o.bounds.y)&&geometry_1.almostEquals(a.width,o.bounds.width)&&geometry_1.almostEquals(a.height,o.bounds.height)||(t.bounds=a,t.boundsChanged=!0)}}})},e.prototype.getBounds=function(e,t){var o=e.getBBox();return{x:o.x,y:o.y,width:o.width,height:o.height}},e=__decorate([inversify_1.injectable(),__param(0,inversify_1.inject(types_1.TYPES.IActionDispatcher)),__param(1,inversify_1.inject(types_1.TYPES.Layouter)),__metadata("design:paramtypes",[Object,layout_1.Layouter])],e)}();exports.HiddenBoundsUpdater=HiddenBoundsUpdater;

/***/ }),
/* 158 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
var __extends=this&&this.__extends||function(){var t=Object.setPrototypeOf||{__proto__:[]}instanceof Array&&function(t,e){t.__proto__=e}||function(t,e){for(var i in e)e.hasOwnProperty(i)&&(t[i]=e[i])};return function(e,i){function n(){this.constructor=e}t(e,i),e.prototype=null===i?Object.create(i):(n.prototype=i.prototype,new n)}}();Object.defineProperty(exports,"__esModule",{value:!0});var animation_1=__webpack_require__(19),ResizeAnimation=function(t){function e(e,i,n,o){void 0===o&&(o=!1);var r=t.call(this,n)||this;return r.model=e,r.elementResizes=i,r.reverse=o,r}return __extends(e,t),e.prototype.tween=function(t){var e=this;return this.elementResizes.forEach(function(i){var n=i.element,o=e.reverse?{width:(1-t)*i.toDimension.width+t*i.fromDimension.width,height:(1-t)*i.toDimension.height+t*i.fromDimension.height}:{width:(1-t)*i.fromDimension.width+t*i.toDimension.width,height:(1-t)*i.fromDimension.height+t*i.toDimension.height};n.bounds={x:n.bounds.x,y:n.bounds.y,width:o.width,height:o.height}}),this.model},e}(animation_1.Animation);exports.ResizeAnimation=ResizeAnimation;

/***/ }),
/* 159 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
Object.defineProperty(exports,"__esModule",{value:!0});var inversify_1=__webpack_require__(0),button_handler_1=__webpack_require__(58),buttonModule=new inversify_1.ContainerModule(function(e){e(button_handler_1.ButtonHandlerRegistry).toSelf().inSingletonScope()});exports.default=buttonModule;

/***/ }),
/* 160 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
Object.defineProperty(exports,"__esModule",{value:!0});var inversify_1=__webpack_require__(0),types_1=__webpack_require__(1),edit_routing_1=__webpack_require__(42),edgeEditModule=new inversify_1.ContainerModule(function(e){e(types_1.TYPES.ICommand).toConstructor(edit_routing_1.SwitchEditModeCommand),e(types_1.TYPES.ICommand).toConstructor(edit_routing_1.MoveRoutingHandleCommand)});exports.default=edgeEditModule;

/***/ }),
/* 161 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
Object.defineProperty(exports,"__esModule",{value:!0});var inversify_1=__webpack_require__(0),types_1=__webpack_require__(1),expand_1=__webpack_require__(43),expandModule=new inversify_1.ContainerModule(function(e){e(types_1.TYPES.IButtonHandler).toConstructor(expand_1.ExpandButtonHandler)});exports.default=expandModule;

/***/ }),
/* 162 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
Object.defineProperty(exports,"__esModule",{value:!0});var snabbdom=__webpack_require__(18),model_1=__webpack_require__(60),smodel_utils_1=__webpack_require__(6),JSX={createElement:snabbdom.svg},ExpandButtonView=function(){function e(){}return e.prototype.render=function(e,t){var n=smodel_utils_1.findParentByFeature(e,model_1.isExpandable),r=void 0!==n&&n.expanded?"M 1,5 L 8,12 L 15,5 Z":"M 1,8 L 8,15 L 8,1 Z";return JSX.createElement("g",{"class-sprotty-button":"{true}","class-enabled":"{button.enabled}"},JSX.createElement("rect",{x:0,y:0,width:16,height:16,opacity:0}),JSX.createElement("path",{d:r}))},e}();exports.ExpandButtonView=ExpandButtonView;

/***/ }),
/* 163 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
Object.defineProperty(exports,"__esModule",{value:!0});var inversify_1=__webpack_require__(0),types_1=__webpack_require__(1),export_1=__webpack_require__(91),svg_exporter_1=__webpack_require__(24),exportSvgModule=new inversify_1.ContainerModule(function(e){e(types_1.TYPES.KeyListener).to(export_1.ExportSvgKeyListener).inSingletonScope(),e(types_1.TYPES.HiddenVNodeDecorator).to(export_1.ExportSvgDecorator).inSingletonScope(),e(types_1.TYPES.ICommand).toConstructor(export_1.ExportSvgCommand),e(types_1.TYPES.SvgExporter).to(svg_exporter_1.SvgExporter).inSingletonScope()});exports.default=exportSvgModule;

/***/ }),
/* 164 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
Object.defineProperty(exports,"__esModule",{value:!0});var inversify_1=__webpack_require__(0),types_1=__webpack_require__(1),fade_1=__webpack_require__(61),fadeModule=new inversify_1.ContainerModule(function(e){e(types_1.TYPES.IVNodeDecorator).to(fade_1.ElementFader).inSingletonScope()});exports.default=fadeModule;

/***/ }),
/* 165 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
Object.defineProperty(exports,"__esModule",{value:!0});var inversify_1=__webpack_require__(0),types_1=__webpack_require__(1),hover_1=__webpack_require__(34),popup_position_updater_1=__webpack_require__(167),initializer_1=__webpack_require__(166),hoverModule=new inversify_1.ContainerModule(function(e){e(types_1.TYPES.PopupVNodeDecorator).to(popup_position_updater_1.PopupPositionUpdater).inSingletonScope(),e(types_1.TYPES.IActionHandlerInitializer).to(initializer_1.PopupActionHandlerInitializer),e(types_1.TYPES.ICommand).toConstructor(hover_1.HoverFeedbackCommand),e(types_1.TYPES.ICommand).toConstructor(hover_1.SetPopupModelCommand),e(types_1.TYPES.MouseListener).to(hover_1.HoverMouseListener),e(types_1.TYPES.PopupMouseListener).to(hover_1.PopupHoverMouseListener),e(types_1.TYPES.KeyListener).to(hover_1.HoverKeyListener),e(types_1.TYPES.HoverState).toConstantValue({mouseOverTimer:void 0,mouseOutTimer:void 0,popupOpen:!1,previousPopupElement:void 0})});exports.default=hoverModule;

/***/ }),
/* 166 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
var __decorate=this&&this.__decorate||function(e,t,r,o){var i,n=arguments.length,p=n<3?t:null===o?o=Object.getOwnPropertyDescriptor(t,r):o;if("object"==typeof Reflect&&"function"==typeof Reflect.decorate)p=Reflect.decorate(e,t,r,o);else for(var c=e.length-1;c>=0;c--)(i=e[c])&&(p=(n<3?i(p):n>3?i(t,r,p):i(t,r))||p);return n>3&&p&&Object.defineProperty(t,r,p),p};Object.defineProperty(exports,"__esModule",{value:!0});var inversify_1=__webpack_require__(0),hover_1=__webpack_require__(34),smodel_factory_1=__webpack_require__(10),center_fit_1=__webpack_require__(66),viewport_1=__webpack_require__(25),move_1=__webpack_require__(45),ClosePopupActionHandler=function(){function e(){this.popupOpen=!1}return e.prototype.handle=function(e){if(e.kind===hover_1.SetPopupModelCommand.KIND)this.popupOpen=e.newRoot.type!==smodel_factory_1.EMPTY_ROOT.type;else if(this.popupOpen)return new hover_1.SetPopupModelAction({id:smodel_factory_1.EMPTY_ROOT.id,type:smodel_factory_1.EMPTY_ROOT.type})},e}(),PopupActionHandlerInitializer=function(){function e(){}return e.prototype.initialize=function(e){var t=new ClosePopupActionHandler;e.register(center_fit_1.FitToScreenCommand.KIND,t),e.register(center_fit_1.CenterCommand.KIND,t),e.register(viewport_1.ViewportCommand.KIND,t),e.register(hover_1.SetPopupModelCommand.KIND,t),e.register(move_1.MoveCommand.KIND,t)},e=__decorate([inversify_1.injectable()],e)}();exports.PopupActionHandlerInitializer=PopupActionHandlerInitializer;

/***/ }),
/* 167 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
var __decorate=this&&this.__decorate||function(e,t,i,n){var o,r=arguments.length,p=r<3?t:null===n?n=Object.getOwnPropertyDescriptor(t,i):n;if("object"==typeof Reflect&&"function"==typeof Reflect.decorate)p=Reflect.decorate(e,t,i,n);else for(var a=e.length-1;a>=0;a--)(o=e[a])&&(p=(r<3?o(p):r>3?o(t,i,p):o(t,i))||p);return r>3&&p&&Object.defineProperty(t,i,p),p},__metadata=this&&this.__metadata||function(e,t){if("object"==typeof Reflect&&"function"==typeof Reflect.metadata)return Reflect.metadata(e,t)},__param=this&&this.__param||function(e,t){return function(i,n){t(i,n,e)}};Object.defineProperty(exports,"__esModule",{value:!0});var inversify_1=__webpack_require__(0),types_1=__webpack_require__(1),PopupPositionUpdater=function(){function e(e){this.options=e}return e.prototype.decorate=function(e,t){return e},e.prototype.postUpdate=function(){var e=document.getElementById(this.options.popupDiv);if(null!==e&&"undefined"!=typeof window){var t=e.getBoundingClientRect();window.innerHeight<t.height+t.top&&(e.style.top=window.scrollY+window.innerHeight-t.height-5+"px"),window.innerWidth<t.left+t.width&&(e.style.left=window.scrollX+window.innerWidth-t.width-5+"px"),t.left<0&&(e.style.left="0px"),t.top<0&&(e.style.top="0px")}},e=__decorate([inversify_1.injectable(),__param(0,inversify_1.inject(types_1.TYPES.ViewerOptions)),__metadata("design:paramtypes",[Object])],e)}();exports.PopupPositionUpdater=PopupPositionUpdater;

/***/ }),
/* 168 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
Object.defineProperty(exports,"__esModule",{value:!0});var inversify_1=__webpack_require__(0),types_1=__webpack_require__(1),move_1=__webpack_require__(45),moveModule=new inversify_1.ContainerModule(function(e){e(types_1.TYPES.MouseListener).to(move_1.MoveMouseListener),e(types_1.TYPES.ICommand).toConstructor(move_1.MoveCommand),e(types_1.TYPES.IVNodeDecorator).to(move_1.LocationDecorator),e(types_1.TYPES.HiddenVNodeDecorator).to(move_1.LocationDecorator)});exports.default=moveModule;

/***/ }),
/* 169 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
Object.defineProperty(exports,"__esModule",{value:!0});var inversify_1=__webpack_require__(0),types_1=__webpack_require__(1),open_1=__webpack_require__(62),openModule=new inversify_1.ContainerModule(function(e){e(types_1.TYPES.MouseListener).to(open_1.OpenMouseListener)});exports.default=openModule;

/***/ }),
/* 170 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
Object.defineProperty(exports,"__esModule",{value:!0});var inversify_1=__webpack_require__(0),types_1=__webpack_require__(1),select_1=__webpack_require__(93),selectModule=new inversify_1.ContainerModule(function(e){e(types_1.TYPES.ICommand).toConstructor(select_1.SelectCommand),e(types_1.TYPES.ICommand).toConstructor(select_1.SelectAllCommand),e(types_1.TYPES.KeyListener).to(select_1.SelectKeyboardListener),e(types_1.TYPES.MouseListener).to(select_1.SelectMouseListener)});exports.default=selectModule;

/***/ }),
/* 171 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
Object.defineProperty(exports,"__esModule",{value:!0});var inversify_1=__webpack_require__(0),types_1=__webpack_require__(1),undo_redo_1=__webpack_require__(63),undoRedoModule=new inversify_1.ContainerModule(function(e){e(types_1.TYPES.KeyListener).to(undo_redo_1.UndoRedoKeyListener)});exports.default=undoRedoModule;

/***/ }),
/* 172 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
Object.defineProperty(exports,"__esModule",{value:!0});var inversify_1=__webpack_require__(0),types_1=__webpack_require__(1),center_fit_1=__webpack_require__(66),viewport_1=__webpack_require__(25),scroll_1=__webpack_require__(94),zoom_1=__webpack_require__(95),viewportModule=new inversify_1.ContainerModule(function(e){e(types_1.TYPES.ICommand).toConstructor(center_fit_1.CenterCommand),e(types_1.TYPES.ICommand).toConstructor(center_fit_1.FitToScreenCommand),e(types_1.TYPES.ICommand).toConstructor(viewport_1.ViewportCommand),e(types_1.TYPES.KeyListener).to(center_fit_1.CenterKeyboardListener),e(types_1.TYPES.MouseListener).to(scroll_1.ScrollMouseListener),e(types_1.TYPES.MouseListener).to(zoom_1.ZoomMouseListener)});exports.default=viewportModule;

/***/ }),
/* 173 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
var __decorate=this&&this.__decorate||function(e,r,t,n){var o,i=arguments.length,a=i<3?r:null===n?n=Object.getOwnPropertyDescriptor(r,t):n;if("object"==typeof Reflect&&"function"==typeof Reflect.decorate)a=Reflect.decorate(e,r,t,n);else for(var c=e.length-1;c>=0;c--)(o=e[c])&&(a=(i<3?o(a):i>3?o(r,t,a):o(r,t))||a);return i>3&&a&&Object.defineProperty(r,t,a),a};Object.defineProperty(exports,"__esModule",{value:!0});var inversify_1=__webpack_require__(0),geometry_1=__webpack_require__(3),LinearEdgeRouter=function(){function e(){}return e.prototype.route=function(e,r){void 0===r&&(r={minimalPointDistance:2});var t=e.source,n=e.target;if(void 0===t||void 0===n)return[];var o,i,a=void 0!==e.routingPoints?e.routingPoints.length:0;if(a>=1){var c=e.routingPoints[0];o=t.getTranslatedAnchor(c,e.parent,e,e.sourceAnchorCorrection);var s=e.routingPoints[a-1];i=n.getTranslatedAnchor(s,e.parent,e,e.targetAnchorCorrection)}else{var u=geometry_1.center(n.bounds);o=t.getTranslatedAnchor(u,n.parent,e,e.sourceAnchorCorrection);var g=geometry_1.center(t.bounds);i=n.getTranslatedAnchor(g,t.parent,e,e.targetAnchorCorrection)}var d=[];d.push({kind:"source",x:o.x,y:o.y});for(var l=0;l<a;l++){var v=e.routingPoints[l];(l>0&&l<a-1||0===l&&geometry_1.maxDistance(o,v)>=r.minimalPointDistance+(e.sourceAnchorCorrection||0)||l===a-1&&geometry_1.maxDistance(v,i)>=r.minimalPointDistance+(e.targetAnchorCorrection||0))&&d.push({kind:"linear",x:v.x,y:v.y,pointIndex:l})}return d.push({kind:"target",x:i.x,y:i.y}),d},e=__decorate([inversify_1.injectable()],e)}();exports.LinearEdgeRouter=LinearEdgeRouter;

/***/ }),
/* 174 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
var __extends=this&&this.__extends||function(){var e=Object.setPrototypeOf||{__proto__:[]}instanceof Array&&function(e,t){e.__proto__=t}||function(e,t){for(var o in t)t.hasOwnProperty(o)&&(e[o]=t[o])};return function(t,o){function r(){this.constructor=t}e(t,o),t.prototype=null===o?Object.create(o):(r.prototype=o.prototype,new r)}}(),__decorate=this&&this.__decorate||function(e,t,o,r){var i,s=arguments.length,n=s<3?t:null===r?r=Object.getOwnPropertyDescriptor(t,o):r;if("object"==typeof Reflect&&"function"==typeof Reflect.decorate)n=Reflect.decorate(e,t,o,r);else for(var a=e.length-1;a>=0;a--)(i=e[a])&&(n=(s<3?i(n):s>3?i(t,o,n):i(t,o))||n);return s>3&&n&&Object.defineProperty(t,o,n),n};Object.defineProperty(exports,"__esModule",{value:!0});var inversify_1=__webpack_require__(0),smodel_factory_1=__webpack_require__(10),smodel_1=__webpack_require__(2),smodel_utils_1=__webpack_require__(6),sgraph_1=__webpack_require__(46),model_1=__webpack_require__(59),SGraphFactory=function(e){function t(){return null!==e&&e.apply(this,arguments)||this}return __extends(t,e),t.prototype.createElement=function(e,t){var o;if(this.registry.hasKey(e.type)){var r=this.registry.get(e.type,void 0);if(!(r instanceof smodel_1.SChildElement))throw new Error("Element with type "+e.type+" was expected to be an SChildElement.");o=r}else o=this.isNodeSchema(e)?new sgraph_1.SNode:this.isPortSchema(e)?new sgraph_1.SPort:this.isEdgeSchema(e)?new sgraph_1.SEdge:this.isLabelSchema(e)?new sgraph_1.SLabel:this.isCompartmentSchema(e)?new sgraph_1.SCompartment:this.isButtonSchema(e)?new model_1.SButton:new smodel_1.SChildElement;return this.initializeChild(o,e,t)},t.prototype.createRoot=function(e){var t;if(this.registry.hasKey(e.type)){var o=this.registry.get(e.type,void 0);if(!(o instanceof smodel_1.SModelRoot))throw new Error("Element with type "+e.type+" was expected to be an SModelRoot.");t=o}else t=this.isGraphSchema(e)?new sgraph_1.SGraph:new smodel_1.SModelRoot;return this.initializeRoot(t,e)},t.prototype.isGraphSchema=function(e){return"graph"===smodel_utils_1.getBasicType(e)},t.prototype.isNodeSchema=function(e){return"node"===smodel_utils_1.getBasicType(e)},t.prototype.isPortSchema=function(e){return"port"===smodel_utils_1.getBasicType(e)},t.prototype.isEdgeSchema=function(e){return"edge"===smodel_utils_1.getBasicType(e)},t.prototype.isLabelSchema=function(e){return"label"===smodel_utils_1.getBasicType(e)},t.prototype.isCompartmentSchema=function(e){return"comp"===smodel_utils_1.getBasicType(e)},t.prototype.isButtonSchema=function(e){return"button"===smodel_utils_1.getBasicType(e)},t=__decorate([inversify_1.injectable()],t)}(smodel_factory_1.SModelFactory);exports.SGraphFactory=SGraphFactory;

/***/ }),
/* 175 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
Object.defineProperty(exports,"__esModule",{value:!0});var snabbdom=__webpack_require__(18),geometry_1=__webpack_require__(3),vnode_utils_1=__webpack_require__(11),smodel_utils_1=__webpack_require__(6),model_1=__webpack_require__(21),JSX={createElement:snabbdom.svg},SGraphView=function(){function e(){}return e.prototype.render=function(e,t){var n="scale("+e.zoom+") translate("+-e.scroll.x+","+-e.scroll.y+")";return JSX.createElement("svg",{"class-sprotty-graph":!0},JSX.createElement("g",{transform:n},t.renderChildren(e)))},e}();exports.SGraphView=SGraphView;var PolylineEdgeView=function(){function e(){}return e.prototype.render=function(e,t){var n=e.route();return 0===n.length?this.renderDanglingEdge("Cannot compute route",e,t):JSX.createElement("g",{"class-sprotty-edge":!0,"class-mouseover":e.hoverFeedback},this.renderLine(e,n,t),this.renderAdditionals(e,n,t),t.renderChildren(e,{route:n}))},e.prototype.renderLine=function(e,t,n){for(var r=t[0],o="M "+r.x+","+r.y,i=1;i<t.length;i++){var s=t[i];o+=" L "+s.x+","+s.y}return JSX.createElement("path",{d:o})},e.prototype.renderAdditionals=function(e,t,n){return[]},e.prototype.renderDanglingEdge=function(e,t,n){return JSX.createElement("text",{"class-sprotty-edge-dangling":!0,title:e},"?")},e}();exports.PolylineEdgeView=PolylineEdgeView;var SRoutingHandleView=function(){function e(){this.minimalPointDistance=10}return e.prototype.render=function(e,t,n){if(n&&n.route){var r=this.getPosition(e,n.route);if(void 0!==r){var o=JSX.createElement("circle",{"class-sprotty-routing-handle":!0,"class-selected":e.selected,"class-mouseover":e.hoverFeedback,cx:r.x,cy:r.y});return vnode_utils_1.setAttr(o,"data-kind",e.kind),o}}return JSX.createElement("g",null)},e.prototype.getPosition=function(e,t){return"line"===e.kind?this.getLinePosition(e,t):this.getJunctionPosition(e,t)},e.prototype.getJunctionPosition=function(e,t){return t.find(function(t){return t.pointIndex===e.pointIndex})},e.prototype.getLinePosition=function(e,t){var n=e.parent;if(model_1.isRoutable(n)){for(var r=function(e){return void 0!==e.pointIndex?e.pointIndex:"target"===e.kind?n.routingPoints.length:-1},o=void 0,i=void 0,s=0,a=t;s<a.length;s++){var u=a[s],l=r(u);l<=e.pointIndex&&(void 0===o||l>r(o))&&(o=u),l>e.pointIndex&&(void 0===i||l<r(i))&&(i=u)}if(void 0!==o&&void 0!==i){if(r(o)!==e.pointIndex&&e.pointIndex>=0){var d=n.routingPoints[e.pointIndex];if(geometry_1.maxDistance(d,o)>=geometry_1.maxDistance(d,i))return}if(r(i)!==e.pointIndex+1&&e.pointIndex+1<n.routingPoints.length){var d=n.routingPoints[e.pointIndex+1];if(geometry_1.maxDistance(d,o)<geometry_1.maxDistance(d,i))return}if(geometry_1.maxDistance(o,i)>=this.minimalPointDistance)return geometry_1.centerOfLine(o,i)}}},e}();exports.SRoutingHandleView=SRoutingHandleView;var SLabelView=function(){function e(){}return e.prototype.render=function(e,t){var n=JSX.createElement("text",{"class-sprotty-label":!0},e.text),r=smodel_utils_1.getSubType(e);return r&&vnode_utils_1.setAttr(n,"class",r),n},e}();exports.SLabelView=SLabelView;var SCompartmentView=function(){function e(){}return e.prototype.render=function(e,t){var n="translate("+e.bounds.x+", "+e.bounds.y+")",r=JSX.createElement("g",{transform:n,"class-sprotty-comp":"{true}"},t.renderChildren(e)),o=smodel_utils_1.getSubType(e);return o&&vnode_utils_1.setAttr(r,"class",o),r},e}();exports.SCompartmentView=SCompartmentView;

/***/ }),
/* 176 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
Object.defineProperty(exports,"__esModule",{value:!0});var strings_1=__webpack_require__(145),PreRenderedView=function(){function e(){}return e.prototype.render=function(e,t){var r=strings_1.default(e.code);return this.correctNamespace(r),r},e.prototype.correctNamespace=function(e){"svg"!==e.sel&&"g"!==e.sel||this.setNamespace(e,"http://www.w3.org/2000/svg")},e.prototype.setNamespace=function(e,t){void 0===e.data&&(e.data={}),e.data.ns=t;var r=e.children;if(void 0!==r)for(var s=0;s<r.length;s++){var a=r[s];"string"!=typeof a&&this.setNamespace(a,t)}},e}();exports.PreRenderedView=PreRenderedView;

/***/ }),
/* 177 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
Object.defineProperty(exports,"__esModule",{value:!0});var snabbdom=__webpack_require__(18),vnode_utils_1=__webpack_require__(11),JSX={createElement:snabbdom.html},HtmlRootView=function(){function e(){}return e.prototype.render=function(e,t){for(var r=JSX.createElement("div",null,t.renderChildren(e)),n=0,o=e.classes;n<o.length;n++){var s=o[n];vnode_utils_1.setClass(r,s,!0)}return r},e}();exports.HtmlRootView=HtmlRootView;

/***/ }),
/* 178 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
var __extends=this&&this.__extends||function(){var e=Object.setPrototypeOf||{__proto__:[]}instanceof Array&&function(e,t){e.__proto__=t}||function(e,t){for(var r in t)t.hasOwnProperty(r)&&(e[r]=t[r])};return function(t,r){function o(){this.constructor=t}e(t,r),t.prototype=null===r?Object.create(r):(o.prototype=r.prototype,new o)}}();Object.defineProperty(exports,"__esModule",{value:!0});var smodel_1=__webpack_require__(2),geometry_1=__webpack_require__(3),anchors_1=__webpack_require__(98),model_1=__webpack_require__(7),model_2=__webpack_require__(22),model_3=__webpack_require__(12),sgraph_1=__webpack_require__(46),CircularNode=function(e){function t(){var t=null!==e&&e.apply(this,arguments)||this;return t.strokeWidth=0,t}return __extends(t,e),Object.defineProperty(t.prototype,"radius",{get:function(){var e=Math.min(this.size.width,this.size.height);return e>0?e/2:0},enumerable:!0,configurable:!0}),t.prototype.getAnchor=function(e,t){void 0===t&&(t=0);var r=.5*this.strokeWidth;return anchors_1.computeCircleAnchor(this.position,this.radius,e,t+r)},t}(sgraph_1.SNode);exports.CircularNode=CircularNode;var RectangularNode=function(e){function t(){var t=null!==e&&e.apply(this,arguments)||this;return t.strokeWidth=0,t}return __extends(t,e),t.prototype.getAnchor=function(e,t){void 0===t&&(t=0);var r=.5*this.strokeWidth;return anchors_1.computeRectangleAnchor(this.bounds,e,t+r)},t}(sgraph_1.SNode);exports.RectangularNode=RectangularNode;var CircularPort=function(e){function t(){var t=null!==e&&e.apply(this,arguments)||this;return t.strokeWidth=0,t}return __extends(t,e),Object.defineProperty(t.prototype,"radius",{get:function(){var e=Math.min(this.size.width,this.size.height);return e>0?e/2:0},enumerable:!0,configurable:!0}),t.prototype.getAnchor=function(e,t){void 0===t&&(t=0);var r=.5*this.strokeWidth;return anchors_1.computeCircleAnchor(this.position,this.radius,e,t+r)},t}(sgraph_1.SPort);exports.CircularPort=CircularPort;var RectangularPort=function(e){function t(){var t=null!==e&&e.apply(this,arguments)||this;return t.strokeWidth=0,t}return __extends(t,e),t.prototype.getAnchor=function(e,t){void 0===t&&(t=0);var r=.5*this.strokeWidth;return anchors_1.computeRectangleAnchor(this.bounds,e,t+r)},t}(sgraph_1.SPort);exports.RectangularPort=RectangularPort;var HtmlRoot=function(e){function t(){var t=null!==e&&e.apply(this,arguments)||this;return t.classes=[],t}return __extends(t,e),t}(smodel_1.SModelRoot);exports.HtmlRoot=HtmlRoot;var PreRenderedElement=function(e){function t(){return null!==e&&e.apply(this,arguments)||this}return __extends(t,e),t}(smodel_1.SChildElement);exports.PreRenderedElement=PreRenderedElement;var ShapedPreRenderedElement=function(e){function t(){var t=null!==e&&e.apply(this,arguments)||this;return t.position=geometry_1.ORIGIN_POINT,t.size=geometry_1.EMPTY_DIMENSION,t.selected=!1,t.alignment=geometry_1.ORIGIN_POINT,t}return __extends(t,e),Object.defineProperty(t.prototype,"bounds",{get:function(){return{x:this.position.x,y:this.position.y,width:this.size.width,height:this.size.height}},set:function(e){this.position={x:e.x,y:e.y},this.size={width:e.width,height:e.height}},enumerable:!0,configurable:!0}),t.prototype.hasFeature=function(e){return e===model_2.moveFeature||e===model_1.boundsFeature||e===model_3.selectFeature||e===model_1.alignFeature},t}(PreRenderedElement);exports.ShapedPreRenderedElement=ShapedPreRenderedElement;

/***/ }),
/* 179 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
Object.defineProperty(exports,"__esModule",{value:!0});var snabbdom=__webpack_require__(18),sgraph_1=__webpack_require__(46),JSX={createElement:snabbdom.svg},SvgViewportView=function(){function e(){}return e.prototype.render=function(e,r){var t="scale("+e.zoom+") translate("+-e.scroll.x+","+-e.scroll.y+")";return JSX.createElement("svg",null,JSX.createElement("g",{transform:t},r.renderChildren(e)))},e}();exports.SvgViewportView=SvgViewportView;var CircularNodeView=function(){function e(){}return e.prototype.render=function(e,r){var t=this.getRadius(e);return JSX.createElement("g",null,JSX.createElement("circle",{"class-sprotty-node":e instanceof sgraph_1.SNode,"class-sprotty-port":e instanceof sgraph_1.SPort,"class-mouseover":e.hoverFeedback,"class-selected":e.selected,r:t,cx:t,cy:t}),r.renderChildren(e))},e.prototype.getRadius=function(e){var r=Math.min(e.size.width,e.size.height);return r>0?r/2:0},e}();exports.CircularNodeView=CircularNodeView;var RectangularNodeView=function(){function e(){}return e.prototype.render=function(e,r){return JSX.createElement("g",null,JSX.createElement("rect",{"class-sprotty-node":e instanceof sgraph_1.SNode,"class-sprotty-port":e instanceof sgraph_1.SPort,"class-mouseover":e.hoverFeedback,"class-selected":e.selected,x:"0",y:"0",width:Math.max(e.size.width,0),height:Math.max(e.size.height,0)}),r.renderChildren(e))},e}();exports.RectangularNodeView=RectangularNodeView;

/***/ }),
/* 180 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
Object.defineProperty(exports,"__esModule",{value:!0});var inversify_1=__webpack_require__(0),types_1=__webpack_require__(1),modelSourceModule=new inversify_1.ContainerModule(function(e){e(types_1.TYPES.ModelSourceProvider).toProvider(function(e){return function(){return new Promise(function(r){r(e.container.get(types_1.TYPES.ModelSource))})}})});exports.default=modelSourceModule;

/***/ }),
/* 181 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
var __extends=this&&this.__extends||function(){var e=Object.setPrototypeOf||{__proto__:[]}instanceof Array&&function(e,t){e.__proto__=t}||function(e,t){for(var o in t)t.hasOwnProperty(o)&&(e[o]=t[o])};return function(t,o){function r(){this.constructor=t}e(t,o),t.prototype=null===o?Object.create(o):(r.prototype=o.prototype,new r)}}(),__decorate=this&&this.__decorate||function(e,t,o,r){var n,i=arguments.length,a=i<3?t:null===r?r=Object.getOwnPropertyDescriptor(t,o):r;if("object"==typeof Reflect&&"function"==typeof Reflect.decorate)a=Reflect.decorate(e,t,o,r);else for(var s=e.length-1;s>=0;s--)(n=e[s])&&(a=(i<3?n(a):i>3?n(t,o,a):n(t,o))||a);return i>3&&a&&Object.defineProperty(t,o,a),a},__metadata=this&&this.__metadata||function(e,t){if("object"==typeof Reflect&&"function"==typeof Reflect.metadata)return Reflect.metadata(e,t)},__param=this&&this.__param||function(e,t){return function(o,r){t(o,r,e)}},__awaiter=this&&this.__awaiter||function(e,t,o,r){return new(o||(o=Promise))(function(n,i){function a(e){try{d(r.next(e))}catch(e){i(e)}}function s(e){try{d(r.throw(e))}catch(e){i(e)}}function d(e){e.done?n(e.value):new o(function(t){t(e.value)}).then(a,s)}d((r=r.apply(e,t||[])).next())})},__generator=this&&this.__generator||function(e,t){function o(e){return function(t){return r([e,t])}}function r(o){if(n)throw new TypeError("Generator is already executing.");for(;d;)try{if(n=1,i&&(a=i[2&o[0]?"return":o[0]?"throw":"next"])&&!(a=a.call(i,o[1])).done)return a;switch(i=0,a&&(o=[0,a.value]),o[0]){case 0:case 1:a=o;break;case 4:return d.label++,{value:o[1],done:!1};case 5:d.label++,i=o[1],o=[0];continue;case 7:o=d.ops.pop(),d.trys.pop();continue;default:if(a=d.trys,!(a=a.length>0&&a[a.length-1])&&(6===o[0]||2===o[0])){d=0;continue}if(3===o[0]&&(!a||o[1]>a[0]&&o[1]<a[3])){d.label=o[1];break}if(6===o[0]&&d.label<a[1]){d.label=a[1],a=o;break}if(a&&d.label<a[2]){d.label=a[2],d.ops.push(o);break}a[2]&&d.ops.pop(),d.trys.pop();continue}o=t.call(e,d)}catch(e){o=[6,e],i=0}finally{n=a=0}if(5&o[0])throw o[1];return{value:o[0]?o[1]:void 0,done:!0}}var n,i,a,s,d={label:0,sent:function(){if(1&a[0])throw a[1];return a[1]},trys:[],ops:[]};return s={next:o(0),throw:o(1),return:o(2)},"function"==typeof Symbol&&(s[Symbol.iterator]=function(){return this}),s};Object.defineProperty(exports,"__esModule",{value:!0});var inversify_1=__webpack_require__(0),async_1=__webpack_require__(184),types_1=__webpack_require__(1),action_handler_1=__webpack_require__(23),smodel_utils_1=__webpack_require__(6),set_model_1=__webpack_require__(31),smodel_1=__webpack_require__(2),bounds_manipulation_1=__webpack_require__(32),model_matching_1=__webpack_require__(64),update_model_1=__webpack_require__(65),hover_1=__webpack_require__(34),model_source_1=__webpack_require__(68),svg_exporter_1=__webpack_require__(24),file_saver_1=__webpack_require__(73),expand_1=__webpack_require__(43),diagram_state_1=__webpack_require__(97),LocalModelSource=function(e){function t(t,o,r,n,i,a,s){var d=e.call(this,t,o,r)||this;return d.logger=n,d.modelProvider=i,d.popupModelProvider=a,d.layoutEngine=s,d.currentRoot={type:"NONE",id:"ROOT"},d.diagramState={expansionState:new diagram_state_1.ExpansionState(d.currentRoot)},d.pendingUpdates=[],d}return __extends(t,e),Object.defineProperty(t.prototype,"model",{get:function(){return this.currentRoot},set:function(e){this.setModel(e)},enumerable:!0,configurable:!0}),t.prototype.initialize=function(t){e.prototype.initialize.call(this,t),t.registerCommand(update_model_1.UpdateModelCommand),t.register(bounds_manipulation_1.ComputedBoundsAction.KIND,this),t.register(hover_1.RequestPopupModelAction.KIND,this),t.register(expand_1.CollapseExpandAction.KIND,this),t.register(expand_1.CollapseExpandAllAction.KIND,this)},t.prototype.setModel=function(e){return this.currentRoot=e,this.diagramState={expansionState:new diagram_state_1.ExpansionState(e)},this.submitModel(e,!1)},t.prototype.updateModel=function(e){return void 0===e?this.submitModel(this.currentRoot,!0):(this.currentRoot=e,this.submitModel(e,!0))},t.prototype.submitModel=function(e,t){if(this.viewerOptions.needsClientLayout){var o=new async_1.Deferred;return this.pendingUpdates.push(o),this.actionDispatcher.dispatch(new bounds_manipulation_1.RequestBoundsAction(e)),o.promise}return this.doSubmitModel(e,t)},t.prototype.doSubmitModel=function(e,t,o){return __awaiter(this,void 0,void 0,function(){var r,n,i,a,s;return __generator(this,function(d){switch(d.label){case 0:if(void 0===this.layoutEngine)return[3,6];d.label=1;case 1:return d.trys.push([1,5,,6]),r=this.layoutEngine.layout(e,o),r instanceof Promise?[4,r]:[3,3];case 2:return e=d.sent(),[3,4];case 3:void 0!==r&&(e=r),d.label=4;case 4:return[3,6];case 5:return n=d.sent(),this.logger.error(this,n.toString(),n.stack),[3,6];case 6:return i=this.lastSubmittedModelType,(this.lastSubmittedModelType=e.type,a=this.pendingUpdates,this.pendingUpdates=[],t&&e.type===i)?(s=Array.isArray(t)?t:e,[4,this.actionDispatcher.dispatch(new update_model_1.UpdateModelAction(s))]):[3,8];case 7:return d.sent(),[3,10];case 8:return[4,this.actionDispatcher.dispatch(new set_model_1.SetModelAction(e))];case 9:d.sent(),d.label=10;case 10:return a.forEach(function(e){return e.resolve()}),[2]}})})},t.prototype.applyMatches=function(e){var t=this.currentRoot;return model_matching_1.applyMatches(t,e),this.submitModel(t,e)},t.prototype.addElements=function(e){for(var t=[],o=0,r=e;o<r.length;o++){var n=r[o],i=n;void 0!==i.element&&void 0!==i.parentId?t.push({right:i.element,rightParentId:i.parentId}):void 0!==i.id&&t.push({right:i,rightParentId:this.currentRoot.id})}return this.applyMatches(t)},t.prototype.removeElements=function(e){var t=[],o=new smodel_1.SModelIndex;o.add(this.currentRoot);for(var r=0,n=e;r<n.length;r++){var i=n[r],a=i;if(void 0!==a.elementId&&void 0!==a.parentId){var s=o.getById(a.elementId);void 0!==s&&t.push({left:s,leftParentId:a.parentId})}else{var s=o.getById(a);void 0!==s&&t.push({left:s,leftParentId:this.currentRoot.id})}}return this.applyMatches(t)},t.prototype.handle=function(e){switch(e.kind){case set_model_1.RequestModelAction.KIND:this.handleRequestModel(e);break;case bounds_manipulation_1.ComputedBoundsAction.KIND:this.handleComputedBounds(e);break;case hover_1.RequestPopupModelAction.KIND:this.handleRequestPopupModel(e);break;case svg_exporter_1.ExportSvgAction.KIND:this.handleExportSvgAction(e);break;case expand_1.CollapseExpandAction.KIND:this.handleCollapseExpandAction(e);break;case expand_1.CollapseExpandAllAction.KIND:this.handleCollapseExpandAllAction(e)}},t.prototype.handleRequestModel=function(e){this.modelProvider&&(this.currentRoot=this.modelProvider.getModel(this.diagramState,this.currentRoot)),this.submitModel(this.currentRoot,!1)},t.prototype.handleComputedBounds=function(e){var t=this.currentRoot,o=new smodel_1.SModelIndex;o.add(t);for(var r=0,n=e.bounds;r<n.length;r++){var i=n[r],a=o.getById(i.elementId);void 0!==a&&this.applyBounds(a,i.newBounds)}if(void 0!==e.alignments)for(var s=0,d=e.alignments;s<d.length;s++){var l=d[s],a=o.getById(l.elementId);void 0!==a&&this.applyAlignment(a,l.newAlignment)}this.doSubmitModel(t,!0,o)},t.prototype.applyBounds=function(e,t){var o=e;o.position={x:t.x,y:t.y},o.size={width:t.width,height:t.height}},t.prototype.applyAlignment=function(e,t){e.alignment={x:t.x,y:t.y}},t.prototype.handleRequestPopupModel=function(e){if(void 0!==this.popupModelProvider){var t=smodel_utils_1.findElement(this.currentRoot,e.elementId),o=this.popupModelProvider.getPopupModel(e,t);void 0!==o&&(o.canvasBounds=e.bounds,this.actionDispatcher.dispatch(new hover_1.SetPopupModelAction(o)))}},t.prototype.handleExportSvgAction=function(e){var t=new Blob([e.svg],{type:"text/plain;charset=utf-8"});file_saver_1.saveAs(t,"diagram.svg")},t.prototype.handleCollapseExpandAction=function(e){if(void 0!==this.modelProvider){this.diagramState.expansionState.apply(e);var t=this.modelProvider.getModel(this.diagramState,this.currentRoot);this.updateModel(t)}},t.prototype.handleCollapseExpandAllAction=function(e){if(void 0!==this.modelProvider){e.expand||this.diagramState.expansionState.collapseAll();var t=this.modelProvider.getModel(this.diagramState,this.currentRoot);this.updateModel(t)}},t=__decorate([inversify_1.injectable(),__param(0,inversify_1.inject(types_1.TYPES.IActionDispatcher)),__param(1,inversify_1.inject(types_1.TYPES.ActionHandlerRegistry)),__param(2,inversify_1.inject(types_1.TYPES.ViewerOptions)),__param(3,inversify_1.inject(types_1.TYPES.ILogger)),__param(4,inversify_1.inject(types_1.TYPES.StateAwareModelProvider)),__param(4,inversify_1.optional()),__param(5,inversify_1.inject(types_1.TYPES.IPopupModelProvider)),__param(5,inversify_1.optional()),__param(6,inversify_1.inject(types_1.TYPES.IModelLayoutEngine)),__param(6,inversify_1.optional()),__metadata("design:paramtypes",[Object,action_handler_1.ActionHandlerRegistry,Object,Object,Object,Object,Object])],t)}(model_source_1.ModelSource);exports.LocalModelSource=LocalModelSource;

/***/ }),
/* 182 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
var __decorate=this&&this.__decorate||function(e,t,o,r){var n,i=arguments.length,g=i<3?t:null===r?r=Object.getOwnPropertyDescriptor(t,o):r;if("object"==typeof Reflect&&"function"==typeof Reflect.decorate)g=Reflect.decorate(e,t,o,r);else for(var a=e.length-1;a>=0;a--)(n=e[a])&&(g=(i<3?n(g):i>3?n(t,o,g):n(t,o))||g);return i>3&&g&&Object.defineProperty(t,o,g),g},__metadata=this&&this.__metadata||function(e,t){if("object"==typeof Reflect&&"function"==typeof Reflect.metadata)return Reflect.metadata(e,t)},__param=this&&this.__param||function(e,t){return function(o,r){t(o,r,e)}};Object.defineProperty(exports,"__esModule",{value:!0});var inversify_1=__webpack_require__(0),logging_1=__webpack_require__(71),types_1=__webpack_require__(1),LoggingAction=function(){function e(t,o,r,n,i){this.severity=t,this.time=o,this.caller=r,this.message=n,this.params=i,this.kind=e.KIND}return e.KIND="logging",e}();exports.LoggingAction=LoggingAction;var ForwardingLogger=function(){function e(e,t){this.modelSourceProvider=e,this.logLevel=t}return e.prototype.error=function(e,t){for(var o=[],r=2;r<arguments.length;r++)o[r-2]=arguments[r];this.logLevel>=logging_1.LogLevel.error&&this.forward(e,t,logging_1.LogLevel.error,o)},e.prototype.warn=function(e,t){for(var o=[],r=2;r<arguments.length;r++)o[r-2]=arguments[r];this.logLevel>=logging_1.LogLevel.warn&&this.forward(e,t,logging_1.LogLevel.warn,o)},e.prototype.info=function(e,t){for(var o=[],r=2;r<arguments.length;r++)o[r-2]=arguments[r];this.logLevel>=logging_1.LogLevel.info&&this.forward(e,t,logging_1.LogLevel.info,o)},e.prototype.log=function(e,t){for(var o=[],r=2;r<arguments.length;r++)o[r-2]=arguments[r];if(this.logLevel>=logging_1.LogLevel.log)try{var n="object"==typeof e?e.constructor.name:String(e);console.log.apply(e,[n+": "+t].concat(o))}catch(e){}},e.prototype.forward=function(e,t,o,r){var n=new Date,i=new LoggingAction(logging_1.LogLevel[o],n.toLocaleTimeString(),"object"==typeof e?e.constructor.name:String(e),t,r.map(function(e){return JSON.stringify(e)}));this.modelSourceProvider().then(function(o){try{o.handle(i)}catch(o){try{console.log.apply(e,[t,i,o])}catch(e){}}})},e=__decorate([inversify_1.injectable(),__param(0,inversify_1.inject(types_1.TYPES.ModelSourceProvider)),__param(1,inversify_1.inject(types_1.TYPES.LogLevel)),__metadata("design:paramtypes",[Function,Number])],e)}();exports.ForwardingLogger=ForwardingLogger;

/***/ }),
/* 183 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
var __extends=this&&this.__extends||function(){var e=Object.setPrototypeOf||{__proto__:[]}instanceof Array&&function(e,t){e.__proto__=t}||function(e,t){for(var r in t)t.hasOwnProperty(r)&&(e[r]=t[r])};return function(t,r){function o(){this.constructor=t}e(t,r),t.prototype=null===r?Object.create(r):(o.prototype=r.prototype,new o)}}(),__decorate=this&&this.__decorate||function(e,t,r,o){var n,i=arguments.length,c=i<3?t:null===o?o=Object.getOwnPropertyDescriptor(t,r):o;if("object"==typeof Reflect&&"function"==typeof Reflect.decorate)c=Reflect.decorate(e,t,r,o);else for(var s=e.length-1;s>=0;s--)(n=e[s])&&(c=(i<3?n(c):i>3?n(t,r,c):n(t,r))||c);return i>3&&c&&Object.defineProperty(t,r,c),c};Object.defineProperty(exports,"__esModule",{value:!0});var inversify_1=__webpack_require__(0),diagram_server_1=__webpack_require__(96),WebSocketDiagramServer=function(e){function t(){return null!==e&&e.apply(this,arguments)||this}return __extends(t,e),t.prototype.listen=function(e){var t=this;e.addEventListener("message",function(e){t.messageReceived(e.data)}),e.addEventListener("error",function(e){t.logger.error(t,"error event received",e)}),this.webSocket=e},t.prototype.disconnect=function(){this.webSocket&&(this.webSocket.close(),this.webSocket=void 0)},t.prototype.sendMessage=function(e){if(!this.webSocket)throw new Error("WebSocket is not connected");this.webSocket.send(JSON.stringify(e))},t=__decorate([inversify_1.injectable()],t)}(diagram_server_1.DiagramServer);exports.WebSocketDiagramServer=WebSocketDiagramServer;

/***/ }),
/* 184 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
Object.defineProperty(exports,"__esModule",{value:!0});var Deferred=function(){function e(){var e=this;this.promise=new Promise(function(r,t){e.resolve=r,e.reject=t})}return e}();exports.Deferred=Deferred;

/***/ }),
/* 185 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
function rgb(t,r,o){return{red:t,green:r,blue:o}}function toSVG(t){return"rgb("+t.red+","+t.green+","+t.blue+")"}Object.defineProperty(exports,"__esModule",{value:!0}),exports.rgb=rgb,exports.toSVG=toSVG;var ColorMap=function(){function t(t){this.stops=t}return t.prototype.getColor=function(t){t=Math.max(0,Math.min(.99999999,t));var r=Math.floor(t*this.stops.length);return this.stops[r]},t}();exports.ColorMap=ColorMap;

/***/ }),
/* 186 */
/***/ (function(module, exports, __webpack_require__) {

function assertEncoding(e){if(e&&!isBufferEncoding(e))throw new Error("Unknown encoding: "+e)}function passThroughWrite(e){return e.toString(this.encoding)}function utf16DetectIncompleteChar(e){this.charReceived=e.length%2,this.charLength=this.charReceived?2:0}function base64DetectIncompleteChar(e){this.charReceived=e.length%3,this.charLength=this.charReceived?3:0}var Buffer=__webpack_require__(38).Buffer,isBufferEncoding=Buffer.isEncoding||function(e){switch(e&&e.toLowerCase()){case"hex":case"utf8":case"utf-8":case"ascii":case"binary":case"base64":case"ucs2":case"ucs-2":case"utf16le":case"utf-16le":case"raw":return!0;default:return!1}},StringDecoder=exports.StringDecoder=function(e){switch(this.encoding=(e||"utf8").toLowerCase().replace(/[-_]/,""),assertEncoding(e),this.encoding){case"utf8":this.surrogateSize=3;break;case"ucs2":case"utf16le":this.surrogateSize=2,this.detectIncompleteChar=utf16DetectIncompleteChar;break;case"base64":this.surrogateSize=3,this.detectIncompleteChar=base64DetectIncompleteChar;break;default:return void(this.write=passThroughWrite)}this.charBuffer=new Buffer(6),this.charReceived=0,this.charLength=0};StringDecoder.prototype.write=function(e){for(var t="";this.charLength;){var r=e.length>=this.charLength-this.charReceived?this.charLength-this.charReceived:e.length;if(e.copy(this.charBuffer,this.charReceived,0,r),this.charReceived+=r,this.charReceived<this.charLength)return"";e=e.slice(r,e.length),t=this.charBuffer.slice(0,this.charLength).toString(this.encoding);var h=t.charCodeAt(t.length-1);if(!(h>=55296&&h<=56319)){if(this.charReceived=this.charLength=0,0===e.length)return t;break}this.charLength+=this.surrogateSize,t=""}this.detectIncompleteChar(e);var i=e.length;this.charLength&&(e.copy(this.charBuffer,0,e.length-this.charReceived,i),i-=this.charReceived),t+=e.toString(this.encoding,0,i);var i=t.length-1,h=t.charCodeAt(i);if(h>=55296&&h<=56319){var c=this.surrogateSize;return this.charLength+=c,this.charReceived+=c,this.charBuffer.copy(this.charBuffer,c,0,c),e.copy(this.charBuffer,0,0,c),t.substring(0,i)}return t},StringDecoder.prototype.detectIncompleteChar=function(e){for(var t=e.length>=3?3:e.length;t>0;t--){var r=e[e.length-t];if(1==t&&r>>5==6){this.charLength=2;break}if(t<=2&&r>>4==14){this.charLength=3;break}if(t<=3&&r>>3==30){this.charLength=4;break}}this.charReceived=t},StringDecoder.prototype.end=function(e){var t="";if(e&&e.length&&(t=this.write(e)),this.charReceived){var r=this.charReceived,h=this.charBuffer,i=this.encoding;t+=h.slice(0,r).toString(i)}return t};

/***/ }),
/* 187 */
/***/ (function(module, exports) {

module.exports={area:!0,base:!0,br:!0,col:!0,embed:!0,hr:!0,img:!0,input:!0,keygen:!0,link:!0,menuitem:!0,meta:!0,param:!0,source:!0,track:!0,wbr:!0};

/***/ }),
/* 188 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
Object.defineProperty(exports,"__esModule",{value:!0});var Disposable;!function(t){function s(t){return{dispose:t}}t.create=s}(Disposable=exports.Disposable||(exports.Disposable={}));var Event;!function(t){var s={dispose:function(){}};t.None=function(){return s}}(Event=exports.Event||(exports.Event={}));var CallbackList=function(){function t(){}return t.prototype.add=function(t,s,e){var o=this;void 0===s&&(s=null),this._callbacks||(this._callbacks=[],this._contexts=[]),this._callbacks.push(t),this._contexts.push(s),Array.isArray(e)&&e.push({dispose:function(){return o.remove(t,s)}})},t.prototype.remove=function(t,s){if(void 0===s&&(s=null),this._callbacks){for(var e=!1,o=0,i=this._callbacks.length;o<i;o++)if(this._callbacks[o]===t){if(this._contexts[o]===s)return this._callbacks.splice(o,1),void this._contexts.splice(o,1);e=!0}if(e)throw new Error("When adding a listener with a context, you should remove it with the same context")}},t.prototype.invoke=function(){for(var t=[],s=0;s<arguments.length;s++)t[s]=arguments[s];if(!this._callbacks)return[];for(var e=[],o=this._callbacks.slice(0),i=this._contexts.slice(0),n=0,c=o.length;n<c;n++)try{e.push(o[n].apply(i[n],t))}catch(t){console.error(t)}return e},t.prototype.isEmpty=function(){return!this._callbacks||0===this._callbacks.length},t.prototype.dispose=function(){this._callbacks=void 0,this._contexts=void 0},t}(),Emitter=function(){function t(t){this._options=t}return Object.defineProperty(t.prototype,"event",{get:function(){var s=this;return this._event||(this._event=function(e,o,i){s._callbacks||(s._callbacks=new CallbackList),s._options&&s._options.onFirstListenerAdd&&s._callbacks.isEmpty()&&s._options.onFirstListenerAdd(s),s._callbacks.add(e,o);var n;return n={dispose:function(){s._callbacks.remove(e,o),n.dispose=t._noop,s._options&&s._options.onLastListenerRemove&&s._callbacks.isEmpty()&&s._options.onLastListenerRemove(s)}},Array.isArray(i)&&i.push(n),n}),this._event},enumerable:!0,configurable:!0}),t.prototype.fire=function(t){this._callbacks&&this._callbacks.invoke.call(this._callbacks,t)},t.prototype.dispose=function(){this._callbacks&&(this._callbacks.dispose(),this._callbacks=void 0)},t._noop=function(){},t}();exports.Emitter=Emitter;

/***/ }),
/* 189 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
function boolean(r){return!0===r||!1===r}function string(r){return"[object String]"===toString.call(r)}function number(r){return"[object Number]"===toString.call(r)}function error(r){return"[object Error]"===toString.call(r)}function func(r){return"[object Function]"===toString.call(r)}function array(r){return Array.isArray(r)}function stringArray(r){return array(r)&&r.every(function(r){return string(r)})}Object.defineProperty(exports,"__esModule",{value:!0});var toString=Object.prototype.toString;exports.boolean=boolean,exports.string=string,exports.number=number,exports.error=error,exports.func=func,exports.array=array,exports.stringArray=stringArray;

/***/ }),
/* 190 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
/* WEBPACK VAR INJECTION */(function(setImmediate) {function __export(e){for(var t in e)exports.hasOwnProperty(t)||(exports[t]=e[t])}function _createMessageConnection(e,t,r,s){function o(e){return"req-"+e.toString()}function n(e){return null===e?"res-unknown-"+(++W).toString():"res-"+e.toString()}function i(){return"not-"+(++P).toString()}function a(e,t){messages_1.isRequestMessage(t)?e.set(o(t.id),t):messages_1.isResponseMessage(t)?e.set(n(t.id),t):e.set(i(),t)}function c(e){}function d(){return B===ConnectionState.Listening}function f(){return B===ConnectionState.Closed}function p(){return B===ConnectionState.Disposed}function u(){B!==ConnectionState.New&&B!==ConnectionState.Listening||(B=ConnectionState.Closed,H.fire(void 0))}function l(e){G.fire([e,void 0,void 0])}function g(e){G.fire(e)}function m(){k||0===A.size||(k=setImmediate(function(){k=void 0,v()}))}function v(){if(0!==A.size){var e=A.shift();try{messages_1.isRequestMessage(e)?h(e):messages_1.isNotificationMessage(e)?_(e):messages_1.isResponseMessage(e)?y(e):T(e)}finally{m()}}}function h(e){function r(r,s,o){var n={jsonrpc:L,id:e.id};r instanceof messages_1.ResponseError?n.error=r.toJson():n.result=void 0===r?null:r,N(n,s,o),t.write(n)}function s(r,s,o){var n={jsonrpc:L,id:e.id,error:r.toJson()};N(n,s,o),t.write(n)}if(!p()){w(e);var o,n,i=J[e.method];i&&(o=i.type,n=i.handler);var a=Date.now();if(n||D){var c=new cancellation_1.CancellationTokenSource,d=String(e.id);z[d]=c;try{var f=void 0;f=void 0===e.params||void 0!==o&&0===o.numberOfParams?n?n(c.token):D(e.method,c.token):Is.array(e.params)&&(void 0===o||o.numberOfParams>1)?n?n.apply(void 0,e.params.concat([c.token])):D.apply(void 0,[e.method].concat(e.params,[c.token])):n?n(e.params,c.token):D(e.method,e.params,c.token);var u=f;f?u.then?u.then(function(t){delete z[d],r(t,e.method,a)},function(t){delete z[d],t instanceof messages_1.ResponseError?s(t,e.method,a):t&&Is.string(t.message)?s(new messages_1.ResponseError(messages_1.ErrorCodes.InternalError,"Request "+e.method+" failed with message: "+t.message),e.method,a):s(new messages_1.ResponseError(messages_1.ErrorCodes.InternalError,"Request "+e.method+" failed unexpectedly without providing any details."),e.method,a)}):(delete z[d],r(f,e.method,a)):(delete z[d],function(r,s,o){void 0===r&&(r=null);var n={jsonrpc:L,id:e.id,result:r};N(n,s,o),t.write(n)}(f,e.method,a))}catch(t){delete z[d],t instanceof messages_1.ResponseError?r(t,e.method,a):t&&Is.string(t.message)?s(new messages_1.ResponseError(messages_1.ErrorCodes.InternalError,"Request "+e.method+" failed with message: "+t.message),e.method,a):s(new messages_1.ResponseError(messages_1.ErrorCodes.InternalError,"Request "+e.method+" failed unexpectedly without providing any details."),e.method,a)}}else s(new messages_1.ResponseError(messages_1.ErrorCodes.MethodNotFound,"Unhandled method "+e.method),e.method,a)}}function y(e){if(!p())if(null===e.id)e.error?r.error("Received response message without id: Error is: \n"+JSON.stringify(e.error,void 0,4)):r.error("Received response message without id. No further error information provided.");else{var t=String(e.id),s=$[t];if(S(e,s),s){delete $[t];try{if(e.error){var o=e.error;s.reject(new messages_1.ResponseError(o.code,o.message,o.data))}else{if(void 0===e.result)throw new Error("Should never happen.");s.resolve(e.result)}}catch(o){o.message?r.error("Response handler '"+s.method+"' failed with message: "+o.message):r.error("Response handler '"+s.method+"' failed unexpectedly.")}}}}function _(e){if(!p()){var t,s=void 0;if(e.method===CancelNotification.type.method)t=function(e){var t=e.id,r=z[String(t)];r&&r.cancel()};else{var o=U[e.method];o&&(t=o.handler,s=o.type)}if(t||V)try{x(e),void 0===e.params||void 0!==s&&0===s.numberOfParams?t?t():V(e.method):Is.array(e.params)&&(void 0===s||s.numberOfParams>1)?t?t.apply(void 0,e.params):V.apply(void 0,[e.method].concat(e.params)):t?t(e.params):V(e.method,e.params)}catch(t){t.message?r.error("Notification handler '"+e.method+"' failed with message: "+t.message):r.error("Notification handler '"+e.method+"' failed unexpectedly.")}else K.fire(e)}}function T(e){if(!e)return void r.error("Received empty message.");r.error("Received message which is neither a response nor a notification message:\n"+JSON.stringify(e,null,4));var t=e;if(Is.string(t.id)||Is.number(t.id)){var s=String(t.id),o=$[s];o&&o.reject(new Error("The received response has neither a result nor an error property."))}}function R(e){if(F!==Trace.Off&&I){var t=void 0;F===Trace.Verbose&&e.params&&(t="Params: "+JSON.stringify(e.params,null,4)+"\n\n"),I.log("Sending request '"+e.method+" - ("+e.id+")'.",t)}}function C(e){if(F!==Trace.Off&&I){var t=void 0;F===Trace.Verbose&&(t=e.params?"Params: "+JSON.stringify(e.params,null,4)+"\n\n":"No parameters provided.\n\n"),I.log("Sending notification '"+e.method+"'.",t)}}function N(e,t,r){if(F!==Trace.Off&&I){F===Trace.Verbose&&(e.error&&e.error.data?"Error data: "+JSON.stringify(e.error.data,null,4)+"\n\n":e.result?"Result: "+JSON.stringify(e.result,null,4)+"\n\n":void 0===e.error&&"No result returned.\n\n"),I.log("Sending response '"+t+" - ("+e.id+")'. Processing request took "+(Date.now()-r)+"ms")}}function w(e){if(F!==Trace.Off&&I){var t=void 0;F===Trace.Verbose&&e.params&&(t="Params: "+JSON.stringify(e.params,null,4)+"\n\n"),I.log("Received request '"+e.method+" - ("+e.id+")'.",t)}}function x(e){if(F!==Trace.Off&&I&&e.method!==LogTraceNotification.type.method){var t=void 0;F===Trace.Verbose&&(t=e.params?"Params: "+JSON.stringify(e.params,null,4)+"\n\n":"No parameters provided.\n\n"),I.log("Received notification '"+e.method+"'.",t)}}function S(e,t){if(F!==Trace.Off&&I){var r=void 0;if(F===Trace.Verbose&&(e.error&&e.error.data?r="Error data: "+JSON.stringify(e.error.data,null,4)+"\n\n":e.result?r="Result: "+JSON.stringify(e.result,null,4)+"\n\n":void 0===e.error&&(r="No result returned.\n\n")),t){var s=e.error?" Request failed: "+e.error.message+" ("+e.error.code+").":"";I.log("Received response '"+t.method+" - ("+e.id+")' in "+(Date.now()-t.timerStart)+"ms."+s,r)}else I.log("Received response "+e.id+" without active response promise.",r)}}function E(){if(f())throw new ConnectionError(ConnectionErrors.Closed,"Connection is closed.");if(p())throw new ConnectionError(ConnectionErrors.Disposed,"Connection is disposed.")}function M(){if(d())throw new ConnectionError(ConnectionErrors.AlreadyListening,"Connection is already listening")}function q(){if(!d())throw new Error("Call listen() first.")}function O(e){return void 0===e?null:e}function b(e,t){var r,s=e.numberOfParams;switch(s){case 0:r=null;break;case 1:r=O(t[0]);break;default:r=[];for(var o=0;o<t.length&&o<s;o++)r.push(O(t[o]));if(t.length<s)for(var o=t.length;o<s;o++)r.push(null)}return r}var k,I,j=0,P=0,W=0,L="2.0",D=void 0,J=Object.create(null),V=void 0,U=Object.create(null),A=new linkedMap_1.LinkedMap,$=Object.create(null),z=Object.create(null),F=Trace.Off,B=ConnectionState.New,G=new events_1.Emitter,H=new events_1.Emitter,K=new events_1.Emitter,Q=new events_1.Emitter;e.onClose(u),e.onError(l),t.onClose(u),t.onError(g);var X=function(e){try{if(messages_1.isNotificationMessage(e)&&e.method===CancelNotification.type.method){var r=o(e.params.id),n=A.get(r);if(messages_1.isRequestMessage(n)){var i=s&&s.cancelUndispatched?s.cancelUndispatched(n,c):void 0;if(i&&(void 0!==i.error||void 0!==i.result))return A.delete(r),i.id=n.id,N(i,e.method,Date.now()),void t.write(i)}}a(A,e)}finally{m()}},Y={sendNotification:function(e){for(var r=[],s=1;s<arguments.length;s++)r[s-1]=arguments[s];E();var o;if(Is.string(e))switch(e,r.length){case 0:o=null;break;case 1:o=r[0];break;default:o=r}else e.method,o=b(e,r);var n={jsonrpc:L,method:Is.string(e)?e:e.method,params:o};C(n),t.write(n)},onNotification:function(e,t){E(),Is.func(e)?V=e:t&&(Is.string(e)?U[e]={type:void 0,handler:t}:U[e.method]={type:e,handler:t})},sendRequest:function(e){for(var r=[],s=1;s<arguments.length;s++)r[s-1]=arguments[s];E(),q();var o,n,i=void 0;if(Is.string(e))switch(o=e,r.length){case 0:n=null;break;case 1:cancellation_1.CancellationToken.is(r[0])?(n=null,i=r[0]):n=O(r[0]);break;default:var a=r.length-1;cancellation_1.CancellationToken.is(r[a])?(i=r[a],n=2===r.length?O(r[0]):r.slice(0,a).map(function(e){return O(e)})):n=r.map(function(e){return O(e)})}else{o=e.method,n=b(e,r);var c=e.numberOfParams;i=cancellation_1.CancellationToken.is(r[c])?r[c]:void 0}var d=j++,f=new Promise(function(e,r){var s={jsonrpc:L,id:d,method:o,params:n},i={method:o,timerStart:Date.now(),resolve:e,reject:r};R(s);try{t.write(s)}catch(e){i.reject(new messages_1.ResponseError(messages_1.ErrorCodes.MessageWriteError,e.message?e.message:"Unknown reason")),i=null}i&&($[String(d)]=i)});return i&&i.onCancellationRequested(function(){Y.sendNotification(CancelNotification.type,{id:d})}),f},onRequest:function(e,t){E(),Is.func(e)?D=e:t&&(Is.string(e)?J[e]={type:void 0,handler:t}:J[e.method]={type:e,handler:t})},trace:function(e,t,r){void 0===r&&(r=!1),F=e,I=F===Trace.Off?void 0:t,!r||f()||p()||Y.sendNotification(SetTraceNotification.type,{value:Trace.toString(e)})},onError:G.event,onClose:H.event,onUnhandledNotification:K.event,onDispose:Q.event,dispose:function(){if(!p()){B=ConnectionState.Disposed,Q.fire(void 0);var r=new Error("Connection got disposed.");Object.keys($).forEach(function(e){$[e].reject(r)}),$=Object.create(null),z=Object.create(null),A=new linkedMap_1.LinkedMap,Is.func(t.dispose)&&t.dispose(),Is.func(e.dispose)&&e.dispose()}},listen:function(){E(),M(),B=ConnectionState.Listening,e.listen(X)},inspect:function(){console.log("inspect")}};return Y.onNotification(LogTraceNotification.type,function(e){F!==Trace.Off&&I&&I.log(e.message,F===Trace.Verbose?e.verbose:void 0)}),Y}function isMessageReader(e){return void 0!==e.listen&&void 0===e.read}function isMessageWriter(e){return void 0!==e.write&&void 0===e.end}function createMessageConnection(e,t,r,s){return _createMessageConnection(isMessageReader(e)?e:new messageReader_1.StreamMessageReader(e),isMessageWriter(t)?t:new messageWriter_1.StreamMessageWriter(t),r,s)}var __extends=this&&this.__extends||function(){var e=Object.setPrototypeOf||{__proto__:[]}instanceof Array&&function(e,t){e.__proto__=t}||function(e,t){for(var r in t)t.hasOwnProperty(r)&&(e[r]=t[r])};return function(t,r){function s(){this.constructor=t}e(t,r),t.prototype=null===r?Object.create(r):(s.prototype=r.prototype,new s)}}();Object.defineProperty(exports,"__esModule",{value:!0});var Is=__webpack_require__(189),messages_1=__webpack_require__(221);exports.RequestType=messages_1.RequestType,exports.RequestType0=messages_1.RequestType0,exports.RequestType1=messages_1.RequestType1,exports.RequestType2=messages_1.RequestType2,exports.RequestType3=messages_1.RequestType3,exports.RequestType4=messages_1.RequestType4,exports.RequestType5=messages_1.RequestType5,exports.RequestType6=messages_1.RequestType6,exports.RequestType7=messages_1.RequestType7,exports.RequestType8=messages_1.RequestType8,exports.RequestType9=messages_1.RequestType9,exports.ResponseError=messages_1.ResponseError,exports.ErrorCodes=messages_1.ErrorCodes,exports.NotificationType=messages_1.NotificationType,exports.NotificationType0=messages_1.NotificationType0,exports.NotificationType1=messages_1.NotificationType1,exports.NotificationType2=messages_1.NotificationType2,exports.NotificationType3=messages_1.NotificationType3,exports.NotificationType4=messages_1.NotificationType4,exports.NotificationType5=messages_1.NotificationType5,exports.NotificationType6=messages_1.NotificationType6,exports.NotificationType7=messages_1.NotificationType7,exports.NotificationType8=messages_1.NotificationType8,exports.NotificationType9=messages_1.NotificationType9;var messageReader_1=__webpack_require__(207);exports.MessageReader=messageReader_1.MessageReader,exports.StreamMessageReader=messageReader_1.StreamMessageReader,exports.IPCMessageReader=messageReader_1.IPCMessageReader,exports.SocketMessageReader=messageReader_1.SocketMessageReader;var messageWriter_1=__webpack_require__(208);exports.MessageWriter=messageWriter_1.MessageWriter,exports.StreamMessageWriter=messageWriter_1.StreamMessageWriter,exports.IPCMessageWriter=messageWriter_1.IPCMessageWriter,exports.SocketMessageWriter=messageWriter_1.SocketMessageWriter;var events_1=__webpack_require__(188);exports.Disposable=events_1.Disposable,exports.Event=events_1.Event,exports.Emitter=events_1.Emitter;var cancellation_1=__webpack_require__(260);exports.CancellationTokenSource=cancellation_1.CancellationTokenSource,exports.CancellationToken=cancellation_1.CancellationToken;var linkedMap_1=__webpack_require__(261);__export(__webpack_require__(262));var CancelNotification;!function(e){e.type=new messages_1.NotificationType("$/cancelRequest")}(CancelNotification||(CancelNotification={}));var Trace;!function(e){e[e.Off=0]="Off",e[e.Messages=1]="Messages",e[e.Verbose=2]="Verbose"}(Trace=exports.Trace||(exports.Trace={})),function(e){function t(t){switch(t=t.toLowerCase()){case"off":return e.Off;case"messages":return e.Messages;case"verbose":return e.Verbose;default:return e.Off}}function r(t){switch(t){case e.Off:return"off";case e.Messages:return"messages";case e.Verbose:return"verbose";default:return"off"}}e.fromString=t,e.toString=r}(Trace=exports.Trace||(exports.Trace={}));var SetTraceNotification;!function(e){e.type=new messages_1.NotificationType("$/setTraceNotification")}(SetTraceNotification=exports.SetTraceNotification||(exports.SetTraceNotification={}));var LogTraceNotification;!function(e){e.type=new messages_1.NotificationType("$/logTraceNotification")}(LogTraceNotification=exports.LogTraceNotification||(exports.LogTraceNotification={}));var ConnectionErrors;!function(e){e[e.Closed=1]="Closed",e[e.Disposed=2]="Disposed",e[e.AlreadyListening=3]="AlreadyListening"}(ConnectionErrors=exports.ConnectionErrors||(exports.ConnectionErrors={}));var ConnectionError=function(e){function t(r,s){var o=e.call(this,s)||this;return o.code=r,Object.setPrototypeOf(o,t.prototype),o}return __extends(t,e),t}(Error);exports.ConnectionError=ConnectionError;var ConnectionStrategy;!function(e){function t(e){var t=e;return t&&Is.func(t.cancelUndispatched)}e.is=t}(ConnectionStrategy=exports.ConnectionStrategy||(exports.ConnectionStrategy={}));var ConnectionState;!function(e){e[e.New=1]="New",e[e.Listening=2]="Listening",e[e.Closed=3]="Closed",e[e.Disposed=4]="Disposed"}(ConnectionState||(ConnectionState={})),exports.createMessageConnection=createMessageConnection;
/* WEBPACK VAR INJECTION */}.call(exports, __webpack_require__(193).setImmediate))

/***/ }),
/* 191 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
/* --------------------------------------------------------------------------------------------
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for license information.
 * ------------------------------------------------------------------------------------------ */

Object.defineProperty(exports, "__esModule", { value: true });
const Is = __webpack_require__(194);
const vscode_jsonrpc_1 = __webpack_require__(190);
var DocumentFilter;
(function (DocumentFilter) {
    function is(value) {
        let candidate = value;
        return Is.string(candidate.language) || Is.string(candidate.scheme) || Is.string(candidate.pattern);
    }
    DocumentFilter.is = is;
})(DocumentFilter = exports.DocumentFilter || (exports.DocumentFilter = {}));
/**
 * The `client/registerCapability` request is sent from the server to the client to register a new feature
 * handler on the client side.
 */
var RegistrationRequest;
(function (RegistrationRequest) {
    RegistrationRequest.type = new vscode_jsonrpc_1.RequestType('client/registerCapability');
})(RegistrationRequest = exports.RegistrationRequest || (exports.RegistrationRequest = {}));
/**
 * The `client/unregisterCapability` request is sent from the server to the client to unregister a previously registered feature
 * handler on the client side.
 */
var UnregistrationRequest;
(function (UnregistrationRequest) {
    UnregistrationRequest.type = new vscode_jsonrpc_1.RequestType('client/unregisterCapability');
})(UnregistrationRequest = exports.UnregistrationRequest || (exports.UnregistrationRequest = {}));
;
/**
 * Defines how the host (editor) should sync
 * document changes to the language server.
 */
var TextDocumentSyncKind;
(function (TextDocumentSyncKind) {
    /**
     * Documents should not be synced at all.
     */
    TextDocumentSyncKind.None = 0;
    /**
     * Documents are synced by always sending the full content
     * of the document.
     */
    TextDocumentSyncKind.Full = 1;
    /**
     * Documents are synced by sending the full content on open.
     * After that only incremental updates to the document are
     * send.
     */
    TextDocumentSyncKind.Incremental = 2;
})(TextDocumentSyncKind = exports.TextDocumentSyncKind || (exports.TextDocumentSyncKind = {}));
/**
 * The initialize request is sent from the client to the server.
 * It is sent once as the request after starting up the server.
 * The requests parameter is of type [InitializeParams](#InitializeParams)
 * the response if of type [InitializeResult](#InitializeResult) of a Thenable that
 * resolves to such.
 */
var InitializeRequest;
(function (InitializeRequest) {
    InitializeRequest.type = new vscode_jsonrpc_1.RequestType('initialize');
})(InitializeRequest = exports.InitializeRequest || (exports.InitializeRequest = {}));
/**
 * Known error codes for an `InitializeError`;
 */
var InitializeError;
(function (InitializeError) {
    /**
     * If the protocol version provided by the client can't be handled by the server.
     * @deprecated This initialize error got replaced by client capabilities. There is
     * no version handshake in version 3.0x
     */
    InitializeError.unknownProtocolVersion = 1;
})(InitializeError = exports.InitializeError || (exports.InitializeError = {}));
/**
 * The intialized notification is send from the client to the
 * server after the client is fully initialized and the server
 * is allowed to send requests from the server to the client.
 */
var InitializedNotification;
(function (InitializedNotification) {
    InitializedNotification.type = new vscode_jsonrpc_1.NotificationType('initialized');
})(InitializedNotification = exports.InitializedNotification || (exports.InitializedNotification = {}));
//---- Shutdown Method ----
/**
 * A shutdown request is sent from the client to the server.
 * It is sent once when the client descides to shutdown the
 * server. The only notification that is sent after a shudown request
 * is the exit event.
 */
var ShutdownRequest;
(function (ShutdownRequest) {
    ShutdownRequest.type = new vscode_jsonrpc_1.RequestType0('shutdown');
})(ShutdownRequest = exports.ShutdownRequest || (exports.ShutdownRequest = {}));
//---- Exit Notification ----
/**
 * The exit event is sent from the client to the server to
 * ask the server to exit its process.
 */
var ExitNotification;
(function (ExitNotification) {
    ExitNotification.type = new vscode_jsonrpc_1.NotificationType0('exit');
})(ExitNotification = exports.ExitNotification || (exports.ExitNotification = {}));
//---- Configuration notification ----
/**
 * The configuration change notification is sent from the client to the server
 * when the client's configuration has changed. The notification contains
 * the changed configuration as defined by the language client.
 */
var DidChangeConfigurationNotification;
(function (DidChangeConfigurationNotification) {
    DidChangeConfigurationNotification.type = new vscode_jsonrpc_1.NotificationType('workspace/didChangeConfiguration');
})(DidChangeConfigurationNotification = exports.DidChangeConfigurationNotification || (exports.DidChangeConfigurationNotification = {}));
//---- Message show and log notifications ----
/**
 * The message type
 */
var MessageType;
(function (MessageType) {
    /**
     * An error message.
     */
    MessageType.Error = 1;
    /**
     * A warning message.
     */
    MessageType.Warning = 2;
    /**
     * An information message.
     */
    MessageType.Info = 3;
    /**
     * A log message.
     */
    MessageType.Log = 4;
})(MessageType = exports.MessageType || (exports.MessageType = {}));
/**
 * The show message notification is sent from a server to a client to ask
 * the client to display a particular message in the user interface.
 */
var ShowMessageNotification;
(function (ShowMessageNotification) {
    ShowMessageNotification.type = new vscode_jsonrpc_1.NotificationType('window/showMessage');
})(ShowMessageNotification = exports.ShowMessageNotification || (exports.ShowMessageNotification = {}));
/**
 * The show message request is sent from the server to the clinet to show a message
 * and a set of options actions to the user.
 */
var ShowMessageRequest;
(function (ShowMessageRequest) {
    ShowMessageRequest.type = new vscode_jsonrpc_1.RequestType('window/showMessageRequest');
})(ShowMessageRequest = exports.ShowMessageRequest || (exports.ShowMessageRequest = {}));
/**
 * The log message notification is sent from the server to the client to ask
 * the client to log a particular message.
 */
var LogMessageNotification;
(function (LogMessageNotification) {
    LogMessageNotification.type = new vscode_jsonrpc_1.NotificationType('window/logMessage');
})(LogMessageNotification = exports.LogMessageNotification || (exports.LogMessageNotification = {}));
//---- Telemetry notification
/**
 * The telemetry event notification is sent from the server to the client to ask
 * the client to log telemetry data.
 */
var TelemetryEventNotification;
(function (TelemetryEventNotification) {
    TelemetryEventNotification.type = new vscode_jsonrpc_1.NotificationType('telemetry/event');
})(TelemetryEventNotification = exports.TelemetryEventNotification || (exports.TelemetryEventNotification = {}));
/**
 * The document open notification is sent from the client to the server to signal
 * newly opened text documents. The document's truth is now managed by the client
 * and the server must not try to read the document's truth using the document's
 * uri.
 */
var DidOpenTextDocumentNotification;
(function (DidOpenTextDocumentNotification) {
    DidOpenTextDocumentNotification.type = new vscode_jsonrpc_1.NotificationType('textDocument/didOpen');
})(DidOpenTextDocumentNotification = exports.DidOpenTextDocumentNotification || (exports.DidOpenTextDocumentNotification = {}));
/**
 * The document change notification is sent from the client to the server to signal
 * changes to a text document.
 */
var DidChangeTextDocumentNotification;
(function (DidChangeTextDocumentNotification) {
    DidChangeTextDocumentNotification.type = new vscode_jsonrpc_1.NotificationType('textDocument/didChange');
})(DidChangeTextDocumentNotification = exports.DidChangeTextDocumentNotification || (exports.DidChangeTextDocumentNotification = {}));
/**
 * The document close notification is sent from the client to the server when
 * the document got closed in the client. The document's truth now exists
 * where the document's uri points to (e.g. if the document's uri is a file uri
 * the truth now exists on disk).
 */
var DidCloseTextDocumentNotification;
(function (DidCloseTextDocumentNotification) {
    DidCloseTextDocumentNotification.type = new vscode_jsonrpc_1.NotificationType('textDocument/didClose');
})(DidCloseTextDocumentNotification = exports.DidCloseTextDocumentNotification || (exports.DidCloseTextDocumentNotification = {}));
/**
 * The document save notification is sent from the client to the server when
 * the document got saved in the client.
 */
var DidSaveTextDocumentNotification;
(function (DidSaveTextDocumentNotification) {
    DidSaveTextDocumentNotification.type = new vscode_jsonrpc_1.NotificationType('textDocument/didSave');
})(DidSaveTextDocumentNotification = exports.DidSaveTextDocumentNotification || (exports.DidSaveTextDocumentNotification = {}));
/**
 * A document will save notification is sent from the client to the server before
 * the document is actually saved.
 */
var WillSaveTextDocumentNotification;
(function (WillSaveTextDocumentNotification) {
    WillSaveTextDocumentNotification.type = new vscode_jsonrpc_1.NotificationType('textDocument/willSave');
})(WillSaveTextDocumentNotification = exports.WillSaveTextDocumentNotification || (exports.WillSaveTextDocumentNotification = {}));
/**
 * A document will save request is sent from the client to the server before
 * the document is actually saved. The request can return an array of TextEdits
 * which will be applied to the text document before it is saved. Please note that
 * clients might drop results if computing the text edits took too long or if a
 * server constantly fails on this request. This is done to keep the save fast and
 * reliable.
 */
var WillSaveTextDocumentWaitUntilRequest;
(function (WillSaveTextDocumentWaitUntilRequest) {
    WillSaveTextDocumentWaitUntilRequest.type = new vscode_jsonrpc_1.RequestType('textDocument/willSaveWaitUntil');
})(WillSaveTextDocumentWaitUntilRequest = exports.WillSaveTextDocumentWaitUntilRequest || (exports.WillSaveTextDocumentWaitUntilRequest = {}));
//---- File eventing ----
/**
 * The watched files notification is sent from the client to the server when
 * the client detects changes to file watched by the lanaguage client.
 */
var DidChangeWatchedFilesNotification;
(function (DidChangeWatchedFilesNotification) {
    DidChangeWatchedFilesNotification.type = new vscode_jsonrpc_1.NotificationType('workspace/didChangeWatchedFiles');
})(DidChangeWatchedFilesNotification = exports.DidChangeWatchedFilesNotification || (exports.DidChangeWatchedFilesNotification = {}));
/**
 * The file event type
 */
var FileChangeType;
(function (FileChangeType) {
    /**
     * The file got created.
     */
    FileChangeType.Created = 1;
    /**
     * The file got changed.
     */
    FileChangeType.Changed = 2;
    /**
     * The file got deleted.
     */
    FileChangeType.Deleted = 3;
})(FileChangeType = exports.FileChangeType || (exports.FileChangeType = {}));
//---- Diagnostic notification ----
/**
 * Diagnostics notification are sent from the server to the client to signal
 * results of validation runs.
 */
var PublishDiagnosticsNotification;
(function (PublishDiagnosticsNotification) {
    PublishDiagnosticsNotification.type = new vscode_jsonrpc_1.NotificationType('textDocument/publishDiagnostics');
})(PublishDiagnosticsNotification = exports.PublishDiagnosticsNotification || (exports.PublishDiagnosticsNotification = {}));
/**
 * Request to request completion at a given text document position. The request's
 * parameter is of type [TextDocumentPosition](#TextDocumentPosition) the response
 * is of type [CompletionItem[]](#CompletionItem) or [CompletionList](#CompletionList)
 * or a Thenable that resolves to such.
 */
var CompletionRequest;
(function (CompletionRequest) {
    CompletionRequest.type = new vscode_jsonrpc_1.RequestType('textDocument/completion');
})(CompletionRequest = exports.CompletionRequest || (exports.CompletionRequest = {}));
/**
 * Request to resolve additional information for a given completion item.The request's
 * parameter is of type [CompletionItem](#CompletionItem) the response
 * is of type [CompletionItem](#CompletionItem) or a Thenable that resolves to such.
 */
var CompletionResolveRequest;
(function (CompletionResolveRequest) {
    CompletionResolveRequest.type = new vscode_jsonrpc_1.RequestType('completionItem/resolve');
})(CompletionResolveRequest = exports.CompletionResolveRequest || (exports.CompletionResolveRequest = {}));
//---- Hover Support -------------------------------
/**
 * Request to request hover information at a given text document position. The request's
 * parameter is of type [TextDocumentPosition](#TextDocumentPosition) the response is of
 * type [Hover](#Hover) or a Thenable that resolves to such.
 */
var HoverRequest;
(function (HoverRequest) {
    HoverRequest.type = new vscode_jsonrpc_1.RequestType('textDocument/hover');
})(HoverRequest = exports.HoverRequest || (exports.HoverRequest = {}));
var SignatureHelpRequest;
(function (SignatureHelpRequest) {
    SignatureHelpRequest.type = new vscode_jsonrpc_1.RequestType('textDocument/signatureHelp');
})(SignatureHelpRequest = exports.SignatureHelpRequest || (exports.SignatureHelpRequest = {}));
//---- Goto Definition -------------------------------------
/**
 * A request to resolve the defintion location of a symbol at a given text
 * document position. The request's parameter is of type [TextDocumentPosition]
 * (#TextDocumentPosition) the response is of type [Definition](#Definition) or a
 * Thenable that resolves to such.
 */
var DefinitionRequest;
(function (DefinitionRequest) {
    DefinitionRequest.type = new vscode_jsonrpc_1.RequestType('textDocument/definition');
})(DefinitionRequest = exports.DefinitionRequest || (exports.DefinitionRequest = {}));
/**
 * A request to resolve project-wide references for the symbol denoted
 * by the given text document position. The request's parameter is of
 * type [ReferenceParams](#ReferenceParams) the response is of type
 * [Location[]](#Location) or a Thenable that resolves to such.
 */
var ReferencesRequest;
(function (ReferencesRequest) {
    ReferencesRequest.type = new vscode_jsonrpc_1.RequestType('textDocument/references');
})(ReferencesRequest = exports.ReferencesRequest || (exports.ReferencesRequest = {}));
//---- Document Highlight ----------------------------------
/**
 * Request to resolve a [DocumentHighlight](#DocumentHighlight) for a given
 * text document position. The request's parameter is of type [TextDocumentPosition]
 * (#TextDocumentPosition) the request reponse is of type [DocumentHighlight[]]
 * (#DocumentHighlight) or a Thenable that resolves to such.
 */
var DocumentHighlightRequest;
(function (DocumentHighlightRequest) {
    DocumentHighlightRequest.type = new vscode_jsonrpc_1.RequestType('textDocument/documentHighlight');
})(DocumentHighlightRequest = exports.DocumentHighlightRequest || (exports.DocumentHighlightRequest = {}));
//---- Document Symbol Provider ---------------------------
/**
 * A request to list all symbols found in a given text document. The request's
 * parameter is of type [TextDocumentIdentifier](#TextDocumentIdentifier) the
 * response is of type [SymbolInformation[]](#SymbolInformation) or a Thenable
 * that resolves to such.
 */
var DocumentSymbolRequest;
(function (DocumentSymbolRequest) {
    DocumentSymbolRequest.type = new vscode_jsonrpc_1.RequestType('textDocument/documentSymbol');
})(DocumentSymbolRequest = exports.DocumentSymbolRequest || (exports.DocumentSymbolRequest = {}));
//---- Workspace Symbol Provider ---------------------------
/**
 * A request to list project-wide symbols matching the query string given
 * by the [WorkspaceSymbolParams](#WorkspaceSymbolParams). The response is
 * of type [SymbolInformation[]](#SymbolInformation) or a Thenable that
 * resolves to such.
 */
var WorkspaceSymbolRequest;
(function (WorkspaceSymbolRequest) {
    WorkspaceSymbolRequest.type = new vscode_jsonrpc_1.RequestType('workspace/symbol');
})(WorkspaceSymbolRequest = exports.WorkspaceSymbolRequest || (exports.WorkspaceSymbolRequest = {}));
/**
 * A request to provide commands for the given text document and range.
 */
var CodeActionRequest;
(function (CodeActionRequest) {
    CodeActionRequest.type = new vscode_jsonrpc_1.RequestType('textDocument/codeAction');
})(CodeActionRequest = exports.CodeActionRequest || (exports.CodeActionRequest = {}));
/**
 * A request to provide code lens for the given text document.
 */
var CodeLensRequest;
(function (CodeLensRequest) {
    CodeLensRequest.type = new vscode_jsonrpc_1.RequestType('textDocument/codeLens');
})(CodeLensRequest = exports.CodeLensRequest || (exports.CodeLensRequest = {}));
/**
 * A request to resolve a command for a given code lens.
 */
var CodeLensResolveRequest;
(function (CodeLensResolveRequest) {
    CodeLensResolveRequest.type = new vscode_jsonrpc_1.RequestType('codeLens/resolve');
})(CodeLensResolveRequest = exports.CodeLensResolveRequest || (exports.CodeLensResolveRequest = {}));
/**
 * A request to to format a whole document.
 */
var DocumentFormattingRequest;
(function (DocumentFormattingRequest) {
    DocumentFormattingRequest.type = new vscode_jsonrpc_1.RequestType('textDocument/formatting');
})(DocumentFormattingRequest = exports.DocumentFormattingRequest || (exports.DocumentFormattingRequest = {}));
/**
 * A request to to format a range in a document.
 */
var DocumentRangeFormattingRequest;
(function (DocumentRangeFormattingRequest) {
    DocumentRangeFormattingRequest.type = new vscode_jsonrpc_1.RequestType('textDocument/rangeFormatting');
})(DocumentRangeFormattingRequest = exports.DocumentRangeFormattingRequest || (exports.DocumentRangeFormattingRequest = {}));
/**
 * A request to format a document on type.
 */
var DocumentOnTypeFormattingRequest;
(function (DocumentOnTypeFormattingRequest) {
    DocumentOnTypeFormattingRequest.type = new vscode_jsonrpc_1.RequestType('textDocument/onTypeFormatting');
})(DocumentOnTypeFormattingRequest = exports.DocumentOnTypeFormattingRequest || (exports.DocumentOnTypeFormattingRequest = {}));
/**
 * A request to rename a symbol.
 */
var RenameRequest;
(function (RenameRequest) {
    RenameRequest.type = new vscode_jsonrpc_1.RequestType('textDocument/rename');
})(RenameRequest = exports.RenameRequest || (exports.RenameRequest = {}));
/**
 * A request to provide document links
 */
var DocumentLinkRequest;
(function (DocumentLinkRequest) {
    DocumentLinkRequest.type = new vscode_jsonrpc_1.RequestType('textDocument/documentLink');
})(DocumentLinkRequest = exports.DocumentLinkRequest || (exports.DocumentLinkRequest = {}));
/**
 * Request to resolve additional information for a given document link. The request's
 * parameter is of type [DocumentLink](#DocumentLink) the response
 * is of type [DocumentLink](#DocumentLink) or a Thenable that resolves to such.
 */
var DocumentLinkResolveRequest;
(function (DocumentLinkResolveRequest) {
    DocumentLinkResolveRequest.type = new vscode_jsonrpc_1.RequestType('documentLink/resolve');
})(DocumentLinkResolveRequest = exports.DocumentLinkResolveRequest || (exports.DocumentLinkResolveRequest = {}));
/**
 * A request send from the client to the server to execute a command. The request might return
 * a workspace edit which the client will apply to the workspace.
 */
var ExecuteCommandRequest;
(function (ExecuteCommandRequest) {
    ExecuteCommandRequest.type = new vscode_jsonrpc_1.RequestType('workspace/executeCommand');
})(ExecuteCommandRequest = exports.ExecuteCommandRequest || (exports.ExecuteCommandRequest = {}));
/**
 * A request sent from the server to the client to modified certain resources.
 */
var ApplyWorkspaceEditRequest;
(function (ApplyWorkspaceEditRequest) {
    ApplyWorkspaceEditRequest.type = new vscode_jsonrpc_1.RequestType('workspace/applyEdit');
})(ApplyWorkspaceEditRequest = exports.ApplyWorkspaceEditRequest || (exports.ApplyWorkspaceEditRequest = {}));


/***/ }),
/* 192 */
/***/ (function(module, exports) {

module.exports = function() {
	throw new Error("define cannot be used indirect");
};


/***/ }),
/* 193 */
/***/ (function(module, exports, __webpack_require__) {

function Timeout(e,t){this._id=e,this._clearFn=t}var apply=Function.prototype.apply;exports.setTimeout=function(){return new Timeout(apply.call(setTimeout,window,arguments),clearTimeout)},exports.setInterval=function(){return new Timeout(apply.call(setInterval,window,arguments),clearInterval)},exports.clearTimeout=exports.clearInterval=function(e){e&&e.close()},Timeout.prototype.unref=Timeout.prototype.ref=function(){},Timeout.prototype.close=function(){this._clearFn.call(window,this._id)},exports.enroll=function(e,t){clearTimeout(e._idleTimeoutId),e._idleTimeout=t},exports.unenroll=function(e){clearTimeout(e._idleTimeoutId),e._idleTimeout=-1},exports._unrefActive=exports.active=function(e){clearTimeout(e._idleTimeoutId);var t=e._idleTimeout;t>=0&&(e._idleTimeoutId=setTimeout(function(){e._onTimeout&&e._onTimeout()},t))},__webpack_require__(205),exports.setImmediate=setImmediate,exports.clearImmediate=clearImmediate;

/***/ }),
/* 194 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
/* --------------------------------------------------------------------------------------------
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for license information.
 * ------------------------------------------------------------------------------------------ */

Object.defineProperty(exports, "__esModule", { value: true });
const toString = Object.prototype.toString;
function boolean(value) {
    return value === true || value === false;
}
exports.boolean = boolean;
function string(value) {
    return toString.call(value) === '[object String]';
}
exports.string = string;
function number(value) {
    return toString.call(value) === '[object Number]';
}
exports.number = number;
function error(value) {
    return toString.call(value) === '[object Error]';
}
exports.error = error;
function func(value) {
    return toString.call(value) === '[object Function]';
}
exports.func = func;
function array(value) {
    return Array.isArray(value);
}
exports.array = array;
function stringArray(value) {
    return array(value) && value.every(elem => string(elem));
}
exports.stringArray = stringArray;
function typedArray(value, check) {
    return Array.isArray(value) && value.every(check);
}
exports.typedArray = typedArray;
function thenable(value) {
    return value && func(value.then);
}
exports.thenable = thenable;


/***/ }),
/* 195 */,
/* 196 */,
/* 197 */,
/* 198 */,
/* 199 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
function placeHoldersCount(o){var r=o.length;if(r%4>0)throw new Error("Invalid string. Length must be a multiple of 4");return"="===o[r-2]?2:"="===o[r-1]?1:0}function byteLength(o){return 3*o.length/4-placeHoldersCount(o)}function toByteArray(o){var r,e,t,u,n,p=o.length;u=placeHoldersCount(o),n=new Arr(3*p/4-u),e=u>0?p-4:p;var a=0;for(r=0;r<e;r+=4)t=revLookup[o.charCodeAt(r)]<<18|revLookup[o.charCodeAt(r+1)]<<12|revLookup[o.charCodeAt(r+2)]<<6|revLookup[o.charCodeAt(r+3)],n[a++]=t>>16&255,n[a++]=t>>8&255,n[a++]=255&t;return 2===u?(t=revLookup[o.charCodeAt(r)]<<2|revLookup[o.charCodeAt(r+1)]>>4,n[a++]=255&t):1===u&&(t=revLookup[o.charCodeAt(r)]<<10|revLookup[o.charCodeAt(r+1)]<<4|revLookup[o.charCodeAt(r+2)]>>2,n[a++]=t>>8&255,n[a++]=255&t),n}function tripletToBase64(o){return lookup[o>>18&63]+lookup[o>>12&63]+lookup[o>>6&63]+lookup[63&o]}function encodeChunk(o,r,e){for(var t,u=[],n=r;n<e;n+=3)t=(o[n]<<16)+(o[n+1]<<8)+o[n+2],u.push(tripletToBase64(t));return u.join("")}function fromByteArray(o){for(var r,e=o.length,t=e%3,u="",n=[],p=0,a=e-t;p<a;p+=16383)n.push(encodeChunk(o,p,p+16383>a?a:p+16383));return 1===t?(r=o[e-1],u+=lookup[r>>2],u+=lookup[r<<4&63],u+="=="):2===t&&(r=(o[e-2]<<8)+o[e-1],u+=lookup[r>>10],u+=lookup[r>>4&63],u+=lookup[r<<2&63],u+="="),n.push(u),n.join("")}exports.byteLength=byteLength,exports.toByteArray=toByteArray,exports.fromByteArray=fromByteArray;for(var lookup=[],revLookup=[],Arr="undefined"!=typeof Uint8Array?Uint8Array:Array,code="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/",i=0,len=code.length;i<len;++i)lookup[i]=code[i],revLookup[code.charCodeAt(i)]=i;revLookup["-".charCodeAt(0)]=62,revLookup["_".charCodeAt(0)]=63;

/***/ }),
/* 200 */
/***/ (function(module, exports) {

exports.read=function(a,o,t,r,h){var M,p,w=8*h-r-1,f=(1<<w)-1,e=f>>1,i=-7,N=t?h-1:0,n=t?-1:1,s=a[o+N];for(N+=n,M=s&(1<<-i)-1,s>>=-i,i+=w;i>0;M=256*M+a[o+N],N+=n,i-=8);for(p=M&(1<<-i)-1,M>>=-i,i+=r;i>0;p=256*p+a[o+N],N+=n,i-=8);if(0===M)M=1-e;else{if(M===f)return p?NaN:1/0*(s?-1:1);p+=Math.pow(2,r),M-=e}return(s?-1:1)*p*Math.pow(2,M-r)},exports.write=function(a,o,t,r,h,M){var p,w,f,e=8*M-h-1,i=(1<<e)-1,N=i>>1,n=23===h?Math.pow(2,-24)-Math.pow(2,-77):0,s=r?0:M-1,u=r?1:-1,l=o<0||0===o&&1/o<0?1:0;for(o=Math.abs(o),isNaN(o)||o===1/0?(w=isNaN(o)?1:0,p=i):(p=Math.floor(Math.log(o)/Math.LN2),o*(f=Math.pow(2,-p))<1&&(p--,f*=2),o+=p+N>=1?n/f:n*Math.pow(2,1-N),o*f>=2&&(p++,f/=2),p+N>=i?(w=0,p=i):p+N>=1?(w=(o*f-1)*Math.pow(2,h),p+=N):(w=o*Math.pow(2,N-1)*Math.pow(2,h),p=0));h>=8;a[t+s]=255&w,s+=u,w/=256,h-=8);for(p=p<<h|w,e+=h;e>0;a[t+s]=255&p,s+=u,p/=256,e-=8);a[t+s-u]|=128*l};

/***/ }),
/* 201 */
/***/ (function(module, exports, __webpack_require__) {

var __WEBPACK_AMD_DEFINE_RESULT__;var LZString=function(){function o(o,r){if(!t[o]){t[o]={};for(var n=0;n<o.length;n++)t[o][o.charAt(n)]=n}return t[o][r]}var r=String.fromCharCode,n="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=",e="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+-$",t={},i={compressToBase64:function(o){if(null==o)return"";var r=i._compress(o,6,function(o){return n.charAt(o)});switch(r.length%4){default:case 0:return r;case 1:return r+"===";case 2:return r+"==";case 3:return r+"="}},decompressFromBase64:function(r){return null==r?"":""==r?null:i._decompress(r.length,32,function(e){return o(n,r.charAt(e))})},compressToUTF16:function(o){return null==o?"":i._compress(o,15,function(o){return r(o+32)})+" "},decompressFromUTF16:function(o){return null==o?"":""==o?null:i._decompress(o.length,16384,function(r){return o.charCodeAt(r)-32})},compressToUint8Array:function(o){for(var r=i.compress(o),n=new Uint8Array(2*r.length),e=0,t=r.length;e<t;e++){var s=r.charCodeAt(e);n[2*e]=s>>>8,n[2*e+1]=s%256}return n},decompressFromUint8Array:function(o){if(null===o||void 0===o)return i.decompress(o);for(var n=new Array(o.length/2),e=0,t=n.length;e<t;e++)n[e]=256*o[2*e]+o[2*e+1];var s=[];return n.forEach(function(o){s.push(r(o))}),i.decompress(s.join(""))},compressToEncodedURIComponent:function(o){return null==o?"":i._compress(o,6,function(o){return e.charAt(o)})},decompressFromEncodedURIComponent:function(r){return null==r?"":""==r?null:(r=r.replace(/ /g,"+"),i._decompress(r.length,32,function(n){return o(e,r.charAt(n))}))},compress:function(o){return i._compress(o,16,function(o){return r(o)})},_compress:function(o,r,n){if(null==o)return"";var e,t,i,s={},p={},u="",c="",a="",l=2,f=3,h=2,d=[],m=0,v=0;for(i=0;i<o.length;i+=1)if(u=o.charAt(i),Object.prototype.hasOwnProperty.call(s,u)||(s[u]=f++,p[u]=!0),c=a+u,Object.prototype.hasOwnProperty.call(s,c))a=c;else{if(Object.prototype.hasOwnProperty.call(p,a)){if(a.charCodeAt(0)<256){for(e=0;e<h;e++)m<<=1,v==r-1?(v=0,d.push(n(m)),m=0):v++;for(t=a.charCodeAt(0),e=0;e<8;e++)m=m<<1|1&t,v==r-1?(v=0,d.push(n(m)),m=0):v++,t>>=1}else{for(t=1,e=0;e<h;e++)m=m<<1|t,v==r-1?(v=0,d.push(n(m)),m=0):v++,t=0;for(t=a.charCodeAt(0),e=0;e<16;e++)m=m<<1|1&t,v==r-1?(v=0,d.push(n(m)),m=0):v++,t>>=1}l--,0==l&&(l=Math.pow(2,h),h++),delete p[a]}else for(t=s[a],e=0;e<h;e++)m=m<<1|1&t,v==r-1?(v=0,d.push(n(m)),m=0):v++,t>>=1;l--,0==l&&(l=Math.pow(2,h),h++),s[c]=f++,a=String(u)}if(""!==a){if(Object.prototype.hasOwnProperty.call(p,a)){if(a.charCodeAt(0)<256){for(e=0;e<h;e++)m<<=1,v==r-1?(v=0,d.push(n(m)),m=0):v++;for(t=a.charCodeAt(0),e=0;e<8;e++)m=m<<1|1&t,v==r-1?(v=0,d.push(n(m)),m=0):v++,t>>=1}else{for(t=1,e=0;e<h;e++)m=m<<1|t,v==r-1?(v=0,d.push(n(m)),m=0):v++,t=0;for(t=a.charCodeAt(0),e=0;e<16;e++)m=m<<1|1&t,v==r-1?(v=0,d.push(n(m)),m=0):v++,t>>=1}l--,0==l&&(l=Math.pow(2,h),h++),delete p[a]}else for(t=s[a],e=0;e<h;e++)m=m<<1|1&t,v==r-1?(v=0,d.push(n(m)),m=0):v++,t>>=1;l--,0==l&&(l=Math.pow(2,h),h++)}for(t=2,e=0;e<h;e++)m=m<<1|1&t,v==r-1?(v=0,d.push(n(m)),m=0):v++,t>>=1;for(;;){if(m<<=1,v==r-1){d.push(n(m));break}v++}return d.join("")},decompress:function(o){return null==o?"":""==o?null:i._decompress(o.length,32768,function(r){return o.charCodeAt(r)})},_decompress:function(o,n,e){var t,i,s,p,u,c,a,l=[],f=4,h=4,d=3,m="",v=[],w={val:e(0),position:n,index:1};for(t=0;t<3;t+=1)l[t]=t;for(s=0,u=Math.pow(2,2),c=1;c!=u;)p=w.val&w.position,w.position>>=1,0==w.position&&(w.position=n,w.val=e(w.index++)),s|=(p>0?1:0)*c,c<<=1;switch(s){case 0:for(s=0,u=Math.pow(2,8),c=1;c!=u;)p=w.val&w.position,w.position>>=1,0==w.position&&(w.position=n,w.val=e(w.index++)),s|=(p>0?1:0)*c,c<<=1;a=r(s);break;case 1:for(s=0,u=Math.pow(2,16),c=1;c!=u;)p=w.val&w.position,w.position>>=1,0==w.position&&(w.position=n,w.val=e(w.index++)),s|=(p>0?1:0)*c,c<<=1;a=r(s);break;case 2:return""}for(l[3]=a,i=a,v.push(a);;){if(w.index>o)return"";for(s=0,u=Math.pow(2,d),c=1;c!=u;)p=w.val&w.position,w.position>>=1,0==w.position&&(w.position=n,w.val=e(w.index++)),s|=(p>0?1:0)*c,c<<=1;switch(a=s){case 0:for(s=0,u=Math.pow(2,8),c=1;c!=u;)p=w.val&w.position,w.position>>=1,0==w.position&&(w.position=n,w.val=e(w.index++)),s|=(p>0?1:0)*c,c<<=1;l[h++]=r(s),a=h-1,f--;break;case 1:for(s=0,u=Math.pow(2,16),c=1;c!=u;)p=w.val&w.position,w.position>>=1,0==w.position&&(w.position=n,w.val=e(w.index++)),s|=(p>0?1:0)*c,c<<=1;l[h++]=r(s),a=h-1,f--;break;case 2:return v.join("")}if(0==f&&(f=Math.pow(2,d),d++),l[a])m=l[a];else{if(a!==h)return null;m=i+i.charAt(0)}v.push(m),l[h++]=i+m.charAt(0),f--,i=m,0==f&&(f=Math.pow(2,d),d++)}}};return i}(); true?!(__WEBPACK_AMD_DEFINE_RESULT__ = function(){return LZString}.call(exports, __webpack_require__, exports, module),
				__WEBPACK_AMD_DEFINE_RESULT__ !== undefined && (module.exports = __WEBPACK_AMD_DEFINE_RESULT__)):"undefined"!=typeof module&&null!=module&&(module.exports=LZString);

/***/ }),
/* 202 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
Object.defineProperty(exports,"__esModule",{value:!0});var services_1=__webpack_require__(211);exports.Disposable=services_1.Disposable;var DisposableCollection=function(){function s(){this.disposables=[]}return s.prototype.dispose=function(){for(;0!==this.disposables.length;)this.disposables.pop().dispose()},s.prototype.push=function(s){var e=this.disposables;return e.push(s),{dispose:function(){var o=e.indexOf(s);-1!==o&&e.splice(o,1)}}},s}();exports.DisposableCollection=DisposableCollection;

/***/ }),
/* 203 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
function PassThrough(r){if(!(this instanceof PassThrough))return new PassThrough(r);Transform.call(this,r)}module.exports=PassThrough;var Transform=__webpack_require__(140),util=__webpack_require__(47);util.inherits=__webpack_require__(39),util.inherits(PassThrough,Transform),PassThrough.prototype._transform=function(r,s,i){i(null,r)};

/***/ }),
/* 204 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
function _classCallCheck(t,e){if(!(t instanceof e))throw new TypeError("Cannot call a class as a function")}function copyBuffer(t,e,h){t.copy(e,h)}var Buffer=__webpack_require__(100).Buffer;module.exports=function(){function t(){_classCallCheck(this,t),this.head=null,this.tail=null,this.length=0}return t.prototype.push=function(t){var e={data:t,next:null};this.length>0?this.tail.next=e:this.head=e,this.tail=e,++this.length},t.prototype.unshift=function(t){var e={data:t,next:this.head};0===this.length&&(this.tail=e),this.head=e,++this.length},t.prototype.shift=function(){if(0!==this.length){var t=this.head.data;return 1===this.length?this.head=this.tail=null:this.head=this.head.next,--this.length,t}},t.prototype.clear=function(){this.head=this.tail=null,this.length=0},t.prototype.join=function(t){if(0===this.length)return"";for(var e=this.head,h=""+e.data;e=e.next;)h+=t+e.data;return h},t.prototype.concat=function(t){if(0===this.length)return Buffer.alloc(0);if(1===this.length)return this.head.data;for(var e=Buffer.allocUnsafe(t>>>0),h=this.head,n=0;h;)copyBuffer(h.data,e,n),n+=h.data.length,h=h.next;return e},t}();

/***/ }),
/* 205 */
/***/ (function(module, exports, __webpack_require__) {

/* WEBPACK VAR INJECTION */(function(global, process) {!function(e,t){"use strict";function n(e){"function"!=typeof e&&(e=new Function(""+e));for(var t=new Array(arguments.length-1),n=0;n<t.length;n++)t[n]=arguments[n+1];var a={callback:e,args:t};return r[i]=a,c(i),i++}function a(e){delete r[e]}function s(e){var n=e.callback,a=e.args;switch(a.length){case 0:n();break;case 1:n(a[0]);break;case 2:n(a[0],a[1]);break;case 3:n(a[0],a[1],a[2]);break;default:n.apply(t,a)}}function o(e){if(f)setTimeout(o,0,e);else{var t=r[e];if(t){f=!0;try{s(t)}finally{a(e),f=!1}}}}if(!e.setImmediate){var c,i=1,r={},f=!1,u=e.document,l=Object.getPrototypeOf&&Object.getPrototypeOf(e);l=l&&l.setTimeout?l:e,"[object process]"==={}.toString.call(e.process)?function(){c=function(e){process.nextTick(function(){o(e)})}}():function(){if(e.postMessage&&!e.importScripts){var t=!0,n=e.onmessage;return e.onmessage=function(){t=!1},e.postMessage("","*"),e.onmessage=n,t}}()?function(){var t="setImmediate$"+Math.random()+"$",n=function(n){n.source===e&&"string"==typeof n.data&&0===n.data.indexOf(t)&&o(+n.data.slice(t.length))};e.addEventListener?e.addEventListener("message",n,!1):e.attachEvent("onmessage",n),c=function(n){e.postMessage(t+n,"*")}}():e.MessageChannel?function(){var e=new MessageChannel;e.port1.onmessage=function(e){o(e.data)},c=function(t){e.port2.postMessage(t)}}():u&&"onreadystatechange"in u.createElement("script")?function(){var e=u.documentElement;c=function(t){var n=u.createElement("script");n.onreadystatechange=function(){o(t),n.onreadystatechange=null,e.removeChild(n),n=null},e.appendChild(n)}}():function(){c=function(e){setTimeout(o,0,e)}}(),l.setImmediate=n,l.clearImmediate=a}}("undefined"==typeof self?"undefined"==typeof global?this:global:self);
/* WEBPACK VAR INJECTION */}.call(exports, __webpack_require__(27), __webpack_require__(28)))

/***/ }),
/* 206 */
/***/ (function(module, exports, __webpack_require__) {

/* WEBPACK VAR INJECTION */(function(global) {function deprecate(r,e){function o(){if(!t){if(config("throwDeprecation"))throw new Error(e);config("traceDeprecation")?console.trace(e):console.warn(e),t=!0}return r.apply(this,arguments)}if(config("noDeprecation"))return r;var t=!1;return o}function config(r){try{if(!global.localStorage)return!1}catch(r){return!1}var e=global.localStorage[r];return null!=e&&"true"===String(e).toLowerCase()}module.exports=deprecate;
/* WEBPACK VAR INJECTION */}.call(exports, __webpack_require__(27)))

/***/ }),
/* 207 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
/* WEBPACK VAR INJECTION */(function(Buffer) {var __extends=this&&this.__extends||function(){var e=Object.setPrototypeOf||{__proto__:[]}instanceof Array&&function(e,t){e.__proto__=t}||function(e,t){for(var r in t)t.hasOwnProperty(r)&&(e[r]=t[r])};return function(t,r){function s(){this.constructor=t}e(t,r),t.prototype=null===r?Object.create(r):(s.prototype=r.prototype,new s)}}();Object.defineProperty(exports,"__esModule",{value:!0});var events_1=__webpack_require__(188),Is=__webpack_require__(189),DefaultSize=8192,CR=new Buffer("\r","ascii")[0],LF=new Buffer("\n","ascii")[0],CRLF="\r\n",MessageBuffer=function(){function e(e){void 0===e&&(e="utf8"),this.encoding=e,this.index=0,this.buffer=new Buffer(DefaultSize)}return e.prototype.append=function(e){var t=e;if("string"==typeof e){var r=e,s=Buffer.byteLength(r,this.encoding);t=new Buffer(s),t.write(r,0,s,this.encoding)}if(this.buffer.length-this.index>=t.length)t.copy(this.buffer,this.index,0,t.length);else{var i=(Math.ceil((this.index+t.length)/DefaultSize)+1)*DefaultSize;0===this.index?(this.buffer=new Buffer(i),t.copy(this.buffer,0,0,t.length)):this.buffer=Buffer.concat([this.buffer.slice(0,this.index),t],i)}this.index+=t.length},e.prototype.tryReadHeaders=function(){for(var e=void 0,t=0;t+3<this.index&&(this.buffer[t]!==CR||this.buffer[t+1]!==LF||this.buffer[t+2]!==CR||this.buffer[t+3]!==LF);)t++;if(t+3>=this.index)return e;e=Object.create(null),this.buffer.toString("ascii",0,t).split(CRLF).forEach(function(t){var r=t.indexOf(":");if(-1===r)throw new Error("Message header must separate key and value using :");var s=t.substr(0,r),i=t.substr(r+1).trim();e[s]=i});var r=t+4;return this.buffer=this.buffer.slice(r),this.index=this.index-r,e},e.prototype.tryReadContent=function(e){if(this.index<e)return null;var t=this.buffer.toString(this.encoding,0,e),r=e;return this.buffer.copy(this.buffer,0,r),this.index=this.index-r,t},Object.defineProperty(e.prototype,"numberOfBytes",{get:function(){return this.index},enumerable:!0,configurable:!0}),e}(),MessageReader;!function(e){function t(e){var t=e;return t&&Is.func(t.listen)&&Is.func(t.dispose)&&Is.func(t.onError)&&Is.func(t.onClose)&&Is.func(t.onPartialMessage)}e.is=t}(MessageReader=exports.MessageReader||(exports.MessageReader={}));var AbstractMessageReader=function(){function e(){this.errorEmitter=new events_1.Emitter,this.closeEmitter=new events_1.Emitter,this.partialMessageEmitter=new events_1.Emitter}return e.prototype.dispose=function(){this.errorEmitter.dispose(),this.closeEmitter.dispose()},Object.defineProperty(e.prototype,"onError",{get:function(){return this.errorEmitter.event},enumerable:!0,configurable:!0}),e.prototype.fireError=function(e){this.errorEmitter.fire(this.asError(e))},Object.defineProperty(e.prototype,"onClose",{get:function(){return this.closeEmitter.event},enumerable:!0,configurable:!0}),e.prototype.fireClose=function(){this.closeEmitter.fire(void 0)},Object.defineProperty(e.prototype,"onPartialMessage",{get:function(){return this.partialMessageEmitter.event},enumerable:!0,configurable:!0}),e.prototype.firePartialMessage=function(e){this.partialMessageEmitter.fire(e)},e.prototype.asError=function(e){return e instanceof Error?e:new Error("Reader recevied error. Reason: "+(Is.string(e.message)?e.message:"unknown"))},e}();exports.AbstractMessageReader=AbstractMessageReader;var StreamMessageReader=function(e){function t(t,r){void 0===r&&(r="utf8");var s=e.call(this)||this;return s.readable=t,s.buffer=new MessageBuffer(r),s._partialMessageTimeout=1e4,s}return __extends(t,e),Object.defineProperty(t.prototype,"partialMessageTimeout",{get:function(){return this._partialMessageTimeout},set:function(e){this._partialMessageTimeout=e},enumerable:!0,configurable:!0}),t.prototype.listen=function(e){var t=this;this.nextMessageLength=-1,this.messageToken=0,this.partialMessageTimer=void 0,this.callback=e,this.readable.on("data",function(e){t.onData(e)}),this.readable.on("error",function(e){return t.fireError(e)}),this.readable.on("close",function(){return t.fireClose()})},t.prototype.onData=function(e){for(this.buffer.append(e);;){if(-1===this.nextMessageLength){var t=this.buffer.tryReadHeaders();if(!t)return;var r=t["Content-Length"];if(!r)throw new Error("Header must provide a Content-Length property.");var s=parseInt(r);if(isNaN(s))throw new Error("Content-Length value must be a number.");this.nextMessageLength=s}var i=this.buffer.tryReadContent(this.nextMessageLength);if(null===i)return void this.setPartialMessageTimer();this.clearPartialMessageTimer(),this.nextMessageLength=-1,this.messageToken++;var n=JSON.parse(i);this.callback(n)}},t.prototype.clearPartialMessageTimer=function(){this.partialMessageTimer&&(clearTimeout(this.partialMessageTimer),this.partialMessageTimer=void 0)},t.prototype.setPartialMessageTimer=function(){var e=this;this.clearPartialMessageTimer(),this._partialMessageTimeout<=0||(this.partialMessageTimer=setTimeout(function(t,r){e.partialMessageTimer=void 0,t===e.messageToken&&(e.firePartialMessage({messageToken:t,waitingTime:r}),e.setPartialMessageTimer())},this._partialMessageTimeout,this.messageToken,this._partialMessageTimeout))},t}(AbstractMessageReader);exports.StreamMessageReader=StreamMessageReader;var IPCMessageReader=function(e){function t(t){var r=e.call(this)||this;r.process=t;var s=r.process;return s.on("error",function(e){return r.fireError(e)}),s.on("close",function(){return r.fireClose()}),r}return __extends(t,e),t.prototype.listen=function(e){this.process.on("message",e)},t}(AbstractMessageReader);exports.IPCMessageReader=IPCMessageReader;var SocketMessageReader=function(e){function t(t,r){return void 0===r&&(r="utf-8"),e.call(this,t,r)||this}return __extends(t,e),t}(StreamMessageReader);exports.SocketMessageReader=SocketMessageReader;
/* WEBPACK VAR INJECTION */}.call(exports, __webpack_require__(38).Buffer))

/***/ }),
/* 208 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
/* WEBPACK VAR INJECTION */(function(Buffer) {var __extends=this&&this.__extends||function(){var e=Object.setPrototypeOf||{__proto__:[]}instanceof Array&&function(e,r){e.__proto__=r}||function(e,r){for(var t in r)r.hasOwnProperty(t)&&(e[t]=r[t])};return function(r,t){function n(){this.constructor=r}e(r,t),r.prototype=null===t?Object.create(t):(n.prototype=t.prototype,new n)}}();Object.defineProperty(exports,"__esModule",{value:!0});var events_1=__webpack_require__(188),Is=__webpack_require__(189),ContentLength="Content-Length: ",CRLF="\r\n",MessageWriter;!function(e){function r(e){var r=e;return r&&Is.func(r.dispose)&&Is.func(r.onClose)&&Is.func(r.onError)&&Is.func(r.write)}e.is=r}(MessageWriter=exports.MessageWriter||(exports.MessageWriter={}));var AbstractMessageWriter=function(){function e(){this.errorEmitter=new events_1.Emitter,this.closeEmitter=new events_1.Emitter}return e.prototype.dispose=function(){this.errorEmitter.dispose(),this.closeEmitter.dispose()},Object.defineProperty(e.prototype,"onError",{get:function(){return this.errorEmitter.event},enumerable:!0,configurable:!0}),e.prototype.fireError=function(e,r,t){this.errorEmitter.fire([this.asError(e),r,t])},Object.defineProperty(e.prototype,"onClose",{get:function(){return this.closeEmitter.event},enumerable:!0,configurable:!0}),e.prototype.fireClose=function(){this.closeEmitter.fire(void 0)},e.prototype.asError=function(e){return e instanceof Error?e:new Error("Writer recevied error. Reason: "+(Is.string(e.message)?e.message:"unknown"))},e}();exports.AbstractMessageWriter=AbstractMessageWriter;var StreamMessageWriter=function(e){function r(r,t){void 0===t&&(t="utf8");var n=e.call(this)||this;return n.writable=r,n.encoding=t,n.errorCount=0,n.writable.on("error",function(e){return n.fireError(e)}),n.writable.on("close",function(){return n.fireClose()}),n}return __extends(r,e),r.prototype.write=function(e){var r=JSON.stringify(e),t=Buffer.byteLength(r,this.encoding),n=[ContentLength,t.toString(),CRLF,CRLF];try{this.writable.write(n.join(""),"ascii"),this.writable.write(r,this.encoding),this.errorCount=0}catch(r){this.errorCount++,this.fireError(r,e,this.errorCount)}},r}(AbstractMessageWriter);exports.StreamMessageWriter=StreamMessageWriter;var IPCMessageWriter=function(e){function r(r){var t=e.call(this)||this;t.process=r,t.errorCount=0,t.queue=[],t.sending=!1;var n=t.process;return n.on("error",function(e){return t.fireError(e)}),n.on("close",function(){return t.fireClose}),t}return __extends(r,e),r.prototype.write=function(e){this.sending||0!==this.queue.length?this.queue.push(e):this.doWriteMessage(e)},r.prototype.doWriteMessage=function(e){var r=this;try{this.process.send&&(this.sending=!0,this.process.send(e,void 0,void 0,function(t){r.sending=!1,t?(r.errorCount++,r.fireError(t,e,r.errorCount)):r.errorCount=0,r.queue.length>0&&r.doWriteMessage(r.queue.shift())}))}catch(r){this.errorCount++,this.fireError(r,e,this.errorCount)}},r}(AbstractMessageWriter);exports.IPCMessageWriter=IPCMessageWriter;var SocketMessageWriter=function(e){function r(r,t){void 0===t&&(t="utf8");var n=e.call(this)||this;return n.socket=r,n.queue=[],n.sending=!1,n.encoding=t,n.errorCount=0,n.socket.on("error",function(e){return n.fireError(e)}),n.socket.on("close",function(){return n.fireClose()}),n}return __extends(r,e),r.prototype.write=function(e){this.sending||0!==this.queue.length?this.queue.push(e):this.doWriteMessage(e)},r.prototype.doWriteMessage=function(e){var r=this,t=JSON.stringify(e),n=Buffer.byteLength(t,this.encoding),o=[ContentLength,n.toString(),CRLF,CRLF];try{this.sending=!0,this.socket.write(o.join(""),"ascii",function(n){n&&r.handleError(n,e);try{r.socket.write(t,r.encoding,function(t){r.sending=!1,t?r.handleError(t,e):r.errorCount=0,r.queue.length>0&&r.doWriteMessage(r.queue.shift())})}catch(n){r.handleError(n,e)}})}catch(r){this.handleError(r,e)}},r.prototype.handleError=function(e,r){this.errorCount++,this.fireError(e,r,this.errorCount)},r}(AbstractMessageWriter);exports.SocketMessageWriter=SocketMessageWriter;
/* WEBPACK VAR INJECTION */}.call(exports, __webpack_require__(38).Buffer))

/***/ }),
/* 209 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
function __export(e){for(var r in e)exports.hasOwnProperty(r)||(exports[r]=e[r])}Object.defineProperty(exports,"__esModule",{value:!0}),__export(__webpack_require__(210)),__export(__webpack_require__(267)),__export(__webpack_require__(268));

/***/ }),
/* 210 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
/* WEBPACK VAR INJECTION */(function(Buffer) {var __extends=this&&this.__extends||function(){var t=Object.setPrototypeOf||{__proto__:[]}instanceof Array&&function(t,e){t.__proto__=e}||function(t,e){for(var r in e)e.hasOwnProperty(r)&&(t[r]=e[r])};return function(e,r){function n(){this.constructor=e}t(e,r),e.prototype=null===r?Object.create(r):(n.prototype=r.prototype,new n)}}();Object.defineProperty(exports,"__esModule",{value:!0});var stream_1=__webpack_require__(253),ReadableStream=function(t){function e(e){var r=t.call(this)||this;return r.push(e),r.push(null),r}return __extends(e,t),e.prototype._read=function(t){},e}(stream_1.Readable);exports.ReadableStream=ReadableStream;var WritableStream=function(t){function e(){var e=null!==t&&t.apply(this,arguments)||this;return e.data=new Buffer(""),e}return __extends(e,t),e.prototype._write=function(t,e,r){var n=this.toBuffer(t,e);this.data=Buffer.concat([this.data,n]),r()},e.prototype.toBuffer=function(t,e){return Buffer.isBuffer(t)?t:new Buffer(t,e)},e}(stream_1.Writable);exports.WritableStream=WritableStream;
/* WEBPACK VAR INJECTION */}.call(exports, __webpack_require__(38).Buffer))

/***/ }),
/* 211 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
/* --------------------------------------------------------------------------------------------
 * Copyright (c) 2017 TypeFox GmbH (http://www.typefox.io). All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for license information.
 * ------------------------------------------------------------------------------------------ */

function __export(m) {
    for (var p in m) if (!exports.hasOwnProperty(p)) exports[p] = m[p];
}
Object.defineProperty(exports, "__esModule", { value: true });
const vscode_jsonrpc_1 = __webpack_require__(190);
exports.Disposable = vscode_jsonrpc_1.Disposable;
exports.CancellationToken = vscode_jsonrpc_1.CancellationToken;
exports.Event = vscode_jsonrpc_1.Event;
exports.Emitter = vscode_jsonrpc_1.Emitter;
__export(__webpack_require__(191));
__export(__webpack_require__(222));


/***/ }),
/* 212 */
/***/ (function(module, exports) {

/* (ignored) */

/***/ }),
/* 213 */,
/* 214 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
Object.defineProperty(exports,"__esModule",{value:!0});var MonacoCommands=function(){function o(o){this.editor=o}return o.prototype.registerCommand=function(o,n,e){return this.editor._commandService.addCommand(o,{handler:function(o){for(var e=[],r=1;r<arguments.length;r++)e[r-1]=arguments[r];return n.apply(void 0,e)}})},o}();exports.MonacoCommands=MonacoCommands;

/***/ }),
/* 215 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
Object.defineProperty(exports,"__esModule",{value:!0});var protocol_1=__webpack_require__(191),ConsoleWindow=function(){function o(){this.channels=new Map}return o.prototype.showMessage=function(o,e){for(var n=[],r=2;r<arguments.length;r++)n[r-2]=arguments[r];return o===protocol_1.MessageType.Error&&console.error(e),o===protocol_1.MessageType.Warning&&console.warn(e),o===protocol_1.MessageType.Info&&console.info(e),o===protocol_1.MessageType.Log&&console.log(e),Promise.resolve(void 0)},o.prototype.createOutputChannel=function(o){var e=this.channels.get(o);if(e)return e;var n={append:function(e){console.log(o+": "+e)},appendLine:function(e){console.log(o+": "+e)},show:function(){}};return this.channels.set(o,n),n},o}();exports.ConsoleWindow=ConsoleWindow;

/***/ }),
/* 216 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
var __assign=this&&this.__assign||Object.assign||function(t){for(var e,n=1,o=arguments.length;n<o;n++){e=arguments[n];for(var r in e)Object.prototype.hasOwnProperty.call(e,r)&&(t[r]=e[r])}return t};Object.defineProperty(exports,"__esModule",{value:!0});var is=__webpack_require__(194),base_1=__webpack_require__(227),ProtocolCodeLens;!function(t){function e(t){return!!t&&"data"in t}t.is=e}(ProtocolCodeLens=exports.ProtocolCodeLens||(exports.ProtocolCodeLens={}));var ProtocolCompletionItem;!function(t){function e(t){return!!t&&"data"in t}t.is=e}(ProtocolCompletionItem=exports.ProtocolCompletionItem||(exports.ProtocolCompletionItem={}));var MonacoToProtocolConverter=function(){function t(){}return t.prototype.asPosition=function(t,e){return{line:void 0===t||null===t?void 0:t-1,character:void 0===e||null===e?void 0:e-1}},t.prototype.asRange=function(t){if(void 0!==t){if(null===t)return null;return{start:this.asPosition(t.startLineNumber,t.startColumn),end:this.asPosition(t.endLineNumber,t.endColumn)}}},t.prototype.asTextDocumentIdentifier=function(t){return{uri:t.uri.toString()}},t.prototype.asTextDocumentPositionParams=function(t,e){return{textDocument:this.asTextDocumentIdentifier(t),position:this.asPosition(e.lineNumber,e.column)}},t.prototype.asCompletionItem=function(t){var e={label:t.label};return t.detail&&(e.detail=t.detail),t.documentation&&(e.documentation=t.documentation),t.filterText&&(e.filterText=t.filterText),this.fillPrimaryInsertText(e,t),is.number(t.kind)&&(monaco.languages.CompletionItemKind.Text<=t.kind&&t.kind<=monaco.languages.CompletionItemKind.Reference?e.kind=t.kind+1:e.kind=base_1.CompletionItemKind.Text),t.sortText&&(e.sortText=t.sortText),ProtocolCompletionItem.is(t)&&(e.data=t.data),e},t.prototype.fillPrimaryInsertText=function(t,e){var n,o,r=base_1.InsertTextFormat.PlainText;e.textEdit?(n=e.textEdit.text,o=this.asRange(e.textEdit.range)):"string"==typeof e.insertText?n=e.insertText:e.insertText&&(r=base_1.InsertTextFormat.Snippet,n=e.insertText.value),e.range&&(o=this.asRange(e.range)),t.insertTextFormat=r,e.fromEdit&&n&&o?t.textEdit={newText:n,range:o}:t.insertText=n},t.prototype.asReferenceParams=function(t,e,n){return{textDocument:this.asTextDocumentIdentifier(t),position:this.asPosition(e.lineNumber,e.column),context:{includeDeclaration:n.includeDeclaration}}},t.prototype.asDocumentSymbolParams=function(t){return{textDocument:this.asTextDocumentIdentifier(t)}},t.prototype.asCodeLensParams=function(t){return{textDocument:this.asTextDocumentIdentifier(t)}},t.prototype.asDiagnosticSeverity=function(t){switch(t){case monaco.Severity.Error:return base_1.DiagnosticSeverity.Error;case monaco.Severity.Warning:return base_1.DiagnosticSeverity.Warning;case monaco.Severity.Info:return base_1.DiagnosticSeverity.Information}},t.prototype.asDiagnostic=function(t){var e=this.asRange(new monaco.Range(t.startLineNumber,t.startColumn,t.endLineNumber,t.endColumn)),n=this.asDiagnosticSeverity(t.severity);return base_1.Diagnostic.create(e,t.message,n,t.code,t.source)},t.prototype.asDiagnostics=function(t){var e=this;return void 0===t||null===t?t:t.map(function(t){return e.asDiagnostic(t)})},t.prototype.asCodeActionContext=function(t){return void 0===t||null===t?t:{diagnostics:this.asDiagnostics(t.markers)}},t.prototype.asCodeActionParams=function(t,e,n){return{textDocument:this.asTextDocumentIdentifier(t),range:this.asRange(e),context:this.asCodeActionContext(n)}},t.prototype.asCommand=function(t){if(t){var e=t.arguments||[];return base_1.Command.create.apply(base_1.Command,[t.title,t.id].concat(e))}},t.prototype.asCodeLens=function(t){return{range:this.asRange(t.range),data:ProtocolCodeLens.is(t)?t.data:void 0,command:this.asCommand(t.command)}},t.prototype.asFormattingOptions=function(t){return{tabSize:t.tabSize,insertSpaces:t.insertSpaces}},t.prototype.asDocumentFormattingParams=function(t,e){return{textDocument:this.asTextDocumentIdentifier(t),options:this.asFormattingOptions(e)}},t.prototype.asDocumentRangeFormattingParams=function(t,e,n){return{textDocument:this.asTextDocumentIdentifier(t),range:this.asRange(e),options:this.asFormattingOptions(n)}},t.prototype.asDocumentOnTypeFormattingParams=function(t,e,n,o){return{textDocument:this.asTextDocumentIdentifier(t),position:this.asPosition(e.lineNumber,e.column),ch:n,options:this.asFormattingOptions(o)}},t.prototype.asRenameParams=function(t,e,n){return{textDocument:this.asTextDocumentIdentifier(t),position:this.asPosition(e.lineNumber,e.column),newName:n}},t.prototype.asDocumentLinkParams=function(t){return{textDocument:this.asTextDocumentIdentifier(t)}},t.prototype.asDocumentLink=function(t){return{range:this.asRange(t.range),target:t.url}},t}();exports.MonacoToProtocolConverter=MonacoToProtocolConverter;var ProtocolToMonacoConverter=function(){function t(){}return t.prototype.asResourceEdits=function(t,e){var n=this;return e.map(function(e){var o=n.asRange(e.range);return{resource:t,range:o,newText:e.newText}})},t.prototype.asWorkspaceEdit=function(t){if(t){var e=[];if(t.documentChanges)for(var n=0,o=t.documentChanges;n<o.length;n++){var r=o[n],i=monaco.Uri.parse(r.textDocument.uri);e.push.apply(e,this.asResourceEdits(i,r.edits))}else if(t.changes)for(var a=0,s=Object.keys(t.changes);a<s.length;a++){var u=s[a],i=monaco.Uri.parse(u);e.push.apply(e,this.asResourceEdits(i,t.changes[u]))}return{edits:e}}},t.prototype.asTextEdit=function(t){return{range:this.asRange(t.range),text:t.newText}},t.prototype.asTextEdits=function(t){var e=this;if(t)return t.map(function(t){return e.asTextEdit(t)})},t.prototype.asCodeLens=function(t){if(t){var e=this.asRange(t.range),n={range:e};return t.command&&(n.command=this.asCommand(t.command)),void 0!==t.data&&null!==t.data&&(n.data=t.data),n}},t.prototype.asCodeLenses=function(t){var e=this;if(t)return t.map(function(t){return e.asCodeLens(t)})},t.prototype.asCommand=function(t){return{id:t.command,title:t.title,arguments:t.arguments}},t.prototype.asCommands=function(t){var e=this;return t.map(function(t){return e.asCommand(t)})},t.prototype.asSymbolInformations=function(t,e){var n=this;if(t)return t.map(function(t){return n.asSymbolInformation(t,e)})},t.prototype.asSymbolInformation=function(t,e){return{name:t.name,containerName:t.containerName,kind:t.kind-1,location:this.asLocation(e?__assign({},t.location,{uri:e.toString()}):t.location)}},t.prototype.asDocumentHighlights=function(t){var e=this;if(t)return t.map(function(t){return e.asDocumentHighlight(t)})},t.prototype.asDocumentHighlight=function(t){return{range:this.asRange(t.range),kind:is.number(t.kind)?this.asDocumentHighlightKind(t.kind):void 0}},t.prototype.asDocumentHighlightKind=function(t){switch(t){case base_1.DocumentHighlightKind.Text:return monaco.languages.DocumentHighlightKind.Text;case base_1.DocumentHighlightKind.Read:return monaco.languages.DocumentHighlightKind.Read;case base_1.DocumentHighlightKind.Write:return monaco.languages.DocumentHighlightKind.Write}return monaco.languages.DocumentHighlightKind.Text},t.prototype.asReferences=function(t){var e=this;if(t)return t.map(function(t){return e.asLocation(t)})},t.prototype.asDefinitionResult=function(t){var e=this;if(t)return is.array(t)?t.map(function(t){return e.asLocation(t)}):this.asLocation(t)},t.prototype.asLocation=function(t){if(t){return{uri:monaco.Uri.parse(t.uri),range:this.asRange(t.range)}}},t.prototype.asSignatureHelp=function(t){if(t){var e={};return is.number(t.activeSignature)?e.activeSignature=t.activeSignature:e.activeSignature=0,is.number(t.activeParameter)?e.activeParameter=t.activeParameter:e.activeParameter=0,t.signatures&&(e.signatures=this.asSignatureInformations(t.signatures)),e}},t.prototype.asSignatureInformations=function(t){var e=this;return t.map(function(t){return e.asSignatureInformation(t)})},t.prototype.asSignatureInformation=function(t){var e={label:t.label};return t.documentation&&(e.documentation=t.documentation),t.parameters&&(e.parameters=this.asParameterInformations(t.parameters)),e},t.prototype.asParameterInformations=function(t){var e=this;return t.map(function(t){return e.asParameterInformation(t)})},t.prototype.asParameterInformation=function(t){var e={label:t.label};return t.documentation&&(e.documentation=t.documentation),e},t.prototype.asHover=function(t){if(t){return{contents:Array.isArray(t.contents)?t.contents:[t.contents],range:this.asRange(t.range)}}},t.prototype.asSeverity=function(t){return 1===t?monaco.Severity.Error:2===t?monaco.Severity.Warning:3===t?monaco.Severity.Info:monaco.Severity.Ignore},t.prototype.asMarker=function(t){return{code:"number"==typeof t.code?t.code.toString():t.code,severity:this.asSeverity(t.severity),message:t.message,source:t.source,startLineNumber:t.range.start.line+1,startColumn:t.range.start.character+1,endLineNumber:t.range.end.line+1,endColumn:t.range.end.character+1}},t.prototype.asCompletionResult=function(t){var e=this;if(t)return Array.isArray(t)?t.map(function(t){return e.asCompletionItem(t)}):{isIncomplete:t.isIncomplete,items:t.items.map(this.asCompletionItem.bind(this))}},t.prototype.asCompletionItem=function(t){var e={label:t.label};t.detail&&(e.detail=t.detail),t.documentation&&(e.documentation=t.documentation),t.filterText&&(e.filterText=t.filterText);var n=this.asCompletionInsertText(t);return n&&(e.insertText=n.text,e.range=n.range,e.fromEdit=n.fromEdit),is.number(t.kind)&&t.kind>0&&(e.kind=t.kind-1),t.sortText&&(e.sortText=t.sortText),void 0!==t.data&&null!==t.data&&(e.data=t.data),e},t.prototype.asCompletionInsertText=function(t){if(t.textEdit){var e=this.asRange(t.textEdit.range),n=t.textEdit.newText,o=t.insertTextFormat===base_1.InsertTextFormat.Snippet?{value:n}:n;return{text:o,range:e,fromEdit:!0}}if(t.insertText){var n=t.insertText,o=t.insertTextFormat===base_1.InsertTextFormat.Snippet?{value:n}:n;return{text:o,fromEdit:!1}}},t.prototype.asILinks=function(t){for(var e=[],n=0,o=t;n<o.length;n++){var r=o[n];e.push(this.asILink(r))}return e},t.prototype.asILink=function(t){return{range:this.asRange(t.range),url:t.target}},t.prototype.asRange=function(t){if(void 0!==t){if(null===t)return null;var e=this.asPosition(t.start),n=this.asPosition(t.end);if(e instanceof monaco.Position&&n instanceof monaco.Position)return new monaco.Range(e.lineNumber,e.column,n.lineNumber,n.column);return{startLineNumber:e&&void 0!==e.lineNumber?e.lineNumber:void 0,startColumn:e&&void 0!==e.column?e.column:void 0,endLineNumber:n&&void 0!==n.lineNumber?n.lineNumber:void 0,endColumn:n&&void 0!==n.column?n.column:void 0}}},t.prototype.asPosition=function(t){if(void 0!==t){if(null===t)return null;var e=t.line,n=t.character,o=void 0===e?void 0:e+1,r=void 0===n?void 0:n+1;return void 0!==o&&void 0!==r?new monaco.Position(o,r):{lineNumber:o,column:r}}},t}();exports.ProtocolToMonacoConverter=ProtocolToMonacoConverter;

/***/ }),
/* 217 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
function testGlob(e,r){return globToRegExp(e,{extended:!0,globstar:!0}).test(r)}function getLanguages(){for(var e=[],r=0,o=monaco.languages.getLanguages().map(function(e){return e.id});r<o.length;r++){var t=o[r];-1===e.indexOf(t)&&e.push(t)}return e}Object.defineProperty(exports,"__esModule",{value:!0});var globToRegExp=__webpack_require__(238),services_1=__webpack_require__(211),diagnostic_collection_1=__webpack_require__(239),disposable_1=__webpack_require__(202),MonacoModelIdentifier;!function(e){function r(e){return{uri:monaco.Uri.parse(e.uri),languageId:e.languageId}}function o(e){return{uri:e.uri,languageId:e.getModeId()}}e.fromDocument=r,e.fromModel=o}(MonacoModelIdentifier=exports.MonacoModelIdentifier||(exports.MonacoModelIdentifier={})),exports.testGlob=testGlob,exports.getLanguages=getLanguages;var MonacoLanguages=function(){function e(e,r){this.p2m=e,this.m2p=r,this.completion={completionItem:{snippetSupport:!0}}}return e.prototype.match=function(e,r){return this.matchModel(e,MonacoModelIdentifier.fromDocument(r))},e.prototype.createDiagnosticCollection=function(e){return new diagnostic_collection_1.MonacoDiagnosticCollection(e||"default",this.p2m)},e.prototype.registerCompletionItemProvider=function(e,r){for(var o=[],t=2;t<arguments.length;t++)o[t-2]=arguments[t];for(var n=this.createCompletionProvider.apply(this,[e,r].concat(o)),i=new disposable_1.DisposableCollection,a=0,s=getLanguages();a<s.length;a++){var u=s[a];i.push(monaco.languages.registerCompletionItemProvider(u,n))}return i},e.prototype.createCompletionProvider=function(e,r){for(var o=this,t=[],n=2;n<arguments.length;n++)t[n-2]=arguments[n];return{triggerCharacters:t,provideCompletionItems:function(t,n,i){if(!o.matchModel(e,MonacoModelIdentifier.fromModel(t)))return[];var a=o.m2p.asTextDocumentPositionParams(t,n);return r.provideCompletionItems(a,i).then(function(e){return o.p2m.asCompletionResult(e)})},resolveCompletionItem:r.resolveCompletionItem?function(e,t){var n=o.m2p.asCompletionItem(e);return r.resolveCompletionItem(n,t).then(function(e){return o.p2m.asCompletionItem(e)})}:void 0}},e.prototype.registerHoverProvider=function(e,r){for(var o=this.createHoverProvider(e,r),t=new disposable_1.DisposableCollection,n=0,i=getLanguages();n<i.length;n++){var a=i[n];t.push(monaco.languages.registerHoverProvider(a,o))}return t},e.prototype.createHoverProvider=function(e,r){var o=this;return{provideHover:function(t,n,i){if(o.matchModel(e,MonacoModelIdentifier.fromModel(t))){var a=o.m2p.asTextDocumentPositionParams(t,n);return r.provideHover(a,i).then(function(e){return o.p2m.asHover(e)})}}}},e.prototype.registerSignatureHelpProvider=function(e,r){for(var o=[],t=2;t<arguments.length;t++)o[t-2]=arguments[t];for(var n=this.createSignatureHelpProvider.apply(this,[e,r].concat(o)),i=new disposable_1.DisposableCollection,a=0,s=getLanguages();a<s.length;a++){var u=s[a];i.push(monaco.languages.registerSignatureHelpProvider(u,n))}return i},e.prototype.createSignatureHelpProvider=function(e,r){for(var o=this,t=[],n=2;n<arguments.length;n++)t[n-2]=arguments[n];return{signatureHelpTriggerCharacters:t,provideSignatureHelp:function(t,n,i){if(o.matchModel(e,MonacoModelIdentifier.fromModel(t))){var a=o.m2p.asTextDocumentPositionParams(t,n);return r.provideSignatureHelp(a,i).then(function(e){return o.p2m.asSignatureHelp(e)})}}}},e.prototype.registerDefinitionProvider=function(e,r){for(var o=this.createDefinitionProvider(e,r),t=new disposable_1.DisposableCollection,n=0,i=getLanguages();n<i.length;n++){var a=i[n];t.push(monaco.languages.registerDefinitionProvider(a,o))}return t},e.prototype.createDefinitionProvider=function(e,r){var o=this;return{provideDefinition:function(t,n,i){if(o.matchModel(e,MonacoModelIdentifier.fromModel(t))){var a=o.m2p.asTextDocumentPositionParams(t,n);return r.provideDefinition(a,i).then(function(e){return o.p2m.asDefinitionResult(e)})}}}},e.prototype.registerReferenceProvider=function(e,r){for(var o=this.createReferenceProvider(e,r),t=new disposable_1.DisposableCollection,n=0,i=getLanguages();n<i.length;n++){var a=i[n];t.push(monaco.languages.registerReferenceProvider(a,o))}return t},e.prototype.createReferenceProvider=function(e,r){var o=this;return{provideReferences:function(t,n,i,a){if(!o.matchModel(e,MonacoModelIdentifier.fromModel(t)))return[];var s=o.m2p.asReferenceParams(t,n,i);return r.provideReferences(s,a).then(function(e){return o.p2m.asReferences(e)})}}},e.prototype.registerDocumentHighlightProvider=function(e,r){for(var o=this.createDocumentHighlightProvider(e,r),t=new disposable_1.DisposableCollection,n=0,i=getLanguages();n<i.length;n++){var a=i[n];t.push(monaco.languages.registerDocumentHighlightProvider(a,o))}return t},e.prototype.createDocumentHighlightProvider=function(e,r){var o=this;return{provideDocumentHighlights:function(t,n,i){if(!o.matchModel(e,MonacoModelIdentifier.fromModel(t)))return[];var a=o.m2p.asTextDocumentPositionParams(t,n);return r.provideDocumentHighlights(a,i).then(function(e){return o.p2m.asDocumentHighlights(e)})}}},e.prototype.registerDocumentSymbolProvider=function(e,r){for(var o=this.createDocumentSymbolProvider(e,r),t=new disposable_1.DisposableCollection,n=0,i=getLanguages();n<i.length;n++){var a=i[n];t.push(monaco.languages.registerDocumentSymbolProvider(a,o))}return t},e.prototype.createDocumentSymbolProvider=function(e,r){var o=this;return{provideDocumentSymbols:function(t,n){if(!o.matchModel(e,MonacoModelIdentifier.fromModel(t)))return[];var i=o.m2p.asDocumentSymbolParams(t);return r.provideDocumentSymbols(i,n).then(function(e){return o.p2m.asSymbolInformations(e)})}}},e.prototype.registerCodeActionsProvider=function(e,r){for(var o=this.createCodeActionProvider(e,r),t=new disposable_1.DisposableCollection,n=0,i=getLanguages();n<i.length;n++){var a=i[n];t.push(monaco.languages.registerCodeActionProvider(a,o))}return t},e.prototype.createCodeActionProvider=function(e,r){var o=this;return{provideCodeActions:function(t,n,i,a){if(!o.matchModel(e,MonacoModelIdentifier.fromModel(t)))return[];var s=o.m2p.asCodeActionParams(t,n,i);return r.provideCodeActions(s,a).then(function(e){return o.p2m.asCommands(e)})}}},e.prototype.registerCodeLensProvider=function(e,r){for(var o=this.createCodeLensProvider(e,r),t=new disposable_1.DisposableCollection,n=0,i=getLanguages();n<i.length;n++){var a=i[n];t.push(monaco.languages.registerCodeLensProvider(a,o))}return t},e.prototype.createCodeLensProvider=function(e,r){var o=this;return{provideCodeLenses:function(t,n){if(!o.matchModel(e,MonacoModelIdentifier.fromModel(t)))return[];var i=o.m2p.asCodeLensParams(t);return r.provideCodeLenses(i,n).then(function(e){return o.p2m.asCodeLenses(e)})},resolveCodeLens:r.resolveCodeLens?function(t,n,i){if(!o.matchModel(e,MonacoModelIdentifier.fromModel(t)))return n;var a=o.m2p.asCodeLens(n);return r.resolveCodeLens(a,i).then(function(e){return o.p2m.asCodeLens(e)})}:function(e,r,o){return r}}},e.prototype.registerDocumentFormattingEditProvider=function(e,r){for(var o=this.createDocumentFormattingEditProvider(e,r),t=new disposable_1.DisposableCollection,n=0,i=getLanguages();n<i.length;n++){var a=i[n];t.push(monaco.languages.registerDocumentFormattingEditProvider(a,o))}return t},e.prototype.createDocumentFormattingEditProvider=function(e,r){var o=this;return{provideDocumentFormattingEdits:function(t,n,i){if(!o.matchModel(e,MonacoModelIdentifier.fromModel(t)))return[];var a=o.m2p.asDocumentFormattingParams(t,n);return r.provideDocumentFormattingEdits(a,i).then(function(e){return o.p2m.asTextEdits(e)})}}},e.prototype.registerDocumentRangeFormattingEditProvider=function(e,r){for(var o=this.createDocumentRangeFormattingEditProvider(e,r),t=new disposable_1.DisposableCollection,n=0,i=getLanguages();n<i.length;n++){var a=i[n];t.push(monaco.languages.registerDocumentRangeFormattingEditProvider(a,o))}return t},e.prototype.createDocumentRangeFormattingEditProvider=function(e,r){var o=this;return{provideDocumentRangeFormattingEdits:function(t,n,i,a){if(!o.matchModel(e,MonacoModelIdentifier.fromModel(t)))return[];var s=o.m2p.asDocumentRangeFormattingParams(t,n,i);return r.provideDocumentRangeFormattingEdits(s,a).then(function(e){return o.p2m.asTextEdits(e)})}}},e.prototype.registerOnTypeFormattingEditProvider=function(e,r,o){for(var t=[],n=3;n<arguments.length;n++)t[n-3]=arguments[n];for(var i=this.createOnTypeFormattingEditProvider.apply(this,[e,r,o].concat(t)),a=new disposable_1.DisposableCollection,s=0,u=getLanguages();s<u.length;s++){var c=u[s];a.push(monaco.languages.registerOnTypeFormattingEditProvider(c,i))}return a},e.prototype.createOnTypeFormattingEditProvider=function(e,r,o){for(var t=this,n=[],i=3;i<arguments.length;i++)n[i-3]=arguments[i];return{autoFormatTriggerCharacters:[o].concat(n),provideOnTypeFormattingEdits:function(o,n,i,a,s){if(!t.matchModel(e,MonacoModelIdentifier.fromModel(o)))return[];var u=t.m2p.asDocumentOnTypeFormattingParams(o,n,i,a);return r.provideOnTypeFormattingEdits(u,s).then(function(e){return t.p2m.asTextEdits(e)})}}},e.prototype.registerRenameProvider=function(e,r){for(var o=this.createRenameProvider(e,r),t=new disposable_1.DisposableCollection,n=0,i=getLanguages();n<i.length;n++){var a=i[n];t.push(monaco.languages.registerRenameProvider(a,o))}return t},e.prototype.createRenameProvider=function(e,r){var o=this;return{provideRenameEdits:function(t,n,i,a){if(o.matchModel(e,MonacoModelIdentifier.fromModel(t))){var s=o.m2p.asRenameParams(t,n,i);return r.provideRenameEdits(s,a).then(function(e){return o.p2m.asWorkspaceEdit(e)})}}}},e.prototype.registerDocumentLinkProvider=function(e,r){for(var o=this.createDocumentLinkProvider(e,r),t=new disposable_1.DisposableCollection,n=0,i=getLanguages();n<i.length;n++){var a=i[n];t.push(monaco.languages.registerLinkProvider(a,o))}return t},e.prototype.createDocumentLinkProvider=function(e,r){var o=this;return{provideLinks:function(t,n){if(o.matchModel(e,MonacoModelIdentifier.fromModel(t))){var i=o.m2p.asDocumentLinkParams(t);return r.provideDocumentLinks(i,n).then(function(e){return o.p2m.asILinks(e)})}},resolveLink:function(e,t){if(r.resolveDocumentLink&&(null===e.url||void 0===e.url)){var n=o.m2p.asDocumentLink(e);return r.resolveDocumentLink(n,t).then(function(e){return o.p2m.asILink(e)})}return e}}},e.prototype.matchModel=function(e,r){var o=this;return Array.isArray(e)?-1!==e.findIndex(function(e){return o.matchModel(e,r)}):services_1.DocumentFilter.is(e)?(!e.language||e.language===r.languageId)&&((!e.scheme||e.scheme===r.uri.scheme)&&!(e.pattern&&!testGlob(e.pattern,r.uri.path))):e===r.languageId},e}();exports.MonacoLanguages=MonacoLanguages;

/***/ }),
/* 218 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
Object.defineProperty(exports,"__esModule",{value:!0});var services_1=__webpack_require__(211),MonacoWorkspace=function(){function e(e,t,n){void 0===n&&(n=null);var o=this;this.p2m=e,this.m2p=t,this._rootUri=n,this.capabilities={applyEdit:!0,workspaceEdit:{documentChanges:!0}},this.documents=new Map,this.onDidOpenTextDocumentEmitter=new services_1.Emitter,this.onDidCloseTextDocumentEmitter=new services_1.Emitter,this.onDidChangeTextDocumentEmitter=new services_1.Emitter;for(var r=0,i=monaco.editor.getModels();r<i.length;r++){var s=i[r];this.addModel(s)}monaco.editor.onDidCreateModel(function(e){return o.addModel(e)}),monaco.editor.onWillDisposeModel(function(e){return o.removeModel(e)})}return Object.defineProperty(e.prototype,"rootUri",{get:function(){return this._rootUri},enumerable:!0,configurable:!0}),e.prototype.removeModel=function(e){var t=e.uri.toString(),n=this.documents.get(t);n&&(this.documents.delete(t),this.onDidCloseTextDocumentEmitter.fire(n))},e.prototype.addModel=function(e){var t=this,n=e.uri.toString(),o=this.setModel(n,e);this.onDidOpenTextDocumentEmitter.fire(o),e.onDidChangeContent(function(o){return t.onDidChangeContent(n,e,o)})},e.prototype.onDidChangeContent=function(e,t,n){for(var o=this.setModel(e,t),r=[],i=0,s=n.changes;i<s.length;i++){var c=s[i],u=this.m2p.asRange(c.range),a=c.rangeLength,d=c.text;r.push({range:u,rangeLength:a,text:d})}this.onDidChangeTextDocumentEmitter.fire({textDocument:o,contentChanges:r})},e.prototype.setModel=function(e,t){var n=services_1.TextDocument.create(e,t.getModeId(),t.getVersionId(),t.getValue());return this.documents.set(e,n),n},Object.defineProperty(e.prototype,"textDocuments",{get:function(){return Array.from(this.documents.values())},enumerable:!0,configurable:!0}),Object.defineProperty(e.prototype,"onDidOpenTextDocument",{get:function(){return this.onDidOpenTextDocumentEmitter.event},enumerable:!0,configurable:!0}),Object.defineProperty(e.prototype,"onDidCloseTextDocument",{get:function(){return this.onDidCloseTextDocumentEmitter.event},enumerable:!0,configurable:!0}),Object.defineProperty(e.prototype,"onDidChangeTextDocument",{get:function(){return this.onDidChangeTextDocumentEmitter.event},enumerable:!0,configurable:!0}),e.prototype.applyEdit=function(e){var t=this.p2m.asWorkspaceEdit(e),n=t.edits.reduce(function(e,t){return e[t.resource.toString()]=monaco.editor.getModel(t.resource),e},{});if(!Object.keys(n).map(function(e){return n[e]}).every(function(e){return!!e}))return Promise.resolve(!1);var o=t.edits.reduce(function(e,t){var n=t.resource.toString();return n in e||(e[n]=[]),e[n].push(t),e},{});return Object.keys(o).forEach(function(e){n[e].pushEditOperations([],o[e].map(function(e){return{identifier:{major:1,minor:0},range:monaco.Range.lift(e.range),text:e.newText,forceMoveMarkers:!0}}),function(){return[]})}),Promise.resolve(!0)},e}();exports.MonacoWorkspace=MonacoWorkspace;

/***/ }),
/* 219 */
/***/ (function(module, exports) {



/***/ }),
/* 220 */,
/* 221 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
function isRequestMessage(e){var t=e;return t&&is.string(t.method)&&(is.string(t.id)||is.number(t.id))}function isNotificationMessage(e){var t=e;return t&&is.string(t.method)&&void 0===e.id}function isResponseMessage(e){var t=e;return t&&(void 0!==t.result||!!t.error)&&(is.string(t.id)||is.number(t.id)||null===t.id)}var __extends=this&&this.__extends||function(){var e=Object.setPrototypeOf||{__proto__:[]}instanceof Array&&function(e,t){e.__proto__=t}||function(e,t){for(var r in t)t.hasOwnProperty(r)&&(e[r]=t[r])};return function(t,r){function s(){this.constructor=t}e(t,r),t.prototype=null===r?Object.create(r):(s.prototype=r.prototype,new s)}}();Object.defineProperty(exports,"__esModule",{value:!0});var is=__webpack_require__(189),ErrorCodes;!function(e){e.ParseError=-32700,e.InvalidRequest=-32600,e.MethodNotFound=-32601,e.InvalidParams=-32602,e.InternalError=-32603,e.serverErrorStart=-32099,e.serverErrorEnd=-32e3,e.ServerNotInitialized=-32002,e.UnknownErrorCode=-32001,e.RequestCancelled=-32800,e.MessageWriteError=1,e.MessageReadError=2}(ErrorCodes=exports.ErrorCodes||(exports.ErrorCodes={}));var ResponseError=function(e){function t(r,s,i){var n=e.call(this,s)||this;return n.code=is.number(r)?r:ErrorCodes.UnknownErrorCode,void 0!==i&&(n.data=i),Object.setPrototypeOf(n,t.prototype),n}return __extends(t,e),t.prototype.toJson=function(){var e={code:this.code,message:this.message};return void 0!==this.data&&(e.data=this.data),e},t}(Error);exports.ResponseError=ResponseError;var AbstractMessageType=function(){function e(e,t){this._method=e,this._numberOfParams=t}return Object.defineProperty(e.prototype,"method",{get:function(){return this._method},enumerable:!0,configurable:!0}),Object.defineProperty(e.prototype,"numberOfParams",{get:function(){return this._numberOfParams},enumerable:!0,configurable:!0}),e}();exports.AbstractMessageType=AbstractMessageType;var RequestType0=function(e){function t(t){var r=e.call(this,t,0)||this;return r._=void 0,r}return __extends(t,e),t}(AbstractMessageType);exports.RequestType0=RequestType0;var RequestType=function(e){function t(t){var r=e.call(this,t,1)||this;return r._=void 0,r}return __extends(t,e),t}(AbstractMessageType);exports.RequestType=RequestType;var RequestType1=function(e){function t(t){var r=e.call(this,t,1)||this;return r._=void 0,r}return __extends(t,e),t}(AbstractMessageType);exports.RequestType1=RequestType1;var RequestType2=function(e){function t(t){var r=e.call(this,t,2)||this;return r._=void 0,r}return __extends(t,e),t}(AbstractMessageType);exports.RequestType2=RequestType2;var RequestType3=function(e){function t(t){var r=e.call(this,t,3)||this;return r._=void 0,r}return __extends(t,e),t}(AbstractMessageType);exports.RequestType3=RequestType3;var RequestType4=function(e){function t(t){var r=e.call(this,t,4)||this;return r._=void 0,r}return __extends(t,e),t}(AbstractMessageType);exports.RequestType4=RequestType4;var RequestType5=function(e){function t(t){var r=e.call(this,t,5)||this;return r._=void 0,r}return __extends(t,e),t}(AbstractMessageType);exports.RequestType5=RequestType5;var RequestType6=function(e){function t(t){var r=e.call(this,t,6)||this;return r._=void 0,r}return __extends(t,e),t}(AbstractMessageType);exports.RequestType6=RequestType6;var RequestType7=function(e){function t(t){var r=e.call(this,t,7)||this;return r._=void 0,r}return __extends(t,e),t}(AbstractMessageType);exports.RequestType7=RequestType7;var RequestType8=function(e){function t(t){var r=e.call(this,t,8)||this;return r._=void 0,r}return __extends(t,e),t}(AbstractMessageType);exports.RequestType8=RequestType8;var RequestType9=function(e){function t(t){var r=e.call(this,t,9)||this;return r._=void 0,r}return __extends(t,e),t}(AbstractMessageType);exports.RequestType9=RequestType9;var NotificationType=function(e){function t(t){var r=e.call(this,t,1)||this;return r._=void 0,r}return __extends(t,e),t}(AbstractMessageType);exports.NotificationType=NotificationType;var NotificationType0=function(e){function t(t){var r=e.call(this,t,0)||this;return r._=void 0,r}return __extends(t,e),t}(AbstractMessageType);exports.NotificationType0=NotificationType0;var NotificationType1=function(e){function t(t){var r=e.call(this,t,1)||this;return r._=void 0,r}return __extends(t,e),t}(AbstractMessageType);exports.NotificationType1=NotificationType1;var NotificationType2=function(e){function t(t){var r=e.call(this,t,2)||this;return r._=void 0,r}return __extends(t,e),t}(AbstractMessageType);exports.NotificationType2=NotificationType2;var NotificationType3=function(e){function t(t){var r=e.call(this,t,3)||this;return r._=void 0,r}return __extends(t,e),t}(AbstractMessageType);exports.NotificationType3=NotificationType3;var NotificationType4=function(e){function t(t){var r=e.call(this,t,4)||this;return r._=void 0,r}return __extends(t,e),t}(AbstractMessageType);exports.NotificationType4=NotificationType4;var NotificationType5=function(e){function t(t){var r=e.call(this,t,5)||this;return r._=void 0,r}return __extends(t,e),t}(AbstractMessageType);exports.NotificationType5=NotificationType5;var NotificationType6=function(e){function t(t){var r=e.call(this,t,6)||this;return r._=void 0,r}return __extends(t,e),t}(AbstractMessageType);exports.NotificationType6=NotificationType6;var NotificationType7=function(e){function t(t){var r=e.call(this,t,7)||this;return r._=void 0,r}return __extends(t,e),t}(AbstractMessageType);exports.NotificationType7=NotificationType7;var NotificationType8=function(e){function t(t){var r=e.call(this,t,8)||this;return r._=void 0,r}return __extends(t,e),t}(AbstractMessageType);exports.NotificationType8=NotificationType8;var NotificationType9=function(e){function t(t){var r=e.call(this,t,9)||this;return r._=void 0,r}return __extends(t,e),t}(AbstractMessageType);exports.NotificationType9=NotificationType9,exports.isRequestMessage=isRequestMessage,exports.isNotificationMessage=isNotificationMessage,exports.isResponseMessage=isResponseMessage;

/***/ }),
/* 222 */
/***/ (function(module, exports) {

!function(e){if("object"==typeof module&&"object"==typeof module.exports){var t=e(require,exports);void 0!==t&&(module.exports=t)}else"function"==typeof define&&define.amd&&define(["require","exports"],e)}(function(e,t){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var n;!function(e){function t(e,t){return{line:e,character:t}}function n(e){var t=e;return d.defined(t)&&d.number(t.line)&&d.number(t.character)}e.create=t,e.is=n}(n=t.Position||(t.Position={}));var r;!function(e){function t(e,t,r,i){if(d.number(e)&&d.number(t)&&d.number(r)&&d.number(i))return{start:n.create(e,t),end:n.create(r,i)};if(n.is(e)&&n.is(t))return{start:e,end:t};throw new Error("Range#create called with invalid arguments["+e+", "+t+", "+r+", "+i+"]")}function r(e){var t=e;return d.defined(t)&&n.is(t.start)&&n.is(t.end)}e.create=t,e.is=r}(r=t.Range||(t.Range={}));!function(e){function t(e,t){return{uri:e,range:t}}function n(e){var t=e;return d.defined(t)&&r.is(t.range)&&(d.string(t.uri)||d.undefined(t.uri))}e.create=t,e.is=n}(t.Location||(t.Location={}));!function(e){e.Error=1,e.Warning=2,e.Information=3,e.Hint=4}(t.DiagnosticSeverity||(t.DiagnosticSeverity={}));var i;!function(e){function t(e,t,n,r,i){var o={range:e,message:t};return d.defined(n)&&(o.severity=n),d.defined(r)&&(o.code=r),d.defined(i)&&(o.source=i),o}function n(e){var t=e;return d.defined(t)&&r.is(t.range)&&d.string(t.message)&&(d.number(t.severity)||d.undefined(t.severity))&&(d.number(t.code)||d.string(t.code)||d.undefined(t.code))&&(d.string(t.source)||d.undefined(t.source))}e.create=t,e.is=n}(i=t.Diagnostic||(t.Diagnostic={}));var o;!function(e){function t(e,t){for(var n=[],r=2;r<arguments.length;r++)n[r-2]=arguments[r];var i={title:e,command:t};return d.defined(n)&&n.length>0&&(i.arguments=n),i}function n(e){var t=e;return d.defined(t)&&d.string(t.title)&&d.string(t.title)}e.create=t,e.is=n}(o=t.Command||(t.Command={}));var u;!function(e){function t(e,t){return{range:e,newText:t}}function n(e,t){return{range:{start:e,end:e},newText:t}}function r(e){return{range:e,newText:""}}e.replace=t,e.insert=n,e.del=r}(u=t.TextEdit||(t.TextEdit={}));!function(e){function t(e,t){return{textDocument:e,edits:t}}function n(e){var t=e;return d.defined(t)&&s.is(t.textDocument)&&Array.isArray(t.edits)}e.create=t,e.is=n}(t.TextDocumentEdit||(t.TextDocumentEdit={}));var a=function(){function e(e){this.edits=e}return e.prototype.insert=function(e,t){this.edits.push(u.insert(e,t))},e.prototype.replace=function(e,t){this.edits.push(u.replace(e,t))},e.prototype.delete=function(e){this.edits.push(u.del(e))},e.prototype.add=function(e){this.edits.push(e)},e.prototype.all=function(){return this.edits},e.prototype.clear=function(){this.edits.splice(0,this.edits.length)},e}(),c=function(){function e(e){var t=this;this._textEditChanges=Object.create(null),e&&(this._workspaceEdit=e,e.documentChanges?e.documentChanges.forEach(function(e){var n=new a(e.edits);t._textEditChanges[e.textDocument.uri]=n}):e.changes&&Object.keys(e.changes).forEach(function(n){var r=new a(e.changes[n]);t._textEditChanges[n]=r}))}return Object.defineProperty(e.prototype,"edit",{get:function(){return this._workspaceEdit},enumerable:!0,configurable:!0}),e.prototype.getTextEditChange=function(e){if(s.is(e)){if(this._workspaceEdit||(this._workspaceEdit={documentChanges:[]}),!this._workspaceEdit.documentChanges)throw new Error("Workspace edit is not configured for versioned document changes.");var t=e,n=this._textEditChanges[t.uri];if(!n){var r=[],i={textDocument:t,edits:r};this._workspaceEdit.documentChanges.push(i),n=new a(r),this._textEditChanges[t.uri]=n}return n}if(this._workspaceEdit||(this._workspaceEdit={changes:Object.create(null)}),!this._workspaceEdit.changes)throw new Error("Workspace edit is not configured for normal text edit changes.");var n=this._textEditChanges[e];if(!n){var r=[];this._workspaceEdit.changes[e]=r,n=new a(r),this._textEditChanges[e]=n}return n},e}();t.WorkspaceChange=c;!function(e){function t(e){return{uri:e}}function n(e){var t=e;return d.defined(t)&&d.string(t.uri)}e.create=t,e.is=n}(t.TextDocumentIdentifier||(t.TextDocumentIdentifier={}));var s;!function(e){function t(e,t){return{uri:e,version:t}}function n(e){var t=e;return d.defined(t)&&d.string(t.uri)&&d.number(t.version)}e.create=t,e.is=n}(s=t.VersionedTextDocumentIdentifier||(t.VersionedTextDocumentIdentifier={}));!function(e){function t(e,t,n,r){return{uri:e,languageId:t,version:n,text:r}}function n(e){var t=e;return d.defined(t)&&d.string(t.uri)&&d.string(t.languageId)&&d.number(t.version)&&d.string(t.text)}e.create=t,e.is=n}(t.TextDocumentItem||(t.TextDocumentItem={}));!function(e){e.Text=1,e.Method=2,e.Function=3,e.Constructor=4,e.Field=5,e.Variable=6,e.Class=7,e.Interface=8,e.Module=9,e.Property=10,e.Unit=11,e.Value=12,e.Enum=13,e.Keyword=14,e.Snippet=15,e.Color=16,e.File=17,e.Reference=18}(t.CompletionItemKind||(t.CompletionItemKind={}));!function(e){e.PlainText=1,e.Snippet=2}(t.InsertTextFormat||(t.InsertTextFormat={}));!function(e){function t(e){return{label:e}}e.create=t}(t.CompletionItem||(t.CompletionItem={}));!function(e){function t(e,t){return{items:e||[],isIncomplete:!!t}}e.create=t}(t.CompletionList||(t.CompletionList={}));!function(e){function t(e){return e.replace(/[\\`*_{}[\]()#+\-.!]/g,"\\$&")}e.fromPlainText=t}(t.MarkedString||(t.MarkedString={}));!function(e){function t(e,t){return t?{label:e,documentation:t}:{label:e}}e.create=t}(t.ParameterInformation||(t.ParameterInformation={}));!function(e){function t(e,t){for(var n=[],r=2;r<arguments.length;r++)n[r-2]=arguments[r];var i={label:e};return d.defined(t)&&(i.documentation=t),d.defined(n)?i.parameters=n:i.parameters=[],i}e.create=t}(t.SignatureInformation||(t.SignatureInformation={}));!function(e){e.Text=1,e.Read=2,e.Write=3}(t.DocumentHighlightKind||(t.DocumentHighlightKind={}));!function(e){function t(e,t){var n={range:e};return d.number(t)&&(n.kind=t),n}e.create=t}(t.DocumentHighlight||(t.DocumentHighlight={}));!function(e){e.File=1,e.Module=2,e.Namespace=3,e.Package=4,e.Class=5,e.Method=6,e.Property=7,e.Field=8,e.Constructor=9,e.Enum=10,e.Interface=11,e.Function=12,e.Variable=13,e.Constant=14,e.String=15,e.Number=16,e.Boolean=17,e.Array=18}(t.SymbolKind||(t.SymbolKind={}));!function(e){function t(e,t,n,r,i){var o={name:e,kind:t,location:{uri:r,range:n}};return i&&(o.containerName=i),o}e.create=t}(t.SymbolInformation||(t.SymbolInformation={}));!function(e){function t(e){return{diagnostics:e}}function n(e){var t=e;return d.defined(t)&&d.typedArray(t.diagnostics,i.is)}e.create=t,e.is=n}(t.CodeActionContext||(t.CodeActionContext={}));!function(e){function t(e,t){var n={range:e};return d.defined(t)&&(n.data=t),n}function n(e){var t=e;return d.defined(t)&&r.is(t.range)&&(d.undefined(t.command)||o.is(t.command))}e.create=t,e.is=n}(t.CodeLens||(t.CodeLens={}));!function(e){function t(e,t){return{tabSize:e,insertSpaces:t}}function n(e){var t=e;return d.defined(t)&&d.number(t.tabSize)&&d.boolean(t.insertSpaces)}e.create=t,e.is=n}(t.FormattingOptions||(t.FormattingOptions={}));var f=function(){function e(){}return e}();t.DocumentLink=f,function(e){function t(e,t){return{range:e,target:t}}function n(e){var t=e;return d.defined(t)&&r.is(t.range)&&(d.undefined(t.target)||d.string(t.target))}e.create=t,e.is=n}(f=t.DocumentLink||(t.DocumentLink={})),t.DocumentLink=f,t.EOL=["\n","\r\n","\r"];!function(e){function t(e,t,n,r){return new g(e,t,n,r)}function n(e){var t=e;return!!(d.defined(t)&&d.string(t.uri)&&(d.undefined(t.languageId)||d.string(t.languageId))&&d.number(t.lineCount)&&d.func(t.getText)&&d.func(t.positionAt)&&d.func(t.offsetAt))}e.create=t,e.is=n}(t.TextDocument||(t.TextDocument={}));!function(e){e.Manual=1,e.AfterDelay=2,e.FocusOut=3}(t.TextDocumentSaveReason||(t.TextDocumentSaveReason={}));var d,g=function(){function e(e,t,n,r){this._uri=e,this._languageId=t,this._version=n,this._content=r,this._lineOffsets=null}return Object.defineProperty(e.prototype,"uri",{get:function(){return this._uri},enumerable:!0,configurable:!0}),Object.defineProperty(e.prototype,"languageId",{get:function(){return this._languageId},enumerable:!0,configurable:!0}),Object.defineProperty(e.prototype,"version",{get:function(){return this._version},enumerable:!0,configurable:!0}),e.prototype.getText=function(){return this._content},e.prototype.update=function(e,t){this._content=e.text,this._version=t,this._lineOffsets=null},e.prototype.getLineOffsets=function(){if(null===this._lineOffsets){for(var e=[],t=this._content,n=!0,r=0;r<t.length;r++){n&&(e.push(r),n=!1);var i=t.charAt(r);n="\r"===i||"\n"===i,"\r"===i&&r+1<t.length&&"\n"===t.charAt(r+1)&&r++}n&&t.length>0&&e.push(t.length),this._lineOffsets=e}return this._lineOffsets},e.prototype.positionAt=function(e){e=Math.max(Math.min(e,this._content.length),0);var t=this.getLineOffsets(),r=0,i=t.length;if(0===i)return n.create(0,e);for(;r<i;){var o=Math.floor((r+i)/2);t[o]>e?i=o:r=o+1}var u=r-1;return n.create(u,e-t[u])},e.prototype.offsetAt=function(e){var t=this.getLineOffsets();if(e.line>=t.length)return this._content.length;if(e.line<0)return 0;var n=t[e.line],r=e.line+1<t.length?t[e.line+1]:this._content.length;return Math.max(Math.min(n+e.character,r),n)},Object.defineProperty(e.prototype,"lineCount",{get:function(){return this.getLineOffsets().length},enumerable:!0,configurable:!0}),e}();!function(e){function t(e){return void 0!==e}function n(e){return void 0===e}function r(e){return!0===e||!1===e}function i(e){return"[object String]"===c.call(e)}function o(e){return"[object Number]"===c.call(e)}function u(e){return"[object Function]"===c.call(e)}function a(e,t){return Array.isArray(e)&&e.every(t)}var c=Object.prototype.toString;e.defined=t,e.undefined=n,e.boolean=r,e.string=i,e.number=o,e.func=u,e.typedArray=a}(d||(d={}))});

/***/ }),
/* 223 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
Object.defineProperty(exports,"__esModule",{value:!0});var ConsoleLogger=function(){function o(){}return o.prototype.error=function(o){console.error(o)},o.prototype.warn=function(o){console.warn(o)},o.prototype.info=function(o){console.info(o)},o.prototype.log=function(o){console.log(o)},o.prototype.debug=function(o){console.debug(o)},o}();exports.ConsoleLogger=ConsoleLogger;

/***/ }),
/* 224 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
function __export(e){for(var r in e)exports.hasOwnProperty(r)||(exports[r]=e[r])}Object.defineProperty(exports,"__esModule",{value:!0}),__export(__webpack_require__(225)),__export(__webpack_require__(226)),__export(__webpack_require__(266));

/***/ }),
/* 225 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
var __extends=this&&this.__extends||function(){var e=Object.setPrototypeOf||{__proto__:[]}instanceof Array&&function(e,t){e.__proto__=t}||function(e,t){for(var s in t)t.hasOwnProperty(s)&&(e[s]=t[s])};return function(t,s){function r(){this.constructor=t}e(t,s),t.prototype=null===s?Object.create(s):(r.prototype=s.prototype,new r)}}();Object.defineProperty(exports,"__esModule",{value:!0});var stream_1=__webpack_require__(209),WebSocketMessageReader=function(e){function t(t){var s=e.call(this)||this;return s.socket=t,s.state="initial",s.events=[],s.socket.onMessage(function(e){return s.readMessage(e)}),s.socket.onError(function(e){return s.fireError(e)}),s.socket.onClose(function(e,t){if(1e3!==e){var r={name:""+e,message:"Error during socket reconnect: code = "+e+", reason = "+t};s.fireError(r)}s.fireClose()}),s}return __extends(t,e),t.prototype.listen=function(t){if("initial"===this.state){if(0!==this.events.length){var s=this.events.pop();s.message?e.prototype.readMessage.call(this,s.message,t):s.error?this.fireError(s.error):this.fireClose()}this.callback=t,this.state="listening"}},t.prototype.readMessage=function(t){"initial"===this.state?this.events.splice(0,0,{message:t}):"listening"===this.state&&e.prototype.readMessage.call(this,t,this.callback)},t.prototype.fireError=function(t){"initial"===this.state?this.events.splice(0,0,{error:t}):"listening"===this.state&&e.prototype.fireError.call(this,t)},t.prototype.fireClose=function(){"initial"===this.state?this.events.splice(0,0,{}):"listening"===this.state&&e.prototype.fireClose.call(this),this.state="closed"},t}(stream_1.AbstractStreamMessageReader);exports.WebSocketMessageReader=WebSocketMessageReader;

/***/ }),
/* 226 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
var __extends=this&&this.__extends||function(){var t=Object.setPrototypeOf||{__proto__:[]}instanceof Array&&function(t,e){t.__proto__=e}||function(t,e){for(var r in e)e.hasOwnProperty(r)&&(t[r]=e[r])};return function(e,r){function o(){this.constructor=e}t(e,r),e.prototype=null===r?Object.create(r):(o.prototype=r.prototype,new o)}}();Object.defineProperty(exports,"__esModule",{value:!0});var stream_1=__webpack_require__(209),WebSocketMessageWriter=function(t){function e(e){var r=t.call(this)||this;return r.socket=e,r}return __extends(e,t),e.prototype.send=function(t){try{this.socket.send(t)}catch(t){this.fireError(t)}},e}(stream_1.AbstractStreamMessageWriter);exports.WebSocketMessageWriter=WebSocketMessageWriter;

/***/ }),
/* 227 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
/* WEBPACK VAR INJECTION */(function(process) {/* --------------------------------------------------------------------------------------------
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for license information.
 * ------------------------------------------------------------------------------------------ */

function __export(m) {
    for (var p in m) if (!exports.hasOwnProperty(p)) exports[p] = m[p];
}
Object.defineProperty(exports, "__esModule", { value: true });
const vscode_jsonrpc_1 = __webpack_require__(190);
exports.ErrorCodes = vscode_jsonrpc_1.ErrorCodes;
exports.ResponseError = vscode_jsonrpc_1.ResponseError;
exports.RequestType = vscode_jsonrpc_1.RequestType;
exports.RequestType0 = vscode_jsonrpc_1.RequestType0;
exports.NotificationType = vscode_jsonrpc_1.NotificationType;
exports.NotificationType0 = vscode_jsonrpc_1.NotificationType0;
const protocol_1 = __webpack_require__(191);
exports.InitializeError = protocol_1.InitializeError;
const is = __webpack_require__(194);
const async_1 = __webpack_require__(272);
const UUID = __webpack_require__(273);
__export(__webpack_require__(222));
__export(__webpack_require__(191));
/**
 * An action to be performed when the connection is producing errors.
 */
var ErrorAction;
(function (ErrorAction) {
    /**
     * Continue running the server.
     */
    ErrorAction[ErrorAction["Continue"] = 1] = "Continue";
    /**
     * Shutdown the server.
     */
    ErrorAction[ErrorAction["Shutdown"] = 2] = "Shutdown";
})(ErrorAction = exports.ErrorAction || (exports.ErrorAction = {}));
/**
 * An action to be performed when the connection to a server got closed.
 */
var CloseAction;
(function (CloseAction) {
    /**
     * Don't restart the server. The connection stays closed.
     */
    CloseAction[CloseAction["DoNotRestart"] = 1] = "DoNotRestart";
    /**
     * Restart the server.
     */
    CloseAction[CloseAction["Restart"] = 2] = "Restart";
})(CloseAction = exports.CloseAction || (exports.CloseAction = {}));
class DefaultErrorHandler {
    constructor(name, client) {
        this.name = name;
        this.client = client;
        this.restarts = [];
    }
    error(_error, _message, count) {
        if (count && count <= 3) {
            return ErrorAction.Continue;
        }
        return ErrorAction.Shutdown;
    }
    closed() {
        this.restarts.push(Date.now());
        if (this.restarts.length < 5) {
            return CloseAction.Restart;
        }
        else {
            let diff = this.restarts[this.restarts.length - 1] - this.restarts[0];
            if (diff <= 3 * 60 * 1000) {
                if (this.client.window) {
                    this.client.window.showMessage(protocol_1.MessageType.Error, `The ${this.name} server crashed 5 times in the last 3 minutes. The server will not be restarted.`);
                }
                return CloseAction.DoNotRestart;
            }
            else {
                this.restarts.shift();
                return CloseAction.Restart;
            }
        }
    }
}
var RevealOutputChannelOn;
(function (RevealOutputChannelOn) {
    RevealOutputChannelOn[RevealOutputChannelOn["Info"] = 1] = "Info";
    RevealOutputChannelOn[RevealOutputChannelOn["Warn"] = 2] = "Warn";
    RevealOutputChannelOn[RevealOutputChannelOn["Error"] = 3] = "Error";
    RevealOutputChannelOn[RevealOutputChannelOn["Never"] = 4] = "Never";
})(RevealOutputChannelOn = exports.RevealOutputChannelOn || (exports.RevealOutputChannelOn = {}));
var State;
(function (State) {
    State[State["Stopped"] = 1] = "Stopped";
    State[State["Running"] = 2] = "Running";
})(State = exports.State || (exports.State = {}));
var ClientState;
(function (ClientState) {
    ClientState[ClientState["Initial"] = 0] = "Initial";
    ClientState[ClientState["Starting"] = 1] = "Starting";
    ClientState[ClientState["StartFailed"] = 2] = "StartFailed";
    ClientState[ClientState["Running"] = 3] = "Running";
    ClientState[ClientState["Stopping"] = 4] = "Stopping";
    ClientState[ClientState["Stopped"] = 5] = "Stopped";
})(ClientState || (ClientState = {}));
class DocumentNotifiactions {
    constructor(_client, _event, _type, _createParams, _selectorFilter) {
        this._client = _client;
        this._event = _event;
        this._type = _type;
        this._createParams = _createParams;
        this._selectorFilter = _selectorFilter;
        this._selectors = new Map();
    }
    static textDocumentFilter(languages, selectors, textDocument) {
        for (const selector of selectors) {
            if (languages.match(selector, textDocument)) {
                return true;
            }
        }
        return false;
    }
    register(data) {
        if (!data.registerOptions.documentSelector) {
            return;
        }
        if (!this._listener) {
            this._listener = this._event(this.callback, this);
        }
        this._selectors.set(data.id, data.registerOptions.documentSelector);
    }
    callback(data) {
        if (!this._selectorFilter || this._selectorFilter(this._selectors.values(), data)) {
            this._client.sendNotification(this._type, this._createParams(data));
            this.notificationSent(data);
        }
    }
    notificationSent(_data) {
    }
    unregister(id) {
        this._selectors.delete(id);
        if (this._selectors.size === 0 && this._listener) {
            this._listener.dispose();
            this._listener = undefined;
        }
    }
    dispose() {
        if (this._listener) {
            this._listener.dispose();
        }
    }
}
class DidOpenTextDocumentFeature extends DocumentNotifiactions {
    constructor(client, _syncedDocuments) {
        super(client, client.workspace.onDidOpenTextDocument, protocol_1.DidOpenTextDocumentNotification.type, (textDocument) => {
            const { uri, languageId, version } = textDocument;
            const text = textDocument.getText();
            return {
                textDocument: {
                    uri, languageId, version, text
                }
            };
        }, (selectors, data) => DocumentNotifiactions.textDocumentFilter(client.languages, selectors, data));
        this._syncedDocuments = _syncedDocuments;
    }
    register(data) {
        super.register(data);
        if (!data.registerOptions.documentSelector) {
            return;
        }
        let documentSelector = data.registerOptions.documentSelector;
        this._client.workspace.textDocuments.forEach((textDocument) => {
            let uri = textDocument.uri;
            if (!textDocument || this._syncedDocuments.has(uri)) {
                return;
            }
            if (this._client.languages.match(documentSelector, textDocument)) {
                this._client.sendNotification(this._type, this._createParams(textDocument));
                this._syncedDocuments.set(uri, textDocument);
            }
        });
    }
    notificationSent(textDocument) {
        super.notificationSent(textDocument);
        this._syncedDocuments.set(textDocument.uri, textDocument);
    }
}
class DidCloseTextDocumentFeature extends DocumentNotifiactions {
    constructor(client, _syncedDocuments) {
        super(client, client.workspace.onDidCloseTextDocument, protocol_1.DidCloseTextDocumentNotification.type, (textDocument) => {
            return {
                textDocument: {
                    uri: textDocument.uri
                }
            };
        }, (selectors, data) => DocumentNotifiactions.textDocumentFilter(client.languages, selectors, data));
        this._syncedDocuments = _syncedDocuments;
    }
    notificationSent(textDocument) {
        super.notificationSent(textDocument);
        this._syncedDocuments.delete(textDocument.uri);
    }
    unregister(id) {
        let selector = this._selectors.get(id);
        super.unregister(id);
        let selectors = this._selectors.values();
        this._syncedDocuments.forEach((textDocument) => {
            if (this._client.languages.match(selector, textDocument) && !this._selectorFilter(selectors, textDocument)) {
                this._client.sendNotification(this._type, this._createParams(textDocument));
                this._syncedDocuments.delete(textDocument.uri);
            }
        });
    }
}
class DidChangeTextDocumentFeature {
    constructor(_client) {
        this._client = _client;
        this._changeData = new Map();
        this._forcingDelivery = false;
        this._workspace = _client.workspace;
        this._languages = _client.languages;
    }
    register(data) {
        if (!data.registerOptions.documentSelector) {
            return;
        }
        if (!this._listener) {
            this._listener = this._workspace.onDidChangeTextDocument(this.callback, this);
        }
        this._changeData.set(data.id, {
            documentSelector: data.registerOptions.documentSelector,
            syncKind: data.registerOptions.syncKind
        });
    }
    callback(event) {
        for (const changeData of this._changeData.values()) {
            if (this._languages.match(changeData.documentSelector, event.textDocument)) {
                if (changeData.syncKind === protocol_1.TextDocumentSyncKind.Incremental) {
                    this.sendDidChangeTextDocumentNotification(event.textDocument, event.contentChanges);
                }
                else if (changeData.syncKind === protocol_1.TextDocumentSyncKind.Full) {
                    if (this._changeDelayer) {
                        if (this._changeDelayer.uri !== event.textDocument.uri) {
                            // Use this force delivery to track boolean state. Otherwise we might call two times.
                            this.forceDelivery();
                            this._changeDelayer.uri = event.textDocument.uri;
                        }
                        this._changeDelayer.delayer.trigger(() => {
                            this.sendDidChangeTextDocumentNotification(event.textDocument);
                        });
                    }
                    else {
                        this._changeDelayer = {
                            uri: event.textDocument.uri,
                            delayer: new async_1.Delayer(200)
                        };
                        this._changeDelayer.delayer.trigger(() => {
                            this.sendDidChangeTextDocumentNotification(event.textDocument);
                        }, -1);
                    }
                }
            }
        }
    }
    sendDidChangeTextDocumentNotification(textDocument, contentChanges = [{ text: textDocument.getText() }]) {
        this._client.sendNotification(protocol_1.DidChangeTextDocumentNotification.type, {
            textDocument: {
                uri: textDocument.uri,
                version: textDocument.version
            },
            contentChanges
        });
    }
    unregister(id) {
        this._changeData.delete(id);
        if (this._changeData.size === 0 && this._listener) {
            this._listener.dispose();
            this._listener = undefined;
        }
    }
    dispose() {
        if (this._listener) {
            this._listener.dispose();
            this._listener = undefined;
        }
    }
    forceDelivery() {
        if (this._forcingDelivery || !this._changeDelayer) {
            return;
        }
        try {
            this._forcingDelivery = true;
            this._changeDelayer.delayer.forceDelivery();
        }
        finally {
            this._forcingDelivery = false;
        }
    }
}
class WillSaveWaitUntilFeature {
    constructor(_client) {
        this._client = _client;
        this._selectors = new Map();
        this.workspace = _client.workspace;
        this.languages = _client.languages;
    }
    register(data) {
        if (!data.registerOptions.documentSelector) {
            return;
        }
        if (!this._listener) {
            this._listener = this.workspace.onWillSaveTextDocument(this.callback, this);
        }
        this._selectors.set(data.id, data.registerOptions.documentSelector);
    }
    callback(event) {
        if (DocumentNotifiactions.textDocumentFilter(this.languages, this._selectors.values(), event.textDocument)) {
            event.waitUntil(this._client.sendRequest(protocol_1.WillSaveTextDocumentWaitUntilRequest.type, event));
        }
    }
    unregister(id) {
        this._selectors.delete(id);
        if (this._selectors.size === 0 && this._listener) {
            this._listener.dispose();
            this._listener = undefined;
        }
    }
    dispose() {
        if (this._listener) {
            this._listener.dispose();
            this._listener = undefined;
        }
    }
}
class DidSaveTextDocumentFeature extends DocumentNotifiactions {
    constructor(client) {
        super(client, client.workspace.onDidSaveTextDocument, protocol_1.DidSaveTextDocumentNotification.type, (textDocument) => {
            let result = {
                textDocument: {
                    uri: textDocument.uri,
                    version: textDocument.version
                }
            };
            if (this._includeText) {
                result.text = textDocument.getText();
            }
            return result;
        }, (selectors, data) => DocumentNotifiactions.textDocumentFilter(client.languages, selectors, data));
    }
    register(data) {
        this._includeText = !!data.registerOptions.includeText;
        super.register(data);
    }
}
class LanguageFeature {
    constructor(_createProvider) {
        this._createProvider = _createProvider;
        this._providers = new Map();
    }
    register(data) {
        if (!data.registerOptions.documentSelector) {
            return;
        }
        let provider = this._createProvider(data.registerOptions);
        if (provider) {
            this._providers.set(data.id, provider);
        }
    }
    unregister(id) {
        let provider = this._providers.get(id);
        if (provider) {
            provider.dispose();
        }
    }
    dispose() {
        this._providers.forEach((value) => {
            value.dispose();
        });
    }
}
class ExecuteCommandFeature {
    constructor(_client, _logger) {
        this._client = _client;
        this._logger = _logger;
        this._commands = new Map();
    }
    register(data) {
        if (data.registerOptions.commands) {
            let disposeables = [];
            for (const command of data.registerOptions.commands) {
                disposeables.push(this._client.commands.registerCommand(command, (...args) => {
                    let params = {
                        command,
                        arguments: args
                    };
                    this._client.sendRequest(protocol_1.ExecuteCommandRequest.type, params).then(undefined, (error) => { this._logger(protocol_1.ExecuteCommandRequest.type, error); });
                }));
            }
            this._commands.set(data.id, disposeables);
        }
    }
    unregister(id) {
        let disposeables = this._commands.get(id);
        if (disposeables) {
            disposeables.forEach(disposable => disposable.dispose());
        }
    }
    dispose() {
        this._commands.forEach((value) => {
            value.forEach(disposable => disposable.dispose());
        });
    }
}
class BaseLanguageClient {
    constructor(options) {
        this._registeredHandlers = new Map();
        this._name = options.name;
        this._id = options.id || options.name.toLowerCase();
        this.languages = options.services.languages;
        this.workspace = options.services.workspace;
        this.commands = options.services.commands;
        this.window = options.services.window;
        this.connectionProvider = options.connectionProvider;
        const clientOptions = options.clientOptions;
        this._clientOptions = Object.assign({}, clientOptions, { documentSelector: clientOptions.documentSelector || [], synchronize: clientOptions.synchronize || {}, outputChannelName: clientOptions.outputChannelName || this._name, revealOutputChannelOn: clientOptions.revealOutputChannelOn || RevealOutputChannelOn.Error, errorHandler: clientOptions.errorHandler || new DefaultErrorHandler(this._name, this) });
        this._clientOptions.synchronize = this._clientOptions.synchronize || {};
        this.state = ClientState.Initial;
        this._connectionPromise = undefined;
        this._resolvedConnection = undefined;
        this._outputChannel = undefined;
        this._listeners = undefined;
        this._providers = undefined;
        this._diagnostics = undefined;
        this._fileEvents = [];
        this._fileEventDelayer = new async_1.Delayer(250);
        this._onReady = new Promise((resolve, reject) => {
            this._onReadyCallbacks = { resolve, reject };
        });
        this._telemetryEmitter = new vscode_jsonrpc_1.Emitter();
        this._stateChangeEmitter = new vscode_jsonrpc_1.Emitter();
        this._tracer = {
            log: (message, data) => {
                this.logTrace(message, data);
            }
        };
    }
    get state() {
        return this._state;
    }
    set state(value) {
        let oldState = this.getPublicState();
        this._state = value;
        let newState = this.getPublicState();
        if (newState !== oldState) {
            this._stateChangeEmitter.fire({ oldState, newState });
        }
    }
    getPublicState() {
        if (this.state === ClientState.Running) {
            return State.Running;
        }
        else {
            return State.Stopped;
        }
    }
    sendRequest(type, ...params) {
        if (!this.isConnectionActive()) {
            throw new Error('Language client is not ready yet');
        }
        this.forceDocumentSync();
        try {
            return this._resolvedConnection.sendRequest(type, ...params);
        }
        catch (error) {
            this.error(`Sending request ${is.string(type) ? type : type.method} failed.`, error);
            throw error;
        }
    }
    onRequest(type, handler) {
        if (!this.isConnectionActive()) {
            throw new Error('Language client is not ready yet');
        }
        try {
            this._resolvedConnection.onRequest(type, handler);
        }
        catch (error) {
            this.error(`Registering request handler ${is.string(type) ? type : type.method} failed.`, error);
            throw error;
        }
    }
    sendNotification(type, params) {
        if (!this.isConnectionActive()) {
            throw new Error('Language client is not ready yet');
        }
        this.forceDocumentSync();
        try {
            this._resolvedConnection.sendNotification(type, params);
        }
        catch (error) {
            this.error(`Sending notification ${is.string(type) ? type : type.method} failed.`, error);
            throw error;
        }
    }
    onNotification(type, handler) {
        if (!this.isConnectionActive()) {
            throw new Error('Language client is not ready yet');
        }
        try {
            this._resolvedConnection.onNotification(type, handler);
        }
        catch (error) {
            this.error(`Registering notification handler ${is.string(type) ? type : type.method} failed.`, error);
            throw error;
        }
    }
    get onTelemetry() {
        return this._telemetryEmitter.event;
    }
    get onDidChangeState() {
        return this._stateChangeEmitter.event;
    }
    get outputChannel() {
        if (!this._outputChannel && this.window && this.window.createOutputChannel) {
            this._outputChannel = this.window.createOutputChannel(this._clientOptions.outputChannelName ? this._clientOptions.outputChannelName : this._name);
        }
        return this._outputChannel;
    }
    get diagnostics() {
        return this._diagnostics;
    }
    createDefaultErrorHandler() {
        return new DefaultErrorHandler(this._name, this);
    }
    set trace(value) {
        this._trace = value;
        this.onReady().then(() => {
            this.resolveConnection().then((connection) => {
                connection.trace(value, this._tracer);
            });
        }, () => {
        });
    }
    data2String(data) {
        if (data instanceof vscode_jsonrpc_1.ResponseError) {
            const responseError = data;
            return `  Message: ${responseError.message}\n  Code: ${responseError.code} ${responseError.data ? '\n' + responseError.data.toString() : ''}`;
        }
        if (data instanceof Error) {
            if (is.string(data.stack)) {
                return data.stack;
            }
            return data.message;
        }
        if (is.string(data)) {
            return data;
        }
        return data.toString();
    }
    info(message, data) {
        const outputChannel = this.outputChannel;
        if (outputChannel) {
            outputChannel.appendLine(`[Info  - ${(new Date().toLocaleTimeString())}] ${message}`);
            if (data) {
                outputChannel.appendLine(this.data2String(data));
            }
            if (this._clientOptions.revealOutputChannelOn <= RevealOutputChannelOn.Info) {
                outputChannel.show(true);
            }
        }
    }
    warn(message, data) {
        const outputChannel = this.outputChannel;
        if (outputChannel) {
            outputChannel.appendLine(`[Warn  - ${(new Date().toLocaleTimeString())}] ${message}`);
            if (data) {
                outputChannel.appendLine(this.data2String(data));
            }
            if (this._clientOptions.revealOutputChannelOn <= RevealOutputChannelOn.Warn) {
                outputChannel.show(true);
            }
        }
    }
    error(message, data) {
        const outputChannel = this.outputChannel;
        if (outputChannel) {
            outputChannel.appendLine(`[Error - ${(new Date().toLocaleTimeString())}] ${message}`);
            if (data) {
                outputChannel.appendLine(this.data2String(data));
            }
            if (this._clientOptions.revealOutputChannelOn <= RevealOutputChannelOn.Error) {
                outputChannel.show(true);
            }
        }
    }
    logTrace(message, data) {
        const outputChannel = this.outputChannel;
        if (outputChannel) {
            outputChannel.appendLine(`[Trace - ${(new Date().toLocaleTimeString())}] ${message}`);
            if (data) {
                outputChannel.appendLine(this.data2String(data));
            }
            outputChannel.show(true);
        }
    }
    needsStart() {
        return this.state === ClientState.Initial || this.state === ClientState.Stopping || this.state === ClientState.Stopped;
    }
    needsStop() {
        return this.state === ClientState.Starting || this.state === ClientState.Running;
    }
    onReady() {
        return this._onReady;
    }
    isConnectionActive() {
        return this.state === ClientState.Running && !!this._resolvedConnection;
    }
    start() {
        this._listeners = [];
        this._providers = [];
        // If we restart then the diagnostics collection is reused.
        if (!this._diagnostics && this.languages.createDiagnosticCollection) {
            this._diagnostics = this.languages.createDiagnosticCollection(this._clientOptions.diagnosticCollectionName);
        }
        this.state = ClientState.Starting;
        this.resolveConnection().then((connection) => {
            connection.onLogMessage((message) => {
                switch (message.type) {
                    case protocol_1.MessageType.Error:
                        this.error(message.message);
                        break;
                    case protocol_1.MessageType.Warning:
                        this.warn(message.message);
                        break;
                    case protocol_1.MessageType.Info:
                        this.info(message.message);
                        break;
                    default: {
                        if (this.outputChannel) {
                            this.outputChannel.appendLine(message.message);
                        }
                    }
                }
            });
            const window = this.window;
            if (window) {
                connection.onShowMessage((message) => window.showMessage(message.type, message.message));
                connection.onRequest(protocol_1.ShowMessageRequest.type, (params) => {
                    const actions = params.actions || [];
                    return window.showMessage(params.type, params.message, ...actions);
                });
            }
            connection.onTelemetry((data) => {
                this._telemetryEmitter.fire(data);
            });
            this.initRegistrationHandlers(connection);
            connection.listen();
            // Error is handled in the intialize call.
            this.initialize(connection).then(undefined, () => { });
        }, (error) => {
            this.state = ClientState.StartFailed;
            this._onReadyCallbacks.reject(error);
            this.error('Starting client failed', error);
            if (this.window) {
                this.window.showMessage(protocol_1.MessageType.Error, `Couldn't start client ${this._name}`);
            }
        });
        return vscode_jsonrpc_1.Disposable.create(() => {
            if (this.needsStop()) {
                this.stop();
            }
        });
    }
    resolveConnection() {
        if (!this._connectionPromise) {
            this._connectionPromise = this.createConnection();
        }
        return this._connectionPromise;
    }
    createClientCapabilities() {
        return {
            workspace: Object.assign({}, this.workspace.capabilities, { didChangeConfiguration: {
                    dynamicRegistration: false
                }, didChangeWatchedFiles: {
                    dynamicRegistration: false
                }, symbol: {
                    dynamicRegistration: true
                }, executeCommand: {
                    dynamicRegistration: true
                } }),
            textDocument: {
                synchronization: Object.assign({}, this.workspace.synchronization, { dynamicRegistration: true }),
                completion: Object.assign({}, this.languages.completion, { dynamicRegistration: true }),
                hover: {
                    dynamicRegistration: true
                },
                signatureHelp: {
                    dynamicRegistration: true
                },
                references: {
                    dynamicRegistration: true
                },
                documentHighlight: {
                    dynamicRegistration: true
                },
                documentSymbol: {
                    dynamicRegistration: true
                },
                formatting: {
                    dynamicRegistration: true
                },
                rangeFormatting: {
                    dynamicRegistration: true
                },
                onTypeFormatting: {
                    dynamicRegistration: true
                },
                definition: {
                    dynamicRegistration: true
                },
                codeAction: {
                    dynamicRegistration: true
                },
                codeLens: {
                    dynamicRegistration: true
                },
                documentLink: {
                    dynamicRegistration: true
                },
                rename: {
                    dynamicRegistration: true
                }
            }
        };
    }
    initialize(connection) {
        this.refreshTrace(connection, false);
        let initOption = this._clientOptions.initializationOptions;
        const rootPath = this.workspace && this.workspace.rootPath || null;
        const rootUri = this.workspace && this.workspace.rootUri || null;
        const clientCapabilities = this.createClientCapabilities();
        let initParams = {
            processId: process.pid,
            rootPath, rootUri,
            capabilities: clientCapabilities,
            initializationOptions: is.func(initOption) ? initOption() : initOption,
            trace: vscode_jsonrpc_1.Trace.toString(this._trace)
        };
        return connection.initialize(initParams).then((result) => {
            this._resolvedConnection = connection;
            this.state = ClientState.Running;
            this._capabilites = result.capabilities;
            connection.onDiagnostics(params => this.handleDiagnostics(params));
            // backward compatibility
            connection.onRequest('client/registerFeature', params => this.handleRegistrationRequest(params));
            connection.onRequest(protocol_1.RegistrationRequest.type, params => this.handleRegistrationRequest(params));
            // backward compatibility
            connection.onRequest('client/unregisterFeature', params => this.handleUnregistrationRequest(params));
            connection.onRequest(protocol_1.UnregistrationRequest.type, params => this.handleUnregistrationRequest(params));
            connection.onRequest(protocol_1.ApplyWorkspaceEditRequest.type, params => this.handleApplyWorkspaceEdit(params));
            connection.sendNotification(protocol_1.InitializedNotification.type, {});
            this.hookFileEvents(connection);
            this.hookConfigurationChanged(connection);
            if (this._clientOptions.documentSelector) {
                let selectorOptions = { documentSelector: this._clientOptions.documentSelector };
                let textDocumentSyncOptions = undefined;
                if (is.number(this._capabilites.textDocumentSync) && this._capabilites.textDocumentSync !== protocol_1.TextDocumentSyncKind.None) {
                    textDocumentSyncOptions = {
                        openClose: true,
                        change: this._capabilites.textDocumentSync,
                        save: {
                            includeText: false
                        }
                    };
                }
                else if (this._capabilites.textDocumentSync !== void 0 && this._capabilites.textDocumentSync !== null) {
                    textDocumentSyncOptions = this._capabilites.textDocumentSync;
                }
                if (textDocumentSyncOptions) {
                    if (textDocumentSyncOptions.openClose) {
                        this.registerHandler(protocol_1.DidOpenTextDocumentNotification.type.method, { id: UUID.generateUuid(), registerOptions: selectorOptions });
                        this.registerHandler(protocol_1.DidCloseTextDocumentNotification.type.method, { id: UUID.generateUuid(), registerOptions: selectorOptions });
                    }
                    if (textDocumentSyncOptions.change !== protocol_1.TextDocumentSyncKind.None) {
                        this.registerHandler(protocol_1.DidChangeTextDocumentNotification.type.method, {
                            id: UUID.generateUuid(),
                            registerOptions: Object.assign({}, selectorOptions, { syncKind: textDocumentSyncOptions.change })
                        });
                    }
                    if (textDocumentSyncOptions.willSave) {
                        this.registerHandler(protocol_1.WillSaveTextDocumentNotification.type.method, { id: UUID.generateUuid(), registerOptions: selectorOptions });
                    }
                    if (textDocumentSyncOptions.willSaveWaitUntil) {
                        this.registerHandler(protocol_1.WillSaveTextDocumentWaitUntilRequest.type.method, { id: UUID.generateUuid(), registerOptions: selectorOptions });
                    }
                    if (textDocumentSyncOptions.save) {
                        this.registerHandler(protocol_1.DidSaveTextDocumentNotification.type.method, {
                            id: UUID.generateUuid(),
                            registerOptions: Object.assign({}, selectorOptions, { includeText: !!textDocumentSyncOptions.save.includeText })
                        });
                    }
                }
            }
            this.hookCapabilities(connection);
            this._onReadyCallbacks.resolve();
            return result;
        }, (error) => {
            if (this._clientOptions.initializationFailedHandler) {
                if (this._clientOptions.initializationFailedHandler(error)) {
                    this.initialize(connection);
                }
                else {
                    this.stop();
                    this._onReadyCallbacks.reject(error);
                }
            }
            else if (error instanceof vscode_jsonrpc_1.ResponseError && error.data && error.data.retry && this.window) {
                this.window.showMessage(protocol_1.MessageType.Error, error.message, { title: 'Retry', id: "retry" }).then(item => {
                    if (item && item.id === 'retry') {
                        this.initialize(connection);
                    }
                    else {
                        this.stop();
                        this._onReadyCallbacks.reject(error);
                    }
                });
            }
            else {
                if (error && error.message && this.window) {
                    this.window.showMessage(protocol_1.MessageType.Error, error.message);
                }
                this.error('Server initialization failed.', error);
                this.stop();
                this._onReadyCallbacks.reject(error);
            }
        });
    }
    stop() {
        if (!this._connectionPromise) {
            this.state = ClientState.Stopped;
            return Promise.resolve();
        }
        this.state = ClientState.Stopping;
        this.cleanUp();
        // unkook listeners
        return this.resolveConnection().then(connection => {
            return connection.shutdown().then(() => {
                connection.exit();
                connection.dispose();
                this.state = ClientState.Stopped;
                this._connectionPromise = undefined;
                this._resolvedConnection = undefined;
                // Remove all markers
            });
        });
    }
    cleanUp(diagnostics = true) {
        if (this._listeners) {
            this._listeners.forEach(listener => listener.dispose());
            this._listeners = undefined;
        }
        if (this._providers) {
            this._providers.forEach(provider => provider.dispose());
            this._providers = undefined;
        }
        if (diagnostics && this._diagnostics) {
            this._diagnostics.dispose();
            this._diagnostics = undefined;
        }
        for (const handler of Array.from(this._registeredHandlers.values())) {
            handler.dispose();
        }
        this._registeredHandlers.clear();
    }
    notifyFileEvent(event) {
        this._fileEvents.push(event);
        this._fileEventDelayer.trigger(() => {
            this.onReady().then(() => {
                this.resolveConnection().then(connection => {
                    if (this.isConnectionActive()) {
                        connection.didChangeWatchedFiles({ changes: this._fileEvents });
                    }
                    this._fileEvents = [];
                });
            }, (error) => {
                this.error(`Notify file events failed.`, error);
            });
        });
    }
    forceDocumentSync() {
        this._registeredHandlers.get(protocol_1.DidChangeTextDocumentNotification.type.method).forceDelivery();
    }
    handleDiagnostics(params) {
        if (!this._diagnostics) {
            return;
        }
        this._diagnostics.set(params.uri, params.diagnostics);
    }
    createConnection() {
        const errorHandler = this.handleConnectionError.bind(this);
        const closeHandler = this.handleConnectionClosed.bind(this);
        return this.connectionProvider.get(errorHandler, closeHandler, this.outputChannel);
    }
    handleConnectionClosed() {
        // Check whether this is a normal shutdown in progress or the client stopped normally.
        if (this.state === ClientState.Stopping || this.state === ClientState.Stopped) {
            return;
        }
        this._connectionPromise = undefined;
        this._resolvedConnection = undefined;
        let action = this._clientOptions.errorHandler.closed();
        if (action === CloseAction.DoNotRestart) {
            this.error('Connection to server got closed. Server will not be restarted.');
            this.state = ClientState.Stopped;
            this.cleanUp();
        }
        else if (action === CloseAction.Restart) {
            this.info('Connection to server got closed. Server will restart.');
            this.cleanUp(false);
            this.state = ClientState.Initial;
            this.start();
        }
    }
    handleConnectionError(error, message, count) {
        let action = this._clientOptions.errorHandler.error(error, message, count);
        if (action === ErrorAction.Shutdown) {
            this.error('Connection to server is erroring. Shutting down server.');
            this.stop();
        }
    }
    hookConfigurationChanged(connection) {
        if (!this._clientOptions.synchronize.configurationSection || !this.workspace.configurations) {
            return;
        }
        this.workspace.configurations.onDidChangeConfiguration(() => this.onDidChangeConfiguration(connection), this, this._listeners);
        this.onDidChangeConfiguration(connection);
    }
    refreshTrace(connection, sendNotification = false) {
        const configurations = this.workspace.configurations;
        if (configurations) {
            const config = configurations.getConfiguration(this._id);
            this._trace = !!config ? vscode_jsonrpc_1.Trace.fromString(config.get('trace.server', 'off')) : vscode_jsonrpc_1.Trace.Off;
            connection.trace(this._trace, this._tracer, sendNotification);
        }
    }
    onDidChangeConfiguration(connection) {
        this.refreshTrace(connection, true);
        let keys;
        let configurationSection = this._clientOptions.synchronize.configurationSection;
        if (is.string(configurationSection)) {
            keys = [configurationSection];
        }
        else if (is.stringArray(configurationSection)) {
            keys = configurationSection;
        }
        if (keys) {
            if (this.isConnectionActive()) {
                connection.didChangeConfiguration({ settings: this.extractSettingsInformation(keys) });
            }
        }
    }
    extractSettingsInformation(keys) {
        function ensurePath(config, path) {
            let current = config;
            for (let i = 0; i < path.length - 1; i++) {
                let obj = current[path[i]];
                if (!obj) {
                    obj = Object.create(null);
                    current[path[i]] = obj;
                }
                current = obj;
            }
            return current;
        }
        let result = Object.create(null);
        for (let i = 0; i < keys.length; i++) {
            let key = keys[i];
            let index = key.indexOf('.');
            let config = null;
            if (index >= 0) {
                config = this.workspace.configurations.getConfiguration(key.substr(0, index)).get(key.substr(index + 1));
            }
            else {
                config = this.workspace.configurations.getConfiguration(key);
            }
            if (config) {
                let path = keys[i].split('.');
                ensurePath(result, path)[path[path.length - 1]] = config;
            }
        }
        return result;
    }
    hookFileEvents(_connection) {
        let fileEvents = this._clientOptions.synchronize.fileEvents;
        if (!fileEvents) {
            return;
        }
        let watchers;
        if (is.array(fileEvents)) {
            watchers = fileEvents;
        }
        else {
            watchers = [fileEvents];
        }
        if (!watchers) {
            return;
        }
        watchers.forEach(watcher => {
            watcher.onFileEvent(event => this.notifyFileEvent(event), null, this._listeners);
        });
    }
    initRegistrationHandlers(_connection) {
        const syncedDocuments = new Map();
        const logger = (type, error) => { this.logFailedRequest(type, error); };
        this._registeredHandlers.set(protocol_1.DidOpenTextDocumentNotification.type.method, new DidOpenTextDocumentFeature(this, syncedDocuments));
        this._registeredHandlers.set(protocol_1.DidChangeTextDocumentNotification.type.method, new DidChangeTextDocumentFeature(this));
        if (this.workspace.onWillSaveTextDocument) {
            this._registeredHandlers.set(protocol_1.WillSaveTextDocumentNotification.type.method, new DocumentNotifiactions(this, this.workspace.onWillSaveTextDocument, protocol_1.WillSaveTextDocumentNotification.type, event => event, (selectors, willSaveEvent) => DocumentNotifiactions.textDocumentFilter(this.languages, selectors, willSaveEvent.textDocument)));
            if (!!this.workspace.synchronization && this.workspace.synchronization.willSaveWaitUntil) {
                this._registeredHandlers.set(protocol_1.WillSaveTextDocumentWaitUntilRequest.type.method, new WillSaveWaitUntilFeature(this));
            }
        }
        if (this.workspace.onDidSaveTextDocument) {
            this._registeredHandlers.set(protocol_1.DidSaveTextDocumentNotification.type.method, new DidSaveTextDocumentFeature(this));
        }
        this._registeredHandlers.set(protocol_1.DidCloseTextDocumentNotification.type.method, new DidCloseTextDocumentFeature(this, syncedDocuments));
        if (this.languages.registerCompletionItemProvider) {
            this._registeredHandlers.set(protocol_1.CompletionRequest.type.method, new LanguageFeature((options) => this.createCompletionProvider(options)));
        }
        if (this.languages.registerHoverProvider) {
            this._registeredHandlers.set(protocol_1.HoverRequest.type.method, new LanguageFeature((options) => this.createHoverProvider(options)));
        }
        if (this.languages.registerSignatureHelpProvider) {
            this._registeredHandlers.set(protocol_1.SignatureHelpRequest.type.method, new LanguageFeature((options) => this.createSignatureHelpProvider(options)));
        }
        if (this.languages.registerDefinitionProvider) {
            this._registeredHandlers.set(protocol_1.DefinitionRequest.type.method, new LanguageFeature((options) => this.createDefinitionProvider(options)));
        }
        if (this.languages.registerReferenceProvider) {
            this._registeredHandlers.set(protocol_1.ReferencesRequest.type.method, new LanguageFeature((options) => this.createReferencesProvider(options)));
        }
        if (this.languages.registerDocumentHighlightProvider) {
            this._registeredHandlers.set(protocol_1.DocumentHighlightRequest.type.method, new LanguageFeature((options) => this.createDocumentHighlightProvider(options)));
        }
        if (this.languages.registerDocumentSymbolProvider) {
            this._registeredHandlers.set(protocol_1.DocumentSymbolRequest.type.method, new LanguageFeature((options) => this.createDocumentSymbolProvider(options)));
        }
        if (this.languages.registerWorkspaceSymbolProvider) {
            this._registeredHandlers.set(protocol_1.WorkspaceSymbolRequest.type.method, new LanguageFeature((options) => this.createWorkspaceSymbolProvider(options)));
        }
        if (this.languages.registerCodeActionsProvider) {
            this._registeredHandlers.set(protocol_1.CodeActionRequest.type.method, new LanguageFeature((options) => this.createCodeActionsProvider(options)));
        }
        if (this.languages.registerCodeLensProvider) {
            this._registeredHandlers.set(protocol_1.CodeLensRequest.type.method, new LanguageFeature((options) => this.createCodeLensProvider(options)));
        }
        if (this.languages.registerDocumentFormattingEditProvider) {
            this._registeredHandlers.set(protocol_1.DocumentFormattingRequest.type.method, new LanguageFeature((options) => this.createDocumentFormattingProvider(options)));
        }
        if (this.languages.registerDocumentRangeFormattingEditProvider) {
            this._registeredHandlers.set(protocol_1.DocumentRangeFormattingRequest.type.method, new LanguageFeature((options) => this.createDocumentRangeFormattingProvider(options)));
        }
        if (this.languages.registerOnTypeFormattingEditProvider) {
            this._registeredHandlers.set(protocol_1.DocumentOnTypeFormattingRequest.type.method, new LanguageFeature((options) => this.createDocumentOnTypeFormattingProvider(options)));
        }
        if (this.languages.registerRenameProvider) {
            this._registeredHandlers.set(protocol_1.RenameRequest.type.method, new LanguageFeature((options) => this.createRenameProvider(options)));
        }
        if (this.languages.registerDocumentLinkProvider) {
            this._registeredHandlers.set(protocol_1.DocumentLinkRequest.type.method, new LanguageFeature((options) => this.createDocumentLinkProvider(options)));
        }
        if (this.commands) {
            this._registeredHandlers.set(protocol_1.ExecuteCommandRequest.type.method, new ExecuteCommandFeature(this, logger));
        }
    }
    handleRegistrationRequest(params) {
        return new Promise((resolve, _reject) => {
            params.registrations.forEach((element) => {
                const handler = this._registeredHandlers.get(element.method);
                const options = element.registerOptions || {};
                options.documentSelector = options.documentSelector || this._clientOptions.documentSelector;
                const data = {
                    id: element.id,
                    registerOptions: options
                };
                if (handler) {
                    handler.register(data);
                }
            });
            resolve();
        });
    }
    handleUnregistrationRequest(params) {
        return new Promise((resolve, _reject) => {
            params.unregisterations.forEach((element) => {
                const handler = this._registeredHandlers.get(element.method);
                if (handler) {
                    handler.unregister(element.id);
                }
            });
            resolve();
        });
    }
    handleApplyWorkspaceEdit(params) {
        if (!this.workspace.applyEdit) {
            return Promise.resolve({ applied: false });
        }
        // This is some sort of workaround since the version check should be done by VS Code in the Workspace.applyEdit.
        // However doing it here adds some safety since the server can lag more behind then an extension.
        let workspaceEdit = params.edit;
        let openTextDocuments = new Map();
        this.workspace.textDocuments.forEach((document) => openTextDocuments.set(document.uri, document));
        let versionMismatch = false;
        if (workspaceEdit.documentChanges) {
            for (const change of workspaceEdit.documentChanges) {
                if (change.textDocument.version && change.textDocument.version >= 0) {
                    let textDocument = openTextDocuments.get(change.textDocument.uri);
                    if (textDocument && textDocument.version !== change.textDocument.version) {
                        versionMismatch = true;
                        break;
                    }
                }
            }
        }
        if (versionMismatch) {
            return Promise.resolve({ applied: false });
        }
        return this.workspace.applyEdit(params.edit).then(applied => { return { applied }; });
    }
    ;
    registerHandler(method, data) {
        const handler = this._registeredHandlers.get(method);
        if (handler) {
            handler.register(data);
        }
    }
    hookCapabilities(_connection) {
        let documentSelector = this._clientOptions.documentSelector;
        if (!documentSelector) {
            return;
        }
        let selectorOptions = { documentSelector: documentSelector };
        if (this._capabilites.completionProvider) {
            let options = Object.assign({}, selectorOptions, this._capabilites.completionProvider);
            this.registerHandler(protocol_1.CompletionRequest.type.method, { id: UUID.generateUuid(), registerOptions: options });
        }
        if (this._capabilites.hoverProvider) {
            this.registerHandler(protocol_1.HoverRequest.type.method, { id: UUID.generateUuid(), registerOptions: Object.assign({}, selectorOptions) });
        }
        if (this._capabilites.signatureHelpProvider) {
            let options = Object.assign({}, selectorOptions, this._capabilites.signatureHelpProvider);
            this.registerHandler(protocol_1.SignatureHelpRequest.type.method, { id: UUID.generateUuid(), registerOptions: options });
        }
        if (this._capabilites.definitionProvider) {
            this.registerHandler(protocol_1.DefinitionRequest.type.method, { id: UUID.generateUuid(), registerOptions: Object.assign({}, selectorOptions) });
        }
        if (this._capabilites.referencesProvider) {
            this.registerHandler(protocol_1.ReferencesRequest.type.method, { id: UUID.generateUuid(), registerOptions: Object.assign({}, selectorOptions) });
        }
        if (this._capabilites.documentHighlightProvider) {
            this.registerHandler(protocol_1.DocumentHighlightRequest.type.method, { id: UUID.generateUuid(), registerOptions: Object.assign({}, selectorOptions) });
        }
        if (this._capabilites.documentSymbolProvider) {
            this.registerHandler(protocol_1.DocumentSymbolRequest.type.method, { id: UUID.generateUuid(), registerOptions: Object.assign({}, selectorOptions) });
        }
        if (this._capabilites.workspaceSymbolProvider) {
            this.registerHandler(protocol_1.WorkspaceSymbolRequest.type.method, { id: UUID.generateUuid(), registerOptions: Object.assign({}, selectorOptions) });
        }
        if (this._capabilites.codeActionProvider) {
            this.registerHandler(protocol_1.CodeActionRequest.type.method, { id: UUID.generateUuid(), registerOptions: Object.assign({}, selectorOptions) });
        }
        if (this._capabilites.codeLensProvider) {
            let options = Object.assign({}, selectorOptions, this._capabilites.codeLensProvider);
            this.registerHandler(protocol_1.CodeLensRequest.type.method, { id: UUID.generateUuid(), registerOptions: options });
        }
        if (this._capabilites.documentFormattingProvider) {
            this.registerHandler(protocol_1.DocumentFormattingRequest.type.method, { id: UUID.generateUuid(), registerOptions: Object.assign({}, selectorOptions) });
        }
        if (this._capabilites.documentRangeFormattingProvider) {
            this.registerHandler(protocol_1.DocumentRangeFormattingRequest.type.method, { id: UUID.generateUuid(), registerOptions: Object.assign({}, selectorOptions) });
        }
        if (this._capabilites.documentOnTypeFormattingProvider) {
            let options = Object.assign({}, selectorOptions, this._capabilites.documentOnTypeFormattingProvider);
            this.registerHandler(protocol_1.DocumentOnTypeFormattingRequest.type.method, { id: UUID.generateUuid(), registerOptions: options });
        }
        if (this._capabilites.renameProvider) {
            this.registerHandler(protocol_1.RenameRequest.type.method, { id: UUID.generateUuid(), registerOptions: Object.assign({}, selectorOptions) });
        }
        if (this._capabilites.documentLinkProvider) {
            let options = Object.assign({}, selectorOptions, this._capabilites.documentLinkProvider);
            this.registerHandler(protocol_1.DocumentLinkRequest.type.method, { id: UUID.generateUuid(), registerOptions: options });
        }
        if (this._capabilites.executeCommandProvider) {
            let options = Object.assign({}, this._capabilites.executeCommandProvider);
            this.registerHandler(protocol_1.ExecuteCommandRequest.type.method, { id: UUID.generateUuid(), registerOptions: options });
        }
    }
    logFailedRequest(type, error) {
        // If we get a request cancel don't log anything.
        if (error instanceof vscode_jsonrpc_1.ResponseError && error.code === vscode_jsonrpc_1.ErrorCodes.RequestCancelled) {
            return;
        }
        this.error(`Request ${type.method} failed.`, error);
    }
    createRequestHandler(type, onError) {
        return (params, token) => {
            return this.sendRequest(type, params, token).then((result) => result, (error) => {
                this.logFailedRequest(type, error);
                const result = onError ? onError(params, error) : null;
                return Promise.reject(result);
            });
        };
    }
    createCompletionProvider(options) {
        let triggerCharacters = options.triggerCharacters || [];
        return this.languages.registerCompletionItemProvider(options.documentSelector, {
            provideCompletionItems: this.createRequestHandler(protocol_1.CompletionRequest.type, () => []),
            resolveCompletionItem: options.resolveProvider ? this.createRequestHandler(protocol_1.CompletionResolveRequest.type, (params) => params) : undefined
        }, ...triggerCharacters);
    }
    createHoverProvider(options) {
        return this.languages.registerHoverProvider(options.documentSelector, {
            provideHover: this.createRequestHandler(protocol_1.HoverRequest.type)
        });
    }
    createSignatureHelpProvider(options) {
        let triggerCharacters = options.triggerCharacters || [];
        return this.languages.registerSignatureHelpProvider(options.documentSelector, {
            provideSignatureHelp: this.createRequestHandler(protocol_1.SignatureHelpRequest.type)
        }, ...triggerCharacters);
    }
    createDefinitionProvider(options) {
        return this.languages.registerDefinitionProvider(options.documentSelector, {
            provideDefinition: this.createRequestHandler(protocol_1.DefinitionRequest.type)
        });
    }
    createReferencesProvider(options) {
        return this.languages.registerReferenceProvider(options.documentSelector, {
            provideReferences: this.createRequestHandler(protocol_1.ReferencesRequest.type, () => [])
        });
    }
    createDocumentHighlightProvider(options) {
        return this.languages.registerDocumentHighlightProvider(options.documentSelector, {
            provideDocumentHighlights: this.createRequestHandler(protocol_1.DocumentHighlightRequest.type, () => [])
        });
    }
    createDocumentSymbolProvider(options) {
        return this.languages.registerDocumentSymbolProvider(options.documentSelector, {
            provideDocumentSymbols: this.createRequestHandler(protocol_1.DocumentSymbolRequest.type, () => [])
        });
    }
    createWorkspaceSymbolProvider(_options) {
        return this.languages.registerWorkspaceSymbolProvider({
            provideWorkspaceSymbols: this.createRequestHandler(protocol_1.WorkspaceSymbolRequest.type, () => [])
        });
    }
    createCodeActionsProvider(options) {
        return this.languages.registerCodeActionsProvider(options.documentSelector, {
            provideCodeActions: this.createRequestHandler(protocol_1.CodeActionRequest.type, () => [])
        });
    }
    createCodeLensProvider(options) {
        return this.languages.registerCodeLensProvider(options.documentSelector, {
            provideCodeLenses: this.createRequestHandler(protocol_1.CodeLensRequest.type, () => []),
            resolveCodeLens: options.resolveProvider ? this.createRequestHandler(protocol_1.CodeLensResolveRequest.type, (params) => params) : undefined
        });
    }
    createDocumentFormattingProvider(options) {
        return this.languages.registerDocumentFormattingEditProvider(options.documentSelector, {
            provideDocumentFormattingEdits: this.createRequestHandler(protocol_1.DocumentFormattingRequest.type, () => [])
        });
    }
    createDocumentRangeFormattingProvider(options) {
        return this.languages.registerDocumentRangeFormattingEditProvider(options.documentSelector, {
            provideDocumentRangeFormattingEdits: this.createRequestHandler(protocol_1.DocumentRangeFormattingRequest.type, () => [])
        });
    }
    createDocumentOnTypeFormattingProvider(options) {
        let moreTriggerCharacter = options.moreTriggerCharacter || [];
        return this.languages.registerOnTypeFormattingEditProvider(options.documentSelector, {
            provideOnTypeFormattingEdits: this.createRequestHandler(protocol_1.DocumentOnTypeFormattingRequest.type, () => [])
        }, options.firstTriggerCharacter, ...moreTriggerCharacter);
    }
    createRenameProvider(options) {
        return this.languages.registerRenameProvider(options.documentSelector, {
            provideRenameEdits: this.createRequestHandler(protocol_1.RenameRequest.type, (_, error) => new Error(error.message))
        });
    }
    createDocumentLinkProvider(options) {
        return this.languages.registerDocumentLinkProvider(options.documentSelector, {
            provideDocumentLinks: this.createRequestHandler(protocol_1.DocumentLinkRequest.type, (_, error) => new Error(error.message)),
            resolveDocumentLink: options.resolveProvider ? this.createRequestHandler(protocol_1.DocumentLinkResolveRequest.type, (_, error) => new Error(error.message)) : undefined
        });
    }
}
exports.BaseLanguageClient = BaseLanguageClient;
class SettingMonitor {
    constructor(_client, _setting) {
        this._client = _client;
        this._setting = _setting;
        this._listeners = [];
    }
    start() {
        if (this._client.workspace.configurations) {
            this._client.workspace.configurations.onDidChangeConfiguration(this.onDidChangeConfiguration, this, this._listeners);
            this.onDidChangeConfiguration();
        }
        return vscode_jsonrpc_1.Disposable.create(() => {
            if (this._client.needsStop()) {
                this._client.stop();
            }
        });
    }
    onDidChangeConfiguration() {
        const configurations = this._client.workspace.configurations;
        let index = this._setting.indexOf('.');
        let primary = index >= 0 ? this._setting.substr(0, index) : this._setting;
        let rest = index >= 0 ? this._setting.substr(index + 1) : undefined;
        let enabled = rest ? configurations.getConfiguration(primary).get(rest, false) : configurations.getConfiguration(primary);
        if (enabled && this._client.needsStart()) {
            this._client.start();
        }
        else if (!enabled && this._client.needsStop()) {
            this._client.stop();
        }
    }
}
exports.SettingMonitor = SettingMonitor;

/* WEBPACK VAR INJECTION */}.call(exports, __webpack_require__(28)))

/***/ }),
/* 228 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
function createLanguageClient(e){return new monaco_languageclient_1.BaseLanguageClient({name:"ELK Graph Language Client",clientOptions:{documentSelector:["elkt"],errorHandler:{error:function(){return monaco_languageclient_1.ErrorAction.Continue},closed:function(){return monaco_languageclient_1.CloseAction.DoNotRestart}}},services:services,connectionProvider:{get:function(n,o){var r=monaco_languageclient_1.createConnection(e,n,o);return diagramServer.listen(r),Promise.resolve(r)}}})}Object.defineProperty(exports,"__esModule",{value:!0}),__webpack_require__(106);var vscode_ws_jsonrpc_1=__webpack_require__(265),monaco_languageclient_1=__webpack_require__(240),lib_1=__webpack_require__(37),url_parameters_1=__webpack_require__(105),sprotty_config_1=__webpack_require__(104),language_diagram_server_1=__webpack_require__(233),LZString=__webpack_require__(201);__webpack_require__(232);var WebSocket=__webpack_require__(252),urlParameters=url_parameters_1.getParameters(),initialContent;initialContent=void 0!==urlParameters.compressedContent?LZString.decompressFromEncodedURIComponent(urlParameters.compressedContent):void 0!==urlParameters.initialContent?decodeURIComponent(urlParameters.initialContent):"algorithm: layered\n\nnode n1\nnode n2\nnode n3\nedge n1 -> n2\nedge n1 -> n3";var sprottyContainer=sprotty_config_1.default();sprottyContainer.bind(lib_1.TYPES.ModelSource).to(language_diagram_server_1.default).inSingletonScope();var diagramServer=sprottyContainer.get(lib_1.TYPES.ModelSource),modelUri="inmemory:/model.elkt",editor=monaco.editor.create(document.getElementById("monaco-editor"),{model:monaco.editor.createModel(initialContent,"elkt",monaco.Uri.parse(modelUri))});editor.updateOptions({minimap:{enabled:!1}}),url_parameters_1.setupModelLink(editor,function(e){return{compressedContent:LZString.compressToEncodedURIComponent(editor.getValue())}});var socketUrl=("https:"==='localhost:9090'?"wss":"ws")+"://"+'localhost:9090'+"/elkgraph",socketOptions={maxReconnectionDelay:1e4,minReconnectionDelay:1e3,reconnectionDelayGrowFactor:1.3,connectionTimeout:1e4,maxRetries:1/0,debug:!1},webSocket=new WebSocket(socketUrl,void 0,socketOptions),services=monaco_languageclient_1.createMonacoServices(editor);vscode_ws_jsonrpc_1.listen({webSocket:webSocket,onConnection:function(e){var n=createLanguageClient(e),o=n.start();e.onClose(function(){diagramServer.disconnect(),o.dispose()})}});

/***/ }),
/* 229 */,
/* 230 */,
/* 231 */,
/* 232 */
/***/ (function(module, exports) {

monaco.languages.register({id:"elkt",extensions:[".elkt"]}),monaco.languages.setLanguageConfiguration("elkt",{comments:{lineComment:"//",blockComment:["/*","*/"]},brackets:[["{","}"],["[","]"]],autoClosingPairs:[{open:"{",close:"}"},{open:"[",close:"]"}]}),monaco.languages.setMonarchTokensProvider("elkt",{keywords:["graph","node","label","port","edge","layout","position","size","incoming","outgoing","start","end","bends","section","true","false"],typeKeywords:[],operators:[],symbols:/[=><!~?:&|+\-*\/\^%]+/,escapes:/\\(?:[btnfru\\"']|x[0-9A-Fa-f]{1,4}|u[0-9A-Fa-f]{4}|U[0-9A-Fa-f]{8})/,tokenizer:{root:[[/[a-z_$][\w$]*/,{cases:{"@typeKeywords":"keyword","@keywords":"keyword","@default":"identifier"}}],{include:"@whitespace"},[/[{}()\[\]]/,"@brackets"],[/[<>](?!@symbols)/,"@brackets"],[/@symbols/,{cases:{"@operators":"operator","@default":""}}]],whitespace:[[/[ \t\r\n]+/,"white"],[/\/\*/,"comment","@comment"],[/\/\/.*$/,"comment"]],comment:[[/[^\/*]+/,"comment"],[/\/\*/,"comment.invalid"],["\\*/","comment","@pop"],[/[\/*]/,"comment"]],string:[[/[^\\"]+/,"string"],[/@escapes/,"string.escape"],[/\\./,"string.escape.invalid"],[/"/,"string","@pop"]]}});

/***/ }),
/* 233 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
var __extends=this&&this.__extends||function(){var t=Object.setPrototypeOf||{__proto__:[]}instanceof Array&&function(t,n){t.__proto__=n}||function(t,n){for(var e in n)n.hasOwnProperty(e)&&(t[e]=n[e])};return function(n,e){function o(){this.constructor=n}t(n,e),n.prototype=null===e?Object.create(e):(o.prototype=e.prototype,new o)}}();Object.defineProperty(exports,"__esModule",{value:!0});var lib_1=__webpack_require__(37),DIAGRAM_ENDPOINT_NOTIFICATION="diagram/accept",DID_CLOSE_NOTIFICATION="diagram/didClose",LanguageDiagramServer=function(t){function n(){return null!==t&&t.apply(this,arguments)||this}return __extends(n,t),n.prototype.listen=function(t){var n=this;t.onNotification(DIAGRAM_ENDPOINT_NOTIFICATION,function(t){n.messageReceived(t)}),this.connection=t},n.prototype.disconnect=function(){void 0!==this.connection&&(this.connection.sendNotification(DID_CLOSE_NOTIFICATION,this.clientId),this.connection=void 0)},n.prototype.sendMessage=function(t){void 0!==this.connection&&this.connection.sendNotification(DIAGRAM_ENDPOINT_NOTIFICATION,t)},n}(lib_1.DiagramServer);exports.default=LanguageDiagramServer;

/***/ }),
/* 234 */
/***/ (function(module, exports, __webpack_require__) {

window.onload=function(){window.require(["vs/editor/editor.main"],function(){__webpack_require__(228)})};

/***/ }),
/* 235 */,
/* 236 */,
/* 237 */,
/* 238 */
/***/ (function(module, exports) {

module.exports=function(e,a){if("string"!=typeof e)throw new TypeError("Expected a string");for(var s,r=String(e),c="",f=!!a&&!!a.extended,i=!!a&&!!a.globstar,t=!1,n=a&&"string"==typeof a.flags?a.flags:"",o=0,g=r.length;o<g;o++)switch(s=r[o]){case"\\":case"/":case"$":case"^":case"+":case".":case"(":case")":case"=":case"!":case"|":c+="\\"+s;break;case"?":if(f){c+=".";break}case"[":case"]":if(f){c+=s;break}case"{":if(f){t=!0,c+="(";break}case"}":if(f){t=!1,c+=")";break}case",":if(t){c+="|";break}c+="\\"+s;break;case"*":for(var b=r[o-1],d=1;"*"===r[o+1];)d++,o++;var k=r[o+1];if(i){d>1&&("/"===b||void 0===b)&&("/"===k||void 0===k)?(c+="(?:[^/]*(?:/|$))*",o++):c+="[^/]*"}else c+=".*";break;default:c+=s}return n&&~n.indexOf("g")||(c="^"+c+"$"),new RegExp(c,n)};

/***/ }),
/* 239 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
Object.defineProperty(exports,"__esModule",{value:!0});var disposable_1=__webpack_require__(202),MonacoDiagnosticCollection=function(){function t(t,o){this.name=t,this.p2m=o,this.diagnostics=new Map,this.toDispose=new disposable_1.DisposableCollection}return t.prototype.dispose=function(){this.toDispose.dispose()},t.prototype.get=function(t){var o=this.diagnostics.get(t);return o?o.diagnostics:[]},t.prototype.set=function(t,o){var e=this,i=this.diagnostics.get(t);if(i)i.diagnostics=o;else{var s=new MonacoModelDiagnostics(t,o,this.name,this.p2m);this.diagnostics.set(t,s),this.toDispose.push(disposable_1.Disposable.create(function(){e.diagnostics.delete(t),s.dispose()}))}},t}();exports.MonacoDiagnosticCollection=MonacoDiagnosticCollection;var MonacoModelDiagnostics=function(){function t(t,o,e,i){var s=this;this.owner=e,this.p2m=i,this.uri=monaco.Uri.parse(t),this.diagnostics=o,monaco.editor.onDidCreateModel(function(t){return s.doUpdateModelMarkers(t)})}return Object.defineProperty(t.prototype,"diagnostics",{get:function(){return this._diagnostics},set:function(t){var o=this;this._diagnostics=t,this._markers=t.map(function(t){return o.p2m.asMarker(t)}),this.updateModelMarkers()},enumerable:!0,configurable:!0}),Object.defineProperty(t.prototype,"markers",{get:function(){return this._markers},enumerable:!0,configurable:!0}),t.prototype.dispose=function(){this._markers=[],this.updateModelMarkers()},t.prototype.updateModelMarkers=function(){var t=monaco.editor.getModel(this.uri);this.doUpdateModelMarkers(t)},t.prototype.doUpdateModelMarkers=function(t){t&&this.uri.toString()===t.uri.toString()&&monaco.editor.setModelMarkers(t,this.owner,this._markers)},t}();exports.MonacoModelDiagnostics=MonacoModelDiagnostics;

/***/ }),
/* 240 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
function __export(e){for(var r in e)exports.hasOwnProperty(r)||(exports[r]=e[r])}Object.defineProperty(exports,"__esModule",{value:!0}),__export(__webpack_require__(202)),__export(__webpack_require__(214)),__export(__webpack_require__(215)),__export(__webpack_require__(217)),__export(__webpack_require__(218)),__export(__webpack_require__(216)),__export(__webpack_require__(241)),__export(__webpack_require__(227)),__export(__webpack_require__(271));

/***/ }),
/* 241 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
function createMonacoServices(e,o){void 0===o&&(o={});var r=new converter_1.MonacoToProtocolConverter,n=new converter_1.ProtocolToMonacoConverter;return{commands:new commands_1.MonacoCommands(e),languages:new languages_1.MonacoLanguages(n,r),workspace:new workspace_1.MonacoWorkspace(n,r,o.rootUri),window:new console_window_1.ConsoleWindow}}Object.defineProperty(exports,"__esModule",{value:!0});var converter_1=__webpack_require__(216),commands_1=__webpack_require__(214),languages_1=__webpack_require__(217),workspace_1=__webpack_require__(218),console_window_1=__webpack_require__(215);exports.createMonacoServices=createMonacoServices;

/***/ }),
/* 242 */
/***/ (function(module, exports) {

exports.endianness=function(){return"LE"},exports.hostname=function(){return"undefined"!=typeof location?location.hostname:""},exports.loadavg=function(){return[]},exports.uptime=function(){return 0},exports.freemem=function(){return Number.MAX_VALUE},exports.totalmem=function(){return Number.MAX_VALUE},exports.cpus=function(){return[]},exports.type=function(){return"Browser"},exports.release=function(){return"undefined"!=typeof navigator?navigator.appVersion:""},exports.networkInterfaces=exports.getNetworkInterfaces=function(){return{}},exports.arch=function(){return"javascript"},exports.platform=function(){return"browser"},exports.tmpdir=exports.tmpDir=function(){return"/tmp"},exports.EOL="\n";

/***/ }),
/* 243 */
/***/ (function(module, exports, __webpack_require__) {

/* WEBPACK VAR INJECTION */(function(process) {function normalizeArray(r,t){for(var e=0,n=r.length-1;n>=0;n--){var s=r[n];"."===s?r.splice(n,1):".."===s?(r.splice(n,1),e++):e&&(r.splice(n,1),e--)}if(t)for(;e--;e)r.unshift("..");return r}function filter(r,t){if(r.filter)return r.filter(t);for(var e=[],n=0;n<r.length;n++)t(r[n],n,r)&&e.push(r[n]);return e}var splitPathRe=/^(\/?|)([\s\S]*?)((?:\.{1,2}|[^\/]+?|)(\.[^.\/]*|))(?:[\/]*)$/,splitPath=function(r){return splitPathRe.exec(r).slice(1)};exports.resolve=function(){for(var r="",t=!1,e=arguments.length-1;e>=-1&&!t;e--){var n=e>=0?arguments[e]:process.cwd();if("string"!=typeof n)throw new TypeError("Arguments to path.resolve must be strings");n&&(r=n+"/"+r,t="/"===n.charAt(0))}return r=normalizeArray(filter(r.split("/"),function(r){return!!r}),!t).join("/"),(t?"/":"")+r||"."},exports.normalize=function(r){var t=exports.isAbsolute(r),e="/"===substr(r,-1);return r=normalizeArray(filter(r.split("/"),function(r){return!!r}),!t).join("/"),r||t||(r="."),r&&e&&(r+="/"),(t?"/":"")+r},exports.isAbsolute=function(r){return"/"===r.charAt(0)},exports.join=function(){var r=Array.prototype.slice.call(arguments,0);return exports.normalize(filter(r,function(r,t){if("string"!=typeof r)throw new TypeError("Arguments to path.join must be strings");return r}).join("/"))},exports.relative=function(r,t){function e(r){for(var t=0;t<r.length&&""===r[t];t++);for(var e=r.length-1;e>=0&&""===r[e];e--);return t>e?[]:r.slice(t,e-t+1)}r=exports.resolve(r).substr(1),t=exports.resolve(t).substr(1);for(var n=e(r.split("/")),s=e(t.split("/")),i=Math.min(n.length,s.length),o=i,u=0;u<i;u++)if(n[u]!==s[u]){o=u;break}for(var l=[],u=o;u<n.length;u++)l.push("..");return l=l.concat(s.slice(o)),l.join("/")},exports.sep="/",exports.delimiter=":",exports.dirname=function(r){var t=splitPath(r),e=t[0],n=t[1];return e||n?(n&&(n=n.substr(0,n.length-1)),e+n):"."},exports.basename=function(r,t){var e=splitPath(r)[2];return t&&e.substr(-1*t.length)===t&&(e=e.substr(0,e.length-t.length)),e},exports.extname=function(r){return splitPath(r)[3]};var substr="b"==="ab".substr(-1)?function(r,t,e){return r.substr(t,e)}:function(r,t,e){return t<0&&(t=r.length+t),r.substr(t,e)};
/* WEBPACK VAR INJECTION */}.call(exports, __webpack_require__(28)))

/***/ }),
/* 244 */,
/* 245 */,
/* 246 */,
/* 247 */,
/* 248 */
/***/ (function(module, exports, __webpack_require__) {

module.exports=__webpack_require__(40);

/***/ }),
/* 249 */
/***/ (function(module, exports, __webpack_require__) {

module.exports=__webpack_require__(103).PassThrough;

/***/ }),
/* 250 */
/***/ (function(module, exports, __webpack_require__) {

module.exports=__webpack_require__(103).Transform;

/***/ }),
/* 251 */
/***/ (function(module, exports, __webpack_require__) {

module.exports=__webpack_require__(102);

/***/ }),
/* 252 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
var isWebSocket=function(e){return e&&2===e.CLOSING},isGlobalWebSocket=function(){return"undefined"!=typeof WebSocket&&isWebSocket(WebSocket)},getDefaultOptions=function(){return{constructor:isGlobalWebSocket()?WebSocket:null,maxReconnectionDelay:1e4,minReconnectionDelay:1500,reconnectionDelayGrowFactor:1.3,connectionTimeout:4e3,maxRetries:1/0,debug:!1}},bypassProperty=function(e,n,o){Object.defineProperty(n,o,{get:function(){return e[o]},set:function(n){e[o]=n},enumerable:!0,configurable:!0})},initReconnectionDelay=function(e){return e.minReconnectionDelay+Math.random()*e.minReconnectionDelay},updateReconnectionDelay=function(e,n){var o=n*e.reconnectionDelayGrowFactor;return o>e.maxReconnectionDelay?e.maxReconnectionDelay:o},LEVEL_0_EVENTS=["onopen","onclose","onmessage","onerror"],reassignEventListeners=function(e,n,o){Object.keys(o).forEach(function(n){o[n].forEach(function(o){var t=o[0],r=o[1];e.addEventListener(n,t,r)})}),n&&LEVEL_0_EVENTS.forEach(function(o){e[o]=n[o]})},ReconnectingWebsocket=function(e,n,o){var t=this;void 0===o&&(o={});var r,c,i=0,s=0,a=!0,u=null,l={};if(!(this instanceof ReconnectingWebsocket))throw new TypeError("Failed to construct 'ReconnectingWebSocket': Please use the 'new' operator");var f=getDefaultOptions();if(Object.keys(f).filter(function(e){return o.hasOwnProperty(e)}).forEach(function(e){return f[e]=o[e]}),!isWebSocket(f.constructor))throw new TypeError("Invalid WebSocket constructor. Set `options.constructor`");var d=f.debug?function(){for(var e=[],n=0;n<arguments.length;n++)e[n]=arguments[n];return console.log.apply(console,["RWS:"].concat(e))}:function(){},v=function(e,n){return setTimeout(function(){var o=new Error(n);o.code=e,Array.isArray(l.error)&&l.error.forEach(function(e){return(0,e[0])(o)}),r.onerror&&r.onerror(o)},0)},y=function(){if(d("handleClose",{shouldRetry:a}),s++,d("retries count:",s),s>f.maxRetries)return void v("EHOSTDOWN","Too many failed connection attempts");i=i?updateReconnectionDelay(f,i):initReconnectionDelay(f),d("handleClose - reconnectDelay:",i),a&&setTimeout(E,i)},E=function(){if(a){d("connect");var o=r,E="function"==typeof e?e():e;r=new f.constructor(E,n),c=setTimeout(function(){d("timeout"),r.close(),v("ETIMEDOUT","Connection timeout")},f.connectionTimeout),d("bypass properties");for(var m in r)["addEventListener","removeEventListener","close","send"].indexOf(m)<0&&bypassProperty(r,t,m);r.addEventListener("open",function(){clearTimeout(c),d("open"),i=initReconnectionDelay(f),d("reconnectDelay:",i),s=0}),r.addEventListener("close",y),reassignEventListeners(r,o,l),r.onclose=r.onclose||u,u=null}};d("init"),E(),this.close=function(e,n,o){void 0===e&&(e=1e3),void 0===n&&(n="");var t=void 0===o?{}:o,c=t.keepClosed,v=void 0!==c&&c,E=t.fastClose,m=void 0===E||E,p=t.delay,b=void 0===p?0:p;if(d("close - params:",{reason:n,keepClosed:v,fastClose:m,delay:b,retriesCount:s,maxRetries:f.maxRetries}),a=!v&&s<=f.maxRetries,b&&(i=b),r.close(e,n),m){var h={code:e,reason:n,wasClean:!0};y(),r.removeEventListener("close",y),Array.isArray(l.close)&&l.close.forEach(function(e){var n=e[0],o=e[1];n(h),r.removeEventListener("close",n,o)}),r.onclose&&(u=r.onclose,r.onclose(h),r.onclose=null)}},this.send=function(e){r.send(e)},this.addEventListener=function(e,n,o){Array.isArray(l[e])?l[e].some(function(e){return e[0]===n})||l[e].push([n,o]):l[e]=[[n,o]],r.addEventListener(e,n,o)},this.removeEventListener=function(e,n,o){Array.isArray(l[e])&&(l[e]=l[e].filter(function(e){return e[0]!==n})),r.removeEventListener(e,n,o)}};module.exports=ReconnectingWebsocket;

/***/ }),
/* 253 */
/***/ (function(module, exports, __webpack_require__) {

function Stream(){EE.call(this)}module.exports=Stream;var EE=__webpack_require__(101).EventEmitter,inherits=__webpack_require__(39);inherits(Stream,EE),Stream.Readable=__webpack_require__(103),Stream.Writable=__webpack_require__(251),Stream.Duplex=__webpack_require__(248),Stream.Transform=__webpack_require__(250),Stream.PassThrough=__webpack_require__(249),Stream.Stream=Stream,Stream.prototype.pipe=function(e,r){function t(r){e.writable&&!1===e.write(r)&&m.pause&&m.pause()}function n(){m.readable&&m.resume&&m.resume()}function a(){u||(u=!0,e.end())}function o(){u||(u=!0,"function"==typeof e.destroy&&e.destroy())}function i(e){if(s(),0===EE.listenerCount(this,"error"))throw e}function s(){m.removeListener("data",t),e.removeListener("drain",n),m.removeListener("end",a),m.removeListener("close",o),m.removeListener("error",i),e.removeListener("error",i),m.removeListener("end",s),m.removeListener("close",s),e.removeListener("close",s)}var m=this;m.on("data",t),e.on("drain",n),e._isStdio||r&&!1===r.end||(m.on("end",a),m.on("close",o));var u=!1;return m.on("error",i),e.on("error",i),m.on("end",s),m.on("close",s),e.on("close",s),e.emit("pipe",m),e};

/***/ }),
/* 254 */,
/* 255 */,
/* 256 */,
/* 257 */,
/* 258 */,
/* 259 */,
/* 260 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
Object.defineProperty(exports,"__esModule",{value:!0});var events_1=__webpack_require__(188),Is=__webpack_require__(189),CancellationToken;!function(e){function t(t){var n=t;return n&&(n===e.None||n===e.Cancelled||Is.boolean(n.isCancellationRequested)&&!!n.onCancellationRequested)}e.None=Object.freeze({isCancellationRequested:!1,onCancellationRequested:events_1.Event.None}),e.Cancelled=Object.freeze({isCancellationRequested:!0,onCancellationRequested:events_1.Event.None}),e.is=t}(CancellationToken=exports.CancellationToken||(exports.CancellationToken={}));var shortcutEvent=Object.freeze(function(e,t){var n=setTimeout(e.bind(t),0);return{dispose:function(){clearTimeout(n)}}}),MutableToken=function(){function e(){this._isCancelled=!1}return e.prototype.cancel=function(){this._isCancelled||(this._isCancelled=!0,this._emitter&&(this._emitter.fire(void 0),this._emitter=void 0))},Object.defineProperty(e.prototype,"isCancellationRequested",{get:function(){return this._isCancelled},enumerable:!0,configurable:!0}),Object.defineProperty(e.prototype,"onCancellationRequested",{get:function(){return this._isCancelled?shortcutEvent:(this._emitter||(this._emitter=new events_1.Emitter),this._emitter.event)},enumerable:!0,configurable:!0}),e}(),CancellationTokenSource=function(){function e(){}return Object.defineProperty(e.prototype,"token",{get:function(){return this._token||(this._token=new MutableToken),this._token},enumerable:!0,configurable:!0}),e.prototype.cancel=function(){this._token?this._token.cancel():this._token=CancellationToken.Cancelled},e.prototype.dispose=function(){this.cancel()},e}();exports.CancellationTokenSource=CancellationTokenSource;

/***/ }),
/* 261 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
Object.defineProperty(exports,"__esModule",{value:!0});var Touch;!function(t){t.None=0,t.First=1,t.Last=2}(Touch=exports.Touch||(exports.Touch={}));var LinkedMap=function(){function t(){this._map=new Map,this._head=void 0,this._tail=void 0,this._size=0}return t.prototype.clear=function(){this._map.clear(),this._head=void 0,this._tail=void 0,this._size=0},t.prototype.isEmpty=function(){return!this._head&&!this._tail},Object.defineProperty(t.prototype,"size",{get:function(){return this._size},enumerable:!0,configurable:!0}),t.prototype.has=function(t){return this._map.has(t)},t.prototype.get=function(t){var i=this._map.get(t);if(i)return i.value},t.prototype.set=function(t,i,e){void 0===e&&(e=Touch.None);var s=this._map.get(t);if(s)s.value=i,e!==Touch.None&&this.touch(s,e);else{switch(s={key:t,value:i,next:void 0,previous:void 0},e){case Touch.None:this.addItemLast(s);break;case Touch.First:this.addItemFirst(s);break;case Touch.Last:default:this.addItemLast(s)}this._map.set(t,s),this._size++}},t.prototype.delete=function(t){var i=this._map.get(t);return!!i&&(this._map.delete(t),this.removeItem(i),this._size--,!0)},t.prototype.shift=function(){if(this._head||this._tail){if(!this._head||!this._tail)throw new Error("Invalid list");var t=this._head;return this._map.delete(t.key),this.removeItem(t),this._size--,t.value}},t.prototype.forEach=function(t,i){for(var e=this._head;e;)i?t.bind(i)(e.value,e.key,this):t(e.value,e.key,this),e=e.next},t.prototype.forEachReverse=function(t,i){for(var e=this._tail;e;)i?t.bind(i)(e.value,e.key,this):t(e.value,e.key,this),e=e.previous},t.prototype.values=function(){for(var t=[],i=this._head;i;)t.push(i.value),i=i.next;return t},t.prototype.keys=function(){for(var t=[],i=this._head;i;)t.push(i.key),i=i.next;return t},t.prototype.addItemFirst=function(t){if(this._head||this._tail){if(!this._head)throw new Error("Invalid list");t.next=this._head,this._head.previous=t}else this._tail=t;this._head=t},t.prototype.addItemLast=function(t){if(this._head||this._tail){if(!this._tail)throw new Error("Invalid list");t.previous=this._tail,this._tail.next=t}else this._head=t;this._tail=t},t.prototype.removeItem=function(t){if(t===this._head&&t===this._tail)this._head=void 0,this._tail=void 0;else if(t===this._head)this._head=t.next;else if(t===this._tail)this._tail=t.previous;else{var i=t.next,e=t.previous;if(!i||!e)throw new Error("Invalid list");i.previous=e,e.next=i}},t.prototype.touch=function(t,i){if(!this._head||!this._tail)throw new Error("Invalid list");if(i===Touch.First||i===Touch.Last)if(i===Touch.First){if(t===this._head)return;var e=t.next,s=t.previous;t===this._tail?(s.next=void 0,this._tail=s):(e.previous=s,s.next=e),t.previous=void 0,t.next=this._head,this._head.previous=t,this._head=t}else if(i===Touch.Last){if(t===this._tail)return;var e=t.next,s=t.previous;t===this._head?(e.previous=void 0,this._head=e):(e.previous=s,s.next=e),t.next=void 0,t.previous=this._tail,this._tail.next=t,this._tail=t}},t}();exports.LinkedMap=LinkedMap;

/***/ }),
/* 262 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
/* WEBPACK VAR INJECTION */(function(process) {function generateRandomPipeName(){var e=crypto_1.randomBytes(21).toString("hex");return"win32"===process.platform?"\\\\.\\pipe\\vscode-jsonrpc-"+e+"-sock":path_1.join(os_1.tmpdir(),"vscode-"+e+".sock")}function createClientPipeTransport(e,r){void 0===r&&(r="utf-8");var t,n=new Promise(function(e,r){t=e});return new Promise(function(o,s){var a=net_1.createServer(function(e){a.close(),t([new messageReader_1.SocketMessageReader(e,r),new messageWriter_1.SocketMessageWriter(e,r)])});a.on("error",s),a.listen(e,function(){a.removeListener("error",s),o({onConnected:function(){return n}})})})}function createServerPipeTransport(e,r){void 0===r&&(r="utf-8");var t=net_1.createConnection(e);return[new messageReader_1.SocketMessageReader(t,r),new messageWriter_1.SocketMessageWriter(t,r)]}Object.defineProperty(exports,"__esModule",{value:!0});var path_1=__webpack_require__(243),os_1=__webpack_require__(242),crypto_1=__webpack_require__(219),net_1=__webpack_require__(219),messageReader_1=__webpack_require__(207),messageWriter_1=__webpack_require__(208);exports.generateRandomPipeName=generateRandomPipeName,exports.createClientPipeTransport=createClientPipeTransport,exports.createServerPipeTransport=createServerPipeTransport;
/* WEBPACK VAR INJECTION */}.call(exports, __webpack_require__(28)))

/***/ }),
/* 263 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
function listen(e){var o=e.webSocket,n=e.onConnection,t=e.logger||new logger_1.ConsoleLogger;o.onopen=function(){var e=toSocket(o),r=socket_1.createWebSocketConnection(e,t);n(r)}}function toSocket(e){return{send:function(o){return e.send(o)},onMessage:function(o){return e.onmessage=function(e){return o(e.data)}},onError:function(o){return e.onerror=function(e){e instanceof ErrorEvent&&o(e.message)}},onClose:function(o){return e.onclose=function(e){return o(e.code,e.reason)}},dispose:function(){return e.close()}}}Object.defineProperty(exports,"__esModule",{value:!0});var socket_1=__webpack_require__(224),logger_1=__webpack_require__(223);exports.listen=listen,exports.toSocket=toSocket;

/***/ }),
/* 264 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
Object.defineProperty(exports,"__esModule",{value:!0});var events_1=__webpack_require__(188);exports.Disposable=events_1.Disposable;var DisposableCollection=function(){function s(){this.disposables=[]}return s.prototype.dispose=function(){for(;0!==this.disposables.length;)this.disposables.pop().dispose()},s.prototype.push=function(s){var e=this.disposables;return e.push(s),{dispose:function(){var o=e.indexOf(s);-1!==o&&e.splice(o,1)}}},s}();exports.DisposableCollection=DisposableCollection;

/***/ }),
/* 265 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
function __export(e){for(var r in e)exports.hasOwnProperty(r)||(exports[r]=e[r])}Object.defineProperty(exports,"__esModule",{value:!0}),__export(__webpack_require__(190)),__export(__webpack_require__(221)),__export(__webpack_require__(264)),__export(__webpack_require__(209)),__export(__webpack_require__(224)),__export(__webpack_require__(223)),__export(__webpack_require__(263));

/***/ }),
/* 266 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
function createWebSocketConnection(e,r){var o=new reader_1.WebSocketMessageReader(e),t=new writer_1.WebSocketMessageWriter(e),n=vscode_jsonrpc_1.createMessageConnection(o,t,r);return n.onClose(function(){return n.dispose()}),n}Object.defineProperty(exports,"__esModule",{value:!0});var vscode_jsonrpc_1=__webpack_require__(190),reader_1=__webpack_require__(225),writer_1=__webpack_require__(226);exports.createWebSocketConnection=createWebSocketConnection;

/***/ }),
/* 267 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
var __extends=this&&this.__extends||function(){var e=Object.setPrototypeOf||{__proto__:[]}instanceof Array&&function(e,r){e.__proto__=r}||function(e,r){for(var t in r)r.hasOwnProperty(t)&&(e[t]=r[t])};return function(r,t){function s(){this.constructor=r}e(r,t),r.prototype=null===t?Object.create(t):(s.prototype=t.prototype,new s)}}();Object.defineProperty(exports,"__esModule",{value:!0});var messageReader_1=__webpack_require__(207),stream_1=__webpack_require__(210),AbstractStreamMessageReader=function(e){function r(){return null!==e&&e.apply(this,arguments)||this}return __extends(r,e),r.prototype.readMessage=function(e,r){var t=this,s=new stream_1.ReadableStream(e),a=new messageReader_1.StreamMessageReader(s);a.onError(function(e){return t.fireError(e)}),a.onClose(function(){return t.fireClose()}),a.onPartialMessage(function(e){return t.firePartialMessage(e)}),a.listen(r)},r}(messageReader_1.AbstractMessageReader);exports.AbstractStreamMessageReader=AbstractStreamMessageReader;

/***/ }),
/* 268 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
var __extends=this&&this.__extends||function(){var e=Object.setPrototypeOf||{__proto__:[]}instanceof Array&&function(e,t){e.__proto__=t}||function(e,t){for(var r in t)t.hasOwnProperty(r)&&(e[r]=t[r])};return function(t,r){function s(){this.constructor=t}e(t,r),t.prototype=null===r?Object.create(r):(s.prototype=r.prototype,new s)}}();Object.defineProperty(exports,"__esModule",{value:!0});var messageWriter_1=__webpack_require__(208),stream_1=__webpack_require__(210),AbstractStreamMessageWriter=function(e){function t(){return null!==e&&e.apply(this,arguments)||this}return __extends(t,e),t.prototype.write=function(e){var t=this.writeMessage(e);this.send(t)},t.prototype.writeMessage=function(e){var t=this,r=new stream_1.WritableStream,s=new messageWriter_1.StreamMessageWriter(r);return s.onError(function(e){return t.fireError(e)}),s.onClose(function(){return t.fireClose()}),s.write(e),r.end(),r.data.toString()},t}(messageWriter_1.AbstractMessageWriter);exports.AbstractStreamMessageWriter=AbstractStreamMessageWriter;

/***/ }),
/* 269 */,
/* 270 */,
/* 271 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
/* --------------------------------------------------------------------------------------------
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for license information.
 * ------------------------------------------------------------------------------------------ */

Object.defineProperty(exports, "__esModule", { value: true });
const protocol_1 = __webpack_require__(191);
const is = __webpack_require__(194);
function createConnection(connection, errorHandler, closeHandler) {
    connection.onError((data) => { errorHandler(data[0], data[1], data[2]); });
    connection.onClose(closeHandler);
    let result = {
        listen: () => connection.listen(),
        sendRequest: (type, ...params) => connection.sendRequest(is.string(type) ? type : type.method, ...params),
        onRequest: (type, handler) => connection.onRequest(is.string(type) ? type : type.method, handler),
        sendNotification: (type, params) => connection.sendNotification(is.string(type) ? type : type.method, params),
        onNotification: (type, handler) => connection.onNotification(is.string(type) ? type : type.method, handler),
        trace: (value, tracer, sendNotification = false) => connection.trace(value, tracer, sendNotification),
        initialize: (params) => connection.sendRequest(protocol_1.InitializeRequest.type, params),
        shutdown: () => connection.sendRequest(protocol_1.ShutdownRequest.type, undefined),
        exit: () => connection.sendNotification(protocol_1.ExitNotification.type),
        onLogMessage: (handler) => connection.onNotification(protocol_1.LogMessageNotification.type, handler),
        onShowMessage: (handler) => connection.onNotification(protocol_1.ShowMessageNotification.type, handler),
        onTelemetry: (handler) => connection.onNotification(protocol_1.TelemetryEventNotification.type, handler),
        didChangeConfiguration: (params) => connection.sendNotification(protocol_1.DidChangeConfigurationNotification.type, params),
        didChangeWatchedFiles: (params) => connection.sendNotification(protocol_1.DidChangeWatchedFilesNotification.type, params),
        didOpenTextDocument: (params) => connection.sendNotification(protocol_1.DidOpenTextDocumentNotification.type, params),
        didChangeTextDocument: (params) => connection.sendNotification(protocol_1.DidChangeTextDocumentNotification.type, params),
        didCloseTextDocument: (params) => connection.sendNotification(protocol_1.DidCloseTextDocumentNotification.type, params),
        didSaveTextDocument: (params) => connection.sendNotification(protocol_1.DidSaveTextDocumentNotification.type, params),
        onDiagnostics: (handler) => connection.onNotification(protocol_1.PublishDiagnosticsNotification.type, handler),
        dispose: () => connection.dispose()
    };
    return result;
}
exports.createConnection = createConnection;


/***/ }),
/* 272 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
/* --------------------------------------------------------------------------------------------
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for license information.
 * ------------------------------------------------------------------------------------------ */

Object.defineProperty(exports, "__esModule", { value: true });
class Delayer {
    constructor(defaultDelay) {
        this.defaultDelay = defaultDelay;
        this.timeout = undefined;
        this.completionPromise = undefined;
        this.onSuccess = undefined;
        this.task = undefined;
    }
    trigger(task, delay = this.defaultDelay) {
        this.task = task;
        if (delay >= 0) {
            this.cancelTimeout();
        }
        if (!this.completionPromise) {
            this.completionPromise = new Promise((resolve) => {
                this.onSuccess = resolve;
            }).then(() => {
                this.completionPromise = undefined;
                this.onSuccess = undefined;
                var result = this.task();
                this.task = undefined;
                return result;
            });
        }
        if (delay >= 0 || this.timeout === void 0) {
            this.timeout = setTimeout(() => {
                this.timeout = undefined;
                this.onSuccess(undefined);
            }, delay >= 0 ? delay : this.defaultDelay);
        }
        return this.completionPromise;
    }
    forceDelivery() {
        if (!this.completionPromise) {
            return undefined;
        }
        this.cancelTimeout();
        let result = this.task();
        this.completionPromise = undefined;
        this.onSuccess = undefined;
        this.task = undefined;
        return result;
    }
    isTriggered() {
        return this.timeout !== void 0;
    }
    cancel() {
        this.cancelTimeout();
        this.completionPromise = undefined;
    }
    cancelTimeout() {
        if (this.timeout !== void 0) {
            clearTimeout(this.timeout);
            this.timeout = undefined;
        }
    }
}
exports.Delayer = Delayer;


/***/ }),
/* 273 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
/*---------------------------------------------------------------------------------------------
 *  Copyright (c) Microsoft Corporation. All rights reserved.
 *  Licensed under the MIT License. See License.txt in the project root for license information.
 *--------------------------------------------------------------------------------------------*/

Object.defineProperty(exports, "__esModule", { value: true });
class ValueUUID {
    constructor(_value) {
        this._value = _value;
        // empty
    }
    asHex() {
        return this._value;
    }
    equals(other) {
        return this.asHex() === other.asHex();
    }
}
class V4UUID extends ValueUUID {
    constructor() {
        super([
            V4UUID._randomHex(),
            V4UUID._randomHex(),
            V4UUID._randomHex(),
            V4UUID._randomHex(),
            V4UUID._randomHex(),
            V4UUID._randomHex(),
            V4UUID._randomHex(),
            V4UUID._randomHex(),
            '-',
            V4UUID._randomHex(),
            V4UUID._randomHex(),
            V4UUID._randomHex(),
            V4UUID._randomHex(),
            '-',
            '4',
            V4UUID._randomHex(),
            V4UUID._randomHex(),
            V4UUID._randomHex(),
            '-',
            V4UUID._oneOf(V4UUID._timeHighBits),
            V4UUID._randomHex(),
            V4UUID._randomHex(),
            V4UUID._randomHex(),
            '-',
            V4UUID._randomHex(),
            V4UUID._randomHex(),
            V4UUID._randomHex(),
            V4UUID._randomHex(),
            V4UUID._randomHex(),
            V4UUID._randomHex(),
            V4UUID._randomHex(),
            V4UUID._randomHex(),
            V4UUID._randomHex(),
            V4UUID._randomHex(),
            V4UUID._randomHex(),
            V4UUID._randomHex(),
        ].join(''));
    }
    static _oneOf(array) {
        return array[Math.floor(array.length * Math.random())];
    }
    static _randomHex() {
        return V4UUID._oneOf(V4UUID._chars);
    }
}
V4UUID._chars = ['0', '1', '2', '3', '4', '5', '6', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'];
V4UUID._timeHighBits = ['8', '9', 'a', 'b'];
/**
 * An empty UUID that contains only zeros.
 */
exports.empty = new ValueUUID('00000000-0000-0000-0000-000000000000');
function v4() {
    return new V4UUID();
}
exports.v4 = v4;
const _UUIDPattern = /^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$/i;
function isUUID(value) {
    return _UUIDPattern.test(value);
}
exports.isUUID = isUUID;
/**
 * Parses a UUID that is of the format xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx.
 * @param value A uuid string.
 */
function parse(value) {
    if (!isUUID(value)) {
        throw new Error('invalid uuid');
    }
    return new ValueUUID(value);
}
exports.parse = parse;
function generateUuid() {
    return v4().asHex();
}
exports.generateUuid = generateUuid;


/***/ })
/******/ ]);
//# sourceMappingURL=elkgraph.bundle.js.map
