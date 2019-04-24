package org.eclipse.scava.plugin.usermonitoring.event.resource;

import org.eclipse.scava.plugin.usermonitoring.database.VertexType;
import org.eclipse.scava.plugin.usermonitoring.event.Event;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.runtime.CoreException;

@Deprecated
public class ResourceEvent extends Event {

	private final IResourceChangeEvent changeEvent;

	public ResourceEvent(IResourceChangeEvent changeEvent) {
		super();
		this.changeEvent = changeEvent;
	}

	@Override
	public String toString() {

		StringBuilder builder = new StringBuilder();

		try {
			changeEvent.getDelta().accept(new IResourceDeltaVisitor() {

				@Override
				public boolean visit(IResourceDelta delta) {

					switch (delta.getKind()) {
					case IResourceDelta.ADDED:
						builder.append("ADDED: " + delta.getResource() + "\n");
						break;
					case IResourceDelta.REMOVED:
						builder.append("REMOVED: " + delta.getResource() + "\n");
						break;
					case IResourceDelta.CHANGED:

						System.out.println("==============================");
						System.out.println("\t" + changeEvent.getSource());
						System.out.println("\t" + delta.getResource().getFileExtension());
						// System.out.println("\t"+delt);
						System.out.println("\t" + delta.getResource().getType());
						System.out.println("\t" + delta.getResource().getName());
						System.out.println("\t" + changeEvent.getResource());
						System.out.println("==============================");
						builder.append("CHANGED: " + delta.getResource() + "\n");
						break;
					}
					builder.append(" + " + delta.getFullPath() + " + ");

					return true;
				}
			});
		} catch (CoreException e) {
			e.printStackTrace();
		}
		return "Date: " + date + " VertexType: " + VertexType.RESOURCE_EVENT + " --- " + builder;
	}

	@Override
	public Vertex toNode(GraphTraversalSource graphTraversalSource) {

		return null;
	}

}
