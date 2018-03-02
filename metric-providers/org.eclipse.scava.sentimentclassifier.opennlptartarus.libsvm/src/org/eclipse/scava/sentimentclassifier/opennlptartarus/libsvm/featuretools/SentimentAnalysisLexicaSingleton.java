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

public class SentimentAnalysisLexicaSingleton {

	private static SentimentAnalysisLexicaSingleton singleton = new SentimentAnalysisLexicaSingleton( );
	private static SentimentAnalysisLexica sentimentAnalysisLexica;
	
	private SentimentAnalysisLexicaSingleton(){
		sentimentAnalysisLexica = new SentimentAnalysisLexica();
		sentimentAnalysisLexica.load();
    }
	   
	public static SentimentAnalysisLexicaSingleton getInstance( ) {
		return singleton;
	}
	
	public SentimentAnalysisLexica getLexica( ) {
		return sentimentAnalysisLexica;
	}

}
