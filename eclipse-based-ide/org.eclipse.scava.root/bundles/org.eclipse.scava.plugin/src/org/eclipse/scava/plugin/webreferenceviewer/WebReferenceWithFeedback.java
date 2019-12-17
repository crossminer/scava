/*********************************************************************
* Copyright c 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse PublicLicense 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.webreferenceviewer;

import org.eclipse.scava.plugin.feedback.FeedbackResource;

public class WebReferenceWithFeedback {
	private final String id;
	private final FeedbackResource feedbackResource;

	public WebReferenceWithFeedback(String id, FeedbackResource feedbackResource) {
		super();
		this.id = id;
		this.feedbackResource = feedbackResource;
	}

	public FeedbackResource getFeedbackResource() {
		return feedbackResource;
	}

	public String getId() {
		return id;
	}
}
