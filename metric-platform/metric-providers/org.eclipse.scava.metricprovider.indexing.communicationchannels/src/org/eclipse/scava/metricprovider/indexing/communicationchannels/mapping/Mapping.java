package org.eclipse.scava.metricprovider.indexing.communicationchannels.mapping;

public final class Mapping {
	
	//TODO add mappings for mbox, IRC, discussions, sympa!
		
	private static final String forumMapping = "{\n" + 
			"  \"properties\" : {\n" + 
			"    \"post_id\" : {\n" + 
			"      \"type\" : \"keyword\"\n" + 
			"    },\n" + 
			"    \"forum_id\" : {\n" + 
			"      \"type\" : \"keyword\"\n" + 
			"    },\n" + 
			"    \"topic_id\" : {\n" + 
			"      \"type\" : \"keyword\"\n" + 
			"    },\n" + 
			"    \"forum_name\" : {\n" + 
			"      \"type\" : \"text\"\n" + 
			"    },\n" + 
			"    \"uid\" : {\n" + 
			"      \"type\" : \"text\"\n" + 
			"    },\n" + 
			"    \"project_name\" : {\n" + 
			"      \"type\" : \"text\"\n" + 
			"    },\n" + 
			"    \"message_body\" : {\n" + 
			"      \"type\" : \"text\"\n" + 
			"    },\n" + 
			"    \"subject\" : {\n" + 
			"      \"type\" : \"text\"\n" + 
			"    },\n" + 
			"    \"creator\" : {\n" + 
			"      \"type\" : \"text\"\n" + 
			"    },\n" + 
			"    \"created_at\" : {\n" + 
			"      \"type\" : \"date\"\n" + 
			"    },\n" + 
			"    \"emotional_dimension\" : {\n" + 
			"      \"type\" : \"text\"\n" + 
			"    },\n" + 
			"    \"sentiment\" : {\n" + 
			"      \"type\" : \"text\"\n" + 
			"    },\n" + 
			"    \"plain_text\" : {\n" + 
			"      \"type\" : \"text\"\n" + 
			"    },\n" + 
			"    \"request_reply_classification\" : {\n" + 
			"      \"type\" : \"text\"\n" + 
			"    },\n" + 
			"    \"content_class\" : {\n" + 
			"      \"type\" : \"text\"\n" + 
			"    },\n" + 
			"    \"contains_code\" : {\n" + 
			"      \"type\" : \"boolean\"\n" + 
			"    }\n" + 
			"  }\n" + 
			"}";
	
	private static final  String newsgroupMapping = "{\n" + 
			"  \"properties\" : {\n" + 
			"    \"article_id\" : {\n" + 
			"      \"type\" : \"keyword\"\n" + 
			"    },\n" + 
			"    \"articleNumber\" : {\n" + 
			"      \"type\" : \"integer\"\n" + 
			"    },\n" + 
			"    \"message_thread_id\" : {\n" + 
			"      \"type\" : \"keyword\"\n" + 
			"    },\n" + 
			"    \"subject\" : {\n" + 
			"      \"type\" : \"text\"\n" + 
			"    },\n" + 
			"    \"text\" : {\n" + 
			"      \"type\" : \"text\"\n" + 
			"    },\n" + 
			"    \"user\" : {\n" + 
			"      \"type\" : \"text\"\n" + 
			"    },\n" + 
			"    \"uid\" : {\n" + 
			"      \"type\" : \"text\"\n" + 
			"    },\n" + 
			"    \"project\" : {\n" + 
			"      \"type\" : \"text\"\n" + 
			"    },\n" + 
			"    \"body\" : {\n" + 
			"      \"type\" : \"text\"\n" + 
			"    },\n" + 
			"    \"creator\" : {\n" + 
			"      \"type\" : \"text\"\n" + 
			"    },\n" + 
			"    \"emotion\" : {\n" + 
			"      \"type\" : \"text\"\n" + 
			"    },\n" + 
			"    \"sentiment\" : {\n" + 
			"      \"type\" : \"text\"\n" + 
			"    },\n" + 
			"    \"severity\" : {\n" + 
			"      \"type\" : \"text\"\n" + 
			"    },\n" + 
			"    \"plain_text\" : {\n" + 
			"      \"type\" : \"text\"\n" + 
			"    },\n" + 
			"    \"created_at\" : {\n" + 
			"      \"type\" : \"date\"\n" + 
			"    },\n" + 
			"    \"updated_at\" : {\n" + 
			"      \"type\" : \"date\"\n" + 
			"    },\n" + 
			"    \"request\" : {\n" + 
			"      \"type\" : \"boolean\"\n" + 
			"    },\n" + 
			"    \"contains_code\" : {\n" + 
			"      \"type\" : \"boolean\"\n" + 
			"    }\n" + 
			"  }\n" + 
			"}";
	
	private static final  String mboxMapping = "";
	
	private static final  String discussionMapping = "";
	
	private static final  String sympaMapping = "";
	
	private static final  String ircMapping = "";
	
	//=======================================================================

	/**
	 * This will return the mapping for the source based upon the documentType
	 * @param documentType
	 * @return mapping
	 */
	public static String getMapping (String documentType) {
		
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
	
	//=======================================================================
	
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
