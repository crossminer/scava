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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.eclipse.scava.Application;
import org.eclipse.scava.business.IAggregatedSimilarityCalculator;
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
import com.google.common.collect.Table;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {Application.class})
@TestPropertySource(locations="classpath:application.properties")
public class CROSSSimSimilarityCalculatorTest {
	@Autowired
	private IAggregatedSimilarityCalculator crossSim;

	private static final Logger logger = Logger.getLogger(CROSSSimSimilarityCalculatorTest.class);
	
	@Autowired
	private ArtifactRepository artifactRepository;
	
	@Autowired
	OssmeterImporter ossmeterImporter;
	
	private List<Artifact> artifacts;

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
			artifacts = mapper.readValue(resourceInputStream, new TypeReference<List<Artifact>>(){});
			artifactRepository.save(artifacts);
			for (Artifact artifact : artifacts) {
				ossmeterImporter.storeGithubUser(artifact.getStarred(), artifact.getFullName());
				ossmeterImporter.storeGithubUserCommitter(artifact.getCommitteers(), artifact.getFullName());
			}
			resourceInputStream.close();
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
		Map<String, String> parameters = new HashMap<>();
		parameters.put("committers","false");
		parameters.put("deps","true");
		parameters.put("stargazers","true");
		parameters.put("freqDeps", "129");
		Table<String, String, Double> table = crossSim.calculateAggregatedSimilarityValues(artifacts, parameters);
		double val1 = table.get(artifacts.get(0).getFullName(), artifacts.get(1).getFullName());
		double val2 = table.get(artifacts.get(1).getFullName(), artifacts.get(0).getFullName());
		assertEquals(val1,val2, 0.0);
	}
	
	@Test
	public void crossSimIdentityTest() {
		Map<String, String> parameters = new HashMap<>();
		parameters.put("committers","false");
		parameters.put("deps","true");
		parameters.put("stargazers","true");
		parameters.put("freqDeps", "129");
		Table<String, String, Double> table = crossSim.calculateAggregatedSimilarityValues(artifacts, parameters);
		double val1 = table.get(artifacts.get(0).getFullName(), artifacts.get(0).getFullName());
		assertEquals(val1, 1, 0.0);
	}
}
