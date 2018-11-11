package org.eclipse.scava.crossflow.restmule.client.stackexchange.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.scava.crossflow.restmule.client.stackexchange.api.StackExchangeApi;
import org.eclipse.scava.crossflow.restmule.client.stackexchange.model.Posts;
import org.eclipse.scava.crossflow.restmule.client.stackexchange.model.Questions;
import org.eclipse.scava.crossflow.restmule.client.stackexchange.api.IStackExchangeApi;
import org.eclipse.scava.crossflow.restmule.client.stackexchange.util.StackExchangeUtils;
import org.eclipse.scava.crossflow.restmule.core.data.IDataSet;
import org.junit.Test;

public class BasicApiQueryTest extends StackExchangeUtils {

	@SuppressWarnings("unused")
	private static final Logger LOG = LogManager.getLogger(BasicApiQueryTest.class);

	
	@Test
	public void testPublicSessionTotal() {
		// Instantiate API client
		IStackExchangeApi client = StackExchangeUtils.getOAuthClient();
		
		String tagged = "java";
		String order = "desc";
		String max = "";
		String min = "";
		String sort = "creation";
		Integer fromdate = 1541091600;
		Integer todate = 1541092200; // 10 results
		Integer pagesize = 4;
		Integer page = 1;
		String filter = "total"; // the "total" filter will be removed after the 1st call (it is used to determine the total number of results required for pagination)
		String callback = "";
		String site = "stackoverflow";
	
		// Submit API query
		IDataSet<Questions> questions = client.getQuestions(tagged, order, max, min, sort, fromdate, todate, filter, callback, site);
		
		// Observe search repositories data set
		questions.observe()
		
			.doOnNext(result -> {
				LOG.info(questions.id() + " --- " + result.getQuestionId());
			})
			
			.doOnError(e -> {
				LOG.error(questions.id() + " --- " + "ERROR OCCURRED: " + e.getMessage());
			})
			
			.doOnComplete(() -> {
				LOG.info(questions.id() + " --- " + "COMPLETED !");
			})
			
			.blockingSubscribe();
	
		Long count = questions.observe().count().blockingGet();
		LOG.info("Final observe count of task " + questions.id() + ": " + count);
	}
	
	@Test
	public void testPublicSession() {
		// Instantiate API client
		IStackExchangeApi client = StackExchangeUtils.getOAuthClient();
		
		String tagged = "java";
		String order = "desc";
		String max = "";
		String min = "";
		String sort = "creation";
		Integer fromdate = 1541091600; // 11/01/2018 @ 5:00pm (UTC)
		Integer todate = 1541095200; // 11/01/2018 @ 6:00pm (UTC) --- 33 results
		Integer pagesize = 10;
		Integer page = 1;
		String filter = "";
		String callback = "";
		String site = "stackoverflow";
	
		// Submit API query
		IDataSet<Questions> questions = client.getQuestions(tagged, order, max, min, sort, fromdate, todate, filter, callback, site);
		
		// Observe search repositories data set
		questions.observe()
		
			.doOnNext(result -> {
				LOG.info(questions.id() + " --- " + result.getQuestionId());
			})
			
			.doOnError(e -> {
				LOG.error(questions.id() + " --- " + "ERROR OCCURRED: " + e.getMessage());
			})
			
			.doOnComplete(() -> {
				LOG.info(questions.id() + " --- " + "COMPLETED !");
			})
			
			.blockingSubscribe();

//		try {
//			Thread.sleep(10000);
//		} catch (InterruptedException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
		
		Long count = questions.observe().count().blockingGet();
		LOG.info("Final observe count of task " + questions.id() + ": " + count);

	}
	
	@Test
	public void testPublicSessionPosts() {
		// Instantiate API client
		IStackExchangeApi client = StackExchangeUtils.getOAuthClient();
		
		String tagged = "java";
		String order = "desc";
		String max = "";
		String min = "";
		String sort = "creation";
		Integer fromdate = 1541091600; // 11/01/2018 @ 5:00pm (UTC)
		Integer todate = 1541095200; // 11/01/2018 @ 6:00pm (UTC) --- 33 results
		Integer pagesize = 10;
		Integer page = 1;
		String filter = "";
		String callback = "";
		String site = "stackoverflow";
	
		// Submit API query
		IDataSet<Posts> posts = client.getPosts(order, max, min, sort, fromdate, todate, filter, callback, site);

		
		// Observe search repositories data set
		posts.observe()
		
			.doOnNext(result -> {
				LOG.info(posts.id() + " --- " + result.getPostId());
			})
			
			.doOnError(e -> {
				LOG.error(posts.id() + " --- " + "ERROR OCCURRED: " + e.getMessage());
			})
			
			.doOnComplete(() -> {
				LOG.info(posts.id() + " --- " + "COMPLETED !");
			})
			
			.blockingSubscribe();

//		try {
//			Thread.sleep(10000);
//		} catch (InterruptedException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
		
		Long count = posts.observe().count().blockingGet();
		LOG.info("Final observe count of task " + posts.id() + ": " + count);

	}
	
	public static void main(String args[]) {
		// Instantiate API client
		IStackExchangeApi client = StackExchangeUtils.getOAuthClient();//.getPublicClient()
		
		String tagged = "java";
		String order = "desc";
		String max = "";
		String min = "";
		String sort = "creation";
		Integer fromdate = 1541091600;
		Integer todate = 1541712821;// (now) //1541092200; // 10 results
		Integer pagesize = 5; // (many) // 2 pages
		Integer page = 1;
		String filter = "";//"total";
		String callback = "";
		String site = "stackoverflow";
	
		// Submit API query
		IDataSet<Questions> questions = client.getQuestions(tagged, order, max, min, sort, fromdate, todate, filter, callback, site);
		
		// Observe search repositories data set
		questions.observe()
		
			.doOnNext(result -> {
				LOG.info(questions.id() + " --- " + result.getQuestionId());
			})
			
			.doOnError(e -> {
				LOG.error(questions.id() + " --- " + "ERROR OCCURRED: " + e.getMessage());
			})
			
			.doOnComplete(() -> {
				LOG.info(questions.id() + " --- " + "COMPLETED !");
			})
			
			.blockingSubscribe();
	
		Long count = questions.observe().count().blockingGet();
		LOG.info("Final observe count of task " + questions.id() + ": " + count);
	}

}
