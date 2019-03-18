"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
/*******************************************************************************
 * Copyright (c) 2017 Kiel University and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
require("reflect-metadata");
var lib_1 = require("sprotty/lib");
var elkgraph_to_sprotty_1 = require("../json/elkgraph-to-sprotty");
var sprotty_config_1 = require("../sprotty-config");
var url_parameters_1 = require("../url-parameters");
var elk_api_js_1 = require("elkjs/lib/elk-api.js");
var https = require("https");
var $ = require("jquery");
var JSON5 = require("json5");
require('devbridge-autocomplete');
var urlParameters = url_parameters_1.getParameters();
var githubOwner = 'eclipse';
var githubRepo = 'elk-models';
// Create Sprotty viewer
var sprottyContainer = sprotty_config_1.default();
sprottyContainer.bind(lib_1.TYPES.ModelSource).to(lib_1.LocalModelSource).inSingletonScope();
var modelSource = sprottyContainer.get(lib_1.TYPES.ModelSource);
var actionDispatcher = sprottyContainer.get(lib_1.TYPES.IActionDispatcher);
// Set up ELK
var elk = new elk_api_js_1.default({
    workerUrl: './elk/elk-worker.min.js'
});
// Div with loading indicator
var loading = document.getElementById('loading');
function setLoading(load) {
    if (load) {
        loading.style.display = 'block';
    }
    else {
        loading.style.display = 'none';
    }
}
// Div to show errors
var errorDiv = document.getElementById('error');
function showError(err) {
    if (err && err.message) {
        errorDiv.innerHTML = err.message;
    }
    else {
        errorDiv.innerHTML = "A problem ocurred while loading the model.";
    }
    errorDiv.style.display = 'block';
}
function updateSprottyModel(graph) {
    var sGraph = new elkgraph_to_sprotty_1.ElkGraphJsonToSprotty().transform(graph);
    modelSource.setModel(sGraph);
    actionDispatcher.dispatch(new lib_1.FitToScreenAction([]));
}
function loadModel(path) {
    setLoading(true);
    errorDiv.style.display = 'none';
    getFileContent(path)
        .then(function (g) { return elk.layout(g); })
        .then(updateSprottyModel)
        .then(function () {
        var encodedPath = encodeURIComponent(path);
        var queryString = url_parameters_1.combineParameters({ link: encodedPath, owner: githubOwner, repo: githubRepo });
        window.history.pushState("", "", queryString);
    })
        .then(function () { return setLoading(false); })
        .catch(function (err) {
        setLoading(false);
        if (err) {
            console.error(err);
            showError(err);
        }
    });
}
// Initial model
var currentModel = '';
if (urlParameters.link) {
    currentModel = decodeURIComponent(urlParameters.link);
    // not yet supported
    //githubOwner = owner || githubOwner
    //githubRepo = repo || githubRepo
    $('#autocomplete').val(currentModel);
    loadModel(currentModel);
}
function initAutocomplete(files) {
    $('#autocomplete').autocomplete({
        lookup: files,
        minChars: 0,
        onSelect: function (suggestion) {
            var path = suggestion.value;
            if (currentModel != path) {
                currentModel = path;
                loadModel(currentModel);
            }
        }
    });
}
// Request contents of the models repository
getContentsRecursively('')
    .then(function (data) {
    var files = collectFiles(data);
    initAutocomplete(files);
})
    .catch(function (err) { return showError(err); });
function refreshLayout() {
    $('#sprotty').css('top', $('#navbar').height() + 'px');
}
$(window).resize(refreshLayout);
$(document).ready(setTimeout(refreshLayout, 50));
function githubRequest(path) {
    return {
        host: 'api.github.com',
        path: '/repos/' + githubOwner + '/' + githubRepo + '/contents/' + path,
        headers: {
            'User-Agent': 'elk-models-viewer'
        }
    };
}
function asyncGet(req) {
    return new Promise(function (resolve, reject) {
        https.get(req, function (response) {
            if (response.statusCode !== 200) {
                reject(new Error("Request failed with code: " + response.statusCode));
            }
            response.setEncoding('utf8');
            var body = '';
            response.on('data', function (c) { return body += c; });
            response.on('end', function () {
                try {
                    resolve(JSON5.parse(body));
                }
                catch (e) {
                    reject(e);
                }
            });
            response.on('error', reject);
        }).on('error', reject);
    });
}
function getContentsRecursively(parentDir) {
    var path = parentDir.path || '';
    return asyncGet(githubRequest(path))
        .then(function (response) {
        if (!Array.isArray(response)) {
            return Promise.reject(new Error("Unexpected response: " + response + "."));
        }
        var dir = {
            name: parentDir.name || '/',
            path: path,
            files: response.filter(function (e) { return e.type == 'file'; }).map(function (f) {
                return {
                    name: f.name,
                    path: f.path
                };
            })
        };
        return Promise.all(response.filter(function (e) { return e.type == 'dir'; }).map(function (d) {
            return getContentsRecursively(d)
                .then(function (subdir) {
                return subdir;
            });
        })).then(function (subDirs) {
            dir.dirs = subDirs;
            return dir;
        });
    });
}
function getFileContent(filePath) {
    return asyncGet(githubRequest(filePath))
        .then(function (response) {
        return new Promise(function (resolve, reject) {
            try {
                var buf = Buffer.from(response.content, 'base64');
                resolve(JSON5.parse(buf.toString()));
            }
            catch (err) {
                reject(err);
            }
        });
    });
}
function collectFiles(dir) {
    return (_a = (dir.files || [])
        .filter(function (f) { return f.path.endsWith('.json'); })
        .map(function (f) { return { value: f.path, data: f.path }; })).concat.apply(_a, (dir.dirs || []).map(function (sd) { return collectFiles(sd); }));
    var _a;
}
//# sourceMappingURL=main.js.map