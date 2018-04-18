package org.eclipse.scava.rascal.test.regression;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.ParseException;
import java.util.Properties;

import org.eclipse.scava.platform.Date;
import org.eclipse.scava.platform.Platform;
import org.eclipse.scava.platform.osgi.executors.ProjectExecutor;
import org.eclipse.scava.rascal.test.ProjectCreationUtil;
import org.eclipse.scava.repository.model.Project;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.googlecode.pongo.runtime.PongoFactory;
import com.googlecode.pongo.runtime.osgi.OsgiPongoFactoryContributor;
import com.mongodb.Mongo;
import com.mongodb.util.JSON;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class RascalMetricsTest {

	private static final String PROJECTS = "test_projects";
	private static final String PROJECT = "test_project";
	private static final String NAME_SUFFIX = "_name";
	private static final String URL_SUFFIX = "_url";
	private static final String TEST_SUFFIX = "test_suffix";
	
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
			createDBCollectionsFromDump(db);
		}
		
		mongo.close();
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
		Date dEnd = (new Date(dStart.toString())).addDays(10);
		executor.executeProject(dStart, dEnd);
	}
	
	private void createDBCollectionsFromDump(String db) throws IOException {
		File dump = new java.io.File("./dump/" + db);
		File[] files = dump.listFiles((d,n) -> {
			return n.toLowerCase().endsWith(".json");
		});
		
		for(File f : files) {
			String name = f.getName();
			String colName = name.substring(0, name.lastIndexOf("."));
			DBCollection col = mongo.getDB(db).getCollection(colName);
			JSONObject jsonRef = new JSONObject(Files.readAllLines(f.toPath()));
			
			if(col != null) {
				DBCursor cursor = col.find();
				if(cursor.hasNext()) {
					DBObject obj = cursor.next();
					JSONObject json = new JSONObject(JSON.serialize(obj));
					//TODO: check json content + compare collections
				}
			}
		}
	}

	@After
	public void tearDown() throws Exception {
		Mongo mongo = new Mongo();
		for(String db : dbs) {
			System.out.println("Dropping database " + db);
			mongo.dropDatabase(db);
		}
		mongo.close();
	}

	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
