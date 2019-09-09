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

import java.io.IOException;

import org.eclipse.scava.business.impl.UnparallelImporter;
import org.eclipse.scava.business.integration.ArtifactRepository;
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
@TestPropertySource(locations = "classpath:application.properties")
public class UnparallelImporterTest {
	@Autowired
	private UnparallelImporter importer;
	@Autowired
	private ArtifactRepository artifactRepository;

	private static final Logger logger = LoggerFactory.getLogger(UnparallelImporterTest.class);

	@Test
	public void importProjectTest() throws IOException  {

		try {
			importer.importProjects("components-with-tags.csv");
		} catch (IOException e) {
			logger.error(e.getMessage());
			throw e;
		}
	}

}
