/*********************************************************************
* Copyright c 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse PublicLicense 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.projectsearch;

import org.eclipse.scava.plugin.mvc.controller.Controller;
import org.eclipse.scava.plugin.mvc.event.routed.IRoutedEvent;
import org.eclipse.scava.plugin.projectsearch.install.InstallController;
import org.eclipse.scava.plugin.projectsearch.install.InstallModel;
import org.eclipse.scava.plugin.projectsearch.install.InstallView;
import org.eclipse.scava.plugin.projectsearch.search.SearchController;
import org.eclipse.scava.plugin.projectsearch.search.SearchHideRequestEvent;
import org.eclipse.scava.plugin.projectsearch.search.SearchModel;
import org.eclipse.scava.plugin.projectsearch.search.SearchShowRequestEvent;
import org.eclipse.scava.plugin.projectsearch.search.SearchView;
import org.eclipse.ui.PlatformUI;

public class ProjectSearchController extends Controller {
	
	public ProjectSearchController(Controller parent) {
		super(parent);
	}

	@Override
	public void init() {
		super.init();
		
		SearchModel model = new SearchModel();
		SearchView view = new SearchView(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell());
		SearchController controller = new SearchController(this, model, view);
		controller.init();
	}
	
	@Override
	protected void onReceiveRoutedEventFromSubController(IRoutedEvent routedEvent, Controller forwarderController) {
		if (routedEvent instanceof ProjectSearchInstallRequestEvent) {
			ProjectSearchInstallRequestEvent event = (ProjectSearchInstallRequestEvent) routedEvent;

			routeEventToSubControllers(new SearchHideRequestEvent(this));
			
			InstallModel model = new InstallModel(event.getProjects());
			InstallView view = new InstallView(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell());
			InstallController controller = new InstallController(this, model, view);
			controller.init();
			
			return;
		}
		
		if( routedEvent instanceof ProjectSearchInstallFinishedEvent ) {
			dispose();
			return;
		}
		
		if (routedEvent instanceof ProjectSearchSearchRequestEvent) {
			ProjectSearchSearchRequestEvent event = (ProjectSearchSearchRequestEvent) routedEvent;
			getSubControllers().stream().filter(InstallController.class::isInstance).forEach(Controller::dispose);
			routeEventToSubControllers(new SearchShowRequestEvent(this));
			return;
		}
		
		super.onReceiveRoutedEventFromSubController(routedEvent, forwarderController);
	}
}
