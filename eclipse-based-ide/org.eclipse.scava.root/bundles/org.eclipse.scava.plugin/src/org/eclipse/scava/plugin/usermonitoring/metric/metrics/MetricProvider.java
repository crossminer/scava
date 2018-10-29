package org.eclipse.scava.plugin.usermonitoring.metric.metrics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.scava.plugin.usermonitoring.metric.aggregator.Aggregation;
import org.eclipse.scava.plugin.usermonitoring.metric.basicmetrics.GuiUsageRateBasicMetric;
import org.eclipse.scava.plugin.usermonitoring.metric.basicmetrics.ModificationRateBasicMetric;
import org.eclipse.scava.plugin.usermonitoring.metric.basicmetrics.ScavaLibraryUsageBasicMetric;
import org.eclipse.scava.plugin.usermonitoring.metric.basicmetrics.ScavaSearchSuccesBasicMetric;
import org.eclipse.scava.plugin.usermonitoring.metric.basicmetrics.ScavaSearchUsageBasicMetric;
import org.eclipse.scava.plugin.usermonitoring.metric.basicmetrics.TestingRateBasicMetric;
import org.eclipse.scava.plugin.usermonitoring.metric.term.Duration;
import org.eclipse.scava.plugin.usermonitoring.metric.term.Milestone;

public class MetricProvider {

	public static List<Metric> getMetricList() {

		List<Metric> metrics = new ArrayList<>();

		// Modification rate metrics
		metrics.add(new Metric(Aggregation::average, Duration.SHORT, new ModificationRateBasicMetric(), "shortterm_modificaton_rate"));
		metrics.add(new Metric(Aggregation::average, Duration.MID, new ModificationRateBasicMetric(), "midterm_modificaton_rate"));
		metrics.add(new Metric(Aggregation::average, Duration.LONG, new ModificationRateBasicMetric(), "longterm_modificaton_rate"));
		metrics.add(new Metric(Aggregation::average, Duration.OVERVIEW, new ModificationRateBasicMetric(), "overview_modificaton_rate"));
		metrics.add(new Metric(Aggregation::stddev, Duration.OVERVIEW, new ModificationRateBasicMetric(), "divergency_modificaton_rate"));

		// Trust metrics
		metrics.add(new Metric(Aggregation::average, Milestone.ENGAGEMENT, new ModificationRateBasicMetric(), "per-engagement_trust"));
		metrics.add(new Metric(Aggregation::average, Milestone.LAST_ENGAGEMENT, new ModificationRateBasicMetric(), "recent_trust"));
		metrics.add(new Metric(Aggregation::stddev, Milestone.ENGAGEMENT, new ModificationRateBasicMetric(), "divergency_trust"));
		
		// Confidence metrics
		metrics.add(new Metric(Aggregation::average, Milestone.CODING_SESSION, new ModificationRateBasicMetric(), "per-coding-session_confidence"));
		metrics.add(new Metric(Aggregation::average, Milestone.LAST_CODING_SESSION, new ModificationRateBasicMetric(), "recent_confidence"));
		metrics.add(new Metric(Aggregation::stddev, Milestone.CODING_SESSION, new ModificationRateBasicMetric(), "divergency_confidence"));

		// Gui usage metrics
		metrics.add(new Metric(Aggregation::average, Duration.SHORT, new GuiUsageRateBasicMetric(), "shortterm_gui_usage"));
		metrics.add(new Metric(Aggregation::average, Duration.MID, new GuiUsageRateBasicMetric(), "midterm_gui_usage"));
		metrics.add(new Metric(Aggregation::average, Duration.LONG, new GuiUsageRateBasicMetric(), "longterm_gui_usage"));
		metrics.add(new Metric(Aggregation::average, Duration.OVERVIEW, new GuiUsageRateBasicMetric(), "overview_gui_usage"));
		metrics.add(new Metric(Aggregation::stddev, Duration.OVERVIEW, new GuiUsageRateBasicMetric(), "divergency_gui_usage"));

		// Testing rate metrics
		metrics.add(new Metric(Aggregation::average, Duration.SHORT, new TestingRateBasicMetric(), "shortterm_debug_rate"));
		metrics.add(new Metric(Aggregation::average, Duration.MID, new TestingRateBasicMetric(), "midterm_debug_rate"));
		metrics.add(new Metric(Aggregation::average, Duration.LONG, new TestingRateBasicMetric(), "longterm_debug_rate"));
		metrics.add(new Metric(Aggregation::average, Duration.OVERVIEW, new TestingRateBasicMetric(), "overview_debug_rate"));
		metrics.add(new Metric(Aggregation::stddev, Duration.OVERVIEW, new TestingRateBasicMetric(), "divergency_debug_rate"));
		metrics.add(new Metric(Aggregation::stddev, Milestone.CODING_SESSION, new TestingRateBasicMetric(), "per_coding_session_debug_rate"));
		
		//Scava Library Usage
		/*metrics.add(new Metric(Aggregation::average, Duration.SHORT, new ScavaLibraryUsageBasicMetric(), "shortterm_scava_lib_usage"));
		metrics.add(new Metric(Aggregation::average, Duration.MID, new ScavaLibraryUsageBasicMetric(), "midterm_scava_lib_usage"));
		metrics.add(new Metric(Aggregation::average, Duration.LONG, new ScavaLibraryUsageBasicMetric(), "longterm_scava_lib_usage"));
		metrics.add(new Metric(Aggregation::average, Duration.OVERVIEW, new ScavaLibraryUsageBasicMetric(), "overview_scava_lib_usage"));
		metrics.add(new Metric(Aggregation::stddev, Duration.OVERVIEW, new ScavaLibraryUsageBasicMetric(), "divergency_scava_lib_usage"));*/
		
		//Scava Search Usage
		metrics.add(new Metric(Aggregation::average, Duration.SHORT, new ScavaSearchUsageBasicMetric(), "shortterm_scava_search_usage"));
		metrics.add(new Metric(Aggregation::average, Duration.MID, new ScavaSearchUsageBasicMetric(), "midterm_scava_search_usage"));
		metrics.add(new Metric(Aggregation::average, Duration.LONG, new ScavaSearchUsageBasicMetric(), "longterm_scava_search_usage"));
		metrics.add(new Metric(Aggregation::average, Duration.OVERVIEW, new ScavaSearchUsageBasicMetric(), "overview_scava_search_usage"));
		metrics.add(new Metric(Aggregation::stddev, Duration.OVERVIEW, new ScavaSearchUsageBasicMetric(), "divergency_scava_search_usage"));
		
		//Scava Search Succes
		/*metrics.add(new Metric(Aggregation::average, Duration.SHORT, new ScavaSearchSuccesBasicMetric(), "shortterm_scava_search_succes"));
		metrics.add(new Metric(Aggregation::average, Duration.MID, new ScavaSearchSuccesBasicMetric(), "midterm_scava_search_succes"));
		metrics.add(new Metric(Aggregation::average, Duration.LONG, new ScavaSearchSuccesBasicMetric(), "longterm_scava_search_succes"));
		metrics.add(new Metric(Aggregation::average, Duration.OVERVIEW, new ScavaSearchSuccesBasicMetric(), "overview_scava_search_succes"));
		metrics.add(new Metric(Aggregation::stddev, Duration.OVERVIEW, new ScavaSearchSuccesBasicMetric(), "divergency_scava_search_succes"));*/
		

		return Collections.unmodifiableList(metrics);
	}

}