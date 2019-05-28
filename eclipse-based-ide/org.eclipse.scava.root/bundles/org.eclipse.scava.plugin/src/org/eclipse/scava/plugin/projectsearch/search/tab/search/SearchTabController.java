/*********************************************************************
* Copyright c 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse PublicLicense 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.projectsearch.search.tab.search;

import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.scava.plugin.mvc.controller.Controller;
import org.eclipse.scava.plugin.mvc.controller.ModelViewController;
import org.eclipse.scava.plugin.mvc.event.routed.IRoutedEvent;
import org.eclipse.scava.plugin.projectsearch.search.details.DetailsController;
import org.eclipse.scava.plugin.projectsearch.search.details.DetailsModel;
import org.eclipse.scava.plugin.projectsearch.search.details.DetailsView;
import org.eclipse.scava.plugin.projectsearch.search.searchresult.ShowDetailsRequestEvent;
import org.eclipse.scava.plugin.projectsearch.search.searchresult.SearchResultController;
import org.eclipse.scava.plugin.projectsearch.search.searchresult.SearchResultModel;
import org.eclipse.scava.plugin.projectsearch.search.searchresult.SearchResultView;

import io.swagger.client.model.Artifact;

public class SearchTabController extends ModelViewController<SearchTabModel, SearchTabView>
		implements ISearchTabViewEventListener {

	public SearchTabController(Controller parent, SearchTabModel model, SearchTabView view) {
		super(parent, model, view);
	}

	@Override
	public void init() {
		super.init();
		
		List<Artifact> results = getModel().getResults();
		
		List<SearchResultView> resultViews = results.stream().map(a -> {
			
			SearchResultModel model = new SearchResultModel(a);
			SearchResultView view = new SearchResultView();
			SearchResultController controller = new SearchResultController(SearchTabController.this, model, view);
			controller.init();
			
			return view;
		}).collect(Collectors.toList());
		
		getView().setResults(resultViews);
		getView().setDescription(getModel().getDescription());
		
		routeEventToParentController(new FilterAlreadySelectedProjectsRequestEvent(this, results));
	}

	@Override
	public void requestViewClose() {
		dispose();
	}

	@Override
	protected void onReceiveRoutedEventFromSubController(IRoutedEvent routedEvent, Controller forwarderController) {
		
		if( routedEvent instanceof ShowDetailsRequestEvent ) {
			ShowDetailsRequestEvent event = (ShowDetailsRequestEvent)routedEvent;
			
			DetailsModel model = new DetailsModel(event.getProject());
			DetailsView view = new DetailsView();
			DetailsController controller = new DetailsController(this, model, view);
			controller.init();
			
			getView().setDetails(view);
			
			getSubControllers().stream().filter(c -> c instanceof DetailsController && c != controller).forEach(c -> c.dispose());
			
			return;
		}
		
		super.onReceiveRoutedEventFromSubController(routedEvent, forwarderController);
	}
	
	
}
