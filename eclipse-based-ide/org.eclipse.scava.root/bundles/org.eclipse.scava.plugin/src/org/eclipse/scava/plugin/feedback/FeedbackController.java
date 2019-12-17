/*********************************************************************
* Copyright c 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse PublicLicense 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.feedback;

import org.eclipse.scava.plugin.main.SendFeedbackRequestEvent;
import org.eclipse.scava.plugin.mvc.controller.Controller;
import org.eclipse.scava.plugin.mvc.controller.ModelViewController;

public class FeedbackController extends ModelViewController<FeedbackModel, FeedbackView>
		implements IFeedbackViewEventListener {

	public FeedbackController(Controller parent, FeedbackModel model, FeedbackView view) {
		super(parent, model, view);
	}

	@Override
	public void init() {
		super.init();
	}

	@Override
	public void requestViewClose() {
		dispose();
	}

	@Override
	public void onLeaveFeedback(String message, int rating) {
		SendFeedbackRequestEvent request = new SendFeedbackRequestEvent(this, getModel().getFeedbackResource(), rating,
				message);
		routeEventToParentController(request);
		if (request.isDone()) {
			dispose();
		}
	}

}
