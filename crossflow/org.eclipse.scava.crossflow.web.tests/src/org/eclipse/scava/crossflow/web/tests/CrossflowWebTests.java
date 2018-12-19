package org.eclipse.scava.crossflow.web.tests;

import static org.junit.Assert.assertTrue;

import org.apache.thrift.protocol.TJSONProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.THttpClient;
import org.apache.thrift.transport.TTransport;
import org.eclipse.scava.crossflow.web.Crossflow;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CrossflowWebTests {
	
	protected Crossflow.Client client;
	protected TTransport transport;
	
	@Test
	public void testStartStopBroker() throws Exception {
		client.startBroker();
		Thread.sleep(3000);
		assertTrue(client.isBrokerRunning());
		client.stopBroker();
		Thread.sleep(5000);
	}
	
	@Before
	public void setup() throws Exception {
		transport = new THttpClient("http://localhost:8080/org.eclipse.scava.crossflow.web/thrift");
		transport.open();
		TProtocol protocol = new TJSONProtocol(transport); // JSON transport forma
		client = new Crossflow.Client(protocol);
	}
	
	@After
	public void teardown() throws Exception {
		transport.close();
	}
	
}
