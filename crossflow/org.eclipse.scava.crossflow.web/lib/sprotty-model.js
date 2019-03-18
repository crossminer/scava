"use strict";
var __extends = (this && this.__extends) || (function () {
    var extendStatics = Object.setPrototypeOf ||
        ({ __proto__: [] } instanceof Array && function (d, b) { d.__proto__ = b; }) ||
        function (d, b) { for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p]; };
    return function (d, b) {
        extendStatics(d, b);
        function __() { this.constructor = d; }
        d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
    };
})();
Object.defineProperty(exports, "__esModule", { value: true });
/*******************************************************************************
 * Copyright (c) 2017 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
var lib_1 = require("sprotty/lib");
var ElkNode = /** @class */ (function (_super) {
    __extends(ElkNode, _super);
    function ElkNode() {
        return _super !== null && _super.apply(this, arguments) || this;
    }
    ElkNode.prototype.hasFeature = function (feature) {
        if (feature === lib_1.moveFeature)
            return false;
        else
            return _super.prototype.hasFeature.call(this, feature);
    };
    return ElkNode;
}(lib_1.RectangularNode));
exports.ElkNode = ElkNode;
var ElkPort = /** @class */ (function (_super) {
    __extends(ElkPort, _super);
    function ElkPort() {
        return _super !== null && _super.apply(this, arguments) || this;
    }
    ElkPort.prototype.hasFeature = function (feature) {
        if (feature === lib_1.moveFeature)
            return false;
        else
            return _super.prototype.hasFeature.call(this, feature);
    };
    return ElkPort;
}(lib_1.RectangularPort));
exports.ElkPort = ElkPort;
var ElkEdge = /** @class */ (function (_super) {
    __extends(ElkEdge, _super);
    function ElkEdge() {
        return _super !== null && _super.apply(this, arguments) || this;
    }
    ElkEdge.prototype.hasFeature = function (feature) {
        if (feature === lib_1.editFeature)
            return false;
        else
            return _super.prototype.hasFeature.call(this, feature);
    };
    return ElkEdge;
}(lib_1.SEdge));
exports.ElkEdge = ElkEdge;
var ElkJunction = /** @class */ (function (_super) {
    __extends(ElkJunction, _super);
    function ElkJunction() {
        return _super !== null && _super.apply(this, arguments) || this;
    }
    ElkJunction.prototype.hasFeature = function (feature) {
        if (feature === lib_1.moveFeature || feature === lib_1.selectFeature || feature === lib_1.hoverFeedbackFeature)
            return false;
        else
            return _super.prototype.hasFeature.call(this, feature);
    };
    return ElkJunction;
}(lib_1.SNode));
exports.ElkJunction = ElkJunction;
//# sourceMappingURL=sprotty-model.js.map