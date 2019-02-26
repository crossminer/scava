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
var lib_1 = require("sprotty/lib");
var url_parameters_1 = require("../url-parameters");
var sprotty_config_1 = require("../sprotty-config");
var elkgraph_to_sprotty_1 = require("./elkgraph-to-sprotty");
var elk_api_1 = require("elkjs/lib/elk-api");
var JSON5 = require("json5");
var LZString = require("lz-string");
var urlParameters = url_parameters_1.getParameters();
var initialContent;
if (urlParameters.compressedContent !== undefined) {
    initialContent = LZString.decompressFromEncodedURIComponent(urlParameters.compressedContent);
}
else if (urlParameters.initialContent !== undefined) {
    initialContent = decodeURIComponent(urlParameters.initialContent);
}
else {
    initialContent = "{\n  id: \"root\",\n  properties: { 'algorithm': 'layered' },\n  children: [\n    { id: \"n1\", width: 30, height: 30 },\n    { id: \"n2\", width: 30, height: 30 },\n    { id: \"n3\", width: 30, height: 30 }\n  ],\n  edges: [\n    { id: \"e1\", sources: [ \"n1\" ], targets: [ \"n2\" ] },\n    { id: \"e2\", sources: [ \"n1\" ], targets: [ \"n3\" ] } \n  ]\n}";
}
// Create Monaco editor
monaco.languages.register({
    id: 'json',
    extensions: ['.json'],
    aliases: ['JSON', 'json'],
    mimetypes: ['application/json'],
});
var editor = monaco.editor.create(document.getElementById('monaco-editor'), {
    model: monaco.editor.createModel(initialContent, 'json', monaco.Uri.parse('inmemory:/model.json'))
});
editor.updateOptions({
    minimap: { enabled: false }
});
// Create Sprotty viewer
var sprottyContainer = sprotty_config_1.default();
sprottyContainer.bind(lib_1.TYPES.ModelSource).to(lib_1.LocalModelSource).inSingletonScope();
var modelSource = sprottyContainer.get(lib_1.TYPES.ModelSource);
// Set up ELK
var elk = new elk_api_1.default({
    workerUrl: './elk/elk-worker.min.js'
});
// Register listener
editor.getModel().onDidChangeContent(function (e) { return updateModel(); });
// Initial layout
updateModel();
url_parameters_1.setupModelLink(editor, function (event) {
    return {
        compressedContent: LZString.compressToEncodedURIComponent(editor.getValue())
    };
});
function updateModel() {
    try {
        var json = JSON5.parse(editor.getValue());
        monaco.editor.setModelMarkers(editor.getModel(), "", []);
        elk.layout(json)
            .then(function (g) {
            var sGraph = new elkgraph_to_sprotty_1.ElkGraphJsonToSprotty().transform(g);
            modelSource.updateModel(sGraph);
        })
            .catch(function (e) {
            var markers = [errorToMarker(e)];
            monaco.editor.setModelMarkers(editor.getModel(), "", markers);
        });
    }
    catch (e) {
        var markers = [errorToMarker(e)];
        monaco.editor.setModelMarkers(editor.getModel(), "", markers);
    }
}
function errorToMarker(e) {
    return {
        severity: monaco.Severity.Error,
        startLineNumber: e.lineNumber || 0,
        startColumn: e.columnNumber || 0,
        message: e.message
    };
}
//# sourceMappingURL=editor.js.map