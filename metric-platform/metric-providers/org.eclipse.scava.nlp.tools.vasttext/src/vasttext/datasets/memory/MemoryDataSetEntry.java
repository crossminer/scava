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

public class MemoryDataSetEntry
{
	String text;
	String labels;
	List<Double> numericFeatures;
		
	public MemoryDataSetEntry(List<String> labels, String text, List<Double> numericFeatures)
	{
		if(labels!=null)
			this.labels=convertLabels(labels);
		else
			this.labels=null;
		this.text=text;
		this.numericFeatures=numericFeatures;
	}
	
	private String convertLabels(List<String> labels)
	{
		return String.join(" ", labels);
	}

	public String getText()
	{
		return text;
	}

	public String getLabels()
	{
		return labels;
	}
	
	public int getSizeNumericFeatures()
	{
		return numericFeatures.size();
	}

	public List<Double> getNumericFeatures()
	{
		return numericFeatures;
	}
	
	
}
