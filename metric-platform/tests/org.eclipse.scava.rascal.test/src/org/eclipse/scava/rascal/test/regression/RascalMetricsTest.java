/*********************************************************************
* Copyright (c) 2018 Centrum Wiskunde & Informatica
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.scava.rascal.test.regression;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.Properties;

import org.bson.BSONDecoder;
import org.bson.BSONObject;
import org.bson.BasicBSONDecoder;
import org.eclipse.scava.platform.Configuration;
import org.eclipse.scava.platform.Platform;
import org.eclipse.scava.platform.analysis.data.model.Worker;
import org.eclipse.scava.platform.osgi.analysis.ProjectAnalyser;
import org.eclipse.scava.rascal.test.ProjectCreationUtil;
import org.eclipse.scava.repository.model.Project;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONCompare;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.JSONCompareResult;

import com.googlecode.pongo.runtime.PongoFactory;
import com.googlecode.pongo.runtime.osgi.OsgiPongoFactoryContributor;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.util.JSON;

public class RascalMetricsTest {

	private static final String PROJECTS = "test_projects";
	private static final String PROJECT = "test_project";
	private static final String NAME_SUFFIX = "_name";
	private static final String URL_SUFFIX = "_url";
	private static final String TEST_SUFFIX = "test_suffix";
	
	private static Platform platform;
	private static Mongo mongo;
	private static String[] dbs;
	
	private static String WORKER_ID;	
	
	@Before
	public void setUp() throws Exception {
		mongo = new Mongo();
		PongoFactory.getInstance().getContributors().add(new OsgiPongoFactoryContributor());
		platform = new Platform(mongo);
		
		WORKER_ID = Configuration.getInstance().getSlaveIdentifier();
		// Register Worker
		platform.getAnalysisRepositoryManager().getWorkerService().registerWorker(WORKER_ID);

		//Reading test properties
		Properties prop = new Properties();
		prop.load(this.getClass().getResourceAsStream("/config/local.properties"));

		System.out.println("Creating test projects");
		int projects = Integer.parseInt(prop.getProperty(PROJECTS));
		dbs = new String[projects];

		for (int i = 0; i < projects; i++) {
			String db = prop.getProperty(PROJECT + i + NAME_SUFFIX) + prop.getProperty(TEST_SUFFIX);
			String url = prop.getProperty(PROJECT + i + URL_SUFFIX);
			dbs[i] = db;

			createDBCollectionsFromRepo(db, url);
		}

		System.out.println("Ending test projects creation");
	}

	private void createDBCollectionsFromRepo(String db, String url) throws ParseException {
		Project project = ProjectCreationUtil.createGitProject(db, url);
		platform.getProjectRepositoryManager().getProjectRepository().getProjects().add(project);
		platform.getProjectRepositoryManager().getProjectRepository().getProjects().sync();	

		System.out.println("Executing test project " + project.getName());
//		ProjectExecutor executor = new ProjectExecutor(platform, project);
//		//Compute metrics only for 9 dates
//		Date dStart = executor.getLastExecutedDate();
//		Date dEnd = (new Date(dStart.toString())).addDays(10);
//		executor.executeProject(dStart, dEnd);
		
		String analysisTaskId = platform.getAnalysisRepositoryManager().getSchedulingService().getOlderPendingAnalysiTask();
		if (analysisTaskId != null) {
			platform.getAnalysisRepositoryManager().getWorkerService().assignTask(analysisTaskId,WORKER_ID);
			ProjectAnalyser projectAnalyser = new ProjectAnalyser(platform);
			projectAnalyser.executeAnalyse(analysisTaskId,WORKER_ID);	
			platform.getAnalysisRepositoryManager().getWorkerService().completeTask(WORKER_ID);

		} else {				
			Worker worker = platform.getAnalysisRepositoryManager().getRepository().getWorkers().findOneByWorkerId(WORKER_ID);
			worker.setHeartbeat(new java.util.Date());
			platform.getAnalysisRepositoryManager().getRepository().sync();	
		}
		
	}

	@After
	public void tearDown() throws Exception {
		for(String db : dbs) {
			System.out.println("Dropping database " + db);
			mongo.dropDatabase(db);
		}
		mongo.close();
	}

	@Test
	public void collectionsRemainTheSame() {
		boolean result = true;
		
		for(String db : dbs) {
			File dump = new java.io.File("./dump/" + db);
			File[] files = dump.listFiles((d,n) -> {
				return n.toLowerCase().endsWith(".bson");
			});
			
			int cols = 0;
			int colsDiff = 0;
			int colPHP = 0;
			
			for(File f : files) {
				String name = f.getName();
				String col = name.substring(0, name.lastIndexOf("."));
				
				//Only check Rascal collections.
				if(col.contains("rascal")) {
					cols++;
					JSONArray a1 = getJSONArrayFromDump(f);
					JSONArray a2 = getJSONArrayFromDB(db, col);

					String expected = a1.toString().replaceAll("\"_id\":\"[a-z0-9-]+\",", "");
					String actual = a2.toString().replaceAll("\"_id\":\"[a-z0-9-]+\",", "");

					JSONCompareResult res = JSONCompare.compareJSON(expected, actual, JSONCompareMode.NON_EXTENSIBLE);

					boolean current = res.passed();
					
					if(!current) {
						colsDiff++;
						
						result = false;
						
						if(col.contains("PHP") || col.contains("php")) {
							colPHP++;
						}
						else {
//							System.err.println("[ERROR] There are different values in the " + db 
//									+ " database and the " + col + " collection.");
							System.err.println(res);
						}
						//assertTrue("There are different values in the " + db 
						//		+ " database and the " + col + " collection.", result);
					}
				}
			}
			
			System.out.println("Total: " + cols + " - Different: " + colsDiff + " - PHP: " + colPHP);
		}
		
		assertTrue(result);
	}

	private JSONArray getJSONArrayFromDump(File dump) {
		try {
			JSONArray result = new JSONArray(); 
			InputStream is = new FileInputStream(dump);
			BSONDecoder decoder = new BasicBSONDecoder();

			while(is.available() > 0) {
				BSONObject obj = decoder.readObject(is);
				if(obj == null) {
					break;
				}
				else {
					JSONObject json = new JSONObject(JSON.serialize(obj));
					result.put(json);
				}
			}
			is.close();
			return result;
		}
		catch(IOException e) {
			System.out.println("We got an error when parsing a BSON file: " + e);
			return null;
		}
	}

	private JSONArray getJSONArrayFromDB(String db, String col) {
		try {
			JSONArray result = new JSONArray(); 
			DBCollection collection = mongo.getDB(db).getCollectionFromString(col);
			DBCursor cursor = collection.find();

			while(cursor.hasNext()) {
				DBObject obj = cursor.next();
				JSONObject json = new JSONObject(JSON.serialize(obj));
				result.put(json);
			}
			return result;
		}
		catch(Exception e) {
			System.out.println("We got an error when creating a JSONArray: " + e);
			return null;
		}
	}
	
	private boolean compareJSONArrays(JSONArray a1, JSONArray a2) {
		//1. Check number of objects in array
		if(a1.length() != a2.length()) {
			System.err.println("[ERROR] The dump file has " + a1.length() 
			+ " objects while the new DB collection has " + a2.length() + " objects.");
			return false;
		}

		//2. Check that objects have the same value
		for(int i = 0; i < a1.length(); i++) {
			try {
				boolean found = false;
				Object obj = a1.get(i);
				
				if(obj instanceof JSONObject) {
					JSONObject o1 = a1.getJSONObject(i);

					for(int j = 0; j < a2.length(); j++) {
						JSONObject o2 = a2.getJSONObject(j);
						if(compareJSONObjects(o1,o2)) {
							found = true;
							// So we don't iterate multiple times over the same object.
							a2.remove(j);
							break;
						}
					}
					if(!found) {
						return false;
					}
				}
			}
			catch(JSONException e) {
				System.err.println("We got an error when getting a JSONObject: " + a1);
			}
		}

		//3. Otherwise, everything is fine!
		return true;
	}

	private boolean compareJSONObjects(JSONObject o1, JSONObject o2) {
		String[] names = JSONObject.getNames(o1);
		for(String name : names) {
			if(!o2.has(name)) {
				return false;
			}
			//The _id/$id attribute is always different for different objects.
			if(!name.equalsIgnoreCase("_id") && !name.equalsIgnoreCase("$id")) {
				Object v1 = o1.get(name);
				Object v2 = o2.get(name);

				//Check if it is an array
				if(v1 instanceof JSONArray && !compareJSONArrays((JSONArray) v1, (JSONArray) v2)) {
					return false;
				}
				//And if it is not..
				if(!(v1 instanceof JSONArray) && !v1.toString().equals(v2.toString())) {
					return false;
				}
			}
		}
		return true;
	}
}
