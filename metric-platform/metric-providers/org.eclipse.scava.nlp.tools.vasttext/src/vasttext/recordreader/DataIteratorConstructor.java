/*******************************************************************************
 * Copyright (c) 2019 Edge Hill University
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package vasttext.recordreader;

import java.io.IOException;
import java.util.List;

import org.datavec.api.conf.Configuration;
import org.datavec.api.split.FileSplit;
import org.nd4j.linalg.dataset.api.iterator.MultiDataSetIterator;

import vasttext.datasets.file.FileDataSet;
import vasttext.datasets.file.MultiLabelFileDataSet;
import vasttext.datasets.file.NoLabelFileDataSet;
import vasttext.datasets.file.SingleLabelFileDataSet;
import vasttext.datasets.memory.MultiLabelMemoryDataSet;
import vasttext.datasets.memory.NoLabelMemoryDataSet;
import vasttext.datasets.memory.SingleLabelMemoryDataSet;
import vasttext.inputsplit.MemoryDataSetInputSplit;
import vasttext.recordreader.VasttextDataIterator.Builder;
import vasttext.recordreader.file.VasttextExtraFileReader;
import vasttext.recordreader.file.VasttextTextFileReader;
import vasttext.recordreader.memory.VasttextExtraMemoryReader;
import vasttext.recordreader.memory.VasttextTextMemoryReader;
import vasttext.vectorizer.VasttextTextVectorizer;

public class DataIteratorConstructor
{
	private MultiDataSetIterator dataIterator;
	
	private static String NAME_SPACE = DataIteratorConstructor.class.getName();
	
	private boolean multilabel;
	private List<String> labels;
	private int textFeaturesSize;
	private int numericFeaturesSize=0;
	private VasttextTextVectorizer vectorizer=null;
	private boolean storeVectorizer=false;
	
	//Configurable
	public final static String batchSize = NAME_SPACE+".batchSize";
	
	//File based
	public DataIteratorConstructor(Configuration configuration, MultiLabelFileDataSet dataset) throws IOException, InterruptedException
	{
		storeVectorizer=true;
		buildDataIterator(configuration, dataset, null);
	}
	
	public DataIteratorConstructor(Configuration configuration, MultiLabelFileDataSet dataset, VasttextTextVectorizer vectorizer) throws IOException, InterruptedException
	{
		storeVectorizer=false;
		buildDataIterator(configuration, dataset, vectorizer);
	}
	
	public DataIteratorConstructor(Configuration configuration, SingleLabelFileDataSet dataset) throws IOException, InterruptedException
	{
		storeVectorizer=true;
		buildDataIterator(configuration, dataset, null);
	}
	
	public DataIteratorConstructor(Configuration configuration, SingleLabelFileDataSet dataset, VasttextTextVectorizer vectorizer) throws IOException, InterruptedException
	{
		storeVectorizer=false;
		buildDataIterator(configuration, dataset, vectorizer);
	}
	
	public DataIteratorConstructor(Configuration configuration, NoLabelFileDataSet dataset, VasttextTextVectorizer vectorizer) throws IOException, InterruptedException
	{
		buildDataIterator(configuration, dataset, vectorizer);
	}
	
	//Memory based
	public DataIteratorConstructor(Configuration configuration, MultiLabelMemoryDataSet dataset) throws IOException, InterruptedException
	{
		storeVectorizer=true;
		buildDataIterator(configuration, new MemoryDataSetInputSplit(dataset), null);
	}
	
	public DataIteratorConstructor(Configuration configuration, MultiLabelMemoryDataSet dataset, VasttextTextVectorizer vectorizer) throws IOException, InterruptedException
	{
		buildDataIterator(configuration, new MemoryDataSetInputSplit(dataset), vectorizer);
	}
	
	public DataIteratorConstructor(Configuration configuration, SingleLabelMemoryDataSet dataset) throws IOException, InterruptedException
	{
		storeVectorizer=true;
		buildDataIterator(configuration, new MemoryDataSetInputSplit(dataset), null);
	}
	
	public DataIteratorConstructor(Configuration configuration, SingleLabelMemoryDataSet dataset, VasttextTextVectorizer vectorizer) throws IOException, InterruptedException
	{
		buildDataIterator(configuration, new MemoryDataSetInputSplit(dataset), vectorizer);
	}
	
	public DataIteratorConstructor(Configuration configuration, NoLabelMemoryDataSet dataset, VasttextTextVectorizer vectorizer) throws IOException, InterruptedException
	{
		buildDataIterator(configuration, new MemoryDataSetInputSplit(dataset), vectorizer);
	}
	
	private void buildDataIterator(Configuration configuration, FileDataSet fileDataSet, VasttextTextVectorizer vectorizer) throws IOException, InterruptedException
	{
		Builder vasttextDataIteratorBuilder = new VasttextDataIterator.Builder(configuration.getInt(batchSize, 32));
		
		configuration.setBoolean(VasttextTextFileReader.LABELLED, fileDataSet.isLabelled());
		configuration.setBoolean(VasttextTextFileReader.MULTILABEL, fileDataSet.isMultiLabel());
		
		VasttextTextFileReader textFileReader = new VasttextTextFileReader();
		if(vectorizer!=null)
			textFileReader.setVastTextTextVectorizer(vectorizer);
		textFileReader.initialize(configuration, new FileSplit(fileDataSet.getTextFilePath().toFile()));
		
		multilabel=fileDataSet.isMultiLabel();
		textFeaturesSize=textFileReader.getTextFeaturesSize();
		labels=textFileReader.getLabels();
		
		if(labels!=null && storeVectorizer)
		{
			this.vectorizer=textFileReader.getVasttextTextVectorizer();
		}
		else
		{
			storeVectorizer=false;
			this.vectorizer=null;
		}
		
		vasttextDataIteratorBuilder.addReader("textReader", textFileReader);
		if(labels!=null)
			vasttextDataIteratorBuilder.addOutput("textReader", labels.size());
		vasttextDataIteratorBuilder.addInput("textReader");
		
		if(fileDataSet.getExtraFilePath()!=null)
		{
			configuration.setInt(VasttextExtraFileReader.FEATURES, fileDataSet.numericFeaturesSize());
			
			VasttextExtraFileReader extraFileReader = new VasttextExtraFileReader(textFileReader.getLinesDeleted());
			extraFileReader.initialize(configuration, new FileSplit(fileDataSet.getExtraFilePath().toFile()));
			
			numericFeaturesSize=fileDataSet.numericFeaturesSize();
			
			vasttextDataIteratorBuilder.addReader("extraReader", extraFileReader);
			vasttextDataIteratorBuilder.addInput("extraReader");
		}

		dataIterator = vasttextDataIteratorBuilder.build();
		
	}
	
	private void buildDataIterator(Configuration configuration, MemoryDataSetInputSplit memoryDataSetInputSplit, VasttextTextVectorizer vectorizer) throws IOException, InterruptedException
	{	
		Builder vasttextDataIteratorBuilder = new VasttextDataIterator.Builder(configuration.getInt(batchSize, 32));
		
		VasttextTextMemoryReader textMemoryReader = new VasttextTextMemoryReader();
		if(vectorizer!=null)
			textMemoryReader.setVastTextTextVectorizer(vectorizer);
		textMemoryReader.initialize(configuration, memoryDataSetInputSplit);
		
		
		
		multilabel=textMemoryReader.isMultilabel();
		textFeaturesSize = textMemoryReader.getTextFeaturesSize();
		labels=textMemoryReader.getLabels();
		
		if(labels!=null && storeVectorizer)
		{
			this.vectorizer=textMemoryReader.getVasttextTextVectorizer();
		}
		else
		{
			storeVectorizer=false;
			this.vectorizer=null;
		}
		
		vasttextDataIteratorBuilder.addReader("textReader", textMemoryReader);
		if(labels!=null)
			vasttextDataIteratorBuilder.addOutput("textReader", labels.size());
		vasttextDataIteratorBuilder.addInput("textReader");
		
		if(memoryDataSetInputSplit.hasNumericFeatures())
		{
			VasttextExtraMemoryReader extraMemoryReader = new VasttextExtraMemoryReader(textMemoryReader.getLinesDeleted());
			extraMemoryReader.initialize(configuration, memoryDataSetInputSplit);
			
			numericFeaturesSize=extraMemoryReader.getNumericFeaturesSize();
			
			vasttextDataIteratorBuilder.addReader("extraReader", extraMemoryReader);
			vasttextDataIteratorBuilder.addInput("extraReader");
		}

		dataIterator = vasttextDataIteratorBuilder.build();
		
		
	}
	
	public boolean isMultilabel()
	{
		return multilabel;
	}
	
	public int getTextFeaturesSize()
	{
		return textFeaturesSize;
	}
	
	public int getNumericFeaturesSize()
	{
		return numericFeaturesSize;
	}
	
	public List<String> getLabels()
	{
		return labels;
	}
	
	public MultiDataSetIterator getDataIterator()
	{
		return dataIterator;
	}
	
	public VasttextTextVectorizer getVectorizer()
	{
		return vectorizer;	
	}
}
