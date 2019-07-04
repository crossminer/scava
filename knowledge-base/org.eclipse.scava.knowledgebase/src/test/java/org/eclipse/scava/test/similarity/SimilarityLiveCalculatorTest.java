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
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.eclipse.scava.business.ISimilarityManager;
import org.eclipse.scava.business.ISingleSimilarityCalculator;
import org.eclipse.scava.business.impl.OssmeterImporter;
import org.eclipse.scava.business.integration.ArtifactRepository;
import org.eclipse.scava.business.integration.GithubUserRepository;
import org.eclipse.scava.business.model.Artifact;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@TestPropertySource(locations="classpath:application.properties")
public class SimilarityLiveCalculatorTest {
	@Mock
	private ArtifactRepository artifactRepository;
	@InjectMocks
	private ISimilarityManager simManager;
	@Autowired
	@Qualifier("CrossRec")
	private ISingleSimilarityCalculator compoundSim;
	private static final Logger logger = LoggerFactory.getLogger(SimilarityLiveCalculatorTest.class);


	private Artifact artifact1;
	private List<Artifact> artifacts;
	@Before
	public void init(){
		try {
			ObjectMapper mapper = new ObjectMapper();
			Resource resource = new ClassPathResource("artifacts.json");
			InputStream resourceInputStream = resource.getInputStream();
			artifacts = mapper.readValue(resourceInputStream, new TypeReference<List<Artifact>>(){});
			resourceInputStream.close();
			artifact1 = artifacts.get(0);
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}
	@Test
	public void dispose(){
		when(artifactRepository.findAll()).thenReturn(
        		artifacts);
	}
	
}
