package org.eclipse.scava.plugin.librarysearch;

import java.util.List;

import org.eclipse.scava.plugin.knowledgebase.access.SimilarityMethod;
import org.eclipse.scava.plugin.mvc.IModel;

import io.swagger.client.model.Artifact;
import io.swagger.client.model.RecommendedLibrary;

public interface ILibrarySearchModel extends IModel {
	void addToBeInstalled(Artifact library);

	void removeToBeInstalled(Artifact library);

	List<Artifact> getToBeInstalledLibraries();

	List<Artifact> getLibrariesByQueryString(String query);

	List<Artifact> getSimilarLibrariesTo(Artifact artifact, SimilarityMethod simMethod, int numberOfResults);
	
	List<RecommendedLibrary> getRecommendedLibraries(List<String> basedOn);
	
	String getPom();
}
