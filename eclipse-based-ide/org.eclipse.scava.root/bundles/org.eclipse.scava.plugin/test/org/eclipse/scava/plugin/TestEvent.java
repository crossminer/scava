package org.eclipse.scava.plugin;

import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.eclipse.scava.plugin.usermonitoring.event.events.Event;
import org.eclipse.scava.plugin.usermonitoring.gremlin.database.VertexAllocator;
import org.eclipse.scava.plugin.usermonitoring.metric.metrics.MetricException;

public class TestEvent extends Event{
	
	
	public TestEvent() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Vertex toNode(VertexAllocator allocator) throws MetricException {
		// TODO Auto-generated method stub
		return null;
	}

}
