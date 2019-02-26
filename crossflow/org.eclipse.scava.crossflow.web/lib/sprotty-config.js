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
var inversify_1 = require("inversify");
var lib_1 = require("sprotty/lib");
var views_1 = require("./views");
var sprotty_model_1 = require("./sprotty-model");
var FilteringSvgExporter = /** @class */ (function (_super) {
    __extends(FilteringSvgExporter, _super);
    function FilteringSvgExporter() {
        return _super !== null && _super.apply(this, arguments) || this;
    }
    FilteringSvgExporter.prototype.isExported = function (styleSheet) {
        return styleSheet.href !== null && (styleSheet.href.endsWith('diagram.css') || styleSheet.href.endsWith('sprotty.css'));
    };
    return FilteringSvgExporter;
}(lib_1.SvgExporter));
exports.default = (function () {
    var elkGraphModule = new inversify_1.ContainerModule(function (bind, unbind, isBound, rebind) {
        rebind(lib_1.TYPES.ILogger).to(lib_1.ConsoleLogger).inSingletonScope();
        rebind(lib_1.TYPES.LogLevel).toConstantValue(lib_1.LogLevel.warn);
        rebind(lib_1.TYPES.IModelFactory).to(lib_1.SGraphFactory).inSingletonScope();
        rebind(lib_1.TYPES.SvgExporter).to(FilteringSvgExporter).inSingletonScope();
        var context = { bind: bind, unbind: unbind, isBound: isBound, rebind: rebind };
        lib_1.configureModelElement(context, 'graph', lib_1.SGraph, lib_1.SGraphView);
        lib_1.configureModelElement(context, 'node', sprotty_model_1.ElkNode, views_1.ElkNodeView);
        lib_1.configureModelElement(context, 'port', sprotty_model_1.ElkPort, views_1.ElkPortView);
        lib_1.configureModelElement(context, 'edge', sprotty_model_1.ElkEdge, views_1.ElkEdgeView);
        lib_1.configureModelElement(context, 'label', lib_1.SLabel, views_1.ElkLabelView);
        lib_1.configureModelElement(context, 'junction', sprotty_model_1.ElkJunction, views_1.JunctionView);
        lib_1.configureViewerOptions(context, {
            needsClientLayout: false
        });
    });
    var container = new inversify_1.Container();
    container.load(lib_1.defaultModule, lib_1.selectModule, lib_1.boundsModule, lib_1.moveModule, lib_1.fadeModule, lib_1.hoverModule, lib_1.viewportModule, lib_1.exportModule, lib_1.edgeEditModule, elkGraphModule);
    return container;
});
//# sourceMappingURL=sprotty-config.js.map