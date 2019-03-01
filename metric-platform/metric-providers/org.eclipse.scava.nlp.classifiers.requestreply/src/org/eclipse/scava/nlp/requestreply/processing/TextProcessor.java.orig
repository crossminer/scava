/*******************************************************************************
 * Copyright (c) 2019 Edge Hill University
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.nlp.requestreply.processing;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.scava.nlp.requestreply.RequestReplyExternalExtraFeatures;
import org.eclipse.scava.nlp.tools.core.analyzer.NLPCoreAnalyzer;
import org.eclipse.scava.nlp.tools.other.emoticons.EmoticonConverter;
import org.eclipse.scava.nlp.tools.other.symbolconverter.SymbolConverter;
import org.eclipse.scava.nlp.tools.preprocessor.normalizer.Normalizer;

public class TextProcessor
{
	private List<Double> extraFeatures;
	private String processedText;
	
	public TextProcessor(String text, RequestReplyExternalExtraFeatures extra)
	{
		text = Normalizer.normalize(text);
		text = EmoticonConverter.transform(text);
		text = SymbolConverter.transform(text);
		
		extraFeatures = new ArrayList<Double>();
		extraFeatures=ExtraTextualFeaturesExtractor.getExtraFeatures(text);
		extraFeatures.add(extra.hasCode() ? 1.0 : 0.0);
		extraFeatures.add(extra.hadReplies() ? 1.0 : 0.0);
		
		NLPCoreAnalyzer coreAnalyzedText = new NLPCoreAnalyzer(text);
		
		processedText=TextPostProcessor.apply(coreAnalyzedText.tokenizedText());
	}
	
	public TextProcessor(String text)
	{
		
		text = EmoticonConverter.transform(text);
		text = SymbolConverter.transform(text);
		
		extraFeatures = new ArrayList<Double>();
		extraFeatures=ExtraTextualFeaturesExtractor.getExtraFeatures(text);
		extraFeatures.add(0.0);
		extraFeatures.add(0.0);
		
		NLPCoreAnalyzer coreAnalyzedText = new NLPCoreAnalyzer(text);
		
		processedText=TextPostProcessor.apply(coreAnalyzedText.lemmatizeAsText());
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
