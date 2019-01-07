package org.eclipse.scava.crossflow.examples.github.techrank;

import java.util.Properties;

import org.kohsuke.github.GHContent;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;
import org.kohsuke.github.PagedSearchIterable;

public class GitHubCodeSearcher extends GitHubCodeSearcherBase {
	
	protected GitHub github = null;
	
	@Override
	public void consumeTechnologies(Technology technology) throws Exception {
		if (github == null) connectToGitHub();

		PagedSearchIterable<GHContent> it = github.searchContent().extension(technology.getExtension()).q(technology.getKeyword()).list();
		it.forEach(c -> sendToRepositories(new Repository(c.getOwner().getFullName(), technology)));
	}
	
	protected void connectToGitHub() throws Exception {		
		Properties properties = new TechrankWorkflowContext(workflow).getProperties();
		github = GitHubBuilder.fromProperties(properties).build();
	}

}