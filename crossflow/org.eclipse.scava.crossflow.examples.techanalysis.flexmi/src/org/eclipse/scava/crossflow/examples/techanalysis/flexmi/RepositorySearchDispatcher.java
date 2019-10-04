package org.eclipse.scava.crossflow.examples.techanalysis.flexmi;

import java.util.HashSet;
import java.util.Set;

public class RepositorySearchDispatcher extends RepositorySearchDispatcherBase {

	private Set<String> repos = new HashSet<>();

	@Override
	public void consumeRepositories(Repository repository) throws Exception {

		// send each repository only once to be cloned/analysed
		if (repos.add(repository.name))
			sendToRepositorySearches(repository);

	}

}
