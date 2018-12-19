package org.eclipse.scava.crossflow.examples.github.techrank;

import java.util.HashMap;

public class ResultsSink extends ResultsSinkBase {
	
	protected HashMap<String, Integer> aggregate = new HashMap<>();
	
	@Override
	public void consumeRepositorySearchResults(RepositorySearchResult repositorySearchResult) throws Exception {
		
		if (!aggregate.containsKey(repositorySearchResult.getTechnology())) {
			aggregate.put(repositorySearchResult.getTechnology(), repositorySearchResult.getResults());
		}
		else {
			aggregate.put(repositorySearchResult.getTechnology(), aggregate.get(repositorySearchResult.getTechnology()) + repositorySearchResult.getResults());
		}
		
		System.out.println("--- Results ---");
		for (String key : aggregate.keySet()) {
			System.out.println(key + " - " + aggregate.get(key));
		}
		System.out.println("---------------");
	}

}