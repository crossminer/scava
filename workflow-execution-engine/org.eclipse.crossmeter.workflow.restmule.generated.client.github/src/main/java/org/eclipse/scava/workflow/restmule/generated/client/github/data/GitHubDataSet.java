package org.eclipse.scava.workflow.restmule.generated.client.github.data;

import org.eclipse.scava.workflow.restmule.core.data.AbstractDataSet;
import org.eclipse.scava.workflow.restmule.generated.client.github.page.GitHubPagination;

public class GitHubDataSet<T> extends AbstractDataSet<T> {

	public GitHubDataSet(){
		super(GitHubPagination.get());
	}
	
}
