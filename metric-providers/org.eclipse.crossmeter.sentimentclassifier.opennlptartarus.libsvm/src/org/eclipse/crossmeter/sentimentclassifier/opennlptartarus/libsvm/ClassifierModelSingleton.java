package org.eclipse.crossmeter.sentimentclassifier.opennlptartarus.libsvm;

import libsvm.svm_model;

import org.eclipse.crossmeter.libsvm.svm_predict_nofiles;


public class ClassifierModelSingleton {

	private static ClassifierModelSingleton singleton = new ClassifierModelSingleton( );
	private static svm_model model;
	
	/* A private Constructor prevents any other 
	 * class from instantiating.
	 */
	private ClassifierModelSingleton(){
		String path = getClass().getProtectionDomain().getCodeSource().getLocation().getFile();
		if (path.endsWith("bin/"))
			path = path.substring(0, path.lastIndexOf("bin/"));
		String argumentString = "-b 1 " + path + "classifierFiles/Test-SentimentAnalysis-40.rbf.m";
		model = svm_predict_nofiles.parse_args_and_load_model(argumentString.split(" "));
		System.err.println("Sentiment classification model loaded");
    }
	   
	/* Static 'instance' method */
	public static ClassifierModelSingleton getInstance( ) {
		return singleton;
	}
	
	/* Other methods protected by singleton-ness */
	public svm_model getModel( ) {
		return model;
	}
	
}
