/*******************************************************************************
 * Copyright (c) 2018 Edge Hill University
 * Copyright (c) 2017 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.severityclassifier.opennlptartarus.libsvm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import libsvm.svm_model;
import libsvm.svm_node;

import org.apache.commons.lang3.time.DurationFormatUtils;
import org.eclipse.scava.libsvm.svm_predict_nofiles;
import org.eclipse.scava.metricprovider.trans.newsgroups.threads.model.ArticleData;
import org.eclipse.scava.platform.delta.communicationchannel.CommunicationChannelArticle;

public class Classifier {

	private ClassificationInstanceCollection classificationInstanceCollection;
	private Map<String, String> classificationResults; 
	private FeatureGenerator featureGenerator;

	public Classifier() {
		classificationInstanceCollection = new ClassificationInstanceCollection();
		featureGenerator = new FeatureGenerator(
				"classifierFiles/severityClassification_Unigrams", 
				"classifierFiles/severityClassification_Bigrams", 
				"classifierFiles/severityClassification_Trigrams", 
				"classifierFiles/severityClassification_Quadgrams", 
				"classifierFiles/severityClassification_Char_Trigrams", 
				"classifierFiles/severityClassification_Char_Quadgrams", 
				"classifierFiles/severityClassification_Char_Fivegrams");
	}

	public int instanceCollectionSize() {
		return classificationInstanceCollection.size();
	}
	
	public void add(ClassifierMessage classifierMessage) {
		classificationInstanceCollection.add(classifierMessage);
	}
	
	public void add(ClassifierMessage classifierMessage, FeatureIdCollection featureIdCollection) {
		classificationInstanceCollection.add(featureGenerator, classifierMessage, featureIdCollection);
	}
	
	public void add(ArticleData articleData, int threadId, FeatureIdCollection featureIdCollection) {
		classificationInstanceCollection.add(featureGenerator, articleData, threadId, featureIdCollection);
	}

//	public void add(String url, CommunicationChannelArticle deltaArticle, int threadId) {
//		classificationInstanceCollection.add(url, deltaArticle, threadId);
//	}
	
	public void add(String url, CommunicationChannelArticle deltaArticle, int threadId, ClassificationInstance classificationInstance) {
		classificationInstanceCollection.add(url, deltaArticle, threadId, classificationInstance);
	}

	public ClassificationInstance getClassificationInstance(ClassifierMessage classifierMessage) {
		return classificationInstanceCollection.getClassificationInstance(classifierMessage);
	}
	public Set<Integer> getUnigramOrders(Set<String> unigrams) {
		Set<Integer> unigramOrders = new HashSet<Integer>();
		for (String unigram: unigrams) {
			int unigramid = featureGenerator.getUnigramOrder(unigram);
			if (unigramid>0)
				unigramOrders.add(unigramid);
		}
		return unigramOrders;
	}
	
	public Set<Integer> getBigramOrders(Set<String> bigrams) {
		Set<Integer> bigramOrders = new HashSet<Integer>();
		for (String bigram: bigrams){
			int bigramId = featureGenerator.getBigramOrder(bigram);
			if (bigramId>0)
				bigramOrders.add(bigramId);
		}
		return bigramOrders;
	}

	public Set<Integer> getTrigramOrders(Set<String> trigrams) {
		Set<Integer> trigramOrders = new HashSet<Integer>();
		for (String trigram: trigrams) {
			int trigramId = featureGenerator.getTrigramOrder(trigram);
			if (trigramId>0)
				trigramOrders.add(trigramId);
		}
		return trigramOrders;
	}
	
	public Set<Integer> getQuadgramOrders(Set<String> quadgrams) {
		Set<Integer> quadgramOrders = new HashSet<Integer>();
		for (String quadgram: quadgrams) {
			int quadgramId = featureGenerator.getQuadgramOrder(quadgram);
			if (quadgramId>0)
				quadgramOrders.add(quadgramId);
		}
		return quadgramOrders;
	}

	public Set<Integer> getCharTrigramOrders(Set<String> charTrigrams) {
		Set<Integer> charTrigramOrders = new HashSet<Integer>();
		for (String charTrigram: charTrigrams) {
			int charTrigramId = featureGenerator.getCharTrigramOrder(charTrigram);
			if (charTrigramId>0)
				charTrigramOrders.add(charTrigramId);
		}
		return charTrigramOrders;
	}
	
	public Set<Integer> getCharQuadgramOrders(Set<String> charQuadgrams) {
		Set<Integer> charQuadgramOrders = new HashSet<Integer>();
		for (String charQuadgram: charQuadgrams) {
			int charQuadgramId = featureGenerator.getCharQuadgramOrder(charQuadgram);
			if (charQuadgramId>0)
				charQuadgramOrders.add(charQuadgramId);
		}
		return charQuadgramOrders;
	}

	public Set<Integer> getCharFivegramOrders(Set<String> charFivegrams) {
		Set<Integer> charFivegramOrders = new HashSet<Integer>();
		for (String charFivegram: charFivegrams) {
			int charFivegramId = featureGenerator.getCharFivegramOrder(charFivegram);
			if (charFivegramId>0)
			charFivegramOrders.add(charFivegramId);
		}
		return charFivegramOrders;
	}

	public String getClassificationResult(ClassifierMessage classifierMessage) {
		String composedId = classifierMessage.getComposedId();
		if (classificationResults.containsKey(composedId))
			return classificationResults.get(composedId);
		else {
			System.out.println("No classification result found for classificationInstance: " 
									+ classifierMessage.toString());
			return null;
		}
	}
	
	public void classify() {
		final long startTime = System.currentTimeMillis();
		long previousTime = startTime;
		previousTime = printTimeMessage(startTime, previousTime, instanceCollectionSize(), 
										"initialised featureGenerator");

//		long taggerTime = 0; 
		for (ClassificationInstance classificationInstance: 
					classificationInstanceCollection.getInstanceList()) {
			featureGenerator.updateData(classificationInstance);
//				currentTime = System.currentTimeMillis();
//				taggerTime += (currentTime - previousTime);
//				previousTime = currentTime;
		}
//		System.err.println(time(taggerTime) + "\t" + "tagger time");
		
		previousTime = printTimeMessage(startTime, previousTime, instanceCollectionSize(), 
										"updated featureGenerator");

		List<Double> target_list = featureGenerator.generateTargets(classificationInstanceCollection);
		List<svm_node[]> svm_node_list = featureGenerator.generateFeatures(classificationInstanceCollection);
		
		
		previousTime = printTimeMessage(startTime, previousTime, instanceCollectionSize(), 
										"generated features");

		List<List<Double>> output_list = null;

		if (target_list.size()>0) {
			svm_model model = ClassifierModelSingleton.getInstance().getModel();
			try {
				output_list = svm_predict_nofiles.predict(model, target_list, svm_node_list);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			output_list = new ArrayList<List<Double>>();
		}

		previousTime = printTimeMessage(startTime, previousTime, instanceCollectionSize(), 
										"classification finished");

		classificationResults = new HashMap<String, String>();
		
		for (int index = 0; index < classificationInstanceCollection.size(); index++) {
			
			ClassificationInstance classificationInstance = 
					classificationInstanceCollection.getInstanceList().get(index);
			List<Double> output = output_list.get(index);
			double prediction = output.get(0);

			if ( prediction == (double) 1 )
				classificationResults.put(classificationInstance.getComposedId(), "blocker");
			else if ( prediction == (double) 2 )
				classificationResults.put(classificationInstance.getComposedId(), "critical");
			else if ( prediction == (double) 3 )
				classificationResults.put(classificationInstance.getComposedId(), "major");
			else if ( prediction == (double) 4 )
				classificationResults.put(classificationInstance.getComposedId(), "normal");
			else if ( prediction == (double) 5 )
				classificationResults.put(classificationInstance.getComposedId(), "minor");
			else if ( prediction == (double) 6 )
				classificationResults.put(classificationInstance.getComposedId(), "trivial");
			else if ( prediction == (double) 7 )
				classificationResults.put(classificationInstance.getComposedId(), "enhancement");
			else 
				System.err.println("Irrecognisable classification output: " + prediction);

		}
		
		previousTime = printTimeMessage(startTime, previousTime, instanceCollectionSize(), 
										"classification results processed");
//		return previousTime;
	}

	public void getNGrams(String url, ArticleData articleData) {
		classificationInstanceCollection.getNGrams(url, articleData);
		// TODO Auto-generated method stub
		
	}

	private long printTimeMessage(long startTime, long previousTime, int size, String message) {
		long currentTime = System.currentTimeMillis();
		System.err.println(time(currentTime - previousTime) + "\t" +
						   time(currentTime - startTime) + "\t" +
						   size + "\t" + message);
		return currentTime;
	}

	private String time(long timeInMS) {
		return DurationFormatUtils.formatDuration(timeInMS, "HH:mm:ss,SSS");
	}

}
