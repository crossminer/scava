/*******************************************************************************
 * Copyright (c) 2017 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package uk.ac.nactem.posstemmer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import opennlp.tools.cmdline.postag.POSModelLoader;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.tokenize.SimpleTokenizer;

import org.tartarus.snowball.SnowballStemmer;
import org.tartarus.snowball.ext.englishStemmer;


public class OpenNlpTartarus {
	
	private static SentenceDetectorME sentenceDetector;

//	A different tokenizer can be invoked by uncommented commented lines.
//	private static TokenizerME tokenizerME;
	private static SimpleTokenizer simpleTokenizer;

	private static POSTaggerME posTaggerME;
	
	private static SnowballStemmer stemmer; 
	
	public OpenNlpTartarus() {
		String path = getClass().getProtectionDomain().getCodeSource().getLocation().getFile();
		if (path.endsWith("bin/"))
			path = path.substring(0, path.lastIndexOf("bin/"));
		
		System.err.println("path: " + path);
		posTaggerME = loadPoSME(path + "models/en-pos-maxent.bin");

//		InputStream tokenizerModelInput = loadModelInput("models/en-token.bin");
//		TokenizerModel tokenizerModel = loadTokenizerModel(tokenizerModelInput);
//		tokenizerME = new TokenizerME(tokenizerModel);

		simpleTokenizer = SimpleTokenizer.INSTANCE;
		
		InputStream sentenceModelInput = loadModelInput(path + "models/en-sent.bin");
		SentenceModel sentenceModel = loadSentenceModel(sentenceModelInput);
		sentenceDetector = new SentenceDetectorME(sentenceModel);
		
		stemmer = new englishStemmer();
	}
	
	private static final int MAX_CHARACTERS_IN_WORD = 30;
	
	public List<List<Token>> tag(String text) {
		
		String cleanedText = "";
		String[] components = text.split("\\s+");
		for (String component: components) {
			if (component.length() < MAX_CHARACTERS_IN_WORD) {
				if (cleanedText.length() > 0) cleanedText += " ";
				cleanedText += component;
			}
		}
		List<List<Token>> tokenSentences = new ArrayList<List<Token>>();
		synchronized(this) {
			for (String sentence: sentenceDetector.sentDetect(cleanedText)) {
//			String[] tokenised = tokenizerME.tokenize(sentence);
				String[] tokenised = simpleTokenizer.tokenize(sentence);
				String[] tags = posTaggerME.tag(tokenised);
				tokenSentences.add(tagToTokens(tokenised, tags));
			}
		}
		return tokenSentences;
	}
	
	private POSTaggerME loadPoSME(String filename) {
		File file = new File(filename);
		POSModelLoader posModelLoader = new POSModelLoader();
		POSModel model = posModelLoader.load(file);
		POSTaggerME posTagger = new POSTaggerME(model);
		return posTagger;
	}

	private FileInputStream loadModelInput(String modelFilename) {
		FileInputStream tokenisationModelInput = null;
		try {
			tokenisationModelInput = new FileInputStream(modelFilename);
		} catch (FileNotFoundException e) {
			System.err.println("Cannot read tokenisation model file!");
			e.printStackTrace();
		}
		return tokenisationModelInput;
	}

//	private TokenizerModel loadTokenizerModel(InputStream tokenizerModelInput) {
//		TokenizerModel tokenizerModel = null;
//		try { 
//			tokenizerModel = new TokenizerModel(tokenizerModelInput);
//		} catch (IOException e) { 
//			System.err.println("Cannot load tokenizer model!");
//			e.printStackTrace(); 
//		} finally {
//			if (tokenizerModelInput != null) {
//				try { 
//					tokenizerModelInput.close(); 
//				} catch (IOException e) {
//					System.err.println("Cannot close tokenizer input file!");
//				}
//			}
//		}
//		return tokenizerModel;
//	}

	private SentenceModel loadSentenceModel(InputStream sentenceModelInput) {
		SentenceModel sentenceModel = null;
		try { 
			sentenceModel = new SentenceModel(sentenceModelInput);
		} catch (IOException e) { 
			System.err.println("Cannot load sentence model!");
			e.printStackTrace(); 
		} finally {
			if (sentenceModelInput != null) {
				try { 
					sentenceModelInput.close(); 
				} catch (IOException e) {
					System.err.println("Cannot close sentence input file!");
				}
			}
		}
		return sentenceModel;
	}

	private List<Token> tagToTokens(String[] tokenised, String[] tags) {
		List<Token> tokens = new ArrayList<Token>();
		for (int index=0; index<tokenised.length; index++){
			String surface = tokenised[index];
			stemmer.setCurrent(surface.toLowerCase());
			stemmer.stem();
			tokens.add(new Token(surface, stemmer.getCurrent(), tags[index]));
		}
		return tokens;
	}
}
