package org.eclipse.scava.crossflow.examples.github.techrank;

import java.io.File;
import java.util.Properties;

import org.kohsuke.github.GHContent;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;
import org.kohsuke.github.PagedSearchIterable;
import org.kohsuke.github.extras.OkHttpConnector;

import com.squareup.okhttp.Cache;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.OkUrlFactory;

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
		
		Cache cache = new Cache(new File("okhttp-cache"), 10 * 1024 * 1024); // 100MB cache
		github = GitHubBuilder.fromProperties(properties).
			withConnector(new OkHttpConnector(new OkUrlFactory(new OkHttpClient().setCache(cache)))).build();
	}

}