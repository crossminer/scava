/*******************************************************************************
 * Copyright (c) 2019 Edge Hill University
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.nlp.tools.license;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;

import org.eclipse.scava.platform.logging.OssmeterLogger;

class LicenseAnalyserSingleton {

	private static LicenseAnalyserSingleton singleton = new LicenseAnalyserSingleton();

	protected OssmeterLogger logger;
	// DIRECTORIES FOR MODELS
	// Group
	private String groupModelPath = "model/LicenseGroup_LanguageModel_ngram_3.model";

	// Single
	private String singleModelPath = "model/LicenseIndividual_LanguageModel_ngram_3.model";
	

	// Headers
	private String headerModelPath = "model/LicenseHeaders_LanguageModel_ngram_3.model";
	private String headerStatsPath = "stats/LicenseHeaders_ngram_3.stats";

	private String licenseHierarchyPath = "hierarchy/LicenseGroupsSorted_3_ngrams.sorted";

	// MODELS
	private Map<String, Map<String, Double>> groupModel, singleModel, headerModel;
	private Map<String, Integer> headerStats;
	private Map<String, List<License>> licenseHierarchy;

	private LicenseAnalyserSingleton() {
		logger = (OssmeterLogger) OssmeterLogger.getLogger("nlp.tools.license");

		// load hierarchy
		try {
			this.licenseHierarchy = loadHierarchy(licenseHierarchyPath);
			logger.info(licenseHierarchyPath + " has been sucessfully loaded");
		} catch (ClassNotFoundException | IOException e) {
			logger.error("Error while loading the license hierarchy: \n", e);
			e.printStackTrace();
		}

		// load models
		try {
			this.groupModel = loadModel(groupModelPath);
			logger.info(groupModelPath + " has been sucessfully loaded");
		} catch (ClassNotFoundException | IOException e) {
			logger.error("Error while loading the group model: \n", e);
			e.printStackTrace();
		}

		try {
			this.singleModel = loadModel(singleModelPath);
			logger.info(singleModelPath + " has been sucessfully loaded");
		} catch (ClassNotFoundException | IOException e) {
			logger.error("Error while loading the single model: \n", e);
			e.printStackTrace();
		}

		try {
			this.headerModel = loadModel(headerModelPath);
			logger.info(headerModelPath + " has been sucessfully loaded");
		} catch (ClassNotFoundException | IOException e) {
			logger.error("Error while loading the header model: \n", e);
			e.printStackTrace();
		}

		// loading stats
	
		try {
			this.headerStats = loadStats(headerStatsPath);
			logger.info(headerStatsPath + " has been sucessfully loaded");
		} catch (ClassNotFoundException | IOException e) {
			logger.error("Error while loading the header stats: \n", e);
			e.printStackTrace();
		}

		logger.info("I'm done loading the models that I need...");
	}

	@SuppressWarnings("unchecked")
	private Map<String, Map<String, Double>> loadModel(String directory) throws ClassNotFoundException, IOException {

		Map<String, Map<String, Double>> model = null;
		ClassLoader cl = getClass().getClassLoader();
		InputStream resource = cl.getResourceAsStream(directory);

		if (resource == null) {

			String path = getClass().getProtectionDomain().getCodeSource().getLocation().getFile();

			if (path.endsWith("bin/"))

				path = path.substring(0, path.lastIndexOf("bin/"));

			if (path.endsWith("target/classes/"))

				path = path.substring(0, path.lastIndexOf("target/classes/"));

			File file = new File(path + directory);

			if (!Files.exists(file.toPath()))

				throw new FileNotFoundException("The file " + directory + " has not been found");

			else
				resource = new FileInputStream(file);

		}

		ObjectInputStream ois = new ObjectInputStream(resource);
		model = (Map<String, Map<String, Double>>) ois.readObject();
		ois.close();
		resource.close();

		return model;
		// this.groupModel = model;

	}

	@SuppressWarnings("unchecked")
	private Map<String, Integer> loadStats(String directory) throws IOException, ClassNotFoundException {

		Map<String, Integer> stats = null;

		ClassLoader cl = getClass().getClassLoader();
		InputStream resource = cl.getResourceAsStream(directory);

		if (resource == null) {

			String path = getClass().getProtectionDomain().getCodeSource().getLocation().getFile();

			if (path.endsWith("bin/"))

				path = path.substring(0, path.lastIndexOf("bin/"));

			if (path.endsWith("target/classes/"))

				path = path.substring(0, path.lastIndexOf("target/classes/"));

			File file = new File(path + directory);

			if (!Files.exists(file.toPath()))

				throw new FileNotFoundException("The file " + directory + " has not been found");

			else
				resource = new FileInputStream(file);

		}

		ObjectInputStream ois = new ObjectInputStream(resource);
		stats = (Map<String, Integer>) ois.readObject();

		ois.close();
		resource.close();

		return stats;
	}

	@SuppressWarnings("unchecked")
	private Map<String, List<License>> loadHierarchy(String directory) throws IOException, ClassNotFoundException {

		Map<String, List<License>> hierarchy = null;

		ClassLoader cl = getClass().getClassLoader();
		InputStream resource = cl.getResourceAsStream(directory);

		if (resource == null) {

			String path = getClass().getProtectionDomain().getCodeSource().getLocation().getFile();

			if (path.endsWith("bin/"))

				path = path.substring(0, path.lastIndexOf("bin/"));

			if (path.endsWith("target/classes/"))

				path = path.substring(0, path.lastIndexOf("target/classes/"));

			File file = new File(path + directory);

			if (!Files.exists(file.toPath()))

				throw new FileNotFoundException("The file " + directory + " has not been found");

			else
				resource = new FileInputStream(file);

		}

		ObjectInputStream ois = new ObjectInputStream(resource);
		hierarchy = (Map<String, List<License>>) ois.readObject();

		ois.close();
		resource.close();

		return hierarchy;
	}

	public static LicenseAnalyserSingleton getInstance() {

		return singleton;
	}

	// GROUP
	public Map<String, Map<String, Double>> getGroupLicenseModel() {

		return groupModel;
	}

	

	// SINGLE
	public Map<String, Map<String, Double>> getIndividualLicenseModel() {

		return singleModel;
	}



	// HEADERS
	public Map<String, Map<String, Double>> getLicenseHeaderModel() {

		return headerModel;
	}

	public Map<String, Integer> getLiceseHeaderStats() {

		return headerStats;
	}

	// HIERARCHY
	public Map<String, List<License>> getLicenseHierarchy() {

		return licenseHierarchy;
	}

}
