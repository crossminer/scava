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
import java.util.List;

public class SingleLabelMemoryDataSet extends MemoryDataSet
{
	public SingleLabelMemoryDataSet()
	{
		super();
	}
	
	public SingleLabelMemoryDataSet(int size)
	{
		super(size);
	}
	
	public void addEntry(String label, String text, List<Double> numericFeatures)
	{
		checkMixNumericFeatures(true);
		checkSizeNumericFeatures(numericFeatures.size());
		dataset.add(new MemoryDataSetEntry(stringToLabel(label), text, numericFeatures));
	}
	
	public void addEntry(String label, String text)
	{
		checkMixNumericFeatures(false);
		dataset.add(new MemoryDataSetEntry(stringToLabel(label), text, null));
	}
	
	public void modifyEntry(int index, String label, String text, List<Double> numericFeatures)
	{
		checkMixNumericFeatures(true);
		checkSizeNumericFeatures(numericFeatures.size());
		dataset.set(index, new MemoryDataSetEntry(stringToLabel(label), text, numericFeatures));
	}
	
	public void modifyEntry(int index, String label, String text)
	{
		checkMixNumericFeatures(false);
		dataset.set(index, new MemoryDataSetEntry(stringToLabel(label), text, null));
	}
	
	private List<String> stringToLabel(String label)
	{
		List<String> labels = new ArrayList<String>();
		labels.add(label);
		return labels;
	}
}
