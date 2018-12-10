package org.eclipse.scava.plugin.preferences;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.scava.plugin.usermonitoring.metric.metrics.Metric;

public class UserActivityDisablements {
	private Set<String> disabledEvents;
	private Set<String> disabledMetrics;

	public UserActivityDisablements() {
		disabledEvents = new HashSet<>();
		disabledMetrics = new HashSet<>();
	}

	public void disable(Class<?> eventClass) {
		String name = eventClass.getSimpleName();
		disabledEvents.add(name);
	}

	public void enable(Class<?> eventClass) {
		String name = eventClass.getSimpleName();
		disabledEvents.remove(name);
	}

	public boolean isDisabled(Class<?> eventClass) {
		String name = eventClass.getSimpleName();
		return disabledEvents.contains(name);
	}

	public void disable(Metric metric) {
		String metricId = metric.getID();
		disabledMetrics.add(metricId);
	}

	public void enable(Metric metric) {
		String metricId = metric.getID();
		disabledMetrics.remove(metricId);
	}

	public boolean isDisabled(Metric metric) {
		String metricId = metric.getID();
		return disabledMetrics.contains(metricId);
	}
}
