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

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Collection;
import java.util.Scanner;
import java.util.stream.Collectors;

import org.eclipse.scava.plugin.apidocumentation.result.ApiDocumentation;
import org.eclipse.scava.plugin.knowledgebase.access.KnowledgeBaseAccess;
import org.eclipse.scava.plugin.mvc.model.Model;

import io.swagger.client.ApiException;
import io.swagger.client.api.RecommenderRestControllerApi;
import io.swagger.client.model.Query;
import io.swagger.client.model.Recommendation;

public class ApiDocumentationModel extends Model {
	private final KnowledgeBaseAccess knowledgeBaseAccess;

	public ApiDocumentationModel() {
		knowledgeBaseAccess = new KnowledgeBaseAccess();
	}

	public Collection<ApiDocumentation> getApiDocumentations(String methodCode) throws ApiException {
		RecommenderRestControllerApi recommenderRestController = knowledgeBaseAccess.getRecommenderRestController();

		Query query = new Query();
		query.setCompilationUnit(methodCode);

		Recommendation recommendation = recommenderRestController.getApiDocumentationRecommendationUsingPOST(query);
		return recommendation.getRecommendationItems().stream().map(r -> {
			String url = "https://stackoverflow.com/questions/" + r.getApiDocumentationLink();
			String label = getTitleOfPage(url);
			return new ApiDocumentation(label, url);
		}).collect(Collectors.toList());
	}

	private String getTitleOfPage(String url) {
		try {
			InputStream response = new URL(url).openStream();

			Scanner scanner = new Scanner(response);
			String responseBody = scanner.useDelimiter("\\A").next();
			return responseBody.substring(responseBody.indexOf("<title>") + 7, responseBody.indexOf("</title>"));
		} catch (IOException e) {
			e.printStackTrace();
			return "Title is not available";
		}
	}
}
