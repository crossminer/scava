/*******************************************************************************
 * Copyright (c) 2017 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.requestreplyclassifier.opennlptartarus.libsvm;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public class DocumentFeatures {
	private Map<String, HashMap<Integer, Integer>> documentFeatures;
	private Set<String> keptPoSes;
	private Features lemmaFeatures;

	public DocumentFeatures(Features lemmaFeatures, Set<String> keptPoSes) {
		this.lemmaFeatures = lemmaFeatures;
		this.keptPoSes = keptPoSes;
		documentFeatures = new HashMap<String, HashMap<Integer,Integer>>();
	}
		

	public void add(String documentId, String lemma, String PoS) {
		if ((lemmaFeatures.containsLemma(lemma)) && (keptPoSes.contains(PoS.substring(0, 1)))) {
			if (!documentFeatures.containsKey(documentId)) {
				documentFeatures.put(documentId, new HashMap<Integer, Integer>());
			}
			Map<Integer,Integer> features = documentFeatures.get(documentId);
			int order = lemmaFeatures.getOrder(lemma);
			int frequency;
			if (features.containsKey(order)) {
				frequency = features.get(order) + 1;
			} else {
				frequency = 1;
			}
			features.put(order, frequency);
		}
	}

	public Set<String> getDocumentIds() {
		return documentFeatures.keySet();
	}
	
	public SortedSet<Integer> getSortedOrders(String documentId) {
		if (documentFeatures.containsKey(documentId))
			return new TreeSet<Integer>(documentFeatures.get(documentId).keySet());
		else
			return null;
	}
	
	public int getFrequency(String documentId, int order) {
		return documentFeatures.get(documentId).get(order);
	}

}
