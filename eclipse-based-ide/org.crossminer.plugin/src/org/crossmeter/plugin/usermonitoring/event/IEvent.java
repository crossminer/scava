package org.crossmeter.plugin.usermonitoring.event;

import java.sql.Timestamp;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;

public interface IEvent {
	
	
	public Node toNode(GraphDatabaseService service);
}
