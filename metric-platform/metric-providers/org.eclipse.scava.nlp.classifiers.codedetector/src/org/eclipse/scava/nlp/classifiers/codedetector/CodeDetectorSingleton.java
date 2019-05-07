/*******************************************************************************
 * Copyright (C) 2018 Edge Hill University
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.nlp.classifiers.codedetector;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.eclipse.scava.platform.logging.OssmeterLogger;

import cc.fasttext.FastText;

class CodeDetectorSingleton
{
	private static CodeDetectorSingleton singleton = new CodeDetectorSingleton();
	protected OssmeterLogger logger;
	private FastText codeDetector;
	private String modelPath="/model/model_Mixed_sentences_all.bin";
	
	private CodeDetectorSingleton()
	{
		logger = (OssmeterLogger) OssmeterLogger.getLogger("nlp.classifiers.codedetector");
		try {
			codeDetector = getModelBin();
			logger.info("Model has been sucessfully loaded");
		} catch (IllegalArgumentException | IOException e) {
			logger.error("Error while loading the model:", e);
			e.printStackTrace();
		}
	}
	
	private FastText getModelBin() throws IllegalArgumentException, IOException
	{
		FastText.Factory factory = FastText.DEFAULT_FACTORY;
		ClassLoader cl = getClass().getClassLoader();
		InputStream resource = cl.getResourceAsStream(modelPath);
		if(resource==null)
			throw new FileNotFoundException("The file "+modelPath+" has not been found");
		return factory.load(resource);
	}
	
	public static CodeDetectorSingleton getInstance()
	{
		return singleton;
	}
	
	public FastText getCodeDetector()
	{
		return codeDetector;
	}
}
