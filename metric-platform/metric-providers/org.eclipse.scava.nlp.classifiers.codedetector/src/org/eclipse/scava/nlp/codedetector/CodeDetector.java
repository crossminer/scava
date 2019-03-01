/*******************************************************************************
 * Copyright (C) 2018 Edge Hill University
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.nlp.codedetector;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.scava.nlp.codedetector.processing.CodeDetectorFormater;
import org.eclipse.scava.nlp.tools.predictions.singlelabel.SingleLabelPrediction;
import org.eclipse.scava.nlp.tools.predictions.singlelabel.SingleLabelPredictionCollection;
import org.eclipse.scava.nlp.tools.preprocessor.normalizer.Normalizer;

import cc.fasttext.FastText;
import cc.fasttext.Vector;

public class CodeDetector
{
	private static FastText codeDetector;
	
	/** The {@code defaultLabel} is the label that it is used when a string, after formatting it for the Code Detector, is empty.
	 * Thus, the Code Detector will be unable to determine a label. The default value is <i>__label__English</i>, as many of the cases may
	 * occur in English contexts. In theory, if the pre-processing tool was used previously, the only case were it is going to be used is a string composed
	 * uniquely of a comma or a set of it, e.g. "," or ",,,,,,,".
	 */
	private static String defaultLabel="__label__English";

	static
	{
		codeDetector = CodeDetectorSingleton.getInstance().getCodeDetector();
	}
	
	private static String formatter(String input)
	{
		input=Normalizer.normalize(input);
		return CodeDetectorFormater.apply(input);
	}
	
	public static void printWordVector(String input)
	{
		input=formatter(input);
		Vector vec = codeDetector.getWordVector(input);
        System.out.println(input + " " + vec);
	}
	
	public static void printSentenceVector(String input)
	{
		Vector vec;
		try
		{
			input=formatter(input);
			vec = codeDetector.getSentenceVector(input);
			System.out.println(input + " " + vec);
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Predicts whether a text is Code or English.
	 * @param text Input to analyze.
     * @return {@link Prediction}, where it is kept the input text, 
     * its label (<i>__label__Code</i> or <i>__label__English</i>), and label's probabilities (float).
     * 
     * <br><b>Note:</b> In the case that {@code text} is an empty string after being formatted for the Code Detector,
     * it will return a {@link CodeDetector#defaultLabel}. The {@code Prediction}, beside having the default label,
     * can be recognized as well for having a probability of -1.0.
	 */
	public static SingleLabelPrediction predict(String text)
	{
		return predict(text, defaultLabel);
	}
	
	
	/**
	 * Predicts whether a text is Code or English.
	 * @param text Input to analyze.
	 * @param defaultLabel In the case that {@code text} is an empty string, this method returns a {@link Prediction} with
	 * the label defined in {@code defaultLabel} and a probability of -1.0.
	 * @return {@link Prediction}, where it is kept the input text, 
     * its label (<i>__label__Code</i> or <i>__label__English</i>), and label's probabilities (float).
     */
	public static SingleLabelPrediction predict(String text, String defaultLabel)
	{
		SingleLabelPrediction prediction = new SingleLabelPrediction(text);
		prediction.setLabel(predictGeneral(text, defaultLabel));
		return prediction;
	}
	
	/**
	 * Predicts for each element of a list of texts whether it is Code or English.
	 * @param textList Input to analyze.
     * @return {@code List<Prediction>}, where it is kept, for each entry of <b>textList</b>, the input text, 
     * its label (<i>__label__Code</i> or <i>__label__English</i>), and label's probabilities (float).
     * 
     * * <br><b>Note:</b> In the case that and element of {@code textList} is an empty string after being formatted for the Code Detector,
     * it will return a {@link CodeDetector#defaultLabel}. The {@code Prediction}, beside having the default label,
     * can be recognized as well for having a probability of -1.0.
     * 
     * @see Prediction
	 */
	public static SingleLabelPredictionCollection predict(List <String> textList)
	{
		return predict(textList, defaultLabel);
	}
	
	/**
	 * Predicts for each element of a list of texts whether it is Code or English.
	 * @param textList Input to analyze.
	 * @param defaultLabel In the case an element of {@code textList} is an empty string, this method returns a {@link Prediction} with
	 * the label defined in {@code defaultLabel} and a probability of -1.0.
	 * @return {@code List<Prediction>}, where it is kept, for each entry of <b>textList</b>, the input text, 
     * its label (<i>__label__Code</i> or <i>__label__English</i>), and label's probabilities (float).
	 */
	public static SingleLabelPredictionCollection predict(List <String> textList, String defaultLabel)
	{
		SingleLabelPredictionCollection predictionCollection = new SingleLabelPredictionCollection(textList.size());
		List<Object> predictedLabels = new ArrayList<Object>(textList.size());
		//It must be forEachOrdered otherwise, the output may not keep the same input order
		textList.stream().forEachOrdered(text->{
												predictionCollection.addText(text);
												predictedLabels.add(predictGeneral(text, defaultLabel));
		});
		predictionCollection.setPredictions(predictedLabels);
		return predictionCollection;
	}
	
	public static SingleLabelPredictionCollection predict(SingleLabelPredictionCollection textCollection)
	{
		return predict(textCollection, defaultLabel);
	}
	
	public static SingleLabelPredictionCollection predict(SingleLabelPredictionCollection predictionCollection, String defaultLabel)
	{
		List<Object> predictedLabels = new ArrayList<Object>(predictionCollection.size());
		for(String text: predictionCollection.getTextCollection())
		{
			predictedLabels.add(predictGeneral(text, defaultLabel));
		}
		predictionCollection.setPredictions(predictedLabels);
		return predictionCollection;
	}
	
	private static String predictGeneral(String text, String defaultLabel)
	{
		String formattedText=formatter(text);
		if(formattedText.isEmpty())
			return defaultLabel;
		//The new line is added in order to have the same output that the C++ version. In fact the new line character is used to predict unseen words.
		formattedText += "\n";
		return (String) codeDetector.predictLine(formattedText, 1).keySet().toArray()[0];
	}
}
