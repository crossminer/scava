package org.eclipse.scava.nlp.tools.references.gitlab;

import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import org.eclipse.scava.nlp.tools.other.regexdataextractor.RegexDataExtractor;
import org.eclipse.scava.nlp.tools.references.NormalizedReferences;

public class ReferencesInGitLab {

	private static Pattern gitlabCommit;
	
	private static Pattern gitlabIssue;
	
	private static String leftBoundary="(?:^|\\s|\\p{P})";					//Detects start of the line or any space
	private static String rightBoundary="(?:$|\\s|\\p{P})";				//Detects end of line or any space
	
	static
	{
		gitlabIssue=Pattern.compile(leftBoundary+"(https?://[^/\\s]+/[^/\\s]+/[^/\\s]+/issues/\\d+)"+rightBoundary);					//1 capture	//https://gitlab.com/gitlab-org/gitlab-ce/issues/30204

		gitlabCommit=Pattern.compile(leftBoundary+"(https?://[^/\\s]+/[^/\\s]+/[^/\\s]+/commits/[a-z0-9]{40})"+rightBoundary);			//1 capture //http://localhost:3000/gitlab-org/gitlab-test/commit/c347ca2e140aa667b968e51ed0ffe055501fe4f4
	}
	
	public static NormalizedReferences findReferences(String text)
	{
		NormalizedReferences normalizedReferences = new NormalizedReferences();
		List<List<String>> referencesFound;
		
		//Issues
		referencesFound = RegexDataExtractor.capturePatterns(gitlabIssue, text);
		processFullReferences(referencesFound, normalizedReferences.getNormalizedBugsReferences());
		
		
		//Commits
		referencesFound = RegexDataExtractor.capturePatterns(gitlabCommit, text);
		processFullReferences(referencesFound, normalizedReferences.getNormalizedCommitsReferences());
		
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
