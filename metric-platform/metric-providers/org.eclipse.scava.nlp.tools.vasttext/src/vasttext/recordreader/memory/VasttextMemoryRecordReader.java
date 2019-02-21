/*******************************************************************************
 * Copyright (c) 2019 Edge Hill University
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package vasttext.recordreader.memory;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.datavec.api.conf.Configuration;
import org.datavec.api.records.Record;
import org.datavec.api.records.metadata.RecordMetaData;
import org.datavec.api.records.metadata.RecordMetaDataIndex;
import org.datavec.api.records.reader.BaseRecordReader;
import org.datavec.api.split.InputSplit;
import org.datavec.api.writable.NDArrayWritable;
import org.datavec.api.writable.Text;
import org.datavec.api.writable.Writable;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

import vasttext.datasets.memory.MemoryDataSetEntry;
import vasttext.inputsplit.MemoryDataSetInputSplit;

public class VasttextMemoryRecordReader extends BaseRecordReader
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8314511112544776865L;
	
	protected InputSplit inputSplit;
	protected Configuration conf;
	private Iterator<MemoryDataSetEntry> dataIterator;
	protected int listIndex = 0 ;
	protected boolean multilabel=false;
	protected boolean labelled=false;
	protected int numericFeaturesSize=-1;
	private boolean analyzeNumericFeatures=false;
	
	protected void analyzeNumericFeatures()
	{
		analyzeNumericFeatures=true;
	}
	
	@Override
	public void initialize(InputSplit split) throws IOException, InterruptedException {
		this.inputSplit = split;
		dataIterator = null;
		if(inputSplit instanceof MemoryDataSetInputSplit)
		{
			MemoryDataSetInputSplit memoryInputSplit = (MemoryDataSetInputSplit) inputSplit;
			dataIterator=memoryInputSplit.getDataset().listIterator();
			labelled=memoryInputSplit.isLabelled();
			multilabel=memoryInputSplit.isMultilabel();
			numericFeaturesSize=memoryInputSplit.numericFeaturesSize();
		}
		if (dataIterator == null)
            throw new UnsupportedOperationException("Unknown input split: " + inputSplit);
	}

	@Override
	public void initialize(Configuration conf, InputSplit split) throws IOException, InterruptedException {
		this.conf = conf;
        initialize(split);
	}

	@Override
	public List<Writable> next() {
		if(hasNext())
		{
			List<Writable> processNext = new ArrayList<>();
			MemoryDataSetEntry next = dataIterator.next();
			invokeListeners(next);
			if(!analyzeNumericFeatures)
			{
				processNext.add(new Text(next.getText()));
				if(labelled)
					processNext.add(new Text(next.getLabels()));
			}
			else
			{
				INDArray transformed = Nd4j.create(next.getNumericFeatures().stream().mapToDouble(d->d).toArray(),new int[]{next.getSizeNumericFeatures(),1});
				processNext.add(new NDArrayWritable(transformed));
			}
			listIndex++;
			return processNext;
		}
		else
		{
			throw new NoSuchElementException("No more elements found.");
		}
	}

	@Override
	public boolean hasNext()
	{
		if (dataIterator!=null && dataIterator.hasNext())
			return true;
		return false;
	}

	@Override
	public List<String> getLabels()
	{
		return null;
	}

	@Override
	public void reset()
	{
		if(inputSplit == null)
			throw new UnsupportedOperationException("Initilize first, then reset");
		try
		{
			inputSplit.reset();         //In theory this line is useless, if they are using MemoryListInputSplit
			listIndex=0;
			initialize(inputSplit);
		}
		catch (Exception e) {
            throw new RuntimeException("Error during VasttextMemoryRecordReader reset", e);
        }
		
	}

	@Override
	public boolean resetSupported() {
		if(inputSplit != null)
			return inputSplit.resetSupported();
		return false;
	}

	@Override
	public List<Writable> record(URI uri, DataInputStream dataInputStream) throws IOException {
		return null;
	}

	@Override
	public Record nextRecord() {
		List<Writable> next = next();
		//As we increase in next the listIndex, then we need to reduce in one the value of listIndex
		RecordMetaData meta = new RecordMetaDataIndex(listIndex-1, null, VasttextMemoryRecordReader.class);
		return new org.datavec.api.records.impl.Record(next, meta);
	}

	@Override
	public Record loadFromMetaData(RecordMetaData recordMetaData) throws IOException {
		return null;
	}

	@Override
	public List<Record> loadFromMetaData(List<RecordMetaData> recordMetaDatas) throws IOException {
		return null;
	}

	@Override
	public void close() throws IOException {
		
	}

	@Override
	public void setConf(Configuration conf) {
		this.conf=conf;
	}

	@Override
	public Configuration getConf() {
		return conf;
	}

}
