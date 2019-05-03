/*******************************************************************************
 * Copyright (c) 2019 Edge Hill University
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.nlp.tools.references.gitlab;

import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import org.eclipse.scava.nlp.tools.other.regexdataextractor.RegexDataExtractor;
import org.eclipse.scava.nlp.tools.references.NormalizedReferences;

public class ReferencesInGitLab {

	private static String gitlabLeft;
	
	private static String gitlabCommit1Right;
	private static Pattern gitlabCommit2;
	
	private static String gitlabIssue1Right;
	private static Pattern gitlabIssue2;
	
	private static String leftBoundary="(?:^|\\s|\\p{P})";					//Detects start of the line or any space
	private static String rightBoundary="(?:$|\\s|\\p{P})";				//Detects end of line or any space
	
	static
	{
		gitlabLeft="(";
		
		gitlabIssue1Right="/issues/\\d+)";
		gitlabIssue2=Pattern.compile(leftBoundary+"#(\\d+)"+rightBoundary);										//1 capture	//#26
			
		gitlabCommit1Right="/commit/[a-z0-9]{40})";
		gitlabCommit2=Pattern.compile(leftBoundary+"([a-z0-9]{40})"+rightBoundary);								//1 capture //a5c3785ed8d6a35868bc169f07e40e889087fd2e
	}
	
	public static NormalizedReferences findReferences(String text, String url)
	{
		
		Pattern gitlabIssue1=Pattern.compile(leftBoundary+gitlabLeft+url+gitlabIssue1Right+rightBoundary);					//1 capture	//https://gitlab.com/gitlab-org/gitlab-ce/issues/30204
		Pattern gitlabCommit1=Pattern.compile(leftBoundary+gitlabLeft+url+gitlabCommit1Right+rightBoundary);				//1 capture //http://localhost:3000/gitlab-org/gitlab-test/commit/c347ca2e140aa667b968e51ed0ffe055501fe4f4
		NormalizedReferences normalizedReferences = new NormalizedReferences();
		List<List<String>> referencesFound;
		
		//Issues
		referencesFound = RegexDataExtractor.capturePatterns(gitlabIssue1, text);
		processFullReferences(referencesFound, normalizedReferences.getNormalizedBugsReferences());
		
		referencesFound = RegexDataExtractor.capturePatterns(gitlabIssue2, text);
		processSelfReferences(referencesFound, normalizedReferences.getNormalizedBugsReferences(), url, "issues");
		
		
		//Commits
		referencesFound = RegexDataExtractor.capturePatterns(gitlabCommit1, text);
		processFullReferences(referencesFound, normalizedReferences.getNormalizedCommitsReferences());
		
		referencesFound = RegexDataExtractor.capturePatterns(gitlabCommit2, text);
		processSelfReferences(referencesFound, normalizedReferences.getNormalizedBugsReferences(), url, "commit");
		
		return normalizedReferences;
	}

	
	private static void processFullReferences(List<List<String>> references, Set<String> normalizedReferences)
	{
		if(references.size()==0)
			return;
		for(List<String> reference : references)
			normalizedReferences.add(reference.get(0));
	}
	
	private static void processSelfReferences(List<List<String>> references,Set<String> normalizedReferences, String url, String type)
	{
		if(references.size()==0)
			return;
		for(List<String> reference : references)
			normalizedReferences.add(url+"/"+type+"/"+reference.get(0));
	}
	
}
