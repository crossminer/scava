/*******************************************************************************
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.presentation.rest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.eclipse.aether.artifact.Artifact;
import org.eclipse.scava.business.dto.Recommendation;
import org.eclipse.scava.business.impl.SORecommender;
import org.eclipse.scava.business.impl.APIMigration.MigrationService;
import org.eclipse.scava.business.model.migration.Delta;
import org.eclipse.scava.business.model.migration.DetectionResult;
import org.maracas.data.Detection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.Lists;

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
	@Value("${migration.local.m3.files.path}")
	private String localM3Files;
	private static final Logger logger = LoggerFactory.getLogger(ApiMigrationRestController.class);

	@ApiOperation(value = "This API returns all client pairs that migrate from coordV1 to coordV2", response = Iterable.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "coordV1", value = "maven cooridnate of lib v1 (groupId:artifactId:version)", required = true, dataType = "string", paramType = "path"),
			@ApiImplicitParam(name = "coordV2", value = "maven cooridnate of lib v2 (groupId:artifactId:version)", required = false, dataType = "string", paramType = "path") })
	@RequestMapping(value = "/{coordV1}/{coordV2:.+}", produces = { "application/json",
			"application/xml" }, method = RequestMethod.GET)
	public @ResponseBody MigrationInfo getMigrationPairs(@PathVariable("coordV1") String coordV1,
			@PathVariable("coordV2") String coordV2) {
		return migrationService.getMigrationPairs(coordV1, coordV2);
	}
	
	@ApiOperation(value = "This API returns the migration matrix of coord (groupid:artifactid)", response = Iterable.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "coordV1", value = "maven cooridnate of lib v2 (groupId:artifactId:version)", required = false, dataType = "string", paramType = "path") })
	@RequestMapping(value = "/matrix/{coordV1:.+}", produces = { "application/json",
			"application/xml" }, method = RequestMethod.GET)
	public @ResponseBody List<MigrationInfo> getMigrationPairs(@PathVariable("coordV1") String coordV1) {
		return migrationService.getMigrationMatrix(coordV1);
	}
	
	@ApiOperation(value = "This API returns the delta between coordV1 and coordV2", response = Iterable.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "coordV1", value = "maven cooridnate of lib v1 (groupId:artifactId:version)", required = true, dataType = "string", paramType = "path"),
			@ApiImplicitParam(name = "coordV2", value = "maven cooridnate of lib v2 (groupId:artifactId:version)", required = false, dataType = "string", paramType = "path") })
	@RequestMapping(value = "/delta/{coordV1}/{coordV2:.+}", produces = { "application/json",
			"application/xml" }, method = RequestMethod.GET)
	public @ResponseBody Delta getDelta(@PathVariable("coordV1") String coordV1,
			@PathVariable("coordV2") String coordV2) throws Exception {
		try {
			return migrationService.storeDelta(coordV1, coordV2);
		} catch (Exception e) {
			logger.error("error in computing delta");
			throw e;
		}
	}

	@ApiOperation(value = "This API returns all client that use a specific dependency coordV1", response = Iterable.class)
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
	
	@ApiOperation(value = "This API returns detections", response = Iterable.class)
	@RequestMapping(value = "/detection/{coordV1}/{coordV2:.+}", method = RequestMethod.POST)
	public @ResponseBody List<Detection> getDetection(
			@PathVariable("coordV1") String coordV1,
			@PathVariable("coordV2") String coordV2,
			@RequestParam("file") MultipartFile file) {
		List<Detection> v = Lists.newArrayList();
		try {
			String fileName = getLocalFilePath(file);
			v = migrationService.getDetections(coordV1, coordV2, fileName);
			return v;
		} catch (IllegalStateException | IOException e) {
			logger.error(e.getMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return null;
	}
	
	@ApiOperation(value = "This API returns recommendation", response = Iterable.class)
	@RequestMapping(value = "/similar_migration_snippet/{coordV1}/{coordV2:.+}", method = RequestMethod.POST,
			produces = { "application/json", "application/xml" })
	public @ResponseBody MultiValueMap<String, String> getRecommendationSnippet(
			@PathVariable("coordV1") String coordV1,
			@PathVariable("coordV2") String coordV2,
			@RequestParam("file") MultipartFile file) {
		
		try {
			String fileName = getLocalFilePath(file);
			MultiValueMap<String, String> v = migrationService.recommendsSnippet(coordV1, coordV2, fileName);
			return v;
		} catch (IllegalStateException | IOException e) {
			logger.error(e.getMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return null;
	}
	
	@ApiOperation(value = "This API returns recommendation", response = Iterable.class)
	@RequestMapping(value = "/similar_migration_location/{coordV1}/{coordV2:.+}", method = RequestMethod.POST)
	public @ResponseBody List<DetectionResult> getRecommendation(
			@PathVariable("coordV1") String coordV1,
			@PathVariable("coordV2") String coordV2,
			@RequestParam("file") MultipartFile file) {
		
		try {
			String fileName = getLocalFilePath(file);
			List<DetectionResult> v = migrationService.getDetecionResults(coordV1, coordV2, fileName);
			return v;
		} catch (IllegalStateException | IOException e) {
			logger.error(e.getMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return null;
	}
	
	@ApiOperation(value = "This API returns the m3 model of the input jar file", response = Iterable.class)
	@RequestMapping(value = "/M3Model", method = RequestMethod.POST, 
		produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public @ResponseBody byte[] getM3Model(
			@RequestParam("file") MultipartFile file) {
		
		try {
			String fileName = getLocalFilePath(file);
			String v = migrationService.getM3modelFromJar(fileName);
			InputStream in = new FileInputStream(v);
			return IOUtils.toByteArray(in);
		} catch (IllegalStateException | IOException e) {
			logger.error(e.getMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return null;
	}
	
	
	@ApiOperation(value = "This API returns the snippet (pointed by @clientLoc) of code of @coordV1", response = Iterable.class)
	@RequestMapping(value = "client-code", produces = { "application/json",
			"application/xml" }, method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<String> getCode(@RequestParam("coordV1") String coordV1,
			@RequestParam("clientLoc") String coordV2) {
		try {
			String s = migrationService.getCode(coordV1, coordV2);
			return new ResponseEntity<String>(s, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	private String getLocalFilePath(MultipartFile multipartFile) throws IOException {
			String fileName = Paths.get(localM3Files, multipartFile.getOriginalFilename()).toString();
			byte[] bytes = multipartFile.getBytes();
		    java.nio.file.Path path = Paths.get(fileName);
		    Files.write(path, bytes);
		    return fileName;
	}
}
