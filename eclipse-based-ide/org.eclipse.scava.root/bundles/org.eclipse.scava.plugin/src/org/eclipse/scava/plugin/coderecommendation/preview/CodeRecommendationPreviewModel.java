/*********************************************************************
* Copyright (c) 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.coderecommendation.preview;

import java.util.List;

import org.eclipse.scava.plugin.coderecommendation.CodeRecommendation;
import org.eclipse.scava.plugin.coderecommendation.CodeRecommendationRequest;
import org.eclipse.scava.plugin.coderecommendation.IPreviewable;
import org.eclipse.scava.plugin.feedback.FeedbackResource;
import org.eclipse.scava.plugin.mvc.model.Model;

import io.swagger.client.model.Query;
import io.swagger.client.model.Recommendation;

public class CodeRecommendationPreviewModel extends Model {
	private final IPreviewable previewable;
	private final FeedbackResource feedbackResource;

	public CodeRecommendationPreviewModel(IPreviewable previewable) {
		super();
		this.previewable = previewable;

		if (previewable instanceof CodeRecommendation) {
			CodeRecommendation codeRecommendation = (CodeRecommendation) previewable;
			this.feedbackResource = codeRecommendation.getFeedbackResource();
		} else {
			CodeRecommendationRequest codeRecommendationRequest = (CodeRecommendationRequest) previewable;
			List<CodeRecommendation> codeRecommendations = codeRecommendationRequest.getCodeRecommendations();
			Recommendation recommendation = new Recommendation();
			Query query = codeRecommendations.get(0).getFeedbackResource().getQuery();

			codeRecommendations.forEach(r -> {
				r.getFeedbackResource().getRecommendation().getRecommendationItems().forEach(item -> {
					recommendation.addRecommendationItemsItem(item);
				});
			});

			this.feedbackResource = new FeedbackResource(query, recommendation);
		}
	}

	public IPreviewable getPreviewable() {
		return previewable;
	}

	public FeedbackResource getFeedbackResource() {
		return feedbackResource;
	}
}
