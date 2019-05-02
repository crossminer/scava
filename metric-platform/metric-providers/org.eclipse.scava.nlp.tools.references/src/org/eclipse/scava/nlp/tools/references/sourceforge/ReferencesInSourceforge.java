package org.eclipse.scava.nlp.tools.references.sourceforge;

import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import org.eclipse.scava.nlp.tools.other.regexdataextractor.RegexDataExtractor;
import org.eclipse.scava.nlp.tools.references.NormalizedReferences;

public class ReferencesInSourceforge {

	private static Pattern sourceforgeIssue;
	
	private static String leftBoundary="(?:^|\\s|\\p{P})";					//Detects start of the line or any space
	private static String rightBoundary="(?:$|\\s|\\p{P})";				//Detects end of line or any space
	
	static
	{
		sourceforgeIssue=Pattern.compile(leftBoundary+"(https?://sourceforge.net/p/[^/\\s]+/bugs/\\d+/)"+rightBoundary);					//1 capture	//https://gitlab.com/gitlab-org/gitlab-ce/issues/30204

	}
	
	public static NormalizedReferences findReferences(String text)
	{
		NormalizedReferences normalizedReferences = new NormalizedReferences();
		List<List<String>> referencesFound;
		
		//Issues
		referencesFound = RegexDataExtractor.capturePatterns(sourceforgeIssue, text);
		processFullReferences(referencesFound, normalizedReferences.getNormalizedBugsReferences());

		return normalizedReferences;
	}

	
	private static void processFullReferences(List<List<String>> references, Set<String> normalizedReferences)
	{
		if(references.size()==0)
			return;
		for(List<String> reference : references)
			normalizedReferences.add(reference.get(0));
	}
	
}
