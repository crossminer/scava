/*********************************************************************
* Copyright c 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse PublicLicense 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.projectsearch.search.selectedResult;

import org.eclipse.scava.plugin.mvc.controller.Controller;
import org.eclipse.scava.plugin.mvc.controller.ModelViewController;
import org.eclipse.scava.plugin.mvc.event.routed.IRoutedEvent;
import org.eclipse.scava.plugin.projectsearch.search.ProjectDeselectedEvent;
import org.eclipse.scava.plugin.projectsearch.search.searchresult.ShowDetailsRequestEvent;

import io.swagger.client.model.Artifact;

public class SelectedResultController extends ModelViewController<SelectedResultModel, SelectedResultView> implements ISelectedResultViewEventListener {

	public SelectedResultController(Controller parent, SelectedResultModel model, SelectedResultView view) {
		super(parent, model, view);
	}

	@Override
	public void init() {
		super.init();
		
		Artifact project = getModel().getProject();
		
		getView().setName(project.getFullName());
	}

	@Override
	public void requestViewClose() {
		dispose();
	}

	@Override
	public void onRemove() {
		DeselectRequestEvent event = new DeselectRequestEvent(this, getModel().getProject());
		routeEventToParentController(event);
	}

	@Override
	protected void onReceiveRoutedEventFromParentController(IRoutedEvent routedEvent) {
		if(routedEvent instanceof ProjectDeselectedEvent) {
			ProjectDeselectedEvent event = (ProjectDeselectedEvent)routedEvent;
			
			if( getModel().getProject().getId().equals(event.getProject().getId()) ) {
				dispose();
				return;
			}
		}
		
		super.onReceiveRoutedEventFromParentController(routedEvent);
	}

	@Override
	public void onOpen() {
		routeEventToParentController(new ShowDetailsRequestEvent(this, getModel().getProject()));
	}
	
	
}
