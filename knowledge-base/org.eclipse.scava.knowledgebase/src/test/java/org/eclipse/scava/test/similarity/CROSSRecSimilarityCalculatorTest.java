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
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.eclipse.scava.Application;
import org.eclipse.scava.business.dto.Dependency;
import org.eclipse.scava.business.impl.CROSSRecSimilarityCalculator;
import org.eclipse.scava.business.impl.OssmeterImporter;
import org.eclipse.scava.business.integration.ArtifactRepository;
import org.eclipse.scava.business.integration.CROSSRecGraphRepository;
import org.eclipse.scava.business.integration.GithubUserRepository;
import org.eclipse.scava.business.model.Artifact;
import org.eclipse.scava.business.model.CROSSRecGraph;
import org.eclipse.scava.config.SwaggerConfig;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Table;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@TestPropertySource(locations="classpath:application.properties")
public class CROSSRecSimilarityCalculatorTest {
	@Autowired
	@Qualifier("CrossRec")
	private CROSSRecSimilarityCalculator crossRec;

	private static final Logger logger = LoggerFactory.getLogger(CROSSRecSimilarityCalculatorTest.class);
	
	@Autowired
	private ArtifactRepository artifactRepository;
	
	@Autowired
	OssmeterImporter ossmeterImporter;
	
	private List<Artifact> artifacts;

	@Autowired
	private GithubUserRepository githubUserRepository;
	@Autowired
	private CROSSRecGraphRepository crossRecGraphRepository;
	
	@Before
	public void init(){
		artifactRepository.deleteAll();
		githubUserRepository.deleteAll();
		crossRecGraphRepository.deleteAll();
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
		crossRecGraphRepository.deleteAll();
	}
	@Test
	public void crossSimCommutativeTest() {
		Map<String, String> parameters = new HashMap<>();
		parameters.put("committers","false");
		parameters.put("deps","true");
		parameters.put("stargazers","true");
		parameters.put("freqDeps", "129");
		Table<String, String, Double> table = crossRec.calculateAggregatedSimilarityValues(artifacts, parameters);
		double val1 = table.get(artifacts.get(0).getFullName(), artifacts.get(1).getFullName());
		double val2 = table.get(artifacts.get(1).getFullName(), artifacts.get(0).getFullName());
		assertEquals(val1,val2, 0.0001);
	}
	
	@Test
	public void crossSimIdentityTest() {
		Map<String, String> parameters = new HashMap<>();
		parameters.put("committers","false");
		parameters.put("deps","true");
		parameters.put("stargazers","true");
		parameters.put("freqDeps", "129");
		Table<String, String, Double> table = crossRec.calculateAggregatedSimilarityValues(artifacts, parameters);
		double val1 = table.get(artifacts.get(0).getFullName(), artifacts.get(0).getFullName());
		assertEquals(val1, 1, 0.0);
	}
	
	@Test
	public void createGraphTest() {
		CROSSRecGraph g = crossRec.createCROSSRecGraph();
		assertNotNull(g);
	}
	@Test
	public void getGraphTest() throws Exception {
		List<Artifact> arts = artifactRepository.findAll();
		Artifact art = arts.get(0);
		CROSSRecGraph graph = crossRec.createGraphFromArtifact(art);
		assertNotNull(graph);
	}

	@Test
	public void combineGraphTest() throws Exception {
		List<Artifact> arts = artifactRepository.findAll();
		Artifact art1 = arts.get(0);
		Artifact art2 = arts.get(1);

		CROSSRecGraph graph1 = crossRec.createGraphFromArtifact(art1);
		CROSSRecGraph graph2 = crossRec.createGraphFromArtifact(art2);

		CROSSRecGraph graph = crossRec.combine(graph1, graph2);
		assertEquals(graph.getDictionarySwitch().size(), graph.getDictionary().size());
		assertEquals(graph.getDictionarySwitch().size(), graph.getNodeCount());
		assertNotNull(graph);
	}

	@Test
	public void combineGraphEqualsTest() throws Exception {
		List<Artifact> arts = artifactRepository.findAll();
		Artifact art1 = arts.get(0);
		CROSSRecGraph graph1 = crossRec.createGraphFromArtifact(art1);
		CROSSRecGraph graph2 = crossRec.createGraphFromArtifact(art1);

		CROSSRecGraph graph = crossRec.combine(graph1, graph2);
		assertEquals(graph.getDictionarySwitch().size(), graph.getDictionary().size());
		assertEquals(graph.getDictionarySwitch().size(), art1.getDependencies().size() + 1);
		assertNotNull(graph);
	}


	@Test
	public void computeWeightCosineSimilarityTest() throws Exception {
		List<Artifact> arts = artifactRepository.findAll();
		CROSSRecGraph graph = null;
		for (Artifact artifact : arts) {
			CROSSRecGraph graph1 = crossRec.createGraphFromArtifact(artifact);
			graph = crossRec.combine(graph, graph1);
		}
		crossRecGraphRepository.save(graph);
		Artifact query = arts.get(0);
		List<Dependency> queryDeps = new ArrayList<>();
		for (String dep : query.getDependencies()) {
			Dependency d = new Dependency();
			d.setName(dep);
			queryDeps.add(d);
		}
		Map<String, Double> res = crossRec.computeWeightCosineSimilarity(queryDeps);
		assertNotNull(res);
		assertEquals(res.size(),arts.size());
	}
}
