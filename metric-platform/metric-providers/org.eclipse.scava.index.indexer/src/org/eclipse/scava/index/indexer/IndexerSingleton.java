package org.eclipse.scava.index.indexer;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.http.HttpHost;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

class IndexerSingleton {

	private static IndexerSingleton singleton = null;
	//private  Properties indexProperties = new Properties();
	private  static String hostname = "localhost";//"elasticsearch";
	private  static String scheme = "http";
	private  static String clustername = "elasticsearch";//"bitergia_elasticsearch";
	private  static int port = 9200;
	private  static int clusterport = 9300;
	private  RestHighLevelClient highLevelclient;
	private  Client adminClient;
	
	
	private IndexerSingleton() {

		try {
			//setProperites();
			//indexProperties.entrySet();
			//indexProperties = loadIndexProperties();
//			hostname = "elasticsearch"; //loadIndexProperties().get("hostname").toString();// hostname
//			port = 9200;//Integer.valueOf(loadIndexProperties().getProperty("port").toString());
//			scheme = "http";//loadIndexProperties().getProperty("scheme").toString();
//			clusterName = "bitergia_elasticsearch"; //loadIndexProperties().getProperty("cluster-name").toString(); // name given to the cluster
//			clusterport = 9300; //Integer.valueOf(loadIndexProperties().getProperty("cluster-port").toString()); // //this is a different value to the 'port' check cluster settings
			highLevelclient = createHighLevelClient(); // This client handles 'High Level requests' such as indexing docs
			adminClient = createAdminClient(); // this client is used to perform administration tasks on ES
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	

//	/**
//	 * Reads Elasticsearch configuration properties from properties file
//	 * 
//	 * @return indexProperties
//	 */
//	private Properties loadIndexProperties() {
//
//		Properties properties = new Properties();
//		InputStream iStream = null;
//		try {
//			// Loading properties file from the path (relative path given here)
//			iStream = new FileInputStream(locateProperties());
//			properties.load(iStream);
//
//		} catch (IOException e) {
//
//			e.printStackTrace();
//		} finally {
//			try {
//				if (iStream != null) {
//					iStream.close();
//					return properties;
//				}
//			} catch (IOException e) {
//
//				e.printStackTrace();
//			}
//		}
//		return properties;
//	}

	/**
	 * 
	 * Builds an Elasticsearch HighLevel REST client
	 * 
	 * @return RestHighLevelClient
	 */
	private RestHighLevelClient createHighLevelClient() {

		RestHighLevelClient client = new RestHighLevelClient(
				RestClient.builder(new HttpHost(hostname, port, scheme),
						new HttpHost(hostname, port + 1, scheme)));
		return client;

	}
	/**
	 * Creates an Elasticsearch Admin Client 
	 * @return Client
	 */
	
	private Client createAdminClient() {

		Client client = null;

		Settings settings = Settings.builder().put("client.transport.sniff", true).put("cluster.name", clustername)
				.build();

		try {
			client = new PreBuiltTransportClient(settings)
					.addTransportAddress(new TransportAddress(InetAddress.getByName(hostname), clusterport));
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	

		return client;
	}

	// ------------------------------------------------------------------------------------------
	// ------------------------------------------------------------------------------------------
	
		// MISC HELPER METHODS
	
	// ------------------------------------------------------------------------------------------
	// ------------------------------------------------------------------------------------------
	
//	/**
//	 * Locates the elasticsearch.properties file within the 'prefs' directory and returns a file path
//	 * 
//	 * @return String 
//	 * @throws IllegalArgumentException
//	 * @throws IOException
//	 */
//	private String locateProperties() throws IllegalArgumentException, IOException {
//		
//		String path = getClass().getProtectionDomain().getCodeSource().getLocation().getFile();
//		if (path.endsWith("bin/"))
//			path = path.substring(0, path.lastIndexOf("bin/"));
//		File file = new File(path + "prefs/elasticsearch.properties");
//		checkPropertiesFilePath(file.toPath());
//
//		return file.getPath();
//		
//	
//	}


	
	
//	/**
//	 * Checks if a file exists
//	 * 
//	 * @param path
//	 */
//	private void checkPropertiesFilePath(Path path) {
//		
//		if (!Files.exists(path)) {
//			System.err.println("The file " + path + " has not been found");
//		}
//	}

	// ------------------------------------------------------------------------------------------
	// ------------------------------------------------------------------------------------------
	
		//PUBLIC GETTERS 
	
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

	
	
	public  RestHighLevelClient getHighLevelclient() {
		return highLevelclient;
	}

	public  Client getAdminClient() {
		return adminClient;
	}
	
}
