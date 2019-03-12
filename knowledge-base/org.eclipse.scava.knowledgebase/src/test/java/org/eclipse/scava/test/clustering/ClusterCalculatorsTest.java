/*******************************************************************************
 * Copyright (C) 2017 University of L'Aquila
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.test.clustering;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.scava.business.ISimilarityCalculator;
import org.eclipse.scava.business.ISimilarityManager;
import org.eclipse.scava.business.impl.CROSSRecSimilarityCalculator;
import org.eclipse.scava.business.impl.CROSSSimSimilarityCalculator;
import org.eclipse.scava.business.impl.ClaraClulsterCalulator;
import org.eclipse.scava.business.impl.ClusterManager;
import org.eclipse.scava.business.impl.HierarchicalClulsterCalulator;
import org.eclipse.scava.business.impl.KmeansClulsterCalulator;
import org.eclipse.scava.business.impl.OssmeterImporter;
import org.eclipse.scava.business.integration.ArtifactRepository;
import org.eclipse.scava.business.integration.ClusterRepository;
import org.eclipse.scava.business.integration.ClusterizationRepository;
import org.eclipse.scava.business.integration.GithubUserRepository;
import org.eclipse.scava.business.integration.RelationRepository;
import org.eclipse.scava.business.model.Artifact;
import org.eclipse.scava.business.model.Cluster;
import org.eclipse.scava.business.validator.ExternalValidator;
import org.eclipse.scava.business.validator.InternalValidator;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:application.properties")
public class ClusterCalculatorsTest {

	private static final Logger logger = LoggerFactory.getLogger(ClusterCalculatorsTest.class);

	@Autowired
	private KmeansClulsterCalulator kmeans;
	@Autowired
	private ClaraClulsterCalulator clara;
	@Autowired
	private HierarchicalClulsterCalulator hclust;
	@Autowired
	@Qualifier ("Dependency")
	private ISimilarityCalculator simDependencyCalculator;
	
//	@Test
//	public void kmeansTest() {
//		ArrayList<Float> compactnesss = new ArrayList<Float>();
//		ArrayList<Float> entropys = new ArrayList<Float>();
//		ArrayList<Float> puritys = new ArrayList<Float>();
//		ArrayList<Float> fmeasures = new ArrayList<Float>();
//		ArrayList<Float> siluettes = new ArrayList<Float>();
//		ArrayList<Float> randIndexes = new ArrayList<Float>();
//		for (int trial = 0; trial < 50; trial++) {
//			logger.info("CLARA - CROSSSIM");
//			List<Cluster> clusters = clara.calculateCluster(crossRec, 43);
//			for(int i= clusters.size();i<47;i++) {
//				clusters.add(new Cluster());
//			}
//			float compactness = iValidator.computeCompactness(clusters, 570);
//			logger.info("compactness " + compactness);
//			compactnesss.add(compactness);
//			float entropy = eValidator.computeEntropy(clusters, 570, "doctopic.csv");
//			logger.info("entopy  " + entropy);
//			entropys.add(entropy);
//			float purity = eValidator.computePurity(clusters, 570, "doctopic.csv");
//			logger.info("purity  " + purity);
//			puritys.add(purity);
//			float fmeasure = eValidator.computeFMeasure(clusters, 570, "doctopic.csv");
//			logger.info("fmeasure  " + fmeasure);
//			fmeasures.add(fmeasure);
//			float siluette = iValidator.computeSilhouette(clusters,570);
//			logger.info("siluette  " + siluette);
//			siluettes.add(siluette);
//			float randIndex = eValidator.computeRandIndex(clusters, 570, "doctopic.csv");
//			randIndexes.add(randIndex);
//			logger.info("rendIndex  " + randIndex);
//		}
//		String compactnessstring = "";
//		String entropystring = "";
//		String puritystring = "";
//		String fmeasurestring = "";
//		String siluettestring = "";
//		String randIndexestring = "";
//		for (int i = 0; i < randIndexes.size(); i++) {
//			compactnessstring = compactnessstring + compactnesss.get(i) + ",";
//			entropystring = entropystring +  entropys.get(i) + ",";
//			puritystring = puritystring +  puritys.get(i) + ",";
//			fmeasurestring = fmeasurestring +  fmeasures.get(i) + ",";
//			siluettestring = siluettestring +  siluettes.get(i) + ",";
//			randIndexestring = randIndexestring +  randIndexes.get(i) + ",";
//		}
//		logger.info("compactnessstring" + compactnessstring);
//		logger.info("entropystring" + entropystring);
//		logger.info("puritystring" + puritystring);
//		logger.info("fmeasurestring" + fmeasurestring);
//		logger.info("siluettestring" + siluettestring);
//		logger.info("randIndexestring" + randIndexestring);
////		logger.info("KMEDOIDS - CROSSREC");
////		List<Cluster> clusters = kmeans.calculateCluster(crossRec);
////		for(int i= clusters.size();i<47;i++) {
////			clusters.add(new Cluster());
////		}
////		float value = iValidator.computeCompactness(clusters, 570);
////		logger.info("compactness " + value);
////		float entropy = eValidator.computeEntropy(clusters, 570, "doctopic.csv");
////		logger.info("entopy  " + entropy);
////		float purity = eValidator.computePurity(clusters, 570, "doctopic.csv");
////		logger.info("purity  " + purity);
////		float fmeasure = eValidator.computeFMeasure(clusters, 570, "doctopic.csv");
////		logger.info("fmeasure  " + fmeasure);
////		float siluette = iValidator.computeSilhouette(clusters,570);
////		logger.info("siluette  " + siluette);
////		float randIndex = eValidator.computeRandIndex(clusters, 570, "doctopic.csv");
////		logger.info("rendIndex  " + randIndex);
////		logger.info("KMEDOIDS - CROSSSIM");
////		clusters = kmeans.calculateCluster(crossSim);
////		value = iValidator.computeCompactness(clusters, 570);
////		logger.info("compactness " + value);
////		entropy = eValidator.computeEntropy(clusters, 570, "doctopic.csv");
////		logger.info("entopy  " + entropy);
////		purity = eValidator.computePurity(clusters, 570, "doctopic.csv");
////		logger.info("purity  " + purity);
////		fmeasure = eValidator.computeFMeasure(clusters, 570, "doctopic.csv");
////		logger.info("fmeasure  " + fmeasure);
////		siluette = iValidator.computeSilhouette(clusters,570);
////		logger.info("siluette  " + siluette);
////		randIndex = eValidator.computeRandIndex(clusters, 570, "doctopic.csv");
////		logger.info("rendIndex  " + randIndex);
////		int count = 0;
////		for (Cluster cluster : clusters) {
////			String s = "";
////			for (Artifact art : cluster.getArtifacts())
////				s = s + art.getFullName().replace("/", "__") + " ";
////			logger.info(count + ": " + s + cluster.getMostRepresentative().getFullName());
////			count++;
////		}
//	}
//	@Ignore
//	@Test
//	public void claraTest() {
//		logger.info("CLARA - CROSSREC");
//		List<Cluster> clusters = clara.calculateCluster(crossRec, 43);
//		int cs = clusters.size();
//		float value = iValidator.computeCompactness(clusters, 570);
//		logger.info("compactness " + value);
//		float entropy = eValidator.computeEntropy(clusters, 570, "doctopic.csv");
//		logger.info("entopy  " + entropy);
//		float purity = eValidator.computePurity(clusters, 570, "doctopic.csv");
//		logger.info("purity  " + purity);
//		float fmeasure = eValidator.computeFMeasure(clusters, 570, "doctopic.csv");
//		logger.info("fmeasure  " + fmeasure);
//		float siluette = iValidator.computeSilhouette(clusters,570);
//		logger.info("siluette  " + siluette);
//		float randIndex = eValidator.computeRandIndex(clusters, 570, "doctopic.csv");
//		logger.info("rendIndex  " + randIndex);
//		logger.info("CLARA - CROSSSIM");
//		clusters = clara.calculateCluster(crossSim, 43);
//		value = iValidator.computeCompactness(clusters, 570);
//		logger.info("compactness " + value);
//		entropy = eValidator.computeEntropy(clusters, 570, "doctopic.csv");
//		logger.info("entopy  " + entropy);
//		purity = eValidator.computePurity(clusters, 570, "doctopic.csv");
//		logger.info("purity  " + purity);
//		fmeasure = eValidator.computeFMeasure(clusters, 570, "doctopic.csv");
//		logger.info("fmeasure  " + fmeasure);
//		siluette = iValidator.computeSilhouette(clusters,570);
//		logger.info("siluette  " + siluette);
//		randIndex = eValidator.computeRandIndex(clusters, 570, "doctopic.csv");
//		logger.info("rendIndex  " + randIndex);
////		int count = 0;
////		for (Cluster cluster : clusters) {
////			String s = "";
////			for (Artifact art : cluster.getArtifacts())
////				s = s + art.getFullName().replace("/", "__") + " ";
////			logger.info(count + ": " + s + cluster.getMostRepresentative().getFullName());
////			count++;
////		}
//	}
	@Autowired
	private ClusterRepository clusterRepository;
	
	@Autowired
	private ClusterizationRepository clusterizationRepository;
	
	@Autowired
	private RelationRepository relationRepository;
	
	@Autowired
	private GithubUserRepository githubUserRepository;
	
	@Autowired
	private ArtifactRepository artifactRepository;
	private List<Artifact> artifacts;
	@Autowired
	OssmeterImporter ossmeterImporter;
	@Autowired
	private ISimilarityManager similarityManager;

	
	@Before
	public void testCreateAndStoreDistanceMatrix() {
		artifactRepository.deleteAll();
		githubUserRepository.deleteAll();
		relationRepository.deleteAll();
		clusterRepository.deleteAll();
		clusterizationRepository.deleteAll();
		try {
			ObjectMapper mapper = new ObjectMapper();
			Resource resource = new ClassPathResource("artifacts.json");
			InputStream resourceInputStream = resource.getInputStream();
			artifacts = mapper.readValue(resourceInputStream, new TypeReference<List<Artifact>>(){});
			artifactRepository.save(artifacts);
			for (Artifact artifact : artifacts) {
				ossmeterImporter.storeGithubUser(artifact.getStarred(), artifact.getFullName());
				ossmeterImporter.storeGithubUserCommitter(artifact.getCommitteers(), artifact.getFullName());
			} 
			resourceInputStream.close();
			
			similarityManager.createAndStoreDistanceMatrix(simDependencyCalculator);
			assertEquals(((artifacts.size() * (artifacts.size() -1))/2), 
					relationRepository.findAllByTypeName(simDependencyCalculator.getSimilarityName()).size());
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}
	@Test
	public void testClara() {
		List<Cluster> cls = clara.calculateCluster(simDependencyCalculator, 4);
		assertNotEquals(cls.size(), 0);
	}
	@Test
	public void testKmedoid() {
		List<Cluster> cls = kmeans.calculateCluster(simDependencyCalculator, 4);
		assertNotEquals(cls.size(), 0);
	}
	@Test
	public void testHierarchical() {
		List<Cluster> cls = hclust.calculateCluster(simDependencyCalculator, 0.1);
		assertNotEquals(cls.size(), 0);
	}
}
