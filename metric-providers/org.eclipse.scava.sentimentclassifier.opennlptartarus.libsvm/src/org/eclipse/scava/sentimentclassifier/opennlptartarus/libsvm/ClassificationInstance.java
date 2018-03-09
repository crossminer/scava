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
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.eclipse.scava.sentimentclassifier.opennlptartarus.libsvm.featuretools.CMUclusters;
import org.eclipse.scava.sentimentclassifier.opennlptartarus.libsvm.featuretools.CMUclustersSingleton;
import org.eclipse.scava.sentimentclassifier.opennlptartarus.libsvm.featuretools.Negation;
import org.eclipse.scava.sentimentclassifier.opennlptartarus.libsvm.featuretools.SentimentAnalysisLexica;
import org.eclipse.scava.sentimentclassifier.opennlptartarus.libsvm.featuretools.SentimentAnalysisLexicaSingleton;

import uk.ac.nactem.posstemmer.OpenNlpTartarusSingleton;
import uk.ac.nactem.posstemmer.Token;

public class ClassificationInstance {
	
	private String bugTrackerId;
	private String bugId;
	private String commentId;
	
	private String newsgroupName;
	private int articleNumber;
	private String subject;
	private List<List<Token>> tokenSentences;
	private List<List<Token>> cleanTokenSentences;

	private String text;
	private String cleanText;
	private String composedId;
	
	private Negation negation;
	
	private Map<String, Integer> partsOfSpeech;
	
	private Set<String> unigrams, bigrams, trigrams, quadgrams, 
						unigramUnigramPairs, bigramBigramPairs, 
						bigramUnigramPairs, unigramBigramPairs,
						charTrigrams, charQuadgrams, charFivegrams, occurrenceOfClusters;

	private Set<String> unigramsNeg, bigramsNeg, trigramsNeg, quadgramsNeg, 
						unigramUnigramPairsNeg, bigramBigramPairsNeg, 
						bigramUnigramPairsNeg, unigramBigramPairsNeg,
						charTrigramsNeg, charQuadgramsNeg, charFivegramsNeg;

	private List<String> sortedUnigrams, sortedBigrams, 
						 sortedTrigrams, sortedQuadgrams, 
						 sortedUnigramUnigramPairs, sortedBigramBigramPairs, 
						 sortedBigramUnigramPairs, sortedUnigramBigramPairs,
						 sortedCharTrigrams, sortedCharQuadgrams, sortedCharFivegrams,
						 sortedPartsOfSpeech;

	private List<String> sortedUnigramsNeg, sortedBigramsNeg, 
						 sortedTrigramsNeg, sortedQuadgramsNeg, 
						 sortedUnigramUnigramPairsNeg, sortedBigramBigramPairsNeg, 
						 sortedBigramUnigramPairsNeg, sortedUnigramBigramPairsNeg,
						 sortedCharTrigramsNeg, sortedCharQuadgramsNeg, sortedCharFivegramsNeg;
	
	private int wordsAllCaps, punctuationSequenceLength, sentencesEndingInPunctuation, elongatedWords, negatedContexts;
	
	private Map<String, Map<String, Integer>> positiveScoreUnigramsPerPoS,
											  positiveScoreNgrams;
	
	private Map<String, Map<String, Double>> totalScoreUnigramsPerPoS,
											 totalScoreNgrams,
											 maximalScoreNgrams,
											 lastScoreUnigramsPerPoS,
											 lastScoreNgrams;

	private static String HuLiuMethodName = "HuLiu",
						  SubjectivityMethodName = "Subjectivity",
						  NRCMethodName = "NRC",
						  NRCHashtagSentimentMethodName = "NRCHashtagSentiment",
						  Sentiment140MethodName = "Sentiment140";

	private static String[] methodNames = { HuLiuMethodName, SubjectivityMethodName, NRCMethodName,	
											NRCHashtagSentimentMethodName, Sentiment140MethodName };
	
	private static String unigramString = "Unigrams",
						  allCapsString = "AllCaps",
						  bigramString = "Bigrams",
						  unigramUnigramString = "UnigramUnigramPairs",
						  bigramBigramString = "BigramBigramPairs",
						  unigramBigramString = "UnigramBigramPairs",
						  bigramUnigramString = "BigramUnigramPairs";

	
	public  ClassificationInstance() {
	}
	
	public String getComposedId() {
		if (composedId==null) setComposedId();
		return composedId;
	}

	private void setComposedId() {
		if ((bugTrackerId!=null)&&(bugId!=null)&&(commentId!=null))
			composedId = bugTrackerId+"#"+bugId+"#"+commentId;
		else if ((newsgroupName!=null)&&(articleNumber!=0)) 
			composedId = newsgroupName+"#"+articleNumber;
		else {
			System.err.println("Unable to compose ID");
		}
		toString();
	}

	public String getBugTrackerId() {
		return bugTrackerId;
	}
	
