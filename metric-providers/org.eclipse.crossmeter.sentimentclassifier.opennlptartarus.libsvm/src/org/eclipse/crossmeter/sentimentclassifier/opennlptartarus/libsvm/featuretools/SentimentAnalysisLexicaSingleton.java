package org.eclipse.crossmeter.sentimentclassifier.opennlptartarus.libsvm.featuretools;

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
