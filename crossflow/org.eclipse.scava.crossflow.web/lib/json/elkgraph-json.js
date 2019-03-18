"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
function isPrimitive(edge) {
    return edge.source !== undefined && edge.target !== undefined;
}
exports.isPrimitive = isPrimitive;
function isExtended(edge) {
    return edge.sources !== undefined && edge.targets !== undefined;
}
exports.isExtended = isExtended;
//# sourceMappingURL=elkgraph-json.js.map