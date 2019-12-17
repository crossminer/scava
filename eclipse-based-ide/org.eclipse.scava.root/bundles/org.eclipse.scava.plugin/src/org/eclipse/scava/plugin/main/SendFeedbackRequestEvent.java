/*********************************************************************
* Copyright c 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse PublicLicense 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.main;

import org.eclipse.scava.plugin.feedback.FeedbackResource;
import org.eclipse.scava.plugin.mvc.controller.Controller;
import org.eclipse.scava.plugin.mvc.event.routed.RoutedEvent;

public class SendFeedbackRequestEvent extends RoutedEvent {
	private final FeedbackResource feedbackResource;
	private final int rating;
	private final String message;
	private boolean done;

	public SendFeedbackRequestEvent(Controller source, FeedbackResource feedbackResource, int rating, String message) {
		super(source);
		this.feedbackResource = feedbackResource;
		this.rating = rating;
		this.message = message;
	}

	public FeedbackResource getFeedbackResource() {
		return feedbackResource;
	}

	public int getRating() {
		return rating;
	}

	public String getMessage() {
		return message;
	}

	public void setDone(boolean done) {
		this.done = done;
	}

	public boolean isDone() {
		return done;
	}
}
