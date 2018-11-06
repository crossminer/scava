package org.eclipse.scava.plugin.librarysearch.tabs.results;

import java.util.List;

import org.eclipse.scava.plugin.knowledgebase.access.SimilarityMethod;
import org.eclipse.scava.plugin.librarysearch.list.LibraryListInfo;
import org.eclipse.scava.plugin.mvc.IModel;

import io.swagger.client.model.Artifact;

public interface ILibrarySearchResultModel extends IModel {
	String getDescription();

	List<LibraryListInfo> getResults();
	
	List<Artifact> getSimilarsTo(Artifact library, SimilarityMethod method, int numberOfExpectedResults);
}
