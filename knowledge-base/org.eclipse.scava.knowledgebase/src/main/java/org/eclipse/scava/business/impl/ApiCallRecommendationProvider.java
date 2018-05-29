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

import org.apache.log4j.Logger;
import org.eclipse.scava.business.IRecommendationProvider;
import org.eclipse.scava.business.dto.Query;
import org.eclipse.scava.business.dto.Recommendation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * @author Juri Di Rocco
 *
 */
@Service
@Qualifier("ApiCallRecommendation")
public class ApiCallRecommendationProvider implements IRecommendationProvider {

	private static final Logger logger = Logger.getLogger(ApiCallRecommendationProvider.class);


	
	@Autowired
	private CROSSRecServiceImpl crossRecService;
	
	@Override
	public Recommendation getRecommendation(Query query) throws Exception {
		Recommendation rec = new Recommendation();
		
		
		
		return rec;
	}
}
