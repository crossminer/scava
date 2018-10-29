package org.eclipse.scava.plugin.usermonitoring.database;

import java.util.ArrayList;
import java.util.List;

import org.apache.tinkerpop.gremlin.process.traversal.P;

public class VertexType {
	public static final String EVENT = "Event";
	public static final String RESOURCE_EVENT = "ResourceEvent";
	public static final String CLASSPATH_CHANGE_EVENT = "ClasspathChangeEvent";
	public static final String DOCUMENT_EVENT = "DocumentEvent";
	public static final String PART_EVENT = "PartEvent";
	public static final String WINDOW_EVENT = "WindowEvent";
	public static final String ELEMENT_EVENT = "ElementEvent";
	public static final String LAUNCH_EVENT = "LaunchEvent";
	public static final String ECLIPSE_CLOSE_EVENT = "EclipseCloseEvent";
	public static final String SCAVA_EVENT = "ScavaEvent";
	public static final String RESOURCE = "Resource";
	public static final String FILE = "File";
	public static final String PART = "Part";
	public static final String WINDOW = "Window";
	public static final String LIBRARY = "Library";
	public static final String PROJECT = "Project";


	
	public static List<String> getEvents(){
		List<String> eventList = new ArrayList<>();
		
	
		eventList.add(RESOURCE_EVENT);
		eventList.add(CLASSPATH_CHANGE_EVENT);
		eventList.add(DOCUMENT_EVENT);
		eventList.add(PART_EVENT);
		eventList.add(WINDOW_EVENT);
		eventList.add(ELEMENT_EVENT);
		eventList.add(LAUNCH_EVENT);
		eventList.add(ECLIPSE_CLOSE_EVENT);
		eventList.add(SCAVA_EVENT);
		
		return eventList;
	}
	
	public static List<String> getResources(){
		List<String> resourceList = new ArrayList<>();
		
		resourceList.add("All");
		resourceList.add(FILE);
		resourceList.add(PART);
		resourceList.add(WINDOW);
		resourceList.add(LIBRARY);
		resourceList.add(PROJECT);
		
		return resourceList;
	}
	
}
