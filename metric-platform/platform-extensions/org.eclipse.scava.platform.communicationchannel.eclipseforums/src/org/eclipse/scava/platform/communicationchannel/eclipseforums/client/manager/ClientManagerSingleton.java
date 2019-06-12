package org.eclipse.scava.platform.communicationchannel.eclipseforums.client.manager;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.scava.platform.logging.OssmeterLogger;

public class ClientManagerSingleton {

	private static ClientManagerSingleton singleton = new ClientManagerSingleton();
	private Map<String, ClientData> ClientMap;
	protected OssmeterLogger logger;

	private ClientManagerSingleton() {
		logger = (OssmeterLogger) OssmeterLogger.getLogger("eclipse_forums.token.manager");
		this.ClientMap = new HashMap<String, ClientData>();
		logger.info("Client map has been initialised");
	}

	public static ClientManagerSingleton getInstance() {

		return singleton;
	}

	public Map<String, ClientData> getClientData() {

		return this.ClientMap;
	}

}
