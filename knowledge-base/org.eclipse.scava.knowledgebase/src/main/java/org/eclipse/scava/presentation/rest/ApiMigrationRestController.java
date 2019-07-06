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

import org.apache.commons.compress.utils.Lists;
import org.eclipse.aether.artifact.Artifact;
import org.eclipse.scava.business.dto.Recommendation;
import org.eclipse.scava.business.impl.SORecommender;
import org.eclipse.scava.business.impl.APIMigration.MigrationService;
import org.maracas.data.Detection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import nl.cwi.swat.aethereal.MigrationInfo;

/**
 * @author Juri Di Rocco
 *
 */
@RestController
@RequestMapping("/api/api-migration/")

public class ApiMigrationRestController {

	@Autowired
	private MigrationService migrationService;
	@Autowired
	private SORecommender soRecommender;

	@ApiOperation(value = "This API returns all client pairs that migrate from coordV2 to coordV2", response = Iterable.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "coordV1", value = "maven cooridnate of lib v1 (groupId:artifactId:version)", required = true, dataType = "string", paramType = "path"),
			@ApiImplicitParam(name = "coordV2", value = "maven cooridnate of lib v2 (groupId:artifactId:version)", required = false, dataType = "string", paramType = "path") })
	@RequestMapping(value = "/{coordV1}/{coordV2:.+}", produces = { "application/json",
			"application/xml" }, method = RequestMethod.GET)
	public @ResponseBody MigrationInfo getMigrationPairs(@PathVariable("coordV1") String coordV1,
			@PathVariable("coordV2") String coordV2) {
		return migrationService.getMigrationPairs(coordV1, coordV2);
	}

	@ApiOperation(value = "This API returns all client pairs that migrate from coordV2 to coordV2", response = Iterable.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "coordV1", value = "maven cooridnate of lib v1 (groupId:artifactId:version)", required = true, dataType = "string", paramType = "path") })
	@RequestMapping(value = "/{coordV1}", produces = { "application/json",
			"application/xml" }, method = RequestMethod.GET)
	public @ResponseBody List<Artifact> getClients(@PathVariable("coordV1") String coordV1) {
		return migrationService.getClients(coordV1);
	}

	@ApiOperation(value = "This API returns stack overflow posts related to the version parameters", response = Iterable.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "coordV1", value = "maven cooridnate of lib v1 (groupId:artifactId:version)", required = true, dataType = "string", paramType = "path"),
			@ApiImplicitParam(name = "coordV2", value = "maven cooridnate of lib v2 (groupId:artifactId:version)", required = false, dataType = "string", paramType = "path") })
	@RequestMapping(value = "/sorec/{coordV1}/{coordV2:.+}", produces = { "application/json",
			"application/xml" }, method = RequestMethod.GET)
	public @ResponseBody Recommendation getDocumentation(@PathVariable("coordV1") String coordV1,
			@PathVariable("coordV2") String coordV2) {
		return soRecommender.getVersionsSORecommendations(coordV1, coordV2);
	}
	@ApiOperation(value = "This API returns stack overflow posts related to the version parameters", response = Iterable.class)
	
	@RequestMapping(value = "/detection/{coordV1}/{coordV2:.+}", method = RequestMethod.POST
			)
	public @ResponseBody List<Detection> getDetection(
			@PathVariable("coordV1") String coordV1,
			@PathVariable("coordV2") String coordV2,
			@RequestParam("file") MultipartFile file) {
		List<Detection> v = Lists.newArrayList();
		//v.add(new Detection(file.getName(),  coordV1, coordV2,Type.ADDED,  0.2);
		return v;
	}
	
}
