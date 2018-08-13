package org.eclipse.scava.platform.analysis.data.test;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.scava.platform.analysis.data.AnalysisTaskScheduling;
import org.eclipse.scava.platform.analysis.data.IAnalysisRepositoryService;

import com.mongodb.Mongo;
import com.mongodb.ServerAddress;

public class ResetTask {
	
	public static void main(String[] params) {
		try {

			IAnalysisRepositoryService service =new AnalysisTaskScheduling(getMongoConnection());
	    	service.resetAnalysisTask("QualityGuardAnalysis:Analysis1");

			
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
