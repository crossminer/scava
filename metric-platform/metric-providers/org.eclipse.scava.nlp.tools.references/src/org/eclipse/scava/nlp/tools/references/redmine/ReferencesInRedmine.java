package org.eclipse.scava.nlp.tools.references.redmine;

import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import org.eclipse.scava.nlp.tools.other.regexdataextractor.RegexDataExtractor;
import org.eclipse.scava.nlp.tools.references.NormalizedReferences;

public class ReferencesInRedmine {
	
	private static String leftBoundary="(?:^|\\s|\\p{P})";					//Detects start of the line or any space
	private static String rightBoundary="(?:$|\\s|\\p{P})";				//Detects end of line or any space
	
	private static Pattern redmineIssue;
	
	static
	{
		redmineIssue=Pattern.compile(leftBoundary+"#(\\d+)"+rightBoundary);
	}
	
	public static NormalizedReferences findReferences(String text, String redmineHost)
	{
		NormalizedReferences normalizedReferences = new NormalizedReferences();
		List<List<String>> referencesFound;

		//Issues
		referencesFound = RegexDataExtractor.capturePatterns(redmineIssue, text);
		processSelfReferences(referencesFound, redmineHost, normalizedReferences.getNormalizedBugsReferences());
		
		return normalizedReferences;
	}
	
	private static void processSelfReferences(List<List<String>> references, String redmineHost, Set<String> normalizedReferences)
	{
		if(references.size()==0)
			return;
		for(List<String> reference : references)
			normalizedReferences.add(redmineHost+"/issues/"+reference.get(0));
	}
		
}
