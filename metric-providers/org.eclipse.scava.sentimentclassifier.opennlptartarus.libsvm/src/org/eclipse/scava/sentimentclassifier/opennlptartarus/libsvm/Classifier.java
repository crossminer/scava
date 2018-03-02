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

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import libsvm.svm_model;
import libsvm.svm_node;

import org.eclipse.scava.libsvm.svm_predict_nofiles;

public class Classifier {

	private List<ClassificationInstance> classificationInstanceList;
	private Map<String, String> classificationResults; 

	public Classifier() {
		classificationInstanceList = new ArrayList<ClassificationInstance>();
	}

	public int instanceListSize() {
		return classificationInstanceList.size();
	}
	
	public void add(ClassificationInstance classificationInstance) {
		classificationInstanceList.add(classificationInstance);
	}
	
	public String getClassificationResult(ClassificationInstance classificationInstance) {
		String composedId = classificationInstance.getComposedId();
		if (classificationResults.containsKey(composedId))
			return classificationResults.get(composedId);
		else {
			System.out.println("No classification result found for classificationInstance: " 
									+ classificationInstance.toString());
			return null;
		}
	}
	
	public void classify() {

		FeatureGenerator featureGenerator = new FeatureGenerator(
				"classifierFiles/sentimentAnalysis_Unigrams", 
				"classifierFiles/sentimentAnalysis_Bigrams", 
				"classifierFiles/sentimentAnalysis_Trigrams", 
				"classifierFiles/sentimentAnalysis_Quadgrams", 
				"classifierFiles/sentimentAnalysis_Pairs", 
				"classifierFiles/sentimentAnalysis_Char_Trigrams", 
				"classifierFiles/sentimentAnalysis_Char_Quadgrams", 
				"classifierFiles/sentimentAnalysis_Char_Fivegrams", 
				"classifierFiles/sentimentAnalysis_HeuristicAndPoS", 
				"classifierFiles/sentimentAnalysis_CMUclusters");

//		previousTime = printTimeMessage(startTime, previousTime, instanceListSize(), 
//										"initialised featureGenerator");

//		long taggerTime = 0; 
		for (ClassificationInstance classificationInstance: classificationInstanceList) {
			featureGenerator.updateData(classificationInstance);
//				currentTime = System.currentTimeMillis();
//				taggerTime += (currentTime - previousTime);
//				previousTime = currentTime;
		}
//		System.err.println(time(taggerTime) + "\t" + "tagger time");
		
//		previousTime = printTimeMessage(startTime, previousTime, instanceListSize(), 
//										"updated featureGenerator");

		List<Double> target_list = featureGenerator.generateTargets(classificationInstanceList);
		List<svm_node[]> svm_node_list = featureGenerator.generateFeatures(classificationInstanceList);
		
		
//		previousTime = printTimeMessage(startTime, previousTime, instanceListSize(), 
//										"generated features");

		svm_model model = ClassifierModelSingleton.getInstance().getModel();
		
		List<List<Double>> output_list = null;
		try {
			output_list = svm_predict_nofiles.predict(model, target_list, svm_node_list);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

//		previousTime = printTimeMessage(startTime, previousTime, instanceListSize(), 
//										"classification finished");

		classificationResults = new HashMap<String, String>();
		for (int index = 0; index < classificationInstanceList.size(); index++) {
			ClassificationInstance xmlItem = classificationInstanceList.get(index);
			List<Double> output = output_list.get(index);
			double prediction = output.get(0);
			if (prediction==(double)1)
				classificationResults.put(xmlItem.getComposedId(), "Positive");
			else if (prediction==(double)-1)
				classificationResults.put(xmlItem.getComposedId(), "Negative");
			else if (prediction==(double)0)
				classificationResults.put(xmlItem.getComposedId(), "Neutral");
			else
				System.err.println("Irrecognisable classification output: " + prediction);
		}
		
//		previousTime = printTimeMessage(startTime, previousTime, instanceListSize(), 
//										"classification finished");
//		return previousTime;
	}

//	private long printTimeMessage(long startTime, long previousTime, int size, String message) {
//		long currentTime = System.currentTimeMillis();
//		System.err.println(time(currentTime - previousTime) + "\t" +
//						   time(currentTime - startTime) + "\t" +
//						   size + "\t" + message);
//		return currentTime;
//	}

//	private String time(long timeInMS) {
//		return DurationFormatUtils.formatDuration(timeInMS, "HH:mm:ss,SSS");
//	}

}
