package org.eclipse.scava.plugin.usermonitoring.database;

import java.io.File;
import org.apache.tinkerpop.gremlin.orientdb.OrientGraph;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import com.orientechnologies.orient.core.db.ODatabaseType;
import com.orientechnologies.orient.core.db.OrientDB;
import com.orientechnologies.orient.core.db.OrientDBConfig;

public class DatabaseManager implements IDatabaseManager {

	private GraphTraversalSource graphTraversalSource;
	private String ORIENTDB_PATH;

	public DatabaseManager(String ORIENTDB_PATH) {

		this.ORIENTDB_PATH = ORIENTDB_PATH;
		OrientDB orient = new OrientDB(ORIENTDB_PATH, OrientDBConfig.defaultConfig());

		orient.createIfNotExists("UserEventCollection", ODatabaseType.PLOCAL, OrientDBConfig.defaultConfig());
		orient.close();

		graphTraversalSource = OrientGraph.open(ORIENTDB_PATH + File.separator + "UserEventCollection", "admin", "admin").traversal();
	}

	public GraphTraversalSource getGraphTraversalSource() {

		return graphTraversalSource;
	}
	
	public void close() throws Exception {
		
		
		graphTraversalSource.close();
		
	}
	
	public void erase() throws Exception {
		
		close();
		
		OrientDB orient = new OrientDB(ORIENTDB_PATH, OrientDBConfig.defaultConfig());

		if (orient.exists("UserEventCollection")) {
			orient.drop("UserEventCollection");
		}
	}

}
