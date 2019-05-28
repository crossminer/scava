package org.eclipse.scava.plugin.usermonitoring.metric.aggregator;

import java.util.List;

public class Aggregation {

	public static double average(List<Double> values) {

		double sum = 0;
		for (Double value : values) {
			sum += value;
		}

		double average = sum / values.size();
		return average;
	}

	public static double stddev(List<Double> values) {

		double average = average(values);
		double sum = 0;

		for (Double value : values) {
			sum += Math.pow((value - average), 2);
		}

		double stddev = Math.sqrt(sum / values.size());
		return stddev;
	}

}
