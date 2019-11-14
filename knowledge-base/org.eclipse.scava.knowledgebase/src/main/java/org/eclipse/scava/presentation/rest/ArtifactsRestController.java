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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

import org.eclipse.scava.business.IRecommenderManager;
import org.eclipse.scava.business.ISimilarityCalculator;
import org.eclipse.scava.business.dto.metrics.MetricsForProject;
import org.eclipse.scava.business.impl.GithubImporter;
import org.eclipse.scava.business.impl.OssmeterImporter;
import org.eclipse.scava.business.integration.ArtifactRepository;
import org.eclipse.scava.business.integration.LibBoostRepository;
import org.eclipse.scava.business.integration.MetricForProjectRepository;
import org.eclipse.scava.business.model.Artifact;
import org.eclipse.scava.business.model.LibBoost;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
	private MetricForProjectRepository m4pRepository;
	@Autowired
	private OssmeterImporter ossmeterImporter;

	@Value("${migration.local.m3.files.path}")
	private String localFile;

	@Autowired
	private GithubImporter importer;

	@ApiImplicitParams({ // FIXME
			@ApiImplicitParam(name = "page", dataType = "integer", paramType = "query", value = "Results page you want to retrieve (0..N)"),
			@ApiImplicitParam(name = "size", dataType = "integer", paramType = "query", value = "Number of records per page."),
			@ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query", value = "Sorting criteria in the format: property(,asc|desc). "
					+ "Default sort order is ascending. " + "Multiple sort criteria are supported.") })
	@ApiOperation(value = "This resource is used to retrieve the list of artifacts analyzed by the CROSSMINER ", response = Iterable.class)
	@RequestMapping(value = "artifacts", produces = { "application/json",
			"application/xml" }, method = RequestMethod.GET)
	public @ResponseBody Page<Artifact> getArtifacts(Pageable pageable) {
		return artifactRepository.findAll(pageable);
	}

	@ApiOperation(value = "Get artifact by id")
	@RequestMapping(value = "/{artifact_id}", produces = { "application/json",
			"application/xml" }, method = RequestMethod.GET)
	public @ResponseBody Artifact getArtifact(@PathVariable("artifact_id") String id) {
		return artifactRepository.findOne(id);
	}

	@ApiOperation(value = "Get metrics by user id")
	@RequestMapping(value = "/m4p/uid//{user_id}", produces = { "application/json",
			"application/xml" }, method = RequestMethod.GET)
	public @ResponseBody List<MetricsForProject> getMetricForProjectByUserId(@PathVariable("user_id") String userId) {
		return m4pRepository.findByUserId(userId);
	}

	@ApiImplicitParams({ // FIXME
			@ApiImplicitParam(name = "page", dataType = "integer", paramType = "query", value = "Results page you want to retrieve (0..N)"),
			@ApiImplicitParam(name = "size", dataType = "integer", paramType = "query", value = "Number of records per page."),
			@ApiImplicitParam(name = "sort", dataType = "string", paramType = "query", value = "Sorting criteria in the format: property(,asc|desc). "
					+ "Default sort order is ascending. " + "Multiple sort criteria are supported.") })
	@ApiOperation(value = "This resource is used to retrieve the list of deplovelor acvtivity metrics", response = Iterable.class)
	@RequestMapping(value = "/m4p/", produces = { "application/json", "application/xml" }, method = RequestMethod.GET)
	public @ResponseBody Page<MetricsForProject> getAllMetricForProject(Pageable pageable) {
		return m4pRepository.findAll(pageable);
	}

	@ApiOperation(value = "Get metrics by projectID id")
	@RequestMapping(value = "/m4p/pid/{project_id}", produces = { "application/json",
			"application/xml" }, method = RequestMethod.GET)
	public @ResponseBody List<MetricsForProject> getMetricForProjectByProjectId(@PathVariable("project_id") String id) {
		return m4pRepository.findByProjectId(id);
	}

