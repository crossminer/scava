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

import org.eclipse.scava.business.model.Cluster;

/**
 * @author Juri Di Rocco
 *
 */
public interface IClusterCalculator {
	List<Cluster> calculateCluster(ISimilarityCalculator sm);
	com.apporiented.algorithm.clustering.Cluster getHierarchicalCluster(ISimilarityCalculator valuedRelationService);
	List<Cluster> calculateCluster(ISimilarityCalculator sm, com.apporiented.algorithm.clustering.Cluster cluster);
}
