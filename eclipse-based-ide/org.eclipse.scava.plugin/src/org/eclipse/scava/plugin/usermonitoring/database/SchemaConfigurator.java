package org.eclipse.scava.plugin.usermonitoring.database;

import com.orientechnologies.orient.core.db.ODatabaseSession;
import com.orientechnologies.orient.core.metadata.schema.OClass;
import com.orientechnologies.orient.core.metadata.schema.OType;

@Deprecated
public class SchemaConfigurator {
	
	
	public static void setSchema(ODatabaseSession db) {
		
		
		
		OClass event = db.createVertexClass(VertexType.EVENT);
		event.createProperty("TimeStamp", OType.DATETIME);
		
		OClass resourceEvent = db.createVertexClass(VertexType.RESOURCE_EVENT);
		resourceEvent.addSuperClass(event);
		
		OClass documentEvent = db.createVertexClass(VertexType.DOCUMENT_EVENT);
		documentEvent.addSuperClass(event);
		documentEvent.createProperty("Type", OType.STRING);
		
		OClass partEvent = db.createVertexClass(VertexType.PART_EVENT);
		partEvent.addSuperClass(event);
		partEvent.createProperty("Type", OType.STRING);
		
		OClass windowEvent = db.createVertexClass(VertexType.WINDOW_EVENT);
		windowEvent.addSuperClass(event);
		windowEvent.createProperty("Type", OType.STRING);
		
		OClass elementEvent = db.createVertexClass(VertexType.ELEMENT_EVENT);
		elementEvent.addSuperClass(event);
		elementEvent.createProperty("Type", OType.STRING);
		
		OClass eclipseCloseEvent = db.createVertexClass(VertexType.ECLIPSE_CLOSE_EVENT);
		eclipseCloseEvent.addSuperClass(event);
		
		OClass scavaEvent = db.createVertexClass(VertexType.SCAVA_EVENT);
		scavaEvent.addSuperClass(event);
		scavaEvent.createProperty("Type", OType.STRING);
		
		OClass resource = db.createVertexClass(VertexType.RESOURCE);
		
		OClass file = db.createVertexClass(VertexType.FILE);
		file.addSuperClass(resource);
		file.createProperty("Title", OType.STRING);
		
		OClass part = db.createVertexClass(VertexType.PART);
		part.addSuperClass(resource);
		part.createProperty("Title", OType.STRING);
		
		OClass window = db.createVertexClass(VertexType.WINDOW);
		window.addSuperClass(resource);
		window.createProperty("Title", OType.STRING);
		
		OClass library = db.createVertexClass(VertexType.LIBRARY);
		library.addSuperClass(resource);
		library.createProperty("Title", OType.STRING);
		
		OClass next = db.createEdgeClass(EdgeType.NEXT);

		OClass subjectResource = db.createEdgeClass(EdgeType.SUBJECT_RESOURCE);
		
		OClass subject = db.createEdgeClass(EdgeType.SUBJECT);
		
		OClass changedFrom = db.createEdgeClass(EdgeType.CHANGED_FROM);
		
		OClass changedTo = db.createEdgeClass(EdgeType.CHANGED_TO);
		
	}

}