	public void setBugTrackerId(String bugTrackerId) {
		this.bugTrackerId = bugTrackerId;
		if (composedId!=null) setComposedId();
	}
	
	public String getNewsgroupName() {
		return newsgroupName;
	}

	public void setNewsgroupName(String newsgroupName) {
		this.newsgroupName = newsgroupName;
		if (composedId!=null) setComposedId();
	}

	public String getBugId() {
		return bugId;
	}

	public void setBugId(String bugId) {
		this.bugId = bugId;
		if (composedId!=null) setComposedId();
	}


	public String getCommentId() {
		return commentId;
	}


	public void setCommentId(String commentId) {
		this.commentId = commentId;
		if (composedId!=null) setComposedId();
	}


	public int getArticleNumber() {
		return articleNumber;
	}
	
	public void setArticleNumber(int articleNumber) {
		this.articleNumber = articleNumber;
		if (composedId!=null) setComposedId();
	}
	
	public String getSubject() {
		return subject;
	}
	
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
		setCleanText();
		addTokens();
	}
	
	public void setCleanText() {
		StringBuilder stringBuilder = new StringBuilder();
		for (String line: text.split("\n")) {
			String trimmedLine = line.trim();
			if ( (!trimmedLine.startsWith("<")) && (!trimmedLine.startsWith(">")) ) {
				stringBuilder.append(line);
				stringBuilder.append("\n");
			}
		}
		cleanText = stringBuilder.toString();
	}
	
	public String getCleanText() {
		return cleanText;
	}

	public List<List<Token>> getTokenSentences() {
		if (tokenSentences==null) {
			addTokens();
//			outputTag(tokenSentences);
		}
		return tokenSentences;
	}

	private void addTokens() {
		OpenNlpTartarusSingleton tartarus = OpenNlpTartarusSingleton.getInstance();
		tokenSentences = tartarus.getTagger().tag(text);
		wordsAllCaps = 0;
		punctuationSequenceLength = 0;
		sentencesEndingInPunctuation = 0;
		elongatedWords = 0;
		negatedContexts = 0;
		partsOfSpeech = new HashMap<String, Integer>();
		unigrams = new HashSet<String>();
		unigramsNeg = new HashSet<String>();
		bigrams = new HashSet<String>();
		bigramsNeg = new HashSet<String>();
		trigrams = new HashSet<String>();
		trigramsNeg = new HashSet<String>();
		quadgrams = new HashSet<String>();
		quadgramsNeg = new HashSet<String>();
		unigramUnigramPairs = new HashSet<String>();
		unigramUnigramPairsNeg = new HashSet<String>();
		bigramBigramPairs = new HashSet<String>();
		bigramBigramPairsNeg = new HashSet<String>();
		bigramUnigramPairs = new HashSet<String>();
		bigramUnigramPairsNeg = new HashSet<String>();
		unigramBigramPairs = new HashSet<String>();
		unigramBigramPairsNeg = new HashSet<String>();
		charTrigrams = new HashSet<String>();
		charTrigramsNeg = new HashSet<String>();
		charQuadgrams = new HashSet<String>();
		charQuadgramsNeg = new HashSet<String>();
		charFivegrams = new HashSet<String>();
		charFivegramsNeg = new HashSet<String>();
		occurrenceOfClusters = new HashSet<String>();
		positiveScoreUnigramsPerPoS = new HashMap<String, Map<String,Integer>>();
		positiveScoreNgrams = new HashMap<String, Map<String,Integer>>();
		totalScoreUnigramsPerPoS = new HashMap<String, Map<String,Double>>();
		totalScoreNgrams = new HashMap<String, Map<String,Double>>();
		maximalScoreNgrams = new HashMap<String, Map<String,Double>>();
		lastScoreUnigramsPerPoS = new HashMap<String, Map<String,Double>>();
		lastScoreNgrams = new HashMap<String, Map<String,Double>>();
		for (List<Token> sentence: getCleanTokenSentences()) {
			wordsAllCaps += countAllCaps(sentence);
			int seqLength = countContiguousPunctuationSequence(sentence);
			if (punctuationSequenceLength<seqLength)
				punctuationSequenceLength = seqLength;
			if (endsInPunctuation(sentence))
				sentencesEndingInPunctuation++;
			elongatedWords += countElongatedWords(sentence);
			if (negation!=null)
				negatedContexts += Negation.update(sentence);
			addPartsOfSpeech(sentence);
			addUnigrams(sentence);
			addBigrams(sentence);
			addTrigrams(sentence);
			addQuadgrams(sentence);
			addUnigramUnigramPairs(sentence);
			addBigramBigramPairs(sentence);
			addBigramUnigramPairs(sentence);
			addUnigramBigramPairs(sentence);
			addCharTrigrams(sentence);
			addCharQuadgrams(sentence);
			addCharFivegrams(sentence);
			addCMUclusterFeatures(sentence);
		}
		computeLexiconScores();
	}

	private void addCMUclusterFeatures(List<Token> sentence) {
		CMUclusters cmuClusters = CMUclustersSingleton.getInstance().getClusters();

		for (Token token: sentence) {
			if (cmuClusters.contains(getSF(token)))
				occurrenceOfClusters.add(cmuClusters.getCluster(getSF(token)));
		}
	}

	private void computeLexiconScores() {
		for (String methodName: methodNames) {
			computeUnigramScores(methodName);
			if (methodName.equals(NRCHashtagSentimentMethodName) ||
					methodName.equals(Sentiment140MethodName))
				computeBigramAndPairScores(methodName);
		}
	}
	
	private void computeUnigramScores(String methodName) {
		double unigramMaximalScore = 0, allCapsMaximalScore = 0;
		for (List<Token> sentence: getCleanTokenSentences()) {
			Map<String, Double> lastScoresPerPos = new HashMap<String, Double>();
			double lastScore = 0, allCapsLastScore = 0;
			for (Token token: sentence) {
				double score = computeTokenScore(methodName, token);
				if (score>0) {
					if (unigramMaximalScore<score) unigramMaximalScore=score;
					lastScore=score;
					if (token.isAllCaps()) {
						if (allCapsMaximalScore<score) allCapsMaximalScore = score;
						allCapsLastScore=score;
						incrementScoreInt(positiveScoreNgrams, methodName, allCapsString);
						incrementScoreDouble(totalScoreNgrams, methodName, allCapsString, score);
					}
					storeScore(lastScoresPerPos, token.getPoS(), score);
					incrementScoreInt(positiveScoreNgrams, methodName, unigramString);
					incrementScoreDouble(totalScoreNgrams, methodName, unigramString, score);
					incrementScoreInt(positiveScoreUnigramsPerPoS, methodName, token.getPoS());
					incrementScoreDouble(totalScoreUnigramsPerPoS, methodName, token.getPoS(), score);
				}
			}
			incrementScoreDouble(lastScoreNgrams, methodName, unigramString, lastScore);
			incrementScoreDouble(lastScoreNgrams, methodName, allCapsString, allCapsLastScore);
			incrementScoreDouble(lastScoreUnigramsPerPoS, methodName, lastScoresPerPos);
		}
		incrementScoreDouble(maximalScoreNgrams, methodName, unigramString, unigramMaximalScore);
	}
	
	private double computeTokenScore(String methodName, Token token) {
		SentimentAnalysisLexica sentimentAnalysisLexica = 
				SentimentAnalysisLexicaSingleton.getInstance().getLexica();
		double score = 0;
		if (methodName.equals(HuLiuMethodName))
			if (sentimentAnalysisLexica.isHuLiuPositive(getSF(token)))
				score = 1;
		else if (methodName.equals(SubjectivityMethodName)) {
			if (sentimentAnalysisLexica.existsInSubjectLexicon(token, "strong", "positive"))
				score = 1;
			else if (sentimentAnalysisLexica.existsInSubjectLexicon(token, "weak", "positive"))
				score = 0.5;
		} else if (methodName.equals(NRCMethodName))
			if (sentimentAnalysisLexica.isInNRCLexicon("positive", getSF(token)))
				score = 1;
		else if (methodName.equals(NRCHashtagSentimentMethodName))
			score = sentimentAnalysisLexica.lookUpNRCHashtagSentimentUnigram(getSF(token));
		else if (methodName.equals(Sentiment140MethodName))
			score = sentimentAnalysisLexica.lookUpSentiment140Unigram(getSF(token));
		return score;
	}

	private void computeBigramAndPairScores(String methodName) {
		double maximalScore = 0;
		for (String bigram: bigrams) {
			double score = computeBigramScore(methodName, bigram);
			if (score>0) {
				if (maximalScore<score) maximalScore=score;
				maximalScore=score;
				incrementScoreInt(positiveScoreNgrams, methodName, bigramString);
				incrementScoreDouble(totalScoreNgrams, methodName, bigramString, score);
			}
		}
		incrementScoreDouble(maximalScoreNgrams, methodName, bigramString, maximalScore);

		maximalScore = 0;
		for (String unigramUnigramPair: unigramUnigramPairs) {
			double score = computeUnigramUnigramPairScore(methodName, unigramUnigramPair);
			if (score>0) {
				if (maximalScore<score) maximalScore=score;
				maximalScore=score;
				incrementScoreInt(positiveScoreNgrams, methodName, unigramUnigramString);
				incrementScoreDouble(totalScoreNgrams, methodName, unigramUnigramString, score);
			}
		}
		incrementScoreDouble(maximalScoreNgrams, methodName, unigramUnigramString, maximalScore);

		maximalScore = 0;
		for (String bigramBigramPair: bigramBigramPairs) {
			double score = computeBigramBigramPairScore(methodName, bigramBigramPair);
			if (score>0) {
				if (maximalScore<score) maximalScore=score;
				maximalScore=score;
				incrementScoreInt(positiveScoreNgrams, methodName, bigramBigramString);
				incrementScoreDouble(totalScoreNgrams, methodName, bigramBigramString, score);
			}
		}
		incrementScoreDouble(maximalScoreNgrams, methodName, bigramBigramString, maximalScore);

		maximalScore = 0;
		for (String bigramUnigramPair: bigramUnigramPairs) {
			double score = computeBigramUnigramPairScore(methodName, bigramUnigramPair);
			if (score>0) {
				if (maximalScore<score) maximalScore=score;
				maximalScore=score;
				incrementScoreInt(positiveScoreNgrams, methodName, bigramUnigramString);
				incrementScoreDouble(totalScoreNgrams, methodName, bigramUnigramString, score);
			}
		}
		incrementScoreDouble(maximalScoreNgrams, methodName, bigramUnigramString, maximalScore);

		maximalScore = 0;
		for (String unigramBigramPair: unigramBigramPairs) {
			double score = computeUnigramBigramPairScore(methodName, unigramBigramPair);
			if (score>0) {
				if (maximalScore<score) maximalScore=score;
				maximalScore=score;
				incrementScoreInt(positiveScoreNgrams, methodName, unigramBigramString);
				incrementScoreDouble(totalScoreNgrams, methodName, unigramBigramString, score);
			}
		}
		incrementScoreDouble(maximalScoreNgrams, methodName, unigramBigramString, maximalScore);
	}
	
	private double computeBigramScore(String methodName, String bigram) {
		SentimentAnalysisLexica sentimentAnalysisLexica = 
				SentimentAnalysisLexicaSingleton.getInstance().getLexica();
		double score = 0;
		if (methodName.equals(NRCHashtagSentimentMethodName))
			score = sentimentAnalysisLexica.lookUpNRCHashtagSentimentBigram(bigram);
		else if (methodName.equals(Sentiment140MethodName))
			score = sentimentAnalysisLexica.lookUpSentiment140Bigram(bigram);
		return score;
	}

	private double computeUnigramUnigramPairScore(String methodName, String pair) {
		SentimentAnalysisLexica sentimentAnalysisLexica = 
				SentimentAnalysisLexicaSingleton.getInstance().getLexica();
		double score = 0;
		if (methodName.equals(NRCHashtagSentimentMethodName))
			score = sentimentAnalysisLexica.lookUpNRCHashtagSentimentUnigramUnigramPair(pair);
		else if (methodName.equals(Sentiment140MethodName))
			score = sentimentAnalysisLexica.lookUpSentiment140UnigramUnigramPair(pair);
		return score;
	}

	private double computeBigramBigramPairScore(String methodName, String pair) {
		SentimentAnalysisLexica sentimentAnalysisLexica = 
				SentimentAnalysisLexicaSingleton.getInstance().getLexica();
		double score = 0;
		if (methodName.equals(NRCHashtagSentimentMethodName))
			score = sentimentAnalysisLexica.lookUpNRCHashtagSentimentBigramBigramPair(pair);
		else if (methodName.equals(Sentiment140MethodName))
			score = sentimentAnalysisLexica.lookUpSentiment140BigramBigramPair(pair);
		return score;
	}

	private double computeBigramUnigramPairScore(String methodName, String pair) {
		SentimentAnalysisLexica sentimentAnalysisLexica = 
				SentimentAnalysisLexicaSingleton.getInstance().getLexica();
		double score = 0;
		if (methodName.equals(NRCHashtagSentimentMethodName))
			score = sentimentAnalysisLexica.lookUpNRCHashtagSentimentBigramUnigramPair(pair);
		else if (methodName.equals(Sentiment140MethodName))
			score = sentimentAnalysisLexica.lookUpSentiment140BigramUnigramPair(pair);
		return score;
	}

	private double computeUnigramBigramPairScore(String methodName, String pair) {
		SentimentAnalysisLexica sentimentAnalysisLexica = 
				SentimentAnalysisLexicaSingleton.getInstance().getLexica();
		double score = 0;
		if (methodName.equals(NRCHashtagSentimentMethodName))
			score = sentimentAnalysisLexica.lookUpNRCHashtagSentimentUnigramBigramPair(pair);
		else if (methodName.equals(Sentiment140MethodName))
			score = sentimentAnalysisLexica.lookUpSentiment140UnigramBigramPair(pair);
		return score;
	}
	
	private void storeScore(Map<String, Integer> map, String sequenceType, int score) {
		if (map.containsKey(sequenceType))
			map.put(sequenceType, map.get(sequenceType) + score);
		else
			map.put(sequenceType, score);
	}

	private void storeScore(Map<String, Double> map, String sequenceType, double score) {
		if (map.containsKey(sequenceType))
			map.put(sequenceType, map.get(sequenceType) + score);
		else
			map.put(sequenceType, score);
	}

	private void incrementScoreInt(Map<String, Map<String, Integer>> map,
								String methodName, String sequenceType) {
		Map<String, Integer> tempMap;
		if (map.containsKey(methodName))
			tempMap = map.get(methodName);
		else {
			tempMap = new HashMap<String, Integer>();
			map.put(methodName, tempMap);
		}
		storeScore(tempMap, sequenceType, 1);
	}

	private void incrementScoreDouble(Map<String, Map<String, Double>> map,
								String methodName, String sequenceType, double increment) {
		if (increment!=0) {
			Map<String, Double> tempMap;
			if (map.containsKey(methodName))
				tempMap = map.get(methodName);
			else {
				tempMap = new HashMap<String, Double>();
				map.put(methodName, tempMap);
			}
			storeScore(tempMap, sequenceType, increment);
		}
	}

	private void incrementScoreDouble(Map<String, Map<String, Double>> map,
								String methodName, Map<String, Double> scoreMap) {
		Map<String, Double> tempMap;
		if (map.containsKey(methodName))
			tempMap = map.get(methodName);
		else {
			tempMap = new HashMap<String, Double>();
			map.put(methodName, tempMap);
		}
		for (String sequenceType: scoreMap.keySet())
			storeScore(tempMap, sequenceType, scoreMap.get(sequenceType));
}

	private int countElongatedWords(List<Token> sentence) {
		int elongatedWords = 0;
		for (Token token: sentence)
			if (token.isElongated())
				elongatedWords++;
		return elongatedWords;
	}
	
	public int getElongatedWords() {
		return elongatedWords;
	}

	public int getNegatedContexts() {
		return negatedContexts;
	}

	private boolean endsInPunctuation(List<Token> sentence) {
		if ((sentence.size()>0) && 
			(sentence.get(sentence.size()-1).containsPunctuation()))
			return true;
		return false;
	}

	public int getSentencesEndingInPunctuation() {
		return sentencesEndingInPunctuation;
	}

	private int countContiguousPunctuationSequence(List<Token> sentence) {
		int seqLength = 0, newSeqLength = 0;
		for (Token token: sentence) {
			if (token.containsPunctuation()) {
				newSeqLength++;
				if (newSeqLength>seqLength)
					seqLength = newSeqLength;
			} else
				newSeqLength = 0;
		}
		return seqLength;
	}

	public int getContiguousPunctuationWords() {
		return punctuationSequenceLength;
	}

	private void addPartsOfSpeech(List<Token> sentence) {
		for (Token token: sentence) {
			int freq = partsOfSpeech.containsKey(token.getPoS()) ? partsOfSpeech.get(token.getPoS()) + 1 : 1;
			partsOfSpeech.put(token.getPoS(), freq);
		}
	}
	
	public Set<String> getPartsOfSpeech() {
		return partsOfSpeech.keySet();
	}

	public List<String> getSortedPartsOfSpeech() {
		return sortSet(partsOfSpeech.keySet(), sortedPartsOfSpeech);
	}
	
	public int getFrequency(String partOfSpeech) {
		return partsOfSpeech.containsKey(partOfSpeech) ? partsOfSpeech.get(partOfSpeech): 0;
	}

	private int countAllCaps(List<Token> sentence) {
		int wordsAllCaps = 0;
		for (Token token: sentence)
			if (token.isAllCaps())
				wordsAllCaps++;
		return wordsAllCaps;
	}

	public int getWordsAllCaps() {
		return wordsAllCaps;
	}

	public List<List<Token>> getTokens() {
		return tokenSentences;
	}

	private String getSF(Token token) {
		return token.getSurfaceForm().toLowerCase();
	}
	
	private String getSFneg(Token token) {
		return token.getSurfaceFormNeg().toLowerCase();
	}
	
	private String getBigram(Token tokenA, Token tokenB) {
		return getSF(tokenA) + " " + getSF(tokenB);
	}
	
	private String getBigramNeg(Token tokenA, Token tokenB) {
		return getSFneg(tokenA) + " " + getSFneg(tokenB);
	}
		
	private String getBigram(List<Token> sentence, int indexA, int indexB) {
		return getBigram(sentence.get(indexA), sentence.get(indexB));
	}
	
	private String getBigramNeg(List<Token> sentence, int indexA, int indexB) {
		return getBigramNeg(sentence.get(indexA), sentence.get(indexB));
	}
		
	private String getTrigram(List<Token> sentence, int indexA, int indexB, int indexC) {
		return getSF(sentence.get(indexA)) + " " + 
				getSF(sentence.get(indexB)) + " " + 
				 getSF(sentence.get(indexC));
	}
	
	private String getTrigramNeg(List<Token> sentence, int indexA, int indexB, int indexC) {
		return getSFneg(sentence.get(indexA)) + " " + 
				getSFneg(sentence.get(indexB)) + " " + 
				 getSFneg(sentence.get(indexC));
	}
		
	private String getQuadgram(List<Token> sentence, int indexA, int indexB, int indexC, int indexD) {
		return getBigram(sentence, indexA, indexB) + " " + getBigram(sentence, indexC, indexD);
	}
	
	private String getQuadgramNeg(List<Token> sentence, int indexA, int indexB, int indexC, int indexD) {
		return getBigramNeg(sentence, indexA, indexB) + " " + getBigramNeg(sentence, indexC, indexD);
	}

	private void addUnigrams(List<Token> sentence) {
		for (Token token: sentence) {
			unigrams.add(getSF(token));
			unigramsNeg.add(getSFneg(token));
		}
	}
	
	private void addBigrams(List<Token> sentence) {
		for (int index=0; index<sentence.size()-1; index++) {
			bigrams.add(getBigram(sentence, index, index+1));
			bigramsNeg.add(getBigramNeg(sentence, index, index+1));
		}
	}
	
	private void addTrigrams(List<Token> sentence) {
		for (int index=0; index<sentence.size()-2; index++) {
			trigrams.add(getTrigram(sentence, index, index+1, index+2));
			trigramsNeg.add(getTrigramNeg(sentence, index, index+1, index+2));
		}
	}
	
	private void addQuadgrams(List<Token> sentence) {
		for (int index=0; index<sentence.size()-3; index++) {
			quadgrams.add(getQuadgram(sentence, index, index+1, index+2, index+3));
			quadgramsNeg.add(getQuadgramNeg(sentence, index, index+1, index+2, index+3));
		}
	}

	private void addUnigramUnigramPairs(List<Token> sentence) {
		for (int indexA=0; indexA<sentence.size()-1; indexA++) {
			Token tokenA = sentence.get(indexA);
			if (tokenA.containsLetters()) {
				for (int indexB=indexA+1; indexB<sentence.size(); indexB++) {
					Token tokenB = sentence.get(indexB);
					if (tokenB.containsLetters()) {
						unigramUnigramPairs.add(getSF(tokenA) + "---" + getSF(tokenB));
						unigramUnigramPairsNeg.add(getSFneg(tokenA) + "---" + getSFneg(tokenB));
					}
				}
			}
		}
	}
	
	private void addBigramBigramPairs(List<Token> sentence) {
		for (int indexA=0; indexA<sentence.size()-3; indexA++) {
			Token tokenA = sentence.get(indexA),
				  tokenB = sentence.get(indexA+1);
			if (tokenA.containsLetters() && tokenB.containsLetters()) {
				for (int indexB=indexA+1; indexB<sentence.size()-1; indexB++) {
					Token tokenC = sentence.get(indexB),
						  tokenD = sentence.get(indexB+1);
					if (tokenC.containsLetters() && tokenD.containsLetters()) {
						bigramBigramPairs.add(getBigram(tokenA, tokenB) + "---" + getBigram(tokenC, tokenD));
						bigramBigramPairsNeg.add(getBigramNeg(tokenA, tokenB) + "---" + getBigramNeg(tokenC, tokenD));
					}
				}
			}
		}
	}
	
	private void addBigramUnigramPairs(List<Token> sentence) {
		for (int indexA=0; indexA<sentence.size()-2; indexA++) {
			Token tokenA = sentence.get(indexA),
				  tokenB = sentence.get(indexA+1);
			if (tokenA.containsLetters() && tokenB.containsLetters()) {
				for (int indexB=indexA+1; indexB<sentence.size(); indexB++) {
					Token tokenC = sentence.get(indexB);
					if (tokenC.containsLetters()) {
						bigramUnigramPairs.add(getBigram(tokenA, tokenB) + "---" + getSF(tokenC));
						bigramUnigramPairsNeg.add(getBigramNeg(tokenA, tokenB) + "---" + getSFneg(tokenC));
					}
				}
			}
		}
	}
	
	
	private void addUnigramBigramPairs(List<Token> sentence) {
		for (int indexA=0; indexA<sentence.size()-2; indexA++) {
			Token tokenA = sentence.get(indexA);
			if (tokenA.containsLetters()) {
				for (int indexB=indexA+1; indexB<sentence.size()-1; indexB++) {
					Token tokenB = sentence.get(indexB),
						  tokenC = sentence.get(indexB+1);
					if (tokenB.containsLetters() && tokenC.containsLetters()) {
						unigramBigramPairs.add(getSF(tokenA) + "---" + getBigram(tokenB, tokenC));
						unigramBigramPairsNeg.add(getSFneg(tokenA) + "---" + getBigramNeg(tokenB, tokenC));
					}
				}
			}
		}
	}

	private void addCharTrigrams(List<Token> sentence) {
		addCharNgrams(sentence, charTrigrams, charTrigramsNeg, 3);
	}
	
	private void addCharQuadgrams(List<Token> sentence) {
		addCharNgrams(sentence, charQuadgrams, charQuadgramsNeg, 4);
	}
	
	private void addCharFivegrams(List<Token> sentence) {
		addCharNgrams(sentence, charFivegrams, charFivegramsNeg, 5);
	}
	
	private void addCharNgrams(List<Token> sentence, Set<String> charNgrams, Set<String> charNgramsNeg, int n) {
		for (Token token: sentence) {
			String word = token.getSurfaceForm().toLowerCase();
			for (int index=0; index<=word.length()-n; index++)
				charNgrams.add(word.substring(index, index+n));
			String wordNeg = token.getSurfaceFormNeg().toLowerCase();
			for (int index=0; index<=wordNeg.length()-n; index++)
				charNgramsNeg.add(wordNeg.substring(index, index+n));
		}
	}

	public Set<String> getUnigrams() {
		return unigrams;
	}

	public Set<String> getUnigramsNeg() {
		return unigramsNeg;
	}

	public Set<String> getBigrams() {
		return bigrams;
	}

	public Set<String> getBigramsNeg() {
		return bigramsNeg;
	}

	public Set<String> getTrigrams() {
		return trigrams;
	}
	public Set<String> getTrigramsNeg() {
		return trigramsNeg;
	}
	public Set<String> getQuadgrams() {
		return quadgrams;
	}

	public Set<String> getQuadgramsNeg() {
		return quadgramsNeg;
	}

	public Set<String> getUnigramUnigramPairs() {
		return unigramUnigramPairs;
	}

	public Set<String> getBigramBigramPairs() {
		return bigramBigramPairs;
	}

	public Set<String> getBigramBigramPairsNeg() {
		return bigramBigramPairsNeg;
	}

	public Set<String> getBigramUnigramPairs() {
		return bigramUnigramPairs;
	}

	public Set<String> getBigramUnigramPairsNeg() {
		return bigramUnigramPairsNeg;
	}

	public Set<String> getUnigramBigramPairs() {
		return unigramBigramPairs;
	}

	public Set<String> getUnigramBigramPairsNeg() {
		return unigramBigramPairsNeg;
	}

	public Set<String> getCharTrigrams() {
		return charTrigrams;
	}
	
	public Set<String> getCharTrigramsNeg() {
		return charTrigramsNeg;
	}
	
	public Set<String> getCharQuadgrams() {
		return charQuadgrams;
	}

	public Set<String> getCharQuadgramsNeg() {
		return charQuadgramsNeg;
	}

	public Set<String> getCharFivegrams() {
		return charFivegrams;
	}
	
	public Set<String> getCharFivegramsNeg() {
		return charFivegramsNeg;
	}
	
	public List<String> getSortedUnigrams() {
		return sortedUnigrams = sortSet(unigrams, sortedUnigrams);
	}
	
	public List<String> getSortedUnigramsNeg() {
		return sortedUnigramsNeg = sortSet(unigramsNeg, sortedUnigramsNeg);
	}
	
	public List<String> getSortedBigrams() {
		return sortedBigrams = sortSet(bigrams, sortedBigrams);
	}

	public List<String> getSortedBigramsNeg() {
		return sortedBigramsNeg = sortSet(bigramsNeg, sortedBigramsNeg);
	}

	public List<String> getSortedTrigrams() {
		return sortedTrigrams = sortSet(trigrams, sortedTrigrams);
	}
	
	public List<String> getSortedTrigramsNeg() {
		return sortedTrigramsNeg = sortSet(trigramsNeg, sortedTrigramsNeg);
	}
	
	public List<String> getSortedQuadgrams() {
		return sortedQuadgrams = sortSet(quadgrams, sortedQuadgrams);
	}

	public List<String> getSortedQuadgramsNeg() {
		return sortedQuadgramsNeg = sortSet(quadgramsNeg, sortedQuadgramsNeg);
	}
	
	public List<String> getSortedUnigramUnigramPairs() {
		return sortedUnigramUnigramPairs = sortSet(unigramUnigramPairs, sortedUnigramUnigramPairs);
	}
	
	public List<String> getSortedUnigramUnigramPairsNeg() {
		return sortedUnigramUnigramPairsNeg = sortSet(unigramUnigramPairsNeg, sortedUnigramUnigramPairsNeg);
	}
	
	public List<String> getSortedBigramBigramPairs() {
		return sortedBigramBigramPairs = sortSet(bigramBigramPairs, sortedBigramBigramPairs);
	}
	
	public List<String> getSortedBigramBigramPairsNeg() {
		return sortedBigramBigramPairsNeg = sortSet(bigramBigramPairsNeg, sortedBigramBigramPairsNeg);
	}
	
	public List<String> getSortedBigramUnigramPairs() {
		return sortedBigramUnigramPairs = sortSet(bigramUnigramPairs, sortedBigramUnigramPairs);
	}
	
	public List<String> getSortedBigramUnigramPairsNeg() {
		return sortedBigramUnigramPairsNeg = sortSet(bigramUnigramPairsNeg, sortedBigramUnigramPairsNeg);
	}
	
	public List<String> getSortedUnigramBigramPairs() {
		return sortedUnigramBigramPairs = sortSet(unigramBigramPairs, sortedUnigramBigramPairs);
	}

	public List<String> getSortedUnigramBigramPairsNeg() {
		return sortedUnigramBigramPairsNeg = sortSet(unigramBigramPairsNeg, sortedUnigramBigramPairsNeg);
	}

	public List<String> getSortedCharTrigrams() {
		return sortedCharTrigrams = sortSet(charTrigrams, sortedCharTrigrams);
	}
	
	public List<String> getSortedCharTrigramsNeg() {
		return sortedCharTrigramsNeg = sortSet(charTrigramsNeg, sortedCharTrigramsNeg);
	}
	
	public List<String> getSortedCharQuadgrams() {
		return sortedCharQuadgrams = sortSet(charQuadgrams, sortedCharQuadgrams);
	}
	
	public List<String> getSortedCharQuadgramsNeg() {
		return sortedCharQuadgramsNeg = sortSet(charQuadgramsNeg, sortedCharQuadgramsNeg);
	}
	
	public List<String> getSortedCharFivegrams() {
		return sortedCharFivegrams = sortSet(charFivegrams, sortedCharFivegrams);
	}
	
	public List<String> getSortedCharFivegramsNeg() {
		return sortedCharFivegramsNeg = sortSet(charFivegramsNeg, sortedCharFivegramsNeg);
	}
	
	public boolean participatesInCluster(String clusterId) {
		return occurrenceOfClusters.contains(clusterId);
	}
	
	public Set<String> getOccurringClusters() {
		return occurrenceOfClusters;
	}

	public int getPositiveScoreNgrams(String methodName, String sequenceType) {
		return getHashMapValueI(positiveScoreNgrams, methodName, sequenceType);
	}
	
	public int getPositiveScoreUnigramsPerPoS(String methodName, String pos) {
		return getHashMapValueI(positiveScoreUnigramsPerPoS, methodName, pos);
	}
	
	private int getHashMapValueI(Map<String, Map<String, Integer>> map, String methodName, String item) {
		if (!map.containsKey(methodName))
			return 0;
		if (!map.get(methodName).containsKey(item))
			return 0;
		return map.get(methodName).get(item);
	}
	
	public double getTotalScoreNgrams(String methodName, String sequenceType) {
		return getHashMapValueD(totalScoreNgrams, methodName, sequenceType);
	}
	
	public double getTotalScoreUnigramsPerPoS(String methodName, String pos) {
		return getHashMapValueD(totalScoreUnigramsPerPoS, methodName, pos);
	}

	public double getMaximalScoreNgrams(String methodName, String sequenceType) {
		return getHashMapValueD(maximalScoreNgrams, methodName, sequenceType);
	}
	
	public double getLastScoreNgrams(String methodName, String sequenceType) {
		return getHashMapValueD(lastScoreNgrams, methodName, sequenceType);
	}

	public double getLastScoreUnigramsPerPoS(String methodName, String pos) {
		return getHashMapValueD(lastScoreUnigramsPerPoS, methodName, pos);
	}

	private double getHashMapValueD(Map<String, Map<String, Double>> map, String methodName, String item) {
		if (!map.containsKey(methodName))
			return 0;
		if (!map.get(methodName).containsKey(item))
			return 0;
		return map.get(methodName).get(item);
	}
	
	public String[] getMethodNames() {
		return methodNames;
	}

	private List<String> sortSet(Set<String> set, List<String> sortedList) {
		if ((sortedList==null)||(set.size()!=sortedList.size())) {
			TreeSet<String> sortedSet = new TreeSet<String>();
			sortedSet.addAll(set);
			sortedList = new ArrayList<String>(sortedSet); 
		}
		return sortedList;
	}
	
