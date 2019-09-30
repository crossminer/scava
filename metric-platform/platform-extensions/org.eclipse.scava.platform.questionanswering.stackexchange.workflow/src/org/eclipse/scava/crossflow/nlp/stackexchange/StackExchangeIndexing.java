package org.eclipse.scava.crossflow.nlp.stackexchange;

import org.eclipse.scava.crossflow.nlp.stackexchange.gen.Document;
import org.eclipse.scava.crossflow.nlp.stackexchange.gen.IndexerBase;
import org.eclipse.scava.crossflow.nlp.stackexchange.gen.Post;
import org.eclipse.scava.index.indexer.Indexer;
import org.eclipse.scava.index.indexer.MappingStorage;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class StackExchangeIndexing extends IndexerBase {

	private static final String KNOWLEDGE_TYPE = "nlp";
	private static final String DOCUMENT_TYPE = "post";
	private static ObjectMapper objectMapper = new ObjectMapper();

	private static final MappingStorage MAPPING = new MappingStorage("{\n" + "\"_meta\" : {\n" + 
			"		\"mapping_version\" : \"1.0\"\n" + 
			"	},  \"properties\" : {\n" + "    \"uid\" : {\n"
			+ "      \"type\" : \"text\"\n" + "    },\n" + "    \"project_name\" : {\n" + "      \"type\" : \"text\"\n"
			+ "    },\n" + "    \"post_id\" : {\n" + "      \"type\" : \"keyword\"\n" + "    },\n"
			+ "    \"parent_id\" : {\n" + "      \"type\" : \"keyword\"\n" + "    },\n" + "    \"creator\" : {\n"
			+ "      \"type\" : \"text\"\n" + "    },\n" + "    \"created_at\" : {\n" + "      \"type\" : \"date\"\n"
			+ "    },\n" + "    \"score\" : {\n" + "      \"type\" : \"integer\"\n" + "    },\n"
			+ "    \"view_count\" : {\n" + "      \"type\" : \"long\"\n" + "    },\n"
			+ "    \"accepted_answer_id\" : {\n" + "      \"type\" : \"keyword\"\n" + "    },\n"
			+ "    \"message_body\" : {\n" + "      \"type\" : \"text\"\n" + "    },\n" + "    \"last_editor\" : {\n"
			+ "      \"type\" : \"text\"\n" + "    },\n" + "    \"last_editor_display_name\" : {\n"
			+ "      \"type\" : \"text\"\n" + "    },\n" + "    \"last_edit_date\" : {\n"
			+ "      \"type\" : \"date\"\n" + "    },\n" + "    \"last_activity_date\" : {\n"
			+ "      \"type\" : \"date\"\n" + "    },\n" + "    \"community_owned_date\" : {\n"
			+ "      \"type\" : \"date\"\n" + "    },\n" + "    \"title\" : {\n" + "      \"type\" : \"text\"\n"
			+ "    },\n" + "    \"tags\" : {\n" + "      \"type\" : \"text\"\n" + "    },\n"
			+ "    \"answer_count\" : {\n" + "      \"type\" : \"integer\"\n" + "    },\n"
			+ "    \"comment_count\" : {\n" + "      \"type\" : \"integer\"\n" + "    },\n"
			+ "    \"favourite count\" : {\n" + "      \"type\" : \"integer\"\n" + "    },\n"
			+ "    \"plain_text\" : {\n" + "      \"type\" : \"text\"\n" + "    },\n" + "    \"contains_code\" : {\n"
			+ "      \"type\" : \"boolean\"\n" + "    }\n" + "  }\n" + "}", (float) 1.0);

	@Override
	public void consumeIndexingQueue(Post post) {
		String indexName = Indexer.generateIndexName("stackoverflow", DOCUMENT_TYPE, KNOWLEDGE_TYPE);
		String uid = generateUniqueDocumentId(post);
		String document = postToJsonString(post, uid);
		Indexer.indexDocument(indexName, MAPPING, DOCUMENT_TYPE, uid, document);
		// returns nothing as this is the end...
	}

	/**
	 * Converts a Post object to a JSON String compatible for indexing
	 * 
	 * @param post
	 * @return string
	 */
	private String postToJsonString(Post post, String uid) {

		String json = "";
		try {
			Document doc = postToDocument(post, uid);
			json = objectMapper.writeValueAsString(doc);
		} catch (JsonProcessingException e) {

			e.printStackTrace();
		}
		return json;
	}

	/**
	 * 
	 * Converts Post object to Document Object
	 * 
	 * @param post
	 * @param uid
	 * @return document
	 */
	private Document postToDocument(Post post, String uid) {
		Document document = new Document();

		document.setUid(uid);
		document.setProject_name(post.getProject_name());
		document.setPost_id(post.getPostId().toString());
		if (post.getParentId() != null) {
			document.setParent_id(post.getParentId().toString());
		}

		if (post.getOwnerUserId() != null) {
			document.setCreator(post.getOwnerUserId().toString());
		}
		if (post.getAcceptedAnswerId() != null) {
			document.setAccepted_answer_id(post.getAcceptedAnswerId().toString());
		}
		if (post.getCreationDate() != null) {
			document.setCreated_at(post.getCreationDate());
		}
		if (post.getScore() != null) {
			document.setScore(post.getScore());
		}
		if (post.getViewCount() != null) {
			document.setView_count(post.getViewCount());
		}
		if (post.getBody() != null) {
			document.setMessage_body(post.getBody());
		}
		if (post.getLastEditorUserId() != null) {
			;
			document.setLast_editor(post.getLastEditorUserId().toString());
		}
		if (post.getLastEditorDisplayName() != null) {
			document.setLast_editor_display_name(post.getLastEditorDisplayName());
		}
		if (post.getLastEditDate() != null) {
			document.setLast_edit_date(post.getLastEditDate());
		}
		if (post.getLastActivityDate() != null) {
			document.setLast_activity_date(post.getLastActivityDate());
		}
		if (post.getCommunityOwnedDate() != null) {
			document.setCommunity_owned_date(post.getCommunityOwnedDate());
		}
		if (post.getTitle() != null) {
			document.setTitle(post.getTitle());
		}
		if (post.getTags() != null) {
			document.setTags(post.getTags());
		}
		if (post.getAnswerCount() != null) {
			document.setAnswer_count(post.getAnswerCount());
		}
		if (post.getCommentCount() != null) {
			document.setComment_count(post.getCommentCount());
		}
		if (post.getFavoriteCount() != null) {
			document.setFavourite_count(post.getFavoriteCount());
		}
		if (post.getPlainText() != null) {
			document.setPlain_text(post.getPlainText());
		}

		Boolean flag = post.getHasCode();

		if (flag != null) {
			document.setContains_code(post.getHasCode());
		}

		return document;
	}

	/**
	 * This method returns a unique Identifier based upon the SourceType, Project,
	 * Document Type and source ID;
	 * 
	 * @return String uid - a uniquely identifiable string.
	 */
	private String generateUniqueDocumentId(Post post) {

		// String projectName = post.getProject_name();
		// String sourceType = post.getRepository();
		String projectName = "test";
		String sourceType = "stack_overflow";
		String uid = sourceType + " " + projectName + " " + post.getPostId();

		return uid.toLowerCase();
	}

}
