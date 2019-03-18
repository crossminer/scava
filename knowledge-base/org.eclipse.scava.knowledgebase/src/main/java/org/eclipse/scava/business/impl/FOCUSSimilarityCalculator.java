/*******************************************************************************
 * Copyright (C) 2017 University of L'Aquila
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.business.impl;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.eclipse.scava.business.IAggregatedSimilarityCalculator;
import org.eclipse.scava.business.model.Artifact;
import org.eclipse.scava.business.model.MethodDeclaration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Sets;
import com.google.common.collect.Table; 


class ValueComparators implements Comparator<Artifact> {
	Map<Artifact, Float> base;

	public ValueComparators(Map<Artifact, Float> base) {
		this.base = base;
	}

	// Note: this comparator imposes orderings that are inconsistent with equals.
	public int compare(Artifact a, Artifact b) {
		float va = base.get(a);
		float vb = base.get(b);
		if (va > vb) {
			return -1;
		} else if (va == vb) {
			return a.getName().compareTo(b.getName());
		} else {
			return 1;
		}
	}
}
@Service
@Qualifier("Focus")
public class FOCUSSimilarityCalculator implements IAggregatedSimilarityCalculator {

	private static final Logger log = LoggerFactory.getLogger(FOCUSSimilarityCalculator.class);
	
	@Override
	public boolean appliesTo(Artifact art) {
		return (!art.getMethodDeclarations().isEmpty());
	}

	@Override
	public String getSimilarityName() {
		return "Focus";
	}

	@Override
	public Table<String, String, Double> calculateAggregatedSimilarityValues(List<Artifact> artifacts, Map<String, String> params) {
		Table<String, String, Double> result = HashBasedTable.create();
		for (Artifact artifact : artifacts) {
			Map<Artifact, Float> map = computeSimilarity(artifact,artifacts);
			for (Artifact art : map.keySet()) {
				result.put(artifact.getFullName(), art.getFullName(), map.get(art).doubleValue());
			}
			result.put(artifact.getFullName(), artifact.getFullName(), 1.0);
		}
		return result;
	}
	
	public Map<Artifact, Float> computeSimilarity(Artifact testing, List<Artifact> trainings) {
		Map<Artifact, Map<String, Integer>> trainingProjectsWithMIAndFrequency = new HashMap<>();
		for (Artifact training : trainings)
			trainingProjectsWithMIAndFrequency.putAll(getProjectsInvocationsWithFrequency(training));

		Map<Artifact, Map<String, Integer>> testingProject2 = getProjectsInvocationsWithFrequency(testing);

		trainingProjectsWithMIAndFrequency.putAll(testingProject2);
		TreeMap<Artifact, Float> v = computeSimilarity(testing, trainingProjectsWithMIAndFrequency);
		trainingProjectsWithMIAndFrequency.remove(testing);
		return v;
	}
	/**
	 * Compute the similarity between the project testingPro and all the projects in
	 * the supplied list and serialize the results.
	 * @return 
	 */
	private TreeMap<Artifact, Float> computeSimilarity(Artifact testingPro,
			Map<Artifact, Map<String, Integer>> trainingProjectsWithMIAndFrequency) {
		Map<String, Integer> termMIsFrequency = computeTermFrequency(trainingProjectsWithMIAndFrequency);
		Map<String, Float> testingProjectVector = new HashMap<>();
		Map<Artifact, Float> projectSimilarities = new HashMap<>();

		log.info("Computing similarity between %s and all other projects", testingPro);

		// Computes the feature vector of the testing project,
		// ie. the TF-IDF for all its invocations
		Map<String, Integer> miWihtFrequency = trainingProjectsWithMIAndFrequency.get(testingPro);
		for (String term : miWihtFrequency.keySet()) {
			float tfIdf = computeTF_IDF(miWihtFrequency.get(term), trainingProjectsWithMIAndFrequency.size(), termMIsFrequency.get(term));
			testingProjectVector.put(term, tfIdf);
		}

		// Compute the feature vector of all training projects in the corpus and
		// store their similarity with the testing project in the similarity vector
		for (Artifact trainingProject : trainingProjectsWithMIAndFrequency.keySet()) {
			if (!trainingProject.equals(testingPro)) {
				Map<String, Float> trainingProjectVector = new HashMap<>();
				miWihtFrequency = trainingProjectsWithMIAndFrequency.get(trainingProject);

				for (String term : miWihtFrequency.keySet()) {
					float tfIdf = computeTF_IDF(miWihtFrequency.get(term), trainingProjectsWithMIAndFrequency.size(), termMIsFrequency.get(term));
					trainingProjectVector.put(term, tfIdf);
				}

				float similarity = computeCosineSimilarity(testingProjectVector, trainingProjectVector);
				projectSimilarities.put(trainingProject, similarity);
			}
		}

		// Order projects by similarity in a sortedMap
		ValueComparators bvc = new ValueComparators(projectSimilarities);
		TreeMap<Artifact, Float> sortedMap = new TreeMap<>(bvc);
		sortedMap.putAll(projectSimilarities);

		// Store similarities in the evaluation directory
		//reader.writeSimilarityScores(simDir, testingPro, sortedMap);
		log.info("Focus computed similarity between %s and all other projects", testingPro);
		return sortedMap;
	}

	private Map<Artifact, Map<String, Integer>> getProjectsInvocationsWithFrequency(Artifact training) {
		Map<Artifact, Map<String, Integer>> methodInvocations = new HashMap<Artifact, Map<String, Integer>>();
		Map<String, Integer> terms = new HashMap<String, Integer>();

		int freq = 0;
		for (MethodDeclaration md : training.getMethodDeclarations()) {
			for (String mi : md.getMethodInvocations()) {
				if (terms.containsKey(mi))
					freq = terms.get(mi) + 1;
				else
					freq = 1;
				terms.put(mi, freq);
			}

		}
		methodInvocations.put(training, terms);
		return methodInvocations;
	}

	/**
	 * Compute the cosine similarity between two project vectors
	 */
	private float computeCosineSimilarity(Map<String, Float> v1, Map<String, Float> v2) {
		Set<String> both = Sets.intersection(v1.keySet(), v2.keySet());
		double scalar = 0, norm1 = 0, norm2 = 0;

		// Only perform cosine similarity on words that exist in both lists
		if (both.size() > 0) {
			for (Float f : v1.values())
				norm1 += f * f;

			for (Float f : v2.values())
				norm2 += f * f;

			for (String k : both)
				scalar += v1.get(k) * v2.get(k);

			if (scalar == 0)
				return 0f;
			else
				return (float) (scalar / Math.sqrt(norm1 * norm2));
		} else {
			return 0f;
		}
	}
	/**
	 * Compute a term-frequency map which stores, for every invocation, how many
	 * projects in the supplied list invoke it
	 * 
	 * java/util/ArrayList/ArrayList()=131 java/util/List/add(E)=129
	 * java/io/PrintStream/println(java.lang.String)=128
	 */
	private Map<String, Integer> computeTermFrequency(Map<Artifact, Map<String, Integer>> projects) {
		Map<String, Integer> termFrequency = new HashMap<>();

		for (Map<String, Integer> terms : projects.values()) {
			for (String term : terms.keySet()) {
				termFrequency.put(term, termFrequency.getOrDefault(term, 0) + 1);
			}
		}
		return termFrequency;
	}

	/**
	 * Standard term-frequency inverse document frequency calculation
	 */
	private float computeTF_IDF(int count, int total, int freq) {
		return (float) (count * Math.log(total / freq));
	}
	
	/**
	 * Compute the similarity between two vectors using Jaccard Similarity
	 */
	public float computeJaccardSimilarity(byte[] vector1, byte[] vector2) {
		int count = 0;
		int length = vector1.length;

		for (int i = 0; i < length; i++)
			if (vector1[i] == 1.0 && vector2[i] == 1.0)
				count++;

		return (float) count / (2 * length - count);
	}
}
