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

import java.util.List;
import java.util.Set;

import org.eclipse.scava.business.model.Artifact;
import org.eclipse.scava.business.model.Relation;

import com.google.common.collect.Table;

/**
 * @author Juri Di Rocco
 *
 */
public interface ISimilarityManager {

	List<Artifact> getSimilarProjects(Artifact p1, int numResults);
	List<Artifact> getSimilarProjects(Artifact p1, double threshold);
	List<Artifact> getSimilarProjects(Artifact p1, ISimilarityCalculator similarityCalculator, int numResults);
//	void calculateSimilarity(Artifact prj1, Artifact prj2, ISimilarityCalculator similarityCalculator);
	/*
	 * @param  simCalculator  If it is null, method uses SimRank Algorithm
	 * (non-Javadoc)
	 * @see org.scava.business.ISimilarityManager#createAndStoreDistanceMatrix(org.scava.business.ISimilarityCalculator)
	 */
	void createAndStoreDistanceMatrix(ISimilarityCalculator simCalculator);
	Table<String, String, Double> getDistanceMatrix(ISimilarityCalculator simCalculator);
	Set<Relation> getSimilarProjectsRelations(Artifact p1, ISimilarityCalculator similarityCalculator);
	Relation getRelation(Artifact prj1, Artifact prj2, ISimilarityCalculator simCalculator);
	List<Artifact> appliableProjects(ISimilarityCalculator simCalculator, List<Artifact> corpus);
	void storeDistanceMatrix(Table<String, String, Double> distanceMatrix, ISimilarityCalculator similarityCalculator);
	void deleteRelations(ISimilarityCalculator simCalculator);
	List<Relation> getRelations(ISimilarityCalculator simCalculator);
}
