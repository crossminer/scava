package org.eclipse.scava.metricprovider.indexing.bugs.mapping;

public final class Mapping {
	
	private static final String bugMapping = "{\n" + 
			"  \"properties\" : {\n" + 
			"    \"created_at\" : {\n" + 
			"      \"type\" : \"date\"\n" + 
			"    },\n" + 
			"    \"bug_summary\" : {\n" + 
			"      \"type\" : \"text\"\n" + 
			"    },\n" + 
			"    \"severity\" : {\n" + 
			"      \"type\" : \"text\"\n" + 
			"    },\n" + 
			"    \"bug_id\" : {\n" + 
			"      \"type\" : \"keyword\"\n" + 
			"    },\n" + 
			"    \"project_name\" : {\n" + 
			"      \"type\" : \"text\"\n" + 
			"    },\n" + 
			"    \"creator\" : {\n" + 
			"      \"type\" : \"text\"\n" + 
			"    },\n" + 
			"    \"uid\" : {\n" + 
			"      \"type\" : \"text\"\n" + 
			"    }\n" + 
			"  }\n" + 
			"}";
	
	private static final String commentMapping = "{\n" + 
			"  \"properties\" : {\n" + 
			"    \"comment_Id\" : {\n" + 
			"      \"type\" : \"text\"\n" + 
			"    },\n" + 
			"    \"body\" : {\n" + 
			"      \"type\" : \"text\"\n" + 
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
			"    },\n" + 
			"    \"bug_id\" : {\n" + 
			"      \"type\" : \"keyword\"\n" + 
			"    },\n" + 
			"    \"project_name\" : {\n" + 
			"      \"type\" : \"text\"\n" + 
			"    },\n" + 
			"    \"creator\" : {\n" + 
			"      \"type\" : \"text\"\n" + 
			"    },\n" + 
			"    \"created_at\" : {\n" + 
			"      \"type\" : \"date\"\n" + 
			"    },\n" + 
			"    \"uid\" : {\n" + 
			"      \"type\" : \"text\"\n" + 
			"    }\n" + 
			"  }\n" + 
			"}";
	
	//=======================================================================
	
	public static String getMapping(String docmentType) {
		
		switch (docmentType) {
		case "bug.post":
			return getBugMapping();
		
		case "bug.comment":
			return getCommentMapping();
	
		default:
			System.err.println("No mapping found for " + docmentType);
			return "";
			
		}
	}

	//=======================================================================
	
	private static String getBugMapping() {
		return bugMapping;
	}

	private static String getCommentMapping() {
		return commentMapping;
	}
}
