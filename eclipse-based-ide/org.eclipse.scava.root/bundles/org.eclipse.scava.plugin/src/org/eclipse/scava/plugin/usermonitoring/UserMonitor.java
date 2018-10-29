package org.eclipse.scava.plugin.usermonitoring;

import org.eclipse.scava.plugin.usermonitoring.event.EventManager;
import org.eclipse.scava.plugin.usermonitoring.metric.adapter.GremlinAdapter;
import org.eclipse.scava.plugin.usermonitoring.metric.metrics.MetricProvider;

import com.google.common.eventbus.EventBus;

public class UserMonitor {

	private final EventManager eventManager;
	private static GremlinAdapter gremlinAdapter;
	private final MetricProvider metricProvider;

	public UserMonitor(EventBus eventBus) {

		gremlinAdapter = new GremlinAdapter();
		this.eventManager = new EventManager(gremlinAdapter, eventBus);
		this.metricProvider = new MetricProvider();
	}

	public EventManager getEventManager() {
		return this.eventManager;
	}

	public static GremlinAdapter getGremlinAdapter() {
		return gremlinAdapter;
	}

}
