/*******************************************************************************
 * Copyright (c) 2019 Edge Hill University
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.nlp.tools.license.processing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NgramTokeniser {

	private List<String> ngramTokensList;
	private final int ngramSize = 3;

	public NgramTokeniser(String text) {

		ngramTokensList = textToNgrams(text, ngramSize);
	}

	
	public List<String> getNgrams() {

		return ngramTokensList;
	}

	
	private List<String> textToNgrams(String text, int maxNgrams) {
		List<String> tokens = simpleTokenizer(text);
		String ngram;
		List<String> ngrams = new ArrayList<String>();

		for (int i = 0; i < tokens.size() - (maxNgrams - 1); i++) {
			ngram = "";
			for (int j = 0; j < maxNgrams; j++) {
				ngram += tokens.get(i + j);
				if (j != maxNgrams - 1)
					ngram += " ";
			}
			ngrams.add(ngram);
		}
		return ngrams;
	}
	
	
	private List<String> simpleTokenizer(String text) {
		List<String> tokens = new ArrayList<String>(Arrays.asList(text.split("\\s|\\p{P}")));
		tokens.removeIf(token -> token.equals(""));
		return tokens;
	}
	
	
	
}