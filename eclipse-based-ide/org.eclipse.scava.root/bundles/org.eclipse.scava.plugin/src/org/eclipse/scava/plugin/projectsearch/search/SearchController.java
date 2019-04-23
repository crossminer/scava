/*********************************************************************
* Copyright c 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse PublicLicense 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.projectsearch.search;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.scava.plugin.mvc.controller.Controller;
import org.eclipse.scava.plugin.mvc.controller.ModelViewController;
import org.eclipse.scava.plugin.mvc.event.routed.IRoutedEvent;
import org.eclipse.scava.plugin.projectsearch.ProjectSearchInstallRequestEvent;
import org.eclipse.scava.plugin.projectsearch.search.details.SearchSimilarsRequestEvent;
import org.eclipse.scava.plugin.projectsearch.search.searchresult.ShowDetailsRequestEvent;
import org.eclipse.scava.plugin.projectsearch.search.selectedResult.DeselectRequestEvent;
import org.eclipse.scava.plugin.projectsearch.search.selectedResult.SelectedResultController;
import org.eclipse.scava.plugin.projectsearch.search.selectedResult.SelectedResultModel;
import org.eclipse.scava.plugin.projectsearch.search.selectedResult.SelectedResultView;
import org.eclipse.scava.plugin.projectsearch.search.searchresult.SearchResultPickRequestEvent;
import org.eclipse.scava.plugin.projectsearch.search.tab.details.DetailsTabController;
import org.eclipse.scava.plugin.projectsearch.search.tab.details.DetailsTabModel;
import org.eclipse.scava.plugin.projectsearch.search.tab.details.DetailsTabView;
import org.eclipse.scava.plugin.projectsearch.search.tab.search.ExpressionSearchTabModel;
import org.eclipse.scava.plugin.projectsearch.search.tab.search.FilterAlreadySelectedProjectsRequestEvent;
import org.eclipse.scava.plugin.projectsearch.search.tab.search.SearchTabController;
import org.eclipse.scava.plugin.projectsearch.search.tab.search.SearchTabModel;
import org.eclipse.scava.plugin.projectsearch.search.tab.search.SearchTabView;
import org.eclipse.scava.plugin.projectsearch.search.tab.search.SimilarsSearchTabModel;

import io.swagger.client.model.Artifact;

public class SearchController extends ModelViewController<SearchModel, SearchView> implements ISearchViewEventListener {

	public SearchController(Controller parent, SearchModel model, SearchView view) {
		super(parent, model, view);
	}

	@Override
	public void requestViewClose() {
		dispose();
	}

	@Override
	public void onSearch(String expression) {
		if (expression.isEmpty()) {
			return;
		}

		SearchTabModel model = new ExpressionSearchTabModel(expression);
		SearchTabView view = new SearchTabView();
		SearchTabController controller = new SearchTabController(this, model, view);
		controller.init();

		getView().addSearchTab(model.getDescription(), view);
	}

	@Override
	protected void onReceiveRoutedEventFromSubController(IRoutedEvent routedEvent, Controller forwarderController) {
		if (routedEvent instanceof SearchResultPickRequestEvent) {
			SearchResultPickRequestEvent event = (SearchResultPickRequestEvent) routedEvent;
			selectProject(event.getProject());
			return;
		}

		if (routedEvent instanceof DeselectRequestEvent) {
			DeselectRequestEvent event = (DeselectRequestEvent) routedEvent;
			deselectProject(event.getProject());
			return;
		}

		if (routedEvent instanceof FilterAlreadySelectedProjectsRequestEvent) {
			FilterAlreadySelectedProjectsRequestEvent event = (FilterAlreadySelectedProjectsRequestEvent) routedEvent;

			List<Artifact> filtered = event.getProjects().stream()
					.filter(a -> getModel().getSelectedProjects().contains(a)).collect(Collectors.toList());
			for (Artifact project : filtered) {
				routeEventToSubControllers(new ProjectSelectedEvent(this, project));
			}
			return;
		}

		if (routedEvent instanceof SearchSimilarsRequestEvent) {
			SearchSimilarsRequestEvent event = (SearchSimilarsRequestEvent) routedEvent;

			SearchTabModel model = new SimilarsSearchTabModel(event.getProject(), event.getSimilarityMethod());
			SearchTabView view = new SearchTabView();
			SearchTabController controller = new SearchTabController(this, model, view);
			controller.init();

			getView().addSearchTab(model.getDescription(), view);

			return;
		}

		if (routedEvent instanceof ShowDetailsRequestEvent) {
			ShowDetailsRequestEvent event = (ShowDetailsRequestEvent) routedEvent;

			DetailsTabModel model = new DetailsTabModel(event.getProject());
			DetailsTabView view = new DetailsTabView();
			DetailsTabController controller = new DetailsTabController(this, model, view);
			controller.init();

			getView().addSearchTab(
					"Details of \"" + Optional.ofNullable(event.getProject().getFullName()).orElse("-") + "\"", view);

			return;
		}

		super.onReceiveRoutedEventFromSubController(routedEvent, forwarderController);
	}

	@Override
	protected void onReceiveRoutedEventFromParentController(IRoutedEvent routedEvent) {

		if (routedEvent instanceof SearchHideRequestEvent) {
			SearchHideRequestEvent event = (SearchHideRequestEvent) routedEvent;

			getView().setVisible(false);

			return;
		}

		if (routedEvent instanceof SearchShowRequestEvent) {
			SearchShowRequestEvent event = (SearchShowRequestEvent) routedEvent;

			getView().setVisible(true);

			return;
		}

		super.onReceiveRoutedEventFromParentController(routedEvent);
	}

	private void selectProject(Artifact project) {
		getModel().addSelectedProject(project);

		SelectedResultModel model = new SelectedResultModel(project);
		SelectedResultView view = new SelectedResultView();
		SelectedResultController controller = new SelectedResultController(this, model, view);
		controller.init();

		getView().addToSelectedList(view);

		ProjectSelectedEvent event = new ProjectSelectedEvent(this, project);
		routeEventToSubControllers(event);

		getView().setEnableInstall(!getModel().getSelectedProjects().isEmpty());
	}

	private void deselectProject(Artifact project) {
		getModel().removeSelectedProject(project);

		routeEventToSubControllers(new ProjectDeselectedEvent(this, project));

		getView().setEnableInstall(!getModel().getSelectedProjects().isEmpty());
	}

	@Override
	public void onInstall() {
		Set<Artifact> projects = getModel().getSelectedProjects();

		if (projects.isEmpty()) {
			MessageDialog.openInformation(getView().getShell(), "No projects",
					"There is no project selected to be installed");
			return;
		}

		routeEventToParentController(new ProjectSearchInstallRequestEvent(this, projects));
	}
}
