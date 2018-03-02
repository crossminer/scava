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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.scava.Application;
import org.eclipse.scava.business.IRecommenderManager;
import org.eclipse.scava.business.impl.OssmeterImporter;
import org.eclipse.scava.business.integration.ArtifactRepository;
import org.eclipse.scava.business.integration.GithubUserRepository;
import org.eclipse.scava.business.model.Artifact;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
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
public class RecommenderManagerTest {
	

	@Autowired
	private IRecommenderManager recommenderManager;
	
	@Autowired
	private ArtifactRepository artifactRepository;

	@Autowired
	OssmeterImporter ossmeterImporter;
	
	@Autowired
	private GithubUserRepository githubUserRepository;
	
	private static final Logger logger = Logger.getLogger(SimilarityManagerTest.class);
	private List<Artifact> artifacts;
	@Before
	public void testCreateAndStoreDistanceMatrix() {
		artifactRepository.deleteAll();
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
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}
	@After
	public void dispose(){
		artifactRepository.deleteAll();
		githubUserRepository.deleteAll();
	}
	
	
	@Test
	public void testGetClusters() throws Exception {
		assertEquals(recommenderManager.getArtifactsByQuery("minimalistic").size(),1);
	}

}
