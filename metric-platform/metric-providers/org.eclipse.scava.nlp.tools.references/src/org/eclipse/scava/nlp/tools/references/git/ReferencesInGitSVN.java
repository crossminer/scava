/*******************************************************************************
 * Copyright (c) 2019 Edge Hill University
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.nlp.tools.references.git;

import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import org.eclipse.scava.nlp.tools.other.regexdataextractor.RegexDataExtractor;
import org.eclipse.scava.nlp.tools.references.NormalizedReferences;

public class ReferencesInGitSVN {

	private static Pattern gitSVNCommit;
	
	private static String leftBoundary="(?:^|\\s|\\p{P})";					//Detects start of the line or any space
	private static String rightBoundary="(?:$|\\s|\\p{P})";				//Detects end of line or any space
	
	static
	{
		gitSVNCommit=Pattern.compile(leftBoundary+"([a-z0-9]{40})"+rightBoundary);								//1 capture //a5c3785ed8d6a35868bc169f07e40e889087fd2e
	}
	
	public static NormalizedReferences findReferences(String text, String url)
	{
		NormalizedReferences normalizedReferences = new NormalizedReferences();
				
		//Commits

		List<List<String>> referencesFound = RegexDataExtractor.capturePatterns(gitSVNCommit, text);
		processSelfReferences(referencesFound, normalizedReferences.getNormalizedBugsReferences(), url, "commit");
		
		return normalizedReferences;
	}

	
	private static void processSelfReferences(List<List<String>> references,Set<String> normalizedReferences, String url, String type)
	{
		if(references.size()==0)
			return;
		for(List<String> reference : references)
			normalizedReferences.add(url+"/"+type+"/"+reference.get(0));
	}
	
}
