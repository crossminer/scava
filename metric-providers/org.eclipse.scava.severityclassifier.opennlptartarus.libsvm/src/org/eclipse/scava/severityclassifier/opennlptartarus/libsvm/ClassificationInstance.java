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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.eclipse.scava.metricprovider.trans.newsgroups.threads.model.ArticleData;
import org.eclipse.scava.platform.delta.communicationchannel.CommunicationChannelArticle;

import uk.ac.nactem.posstemmer.OpenNlpTartarusSingleton;
import uk.ac.nactem.posstemmer.Token;

public class ClassificationInstance {
	
	private String bugTrackerId;
	private String bugId;
	
	private String newsgroupName;
	private int threadId;
	private String subject;
	private List<List<Token>> tokenSentences;
	private List<List<Token>> cleanTokenSentences;

//	private String text;
//	private String cleanText;
	private String composedId;
	
	private Set<String> unigrams, bigrams, trigrams, quadgrams, 
						charTrigrams, charQuadgrams, charFivegrams;

	private List<String> sortedUnigrams, sortedBigrams, 
						 sortedTrigrams, sortedQuadgrams, 
						 sortedCharTrigrams, sortedCharQuadgrams, sortedCharFivegrams;
	
	public ClassificationInstance(ClassifierMessage classifierMessage) {
		initialiseNGrams();
		initialiseMetadata(classifierMessage);
		addText(classifierMessage.getText()); 
	}

	public ClassificationInstance(FeatureGenerator featureGenerator, 
								  ClassifierMessage classifierMessage,
								  FeatureIdCollection featureIdCollection) {
		initialiseNGrams();
		initialiseMetadata(classifierMessage);
		addText(classifierMessage.getText()); 
		updateFeatureIds(featureGenerator, featureIdCollection);
	}

	public ClassificationInstance(FeatureGenerator featureGenerator,
								  ArticleData articleData, 
								  int threadId,
								  FeatureIdCollection featureIdCollection) {
		initialiseNGrams();
		initialiseMetadata(articleData, threadId);
		updateFeatureIds(featureGenerator, featureIdCollection);
	}

	public ClassificationInstance(String url,
								  CommunicationChannelArticle deltaArticle, 
								  int threadId) {
		initialiseNGrams();
		initialiseMetadata(url, deltaArticle, threadId);
		addText(deltaArticle.getText());
	}


	public void update(ClassifierMessage classifierMessage) {
		addText(classifierMessage.getText()); 
	}

	public void update(FeatureGenerator featureGenerator,
					   FeatureIdCollection featureIdCollection) {
		updateFeatureIds(featureGenerator, featureIdCollection);
	}

	public void update(CommunicationChannelArticle deltaArticle) {
		addText(deltaArticle.getText()); 
	}

	private void initialiseMetadata(ClassifierMessage classifierMessage) {
		setBugTrackerId(classifierMessage.getBugTrackerId());
		setBugId(classifierMessage.getBugId());
		setNewsgroupName(classifierMessage.getNewsgroupName());
		setThreadId(classifierMessage.getThreadId());
		setSubject(classifierMessage.getSubject());
	}

	private void initialiseMetadata(ArticleData articleData, int threadId) {
		setNewsgroupName(articleData.getNewsgroupName());
		setThreadId(threadId);
		setSubject(articleData.getSubject());
	}

	private void initialiseMetadata(String newsgroupName, 
									CommunicationChannelArticle deltaArticle, 
									int threadId) {
		setNewsgroupName(newsgroupName);
		setThreadId(threadId);
		if (deltaArticle!=null) {
			System.err.println("deltaArticle.getSubject() : " + deltaArticle.getSubject());
			setSubject(deltaArticle.getSubject());
		} else {
			setSubject("");
		}
	}

	private void updateFeatureIds(FeatureGenerator featureGenerator, 
								  FeatureIdCollection featureIdCollection) {
		
		for (int unigramId: featureIdCollection.getUnigrams())
			unigrams.add(featureGenerator.getUnigramLemma(unigramId));
		for (int bigramId: featureIdCollection.getBigrams())
			bigrams.add(featureGenerator.getBigramLemma(bigramId));
		for (int trigramId: featureIdCollection.getTrigrams())
			trigrams.add(featureGenerator.getTrigramLemma(trigramId));
		for (int quadgramId: featureIdCollection.getQuadgrams())
			quadgrams.add(featureGenerator.getQuadgramLemma(quadgramId));
		
		for (int charTrigramId: featureIdCollection.getCharTrigrams())
			charTrigrams.add(featureGenerator.getCharTrigramLemma(charTrigramId));
		for (int charQuadgramId: featureIdCollection.getCharQuadgrams())
			charQuadgrams.add(featureGenerator.getCharQuadgramLemma(charQuadgramId));
		for (int charFivegramId: featureIdCollection.getCharFivegrams())
			charFivegrams.add(featureGenerator.getCharFivegramLemma(charFivegramId));
		
	}

