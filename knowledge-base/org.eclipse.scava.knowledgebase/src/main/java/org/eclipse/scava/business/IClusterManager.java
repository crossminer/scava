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
import java.util.Map;

import org.eclipse.scava.business.model.Artifact;
import org.eclipse.scava.business.model.Cluster;
import org.eclipse.scava.business.model.Clusterization;

/**
 * @author Juri Di Rocco
 *
 */
public interface IClusterManager {
	void calculateAndStoreClusterization(ISimilarityCalculator simCalc);
	List<Cluster> getClusters(ISimilarityCalculator simCalc) throws Exception;
	void deleteClusterization(Clusterization clusterization);
	Cluster getClusterFromArtifact(Artifact art, ISimilarityCalculator simCalc);
	Cluster getOneByArtifactsName(String string, ISimilarityCalculator simCalc);
	Clusterization getClusterizationBySimilarityMethodLastDate(ISimilarityCalculator simDependencyCalculator);
	Cluster getClusterByArtifactsIdAndClusterizationId(String artifactId, String clusterizationId);

}
