package org.eclipse.scava.crossflow.restmule.client.github.data;

import org.eclipse.scava.crossflow.restmule.client.github.page.GitHubSearchAPIPagination;
import org.eclipse.scava.crossflow.restmule.core.data.AbstractDataSet;

public class GitHubSearchAPIDataSet<T> extends AbstractDataSet<T> {

	public GitHubSearchAPIDataSet(){
		super(GitHubSearchAPIPagination.get());
	}
	
}
