package org.eclipse.scava.crossflow.tests.crawler;

public interface UrlsToAnalyseConsumer {

	public void consumeUrlsToAnalyse(Url url);
	
	/**
	 * wraps consumeUrlsToAnalyse() to provide task status information
	 */
	public void consumeUrlsToAnalyseActual(Url url);

}