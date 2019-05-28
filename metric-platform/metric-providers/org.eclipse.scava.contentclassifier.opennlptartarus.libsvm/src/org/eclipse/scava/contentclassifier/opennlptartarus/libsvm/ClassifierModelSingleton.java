/*******************************************************************************
 * Copyright (c) 2019 Edge Hill University
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.contentclassifier.opennlptartarus.libsvm;

import org.eclipse.scava.libsvm.svm_predict_nofiles;
import org.eclipse.scava.platform.logging.OssmeterLogger;
import libsvm.svm_model;

class ClassifierModelSingleton {

	private static ClassifierModelSingleton singleton = new ClassifierModelSingleton( );
	private svm_model model;
	protected OssmeterLogger logger;
	
	/* A private Constructor prevents any other 
	 * class from instantiating.
	 */
	private ClassifierModelSingleton(){
		logger = (OssmeterLogger) OssmeterLogger.getLogger("contentclassifier.opennlptartarus.libsvm");
		model = svm_predict_nofiles.parse_args_and_load_model(getClass(), "classifierFiles", "Test-TfIdfFeatures-Clean-AllPoS-BeginningOnly-.m", logger);
		System.err.println("Severity classification model loaded");
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
