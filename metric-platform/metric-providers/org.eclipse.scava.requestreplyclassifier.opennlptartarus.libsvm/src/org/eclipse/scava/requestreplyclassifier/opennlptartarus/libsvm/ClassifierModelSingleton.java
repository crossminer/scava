/*******************************************************************************
 * Copyright (c) 2017 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.requestreplyclassifier.opennlptartarus.libsvm;

import org.eclipse.scava.libsvm.svm_predict_nofiles;

import libsvm.svm_model;


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
		String argumentString = "-b 1 " + path + "classifierFiles/Test-IntegerFeatures-Clean-NJ.rbf.m";
		model = svm_predict_nofiles.parse_args_and_load_model(argumentString.split(" "));
		System.err.println("Request-reply classification model loaded");
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
