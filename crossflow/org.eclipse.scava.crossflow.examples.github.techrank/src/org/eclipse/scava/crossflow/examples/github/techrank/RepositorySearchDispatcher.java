package org.eclipse.scava.crossflow.examples.github.techrank;

import java.util.HashSet;

public class RepositorySearchDispatcher extends RepositorySearchDispatcherBase {
	
	protected HashSet<String> repositories = new HashSet<>();
	
	@Override
	public RepositorySearch consumeRepositories(Repository repository) throws Exception {
		
		if (!repositories.contains(repository.getPath())) {
			repositories.add(repository.getPath());
			return new RepositorySearch(repository.getPath(), 
					workflow.getTechnologySource().getTechnologies(), repository);
		}
		return null;
	}

}