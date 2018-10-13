package org.eclipse.scava.plugin.usermonitoring.metric.aggregator;

import java.util.List;

public abstract class AverageMetricAggregator<T extends Number> implements IMetricAggregator<T> {

	@SuppressWarnings("unchecked")
	@Override
	public T aggregateToValue(List<T> items) {

		T sample = items.get(0);
		T avg;

		if (sample instanceof Double) {
			double d = doAverageDouble((List<Double>) items);
			avg = (T) Double.valueOf(d);
		}else if(sample instanceof Float) {
			float f = doAverageFloat((List<Float>) items);
			avg = (T) Float.valueOf(f);
		}else if(sample instanceof Integer) {
			int i = doAverageInteger((List<Integer>) items);
			avg = (T) Integer.valueOf(i);
		}else if(sample instanceof Long) {
			long l = doAverageLong((List<Long>) items);
			avg = (T) Long.valueOf(l);
		}else {
			throw new IllegalStateException("Constructor must be initialiez with either of Double, Integer, Long or Float list");
		}

		return avg;
	}

	private float doAverageFloat(List<Float> l) {

		float sum = 0.0f;

		for (Float f : l) {
			sum += f;
		}

		return (sum / l.size());

	}

	private double doAverageDouble(List<Double> l) {
		double sum = 0.0;

		for (Double d : l) {
			sum += d;
		}

		return (sum / l.size());
	}

	private int doAverageInteger(List<Integer> l) {
		int sum = 0;

		for (Integer i : l) {
			sum += i;
		}

		return (sum / l.size());
	}
	private long doAverageLong(List<Long> l) {
		long sum = 0;

		for (Long lo : l) {
			sum += lo;
		}

		return ((sum) / l.size());
	}

}
