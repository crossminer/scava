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
import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.eclipse.scava.business.IClusterCalculator;
import org.eclipse.scava.business.IClusterManager;
import org.eclipse.scava.business.ISimilarityCalculator;
import org.eclipse.scava.business.integration.ArtifactRepository;
import org.eclipse.scava.business.integration.ClusterRepository;
import org.eclipse.scava.business.integration.ClusterizationRepository;
import org.eclipse.scava.business.model.Artifact;
import org.eclipse.scava.business.model.Cluster;
import org.eclipse.scava.business.model.Clusterization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

/**
 * @author Juri Di Rocco
 *
 */
@Service
public class ClusterManager implements IClusterManager{
	@Autowired
	@Qualifier ("Readme")
	private ISimilarityCalculator simReadmeCalculator;
	
	@Autowired
	private MongoOperations mongoOperations;
	
	@Autowired
	@Qualifier ("Dependency")
	private ISimilarityCalculator simDependencyCalculator;
	
	@Autowired
	@Qualifier ("Compound")
	private ISimilarityCalculator simCompoundCalculator;
	@Autowired
	@Qualifier ("RepoPalCompound")
	private ISimilarityCalculator repoPalCompoundSimilarity;
	@Autowired
	@Qualifier ("RepoPalCompoundV2")
	private ISimilarityCalculator repoPalCompoundSimilarityV2;
	
	@Autowired
	private ArtifactRepository artifactRepository;
	
	@Autowired
	private ClusterizationRepository clusterizationRepository;
	@Autowired
	private ClusterRepository clusterRepository;
	
	private static final double _PARTITION_OR_THRESHOLD = 5;

	@Override
	public void calculateAndStoreClusterization(ISimilarityCalculator simCalc, IClusterCalculator libraryClusterCalculator) {
		Clusterization clusterization = new Clusterization();
		clusterization.setClusterizationDate(new Date());
		List<Cluster> clusters = new ArrayList<>();
		clusters  = libraryClusterCalculator.calculateCluster(simCalc, _PARTITION_OR_THRESHOLD);
		clusterization.setSimilarityMethod(simCalc.getSimilarityName());
		clusterization.setClusterAlgorithm(libraryClusterCalculator.getClusterName());
		clusterizationRepository.save(clusterization);
		for (Cluster cluster : clusters) {
			cluster.setClusterization(clusterization);
			clusterRepository.save(cluster);
		}	
	}
	
	@Override
	public List<Cluster> getClusters(ISimilarityCalculator simCalc, IClusterCalculator clusterAlgoritms) throws Exception {
		//TODO USE CLUSTERALGORITHMS
		Clusterization clusterization = clusterizationRepository.findTopBySimilarityMethodAndClusterAlgorithmOrderByClusterizationDate(simCalc.getSimilarityName(), clusterAlgoritms.getClusterName());
		if (clusterization == null)
			throw new Exception("No clusterization found!");
		
		Query q1 = new Query(Criteria.where("clusterization.$id").is(new ObjectId(clusterization.getId())));
		List<Cluster> result = mongoOperations.find(q1, Cluster.class);
		return result;
	}
	
	@Override
	public Cluster getClusterFromArtifact(Artifact art, ISimilarityCalculator simCalc, IClusterCalculator clusterAlgorithm){
		Clusterization clusterization = clusterizationRepository.findTopBySimilarityMethodAndClusterAlgorithmOrderByClusterizationDate(simCalc.getSimilarityName(), clusterAlgorithm.getClusterName());
		
		Query q1 = new Query();
		q1.addCriteria(Criteria.where("clusterization.$id").is(new ObjectId(clusterization.getId())).orOperator(
				Criteria.where("artifacts._id").is(new ObjectId(art.getId())),
				Criteria.where("mostRepresentative._id").is(new ObjectId(art.getId()))));
		
		Cluster result = mongoOperations.findOne(q1, Cluster.class);
		return result;
	}

	@Override
	public void deleteClusterization(Clusterization clusterization) {
		List<Cluster> clusters = clusterRepository.findByClusterizationId(new ObjectId(clusterization.getId()));
		for (Cluster cluster : clusters) {
			clusterRepository.delete(cluster);
		}
		clusterizationRepository.findTopByOrderByClusterizationDate();
		clusterizationRepository.delete(clusterization);
	}

	@Override
	public Cluster getOneByArtifactsName(String artifactName, ISimilarityCalculator simCalc, IClusterCalculator clusterAlgorithm) {
		Artifact artifact = artifactRepository.findOneByName(artifactName);
		Clusterization clusterization = clusterizationRepository.findTopBySimilarityMethodAndClusterAlgorithmOrderByClusterizationDate(simCalc.getSimilarityName(), clusterAlgorithm.getClusterName());
		Query q1 = new Query();
		q1.addCriteria(Criteria.where("clusterization.$id").is(new ObjectId(clusterization.getId())).orOperator(
				Criteria.where("artifacts._id").is(new ObjectId(artifact.getId())),
				Criteria.where("mostRepresentative._id").is(new ObjectId(artifact.getId()))));
		
		Cluster result = mongoOperations.findOne(q1, Cluster.class);
		return result;
	}

	@Override
	public Clusterization getClusterizationBySimilarityMethodLastDate(ISimilarityCalculator simDependencyCalculator, IClusterCalculator clusterAlgorithm) {
		return clusterizationRepository.findTopBySimilarityMethodAndClusterAlgorithmOrderByClusterizationDate(simDependencyCalculator.getSimilarityName(), clusterAlgorithm.getClusterName());
	}

	@Override
	public Cluster getClusterByArtifactsIdAndClusterizationId(String artifactId, String clusterizationId, IClusterCalculator clusterAlgorithm) {
		Query q1 = new Query();
		q1.addCriteria(Criteria.where("clusterization.$id").is(new ObjectId(clusterizationId)).orOperator(
				Criteria.where("artifacts._id").is(new ObjectId(artifactId)),
				Criteria.where("mostRepresentative._id").is(new ObjectId(artifactId))));
				
		Cluster result = mongoOperations.findOne(q1, Cluster.class);
		
		return result;
	}
	
}
