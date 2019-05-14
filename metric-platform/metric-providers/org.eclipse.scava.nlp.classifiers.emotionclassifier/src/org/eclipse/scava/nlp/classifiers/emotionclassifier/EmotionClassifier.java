/*******************************************************************************
 * Copyright (c) 2019 Edge Hill University
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.nlp.classifiers.emotionclassifier;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.scava.nlp.classifiers.emotionclassifier.processing.TextProcessor;
import org.eclipse.scava.nlp.tools.predictions.multilabel.MultiLabelPrediction;
import org.eclipse.scava.nlp.tools.predictions.multilabel.MultiLabelPredictionCollection;

import vasttext.Vasttext;
import vasttext.datasets.memory.NoLabelMemoryDataSet;

public class EmotionClassifier
{
	private static Vasttext emotionClassifier;
	
	private static List<String> defaultLabel;
	
	static
	{
		emotionClassifier = EmotionClassifierSingleton.getInstance().getEmotionClassifier();
		defaultLabel = new ArrayList<String>(0); //The default label is an empty list
	}
	
	public static MultiLabelPredictionCollection predict(List<String> textCollection) throws IOException, InterruptedException
	{
		NoLabelMemoryDataSet classifierDataSet = new NoLabelMemoryDataSet(textCollection.size());
		MultiLabelPredictionCollection predictionCollection = new MultiLabelPredictionCollection(textCollection.size());
		TextProcessor textProcessed;
		for(String text: textCollection)
		{
			textProcessed = new TextProcessor(text);
			classifierDataSet.addEntry(textProcessed.getProcessedText(), textProcessed.getExtraFeatures());
			predictionCollection.addText(text);
		}
		predictionCollection.setPredictions(emotionClassifier.predictLabels(classifierDataSet), defaultLabel);
		return predictionCollection;
	}
	
	public static MultiLabelPredictionCollection predict(MultiLabelPredictionCollection predictionCollection) throws IOException, InterruptedException
	{
		NoLabelMemoryDataSet classifierDataSet = new NoLabelMemoryDataSet(predictionCollection.size());
		TextProcessor textProcessed;
		for(String text: predictionCollection.getTextCollection())
		{
			textProcessed = new TextProcessor(text);
			classifierDataSet.addEntry(textProcessed.getProcessedText(), textProcessed.getExtraFeatures());
		}
		predictionCollection.setPredictions(emotionClassifier.predictLabels(classifierDataSet), defaultLabel);
		return predictionCollection;
	}
	
	@SuppressWarnings("unchecked")
	public static MultiLabelPrediction predict(String text) throws IOException, InterruptedException
	{
		MultiLabelPrediction prediction = new MultiLabelPrediction(text);
		if(text.isEmpty())
			prediction.setLabels(defaultLabel);
		else
		{
			NoLabelMemoryDataSet dataset = new NoLabelMemoryDataSet(1);
			TextProcessor textProcessed;
			textProcessed = new TextProcessor(text);
			dataset.addEntry(textProcessed.getProcessedText(), textProcessed.getExtraFeatures());
			prediction.setLabels((List<String>) (Object) emotionClassifier.predictLabels(dataset).get(0));
		}
		return prediction;
	}
	
}
