package org.eclipse.scava.business.impl.crossindex.elasticsearch;

import java.io.IOException;
import java.net.UnknownHostException;
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
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestClientBuilder.HttpClientConfigCallback;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

class ElasticSearchSingleton {

	private static ElasticSearchSingleton singleton=null;
	
	@Value("${CROSSIndexRecommender.elasticsearch.hostname}")
	private final static String hostname = "elasticsearch";
	@Value("${CROSSIndexRecommender.elasticsearch.scheme}")
	private final static String scheme = "https";
	@Value("${CROSSIndexRecommender.elasticsearch.port}")
	private final static int port = 9200;
	
	private static RestHighLevelClient client;
	
	private final static Logger logger = LoggerFactory.getLogger(ElasticSearchSingleton.class);
	
	private ElasticSearchSingleton()
	{
		boolean clientFound;
		clientFound=createClientDocker();
		try {
			if(!clientFound)
			{
				client.close();
				clientFound=createClientLocal();
				if(!clientFound)
				{
					logger.error("No instance of ElasticSearch has been found either in localhost or at "+hostname + " on port " + port);
					client.close();
					client=null;
				}
			}
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
	
	synchronized public static ElasticSearchSingleton getInstance() {
		if(singleton==null) {
			singleton = new ElasticSearchSingleton();
		}
		return singleton;
	}
	
	public RestHighLevelClient getClient() {
		return client;
	}
}
