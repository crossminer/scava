/*******************************************************************************
 * Copyright (c) 2019 Edge Hill University
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.nlp.classifiers.sentimentanalyzer.processing;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.eclipse.scava.nlp.tools.other.regexdataextractor.RegexDataExtractor;


class ExtraTextualFeaturesExtractor
{
	
	private static Pattern uppercase_wods;
	private static Pattern exclamation;
	private static Pattern question;
	private static Pattern fullStop;
	private static Pattern ellipsis;
	private static Pattern exclamation_repeatition;
	private static Pattern question_repetition;
	private static Pattern mixed_repetition1;
	private static Pattern mixed_repetition2;
	private static Pattern elongated_words;
	private static Pattern positiveEmoticons;
	private static Pattern negativeEmoticons;
	private static Pattern consecutivePositiveEmoticons;
	private static Pattern consecutiveNegativeEmoticons;
	private static Pattern negation1;
	private static Pattern negation2;
	private static Pattern questionWords;
	
	static
	{
		uppercase_wods=Pattern.compile("\\p{Lu}\\p{Lu}+"); //More than 2 uppercase contiguous
		
		exclamation=Pattern.compile("!"); //More than 2 ! contiguous
		question=Pattern.compile("\\?"); //More than 2 ! contiguous
		fullStop=Pattern.compile("\\.$"); //More than 2 ! contiguous
		ellipsis=Pattern.compile("…");
		
		exclamation_repeatition=Pattern.compile("!( ?!)+"); //More than 2 ! contiguous
		question_repetition=Pattern.compile("\\?( ?\\?)+"); //More than 2 ? contiguous
		
		mixed_repetition1=Pattern.compile("\\?!( ?(\\?|!))*"); //More than 2 ? contiguous
		mixed_repetition2=Pattern.compile("!\\?( ?(!|\\?))*"); //More than 2 ? contiguous
		
		elongated_words=Pattern.compile("(\\p{L})\\1{2,}"); //More than 3 letters contiguous
		
		positiveEmoticons= Pattern.compile("(🙂|😃|😂|😉|😛|😜|❤|😗|👍|😋|🙃)");
		negativeEmoticons= Pattern.compile("(☹️|😭|😮|😐|😕|💔|😡|😳|😱|👎|💩|🙄)");
		
		consecutivePositiveEmoticons=Pattern.compile("[🙂😃😂😉😛😜❤😗👍😋🙃]( ?[🙂😃😂😉😛😜❤😗👍😋🙃])+");
		consecutiveNegativeEmoticons=Pattern.compile("[☹️😭😮😐😕💔😡😳😱👎💩🙄]( ?[☹️😭😮😐😕💔😡😳😱👎💩🙄])+");
		
		negation1=Pattern.compile("(?<!\\w)(not|cannot|n't|never|without)(?!\\w)", Pattern.CASE_INSENSITIVE);
		negation2=Pattern.compile("(?<=\\w)(n't)(?!\\w)", Pattern.CASE_INSENSITIVE);
		
		questionWords=Pattern.compile("(?<!\\w)(how|what|what's|whats|when|where|who|why)(?!\\w)", Pattern.CASE_INSENSITIVE);
		
	}
	
	public static List<Double> getExtraFeatures(String text)
	{
		String[] textSplit = text.split("\\h+");
		List<Double> counters = new ArrayList<Double>();
		
		counters.add((double) RegexDataExtractor.countPattern(consecutivePositiveEmoticons, text));
		counters.add((double) RegexDataExtractor.countPattern(consecutiveNegativeEmoticons, text));
		counters.add((double) RegexDataExtractor.countPattern(exclamation_repeatition, text));
		counters.add((double) RegexDataExtractor.countPattern(question_repetition, text));
		counters.add((double) RegexDataExtractor.countPattern(ellipsis, text));
		counters.add((double) RegexDataExtractor.countPattern(question, text));
		counters.add((double) RegexDataExtractor.countPattern(exclamation, text));
		counters.add((double) (RegexDataExtractor.countPattern(mixed_repetition1, text)+RegexDataExtractor.countPattern(mixed_repetition2, text)));
		
		
		counters.add((double) RegexDataExtractor.countTokenPattern(questionWords, textSplit));
		counters.add((double) RegexDataExtractor.countTokenPattern(positiveEmoticons, textSplit));
		counters.add((double) RegexDataExtractor.countTokenPattern(negativeEmoticons, textSplit));
		counters.add((double) RegexDataExtractor.countTokenPattern(uppercase_wods, textSplit));
		counters.add((double) RegexDataExtractor.countTokenPattern(elongated_words, textSplit));
		counters.add((double) (RegexDataExtractor.countTokenPattern(negation1, textSplit)+RegexDataExtractor.countTokenPattern(negation2, textSplit)));
		
		
		//First token contains
		counters.add((double) RegexDataExtractor.countPattern(positiveEmoticons, textSplit[0]));
		counters.add((double) RegexDataExtractor.countPattern(negativeEmoticons, textSplit[0]));
		counters.add((double) RegexDataExtractor.countPattern(exclamation, textSplit[0]));
		counters.add((double) RegexDataExtractor.countPattern(question, textSplit[0]));
		counters.add((double) RegexDataExtractor.countPattern(ellipsis, textSplit[0]));
		
		//Last token contains
		counters.add((double) RegexDataExtractor.countPattern(positiveEmoticons, textSplit[textSplit.length-1]));
		counters.add((double) RegexDataExtractor.countPattern(negativeEmoticons, textSplit[textSplit.length-1]));
		counters.add((double) RegexDataExtractor.countPattern(exclamation, textSplit[textSplit.length-1]));
		counters.add((double) RegexDataExtractor.countPattern(question, textSplit[textSplit.length-1]));
		counters.add((double) RegexDataExtractor.countPattern(fullStop, textSplit[textSplit.length-1]));
		counters.add((double) RegexDataExtractor.countPattern(ellipsis, textSplit[textSplit.length-1]));
		return counters;
	}
}
