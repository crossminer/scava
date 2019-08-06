package org.eclipse.scava.crossflow.restmule.client.github.page;

import static org.eclipse.scava.crossflow.restmule.core.util.PropertiesUtil.PAGE_INCREMENT;
import static org.eclipse.scava.crossflow.restmule.core.util.PropertiesUtil.PAGE_LABEL;
import static org.eclipse.scava.crossflow.restmule.core.util.PropertiesUtil.PAGE_START_VALUE;
import static org.eclipse.scava.crossflow.restmule.core.util.PropertiesUtil.PER_ITERATION_LABEL;
import static org.eclipse.scava.crossflow.restmule.core.util.PropertiesUtil.PER_ITERATION_VALUE;

import org.eclipse.scava.crossflow.restmule.client.github.callback.GitHubEntityAPICallback;
import org.eclipse.scava.crossflow.restmule.client.github.data.GitHubEntityAPIDataSet;
import org.eclipse.scava.crossflow.restmule.client.github.util.GitHubPropertiesUtil;
import org.eclipse.scava.crossflow.restmule.core.data.IDataSet;
import org.eclipse.scava.crossflow.restmule.core.page.AbstractPagination;

import io.reactivex.annotations.NonNull;

public class GitHubEntityAPIPagination extends AbstractPagination {

	private static GitHubEntityAPIPagination instance;

	public static GitHubEntityAPIPagination get() {
		if (instance == null) {
			instance = new GitHubEntityAPIPagination();
		}
		return instance;
	}

	private GitHubEntityAPIPagination() {
		super(GitHubPropertiesUtil.get(PAGE_LABEL), GitHubPropertiesUtil.get(PER_ITERATION_LABEL),
				Integer.valueOf(GitHubPropertiesUtil.get(PER_ITERATION_VALUE)),
				Integer.MAX_VALUE / Integer.valueOf(GitHubPropertiesUtil.get(PAGE_INCREMENT))
						/ Integer.valueOf(GitHubPropertiesUtil.get(PER_ITERATION_VALUE)),
				Integer.valueOf(GitHubPropertiesUtil.get(PAGE_START_VALUE)),
				Integer.valueOf(GitHubPropertiesUtil.get(PAGE_INCREMENT)));
	}

	public <T, END> IDataSet<T> traverseList(@NonNull String methodName, @NonNull Class<?>[] types,
			@NonNull Object[] vals, @NonNull END client) {
		return super.<T, END, GitHubEntityAPIDataSet<T>, GitHubEntityAPICallback<T>>traversePages(new GitHubEntityAPICallback<T>(), methodName,
				types, vals, client);
	}

	public static int getEntityAPIMaxResults() {
		// FIXME add to fix model and generator
		return Integer.MAX_VALUE;
	}

}
