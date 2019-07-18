package org.eclipse.scava.plugin.usermonitoring;

import org.eclipse.scava.plugin.Activator;
import org.eclipse.scava.plugin.preferences.Preferences;
import org.eclipse.scava.plugin.preferences.UserActivityDisablements;
import org.eclipse.scava.plugin.usermonitoring.event.EventManager;
import org.eclipse.scava.plugin.usermonitoring.metric.adapter.GremlinAdapter;
import org.eclipse.scava.plugin.usermonitoring.metric.metrics.MetricProvider;

import com.google.common.eventbus.EventBus;
import com.google.gson.Gson;

public class UserMonitor {

	private final EventManager eventManager;
	private static GremlinAdapter gremlinAdapter;
	private final MetricProvider metricProvider;

	private UserActivityDisablements disablements;

	public UserMonitor(EventBus eventBus) {
		connectDisablements();
		
		gremlinAdapter = new GremlinAdapter();
		this.eventManager = new EventManager(this, eventBus);
		this.metricProvider = new MetricProvider();
	}

	private void connectDisablements() {
		Activator.getDefault().getPreferenceStore().addPropertyChangeListener(e -> {
			if (e.getProperty().equals(Preferences.USERMONITORING_DISABLEMENTS)) {
				updateDisablements(e.getNewValue());
			}
		});
		Object json = Activator.getDefault().getPreferenceStore().getString(Preferences.USERMONITORING_DISABLEMENTS);
		updateDisablements(json);
	}

	private void updateDisablements(Object data) {
		String json = (String)data;
		disablements = new Gson().fromJson(json, UserActivityDisablements.class);
	}
	
	public UserActivityDisablements getDisablements() {
		return disablements;
	}
	
	public EventManager getEventManager() {
		return this.eventManager;
	}

	public static GremlinAdapter getGremlinAdapter() {
		return gremlinAdapter;
	}

}
