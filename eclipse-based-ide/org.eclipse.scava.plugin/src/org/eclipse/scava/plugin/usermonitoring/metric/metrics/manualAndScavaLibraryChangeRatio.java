package org.eclipse.scava.plugin.usermonitoring.metric.metrics;

import org.eclipse.scava.plugin.usermonitoring.database.VertexType;
import org.eclipse.scava.plugin.usermonitoring.event.element.ResourceElementStateType;
import org.eclipse.scava.plugin.usermonitoring.event.scava.ScavaEventType;
import org.eclipse.scava.plugin.usermonitoring.metric.MilestoneBasedMetric;
import org.eclipse.scava.plugin.usermonitoring.metric.provider.MetricProvider;

public class manualAndScavaLibraryChangeRatio extends MilestoneBasedMetric<Double>{

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Double getValue() {
		long scavaLibraryAdded = MetricProvider.getVertexTypeCount(VertexType.SCAVA_EVENT, ScavaEventType.LIBRARY_ADDED.toString());
		long scavaLibraryRemoved = MetricProvider.getVertexTypeCount(VertexType.SCAVA_EVENT, ScavaEventType.LIBRARY_REMOVED.toString());
		long classPathEvent = MetricProvider.getVertexTypeCount(VertexType.CLASSPATH_CHANGE_EVENT);
		
		long allScavaLibraryChange = scavaLibraryAdded+scavaLibraryRemoved;
		
		return ((classPathEvent*1.0)/(classPathEvent-allScavaLibraryChange));
	}

}
