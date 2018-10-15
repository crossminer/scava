package org.eclipse.scava.plugin.usermonitoring.metric.metrics;

import org.eclipse.scava.plugin.usermonitoring.database.VertexType;
import org.eclipse.scava.plugin.usermonitoring.event.element.ResourceElementStateType;
import org.eclipse.scava.plugin.usermonitoring.metric.MilestoneBasedMetric;
import org.eclipse.scava.plugin.usermonitoring.metric.provider.MetricProvider;

public class milestoneBasedSaveEventAndDocumentEventRatioMetric extends MilestoneBasedMetric<Double>{

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Double getValue() {
		long saveEventCount = MetricProvider.getVertexTypeCount(VertexType.ELEMENT_EVENT, ResourceElementStateType.SAVED.toString());
		long documentEventCount = MetricProvider.getVertexTypeCount(VertexType.DOCUMENT_EVENT);
		
		double ratio = 0.0;
		
		try {
			ratio = saveEventCount/(documentEventCount*1.0);
		} catch (Exception e) {
		}
		
		return ratio;
	}



}
