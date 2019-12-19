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

import io.swagger.client.model.Query;
import io.swagger.client.model.Recommendation;
import io.swagger.client.model.RecommendationItem;

public class FeedbackResource {
	private final Query query;
	private final Recommendation recommendation;

	public FeedbackResource(Query query, RecommendationItem recommendationItem) {
		super();
		this.query = query;
		this.recommendation = new Recommendation().addRecommendationItemsItem(recommendationItem);
	}

	public FeedbackResource(Query query, Recommendation recommendation) {
		super();
		this.query = query;
		this.recommendation = recommendation;
	}

	public Query getQuery() {
		return query;
	}

	public Recommendation getRecommendation() {
		return recommendation;
	}
}
