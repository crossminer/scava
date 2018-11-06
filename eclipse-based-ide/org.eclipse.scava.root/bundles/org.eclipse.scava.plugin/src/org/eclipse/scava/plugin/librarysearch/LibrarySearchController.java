package org.eclipse.scava.plugin.librarysearch;

import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.maven.model.Dependency;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.eclipse.scava.plugin.librarysearch.list.ILibraryListModel;
import org.eclipse.scava.plugin.librarysearch.list.ILibraryListView;
import org.eclipse.scava.plugin.librarysearch.list.LibraryListController;
import org.eclipse.scava.plugin.librarysearch.list.LibraryListInfo;
import org.eclipse.scava.plugin.librarysearch.list.LibraryListModel;
import org.eclipse.scava.plugin.librarysearch.list.LibraryListView;
import org.eclipse.scava.plugin.librarysearch.tabs.finish.ILibrarySearchFinishController;
import org.eclipse.scava.plugin.librarysearch.tabs.finish.ILibrarySearchFinishModel;
import org.eclipse.scava.plugin.librarysearch.tabs.finish.ILibrarySearchFinishView;
import org.eclipse.scava.plugin.librarysearch.tabs.finish.LibrarySearchFinishController;
import org.eclipse.scava.plugin.librarysearch.tabs.finish.LibrarySearchFinishModel;
import org.eclipse.scava.plugin.librarysearch.tabs.finish.LibrarySearchFinishView;
import org.eclipse.scava.plugin.librarysearch.tabs.recommendedlibs.ILibrarySearchRecommendedLibsController;
import org.eclipse.scava.plugin.librarysearch.tabs.recommendedlibs.ILibrarySearchRecommendedLibsModel;
import org.eclipse.scava.plugin.librarysearch.tabs.recommendedlibs.ILibrarySearchRecommendedLibsView;
import org.eclipse.scava.plugin.librarysearch.tabs.recommendedlibs.LibrarySearchRecommendedLibsController;
import org.eclipse.scava.plugin.librarysearch.tabs.recommendedlibs.LibrarySearchRecommendedLibsModel;
import org.eclipse.scava.plugin.librarysearch.tabs.recommendedlibs.LibrarySearchRecommendedLibsView;
import org.eclipse.scava.plugin.librarysearch.tabs.results.ILibrarySearchResultController;
import org.eclipse.scava.plugin.librarysearch.tabs.results.ILibrarySearchResultModel;
import org.eclipse.scava.plugin.librarysearch.tabs.results.ILibrarySearchResultView;
import org.eclipse.scava.plugin.librarysearch.tabs.results.LibrarySearchResultController;
import org.eclipse.scava.plugin.librarysearch.tabs.results.LibrarySearchResultModel;
import org.eclipse.scava.plugin.librarysearch.tabs.results.LibrarySearchResultView;
import org.eclipse.scava.plugin.mvc.IController;
import org.eclipse.scava.plugin.mvc.implementation.AbstractModelViewController;
import org.eclipse.scava.plugin.usermonitoring.event.scava.ScavaSearchUsageEvent;

import com.google.common.eventbus.Subscribe;

import io.swagger.client.model.Artifact;
import io.swagger.client.model.RecommendedLibrary;

public class LibrarySearchController extends AbstractModelViewController<ILibrarySearchModel, ILibrarySearchView> implements ILibrarySearchController {

	private LibraryListController toBeInstalledLibraryListController;

	private boolean isFinishedCurrentlyShown;

	public LibrarySearchController(IController parent, ILibrarySearchModel model, ILibrarySearchView view) {
		super(parent, model, view);
	}

	@Override
	public void init() {
		getView().init();

		showToBeInstalledLibraries();
	}

	private void showToBeInstalledLibraries() {
		if (toBeInstalledLibraryListController != null)
			toBeInstalledLibraryListController.dispose();

		List<Artifact> toBeInstalledLibraries = getModel().getToBeInstalledLibraries();

		List<LibraryListInfo> libraryInfos = toBeInstalledLibraries.stream().map(library -> {
			LibraryListInfo libraryListInfo = new LibraryListInfo(library);
			libraryListInfo.setActionLabel("Remove");
			if (!isFinishedCurrentlyShown) {
				libraryListInfo.setAction(() -> {
					getModel().removeToBeInstalled(library);
					showToBeInstalledLibraries();
				});
			}
			libraryListInfo.setComment("Will be installed");
			libraryListInfo.setShowCompact(true);
			return libraryListInfo;
		}).collect(Collectors.toList());

		ILibraryListModel libraryListModel = new LibraryListModel(libraryInfos);
		ILibraryListView libraryListView = new LibraryListView("No library selected to install");
		toBeInstalledLibraryListController = new LibraryListController(this, libraryListModel, libraryListView);
		toBeInstalledLibraryListController.init();
		getView().showToBeInstalled(libraryListView);

		for (IController subController : getSubControllers()) {
			if (subController instanceof ILibrarySearchResultController) {
				((ILibrarySearchResultController) subController).excludeLibrariesFromResults(toBeInstalledLibraries);
			}
		}
	}

