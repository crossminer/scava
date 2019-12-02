/*********************************************************************
* Copyright c 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse PublicLicense 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.webreferenceviewer.reference;

import org.eclipse.scava.plugin.main.OpenInExternalBrowserRequestEvent;
import org.eclipse.scava.plugin.mvc.controller.Controller;
import org.eclipse.scava.plugin.mvc.controller.ModelViewController;

public class WebReferenceController extends ModelViewController<WebReferenceModel, WebReferenceView>
		implements IWebReferenceViewEventListener {

	public WebReferenceController(Controller parent, WebReferenceModel model, WebReferenceView view) {
		super(parent, model, view);
	}

	@Override
	public void init() {
		super.init();

		getView().setUrl(getModel().getUrl());
		getView().setTitle(getModel().getTitle());
	}

	@Override
	public void requestViewClose() {
		dispose();
	}

	@Override
	public void onOpenUrl() {
		String url = getModel().getUrl();
		routeEventToParentController(new OpenInExternalBrowserRequestEvent(this, url));
	}

}
