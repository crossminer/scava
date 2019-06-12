package org.eclipse.scava.index.indexer;

import java.io.IOException;
import java.net.UnknownHostException;

import org.apache.http.Header;
import org.apache.http.HttpHeaders;
import org.apache.http.message.BasicHeader;
import org.eclipse.scava.platform.logging.OssmeterLogger;
import org.elasticsearch.ElasticsearchStatusException;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.get.GetIndexRequest;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingResponse;
import org.elasticsearch.action.admin.indices.settings.put.UpdateSettingsRequest;
import org.elasticsearch.action.admin.indices.settings.put.UpdateSettingsResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Indexer {

	protected static OssmeterLogger logger;
	private static RestHighLevelClient highLevelClient;
	//private static Client adminClient;

	static {
		IndexerSingleton singleton = IndexerSingleton.getInstance();
		highLevelClient = singleton.getHighLevelclient();
	//	adminClient = singleton.getAdminClient();
		logger = (OssmeterLogger) OssmeterLogger.getLogger("Indexer");
	}

//	/**
//	 * Retrieves all non-default indices from Elasticsearch
//	 * 
//	 * @return List<String>
//	 */
//	private static List<String> getIndices() {
//		List<String> indicesList = new ArrayList<String>();
//		ImmutableOpenMap<String, IndexMetaData> indices = adminClient.admin().cluster().prepareState().get().getState()
//				.getMetaData().getIndices();
//		for (ObjectObjectCursor<String, IndexMetaData> x : indices) {
//			if (!(x.value == null)) {
//				if (!(x.value.getIndex().getName().startsWith("."))) {
//					if (!(indicesList.contains(x.value.getIndex().getName()))) {
//						indicesList.add(x.value.getIndex().getName());
//					}
//				}
//			}
//		}
//		return indicesList;
//	}

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
	 * @throws IOException
	 */
	private static Boolean createIndex(String indexName)  {
		
		
	
		// checks if index exists
		try {
			if (highLevelClient.indices().exists(new GetIndexRequest().indices(indexName), getReadHeaders())) {
				// do nothing already exists
				logger.info("A index for " + indexName + "already exists");
				return true;

			} else {

				CreateIndexRequest request = new CreateIndexRequest(indexName);
				CreateIndexResponse createIndexResponse;

				try {
					createIndexResponse = highLevelClient.indices().create(request, getWriteHeaders());
					
					
					if (createIndexResponse.isAcknowledged() == true) {
						logger.info("The index " + indexName + " has been created");
						return true;
					}

				} catch (ElasticsearchStatusException e) {

					logger.error(indexName + "\tIndexResponse :" + e.getLocalizedMessage());

				} catch (IOException e) {

					logger.error("Issue whilst creating index", e);
				}
			}
			
			
		
		} catch(UnknownHostException e) {
			
			logger.error(e);
			
			
		} catch (IOException e) {
			
			logger.error("Issue whilst creating index", e);
		} 
		
		return false;
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
	private static void addMappingToIndex(String indexName, String documentType, String mapping) {

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
				new BasicHeader("Role", "Write")};
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
				new BasicHeader("Role", "Read")};
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
	private static void index(String indexName, String documentType, String uid, String document) {

		try {
			IndexRequest indexRequest = new IndexRequest();
			indexRequest.index(indexName.toLowerCase()).type(documentType.toLowerCase()).id(uid).source(document,
					XContentType.JSON);
			logger.info("Document (uid: " + uid + ") has been "
					+ highLevelClient.index(indexRequest, getWriteHeaders()).getResult().toString().toLowerCase());

		} catch (IOException io) {

			logger.error(io);
		}
	}

	/**
	 * Closes the HighLevel Rest Client's connection to the Elasticsearch index
	 * 
	 * @throws IOException
	 */
	private static void closeHighLevelClient() {

		try {
			highLevelClient.close();
			logger.info("High Level Client has been closed");
		} catch (IOException io) {

			logger.error(io);
		}
	}


	/**
	 * Closes all clients (HighLevelRest and AdminClients)
	 * 
	 */

	private static void closeAllClients() {

		closeHighLevelClient();
		//closeAdminClient();

	}

	/**
	 * This method is used to index a document within an index.
	 * 
	 * @param indexName
	 * @param mapping
	 * @param documentType
	 * @param uid
	 * @param document
	 * @throws IOException 
	 */

	public static void indexDocument(String indexName, String mapping, String documentType, String uid, String document)  {

		logger.info("Indexing tool has started");
		
			if (createIndex(indexName)) {
				
				//if a mapping is provided add mapping to index
				if (!(mapping.isEmpty()) && (!(mapping.equals(null)))) 
				{
					addMappingToIndex(indexName, documentType, mapping);
				}
					
					index(indexName, documentType, uid, document);
	
			}else {
				
				logger.info("ERROR - " + indexName + " was not created");
				
			}

		logger.info("Indexing tool has finished");
	}

	public static void main(String[] args)  {

		System.out.println(Indexer.highLevelClient);

		Indexer.indexDocument("hello", "", "test", "01010101", "{\n" + "    \"glossary\": {\n"
				+ "        \"title\": \"example glossary\",\n" + "		\"GlossDiv\": {\n"
				+ "            \"title\": \"S\",\n" + "			\"GlossList\": {\n"
				+ "                \"GlossEntry\": {\n" + "                    \"ID\": \"SGML\",\n"
				+ "					\"SortAs\": \"SGML\",\n"
				+ "					\"GlossTerm\": \"Standard Generalized Markup Language\",\n"
				+ "					\"Acronym\": \"SGML\",\n" + "					\"Abbrev\": \"ISO 8879:1986\",\n"
				+ "					\"GlossDef\": {\n"
				+ "                        \"para\": \"A meta-markup language, used to create markup languages such as DocBook.\",\n"
				+ "						\"GlossSeeAlso\": [\"GML\", \"XML\"]\n" + "                    },\n"
				+ "					\"GlossSee\": \"markup\"\n" + "                }\n" + "            }\n"
				+ "        }\n" + "    }\n" + "}");
	
		System.exit(0);
	}

}
