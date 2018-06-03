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

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.eclipse.scava.Application;
import org.eclipse.scava.business.impl.OssmeterImporter;
import org.eclipse.scava.business.integration.ArtifactRepository;
import org.eclipse.scava.business.model.Artifact;
import org.eclipse.scava.config.SwaggerConfig;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { Application.class, SwaggerConfig.class })
@WebAppConfiguration
@TestPropertySource(locations="classpath:application.properties")
public class OssmeterImporterTest {
	@Autowired
	@Qualifier("Ossmeter")
	private OssmeterImporter importer;
	@Autowired
	private ArtifactRepository artifactRepository;
	
	private static final Logger logger = LoggerFactory.getLogger(OssmeterImporterTest.class);
	@Ignore
	@Test
	public void importProjectTest() {
		try {
			Artifact art = importer.importProject("MDEProfile");
			assertThat(art.getDependencies(), is(not(0)));
			assertThat(art.getStarred(), is(not(0)));
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}
	@Ignore
	@Test
	public void importProjectsTest() {
		importer.importAll();
		assertThat(artifactRepository.count(), is(not(0)));
	}
	
	

}
