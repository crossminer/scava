package org.eclipse.scava.plugin.usermonitoring.event.launch;

import java.util.List;

import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.scava.plugin.usermonitoring.database.EdgeType;
import org.eclipse.scava.plugin.usermonitoring.database.VertexType;
import org.eclipse.scava.plugin.usermonitoring.event.Event;

public class LaunchEvent extends Event {

	private ILaunch launch;
	private String launchedFile;
	private IProject launchedProject;

	public LaunchEvent(ILaunch launch) {
		this.launch = launch;

		ILaunchConfiguration launchConfiguration = launch.getLaunchConfiguration();
		try {
			IResource[] mappedResources = launchConfiguration.getMappedResources();
			for (IResource iResource : mappedResources) {
				launchedFile = iResource.getName();
				launchedProject = iResource.getProject();
			}
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String toString() {

		return "Date: " +date + " VertexType: " + VertexType.LAUNCH_EVENT + " EventType: "+ launch.getLaunchMode();
	}

	@Override
	public Vertex toNode(GraphTraversalSource graphTraversalSource) {
		Vertex vertex = graphTraversalSource.addV("event").next();
		vertex.property("VertexType", VertexType.LAUNCH_EVENT);
		vertex.property("Type", launch.getLaunchMode());
		vertex.property("TimeStamp", date);

		
		Vertex resourceFile;
		if (findResource(graphTraversalSource, getLaunchedFile()) != null) {
			resourceFile = findResource(graphTraversalSource, getLaunchedFile());
		} else {
			resourceFile = graphTraversalSource.addV("resource").next();
			resourceFile.property("VertexType", VertexType.FILE);
			resourceFile.property("Title", getLaunchedFile());
		}

		vertex.addEdge(EdgeType.SUBJECT_RESOURCE, resourceFile);
		
		
		
		
		

		return vertex;
	}
	
	private Vertex findResource(GraphTraversalSource graphTraversalSource, String title) {

		List<Vertex> list = graphTraversalSource.V().hasLabel("resource").has("VertexType", VertexType.FILE).has("Title", title).toList();
		Vertex subject = null;

		if (!list.isEmpty()) {
			subject = list.get(0);
		}
		return subject;
	}

	public ILaunch getLaunch() {
		return launch;
	}

	public String getLaunchedFile() {
		return launchedFile;
	}

	public IProject getLaunchedProject() {
		return launchedProject;
	}

}
