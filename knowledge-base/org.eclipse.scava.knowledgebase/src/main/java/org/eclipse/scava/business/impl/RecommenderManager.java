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
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.eclipse.scava.business.IAggregatedSimilarityCalculator;
import org.eclipse.scava.business.IClusterCalculator;
import org.eclipse.scava.business.IClusterManager;
import org.eclipse.scava.business.IRecommendationProvider;
import org.eclipse.scava.business.IRecommenderManager;
import org.eclipse.scava.business.ISimilarityCalculator;
import org.eclipse.scava.business.ISimilarityManager;
import org.eclipse.scava.business.ISingleSimilarityCalculator;
import org.eclipse.scava.business.dto.Query;
import org.eclipse.scava.business.dto.Recommendation;
import org.eclipse.scava.business.dto.RecommendationType;
import org.eclipse.scava.business.integration.ArtifactRepository;
import org.eclipse.scava.business.model.Artifact;
import org.eclipse.scava.business.model.Cluster;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.core.query.TextQuery;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import static java.util.stream.Collectors.*;
import static java.util.Map.Entry.*;

/**
 * @author Juri Di Rocco
 *
 */
@Service
public class RecommenderManager implements IRecommenderManager {

	private static final Logger logger = LoggerFactory.getLogger(RecommenderManager.class);

	@Autowired
	@Qualifier("ApiRecommendation")
	private IRecommendationProvider libRecommendationProvider;

	@Autowired
	@Qualifier("AlternativeLibraries")
	private IRecommendationProvider alternativeLibrariesRecommendationProvider;

	@Autowired
	@Qualifier("Focus")
	private IRecommendationProvider focusRecomenderProvider;

	@Autowired
	@Qualifier("ApiCallRecommendation")
	private IRecommendationProvider apiCallRecommendationProvider;

	@Autowired
	@Qualifier("SORecommender")
	private SORecommender soRecommender;

	@Autowired
	private VersionsRecServiceImpl versionRecommender;

	@Autowired
	private ArtifactRepository artifactRepository;

	@Autowired
	private List<ISimilarityCalculator> similarityFunction;

	@Autowired
	private List<IClusterCalculator> clustercalculators;

	@Autowired
	private IClusterManager clusterManager;

	@Autowired
	private ISimilarityManager similarityManager;

	@Autowired
	private MongoTemplate template;

	@Override
	public Recommendation getRecommendation(Query query, RecommendationType rt) throws Exception {
		if (rt.equals(RecommendationType.ALTERNATIVE_LIBRARY))
			return alternativeLibrariesRecommendationProvider.getRecommendation(query);
		if (rt.equals(RecommendationType.RECOMMENDED_LIBRARY))
			return libRecommendationProvider.getRecommendation(query);
		if (rt.equals(RecommendationType.API_CALL))
			return apiCallRecommendationProvider.getRecommendation(query);
		if (rt.equals(RecommendationType.FOCUS))
			return focusRecomenderProvider.getRecommendation(query);
		if (rt.equals(RecommendationType.API_DOCUMENTATION))
			return soRecommender.getRecommendation(query);
		if (rt.equals(RecommendationType.VERSION))
			return versionRecommender.getRecommendation(query);
		else {
			logger.error("Recommendation not supported");
			return null;
		}
	}

	@Override
	public List<Cluster> getClusters(String similarityName, String algorithmName) {
		try {
			ISimilarityCalculator currentSimilarityCalculator = null;
			for (ISimilarityCalculator iSimilarityCalculator : similarityFunction) {
				if (similarityName.equals(iSimilarityCalculator.getSimilarityName()))
					currentSimilarityCalculator = iSimilarityCalculator;
			}
			IClusterCalculator currentClusterCalculator = null;
			for (IClusterCalculator iClusterSimilarityCalculator : clustercalculators) {
				if (algorithmName.equals(iClusterSimilarityCalculator.getClusterName()))
					currentClusterCalculator = iClusterSimilarityCalculator;
			}
			return clusterManager.getClusters(currentSimilarityCalculator, currentClusterCalculator);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new ArrayList<>();
		}
	}

