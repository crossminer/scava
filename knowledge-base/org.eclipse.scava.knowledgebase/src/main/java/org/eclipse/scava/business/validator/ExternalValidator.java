package org.eclipse.scava.business.validator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.scava.business.integration.ArtifactRepository;
import org.eclipse.scava.business.model.Artifact;
import org.eclipse.scava.business.model.Cluster;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Sets;

@Service
public class ExternalValidator {

	@Autowired
	private ArtifactRepository artifactRepository;
	private Cluster[] clusters;
	private Cluster[] Classes;

	public float computeEntropy(List<Cluster> clusters, int numOfDocs, String classFilename) {

		float e = 0;
		float entropy = 0, tmp = 0;
		this.clusters = new Cluster[clusters.size()];
		this.clusters = clusters.toArray(this.clusters);
		Classes = readClasses(classFilename);

		int m = 0;
		for (int i = 0; i < clusters.size(); i++) {
			e = computeEntropy(i);
			m = this.clusters[i].getArtifacts().size();
			tmp = (float) m / numOfDocs;
			entropy += tmp * e;
		}
		return entropy;
	}

	private Cluster[] readClasses(String classFilename) {
		Cluster[] result = new Cluster[clusters.length];
		try {
			BufferedReader bw = new BufferedReader(new FileReader(classFilename));
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < result.length; i++) {
				result[i] = new Cluster();
				result[i].setArtifacts(new ArrayList<Artifact>());
			}
			String line;
			while ((line = bw.readLine()) != null) {
				String item = line.split(",")[0];
				int cluster = Integer.parseInt(line.split(",")[1]);
				Artifact art = artifactRepository.findOneByFullName(item.replace("__", "/"));
				if (art != null)
					result[cluster].getArtifacts().add(art);
			}
			bw.close();
			return result;
		} catch (IOException e) {
			System.err.println(e.getMessage());
			return result;
		}
	}

	public float computePurity(List<Cluster> clusters, int numOfDocs, String classFilename) {

		float p = 0;
		float purity = 0, tmp = 0;

		this.clusters = new Cluster[clusters.size()];
		this.clusters = clusters.toArray(this.clusters);
		Classes = readClasses(classFilename);

		int m = 0;

		for (int i = 0; i < clusters.size(); i++) {
			p = computePurity(i);
			m = this.clusters[i].getArtifacts().size();//clusters[i].getObjects().size();
			tmp = (float) m / numOfDocs;
			purity += tmp * p;
		}

		return purity;
	}

	public float computeFMeasure(List<Cluster> clusters, int numOfDocs, String classFilename) {
				
		float fmeasure = 0, tmp = 0;
		
		this.clusters = new Cluster[clusters.size()];
		this.clusters = clusters.toArray(this.clusters);
		Classes = readClasses(classFilename);
		
		int m = 0;
		
		Float[] F = new Float[clusters.size()];
								
		for(int i=0;i<clusters.size();i++){			
			F[i] = computeFMeasure(i);
			m = Classes[i].getArtifacts().size();			
			tmp = (float)m/numOfDocs;
			fmeasure += tmp*F[i];
		}
				
		return fmeasure;		
	}

	/* Calculate Entropy for the cluster with clusterID */

	private float computeEntropy(int clusterID) {
		float ret = 0;
		Set<String> vector1 = new HashSet();
		for (Artifact id : clusters[clusterID].getArtifacts())
			vector1.add(id.getId());
		vector1.add(clusters[clusterID].getMostRepresentative().getId());
		Set<String> vector2 = new HashSet<String>();
		Set<String> common = null;
		int m = vector1.size();
		int n = 0;
		float tmp = 0, val = 0;
		for (int i = 0; i < clusters.length; i++) {
			for (Artifact string : Classes[i].getArtifacts())
				try {
					vector2.add(string.getId());
				} catch (Exception e) {
					System.out.println("i_ " + i + " " + string);
				}
			common = Sets.intersection(vector1, vector2);
			n = common.size();
			tmp = (float) n / m;
			if (tmp == 0)
				val = 0;
			else
				val = (float) (tmp * Math.log(tmp) * (-1));
			ret += val;
		}
		return ret;
	}

	private float computePurity(int clusterID) {
		float ret = 0;
		Set<String> vector1 = new HashSet();
		for (Artifact id : clusters[clusterID].getArtifacts())
			vector1.add(id.getId());
		vector1.add(clusters[clusterID].getMostRepresentative().getId());
		Set<String> vector2 = new HashSet<String>();
		Set<String> common = null;
		int m = vector1.size();
		int n = 0, max = 0;
		float purity = 0;
		for (int i = 0; i < clusters.length; i++) {
			for (Artifact string : Classes[i].getArtifacts())
				try {
					vector2.add(string.getId());
				} catch (Exception e) {
					System.out.println("i_ " + i + " " + string);
				}
			common = Sets.intersection(vector1, vector2);
			n = common.size();

			if (max < n)
				max = n;
		}
		purity = (float) max / m;
		return purity;
	}

	private float computeFMeasure(int clusterID) {

		Set<String> vector1 = new HashSet();
		for (Artifact id : clusters[clusterID].getArtifacts())
			vector1.add(id.getId());
		vector1.add(clusters[clusterID].getMostRepresentative().getId());
		
		Set<String> vector2 = new HashSet<String>();
		Set<String> common = null;

		int ni = vector1.size();
		int nij = 0;
		float precision = 0, recall = 0;

		Float[] F = new Float[this.clusters.length];

		for (int j = 0; j < clusters.length; j++) {
			for (Artifact string : Classes[j].getArtifacts())
				try {
					vector2.add(string.getId());
				} catch (Exception e) {
					System.out.println("i_ " + j + " " + string);
				}
			common = Sets.intersection(vector1, vector2);
			nij = common.size();
			int nj = vector2.size();

			recall = (float) nij / ni;
			precision = (float) nij / nj;

			if (recall + precision == 0)
				F[j] = 0f;
			else
				F[j] = (2 * recall * precision) / (recall + precision);
		}
		float max = F[0];
		for (int j = 1; j < clusters.length; j++) {
			if (max < F[j])
				max = F[j];
		}

		return max;

	}


	/* Calculate Rand Index */

	public float computeRandIndex(List<Cluster> clusters, int numOfDocs, String classFilename) {

		float rand = 0;
		Set<String> vector1 = null, vector2 = null;

		this.clusters = new Cluster[clusters.size()];
		this.clusters = clusters.toArray(this.clusters);
		Classes = readClasses(classFilename);

		int[] classCardinality = new int[clusters.size()];
		int[] clusterCardinality = new int[clusters.size()];
		int[][] contigency = new int[clusters.size()][clusters.size()];

		for (int i = 0; i < clusters.size(); i++) {
			classCardinality[i] = Classes[i].getArtifacts().size() + 1; // including the medoid
			clusterCardinality[i] = this.clusters[i].getArtifacts().size() + 1; // including the medoid
		}

		for (int i = 0; i < clusters.size(); i++) {
			vector1 = new HashSet<String>();
			
			for (Artifact strings : Classes[i].getArtifacts()) {
				vector1.add(strings.getId());
			}
			if (Classes[i].getMostRepresentative() != null)
				vector1.add(Classes[i].getMostRepresentative().getId());
			for (int j = 0; j < clusters.size(); j++) {
				vector2 = new HashSet<String>();
				for (Artifact strings : Classes[j].getArtifacts()) {
					vector2.add(strings.getId());
				}
				if (Classes[j].getMostRepresentative() != null)
					vector1.add(Classes[i].getMostRepresentative().getId());
				contigency[i][j] = Sets.intersection(vector1, vector2).size();
			}
		}
		int tmp = (int) numOfDocs * (numOfDocs - 1) / 2;
		int sum1 = 0, sum2 = 0, sum3 = 0, val = 0;
		for (int i = 0; i < clusters.size(); i++) {
			for (int j = 0; j < clusters.size(); j++) {
				val = contigency[i][j];
				sum1 += (int) val * (val - 1) / 2;
			}
			val = classCardinality[i];
			sum2 += (int) val * (val - 1) / 2;
			val = clusterCardinality[i];
			sum3 += (int) val * (val - 1) / 2;
		}
		float sum4 = (sum2 * sum3) / tmp;
		rand = (float) ((sum1 - sum4) / ((sum2 + sum3) / 2 - sum4));
		return rand;
	}

}
