/*******************************************************************************
 * Copyright (c) 2019 Edge Hill University
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package vasttext.inputsplit;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.datavec.api.split.InputSplit;

import vasttext.datasets.memory.MemoryDataSetEntry;
import vasttext.datasets.memory.MultiLabelMemoryDataSet;
import vasttext.datasets.memory.NoLabelMemoryDataSet;
import vasttext.datasets.memory.SingleLabelMemoryDataSet;

public class MemoryDataSetInputSplit implements InputSplit
{
	private List<MemoryDataSetEntry> dataset;
	private boolean labelled;
	private boolean multilabel;
	private int numericFeaturesSize;
	

	public MemoryDataSetInputSplit(MultiLabelMemoryDataSet dataset)
	{
		this.dataset=dataset.getDataset();
		labelled=true;
		multilabel=true;
		numericFeaturesSize=dataset.numericFeaturesSize();
	}
	
	public MemoryDataSetInputSplit(SingleLabelMemoryDataSet dataset)
	{
		this.dataset=dataset.getDataset();
		labelled=true;
		multilabel=false;
		numericFeaturesSize=dataset.numericFeaturesSize();
	}
	
	public MemoryDataSetInputSplit(NoLabelMemoryDataSet dataset)
	{
		this.dataset=dataset.getDataset();
		labelled=false;
		multilabel=false;
		numericFeaturesSize=dataset.numericFeaturesSize();
	}
	

	
	@Override
	public boolean canWriteToLocation(URI location) {
		return false;
	}

	@Override
	public String addNewLocation() {
		return null;
	}

	@Override
	public String addNewLocation(String location) {
		return null;
	}

	@Override
	public void updateSplitLocations(boolean reset) {
	
	}

	@Override
	public boolean needsBootstrapForWrite() {
		return false;
	}

	@Override
	public void bootStrapForWrite() {
		
	}

	@Override
	public OutputStream openOutputStreamFor(String location) throws Exception {
		return null;
	}

	@Override
	public InputStream openInputStreamFor(String location) throws Exception {
		return null;
	}

	@Override
	public long length() {
		return dataset.size();
	}

	@Override
	public URI[] locations() {
		return new URI[0];
	}

	@Override
	public Iterator<URI> locationsIterator() {
		return Collections.emptyIterator();
	}

	@Override
	public Iterator<String> locationsPathIterator() {
		 return Collections.emptyIterator();
	}

	@Override
	public void reset() {
	}

	@Override
	public boolean resetSupported() {
		return true;
	}

	public List<MemoryDataSetEntry> getDataset()
	{
		return dataset;
	}

	public boolean isLabelled()
	{
		return labelled;
	}

	public boolean isMultilabel()
	{
		return multilabel;
	}
	
	public boolean hasNumericFeatures()
	{
		if(numericFeaturesSize>0)
			return true;
		return false;
	}
	
	public int numericFeaturesSize()
	{
		return numericFeaturesSize;
	}
	
}
