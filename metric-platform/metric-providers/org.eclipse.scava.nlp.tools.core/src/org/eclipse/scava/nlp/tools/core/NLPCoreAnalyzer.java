/*******************************************************************************
 * Copyright (c) 2019 Edge Hill University
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.nlp.tools.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

import edu.emory.mathcs.nlp.component.template.node.NLPNode;
import edu.emory.mathcs.nlp.component.tokenizer.token.Token;
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
	
	public int numberOfTokens()
	{
		return decodedText.length;
	}
	
	
	public List<String> getTokens()
	{
		List<String> tokens = new ArrayList<String>(decodedText.length);
		for(int i=1; i<decodedText.length ; i++)
		{
			tokens.add(decodedText[i].getWordForm());
		}
		return tokens;
	}
	
	public String getTokenizedText()
	{
		return listAsText(getTokens());
	}
	
	public List<String> getPOS()
	{
		List<String> posText = new ArrayList<String>(decodedText.length);
		//First element, i.e. 0, is the root
		for(int i=1; i<decodedText.length ; i++)
		{
			posText.add(decodedText[i].getPartOfSpeechTag());
		}
		return posText;
	}
	
	public String getPOSText()
	{
		return listAsText(getPOS());
	}
	
	public String getLemmatizedText()
	{
    	return listAsText(getLemmas());	
	}
	
	public List<String> getLemmas()
	{
		List<String> tokens = new ArrayList<String>(decodedText.length);
		for(int i=1; i<decodedText.length ; i++)
		{
			tokens.add(decodedText[i].getLemma());
		}
		return tokens;
    	
	}
	
	public List<String> getTokenizedSentencesAsText()
	{
		return doubleListToSingleList(getTokenizedSentences());
	}
	
	public List<String> getLemmatizedSentencesAsText()
	{
		return doubleListToSingleList(getLemmatizedSentences());	
	}
	
	public List<String> getPOSSentencesAsText()
	{
		return doubleListToSingleList(getPOSSentences());
	}
	
	
	
	public List<List<String>> getTokenizedSentences()
	{
		List<Token> nodes = new ArrayList<Token>();
		for(String token: getTokens())
			nodes.add(new Token(token));
		return sentencesToString(decoder.getTokenizer().segmentize(nodes));
		
	}
	
	public List<List<String>> getLemmatizedSentences()
	{
		List<Token> nodes = new ArrayList<Token>();
		for(String token: getLemmas())
			nodes.add(new Token(token));
		return sentencesToString(decoder.getTokenizer().segmentize(nodes));
	} 
	
	public List<List<String>> getPOSSentences()
	{
		List<Token> nodes = new ArrayList<Token>();
		for(String token: getPOS())
			nodes.add(new Token(token));
		return sentencesToString(decoder.getTokenizer().segmentize(nodes));
	}
	
	private List<List<String>> sentencesToString(List<List<Token>> sentences)
	{
		List<List<String>> sentencesAsString = new ArrayList<List<String>>(sentences.size());
		for(List<Token> tokens : sentences)
		{
			List<String> tokensAsString = new ArrayList<String>(tokens.size());
			for(Token token: tokens)
				tokensAsString.add(token.getWordForm());
			sentencesAsString.add(tokensAsString);
		}
		return sentencesAsString;
	}
	

	
	public HashMap<String,Integer> getStats()
	{
		if(decodedText==null)
			return null;
		if(stats != null)
			return stats;
		Pattern punctuation=Pattern.compile("(\\$|:|,|\\.|``|''|-LRB-|-RRB-)");
		Pattern numbers=Pattern.compile("CD");
		Pattern others=Pattern.compile("(ADD|SYM|XX|LS)");
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
	
	private List<String> doubleListToSingleList(List<List<String>> unitList)
	{
		List<String> output = new ArrayList<String>(unitList.size());
		for(List<String> units : unitList)
		{
			output.add(listAsText(units));
		}
		return output;
	}
	
	private String listAsText(List<String> units)
	{
		String text = "";
		for(int i=0; i<units.size() ; i++)
		{
			text=text.concat(units.get(i));
			if(i<units.size()-1)
				text=text.concat(" ");
		}
		return text;
	}
	
}
