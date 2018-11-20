package org.eclipse.scava.crossflow.restmule.client.stackexchange.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.scava.crossflow.restmule.client.stackexchange.api.StackExchangeApi;
import org.eclipse.scava.crossflow.restmule.client.stackexchange.model.Answers;
import org.eclipse.scava.crossflow.restmule.client.stackexchange.model.Posts;
import org.eclipse.scava.crossflow.restmule.client.stackexchange.model.QuestionsResponse;
import org.eclipse.scava.crossflow.restmule.client.stackexchange.model.Tags;
import org.eclipse.scava.crossflow.restmule.client.stackexchange.model.Users;
import org.eclipse.scava.crossflow.restmule.client.stackexchange.model.UsersResponse;
import org.eclipse.scava.crossflow.restmule.client.stackexchange.api.IStackExchangeApi;
import org.eclipse.scava.crossflow.restmule.client.stackexchange.util.StackExchangeUtils;
import org.eclipse.scava.crossflow.restmule.core.data.IData;
import org.eclipse.scava.crossflow.restmule.core.data.IDataSet;
import org.junit.Ignore;
import org.junit.Test;

public class BasicApiQueryTest extends StackExchangeUtils {

	@SuppressWarnings("unused")
	private static final Logger LOG = LogManager.getLogger(BasicApiQueryTest.class);

	
	@Test
	public void testPublicSessionSmall() {
		// Instantiate API client
		IStackExchangeApi client = StackExchangeUtils.getOAuthClient();
		
		String tagged = "java";
		String order = "desc";
		String max = null;//"";
		String min = null;//"";
		String sort = "creation";
		Integer fromdate = 1541091600;
		Integer todate = 1541092200; // 10 results
		String filter = null;
		String callback = null;//"";
		String site = "stackoverflow";
	
		// Submit API query
		IData<QuestionsResponse> result = client.getQuestionsQuestionsResponse(tagged, order, max, min, sort, fromdate, todate, filter, callback, site);
		
		// Observe search repositories result set
		result.observe()
		
			.doOnNext(resultItem -> {
				LOG.info(result.hashCode() + " --- ITEM: " + resultItem.toString());
			})
			
			.doOnError(e -> {
				LOG.error(result.hashCode() + " --- " + "ERROR OCCURRED: " + e.getMessage());
			})
			
			.doOnComplete(() -> {
				LOG.info(result.hashCode() + " --- " + "COMPLETED !");
			})
			
			.blockingSubscribe();
	
		Long count = result.observe().count().blockingGet();
		LOG.info("Final observe count of task " + result.hashCode() + ": " + count);
	}
	
	@Test
	public void testPublicSessionGetQuestionsResponse() {
		// Instantiate API client
		IStackExchangeApi client = StackExchangeUtils.getOAuthClient();
		
		String tagged = "java";
		String order = "desc";
		String max = null;//"";
		String min = null;//"";
		String sort = "creation";
		Integer fromdate = 1541091600; // 11/01/2018 @ 5:00pm (UTC)
		Integer todate = 1541095200; // 11/01/2018 @ 6:00pm (UTC) --- 33 results
		String filter = null;//"";
		String callback = null;//""
		String site = "stackoverflow";
	
		// Submit API query
		IData<QuestionsResponse> result = client.getQuestionsQuestionsResponse(tagged, order, max, min, sort, fromdate, todate, filter, callback, site);

		
		// Observe search repositories result set
		result.observe()
		
			.doOnNext(resultItem -> {
				LOG.info(result.hashCode() + " --- ITEM: " + resultItem.toString());
			})
			
			.doOnError(e -> {
				LOG.error(result.hashCode() + " --- " + "ERROR OCCURRED: " + e.getMessage());
			})
			
			.doOnComplete(() -> {
				LOG.info(result.hashCode() + " --- " + "COMPLETED !");
			})
			
			.blockingSubscribe();

		Long count = result.observe().count().blockingGet();
		LOG.info("Final observe count of task " + result.hashCode() + ": " + count);

	}
	
