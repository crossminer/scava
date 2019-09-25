/*******************************************************************************
 * Copyright (c) 2019 Edge Hill University
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.index.indexer;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.http.ssl.SSLContexts;
import org.eclipse.scava.platform.logging.OssmeterLogger;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestClientBuilder.HttpClientConfigCallback;
import org.elasticsearch.client.RestHighLevelClient;

class IndexerSingleton {

	protected static OssmeterLogger logger;

	private static IndexerSingleton singleton = null;

	private static String hostname = "elasticsearch";
	private static String scheme = "https";
	private static int port = 9200;
	
	private RestHighLevelClient client;


	private IndexerSingleton() {

		boolean clientFound;
		logger = (OssmeterLogger) OssmeterLogger.getLogger("Indexer Singleton");
		clientFound=createClientDocker();
		try {
			if(!clientFound)
			{
				client.close();
				logger.info("Searching for a local instance of ElasticSearch");
				clientFound=createClientLocal();
				if(!clientFound)
				{
					logger.error("No instance of ElasticSearch has been found either in localhost or at "+hostname + " on port " + port);
					client.close();
					client=null;
				}
				else
					logger.info("ElasticSearch has been found");
			}
			else
				logger.info("ElasticSearch has been found");
		} catch (IOException e) {
			logger.error("Error while creating client for ElasticSearch: ", e);
		}
	}
	
	private boolean createClientDocker()
	{
		CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
		credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials("admin", "admin"));
		
		TrustStrategy trustStrategy = new TrustSelfSignedStrategy();
		SSLContext sslContext;
		try {
			sslContext = SSLContexts.custom().loadTrustMaterial(trustStrategy).build();
			HostnameVerifier hostnameVerifier = NoopHostnameVerifier.INSTANCE;
			
			RestClientBuilder restClientBuilder = createRestClientBuilder(hostname, scheme);
			
			restClientBuilder.setHttpClientConfigCallback(new HttpClientConfigCallback() {
				@Override
				public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
					httpClientBuilder.setSSLContext(sslContext).setSSLHostnameVerifier(hostnameVerifier).build();
					httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
					return httpClientBuilder;
				}
			});

			return createHighLevelClient(restClientBuilder);
		} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException e) {
			logger.error("Error while creating secure connection to ElasticSearch: ", e);
		}
		
		return false;
	}
	
	private boolean createClientLocal()
	{
		return createHighLevelClient(createRestClientBuilder("localhost", "http")); 
	}
	
	private boolean createHighLevelClient(RestClientBuilder restClientBuilder)
	{
		restClientBuilder.build();
		client = new RestHighLevelClient(restClientBuilder);
		return testClient();
	}
	
	private RestClientBuilder createRestClientBuilder(String host, String scheme)
	{
		RestClientBuilder restClientBuilder = RestClient.builder(new HttpHost(host, port, scheme),
				new HttpHost(host, port + 1, scheme));
		restClientBuilder.setFailureListener(new RestClient.FailureListener() {

			@Override
			public void onFailure(HttpHost host) {
				logger.info("Cluster Connection Failure: Unable to connect to the Scava Elasticsearch cluster");
			}
		});
		
		return restClientBuilder;
	}
	
	private boolean testClient()
	{
		try {
			return client.ping();
		} catch (IOException e) {
			return false;
		}
	}
	


	// PUBLIC GETTERS

	// ------------------------------------------------------------------------------------------
	// ------------------------------------------------------------------------------------------

	synchronized public static IndexerSingleton getInstance() {

		if (singleton == null) {
			singleton = new IndexerSingleton();
		}

		return singleton;
	}

	public RestHighLevelClient getHighLevelclient() {
		return client;
	}


}