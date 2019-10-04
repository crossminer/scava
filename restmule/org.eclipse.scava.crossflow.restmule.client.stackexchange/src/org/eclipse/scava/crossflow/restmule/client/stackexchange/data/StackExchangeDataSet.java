package org.eclipse.scava.crossflow.restmule.client.stackexchange.data;

import org.eclipse.scava.crossflow.restmule.core.data.AbstractDataSet;
import org.eclipse.scava.crossflow.restmule.client.stackexchange.page.StackExchangePagination;

public class StackExchangeDataSet<T> extends AbstractDataSet<T> {

	public StackExchangeDataSet(){
		super(StackExchangePagination.get());
	}
	
}
