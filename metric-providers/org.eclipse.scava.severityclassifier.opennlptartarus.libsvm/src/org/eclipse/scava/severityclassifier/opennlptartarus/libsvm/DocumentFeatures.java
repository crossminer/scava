/*******************************************************************************
 * Copyright (c) 2017 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.severityclassifier.opennlptartarus.libsvm;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public class DocumentFeatures {
	private Map<String, Set<Integer>> documentFeatures;
	private Features lemmaFeatures;

	public DocumentFeatures(Features lemmaFeatures) {
		this.lemmaFeatures = lemmaFeatures;
		documentFeatures = new HashMap<String, Set<Integer>>();
	}
		

	public void add(String documentId, String lemma) {
		if ((lemmaFeatures.containsLemma(lemma))) {
			if (!documentFeatures.containsKey(documentId)) {
				documentFeatures.put(documentId, new HashSet<Integer>());
			}
			Set<Integer> features = documentFeatures.get(documentId);
			int order = lemmaFeatures.getOrder(lemma);
			features.add(order);
		}
	}

	public Set<String> getDocumentIds() {
		return documentFeatures.keySet();
	}
	
	public SortedSet<Integer> getSortedOrders(String documentId) {
		if (documentFeatures.containsKey(documentId))
			return new TreeSet<Integer>(documentFeatures.get(documentId));
		else
			return null;
	}
	
	public boolean containsFeature(String documentId, int order) {
		return documentFeatures.get(documentId).contains(order);
	}

}
