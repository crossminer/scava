/*******************************************************************************
 * Copyright (C) 2017 University of L'Aquila
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.test.importer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.eclipse.scava.business.impl.GithubImporter;
import org.eclipse.scava.business.integration.ArtifactRepository;
import org.eclipse.scava.business.model.Artifact;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@TestPropertySource(locations="classpath:application.properties")
public class GithubImporterTest {
	@Autowired
	private GithubImporter importer;
	
	@Autowired
	private ArtifactRepository artifactRepository;
	
	private static final Logger logger = LoggerFactory.getLogger(GithubImporterTest.class);

	@Before
	public void init(){
		artifactRepository.deleteAll();
	}
	@After
	public void dispose(){
		artifactRepository.deleteAll();
	}
	@Test
	public void importProjectTest() throws IOException {
		importer.importProject("MDEGroup/MDEProfile");
		assertEquals(artifactRepository.count(), 1);
	}
	@Test
	public void importer() throws IOException, XmlPullParserException{
		Artifact art = importer.importProject("fasterxml/jackson-databind");
		assertNotNull(art);
		assertNotEquals(0, art.getDependencies());
	}
}
