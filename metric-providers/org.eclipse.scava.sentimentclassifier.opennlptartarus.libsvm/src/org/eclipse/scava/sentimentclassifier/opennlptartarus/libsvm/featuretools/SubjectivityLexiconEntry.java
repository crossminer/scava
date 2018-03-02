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

public class SubjectivityLexiconEntry {

	private String subjectivityType;
	private String word;
	private String partOfSpeech;
	private String priorPolarity;
	private boolean stemmed;

	public SubjectivityLexiconEntry(String word) {
		super();
		this.word = word;
	}

	public boolean matchesPoS(String externaPoS) {
		if ( externaPoS.startsWith("N") && partOfSpeech.equals("noun") )
			return true;
		else if ( externaPoS.startsWith("V") && partOfSpeech.equals("verb") )
			return true;
		else if ( externaPoS.startsWith("J") && partOfSpeech.equals("adj") )
			return true;
		else if ( externaPoS.startsWith("RB") && partOfSpeech.equals("adverb") )
			return true;
		else if ( partOfSpeech.equals("anypos") )
			return true;
		return false;
	}
	
	public String getSubjectivityType() {
		return subjectivityType;
	}

	public void setSubjectivityType(String subjectivityType) {
		this.subjectivityType = subjectivityType;
	}

	public String getPartOfSpeech() {
		return partOfSpeech;
	}

	public void setPartOfSpeech(String partOfSpeech) {
		this.partOfSpeech = partOfSpeech;
	}

	public String getPriorPolarity() {
		return priorPolarity;
	}

	public void setPriorPolarity(String priorPolarity) {
		this.priorPolarity = priorPolarity;
	}

	public boolean isStemmed() {
		return stemmed;
	}

	public void setStemmed(boolean stemmed) {
		this.stemmed = stemmed;
	}

	public String getWord() {
		return word;
	}

}
