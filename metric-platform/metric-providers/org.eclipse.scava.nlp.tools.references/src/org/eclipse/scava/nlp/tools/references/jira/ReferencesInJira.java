/*******************************************************************************
 * Copyright (c) 2019 Edge Hill University
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
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
	
	public static NormalizedReferences findReferences(String text, String url, String repositoryName)
	{
		NormalizedReferences normalizedReferences = new NormalizedReferences();
		List<List<String>> referencesFound;
		
		Pattern jiraIssue=Pattern.compile(leftBoundary+jiraIssuePatternLeft+repositoryName+jiraIssuePatternRight+rightBoundary);
		
		//Issues
		referencesFound = RegexDataExtractor.capturePatterns(jiraIssue, text);
		processSelfReferences(referencesFound, url, normalizedReferences.getNormalizedBugsReferences());
		
		return normalizedReferences;
	}
	
	private static void processSelfReferences(List<List<String>> references, String url, Set<String> normalizedReferences)
	{
		if(references.size()==0)
			return;
		for(List<String> reference : references)
			normalizedReferences.add(url+"/browse/"+reference.get(0));
	}
	
}
