package org.eclipse.crossmeter.platform.communicationchannel.zendesk.test;

import static org.junit.Assert.fail;

import org.eclipse.crossmeter.platform.Date;
import org.eclipse.crossmeter.platform.Platform;
import org.eclipse.crossmeter.platform.communicationchannel.zendesk.ZendeskManager;
import org.eclipse.crossmeter.platform.delta.communicationchannel.CommunicationChannelDelta;
import org.eclipse.crossmeter.platform.logging.OssmeterLogger;
import org.eclipse.crossmeter.repository.model.cc.zendesk.Zendesk;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.mongodb.DB;
import com.mongodb.Mongo;



public class ZendapiTest {

	static private ZendeskManager zendesk = new ZendeskManager();
	
	static private Zendesk communicationChannel = new Zendesk();
	static {
		communicationChannel.setUsername("juri.dirocco@univaq.it");
		communicationChannel.setPassword("Pavone84");
		communicationChannel.setUrl("https://univaq.zendesk.com");
	}
	static Mongo mongo;
	static Platform platform;
	private OssmeterLogger logger = (OssmeterLogger) OssmeterLogger.getLogger("platform.communicatonchannel.zendesk");
	@BeforeClass
	public static void setup() throws Exception {
		mongo = new Mongo();
		platform = new Platform(mongo);
	}
	
	@AfterClass
	public static void shutdown() throws Exception {
		mongo.close();
	}
	@Rule public ExpectedException expected = ExpectedException.none();
	@Test
	public void testZendeskGetFirstDateMethod() {
		try {
			org.eclipse.crossmeter.platform.Date d = zendesk.getFirstDate(mongo.getDB("crossmeter"), communicationChannel);
			logger.info("First Date: " + d);
		} catch (Exception e) {
			fail("Thows Exception" + e.getMessage());
		}
		
	}
	
	@Test
	public void testZendeskGetDeltaMethod() {
		try {
			CommunicationChannelDelta d = zendesk.getDelta(mongo.getDB("crossmeter"), communicationChannel, new Date().addDays(-5));
			logger.info("Get Delta: " + d);
		} catch (Exception e) {
			fail("Thows Exception" + e.getMessage());
		}
		
	}
	
	@Test
	public void testZendeskGetContentMethod() {
		try {
			CommunicationChannelDelta d = zendesk.getDelta(mongo.getDB("crossmeter"), communicationChannel, new Date().addDays(-5));
			String s = zendesk.getContents(mongo.getDB("crossmeter"), communicationChannel, d.getArticles().get(0));
			logger.info("get Content " + s);
		} catch (Exception e) {
			fail("Thows Exception" + e.getMessage());
		}
		
	}

}

