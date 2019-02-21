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

import java.util.List;

public class NoLabelMemoryDataSet extends MemoryDataSet
{
	public NoLabelMemoryDataSet(int size)
	{
		super(size);
	}
	
	public NoLabelMemoryDataSet()
	{
		super();
	}
	
	public void addEntry(String text, List<Double> numericFeatures)
	{
		checkMixNumericFeatures(true);
		checkSizeNumericFeatures(numericFeatures.size());
		dataset.add(new MemoryDataSetEntry(null, text, numericFeatures));
	}
	
	public void addEntry(String text)
	{
		checkMixNumericFeatures(false);
		dataset.add(new MemoryDataSetEntry(null, text, null));
	}
	
	public void modifyEntry(int index, String text, List<Double> numericFeatures)
	{
		checkMixNumericFeatures(true);
		checkSizeNumericFeatures(numericFeatures.size());
		dataset.set(index, new MemoryDataSetEntry(null, text, numericFeatures));
	}
	
	public void modifyEntry(int index, String text)
	{
		checkMixNumericFeatures(false);
		dataset.set(index, new MemoryDataSetEntry(null, text, null));
	}
}
