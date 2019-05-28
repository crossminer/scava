/*******************************************************************************
 * Copyright (c) 2019 Edge Hill University
 * Copyright (c) 2017 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package uk.ac.nactem.posstemmer;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.eclipse.scava.platform.logging.OssmeterLogger;
import org.tartarus.snowball.SnowballStemmer;
import org.tartarus.snowball.ext.englishStemmer;

import opennlp.tools.cmdline.postag.POSModelLoader;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.tokenize.SimpleTokenizer;
import opennlp.tools.util.InvalidFormatException;


public class OpenNlpTartarus {
	
	private static SentenceDetectorME sentenceDetector;

//	A different tokenizer can be invoked by uncommented commented lines.
//	private static TokenizerME tokenizerME;
	private static SimpleTokenizer simpleTokenizer;

	private static POSTaggerME posTaggerME;
	
	private static SnowballStemmer stemmer; 
	protected OssmeterLogger logger;
	
	
	public OpenNlpTartarus() {
		
		logger = (OssmeterLogger) OssmeterLogger.getLogger("uk.ac.nactem.posstemmer");
		
		ClassLoader cl = getClass().getClassLoader();
		try {
			posTaggerME = loadPoSME(cl, "models/en-pos-maxent.bin");
			simpleTokenizer = SimpleTokenizer.INSTANCE;
			SentenceModel sentenceModel = loadSentenceModel(cl, "models/en-sent.bin");
			sentenceDetector = new SentenceDetectorME(sentenceModel);
			logger.info("Models have been sucessfully loaded");
		} catch (IOException e) {
			logger.error("Error while loading the model:", e);
			e.printStackTrace();
		}

//		InputStream tokenizerModelInput = loadModelInput("models/en-token.bin");
//		TokenizerModel tokenizerModel = loadTokenizerModel(tokenizerModelInput);
//		tokenizerME = new TokenizerME(tokenizerModel);


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
	
	private POSTaggerME loadPoSME(ClassLoader cl, String filename) throws IOException {
		InputStream resource = loadModelInput(cl, filename);
		POSModelLoader posModelLoader = new POSModelLoader();
		POSModel model = posModelLoader.load(createTempFile(resource, "bin"));
		POSTaggerME posTagger = new POSTaggerME(model);
		if(resource!=null)
			resource.close();
		return posTagger;
	}
	
	private InputStream loadModelInput(ClassLoader cl, String filename) throws IOException {
		InputStream resource = cl.getResourceAsStream("/" + filename);
		if(resource==null)
			throw new FileNotFoundException("The file "+filename+" has not been found");
		return resource;
	}
	
	private static File createTempFile(InputStream inputStream, String extension) throws IOException
	{
		File tmpFile = File.createTempFile("openNlpTartarus", extension);
		try {
			BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(tmpFile));
	        IOUtils.copy(inputStream, bufferedOutputStream);
	        bufferedOutputStream.flush();
	        IOUtils.closeQuietly(bufferedOutputStream);
	        return tmpFile;
		} catch (IOException e)
		{
            if(tmpFile != null){
                tmpFile.delete();
            }
            throw e;
        }
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

	private SentenceModel loadSentenceModel(ClassLoader cl, String filename) throws InvalidFormatException, IOException {
		InputStream sentenceModelInput= loadModelInput(cl, filename);
		SentenceModel sentenceModel = null;
		sentenceModel = new SentenceModel(sentenceModelInput);
		if (sentenceModelInput != null) {
			sentenceModelInput.close(); 
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
