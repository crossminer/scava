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

import java.util.List;

import org.eclipse.scava.business.IRecommenderManager;
import org.eclipse.scava.business.ISimilarityCalculator;
import org.eclipse.scava.business.integration.ArtifactRepository;
import org.eclipse.scava.business.model.Artifact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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
	
	@ApiOperation(value = "This resource is used to retrieve the list of artifacts analyzed by the CROSSMINER ", response = Iterable.class)
	@RequestMapping(value="artifacts", produces = "application/json", method = RequestMethod.GET)
    public @ResponseBody Page<Artifact> getArtifacts(Pageable pageable) {
		return artifactRepository.findAll(pageable);
    }
	
	
	@ApiOperation(value = "Search artifact to KB")
	@RequestMapping(value="search/{artifact_query}", produces = "application/json", method = RequestMethod.GET)
    public @ResponseBody List<Artifact> getProject(@PathVariable("artifact_query") String projectQuery) {
		return recommenderManager.getArtifactsByQuery(projectQuery);
    }
	

}
