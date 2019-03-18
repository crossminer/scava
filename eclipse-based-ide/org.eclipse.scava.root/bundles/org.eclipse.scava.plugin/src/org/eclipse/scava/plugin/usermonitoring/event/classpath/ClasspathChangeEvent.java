package org.eclipse.scava.plugin.usermonitoring.event.classpath;

import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.eclipse.scava.plugin.usermonitoring.database.VertexType;
import org.eclipse.scava.plugin.usermonitoring.event.Event;

public class ClasspathChangeEvent extends Event{
	
	private String libraryTitle;
	private ClasspathChangeEventType type;
	
	public ClasspathChangeEvent(String libraryTitle, ClasspathChangeEventType type) {
		this.libraryTitle = libraryTitle;
		this.type = type;
	}

	@Override
	public Vertex toNode(GraphTraversalSource graphTraversalSource) {
		Vertex vertex = graphTraversalSource.addV("event").next();
		vertex.property("VertexType",VertexType.CLASSPATH_CHANGE_EVENT);
		vertex.property("Type",type);
		vertex.property("TimeStamp", date);
		
		return vertex;
	}

	@Override
	public String toString() {
		return "ClasspathChangeEvent [libraryTitle=" + libraryTitle + ", type=" + type + ", date=" + date + "]";
	}

	
	

}
