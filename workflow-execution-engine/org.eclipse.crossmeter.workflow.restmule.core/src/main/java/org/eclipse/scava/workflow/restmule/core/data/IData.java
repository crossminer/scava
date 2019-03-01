package org.eclipse.scava.workflow.restmule.core.data;

import io.reactivex.Observable;

/**
 * 
 * {@link IData}
 * <p>
 * @version 1.0.0
 *
 */
public interface IData<T> {

	Status status();
	Observable<T> observe();
}
