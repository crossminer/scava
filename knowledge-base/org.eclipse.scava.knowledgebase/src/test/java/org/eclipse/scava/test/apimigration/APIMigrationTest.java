/*******************************************************************************
 * Copyright (C) 2017 University of L'Aquila
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.test.apimigration;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.aether.artifact.Artifact;
import org.eclipse.scava.business.impl.APIMigration.MigrationService;
import org.eclipse.scava.business.integration.DetectionMetadataRepository;
import org.eclipse.scava.business.integration.MavenLinkAllRepository;
import org.eclipse.scava.business.model.MavenLibrary;
import org.eclipse.scava.business.model.migration.DetectionMetaData;
import org.eclipse.scava.business.model.migration.DetectionResult;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.maracas.data.Detection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.MultiValueMap;

import nl.cwi.swat.aethereal.MigrationInfo;

//
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:application.properties")
public class APIMigrationTest {

	private static final Logger logger = LoggerFactory.getLogger(APIMigrationTest.class);
	@Autowired
	private MigrationService migrationService;

	@Autowired
	private MavenLinkAllRepository mavenLinkAllrepo;

	@Before
	public void init() {
//		MavenLinkAll mla1 = new MavenLinkAll("lib1:lib1:1","client1:client2:1");
//		MavenLinkAll mla2 = new MavenLinkAll("lib1:lib1:1","client1:client3:1");
//		MavenLinkAll mla3 = new MavenLinkAll("lib1:lib1:1","client1:client4:1");
//		MavenLinkAll mla4 = new MavenLinkAll("lib1:lib1:1","client1:client5:1");
//		MavenLinkAll mla5 = new MavenLinkAll("lib1:lib1:1","client1:client1:1");
//		MavenLinkAll mla1b = new MavenLinkAll("lib1:lib1:2","client1:client2:2");
//		MavenLinkAll mla2b = new MavenLinkAll("lib1:lib1:2","client1:client3:2");
//		MavenLinkAll mla3b = new MavenLinkAll("lib1:lib1:2","client1:client4:2");
//		MavenLinkAll mla4b = new MavenLinkAll("lib1:lib1:2","client1:client5:2");
//		MavenLinkAll mla5b = new MavenLinkAll("lib1:lib1:2","client1:client1:2");
//		mavenLinkAllrepo.save(Arrays.asList(mla1, mla2, mla3, mla4, mla5, mla1b, mla2b, mla3b, mla4b, mla5b));
	}

	@After
	public void dispose() {
		// mavenLinkAllrepo.deleteAll();
	}

	@Test
	@Ignore
	public void importerTest() {
		migrationService.importer("/Users/juri/development/git/aethereal/aethereal/dependency-graph/links_all.csv");
	}

	@Ignore
	@Test
	public void getPairsTest() {
		MigrationInfo test = migrationService.getMigrationPairs("lib1:lib1:1", "lib1:lib1:2");
		assertNotNull(test.libv1);
		assertNotNull(test.libv2);
		assertNotEquals(2, test.clientsV1.size());
		logger.info("It discovers {} clients that have been migrate to {}", test.clientsV1.size(),
				test.libv1.getVersion());
		assertNotEquals(2, test.clientsV2.size());
	}

	@Ignore
	@Test
	public void getClients() {
		List<Artifact> test = migrationService.getClients("client1:client1:1");
		assertNotEquals(0, test.size());
	}

	@Test
	@Ignore
	public void createDeltaTest() throws Exception {
		migrationService.createDelta(new MavenLibrary("guava", "com.google.guava", "18.0"),
				new MavenLibrary("guava", "com.google.guava", "19.0"));
	}

	@Test
	@Ignore
	public void storeDeltaTest() throws Exception {
		migrationService.storeDelta(new MavenLibrary("guava", "com.google.guava", "18.0"),
				new MavenLibrary("guava", "com.google.guava", "19.0"));
	}

	@Test
	@Ignore
	public void storeDeltaByCoordinateTest() throws Exception {
		migrationService.storeDelta("org.sonarsource.sonarqube:sonar-plugin-api:5.6",
				"org.sonarsource.sonarqube:sonar-plugin-api:6.2");
	}

	@Test
	@Ignore
	public void getDetectionsTest() throws Exception {
		List<Detection> detects = migrationService.getDetections("org.sonarsource.sonarqube:sonar-plugin-api:5.6",
				"org.sonarsource.sonarqube:sonar-plugin-api:6.2",
				"/Users/juri/development/git/aethereal/aethereal/dataset/sonar-plugin-api6.2/org/sonarsource/java/java-frontend/4.5.0.8398/java-frontend-4.5.0.8398.jar.m3");
		logger.info(detects.size() + "");
	}

	@Test
	@Ignore
	public void createDetectionTest() throws Exception {
		DetectionMetaData detects = migrationService.createDetection("com.google.guava:guava:18.0",
				"com.google.guava:guava:19.0",
				"/Users/juri/Documents/dataset/clients/com/spotify/helios-client/0.9.25/helios-client-0.9.25.jar.m3");
		logger.info(detects.getDetections().size() + "");
	}

	@Test
	@Ignore
	public void getDetectionMDTest() {
		try {
			DetectionMetaData detects = migrationService.storeDetections("com.google.guava:guava:18.0",
					"com.google.guava:guava:19.0", "", null);
			logger.info(detects.getDetections().size() + "");
		} catch (Exception e) {
			logger.error("Error in detection calculation {}", e.getMessage());
		}
	}

	@Test
	@Ignore
	public void sourceTest() throws Exception {
		logger.info(migrationService.getCode("org.sonarsource.sonarqube:sonar-plugin-api:6.2",
				"|java+method:///org/sonar/server/es/IndexerStartupTask/execute()|"));
	}

	@Autowired
	private DetectionMetadataRepository detMDrepo;

	@Test
	@Ignore
	public void testGetCodeFromDetection() throws Exception {
		logger.info("1");
		List<DetectionMetaData> dects = detMDrepo.findAll();
		logger.info("1");
		for (DetectionMetaData detectionMetaData : dects) {
			logger.info(detectionMetaData.getClientSource());
			for (Detection detection : detectionMetaData.getDetections()) {
				logger.info("\t" + detection.getClientLocation());
				logger.info("\t"
						+ migrationService.getCode(detectionMetaData.getClientSource(), detection.getClientLocation()));
				logger.info("\t===========");
			}
			logger.info("###############");
		}
	}

	@Test
	@Ignore
	public void recommendsDetectionTest() {
		try {
			List<DetectionResult> detectionResults = migrationService.getDetecionResults(
					"org.sonarsource.sonarqube:sonar-plugin-api:5.6", "org.sonarsource.sonarqube:sonar-plugin-api:6.2",
					"/Users/juri/development/git/aethereal/aethereal/dataset/sonar-plugin-api6.2/org/sonarsource/java/java-frontend/4.5.0.8398/java-frontend-4.5.0.8398.jar.m3");
			for (DetectionResult detectionResult : detectionResults) {
				logger.error(detectionResult.getDetection().getClientLocation());
				for (DetectionMetaData detectionMetadata : detectionResult.getDetectionsMD()) {
						logger.error("\tClient name: {}", detectionMetadata.getClientSource());
						for (Detection detection : detectionMetadata.getDetections().stream().limit(10).collect(Collectors.toList()))
							if(detectionResult.getDetection().getOldLibraryLocation().equals(detection.getOldLibraryLocation()))
								logger.info("\t\tClient location: {}", detection.getClientLocation());

						logger.info("-------------------------------------");
				}
		}
		} catch (Exception e) {
			logger.error("error in recommending snippets");
		}
	}

	@Test
	@Ignore
	public void getRecommendationTest() {
		try {
			MultiValueMap<String, String> k = migrationService.recommendsSnippet(
					"org.sonarsource.sonarqube:sonar-plugin-api:5.6", "org.sonarsource.sonarqube:sonar-plugin-api:6.2",
					"/Users/juri/development/git/aethereal/aethereal/dataset/sonar-plugin-api6.2/org/sonarsource/java/java-frontend/4.5.0.8398/java-frontend-4.5.0.8398.jar.m3");
			for (String clientLoc : k.keySet())
				for (String snippet : k.get(clientLoc))
					logger.info("{} -> \n{} ", clientLoc, snippet);

		} catch (Exception e) {
			logger.error("Error in recommendation calculation: {}", e.getMessage());
		}
	}

}
