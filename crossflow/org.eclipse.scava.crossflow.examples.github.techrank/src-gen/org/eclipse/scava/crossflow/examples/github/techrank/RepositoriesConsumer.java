package org.eclipse.scava.crossflow.examples.github.techrank;

public interface RepositoriesConsumer {
	
	void consumeRepositoriesWithNotifications(Repository repository) throws Exception;
	
}