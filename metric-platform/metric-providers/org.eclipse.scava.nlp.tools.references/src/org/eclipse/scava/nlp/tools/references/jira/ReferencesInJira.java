package org.eclipse.scava.nlp.tools.references.jira;

import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import org.eclipse.scava.nlp.tools.other.regexdataextractor.RegexDataExtractor;
import org.eclipse.scava.nlp.tools.references.NormalizedReferences;

public class ReferencesInJira {
	
	private static String leftBoundary="(?:^|\\s|\\p{P})";					//Detects start of the line or any space
	private static String rightBoundary="(?:$|\\s|\\p{P})";				//Detects end of line or any space
	
	private static String jiraIssuePatternLeft="(";			
	private static String jiraIssuePatternRight="-\\d+)";			
	
	public static NormalizedReferences findReferences(String text, String jiraHost, String repositoryName)
	{
		NormalizedReferences normalizedReferences = new NormalizedReferences();
		List<List<String>> referencesFound;
		
		Pattern jiraIssue=Pattern.compile(leftBoundary+jiraIssuePatternLeft+repositoryName+jiraIssuePatternRight+rightBoundary);
		
		//Issues
		referencesFound = RegexDataExtractor.capturePatterns(jiraIssue, text);
		processSelfReferences(referencesFound, jiraHost, normalizedReferences.getNormalizedBugsReferences());
		
		return normalizedReferences;
	}
	
	private static void processSelfReferences(List<List<String>> references, String jiraHost, Set<String> normalizedReferences)
	{
		if(references.size()==0)
			return;
		for(List<String> reference : references)
			normalizedReferences.add(jiraHost+"/browse/"+reference.get(0));
	}
	
}
