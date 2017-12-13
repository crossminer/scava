package org.eclipse.crossmeter.sentimentclassifier.opennlptartarus.libsvm.featuretools;

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
