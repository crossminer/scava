package org.eclipse.scava.plugin.usermonitoring.metric.aggregator;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class AggregationTest {

	@Test
	public void averageWithPositivesTest() {

		List<Double> numbers = new ArrayList<>();

		numbers.add(10.0);
		numbers.add(20.0);
		numbers.add(0.0);
		numbers.add(6.0);

		double average = Aggregation.average(numbers);
		assertEquals(9.0, average, 0.0005);

	}

	@Test
	public void averageWithNegativesTest() {

		List<Double> numbers = new ArrayList<>();

		numbers.add(-10.3);
		numbers.add(-20.11);
		numbers.add(0.5);
		numbers.add(-6.7);

		double average = Aggregation.average(numbers);
		assertEquals(-9.1525, average, 0.0005);

	}

	@Test
	public void stdevTest() {

		List<Double> numbers = new ArrayList<>();

		numbers.add(-10.3);
		numbers.add(20.11);
		numbers.add(0.5);
		numbers.add(-16.7);

		double average = Aggregation.average(numbers);
		assertEquals(-1.5975, average, 0.0005);

	}

	//@Test
	public void stdevWithPositivesTest() {

		List<Double> numbers = new ArrayList<>();

		numbers.add(10.0);
		numbers.add(20.0);
		numbers.add(0.0);
		numbers.add(6.0);

		double stdev = Aggregation.stddev(numbers);
		assertEquals(9.0, stdev, 0.0005);

	}

	//@Test
	public void stdevWithNegativesTest() {

		List<Double> numbers = new ArrayList<>();

		numbers.add(-10.3);
		numbers.add(-20.11);
		numbers.add(0.5);
		numbers.add(-6.7);

		double stdev = Aggregation.stddev(numbers);
		assertEquals(-9.2775, stdev, 0.0005);

	}

	@Test
	public void averageTest() {

		List<Double> numbers = new ArrayList<>();

		numbers.add(-10.3);
		numbers.add(20.11);
		numbers.add(0.5);
		numbers.add(-16.7);

		double stdev = Aggregation.stddev(numbers);
		assertEquals(13.95917, stdev, 0.0005);

	}
}