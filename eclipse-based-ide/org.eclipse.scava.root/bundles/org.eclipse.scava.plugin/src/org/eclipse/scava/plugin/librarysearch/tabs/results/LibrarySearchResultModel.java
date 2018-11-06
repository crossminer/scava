package org.eclipse.scava.plugin.librarysearch.tabs.results;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.scava.plugin.knowledgebase.access.KnowledgeBaseAccess;
import org.eclipse.scava.plugin.knowledgebase.access.SimilarityMethod;
import org.eclipse.scava.plugin.librarysearch.list.LibraryListInfo;
import org.eclipse.scava.plugin.mvc.implementation.AbstractModel;

import io.swagger.client.ApiException;
import io.swagger.client.api.RecommenderRestControllerApi;
import io.swagger.client.model.Artifact;

public class LibrarySearchResultModel extends AbstractModel implements ILibrarySearchResultModel {
	private final String description;
	private final List<LibraryListInfo> results;
	private final KnowledgeBaseAccess knowledgeBaseAccess;

	public LibrarySearchResultModel(String description, List<LibraryListInfo> results) {
		super();
		this.description = description;
		this.results = results;
		knowledgeBaseAccess = new KnowledgeBaseAccess();
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public List<LibraryListInfo> getResults() {
		return Collections.unmodifiableList(results);
	}

	@Override
	public List<Artifact> getSimilarsTo(Artifact library, SimilarityMethod method, int numberOfExpectedResults) {
		RecommenderRestControllerApi recommenderRestController = knowledgeBaseAccess.getRecommenderRestController();

		List<Artifact> artifactList = new ArrayList<>();
		try {
			artifactList = recommenderRestController.getSimilarProjectUsingGET(method.name(), library.getId(),
					numberOfExpectedResults);
		} catch (ApiException e) {
			e.printStackTrace();
		}

		return artifactList;
	}

}
