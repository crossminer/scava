/*******************************************************************************
 * Copyright (C) 2017 University of L'Aquila
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.test.crossrec;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.scava.business.dto.Dependency;
import org.eclipse.scava.business.dto.Recommendation;
import org.eclipse.scava.business.impl.CROSSRecServiceImpl;
import org.eclipse.scava.business.impl.CROSSRecSimilarityCalculator;
import org.eclipse.scava.business.integration.ArtifactRepository;
import org.eclipse.scava.business.integration.CROSSRecGraphRepository;
import org.eclipse.scava.business.model.Artifact;
import org.eclipse.scava.business.model.CROSSRecGraph;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:application.properties")
public class CROSSRECTest {

	@Autowired
	private CROSSRecServiceImpl crossRecService;

	@Autowired
	private ArtifactRepository artifactRepository;

	@Autowired
	private CROSSRecGraphRepository crossRecGraphRepository;
	
	@Autowired
	private CROSSRecSimilarityCalculator crossRecSimilarity;

	private static final Logger logger = LoggerFactory.getLogger(CROSSRECTest.class);
	private List<Artifact> artifacts;

	@Before
	public void testCreateAndStoreDistanceMatrix() throws IOException {
		crossRecGraphRepository.deleteAll();
		ObjectMapper mapper = new ObjectMapper();
		Resource resource = new ClassPathResource("artifacts.json");
		InputStream resourceInputStream = resource.getInputStream();
		artifacts = mapper.readValue(resourceInputStream, new TypeReference<List<Artifact>>(){});
		artifactRepository.save(artifacts);
	}
	

	@After
	public void dispose() {
		crossRecGraphRepository.deleteAll();
		artifactRepository.deleteAll();
	}
	
	@Test
	public void userBasedRecommendationTest() throws Exception {
		List<Artifact> arts = artifactRepository.findAll();
		CROSSRecGraph graph = null;
		for (Artifact artifact : arts) {
			CROSSRecGraph graph1 = crossRecSimilarity.createGraphFromArtifact(artifact);
			graph = crossRecSimilarity.combine(graph, graph1);
		}
		crossRecGraphRepository.save(graph);
		Artifact query = arts.get(0);
		List<Dependency> queryDeps = new ArrayList<>();
		for (String dep : query.getDependencies()) {
			Dependency d = new Dependency();
			d.setName(dep);
			queryDeps.add(d);
		}
		Map<String, Double> simValues = crossRecSimilarity.computeWeightCosineSimilarity(queryDeps);
		Map<String, Double> res = crossRecService.userBasedRecommendation(queryDeps, simValues);
		assertNotNull(res);
	}
	@Test
	public void runTest() throws Exception {
		List<Artifact> arts = artifactRepository.findAll();
		CROSSRecGraph graph = null;
		for (Artifact artifact : arts) {
			CROSSRecGraph graph1 = crossRecSimilarity.createGraphFromArtifact(artifact);
			graph = crossRecSimilarity.combine(graph, graph1);
		}
		crossRecGraphRepository.save(graph);
		Artifact query = arts.get(0);
		List<Dependency> queryDeps = new ArrayList<>();
		for (String dep : query.getDependencies()) {
			Dependency d = new Dependency();
			d.setName(dep);
			queryDeps.add(d);
		}
		Recommendation res = crossRecService.run(queryDeps);
		assertNotNull(res);
	}
}
