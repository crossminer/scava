package org.eclipse.scava.plugin.ui.toolbar;

import java.util.ArrayList;
import java.util.List;

import org.apache.tinkerpop.gremlin.server.util.MetricManager;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.scava.plugin.Activator;
import org.eclipse.scava.plugin.usermonitoring.event.EventManager;
import org.eclipse.scava.plugin.usermonitoring.metric.aggregator.AverageMetricAggregator;
import org.eclipse.scava.plugin.usermonitoring.metric.aggregator.MinMetricAggregator;
import org.eclipse.scava.plugin.usermonitoring.metric.metrics.averageTimeBetweenSaveEvents;
import org.eclipse.scava.plugin.usermonitoring.metric.metrics.milestoneBasedSaveEventAndDocumentEventRatioMetric;

public class MetricHandler extends AbstractHandler{

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		
		Activator.getDefault().getMainController().showMetricDialog();
		return null;
		
	}

}
