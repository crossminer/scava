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
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bson.types.ObjectId;
import org.eclipse.scava.business.IClusterCalculator;
import org.eclipse.scava.business.ISimilarityCalculator;
import org.eclipse.scava.business.integration.ArtifactRepository;
import org.eclipse.scava.business.model.Artifact;
import org.eclipse.scava.business.model.Cluster;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.DBRef;

/**
 * @author Juri Di Rocco
 *
 */
@Service
@Qualifier("Clara")
public class ClaraClulsterCalulator implements IClusterCalculator {

	private static final Logger logger = LoggerFactory.getLogger(ClaraClulsterCalulator.class);
	@Autowired
	private ArtifactRepository arifactRepository;
	@Autowired
	MongoTemplate mongoTemplate;
	private List<Artifact> objects = null;
	private Artifact[] medoids;
	private Cluster[] clusters;
	private ISimilarityCalculator sm;
	int _NUM_OF_CLUSTER = 47;
	private final static String _CLUSTER_NAME = "CLARA";
	@Override
	public List<Cluster> calculateCluster(ISimilarityCalculator sm, double partitionOrTreshold) {
		objects = arifactRepository.findAll();
		// TODO num of cluster as parameter
		
		Cluster[] clusters = new Cluster[new Double(partitionOrTreshold).intValue()];
		
		this.clusters = new Cluster[_NUM_OF_CLUSTER ];
		this.sm = sm;
		for (int i = 0; i < this.clusters.length; i++) {
			clusters[i] = new Cluster();
			this.clusters[i] = new Cluster();
		}
		float quality = 0, oldQuality = 0;
		int ITER = 5;
		for (int i = 0; i < ITER; i++) {
			logger.info("Super Iteration: " + i);
			List<Artifact> sampledObjects = new ArrayList<Artifact>();
			sampledObjects = sampling(this.objects.size(), this.clusters.length);
			Artifact[] medoids = populateRandomMedoids(sampledObjects);
			execute(medoids, sampledObjects);
			clusters = assignObjectsToClusters(medoids, this.objects);
			quality = getClusteringQuality(clusters);
			if (i == 0) {
				this.medoids = medoids;
				this.clusters = clusters;
				oldQuality = quality;
			} else {
				if (quality < oldQuality) {
					this.medoids = medoids;
					this.clusters = clusters;
					oldQuality = quality;
				}
			}
		}
		return Arrays.asList(clusters);
	}

	/* Get a sampled dataset from a big dataset */
	private Artifact[] populateRandomMedoids(List<Artifact> objects) {
		List<Artifact> candidateObjects = new ArrayList<Artifact>();
		candidateObjects.addAll(objects);
		Artifact[] medoids = new Artifact[this.clusters.length];
		/* this.Clusters = new Cluster[this.numberOfClusters]; */

		for (int i = 0; i < this.clusters.length; i++) {
			int randElement = (int) Math.floor(Math.random() * candidateObjects.size());
			if (randElement > this.objects.size() - 1)
				randElement = this.objects.size() - 1;
			if (this.objects.size() == 0)
				continue;
			medoids[i] = candidateObjects.get(randElement);
//            this.Clusters[i].setMedoid(medoids[i]);
			candidateObjects.remove(randElement);
		}
		return medoids;
	}

	private List<Artifact> sampling(int numOfDocs, int numOfClusters) {
		List<Artifact> tempObjects = new ArrayList<Artifact>(this.objects);
		List<Artifact> objects = new ArrayList<Artifact>();
		int size = 40 + 2*numOfClusters;
		for (int i = 0; i < size; i++) {
			int randElement = (int) Math.floor(Math.random() * tempObjects.size());
			if (randElement > this.objects.size() - 1)
				randElement = this.objects.size() - 1;
			if(this.objects.size() == 0)continue;
			objects.add(this.objects.get(randElement));
			tempObjects.remove(randElement);
		}
		return objects;
	}

	private boolean medoidsSwapped(Artifact[] medoids, Artifact[] newMedoids) {
		if (medoids == null || newMedoids == null) {
			return true;
		}
		int length = medoids.length;
		for (int i = 0; i < length; i++) {
			boolean isSameMedoid;
			isSameMedoid = medoids[i].equals(newMedoids[i]);
			if (!isSameMedoid) {
				return true;
			}
		}
		return false;
	}

	private void execute(Artifact[] initialMedoids, List<Artifact> sampledObjects) {
		Artifact[] oldMedoids = null;
		Artifact[] medoids = new Artifact[clusters.length];
		Cluster[] Clusters = new Cluster[clusters.length];

		medoids = initialMedoids;
		int iter = 3;

		for (int i = 0; i < clusters.length; i++) {
			Clusters[i] = new Cluster();
			Clusters[i].setMostRepresentative(initialMedoids[i]);
		}

		while (medoidsSwapped(oldMedoids, medoids) && iter > 0) {
			logger.info("Iteration: " + iter);
			oldMedoids = medoids;
			Clusters = assignObjectsToClusters(oldMedoids, sampledObjects);
			medoids = getNewMedoids(Clusters);
			iter -= 1;
		}
		for (int i = 0; i < initialMedoids.length; i++)
			initialMedoids[i] = medoids[i];
	}

	private Artifact[] getNewMedoids(Cluster[] Clusters) {
		Artifact[] newMedoids = new Artifact[clusters.length];
		for (int clusterID = 0; clusterID < clusters.length; clusterID++) {
			newMedoids[clusterID] = computeNewMedoid(Clusters, clusterID);
		}
		return newMedoids;
	}

