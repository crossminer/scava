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
var snabbdom = require("snabbdom-jsx");
var lib_1 = require("sprotty/lib");
var JSX = { createElement: snabbdom.svg };
var ElkNodeView = /** @class */ (function (_super) {
    __extends(ElkNodeView, _super);
    function ElkNodeView() {
        return _super !== null && _super.apply(this, arguments) || this;
    }
    ElkNodeView.prototype.render = function (node, context) {
        return JSX.createElement("g", null,
            JSX.createElement("rect", { "class-elknode": true, "class-mouseover": node.hoverFeedback, "class-selected": node.selected, x: "0", y: "0", width: node.bounds.width, height: node.bounds.height }),
            context.renderChildren(node));
    };
    return ElkNodeView;
}(lib_1.RectangularNodeView));
exports.ElkNodeView = ElkNodeView;
var ElkPortView = /** @class */ (function (_super) {
    __extends(ElkPortView, _super);
    function ElkPortView() {
        return _super !== null && _super.apply(this, arguments) || this;
    }
    ElkPortView.prototype.render = function (port, context) {
        return JSX.createElement("g", null,
            JSX.createElement("rect", { "class-elkport": true, "class-mouseover": port.hoverFeedback, "class-selected": port.selected, x: "0", y: "0", width: port.bounds.width, height: port.bounds.height }),
            context.renderChildren(port));
    };
    return ElkPortView;
}(lib_1.RectangularNodeView));
exports.ElkPortView = ElkPortView;
var ElkEdgeView = /** @class */ (function (_super) {
    __extends(ElkEdgeView, _super);
    function ElkEdgeView() {
        return _super !== null && _super.apply(this, arguments) || this;
    }
    ElkEdgeView.prototype.renderLine = function (edge, segments, context) {
        var firstPoint = segments[0];
        var path = "M " + firstPoint.x + "," + firstPoint.y;
        for (var i = 1; i < segments.length; i++) {
            var p = segments[i];
            path += " L " + p.x + "," + p.y;
        }
        return JSX.createElement("path", { "class-elkedge": true, d: path });
    };
    ElkEdgeView.prototype.renderAdditionals = function (edge, segments, context) {
        var p1 = segments[segments.length - 2];
        var p2 = segments[segments.length - 1];
        return [
            JSX.createElement("path", { "class-edge": true, "class-arrow": true, d: "M 0,0 L 8,-3 L 8,3 Z", transform: "rotate(" + lib_1.toDegrees(lib_1.angleOfPoint({ x: p1.x - p2.x, y: p1.y - p2.y })) + " " + p2.x + " " + p2.y + ") translate(" + p2.x + " " + p2.y + ")" })
        ];
    };
    return ElkEdgeView;
}(lib_1.PolylineEdgeView));
exports.ElkEdgeView = ElkEdgeView;
var ElkLabelView = /** @class */ (function () {
    function ElkLabelView() {
    }
    ElkLabelView.prototype.render = function (label, context) {
        return JSX.createElement("text", { "class-elklabel": true }, label.text);
    };
    return ElkLabelView;
}());
exports.ElkLabelView = ElkLabelView;
var JunctionView = /** @class */ (function (_super) {
    __extends(JunctionView, _super);
    function JunctionView() {
        return _super !== null && _super.apply(this, arguments) || this;
    }
    JunctionView.prototype.render = function (node, context) {
        var radius = this.getRadius(node);
        return JSX.createElement("g", null,
            JSX.createElement("circle", { "class-elkjunction": true, r: radius }));
    };
    JunctionView.prototype.getRadius = function (node) {
        return 2;
    };
    return JunctionView;
}(lib_1.CircularNodeView));
exports.JunctionView = JunctionView;
//# sourceMappingURL=views.js.map