	@Test
	public void testPublicSessionGetQuestionsResponseLarge() {
		// Instantiate API client
		IStackExchangeApi client = StackExchangeUtils.getOAuthClient();
		
		String tagged = "java";
		String order = "desc";
		String max = null;//"";
		String min = null;//"";
		String sort = "creation";
		Integer fromdate = 1541091600; // 11/01/2018 @ 5:00pm (UTC)
		Integer todate = 1541113200; // 11/01/2018 @ 11:00pm (UTC) --- ~138 results
		String filter = null;//"";
		String callback = null;//""
		String site = "stackoverflow";
	
		// Submit API query
		IData<QuestionsResponse> result = client.getQuestionsQuestionsResponse(tagged, order, max, min, sort, fromdate, todate, filter, callback, site);

		
		// Observe search repositories result set
		result.observe()
		
			.doOnNext(resultItem -> {
				LOG.info(result.hashCode() + " --- ITEM: " + resultItem.toString());
			})
			
			.doOnError(e -> {
				LOG.error(result.hashCode() + " --- " + "ERROR OCCURRED: " + e.getMessage());
			})
			
			.doOnComplete(() -> {
				LOG.info(result.hashCode() + " --- " + "COMPLETED !");
			})
			
			.blockingSubscribe();

		Long count = result.observe().count().blockingGet();
		LOG.info("Final observe count of task " + result.hashCode() + ": " + count);

	}
	
	@Test
	public void testPublicSessionPosts() {
		// Instantiate API client
		IStackExchangeApi client = StackExchangeUtils.getOAuthClient();
		
		String tagged = "java";
		String order = "desc";
		String max = null;//"";
		String min = null;//"";
		String sort = "creation";
		Integer fromdate = 1541091600; // 11/01/2018 @ 5:00pm (UTC)
		Integer todate = 1541095200; // 11/01/2018 @ 6:00pm (UTC) --- 33 results
		String filter = null;//"";
		String callback = null;//""
		String site = "stackoverflow";
	
		// Submit API query
		IData<Posts> result = client.getPosts(order, max, min, sort, fromdate, todate, filter, callback, site);

		
		// Observe search repositories result set
		result.observe()
		
			.doOnNext(resultItem -> {
				LOG.info(result.hashCode() + " --- ITEM: " + resultItem.toString());
			})
			
			.doOnError(e -> {
				LOG.error(result.hashCode() + " --- " + "ERROR OCCURRED: " + e.getMessage());
			})
			
			.doOnComplete(() -> {
				LOG.info(result.hashCode() + " --- " + "COMPLETED !");
			})
			
			.blockingSubscribe();

		Long count = result.observe().count().blockingGet();
		LOG.info("Final observe count of task " + result.hashCode() + ": " + count);

	}
	
	@Test
	public void testPublicSessionTags() {
		// Instantiate API client
		IStackExchangeApi client = StackExchangeUtils.getOAuthClient();
		
		String inname = "own";
		String tagged = "java";
		String order = "desc";
		String max = null;//"";
		String min = null;//"";
		String sort = "popular";
		Integer fromdate = 1535760000;
		Integer todate = 1542412800;
		String filter = null;//"";
		String callback = null;//""
		String site = "stackoverflow";
		
		// Submit API query
		IData<Tags> result = client.getTags(inname, order, max, min, sort, fromdate, todate, filter, callback, site);
		
		// Observe search repositories result set
		result.observe()
		
			.doOnNext(resultItem -> {
				LOG.info(result.hashCode() + " --- ITEM: " + resultItem.toString());
			})
			
			.doOnError(e -> {
				LOG.error(result.hashCode() + " --- " + "ERROR OCCURRED: " + e.getMessage());
			})
			
			.doOnComplete(() -> {
				LOG.info(result.hashCode() + " --- " + "COMPLETED !");
			})
			
			.blockingSubscribe();
		
		Long count = result.observe().count().blockingGet();
		LOG.info("Final observe count of task " + result.hashCode() + ": " + count);

	}
	
