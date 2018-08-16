package org.eclipse.scava.platform.analysis.data.test;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.scava.platform.analysis.data.AnalysisTaskService;

import com.mongodb.Mongo;
import com.mongodb.ServerAddress;

public class StartTask {

	public static void main(String[] params) {
		try {
			AnalysisTaskService service = new AnalysisTaskService(getMongoConnection());
			service.startAnalysisTask("QualityGuardAnalysis:Analysis1");
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
