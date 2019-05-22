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

import java.util.Collection;
import java.util.stream.Collectors;

import org.eclipse.scava.plugin.knowledgebase.access.KnowledgeBaseAccess;
import org.eclipse.scava.plugin.mvc.model.Model;

import io.swagger.client.ApiException;
import io.swagger.client.api.RecommenderRestControllerApi;
import io.swagger.client.model.ApiCallResult;
import io.swagger.client.model.Query;
import io.swagger.client.model.Recommendation;

public class CodeRecommendationModel extends Model {

	private final KnowledgeBaseAccess knowledgeBaseAccess = new KnowledgeBaseAccess();

	public Collection<ApiCallResult> getApiCallResults(String sourceCode) throws ApiException {

		RecommenderRestControllerApi recommenderRestController = knowledgeBaseAccess.getRecommenderRestController();

		Query query = new Query();
		query.setCurrentMethodCode(sourceCode);

		Recommendation recommendation = recommenderRestController.getApiCallRecommendationUsingPOST(query);
		return recommendation.getRecommendationItems().stream().map(r -> r.getApiCallRecommendation())
				.collect(Collectors.toList());
	}
}
