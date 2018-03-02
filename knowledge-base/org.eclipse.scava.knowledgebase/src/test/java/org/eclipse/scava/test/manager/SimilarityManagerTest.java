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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.eclipse.scava.Application;
import org.eclipse.scava.business.IAggregatedSimilarityCalculator;
import org.eclipse.scava.business.ISimilarityCalculator;
import org.eclipse.scava.business.ISimilarityManager;
import org.eclipse.scava.business.ISingleSimilarityCalculator;
import org.eclipse.scava.business.impl.OssmeterImporter;
import org.eclipse.scava.business.integration.ArtifactRepository;
import org.eclipse.scava.business.integration.GithubUserRepository;
import org.eclipse.scava.business.integration.RelationRepository;
import org.eclipse.scava.business.model.Artifact;
import org.eclipse.scava.business.model.Relation;
import org.junit.After;
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

public class SimilarityManagerTest {

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
	@Qualifier ("Compound")
	private ISimilarityCalculator simCompoundCalculator;
	
	@Autowired
	@Qualifier("RepoPalStar")
	private ISingleSimilarityCalculator repoPalStarSimilarityCalculator;
	
	@Autowired
	@Qualifier ("RepoPalCompound")
	private ISimilarityCalculator repoPalCompoundSimilarityCalculator;
	
	@Autowired
	@Qualifier ("RepoPalCompoundV2")
	private ISimilarityCalculator repoPalCompoundSimilarityCalculatorV2;
	
	@Autowired
	private ArtifactRepository artifactRepository;
	
	
	@Autowired
	private ISimilarityManager similarityManager;
	
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
			
			similarityManager.createAndStoreDistanceMatrix(simReadmeCalculator);
			assertEquals(((artifacts.size() * (artifacts.size() -1))/2), 
					relationRepository.findAllByTypeName(simReadmeCalculator.getSimilarityName()).size());
			
			similarityManager.createAndStoreDistanceMatrix(simDependencyCalculator);
			assertEquals(((artifacts.size() * (artifacts.size() -1))/2), 
					relationRepository.findAllByTypeName(simDependencyCalculator.getSimilarityName()).size());
			
			similarityManager.createAndStoreDistanceMatrix(repoPalStarSimilarityCalculator);
			assertEquals(((artifacts.size() * (artifacts.size() -1))/2), 
					relationRepository.findAllByTypeName(repoPalStarSimilarityCalculator.getSimilarityName()).size());
			
			similarityManager.createAndStoreDistanceMatrix(simCompoundCalculator);
			assertEquals(((artifacts.size() * (artifacts.size() -1))/2), 
					relationRepository.findAllByTypeName(simCompoundCalculator.getSimilarityName()).size());
			
			similarityManager.createAndStoreDistanceMatrix(repoPalCompoundSimilarityCalculator);
			assertEquals(((artifacts.size() * (artifacts.size() -1))/2), 
					relationRepository.findAllByTypeName(repoPalCompoundSimilarityCalculator.getSimilarityName()).size());
			
