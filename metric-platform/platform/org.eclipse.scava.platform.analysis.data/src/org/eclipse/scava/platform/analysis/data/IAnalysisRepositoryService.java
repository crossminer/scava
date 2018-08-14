package org.eclipse.scava.platform.analysis.data;

import java.util.List;

import org.eclipse.scava.platform.analysis.data.model.AnalysisTask;

public interface IAnalysisRepositoryService {
	
	public AnalysisTask createAnalysisTask(String projectId, AnalysisTask task, List<String> metricsProviders);
	
	public AnalysisTask updateAnalysisTask(AnalysisTask task, List<String> metricsProviders);
		
	public AnalysisTask deleteAnalysisTask(String analysisTaskId);
	
	public AnalysisTask startAnalysisTask(String analysisTaskId);

	public AnalysisTask stoptAnalysisTask(String analysisTaskId);

	public AnalysisTask resetAnalysisTask(String analysisTaskId);
	
	public List<AnalysisTask> getAnalysisTasksByProject(String projectId);

	public List<AnalysisTask> getAnalysisTasks();


}
