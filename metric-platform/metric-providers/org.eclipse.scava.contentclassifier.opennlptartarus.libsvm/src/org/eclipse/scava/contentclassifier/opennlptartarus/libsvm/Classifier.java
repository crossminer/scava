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

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.scava.libsvm.svm_predict_nofiles;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;

import libsvm.svm_model;
import libsvm.svm_node;

public class Classifier {

	List<ClassificationInstance> classificationInstanceList;
	Map<String, String> classificationResults; 
	Map<Double, String> classMapping; 

	public Classifier() {
		classificationInstanceList = new ArrayList<ClassificationInstance>();
		classMapping = loadFromFile("classifierFiles/classMapping");
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
				"classifierFiles/lemmaFeaturesList", 
				"classifierFiles/empiricalFeaturesList");

//		previousTime = printTimeMessage(startTime, previousTime, instanceListSize(), 
//										"initialised featureGenerator");

//		long taggerTime = 0; 
		for (ClassificationInstance xmlItem: classificationInstanceList) {
				featureGenerator.updateData(xmlItem.getComposedId(), 
												xmlItem.getCleanTokenSentences());
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

//		String argumentString = "-b 1 " + path + "classifierFiles/Test-TfIdfFeatures-Clean-AllPoS.m";
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
			if (classMapping.containsKey(prediction))
				classificationResults.put(xmlItem.getComposedId(), classMapping.get(prediction));
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

	private Map<Double, String> loadFromFile(String filename) {
		Map<Double, String> classMap = new HashMap<Double, String>();
		String content = null;
		try {
			URL resource = getClass().getResource("/" + filename);
			content = Resources.toString(resource, Charsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (content != null) {
			for (String line: content.split("\\n")) {
				String[] elements = line.split("\\t");
				double classNumber = Double.parseDouble(elements[0].trim());
				String classLabel = elements[1].trim();
				if (classLabel.length()>0)
					classMap .put(classNumber, classLabel);
			}
		}
		return classMap;
	}

	private static String readFileAsString(File afile) throws java.io.IOException{
	    byte[] buffer = new byte[(int) afile.length()];
	    BufferedInputStream f = null;
	    try {
	        f = new BufferedInputStream(new FileInputStream(afile));
	        f.read(buffer);
	    } finally { 
	    	if (f != null) try { f.close(); } catch (IOException ignored) { }
	    }
	    return new String(buffer);
	}

}
