package org.eclipse.scava.crossflow.restmule.core.data;

/**
 * 
 * {@link IDataSet}
 * <p>
 * @version 1.0.0
 *
 */
public interface IDataSet<T> extends IData<T> {

	Integer total(); 
	Integer percentage();
	Integer count();
	String id();

}
