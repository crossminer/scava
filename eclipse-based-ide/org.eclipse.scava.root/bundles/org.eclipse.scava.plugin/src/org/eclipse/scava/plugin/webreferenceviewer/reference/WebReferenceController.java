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

import org.eclipse.scava.plugin.feedback.FeedbackController;
import org.eclipse.scava.plugin.feedback.FeedbackModel;
import org.eclipse.scava.plugin.feedback.FeedbackResource;
import org.eclipse.scava.plugin.feedback.FeedbackView;
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

	@Override
	public void onLeaveFeedback() {
		FeedbackResource feedbackResource = getModel().getFeedbackResource();
		FeedbackModel feedbackModel = new FeedbackModel(feedbackResource);
		FeedbackView feedbackView = new FeedbackView(getView().getShell());
		FeedbackController feedbackController = new FeedbackController(this, feedbackModel, feedbackView);
		feedbackController.init();
	}

}
