package org.eclipse.scava.crossflow.restmule.client.bitbucket.page;

import static org.eclipse.scava.crossflow.restmule.core.util.PropertiesUtil.PAGE_INCREMENT;
import static org.eclipse.scava.crossflow.restmule.core.util.PropertiesUtil.PAGE_LABEL;
import static org.eclipse.scava.crossflow.restmule.core.util.PropertiesUtil.PAGE_MAX_VALUE;
import static org.eclipse.scava.crossflow.restmule.core.util.PropertiesUtil.PAGE_START_VALUE;
import static org.eclipse.scava.crossflow.restmule.core.util.PropertiesUtil.PER_ITERATION_LABEL;
import static org.eclipse.scava.crossflow.restmule.core.util.PropertiesUtil.PER_ITERATION_VALUE;

import org.eclipse.scava.crossflow.restmule.core.data.IDataSet;
import org.eclipse.scava.crossflow.restmule.core.page.AbstractPagination;
import org.eclipse.scava.crossflow.restmule.client.bitbucket.callback.BitbucketCallback;
import org.eclipse.scava.crossflow.restmule.client.bitbucket.callback.BitbucketWrappedCallback;
import org.eclipse.scava.crossflow.restmule.client.bitbucket.data.BitbucketDataSet;
import org.eclipse.scava.crossflow.restmule.client.bitbucket.util.BitbucketPropertiesUtil;

import io.reactivex.annotations.NonNull;

public class BitbucketPagination extends AbstractPagination{

	private static BitbucketPagination instance;

	public static BitbucketPagination get(){
		if (instance == null){
			instance = new BitbucketPagination();
		}
		return instance;
	}

	private BitbucketPagination() {
		super(	BitbucketPropertiesUtil.get(PAGE_LABEL),
				BitbucketPropertiesUtil.get(PER_ITERATION_LABEL), 
				Integer.valueOf(BitbucketPropertiesUtil.get(PER_ITERATION_VALUE)),
				Integer.valueOf(BitbucketPropertiesUtil.get(PAGE_MAX_VALUE)), 
				Integer.valueOf(BitbucketPropertiesUtil.get(PAGE_START_VALUE)),
				Integer.valueOf(BitbucketPropertiesUtil.get(PAGE_INCREMENT)));
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public <T, WRAP extends BitbucketPaged<T>, END> IDataSet<T> traverse(
			@NonNull String methodName, 
			@NonNull Class<?>[] types, 
			@NonNull Object[] vals, 
			@NonNull END client)
	{
		return super.<T, WRAP, END, BitbucketDataSet<T>, BitbucketWrappedCallback>
		traverse(new BitbucketWrappedCallback<T, WRAP>(), methodName, types, vals, client);
	}
	
	public <T, END> IDataSet<T> traverseList(
			@NonNull String methodName, 
			@NonNull Class<?>[] types, 
			@NonNull Object[] vals, 
			@NonNull END client)
	{
		return super.<T, END, BitbucketDataSet<T>, BitbucketCallback<T>>
		traversePages(new BitbucketCallback<T>(), methodName, types, vals, client);		
	}

}
