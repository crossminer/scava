/*******************************************************************************
 * Copyright (c) 2019 Edge Hill University
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package vasttext.datasets.memory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

class MemoryDataSet
{
	protected List<MemoryDataSetEntry> dataset;
	protected Boolean numericFeatures = null;
	protected int numericFeaturesSize=-1;
	
	protected MemoryDataSet()
	{
		dataset = new ArrayList<MemoryDataSetEntry>();
	}
	
	protected MemoryDataSet(int size)
	{
		dataset = new ArrayList<MemoryDataSetEntry>(size);
	}
	
	protected void checkSizeNumericFeatures(int size)
	{
		if(size == 0)
			throw new UnsupportedOperationException("Number of features must be greater than zero.");
		if(numericFeaturesSize==-1)
			numericFeaturesSize=size;
		else if(numericFeaturesSize != size)
			throw new UnsupportedOperationException("Number of features must be consistent in the data set.");
	}
	
	protected void checkMixNumericFeatures(boolean flag)
	{
		if(numericFeatures==null)
			numericFeatures=flag;
		if(numericFeatures!=flag)
			throw new UnsupportedOperationException("Cannot mix inputs with and without numeric features.");
	}
	
	public int numericFeaturesSize()
	{
		if(hasNumericFeatures())
			return numericFeaturesSize;
		else
			return 0;
	}
	
	public boolean hasNumericFeatures()
	{
		if(numericFeatures==null)
			return false;
		return numericFeatures;
	}
	
	public void removeEntry(int index)
	{
		dataset.remove(index);
	}
	
	public void shuffle()
	{
		Collections.shuffle(dataset);
	}
	
	public void shuffle(Random random)
	{
		Collections.shuffle(dataset, random);
	}
	
	public List<MemoryDataSetEntry> getDataset()
	{
		return dataset;
	}
}
