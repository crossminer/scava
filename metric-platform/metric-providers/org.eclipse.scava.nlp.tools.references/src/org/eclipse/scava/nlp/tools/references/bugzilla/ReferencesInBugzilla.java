package org.eclipse.scava.nlp.tools.references.bugzilla;

import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import org.eclipse.scava.nlp.tools.other.regexdataextractor.RegexDataExtractor;
import org.eclipse.scava.nlp.tools.references.NormalizedReferences;

public class ReferencesInBugzilla {
	
	private static String leftBoundary="(?:^|\\s|\\p{P})";					//Detects start of the line or any space
	private static String rightBoundary="(?:$|\\s|\\p{P})";				//Detects end of line or any space
	
	private static Pattern bugzillaIssue;
	
	static
	{
		bugzillaIssue=Pattern.compile(leftBoundary+"bug (\\d+)"+rightBoundary);
	}
	
	public static NormalizedReferences findReferences(String text, String bugzillaHost)
	{
		NormalizedReferences normalizedReferences = new NormalizedReferences();
		List<List<String>> referencesFound;

		//Issues
		referencesFound = RegexDataExtractor.capturePatterns(bugzillaIssue, text);
		processSelfReferences(referencesFound, bugzillaHost, normalizedReferences.getNormalizedBugsReferences());
		
		return normalizedReferences;
	}
	
	private static void processSelfReferences(List<List<String>> references, String bugzillaHost, Set<String> normalizedReferences)
	{
		if(references.size()==0)
			return;
		for(List<String> reference : references)
			normalizedReferences.add(bugzillaHost+"/show_bug.cgi?id="+reference.get(0));
	}
		
}