			similarityManager.createAndStoreDistanceMatrix(repoPalCompoundSimilarityCalculatorV2);
			assertEquals(((artifacts.size() * (artifacts.size() -1))/2), 
					relationRepository.findAllByTypeName(repoPalCompoundSimilarityCalculatorV2.getSimilarityName()).size());
			
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}
	@After
	public void dispose(){
		artifactRepository.deleteAll();
		githubUserRepository.deleteAll();
		relationRepository.deleteAll();
	}
	
	
	@Test
	public void testGetSimilarProjectsRelations() {
		
		Set<Relation> rels = similarityManager.getSimilarProjectsRelations(artifacts.get(0), simReadmeCalculator);
		assertEquals(artifacts.size()-1, rels.size());
		rels = similarityManager.getSimilarProjectsRelations(artifacts.get(0), simDependencyCalculator);
		assertEquals(artifacts.size()-1, rels.size());
		rels = similarityManager.getSimilarProjectsRelations(artifacts.get(0), repoPalStarSimilarityCalculator);
		assertEquals(artifacts.size()-1, rels.size());
		rels = similarityManager.getSimilarProjectsRelations(artifacts.get(0), simCompoundCalculator);
		assertEquals(artifacts.size()-1, rels.size());
		rels = similarityManager.getSimilarProjectsRelations(artifacts.get(0), repoPalCompoundSimilarityCalculator);
		assertEquals(artifacts.size()-1, rels.size());
		rels = similarityManager.getSimilarProjectsRelations(artifacts.get(0), repoPalCompoundSimilarityCalculatorV2);
		assertEquals(artifacts.size()-1, rels.size());
	}



	@Test
	public void testGetSimilarProjectsArtifactDouble() {
		List<Artifact> result = similarityManager.getSimilarProjects(artifacts.get(0), 0.1);
		assertNotEquals(result.size(), 0);
	}
	

	
//
//	@Test
//	public void testGetDistanceMatrix() {
//		fail("Not yet implemented");
//	}
//
	@Test
	public void testGetRelation() {
		assertNotNull(similarityManager.getRelation(artifacts.get(0), artifacts.get(1), simReadmeCalculator));
		assertNotNull(similarityManager.getRelation(artifacts.get(0), artifacts.get(1), repoPalStarSimilarityCalculator));
		assertNotNull(similarityManager.getRelation(artifacts.get(0), artifacts.get(1), simCompoundCalculator));
		assertNotNull(similarityManager.getRelation(artifacts.get(0), artifacts.get(1), repoPalCompoundSimilarityCalculator));
		assertNotNull(similarityManager.getRelation(artifacts.get(0), artifacts.get(1), repoPalCompoundSimilarityCalculatorV2));	
	}
	@Test
	public void testGetSimilarProjectsArtifactISimilarityCalculatorInt() {
		assertEquals(similarityManager.getSimilarProjects(artifacts.get(0), simReadmeCalculator, 4).size(),4);
		assertEquals(similarityManager.getSimilarProjects(artifacts.get(0), repoPalStarSimilarityCalculator, 4).size(),4);
		assertEquals(similarityManager.getSimilarProjects(artifacts.get(0), simCompoundCalculator, 4).size(),4);
		assertEquals(similarityManager.getSimilarProjects(artifacts.get(0), repoPalCompoundSimilarityCalculator, 4).size(),4);
		assertEquals(similarityManager.getSimilarProjects(artifacts.get(0), repoPalCompoundSimilarityCalculatorV2, 4).size(),4);
	}
	@Test
	public void testGetSimilarProjectsArtifactInt() {
		assertEquals(similarityManager.getSimilarProjects(artifacts.get(0), 5).size(),5);
	}
//
	@Test
	public void testAppliableProjects() {
		assertEquals(similarityManager.appliableProjects(simReadmeCalculator, artifacts).size(),artifacts.size());
		assertEquals(similarityManager.appliableProjects(repoPalStarSimilarityCalculator, artifacts).size(),artifacts.size());
		assertEquals(similarityManager.appliableProjects(simCompoundCalculator, artifacts).size(),artifacts.size());
		assertEquals(similarityManager.appliableProjects(repoPalCompoundSimilarityCalculator, artifacts).size(),artifacts.size());
		assertEquals(similarityManager.appliableProjects(repoPalCompoundSimilarityCalculatorV2, artifacts).size(),artifacts.size());
		
	}
	@Test
	public void deleteRelation() {
		similarityManager.deleteRelations(simReadmeCalculator);
		assertEquals(similarityManager.getRelations(simReadmeCalculator).size(), 0);
		
	}
	@Test
	public void getRelation() {
		assertEquals(similarityManager.getRelations(simDependencyCalculator).size(),
				((artifacts.size() * (artifacts.size() -1))/2));
		
	}

}
