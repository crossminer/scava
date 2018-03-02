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

public class CMUclustersSingleton {

	private static CMUclustersSingleton singleton = new CMUclustersSingleton( );
	private static CMUclusters cmuClusters;
	
	private CMUclustersSingleton(){
		cmuClusters = new CMUclusters();
		cmuClusters.load();
    }
	   
	public static CMUclustersSingleton getInstance( ) {
		return singleton;
	}
	
	public CMUclusters getClusters( ) {
		return cmuClusters;
	}

}
