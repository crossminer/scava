package org.eclipse.scava.workflow.restmule.generated.client.github.page;

import static org.eclipse.scava.workflow.restmule.core.util.PropertiesUtil.PAGE_INCREMENT;
import static org.eclipse.scava.workflow.restmule.core.util.PropertiesUtil.PAGE_LABEL;
import static org.eclipse.scava.workflow.restmule.core.util.PropertiesUtil.PAGE_MAX_VALUE;
import static org.eclipse.scava.workflow.restmule.core.util.PropertiesUtil.PAGE_START_VALUE;
import static org.eclipse.scava.workflow.restmule.core.util.PropertiesUtil.PER_ITERATION_LABEL;
import static org.eclipse.scava.workflow.restmule.core.util.PropertiesUtil.PER_ITERATION_VALUE;

import org.eclipse.scava.workflow.restmule.core.data.IDataSet;
import org.eclipse.scava.workflow.restmule.core.page.AbstractPagination;
import org.eclipse.scava.workflow.restmule.generated.client.github.callback.GitHubCallback;
import org.eclipse.scava.workflow.restmule.generated.client.github.callback.GitHubWrappedCallback;
import org.eclipse.scava.workflow.restmule.generated.client.github.data.GitHubDataSet;
import org.eclipse.scava.workflow.restmule.generated.client.github.util.GitHubPropertiesUtil;

import io.reactivex.annotations.NonNull;

public class GitHubPagination extends AbstractPagination{

	private static GitHubPagination instance;

	public static GitHubPagination get(){
		if (instance == null){
			instance = new GitHubPagination();
		}
		return instance;
	}

	private GitHubPagination() {
		super(	GitHubPropertiesUtil.get(PAGE_LABEL),
				GitHubPropertiesUtil.get(PER_ITERATION_LABEL), 
				Integer.valueOf(GitHubPropertiesUtil.get(PER_ITERATION_VALUE)),
				Integer.valueOf(GitHubPropertiesUtil.get(PAGE_MAX_VALUE)), 
				Integer.valueOf(GitHubPropertiesUtil.get(PAGE_START_VALUE)),
				Integer.valueOf(GitHubPropertiesUtil.get(PAGE_INCREMENT)));
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public <T, WRAP extends GitHubPaged<T>, END> IDataSet<T> traverse(
			@NonNull String methodName, 
			@NonNull Class<?>[] types, 
			@NonNull Object[] vals, 
			@NonNull END client)
	{
		return super.<T, WRAP, END, GitHubDataSet<T>, GitHubWrappedCallback>
		traverse(new GitHubWrappedCallback<T, WRAP>(), methodName, types, vals, client);
	}
	
	public <T, END> IDataSet<T> traverseList(
			@NonNull String methodName, 
			@NonNull Class<?>[] types, 
			@NonNull Object[] vals, 
			@NonNull END client)
	{
		return super.<T, END, GitHubDataSet<T>, GitHubCallback<T>>
		traversePages(new GitHubCallback<T>(), methodName, types, vals, client);		
	}

}
