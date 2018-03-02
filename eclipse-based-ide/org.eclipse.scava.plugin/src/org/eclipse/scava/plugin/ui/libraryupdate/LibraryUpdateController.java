/*********************************************************************
* Copyright (c) 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.ui.libraryupdate;

import java.util.List;

import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.scava.commons.library.Library;
import org.eclipse.scava.commons.recommendation.RecommendationSet;
import org.eclipse.scava.plugin.context.sourcecodestatus.SourceCodeStatusException;
import org.eclipse.scava.plugin.event.notifier.INotifierEvent;
import org.eclipse.scava.plugin.event.notifier.INotifierEventSubscriber;
import org.eclipse.scava.plugin.event.notifier.NotifierEvent;
import org.eclipse.scava.plugin.knowledgebase.access.ILibraryDescriptionProvider;
import org.eclipse.scava.plugin.main.IRecommendationSetApplier;
import org.eclipse.scava.plugin.main.MainController;
import org.eclipse.scava.plugin.recommendation.ILibraryUpdateRecommendationProvider;
import org.eclipse.scava.plugin.ui.abstractmvc.AbstractController;
import org.eclipse.scava.plugin.ui.recommendationaccept.RecommendationAcceptController;

public class LibraryUpdateController extends
		AbstractController<LibraryUpdateModel, LibraryUpdateView> {
	private final INotifierEvent finished = registerEvent(new NotifierEvent());
	
	private final ILibraryUpdateRecommendationProvider libraryUpdateRecommendationProvider;
	private final ILibraryDescriptionProvider libraryDescriptionProvider;
	private final IRecommendationSetApplier recommendationSetApplier;
	private final IJavaProject project;
	private RecommendationAcceptController recommendationAcceptController;
	
	public LibraryUpdateController(LibraryUpdateModel model, LibraryUpdateView view,
			ILibraryUpdateRecommendationProvider libraryUpdateRecommendationProvider, IJavaProject project,
			IRecommendationSetApplier recommendationSetApplier,
			ILibraryDescriptionProvider libraryDescriptionProvider) {
		super(model, view);
		
		this.libraryUpdateRecommendationProvider = libraryUpdateRecommendationProvider;
		this.recommendationSetApplier = recommendationSetApplier;
		this.project = project;
		this.libraryDescriptionProvider = libraryDescriptionProvider;
		
		getModel().getModelChanged().subscribe(this::onModelChanged);
		getView().getLibrariesAccepted().subscribe(this::onLibrariesAccepted);
		getView().getLibrariesCancelled().subscribe(this::onLibrariesCancelled);
		getView().getLibrarySelected().subscribe(this::onLibrarySelected);
		
		showLibrariesFromModel();
	}
	
	public INotifierEventSubscriber getFinished() {
		return finished;
	}
	
	private void onModelChanged() {
		showLibrariesFromModel();
	}

	private void showLibrariesFromModel() {
		List<ProjectLibrary> libraries = getModel().getLibraries();
		getView().showLibraries(libraries);
	}
	
	private void onLibrariesAccepted(List<AlternativeLibrary> libraries) {
		if (libraries.isEmpty()) {
			getView().showEmptyLibraryListError();
		} else {
			updateLibraries(libraries);
		}
	}
	
	private void updateLibraries(List<AlternativeLibrary> librariesToUpdate) {
		for (AlternativeLibrary alternativeLibrary : librariesToUpdate) {
			try {
				updateLibrary(alternativeLibrary);
			} catch (SourceCodeStatusException e) {
				getView().showUpdateLibraryError(e.getMessage());
			}
		}
	}
	
	private void updateLibrary(AlternativeLibrary library) throws SourceCodeStatusException {
		RecommendationSet recommendationSet = libraryUpdateRecommendationProvider
				.requestRecommendationsToUpdateLibraryInProject(project, library.getOriginalLibrary().getLibrary(),
						library.getLibrary());
		
		recommendationAcceptController = MainController.showRecommendationAccept(recommendationSet, project.getProject(), getView().getShell());
		
		addSubController(recommendationAcceptController);
		
		recommendationAcceptController.getRecommendationsAccepted().subscribe(this::onRecommendationsAccepted);
		recommendationAcceptController.getRecommendationsCancelled().subscribe(this::onRecommendationsCancelled);
	}
	
	
	
	private void onRecommendationsAccepted(RecommendationSet recommendations) {
		recommendationSetApplier.applyRecommendationSetOnProject(project.getProject(), recommendations);
		
		recommendationAcceptController.close();
		removeSubController(recommendationAcceptController);
	}
	
	private void onRecommendationsCancelled() {
		recommendationAcceptController.close();
		removeSubController(recommendationAcceptController);
	}
	
	private void onLibrariesCancelled() {
		finished.invoke();
	}
	
	private void onLibrarySelected(Library library) {
		String description = libraryDescriptionProvider.requestDescriptionOfLibrary(library);
		getView().showDescription(description);
	}
}
