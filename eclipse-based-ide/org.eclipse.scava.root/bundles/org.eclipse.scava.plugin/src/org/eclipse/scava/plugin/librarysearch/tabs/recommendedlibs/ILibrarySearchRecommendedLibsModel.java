package org.eclipse.scava.plugin.librarysearch.tabs.recommendedlibs;

import java.util.List;

import org.eclipse.scava.plugin.mvc.IModel;

import io.swagger.client.model.RecommendedLibrary;

public interface ILibrarySearchRecommendedLibsModel extends IModel {
	List<RecommendedLibrary> getRecommendedLibraries();
	String getPom();
}
