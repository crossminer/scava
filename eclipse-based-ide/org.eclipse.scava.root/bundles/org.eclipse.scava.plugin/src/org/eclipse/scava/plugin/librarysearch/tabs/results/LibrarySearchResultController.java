package org.eclipse.scava.plugin.librarysearch.tabs.results;

import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.scava.plugin.knowledgebase.access.SimilarityMethod;
import org.eclipse.scava.plugin.librarysearch.details.ILibraryDetailsController;
import org.eclipse.scava.plugin.librarysearch.details.LibraryDetailsController;
import org.eclipse.scava.plugin.librarysearch.details.LibraryDetailsModel;
import org.eclipse.scava.plugin.librarysearch.details.LibraryDetailsView;
import org.eclipse.scava.plugin.librarysearch.list.ILibraryListController;
import org.eclipse.scava.plugin.librarysearch.list.ILibraryListModel;
import org.eclipse.scava.plugin.librarysearch.list.ILibraryListView;
import org.eclipse.scava.plugin.librarysearch.list.LibraryListController;
import org.eclipse.scava.plugin.librarysearch.list.LibraryListInfo;
import org.eclipse.scava.plugin.librarysearch.list.LibraryListModel;
import org.eclipse.scava.plugin.librarysearch.list.LibraryListView;
import org.eclipse.scava.plugin.mvc.IController;
import org.eclipse.scava.plugin.mvc.implementation.AbstractModelViewController;

import com.google.common.eventbus.Subscribe;

import io.swagger.client.model.Artifact;

public class LibrarySearchResultController
		extends AbstractModelViewController<ILibrarySearchResultModel, ILibrarySearchResultView>
		implements ILibrarySearchResultController {

	private LibraryListController libraryListController;
	private LibraryDetailsController libraryDetailsController;

	public LibrarySearchResultController(IController parent, ILibrarySearchResultModel model,
			ILibrarySearchResultView view) {
		super(parent, model, view);
	}

	@Override
	public void excludeLibrariesFromResults(List<Artifact> excludedLibraries) {
		List<LibraryListInfo> results = getModel().getResults();

		List<LibraryListInfo> filteredResults = results.stream()
				.filter(libraryInfo -> !excludedLibraries.stream()
						.anyMatch(excludedLibrary -> libraryInfo.getLibrary().getId().equals(excludedLibrary.getId())))
				.collect(Collectors.toList());

		showSearchResults(filteredResults);
	}
	
	@Override
	public void init() {
		getView().init();
		
		String description = getModel().getDescription();
		getView().setDescription(description);
		
		List<LibraryListInfo> results = getModel().getResults();
		showSearchResults(results);
	}

	private void showSearchResults(List<LibraryListInfo> results) {
		if (libraryListController != null)
			libraryListController.dispose();
		
		ILibraryListModel libraryListModel = new LibraryListModel(results);
		ILibraryListView libraryListView = new LibraryListView("No libraries were found");
		libraryListController = new LibraryListController(this, libraryListModel, libraryListView);
		libraryListController.init();
		getView().showLibraries(libraryListView);
	}

	@Subscribe
	public void onLibrarySelected(ILibraryListController.LibrarySelectionEvent e) {
		if (e.getSender() == libraryListController) {
			showDetailsOf(e.getLibrary());
		}
	}

	private void showDetailsOf(Artifact library) {
		if (libraryDetailsController != null)
			libraryDetailsController.dispose();

		List<Artifact> similarLibraries = getModel().getSimilarsTo(library, SimilarityMethod.CrossSim, 6);

		List<LibraryListInfo> similarsInfos = similarLibraries.stream().map(lib -> {
			return new LibraryListInfo(lib);
		}).collect(Collectors.toList());

		LibraryDetailsModel libraryDetailsModel = new LibraryDetailsModel(library, similarsInfos);
		LibraryDetailsView libraryDetailsView = new LibraryDetailsView();
		libraryDetailsController = new LibraryDetailsController(this, libraryDetailsModel, libraryDetailsView);
		libraryDetailsController.init();
		getView().showDetails(libraryDetailsView);
	}

	@Subscribe
	public void onSimilarsRequested(ILibraryDetailsController.SimilarsRequestEvent e) {
		if (e.getSender() == libraryDetailsController) {
			SimilarsRequestedEvent event = new SimilarsRequestedEvent(this, e.getSimilarsTo(), e.getSimilarityMethod());
			getEventBus().post(event);
		}
	}
}
