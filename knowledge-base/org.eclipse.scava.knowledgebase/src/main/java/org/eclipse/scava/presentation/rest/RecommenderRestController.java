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

import java.time.LocalDateTime;
import java.util.List;

import org.eclipse.scava.business.IRecommenderManager;
import org.eclipse.scava.business.dto.Query;
import org.eclipse.scava.business.dto.Recommendation;
import org.eclipse.scava.business.dto.RecommendationFeedback;
import org.eclipse.scava.business.dto.RecommendationType;
import org.eclipse.scava.business.impl.SimilarityManager;
import org.eclipse.scava.business.integration.RecommendationFeedbackRepository;
import org.eclipse.scava.business.model.Artifact;
import org.eclipse.scava.business.model.Cluster;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
	@Autowired
	private RecommendationFeedbackRepository recommendationFeedbackRepo;

	@Autowired
	private SimilarityManager simManager;
	
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
	@ApiOperation(value = "This resource is used to retrieve documentation recommendation from code context.")
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
	

	@ApiOperation(value = "This resource lists plugin versions for each dependecies")
	@RequestMapping(value = "version/", method = RequestMethod.POST, consumes = "application/json", produces = {"application/json", "application/xml"})
	public @ResponseBody Recommendation getVersions(
			@ApiParam(value = "Query object", required = true) @RequestBody Query query) throws Exception {
		return recommenderManager.getRecommendation(query, RecommendationType.VERSION);
		
	}
	@ApiOperation(value = "This resource stores the recommendation feedback")
	@RequestMapping(value = "recommendation-feedback/", method = RequestMethod.POST, consumes = "application/json", produces = {"application/json", "application/xml"})
	public @ResponseBody boolean storeRecommendationFeedback(
			@ApiParam(value = "Query object", required = true) @RequestBody RecommendationFeedback recFed) throws Exception {
		try {
			recommendationFeedbackRepo.save(recFed);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	@ApiOperation(value = "This resource is used to retrieve recommendation about discussions and code snippets for C code using ElasticSearch Indexes.")
	@RequestMapping(value = "crossindex-c/", method = RequestMethod.POST, consumes = "application/json", produces = {"application/json", "application/xml"})
	public @ResponseBody Recommendation getCROSSIndexCRecommendation(
			@ApiParam(value = "Query object", required = true) @RequestBody Query query) throws Exception {
		return recommenderManager.getRecommendation(query, RecommendationType.CROSSIndex_C);
	}
	
	@ApiOperation(value = "This resource is used to retrieve recommendation about discussions and code snippets for Java code using ElasticSearch Indexes.")
	@RequestMapping(value = "crossindex-java/", method = RequestMethod.POST, consumes = "application/json", produces = {"application/json", "application/xml"})
	public @ResponseBody Recommendation getCROSSIndexJavaRecommendation(
			@ApiParam(value = "Query object", required = true) @RequestBody Query query) throws Exception {
		return recommenderManager.getRecommendation(query, RecommendationType.CROSSIndex_JAVA);
	}
	
	@ApiOperation(value = "This resource is used to retrieve recommendation about discussions and code snippets for JavaScript code using ElasticSearch Indexes.")
	@RequestMapping(value = "crossindex-js/", method = RequestMethod.POST, consumes = "application/json", produces = {"application/json", "application/xml"})
	public @ResponseBody Recommendation getCROSSIndexJSRecommendation(
			@ApiParam(value = "Query object", required = true) @RequestBody Query query) throws Exception {
		return recommenderManager.getRecommendation(query, RecommendationType.CROSSIndex_JAVASCRIPT);
	}
	
	@ApiOperation(value = "This resource is used to retrieve recommendation about discussions and code snippets for PHP code using ElasticSearch Indexes.")
	@RequestMapping(value = "crossindex-php/", method = RequestMethod.POST, consumes = "application/json", produces = {"application/json", "application/xml"})
	public @ResponseBody Recommendation getCROSSIndexPHPRecommendation(
			@ApiParam(value = "Query object", required = true) @RequestBody Query query) throws Exception {
		return recommenderManager.getRecommendation(query, RecommendationType.CROSSIndex_PHP);
	}
	
	@ApiOperation(value = "This resource forces tha KB to compute all similarity matrixes")
	@RequestMapping(value = "force-distance-matrices-computation", produces = {"application/json", "application/xml"}, method = RequestMethod.GET)
	public void forceDistanceMatricesComputation() {
		logger.info("Before async method: {}", LocalDateTime.now());
		simManager.storeSimilarityDistances();
		logger.info("After async method: {}", LocalDateTime.now());
	}
	
//	@RequestMapping(value = "code-request-example", method = RequestMethod.GET)
//	public Query getQuery() {
//		Query q = new Query();
//		q.setCurrentMethodCode(
//				  "package camelinaction;" + 
//				  "import org.apache.camel.CamelContext;" + 
//				  "import org.apache.camel.builder.RouteBuilder;" + 
//				  "import org.apache.camel.impl.DefaultCamelContext;" + 
//				  "public class FilePrinter{" + 
//				  "	public static void main (String[] args) throws Exception{" + 
//				  "		CamelContext context =  new DefaultCamelContext();" + 
//				  "		context.addRoutes(new RouteBuilder(){" + 
//				  "			public void configure(){}" + 
//				  "		});" + 
//				  "	}" + 
//				  "}" 
//				);
////				"public KieContainer kieContainer() throws IOException {\n" + 
////				"    KieRepository kieRepository = getKieServices().getRepository();\n" + 
////				" \n" + 
////				"    kieRepository.addKieModule(new KieModule() {\n" + 
////				"        public ReleaseId getReleaseId() {\n" + 
////				"            return kieRepository.getDefaultReleaseId();\n" + 
////				"        }\n" + 
////				"    });\n" + 
////				" \n" + 
////				"    KieBuilder kieBuilder = getKieServices()\n" + 
////				"      .newKieBuilder(kieFileSystem())\n" + 
////				"      .buildAll();\n" + 
////				"     \n" + 
////				" \n" + 
////				"    return getKieServices().newKieContainer(kieRepository.getDefaultReleaseId());\n" + 
////				"}");
//		return q;
//	}
////	
//	@Autowired 
//	private DataReader dr;
////
//	@RequestMapping(value = "focus-example", method = RequestMethod.GET)
//	private List<MethodDeclaration> jj() {
//		Artifact artifacts = dr.readArtifactFromFile("g_activation-1.1.jar.txt");
//		logger.info(artifacts.getMethodDeclarations().get(4).getName());
//        return artifacts.getMethodDeclarations();
//		
//	}
}
