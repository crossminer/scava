package org.eclipse.scava.plugin.librarysearch;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.scava.plugin.knowledgebase.access.KnowledgeBaseAccess;
import org.eclipse.scava.plugin.knowledgebase.access.SimilarityMethod;
import org.eclipse.scava.plugin.mvc.implementation.AbstractModel;

import io.swagger.client.ApiException;
import io.swagger.client.api.ArtifactsRestControllerApi;
import io.swagger.client.api.RecommenderRestControllerApi;
import io.swagger.client.model.Artifact;
import io.swagger.client.model.Dependency;
import io.swagger.client.model.Query;
import io.swagger.client.model.Recommendation;
import io.swagger.client.model.RecommendationItem;
import io.swagger.client.model.RecommendedLibrary;

public class LibrarySearchModel extends AbstractModel implements ILibrarySearchModel {
	private final List<Artifact> toBeInstalledLibraries;
	private final String pom;
	private final KnowledgeBaseAccess knowledgeBaseAccess;

	public LibrarySearchModel(String pom) {
		toBeInstalledLibraries = new ArrayList<>();
		this.pom = pom;
		knowledgeBaseAccess = new KnowledgeBaseAccess();
	}

	@Override
	public void addToBeInstalled(Artifact library) {
		if (!toBeInstalledLibraries.stream().anyMatch(l -> l.getId().equals(library.getId()))) {
			toBeInstalledLibraries.add(library);
		}
	}

	@Override
	public void removeToBeInstalled(Artifact library) {
		toBeInstalledLibraries.removeIf(l -> l.getId().equals(library.getId()));
	}

	@Override
	public List<Artifact> getToBeInstalledLibraries() {
		return Collections.unmodifiableList(toBeInstalledLibraries);
	}

	@Override
	public List<Artifact> getLibrariesByQueryString(String query) {		
		ArtifactsRestControllerApi artifactsRestControllerApi = knowledgeBaseAccess.getArtifactRestControllerApi();
		
		List<Artifact> artifactList = new ArrayList<>();
		try {
			artifactList = artifactsRestControllerApi.getProjectUsingGET(query, null, null, null);
		} catch (ApiException e) {
			e.printStackTrace();
		}
		
		return artifactList;
	}

	@Override
	public List<Artifact> getSimilarLibrariesTo(Artifact artifact, SimilarityMethod simMethod, int numberOfResults) {
		RecommenderRestControllerApi recommenderRestController = knowledgeBaseAccess.getRecommenderRestController();
		
		List<Artifact> artifactList = new ArrayList<>();
		try {
			artifactList = recommenderRestController.getSimilarProjectUsingGET(simMethod.name(), artifact.getId(), numberOfResults);
		} catch (ApiException e) {
			e.printStackTrace();
		}
		
		return artifactList;
	}
	
	/* PREPARED METHOD FOR THE RECOMMENDED_LIBS REQUEST :)
	public void getRecommendedAdditionalLibraries(List<Artifact> baseLibraries) {
		ApiRecommendationRecommended_libraryClientResource resource = knowledgeBaseAccess.apiRecommendationRecommended_library();
		Query query = new Query();
		
		Recommendation recommendation = resource.getRecommendedLibrariesUsingPOST(query);
		recommendation.getRecommendationItems().stream().map(item -> item.get)
	}*/
	
	@Override
	public List<RecommendedLibrary> getRecommendedLibraries(List<String> basedOn) {
		RecommenderRestControllerApi recommenderRestController = knowledgeBaseAccess.getRecommenderRestController();

		Query query = new Query();
		
		List<Dependency> dependencies = basedOn.stream().map(base -> {
			Dependency dependency = new Dependency();
			dependency.setArtifactID("UNKNOW");
			dependency.setName(base);
			dependency.setVersion("1");
			return dependency;
		}).collect(Collectors.toList());
		
		query.setProjectDependencies(dependencies);
		
		List<RecommendedLibrary> recommendedLibraries = new ArrayList<>();
		try {
			Recommendation recommendation = recommenderRestController.getRecommendedLibrariesUsingPOST(query);
			List<RecommendationItem> recommendationItems = recommendation.getRecommendationItems();
			recommendedLibraries = recommendationItems.stream().map(r -> r.getRecommendedLibrary()).collect(Collectors.toList());
		} catch (ApiException e) {
			e.printStackTrace();
		}
		
		return recommendedLibraries;
	}
	
	@Override
	public String getPom() {
		return pom;
	}

}
