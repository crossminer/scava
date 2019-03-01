package org.eclipse.scava.workflow.restmule.core.util;

import java.lang.reflect.InvocationTargetException;

import retrofit2.Call;

/**
 * 
 * {@link RetrofitUtil}
 * <p>
 * @version 1.0.0
 *
 */
public class RetrofitUtil {

	@SuppressWarnings("unchecked")
	public static <R, T> Call<R> getCall(
			String methodName, 
			Class<?>[] types,
			Object[] vals, 
			T client) 
					throws 
					IllegalAccessException, 
					IllegalArgumentException, 
					InvocationTargetException, 
					NoSuchMethodException, 
					SecurityException
	{
		if (types.length > 0 && types.length == vals.length){
			Class<? extends Object> clazz = client.getClass();
			return (Call<R>) clazz.getMethod(methodName,types).invoke(client, vals);
		}
		return null;
	}

}
