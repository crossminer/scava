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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.scava.commons.context.sourcecode.SourceCodeContext;
import org.eclipse.scava.commons.library.Library;
import org.eclipse.scava.commons.recommendation.RecommendationSet;
import org.eclipse.scava.plugin.context.librarystatus.LibraryStatusDetector;
import org.eclipse.scava.plugin.context.librarystatus.LibraryStatusException;
import org.eclipse.scava.plugin.context.sourcecodestatus.SourceCodeStatusDetector;
import org.eclipse.scava.plugin.context.sourcecodestatus.SourceCodeStatusException;
import org.eclipse.scava.plugin.knowledgebase.access.KnowledgeBaseAccessManager;
import org.eclipse.scava.plugin.logger.Logger;
import org.eclipse.scava.plugin.recommendation.RecommendationManager;
import org.eclipse.scava.plugin.ui.UIException;
import org.eclipse.scava.plugin.ui.abstractmvc.AbstractController;
import org.eclipse.scava.plugin.ui.communicationdisplay.CommunicationDisplayController;
import org.eclipse.scava.plugin.ui.communicationdisplay.CommunicationDisplayDisplay;
import org.eclipse.scava.plugin.ui.communicationdisplay.CommunicationDisplayModel;
import org.eclipse.scava.plugin.ui.communicationdisplay.CommunicationDisplayView;
import org.eclipse.scava.plugin.ui.libraryupdate.AlternativeLibrary;
import org.eclipse.scava.plugin.ui.libraryupdate.LibraryUpdateController;
import org.eclipse.scava.plugin.ui.libraryupdate.LibraryUpdateDisplay;
import org.eclipse.scava.plugin.ui.libraryupdate.LibraryUpdateModel;
import org.eclipse.scava.plugin.ui.libraryupdate.LibraryUpdateView;
import org.eclipse.scava.plugin.ui.libraryupdate.ProjectLibrary;
import org.eclipse.scava.plugin.ui.recommendationaccept.RecommendationAcceptController;
import org.eclipse.scava.plugin.ui.recommendationaccept.RecommendationAcceptDisplay;
import org.eclipse.scava.plugin.ui.recommendationaccept.RecommendationAcceptModel;
import org.eclipse.scava.plugin.ui.recommendationaccept.RecommendationAcceptView;
import org.eclipse.scava.plugin.utils.Utils;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.texteditor.ITextEditor;

public class MainController extends AbstractController<MainModel, MainView> implements IRecommendationSetApplier {
	private final KnowledgeBaseAccessManager knowledgeBaseAccessManager;
	private final RecommendationManager recommendationManager;
	private final Shell shell;
	private CommunicationDisplayController communicationDisplayController;
	private LibraryUpdateController libraryUpdateDialogController;
	private RecommendationAcceptController recommendationAcceptController;

	public MainController(MainModel model, MainView view, Shell shell) {
		super(model, view);

		this.shell = shell;

		knowledgeBaseAccessManager = new KnowledgeBaseAccessManager();
		recommendationManager = new RecommendationManager(knowledgeBaseAccessManager);
	}

	@Override
	protected void dispose() {
		Logger.unregisterListener(communicationDisplayController);
	}

	public void showLibraryUpdateDialog(IJavaProject project) {

		try {
			createLibraryUpdateDialog(project, buildProjectLibraries(project));
		} catch (LibraryStatusException e) {
			e.printStackTrace();
		}
	}

	private List<ProjectLibrary> buildProjectLibraries(IJavaProject project) throws LibraryStatusException {
		List<Library> usedLibraries = LibraryStatusDetector.getLibrariesFromProject(project);

		return usedLibraries.stream()
				.map(library -> new ProjectLibrary(library, getAlternativesForUsedLibraries(library)))
				.collect(Collectors.toList());
	}

	private List<AlternativeLibrary> getAlternativesForUsedLibraries(Library originalLibrary) {
		List<AlternativeLibrary> alternativeLibraries = new ArrayList<>();
		knowledgeBaseAccessManager.requestAlternativesOfLibrary(originalLibrary)
				.forEach(lib -> alternativeLibraries.add(new AlternativeLibrary(lib)));

		return alternativeLibraries;
	}

