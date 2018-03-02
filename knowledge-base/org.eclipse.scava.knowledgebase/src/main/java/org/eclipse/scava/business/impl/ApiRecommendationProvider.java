/*******************************************************************************
 * Copyright (C) 2017 University of L'Aquila
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.business.impl;

import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.eclipse.scava.business.IRecommendationProvider;
import org.eclipse.scava.business.ISimilarityCalculator;
import org.eclipse.scava.business.dto.Query;
import org.eclipse.scava.business.dto.Recommendation;
import org.eclipse.scava.business.integration.RelationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * @author Juri Di Rocco
 *
 */
@Service
@Qualifier("ApiRecommendation")
public class ApiRecommendationProvider implements IRecommendationProvider {

	private static final Logger logger = Logger.getLogger(ApiRecommendationProvider.class);
	@Autowired
	RelationRepository relationRepository;

	@Autowired
	List<ISimilarityCalculator> similarities;

	
	@Autowired
	private CROSSRecServiceImpl crossRecService;
	
	@Override
	public Recommendation getRecommendation(Query query) throws Exception {
		Recommendation rec = new Recommendation();
		Recommendation recommendedLibrary = crossRecService.run(query.getProjectDependencies());
		rec.getRecommendationItems().addAll(recommendedLibrary.getRecommendationItems());
		
		return rec;
	}
}
