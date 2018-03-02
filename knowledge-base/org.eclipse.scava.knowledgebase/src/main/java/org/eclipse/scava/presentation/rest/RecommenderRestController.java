/*******************************************************************************
 * Copyright (C) 2017 University of L'Aquila
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.presentation.rest;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.scava.business.IRecommenderManager;
import org.eclipse.scava.business.ISimilarityCalculator;
import org.eclipse.scava.business.dto.Dependency;
import org.eclipse.scava.business.dto.Query;
import org.eclipse.scava.business.dto.Recommendation;
import org.eclipse.scava.business.dto.RecommendationType;
import org.eclipse.scava.business.integration.ArtifactRepository;
import org.eclipse.scava.business.model.Artifact;
import org.eclipse.scava.business.model.Cluster;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Juri Di Rocco
 *
 */
@RestController
@RequestMapping("/api/recommendation")
public class RecommenderRestController {
	@Autowired
	@Qualifier("Dependency")
	private ISimilarityCalculator dependency;
	@Autowired
	@Qualifier("Readme")
	private ISimilarityCalculator readme;
	@Autowired
	private IRecommenderManager recommenderManager;
	@Autowired
	private ArtifactRepository artifactRepository;
	
	
	@RequestMapping(value="artifacts", produces = "application/json")
    public Page<Artifact> getArtifacts(Pageable pageable) {
		return artifactRepository.findAll(pageable);
    }
	
	@RequestMapping(value="cluster/{sim_method}", produces = "application/json")
    public List<Cluster> getClusters(@PathVariable("sim_method") String simFunction) {
		return recommenderManager.getClusters(simFunction);
    }

	@RequestMapping(value="recommended_libs", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public Recommendation getRecommendedLibraries(@RequestBody Query query) throws Exception {
		return recommenderManager.getRecommendation(query, RecommendationType.RECOMMENDED_LIBRARY);
    }
	
	@RequestMapping(value="alternative_recommended", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public Recommendation getAlternativeLibrary(@RequestBody Query query) throws Exception {
		return recommenderManager.getRecommendation(query, RecommendationType.ALTERNATIVE_LIBRARY);
    }
	
	@RequestMapping(value="similar/p/{id}/m/{sim_method}/n/{num}", produces = "application/json")
    public List<Artifact> getSimilarProject(@PathVariable("sim_method") String simFunction,
    		@PathVariable("id") String id,
    		@PathVariable("num") int num) {
		
		return recommenderManager.getSimilarProjects(id, simFunction, num);
    }
	
	@RequestMapping(value="search/{artifact_query}", produces = "application/json")
    public List<Artifact> getProject(@PathVariable("artifact_query") String projectQuery) {
		return recommenderManager.getArtifactsByQuery(projectQuery);
    }
	
	@RequestMapping("query")
	public Query getQuery(){
		Query q = new Query();
		q.setCompilationUnit("...");
		q.setComments(new ArrayList<String>());
		q.getComments().add("TODO");
		q.getComments().add("connect to Api with gsoup");
		Dependency d1 = new Dependency();
		d1.setName("org.apache.httpcomponents:httpclient");
		d1.setVersion("1.10.4.RELEASE");
		d1.setUrl("");
		d1.setArtifactID("5a228b0a2e429420384464da");
		Dependency d2 = new Dependency();
		d2.setName("com.google.code.gson:gson");
		d2.setVersion("6.0.0");
		d2.setUrl("");
		d2.setArtifactID("5a228b0f2e42942038446560");
		Dependency d3 = new Dependency();
		d3.setName("junit:junit");
		d3.setVersion("1.2.17");
		d3.setUrl("");
		d3.setArtifactID("5a228b162e4294203844660b");
		q.setProjectDependencies(new ArrayList<Dependency>());
		q.getProjectDependencies().add(d1);
		q.getProjectDependencies().add(d2);
		q.getProjectDependencies().add(d3);
		q.setClassDependencies(new ArrayList<Dependency>());
		q.getClassDependencies().add(d2);
		q.setMethodInvocation("addDocument");
		q.setRefClassInvocation("IndexWriter");
		q.setProjectName("MDEForge");
		q.setTextOffset(1000);
		return q;
	}
}
