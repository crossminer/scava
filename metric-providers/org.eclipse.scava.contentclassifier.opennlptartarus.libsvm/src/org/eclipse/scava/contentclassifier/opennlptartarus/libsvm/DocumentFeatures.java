/*******************************************************************************
 * Copyright (c) 2017 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.contentclassifier.opennlptartarus.libsvm;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public class DocumentFeatures {
	private Map<String, HashMap<Integer, Integer>> documentFeatures;
	private Map<Integer, Integer> lemmaIdNoOfDocuments;
	private Map<String, Integer> maxLemmaFreqPerDoc;
	private Features lemmaFeatures;

	public DocumentFeatures(Features lemmaFeatures) {
		this.lemmaFeatures = lemmaFeatures;
		documentFeatures = new HashMap<String, HashMap<Integer,Integer>>();
	}
		

	public void add(String documentId, String lemma, String PoS) {
		if ((lemmaFeatures.containsLemma(lemma))) {
			if (!documentFeatures.containsKey(documentId)) {
				documentFeatures.put(documentId, new HashMap<Integer, Integer>());
			}
			Map<Integer,Integer> features = documentFeatures.get(documentId);
			int order = lemmaFeatures.getOrder(lemma);
			int frequency;
			if (features.containsKey(order))
				frequency = features.get(order) + 1;
			else
				frequency = 1;
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

	public void fillInNoOfDocumentsTable() {
    	if (lemmaIdNoOfDocuments==null) {
    		lemmaIdNoOfDocuments = new HashMap<Integer, Integer>();
			maxLemmaFreqPerDoc = new HashMap<String, Integer>();
			for (Entry<String, HashMap<Integer, Integer>> entry: documentFeatures.entrySet()) {
				Map<Integer, Integer> documentLemmaIdsFreq = entry.getValue();
    			for (int lemmaId: documentLemmaIdsFreq.keySet()) {
    				if (lemmaIdNoOfDocuments.containsKey(lemmaId))
    					lemmaIdNoOfDocuments.put(lemmaId, lemmaIdNoOfDocuments.get(lemmaId)+1);
    				else
    					lemmaIdNoOfDocuments.put(lemmaId, 1);
    			}
    			String documentId = entry.getKey();
    			int maxFreq=0;
    			for (int freq: documentLemmaIdsFreq.values())
    				if (freq>maxFreq) maxFreq=freq;
    			maxLemmaFreqPerDoc.put(documentId, maxFreq);
    		}
    	}
	}

	public double getTfIdf(String documentId, int lemmaId) {
		int tfLemma = getFrequency(documentId, lemmaId);
		int documents = documentFeatures.size();
		int lemmaNodocs = lemmaIdNoOfDocuments.get(lemmaId);
		int maxtfLemma = maxLemmaFreqPerDoc.get(documentId);
		if ((tfLemma>0)&&(lemmaNodocs>0)) {
			double tf = tfLemma / (double) maxtfLemma;
			double idf = Math.log10(documents / (double) lemmaNodocs);
			double tfidf = tf * idf;
			return tfidf;
		}
		return 0.0;
	}

}
