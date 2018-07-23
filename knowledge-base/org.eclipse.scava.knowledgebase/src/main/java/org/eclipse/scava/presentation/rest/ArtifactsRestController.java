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

import java.io.IOException;
import java.util.List;

import org.eclipse.scava.business.IRecommenderManager;
import org.eclipse.scava.business.ISimilarityCalculator;
import org.eclipse.scava.business.impl.GithubImporter;
import org.eclipse.scava.business.integration.ArtifactRepository;
import org.eclipse.scava.business.model.Artifact;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * @author Juri Di Rocco
 *
 */
@RestController
@RequestMapping("/api/artifacts")


public class ArtifactsRestController {
	@Autowired
	@Qualifier("Dependency")
	private ISimilarityCalculator dependency;
	@Autowired
	private IRecommenderManager recommenderManager;
	@Autowired
	private ArtifactRepository artifactRepository;
	private static final Logger logger = LoggerFactory.getLogger(ArtifactsRestController.class);
	
	@Autowired
	private GithubImporter importer;
	@ApiImplicitParams({	//FIXME
		@ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
						  value = "Results page you want to retrieve (0..N)"),
		@ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
						  value = "Number of records per page."),
		@ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
						  value = "Sorting criteria in the format: property(,asc|desc). "
								  + "Default sort order is ascending. "
								  + "Multiple sort criteria are supported.")
	})
	@ApiOperation(value = "This resource is used to retrieve the list of artifacts analyzed by the CROSSMINER ", response = Iterable.class)
	@RequestMapping(value="artifacts", produces = "application/json", method = RequestMethod.GET)
    public @ResponseBody Page<Artifact> getArtifacts(Pageable pageable) {
		return artifactRepository.findAll(pageable);
    }
	
	@ApiOperation(value = "Get artifact by id", response = Iterable.class)
	@RequestMapping(value="/{artifact_id}", produces = "application/json", method = RequestMethod.GET)
    public @ResponseBody Artifact getArtifact(@PathVariable("artifact_id") String id) {
		return artifactRepository.findOne(id);
    }
	
	
//	@ApiOperation(value = "Search artifact to KB")
//	@RequestMapping(value="search/{artifact_query}", produces = "application/json", method = RequestMethod.GET)
//    public @ResponseBody List<Artifact> getProject(@PathVariable("artifact_query") String projectQuery) {
//		return recommenderManager.getArtifactsByQuery(projectQuery);
//    }
	@ApiImplicitParams({	//FIXME
		@ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
						  value = "Results page you want to retrieve (0..N)"),
		@ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
						  value = "Number of records per page."),
		@ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
						  value = "Sorting criteria in the format: property(,asc|desc). "
								  + "Default sort order is ascending. "
								  + "Multiple sort criteria are supported.")
	})
	@ApiOperation(value = "Search artifact to KB")
	@RequestMapping(value="search/{artifact_query}", produces = "application/json", method = RequestMethod.GET)
    public @ResponseBody List<Artifact> getProject(@PathVariable("artifact_query") String projectQuery,
    		Pageable page) {
		return recommenderManager.getArtifactsByQuery(projectQuery, page);
    }
	
	@ApiOperation(value = "Add github project to KB")
	@RequestMapping(value="add/{project_name}", produces = "application/json", method = RequestMethod.POST)
    public @ResponseBody boolean importGithubProject(@PathVariable("project_name") String projectName) {
		try {
			Artifact art = importer.importProject(projectName.replace("--", "/").replace("%2E", "."));
			return true;
			
		} catch (IOException e) {
			return false;
		}
    }

}