//	public String setAttribute(String attributeName, String attributeValue) {
//		return attributes.put(attributeName, attributeValue);
//	}
//
//	public String getAttribute(String attributeName) {
//		if (attributes.containsKey(attributeName))
//			return attributes.get(attributeName);
//		return null;
//	}

	public List<List<Token>> getCleanTokenSentences() {
		if (cleanTokenSentences==null) {
			OpenNlpTartarusSingleton tartarus = OpenNlpTartarusSingleton.getInstance();
			cleanTokenSentences = tartarus.getTagger().tag(getCleanText());
//			outputTag(cleanTokenSentences);
		}
		return cleanTokenSentences;
	}

//	private void outputTag(List<List<Token>> tokenSentences) {
//	for (List<Token> tokens: tokenSentences)
//		for (Token token: tokens)
//			System.out.println(token.toString());
//		System.out.println();
//}

	@Override
	public String toString() {
		if (newsgroupName!=null)
			return "ClassificationInstance "
					+ "[newsgroupName=" + newsgroupName + ", articleNumber=" 
					+ articleNumber + ", subject=" + subject + "]";
		else
			return "ClassificationInstance "
					+ "[bugTrackerId=" + bugTrackerId + ", bugId=" + bugId
					+ ", commentId=" + commentId + ", subject=" + subject + "]";
			
	}

}
