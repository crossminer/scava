package org.eclipse.scava.crossflow.examples.github.techrank;

public interface RepositorySearchResultsConsumer {
	
	void consumeRepositorySearchResultsWithNotifications(RepositorySearchResult repositorySearchResult) throws Exception;
	
}