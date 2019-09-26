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

import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.eclipse.scava.business.impl.CROSSRecSimilarityCalculator;
import org.eclipse.scava.business.impl.SimilarityManager;
import org.eclipse.scava.business.impl.UnparallelImporter;
import org.eclipse.scava.business.integration.ArtifactRepository;
import org.eclipse.scava.business.integration.RelationRepository;
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

import com.google.common.collect.Table;
import com.google.common.collect.Table.Cell;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:application.properties")
public class UnparallelCROSSRecSimTest {
	@Autowired
	private UnparallelImporter importer;
	@Autowired
	private SimilarityManager simManager;
	@Autowired
	private CROSSRecSimilarityCalculator crossRecSimCalc;
	@Autowired
	private RelationRepository relRepo;
	@Autowired
	private ArtifactRepository artifactRepository;
	private static final Logger logger = LoggerFactory.getLogger(UnparallelCROSSRecSimTest.class);

	@Before
	public void importProjectTest() throws IOException {
		try {
			relRepo.deleteAll();
			artifactRepository.deleteAll();
			importer.importProjects("components-with-tags.csv");
		} catch (IOException e) {
			logger.error(e.getMessage());
			throw e;
		}
	}

	@Test
	@Ignore
	public void createDistanceMatrixUNP() {
		Table<String, String, Double> v = simManager.createDistanceMatrix(crossRecSimCalc);
		boolean b = false;
		for (Cell<String, String, Double> d : v.cellSet()) {
			if (d.getValue() > 0)
				b = true;
			System.out.println(d.getValue());
		}
		assertTrue(b);
	}
	@Test
	public void createAndSoreDistanceMatrixUNP() {
		simManager.createAndStoreDistanceMatrix(crossRecSimCalc);
	}

}
