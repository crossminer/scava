package org.eclipse.scava.crossflow.examples.techanalysis;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RepositorySearchDispatcher extends RepositorySearchDispatcherBase {

	private Set<String> repos = new HashSet<>();
	private List<Technology> techs;

	@Override
	public void consumeRepositories(Repository repository) throws Exception {

		// send each repository only once to be cloned/analysed
		if (repos.add(repository.name)) {
			for (Technology tech : techs)
				sendToRepositorySearches(new RepoTechPair(tech, repository.url, repository.name));

		}

	}

	@Override
	public void consumeTechsConfigTopic(Techs techs) throws Exception {
		this.techs = techs.techs;
	}

}
