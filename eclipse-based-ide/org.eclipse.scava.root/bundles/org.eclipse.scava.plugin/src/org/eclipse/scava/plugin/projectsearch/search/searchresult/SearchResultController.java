/*********************************************************************
* Copyright c 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse PublicLicense 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.projectsearch.search.searchresult;

import org.eclipse.scava.plugin.projectsearch.search.ProjectSelectedEvent;
import org.eclipse.scava.plugin.mvc.controller.Controller;
import org.eclipse.scava.plugin.mvc.controller.ModelViewController;
import org.eclipse.scava.plugin.mvc.event.routed.IRoutedEvent;
import org.eclipse.scava.plugin.projectsearch.search.ProjectDeselectedEvent;

import io.swagger.client.model.Artifact;

public class SearchResultController extends ModelViewController<SearchResultModel, SearchResultView>
		implements ISearchResultViewEventListener {

	public SearchResultController(Controller parent, SearchResultModel model, SearchResultView view) {
		super(parent, model, view);
	}

	@Override
	public void init() {
		super.init();

		Artifact project = getModel().getProject();

		getView().setName(project.getFullName());
		getView().setStarred(project.getStarred().size());
		getView().setDependencies(project.getDependencies().size());
		getView().setActive(project.isActive());
		getView().setYear(project.getYear());
	}

	@Override
	public void requestViewClose() {
		dispose();
	}

	@Override
	public void onAdd() {
		SearchResultPickRequestEvent event = new SearchResultPickRequestEvent(this, getModel().getProject());
		routeEventToParentController(event);
	}

	@Override
	public void onOpen() {
		routeEventToParentController(new ShowDetailsRequestEvent(this, getModel().getProject()));
	}

	@Override
	protected void onReceiveRoutedEventFromParentController(IRoutedEvent routedEvent) {
		if (routedEvent instanceof ProjectSelectedEvent) {
			ProjectSelectedEvent event = (ProjectSelectedEvent) routedEvent;

			if (getModel().getProject().getId().equals(event.getProject().getId())) {
				getView().setAvailable(false);
				
				return;
			}
		}

		if (routedEvent instanceof ProjectDeselectedEvent) {
			ProjectDeselectedEvent event = (ProjectDeselectedEvent) routedEvent;

			if (getModel().getProject().getId().equals(event.getProject().getId())) {
				getView().setAvailable(true);
				
				return;
			}
		}

		super.onReceiveRoutedEventFromParentController(routedEvent);
	}
}
