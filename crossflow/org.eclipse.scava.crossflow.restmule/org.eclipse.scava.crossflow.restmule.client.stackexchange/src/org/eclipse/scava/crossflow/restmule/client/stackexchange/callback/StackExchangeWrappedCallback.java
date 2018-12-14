package org.eclipse.scava.crossflow.restmule.client.stackexchange.callback;

import static org.eclipse.scava.crossflow.restmule.core.util.PropertiesUtil.PER_ITERATION_VALUE;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.scava.crossflow.restmule.client.stackexchange.data.StackExchangeDataSet;
import org.eclipse.scava.crossflow.restmule.client.stackexchange.page.StackExchangePagination;
import org.eclipse.scava.crossflow.restmule.client.stackexchange.util.StackExchangePropertiesUtil;
import org.eclipse.scava.crossflow.restmule.core.callback.AbstractWrappedCallback;
import org.eclipse.scava.crossflow.restmule.core.page.IWrap;
import org.mortbay.log.Log;

import retrofit2.Call;
import retrofit2.Response;

public class StackExchangeWrappedCallback<D,R extends IWrap<D>> extends AbstractWrappedCallback<D,R, StackExchangeDataSet<D>>  {

	private static final Logger LOG = LogManager.getLogger(StackExchangeWrappedCallback.class);
	
	private static StackExchangePagination paginationPolicy = StackExchangePagination.get();
	private static int maxCount = StackExchangePagination.getMaxResults(); // TODO: add to generator
	
	public StackExchangeWrappedCallback() {
		super(new StackExchangeDataSet<D>());
	}

	// FIXME move these methods to super abstract class <--
	
	@Override
	public void handleResponse(Response<R> response) {
		this.dataset.addElements(response.body().getItems());
	}
	
	@Override
	public void handleTotal(Response<R> response) {
		Integer totalCount = response.body().getTotalCount();
		if (totalCount != null && totalCount > maxCount) {
			Log.info("Please note that for this request, totalCount (" + totalCount + ") > maxCount (" + maxCount
					+ "), as such, only " + maxCount + " elements will be retrieved.");
			this.dataset.setTotal(maxCount);
		} else
			this.dataset.setTotal(totalCount);
	}// TODO: update generator !

	@Override
	public void handleError(Call<R> call, Throwable t) {
		System.out.println("handleError from call="+call);
		LOG.error(t.getMessage());
		LOG.error(call.request().url()); // TODO RETRY
	}
	
	// --->
	
	@Override
	public Integer totalIterations(Response<R> response) { // FIXME
		if ( response.body().getTotalCount() == null ) {
			System.err.println("Cannot determine total number of iterations from non-existing total count.");
			return -1;
		}
		return (response.body().getTotalCount() + Integer.valueOf(StackExchangePropertiesUtil.get(PER_ITERATION_VALUE)) - 1) / Integer.valueOf(StackExchangePropertiesUtil.get(PER_ITERATION_VALUE));
	}
	
}
