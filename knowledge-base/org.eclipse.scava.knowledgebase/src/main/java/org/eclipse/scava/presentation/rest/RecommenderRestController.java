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
import org.eclipse.scava.business.dto.APIMigrationRequest;
import org.eclipse.scava.business.dto.APIMigrationResponse;
import org.eclipse.scava.business.dto.Query;
import org.eclipse.scava.business.dto.Recommendation;
import org.eclipse.scava.business.dto.RecommendationFeedback;
import org.eclipse.scava.business.dto.RecommendationItem;
import org.eclipse.scava.business.dto.RecommendationType;
import org.eclipse.scava.business.integration.RecommendationFeedbackRepository;
import org.eclipse.scava.business.model.Artifact;
import org.eclipse.scava.business.model.Cluster;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
	private IRecommenderManager recommenderManager;
	private static final Logger logger = LoggerFactory.getLogger(RecommenderRestController.class);

	@RequestMapping(value = "cluster/{sim_method}/{cluster_algo}", produces = {"application/json", "application/xml"}, method = RequestMethod.GET)
	public List<Cluster> getClusters(
			@ApiParam(value = "String value which can be Compound, CrossSim, Dependency, Readme, RepoPalCompound or RepoPalCompoundV2.", required = true) @PathVariable("sim_method") String simFunction,
			@ApiParam(value = "String value which can be Clara, KMeans, or HCLibrary.", required = true) @PathVariable("cluster_algo") String clusterAlgo) {
		return recommenderManager.getClusters(simFunction,clusterAlgo);
	}
	
	@RequestMapping(value = "cluster/{sim_method}/{cluster_algo}/{artifact_id}", produces = {"application/json", "application/xml"}, method = RequestMethod.GET)
	public Cluster getClusterByArtifact(
			@ApiParam(value = "String value which can be Compound, CrossSim, Dependency, Readme, RepoPalCompound or RepoPalCompoundV2.", required = true) @PathVariable("sim_method") String simFunction,
			@ApiParam(value = "String value which can be Clara, KMeans, or HCLibrary.", required = true) @PathVariable("cluster_algo") String clusterAlgo,
			@ApiParam(value = "The id of artifact", required = true) @PathVariable("artifact_id") String artifactId) {
		return recommenderManager.getClusterByArtifact(artifactId, simFunction, clusterAlgo);
	}
	
	
	@ApiOperation(value ="This resource is used to retrieve recommended libraries.")
	@RequestMapping(value = "recommended_library", method = RequestMethod.POST, consumes = "application/json", produces = {"application/json", "application/xml"})
	public @ResponseBody Recommendation getRecommendedLibraries(
			@ApiParam(value = "Query object", required = true) @RequestBody Query query) throws Exception {
		Recommendation r =recommenderManager.getRecommendation(query, RecommendationType.RECOMMENDED_LIBRARY);
		return r;
	}
	
	@ApiOperation(value = "This resource is used to retrieve code patterns from code context.")
	@RequestMapping(value = "recommended_API_call", method = RequestMethod.POST, consumes = "application/json", produces = {"application/json", "application/xml"})
	public @ResponseBody Recommendation getApiCallRecommendation(
			@ApiParam(value = "Query object", required = true) @RequestBody Query query) throws Exception {
		return recommenderManager.getRecommendation(query, RecommendationType.API_CALL);
	}
	@ApiOperation(value = "This resource is used to retrieve documentation recommendation from code context. NOT YET IMPLEMENTED.")
	@RequestMapping(value = "recommended_API_documentation", method = RequestMethod.POST, consumes = "application/json", produces = {"application/json", "application/xml"})
	public @ResponseBody Recommendation getApiDocumentationRecommendation(
			@ApiParam(value = "Query object", required = true) @RequestBody Query query) throws Exception {
		return recommenderManager.getRecommendation(query, RecommendationType.API_DOCUMENTATION);
	}

	@ApiOperation(value = "This resource is used to retrieve projects that are similar to a given project.")
	@RequestMapping(value = "similar/p/{id}/m/{sim_method}/n/{num}", produces = {"application/json", "application/xml"}, method = RequestMethod.GET)
	public @ResponseBody List<Artifact> getSimilarProject(
			@ApiParam(value = "results are computed by using the similarity function specified as parameter.  "
					+ "String value which can be Compound, CrossSim, Dependency, CrossRec, Readme, "
					+ "RepoPalCompound or RepoPalCompoundV2.", required = true) @PathVariable("sim_method") String simFunction,
			@ApiParam(value = "This parameter specifies the string ID of a specific artifact.", required = true) @PathVariable("id") String id,
			@ApiParam(value = "Number of expected projects in the results", required = true) @PathVariable("num") int num) {

		return recommenderManager.getSimilarProjects(id, simFunction, num);
	}
	
	@ApiOperation(value = "This resource is used to retrieve recommendation about the next API function calls. It integrates FOCUS technology.")
	@RequestMapping(value = "focus/", method = RequestMethod.POST, consumes = "application/json", produces = {"application/json", "application/xml"})
	public @ResponseBody Recommendation getNextApiCallsRecommendation(
			@ApiParam(value = "Query object", required = true) @RequestBody Query query) throws Exception {
		return recommenderManager.getRecommendation(query, RecommendationType.FOCUS);
	}
	
	@ApiOperation(value = "This resource lists of versions for each dependecies")
	@RequestMapping(value = "version/", method = RequestMethod.POST, consumes = "application/json", produces = {"application/json", "application/xml"})
	public @ResponseBody Recommendation getVersions(
			@ApiParam(value = "Query object", required = true) @RequestBody Query query) throws Exception {
		return recommenderManager.getRecommendation(query, RecommendationType.VERSION);
	}
	@Autowired
	private RecommendationFeedbackRepository recFedRepo;
	@ApiOperation(value = "This resource stores the recommendation feedback")
	@RequestMapping(value = "recommendation-feedback/", method = RequestMethod.POST, consumes = "application/json", produces = {"application/json", "application/xml"})
	public @ResponseBody boolean storeRecommendationFeedback(
			@ApiParam(value = "Query object", required = true) @RequestBody RecommendationFeedback recFed) throws Exception {
		try {
			recFed.equals(recFed);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	@RequestMapping(value = "code-request-example", method = RequestMethod.GET)
	public Query getQuery() {
		Query q = new Query();
		q.setCurrentMethodCode(
				"package com.mkyong.core;\n" + 
				"\n" + 
				"import java.net.UnknownHostException;\n" + 
				"import java.util.Date;\n" + 
				"import com.mongodb.BasicDBObject;\n" + 
				"import com.mongodb.DB;\n" + 
				"import com.mongodb.DBCollection;\n" + 
				"import com.mongodb.DBCursor;\n" + 
				"import com.mongodb.MongoClient;\n" + 
				"import com.mongodb.MongoException;\n" + 
				"\n" + 
				"/**\n" + 
				" * Java + MongoDB Hello world Example\n" + 
				" * \n" + 
				" */\n" + 
				"public class App {\n" + 
				"  public static void main(String[] args) {\n" + 
				"\n" + 
				"    try {\n" + 
				"\n" + 
				"	/**** Connect to MongoDB ****/\n" + 
				"	// Since 2.10.0, uses MongoClient\n" + 
				"	MongoClient mongo = new MongoClient(\"localhost\", 27017);\n" + 
				"\n" + 
				"	/**** Get database ****/\n" + 
				"	// if database doesn't exists, MongoDB will create it for you\n" + 
				"	DB db = mongo.getDB(\"testdb\");\n" + 
				"\n" + 
				"	/**** Get collection / table from 'testdb' ****/\n" + 
				"	// if collection doesn't exists, MongoDB will create it for you\n" + 
				"	DBCollection table = db.getCollection(\"user\");\n" + 
				"\n" + 
				"	/**** Insert ****/\n" + 
				"	// create a document to store key and value\n" + 
				"	BasicDBObject document = new BasicDBObject();\n" + 
				"	document.put(\"name\", \"mkyong\");\n" + 
				"	document.put(\"age\", 30);\n" + 
				"	document.put(\"createdDate\", new Date());\n" + 
				"	table.insert(document);\n" + 
				"\n" + 
				"	/**** Find and display ****/\n" + 
				"	BasicDBObject searchQuery = new BasicDBObject();\n" + 
				"}}}");
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
	
	@ApiOperation(value = "API migration recommendations")
    @RequestMapping(value = "/api-migration", method = RequestMethod.POST, consumes = "application/json", produces = {"application/json", "application/xml"})
    public @ResponseBody APIMigrationResponse getApiMigration(
                    @ApiParam(value = "it should contains the list of method declarations and the cordinates of both old and new versions", required = true)
                    @RequestBody APIMigrationRequest request) {
            APIMigrationResponse result = new APIMigrationResponse();
            return result;
    }
}
