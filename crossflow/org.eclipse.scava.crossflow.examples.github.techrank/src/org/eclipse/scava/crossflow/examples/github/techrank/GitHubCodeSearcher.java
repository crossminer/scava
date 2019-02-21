package org.eclipse.scava.crossflow.examples.github.techrank;

import java.util.Properties;

import org.kohsuke.github.GHContent;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;
import org.kohsuke.github.PagedSearchIterable;

public class GitHubCodeSearcher extends GitHubCodeSearcherBase {
	
	protected GitHub github = null;
	
	@Override
	public Repository consumeTechnologies(Technology technology) throws Exception {
		Repository repository = new Repository();
		if (github == null) connectToGitHub();

		PagedSearchIterable<GHContent> it = github.searchContent().extension(technology.getExtension()).q(technology.getKeyword()).list();
		it.forEach(c -> repository.setPath(c.getOwner().getFullName())); // FIXME: overwrites the repository path and captures only the last path (requires model adaptation to capture multiple paths)
		repository.setCorrelationId(technology.getCorrelationId());
		return repository;
	}
	
	protected void connectToGitHub() throws Exception {		
		Properties properties = new TechrankWorkflowContext(workflow).getProperties();
		github = GitHubBuilder.fromProperties(properties).build();
	}

}