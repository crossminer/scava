package org.eclipse.scava.workflow.restmule.core.page;

import io.reactivex.annotations.Nullable;

/**
 * 
 * {@link IPaged}
 * <p>
 * @version 1.0.0
 *
 */
public interface IPaged {
	
	/**
	 * e.g. 1 (page = 1), 0 (offset = 0 )
	 */
	Integer start();
	
	/**
	 * e.g. 1 (scroll page by page), 100 (limit = 100)
	 */
	Integer increment();
	
	/**
	 * e.g. 100 (100 elements in page)
	 */
	@Nullable Integer perIteration(); 
	
	/**
	 * e.g. 10 pages, 1000 (offset = 1000), NULL
	 * @return
	 */
	@Nullable Integer max();
	
	/**
	 * e.g. "page", "offset"
	 */
	String label(); 
	
	/**
	 * e.g."perPage", "limit"
	 */
	String perIterationLabel(); 
	
	boolean hasMax(); 
	boolean hasPerIteration();
	
}
