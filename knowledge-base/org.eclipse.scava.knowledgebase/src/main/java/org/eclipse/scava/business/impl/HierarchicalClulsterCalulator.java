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
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.eclipse.scava.business.IClusterCalculator;
import org.eclipse.scava.business.ISimilarityCalculator;
import org.eclipse.scava.business.integration.ArtifactRepository;
import org.eclipse.scava.business.model.Artifact;
import org.eclipse.scava.business.model.Cluster;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.apporiented.algorithm.clustering.ClusteringAlgorithm;
import com.apporiented.algorithm.clustering.DefaultClusteringAlgorithm;
import com.apporiented.algorithm.clustering.SingleLinkageStrategy;
import com.google.common.collect.Table;

/**
 * @author Juri Di Rocco
 *
 */
@Service
@Qualifier("HCLibrary")
public class HierarchicalClulsterCalulator implements IClusterCalculator {

	private static final double THRESHOLD = 0.15;
	private static final Logger logger = Logger.getLogger(HierarchicalClulsterCalulator.class);
	@Autowired
	private ArtifactRepository arifactRepository;
	@Autowired
	private SimilarityManager similarityManager;
	@Override
	public List<Cluster> calculateCluster(ISimilarityCalculator sm) {

		List<Cluster> result = new ArrayList<>();
		com.apporiented.algorithm.clustering.Cluster c = getHierarchicalCluster(sm);
		List<com.apporiented.algorithm.clustering.Cluster> k = getClustersWithThreshold(c, THRESHOLD);
		for (com.apporiented.algorithm.clustering.Cluster cluster : k) {
			Cluster clu = new Cluster();
			clu.setArtifacts(new ArrayList<Artifact>());
			getLeaf(cluster).forEach(z ->
					clu.getArtifacts().add(arifactRepository.findOne(z)));
			result.add(clu);
		}
		return result;
	}
	
	@Override
	public List<Cluster> calculateCluster(ISimilarityCalculator sm, com.apporiented.algorithm.clustering.Cluster hCluster) {

		List<Cluster> result = new ArrayList<>();
		List<com.apporiented.algorithm.clustering.Cluster> k = getClustersWithThreshold(hCluster, THRESHOLD);
		for (com.apporiented.algorithm.clustering.Cluster cluster : k) {
			Cluster clu = new Cluster();
			clu.setArtifacts(new ArrayList<Artifact>());
			getLeaf(cluster).forEach(z ->
					clu.getArtifacts().add(arifactRepository.findOne(z)));
			result.add(clu);
		}
		return result;
	}
	
	
	private List<String> getLeaf(com.apporiented.algorithm.clustering.Cluster cluster){
		List<String> result = new ArrayList<>();
		if (cluster.isLeaf())
			result.add(cluster.getName());
		for (com.apporiented.algorithm.clustering.Cluster childs : cluster.getChildren()) {
			result.addAll(getLeaf(childs));
		}
		return result;
	}
	
	@Override
	public com.apporiented.algorithm.clustering.Cluster getHierarchicalCluster(
			ISimilarityCalculator valuedRelationService) {
		List<String> names = arifactRepository.findAll().
				stream().map(n -> n.getId()).collect(Collectors.toList());
		String[] namesString = new String[names.size()];
		namesString = names.toArray(namesString);
		double[][] distances = getSimilarityMatrix(valuedRelationService);
		ClusteringAlgorithm alg = new DefaultClusteringAlgorithm();
		return alg.performClustering(distances, namesString,
				new SingleLinkageStrategy());
	}
	
	private double[][] getSimilarityMatrix(ISimilarityCalculator similarityCalculator) {
		List<Artifact> emms1 = arifactRepository.findAll();
		Table<String, String, Double> matrix = similarityManager.getDistanceMatrix(similarityCalculator);
		double[][] result = new double[emms1.size()][emms1.size()];
		for (int i = 0; i < emms1.size(); i++) {
			for (int j = 0; j < emms1.size(); j++) {
				if (matrix.get(emms1.get(i).getId(), emms1.get(j).getId()) != null){
					result[i][j] = 1 - matrix.get(emms1.get(i).getId(), emms1.get(j).getId());
					result[j][i] = 1 - matrix.get(emms1.get(i).getId(), emms1.get(j).getId());
				}
					
				if (matrix.get(emms1.get(j).getId(), emms1.get(i).getId()) != null){ 
					result[i][j] = 1-matrix.get(emms1.get(j).getId(), emms1.get(i).getId());
					result[j][i] = 1- matrix.get(emms1.get(j).getId(), emms1.get(i).getId());
				}
				if(matrix.get(emms1.get(i).getId(), emms1.get(j).getId()) == null &&
						matrix.get(emms1.get(j).getId(), emms1.get(i).getId()) == null){
					result[i][j] = 1;
					result[j][i] = 1;
				}
				if(emms1.get(j).getId().equals(emms1.get(i).getId())){
					result[i][j] = 0;
					result[j][i] = 0;
				}
				
					
				
			}
		}
		return result;
	}
	
	private List<Cluster> getClustersFromLibrary(
			List<com.apporiented.algorithm.clustering.Cluster> clusterList,
			ISimilarityCalculator valuedRelationService) {
		List<Cluster> result = new ArrayList<>();
		for (com.apporiented.algorithm.clustering.Cluster cluster : clusterList) {
			Cluster myCluster = new Cluster();
			List<com.apporiented.algorithm.clustering.Cluster> leaves = getClusterLeaf(cluster);
			for (com.apporiented.algorithm.clustering.Cluster leaf : leaves) {
				Artifact emm = arifactRepository.findOneByName(leaf.getName());
				myCluster.getArtifacts().add(emm);
			}
			result.add(myCluster);
		}
		return result;
	}	
	
	private List<com.apporiented.algorithm.clustering.Cluster> getClusterLeaf(
			com.apporiented.algorithm.clustering.Cluster cluster) {
		List<com.apporiented.algorithm.clustering.Cluster> result = new ArrayList<>();
		if (cluster.isLeaf())
			result.add(cluster);
		else
			for (com.apporiented.algorithm.clustering.Cluster c : cluster.getChildren()) {
				result.addAll(getClusterLeaf(c));
			}
		return result;
	}

	private List<com.apporiented.algorithm.clustering.Cluster> getClustersWithThreshold(
			com.apporiented.algorithm.clustering.Cluster c, double threshold) {
		List<com.apporiented.algorithm.clustering.Cluster> result = new ArrayList<>();
		if (c.getDistance() != null && c.getDistance() <= threshold)
			result.add(c);
		else if (c.isLeaf())
			result.add(c);
		else
			for (com.apporiented.algorithm.clustering.Cluster cluster : c.getChildren()) {
				result.addAll(getClustersWithThreshold(cluster, threshold));
			}
		return result;
	}
	
}