	private void createLibraryUpdateDialog(IJavaProject project, List<ProjectLibrary> libraries) {
		LibraryUpdateModel libraryUpdateDialogModel = new LibraryUpdateModel(libraries);
		LibraryUpdateDisplay libraryUpdateDialogDisplay = new LibraryUpdateDisplay(shell);
		LibraryUpdateView libraryUpdateDialogView = new LibraryUpdateView(libraryUpdateDialogDisplay);
		libraryUpdateDialogController = new LibraryUpdateController(libraryUpdateDialogModel, libraryUpdateDialogView,
				recommendationManager, project, this, knowledgeBaseAccessManager);

		libraryUpdateDialogController.getFinished().subscribe(this::onLibraryUpdateDialogFinished);

		addSubController(libraryUpdateDialogController);
	}

	private void onLibraryUpdateDialogFinished() {
		libraryUpdateDialogController.close();
		removeSubController(libraryUpdateDialogController);
	}

	public void showCommunicationDisplay() {
		if (communicationDisplayController != null) {
			removeSubController(communicationDisplayController);
		}

		CommunicationDisplayModel communicationDisplayModel = new CommunicationDisplayModel();
		CommunicationDisplayDisplay communicationDisplayDialog = new CommunicationDisplayDisplay(shell);
		CommunicationDisplayView communicationDisplayView = new CommunicationDisplayView(communicationDisplayDialog);
		communicationDisplayController = new CommunicationDisplayController(communicationDisplayModel,
				communicationDisplayView);
		addSubController(communicationDisplayController);

		Logger.registerListener(communicationDisplayController);
	}

	@Override
	public void applyRecommendationSetOnProject(IProject project, RecommendationSet recommendations) {
		recommendationManager.applyRecommendations(project, recommendations);
		PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().saveAllEditors(false);

		if (libraryUpdateDialogController != null) {
			libraryUpdateDialogController.close();
			removeSubController(libraryUpdateDialogController);
		}

	}

	public void generalImprovementsOnCode() {

		IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		saveWorkbenchPage(page);
		IProject project = Utils.getCurrentlyEditedProject().getProject();

		try {
			SourceCodeContext codeContext = getSourceCodeContext(page);
			RecommendationSet recommendations = recommendationManager
					.requestRecommendationsToGeneralImprovements(codeContext);
			recommendationAcceptController = MainController.showRecommendationAccept(recommendations, project, shell);

			addSubController(recommendationAcceptController);

			recommendationAcceptController.getRecommendationsAccepted().subscribe(this::onRecommendationsAccepted);
			recommendationAcceptController.getRecommendationsCancelled().subscribe(this::onRecommendationsCancelled);
		} catch (UIException e) {
			e.printStackTrace();
		}

	}

	private void saveWorkbenchPage(IWorkbenchPage page) {

		IEditorPart editorpart = page.getActiveEditor();
		page.saveEditor(editorpart, false);

	}

	private SourceCodeContext getSourceCodeContext(IWorkbenchPage page) throws UIException {
		ITextEditor editor = (ITextEditor) page.getActiveEditor();
		ITextSelection sel = (ITextSelection) editor.getSelectionProvider().getSelection();
		IEditorInput input = editor.getEditorInput();

		if (input instanceof IFileEditorInput) {
			IFile file = ((IFileEditorInput) input).getFile();
			try {
				SourceCodeContext info = SourceCodeStatusDetector.getSourceCodeContext(file, sel.getOffset(),
						sel.getLength());

				editor.getSelectionProvider().setSelection(new TextSelection(0, 0));

				return info;
			} catch (SourceCodeStatusException e) {
				throw new UIException(e);

			}
		}
		throw new UIException("Input is not an IFileEditorInput");

	}

	public static RecommendationAcceptController showRecommendationAccept(RecommendationSet recommendationSet,
			IProject project, Shell shell) {
		RecommendationAcceptModel recommendationAcceptModel = new RecommendationAcceptModel(recommendationSet,
				project.getProject());
		RecommendationAcceptDisplay recommendationAcceptDisplay = new RecommendationAcceptDisplay(shell);
		RecommendationAcceptView recommendationAcceptView = new RecommendationAcceptView(recommendationAcceptDisplay);
		RecommendationAcceptController recommendationAcceptController = new RecommendationAcceptController(
				recommendationAcceptModel, recommendationAcceptView, project.getProject());

		return recommendationAcceptController;

	}

	private void onRecommendationsAccepted(RecommendationSet recommendations) {

		applyRecommendationSetOnProject(Utils.getCurrentlyEditedProject().getProject(), recommendations);

		recommendationAcceptController.close();
		removeSubController(recommendationAcceptController);
	}

	private void onRecommendationsCancelled() {
		recommendationAcceptController.close();
		removeSubController(recommendationAcceptController);
	}

}
