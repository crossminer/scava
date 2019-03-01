package org.eclipse.scava.workflow.restmule.core.callback;

import java.util.List;

import org.eclipse.scava.workflow.restmule.core.data.AbstractDataSet;
import org.eclipse.scava.workflow.restmule.core.data.IDataSet;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 
 * {@link AbstractCallback}
 * <p>
 * @version 1.0.0
 *
 */
public abstract class AbstractCallback<T, L extends List<T>, D extends AbstractDataSet<T>> implements Callback<L> {

	protected D dataset;			

	// TODO try adding the client to constructor and enqueue the url if error
	protected AbstractCallback(D dataset) {
		this.dataset = dataset;
	}

	public IDataSet<T> getDataset() {
		return dataset;
	}

	@Override
	public void onResponse(Call<L> call, Response<L> response) {
		handleResponse(response);			
	}

	@Override
	public void onFailure(Call<L> call, Throwable t) {
		handleError(call, t);			
	}
	
	public abstract void handleResponse(Response<L> response);
	
	public abstract void handleError(Call<L> call, Throwable t);
	
	public abstract Integer totalIterations(Response<L> response);
	
	public abstract void handleTotal(Response<L> response);
	

}