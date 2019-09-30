/*******************************************************************************
 * Copyright (C) 2017 University of L'Aquila
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.scava.business.IAggregatedSimilarityCalculator;
import org.eclipse.scava.business.IImporter;
import org.eclipse.scava.business.ISimilarityCalculator;
import org.eclipse.scava.business.ISimilarityManager;
import org.eclipse.scava.business.ISingleSimilarityCalculator;
import org.eclipse.scava.business.impl.GithubImporter;
import org.eclipse.scava.business.impl.SimilarityManager;
import org.eclipse.scava.business.integration.ArtifactRepository;
import org.eclipse.scava.business.integration.GithubUserRepository;
import org.eclipse.scava.business.integration.RelationRepository;
import org.eclipse.scava.business.model.Artifact;
import org.eclipse.scava.business.model.GithubUser;
import org.eclipse.scava.business.model.Stargazers;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:application.properties")
public class TestSpring {

	private static final Logger logger = LoggerFactory.getLogger(TestSpring.class);


	
	@Autowired
	private GithubImporter importer;

	@Autowired
	private ArtifactRepository artifactRepository;

	@Autowired
	private List<ISimilarityCalculator> crossRecSimilarityCalculator;

	@Autowired
	private SimilarityManager similarityManager;
	
	@Autowired
	private RelationRepository relRepo;

	
	@After
	public void deleteAll() {
		artifactRepository.deleteAll();
		relRepo.deleteAll();
	}
	
	@Before
	public void testImporter() throws IOException, XmlPullParserException {
		artifactRepository.deleteAll();
//		importer.importProject("spring-projects/spring-data-mongodb", "b3e500c19df0a1a72b01b5e896899dd8a53aa08a").getId();
//		importer.importProject("apache/log4j", "b3e500c19df0a1a72b01b5e896899dd8a53aa08a").getId();
//		importer.importProject("apache/lucene-solr", "b3e500c19df0a1a72b01b5e896899dd8a53aa08a").getId();
		importer.importProject("dozd/mongo-mapper", "b3e500c19df0a1a72b01b5e896899dd8a53aa08a");
		importer.importProject("wangym/koubei-mongo", "b3e500c19df0a1a72b01b5e896899dd8a53aa08a");
		importer.importProject("stump201/mongiORM", "b3e500c19df0a1a72b01b5e896899dd8a53aa08a");
		importer.importProject("289048093/mongoorm", "b3e500c19df0a1a72b01b5e896899dd8a53aa08a");
		importer.importProject("abinj/DataNucleus-MongoDB-Dropwizard", "b3e500c19df0a1a72b01b5e896899dd8a53aa08a");
		importer.importProject("TiagoSG22/MongoDB-Mongo-Java-ORM", "b3e500c19df0a1a72b01b5e896899dd8a53aa08a");
		importer.importProject("belerweb/mongo-java-orm", "b3e500c19df0a1a72b01b5e896899dd8a53aa08a");
		importer.importProject("fondemen/n-orm.mongo", "b3e500c19df0a1a72b01b5e896899dd8a53aa08a");
		importer.importProject("sndyuk/logback-more-appenders", "b3e500c19df0a1a72b01b5e896899dd8a53aa08a");
		importer.importProject("noveogroup/android-logger", "b3e500c19df0a1a72b01b5e896899dd8a53aa08a");
		importer.importProject("Muyangmin/Android-PLog", "b3e500c19df0a1a72b01b5e896899dd8a53aa08a");
		importer.importProject("OpenHFT/Chronicle-Logger", "b3e500c19df0a1a72b01b5e896899dd8a53aa08a");
		importer.importProject("fluent/fluent-logger-java", "b3e500c19df0a1a72b01b5e896899dd8a53aa08a");
		importer.importProject("isrsal/spring-mvc-logger", "b3e500c19df0a1a72b01b5e896899dd8a53aa08a");
		importer.importProject("humanity/logger", "b3e500c19df0a1a72b01b5e896899dd8a53aa08a");
		importer.importProject("Berico-Technologies/CLAVIN", "b3e500c19df0a1a72b01b5e896899dd8a53aa08a");
//		importer.importProject("orientechnologies/orientdb", "b3e500c19df0a1a72b01b5e896899dd8a53aa08a");
		importer.importProject("healthonnet/hash-based-index-splitter", "b3e500c19df0a1a72b01b5e896899dd8a53aa08a");
//		importer.importProject("kolbasa/OCRaptor", "b3e500c19df0a1a72b01b5e896899dd8a53aa08a");
		importer.importProject("ahomansikka/sukija", "b3e500c19df0a1a72b01b5e896899dd8a53aa08a");
		assertThat(artifactRepository.findAll().size(),is(18));
	}


	
	@Test
	public void recomputeSimMatrixTest() {
		for (ISimilarityCalculator iAggregatedSimilarityCalculator : crossRecSimilarityCalculator) {
			similarityManager.createAndStoreDistanceMatrix(iAggregatedSimilarityCalculator);
		}
	}

}
