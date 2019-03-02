/*******************************************************************************
 * Copyright (c) 2019 Edge Hill University
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.nlp.requestreply;

import java.io.IOException;
import java.util.List;

import org.eclipse.scava.nlp.requestreply.processing.TextProcessor;
import org.eclipse.scava.nlp.tools.predictions.singlelabel.SingleLabelPrediction;
import org.eclipse.scava.nlp.tools.predictions.singlelabel.SingleLabelPredictionCollection;
import org.eclipse.scava.tools.vasttext.Vasttext;
import org.eclipse.scava.tools.vasttext.datasets.memory.NoLabelMemoryDataSet;

public class RequestReplyClassifier
{
	private static Vasttext requestReplyClassifier;
	
	static
	{
		requestReplyClassifier = RequestReplyClassifierSingleton.getInstance().getRequestReplyClassifier();
	}
	
	/**
	 * 
	 * @param textCollection The texts must be processed only with a plain text extractor, leave the portions of code if present. 
	 * @param hasCode It is a flag that indicates whether the text contains code or not
	 * @param hadReplies It is a flag that indicates whether the text contained originally the reply character ">" that is is
	 * frequently used in newsgroups.
	 * @return
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static SingleLabelPredictionCollection predict(List<String> textCollection, List<Boolean> hasCode, List<Boolean> hadReplies) throws IOException, InterruptedException
	{
		if(textCollection.size()!=hasCode.size() && textCollection.size()!=hadReplies.size())
			throw new IllegalArgumentException("The length of the lists must be the same.");
		NoLabelMemoryDataSet classifierDataSet = new NoLabelMemoryDataSet(textCollection.size());
		SingleLabelPredictionCollection predictionCollection = new SingleLabelPredictionCollection(textCollection.size());
		TextProcessor textProcessed;
		RequestReplyExternalExtraFeatures external;
		for(int i=0; i<textCollection.size(); i++)
		{
			external = new RequestReplyExternalExtraFeatures(hasCode.get(i), hadReplies.get(i));
			textProcessed = new TextProcessor(textCollection.get(i), external);
			classifierDataSet.addEntry(textProcessed.getProcessedText(), textProcessed.getExtraFeatures());
			predictionCollection.addText(textCollection.get(i), external);
		}
		predictionCollection.setPredictions(requestReplyClassifier.predictLabels(classifierDataSet));
		return predictionCollection;
	}
	
	/**
	 * This method consider that none of the texts in {@linkplain textCollection} contained either code or a reply character 
	 * 
	 * @param textCollection The texts must be processed only with a plain text extractor, leave the portions of code if present.
	 * @return
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static SingleLabelPredictionCollection predict(List<String> textCollection) throws IOException, InterruptedException
	{
		NoLabelMemoryDataSet classifierDataSet = new NoLabelMemoryDataSet(textCollection.size());
		SingleLabelPredictionCollection predictionCollection = new SingleLabelPredictionCollection(textCollection.size());
		TextProcessor textProcessed;
		for(int i=0; i<textCollection.size(); i++)
		{
			textProcessed = new TextProcessor(textCollection.get(i));
			classifierDataSet.addEntry(textProcessed.getProcessedText(), textProcessed.getExtraFeatures());
			predictionCollection.addText(textCollection.get(i));
		}
		predictionCollection.setPredictions(requestReplyClassifier.predictLabels(classifierDataSet));
		return predictionCollection;
	}

	public static SingleLabelPredictionCollection predict(SingleLabelPredictionCollection predictionCollection) throws IOException, InterruptedException
	{
		NoLabelMemoryDataSet classifierDataSet = new NoLabelMemoryDataSet();
		TextProcessor textProcessed;
		if(predictionCollection.getExternalExtraObjectClass()==null)
		{
			for(SingleLabelPrediction entry: predictionCollection.getPredictionCollection())
			{
				textProcessed = new TextProcessor(entry.getText());
				classifierDataSet.addEntry(textProcessed.getProcessedText(), textProcessed.getExtraFeatures());
			}
		}
		else if(predictionCollection.getExternalExtraObjectClass()!=RequestReplyExternalExtraFeatures.class)
			throw new IllegalArgumentException("Use as external extra object the onne for request and reply classifier.");
		else
		{
			for(SingleLabelPrediction entry: predictionCollection.getPredictionCollection())
			{
				textProcessed = new TextProcessor(entry.getText(), (RequestReplyExternalExtraFeatures) entry.getExternalExtra());
				classifierDataSet.addEntry(textProcessed.getProcessedText(), textProcessed.getExtraFeatures());
			}
		}
		predictionCollection.setPredictions(requestReplyClassifier.predictLabels(classifierDataSet));
		return predictionCollection;
	}

	public static SingleLabelPrediction predict(String text, boolean hasCode, boolean hadReplies) throws IOException, InterruptedException
	{
		NoLabelMemoryDataSet dataset = new NoLabelMemoryDataSet(1);
		TextProcessor textProcessed;
		RequestReplyExternalExtraFeatures external = new RequestReplyExternalExtraFeatures(hasCode, hadReplies);
		textProcessed = new TextProcessor(text, external);
		dataset.addEntry(textProcessed.getProcessedText(), textProcessed.getExtraFeatures());
		SingleLabelPrediction prediction = new SingleLabelPrediction(text, external);
		prediction.setLabel((String) requestReplyClassifier.predictLabels(dataset).get(0));
		return prediction;
	}
	
	public static SingleLabelPrediction predict(String text) throws IOException, InterruptedException
	{
		NoLabelMemoryDataSet dataset = new NoLabelMemoryDataSet(1);
		TextProcessor textProcessed;
		textProcessed = new TextProcessor(text);
		dataset.addEntry(textProcessed.getProcessedText(), textProcessed.getExtraFeatures());
		SingleLabelPrediction prediction = new SingleLabelPrediction(text);
		prediction.setLabel((String) requestReplyClassifier.predictLabels(dataset).get(0));
		return prediction;
	}
}
