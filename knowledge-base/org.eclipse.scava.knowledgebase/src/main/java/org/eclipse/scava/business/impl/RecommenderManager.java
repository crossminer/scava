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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.eclipse.scava.business.IClusterManager;
import org.eclipse.scava.business.IRecommendationProvider;
import org.eclipse.scava.business.IRecommenderManager;
import org.eclipse.scava.business.ISimilarityCalculator;
import org.eclipse.scava.business.ISimilarityManager;
import org.eclipse.scava.business.dto.Query;
import org.eclipse.scava.business.dto.Recommendation;
import org.eclipse.scava.business.dto.RecommendationType;
import org.eclipse.scava.business.integration.ArtifactRepository;
import org.eclipse.scava.business.model.Artifact;
import org.eclipse.scava.business.model.Cluster;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.TextIndexDefinition;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.core.query.TextQuery;
import org.springframework.stereotype.Service;

/**
 * @author Juri Di Rocco
 *
 */
@Service
public class RecommenderManager implements IRecommenderManager {

	private static final Logger logger = Logger.getLogger(RecommenderManager.class);
	@Autowired
	@Qualifier("ApiRecommendation")
	private IRecommendationProvider apiRecommendationProvider;
	@Autowired
	@Qualifier("AlternativeLibraries")
	private IRecommendationProvider alternativeLibrariesrecommendationProvider;
	@Autowired
	private ArtifactRepository artifactRepository;

	@Autowired
	List<ISimilarityCalculator> similarityFunction;
	@Autowired
	private IClusterManager clusterManager;
	@Autowired
	private ISimilarityManager similarityManager;
	@Autowired
	private MongoTemplate template;

	@Override
	public Recommendation getRecommendation(Query query, RecommendationType rt) throws Exception {
		if(rt.equals(RecommendationType.ALTERNATIVE_LIBRARY))
			return alternativeLibrariesrecommendationProvider.getRecommendation(query);
		if(rt.equals(RecommendationType.RECOMMENDED_LIBRARY))
			return apiRecommendationProvider.getRecommendation(query);
		else return null;
	}

	@Override
	public List<Cluster> getClusters(String similarityName) {
		try {
			return clusterManager.getClusters(similarityFunction.get(0));
		} catch (Exception e) {
			return new ArrayList<>();
		}
	}

	@Override
	public List<Artifact> getSimilarProjects(String projectId, String similarityFunction, int numOfResult) {
		Artifact p1 = artifactRepository.findOne(projectId);
		try {
			return similarityManager.getSimilarProjects(p1, getSimilarityCalculator(similarityFunction), numOfResult);
		} catch (Exception e) {
			return new ArrayList<>();
		}
	}

	private ISimilarityCalculator getSimilarityCalculator(String similarityMethod) throws Exception {
		Optional<ISimilarityCalculator> a = similarityFunction.stream()
				.filter(z -> z.getSimilarityName().equals(similarityMethod)).findFirst();
		if (a.isPresent())
			return a.get();
		else
			throw new Exception("similarity method " + similarityMethod + " is unavailable");
	}

	@Override
	public List<Artifact> getArtifactsByQuery(String projectQuery) {
		
		TextCriteria criteria = TextCriteria.forDefaultLanguage().matchingPhrase(projectQuery);

		org.springframework.data.mongodb.core.query.Query query = TextQuery.queryText(criteria).sortByScore()
				.with(new PageRequest(0, 5));

		List<Artifact> recipes = template.find(query, Artifact.class);
		
		return recipes;
	}
}
