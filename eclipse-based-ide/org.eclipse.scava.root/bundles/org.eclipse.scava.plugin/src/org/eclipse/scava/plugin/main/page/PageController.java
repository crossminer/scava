/*********************************************************************
* Copyright c 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse PublicLicense 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.main.page;

import java.io.FileNotFoundException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.ITypeRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.internal.ui.packageview.PackageFragmentRootContainer;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.jdt.ui.SharedASTProvider;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.scava.plugin.apidocumentation.ApiDocumentationController;
import org.eclipse.scava.plugin.apidocumentation.ApiDocumentationModel;
import org.eclipse.scava.plugin.apidocumentation.ApiDocumentationView;
import org.eclipse.scava.plugin.coderecommendation.CodeRecommendationController;
import org.eclipse.scava.plugin.coderecommendation.CodeRecommendationModel;
import org.eclipse.scava.plugin.coderecommendation.CodeRecommendationView;
import org.eclipse.scava.plugin.focus.FocusRecommendationModel;
import org.eclipse.scava.plugin.focus.apicall.FocusApiCallRecommendationController;
import org.eclipse.scava.plugin.focus.apicall.FocusApiCallRecommendationView;
import org.eclipse.scava.plugin.focus.codesnippet.FocusCodeSnippetRecommendationController;
import org.eclipse.scava.plugin.focus.codesnippet.FocusCodeSnippetRecommendationView;
import org.eclipse.scava.plugin.knowledgebase.access.KnowledgeBaseAccess;
import org.eclipse.scava.plugin.librarysuggestion.LibrarySuggestionController;
import org.eclipse.scava.plugin.librarysuggestion.LibrarySuggestionModel;
import org.eclipse.scava.plugin.librarysuggestion.LibrarySuggestionView;
import org.eclipse.scava.plugin.libraryversions.LibraryVersionModel;
import org.eclipse.scava.plugin.libraryversions.apimigration.ApiMigrationCenterController;
import org.eclipse.scava.plugin.libraryversions.apimigration.ApiMigrationCenterModel;
import org.eclipse.scava.plugin.libraryversions.apimigration.ApiMigrationCenterView;
import org.eclipse.scava.plugin.libraryversions.checker.LibraryVersionCheckController;
import org.eclipse.scava.plugin.libraryversions.checker.RefreshAvailableLibraryUpdates;
import org.eclipse.scava.plugin.libraryversions.updater.LibraryVersionUpdateApiMigrationRequestEvent;
import org.eclipse.scava.plugin.libraryversions.updater.LibraryVersionUpdaterController;
import org.eclipse.scava.plugin.libraryversions.updater.LibraryVersionUpdaterModel;
import org.eclipse.scava.plugin.libraryversions.updater.LibraryVersionUpdaterView;
import org.eclipse.scava.plugin.main.EclipseInterfaceEvent.ApiDocuemntationRequestEvent;
import org.eclipse.scava.plugin.main.EclipseInterfaceEvent.CodeRecommendationRequestEvent;
import org.eclipse.scava.plugin.main.EclipseInterfaceEvent.FocusApiCallRecommendationRequestEvent;
import org.eclipse.scava.plugin.main.EclipseInterfaceEvent.FocusCodeSnippetRecommendationRequestEvent;
import org.eclipse.scava.plugin.main.EclipseInterfaceEvent.LibrarySearchRequestEvent;
import org.eclipse.scava.plugin.main.EclipseInterfaceEvent.LibraryVersionUpdaterRequestEvent;
import org.eclipse.scava.plugin.main.EclipseInterfaceEvent.ProjectSearchRequestEvent;
import org.eclipse.scava.plugin.main.EclipseInterfaceEvent.WorkbenchStartup;
import org.eclipse.scava.plugin.mvc.controller.Controller;
import org.eclipse.scava.plugin.mvc.controller.ModelController;
import org.eclipse.scava.plugin.mvc.event.routed.IRoutedEvent;
import org.eclipse.scava.plugin.projectsearch.ProjectSearchController;
import org.eclipse.scava.plugin.projectsearch.ProjectSearchModel;
import org.eclipse.scava.plugin.ui.errorhandler.ErrorHandler;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.texteditor.AbstractTextEditor;
import org.eclipse.ui.texteditor.ITextEditor;

public class PageController extends ModelController<PageModel> {

	private CodeRecommendationController codeRecommendationController;
	private FocusApiCallRecommendationController focusApiCallRecommendationController;
	private FocusCodeSnippetRecommendationController focusCodeSnippetRecommendationController;
	private ApiDocumentationController apiDocumentationController;
	private ApiMigrationCenterController apiMigrationCenterController;

	public PageController(Controller parent, PageModel model) {
		super(parent, model);
	}

	@Override
	protected void onReceiveRoutedEventFromParentController(IRoutedEvent routedEvent) {

		if (routedEvent instanceof ProjectSearchRequestEvent) {
			ProjectSearchRequestEvent event = (ProjectSearchRequestEvent) routedEvent;

			ProjectSearchModel model = new ProjectSearchModel(getModel().getKnowledgeBaseAccess());
			ProjectSearchController controller = new ProjectSearchController(this, model);
			controller.init();

			return;
		}

		if (routedEvent instanceof LibrarySearchRequestEvent) {
			LibrarySearchRequestEvent event = (LibrarySearchRequestEvent) routedEvent;

			IProject project = getSelectedProjectFromPackageExplorer();

			if (project == null) {
				MessageDialog.openWarning(getModel().getPage().getWorkbenchWindow().getShell(), "Warning",
						"Select  project in the Package explorer!");
				return;
			}

			IFile pomFile = project.getFile(new Path("pom.xml"));

			if (!pomFile.exists()) {
				MessageDialog.openError(getModel().getPage().getWorkbenchWindow().getShell(), "Error",
						"There is no pom.xml file in the project's folder,\nprobably it is not a Maven project.\n\nThis feature only works with Maven projects.");
				return;
			}

			LibrarySuggestionModel model = new LibrarySuggestionModel(pomFile.getLocation().toOSString(), getModel().getKnowledgeBaseAccess());
			LibrarySuggestionView view = new LibrarySuggestionView(Display.getDefault().getActiveShell());
			LibrarySuggestionController controller = new LibrarySuggestionController(this, model, view);
			controller.init();

			return;
		}

		if (routedEvent instanceof CodeRecommendationRequestEvent) {
			CodeRecommendationRequestEvent event = (CodeRecommendationRequestEvent) routedEvent;

			try {
				IEditorPart activeEditor = getModel().getPage().getActiveEditor();

				if (!(activeEditor instanceof AbstractTextEditor)) {
					MessageDialog.openError(getModel().getPage().getWorkbenchWindow().getShell(), "Error",
							"Select a code chunk in a text editor");
					return;
				}

				ITextEditor editor = (ITextEditor) activeEditor;
				ITextSelection textSelection = (ITextSelection) editor.getSelectionProvider().getSelection();
				IEditorInput editorInput = editor.getEditorInput();

				if (!(editorInput instanceof FileEditorInput)) {
					return;
				}

				IFile file = ((FileEditorInput) editorInput).getFile();

				if (codeRecommendationController == null || codeRecommendationController.isDisposed()) {
					CodeRecommendationModel model = new CodeRecommendationModel(getModel().getKnowledgeBaseAccess());
					CodeRecommendationView view = CodeRecommendationView.open(CodeRecommendationView.ID);
					codeRecommendationController = new CodeRecommendationController(this, model, view);

					codeRecommendationController.init();
				}

				codeRecommendationController.request(file, textSelection.getStartLine() + 1,
						textSelection.getEndLine() + 1, textSelection.getText());
				CodeRecommendationView.open(CodeRecommendationView.ID);

			} catch (Exception e) {
				e.printStackTrace();
				MessageDialog.openError(getModel().getPage().getWorkbenchWindow().getShell(), "Error",
						"Unexpected error:\n\n" + e);
			}

			return;
		}
		
		if (routedEvent instanceof FocusApiCallRecommendationRequestEvent) {
			FocusApiCallRecommendationRequestEvent event = (FocusApiCallRecommendationRequestEvent) routedEvent;

			try {
				IEditorPart activeEditor = getModel().getPage().getActiveEditor();

				if (!(activeEditor instanceof AbstractTextEditor)) {
					MessageDialog.openError(getModel().getPage().getWorkbenchWindow().getShell(), "Error",
							"Put the cursor into the body of a method");
					return;
				}

				ITextEditor editor = (ITextEditor) activeEditor;
				ITextSelection textSelection = (ITextSelection) editor.getSelectionProvider().getSelection();
				
				IEditorInput editorInput = editor.getEditorInput();

				if (!(editorInput instanceof FileEditorInput)) {
					return;
				}

				IFile file = ((FileEditorInput) editorInput).getFile();
				
				ITypeRoot editorInputTypeRoot = JavaUI.getEditorInputTypeRoot(editorInput);
				if( editorInputTypeRoot == null ) {
					return;
				}
				
				CompilationUnit ast = SharedASTProvider.getAST(editorInputTypeRoot, SharedASTProvider.WAIT_YES, null);
				
				if (focusApiCallRecommendationController == null || focusApiCallRecommendationController.isDisposed()) {
					FocusRecommendationModel model = new FocusRecommendationModel(getModel().getKnowledgeBaseAccess());
					FocusApiCallRecommendationView view = FocusApiCallRecommendationView.open(FocusApiCallRecommendationView.ID);
					focusApiCallRecommendationController = new FocusApiCallRecommendationController(this, model, view);

					focusApiCallRecommendationController.init();
				}
				
				focusApiCallRecommendationController.request(file.getProject(), ast, textSelection);
				FocusApiCallRecommendationView.open(FocusApiCallRecommendationView.ID);
			} catch (Throwable e) {
				ErrorHandler.logAndShowErrorMessage(getModel().getPage().getWorkbenchWindow().getShell(), e);
			}

			return;
		}
		
		if (routedEvent instanceof FocusCodeSnippetRecommendationRequestEvent) {
			FocusCodeSnippetRecommendationRequestEvent event = (FocusCodeSnippetRecommendationRequestEvent) routedEvent;

			try {
				IEditorPart activeEditor = getModel().getPage().getActiveEditor();

				if (!(activeEditor instanceof AbstractTextEditor)) {
					MessageDialog.openError(getModel().getPage().getWorkbenchWindow().getShell(), "Error",
							"Put the cursor into the body of a method");
					return;
				}

				ITextEditor editor = (ITextEditor) activeEditor;
				ITextSelection textSelection = (ITextSelection) editor.getSelectionProvider().getSelection();
				
				IEditorInput editorInput = editor.getEditorInput();

				if (!(editorInput instanceof FileEditorInput)) {
					return;
				}

				IFile file = ((FileEditorInput) editorInput).getFile();
				
				ITypeRoot editorInputTypeRoot = JavaUI.getEditorInputTypeRoot(editorInput);
				if( editorInputTypeRoot == null ) {
					return;
				}
				
				CompilationUnit ast = SharedASTProvider.getAST(editorInputTypeRoot, SharedASTProvider.WAIT_YES, null);
				
				if (focusCodeSnippetRecommendationController == null || focusCodeSnippetRecommendationController.isDisposed()) {
					FocusRecommendationModel model = new FocusRecommendationModel(getModel().getKnowledgeBaseAccess());
					FocusCodeSnippetRecommendationView view = FocusCodeSnippetRecommendationView.open(FocusCodeSnippetRecommendationView.ID);
					focusCodeSnippetRecommendationController = new FocusCodeSnippetRecommendationController(this, model, view);

					focusCodeSnippetRecommendationController.init();
				}
				
				focusCodeSnippetRecommendationController.request(file.getProject(), ast, textSelection);
				FocusCodeSnippetRecommendationView.open(FocusCodeSnippetRecommendationView.ID);
			} catch (Throwable e) {
				ErrorHandler.logAndShowErrorMessage(getModel().getPage().getWorkbenchWindow().getShell(), e);
			}

			return;
		}

		if (routedEvent instanceof ApiDocuemntationRequestEvent) {
			ApiDocuemntationRequestEvent event = (ApiDocuemntationRequestEvent) routedEvent;

			try {
				ISelection selection = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getSelectionService().getSelection();
				
				if( !(selection instanceof ITextSelection) ) {
					MessageDialog.openError(getModel().getPage().getWorkbenchWindow().getShell(), "Error",
							"Select some text in a text editor");
					return;
				}
				
				ITextSelection textSelection = (ITextSelection)selection;
				
				if (textSelection.getLength() < 1) {
					MessageDialog.openError(Display.getDefault().getActiveShell(), "Error",
							"There is no text selected");
					return;
				}
				
				if (apiDocumentationController == null || apiDocumentationController.isDisposed()) {
					ApiDocumentationModel model = new ApiDocumentationModel(getModel().getKnowledgeBaseAccess());
					ApiDocumentationView view = ApiDocumentationView.open(ApiDocumentationView.ID);
					apiDocumentationController = new ApiDocumentationController(this, model, view);

					apiDocumentationController.init();
				}

				apiDocumentationController.request(textSelection.getText());
				ApiDocumentationView.open(ApiDocumentationView.ID);
			} catch (Exception e) {
				e.printStackTrace();
				MessageDialog.openError(getModel().getPage().getWorkbenchWindow().getShell(), "Error",
						"Unexpected error:\n\n" + e);
			}

			return;
		}

		if (routedEvent instanceof WorkbenchStartup) {
			onReceiveRoutedEventFromSubController(new RefreshAvailableLibraryUpdates(this), this);
			return;
		}

		if (routedEvent instanceof LibraryVersionUpdaterRequestEvent) {
			LibraryVersionUpdaterRequestEvent event = (LibraryVersionUpdaterRequestEvent) routedEvent;

			IProject project = getSelectedProjectFromPackageExplorer();

			if (project == null) {
				MessageDialog.openWarning(getModel().getPage().getWorkbenchWindow().getShell(), "Warning",
						"Select  project in the Package explorer!");
				return;
			}

			IFile pomFile = project.getFile(new Path("pom.xml"));

			if (!pomFile.exists()) {
				MessageDialog.openError(getModel().getPage().getWorkbenchWindow().getShell(), "Error",
						"There is no pom.xml file in the project's folder,\nprobably it is not a Maven project.\n\nThis feature only works with Maven projects.");
				return;
			}

			try {
				LibraryVersionUpdaterModel model = new LibraryVersionUpdaterModel(project);
				LibraryVersionUpdaterView view = new LibraryVersionUpdaterView(Display.getDefault().getActiveShell());
				LibraryVersionUpdaterController controller = new LibraryVersionUpdaterController(this, model, view);
				controller.init();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				MessageDialog.openError(getModel().getPage().getWorkbenchWindow().getShell(), "Error",
						"Error during opening Library Version Updater:\n\n" + e);
				return;
			}

			return;
		}

		super.onReceiveRoutedEventFromParentController(routedEvent);
	}

	@Override
	protected void onReceiveRoutedEventFromSubController(IRoutedEvent routedEvent, Controller forwarderController) {

		if (routedEvent instanceof LibraryVersionUpdateApiMigrationRequestEvent) {
			LibraryVersionUpdateApiMigrationRequestEvent event = (LibraryVersionUpdateApiMigrationRequestEvent) routedEvent;

			try {
				if (apiMigrationCenterController == null || apiMigrationCenterController.isDisposed()) {
					ApiMigrationCenterModel model = new ApiMigrationCenterModel(new KnowledgeBaseAccess());
					ApiMigrationCenterView view = ApiMigrationCenterView.open(ApiMigrationCenterView.ID);
					apiMigrationCenterController = new ApiMigrationCenterController(this, model, view);
					apiMigrationCenterController.init();
				}

				apiMigrationCenterController.request(event.getProject(), event.getJar(), event.getLibraries());
				ApiMigrationCenterView.open(ApiMigrationCenterView.ID);

			} catch (PartInitException e) {
				e.printStackTrace();
			}

			return;
		}

		if (routedEvent instanceof RefreshAvailableLibraryUpdates) {
			IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();

			for (IProject project : projects) {

				try {
					if (!project.isOpen() || !project.hasNature(JavaCore.NATURE_ID)) {
						continue;
					}

					IFile pomFile = project.getFile(new Path("pom.xml"));

					if (!pomFile.exists()) {
						continue;
					}

					getSubControllers(LibraryVersionCheckController.class).forEach(Controller::dispose);

					LibraryVersionModel model = new LibraryVersionModel();
					LibraryVersionCheckController controller = new LibraryVersionCheckController(this, model);
					controller.markUpdatableLibraries(project);

				} catch (CoreException | FileNotFoundException e) {
					e.printStackTrace();
				}
			}

			return;
		}

		if (routedEvent instanceof OpenFileInEditorRequestEvent) {
			OpenFileInEditorRequestEvent event = (OpenFileInEditorRequestEvent) routedEvent;

			try {
				ITextEditor editor = (ITextEditor) IDE.openEditor(getModel().getPage(), event.getFile());
				if (event.isSelection()) {
					editor.selectAndReveal(event.getSelectionStart(), event.getSelectionEnd());
				}
			} catch (PartInitException e) {
				e.printStackTrace();
			}

			return;
		}

		super.onReceiveRoutedEventFromSubController(routedEvent, forwarderController);
	}

	@SuppressWarnings("restriction")
	private IProject getSelectedProjectFromPackageExplorer() {
		ISelection selection = getModel().getPage().getSelection();

		if (selection instanceof IStructuredSelection) {
			IStructuredSelection structuredSelection = (IStructuredSelection) selection;
			Object selectedElement = structuredSelection.getFirstElement();

			if (selectedElement instanceof IResource) {
				return ((IResource) selectedElement).getProject();
			} else if (selectedElement instanceof PackageFragmentRootContainer) {
				return ((PackageFragmentRootContainer) selectedElement).getJavaProject().getProject();
			} else if (selectedElement instanceof IJavaElement) {
				return ((IJavaElement) selectedElement).getJavaProject().getProject();
			}
		}
		return null;
	}

}
