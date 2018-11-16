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
		String max = null;//"";
		String min = null;//"";
		String sort = "creation";
		Integer fromdate = 1541091600;
		Integer todate = 1541092200; // 10 results
		Integer pagesize = 4;
		Integer page = 1;
		String filter = "total"; // the "total" filter will be removed after the 1st call (it is used to determine the total number of results required for pagination)
		String callback = null;//"";
		String site = "stackoverflow";
	
		// Submit API query
		IData<QuestionsResponse> result = client.getQuestionsQuestionsResponse(tagged, order, max, min, sort, fromdate, todate, filter, callback, site);
		
		// Observe search repositories result set
		result.observe()
		
			.doOnNext(resultItem -> {
				LOG.info(result.hashCode() + " --- " + resultItem.toString());
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
	public void testPublicSessionTotalLarge() {
		// Instantiate API client
		IStackExchangeApi client = StackExchangeUtils.getOAuthClient();
		
		String tagged = "java";
		String order = "desc";
		String max = null;//"";
		String min = null;//"";
		String sort = "creation";
		Integer fromdate = 1541091600;
		Integer todate = 1541113200; // 11/01/2018 @ 11:00pm (UTC) --- more than 100 results
		Integer pagesize = 4;
		Integer page = 1;
		String filter = "total"; // the "total" filter will be removed after the 1st call (it is used to determine the total number of results required for pagination)
		String callback = null;//"";
		String site = "stackoverflow";
	
		// Submit API query
		IData<QuestionsResponse> result = client.getQuestionsQuestionsResponse(tagged, order, max, min, sort, fromdate, todate, filter, callback, site);
		
		// Observe search repositories result set
		result.observe()
		
			.doOnNext(resultItem -> {
				LOG.info(result.hashCode() + " --- " + resultItem.toString());
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
		Integer pagesize = 10;
		Integer page = 1;
		String filter = null;//"";
		String callback = null;//""
		String site = "stackoverflow";
	
		// Submit API query
		IData<QuestionsResponse> result = client.getQuestionsQuestionsResponse(tagged, order, max, min, sort, fromdate, todate, filter, callback, site);

		
		// Observe search repositories result set
		result.observe()
		
			.doOnNext(resultItem -> {
				System.out.println("doOnNext.result="+resultItem.toString());
//				LOG.info(questions.id() + " --- " + result.getQuestionId());
			})
			
			.doOnError(e -> {
				LOG.error(result.hashCode() + " --- " + "ERROR OCCURRED: " + e.getMessage());
			})
			
			.doOnComplete(() -> {
				LOG.info(result.hashCode() + " --- " + "COMPLETED !");
			})
			
			.blockingSubscribe();

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
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
		Integer todate = 1541113200; // 11/01/2018 @ 11:00pm (UTC) --- more than 100 results
		Integer pagesize = 10;
		Integer page = 1;
		String filter = null;//"";
		String callback = null;//""
		String site = "stackoverflow";
	
		// Submit API query
		IData<QuestionsResponse> result = client.getQuestionsQuestionsResponse(tagged, order, max, min, sort, fromdate, todate, filter, callback, site);

		
		// Observe search repositories result set
		result.observe()
		
			.doOnNext(resultItem -> {
				System.out.println("doOnNext.result="+resultItem.toString());
//				LOG.info(questions.id() + " --- " + result.getQuestionId());
			})
			
			.doOnError(e -> {
				LOG.error(result.hashCode() + " --- " + "ERROR OCCURRED: " + e.getMessage());
			})
			
			.doOnComplete(() -> {
				LOG.info(result.hashCode() + " --- " + "COMPLETED !");
			})
			
			.blockingSubscribe();

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
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
		Integer pagesize = 10;
		Integer page = 1;
		String filter = null;//"";
		String callback = null;//""
		String site = "stackoverflow";
	
		// Submit API query
		IData<Posts> result = client.getPosts(order, max, min, sort, fromdate, todate, filter, callback, site);

		
		// Observe search repositories result set
		result.observe()
		
			.doOnNext(resultItem -> {
				LOG.info(result.hashCode() + " --- " + resultItem.toString());
			})
			
			.doOnError(e -> {
				LOG.error(result.hashCode() + " --- " + "ERROR OCCURRED: " + e.getMessage());
			})
			
			.doOnComplete(() -> {
				LOG.info(result.hashCode() + " --- " + "COMPLETED !");
			})
			
			.blockingSubscribe();

		try {
			Thread.sleep(10000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
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
		Integer pagesize = 10;
		Integer page = 1;
		String filter = null;//"";
		String callback = null;//""
		String site = "stackoverflow";
		
		// Submit API query
		IData<Tags> result = client.getTags(inname, order, max, min, sort, fromdate, todate, filter, callback, site);
		
		// Observe search repositories result set
		result.observe()
		
			.doOnNext(resultItem -> {
				LOG.info(result.hashCode() + " --- " + resultItem.toString());
			})
			
			.doOnError(e -> {
				LOG.error(result.hashCode() + " --- " + "ERROR OCCURRED: " + e.getMessage());
			})
			
			.doOnComplete(() -> {
				LOG.info(result.hashCode() + " --- " + "COMPLETED !");
			})
			
			.blockingSubscribe();

		try {
			Thread.sleep(10000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
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
		Integer pagesize = 10;
		Integer page = 1;
		String filter = null;//"";
		String callback = null;//""
		String site = "stackoverflow";
		
		// Submit API query
		IData<UsersResponse> result = client.getUsersUsersResponse(inname, order, max, min, sort, fromdate, todate, filter, callback, site);
		
		// Observe search repositories result set
		result.observe()
		
			.doOnNext(resultItem -> {
				LOG.info(result.hashCode() + " --- " + resultItem.toString());
			})
			
			.doOnError(e -> {
				LOG.error(result.hashCode() + " --- " + "ERROR OCCURRED: " + e.getMessage());
			})
			
			.doOnComplete(() -> {
				LOG.info(result.hashCode() + " --- " + "COMPLETED !");
			})
			
			.blockingSubscribe();

		try {
			Thread.sleep(10000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
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
		Integer pagesize = 10;
		Integer page = 1;
		String filter = null;//"";
		String callback = null;//""
		String site = "stackoverflow";
		
		// Submit API query
		IData<Answers> result = client.getAnswers(order, max, min, sort, fromdate, todate, filter, callback, site);
		
		// Observe search repositories result set
		result.observe()
		
			.doOnNext(resultItem -> {
				LOG.info(result.hashCode() + " --- " + resultItem.toString());
			})
			
			.doOnError(e -> {
				LOG.error(result.hashCode() + " --- " + "ERROR OCCURRED: " + e.getMessage());
			})
			
			.doOnComplete(() -> {
				LOG.info(result.hashCode() + " --- " + "COMPLETED !");
			})
			
			.blockingSubscribe();

		try {
			Thread.sleep(10000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
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
		Integer todate = 1541712821;// (now) //1541092200; // 10 results
		Integer pagesize = 5; // (many) // 2 pages
		Integer page = 1;
		String filter = null;//"";
		String callback = null;//""
		String site = "stackoverflow";
	
		// Submit API query
		IData<QuestionsResponse> result = client.getQuestionsQuestionsResponse(tagged, order, max, min, sort, fromdate, todate, filter, callback, site);
		
		// Observe search repositories result set
		result.observe()
		
			.doOnNext(resultItem -> {
				LOG.info(result.hashCode() + " --- " + resultItem.toString());
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
