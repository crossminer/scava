package org.eclipse.scava.platform.client.api;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.scava.platform.analysis.data.AnalysisTaskScheduling;
import org.eclipse.scava.platform.analysis.data.IAnalysisRepositoryService;
import org.restlet.data.MediaType;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import com.mongodb.Mongo;
import com.mongodb.ServerAddress;

public class AnalysisTasksResource extends ServerResource {

	@Get
	public Representation getAnalysisTasks(Representation entity) throws UnknownHostException {

		IAnalysisRepositoryService service = new AnalysisTaskScheduling(getMongoConnection());
		service.getAnalysisTasks();

		StringRepresentation rep = new StringRepresentation(entity.toString());
		rep.setMediaType(MediaType.APPLICATION_JSON);
		getResponse().setStatus(Status.SUCCESS_CREATED);
		return rep;

	}

	public static Mongo getMongoConnection() throws UnknownHostException {
		List<ServerAddress> mongoHostAddresses = new ArrayList<>();
		mongoHostAddresses.add(new ServerAddress("localhost", 27017));
		return new Mongo(mongoHostAddresses);// ,options);

	}
}
