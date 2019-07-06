/*******************************************************************************
 * Copyright (C) 2017 University of L'Aquila
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.test.apimigration;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Arrays;
import java.util.List;

import org.eclipse.aether.artifact.Artifact;
import org.eclipse.scava.business.impl.APIMigration.MigrationService;
import org.eclipse.scava.business.integration.MavenLinkAllRepository;
import org.eclipse.scava.business.model.MavenLinkAll;
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

import nl.cwi.swat.aethereal.MigrationInfo;
//
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:application.properties")
public class APIMigrationTest {
	

	private static final Logger logger = LoggerFactory.getLogger(APIMigrationTest.class);
	@Autowired
	private MigrationService migrationService;
	
	
	@Autowired
	private MavenLinkAllRepository mavenLinkAllrepo;
	
	
	@Before
	public void init() {
		MavenLinkAll mla1 = new MavenLinkAll("lib1:lib1:1","client1:client2:1");
		MavenLinkAll mla2 = new MavenLinkAll("lib1:lib1:1","client1:client3:1");
		MavenLinkAll mla3 = new MavenLinkAll("lib1:lib1:1","client1:client4:1");
		MavenLinkAll mla4 = new MavenLinkAll("lib1:lib1:1","client1:client5:1");
		MavenLinkAll mla5 = new MavenLinkAll("lib1:lib1:1","client1:client1:1");
		MavenLinkAll mla1b = new MavenLinkAll("lib1:lib1:2","client1:client2:2");
		MavenLinkAll mla2b = new MavenLinkAll("lib1:lib1:2","client1:client3:2");
		MavenLinkAll mla3b = new MavenLinkAll("lib1:lib1:2","client1:client4:2");
		MavenLinkAll mla4b = new MavenLinkAll("lib1:lib1:2","client1:client5:2");
		MavenLinkAll mla5b = new MavenLinkAll("lib1:lib1:2","client1:client1:2");
		mavenLinkAllrepo.save(Arrays.asList(mla1, mla2, mla3, mla4, mla5, mla1b, mla2b, mla3b, mla4b, mla5b));
	}
	@After
	public void dispose() {
		mavenLinkAllrepo.deleteAll();
	}
	
	
	@Test
	@Ignore
	public void importerTest() {
		migrationService.importer("/Users/juri/development/git/aethereal/aethereal/dependency-graph/links_all.csv");
	}
	
	@Test
	public void getPairsTest() {
		MigrationInfo test = migrationService.getMigrationPairs("lib1:lib1:1", "lib1:lib1:2");
		assertNotNull(test.libv1);
		assertNotNull(test.libv2);
		assertNotEquals(2, test.clientsV1.size());
		logger.info("It discovers {} clients that have been migrate to {}", test.clientsV1.size(), test.libv1.getVersion());
		assertNotEquals(2, test.clientsV2.size());
	}
	@Test
	public void getClients() {
		List<Artifact> test = migrationService.getClients("client1:client1:1");
		assertNotEquals(0, test.size());
	}
	

	


}
