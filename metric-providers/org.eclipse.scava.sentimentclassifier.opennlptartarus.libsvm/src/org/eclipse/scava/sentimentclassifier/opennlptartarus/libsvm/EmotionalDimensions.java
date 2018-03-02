/*******************************************************************************
 * Copyright (c) 2017 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.sentimentclassifier.opennlptartarus.libsvm;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.scava.sentimentclassifier.opennlptartarus.libsvm.featuretools.SentimentAnalysisLexica;
import org.eclipse.scava.sentimentclassifier.opennlptartarus.libsvm.featuretools.SentimentAnalysisLexicaSingleton;

import uk.ac.nactem.posstemmer.Token;

public class EmotionalDimensions {

	private static String[] dimensions = { "anger", "anticipation", "disgust", "fear", 
											"joy", "sadness", "surprise", "trust" };

	public static String getDimensions(ClassificationInstance classificationInstance) {
		Set<String> dimesion_set = new HashSet<String>();
		for (List<Token> sentence: classificationInstance.getCleanTokenSentences())
			for (Token token: sentence)
				dimesion_set.addAll(getEmotionalDimensions(token.getNormalForm()));
		List<String> sortedList = asSortedList(dimesion_set);
		String sortedString = "";
		for (String item: sortedList) {
			if (sortedString.length()>0) 
				sortedString += ", ";
			sortedString += item;
		}
		return sortedString;
	}

	private static List<String> getEmotionalDimensions(String lemma) {
		SentimentAnalysisLexica sentimentAnalysisLexica =
				SentimentAnalysisLexicaSingleton.getInstance().getLexica();
		List<String> dimesion_list = new ArrayList<String>();
		for (String dimension: dimensions)
			if (sentimentAnalysisLexica.isInNRCLexicon(dimension, lemma))
				dimesion_list.add(dimension);
		return dimesion_list;
	}
	
	public static <T extends Comparable<? super T>> List<T> asSortedList(Set<T> c) {
	  List<T> list = new ArrayList<T>(c);
	  java.util.Collections.sort(list);
	  return list;
	}
	
}
