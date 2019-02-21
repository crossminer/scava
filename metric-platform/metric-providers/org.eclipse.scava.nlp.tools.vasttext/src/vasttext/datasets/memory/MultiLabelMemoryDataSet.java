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

public class MultiLabelMemoryDataSet extends MemoryDataSet
{	
	public MultiLabelMemoryDataSet()
	{
		super();
	}
	
	public MultiLabelMemoryDataSet(int size)
	{
		super(size);
	}
	
	public void addEntry(List<String> labels, String text, List<Double> numericFeatures)
	{
		checkMixNumericFeatures(true);
		checkSizeNumericFeatures(numericFeatures.size());
		dataset.add(new MemoryDataSetEntry(labels, text, numericFeatures));
	}
	
	public void addEntry(List<String> labels, String text)
	{
		checkMixNumericFeatures(false);
		dataset.add(new MemoryDataSetEntry(labels, text, null));
	}
	
	public void modifyEntry(int index, List<String> labels, String text, List<Double> numericFeatures)
	{
		checkMixNumericFeatures(true);
		checkSizeNumericFeatures(numericFeatures.size());
		dataset.set(index, new MemoryDataSetEntry(labels, text, numericFeatures));
	}
	
	public void modifyEntry(int index, List<String> labels, String text)
	{
		checkMixNumericFeatures(false);
		dataset.set(index, new MemoryDataSetEntry(labels, text, null));
	}
}
