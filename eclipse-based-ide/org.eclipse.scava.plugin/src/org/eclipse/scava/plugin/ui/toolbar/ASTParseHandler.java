/*********************************************************************
* Copyright (c) 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.ui.toolbar;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.scava.commons.context.sourcecode.SourceCodeContext;
import org.eclipse.scava.commons.json.Json;
import org.eclipse.scava.plugin.context.sourcecodestatus.SourceCodeStatusDetector;
import org.eclipse.scava.plugin.context.sourcecodestatus.SourceCodeStatusException;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.texteditor.ITextEditor;

/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class ASTParseHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {

		System.out.println(requestRecommendation(event));

		return null;
	}
	
	private void saveWorkbenchPage(){
		IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		IEditorPart editorpart = page.getActiveEditor();
		page.saveEditor(editorpart, false);
		
	}
	
	private String requestRecommendation(ExecutionEvent event){
		saveWorkbenchPage();
		
		ITextEditor editor = (ITextEditor) HandlerUtil.getActiveEditor(event);
        ITextSelection sel  = (ITextSelection) editor.getSelectionProvider().getSelection();
        IEditorInput input = editor.getEditorInput();
        
        if( input instanceof IFileEditorInput)
        {
        	IFile file = ((IFileEditorInput) input).getFile();
			try {
				SourceCodeContext info = SourceCodeStatusDetector.getSourceCodeContext(file, sel.getOffset(), sel.getLength());
	        	return Json.toJson(info);
			} catch (SourceCodeStatusException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
		return null;
	}
	
}
