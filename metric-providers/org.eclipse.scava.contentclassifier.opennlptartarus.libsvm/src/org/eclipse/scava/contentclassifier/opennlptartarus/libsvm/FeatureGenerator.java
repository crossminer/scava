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

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;

import libsvm.svm_node;

import org.eclipse.scava.contentclassifier.opennlptartarus.libsvm.featuremethods.EmpiricalPredictor;

import uk.ac.nactem.posstemmer.Token;

public class FeatureGenerator {

	private Features lemmaFeatures;
	private DocumentFeatures documentFeatures;
	private Features empiricalFeatures;
	
    public FeatureGenerator(String lemmaFeaturesFileName,
    						String empiricalFeaturesFileName) {
    	super();
    	lemmaFeatures = new Features(lemmaFeaturesFileName);
    	documentFeatures = new DocumentFeatures(lemmaFeatures);
    	empiricalFeatures = new Features(empiricalFeaturesFileName);
    }
    
    public void updateData(String documentId, List<List<Token>> sentences) {
    	for(List<Token> sentence: sentences)
    		for(Token token: sentence)
    			documentFeatures.add(documentId, token.getNormalForm(), token.getPoS());
    }
    
	public List<Double> generateTargets(List<ClassificationInstance> itemList) {
//    	int counter = 0;
		List<Double> target_list = new ArrayList<Double>();
        for (int i = 0; i < itemList.size(); i++) {
 //			REQUEST --> +1,    REPLY --> -1
			target_list.add((double)14);
//			counter++;
		}
//		System.out.println(counter + " instances added to svm_train_nofiles.");
		return target_list;
	}

    public List<svm_node[]> generateFeatures(List<ClassificationInstance> itemList) {
		//	"tfidf"
    	documentFeatures.fillInNoOfDocumentsTable();

//    	int counter = 0;
		List<svm_node[]> svm_node_list = new ArrayList<svm_node[]>();
        for (ClassificationInstance xmlResourceItem: itemList) {
//			REQUEST --> +1,    REPLY --> -1
			svm_node[] svm_node = loadInstanceFeatures(xmlResourceItem);
			svm_node_list.add(svm_node);
//			counter++;
        }
//		System.out.println(counter + " instances added to svm_train_nofiles.");
		return svm_node_list;
	}

	private svm_node[] loadInstanceFeatures(ClassificationInstance xmlResourceItem) {
		List<svm_node> svm_node_list = new ArrayList<svm_node>();
		for (int order: empiricalFeatures.getSortedOrders()) {
			if (EmpiricalPredictor.predict(empiricalFeatures.getLemma(order), xmlResourceItem) == 1)
				svm_node_list.add(generateNode(order, 1));
		}
		SortedSet<Integer> sortedOrders = documentFeatures.getSortedOrders(xmlResourceItem.getComposedId());
		if (sortedOrders!=null) {
			for (int order: sortedOrders) {
				double tfidf = documentFeatures.getTfIdf(xmlResourceItem.getComposedId(), order);
				if (tfidf>0)
					svm_node_list.add(generateNode(order, tfidf));
			}
		}
		return svm_node_list.toArray(new svm_node[svm_node_list.size()]);
	}

	private svm_node generateNode(int index, int value) {
		svm_node node = new svm_node();
		node.index = index;
		node.value = (double) value;
		return node;
	}
	
	private svm_node generateNode(int index, double value) {
		svm_node node = new svm_node();
		node.index = index;
		node.value = value;
		return node;
	}

}
