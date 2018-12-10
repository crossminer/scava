package org.eclipse.scava.crossflow.tests.crawler;

public interface UrlsConsumer {

	public void consumeUrls(Url url);
	
	/**
	 * wraps consumeUrls() to provide task status information
	 */
	public void consumeUrlsActual(Url url);

}