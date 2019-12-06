/*********************************************************************
* Copyright (c) 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
*
* Contributors:
*    Zsolt János Szamosvölgyi
*    Endre Tamás Váradi
*    Gergõ Balogh
**********************************************************************/
package org.eclipse.scava.plugin.usermonitoring.gremlin.database;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.apache.tinkerpop.gremlin.orientdb.OrientGraph;
import org.apache.tinkerpop.gremlin.process.traversal.Order;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.structure.Vertex;

import com.orientechnologies.orient.core.config.OContextConfiguration;
import com.orientechnologies.orient.core.db.ODatabase;
import com.orientechnologies.orient.core.db.ODatabaseType;
import com.orientechnologies.orient.core.db.OrientDB;
import com.orientechnologies.orient.core.db.OrientDBConfig;
import com.orientechnologies.orient.core.db.OrientDBConfigBuilder;

public class DatabaseManager {

	private GraphTraversalSource graphTraversalSource;
	private OrientGraph orientGraph;
	private String ORIENTDB_PATH;

	public DatabaseManager(String ORIENTDB_PATH) {
		
		this.ORIENTDB_PATH = ORIENTDB_PATH;
		OrientDBConfig defaultConfig = OrientDBConfig.defaultConfig();
		OrientDB orient = new OrientDB(ORIENTDB_PATH, defaultConfig);


		boolean notExistedBefore = orient.createIfNotExists("UserEventCollection", ODatabaseType.PLOCAL, defaultConfig);
		orient.close();

		orientGraph = OrientGraph.open(ORIENTDB_PATH + File.separator + "UserEventCollection", "admin", "admin");
		graphTraversalSource = orientGraph.traversal();
		
		
		

		if (notExistedBefore) {
			System.out.println("Database not exist before, so I create one");
			
			Vertex next = graphTraversalSource.addV("event").next();
			next.property("VertexType", VertexType.UPLOAD_EVENT);
			Date truncate = DateUtils.truncate(new Date(), Calendar.HOUR);
			next.property("TimeStamp", truncate.getTime());
			
			
			
		}

	}

	public GraphTraversalSource getTraversal() {
		return graphTraversalSource;

	}

	public void close() throws Exception {
		

		
		graphTraversalSource.close();
		orientGraph.close();
		

	}

	public void erase() throws Exception {

		close();

		OrientDB orient = new OrientDB(ORIENTDB_PATH, OrientDBConfig.defaultConfig());

		if (orient.exists("UserEventCollection")) {
			orient.drop("UserEventCollection");
		}
	}

}
