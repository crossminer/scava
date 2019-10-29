/*********************************************************************
* Copyright c 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse PublicLicense 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.apidocumentation;

import org.eclipse.scava.plugin.async.api.ApiAsyncBuilder;
import org.eclipse.scava.plugin.async.api.IApiAsyncBuilder;
import org.eclipse.scava.plugin.knowledgebase.access.KnowledgeBaseAccess;
import org.eclipse.scava.plugin.mvc.model.Model;

import io.swagger.client.api.RecommenderRestControllerApi;
import io.swagger.client.model.Query;
import io.swagger.client.model.Recommendation;

public class ApiDocumentationModel extends Model {
	private final KnowledgeBaseAccess knowledgeBaseAccess;

	public ApiDocumentationModel() {
		knowledgeBaseAccess = new KnowledgeBaseAccess();
	}

	public IApiAsyncBuilder<Recommendation> requestApiDocumentationAsync(String methodCode) {
		return ApiAsyncBuilder.build(apiCallback -> {
			RecommenderRestControllerApi recommenderRestController = knowledgeBaseAccess.getRecommenderRestController();

			Query query = new Query();
			query.setCompilationUnit(methodCode);
			return recommenderRestController.getApiDocumentationRecommendationUsingPOSTAsync(query, apiCallback);
		});
	}
}
