package org.eclipse.scava.plugin.usermonitoring.metric;

public abstract class MilestoneBasedMetric<T> implements IMetric {

	public abstract T getValue();

}
