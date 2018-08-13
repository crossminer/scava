package org.eclipse.scava.platform.analysis.data;

import java.util.Date;

import org.eclipse.scava.platform.analysis.data.model.ProjectAnalysisResportory;
import org.eclipse.scava.platform.analysis.data.model.ProjectMetricProvider;

/**
 * 
 *
 */

public interface IAnalysisSchedulingService {
	public ProjectAnalysisResportory getRepository();
	
	public String getOlderPendingAnalysiTask();
	public void startMetricExecution(String analysisTaskId, String metricId);
	public void endMetricExecution(String projectId, String analysisTaskId, String metricId);
	public void newDailyTaskExecution(String analysisTaskId, Date date);
	public ProjectMetricProvider findMetricProviderScheduling(String projectId, String metricProviderId);

}
