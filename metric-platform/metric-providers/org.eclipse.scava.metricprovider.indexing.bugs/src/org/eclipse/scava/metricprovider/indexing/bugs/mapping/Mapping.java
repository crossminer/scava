package org.eclipse.scava.metricprovider.indexing.bugs.mapping;

public final class Mapping {
	
	private static final String bugMapping = "";
	
	private static final String commentMapping = "";
	
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
