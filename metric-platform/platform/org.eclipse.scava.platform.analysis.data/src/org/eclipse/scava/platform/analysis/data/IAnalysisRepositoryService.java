package org.eclipse.scava.platform.analysis.data;

import java.util.List;

import org.eclipse.scava.platform.analysis.data.model.AnalysisTask;

public interface IAnalysisRepositoryService {
	
	public void createAnalysisTask(String projectId, AnalysisTask task, List<String> metricsProviders);
	
	public void updateAnalysisTask(AnalysisTask task, List<String> metricsProviders);
		
	public void deleteAnalysisTask(String analysisTaskId);
	
	public void startAnalysisTask(String analysisTaskId);

	public void stoptAnalysisTask(String analysisTaskId);

	public void resetAnalysisTask(String analysisTaskId);
	
	public List<AnalysisTask> getAnalysisTasksByProject(String projectId);

	public List<AnalysisTask> getAnalysisTasks();


}
