package org.eclipse.scava.plugin.librarysearch.tabs.recommendedlibs;

import java.util.List;

import org.eclipse.scava.plugin.mvc.implementation.AbstractModel;

import io.swagger.client.model.RecommendedLibrary;

public class LibrarySearchRecommendedLibsModel extends AbstractModel implements ILibrarySearchRecommendedLibsModel {
	private final List<RecommendedLibrary> recommendedLibraries;
	private final String pom;

	public LibrarySearchRecommendedLibsModel(List<RecommendedLibrary> recommendedLibraries, String pom) {
		super();
		this.recommendedLibraries = recommendedLibraries;
		this.pom = pom;
	}

	@Override
	public List<RecommendedLibrary> getRecommendedLibraries() {
		return recommendedLibraries;
	}

	@Override
	public String getPom() {
		return pom;
	}

}
