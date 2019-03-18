package org.eclipse.scava.plugin.eclipse.handlers;

import javax.jws.soap.SOAPBinding.Use;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.scava.plugin.ui.metric.MetricDisplay;
import org.eclipse.scava.plugin.usermonitoring.UserMonitor;
import org.eclipse.scava.plugin.usermonitoring.metric.adapter.GremlinAdapter;
import org.eclipse.scava.plugin.usermonitoring.metric.metrics.MetricProvider;

public class MetricHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {

		new MetricDisplay(null);

		return null;
	}

}
