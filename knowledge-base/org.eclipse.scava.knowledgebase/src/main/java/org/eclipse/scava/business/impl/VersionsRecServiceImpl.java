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

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.scava.business.IRecommendationProvider;
import org.eclipse.scava.business.dto.Dependency;
import org.eclipse.scava.business.dto.Query;
import org.eclipse.scava.business.dto.Recommendation;
import org.eclipse.scava.business.dto.RecommendationItem;
import org.eclipse.scava.business.dto.RecommendedLibrary;
import org.eclipse.scava.business.integration.MavenLibraryRepository;
import org.eclipse.scava.business.model.MavenLibrary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Qualifier("VersionsRec")
public class VersionsRecServiceImpl implements IRecommendationProvider {
	@Value("${crossrec.numberOfNeighbours}")
	private int numberOfNeighbours;

	@Value("${crossrec.numberOfRecommendedLibs}")
	private int numberOfRecommendedLibs;
	@Autowired
	private MavenLibraryRepository mvnRepository;

	@Value("${versionrecommender.releasefile}")
	private String fileName;
	
	private static final Logger logger = LoggerFactory.getLogger(VersionsRecServiceImpl.class);
	
	@Override
	public Recommendation getRecommendation(Query query) throws Exception {
		Recommendation rec = new Recommendation();
		query.getProjectDependencies();
		for (Dependency dep : query.getProjectDependencies()){
			List<MavenLibrary> libs = mvnRepository.findByGroupidAndArtifactidOrderByReleasedateDesc(dep.getGroupID(), dep.getArtifactID());
			for (MavenLibrary mavenLibrary : libs) {
				RecommendationItem ri = new RecommendationItem();
				RecommendedLibrary rl = new RecommendedLibrary();
				rl.setLibraryName(String.format("%s:%s:%s",mavenLibrary.getGroupid(),mavenLibrary.getArtifactid(),mavenLibrary.getVersion()));
				rl.setReleaseDate(mavenLibrary.getReleasedate());
				ri.setRecommendedLibrary(rl);
				rec.getRecommendationItems().add(ri);
			}
			
		}
		return rec;
	}
	
	public void loadVersions() {
		
		try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
			boolean first = true;
			for (String repo : stream.collect(Collectors.toList())) {
				if (first)
					first = false;
				else {
					logger.info(repo);
					MavenLibrary mvn = new MavenLibrary();
					String completeLibrary = repo.replace("\"", "").split(",")[0];
					mvn.setGroupid(completeLibrary.split(":")[0]);
					mvn.setArtifactid(completeLibrary.split(":")[1]);
					mvn.setVersion(completeLibrary.split(":")[2]);
					DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
					try {
						mvn.setReleasedate(
								dateFormat.parse(repo.split(",")[2].replace("\"", "").replace("Z[GMT]", "")));
					} catch (ParseException e) {
						logger.info("Error parse date: {}", e.getMessage());
					}

					mvnRepository.save(mvn);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
