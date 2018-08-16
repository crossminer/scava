package org.eclipse.scava.platform.analysis.data.test;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.eclipse.scava.platform.analysis.data.AnalysisTaskService;
import org.eclipse.scava.platform.analysis.data.model.AnalysisTask;
import org.eclipse.scava.platform.analysis.data.types.AnalysisExecutionMode;

import com.mongodb.Mongo;
import com.mongodb.ServerAddress;

public class AddTask {
	
	public static void main(String[] params) {
		try {
			
			
			getMongoConnection().dropDatabase("ScavaAnalysisScheduling");
			
			AnalysisTaskService service = new AnalysisTaskService(getMongoConnection());

			AnalysisTask task1 = new AnalysisTask();
			task1.setAnalysisTaskId("QualityGuardAnalysis:Analysis1");
			task1.setLabel("Analysis1");		
			task1.setType(AnalysisExecutionMode.SINGLE_EXECUTION.name());
			task1.setStartDate(new Date(118,4,1));
			task1.setEndDate(new Date());
			
			List<String> metricsProviders = new ArrayList<>();
			metricsProviders.add("org.eclipse.scava.metricprovider.trans.commits.CommitsTransientMetricProvider");
			
	    	service.createAnalysisTask("QualityGuardAnalysis", task1,metricsProviders);
	
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
