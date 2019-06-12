package org.eclipse.scava.metricprovider.indexing.communicationchannels.mapping;

public final class Mapping {

	private static final String forumMapping = "{\n" + "  \"properties\" : {\n" + "    \"post_id\" : {\n"
			+ "      \"type\" : \"keyword\"\n" + "    },\n" + "    \"forum_id\" : {\n"
			+ "      \"type\" : \"keyword\"\n" + "    },\n" + "    \"topic_id\" : {\n"
			+ "      \"type\" : \"keyword\"\n" + "    },\n" + "    \"forum_name\" : {\n" + "      \"type\" : \"text\"\n"
			+ "    },\n" + "    \"uid\" : {\n" + "      \"type\" : \"keyword\"\n" + "    },\n"
			+ "    \"project_name\" : {\n" + "      \"type\" : \"text\"\n" + "    },\n" + "    \"message_body\" : {\n"
			+ "      \"type\" : \"text\"\n" + "    },\n" + "    \"subject\" : {\n" + "      \"type\" : \"text\"\n"
			+ "    },\n" + "    \"creator\" : {\n" + "      \"type\" : \"text\"\n" + "    },\n"
			+ "    \"created_at\" : {\n" + "      \"type\" : \"date\"\n" + "    },\n"
			+ "    \"emotional_dimension\" : {\n" + "      \"type\" : \"text\"\n" + "    },\n"
			+ "    \"sentiment\" : {\n" + "      \"type\" : \"text\"\n" + "    },\n" + "    \"plain_text\" : {\n"
			+ "      \"type\" : \"text\"\n" + "    },\n" + "    \"request_reply_classification\" : {\n"
			+ "      \"type\" : \"text\"\n" + "    },\n" + "    \"content_class\" : {\n" + "      \"type\" : \"text\"\n"
			+ "    },\n" + "    \"contains_code\" : {\n" + "      \"type\" : \"boolean\"\n" + "    }\n" + "  }\n" + "}";

	private static final String articleMapping = "{\n" + "  \"properties\" : {\n" + "    \"article_id\" : {\n"
			+ "      \"type\" : \"keyword\"\n" + "    },\n" + "    \"newsgroup_name\" : {\n"
			+ "      \"type\" : \"text\"\n" + "    },\n" + "    \"message_thread_id\" : {\n"
			+ "      \"type\" : \"keyword\"\n" + "    },\n" + "    \"uid\" : {\n" + "      \"type\" : \"keyword\"\n"
			+ "    },\n" + "    \"project_name\" : {\n" + "      \"type\" : \"text\"\n" + "    },\n"
			+ "    \"message_body\" : {\n" + "      \"type\" : \"text\"\n" + "    },\n" + "    \"subject\" : {\n"
			+ "      \"type\" : \"text\"\n" + "    },\n" + "    \"creator\" : {\n" + "      \"type\" : \"text\"\n"
			+ "    },\n" + "    \"created_at\" : {\n" + "      \"type\" : \"date\"\n" + "    },\n"
			+ "    \"emotional_dimension\" : {\n" + "      \"type\" : \"text\"\n" + "    },\n"
			+ "    \"sentiment\" : {\n" + "      \"type\" : \"text\"\n" + "    },\n" + "    \"plain_text\" : {\n"
			+ "      \"type\" : \"text\"\n" + "    },\n" + "    \"request_reply_classification\" : {\n"
			+ "      \"type\" : \"text\"\n" + "    },\n" + "    \"content_class\" : {\n" + "      \"type\" : \"text\"\n"
			+ "    },\n" + "    \"contains_code\" : {\n" + "      \"type\" : \"boolean\"\n" + "    }\n" + "  }\n" + "}";

	private static final String newsgroupMapping = articleMapping;

	private static final String mboxMapping = articleMapping;

	private static final String discussionMapping = articleMapping;

	private static final String sympaMapping = articleMapping;

	private static final String ircMapping = articleMapping;

	// =======================================================================

	/**
	 * Return the mapping for the source based upon the documentType
	 * 
	 * @param documentType
	 * @return mapping
	 */
	public static String getMapping(String documentType) {

		switch (documentType) {

		case "forum.post":

			return getForumMapping();

		case "newsgroup.article":

			return getNewsgroupMapping();

		case "sympa.mail":

			return getSympaMapping();

		case "discussion.post":

			return getDiscussionMapping();

		case "mbox.mail":

			return getMboxMapping();

		case "irc.message":

			return getIrcMapping();

		default:
			System.err.println("No Mapping found for " + documentType);
			return "";

		}
	}

	// =======================================================================

	private static String getNewsgroupMapping() {
		return newsgroupMapping;
	}

	private static String getForumMapping() {
		return forumMapping;
	}

	private static String getDiscussionMapping() {
		return discussionMapping;
	}

	private static String getSympaMapping() {
		return sympaMapping;
	}

	private static String getIrcMapping() {
		return ircMapping;
	}

	private static String getMboxMapping() {
		return mboxMapping;
	}

}
