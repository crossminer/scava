/*******************************************************************************
 * Copyright (c) 2019 Edge Hill University
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.nlp.classifiers.requestreplydetector.processing;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.eclipse.scava.nlp.tools.other.regexdataextractor.RegexDataExtractor;

class ExtraTextualFeaturesExtractor
{
	private static Pattern exclamation;
	private static Pattern question;
	private static Pattern fullStop;
	private static Pattern ellipsis;
	private static Pattern mixed_repetition1;
	private static Pattern mixed_repetition2;
	private static Pattern questionWords;
	private static Pattern thanksWords;
	private static Pattern upvoteWords;
	private static Pattern downvoteWords;
	private static Pattern reply;
	private static Pattern emoticons;
	
	static
	{
		exclamation=Pattern.compile("!"); //More than 2 ! contiguous
		question=Pattern.compile("\\?"); //More than 2 ! contiguous
		fullStop=Pattern.compile("\\.$"); //More than 2 ! contiguous
		ellipsis=Pattern.compile("…");
		
		mixed_repetition1=Pattern.compile("\\?!( ?(\\?|!))*"); //More than 2 ? contiguous
		mixed_repetition2=Pattern.compile("!\\?( ?(!|\\?))*"); //More than 2 ? contiguous
		
		questionWords=Pattern.compile("(?<!\\w)(how|what|what's|whats|when|where|who(se)?|why)(?!\\w)", Pattern.CASE_INSENSITIVE);
		thanksWords=Pattern.compile("(?<!\\w)(thanks|thank|thanx|thx|cheers)(?!\\w)", Pattern.CASE_INSENSITIVE);
		upvoteWords=Pattern.compile("(?<!\\w)upvot((e(s|d)?)|ing)(?!\\w)", Pattern.CASE_INSENSITIVE);
		downvoteWords=Pattern.compile("(?<!\\w)downvot((e(s|d)?)|ing)(?!\\w)", Pattern.CASE_INSENSITIVE);
		reply=Pattern.compile("^Re:", Pattern.CASE_INSENSITIVE);
		emoticons=Pattern.compile("(🙂|😃|😂|😉|😛|😜|❤|😗|👍|😋|🙃|☹️|😭|😮|😐|😕|💔|😡|😳|😱|👎|💩|🙄)");
	}
	
	public static List<Double> getExtraFeatures(String text)
	{
		String[] textSplit = text.split("\\h+");
		List<Double> counters = new ArrayList<Double>();
		counters.add((double) RegexDataExtractor.countPattern(reply, text));
		counters.add((double) RegexDataExtractor.countPattern(ellipsis, text));
		counters.add((double) RegexDataExtractor.countPattern(question, text));
		counters.add((double) RegexDataExtractor.countPattern(exclamation, text));
		counters.add((double) (RegexDataExtractor.countPattern(mixed_repetition1, text)+RegexDataExtractor.countPattern(mixed_repetition2, text)));
		
		
		counters.add((double) RegexDataExtractor.countTokenPattern(upvoteWords, textSplit));
		counters.add((double) RegexDataExtractor.countTokenPattern(downvoteWords, textSplit));
		counters.add((double) RegexDataExtractor.countTokenPattern(questionWords, textSplit));
		counters.add((double) RegexDataExtractor.countTokenPattern(thanksWords, textSplit));
		counters.add((double) RegexDataExtractor.countTokenPattern(emoticons, textSplit));
				
		//First token contains
		counters.add((double) RegexDataExtractor.countPattern(upvoteWords, textSplit[0]));
		counters.add((double) RegexDataExtractor.countPattern(downvoteWords, textSplit[0]));
		counters.add((double) RegexDataExtractor.countPattern(exclamation, textSplit[0]));
		counters.add((double) RegexDataExtractor.countPattern(question, textSplit[0]));
		counters.add((double) RegexDataExtractor.countPattern(ellipsis, textSplit[0]));
		counters.add((double) RegexDataExtractor.countPattern(thanksWords, textSplit[0]));
		counters.add((double) RegexDataExtractor.countPattern(questionWords, textSplit[0]));
		counters.add((double) RegexDataExtractor.countPattern(emoticons, textSplit[0]));
		
		//Last token contains
		counters.add((double) RegexDataExtractor.countPattern(upvoteWords, textSplit[textSplit.length-1]));
		counters.add((double) RegexDataExtractor.countPattern(downvoteWords, textSplit[textSplit.length-1]));
		counters.add((double) RegexDataExtractor.countPattern(exclamation, textSplit[textSplit.length-1]));
		counters.add((double) RegexDataExtractor.countPattern(question, textSplit[textSplit.length-1]));
		counters.add((double) RegexDataExtractor.countPattern(fullStop, textSplit[textSplit.length-1]));
		counters.add((double) RegexDataExtractor.countPattern(ellipsis, textSplit[textSplit.length-1]));
		counters.add((double) RegexDataExtractor.countPattern(thanksWords, textSplit[textSplit.length-1]));
		counters.add((double) RegexDataExtractor.countPattern(questionWords, textSplit[textSplit.length-1]));
		counters.add((double) RegexDataExtractor.countPattern(emoticons, textSplit[textSplit.length-1]));
		
		return counters;
	}
}
