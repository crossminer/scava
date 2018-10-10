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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.eclipse.scava.business.IRecommenderManager;
import org.eclipse.scava.business.ISimilarityCalculator;
import org.eclipse.scava.business.dto.Query;
import org.eclipse.scava.business.dto.Recommendation;
import org.eclipse.scava.business.dto.RecommendationItem;
import org.eclipse.scava.business.dto.RecommendationType;
import org.eclipse.scava.business.model.Artifact;
import org.eclipse.scava.business.model.Cluster;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

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
	private static final Logger logger = LoggerFactory.getLogger(RecommenderRestController.class);

//	@RequestMapping(value = "cluster/{sim_method}", produces = "application/json", method = RequestMethod.GET)
//	public List<Cluster> getClusters(
//			@ApiParam(value = "String value which can be Compound, CrossSim, Dependency, Readme, RepoPalCompound or RepoPalCompoundV2.", required = true) @PathVariable("sim_method") String simFunction) {
//		return recommenderManager.getClusters(simFunction);
//	}
	@ApiOperation(value ="This resource is used to retrieve recommended libraries.")
	@RequestMapping(value = "recommended_library", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public @ResponseBody Recommendation getRecommendedLibraries(
			@ApiParam(value = "Query object", required = true) @RequestBody Query query) throws Exception {
		Recommendation r =recommenderManager.getRecommendation(query, RecommendationType.RECOMMENDED_LIBRARY);
		for (RecommendationItem ri : r.getRecommendationItems()) {
			if(ri.getRecommendedLibrary().getLibraryName().equals("org.xerial.snappy:snappy-java")){
				ri.getRecommendedLibrary().setLibraryName("org.xerial.snappy:snappy-java:1.1.7.2");
			}
			if(ri.getRecommendedLibrary().getLibraryName().equals("org.slf4j:slf4j-api")){
				ri.getRecommendedLibrary().setLibraryName("org.slf4j:slf4j-api:1.8.0-beta2");
			}
			if(ri.getRecommendedLibrary().getLibraryName().equals("joda-time:joda-time")){
				ri.getRecommendedLibrary().setLibraryName("joda-time:joda-time:2.10");
			}
			if(ri.getRecommendedLibrary().getLibraryName().equals("com.google.code.gson:gson")){
				ri.getRecommendedLibrary().setLibraryName("com.google.code.gson:gson:2.8.5");
			}
			if(ri.getRecommendedLibrary().getLibraryName().equals("org.apache.zookeeper:zookeeper")){
				ri.getRecommendedLibrary().setLibraryName("org.apache.zookeeper:zookeeper:3.4.13");
			}
			if(ri.getRecommendedLibrary().getLibraryName().equals("org.apache.httpcomponents:httpclient")){
				ri.getRecommendedLibrary().setLibraryName("org.apache.httpcomponents:httpclient:4.5.6");
			}
			if(ri.getRecommendedLibrary().getLibraryName().equals("org.elasticsearch:elasticsearch")){
				ri.getRecommendedLibrary().setLibraryName("org.elasticsearch:elasticsearch:6.4.0");
			}
			if(ri.getRecommendedLibrary().getLibraryName().equals("org.codehaus.jackson:jackson-mapper-asl")){
				ri.getRecommendedLibrary().setLibraryName("org.codehaus.jackson:jackson-mapper-asl:1.9.13");
			}
			if(ri.getRecommendedLibrary().getLibraryName().equals("log4j:log4j")){
				ri.getRecommendedLibrary().setLibraryName("log4j:log4j:1.2.17");
			}
			if(ri.getRecommendedLibrary().getLibraryName().equals("org.slf4j:slf4j-log4j12")){
				ri.getRecommendedLibrary().setLibraryName("org.slf4j:slf4j-log4j12:1.7.25");
			}
			/*
			 * 
org.xerial.snappy:snappy-java:1.1.7.2
org.slf4j:slf4j-api:1.8.0-beta2
joda-time:joda-time:2.10
com.google.code.gson:gson:2.8.5
org.apache.zookeeper:zookeeper:3.4.13
org.apache.httpcomponents:httpclient:4.5.6
org.elasticsearch:elasticsearch:6.4.0
org.codehaus.jackson:jackson-mapper-asl:1.9.13
log4j:log4j:1.2.17
org.slf4j:slf4j-log4j12:1.7.25
			 */

			
		}
		return r;
	}
	
	@ApiOperation(value = "This resource is used to retrieve code patterns from code context.")
	@RequestMapping(value = "recommended_API_call", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public @ResponseBody Recommendation getApiCallRecommendation(
			@ApiParam(value = "Query object", required = true) @RequestBody Query query) throws Exception {
		return recommenderManager.getRecommendation(query, RecommendationType.API_CALL);
	}
	@ApiOperation(value = "This resource is used to retrieve documentation recommendation from code context. NOT YET IMPLEMENTED.")
	@RequestMapping(value = "recommended_API_documentation", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public @ResponseBody Recommendation getApiDocumentationRecommendation(
			@ApiParam(value = "Query object", required = true) @RequestBody Query query) throws Exception {
		return recommenderManager.getRecommendation(query, RecommendationType.API_DOCUMENTATION);
	}

	@ApiOperation(value = "This resource is used to retrieve projects that are similar to a given project.")
	@RequestMapping(value = "similar/p/{id}/m/{sim_method}/n/{num}", produces = "application/json", method = RequestMethod.GET)
	public @ResponseBody List<Artifact> getSimilarProject(
			@ApiParam(value = "results are computed by using the similarity function specified as parameter.  "
					+ "String value which can be Compound, CrossSim, Dependency, CrossRec, Readme, "
					+ "RepoPalCompound or RepoPalCompoundV2.", required = true) @PathVariable("sim_method") String simFunction,
			@ApiParam(value = "This parameter specifies the string ID of a specific artifact.", required = true) @PathVariable("id") String id,
			@ApiParam(value = "Number of expected projects in the results", required = true) @PathVariable("num") int num) {

		return recommenderManager.getSimilarProjects(id, simFunction, num);
	}

	@RequestMapping(value = "query", method = RequestMethod.GET)
	public Query getQuery() {
		Query q = new Query();
		q.setCurrentMethodCode(
				"public static KnowledgeBase readKnowledgeBase(List<RuleResource> resources) {\r\n" + 
			    " KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();\r\n" + 
			    " for (RuleResource res: resources) {\r\n" + 
			    "  try {\r\n" + 
			    "   kbuilder.add(ResourceFactory.newClassPathResource(res.getRuleResourceFile()), res.getResType());\r\n" + 
			    "  } catch (Exception ex) {\r\n" + 
			    "   kbuilder.add(ResourceFactory.newFileResource(res.getRuleResourceFile()), res.getResType());\r\n" + 
			    "  }\r\n" + 
			    " }\r\n" + 
			    " KnowledgeBuilderErrors errors = kbuilder.getErrors();\r\n" + 
			    " if (errors.size() > 0) {\r\n" + 
			    "  for (KnowledgeBuilderError error: errors) {\r\n" + 
			    "   System.err.println(error);\r\n" + 
			    "  }\r\n" + 
			    "  throw new IllegalArgumentException(\"Could not parse knowledge.\");\r\n" + 
			    " }\r\n" + 
			    " KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();\r\n" + 
			    " kbase.addKnowledgePackages(kbuilder.getKnowledgePackages());\r\n" + 
			    " return kbase;\r\n" + 
			    "}");
		return q;
	}

	@RequestMapping(value = "/pattern/{file_name:.+}", method = RequestMethod.GET)
	public void getFile(@PathVariable("file_name") String fileName, HttpServletResponse response) {
		try {
			File file = new ClassPathResource("CLAMS_PATTERN/" + fileName).getFile();
			InputStream is = new FileInputStream(file);
			org.apache.commons.io.IOUtils.copy(is, response.getOutputStream());
			response.flushBuffer();
		} catch (IOException ex) {
			logger.info("Error writing file to output stream. Filename was '{}'", fileName, ex);
			throw new RuntimeException("IOError writing file to output stream");
		}

	}
}
