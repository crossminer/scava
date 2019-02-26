"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
var elkgraph_json_1 = require("./elkgraph-json");
var ElkGraphJsonToSprotty = /** @class */ (function () {
    function ElkGraphJsonToSprotty() {
        this.nodeIds = new Set();
        this.edgeIds = new Set();
        this.portIds = new Set();
        this.labelIds = new Set();
        this.sectionIds = new Set();
    }
    ElkGraphJsonToSprotty.prototype.transform = function (elkGraph) {
        var _this = this;
        var sGraph = {
            type: 'graph',
            id: elkGraph.id || 'root',
            children: []
        };
        if (elkGraph.children) {
            var children = elkGraph.children.map(function (n) { return _this.transformElkNode(n); });
            (_a = sGraph.children).push.apply(_a, children);
        }
        if (elkGraph.edges) {
            var sEdges = elkGraph.edges.map(function (e) { return _this.transformElkEdge(e); });
            (_b = sGraph.children).push.apply(_b, sEdges);
        }
        return sGraph;
        var _a, _b;
    };
    ElkGraphJsonToSprotty.prototype.transformElkNode = function (elkNode) {
        var _this = this;
        this.checkAndRememberId(elkNode, this.nodeIds);
        var sNode = {
            type: 'node',
            id: elkNode.id,
            position: this.pos(elkNode),
            size: this.size(elkNode),
            children: []
        };
        // children
        if (elkNode.children) {
            var sNodes = elkNode.children.map(function (n) { return _this.transformElkNode(n); });
            (_a = sNode.children).push.apply(_a, sNodes);
        }
        // ports
        if (elkNode.ports) {
            var sPorts = elkNode.ports.map(function (p) { return _this.transformElkPort(p); });
            (_b = sNode.children).push.apply(_b, sPorts);
        }
        // labels
        if (elkNode.labels) {
            var sLabels = elkNode.labels.map(function (l) { return _this.transformElkLabel(l); });
            (_c = sNode.children).push.apply(_c, sLabels);
        }
        // edges
        if (elkNode.edges) {
            var sEdges = elkNode.edges.map(function (e) { return _this.transformElkEdge(e); });
            (_d = sNode.children).push.apply(_d, sEdges);
        }
        return sNode;
        var _a, _b, _c, _d;
    };
    ElkGraphJsonToSprotty.prototype.transformElkPort = function (elkPort) {
        var _this = this;
        this.checkAndRememberId(elkPort, this.portIds);
        var sPort = {
            type: 'port',
            id: elkPort.id,
            position: this.pos(elkPort),
            size: this.size(elkPort),
            children: []
        };
        // labels
        if (elkPort.labels) {
            var sLabels = elkPort.labels.map(function (l) { return _this.transformElkLabel(l); });
            (_a = sPort.children).push.apply(_a, sLabels);
        }
        return sPort;
        var _a;
    };
    ElkGraphJsonToSprotty.prototype.transformElkLabel = function (elkLabel) {
        this.checkAndRememberId(elkLabel, this.labelIds);
        return {
            type: 'label',
            id: elkLabel.id,
            text: elkLabel.text,
            position: this.pos(elkLabel),
            size: this.size(elkLabel)
        };
    };
    ElkGraphJsonToSprotty.prototype.transformElkEdge = function (elkEdge) {
        var _this = this;
        this.checkAndRememberId(elkEdge, this.edgeIds);
        var sEdge = {
            type: 'edge',
            id: elkEdge.id,
            sourceId: '',
            targetId: '',
            routingPoints: [],
            children: []
        };
        if (elkgraph_json_1.isPrimitive(elkEdge)) {
            sEdge.sourceId = elkEdge.source;
            sEdge.targetId = elkEdge.target;
            if (elkEdge.sourcePoint)
                sEdge.routingPoints.push(elkEdge.sourcePoint);
            if (elkEdge.bendPoints)
                (_a = sEdge.routingPoints).push.apply(_a, elkEdge.bendPoints);
            if (elkEdge.targetPoint)
                sEdge.routingPoints.push(elkEdge.targetPoint);
        }
        else if (elkgraph_json_1.isExtended(elkEdge)) {
            sEdge.sourceId = elkEdge.sources[0];
            sEdge.targetId = elkEdge.targets[0];
            if (elkEdge.sections) {
                elkEdge.sections.forEach(function (section) {
                    _this.checkAndRememberId(section, _this.sectionIds);
                    sEdge.routingPoints.push(section.startPoint);
                    if (section.bendPoints) {
                        (_a = sEdge.routingPoints).push.apply(_a, section.bendPoints);
                    }
                    sEdge.routingPoints.push(section.endPoint);
                    var _a;
                });
            }
        }
        if (elkEdge.junctionPoints) {
            elkEdge.junctionPoints.forEach(function (jp, i) {
                var sJunction = {
                    type: 'junction',
                    id: elkEdge.id + "_j" + i,
                    position: jp
                };
                sEdge.children.push(sJunction);
            });
        }
        // TODO labels
        return sEdge;
        var _a;
    };
    ElkGraphJsonToSprotty.prototype.pos = function (elkShape) {
        return { x: elkShape.x || 0, y: elkShape.y || 0 };
    };
    ElkGraphJsonToSprotty.prototype.size = function (elkShape) {
        return { width: elkShape.width || 0, height: elkShape.height || 0 };
    };
    ElkGraphJsonToSprotty.prototype.checkAndRememberId = function (e, set) {
        if (e.id == undefined) {
            throw Error("An element is missing an id.");
        }
        else if (set.has(e.id)) {
            throw Error("Duplicate id: " + e.id + ".");
        }
        else {
            set.add(e.id);
        }
    };
    return ElkGraphJsonToSprotty;
}());
exports.ElkGraphJsonToSprotty = ElkGraphJsonToSprotty;
//# sourceMappingURL=elkgraph-to-sprotty.js.map