/*******************************************************************************
 * Copyright (c) 2019 Edge Hill University
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.nlp.classifiers.sentiment.processing;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.eclipse.scava.nlp.tools.other.featuresextractor.RegexFeaturesExtractor;


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
		
		counters.add((double) RegexFeaturesExtractor.findPattern(consecutivePositiveEmoticons, text));
		counters.add((double) RegexFeaturesExtractor.findPattern(consecutiveNegativeEmoticons, text));
		counters.add((double) RegexFeaturesExtractor.findPattern(exclamation_repeatition, text));
		counters.add((double) RegexFeaturesExtractor.findPattern(question_repetition, text));
		counters.add((double) RegexFeaturesExtractor.findPattern(ellipsis, text));
		counters.add((double) RegexFeaturesExtractor.findPattern(question, text));
		counters.add((double) RegexFeaturesExtractor.findPattern(exclamation, text));
		counters.add((double) (RegexFeaturesExtractor.findPattern(mixed_repetition1, text)+RegexFeaturesExtractor.findPattern(mixed_repetition2, text)));
		
		
		counters.add((double) RegexFeaturesExtractor.findPattern(questionWords, textSplit));
		counters.add((double) RegexFeaturesExtractor.findPattern(positiveEmoticons, textSplit));
		counters.add((double) RegexFeaturesExtractor.findPattern(negativeEmoticons, textSplit));
		counters.add((double) RegexFeaturesExtractor.findPattern(uppercase_wods, textSplit));
		counters.add((double) RegexFeaturesExtractor.findPattern(elongated_words, textSplit));
		counters.add((double) (RegexFeaturesExtractor.findPattern(negation1, textSplit)+RegexFeaturesExtractor.findPattern(negation2, textSplit)));
		
		
		//First token contains
		counters.add((double) RegexFeaturesExtractor.findPattern(positiveEmoticons, textSplit[0]));
		counters.add((double) RegexFeaturesExtractor.findPattern(negativeEmoticons, textSplit[0]));
		counters.add((double) RegexFeaturesExtractor.findPattern(exclamation, textSplit[0]));
		counters.add((double) RegexFeaturesExtractor.findPattern(question, textSplit[0]));
		counters.add((double) RegexFeaturesExtractor.findPattern(ellipsis, textSplit[0]));
		
		//Last token contains
		counters.add((double) RegexFeaturesExtractor.findPattern(positiveEmoticons, textSplit[textSplit.length-1]));
		counters.add((double) RegexFeaturesExtractor.findPattern(negativeEmoticons, textSplit[textSplit.length-1]));
		counters.add((double) RegexFeaturesExtractor.findPattern(exclamation, textSplit[textSplit.length-1]));
		counters.add((double) RegexFeaturesExtractor.findPattern(question, textSplit[textSplit.length-1]));
		counters.add((double) RegexFeaturesExtractor.findPattern(fullStop, textSplit[textSplit.length-1]));
		counters.add((double) RegexFeaturesExtractor.findPattern(ellipsis, textSplit[textSplit.length-1]));
		return counters;
	}
}
