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

import static org.junit.Assert.assertNotNull;

import org.eclipse.scava.business.integration.ArtifactRepository;
import org.eclipse.scava.business.model.Artifact;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:application.properties")
public class ArtifactRepositoryTest {



	@Autowired
	private ArtifactRepository artifactRepository;

	@After
	public void after() {
		artifactRepository.deleteAll();
	}
	@Before
	public void before() {
		Artifact art = new Artifact();
		art.setName("name");
		art.setFullName("fullName");
		art.setMetricPlatformId("mppID");
		artifactRepository.save(art);
	}
	
	@Test
	public void importFOCUSArtifact() {
		assertNotNull(artifactRepository.findOneByMetricPlatformId("mppID"));
	}

}