	@Override
	public Map<String, Double> getSimilarProjects(Artifact projectId, String similarityFunction, int numOfResult) {

		ISimilarityCalculator simCalc;
		try {
			simCalc = getSimilarityCalculator(similarityFunction);

			List<Artifact> arts = artifactRepository.findAll().stream().filter(z -> simCalc.appliesTo(z))
					.collect(Collectors.toList());
			Map<String, Double> res = Maps.newHashMap();
			if (simCalc instanceof ISingleSimilarityCalculator)
				for (Artifact art : arts)
					res.put(art.getFullName(), ((ISingleSimilarityCalculator) simCalc).calculateSimilarity(projectId, art));
			if (simCalc instanceof IAggregatedSimilarityCalculator) {
				Map<String, String> parameters = Maps.newHashMap();
				parameters.put("committers","false");
				parameters.put("deps","true");
				parameters.put("stargazers","true");
				parameters.put("freqDeps", "129");
				res = ((IAggregatedSimilarityCalculator) simCalc).calculateAggregatedSimilarityValues(arts, parameters).row(projectId.getFullName());
			}
			res = res
			        .entrySet()
			        .stream()
			        .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
			        .limit(numOfResult)
			        .collect(
			            toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
			                LinkedHashMap::new));
			return res;
		} catch (Exception e) {
			return Maps.newHashMap();
		}
	}

	@Override
	public List<Artifact> getSimilarProjects(String projectId, String similarityFunction, int numOfResult) {
		Artifact p1 = artifactRepository.findOne(projectId);
		try {
			return similarityManager.getSimilarProjects(p1, getSimilarityCalculator(similarityFunction), numOfResult);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new ArrayList<>();
		}
	}

	private ISimilarityCalculator getSimilarityCalculator(String similarityMethod) throws Exception {
		Optional<ISimilarityCalculator> a = similarityFunction.stream()
				.filter(z -> z.getSimilarityName().equals(similarityMethod)).findFirst();
		if (a.isPresent())
			return a.get();
		else {
			logger.error("similarity method " + similarityMethod + " is unavailable");
			throw new Exception("similarity method " + similarityMethod + " is unavailable");
		}

	}

	@Override
	public List<Artifact> getArtifactsByQuery(String projectQuery) {
		TextCriteria criteria = TextCriteria.forDefaultLanguage().matchingPhrase(projectQuery);
		org.springframework.data.mongodb.core.query.Query query = TextQuery.queryText(criteria).sortByScore()
				.with(new PageRequest(0, 5));
		List<Artifact> recipes = template.find(query, Artifact.class);
		return recipes;
	}

	@Override
	public List<Artifact> getArtifactsByQuery(String projectQuery, Pageable page) {
		TextCriteria criteria = TextCriteria.forDefaultLanguage().matchingPhrase(projectQuery);
		org.springframework.data.mongodb.core.query.Query query = TextQuery.queryText(criteria).sortByScore()
				.with(page);

		List<Artifact> recipes = template.find(query, Artifact.class);
		if (page.getSort().getOrderFor("temp").getDirection() == Direction.ASC)
			return Lists.reverse(recipes);
		return recipes;
	}

	@Override
	public Cluster getClusterByArtifact(String artId, String simCalculator, String clusterAlgorithm) {
		ISimilarityCalculator currentSimilarityCalculator = null;
		for (ISimilarityCalculator iSimilarityCalculator : similarityFunction) {
			if (simCalculator.equals(iSimilarityCalculator.getSimilarityName()))
				currentSimilarityCalculator = iSimilarityCalculator;
		}
		IClusterCalculator currentClusterCalculator = null;
		for (IClusterCalculator iClusterSimilarityCalculator : clustercalculators) {
			if (clusterAlgorithm.equals(iClusterSimilarityCalculator.getClusterName()))
				currentClusterCalculator = iClusterSimilarityCalculator;
		}
		Artifact art = artifactRepository.findOne(artId);
		return clusterManager.getClusterFromArtifact(art, currentSimilarityCalculator, currentClusterCalculator);
	}
}
