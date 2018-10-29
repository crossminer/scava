package org.eclipse.scava.plugin.usermonitoring.metric;

import java.util.Date;

public abstract class TimeBasedMetric<T> implements IMetric {

	public abstract T getValue(Date fromDate, Date toDate);
}
