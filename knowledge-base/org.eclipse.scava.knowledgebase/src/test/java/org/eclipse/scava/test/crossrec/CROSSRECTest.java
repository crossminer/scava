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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.eclipse.scava.Application;
import org.eclipse.scava.business.dto.Dependency;
import org.eclipse.scava.business.dto.Recommendation;
import org.eclipse.scava.business.impl.CROSSRecServiceImpl;
import org.eclipse.scava.business.integration.ArtifactRepository;
import org.eclipse.scava.business.integration.CROSSRecGraphRepository;
import org.eclipse.scava.business.model.Artifact;
import org.eclipse.scava.business.model.CROSSRecGraph;
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
@ContextConfiguration(classes = { Application.class })
@TestPropertySource(locations = "classpath:application.properties")
public class CROSSRECTest {

	@Autowired
	private CROSSRecServiceImpl crossRecService;

	@Autowired
	private ArtifactRepository artifactRepository;

	@Autowired
	private CROSSRecGraphRepository crossRecGraphRepository;

	private static final Logger logger = Logger.getLogger(CROSSRECTest.class);
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
	public void createGraphTest() {
		CROSSRecGraph g = crossRecService.createCROSSRecGraph();
		assertNotNull(g);
	}
	@Test
	public void getGraphTest() throws Exception {
		List<Artifact> arts = artifactRepository.findAll();
		Artifact art = arts.get(0);
		CROSSRecGraph graph = crossRecService.createGraphFromArtifact(art);
		assertNotNull(graph);
	}

	@Test
	public void combineGraphTest() throws Exception {
		List<Artifact> arts = artifactRepository.findAll();
		Artifact art1 = arts.get(0);
		Artifact art2 = arts.get(1);

		CROSSRecGraph graph1 = crossRecService.createGraphFromArtifact(art1);
		CROSSRecGraph graph2 = crossRecService.createGraphFromArtifact(art2);

		CROSSRecGraph graph = crossRecService.combine(graph1, graph2);
		assertEquals(graph.getDictionarySwitch().size(), graph.getDictionary().size());
		assertEquals(graph.getDictionarySwitch().size(), graph.getNodeCount());
		assertNotNull(graph);
	}

	@Test
	public void combineGraphEqualsTest() throws Exception {
		List<Artifact> arts = artifactRepository.findAll();
		Artifact art1 = arts.get(0);
		CROSSRecGraph graph1 = crossRecService.createGraphFromArtifact(art1);
		CROSSRecGraph graph2 = crossRecService.createGraphFromArtifact(art1);

		CROSSRecGraph graph = crossRecService.combine(graph1, graph2);
		assertEquals(graph.getDictionarySwitch().size(), graph.getDictionary().size());
		assertEquals(graph.getDictionarySwitch().size(), art1.getDependencies().size() + 1);
		assertNotNull(graph);
	}

	@Test
	public void queryCustomTest() throws Exception {

		List<Artifact> arts = artifactRepository.findAll();
		assertNotNull(arts);
		assertNotEquals(arts.size(), 0);
	}

	@Test
	public void computeWeightCosineSimilarityTest() throws Exception {
		List<Artifact> arts = artifactRepository.findAll();
		CROSSRecGraph graph = null;
		for (Artifact artifact : arts) {
			CROSSRecGraph graph1 = crossRecService.createGraphFromArtifact(artifact);
			graph = crossRecService.combine(graph, graph1);
		}
		crossRecGraphRepository.save(graph);
		Artifact query = arts.get(0);
		List<Dependency> queryDeps = new ArrayList<>();
		for (String dep : query.getDependencies()) {
			Dependency d = new Dependency();
			d.setName(dep);
			queryDeps.add(d);
		}
		Map<String, Double> res = crossRecService.computeWeightCosineSimilarity(queryDeps);
		assertNotNull(res);
		assertEquals(res.size(),arts.size());
	}
	
	@Test
	public void userBasedRecommendationTest() throws Exception {
		List<Artifact> arts = artifactRepository.findAll();
		CROSSRecGraph graph = null;
		for (Artifact artifact : arts) {
			CROSSRecGraph graph1 = crossRecService.createGraphFromArtifact(artifact);
			graph = crossRecService.combine(graph, graph1);
		}
		crossRecGraphRepository.save(graph);
		Artifact query = arts.get(0);
		List<Dependency> queryDeps = new ArrayList<>();
		for (String dep : query.getDependencies()) {
			Dependency d = new Dependency();
			d.setName(dep);
			queryDeps.add(d);
		}
		Map<String, Double> simValues = crossRecService.computeWeightCosineSimilarity(queryDeps);
		Map<String, Double> res = crossRecService.userBasedRecommendation(queryDeps, simValues);
		assertNotNull(res);
	}
	@Test
	public void runTest() throws Exception {
		List<Artifact> arts = artifactRepository.findAll();
		CROSSRecGraph graph = null;
		for (Artifact artifact : arts) {
			CROSSRecGraph graph1 = crossRecService.createGraphFromArtifact(artifact);
			graph = crossRecService.combine(graph, graph1);
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
