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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import uk.ac.nactem.posstemmer.Token;

public class SentimentAnalysisLexica {

	private static Set<String> HuLiuPositiveWordSet, HuLiuNegativeWordSet;
	private static Map<String, Set<String>> NRCWordSet;
	private static Map<String, Map<String, Set<SubjectivityLexiconEntry>>> SubjectivityLexiconWordSet;
	private static Map<String, Float> NRCSentimentUnigramSet;
	private static Map<String, Float> NRCSentimentBigramSet;
	private static Map<String, Float> NRCSentimentPairSet;
	private static Map<String, Float> Sentiment140UnigramSet;
	private static Map<String, Float> Sentiment140BigramSet;
	private static Map<String, Float> Sentiment140PairSet;

	String sentimentAnalysisPath = "SentimentAnalysis/";
	String huLiuPath = sentimentAnalysisPath + "OpinionLexicon-HuLiu04/";
	String huLiuPositiveFilename = huLiuPath + "positive-words.txt";
	String huLiuNegativeFilename = huLiuPath + "negative-words.txt";
	
	String nrcFilename = sentimentAnalysisPath + "NRCEmotionLexicon/" +
					 	 "NRC-emotion-lexicon-wordlevel-alphabetized-v0.92.txt";
	String subjectivityFilename = sentimentAnalysisPath + "MPQALexicon-WilsonEtAl05/" +
								  "subjclueslen1-HLTEMNLP05-Modified.tff";
	
	String nrcHashtagPath = sentimentAnalysisPath + "NRC-Hashtag-Sentiment-Lexicon-v0.1/";
	String nrcHashtagUnigramsFilename = nrcHashtagPath + "unigrams-pmilexicon.txt";
	String nrcHashtagBigramsFilename = nrcHashtagPath + "bigrams-pmilexicon.txt";
	String nrcHashtagPairsFilename = nrcHashtagPath +  "pairs-pmilexicon.txt";
	
	String sentiment140Path = sentimentAnalysisPath + "Sentiment140-Lexicon-v0.1/";
	String sentiment140UnigramsFilename = sentiment140Path + "unigrams-pmilexicon.txt";
	String sentiment140BigramsPathFilename = sentiment140Path + "bigrams-pmilexicon.txt";
	String sentiment140PairsFilename = sentiment140Path + "pairs-pmilexicon.txt";

