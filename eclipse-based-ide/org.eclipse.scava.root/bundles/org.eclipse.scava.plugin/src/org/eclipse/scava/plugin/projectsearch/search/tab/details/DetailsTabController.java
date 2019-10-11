/*********************************************************************
* Copyright c 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse PublicLicense 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.projectsearch.search.tab.details;

import org.eclipse.scava.plugin.mvc.controller.Controller;
import org.eclipse.scava.plugin.mvc.controller.ModelViewController;
import org.eclipse.scava.plugin.projectsearch.search.details.DetailsController;
import org.eclipse.scava.plugin.projectsearch.search.details.DetailsModel;
import org.eclipse.scava.plugin.projectsearch.search.details.DetailsView;

public class DetailsTabController extends ModelViewController<DetailsTabModel, DetailsTabView>
		implements IDetailsTabViewEventListener {

	public DetailsTabController(Controller parent, DetailsTabModel model, DetailsTabView view) {
		super(parent, model, view);
	}

	@Override
	public void init() {
		super.init();
		
		DetailsModel model = new DetailsModel(getModel().getProject());
		DetailsView view = new DetailsView();
		DetailsController controller = new DetailsController(this, model, view);
		controller.init();

		getView().setDetails(view);
	}

	@Override
	public void requestViewClose() {
		dispose();
	}

}
