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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.eclipse.scava.business.IRecommendationProvider;
import org.eclipse.scava.business.ISimilarityCalculator;
import org.eclipse.scava.business.ISimilarityManager;
import org.eclipse.scava.business.dto.Dependency;
import org.eclipse.scava.business.dto.Query;
import org.eclipse.scava.business.dto.Recommendation;
import org.eclipse.scava.business.dto.RecommendationItem;
import org.eclipse.scava.business.integration.ArtifactRepository;
import org.eclipse.scava.business.integration.RelationRepository;
import org.eclipse.scava.business.model.Artifact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * @author Juri Di Rocco
 *
 */
@Service
@Qualifier("APIDocumentation")
public class APIDocumentationRecommendationProvider implements IRecommendationProvider {

	private static final Logger logger = LoggerFactory.getLogger(APIDocumentationRecommendationProvider.class);

	@Autowired
	RelationRepository relationRepository;

	@Autowired
	List<ISimilarityCalculator> similarities;

	@Override
	public Recommendation getRecommendation(Query query) throws Exception {
		//TODO
		Recommendation rec = new Recommendation();
		rec.setRecommendationItems(new ArrayList<RecommendationItem>());
		RecommendationItem ri = new RecommendationItem();
		ri.setApiDocumentationLink("https://stackoverflow.com/questions/10128202/jackson2-custom-deserializer-factory");
		rec.getRecommendationItems().add(ri);
		return rec;
	}

	
	
	

}
