package org.eclipse.scava.platform.analysis.tests;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.scava.platform.analysis.data.model.AnalysisTask;
import org.eclipse.scava.platform.analysis.data.model.ProjectAnalysisResportory;

import com.mongodb.Mongo;
import com.mongodb.ServerAddress;

public class MonitorTaskStatus {
	
	public static void main(String[] params) {
		try {		
			ProjectAnalysisResportory repository = new ProjectAnalysisResportory(getMongoConnection().getDB("scava-analysis"));

	    	while(true) {
	    		AnalysisTask task = repository.getAnalysisTasks().findOneByAnalysisTaskId("QualityGuardAnalysis:Analysis1");
	    		if(task != null) {
	    			System.out.println(task.getAnalysisTaskId() + " [" + task.getScheduling().getStatus()+"] : " + task.getScheduling().getProgress() +" %");
	    		}
	    		try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    	}
	    	
	
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
