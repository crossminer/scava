/*******************************************************************************
 * Copyright (C) 2017 University of L'Aquila
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.business;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class SchedulerManager {

	private static final Logger logger = LoggerFactory.getLogger(SchedulerManager.class);
//	@Autowired
//	private List<ISimilarityCalculator> simCalcs;
//
//	@Autowired
//	private SimilarityManager simManager;
//
//	@Autowired
//	private ClusterManager clusterManager;
//
//	@Autowired
//	@Qualifier("Ossmeter")
//	private IImporter importer;
//		
//	@Autowired
//	private CROSSRecSimilarityCalculator crossRecSimilarityCalculator;

	// TODO DE-COMMENT ROW BELOW TO ENABLE TASK EXECUTION
	// @Scheduled(cron = "0 53 12 * * *")
	public void scheduler() {
//		try {
//			logger.info("Importing projects from scava platform");
//			importer.importAll();
//			crossRecSimilarityCalculator.createCROSSRecGraph();
//			logger.info("Imported projects from scava platform");
//		} catch (Exception e) {
//		}
//
//		for (ISimilarityCalculator iSimilarityCalculator : simCalcs)
//			try {
//				logger.info("Generating: " + iSimilarityCalculator.getSimilarityName() + " distance matrix");
//				simManager.deleteRelations(iSimilarityCalculator);
//				simManager.createAndStoreDistanceMatrix(iSimilarityCalculator);
//				logger.info("Generated: " + iSimilarityCalculator.getSimilarityName() + " distance matrix");
//			} catch (Exception e) {
//			}
//		for (ISimilarityCalculator iSimilarityCalculator : simCalcs)
//			try {
//				logger.info("Generating: " + iSimilarityCalculator.getSimilarityName() + " clusterization");
//				clusterManager.calculateAndStoreClusterization(iSimilarityCalculator);
//				logger.info("Generated: " + iSimilarityCalculator.getSimilarityName() + " clusterization");
//			} catch (Exception e) {
//			}
	}
}
