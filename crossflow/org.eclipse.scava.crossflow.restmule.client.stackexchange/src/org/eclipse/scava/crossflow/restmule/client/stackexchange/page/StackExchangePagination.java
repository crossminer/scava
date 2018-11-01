package org.eclipse.scava.crossflow.restmule.client.stackexchange.page;

import static org.eclipse.scava.crossflow.restmule.core.util.PropertiesUtil.PAGE_INCREMENT;
import static org.eclipse.scava.crossflow.restmule.core.util.PropertiesUtil.PAGE_LABEL;
import static org.eclipse.scava.crossflow.restmule.core.util.PropertiesUtil.PAGE_MAX_VALUE;
import static org.eclipse.scava.crossflow.restmule.core.util.PropertiesUtil.PAGE_START_VALUE;
import static org.eclipse.scava.crossflow.restmule.core.util.PropertiesUtil.PER_ITERATION_LABEL;
import static org.eclipse.scava.crossflow.restmule.core.util.PropertiesUtil.PER_ITERATION_VALUE;

import org.eclipse.scava.crossflow.restmule.core.data.IDataSet;
import org.eclipse.scava.crossflow.restmule.core.page.AbstractPagination;
import org.eclipse.scava.crossflow.restmule.client.stackexchange.callback.StackExchangeCallback;
import org.eclipse.scava.crossflow.restmule.client.stackexchange.callback.StackExchangeWrappedCallback;
import org.eclipse.scava.crossflow.restmule.client.stackexchange.data.StackExchangeDataSet;
import org.eclipse.scava.crossflow.restmule.client.stackexchange.util.StackExchangePropertiesUtil;

import io.reactivex.annotations.NonNull;

public class StackExchangePagination extends AbstractPagination{

	private static StackExchangePagination instance;

	public static StackExchangePagination get(){
		if (instance == null){
			instance = new StackExchangePagination();
		}
		return instance;
	}

	private StackExchangePagination() {
		super(	StackExchangePropertiesUtil.get(PAGE_LABEL),
				StackExchangePropertiesUtil.get(PER_ITERATION_LABEL), 
				Integer.valueOf(StackExchangePropertiesUtil.get(PER_ITERATION_VALUE)),
				Integer.valueOf(StackExchangePropertiesUtil.get(PAGE_MAX_VALUE)), 
				Integer.valueOf(StackExchangePropertiesUtil.get(PAGE_START_VALUE)),
				Integer.valueOf(StackExchangePropertiesUtil.get(PAGE_INCREMENT)));
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public <T, WRAP extends StackExchangePaged<T>, END> IDataSet<T> traverse(
			@NonNull String methodName, 
			@NonNull Class<?>[] types, 
			@NonNull Object[] vals, 
			@NonNull END client)
	{
		return super.<T, WRAP, END, StackExchangeDataSet<T>, StackExchangeWrappedCallback>
		traverse(new StackExchangeWrappedCallback<T, WRAP>(), methodName, types, vals, client);
	}
	
	public <T, END> IDataSet<T> traverseList(
			@NonNull String methodName, 
			@NonNull Class<?>[] types, 
			@NonNull Object[] vals, 
			@NonNull END client)
	{
		return super.<T, END, StackExchangeDataSet<T>, StackExchangeCallback<T>>
		traversePages(new StackExchangeCallback<T>(), methodName, types, vals, client);		
	}

}
