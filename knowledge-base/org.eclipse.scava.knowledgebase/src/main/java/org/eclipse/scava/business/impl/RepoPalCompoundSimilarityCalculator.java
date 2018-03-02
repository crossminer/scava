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
@Qualifier("RepoPalCompound")
public class RepoPalCompoundSimilarityCalculator implements ISingleSimilarityCalculator {

	@Autowired
	@Qualifier("Readme")
	private ISingleSimilarityCalculator simReadmeCalculator;
	@Autowired
	@Qualifier("RepoPalStar")
	private ISingleSimilarityCalculator repoPalStarSimilarityCalculator;
	@Autowired
	@Qualifier("RepoPalTime")
	private ISingleSimilarityCalculator repoPalTimeSimilarityCalculator;
	@Autowired
	private SimilarityManager similarityManager;
	
	private static final Logger logger = Logger.getLogger(RepoPalCompoundSimilarityCalculator.class);
	
	
	@Override
	public double calculateSimilarity(Artifact prj1, Artifact prj2) {
		Relation readmeRelation = similarityManager.getRelation(prj1, prj2, simReadmeCalculator);
		double readme;
		if(readmeRelation != null) readme = readmeRelation.getValue();
		else readme = simReadmeCalculator.calculateSimilarity(prj1, prj2);
		
		Relation starRelation = similarityManager.getRelation(prj1, prj2, repoPalStarSimilarityCalculator);
		double star;
		if(starRelation != null) star = starRelation.getValue();
		else star = repoPalStarSimilarityCalculator.calculateSimilarity(prj1, prj2);
		
		double time = repoPalTimeSimilarityCalculator.calculateSimilarity(prj1, prj2);
		
		return readme * star * time;
	}

	@Override
	public String getSimilarityName() {
		return "RepoPalCompound";
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
