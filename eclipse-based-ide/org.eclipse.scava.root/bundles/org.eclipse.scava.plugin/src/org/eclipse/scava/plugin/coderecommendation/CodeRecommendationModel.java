/*********************************************************************
* Copyright (c) 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.coderecommendation;

import java.util.Map;
import java.util.stream.Collectors;

import org.eclipse.scava.plugin.feedback.FeedbackResource;
import org.eclipse.scava.plugin.knowledgebase.access.KnowledgeBaseAccess;
import org.eclipse.scava.plugin.mvc.model.Model;
import org.eclipse.scava.plugin.preferences.Preferences;

import io.swagger.client.ApiException;
import io.swagger.client.api.RecommenderRestControllerApi;
import io.swagger.client.model.ApiCallResult;
import io.swagger.client.model.Query;
import io.swagger.client.model.Recommendation;
import io.swagger.client.model.RecommendationItem;

public class CodeRecommendationModel extends Model {

	private final KnowledgeBaseAccess knowledgeBaseAccess;

	public CodeRecommendationModel(KnowledgeBaseAccess knowledgeBaseAccess) {
		super();
		this.knowledgeBaseAccess = knowledgeBaseAccess;
	}

	public Map<ApiCallResult, FeedbackResource> getApiCallResults(String sourceCode) throws ApiException {

		RecommenderRestControllerApi recommenderRestController = knowledgeBaseAccess
				.getRecommenderRestController(Preferences.TIMEOUT_CODERECOMMENDATION);

		Query query = new Query();
		query.setCurrentMethodCode(sourceCode);

		Recommendation recommendation = recommenderRestController.getApiCallRecommendationUsingPOST(query);

		return recommendation.getRecommendationItems().stream().collect(Collectors
				.toMap(RecommendationItem::getApiCallRecommendation, item -> new FeedbackResource(query, item)));
	}

}