	String getComposedId() {
		if (composedId==null) setComposedId();
		return composedId;
	}

	private void setComposedId() {
		if ((bugTrackerId!=null)&&(bugId!=null))
			composedId = bugTrackerId+"#"+bugId;
		else if ((newsgroupName!=null)&&(threadId!=0)) 
			composedId = newsgroupName+"#"+threadId;
		else {
			System.err.println("Unable to compose ID");
		}
		toString();
	}

	String getBugTrackerId() {
		return bugTrackerId;
	}
	
	void setBugTrackerId(String bugTrackerId) {
		this.bugTrackerId = bugTrackerId;
		if (composedId!=null) setComposedId();
	}
	
	String getNewsgroupName() {
		return newsgroupName;
	}

	void setNewsgroupName(String newsgroupName) {
		this.newsgroupName = newsgroupName;
		if (composedId!=null) setComposedId();
	}

	String getBugId() {
		return bugId;
	}

	void setBugId(String bugId) {
		this.bugId = bugId;
		if (composedId!=null) setComposedId();
	}


	int getThreadId() {
		return threadId;
	}
	
	void setThreadId(int threadId) {
		this.threadId = threadId;
		if (composedId!=null) setComposedId();
	}
	
	String getSubject() {
		return subject;
	}
	
	void setSubject(String subject) {
		this.subject = subject;
	}
	
//	String getText() {
//		return text;
//	}
	
	void addText(String text) {
//		if (this.text.length()>0)
//			this.text += "\n";
//		this.text += text;
//		setCleanText();
		addTokens(text);
	}
	
//	void setCleanText() {
//		StringBuilder stringBuilder = new StringBuilder();
//		for (String line: text.split("\n")) {
//			String trimmedLine = line.trim();
//			if ( (!trimmedLine.startsWith("<")) && (!trimmedLine.startsWith(">")) ) {
//				stringBuilder.append(line);
//				stringBuilder.append("\n");
//			}
//		}
//		cleanText = stringBuilder.toString();
//	}
	
	private String clean(String text) {
		StringBuilder stringBuilder = new StringBuilder();
		for (String line: text.split("\n")) {
			String trimmedLine = line.trim();
			if ( (!trimmedLine.startsWith("<")) && (!trimmedLine.startsWith(">")) ) {
				stringBuilder.append(line);
				stringBuilder.append("\n");
			}
		}
		return stringBuilder.toString();
	}

//	List<List<Token>> getTokenSentences() {
//		if (tokenSentences==null) {
//			addTokens();
////			outputTag(tokenSentences);
//		}
//		return tokenSentences;
//	}

	private void initialiseNGrams() {
		unigrams = new HashSet<String>();
		bigrams = new HashSet<String>();
		trigrams = new HashSet<String>();
		quadgrams = new HashSet<String>();
		charTrigrams = new HashSet<String>();
		charQuadgrams = new HashSet<String>();
		charFivegrams = new HashSet<String>();
	}

	private void addTokens(String text) {
		OpenNlpTartarusSingleton tartarus = OpenNlpTartarusSingleton.getInstance();
		tokenSentences = tartarus.getTagger().tag(text);
		for (List<Token> sentence: getCleanTokenSentences(text)) {
			addUnigrams(sentence);
			addBigrams(sentence);
			addTrigrams(sentence);
			addQuadgrams(sentence);
			addCharTrigrams(sentence);
			addCharQuadgrams(sentence);
			addCharFivegrams(sentence);
		}
	}

	List<List<Token>> getTokens() {
		return tokenSentences;
	}

	private String getSF(Token token) {
		return token.getSurfaceForm().toLowerCase();
	}
	
	private String getBigram(Token tokenA, Token tokenB) {
		return getSF(tokenA) + " " + getSF(tokenB);
	}
		
	private String getBigram(List<Token> sentence, int indexA, int indexB) {
		return getBigram(sentence.get(indexA), sentence.get(indexB));
	}

	private String getTrigram(List<Token> sentence, int indexA, int indexB, int indexC) {
		return getSF(sentence.get(indexA)) + " " + 
				getSF(sentence.get(indexB)) + " " + 
				 getSF(sentence.get(indexC));
	}
	