	@Test
	public void testPublicSessionUsers() {
		// Instantiate API client
		IStackExchangeApi client = StackExchangeUtils.getOAuthClient();
		
		String inname = "own";
		String tagged = "java";
		String order = "desc";
		String max = null;//"";
		String min = null;//"";
		String sort = "reputation";
		Integer fromdate = 1535760000;
		Integer todate = 1541095200;
		String filter = null;//"";
		String callback = null;//""
		String site = "stackoverflow";
		
		// Submit API query
		IData<UsersResponse> result = client.getUsersUsersResponse(inname, order, max, min, sort, fromdate, todate, filter, callback, site);
		
		// Observe search repositories result set
		result.observe()
		
			.doOnNext(resultItem -> {
				LOG.info(result.hashCode() + " --- ITEM: " + resultItem.toString());
			})
			
			.doOnError(e -> {
				LOG.error(result.hashCode() + " --- " + "ERROR OCCURRED: " + e.getMessage());
			})
			
			.doOnComplete(() -> {
				LOG.info(result.hashCode() + " --- " + "COMPLETED !");
			})
			
			.blockingSubscribe();
		
		Long count = result.observe().count().blockingGet();
		LOG.info("Final observe count of task " + result.hashCode() + ": " + count);

	}
	
	@Test
	public void testPublicSessionAnswers() {
		// Instantiate API client
		IStackExchangeApi client = StackExchangeUtils.getOAuthClient();
		
		String tagged = "java";
		String order = "desc";
		String max = null;//"";
		String min = null;//"";
		String sort = "votes";
		Integer fromdate = 1535760000;
		Integer todate = 1541095200;
		String filter = null;//"";
		String callback = null;//""
		String site = "stackoverflow";
		
		// Submit API query
		IData<Answers> result = client.getAnswers(order, max, min, sort, fromdate, todate, filter, callback, site);
		
		// Observe search repositories result set
		result.observe()
		
			.doOnNext(resultItem -> {
				LOG.info(result.hashCode() + " --- ITEM: " + resultItem.toString());
			})
			
			.doOnError(e -> {
				LOG.error(result.hashCode() + " --- " + "ERROR OCCURRED: " + e.getMessage());
			})
			
			.doOnComplete(() -> {
				LOG.info(result.hashCode() + " --- " + "COMPLETED !");
			})
			
			.blockingSubscribe();
		
		Long count = result.observe().count().blockingGet();
		LOG.info("Final observe count of task " + result.hashCode() + ": " + count);

	}
	
	public static void main(String args[]) {
		// Instantiate API client
		IStackExchangeApi client = StackExchangeUtils.getOAuthClient();//.getPublicClient()
		
		String tagged = "java";
		String order = "desc";
		String max = null;//"";
		String min = null;//"";
		String sort = "creation";
		Integer fromdate = 1541091600;
		Integer todate = 1541113200; // 11/01/2018 @ 11:00pm (UTC) --- more than 100 results
		String filter = "total";//"total";
		String callback = null;//""
		String site = "stackoverflow";
	
		// Submit API query
		IData<QuestionsResponse> result = client.getQuestionsQuestionsResponse(tagged, order, max, min, sort, fromdate, todate, filter, callback, site);
		
		// Observe search repositories result set
		result.observe()
		
			.doOnNext(resultItem -> {
				LOG.info(result.hashCode() + " --- ITEM: " + resultItem.toString());
			})
			
			.doOnError(e -> {
				LOG.error(result.hashCode() + " --- " + "ERROR OCCURRED: " + e.getMessage());
			})
			
			.doOnComplete(() -> {
				LOG.info(result.hashCode() + " --- " + "COMPLETED !");
			})
			
			.blockingSubscribe();
	
		Long count = result.observe().count().blockingGet();
		LOG.info("Final observe count of task " + result.hashCode() + ": " + count);
	}

}