	@Subscribe
	public void onSearchRequest(ILibrarySearchView.SearchRequestEvent e) {
		if (e.getSender() == getView()) {
			String queryString = e.getQueryString();
			List<Artifact> queriedLibraries = getModel().getLibrariesByQueryString(queryString);
			showSearchResults("Search for \"" + queryString + "\"", "Search result of \"" + queryString + "\"", queriedLibraries);
			getEventBus().post(new ScavaSearchUsageEvent());
		}
	}

	@Subscribe
	public void onSimilarsRequested(ILibrarySearchResultController.SimilarsRequestedEvent e) {
		if (isSubController(e.getSender())) {

			List<Artifact> similiarLibraries = getModel().getSimilarLibrariesTo(e.getLibrary(), e.getSimilarityMethod(), 10);

			String description = "Similar libraries to \"" + e.getLibrary().getFullName() + "\"";
			showSearchResults(description, description, similiarLibraries);
			getEventBus().post(new ScavaSearchUsageEvent());
		}

	}

	private void showSearchResults(String label, String description, List<Artifact> libraries) {
		List<LibraryListInfo> libraryInfos = libraries.stream().map(library -> {
			LibraryListInfo libraryListInfo = new LibraryListInfo(library);
			libraryListInfo.setActionLabel("Add");
			libraryListInfo.setAction(() -> {
				getModel().addToBeInstalled(library);
				showToBeInstalledLibraries();
			});
			return libraryListInfo;
		}).collect(Collectors.toList());

		ILibrarySearchResultModel librarySearchResultModel = new LibrarySearchResultModel(description, libraryInfos);
		ILibrarySearchResultView librarySearchResultView = new LibrarySearchResultView();

		LibrarySearchResultController librarySearchResultController = new LibrarySearchResultController(this, librarySearchResultModel, librarySearchResultView);
		librarySearchResultController.init();
		librarySearchResultController.excludeLibrariesFromResults(getModel().getToBeInstalledLibraries());

		getView().showSearchResult(label, librarySearchResultView);
	}

	@Subscribe
	public void onFinishRequest(ILibrarySearchView.FinishRequestEvent e) {
		if (e.getSender() == getView()) {
			List<String> currentlyUsedLibraries;
			try {
				MavenXpp3Reader reader = new MavenXpp3Reader();
				Model model = reader.read(new FileReader(getModel().getPom()));
				List<Dependency> dependencies = model.getDependencies();
				currentlyUsedLibraries = dependencies.stream().map(dependency -> dependency.getGroupId() + ":" + dependency.getArtifactId()).collect(Collectors.toList());
			} catch (IOException | XmlPullParserException e1) {
				currentlyUsedLibraries = null;
			}

			ILibrarySearchFinishModel librarySearchFinishModel = new LibrarySearchFinishModel(currentlyUsedLibraries);
			ILibrarySearchFinishView librarySearchFinishView = new LibrarySearchFinishView();

			ILibrarySearchFinishController librarySearchFinishController = new LibrarySearchFinishController(this, librarySearchFinishModel, librarySearchFinishView);
			librarySearchFinishController.init();

			getView().showFinish(librarySearchFinishView);

			isFinishedCurrentlyShown = true;
			showToBeInstalledLibraries();
		}
	}

	@Subscribe
	public void onSearchRecommendedLibraries(ILibrarySearchFinishController.RecommendedSearchRequestEvent e) {
		if (isSubController(e.getSender())) {
			List<RecommendedLibrary> recommendedLibraries = getModel().getRecommendedLibraries(e.getSelectedLibraries());

			ILibrarySearchRecommendedLibsModel librarySearchRecommendedLibsModel = new LibrarySearchRecommendedLibsModel(recommendedLibraries, getModel().getPom());

			ILibrarySearchRecommendedLibsView librarySearchRecommendedLibsView = new LibrarySearchRecommendedLibsView();

			ILibrarySearchRecommendedLibsController librarySearchRecommendedLibsController = new LibrarySearchRecommendedLibsController(this, librarySearchRecommendedLibsModel,
					librarySearchRecommendedLibsView);
			librarySearchRecommendedLibsController.init();
			getView().showSuggestedLibraries("Suggested libraries", librarySearchRecommendedLibsView);

			Arrays.stream(getSubControllers()).filter(c -> c instanceof ILibrarySearchFinishController).forEach(c -> c.dispose());

		}
	}

	@Subscribe
	public void onFinishClosed(ILibrarySearchFinishController.Close e) {
		isFinishedCurrentlyShown = false;
		showToBeInstalledLibraries();
	}

}
