package org.eclipse.scava.index.indexer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpHeaders;
import org.apache.http.message.BasicHeader;
import org.eclipse.scava.platform.logging.OssmeterLogger;
import org.elasticsearch.ElasticsearchStatusException;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingResponse;
import org.elasticsearch.action.admin.indices.settings.put.UpdateSettingsRequest;
import org.elasticsearch.action.admin.indices.settings.put.UpdateSettingsResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.cluster.metadata.IndexMetaData;
import org.elasticsearch.common.collect.ImmutableOpenMap;
import org.elasticsearch.common.xcontent.XContentType;

import com.carrotsearch.hppc.cursors.ObjectObjectCursor;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Indexer {

	protected static OssmeterLogger logger;
	private static RestHighLevelClient highLevelClient;
	private static Client adminClient;
	

	static {
		IndexerSingleton singleton = IndexerSingleton.getInstance();
		highLevelClient = singleton.getHighLevelclient();
		adminClient = singleton.getAdminClient();
		logger = (OssmeterLogger) OssmeterLogger.getLogger("Indexer");
	}

	/**
	 * Retrieves all non-default indices from Elasticsearch
	 * 
	 * @return List<String>
	 */
	private static List<String> getIndices() {
		List<String> indicesList = new ArrayList<String>();
		ImmutableOpenMap<String, IndexMetaData> indices = adminClient.admin().cluster().prepareState().get().getState()
				.getMetaData().getIndices();
		for (ObjectObjectCursor<String, IndexMetaData> x : indices) {
			if (!(x.value == null)) {
				if (!(x.value.getIndex().getName().startsWith("."))) {
					if (!(indicesList.contains(x.value.getIndex().getName()))) {
						indicesList.add(x.value.getIndex().getName());
					}
				}
			}
		}
		return indicesList;
	}

	/**
	 * Adds settings to index
	 * 
	 * @param index
	 *            - name of the index
	 * @param setting
	 *            - settings represented as a JSON String
	 * @throws IOException
	 */
	private static void addIndexSetting(String index, String setting) throws IOException {
		UpdateSettingsRequest updateSettingsRequest = new UpdateSettingsRequest();
		updateSettingsRequest.indices(index);
		updateSettingsRequest.settings(setting, XContentType.JSON);
		UpdateSettingsResponse updateSettingsResponse = highLevelClient.indices().putSettings(updateSettingsRequest,
				getWriteHeaders());
		if (updateSettingsResponse.isAcknowledged() == true) {
			System.out.println("[INDEXER] \tSettings have been added to " + index);
		}
	}

	/**
	 * Creates a new index
	 * 
	 * @param indexName
	 *            - name of the new index
	 */
	private void createIndex(String indexName) {

		
		CreateIndexRequest request = new CreateIndexRequest(indexName);
		CreateIndexResponse createIndexResponse;

		try {
			createIndexResponse = highLevelClient.indices().create(request, getWriteHeaders());

			
			if (createIndexResponse.isAcknowledged() == true) {

				logger.info("The index " + indexName + " has been created");

			}

		} catch (ElasticsearchStatusException e) {

			logger.error(indexName + "\tIndexResponse :" + e.getLocalizedMessage());

		} catch (IOException e) {

			logger.error(e);
		}
		
	}

	/**
	 * Adds a document map (mapping) to a specific index
	 * 
	 * @param index
	 *            - index name
	 * @param type
	 *            - doucment type
	 * @param mapping
	 *            - a JSON document represented as a string
	 */
	private void addMappingToIndex(String indexName, String documentType, String mapping) {

		PutMappingRequest putMappingRequest = new PutMappingRequest(indexName.toLowerCase());
		putMappingRequest.source(mapping, XContentType.JSON).type(documentType.toLowerCase());
		PutMappingResponse putMappingResponse;
		
		try {
			
			putMappingResponse = highLevelClient.indices().putMapping(putMappingRequest, getWriteHeaders());
			
			if (putMappingResponse.isAcknowledged() == true) {
			
				logger.info("Mapping for " + documentType + " in " + indexName + " was added successfully");
			
			}
			
		} catch (IOException e) {
			
			logger.error(e);
		}
	}

	/**
	 * Returns a basic HTTP write headers
	 * 
	 * 
	 * @return headers
	 */
	private static Header[] getWriteHeaders() {

		Header[] headers = { new BasicHeader(HttpHeaders.CONTENT_TYPE, "application/json"),
				new BasicHeader("Role", "Write") };
		return headers;
	}

	/**
	 * Returns a basic HTTP read headers
	 * 
	 * 
	 * @return headers
	 */
	private static Header[] getReadHeaders() {

		Header[] headers = { new BasicHeader(HttpHeaders.CONTENT_TYPE, "application/json"),
				new BasicHeader("Role", "Read") };
		return headers;
	}

	/**
	 * @param reposiotry
	 *            - name of the repository. For example github, mantis
	 * @param documentType
	 *            - document type. For example article, bug, post, comment.
	 * @param knowledgeType
	 *            - type of knowledge. for example nlp
	 * @return indexName - generated name for the index
	 */
	public static String generateIndexName(String reposiotry, String documentType, String knowledgeType) {

		reposiotry = reposiotry.replaceAll(" ", "").trim().toLowerCase();
		documentType = documentType.replaceAll(" ", "").trim().toLowerCase();
		knowledgeType = knowledgeType.replace(" ", "").trim().toLowerCase();

		return reposiotry + "." + documentType + "." + knowledgeType;
	}

	/**
	 * Maps document Object to Json and creates and new index request
	 * 
	 * @param indexName
	 *            - index name
	 * @param documentType
	 *            - document type
	 * @param uid
	 *            - unique identifier
	 * @param object
	 *            - object that represents the structure of a document for indexing
	 * @return
	 * @return IndexResponse
	 * @throws IOException
	 */
	private void index(String indexName, String documentType, String uid, String document) {

		try {
			
			
			IndexRequest indexRequest = new IndexRequest();
			indexRequest.index(indexName.toLowerCase()).type(documentType.toLowerCase()).id(uid).source(document,XContentType.JSON);
			logger.info("Document (uid: " + uid + ") has been " + highLevelClient.index(indexRequest, getWriteHeaders()).getResult().toString().toLowerCase());

		} catch (IOException io) {
			
			logger.error(io);
		}
	}

	/**
	 * Closes the HighLevel Rest Client's connection to the Elasticsearch index
	 * 
	 * @throws IOException
	 */
	private void closeHighLevelClient() {

		try {
		highLevelClient.close();
		logger.info("High Level Client has been closed");
		}catch(IOException io) {

			logger.error(io);
		}
	}

	/**
	 * Closes the Admin Client
	 */
	private void closeAdminClient() {

		adminClient.close();
		logger.info("Admin Client has been closed");
	}

	/**
	 * Closes all clients (HighLevelRest and AdminClients)
	 * 
	 */

	private void closeAllClients()  {

			closeHighLevelClient();
			closeAdminClient();
	
	}

	/**
	 * This method maps a objects attributes and returns a string in a JSON
	 * structure.
	 * 
	 * @param object
	 * @return string
	 * @throws com.fasterxml.jackson.core.JsonProcessingException
	 */

	private static String objectToJson(Object object) throws com.fasterxml.jackson.core.JsonProcessingException {

		ObjectMapper mapper = new ObjectMapper();
		String jsonString = mapper.writeValueAsString(object);
		return jsonString;
	}

	/**
	 * This method is used to index a document within an index.
	 * 
	 * @param indexName
	 * @param mapping
	 * @param documentType
	 * @param uid
	 * @param document
	 */

	public void indexDocument(String indexName, String mapping, String documentType, String uid, String document) {
		
	
		logger.info("Beginning Indexing");
		
		if (!(getIndices().contains(indexName))) {

			createIndex(indexName);

			if (!(mapping.isEmpty()) && (!(mapping.equals(null)))) {

				addMappingToIndex(indexName, documentType, mapping);

			}
		
			index(indexName, documentType, uid, document);
		
		} else {
			
			logger.info("Index " + indexName + " already exists");

			index(indexName, documentType, uid, document);

		}
	
		logger.info("Indexing Complete");
	}
}
