package org.eclipse.scava.crossflow.restmule.client.stackexchange.test;

import static org.junit.Assert.assertTrue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.scava.crossflow.restmule.client.stackexchange.model.Answers;
import org.eclipse.scava.crossflow.restmule.client.stackexchange.model.Posts;
import org.eclipse.scava.crossflow.restmule.client.stackexchange.model.QuestionsResponse;
import org.eclipse.scava.crossflow.restmule.client.stackexchange.model.Tags;
import org.eclipse.scava.crossflow.restmule.client.stackexchange.model.UsersResponse;
import org.eclipse.scava.crossflow.restmule.client.stackexchange.api.IStackExchangeApi;
import org.eclipse.scava.crossflow.restmule.client.stackexchange.api.StackExchangeApi;
import org.eclipse.scava.crossflow.restmule.client.stackexchange.util.StackExchangeUtils;
import org.eclipse.scava.crossflow.restmule.core.data.IData;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

public class StackExchangePublicClientTest extends StackExchangeUtils {

	private static final Logger LOG = LogManager.getLogger(StackExchangePublicClientTest.class);

	private static IStackExchangeApi api;

	@BeforeClass
	public static void setup() {
		StackExchangeUtils.setup();
//		StackExchangeUtils.forceLocalCaching(true);
		// Instantiate API client
		api = StackExchangeApi.create().setSession(publicSession).build();
	}

	@Test
	public void testSessionSmall() {
		
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
		IData<QuestionsResponse> result = api.getQuestionsQuestionsResponse(tagged, order, max, min, sort, fromdate, todate, filter, callback, site);
		
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
	public void testSessionGetQuestionsResponse() {

		String tagged = "java";
		String order = "desc";
		String max = null;// "";
		String min = null;// "";
		String sort = "creation";
		Integer fromdate = 1541091600; // 11/01/2018 @ 5:00pm (UTC)
		Integer todate = 1541095200; // 11/01/2018 @ 6:00pm (UTC) --- 33 results
		String filter = null;// "";
		String callback = null;// ""
		String site = "stackoverflow";

		// Submit API query
		IData<QuestionsResponse> result = api.getQuestionsQuestionsResponse(tagged, order, max, min, sort, fromdate,
				todate, filter, callback, site);

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
		assertTrue( count > 0);
		LOG.info("Final observe count of task " + result.hashCode() + ": " + count);

	}

	@Test
	public void testSessionGetQuestionsResponseLarge() {

		String tagged = "java";
		String order = "desc";
		String max = null;// "";
		String min = null;// "";
		String sort = "creation";
		Integer fromdate = 1541091600; // 11/01/2018 @ 5:00pm (UTC)
		Integer todate = 1541113200; // 11/01/2018 @ 11:00pm (UTC) --- ~138 results
		String filter = null;// "";
		String callback = null;// ""
		String site = "stackoverflow";

		// Submit API query
		IData<QuestionsResponse> result = api.getQuestionsQuestionsResponse(tagged, order, max, min, sort, fromdate,
				todate, filter, callback, site);

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
		assertTrue( count > 0);
		LOG.info("Final observe count of task " + result.hashCode() + ": " + count);

	}

	@Test
	public void testSessionPosts() {
		// Note that there are significant difference between StackExchange API version 2 and higher.
		// See "answer" type doc for more details: https://api.stackexchange.com/docs/types/answer
		
		String order = "desc";
		String max = null;// "";
		String min = null;// "";
		String sort = "creation";
		Integer fromdate = 1541091600; // 11/01/2018 @ 5:00pm (UTC)
		Integer todate = 1541095200; // 11/01/2018 @ 6:00pm (UTC) --- 33 results
		String filter = null;// "";
		String callback = null;// ""
		String site = "stackoverflow";

		// Submit API query
		IData<Posts> result = api.getPosts(order, max, min, sort, fromdate, todate, filter, callback, site);

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
		assertTrue( count > 0);
		LOG.info("Final observe count of task " + result.hashCode() + ": " + count);

	}

	@Test
	public void testSessionTags() {

		String inname = "own";
		String order = "desc";
		String max = null;// "";
		String min = null;// "";
		String sort = "popular";
		Integer fromdate = 1535760000;
		Integer todate = 1542412800;
		String filter = null;// "";
		String callback = null;// ""
		String site = "stackoverflow";

		// Submit API query
		IData<Tags> result = api.getTags(inname, order, max, min, sort, fromdate, todate, filter, callback, site);

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
		assertTrue( count > 0);
		LOG.info("Final observe count of task " + result.hashCode() + ": " + count);

	}

	@Test
	public void testSessionUsers() {

		String inname = "own";
		String order = "desc";
		String max = null;// "";
		String min = null;// "";
		String sort = "reputation";
		Integer fromdate = 1535760000;
		Integer todate = 1541095200;
		String filter = null;// "";
		String callback = null;// ""
		String site = "stackoverflow";

		// Submit API query
		IData<UsersResponse> result = api.getUsersUsersResponse(inname, order, max, min, sort, fromdate, todate, filter,
				callback, site);

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
		assertTrue( count > 0);
		LOG.info("Final observe count of task " + result.hashCode() + ": " + count);

	}

	@Test
	public void testSessionAnswers() {

		String order = "desc";
		String max = null;// "";
		String min = null;// "";
		String sort = "votes";
		Integer fromdate = 1535760000;
		Integer todate = 1535765000;
		String filter = null;// "";
		String callback = null;// ""
		String site = "stackoverflow";

		// Submit API query
		IData<Answers> result = api.getAnswers(order, max, min, sort, fromdate, todate, filter, callback, site);

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
		assertTrue( count > 0);
		LOG.info("Final observe count of task " + result.hashCode() + ": " + count);

	}

	public static void main(String args[]) {

		String tagged = "java";
		String order = "desc";
		String max = null;// "";
		String min = null;// "";
		String sort = "creation";
		Integer fromdate = 1541091600;
		Integer todate = 1541113200; // 11/01/2018 @ 11:00pm (UTC) --- more than 100 results
		String filter = "total";// "total";
		String callback = null;// ""
		String site = "stackoverflow";

		// Submit API query
		IData<QuestionsResponse> result = api.getQuestionsQuestionsResponse(tagged, order, max, min, sort, fromdate,
				todate, filter, callback, site);

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
		assertTrue( count > 0);
		LOG.info("Final observe count of task " + result.hashCode() + ": " + count);
	}

}
