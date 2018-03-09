/*******************************************************************************
 * Copyright (c) 2017 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.sentimentclassifier.opennlptartarus.libsvm;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;

import libsvm.svm_node;

import org.eclipse.scava.sentimentclassifier.opennlptartarus.libsvm.featuretools.CMUclusters;
import org.eclipse.scava.sentimentclassifier.opennlptartarus.libsvm.featuretools.CMUclustersSingleton;

public class FeatureGenerator {

	private static String unigramString = "Unigrams",
						  allCapsString = "AllCaps",
						  bigramString = "Bigrams",
						  bigramBigramString = "BigramBigramPairs",
						  unigramBigramString = "UnigramBigramPairs",
						  bigramUnigramString = "BigramUnigramPairs";

	private static String[] nGramStrings = { unigramString, allCapsString, bigramString,
		 									 bigramBigramString, unigramBigramString, 
		 									 bigramUnigramString };

	private static String HuLiuMethodName = "HuLiu",
						  SubjectivityMethodName = "Subjectivity",
						  NRCMethodName = "NRC",
						  NRCHashtagSentimentMethodName = "NRCHashtagSentiment",
						  Sentiment140MethodName = "Sentiment140";

	private static String[] methodNames = { HuLiuMethodName, SubjectivityMethodName, NRCMethodName,	
											NRCHashtagSentimentMethodName, Sentiment140MethodName };

	private Features unigramFeatures, bigramFeatures,
					 trigramFeatures, quadgramFeatures,
					 pairFeatures,
					 charTrigramFeatures, charQuadgramFeatures, charFivegramFeatures,
					 heuristicAndPoSFeatures, cmuClusterFeatures;
	
	private DocumentFeatures documentUnigramFeatures, documentBigramFeatures,
							 documentTrigramFeatures, documentQuadgramFeatures,
							 documentPairFeatures,
							 documentCharTrigramFeatures, documentCharQuadgramFeatures,
							 documentCharFivegramFeatures;

    public FeatureGenerator(String filenameUnigrams, String filenameBigrams, 
    						String filenameTrigrams, String filenameQuadgrams, 
    						String filenamePairs, 
    						String filenameCharTrigrams, String filenameCharQuadgrams, 
    						String filenameCharFivegrams, 
    						String filenameHeuristicAndPoS, String filenameCMUclusters) {
    	super();
    	unigramFeatures = new Features(filenameUnigrams);
    	documentUnigramFeatures = new DocumentFeatures(unigramFeatures);
    	bigramFeatures = new Features(filenameBigrams);
    	documentBigramFeatures = new DocumentFeatures(bigramFeatures);
    	trigramFeatures = new Features(filenameTrigrams);
    	documentTrigramFeatures = new DocumentFeatures(trigramFeatures);
    	quadgramFeatures = new Features(filenameQuadgrams);
    	documentQuadgramFeatures = new DocumentFeatures(quadgramFeatures);

    	pairFeatures = new Features(filenamePairs);
    	documentPairFeatures = new DocumentFeatures(pairFeatures);

    	charTrigramFeatures = new Features(filenameCharTrigrams);
    	documentCharTrigramFeatures = new DocumentFeatures(charTrigramFeatures);
    	charQuadgramFeatures = new Features(filenameCharQuadgrams);
    	documentCharQuadgramFeatures = new DocumentFeatures(charQuadgramFeatures);
    	charFivegramFeatures = new Features(filenameCharFivegrams);
    	documentCharFivegramFeatures = new DocumentFeatures(charFivegramFeatures);
    	
    	heuristicAndPoSFeatures = new Features(filenameHeuristicAndPoS);
    	cmuClusterFeatures = new Features(filenameCMUclusters);
    }
    
    public void updateData(ClassificationInstance classificationInstance) {
    	for (String unigram: classificationInstance.getUnigramsNeg())
    		documentUnigramFeatures.add(classificationInstance.getComposedId(), unigram);
    	for (String bigram: classificationInstance.getBigramsNeg())
    		documentBigramFeatures.add(classificationInstance.getComposedId(), bigram);
       	for (String trigram: classificationInstance.getTrigramsNeg())
    		documentTrigramFeatures.add(classificationInstance.getComposedId(), trigram);
    	for (String quadgram: classificationInstance.getQuadgramsNeg())
    		documentQuadgramFeatures.add(classificationInstance.getComposedId(), quadgram);
    	
      	for (String bigramBigramPair: classificationInstance.getBigramBigramPairsNeg())
    		documentBigramFeatures.add(classificationInstance.getComposedId(), bigramBigramPair);
       	for (String unigramBigramPair: classificationInstance.getUnigramBigramPairsNeg())
       		documentBigramFeatures.add(classificationInstance.getComposedId(), unigramBigramPair);
    	for (String bigramUnigramPair: classificationInstance.getBigramUnigramPairsNeg())
    		documentBigramFeatures.add(classificationInstance.getComposedId(), bigramUnigramPair);

    	for (String charTrigram: classificationInstance.getCharTrigramsNeg())
    		documentCharTrigramFeatures.add(classificationInstance.getComposedId(), charTrigram);
       	for (String charQuadgram: classificationInstance.getCharQuadgramsNeg())
    		documentCharQuadgramFeatures.add(classificationInstance.getComposedId(), charQuadgram);
    	for (String charFivegram: classificationInstance.getCharFivegramsNeg())
    		documentCharFivegramFeatures.add(classificationInstance.getComposedId(), charFivegram);
    }
    
	public List<Double> generateTargets(List<ClassificationInstance> classificationInstanceList) {
//    	int counter = 0;
		List<Double> target_list = new ArrayList<Double>();
        for (int i = 0; i < classificationInstanceList.size(); i++) {
 //			POSITIVE --> +1,    NEUTRAL --> 0,    NEGATIVE --> -1
			target_list.add((double)0);
//			counter++;
		}
//		System.out.println(counter + " instances added to svm_train_nofiles.");
		return target_list;
	}

    public List<svm_node[]> generateFeatures(List<ClassificationInstance> itemList) {
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

    private void addfeatures(ClassificationInstance instance, 
    							   List<svm_node> svm_node_list, DocumentFeatures documentFeatures) {
    	SortedSet<Integer> sortedOrders = documentFeatures.getSortedOrders(instance.getComposedId());
		if (sortedOrders!=null)
			for (int order: sortedOrders)
				svm_node_list.add(generateNode(order, documentFeatures.getFrequency(instance.getComposedId(), order)));
    }
    
//    private static void writeInstanceFeatures(ThreadMessages threadMessages, svm_train_nofiles svm_train, 
//    		WordNGrams wordNgrams, CharNGrams charNgrams, PartsOfSpeech partsOfSpeech)

    private svm_node[] loadInstanceFeatures(ClassificationInstance instance) {
		List<svm_node> svm_node_list = new ArrayList<svm_node>();

		addfeatures(instance, svm_node_list, documentUnigramFeatures);
		addfeatures(instance, svm_node_list, documentBigramFeatures);
		addfeatures(instance, svm_node_list, documentTrigramFeatures);
		addfeatures(instance, svm_node_list, documentQuadgramFeatures);
		
		addfeatures(instance, svm_node_list, documentPairFeatures);

		addfeatures(instance, svm_node_list, documentCharTrigramFeatures);
		addfeatures(instance, svm_node_list, documentCharQuadgramFeatures);
		addfeatures(instance, svm_node_list, documentCharFivegramFeatures);
		
		if (instance.getWordsAllCaps()>0) {
			int order = heuristicAndPoSFeatures.getOrder("allCaps");
			if (order>0)
				svm_node_list.add(generateNode(order, instance.getWordsAllCaps()));
		}
		
		for (String pos :instance.getSortedPartsOfSpeech()) {
			int order = heuristicAndPoSFeatures.getOrder("POS_" + formatFeature(pos));
			int value = instance.getFrequency(pos);
			if ( (order>0) && (value>0) )
				svm_node_list.add(generateNode(order, value));
		}
		
		for (String methodName: methodNames)
			for (String nGramString: nGramStrings) {
				int order = heuristicAndPoSFeatures.getOrder(
						"positiveScoreNgrams_" + methodName + "_" + nGramString);
				int value = instance.getPositiveScoreNgrams(methodName, nGramString);
				if ( (order>0) && (value>0) )
					svm_node_list.add(generateNode(order, value));
			}

		for (String methodName: methodNames)
			for (String pos: instance.getSortedPartsOfSpeech()) {
				int order = heuristicAndPoSFeatures.getOrder(
						"positiveScoreUnigramsPerPoS_" + methodName + "_" + formatFeature(pos));
				int value = instance.getPositiveScoreUnigramsPerPoS(methodName, pos);
				if ( (order>0) && (value>0) )
					svm_node_list.add(generateNode(order, value));
			}
					
		for (String methodName: methodNames)
			for (String nGramString: nGramStrings) {
				int order = heuristicAndPoSFeatures.getOrder(
						"totalScoreNgrams_" + methodName + "_" + nGramString);
				double value = instance.getTotalScoreNgrams(methodName, nGramString);
				if ( (order>0) && (value>0) )
					svm_node_list.add(generateNode(order, value));
			}
		
		for (String methodName: methodNames)
			for (String pos: instance.getSortedPartsOfSpeech()) {
				int order = heuristicAndPoSFeatures.getOrder(
						"totalScoreUnigramsPerPoS_" + methodName + "_" + formatFeature(pos));
				double value = instance.getTotalScoreUnigramsPerPoS(methodName, pos);
				if ( (order>0) && (value>0) )
					svm_node_list.add(generateNode(order, value));
			}
		
		for (String methodName: methodNames) {
			int order = heuristicAndPoSFeatures.getOrder(
					"maximalScoreNgrams_" + methodName + "_" + unigramString);
			double value = instance.getMaximalScoreNgrams(methodName, unigramString);
			if ( (order>0) && (value>0) )
				svm_node_list.add(generateNode(order, value));
		}

		for (String methodName: methodNames) {
			int order = heuristicAndPoSFeatures.getOrder(
					"lastScoreNgrams_" + methodName + "_" + unigramString);
			double value = instance.getLastScoreNgrams(methodName, unigramString);
			if ( (order>0) && (value>0) )
				svm_node_list.add(generateNode(order, value));
			order = heuristicAndPoSFeatures.getOrder(
					"lastScoreNgrams_" + methodName + "_" + allCapsString);
			value = instance.getLastScoreNgrams(methodName, allCapsString);
			if ( (order>0) && (value>0) )
				svm_node_list.add(generateNode(order, value));
		}
		
		for (String methodName: methodNames)
			for (String pos: instance.getSortedPartsOfSpeech()) {
				int order = heuristicAndPoSFeatures.getOrder(
						"lastScoreUnigramsPerPoS_" + methodName + "_" + formatFeature(pos));
				double value = instance.getLastScoreUnigramsPerPoS(methodName, pos);
				if ( (order>0) && (value>0) )
					svm_node_list.add(generateNode(order, value));
			}
		
		if (instance.getContiguousPunctuationWords()>0) {
			int order = heuristicAndPoSFeatures.getOrder("maximumPunctuationSequence");
			if (order>0)
				svm_node_list.add(generateNode(order, instance.getContiguousPunctuationWords()));
		}
		
		if (instance.getSentencesEndingInPunctuation()>0) {
			int order = heuristicAndPoSFeatures.getOrder("sentencesEndingInPunctuation");
			if (order>0)
				svm_node_list.add(generateNode(order, instance.getSentencesEndingInPunctuation()));
		}
			
		if (instance.getElongatedWords()>0) {
			int order = heuristicAndPoSFeatures.getOrder("elongatedWords");
			if (order>0)
				svm_node_list.add(generateNode(order, instance.getElongatedWords()));
		}
		
		CMUclusters cmuClusters = CMUclustersSingleton.getInstance().getClusters();
		for (String clusterId: cmuClusters.getSortedClusters()) {
			int order = cmuClusterFeatures.getOrder("CMUcluster_" + clusterId);
			if ( (order>0) && instance.participatesInCluster(clusterId) )
				svm_node_list.add(generateNode(order, 1));
		}
		
		if (instance.getNegatedContexts()>0) {
			int order = heuristicAndPoSFeatures.getOrder("negatedContexts");
			if (order>0)
				svm_node_list.add(generateNode(order, instance.getNegatedContexts()));
		}

		return svm_node_list.toArray(new svm_node[svm_node_list.size()]);
	}

	private static String formatFeature(String feature) {
		feature = feature.replace("\\", "\\\\");
		feature = feature.replace("'", "&sq;");
		return feature;
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
