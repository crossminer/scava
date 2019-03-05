"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
/*******************************************************************************
 * Copyright (c) 2017 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
require("reflect-metadata");
var vscode_ws_jsonrpc_1 = require("vscode-ws-jsonrpc");
var monaco_languageclient_1 = require("monaco-languageclient");
var lib_1 = require("sprotty/lib");
var url_parameters_1 = require("../url-parameters");
var sprotty_config_1 = require("../sprotty-config");
var language_diagram_server_1 = require("./language-diagram-server");
var LZString = require("lz-string");
require('./elkt-language');
var WebSocket = require('reconnecting-websocket');
var urlParameters = url_parameters_1.getParameters();
var initialContent;
if (urlParameters.compressedContent !== undefined) {
    initialContent = LZString.decompressFromEncodedURIComponent(urlParameters.compressedContent);
}
else if (urlParameters.initialContent !== undefined) {
    initialContent = decodeURIComponent(urlParameters.initialContent);
}
else {
    initialContent = "";
}
// Create Sprotty viewer
var sprottyContainer = sprotty_config_1.default();
sprottyContainer.bind(lib_1.TYPES.ModelSource).to(language_diagram_server_1.default).inSingletonScope();
var diagramServer = sprottyContainer.get(lib_1.TYPES.ModelSource);
// Create Monaco editor
var modelUri = 'inmemory:/model.elkt';
var editor = monaco.editor.create(document.getElementById('monaco-editor'), {
    model: monaco.editor.createModel(initialContent, 'elkt', monaco.Uri.parse(modelUri))
});
editor.updateOptions({
    minimap: { enabled: false }
});
url_parameters_1.setupModelLink(editor, function (event) {
    return {
        compressedContent: LZString.compressToEncodedURIComponent(editor.getValue())
    };
});
// Create the web socket
// const socketUrl = `${location.protocol === 'https:' ? 'wss' : 'ws'}://${location.host}/elkgraph`
var socketUrl = (location.protocol === 'https:' ? 'wss' : 'ws') + "://localhost:9090/elkgraph";
var socketOptions = {
    maxReconnectionDelay: 10000,
    minReconnectionDelay: 1000,
    reconnectionDelayGrowFactor: 1.3,
    connectionTimeout: 10000,
    maxRetries: Infinity,
    debug: false
};
var webSocket = new WebSocket(socketUrl, undefined, socketOptions);
var services = monaco_languageclient_1.createMonacoServices(editor);
vscode_ws_jsonrpc_1.listen({
    webSocket: webSocket,
    onConnection: function (connection) {
        var languageClient = createLanguageClient(connection);
        var disposable = languageClient.start();
        connection.onClose(function () {
            diagramServer.disconnect();
            disposable.dispose();
        });
    }
});
function createLanguageClient(messageConnection) {
    return new monaco_languageclient_1.BaseLanguageClient({
        name: 'ELK Graph Language Client',
        clientOptions: {
            documentSelector: ['elkt'],
            // Disable the default error handler
            errorHandler: {
                error: function () { return monaco_languageclient_1.ErrorAction.Continue; },
                closed: function () { return monaco_languageclient_1.CloseAction.DoNotRestart; }
            }
        },
        services: services,
        // Create a language client connection from the JSON RPC connection on demand
        connectionProvider: {
            get: function (errorHandler, closeHandler) {
                var connection = monaco_languageclient_1.createConnection(messageConnection, errorHandler, closeHandler);
                diagramServer.listen(connection);
                return Promise.resolve(connection);
            }
        }
    });
}
//# sourceMappingURL=editor.js.map