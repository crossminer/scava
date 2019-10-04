package org.eclipse.scava.crossflow.restmule.client.bitbucket.data;

import org.eclipse.scava.crossflow.restmule.core.data.AbstractDataSet;
import org.eclipse.scava.crossflow.restmule.client.bitbucket.page.BitbucketPagination;

public class BitbucketDataSet<T> extends AbstractDataSet<T> {

	public BitbucketDataSet(){
		super(BitbucketPagination.get());
	}
	
}
