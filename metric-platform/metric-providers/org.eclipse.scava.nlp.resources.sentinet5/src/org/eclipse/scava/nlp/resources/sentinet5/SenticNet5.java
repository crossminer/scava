/*******************************************************************************
 * Copyright (c) 2018 Edge Hill University
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.nlp.resources.sentinet5;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.scava.nlp.tools.core.analyzer.NLPCoreAnalyzer;
import org.eclipse.scava.nlp.tools.other.ngrams.NgramsGenerator;

public class SenticNet5
{
	private static HashMap<String, HashMap<String, Double>> dictionary;
	private static SenticNet5Singleton singleton;
	
	static
	{
		singleton=SenticNet5Singleton.getInstance();
		dictionary=singleton.getDictionary();
	}
	
	/**
	 * This method analyze the text using n-grams (from 1 to 5) and skip-bigrams (from 1 to 3)
	 * @param coreAnalyzedText
	 * @return
	 */
	public static List<Map.Entry<String,HashMap<String, Double>>> analyzeText(NLPCoreAnalyzer coreAnalyzedText)
	{
		List<Map.Entry<String,HashMap<String, Double>>> analyzedText = new ArrayList<Map.Entry<String,HashMap<String, Double>>>();
		HashMap<String, Double> values=null;
		Map.Entry<String, HashMap<String, Double>> token=null;
		List<String> tokens = coreAnalyzedText.lemmatizeAsList();
		List<String> ngramsList = new ArrayList<String>(coreAnalyzedText.numberOfTokens()); //At least the size will be the number of tokens
		ngramsList.addAll(tokens);
		ngramsList.addAll(NgramsGenerator.ngramsGenerator(tokens, 5));
		ngramsList.addAll(NgramsGenerator.skipBigramsGenerator(tokens, 3));
		
		for(String ngram : ngramsList)
		{

			values = SenticNet5.analyzeWord(ngram);
			if(values==null)
				continue;
			token = new AbstractMap.SimpleEntry<String, HashMap<String, Double>>(ngram, values);
			analyzedText.add(token);
		}	
		return analyzedText;
	}
	
	/**
	 * 
	 * @param lemmatizedWord: A word in this sense can be a unigram up to a 5-gram
	 * @return
	 */
	public static HashMap<String, Double> analyzeWord(String lemmatizedWord)
	{
		return dictionary.get(lemmatizedWord);
	}
	
	public static HashMap<String,Double> analyzeTextAndSummaryScores(NLPCoreAnalyzer coreAnalyzedText)
	{
		return summaryScores(analyzeText(coreAnalyzedText));
	}
	
	public static HashMap<String,Double> summaryScores(List<Map.Entry<String, HashMap<String,Double>>> senticnetAnalyzedText)
	{
		HashMap<String,Double> globalScore = new HashMap<String,Double>(29);
		double currentPolarity;
		
		double pos=0.0;
		double countPos=0.0;
		double maxPos=0.0;
		double lastPos=0.0;
		
		double neg=0.0;
		double countNeg=0.0;
		double maxNeg=0.0;
		double lastNeg=0.0;
		
		double pleasantness=0.0;
		double countPleasantness=0.0;
		double currentPleasantness;
		double maxPleasantness=0.0;
		double minPleasantness=0.0;
		double lastPleasantness=0.0;
		
		double attention=0.0;
		double countAttention=0.0;
		double currentAttention;
		double maxAttention=0.0;
		double minAttention=0.0;
		double lastAttention=0.0;
		
		double sensitivity=0.0;
		double countSensitivity=0.0;
		double currentSensitivity;
		double maxSensitivity=0.0;
		double minSensitivity=0.0;
		double lastSensitivity=0.0;
		
		double aptitude=0.0;
		double countAptitude=0.0;
		double currentAptitude;
		double maxAptitude=0.0;
		double minAptitude=0.0;
		double lastAptitude=0.0;
		
		double total=0.0;
		
		for(Map.Entry<String, HashMap<String,Double>> entrySentiment : senticnetAnalyzedText)
		{
			currentPleasantness=entrySentiment.getValue().get("Pleasantness");
			currentAttention=entrySentiment.getValue().get("Attention");
			currentSensitivity=entrySentiment.getValue().get("Sensitivity");
			currentAptitude=entrySentiment.getValue().get("Aptitude");
			currentPolarity=entrySentiment.getValue().get("Polarity");
			
			if(currentPolarity>0.0)
			{
				countPos++;
				maxPos=Math.max(maxPos, currentPolarity);
				pos+=currentPolarity;
				lastPos=currentPolarity;
			}
			if(currentPolarity<0.0)
			{
				countNeg++;
				maxNeg=Math.max(maxNeg, currentPolarity);
				neg+=currentPolarity;
				lastNeg=currentPolarity;
			}
			if(currentPleasantness!=0.0)
			{
				countPleasantness++;
				maxPleasantness=Math.max(maxPleasantness, currentPleasantness);
				minPleasantness=Math.min(minPleasantness, currentPleasantness);
				pleasantness+=currentPleasantness;
				lastPleasantness=currentPleasantness;
			}
			if(currentAttention!=0.0)
			{
				countAttention++;
				maxAttention=Math.max(maxAttention, currentAttention);
				minAttention=Math.min(minAttention, currentAttention);
				attention+=currentAttention;
				lastAttention=currentAttention;
			}
			if(currentSensitivity!=0.0)
			{
				countSensitivity++;
				maxSensitivity=Math.max(maxSensitivity, currentSensitivity);
				minSensitivity=Math.min(minSensitivity, currentSensitivity);
				sensitivity+=currentSensitivity;
				lastSensitivity=currentSensitivity;
			}
			if(currentAptitude!=0.0)
			{
				countAptitude++;
				maxAptitude=Math.max(maxAptitude, currentAptitude);
				minAptitude=Math.min(minAptitude, currentAptitude);
				aptitude+=currentAptitude;
				lastAptitude=currentAptitude;
			}
				
			total++;
		}

		globalScore.put("pos", pos);
		globalScore.put("maxPos", maxPos);
		globalScore.put("totalPos", countPos);
		globalScore.put("lastPos",lastPos);
		
		globalScore.put("neg", -1*neg);
		globalScore.put("maxNeg",-1*maxNeg);
		globalScore.put("totalNeg",countNeg);
		globalScore.put("lastNeg",-1*lastNeg);
		
		globalScore.put("pleasantness", pleasantness);
		globalScore.put("maxPleasantness",maxPleasantness);
		globalScore.put("minPleasantness",minPleasantness);
		globalScore.put("totalPleasantness",countPleasantness);
		globalScore.put("lastPleasantness",lastPleasantness);
		
		globalScore.put("attention", attention);
		globalScore.put("maxAttention",maxAttention);
		globalScore.put("minAttention",minAttention);
		globalScore.put("totalAttention",countAttention);
		globalScore.put("lastAttention",lastAttention);
		
		globalScore.put("sensitivity", sensitivity);
		globalScore.put("maxSensitivity",maxSensitivity);
		globalScore.put("minSensitivity",minSensitivity);
		globalScore.put("totalSensitivity",countSensitivity);
		globalScore.put("lastSensitivity",lastSensitivity);
		
		globalScore.put("aptitude", aptitude);
		globalScore.put("maxAptitude",maxAptitude);
		globalScore.put("minAptitude",minAptitude);
		globalScore.put("totalAptitude",countAptitude);
		globalScore.put("lastAptitude",lastAptitude);
		
		
		globalScore.put("total",total);
		return globalScore;
		
	}
	
	public static List<String> getLexicon()
	{
		return singleton.getPolarityLexicon();
	}
	
	public static List<String> getPosLexicon()
	{
		return singleton.getPositivePolarityLexicon();
	}
	
	public static List<String> getNegLexicon()
	{
		return singleton.getNegativePolarityLexicon();
	}
	
}
