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

import org.eclipse.scava.business.IClusterCalculator;
import org.eclipse.scava.business.ISimilarityCalculator;
import org.eclipse.scava.business.model.Cluster;

/**
 * @author Juri Di Rocco
 *
 */
public class CustomClusterCalculator implements IClusterCalculator{

	
	
	@Override
	public List<Cluster> calculateCluster(ISimilarityCalculator sm) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public com.apporiented.algorithm.clustering.Cluster getHierarchicalCluster(
			ISimilarityCalculator valuedRelationService) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Cluster> calculateCluster(ISimilarityCalculator sm,
			com.apporiented.algorithm.clustering.Cluster cluster) {
		// TODO Auto-generated method stub
		return null;
	}

}
