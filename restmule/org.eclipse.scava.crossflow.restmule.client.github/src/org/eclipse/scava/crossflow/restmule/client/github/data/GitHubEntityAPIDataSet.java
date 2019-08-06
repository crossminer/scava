package org.eclipse.scava.crossflow.restmule.client.github.data;

import org.eclipse.scava.crossflow.restmule.client.github.page.GitHubEntityAPIPagination;
import org.eclipse.scava.crossflow.restmule.core.data.AbstractDataSet;

public class GitHubEntityAPIDataSet<T> extends AbstractDataSet<T> {

	public GitHubEntityAPIDataSet(){
		super(GitHubEntityAPIPagination.get());
	}
	
}
