package org.eclipse.scava.crossflow.restmule.client.stackexchange.test;
import static org.junit.Assert.*;

import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.scava.crossflow.restmule.client.stackexchange.api.StackExchangeApi;
import org.eclipse.scava.crossflow.restmule.client.stackexchange.model.Questions;
import org.eclipse.scava.crossflow.restmule.client.stackexchange.api.IStackExchangeApi;
import org.eclipse.scava.crossflow.restmule.client.stackexchange.util.StackExchangeUtils;
import org.eclipse.scava.crossflow.restmule.core.data.IDataSet;
import org.junit.Ignore;
import org.junit.Test;

public class BasicApiQueryTest extends StackExchangeUtils {

	@SuppressWarnings("unused")
	private static final Logger LOG = LogManager.getLogger(BasicApiQueryTest.class);

	private static IStackExchangeApi api;

	@Test
	public void testPublicSession() {
		api = StackExchangeApi.create()
				.setSession(publicSession)
				.build();
		String tagged = "java";
		String order = "desc";
		String max = "";
		String min = "";
		String sort = "creation";
		Integer fromdate = 973107377;
		Integer todate = 1541100981;
		Integer pagesize = 10;
		Integer page = 1;
		String filter = "";
		String callback = "";
		String site = "stackoverflow";
		
		IDataSet<Questions> questions = api.getQuestions(tagged, order, max, min, sort, fromdate, todate, pagesize, page, filter, callback, site);
		
		questions.observe()
		.doOnNext(item -> LOG.info(item.getQuestionId()))
		.doOnError(e -> e.printStackTrace())
		.blockingSubscribe();
		
		// FIXME: response deserialization exception (see below)
		/*
		 * com.fasterxml.jackson.databind.JsonMappingException: Can not deserialize instance of java.util.ArrayList out of START_OBJECT token
 			at [Source: okhttp3.ResponseBody$BomAwareReader@24b801c5; line: 1, column: 1]
			at com.fasterxml.jackson.databind.JsonMappingException.from(JsonMappingException.java:148)
		 */
	
	}

}
