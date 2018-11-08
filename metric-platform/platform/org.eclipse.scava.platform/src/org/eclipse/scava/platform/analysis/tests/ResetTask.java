package org.eclipse.scava.platform.analysis.tests;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.scava.platform.analysis.AnalysisTaskService;
import org.eclipse.scava.platform.analysis.data.model.ProjectAnalysisResportory;

import com.mongodb.Mongo;
import com.mongodb.ServerAddress;

public class ResetTask {
	
	public static void main(String[] params) {
		try {

			AnalysisTaskService service =new AnalysisTaskService(new ProjectAnalysisResportory(getMongoConnection().getDB("scava-analysis")),getMongoConnection());
	    	service.resetAnalysisTask("QualityGuardAnalysis:Analysis1");
	    	service.resetAnalysisTask("QualityGuardAnalysis:Analysis2");
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
