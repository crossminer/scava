/*******************************************************************************
 * Copyright (c) 2017 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.sentimentclassifier.opennlptartarus.libsvm.featuretools;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.scava.sentimentclassifier.opennlptartarus.libsvm.ClassificationInstance;

import uk.ac.nactem.posstemmer.Token;

public class Negation {

	private static String[] singleWordNegs = { "never", "no", "nothing", "nowhere", "noone",
											   "none", "not", "havent", "hasnt", "hadnt", 
											   "cant", "couldnt", "shouldnt", "wont", 
											   "wouldnt", "dont", "doesnt", "didnt", "isnt", 
											   "arent", "aint " };

	private static String[] firstWordNegs = { "haven", "hasn", "hadn", "can", "couldn", 
											  "shouldn", "won", "wouldn", "don", "doesn", 
											  "didn", "isn", "aren", "ain" };

	private static String[] endOfNegs = { ".", ":", ";", "!", "?" };

	private static String secondWordNeg = "'";
	
	private static String thirdWordNegs = "t";
	
	private static Set<String> singleWordNegSet, firstWordNegSet, endOfNegSet; 
	
	public Negation() {
		super();
		singleWordNegSet = new HashSet<String>();
		for (String word: singleWordNegs)
			singleWordNegSet.add(word);
		firstWordNegSet = new HashSet<String>();
		for (String word: firstWordNegs)
			firstWordNegSet.add(word);
		endOfNegSet = new HashSet<String>();
		for (String word: endOfNegs)
			endOfNegSet.add(word);
	}

	public static boolean isNegation(Token token) {
		return singleWordNegSet.contains(token.getSurfaceForm().toLowerCase());
	}

	public static boolean isNegation(Token beforePreviousToken, Token previousToken, Token token) {
		if (firstWordNegSet.contains(beforePreviousToken.getSurfaceForm().toLowerCase()) &&
				previousToken.getSurfaceForm().toLowerCase().equals(secondWordNeg) &&
					token.getSurfaceForm().toLowerCase().equals(thirdWordNegs))
			return true;
		return false;
	}
	
	public static boolean isEndOfNegation(Token token) {
		return endOfNegSet.contains(token.getSurfaceForm().toLowerCase());
	}

//	public static void update(Map<String, Tweet> tweetContentMap) {
//		for (Tweet tweet: tweetContentMap.values())
//			for (List<Token> sentence: tweet.getTokens())
//				update(sentence);
//	}

//	public static void update(ThreadMessages threadMessages) {
//		for (Thread thread: threadMessages.getThreadIdsThreads().values())
//			for (Message message: thread.getMessageList())
//				for (List<Token> sentence: message.getTokens())
//					update(sentence);
//	}

	public static void update(ClassificationInstance classificationInstance) {
		for (List<Token> sentence: classificationInstance.getCleanTokenSentences())
			update(sentence);
	}

	public static int update(List<Token> sentence) {
		int counter = 0;
		boolean negation = false;
		for (int index = 0; index<sentence.size(); index++) {
			Token token = sentence.get(index);
			if (isEndOfNegation(token)) 
				negation = false; 
			token.setNegation(negation);
			if (isNegation(token)) {
				if (!negation) counter++;
				negation = true;
			}
			else if (index>1) {
				Token beforePreviousToken = sentence.get(index-2);
				Token previousToken = sentence.get(index-1);
				if (isNegation(beforePreviousToken, previousToken, token))
					negation = true;
			}
		}
		return counter;
	}

}