	private String getQuadgram(List<Token> sentence, int indexA, int indexB, int indexC, int indexD) {
		return getBigram(sentence, indexA, indexB) + " " + getBigram(sentence, indexC, indexD);
	}
	
	private Set<String> addUnigrams(List<Token> sentence) {
		Set<String> unigramSet = new HashSet<String>(); 
		for (Token token: sentence) {
			String tokenString = getSF(token);
			unigrams.add(tokenString);
			unigramSet.add(tokenString);
		}
		return unigramSet;
	}
	
	private void addBigrams(List<Token> sentence) {
		for (int index=0; index<sentence.size()-1; index++)
			bigrams.add(getBigram(sentence, index, index+1));
	}
	
	private void addTrigrams(List<Token> sentence) {
		for (int index=0; index<sentence.size()-2; index++)
			trigrams.add(getTrigram(sentence, index, index+1, index+2));
	}
	
	private void addQuadgrams(List<Token> sentence) {
		for (int index=0; index<sentence.size()-3; index++)
			quadgrams.add(getQuadgram(sentence, index, index+1, index+2, index+3));
	}

	private void addCharTrigrams(List<Token> sentence) {
		addCharNgrams(sentence, charTrigrams, 3);
	}
	
	private void addCharQuadgrams(List<Token> sentence) {
		addCharNgrams(sentence, charQuadgrams, 4);
	}
	
	private void addCharFivegrams(List<Token> sentence) {
		addCharNgrams(sentence, charFivegrams, 5);
	}
	
	private void addCharNgrams(List<Token> sentence, Set<String> charNgrams, int n) {
		for (Token token: sentence) {
			String word = token.getSurfaceForm().toLowerCase();
			for (int index=0; index<=word.length()-n; index++)
				charNgrams.add(word.substring(index, index+n));
		}
	}

	public Set<String> getUnigrams() {
		return unigrams;
	}

	public Set<String> getBigrams() {
		return bigrams;
	}

	public Set<String> getTrigrams() {
		return trigrams;
	}

	public Set<String> getQuadgrams() {
		return quadgrams;
	}

	public Set<String> getCharTrigrams() {
		return charTrigrams;
	}
	
	public Set<String> getCharQuadgrams() {
		return charQuadgrams;
	}

	public Set<String> getCharFivegrams() {
		return charFivegrams;
	}
	
	List<String> getSortedUnigrams() {
		return sortedUnigrams = sortSet(unigrams, sortedUnigrams);
	}
	
	List<String> getSortedBigrams() {
		return sortedBigrams = sortSet(bigrams, sortedBigrams);
	}

	List<String> getSortedTrigrams() {
		return sortedTrigrams = sortSet(trigrams, sortedTrigrams);
	}
	
	List<String> getSortedQuadgrams() {
		return sortedQuadgrams = sortSet(quadgrams, sortedQuadgrams);
	}

	List<String> getSortedCharTrigrams() {
		return sortedCharTrigrams = sortSet(charTrigrams, sortedCharTrigrams);
	}
	
	List<String> getSortedCharQuadgrams() {
		return sortedCharQuadgrams = sortSet(charQuadgrams, sortedCharQuadgrams);
	}
	
	List<String> getSortedCharFivegrams() {
		return sortedCharFivegrams = sortSet(charFivegrams, sortedCharFivegrams);
	}
	
	private List<String> sortSet(Set<String> set, List<String> sortedList) {
		if ((sortedList==null)||(set.size()!=sortedList.size())) {
			TreeSet<String> sortedSet = new TreeSet<String>();
			sortedSet.addAll(set);
			sortedList = new ArrayList<String>(sortedSet); 
		}
		return sortedList;
	}
	
	List<List<Token>> getCleanTokenSentences(String text) {
		if (cleanTokenSentences==null) {
			OpenNlpTartarusSingleton tartarus = OpenNlpTartarusSingleton.getInstance();
			cleanTokenSentences = tartarus.getTagger().tag(clean(text));
//			outputTag(cleanTokenSentences);
		}
		return cleanTokenSentences;
	}

	@Override
	public String toString() {
		if (newsgroupName!=null)
			return "ClassificationInstance " + "[newsgroupName=" + newsgroupName + 
					", threadId=" + threadId + ", subject=" + subject + "]";
		else
			return "ClassificationInstance "+ "[bugTrackerId=" + bugTrackerId + 
					", bugId=" + bugId + ", subject=" + subject + "]";
			
	}

}
