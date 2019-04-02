/*******************************************************************************
 * Copyright (c) 2019 Edge Hill University
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.nlp.tools.core.analyzer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

import edu.emory.mathcs.nlp.component.template.node.NLPNode;
import edu.emory.mathcs.nlp.decode.NLPDecoder;

public class NLPCoreAnalyzer
{
	private NLPDecoder decoder;
	private NLPNode[] decodedText=null;
	
	private HashMap<String,Integer> stats = null;
	
	public NLPCoreAnalyzer(String text)
	{
		decoder=NLPCoreAnalyzerSingleton.getInstance().getDecoder();
		decodedText = decoder.decode(text);
	}
	
	public List<String> tokens()
	{
		List<String> tokens = new ArrayList<String>(decodedText.length);
		for(int i=1; i<decodedText.length ; i++)
		{
			tokens.add(decodedText[i].getWordForm());
		}
		return tokens;
	}
	
	public String tokenizedText()
	{
		return listAsText(tokens());
	}
	
	private String listAsText(List<String> tokens)
	{
		String text = "";
		for(int i=0; i<tokens.size() ; i++)
		{
			text=text.concat(tokens.get(i));
			if(i<tokens.size()-1)
				text=text.concat(" ");
		}
		return text;
	}
	
	public List<String> posAsList()
	{
		List<String> posText = new ArrayList<String>(decodedText.length);
		//First element, i.e. 0, is the root
		for(int i=1; i<decodedText.length ; i++)
		{
			posText.add(decodedText[i].getPartOfSpeechTag());
		}
		return posText;
	}
	
	public HashMap<String,Integer> getStats()
	{
		if(decodedText==null)
			return null;
		if(stats != null)
			return stats;
		Pattern punctuation=Pattern.compile("(\\$|:|,|\\.|````|''|-LRB-|-RRB-)");
		Pattern numbers=Pattern.compile("CD");
		Pattern others=Pattern.compile("(ADD|SYM|XX|LS)");;
		stats = new HashMap<String, Integer>(4);
		String pos;
		int punctuationCounter=0;
		int othersCounter=0;
		int numbersCounter=0;
		int wordsCounter=0;
		for(int i=1; i<decodedText.length ; i++)
		{
			pos=decodedText[i].getPartOfSpeechTag();
			if(punctuation.matcher(pos).matches())
				punctuationCounter++;
			else if(others.matcher(pos).matches())
				othersCounter++;
			else if(numbers.matcher(pos).matches())
				numbersCounter++;
			else
			{
				wordsCounter++;
			}
		}	
		stats.put("PUNCTUATION", punctuationCounter);
		stats.put("OTHERS", othersCounter);
		stats.put("NUMBERS", numbersCounter);
		stats.put("WORDS", wordsCounter);
		return stats;
	}
	
	public int getTextLength()
	{
		return decodedText.length;
	}
	
	public String posAsText()
	{
		return listAsText(posAsList());
	}
	
	public String lemmatizeAsText()
	{
    	return listAsText(lemmatizeAsList());	
	}
	
	public List<String> lemmatizeAsList()
	{
		List<String> tokens = new ArrayList<String>(decodedText.length);
		for(int i=1; i<decodedText.length ; i++)
		{
			tokens.add(decodedText[i].getLemma());
		}
		return tokens;
    	
	}
	
	public int numberOfTokens()
	{
		return decodedText.length;
	}
	
}
