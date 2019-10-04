package org.eclipse.scava.crossflow.restmule.client.github.page;

import static org.eclipse.scava.crossflow.restmule.core.util.PropertiesUtil.PAGE_INCREMENT;
import static org.eclipse.scava.crossflow.restmule.core.util.PropertiesUtil.PAGE_LABEL;
import static org.eclipse.scava.crossflow.restmule.core.util.PropertiesUtil.PAGE_MAX_VALUE;
import static org.eclipse.scava.crossflow.restmule.core.util.PropertiesUtil.PAGE_START_VALUE;
import static org.eclipse.scava.crossflow.restmule.core.util.PropertiesUtil.PER_ITERATION_LABEL;
import static org.eclipse.scava.crossflow.restmule.core.util.PropertiesUtil.PER_ITERATION_VALUE;

import org.eclipse.scava.crossflow.restmule.client.github.callback.GitHubSearchAPICallback;
import org.eclipse.scava.crossflow.restmule.client.github.data.GitHubEntityAPIDataSet;
import org.eclipse.scava.crossflow.restmule.client.github.data.GitHubSearchAPIDataSet;
import org.eclipse.scava.crossflow.restmule.client.github.util.GitHubPropertiesUtil;
import org.eclipse.scava.crossflow.restmule.core.data.IDataSet;
import org.eclipse.scava.crossflow.restmule.core.page.AbstractPagination;

import io.reactivex.annotations.NonNull;

public class GitHubSearchAPIPagination extends AbstractPagination {

	private static GitHubSearchAPIPagination instance;

	public static GitHubSearchAPIPagination get() {
		if (instance == null) {
			instance = new GitHubSearchAPIPagination();
		}
		return instance;
	}

	private GitHubSearchAPIPagination() {
		super(GitHubPropertiesUtil.get(PAGE_LABEL), GitHubPropertiesUtil.get(PER_ITERATION_LABEL),
				Integer.valueOf(GitHubPropertiesUtil.get(PER_ITERATION_VALUE)),
				Integer.valueOf(GitHubPropertiesUtil.get(PAGE_MAX_VALUE)),
				Integer.valueOf(GitHubPropertiesUtil.get(PAGE_START_VALUE)),
				Integer.valueOf(GitHubPropertiesUtil.get(PAGE_INCREMENT)));
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public <T, WRAP extends GitHubPaged<T>, END> IDataSet<T> traverse(@NonNull String methodName,
			@NonNull Class<?>[] types, @NonNull Object[] vals, @NonNull END client) {
		return super.<T, WRAP, END, GitHubSearchAPIDataSet<T>, GitHubSearchAPICallback>traverse(
				new GitHubSearchAPICallback<T, WRAP>(), methodName, types, vals, client);
	}
	
	public static int getSearchAPIMaxResults() {
		// FIXME add to fix model and generator
		return 1000;
	}

}
