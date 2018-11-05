/*********************************************************************
* Copyright (c) 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.main;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.scava.plugin.apidocumentation.ApiDocumentationController;
import org.eclipse.scava.plugin.apidocumentation.ApiDocumentationModel;
import org.eclipse.scava.plugin.apidocumentation.ApiDocumentationView;
import org.eclipse.scava.plugin.apidocumentation.IApiDocumentationController;
import org.eclipse.scava.plugin.apidocumentation.IApiDocumentationModel;
import org.eclipse.scava.plugin.apidocumentation.IApiDocumentationView;
import org.eclipse.scava.plugin.coderecommendation.CodeRecommendationController;
import org.eclipse.scava.plugin.coderecommendation.CodeRecommendationModel;
import org.eclipse.scava.plugin.coderecommendation.CodeRecommendationView;
import org.eclipse.scava.plugin.coderecommendation.ICodeRecommendationController;
import org.eclipse.scava.plugin.coderecommendation.ICodeRecommendationModel;
import org.eclipse.scava.plugin.coderecommendation.ICodeRecommendationView;
import org.eclipse.scava.plugin.librarysearch.ILibrarySearchController;
import org.eclipse.scava.plugin.librarysearch.ILibrarySearchModel;
import org.eclipse.scava.plugin.librarysearch.ILibrarySearchView;
import org.eclipse.scava.plugin.librarysearch.LibrarySearchController;
import org.eclipse.scava.plugin.librarysearch.LibrarySearchModel;
import org.eclipse.scava.plugin.librarysearch.LibrarySearchView;
import org.eclipse.scava.plugin.mvc.implementation.AbstractModelController;
import org.eclipse.scava.plugin.mvc.implementation.rcp.RCPViewPartView;
import org.eclipse.scava.plugin.usermonitoring.UserMonitor;
import org.eclipse.scava.plugin.usermonitoring.event.IEvent;
import org.eclipse.swt.SWT;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.texteditor.AbstractTextEditor;
import org.eclipse.ui.texteditor.ITextEditor;

import com.google.common.eventbus.DeadEvent;
import com.google.common.eventbus.Subscribe;

public class MainController extends AbstractModelController<IMainModel> implements IMainController {

	public MainController(IMainModel model) {
		super(null, model);

		setEventBus(getModel().getEventBus());
	}

	@Override
	public void init() {

	}

	@Subscribe
	public void onDeadEvent(DeadEvent e) {
		System.out.println("DEADEVENT: " + e.getEvent() + " from " + e.getSource());
	}

	@Subscribe
	public void onOpenLibrarySearch(OpenLibrarySearchEvent e) {
		IProject activeProject = getModel().getActiveProject();

		if (activeProject == null) {
			MessageDialog.open(MessageDialog.INFORMATION, getModel().getShell(), "Warning", "Please select a project or an editor window.", SWT.NONE);
			return;
		}

		IPath pathToPOM = activeProject.getLocation();
		pathToPOM = pathToPOM.addTrailingSeparator();
		pathToPOM = pathToPOM.append("pom.xml");

		ILibrarySearchModel librarySearchModel = new LibrarySearchModel(pathToPOM.toOSString());
		ILibrarySearchView librarySearchView = new LibrarySearchView(getModel().getShell());

		ILibrarySearchController librarySearchController = new LibrarySearchController(this, librarySearchModel, librarySearchView);
		librarySearchController.init();
	}

	@Subscribe
	public void onRequestCodeRecommendation(RequestCodeRecommendation e) {
		IEditorPart activeEditor = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();

		if (!(activeEditor instanceof AbstractTextEditor)) {
			return;
		}

		ITextEditor editor = (ITextEditor) activeEditor;

		ISelection selection = editor.getSelectionProvider().getSelection();
		ITextSelection textSelection = (ITextSelection) selection;

		ICodeRecommendationModel codeRecommendationModel = new CodeRecommendationModel(textSelection.getText(), editor);
		ICodeRecommendationView codeRecommendationView = new CodeRecommendationView(getModel().getShell());
		ICodeRecommendationController codeRecommendationController = new CodeRecommendationController(this, codeRecommendationModel, codeRecommendationView);
		codeRecommendationController.init();
	}

	@Subscribe
	public void onRequestApiDocumentation(RequestApiDocumentation e) throws PartInitException {
		IEditorPart activeEditor = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();

		if (!(activeEditor instanceof AbstractTextEditor)) {
			return;
		}

		ITextEditor editor = (ITextEditor) activeEditor;

		ISelection selection = editor.getSelectionProvider().getSelection();
		ITextSelection textSelection = (ITextSelection) selection;

		IApiDocumentationModel apiDocumentationModel = new ApiDocumentationModel(textSelection.getText());
		IApiDocumentationView apiDocumentationView = RCPViewPartView.requestView(ApiDocumentationView.ID);
		IApiDocumentationController apiDocumentationController = new ApiDocumentationController(this, apiDocumentationModel, apiDocumentationView);
		apiDocumentationController.init();
	}

	@Subscribe
	public void processEvent(IEvent event) {
		System.out.println(event);
	}

	@Override
	public void startUserMonitoring() {
		UserMonitor userMonitor = new UserMonitor(getEventBus());
	}
}
