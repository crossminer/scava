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
import org.eclipse.scava.business.ISingleSimilarityCalculator;
import org.eclipse.scava.business.model.Artifact;
import org.eclipse.scava.business.model.Relation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
/**
 * @author Juri Di Rocco
 *
 */
@Service
@Qualifier("Compound")
public class CompoundSimilarityCalculator implements ISingleSimilarityCalculator {

	@Autowired
	@Qualifier("Readme")
	ISingleSimilarityCalculator simReadmeCalculator;
	@Autowired
	@Qualifier("Dependency")
	ISingleSimilarityCalculator simDependencyCalculator;
	@Autowired
	SimilarityManager similarityManager;
	private static final Logger logger = Logger.getLogger(CompoundSimilarityCalculator.class);
	
	
	@Override
	public double calculateSimilarity(Artifact prj1, Artifact prj2) {
		Relation r = similarityManager.getRelation(prj1, prj2, simReadmeCalculator);
		double simReadme;
		if(r == null) simReadme = simReadmeCalculator.calculateSimilarity(prj1, prj2);
		else simReadme = r.getValue();
		return simDependencyCalculator.calculateSimilarity(prj1, prj2) * 0.5
				+ simReadme * 0.5;
	}

	@Override
	public String getSimilarityName() {
		return "Compound";
	}

	@Override
	public boolean appliesTo(Artifact art) {
		if (art.getReadmeText()!=null && 
				!art.getReadmeText().equals("") &&
				!art.getDependencies().isEmpty())
			return true;
		else return false;
	}

}
