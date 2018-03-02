/*******************************************************************************
 * Copyright (C) 2017 University of L'Aquila
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.test.similarity;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.eclipse.scava.Application;
import org.eclipse.scava.business.IAggregatedSimilarityCalculator;
import org.eclipse.scava.business.ISingleSimilarityCalculator;
import org.eclipse.scava.business.impl.OssmeterImporter;
import org.eclipse.scava.business.integration.ArtifactRepository;
import org.eclipse.scava.business.integration.GithubUserRepository;
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
import com.google.common.collect.Table;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {Application.class})
@TestPropertySource(locations="classpath:application.properties")
public class ReadmeSimilarityCalculatorTest {
	@Autowired
	private ArtifactRepository artifactRepository;
	@Autowired
	@Qualifier("Readme")
	private ISingleSimilarityCalculator readmeSim;
	private static final Logger logger = Logger.getLogger(ReadmeSimilarityCalculatorTest.class);
	private Artifact artifact1;
	private Artifact artifact2;
	
	@Autowired
	OssmeterImporter ossmeterImporter;
	
	@Autowired
	private GithubUserRepository githubUserRepository;
	@Before
	public void init(){
		artifactRepository.deleteAll();
		githubUserRepository.deleteAll();
		try {
			ObjectMapper mapper = new ObjectMapper();
			Resource resource = new ClassPathResource("artifacts.json");
			InputStream resourceInputStream = resource.getInputStream();
			List<Artifact> myObjects = mapper.readValue(resourceInputStream, new TypeReference<List<Artifact>>(){});
			artifactRepository.save(myObjects);
			for (Artifact artifact : myObjects) {
				ossmeterImporter.storeGithubUser(artifact.getStarred(), artifact.getFullName());
				ossmeterImporter.storeGithubUserCommitter(artifact.getCommitteers(), artifact.getFullName());
			}
			resourceInputStream.close();
			artifact1 = myObjects.get(0);
			artifact2 = myObjects.get(1);
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
	public void crossSimCommutativeTest() {
		double val1 = readmeSim.calculateSimilarity(artifact1, artifact2);
		double val2 = readmeSim.calculateSimilarity(artifact2, artifact1);
		logger.info(val1 + " " + val2);
		assertEquals(val1,val2, 0.0);
	}
	@Test
	public void crossSimIdentityTest() {
		List<Artifact> artifacts = artifactRepository.findFirst10ByOrderByIdDesc();
		double val1 = readmeSim.calculateSimilarity(artifact1, artifact1);
		assertEquals(val1,1, 0.000001);
	}
}