	public void load() {
     	String path = getClass().getProtectionDomain().getCodeSource().getLocation().getFile();
     	if (path.endsWith("bin/"))
     		path = path.substring(0, path.lastIndexOf("bin/"));
		try {
			HuLiuPositiveWordSet = loadHuLiuSet(path + huLiuPositiveFilename);
			HuLiuNegativeWordSet = loadHuLiuSet(path + huLiuNegativeFilename);
			System.out.println("Loaded HuLiu: " + HuLiuPositiveWordSet.size() + " positive " +
							   "and " + HuLiuNegativeWordSet.size() + " negative words.");
			NRCWordSet = loadNRCSet(path + nrcFilename);
			System.out.println("Loaded NRCLexicon: " + NRCWordSet.get("positive").size() + " positive " +
							   "and " + NRCWordSet.get("negative").size() + " negative words.");
			SubjectivityLexiconWordSet = loadSubjectivityLexicon(path + subjectivityFilename);
			System.out.println("Loaded SubjectivityLexicon: " + SubjectivityLexiconWordSet.size());

			NRCSentimentUnigramSet = loadWordList(path + nrcHashtagUnigramsFilename);
			System.out.println("Loaded NRCSentimentLexiconUnigrams: " + NRCSentimentUnigramSet.size());
			NRCSentimentBigramSet = loadWordList(path + nrcHashtagBigramsFilename);
			System.out.println("Loaded NRCSentimentLexiconBigrams: " + NRCSentimentBigramSet.size());
			NRCSentimentPairSet = loadWordList(path + nrcHashtagPairsFilename);
			System.out.println("Loaded NRCSentimentLexiconPairs: " + NRCSentimentPairSet.size());
			
			Sentiment140UnigramSet = loadWordList(path + sentiment140UnigramsFilename);
			System.out.println("Loaded Sentiment140Unigrams: " + Sentiment140UnigramSet.size());
			Sentiment140BigramSet = loadWordList(path + sentiment140BigramsPathFilename);
			System.out.println("Loaded Sentiment140Bigrams: " + Sentiment140BigramSet.size());
			Sentiment140PairSet = loadWordList(path + sentiment140PairsFilename);
			System.out.println("Loaded Sentiment140Pairs: " + Sentiment140PairSet.size());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean isHuLiuPositive(String word) {
		if (HuLiuPositiveWordSet.contains(word))
			return true;
		return false;
	}
	
	static boolean isHuLiuNegative(String word) {
		if (HuLiuNegativeWordSet.contains(word))
			return true;
		return false;
	}
	
	private static Set<String> loadHuLiuSet(String HuLiuPath) throws IOException {
		Set<String> HuLiuWordSet = new HashSet<String>();
		String HuLiuContent = readFileAsString(new File(HuLiuPath));
		for (String HuLiuLine: HuLiuContent.split("\n"))
			if ( (!HuLiuLine.startsWith(";")) && (HuLiuLine.trim().length()>0) )
				HuLiuWordSet.add(HuLiuLine.trim());
		return HuLiuWordSet;
	}

	private static Map<String, Set<String>> loadNRCSet(String NRCPath) throws IOException {
		Map<String, Set<String>> NRCWordSet = new HashMap<String, Set<String>>();
		NRCWordSet.put("allWords", new HashSet<String>());
		String NRCcontent = readFileAsString(new File(NRCPath));
		for (String NRCline: NRCcontent.split("\n"))
			if ( (!NRCline.startsWith(";")) && (NRCline.trim().length()>0) ) {
				String[] NRClist = NRCline.split("\t");
				if (NRClist.length==3) {
					NRCWordSet.get("allWords").add(NRClist[0]);
					if (NRClist[2].equals("1")) {
						Set<String> NRCWordSubset;
						if (NRCWordSet.containsKey(NRClist[1]))
							NRCWordSubset = NRCWordSet.get(NRClist[1]);
						else {
							NRCWordSubset = new HashSet<String>();
							NRCWordSet.put(NRClist[1], NRCWordSubset);
						}
						NRCWordSubset.add(NRClist[0]);
					}
				}
			}
		return NRCWordSet;
	}
	
	private static Map<String, Map<String, Set<SubjectivityLexiconEntry>>> 
						loadSubjectivityLexicon(String subjectivityPath) throws IOException {
		Map<String, Map<String, Set<SubjectivityLexiconEntry>>> subjectivityLexiconWordSet = 
				new HashMap<String, Map<String, Set<SubjectivityLexiconEntry>>>();
		subjectivityLexiconWordSet.put("strong", new HashMap<String, Set<SubjectivityLexiconEntry>>());
		subjectivityLexiconWordSet.put("weak", new HashMap<String, Set<SubjectivityLexiconEntry>>());
		String NRCcontent = readFileAsString(new File(subjectivityPath));
		for (String SLline: NRCcontent.split("\n"))
			if (SLline.trim().length()>0) {
				String[] NRClist = SLline.split(" ");
				if (NRClist.length==6) {
					String[] tempList = NRClist[2].split("=");
					String word = tempList[1];
					SubjectivityLexiconEntry entry = new SubjectivityLexiconEntry(word);
					tempList = NRClist[0].split("=");
					String entryType = tempList[1];
					entry.setSubjectivityType(entryType);
					tempList = NRClist[3].split("=");
					String partOfSpeech = tempList[1];
					entry.setPartOfSpeech(partOfSpeech);
					tempList = NRClist[4].split("=");
					String stemmed = tempList[1];
					if (stemmed.equals("y"))
						entry.setStemmed(true);
					else
						entry.setStemmed(false);
					tempList = NRClist[5].split("=");
					String priorPolarity = tempList[1];
					entry.setPriorPolarity(priorPolarity);
					if (priorPolarity.equals("positive") || priorPolarity.equals("negative")) {
						Map<String, Set<SubjectivityLexiconEntry>> wordSet = null;
						if (entryType.equals("strongsubj")) {
							wordSet  = subjectivityLexiconWordSet.get("strong");
						}
						else if (entryType.equals("weaksubj")) {
							wordSet = subjectivityLexiconWordSet.get("weak");
						}
						Set<SubjectivityLexiconEntry> tempWordSet;
						if (wordSet.containsKey(entry.getWord()))
							tempWordSet = wordSet.get(entry.getWord());
						else
							tempWordSet = new HashSet<SubjectivityLexiconEntry>();
						tempWordSet.add(entry);
						wordSet.put(entry.getWord(), tempWordSet);
					}
					else if (priorPolarity.equals("neutral") || priorPolarity.equals("both")) {
					}
					else
						System.err.println(priorPolarity);
				} else
					System.err.println("NRClist.length: " + NRClist.length + "\t" + SLline);
				
			} else
				System.err.println("SLline:\t" + SLline);
		return subjectivityLexiconWordSet;
	}
	
	private static Map<String, Float> loadWordList(String path) throws IOException {
		Map<String, Float> set = new HashMap<String, Float>();
		String content = readFileAsString(new File(path));
		for (String line: content.split("\n"))
			if (line.trim().length()>0) {
				String[] list = line.split("\t");
				if (list.length==4) {
					float score = Float.parseFloat(list[1]);
					set.put(list[0], score);
				}
			}
		return set;
	}
	
	public float lookUpNRCHashtagSentimentUnigram(String unigram) {
		return lookUpWordList(NRCSentimentUnigramSet, unigram);
	}

	static float lookUpNRCHashtagSentimentBigram(String unigramA, String unigramB) {
		return lookUpWordList(NRCSentimentBigramSet, unigramA + " " + unigramB);
	}

	public float lookUpNRCHashtagSentimentBigram(String bigram) {
		return lookUpWordList(NRCSentimentBigramSet, bigram);
	}

	static float lookUpNRCHashtagSentimentUnigramUnigramPair(String unigramA, String unigramB) {
		return lookUpWordList(NRCSentimentPairSet, unigramA + "---" + unigramB);
	}
	
	public float lookUpNRCHashtagSentimentUnigramUnigramPair(String pair) {
		return lookUpWordList(NRCSentimentPairSet, pair);
	}
	public float lookUpNRCHashtagSentimentBigramBigramPair(String bigramAA, String bigramAB,
																  String bigramBA, String bigramBB) {
		String bigramA = bigramAA + " " + bigramAB,
			   bigramB = bigramBA + " " + bigramBB;
		float score = lookUpWordList(NRCSentimentPairSet, bigramA + "---" + bigramB);
		if (score!=0) return score;
		return lookUpWordList(NRCSentimentPairSet, bigramB + "---" + bigramA);
	}

	public float lookUpNRCHashtagSentimentBigramBigramPair(String pair) {
		return lookUpWordList(NRCSentimentPairSet, pair);
	}

	public float lookUpNRCHashtagSentimentBigramUnigramPair(String bigramA, String bigramB,
																   String unigram) {
		String bigram = bigramA + " " + bigramB;
		return lookUpWordList(NRCSentimentPairSet, bigram + "---" + unigram);
	}

	public float lookUpNRCHashtagSentimentBigramUnigramPair(String pair) {
		return lookUpWordList(NRCSentimentPairSet, pair);
	}

	public float lookUpNRCHashtagSentimentUnigramBigramPair(String unigram, 
																   String bigramA, String bigramB) {
		String bigram = bigramA + " " + bigramB;
		return lookUpWordList(NRCSentimentPairSet, unigram + "---" + bigram);
	}

	public float lookUpNRCHashtagSentimentUnigramBigramPair(String pair) {
		return lookUpWordList(NRCSentimentPairSet, pair);
	}

	public float lookUpSentiment140Unigram(String unigram) {
		return lookUpWordList(Sentiment140UnigramSet, unigram);
	}

	static float lookUpSentiment140Bigram(String unigramA, String unigramB) {
		return lookUpWordList(Sentiment140BigramSet, unigramA + " " + unigramB);
	}

	public float lookUpSentiment140Bigram(String bigram) {
		return lookUpWordList(Sentiment140BigramSet, bigram);
	}

	static float lookUpSentiment140UnigramUnigramPair(String unigramA, String unigramB) {
		return lookUpWordList(Sentiment140PairSet, unigramA + "---" + unigramB);
	}
	
	public float lookUpSentiment140UnigramUnigramPair(String pair) {
		return lookUpWordList(Sentiment140PairSet, pair);
	}

	static float lookUpSentiment140BigramBigramPair(String bigramAA, String bigramAB,
															String bigramBA, String bigramBB) {
		String bigramA = bigramAA + " " + bigramAB,
				bigramB = bigramBA + " " + bigramBB;
		float score = lookUpWordList(Sentiment140PairSet, bigramA + "---" + bigramB);
		if (score!=0) return score;
		return lookUpWordList(Sentiment140PairSet, bigramB + "---" + bigramA);
	}
	
	public float lookUpSentiment140BigramBigramPair(String pair) {
		return lookUpWordList(Sentiment140PairSet, pair);
	}

	public float lookUpSentiment140BigramUnigramPair(String bigramA, String bigramB,
															String unigram) {
		String bigram = bigramA + " " + bigramB;
		return lookUpWordList(Sentiment140PairSet, bigram + "---" + unigram);
	}
	
	public float lookUpSentiment140BigramUnigramPair(String pair) {
		return lookUpWordList(Sentiment140PairSet, pair);
	}

	public float lookUpSentiment140UnigramBigramPair(String unigram, 
															String bigramA, String bigramB) {
		String bigram = bigramA + " " + bigramB;
		return lookUpWordList(Sentiment140PairSet, unigram + "---" + bigram);
	}
	
	public float lookUpSentiment140UnigramBigramPair(String pair) {
		return lookUpWordList(Sentiment140PairSet, pair);
	}

	private static float lookUpWordList(Map<String, Float> wordList, String item) {
		if (wordList.containsKey(item))
			return wordList.get(item);
		else
			return 0.0f;
	}

	public boolean isInNRCLexicon(String annotationType, String word) {
		if (NRCWordSet.get(annotationType).contains(word))
			return true;
		return false;
	}
	
	public boolean existsInSubjectLexicon(Token token, String subjectivityStrength, String label) {
		Map<String, Set<SubjectivityLexiconEntry>> SLset = SubjectivityLexiconWordSet.get(subjectivityStrength);
		String word = token.getSurfaceForm().toLowerCase();
		if (SLset.containsKey(word)) {
			for (SubjectivityLexiconEntry entry: SLset.get(word)) {
				if ( (!entry.isStemmed()) &&
					 (entry.getWord().equals(word)) &&
					 (entry.matchesPoS(token.getPoS())) &&
					 (entry.getPriorPolarity().equals(label)) ) {
					return true;
				}
			}
		}

		if (SLset.containsKey(token.getNormalForm())) {
			for (SubjectivityLexiconEntry entry: SLset.get(token.getNormalForm())) {
				if ( (entry.isStemmed()) &&
					 (entry.getWord().equals(token.getNormalForm())) &&
					 (entry.matchesPoS(token.getPoS())) &&
					 (entry.getPriorPolarity().equals(label)) ) {
					return true;
				}
			}
		}
		return false;
	}

	private static String readFileAsString(File afile) throws IOException {
		StringBuffer stringBuffer = new StringBuffer();
		BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(new FileInputStream(afile), "UTF-8"));
		String line = null;
		while((line = bufferedReader.readLine()) != null)
			stringBuffer.append(line).append("\n");
		bufferedReader.close();
		return stringBuffer.toString();
	}

}
