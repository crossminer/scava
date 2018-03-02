/*******************************************************************************
 * Copyright (C) 2017 University of L'Aquila
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.test.recommendation.providers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.scava.Application;
import org.eclipse.scava.business.IRecommendationProvider;
import org.eclipse.scava.business.ISimilarityCalculator;
import org.eclipse.scava.business.ISimilarityManager;
import org.eclipse.scava.business.dto.Dependency;
import org.eclipse.scava.business.dto.Query;
import org.eclipse.scava.business.dto.Recommendation;
import org.eclipse.scava.business.integration.ArtifactRepository;
import org.eclipse.scava.business.integration.GithubUserRepository;
import org.eclipse.scava.business.integration.RelationRepository;
import org.eclipse.scava.business.model.Artifact;
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

public class AlternativeLibrariesRecommendationProviderTest {

	@Autowired
	private ArtifactRepository artifactRepository;
	
	@Autowired
	private GithubUserRepository githubUserRepository;
	
	@Autowired
	private RelationRepository relationRepository;
		
	@Autowired
	@Qualifier("AlternativeLibraries")
	private IRecommendationProvider alternativeRecomendationProvider;
	
	@Autowired
	private ISimilarityManager similarityManager;
	
	@Autowired
	@Qualifier ("Dependency")
	private ISimilarityCalculator simDependencyCalculator;
	
	private static final Logger logger = Logger.getLogger(AlternativeLibrariesRecommendationProviderTest.class);
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
			resourceInputStream.close();
			similarityManager.createAndStoreDistanceMatrix(simDependencyCalculator);
			assertEquals(((artifacts.size() * (artifacts.size() -1))/2), 
					relationRepository.findAllByTypeName(simDependencyCalculator.getSimilarityName()).size());
			
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
	public void getRecommendationTest() throws Exception {
		Query q = new Query();
		q.setCompilationUnit("...");
		q.setComments(new ArrayList<String>());
		q.getComments().add("TODO");
		q.getComments().add("connect to Api with gsoup");
		Dependency d1 = new Dependency();
		d1.setName("org.apache.httpcomponents:httpclient");
		d1.setVersion("1.10.4.RELEASE");
		d1.setUrl("");
		d1.setArtifactID("5a228b0a2e429420384464da");
		Dependency d2 = new Dependency();
		d2.setName("com.google.code.gson:gson");
		d2.setVersion("6.0.0");
		d2.setUrl("");
		d2.setArtifactID("5a228b0f2e42942038446560");
		Dependency d3 = new Dependency();
		d3.setName("junit:junit");
		d3.setVersion("1.2.17");
		d3.setUrl("");
		d3.setArtifactID("5a228b162e4294203844660b");
		q.setProjectDependencies(new ArrayList<Dependency>());
		q.getProjectDependencies().add(d1);
		q.getProjectDependencies().add(d2);
		q.getProjectDependencies().add(d3);
		q.setClassDependencies(new ArrayList<Dependency>());
		q.getClassDependencies().add(d2);
		q.setMethodInvocation("addDocument");
		q.setRefClassInvocation("IndexWriter");
		q.setProjectName("MDEForge");
		q.setTextOffset(1000);
		q.setSimilarityMethod("Dependency");
		
		Recommendation r = alternativeRecomendationProvider.getRecommendation(q);
		assertNotNull(r);
	}


}
