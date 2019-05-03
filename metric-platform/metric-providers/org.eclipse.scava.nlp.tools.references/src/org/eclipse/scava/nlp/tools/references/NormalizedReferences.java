/*******************************************************************************
 * Copyright (c) 2019 Edge Hill University
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
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
