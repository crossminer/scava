/*******************************************************************************
 * Copyright (c) 2019 Edge Hill University
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.nlp.tools.references.github;

import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import org.eclipse.scava.nlp.tools.other.regexdataextractor.RegexDataExtractor;
import org.eclipse.scava.nlp.tools.references.NormalizedReferences;

public class ReferencesInGitHub {

	private static Pattern githubCommit1;
	private static Pattern githubCommit2;
	private static Pattern githubCommit3;
	private static Pattern githubCommit4;
	
	private static Pattern githubIssue1;
	private static Pattern githubIssue2;
	private static Pattern githubIssue3;
	private static Pattern githubIssue4;
	
	private static String leftBoundary="(?:^|\\s|\\p{P})";				//Detects start of the line or any space
	private static String rightBoundary="(?:$|\\s|\\p{P})";				//Detects end of line or any space
	
	static
	{	
		githubIssue1=Pattern.compile(leftBoundary+"#(\\d+)"+rightBoundary);												//1 capture	//#26
		githubIssue2=Pattern.compile(leftBoundary+"GH-(\\d+)"+rightBoundary);											//1 capture	//GH-26
		githubIssue3=Pattern.compile(leftBoundary+"([^/\\s]+/[^/\\s]+)#(\\d+)"+rightBoundary);							//2 captures	//jlord/sheetsee.js#26
		githubIssue4=Pattern.compile(leftBoundary+"(https?://github.com/[^/\\s]+/[^/\\s]+/issues/\\d+)"+rightBoundary);	//1 capture //https://github.com/jlord/sheetsee.js/issues/26
		
		githubCommit1=Pattern.compile(leftBoundary+"([a-z0-9]{40})"+rightBoundary);										//1 capture //a5c3785ed8d6a35868bc169f07e40e889087fd2e
		githubCommit2=Pattern.compile(leftBoundary+"[^\\s@/]+@([a-z0-9]{40})"+rightBoundary);							//1 capture //jlord@a5c3785ed8d6a35868bc169f07e40e889087fd2e								
		githubCommit3=Pattern.compile(leftBoundary+"([^\\s@/]+/[^\\s@/]+)@([a-z0-9]{40})"+rightBoundary);				//2 captures //jlord/sheetsee.js@a5c3785ed8d6a35868bc169f07e40e889087fd2e
		githubCommit4=Pattern.compile(leftBoundary+"(https?://github.com/[^/\\s]+/[^/\\s]+/commit/[a-z0-9]{40})"+rightBoundary); //https://github.com/jlord/sheetsee.js/commit/a5c3785ed8d6a35868bc169f07e40e889087fd2e
	}
	
	public static NormalizedReferences findReferences(String text, String owner, String repository)
	{
		NormalizedReferences normalizedReferences = new NormalizedReferences();
		List<List<String>> referencesFound;
		
		//Issues
		referencesFound = RegexDataExtractor.capturePatterns(githubIssue1, text);
		processSelfReferences(referencesFound, owner, repository, "issues", normalizedReferences.getNormalizedBugsReferences());
		
		referencesFound = RegexDataExtractor.capturePatterns(githubIssue2, text);
		processSelfReferences(referencesFound, owner, repository, "issues", normalizedReferences.getNormalizedBugsReferences());
		
		referencesFound = RegexDataExtractor.capturePatterns(githubIssue3, text);
		processPartialReferences(referencesFound, "issues", normalizedReferences.getNormalizedBugsReferences());
		
		referencesFound = RegexDataExtractor.capturePatterns(githubIssue4, text);
		processFullReferences(referencesFound, normalizedReferences.getNormalizedBugsReferences());
		
		
		//Commits
		referencesFound = RegexDataExtractor.capturePatterns(githubCommit1, text);
		processSelfReferences(referencesFound, owner, repository, "commit", normalizedReferences.getNormalizedCommitsReferences());
		
		//As we don't know all Github's commits, we guess type 2 of commits are a type of self reference
		referencesFound = RegexDataExtractor.capturePatterns(githubCommit2, text);
		processSelfReferences(referencesFound, owner, repository, "commit", normalizedReferences.getNormalizedCommitsReferences());
		
		referencesFound = RegexDataExtractor.capturePatterns(githubCommit3, text);
		processPartialReferences(referencesFound, "commit", normalizedReferences.getNormalizedCommitsReferences());
		
		referencesFound = RegexDataExtractor.capturePatterns(githubCommit4, text);
		processFullReferences(referencesFound, normalizedReferences.getNormalizedCommitsReferences());
		
		return normalizedReferences;
	}
	
	private static void processSelfReferences(List<List<String>> references, String owner, String repository, String type, Set<String> normalizedReferences)
	{
		if(references.size()==0)
			return;
		for(List<String> reference : references)
			normalizedReferences.add("https://github.com/"+owner+"/"+repository+"/"+type+"/"+reference.get(0));
	}
	
	private static void processPartialReferences(List<List<String>> references, String type, Set<String> normalizedReferences)
	{
		if(references.size()==0)
			return;
		for(List<String> reference : references)
			normalizedReferences.add("https://github.com/"+reference.get(0)+"/"+type+"/"+reference.get(1));
	}
	
	private static void processFullReferences(List<List<String>> references, Set<String> normalizedReferences)
	{
		if(references.size()==0)
			return;
		for(List<String> reference : references)
			normalizedReferences.add(reference.get(0));
	}
	
}
