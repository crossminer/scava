/// <reference types="monaco-editor-core/monaco" />
/*******************************************************************************
 * Copyright (c) 2017 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
import ICodeEditor = monaco.editor.ICodeEditor;
import IModelContentChangedEvent = monaco.editor.IModelContentChangedEvent;
export declare function getParameters(): {
    [key: string]: string;
};
export declare function setupModelLink(editor: ICodeEditor, getParameters: (event: IModelContentChangedEvent) => {
    [key: string]: string;
}): void;
export declare function assembleHref(parameters: {
    [key: string]: string;
}): string;
export declare function combineParameters(parameters: {
    [key: string]: string;
}): string;
