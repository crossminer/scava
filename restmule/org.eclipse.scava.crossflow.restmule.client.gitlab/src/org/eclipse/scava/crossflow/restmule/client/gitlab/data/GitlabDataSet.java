package org.eclipse.scava.crossflow.restmule.client.gitlab.data;

import org.eclipse.scava.crossflow.restmule.core.data.AbstractDataSet;
import org.eclipse.scava.crossflow.restmule.client.gitlab.page.GitlabPagination;

public class GitlabDataSet<T> extends AbstractDataSet<T> {

	public GitlabDataSet(){
		super(GitlabPagination.get());
	}
	
}
