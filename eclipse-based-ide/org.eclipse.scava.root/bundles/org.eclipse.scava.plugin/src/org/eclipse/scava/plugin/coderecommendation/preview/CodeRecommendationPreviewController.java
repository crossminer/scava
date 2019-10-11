/*********************************************************************
* Copyright (c) 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.coderecommendation.preview;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.scava.plugin.coderecommendation.IPreviewable;
import org.eclipse.scava.plugin.coderecommendation.results.CodeRecommendationDroppedEvent;
import org.eclipse.scava.plugin.mvc.controller.Controller;
import org.eclipse.scava.plugin.mvc.controller.ModelViewController;
import org.eclipse.scava.plugin.mvc.event.routed.IRoutedEvent;
import org.eclipse.swt.widgets.Display;

public class CodeRecommendationPreviewController
		extends ModelViewController<CodeRecommendationPreviewModel, CodeRecommendationPreviewView>
		implements ICodeRecommendationPreviewViewEventListener {

	private IDocument activeEditorDocument;
	private ITextSelection activeEditorSelection;

	public CodeRecommendationPreviewController(Controller parent, CodeRecommendationPreviewModel model,
			CodeRecommendationPreviewView view) {
		super(parent, model, view);
	}

	@Override
	public void init() {
		super.init();

		IPreviewable previewable = getModel().getPreviewable();
		getView().setTitle(previewable.getPreviewTitle());
		getView().setContent(previewable.getPreviewContent());
		getView().setShowInsertAtCursor(previewable.canBeInserted());
		getView().setEnableInserAtCursor(false);
	}

	@Override
	public void requestViewClose() {
		dispose();
	}

	@Override
	protected void onReceiveRoutedEventFromParentController(IRoutedEvent routedEvent) {

		if (routedEvent instanceof EnableCodeInsertionEvent) {
			EnableCodeInsertionEvent event = (EnableCodeInsertionEvent) routedEvent;

			getView().setEnableInserAtCursor(true);
			activeEditorDocument = event.getDocument();
			activeEditorSelection = event.getSelection();

			return;
		}

		if (routedEvent instanceof DisableCodeInsertionEvent) {
			DisableCodeInsertionEvent event = (DisableCodeInsertionEvent) routedEvent;

			getView().setEnableInserAtCursor(false);
			activeEditorDocument = null;
			activeEditorSelection = null;

			return;
		}

		if (routedEvent instanceof CodeRecommendationDroppedEvent) {
			CodeRecommendationDroppedEvent event = (CodeRecommendationDroppedEvent) routedEvent;

			if (event.getCodeRecommendationElements().contains(getModel().getPreviewable())) {
				dispose();
			}

			return;
		}

		super.onReceiveRoutedEventFromParentController(routedEvent);
	}

	@Override
	public void onInsertAtCursor() {
		if (activeEditorDocument == null || activeEditorSelection == null
				|| !getModel().getPreviewable().canBeInserted()) {
			return;
		}

		try {
			String codeLines = getModel().getPreviewable().getPreviewContent();
			activeEditorDocument.replace(activeEditorSelection.getOffset(), activeEditorSelection.getLength(),
					codeLines);
		} catch (BadLocationException e) {
			e.printStackTrace();
			MessageDialog.openError(Display.getDefault().getActiveShell(), "Error",
					"Unexpected error during code insertion:\n" + e);
		}
	}
}
