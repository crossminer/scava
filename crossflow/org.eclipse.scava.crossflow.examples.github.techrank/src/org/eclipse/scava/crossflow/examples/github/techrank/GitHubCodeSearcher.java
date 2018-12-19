package org.eclipse.scava.crossflow.examples.github.techrank;

import java.util.Arrays;
import java.util.List;

public class GitHubCodeSearcher extends GitHubCodeSearcherBase {
	
	@Override
	public void consumeTechnologies(Technology technology) throws Exception {
		
		List<String> repositories = Arrays.asList("hellspawn14/WebPicture-Samples", "Mastrognu/BramboProject");
		
		for (String repository : repositories) {
			sendToRepositories(new Repository(repository, technology));
		}
		
	}

}