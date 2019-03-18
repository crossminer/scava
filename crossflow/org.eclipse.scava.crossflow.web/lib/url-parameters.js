"use strict";
/*******************************************************************************
 * Copyright (c) 2017 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
Object.defineProperty(exports, "__esModule", { value: true });
function getParameters() {
    var search = window.location.search.substring(1);
    var result = {};
    while (search.length > 0) {
        var nextParamIndex = search.indexOf('&');
        var param = void 0;
        if (nextParamIndex < 0) {
            param = search;
            search = '';
        }
        else {
            param = search.substring(0, nextParamIndex);
            search = search.substring(nextParamIndex + 1);
        }
        var valueIndex = param.indexOf('=');
        if (valueIndex > 0 && valueIndex < param.length - 1) {
            result[param.substring(0, valueIndex)] = param.substring(valueIndex + 1);
        }
    }
    return result;
}
exports.getParameters = getParameters;
function setupModelLink(editor, getParameters) {
    var contentChangeTimeout;
    var listener = function (event) {
        if (contentChangeTimeout !== undefined) {
            window.clearTimeout(contentChangeTimeout);
        }
        contentChangeTimeout = window.setTimeout(function () {
            var anchor = document.getElementById('model-link');
            if (anchor !== null) {
                var parameters = getParameters(event);
                var newHref = assembleHref(parameters);
                anchor.setAttribute('href', newHref);
            }
        }, 400);
    };
    editor.onDidChangeModelContent(listener);
    listener({});
}
exports.setupModelLink = setupModelLink;
function assembleHref(parameters) {
    var newHref = window.location.href;
    var queryIndex = newHref.indexOf('?');
    if (queryIndex > 0)
        newHref = newHref.substring(0, queryIndex);
    newHref += combineParameters(parameters);
    return newHref;
}
exports.assembleHref = assembleHref;
function combineParameters(parameters) {
    var result = '';
    var i = 0;
    for (var param in parameters) {
        result += "" + (i === 0 ? '?' : '&') + param + "=" + parameters[param];
        i++;
    }
    return result;
}
exports.combineParameters = combineParameters;
//# sourceMappingURL=url-parameters.js.map