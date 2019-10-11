/*********************************************************************
* Copyright (c) 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.coderecommendation;

import java.util.Collection;
import java.util.Date;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.scava.plugin.coderecommendation.preview.CodeRecommendationPreviewController;
import org.eclipse.scava.plugin.coderecommendation.preview.CodeRecommendationPreviewModel;
import org.eclipse.scava.plugin.coderecommendation.preview.CodeRecommendationPreviewView;
import org.eclipse.scava.plugin.coderecommendation.preview.DisableCodeInsertionEvent;
import org.eclipse.scava.plugin.coderecommendation.preview.EnableCodeInsertionEvent;
import org.eclipse.scava.plugin.coderecommendation.results.AddCodeRecommendationsRequestEvent;
import org.eclipse.scava.plugin.coderecommendation.results.CodeRecommendationDroppedEvent;
import org.eclipse.scava.plugin.coderecommendation.results.CodeRecommendationResultsController;
import org.eclipse.scava.plugin.coderecommendation.results.CodeRecommendationResultsModel;
import org.eclipse.scava.plugin.coderecommendation.results.CodeRecommendationResultsView;
import org.eclipse.scava.plugin.coderecommendation.results.CodeRecommendationSelectedEvent;
import org.eclipse.scava.plugin.mvc.controller.Controller;
import org.eclipse.scava.plugin.mvc.controller.ModelViewController;
import org.eclipse.scava.plugin.mvc.event.routed.IRoutedEvent;
import org.eclipse.scava.plugin.ui.errorhandler.ErrorHandler;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IPartListener2;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.texteditor.ITextEditor;

import io.swagger.client.ApiException;
import io.swagger.client.model.ApiCallResult;

public class CodeRecommendationController extends ModelViewController<CodeRecommendationModel, CodeRecommendationView>
		implements ICodeRecommendationViewEventListener {

	private ActiveEditorOnPageListener activeEditorOnPageListener;

	public CodeRecommendationController(Controller parent, CodeRecommendationModel model, CodeRecommendationView view) {
		super(parent, model, view);
	}

	@Override
	public void init() {
		super.init();

		IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		activeEditorOnPageListener = new ActiveEditorOnPageListener(page);
		page.addPartListener(activeEditorOnPageListener);
		activeEditorOnPageListener.checkEditorInWindow();

		CodeRecommendationResultsModel model = new CodeRecommendationResultsModel();
		CodeRecommendationResultsView view = new CodeRecommendationResultsView();
		CodeRecommendationResultsController controller = new CodeRecommendationResultsController(this, model, view);
		controller.init();

		getView().showResults(view);
	}

	@Override
	public void requestViewClose() {
		dispose();
	}

	public void request(IFile file, int startLine, int endLine, String sourceCode) {
		try {
			Collection<ApiCallResult> apiCallResults = getModel().getApiCallResults(sourceCode);

			if (apiCallResults.isEmpty()) {
				MessageDialog.openError(Display.getDefault().getActiveShell(), "Error",
						"No code recommendations were found for the selected code chunk");
			} else {
				CodeRecommendationTarget target = new CodeRecommendationTarget(file);
				CodeRecommendationRequest request = new CodeRecommendationRequest(new Date(), startLine, endLine,
						sourceCode, target);
				target.getCodeRecommendationsRequests().add(request);

				apiCallResults.forEach(aps -> {
					CodeRecommendation codeRecommendation = new CodeRecommendation(request, aps);
					request.getCodeRecommendations().add(codeRecommendation);
				});

				routeEventToSubControllers(new AddCodeRecommendationsRequestEvent(this, target));
			}

		} catch (ApiException e) {
			e.printStackTrace();
			ErrorHandler.handle(Display.getDefault().getActiveShell(), e);
		}

		try {
			CodeRecommendationView.open(CodeRecommendationView.ID);
		} catch (PartInitException e) {
			e.printStackTrace();
			MessageDialog.openError(Display.getDefault().getActiveShell(), "Error",
					"Unexpected error during showing view:\n\n" + e);
		}
	}

	@Override
	protected void onReceiveRoutedEventFromSubController(IRoutedEvent routedEvent, Controller forwarderController) {

		if (routedEvent instanceof CodeRecommendationSelectedEvent) {
			CodeRecommendationSelectedEvent event = (CodeRecommendationSelectedEvent) routedEvent;

			ICodeRecommendationElement element = event.getElement();

			if (element instanceof IPreviewable) {
				IPreviewable previewable = (IPreviewable) element;

				getSubControllers().stream().filter(CodeRecommendationPreviewController.class::isInstance)
						.forEach(Controller::dispose);

				CodeRecommendationPreviewModel model = new CodeRecommendationPreviewModel(previewable);
				CodeRecommendationPreviewView view = new CodeRecommendationPreviewView();
				CodeRecommendationPreviewController controller = new CodeRecommendationPreviewController(this, model,
						view);
				controller.init();

				getView().showPreview(view);

				activeEditorOnPageListener.checkEditorInWindow();
			}

			return;
		}

		if (routedEvent instanceof CodeRecommendationDroppedEvent) {
			routeEventToSubControllers(routedEvent);

			return;
		}

		super.onReceiveRoutedEventFromSubController(routedEvent, forwarderController);
	}

	private void onEditorAvailable(IDocument document, ITextSelection textSelection) {
		routeEventToSubControllers(new EnableCodeInsertionEvent(this, document, textSelection));
	}

	private void onEditorNotAvailable() {
		routeEventToSubControllers(new DisableCodeInsertionEvent(this));
	}

	private class ActiveEditorOnPageListener implements IPartListener2 {
		private final IWorkbenchPage page;

		public ActiveEditorOnPageListener(IWorkbenchPage page) {
			super();
			this.page = page;
		}

		@Override
		public void partActivated(IWorkbenchPartReference partRef) {
			checkEditorInWindow();
		}

		@Override
		public void partBroughtToTop(IWorkbenchPartReference partRef) {
			checkEditorInWindow();
		}

		@Override
		public void partClosed(IWorkbenchPartReference partRef) {
			checkEditorInWindow();
		}

		@Override
		public void partDeactivated(IWorkbenchPartReference partRef) {
			checkEditorInWindow();
		}

		@Override
		public void partOpened(IWorkbenchPartReference partRef) {
			checkEditorInWindow();
		}

		@Override
		public void partHidden(IWorkbenchPartReference partRef) {
			checkEditorInWindow();
		}

		@Override
		public void partVisible(IWorkbenchPartReference partRef) {
			checkEditorInWindow();
		}

		@Override
		public void partInputChanged(IWorkbenchPartReference partRef) {
			checkEditorInWindow();
		}

		private void checkEditorInWindow() {
			IEditorPart activeEditor = page.getActiveEditor();

			if (activeEditor != null) {
				IEditorInput input = activeEditor.getEditorInput();

				if (activeEditor instanceof ITextEditor && input instanceof FileEditorInput) {
					ITextEditor textEditor = (ITextEditor) activeEditor;
					IDocument document = textEditor.getDocumentProvider().getDocument(input);

					ISelection selection = textEditor.getSelectionProvider().getSelection();
					ITextSelection textSelection = (ITextSelection) selection;

					onEditorAvailable(document, textSelection);
					return;
				}
			}

			onEditorNotAvailable();
		}
	}
}
