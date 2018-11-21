package org.eclipse.scava.business.validator;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.bson.types.ObjectId;
import org.eclipse.scava.business.model.Artifact;
import org.eclipse.scava.business.model.Cluster;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.DBRef;

class ValueComparator implements Comparator<String> {

	Map<String, Float> base;

	public ValueComparator(Map<String, Float> base) {
		this.base = base;
	}

	// Note: this comparator imposes orderings that are inconsistent with equals.
	public int compare(String a, String b) {
		if (base.get(a) >= base.get(b)) {
			return -1;
		} else {
			return 1;
		} // returning 0 would merge keys
	}
}

@Service
public class InternalValidator {
	private static final Logger logger = LoggerFactory.getLogger(InternalValidator.class);
	private Artifact[] medoids;
	@Autowired
	private MongoTemplate mongoTemplate;

	private static final String _SIMILARITY_METHOD = "CrossRec";
	private int numberOfClusters;

	private Cluster[] clusters;

	private Map<String, Float> readDistanceScores(String object) {
		Map<String, Float> result = new HashMap<String, Float>();
		Query query = new org.springframework.data.mongodb.core.query.Query();
		query.addCriteria(Criteria.where("type.name").is(_SIMILARITY_METHOD).orOperator(
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

	private Map<String, Float> readDistanceScores(String startingObject, Set<String> others) {
		Map<String, Float> result = new HashMap<String, Float>();
		Query query = new org.springframework.data.mongodb.core.query.Query();
		query.addCriteria(Criteria.where("type.name").is(_SIMILARITY_METHOD).orOperator(
				Criteria.where("fromArtifact.$id").is(new ObjectId(startingObject)),
				Criteria.where("toArtifact.$id").is(new ObjectId(startingObject))));
		DBCollection dbCollection = mongoTemplate.getCollection("relation");
		DBCursor cursor = dbCollection.find(query.getQueryObject());
		List<DBObject> list = cursor.toArray();
		for (DBObject dbObject : list) {
			String toArtifact = ((DBRef) dbObject.get("toArtifact")).getId().toString();
			String fromArtifact = ((DBRef) dbObject.get("fromArtifact")).getId().toString();
			double value = ((double) dbObject.get("value"));
			if (toArtifact.equals(startingObject)) {
				if (others.contains(fromArtifact))
					result.put(fromArtifact, (float) (1 - value));
			} else if (others.contains(toArtifact))
				result.put(toArtifact, (float) (1 - value));
		}
		return result;
	}

	public float computeCompactness(List<Cluster> clusterList, int numOfObjects) {
		clusters = new Cluster[clusterList.size()];
		clusters = clusterList.toArray(clusters);
		this.numberOfClusters = clusterList.size();
		medoids = new Artifact[this.numberOfClusters];
		Set<String> tmp = null;
		Artifact medoid;
		float numerator = 0f, denominator = 0f;

		Map<String, Float> distances = new HashMap<String, Float>();

		for (int clusterID = 0; clusterID < this.numberOfClusters; clusterID++) {
			tmp = new HashSet<String>();
			medoid = clusters[clusterID].getMostRepresentative();
			medoids[clusterID] = medoid;
			for (Artifact string : clusters[clusterID].getArtifacts()) {
				tmp.add(string.getId());
			}
			distances = readDistanceScores(medoid.getId(), tmp);
			for (String object : tmp) {
				numerator += distances.get(object);
			}
		}

		for (int clusterID1 = 0; clusterID1 < this.numberOfClusters - 1; clusterID1++) {
			distances = readDistanceScores(medoids[clusterID1].getId());
			for (int clusterID2 = clusterID1 + 1; clusterID2 < this.numberOfClusters; clusterID2++) {
				denominator += distances.get(medoids[clusterID2].getId());
			}
		}

		return (float) numerator / denominator;
	}

	public float computeSilhouette(List<Cluster> clusterList, int numOfObjects) {
		clusters = new Cluster[clusterList.size()];
		clusters = clusterList.toArray(clusters);
		this.numberOfClusters = clusterList.size();
		Artifact medoid;
		String[] clusterElementsStringList = null;
		Map<String, Float> Silhouette = new HashMap<String, Float>();
		Map<String, Float> a = new HashMap<String, Float>();
		// Table<String, String, Float> b1 = new HashBasedTable.create();
		Table<String, Integer, Float> distenceBetweenElementAndCluster = HashBasedTable.create();
		int index = 0;
		float min = 1.0f;

		Map<String, Float> distances = new HashMap<String, Float>();

		for (int clusterID1 = 0; clusterID1 < this.numberOfClusters; clusterID1++) {
			medoid = clusters[clusterID1].getMostRepresentative();
			Set<String> tmp = new HashSet<String>();
			tmp.add(medoid.getId());
			// INIT CLUSTER ELEMENTS
			for (Artifact fs : clusters[clusterID1].getArtifacts())
				tmp.add(fs.getId());
			clusterElementsStringList = (String[]) tmp.toArray(new String[tmp.size()]);

			for (String clusterElement : clusterElementsStringList) {
				Set<String> tmp2 = new HashSet<String>(Arrays.asList(clusterElementsStringList));
				tmp2.remove(clusterElement);
				distances = readDistanceScores(clusterElement);
				int len = tmp2.size();
				float sum = 0.0f;
				for (String t : tmp2)
					sum += distances.get(t);
				a.put(clusterElement, (float) sum / len);
				for (int clusterID2 = 0; clusterID2 < this.numberOfClusters; clusterID2++) {
					if (clusterID2 == clusterID1)
						continue;
					tmp2 = new HashSet<String>();
					medoid = clusters[clusterID2].getMostRepresentative();
					tmp2.add(medoid.getId());
					for (Artifact fs : clusters[clusterID2].getArtifacts())
						tmp2.add(fs.getId());

					len = tmp2.size();
					sum = 0.0f;
					for (String t : tmp2)
						sum += distances.get(t);
					distenceBetweenElementAndCluster.put(clusterElement, clusterID2, sum / len);
				}
				min = distenceBetweenElementAndCluster.get(clusterElement, 0==clusterID1?1:0);
				index = 0==clusterID1?1:0;

				for (int clusterID2 = 0; clusterID2 < this.numberOfClusters; clusterID2++) {
					if(clusterID2 == clusterID1)
						continue;
					if ((distenceBetweenElementAndCluster.get(clusterElement,clusterID2) < min)
							&& (distenceBetweenElementAndCluster.get(clusterElement,clusterID2) != 0)) {
						min = distenceBetweenElementAndCluster.get(clusterElement, clusterID2);
						index = clusterID2;
					}
				}
				
				if (distenceBetweenElementAndCluster.get(clusterElement, index) > a.get(clusterElement))
					Silhouette.put(clusterElement,
							(float) (1 - a.get(clusterElement) / distenceBetweenElementAndCluster.get(clusterElement,index)));
				else if (distenceBetweenElementAndCluster.get(clusterElement, index) < a.get(clusterElement))
					Silhouette.put(clusterElement,
							(float) (distenceBetweenElementAndCluster.get(clusterElement, index) / a.get(clusterElement) - 1));
				else
					Silhouette.put(clusterElement, 0f);
			}
		}
		float result = 0;
		for (String key : Silhouette.keySet())
			result += Silhouette.get(key);
		return result/Silhouette.size();
	}

	public float computeConnectivity(List<Cluster> clusterList, int numOfObjects) {
		clusters = new Cluster[clusterList.size()];
		clusters = clusterList.toArray(clusters);
		this.numberOfClusters = clusterList.size();

		float ret = 0, val = 0;
		int order = 0, count = 0;
		Set<String> tmp = null;
		Artifact medoid;
		Map<String, Float> similarity = new HashMap<String, Float>();
		for (int clusterID = 0; clusterID < this.numberOfClusters; clusterID++) {
			tmp = new HashSet<String>();
			medoid = clusters[clusterID].getMostRepresentative();
			tmp.add(medoid.getId());
			for (Artifact art : clusters[clusterID].getArtifacts()) {

				tmp.add(art.getId());
			}
			for (String object : tmp) {
				order = 0;
				count = 0;
				// TODO Changed
				similarity = readDistanceScores(object);
				ValueComparator bvc = new ValueComparator(similarity);
				TreeMap<String, Float> sorted_map = new TreeMap<String, Float>(bvc);
				sorted_map.putAll(similarity);
				Set<String> keySet = sorted_map.keySet();

				for (String key : keySet) {
					if (count < 10)
						if (!key.equals(object)) {
							if (tmp.contains(key))
								val = 0;
							else
								val = (float) 1 / order;
							ret += val;
							count++;
						}
					order += 1;
				}
			}
		}
		return ret;
	}
}
