package org.eclipse.scava.crossflow.restmule.client.stackexchange.callback;

import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.scava.crossflow.restmule.core.callback.AbstractCallback;
import org.eclipse.scava.crossflow.restmule.client.stackexchange.data.StackExchangeDataSet;
import org.eclipse.scava.crossflow.restmule.client.stackexchange.page.StackExchangePagination;
import retrofit2.Call;
import retrofit2.Response;

public class StackExchangeCallback<D> extends AbstractCallback<D, List<D>, StackExchangeDataSet<D>> {

	private static final Logger LOG = LogManager.getLogger(StackExchangeCallback.class);

	private static StackExchangePagination paginationPolicy = StackExchangePagination.get();
	private static int maxCount = StackExchangePagination.getMaxResults(); // TODO: add to generator

	public StackExchangeCallback() {
		super(new StackExchangeDataSet<D>());
	}

	// FIXME move these methods to super abstract class <--

	@Override
	public void handleResponse(Response<List<D>> response) {
		this.dataset.addElements(response.body());
	}

	@Override
	public void handleTotal(Response<List<D>> response) {
		// Ignore for the moment
	}

	@Override
	public void handleError(Call<List<D>> call, Throwable t) {
		LOG.error(t.getMessage());
		LOG.error(call.request().url()); // TODO RETRY
	}

	// --->

	@Override
	public Integer totalIterations(Response<List<D>> response) { // FIXME
		return maxCount;
	}

}