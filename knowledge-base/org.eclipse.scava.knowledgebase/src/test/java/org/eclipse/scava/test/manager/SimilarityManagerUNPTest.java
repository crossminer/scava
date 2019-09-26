/*******************************************************************************
 * Copyright (C) 2017 University of L'Aquila
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.test.manager;

import java.util.Date;

import org.eclipse.scava.business.IAggregatedSimilarityCalculator;
import org.eclipse.scava.business.ISimilarityManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@TestPropertySource(locations="classpath:application.properties")

public class SimilarityManagerUNPTest {

	
	@Autowired
	@Qualifier("CrossRec")
	private IAggregatedSimilarityCalculator aggregateSimilarityCalculator;
	
	@Autowired
	private ISimilarityManager similarityManager;
	
	private static final Logger logger = LoggerFactory.getLogger(SimilarityManagerUNPTest.class);
	
	
	@Test
	public void testCROSSRecDistanceMatrix() {
		logger.info("Started at: {}", new Date().toString());
		similarityManager.createAndStoreDistanceMatrix(aggregateSimilarityCalculator);
		logger.info("Stopped at: {}", new Date().toString());
	}


}
