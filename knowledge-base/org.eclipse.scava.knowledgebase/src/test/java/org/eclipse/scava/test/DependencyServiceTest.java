/*******************************************************************************
 * Copyright (C) 2017 University of L'Aquila
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.test;

import static org.junit.Assert.assertNotEquals;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.eclipse.scava.Application;
import org.eclipse.scava.business.IDependencyService;
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

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {Application.class})
@TestPropertySource(locations="classpath:application.properties")
public class DependencyServiceTest {

	
	@Autowired
	private IDependencyService depService;
	
	
	private List<Artifact> artifacts;

	@Autowired
	private GithubUserRepository githubUserRepository;
	
	@Autowired
	private ArtifactRepository artifactRepository;
	
	@Before
	public void init() throws JsonParseException, JsonMappingException, IOException{
		artifactRepository.deleteAll();
		githubUserRepository.deleteAll();
		
		ObjectMapper mapper = new ObjectMapper();
		Resource resource = new ClassPathResource("artifacts.json");
		InputStream resourceInputStream = resource.getInputStream();
		artifacts = mapper.readValue(resourceInputStream, new TypeReference<List<Artifact>>(){});
		artifactRepository.save(artifacts);
		resourceInputStream.close();
	}
	@After
	public void dispose(){
		artifactRepository.deleteAll();
		githubUserRepository.deleteAll();
	}
	
	@Test
	public void getResults(){
		assertNotEquals(depService.getNumberOfProjectThatUseDependencies("junit:junit"), 0);
	}

	
}