//	@ApiOperation(value = "Search artifact to KB")
//	@RequestMapping(value="search/{artifact_query}", produces = "application/json", method = RequestMethod.GET)
//    public @ResponseBody List<Artifact> getProject(@PathVariable("artifact_query") String projectQuery) {
//		return recommenderManager.getArtifactsByQuery(projectQuery);
//    }

	@ApiOperation(value = "Search artifact to KB")
	@RequestMapping(value = "search/{artifact_query}", produces = { "application/json",
			"application/xml" }, method = RequestMethod.GET)
	@ApiImplicitParams({ // FIXME
			@ApiImplicitParam(name = "page", dataType = "integer", paramType = "query", value = "Results page you want to retrieve (0..N)"),
			@ApiImplicitParam(name = "size", dataType = "integer", paramType = "query", value = "Number of records per page."),
			@ApiImplicitParam(name = "sort", dataType = "string", paramType = "query", value = "Sorting criteria in the format: [asc|desc]") })
	public @ResponseBody List<Artifact> getProject(@PathVariable("artifact_query") String projectQuery,
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "size", defaultValue = "10") int size,
			@RequestParam(value = "sort", defaultValue = "asc") String sort) {
		PageRequest pr = new PageRequest(page, size, new Sort(Arrays.asList(
				sort.equalsIgnoreCase("ASC") ? new Order(Direction.ASC, "temp") : new Order(Direction.DESC, "temp"))));
		return recommenderManager.getArtifactsByQuery(projectQuery, pr);
	}

	@ApiOperation(value = "Get artifact by metric platform id")
	@RequestMapping(value = "artifact/mpp/{metricPlatformId}", produces = { "application/json",
			"application/xml" }, method = RequestMethod.GET)
	public @ResponseBody Artifact getProjectByMetricPlatformId(@PathVariable("metricPlatformId") String mppID) {
		return artifactRepository.findOneByMetricPlatformId(mppID);
	}

	@ApiOperation(value = "Add github project to KB. use <owner>--<repo>")
	@RequestMapping(value = "add/{project_name}/{access_token}", produces = { "application/json",
			"application/xml" }, method = RequestMethod.POST)
	public @ResponseBody boolean importGithubProject(@PathVariable("project_name") String projectName,
			@PathVariable("access_token") String access_token) {
		try {
			importer.importProject(projectName.replace("--", "/").replace("%2E", "."), access_token);
			return true;

		} catch (IOException e) {
			return false;
		}
	}

	@ApiOperation(value = "Import projects from the metric provider platform")
	@RequestMapping(value = "add-mpp/", produces = { "application/json",
			"application/xml" }, method = RequestMethod.POST)
	public @ResponseBody boolean importMPPProjects() {
		ossmeterImporter.importAll();
		return true;
	}

	@ApiOperation(value = "Import projects from the metric provider platform from a specific url. The url should be <server_url>:<server_port>")
	@RequestMapping(value = "add-mpp.by-url/", produces = { "application/json",
			"application/xml" }, method = RequestMethod.POST)
	public @ResponseBody boolean importMPPProjectsFromSpecificUrl(@RequestParam("mppUrl") String mppUrl) {
		ossmeterImporter.importAll(mppUrl);
		return true;
	}

	@ApiOperation(value = "Store IDE metrics")
	@RequestMapping(value = "store-metrics", produces = { "application/json",
			"application/xml" }, method = RequestMethod.POST)
	public ResponseEntity<Object> storeIDEMetrics(@RequestBody MetricsForProject metricForProject) {
		try {
			m4pRepository.save(metricForProject);
		} catch (Exception e) {
			new ResponseEntity<Object>(e, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Object>(Boolean.TRUE, HttpStatus.ACCEPTED);
	}

	@ApiOperation(value = "This API stores the lib boost values given as txt file")
	@RequestMapping(value = "/storeLibsBoost/", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<Object> storeLibBoosts(@RequestParam("file") MultipartFile file) {
		try {
			storeLibBoost(file);
			logger.info("LibBoost has been imported");
		} catch (IllegalStateException | IOException e) {
			logger.error(e.getMessage());
			new ResponseEntity<Object>(e, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			logger.error(e.getMessage());
			new ResponseEntity<Object>(e, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Object>(Boolean.TRUE, HttpStatus.ACCEPTED);
	}
	
	@Autowired
	private LibBoostRepository libBoostRepo;

	private void storeLibBoost(MultipartFile multipartFile) throws IOException {
//		String fileName = Paths.get(localFile, multipartFile.getOriginalFilename()).toString();

		try (BufferedReader br = new BufferedReader(new InputStreamReader(multipartFile.getInputStream()))) {
			String line;
			while ((line = br.readLine()) != null) {
				String name = line.replace("/", "");
				if (libBoostRepo.findOneByName(name) == null) {
					libBoostRepo.save(new LibBoost(name));
				}
			}
		}

	}
//	@RequestMapping(value="/gargo", produces = {"application/json", "application/xml"}, method = RequestMethod.GET)
//	public @ResponseBody MetricsForProject temp(){
//		MetricsForProject metric4project = new MetricsForProject();
//		metric4project.setProjectId("DEMO METRIC4PROJECT");
//		List<MetricMilestoneSlice> metricMilestoneSlices = new ArrayList<MetricMilestoneSlice>();
//		MetricMilestoneSlice metricMilestoneSlice = new MetricMilestoneSlice();
//		metricMilestoneSlice.setBounder("DEMO BOUNDER");
//		MetricBoundary metricBoundary = new MetricBoundary();
//		metricBoundary.setBeginDate(new Date());
//		metricBoundary.setEndDate(new Date());
//		List<MetricBoundary> mbs = new ArrayList<MetricBoundary>();
//		mbs.add(metricBoundary);
//		metricMilestoneSlice.setBoundary(mbs);
//		MetricDescriptor descriptor = new MetricDescriptor();
//		Map<String,Double> map = new HashMap<String,Double>();
//		map.put("name", 2.0);
//		descriptor.setItemValuePairs(map);
//		descriptor.setMetricName("DEMO METRICNAME");
//		ArrayList<MetricDescriptor> mds = new ArrayList<>();
//		mds.add(descriptor);
//		metricBoundary.setMetricValues(mds);
//		metricMilestoneSlices.add(metricMilestoneSlice);
//		metric4project.setMetricMilestoneSlice(metricMilestoneSlices);
//		return metric4project ;
//	}

}
