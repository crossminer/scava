package org.crossmeter.plugin.usermonitoring.event.resource;

import org.crossmeter.plugin.usermonitoring.event.Event;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.runtime.CoreException;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;

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
			         case IResourceDelta.ADDED :
			             builder.append("ADDED: "+delta.getResource()+"\n");
			             break;
			         case IResourceDelta.REMOVED :
			        	 builder.append("REMOVED: "+delta.getResource()+"\n");
			             break;
			         case IResourceDelta.CHANGED :
			        	 builder.append("CHANGED: "+delta.getResource()+"\n"); 
			             break;
			         }
			         builder.append(" + "+delta.getFullPath()+" + ");
			       
			     return true;
			     }
			});
		} catch (CoreException e) {
			e.printStackTrace();
		}
		return timestamp + " [org.eclipse.core.resources.IResourceChangeListener.resourceChanged(IResourceChangeEvent)] : \n"+ builder;	
	}

	/* (non-Javadoc)
	 * @see org.crossmeter.plugin.usermonitoring.event.IEvent#toNode(org.neo4j.graphdb.GraphDatabaseService)
	 */
	@Override
	public Node toNode(GraphDatabaseService service) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
