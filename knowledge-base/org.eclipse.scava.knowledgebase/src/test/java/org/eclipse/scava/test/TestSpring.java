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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.eclipse.scava.Application;
import org.eclipse.scava.business.IImporter;
import org.eclipse.scava.business.ISimilarityManager;
import org.eclipse.scava.business.ISingleSimilarityCalculator;
import org.eclipse.scava.business.integration.ArtifactRepository;
import org.eclipse.scava.business.integration.GithubUserRepository;
import org.eclipse.scava.business.model.Artifact;
import org.eclipse.scava.business.model.GithubUser;
import org.eclipse.scava.business.model.Stargazers;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { Application.class })
@TestPropertySource(locations = "classpath:application.properties")
public class TestSpring {

	private static final Logger logger = Logger.getLogger(TestSpring.class);
	@Autowired
	@Qualifier("Readme")
	private ISingleSimilarityCalculator simReadmeCalculator;

	@Autowired
	@Qualifier("Dependency")
	private ISingleSimilarityCalculator simDependencyCalculator;

	@Autowired
	@Qualifier("Compound")
	private ISingleSimilarityCalculator simCompoundCalculator;

	@Autowired
	private ISimilarityManager simManager;

	@Autowired
	private IImporter importer;

	@Autowired
	private ArtifactRepository artifactRepository;

	@Autowired
	private GithubUserRepository userRepository;

	@Ignore
	@Test
	public void test() {

		Artifact p1 = new Artifact();
		p1.setReadmeText("Come back");
		Artifact p2 = new Artifact();
		p2.setReadmeText("Back to the system");
		double d = simReadmeCalculator.calculateSimilarity(p1, p2);
		logger.debug(d);
	}
	
	
	
	
	@Ignore
	@Test
	public void testImporter() throws IOException, XmlPullParserException {
		importer.importProject("spring-projects/spring-data-mongodb").getId();
		importer.importProject("apache/log4j").getId();
		importer.importProject("apache/lucene-solr").getId();
		importer.importProject("dozd/mongo-mapper");
		importer.importProject("wangym/koubei-mongo");
		importer.importProject("stump201/mongiORM");
		importer.importProject("289048093/mongoorm");
		importer.importProject("abinj/DataNucleus-MongoDB-Dropwizard");
		importer.importProject("TiagoSG22/MongoDB-Mongo-Java-ORM");
		importer.importProject("belerweb/mongo-java-orm");
		importer.importProject("fondemen/n-orm.mongo");
		importer.importProject("sndyuk/logback-more-appenders");
		importer.importProject("noveogroup/android-logger");
		importer.importProject("Muyangmin/Android-PLog");
		importer.importProject("OpenHFT/Chronicle-Logger");
		importer.importProject("fluent/fluent-logger-java");
		importer.importProject("isrsal/spring-mvc-logger");
		importer.importProject("humanity/logger");
		importer.importProject("Berico-Technologies/CLAVIN");
		importer.importProject("orientechnologies/orientdb");
		importer.importProject("healthonnet/hash-based-index-splitter");
		importer.importProject("kolbasa/OCRaptor");
		importer.importProject("ahomansikka/sukija");
		assertThat(artifactRepository.findAll().size(),is(23));
	}

	@Ignore
	@Test
	public void testProjectRepositorY() {
		artifactRepository.findAll().forEach(x -> System.out.println(x.getFullName()));
	}

//	@Ignore
//	@Test
//	public void testSimReadmeManager() {
//		List<Artifact> prjs = artifactRepository.findAll();
//		simManager.calculateSimilarity(prjs.get(0), prjs.get(1), simReadmeCalculator);
//	}
//
//	@Ignore
//	@Test
//	public void testSimDependenciesManager() {
//		List<Artifact> prjs = artifactRepository.findAll();
//		simManager.calculateSimilarity(prjs.get(0), prjs.get(1), simDependencyCalculator);
//	}
//
//	@Ignore
//	@Test
//	public void testSimCompoundManager() {
//		List<Artifact> prjs = artifactRepository.findAll();
//		simManager.calculateSimilarity(prjs.get(0), prjs.get(1), simCompoundCalculator);
//	}

	@Ignore
	@Test
	public void testSimilarity() {
		List<Artifact> prjs = artifactRepository.findAll();
		logger.debug(simDependencyCalculator.calculateSimilarity(prjs.get(0), prjs.get(1)));
	}

	@Ignore
	@Test
	public void cloneRepo() {
		try {
			Git git = Git.cloneRepository().setURI("https://github.com/eclipse/jgit.git")
					.setDirectory(new File("cloneFolder")).call();
		} catch (GitAPIException e) {
			e.printStackTrace();
		}
	}
	@Ignore
	@Test
	public void removeDuplicateDeps(){
//		List<Artifact> arts = artifactRepository.findAll();
//		int count = 1;
//		for (Artifact artifact : arts) {
//			Set<String> depsSet = new HashSet<>(artifact.getDependencies());
//			logger.debug(artifact.getFullName() + ": " + artifact.getDependencies().size() + " of " + depsSet.size());
//			if(depsSet.size() < 8){
//				logger.debug(artifact.getFullName());
//				//removeDuplicates(artifact);
//				artifactRepository.delete(artifact);
//				count++;
//			}
//			else{
//				artifact.getDependencies().clear();
//				artifact.getDependencies().addAll(depsSet);
//				artifactRepository.save(artifact);
//			}
//		}
//		logger.debug(count);
		List<Artifact> arts = artifactRepository.findAll();
		int count = 1;
		int total = arts.size();
		for (Artifact artifact : arts) {
			if (!(artifact.getStarred().size() > 20 && artifact.getReadmeText() != null
					&& artifact.getDependencies().size() >= 8)) {
				for (Stargazers star : artifact.getStarred()) {
					GithubUser usr = userRepository.findOneByLogin(star.getLogin());
					usr.getStarredRepo().remove(artifact.getFullName());
					if(usr.getStarredRepo().isEmpty())
						userRepository.delete(usr);
					else userRepository.save(usr);
				}
				artifactRepository.delete(artifact);
				System.out.println("DROP " + artifact.getFullName());
				count ++;
			}
		}
		System.out.println(total + ": " + count);
	}
	
	@Test
	public void cleanDataSetTest() {
		List<Artifact> arts = artifactRepository.findAll();
		int count = 1;
		int total = arts.size();
		for (Artifact artifact : arts) {
			if (!(artifact.getStarred().size() > 20 && artifact.getReadmeText() != null
					&& artifact.getDependencies().size() >= 8)) {
				for (Stargazers star : artifact.getStarred()) {
					GithubUser usr = userRepository.findOneByLogin(star.getLogin());
					usr.getStarredRepo().remove(artifact.getFullName());
					if(usr.getStarredRepo().isEmpty())
						userRepository.delete(usr);
					else userRepository.save(usr);
				}
				artifactRepository.delete(artifact);
				System.out.println("DROP " + artifact.getFullName());
				count ++;
			}
		}
		System.out.println(total + ": " + count);
	}


}
