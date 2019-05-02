package org.eclipse.scava.nlp.tools.references;

import java.util.HashSet;
import java.util.Set;

public class NormalizedReferences
{
	private Set<String> normalizedBugsReferences;
	private Set<String> normalizedCommitsReferences;
	
	public NormalizedReferences() {
		normalizedBugsReferences = new HashSet<String>(1);
		normalizedCommitsReferences = new HashSet<String>(1);
	}

	public Set<String> getNormalizedBugsReferences() {
		return normalizedBugsReferences;
	}

	public Set<String> getNormalizedCommitsReferences() {
		return normalizedCommitsReferences;
	}
	
	
}
