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

import java.io.IOException;
import java.util.List;

import org.datavec.api.conf.Configuration;
import org.datavec.api.records.Record;
import org.datavec.api.records.metadata.RecordMetaData;
import org.datavec.api.records.metadata.RecordMetaDataIndex;
import org.datavec.api.split.InputSplit;
import org.datavec.api.writable.Writable;

public class VasttextExtraMemoryReader  extends VasttextMemoryRecordReader
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7139676315019263368L;
	
    
    private List<Integer> linesToAvoid;
	
    public VasttextExtraMemoryReader(List<Integer> linesToAvoid)
    {
		this.linesToAvoid=linesToAvoid;
	}
    
    @Override
    public void reset()
    {
    	super.reset();
    }
    
    @Override
    public void initialize(Configuration conf, InputSplit split) throws IOException, InterruptedException {
    	super.analyzeNumericFeatures();
        super.initialize(conf, split);
        checkNumericFeatures();
    }
    
    private void checkNumericFeatures()
    {
    	if(numericFeaturesSize==0)
    		throw new UnsupportedOperationException("Number of features has been defined as 0, therefore, do not use this reader.");
    	else if(numericFeaturesSize<0)
    		throw new UnsupportedOperationException("Please intialize before checking the number of features.");
    }
    
    private Record processNextRecord()
    {
    	List<Writable> next = super.next();
    	//As we increase in next the listIndex, then we need to reduce in one the value of listIndex
    	RecordMetaData meta = new RecordMetaDataIndex(listIndex-1, null, VasttextExtraMemoryReader.class);
		return new org.datavec.api.records.impl.Record(next, meta);
    }
    
    @Override
    public List<String> getLabels() {
        return null;
    }
    
    @Override
    public List<Writable> next() {
    		return nextRecord().getRecord();
    }

    @Override
    public Record nextRecord() {
            return processNextRecord();
    }

    @Override
    public boolean hasNext() {
    	if(linesToAvoid.contains(listIndex))
    	{
    		System.err.println("WARNING: The entry "+ listIndex + " has been skipped from data set due to a label problem.");
    		if(super.hasNext())
    			next();
    		else
    			return false;
    	}
        return super.hasNext();
    }

    @Override
    public void close() throws IOException {

    }

    @Override
    public void setConf(Configuration conf) {
        this.conf = conf;
    }

    @Override
    public Configuration getConf() {
        return conf;
    }

    public int getNumericFeaturesSize() {
        return numericFeaturesSize;
    }

    @Override
    public Record loadFromMetaData(RecordMetaData recordMetaData) throws IOException {
        return null;
    }

    @Override
    public List<Record> loadFromMetaData(List<RecordMetaData> recordMetaDatas) throws IOException {
        return null;
    }
}
