package org.eclipse.scava.platform.analysis.tests;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.eclipse.scava.platform.analysis.AnalysisTaskService;
import org.eclipse.scava.platform.analysis.data.model.AnalysisTask;
import org.eclipse.scava.platform.analysis.data.model.ProjectAnalysisResportory;
import org.eclipse.scava.platform.analysis.data.types.AnalysisExecutionMode;

import com.mongodb.Mongo;
import com.mongodb.ServerAddress;

public class AddTask {
	
	public static void main(String[] params) {
		try {
			
			AnalysisTaskService service = new AnalysisTaskService(new ProjectAnalysisResportory(getMongoConnection().getDB("scava-analysis")),getMongoConnection());
			
			AnalysisTask task1 = new AnalysisTask();
			task1.setAnalysisTaskId("QualityGuardAnalysis:Analysis1");
			task1.setLabel("Analysis1");		
			task1.setType(AnalysisExecutionMode.SINGLE_EXECUTION.name());
			task1.setStartDate(new Date(118,4,1));
			task1.setEndDate(new Date());
			
			List<String> metricsProviders = new ArrayList<>();
			metricsProviders.add("org.eclipse.scava.metricprovider.trans.commits.CommitsTransientMetricProvider");
			
	    	service.createAnalysisTask("QualityGuardAnalysis", task1,metricsProviders);
	    	
			AnalysisTask task2 = new AnalysisTask();
			task2.setAnalysisTaskId("QualityGuardAnalysis:Analysis2");
			task2.setLabel("Analysis2");		
			task2.setType(AnalysisExecutionMode.SINGLE_EXECUTION.name());
			task2.setStartDate(new Date(118,4,1));
			task2.setEndDate(new Date());
			
			
			List<String> metricsProviders2 = new ArrayList<>();
			metricsProviders2.add("org.eclipse.scava.metricprovider.historic.bugs.bugs");
			metricsProviders2.add("org.eclipse.scava.metricprovider.trans.bugs.bugmetadata.BugMetadataTransMetricProvider");
			metricsProviders2.add("org.eclipse.scava.metricprovider.trans.bugs.activeusers.ActiveUsersTransMetricProvider");
			
			
	    	service.createAnalysisTask("QualityGuardAnalysis", task2,metricsProviders2);
	    	
	
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
	
	

	public static Mongo getMongoConnection() throws UnknownHostException {
		List<ServerAddress> mongoHostAddresses = new ArrayList<>();
		mongoHostAddresses.add(new ServerAddress("localhost", 27017));
		return new Mongo(mongoHostAddresses);// ,options);

	}

}
