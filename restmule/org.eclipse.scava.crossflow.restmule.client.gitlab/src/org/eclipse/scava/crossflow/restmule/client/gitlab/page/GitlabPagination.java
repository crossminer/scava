package org.eclipse.scava.crossflow.restmule.client.gitlab.page;

import static org.eclipse.scava.crossflow.restmule.core.util.PropertiesUtil.PAGE_INCREMENT;
import static org.eclipse.scava.crossflow.restmule.core.util.PropertiesUtil.PAGE_LABEL;
import static org.eclipse.scava.crossflow.restmule.core.util.PropertiesUtil.PAGE_MAX_VALUE;
import static org.eclipse.scava.crossflow.restmule.core.util.PropertiesUtil.PAGE_START_VALUE;
import static org.eclipse.scava.crossflow.restmule.core.util.PropertiesUtil.PER_ITERATION_LABEL;
import static org.eclipse.scava.crossflow.restmule.core.util.PropertiesUtil.PER_ITERATION_VALUE;

import org.eclipse.scava.crossflow.restmule.core.data.IDataSet;
import org.eclipse.scava.crossflow.restmule.core.page.AbstractPagination;
import org.eclipse.scava.crossflow.restmule.client.gitlab.callback.GitlabCallback;
import org.eclipse.scava.crossflow.restmule.client.gitlab.callback.GitlabWrappedCallback;
import org.eclipse.scava.crossflow.restmule.client.gitlab.data.GitlabDataSet;
import org.eclipse.scava.crossflow.restmule.client.gitlab.util.GitlabPropertiesUtil;

import io.reactivex.annotations.NonNull;

public class GitlabPagination extends AbstractPagination{

	private static GitlabPagination instance;

	public static GitlabPagination get(){
		if (instance == null){
			instance = new GitlabPagination();
		}
		return instance;
	}

	private GitlabPagination() {
		super(	GitlabPropertiesUtil.get(PAGE_LABEL),
				GitlabPropertiesUtil.get(PER_ITERATION_LABEL), 
				Integer.valueOf(GitlabPropertiesUtil.get(PER_ITERATION_VALUE)),
				Integer.valueOf(GitlabPropertiesUtil.get(PAGE_MAX_VALUE)), 
				Integer.valueOf(GitlabPropertiesUtil.get(PAGE_START_VALUE)),
				Integer.valueOf(GitlabPropertiesUtil.get(PAGE_INCREMENT)));
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public <T, WRAP extends GitlabPaged<T>, END> IDataSet<T> traverse(
			@NonNull String methodName, 
			@NonNull Class<?>[] types, 
			@NonNull Object[] vals, 
			@NonNull END client)
	{
		return super.<T, WRAP, END, GitlabDataSet<T>, GitlabWrappedCallback>
		traverse(new GitlabWrappedCallback<T, WRAP>(), methodName, types, vals, client);
	}
	
	public <T, END> IDataSet<T> traverseList(
			@NonNull String methodName, 
			@NonNull Class<?>[] types, 
			@NonNull Object[] vals, 
			@NonNull END client)
	{
		return super.<T, END, GitlabDataSet<T>, GitlabCallback<T>>
		traversePages(new GitlabCallback<T>(), methodName, types, vals, client);		
	}

}
