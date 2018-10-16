package org.eclipse.scava.crossflow.restmule.client.github.data;

import org.eclipse.scava.crossflow.restmule.core.data.AbstractDataSet;
import org.eclipse.scava.crossflow.restmule.client.github.page.GitHubPagination;

public class GitHubDataSet<T> extends AbstractDataSet<T> {

	public GitHubDataSet(){
		super(GitHubPagination.get());
	}
	
}
