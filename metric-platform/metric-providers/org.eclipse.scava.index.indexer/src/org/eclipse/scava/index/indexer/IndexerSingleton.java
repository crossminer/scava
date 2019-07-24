package org.eclipse.scava.index.indexer;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;

import org.apache.http.Header;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.ssl.SSLContexts;
import org.eclipse.scava.platform.logging.OssmeterLogger;
import org.elasticsearch.action.admin.indices.get.GetIndexRequest;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestClientBuilder.HttpClientConfigCallback;
import org.elasticsearch.client.RestHighLevelClient;

class IndexerSingleton {

	protected static OssmeterLogger logger;

	private static IndexerSingleton singleton = null;


	// When running inside CROSSMINER
		private static String hostname = "elasticsearch";
		private static String scheme = "https";
		private static int port = 9200;
	
	private RestHighLevelClient highLevelclient;


	private IndexerSingleton() {

		try {
			logger = (OssmeterLogger) OssmeterLogger.getLogger("Indexer Singleton");
			highLevelclient = createHighLevelClient(hostname); 
			//highLevelclient = createHighLevelClientLocal("localhost");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// /**
	// * Reads Elasticsearch configuration properties from properties file
	// *
	// * @return indexProperties
	// */
	// private Properties loadIndexProperties() {
	//
	// Properties properties = new Properties();
	// InputStream iStream = null;
	// try {
	// // Loading properties file from the path (relative path given here)
	// iStream = new FileInputStream(locateProperties());
	// properties.load(iStream);
	//
	// } catch (IOException e) {
	//
	// e.printStackTrace();
	// } finally {
	// try {
	// if (iStream != null) {
	// iStream.close();
	// return properties;
	// }
	// } catch (IOException e) {
	//
	// e.printStackTrace();
	// }
	// }
	// return properties;
	// }

	/**
	 * 
	 * Builds an Elasticsearch HighLevel REST client
	 * 
	 * @return RestHighLevelClient
	 * @throws KeyStoreException
	 * @throws NoSuchAlgorithmException
	 * @throws KeyManagementException
	 * @throws IOException 
	 */

	private RestHighLevelClient createHighLevelClient(String host)
			throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {


		final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
		credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials("admin", "admin"));

		TrustStrategy trustStrategy = new TrustSelfSignedStrategy();
		final SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(trustStrategy).build();
		final HostnameVerifier hostnameVerifier = NoopHostnameVerifier.INSTANCE;

		RestClientBuilder restClientBuilder = RestClient.builder(new HttpHost(host, port, scheme),
				new HttpHost(host, port + 1, scheme));
		restClientBuilder.setFailureListener(new RestClient.FailureListener() {

			@Override
			public void onFailure(HttpHost host) {
				logger.info("Cluster Connection Failure: Unable to connect to the Scava Elasticsearch cluster");
			}
		});

		restClientBuilder.setHttpClientConfigCallback(new HttpClientConfigCallback() {
			@Override
			public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
				httpClientBuilder.setSSLContext(sslContext).setSSLHostnameVerifier(hostnameVerifier).build();
				httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
				return httpClientBuilder;
			}
		});
		
		restClientBuilder.build();

		RestHighLevelClient client = new RestHighLevelClient(restClientBuilder);
		
		Header[] headers = { new BasicHeader(HttpHeaders.CONTENT_TYPE, "application/json"),
				new BasicHeader("Role", "Read")};
		
		GetIndexRequest request = new GetIndexRequest();
		request.indices("bla");
		
		
		try {
			
			client.indices().exists(request, headers);
			client.close();
			client = new RestHighLevelClient(restClientBuilder);
			
	
		} catch (IOException e) {
			
			logger.error("Error connecting to the Elasticsearch Cluster\n\n" + e);
		//DISABLE LOCAL HOST 	
		//client = createHighLevelClientLocal("localHost");
		

		}
	
		return client;

	}
	
	
	/***
	 * USED WHEN TESTING A LOCAL INSTANCE OF ES
	 * 
	 * 
	 * @param host
	 * @return client
	 */
	private RestHighLevelClient createHighLevelClientLocal(String host) {
		
		
		RestClientBuilder restClientBuilder = RestClient.builder(new HttpHost(host, port, "http"),
				new HttpHost(host, port + 1, "http"));
		restClientBuilder.setFailureListener(new RestClient.FailureListener() {

			@Override
			public void onFailure(HttpHost host) {
				logger.info("Development (Local Instance) Client Connection Failure");
			}
		});


		
		restClientBuilder.build();

		RestHighLevelClient client = new RestHighLevelClient(restClientBuilder);
		
		Header[] headers = { new BasicHeader(HttpHeaders.CONTENT_TYPE, "application/json"),
				new BasicHeader("Role", "Read")};
		
		GetIndexRequest request = new GetIndexRequest();
		request.indices("bla");
		
		
		try {
			
			client.indices().exists(request, headers);
			client.close();
			
	
		} catch (IOException e) {
			logger.error("Unable to connect to development Elastic search instance\n" + e);
			
		}
	

		return new RestHighLevelClient(restClientBuilder);

	}



	// ------------------------------------------------------------------------------------------
	// ------------------------------------------------------------------------------------------

	// PUBLIC GETTERS

	// ------------------------------------------------------------------------------------------
	// ------------------------------------------------------------------------------------------

	public static IndexerSingleton getInstance() {

		if (singleton == null) {

			synchronized (IndexerSingleton.class) {

				if (singleton == null) {

					singleton = new IndexerSingleton();
				}
			}

		}

		return singleton;
	}

	public RestHighLevelClient getHighLevelclient() {
		return highLevelclient;
	}


}


// ------------------------------------------------------------------------------------------
// ------------------------------------------------------------------------------------------

// MISC HELPER METHODS

// ------------------------------------------------------------------------------------------
// ------------------------------------------------------------------------------------------

// /**
// * Locates the elasticsearch.properties file within the 'prefs' directory and
// returns a file path
// *
// * @return String
// * @throws IllegalArgumentException
// * @throws IOException
// */
// private String locateProperties() throws IllegalArgumentException,
// IOException {
//
// String path =
// getClass().getProtectionDomain().getCodeSource().getLocation().getFile();
// if (path.endsWith("bin/"))
// path = path.substring(0, path.lastIndexOf("bin/"));
// File file = new File(path + "prefs/elasticsearch.properties");
// checkPropertiesFilePath(file.toPath());
//
// return file.getPath();
//
//
// }

// /**
// * Checks if a file exists
// *
// * @param path
// */
// private void checkPropertiesFilePath(Path path) {
//
// if (!Files.exists(path)) {
// System.err.println("The file " + path + " has not been found");
// }
// }
