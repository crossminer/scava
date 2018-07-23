/*********************************************************************
* Copyright (c) 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
*
* Contributors:
*    Zsolt János Szamosvölgyi
*    Endre Tamás Váradi
*    Gergő Balogh
**********************************************************************/
package org.crossmeter.plugin.usermonitoring.event.part;

import java.awt.Event;

import javax.swing.JToolBar.Separator;

import org.crossmeter.plugin.usermonitoring.event.EventManager;
import org.crossmeter.plugin.usermonitoring.event.IEventListener;
import org.crossmeter.plugin.usermonitoring.event.document.DocumentEventListener;
import org.crossmeter.plugin.usermonitoring.event.element.ResourceElementEventListener;
import org.eclipse.jface.action.ControlContribution;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.IContributionManager;
import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.jface.text.IDocument;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.CoolBar;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IPartListener2;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.eclipse.ui.texteditor.ITextEditor;
import org.neo4j.cypher.internal.compiler.v2_3.ast.rewriters.reattachAliasedExpressions;

public class PartEventListener implements IEventListener, IPartListener2 {

	public PartEventListener() {

	}

	@Override
	public void partActivated(IWorkbenchPartReference partRef) {
		EventManager.triggerEvent(new PartEvent(partRef, PartEventType.ACTIVATED));
		

	}

	@Override
	public void partBroughtToTop(IWorkbenchPartReference partRef) {
		EventManager.triggerEvent(new PartEvent(partRef, PartEventType.BROUGHT_TO_TOP));

	}

	@Override
	public void partClosed(IWorkbenchPartReference partRef) {
		EventManager.triggerEvent(new PartEvent(partRef, PartEventType.CLOSED));

	}

	@Override
	public void partDeactivated(IWorkbenchPartReference partRef) {
		EventManager.triggerEvent(new PartEvent(partRef, PartEventType.DEACTIVATED));

	}

	@Override
	public void partOpened(IWorkbenchPartReference partRef) {
		EventManager.triggerEvent(new PartEvent(partRef, PartEventType.OPENED));
		subscribeDocumentEventListener(partRef);
	}

	@Override
	public void partHidden(IWorkbenchPartReference partRef) {
		EventManager.triggerEvent(new PartEvent(partRef, PartEventType.HIDDEN));

	}

	@Override
	public void partVisible(IWorkbenchPartReference partRef) {
		EventManager.triggerEvent(new PartEvent(partRef, PartEventType.VISIBLE));

	}

	@Override
	public void partInputChanged(IWorkbenchPartReference partRef) {
		EventManager.triggerEvent(new PartEvent(partRef, PartEventType.INPUT_CHANGED));

	}

	private void subscribeDocumentEventListener(IWorkbenchPartReference partRef) {
		IWorkbenchPart part = partRef.getPart(false);
		if (part instanceof IEditorPart) {
			IEditorPart editor = (IEditorPart) part;
			IEditorInput input = editor.getEditorInput();

			if (editor instanceof ITextEditor && input instanceof FileEditorInput) 
			{
				ITextEditor textEditor = (ITextEditor) editor;
				
				
				EventManager.setEditor(textEditor);
				
				saveListener(textEditor);
				
				
				IDocument document = textEditor.getDocumentProvider().getDocument(input);
				
				

				DocumentEventListener documentListener = new DocumentEventListener(textEditor.getTitle());
				document.addDocumentListener(documentListener);
			}
		}
	}
	
	private void saveListener(ITextEditor textEditor) {
		IDocumentProvider provider= textEditor.getDocumentProvider();
		IEditorInput input= textEditor.getEditorInput();
		
		provider.addElementStateListener(new ResourceElementEventListener(input));
	}

}
