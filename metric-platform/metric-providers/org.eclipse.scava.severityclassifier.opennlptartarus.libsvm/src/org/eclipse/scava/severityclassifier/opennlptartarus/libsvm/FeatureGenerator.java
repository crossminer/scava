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

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;

import libsvm.svm_node;

public class FeatureGenerator {

	private Features unigramFeatures, bigramFeatures,
					 trigramFeatures, quadgramFeatures,
					 charTrigramFeatures, charQuadgramFeatures, charFivegramFeatures;
	
	private DocumentFeatures documentUnigramFeatures, documentBigramFeatures,
							 documentTrigramFeatures, documentQuadgramFeatures,
							 documentCharTrigramFeatures, documentCharQuadgramFeatures,
							 documentCharFivegramFeatures;

    public FeatureGenerator(String filenameUnigrams, String filenameBigrams, 
    						String filenameTrigrams, String filenameQuadgrams,
    						String filenameCharTrigrams, String filenameCharQuadgrams, 
    						String filenameCharFivegrams) {
    	super();
    	unigramFeatures = new Features(filenameUnigrams);
    	documentUnigramFeatures = new DocumentFeatures(unigramFeatures);
    	bigramFeatures = new Features(filenameBigrams);
    	documentBigramFeatures = new DocumentFeatures(bigramFeatures);
    	trigramFeatures = new Features(filenameTrigrams);
    	documentTrigramFeatures = new DocumentFeatures(trigramFeatures);
    	quadgramFeatures = new Features(filenameQuadgrams);
    	documentQuadgramFeatures = new DocumentFeatures(quadgramFeatures);

    	charTrigramFeatures = new Features(filenameCharTrigrams);
    	documentCharTrigramFeatures = new DocumentFeatures(charTrigramFeatures);
    	charQuadgramFeatures = new Features(filenameCharQuadgrams);
    	documentCharQuadgramFeatures = new DocumentFeatures(charQuadgramFeatures);
    	charFivegramFeatures = new Features(filenameCharFivegrams);
    	documentCharFivegramFeatures = new DocumentFeatures(charFivegramFeatures);
    }
    
    public void updateData(ClassificationInstance classificationInstance) {
    	for (String unigram: classificationInstance.getUnigrams())
    		documentUnigramFeatures.add(classificationInstance.getComposedId(), unigram);
    	for (String bigram: classificationInstance.getBigrams())
    		documentBigramFeatures.add(classificationInstance.getComposedId(), bigram);
       	for (String trigram: classificationInstance.getTrigrams())
    		documentTrigramFeatures.add(classificationInstance.getComposedId(), trigram);
    	for (String quadgram: classificationInstance.getQuadgrams())
    		documentQuadgramFeatures.add(classificationInstance.getComposedId(), quadgram);

    	for (String charTrigram: classificationInstance.getCharTrigrams())
    		documentCharTrigramFeatures.add(classificationInstance.getComposedId(), charTrigram);
       	for (String charQuadgram: classificationInstance.getCharQuadgrams())
    		documentCharQuadgramFeatures.add(classificationInstance.getComposedId(), charQuadgram);
    	for (String charFivegram: classificationInstance.getCharFivegrams())
    		documentCharFivegramFeatures.add(classificationInstance.getComposedId(), charFivegram);
    }
    
	public List<Double> generateTargets(ClassificationInstanceCollection classificationInstanceCollection) {
		List<Double> target_list = new ArrayList<Double>();
        for (int i = 0; i < classificationInstanceCollection.size(); i++) {
			target_list.add((double)0);
		}
		return target_list;
	}

    public List<svm_node[]> generateFeatures(ClassificationInstanceCollection classificationInstanceCollection) {
		List<svm_node[]> svm_node_list = new ArrayList<svm_node[]>();
        for (ClassificationInstance classificationInstance: classificationInstanceCollection.getInstanceList()) {
			svm_node[] svm_node = loadInstanceFeatures(classificationInstance);
			svm_node_list.add(svm_node);
        }
		return svm_node_list;
	}

    private void addfeatures(ClassificationInstance instance, 
    							   List<svm_node> svm_node_list, DocumentFeatures documentFeatures) {
    	SortedSet<Integer> sortedOrders = documentFeatures.getSortedOrders(instance.getComposedId());
		if (sortedOrders!=null)
			for (int order: sortedOrders)
				if (documentFeatures.containsFeature(instance.getComposedId(), order))
				svm_node_list.add(generateNode(order, 1));
    }
    
    private svm_node[] loadInstanceFeatures(ClassificationInstance instance) {
		List<svm_node> svm_node_list = new ArrayList<svm_node>();

		addfeatures(instance, svm_node_list, documentUnigramFeatures);
		addfeatures(instance, svm_node_list, documentBigramFeatures);
		addfeatures(instance, svm_node_list, documentTrigramFeatures);
		addfeatures(instance, svm_node_list, documentQuadgramFeatures);

		addfeatures(instance, svm_node_list, documentCharTrigramFeatures);
		addfeatures(instance, svm_node_list, documentCharQuadgramFeatures);
		addfeatures(instance, svm_node_list, documentCharFivegramFeatures);
				
		return svm_node_list.toArray(new svm_node[svm_node_list.size()]);
	}

	private svm_node generateNode(int index, int value) {
		svm_node node = new svm_node();
		node.index = index;
		node.value = (double) value;
		return node;
	}
	
	public String getUnigramLemma(int unigramId) {
		return unigramFeatures.getLemma(unigramId);
	}
	
	public int getUnigramOrder(String unigram) {
		return unigramFeatures.getOrder(unigram);
	}

	public String getBigramLemma(int bigramId) {
		return bigramFeatures.getLemma(bigramId);
	}
	
	public int getBigramOrder(String bigram) {
		return bigramFeatures.getOrder(bigram);
	}
	
	public String getTrigramLemma(int trigramId) {
		return trigramFeatures.getLemma(trigramId);
	}

	public int getTrigramOrder(String trigram) {
		return trigramFeatures.getOrder(trigram);
	}
	
	public String getQuadgramLemma(int quadgramId) {
		return quadgramFeatures.getLemma(quadgramId);
	}
	
	public int getQuadgramOrder(String quadgram) {
		return quadgramFeatures.getOrder(quadgram);
	}
	
	public String getCharTrigramLemma(int charTrigramId) {
		return charTrigramFeatures.getLemma(charTrigramId);
	}
	
	public int getCharTrigramOrder(String charTrigram) {
		return charTrigramFeatures.getOrder(charTrigram);
	}
	
	public String getCharQuadgramLemma(int charQuadgramId) {
		return charQuadgramFeatures.getLemma(charQuadgramId);
	}
	
	public int getCharQuadgramOrder(String charQuadgram) {
		return charQuadgramFeatures.getOrder(charQuadgram);
	}
	
	public String getCharFivegramLemma(int charFivegramId) {
		return charFivegramFeatures.getLemma(charFivegramId);
	}
	
	public int getCharFivegramOrder(String charFivegram) {
		return charFivegramFeatures.getOrder(charFivegram);
	}
	
}