	private Cluster[] assignObjectsToClusters(Artifact[] medoids, List<Artifact> Objects) {
		int clusterID = 0;
		Set<Artifact> nonMedoids = null;
		Cluster[] tmpClusters = new Cluster[clusters.length];
		Map<String, Float> distance = new HashMap<String, Float>();
		for (int i = 0; i < clusters.length; i++) {
			tmpClusters[i] = new Cluster();
			tmpClusters[i].setArtifacts(new ArrayList<Artifact>());
			tmpClusters[i].setMostRepresentative(medoids[i]);
		}
		Set<Artifact> tmp = new HashSet<Artifact>();
		tmp.addAll(Objects);
		for (int i = 0; i < clusters.length; i++)
			tmp.remove(medoids[i]);
		for (Artifact object : tmp) {
			distance = readDistanceScores(object.getId());
			Float[] d = new Float[clusters.length];
			for (int i = 0; i < clusters.length; i++)
				d[i] = distance.get(medoids[i].getId());
			clusterID = getIndexOfSmallestElement(d);
			if (tmpClusters[clusterID].getMostRepresentative() == null)
				tmpClusters[clusterID].setMostRepresentative(medoids[clusterID]);
			nonMedoids = new HashSet<Artifact>();
			nonMedoids.addAll(tmpClusters[clusterID].getArtifacts());
			nonMedoids.add(object);
			tmpClusters[clusterID].setArtifacts(new ArrayList<Artifact>(nonMedoids));
		}
		return tmpClusters;
	}

	private int getIndexOfSmallestElement(Float[] elements) {
		int length = elements.length;
		float min = elements[0];
		int index = 0;
		for (int i = 0; i < length; i++)
			if (elements[i] < min) {
				min = elements[i];
				index = i;
			}
		return index;
	}

	private Map<String, Float> readDistanceScores(String object) {
		Map<String, Float> result = new HashMap<String, Float>();
		Query query = new org.springframework.data.mongodb.core.query.Query();
		query.addCriteria(Criteria.where("type.name").is(sm.getSimilarityName()).orOperator(
				Criteria.where("fromArtifact.$id").is(new ObjectId(object)),
				Criteria.where("toArtifact.$id").is(new ObjectId(object))));
		DBCollection dbCollection = mongoTemplate.getCollection("relation");
		DBCursor cursor = dbCollection.find(query.getQueryObject());
		List<DBObject> list = cursor.toArray();
		for (DBObject dbObject : list) {
			String toArtifact = ((DBRef) dbObject.get("toArtifact")).getId().toString();
			String fromArtifact = ((DBRef) dbObject.get("fromArtifact")).getId().toString();
			double value = ((double) dbObject.get("value"));
			if (toArtifact.equals(object))
				result.put(fromArtifact, (float) (1 - value));
			else
				result.put(toArtifact, (float) (1 - value));
		}
		return result;
	}

	/* calculate the average distance of all objects */
	private float getClusteringQuality(Cluster[] Clusters) {
		float ret = 0, val = 0;
		Artifact medoid = null;

		for (int i = 0; i < clusters.length; i++) {
			medoid = Clusters[i].getMostRepresentative();
			Map<String, Float> distance = new HashMap<String, Float>();
			distance = readDistanceScores(medoid.getId());
			Set<Artifact> tmp = new HashSet<Artifact>();
			tmp.addAll(Clusters[i].getArtifacts());
			Artifact[] nonMedoids = (Artifact[]) tmp.toArray(new Artifact[tmp.size()]);
			val += getAverageDistance(distance, nonMedoids);
		}
		ret = (float) val / clusters.length;

		return ret;
	}

	/*
	 * calculate the average distance from a medoid to all other objects of a
	 * cluster
	 */
	private float getAverageDistance(Map<String, Float> distances, Artifact[] nonMedoids) {
		int length = nonMedoids.length;
		float sum = 0.0f;
		for (int i = 0; i < length; i++) {
			sum += distances.get(nonMedoids[i].getId());
		}
		float ret = (float) sum / length;
		return ret;
	}

	private Artifact computeNewMedoid(Cluster[] Clusters, int clusterID) {
		Artifact newMedoid = null;
		Set<Artifact> tmp = new HashSet<Artifact>();
		tmp.addAll(Clusters[clusterID].getArtifacts());
		Artifact medoid = Clusters[clusterID].getMostRepresentative();
		Set<Artifact> tmp2 = new HashSet<Artifact>();
		Artifact[] nonMedoids = null;
		float d = 0.0f;
		float minDistance = 0.0f;
		Map<String, Float> distances = new HashMap<String, Float>();
		nonMedoids = tmp.toArray(new Artifact[tmp.size()]);
		distances = readDistanceScores(medoid.getId());
		minDistance = getAverageDistance(distances, nonMedoids);
		newMedoid = medoid;
		tmp.add(medoid);
		for (Artifact object : tmp) {
			tmp2 = new HashSet<Artifact>();
			tmp2.addAll(tmp);
			tmp2.remove(object);

			distances = readDistanceScores(object.getId());
			nonMedoids = (Artifact[]) tmp2.toArray(new Artifact[tmp2.size()]);
			d = getAverageDistance(distances, nonMedoids);
			if (d < minDistance) {
				minDistance = d;
				newMedoid = object;
			}
		}
		return newMedoid;
	}

	@Override
	public String getClusterName() {
		return _CLUSTER_NAME;
	}
}
