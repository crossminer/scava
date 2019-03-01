package org.eclipse.scava.workflow.restmule.core.cache;

/**
 * 
 * {@link IAdapter}
 * <p>
 * @version 1.0.0
 *
 */
public interface IAdapter<T> {

	T response();
	String body();
}
