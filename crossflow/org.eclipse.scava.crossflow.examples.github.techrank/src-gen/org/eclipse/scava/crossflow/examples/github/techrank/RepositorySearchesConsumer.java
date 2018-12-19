package org.eclipse.scava.crossflow.examples.github.techrank;

public interface RepositorySearchesConsumer {
	
	void consumeRepositorySearchesWithNotifications(RepositorySearch repositorySearch) throws Exception;
	
}