package org.eclipse.scava.platform.client.api;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import com.mongodb.Mongo;
import com.mongodb.ServerAddress;

public class SingletonMongoConnection {
	
	private static Mongo mongo;
	
	private SingletonMongoConnection() {
	}
	
	static {
		List<ServerAddress> mongoHostAddresses = new ArrayList<>();
		try {
			mongoHostAddresses.add(new ServerAddress("localhost", 27017));
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		mongo = new Mongo(mongoHostAddresses);// ,options);
	}
	
	private static Mongo getMongoConnection() {
		return mongo;
	}
	
	public static synchronized Mongo getInstance() throws UnknownHostException {
		if(mongo == null) {
			mongo = getMongoConnection();
		}
		return mongo;
	}
	
}
