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

import java.time.LocalDateTime;

import org.eclipse.scava.business.impl.SimilarityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SchedulerManager {

	private static final Logger logger = LoggerFactory.getLogger(SchedulerManager.class);

	@Autowired
	private SimilarityManager simManager;

	@Scheduled(cron = "0 30 20 ? * SUN")
//	@Scheduled(cron = "0 0/5 * * * ?")
	public void scheduler() {
		try {
			logger.info("Offline computating at: {}", LocalDateTime.now());
			simManager.storeAllSimilarityDistances();
			logger.info("Offline computated at: {}", LocalDateTime.now());
		} catch (Exception e) {
			logger.error("error in computing distances: {}", e.getMessage());
		}
	}
}
