package org.eclipse.scava.plugin.apidocumentation;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import org.eclipse.scava.plugin.knowledgebase.access.KnowledgeBaseAccess;
import org.eclipse.scava.plugin.mvc.implementation.AbstractModel;

import io.swagger.client.ApiException;
import io.swagger.client.api.RecommenderRestControllerApi;
import io.swagger.client.model.Query;
import io.swagger.client.model.Recommendation;

public class ApiDocumentationModel extends AbstractModel implements IApiDocumentationModel {
	private final KnowledgeBaseAccess knowledgeBaseAccess;
	private final String methodCode;

	public ApiDocumentationModel(String methodCode) {
		this.methodCode = methodCode;
		knowledgeBaseAccess = new KnowledgeBaseAccess();
	}

	@Override
	public List<WebResult> getResults() {
		RecommenderRestControllerApi recommenderRestController = knowledgeBaseAccess.getRecommenderRestController();

		Query query = new Query();
		query.setCurrentMethodCode(methodCode);

		List<WebResult> results = new ArrayList<>();
		try {
			Recommendation recommendation = recommenderRestController.getApiDocumentationRecommendationUsingPOST(query);
			results = recommendation.getRecommendationItems().stream().map(r -> {
				String url = r.getApiDocumentationLink();
				String label = getTitleOfPage(url);
				return new WebResult(label, url);
			}).collect(Collectors.toList());
		} catch (ApiException e) {
			e.printStackTrace();
		}

		return results;
	}

	private String getTitleOfPage(String url) {
		try {
			InputStream response = new URL(url).openStream();

			Scanner scanner = new Scanner(response);
			String responseBody = scanner.useDelimiter("\\A").next();
			return responseBody.substring(responseBody.indexOf("<title>") + 7, responseBody.indexOf("</title>"));
		} catch (IOException e) {
			return "Title not available";
		}
	}
}
