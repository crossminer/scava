/*******************************************************************************
 * Copyright (c) 2019 Edge Hill University
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.nlp.tools.references.bitbucket;

import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import org.eclipse.scava.nlp.tools.other.regexdataextractor.RegexDataExtractor;
import org.eclipse.scava.nlp.tools.references.NormalizedReferences;

public class ReferencesInBitBucket {

	private static Pattern bitbucketCommit;
	
	private static Pattern bitbucketIssue;
	
	static
	{
		bitbucketIssue=Pattern.compile("href=\\\"(https?://)api.([^/\\s]+/[^/\\s]+/[^/\\s]+/issues/\\d+)/[^/\\s]+\\\"");	//1 capture	//href=\"https://api.bitbucket.org/fenics-project/dolfin/issues/313/boundarycomputation-broken-in-parallel\"
		
		bitbucketCommit=Pattern.compile("href=\\\"(https?://)api.([^/\\s]+/[^/\\s]+/[^/\\s]+/commits/[a-z0-9]+)\\\"");			//1 capture //<a href=\"https://api.bitbucket.org/fenics-project/dolfin/commits/317f347ca75b\" rel=
	}
	
	public static NormalizedReferences findReferences(String text)
	{
		NormalizedReferences normalizedReferences = new NormalizedReferences();
		List<List<String>> referencesFound;
		
		//Issues
		referencesFound = RegexDataExtractor.capturePatterns(bitbucketIssue, text);
		processAPIReferences(referencesFound, normalizedReferences.getNormalizedBugsReferences());
		
		
		//Commits
		referencesFound = RegexDataExtractor.capturePatterns(bitbucketCommit, text);
		processAPIReferences(referencesFound, normalizedReferences.getNormalizedCommitsReferences());
		
		return normalizedReferences;
	}
	
	private static void processAPIReferences(List<List<String>> references, Set<String> normalizedReferences)
	{
		if(references.size()==0)
			return;
		for(List<String> reference : references)
			normalizedReferences.add(reference.get(0)+reference.get(1));
	}

}
