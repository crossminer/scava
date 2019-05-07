/*-
 * 	* Copyright 2018 Edge Hill University
 *  * Copyright 2016 Skymind, Inc.
 *  *
 *  *    Licensed under the Apache License, Version 2.0 (the "License");
 *  *    you may not use this file except in compliance with the License.
 *  *    You may obtain a copy of the License at
 *  *
 *  *        http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  *    Unless required by applicable law or agreed to in writing, software
 *  *    distributed under the License is distributed on an "AS IS" BASIS,
 *  *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  *    See the License for the specific language governing permissions and
 *  *    limitations under the License.
 *  *
 *  * 	 This file has been modified to support characteristics from VastText.
 */

package vasttext.recordreader.memory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.datavec.api.conf.Configuration;
import org.datavec.api.records.Record;
import org.datavec.api.records.metadata.RecordMetaData;
import org.datavec.api.records.metadata.RecordMetaDataIndex;
import org.datavec.api.records.metadata.RecordMetaDataURI;
import org.datavec.api.split.InputSplit;
import org.datavec.api.vector.Vectorizer;
import org.datavec.api.writable.IntWritable;
import org.datavec.api.writable.NDArrayWritable;
import org.datavec.api.writable.Text;
import org.datavec.api.writable.Writable;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

import vasttext.vectorizer.VasttextTextVectorizer;

/**
 * This program is based on the TFIDF record reader created by Adam Gibson.
 * It now reads the file format of VastText.
 *
 * @author Adam Gibson
 * @author Adri�n Cabrera
 */
public class VasttextTextMemoryReader extends VasttextMemoryRecordReader
{

	


	/**
	 * 
	 */
	private static final long serialVersionUID = -2050559157750634037L;
	
	
	private VasttextTextVectorizer vasttextTextVectorizer;
    private List<Record> records = new ArrayList<>();
    private Iterator<Record> recordIter;
    private int textFeaturesSize;
    private boolean initialized = false;
    
    private List<Integer> linesDeleted= new ArrayList<Integer>();
    
    public List<Integer> getLinesDeleted()
    {
    	return linesDeleted;
    }
    
    public boolean isMultilabel()
    {
    	return multilabel;
    }
    
    
    @Override
    public void initialize(Configuration conf, InputSplit split) throws IOException, InterruptedException {
        super.initialize(conf, split);
        //train  a new one since it hasn't been specified
        if (vasttextTextVectorizer == null) {
            vasttextTextVectorizer = new VasttextTextVectorizer();
            vasttextTextVectorizer.initialize(conf);
            //clear out old strings
            records.clear();

            vasttextTextVectorizer.fitTransform(this, new Vectorizer.RecordCallBack() {
                @Override
                public void onRecord(Record fullRecord) {
                    records.add(fullRecord);
                }
            });

            //cache the number of features used for each document
            textFeaturesSize = vasttextTextVectorizer.getVocabularySize();
            recordIter = records.iterator();
            vasttextTextVectorizer.setfitFinished();
        } else {
            records = new ArrayList<>();
            int lastLine=0;
            //the record reader has 2 phases, we are skipping the
            //document frequency phase and just using the super() to get the file contents
            //and pass it to the already existing vectorizer.
            while (hasNext())
            {
            	Record record = nextRecord();
            	if(record==null)
            	{
            		System.err.println("WARNING: The entry "+ lastLine + " has been deleted from data set due to a label problem.");
            		linesDeleted.add(lastLine);
                	continue;
            	}
            	lastLine++;
            	
                INDArray transformed = vasttextTextVectorizer.transform(record);
                
                org.datavec.api.records.impl.Record transformedRecord = new org.datavec.api.records.impl.Record(
                				new ArrayList<>(Collections.<Writable>singletonList(new NDArrayWritable(transformed))),
                                new RecordMetaDataURI(record.getMetaData().getURI(), VasttextTextMemoryReader.class));

                if(labelled)
                	transformedRecord.getRecord().add(record.getRecord().get(record.getRecord().size() - 1));

                records.add(transformedRecord);
            }

            recordIter = records.iterator();
        }
        this.initialized = true;
    }

