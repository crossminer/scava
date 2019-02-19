/*******************************************************************************
 * Copyright (c) 2017 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.severityclassifier.opennlptartarus.libsvm;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FeatureIdCollection {

	private	Set<Integer> unigrams, bigrams, trigrams, quadgrams, 
						charTrigrams, charQuadgrams, charFivegrams;

	public FeatureIdCollection() {
		super();
		unigrams = new HashSet<Integer>();
		bigrams = new HashSet<Integer>();
		trigrams = new HashSet<Integer>();
		quadgrams = new HashSet<Integer>();
		charTrigrams = new HashSet<Integer>();
		charQuadgrams = new HashSet<Integer>();
		charFivegrams = new HashSet<Integer>();
	}

	public Set<Integer> getUnigrams() {
		return unigrams;
	}

	public void addUnigram(int unigram) {
		unigrams.add(unigram);
	}

	public void addUnigrams(List<Integer> unigramList) {
		for (int unigram: unigramList)
			unigrams.add(unigram);
	}

	public Set<Integer> getBigrams() {
		return bigrams;
	}

	public void addBigram(int bigram) {
		bigrams.add(bigram);
	}

	public void addBigrams(List<Integer> bigramList) {
		for (int bigram: bigramList)
			unigrams.add(bigram);
	}

	public Set<Integer> getTrigrams() {
		return trigrams;
	}

	public void addTrigram(int trigram) {
		trigrams.add(trigram);
	}

	public void addTrigrams(List<Integer> trigramList) {
		for (int trigram: trigramList)
			unigrams.add(trigram);
	}

	public Set<Integer> getQuadgrams() {
		return quadgrams;
	}

	public void addQuadgram(int quadgram) {
		quadgrams.add(quadgram);
	}

	public void addQuadgrams(List<Integer> quadgramList) {
		for (int quadgram: quadgramList)
			unigrams.add(quadgram);
	}

	public Set<Integer> getCharTrigrams() {
		return charTrigrams;
	}

	public void addCharTrigram(int charTrigram) {
		charTrigrams.add(charTrigram);
	}

	public void addCharTrigrams(List<Integer> charTrigramList) {
		for (int charTrigram: charTrigramList)
			unigrams.add(charTrigram);
	}

	public Set<Integer> getCharQuadgrams() {
		return charQuadgrams;
	}

	public void addCharQuadgram(int charQuadgram) {
		charQuadgrams.add(charQuadgram);
	}

	public void addCharQuadgrams(List<Integer> charQuadgramList) {
		for (int charQuadgram: charQuadgramList)
			unigrams.add(charQuadgram);
	}

	public Set<Integer> getCharFivegrams() {
		return charFivegrams;
	}

	public void addCharFivegram(int charFivegram) {
		charFivegrams.add(charFivegram);
	}
	
	public void addCharFivegrams(List<Integer> charFivegramList) {
		for (int charFivegram: charFivegramList)
			unigrams.add(charFivegram);
	}

}
