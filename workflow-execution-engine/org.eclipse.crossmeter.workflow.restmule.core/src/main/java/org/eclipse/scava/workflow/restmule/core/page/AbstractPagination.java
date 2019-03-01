package org.eclipse.scava.workflow.restmule.core.page;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.scava.workflow.restmule.core.callback.AbstractCallback;
import org.eclipse.scava.workflow.restmule.core.callback.AbstractWrappedCallback;
import org.eclipse.scava.workflow.restmule.core.data.AbstractDataSet;
import org.eclipse.scava.workflow.restmule.core.data.IDataSet;
import org.eclipse.scava.workflow.restmule.core.util.RetrofitUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 
 * {@link AbstractPagination}
 * <p>
 * @version 1.0.0
 *
 */
public abstract class AbstractPagination implements IPaged {
	
	private static final Logger LOG = LogManager.getLogger(AbstractPagination.class);

	Integer startValue;
	Integer max;
	Integer increment;
	Integer perIteration;
	String perIterationLabel;
	String pageLabel;

	protected AbstractPagination(){}

	protected AbstractPagination(
			String pageLabel, 
			String perIterationLabel, 
			Integer perIteration,
			Integer max, 
			Integer startValue, 
			Integer increment)
	{
		set(pageLabel, perIterationLabel, perIteration, max, startValue, increment);
	}

	protected void set(
			String pageLabel, 
			String perIterationLabel, 
			Integer perIteration,
			Integer max, 
			Integer startValue, 
			Integer increment)
	{
		this.pageLabel = pageLabel;
		this.perIterationLabel = perIterationLabel;
		this.perIteration = perIteration;
		this.startValue = startValue;
		this.increment = increment;
		this.max = max;
	}

	@Override
	public Integer start() {
		return this.startValue;
	}

	@Override
	public Integer increment() {
		return this.increment;
	}

	@Override
	public Integer perIteration() {
		return this.perIteration;
	}

	@Override
	public boolean hasPerIteration() {
		return this.perIteration != null;
	}

	@Override
	public Integer max() {
		return this.max;
	}

	@Override
	public boolean hasMax() {
		return this.max != null;
	}

	@Override
	public String label() {
		return this.pageLabel;
	}

	@Override
	public String perIterationLabel() {
		return this.perIterationLabel;
	}

	/** UTILS FOR SUBCLASSES */

	protected <
		T, WRAP extends IWrap<T>, END, DATA extends AbstractDataSet<T>, CALL extends AbstractWrappedCallback<T,WRAP,DATA> 	
	> 
	IDataSet<T> traverse(
			CALL callback,			
			String methodName, 
			Class<?>[] types, 
			Object[] vals, 
			END client )
	{
		try {
			Integer start = this.start();
			Integer increment = this.increment(); 
			int pagedParams;
			Object[] listVals;
			Class<?>[] listClass;

			int l = 0;
			if (types != null && vals != null){				
				if (types.length != vals.length) {
					throw new IllegalArgumentException("Invalid parameters");
				}
				l = vals.length;
			}
			
			final int argsLength = l;
			
			if (this.hasPerIteration()){
				pagedParams = 2;

				listVals = new Object[argsLength + pagedParams];
				listClass = new Class<?>[argsLength + pagedParams];

				listClass[argsLength] = Integer.class;
				listVals[argsLength] = this.perIteration();
				;
			} else{
				pagedParams = 1;
				listVals = new Object[argsLength + pagedParams];
				listClass = new Class<?>[argsLength + pagedParams];
			}
			for (int i = 0 ; i < types.length ; i++ ){
				listVals[i] = vals[i];
				listClass[i] = types[i];
			}

			listClass[argsLength + pagedParams - 1] = Integer.class;
			listVals[argsLength + pagedParams - 1] = start;
			Call<WRAP> call = RetrofitUtil.<WRAP, END>getCall(methodName, listClass, listVals, (END) client);
			call.enqueue(new Callback<WRAP>() {
				@Override public void onResponse(Call<WRAP> call, Response<WRAP> response) {
					callback.handleTotal(response);
					callback.handleResponse(response);
					Integer limit = callback.totalIterations(response); // FIXME
					if (limit != null && limit != start ){
						for (int iteration = start + increment; iteration <= limit; iteration = iteration + increment){
							try {
								listVals[argsLength + pagedParams - 1] = iteration;
								Call<WRAP> pageCall = RetrofitUtil.<WRAP, END>
									getCall(methodName, listClass, listVals, client);
								pageCall.enqueue((Callback<WRAP>) callback);
							} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
									| NoSuchMethodException | SecurityException e) {
								e.printStackTrace();
							}
						}
					}
				}
				@Override public void onFailure(Call<WRAP> call, Throwable t) { t.printStackTrace(); }
			});
		} catch (Exception e) { e.printStackTrace(); }
		return callback.getDataset();		
	}
	
	
	protected <
		T, 
		END, 
		DATA extends AbstractDataSet<T>,
		CALL extends AbstractCallback<T, List<T>, DATA>
	> 
	IDataSet<T> traversePages(
			CALL callback,			
			String methodName, 
			Class<?>[] types, 
			Object[] vals, 
			END client)
	{
		try {
			Integer start = this.start();
			Integer increment = this.increment(); 

			int pagedParams;
			Object[] listVals;
			Class<?>[] listClass;
			
			int l = 0;
			if (types != null && vals != null){				
				if (types.length != vals.length) {
					throw new IllegalArgumentException("Invalid parameters");
				}
				l = vals.length;
			}
			
			final int argsLength = l;
			
			if (this.hasPerIteration()){
				pagedParams = 2;

				listVals = new Object[argsLength + pagedParams];
				listClass = new Class<?>[argsLength + pagedParams];

				listClass[argsLength] = Integer.class;
				listVals[argsLength] = this.perIteration();
				;
			} else{
				pagedParams = 1;
				listVals = new Object[argsLength + pagedParams];
				listClass = new Class<?>[argsLength + pagedParams];
			}
			for (int i = 0 ; i < types.length ; i++ ){
				listVals[i] = vals[i];
				listClass[i] = types[i];
			}
		
			listClass[argsLength + pagedParams - 1] = Integer.class;
			listVals[argsLength + pagedParams - 1] = start;

			Call<List<T>> call = RetrofitUtil.<List<T>, END>getCall(methodName, listClass, listVals, (END) client);
			call.enqueue(new Callback<List<T>>() {
				@Override public void onResponse(Call<List<T>> call, Response<List<T>> response) {
					((CALL) callback).handleTotal(response);
					((CALL) callback).handleResponse(response);
					Integer limit = callback.totalIterations(response);
					if (limit != null && limit != start){
						for (int iteration = start + increment; iteration <= limit; iteration = iteration + increment){
							try {
								listVals[argsLength + pagedParams - 1] = iteration;
								Call<List<T>> pageCall = RetrofitUtil.<List<T>, END>
								getCall(methodName, listClass, listVals, client);
								pageCall.enqueue(callback);
							} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
									| NoSuchMethodException | SecurityException e) {
								e.printStackTrace();
							}
						}
					}
				}
				@Override public void onFailure(Call<List<T>> call, Throwable t) {
					t.printStackTrace();
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return callback.getDataset();		
	}
	
}
