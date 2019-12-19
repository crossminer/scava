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

import org.eclipse.scava.plugin.feedback.FeedbackResource;
import org.eclipse.scava.plugin.mvc.model.Model;

public abstract class WebReferenceModel extends Model {
	private final String url;
	private final String title;
	private final FeedbackResource feedbackResource;

	public WebReferenceModel(String url, String title, FeedbackResource feedbackResource) {
		super();
		this.url = url;
		this.title = title;
		this.feedbackResource = feedbackResource;
	}

	public String getUrl() {
		return url;
	}

	public String getTitle() {
		return title;
	}

	public FeedbackResource getFeedbackResource() {
		return feedbackResource;
	}
}
