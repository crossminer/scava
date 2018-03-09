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

public class OpenNlpTartarusSingleton {

	private static OpenNlpTartarusSingleton singleton = new OpenNlpTartarusSingleton( );
	private static OpenNlpTartarus tagger;
	
	/* A private Constructor prevents any other 
	 * class from instantiating.
	 */
	private OpenNlpTartarusSingleton(){
//      String path = getClass().getProtectionDomain().getCodeSource().getLocation().getFile();
//      if (path.endsWith("bin/"))
//		path = path.substring(0, path.lastIndexOf("bin/"));
		tagger = new OpenNlpTartarus();
    }
	   
	/* Static 'instance' method */
	public static OpenNlpTartarusSingleton getInstance( ) {
		return singleton;
	}
	
	/* Other methods protected by singleton-ness */
	public OpenNlpTartarus getTagger( ) {
		return tagger;
	}
	
}