    @Override
    public void reset() {
        if (inputSplit == null)
            throw new UnsupportedOperationException("Cannot reset without first initializing");
        recordIter = records.iterator();
    }
    
    public Record processNextRecord() {
        List<Writable> nextProcessed = new ArrayList<>();
        List<Writable> next = next();
        int label;
        
        String text = next.get(0).toString();
        //Text part
        nextProcessed.add(new Text(text));
       
       //Label part 
       if(labelled)
       {
			if (multilabel)
			{
				String[] labels = next.get(1).toString().split(" ");
				// We need to create a vector of labels
				List<Double> transformedLabels = new ArrayList<Double>();
				boolean seenLabels = false;
				for (int i = 0; i < labels.length; i++)
				{
					label = vasttextTextVectorizer.getLabelIndex(labels[i]);
					if (label == -1 && vasttextTextVectorizer.hasfitFinished())
					{
						System.err.println("WARNING: The label \"" + next.get(1).toString()
								+ "\" wasn't seen during the training. It has been removed from the entry "
								+ (listIndex - 1));
						continue;
					}
					else if (label == -1)
						throw new UnsupportedOperationException(
								"Unknown error from the vectorizer. Returned a label of -1 during fitting the dictionary.");
					transformedLabels.add((double) label);
					seenLabels = true;
				}
				// This means that none of the labels was seen in the training
				if (!seenLabels)
					return null;
				INDArray transformed = Nd4j.create(transformedLabels.stream().mapToDouble(d -> d).toArray(),
						new int[] { transformedLabels.size(), 1 });
				nextProcessed.add(new NDArrayWritable(transformed));

			}
			else 
			{
				label = vasttextTextVectorizer.getLabelIndex(next.get(1).toString());
				if (label == -1 && vasttextTextVectorizer.hasfitFinished())
				{
					System.err.println(
							"WARNING: The label \"" + next.get(1).toString() + "\" wasn't seen during the training.");
					return null;
				}
				else if (label == -1)
					throw new UnsupportedOperationException(
							"Unknown error from the vectorizer. Returned a label of -1 during fitting the dictionary.");
				nextProcessed.add(new IntWritable(label));
	        }
       }
       //As we increase in next the listIndex, then we need to reduce in one the value of listIndex
       RecordMetaData meta = new RecordMetaDataIndex(listIndex-1, null, VasttextMemoryRecordReader.class); 
       return new org.datavec.api.records.impl.Record(nextProcessed, meta);
    }
    
    @Override
    public List<String> getLabels()
    {
    	if(labelled)
    		return vasttextTextVectorizer.getLabels();
    	else
    		return null;
    }
    
    @Override
    public List<Writable> next() {
    	//we aren't done vectorizing yet
    	if (recordIter == null)
    		return super.next();
        return nextRecord().getRecord();
    }

    @Override
    public Record nextRecord() {
    	//we aren't done vectorizing yet
        if (recordIter == null)
            return processNextRecord();
        return recordIter.next();
    }

    @Override
    public boolean hasNext() {
        //we aren't done vectorizing yet
        if (recordIter == null)
            return super.hasNext();
        return recordIter.hasNext();
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

    public VasttextTextVectorizer getVasttextTextVectorizer() {
        return vasttextTextVectorizer;
    }

    public void setVastTextTextVectorizer(VasttextTextVectorizer vastTextTextVectorizer) {
        if (initialized)
            throw new IllegalArgumentException("Setting VasttextTextVectorizer after VasttextTextRecordReader initialization doesn't have an effect");
        this.vasttextTextVectorizer = vastTextTextVectorizer;
    }

    public int getTextFeaturesSize() {
        return textFeaturesSize;
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

