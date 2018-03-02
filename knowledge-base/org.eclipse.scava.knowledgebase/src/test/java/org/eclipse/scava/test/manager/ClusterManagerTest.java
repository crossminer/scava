/*******************************************************************************
 * Copyright (C) 2017 University of L'Aquila
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.test.manager;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.scava.Application;
import org.eclipse.scava.business.IAggregatedSimilarityCalculator;
import org.eclipse.scava.business.ISimilarityCalculator;
import org.eclipse.scava.business.ISimilarityManager;
import org.eclipse.scava.business.impl.ClusterManager;
import org.eclipse.scava.business.impl.OssmeterImporter;
import org.eclipse.scava.business.integration.ArtifactRepository;
import org.eclipse.scava.business.integration.ClusterRepository;
import org.eclipse.scava.business.integration.ClusterizationRepository;
import org.eclipse.scava.business.integration.GithubUserRepository;
import org.eclipse.scava.business.integration.RelationRepository;
import org.eclipse.scava.business.model.Artifact;
import org.eclipse.scava.business.model.Cluster;
import org.eclipse.scava.business.model.Clusterization;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {Application.class})
@TestPropertySource(locations="classpath:application.properties")
public class ClusterManagerTest {

	@Autowired
	private ClusterRepository clusterRepository;
	
	@Autowired
	private ClusterizationRepository clusterizationRepository;
	
	@Autowired
	private RelationRepository relationRepository;
	@Autowired
	private IAggregatedSimilarityCalculator aggregateSimilarityCalculator;
	
	@Autowired
	@Qualifier ("Readme")
	private ISimilarityCalculator simReadmeCalculator;
	
	@Autowired
	@Qualifier ("Dependency")
	private ISimilarityCalculator simDependencyCalculator;
	
	@Autowired
	@Qualifier ("RepoPalCompound")
	private ISimilarityCalculator repoPalCompoundSimilarityCalculator;
	
	@Autowired
	private ArtifactRepository artifactRepository;
	
	@Autowired
	private ISimilarityManager similarityManager;
	
	@Autowired
	private ClusterManager clusterManager;

	@Autowired
	OssmeterImporter ossmeterImporter;
	
	@Autowired
	private GithubUserRepository githubUserRepository;
	
	private static final Logger logger = Logger.getLogger(SimilarityManagerTest.class);
	private List<Artifact> artifacts;
	@Before
	public void testCreateAndStoreDistanceMatrix() {
		artifactRepository.deleteAll();
		githubUserRepository.deleteAll();
		relationRepository.deleteAll();
		clusterRepository.deleteAll();
		clusterizationRepository.deleteAll();
		try {
			ObjectMapper mapper = new ObjectMapper();
			Resource resource = new ClassPathResource("artifacts.json");
			InputStream resourceInputStream = resource.getInputStream();
			artifacts = mapper.readValue(resourceInputStream, new TypeReference<List<Artifact>>(){});
			artifactRepository.save(artifacts);
			for (Artifact artifact : artifacts) {
				ossmeterImporter.storeGithubUser(artifact.getStarred(), artifact.getFullName());
				ossmeterImporter.storeGithubUserCommitter(artifact.getCommitteers(), artifact.getFullName());
			} 
			resourceInputStream.close();
			
			similarityManager.createAndStoreDistanceMatrix(aggregateSimilarityCalculator);
			assertEquals(((artifacts.size() * (artifacts.size() -1))/2), 
					relationRepository.findAllByTypeName(aggregateSimilarityCalculator.getSimilarityName()).size());
			clusterManager.calculateAndStoreClusterization(aggregateSimilarityCalculator);
			
			similarityManager.createAndStoreDistanceMatrix(simReadmeCalculator);
			assertEquals(((artifacts.size() * (artifacts.size() -1))/2), 
					relationRepository.findAllByTypeName(simReadmeCalculator.getSimilarityName()).size());
			clusterManager.calculateAndStoreClusterization(simReadmeCalculator);
			
			similarityManager.createAndStoreDistanceMatrix(simDependencyCalculator);
			assertEquals(((artifacts.size() * (artifacts.size() -1))/2), 
					relationRepository.findAllByTypeName(simDependencyCalculator.getSimilarityName()).size());
			clusterManager.calculateAndStoreClusterization(simDependencyCalculator);
			
			similarityManager.createAndStoreDistanceMatrix(repoPalCompoundSimilarityCalculator);
			assertEquals(((artifacts.size() * (artifacts.size() -1))/2), 
					relationRepository.findAllByTypeName(repoPalCompoundSimilarityCalculator.getSimilarityName()).size());
			clusterManager.calculateAndStoreClusterization(repoPalCompoundSimilarityCalculator);
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}
	@Before
	public void dispose(){
		artifactRepository.deleteAll();
		githubUserRepository.deleteAll();
		clusterRepository.deleteAll();
		relationRepository.deleteAll();
		clusterizationRepository.deleteAll();
	}
	
	
	@Test
	public void testGetClusters() throws Exception {
		List<Cluster> clusters = clusterManager.getClusters(repoPalCompoundSimilarityCalculator);
		assertNotNull(clusters);
		assertNotEquals(clusters.size(), 0);
		
		clusters = clusterManager.getClusters(simDependencyCalculator);
		assertNotNull(clusters);
		assertNotEquals(clusters.size(), 0);
		
		clusters = clusterManager.getClusters(simReadmeCalculator);
		assertNotNull(clusters);
		assertNotEquals(clusters.size(), 0);
		
		clusters = clusterManager.getClusters(aggregateSimilarityCalculator);
		assertNotNull(clusters);
		assertNotEquals(clusters.size(), 0);
		
	}

	@Test
	public void testGetClusterFromArtifact() {
		Cluster cluster = clusterManager.getClusterFromArtifact(artifacts.get(0), simDependencyCalculator);
		assertNotNull(cluster);
	}

	@Test
	public void testDeleteClusterization() {
		Clusterization clusterization = clusterManager.getClusterizationBySimilarityMethodLastDate(simReadmeCalculator);
		clusterManager.deleteClusterization(clusterization);
	}

	@Test
	public void testGetOneByArtifactsName() {
		Cluster cluster = clusterManager.getOneByArtifactsName("AlipayOrdersSupervisor-GUI", simDependencyCalculator);
		assertNotNull(cluster);
	}

	@Test
	public void testGetClusterizationBySimilarityMethodLastDate() {
		Clusterization clusterization = clusterManager.getClusterizationBySimilarityMethodLastDate(simReadmeCalculator);
		assertNotNull(clusterization);
	}

	@Test
	public void testGetClusterByArtifactsIdAndClusterizationId() {
		Clusterization clusterization = clusterManager.getClusterizationBySimilarityMethodLastDate(simReadmeCalculator);
		Cluster cluster = clusterManager.getClusterByArtifactsIdAndClusterizationId(artifacts.get(0).getId(),
				clusterization.getId());
		assertNotNull(cluster);
	}

}
