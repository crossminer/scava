package org.eclipse.scava.index.indexer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

import org.apache.http.HttpHost;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

class IndexerSingleton {

	private static IndexerSingleton singleton = null;
	private  Properties indexProperties = new Properties();
	private  String hostname;
	private  String scheme;
	private  String clusterName;
	private int port;
	private  int clusterport;
	private  RestHighLevelClient highLevelclient;
	private  Client adminClient;
	
	
	private IndexerSingleton() {

		try {
			
			indexProperties = loadIndexProperties();
			hostname = loadIndexProperties().get("hostname").toString();// hostname
			port = Integer.valueOf(loadIndexProperties().getProperty("port").toString());
			scheme = loadIndexProperties().getProperty("scheme").toString();
			clusterName = loadIndexProperties().getProperty("cluster-name").toString(); // name given to the cluster
			clusterport = Integer.valueOf(loadIndexProperties().getProperty("cluster-port").toString()); // //this is a different value to the 'port' check cluster settings
			highLevelclient = createHighLevelClient(); // This client handles 'High Level requests' such as indexing docs
			adminClient = createAdminClient(); // this client is used to perform administration tasks on ES
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Reads Elasticsearch configuration properties from properties file
	 * 
	 * @return indexProperties
	 */
	private Properties loadIndexProperties() {

		Properties properties = new Properties();
		InputStream iStream = null;
		try {
			// Loading properties file from the path (relative path given here)
			iStream = new FileInputStream(locateProperties());
			properties.load(iStream);

		} catch (IOException e) {

			e.printStackTrace();
		} finally {
			try {
				if (iStream != null) {
					iStream.close();
					return properties;
				}
			} catch (IOException e) {

				e.printStackTrace();
			}
		}
		return properties;
	}
	/**
	 * Loads the mapping for a particular index from the 'mappings'directory
	 * 
	 * @param mapping
	 * @return String
	 */
	private String loadMapping(String mapping) {
		
		String indexmapping = "";
		File file = null;
		try {
			file = new File(locateMappings(mapping));
		} catch (IllegalArgumentException | IOException e1) {
			e1.printStackTrace();
		} 
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line; 
			while ((line = br.readLine()) != null) {
				indexmapping = indexmapping + line;
			  } 
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return indexmapping;
		
	}
	/**
	 * 
	 * Builds an Elasticsearch HighLevel REST client
	 * 
	 * @return RestHighLevelClient
	 */
	private RestHighLevelClient createHighLevelClient() {

		RestHighLevelClient client = new RestHighLevelClient(
				RestClient.builder(new HttpHost(this.hostname, this.port, this.scheme),
						new HttpHost(this.hostname, this.port + 1, this.scheme)));
		return client;

	}
	/**
	 * Creates an Elasticsearch Admin Client 
	 * @return Client
	 */
	private Client createAdminClient() {

		Client client = null;

		Settings settings = Settings.builder().put("client.transport.sniff", true).put("cluster.name", this.clusterName)
				.build();

		try {
			client = new PreBuiltTransportClient(settings)
					.addTransportAddress(new TransportAddress(InetAddress.getByName("localhost"), clusterport));
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
	
	/**
	 * Locates the elasticsearch.properties file within the 'prefs' directory and returns a file path
	 * 
	 * @return String 
	 * @throws IllegalArgumentException
	 * @throws IOException
	 */
	private String locateProperties() throws IllegalArgumentException, IOException {
		String path = getClass().getProtectionDomain().getCodeSource().getLocation().getFile();
		if (path.endsWith("bin/"))
			path = path.substring(0, path.lastIndexOf("bin/"));
		File file = new File(path + "prefs/elasticsearch.properties");
		checkPropertiesFilePath(file.toPath());

		return file.getPath();
	}

	
	/**
	 * Locates the mapping file within the 'mappings' directory and returns a file path
	 * 
	 * @return String 
	 * @throws IllegalArgumentException
	 * @throws IOException
	 */
	private String locateMappings(String mapping) throws IllegalArgumentException, IOException {
		String path = getClass().getProtectionDomain().getCodeSource().getLocation().getFile();
		if (path.endsWith("bin/"))
			path = path.substring(0, path.lastIndexOf("bin/"));
		File file = new File(path + "mappings/"+ mapping);
		checkPropertiesFilePath(file.toPath());

		return file.getPath();
	}
	
	/**
	 * Checks if a file exists
	 * 
	 * @param path
	 */
	private void checkPropertiesFilePath(Path path) {
		
		if (!Files.exists(path)) {
			System.err.println("The file " + path + " has not been found");
		}
	}

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
