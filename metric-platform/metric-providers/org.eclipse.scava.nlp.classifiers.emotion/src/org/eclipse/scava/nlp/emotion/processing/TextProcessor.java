/*******************************************************************************
 * Copyright (c) 2019 Edge Hill University
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.nlp.emotion.processing;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.scava.nlp.resources.sentinet5.SenticNet5;
import org.eclipse.scava.nlp.tools.core.analyzer.NLPCoreAnalyzer;
import org.eclipse.scava.nlp.tools.other.emoticons.EmoticonConverter;
import org.eclipse.scava.nlp.tools.other.symbolconverter.SymbolConverter;
import org.eclipse.scava.nlp.tools.preprocessor.normalizer.Normalizer;

public class TextProcessor
{
	private List<Double> extraFeatures;
	private String processedText;
	
	public TextProcessor(String text)
	{
		text = Normalizer.normalize(text);
		text = EmoticonConverter.transform(text);
		text = SymbolConverter.transform(text);
		
		extraFeatures = new ArrayList<Double>();
		extraFeatures=ExtraTextualFeaturesExtractor.getExtraFeatures(text);
		
		NLPCoreAnalyzer coreAnalyzedText = new NLPCoreAnalyzer(text);
		
		extraFeatures.addAll(ExtraSenticNetFeatures.getFeatures(SenticNet5.analyzeTextAndSummaryScores(coreAnalyzedText)));
		
		processedText=TextPostProcessor.apply(coreAnalyzedText.tokenizedText());
	}
	
	public String getProcessedText()
	{
		return processedText;
	}
	
	public List<Double> getExtraFeatures()
	{
		return extraFeatures;
	}
	
	
}
