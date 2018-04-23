package org.eclipse.scava.rascal.test.regression;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.bson.BSON;
import org.bson.BSONDecoder;
import org.bson.BSONObject;
import org.bson.BasicBSONDecoder;
import org.eclipse.scava.platform.Date;
import org.eclipse.scava.platform.Platform;
import org.eclipse.scava.platform.osgi.executors.ProjectExecutor;
import org.eclipse.scava.rascal.test.ProjectCreationUtil;
import org.eclipse.scava.repository.model.Project;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.googlecode.pongo.runtime.PongoFactory;
import com.googlecode.pongo.runtime.osgi.OsgiPongoFactoryContributor;
import com.mongodb.Mongo;
import com.mongodb.util.JSON;

import sun.misc.IOUtils;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class RascalMetricsTest {

	private static final String PROJECTS = "test_projects";
	private static final String PROJECT = "test_project";
	private static final String NAME_SUFFIX = "_name";
	private static final String URL_SUFFIX = "_url";
	private static final String TEST_SUFFIX = "test_suffix";
	private static final String[] METRIC_EXCEPTIONS = {"cocomo"};
	
	private static Platform platform;
	private static Mongo mongo;
	private static String[] dbs;

	@Before
	public void setUp() throws Exception {
		mongo = new Mongo();
		PongoFactory.getInstance().getContributors().add(new OsgiPongoFactoryContributor());
		platform = new Platform(mongo);

		// Reading test properties
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
		ProjectExecutor executor = new ProjectExecutor(platform, project);
		// Compute metrics only for 9 dates
		Date dStart = executor.getLastExecutedDate();
		Date dEnd = (new Date(dStart.toString())).addDays(1);
		executor.executeProject(dStart, dEnd);
	}

	@After
	public void tearDown() throws Exception {
		for(String db : dbs) {
			System.out.println("Dropping database " + db);
			//mongo.dropDatabase(db);
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

			for(File f : files) {
				String name = f.getName();
				String col = name.substring(0, name.lastIndexOf("."));

				JSONArray a1 = getJSONArrayFromDump(f);
				JSONArray a2 = getJSONArrayFromDB(db, col);
				result &= compareJSONArrays(a1, a2);

				if(!result) {
					System.err.println("There are different values in the " + db 
							+ " database and the " + col + " collection.");
					break;
				}
			}

		}

		assertTrue(result);
	}


	private boolean compareJSONArrays(JSONArray a1, JSONArray a2) {
		// 1. Check number of objects in array
		if(a1.length() != a2.length()) {
			System.err.println("[ERROR] The dump file has " + a1.length() 
			+ " objects while the new DB collection has " + a2.length() + " objects.");
			return false;
		}

		// 2. Check that objects have the same value
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
						System.err.println("[ERROR] There is no object in the DB matching: ");
						System.err.println(o1.toString());
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
			//The _id/$id attribute is always different for different objects.
			if(!name.equalsIgnoreCase("_id") && !name.equalsIgnoreCase("$id")) {
				Object v1 = o1.get(name);
				Object v2 = o2.get(name);

				//Manage exceptions 
				if(name.equalsIgnoreCase("metricId")) {
					for(String e : METRIC_EXCEPTIONS) {
						if(e.equalsIgnoreCase(v1.toString())) {
							return true;
						}
					}
				}
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
}
