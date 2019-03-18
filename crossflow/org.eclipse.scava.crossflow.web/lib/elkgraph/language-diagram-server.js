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
var lib_1 = require("sprotty/lib");
var DIAGRAM_ENDPOINT_NOTIFICATION = 'diagram/accept';
var DID_CLOSE_NOTIFICATION = 'diagram/didClose';
var LanguageDiagramServer = /** @class */ (function (_super) {
    __extends(LanguageDiagramServer, _super);
    function LanguageDiagramServer() {
        return _super !== null && _super.apply(this, arguments) || this;
    }
    LanguageDiagramServer.prototype.listen = function (connection) {
        var _this = this;
        connection.onNotification(DIAGRAM_ENDPOINT_NOTIFICATION, function (message) {
            _this.messageReceived(message);
        });
        this.connection = connection;
    };
    LanguageDiagramServer.prototype.disconnect = function () {
        if (this.connection !== undefined) {
            this.connection.sendNotification(DID_CLOSE_NOTIFICATION, this.clientId);
            this.connection = undefined;
        }
    };
    LanguageDiagramServer.prototype.sendMessage = function (message) {
        if (this.connection !== undefined) {
            this.connection.sendNotification(DIAGRAM_ENDPOINT_NOTIFICATION, message);
        }
    };
    return LanguageDiagramServer;
}(lib_1.DiagramServer));
exports.default = LanguageDiagramServer;
//# sourceMappingURL=language-diagram-server.js.map