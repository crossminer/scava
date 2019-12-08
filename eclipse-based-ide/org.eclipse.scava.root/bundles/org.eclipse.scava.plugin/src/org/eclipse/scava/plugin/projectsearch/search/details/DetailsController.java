/*********************************************************************
* Copyright c 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse PublicLicense 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.projectsearch.search.details;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.scava.plugin.Activator;
import org.eclipse.scava.plugin.knowledgebase.access.SimilarityMethod;
import org.eclipse.scava.plugin.main.OpenInExternalBrowserRequestEvent;
import org.eclipse.scava.plugin.mvc.controller.Controller;
import org.eclipse.scava.plugin.mvc.controller.ModelViewController;
import org.eclipse.scava.plugin.preferences.Preferences;

public class DetailsController extends ModelViewController<DetailsModel, DetailsView>
		implements IDetailsViewEventListener {

	public DetailsController(Controller parent, DetailsModel model, DetailsView view) {
		super(parent, model, view);
	}

	@Override
	public void init() {
		super.init();

		getView().setProject(getModel().getProject());
	}

	@Override
	public void requestViewClose() {
		dispose();
	}

	@Override
	public void onCheckOnGithub() {
		String url = getModel().getProject().getHtmlUrl();
		if (url != null) {
			routeEventToParentController(new OpenInExternalBrowserRequestEvent(this, url));
		}
	}

	@Override
	public void onCheckOnWeb() {
		String url = getModel().getProject().getHomePage();
		if (url != null) {
			routeEventToParentController(new OpenInExternalBrowserRequestEvent(this, url));
		}
	}

	@Override
	public void onCheckOnWebDashboard() {
		String id = getModel().getProject().getWebDashboardId();
		if (id != null) {
			IPreferenceStore preferences = Activator.getDefault().getPreferenceStore();
			String webdashboardBasePath = preferences.getString(Preferences.WEBDASHBOARD_BASE_PATH);

			String url = webdashboardBasePath + id;
			routeEventToParentController(new OpenInExternalBrowserRequestEvent(this, url));
		}
	}

	@Override
	public void onSearchSimilars(SimilarityMethod method) {
		routeEventToParentController(new SearchSimilarsRequestEvent(this, getModel().getProject(), method));
	}

	@Override
	public void onOpenInBrowserRequest(String url) {
		routeEventToParentController(new OpenInExternalBrowserRequestEvent(this, url));
	}

}
