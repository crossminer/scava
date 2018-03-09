/*******************************************************************************
 * Copyright (c) 2018 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.platform.bugtrackingsystem.sourceforge.api;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.Iterator;

import org.eclipse.scava.platform.delta.bugtrackingsystem.BugTrackingSystemComment;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.GetRequest;

public class SourceForgeTrackerRestClient {

	private int pageSize = SourceForgeConstants.DEFAULT_PAGE_SIZE;

	private static final ObjectMapper objectMapper;
	private static final ObjectReader searchReader;
	private static final ObjectReader ticketReader;

	static {
		// http://wiki.fasterxml.com/JacksonBestPracticesPerformance
		objectMapper = new ObjectMapper();
		objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

		SimpleModule module = new SimpleModule();
		module.addDeserializer(SourceForgeSearchResult.class,
				new SourceForgeSearchResultDeserialiser());
		module.addDeserializer(SourceForgeTicket.class,
				new SourceForgeTicketDeserialiser());
		module.addDeserializer(SourceForgeComment.class,
				new SourceForgeCommentDeserialiser());
		objectMapper.registerModule(module);

		searchReader = objectMapper.reader(SourceForgeSearchResult.class);
		ticketReader = objectMapper.reader(SourceForgeTicket.class);
	}

	private String trackerUrl;
	private String login;
	private String password;

	public SourceForgeTrackerRestClient(String trackerUrl)
			throws URISyntaxException {
		if (!trackerUrl.endsWith("/")) {
			trackerUrl += '/';
		}

		this.trackerUrl = trackerUrl;
	}

	public void shutdown() throws IOException {
		Unirest.shutdown();
	}
	
	public void setCredentials(String login, String password) {
		this.login = login;
		this.password = password;
	}

	/**
	 * 
	 * Returns the tickets that were either updated or created between the given
	 * dates
	 * 
	 * @param after
	 * @param before
	 * @return
	 * @throws UnirestException
	 * @throws JsonParseException
	 * @throws IOException
	 */
	// TODO think we don't need the created clauses in here, for our caching purposes.
	public Iterator<SourceForgeTicket> getTickets(Date after, Date before)
			throws UnirestException, JsonParseException, IOException {

		String query = SourceForgeConstants.LAST_MODIFIED_QUERY_FIELD + ":["
				+ printDate(after) + " TO " + printDate(before) + "] OR "
				+ SourceForgeConstants.CREATED_DATE_QUERY_FIELD + ":["
				+ printDate(after) + " TO " + printDate(before) + "]";

		return new SourceForgeTicketIterator(this, getTicketIds(query));
	}

	public Iterator<Integer> getTicketIds(String query) {
		return new SourceForgeTicketIdIterator(this, query);
	}

	public SourceForgeTicket getTicket(int id) throws JsonParseException,
			JsonMappingException, IOException, UnirestException {
		String url = trackerUrl + id;
		GetRequest getRequest = Unirest.get(url);
		HttpResponse<InputStream> response = executeRestCall(getRequest);
		return ticketReader.readValue(response.getRawBody());
	}

	public SourceForgeComment getComment(int ticketId, String slug)
			throws JsonParseException, JsonMappingException, IOException,
			UnirestException {
		SourceForgeTicket ticket = getTicket(ticketId);
		if (null != ticket) {
			for (BugTrackingSystemComment comment : ticket.getComments()) {
				if (comment.getCommentId().equals(slug)) {
					return (SourceForgeComment) comment;
				}
			}
		}
		return null;
	}

	public SourceForgeSearchResult search(String query, int page)
			throws JsonProcessingException, IOException, UnirestException {
		return search(query, page, pageSize);
	}

	public SourceForgeSearchResult search(String query, int page, int pageSize)
			throws JsonProcessingException, IOException, UnirestException {
		String url = trackerUrl + "search";

		GetRequest getRequest = Unirest.get(url).field("q", query)
				.field("page", page).field("limit", pageSize);
		HttpResponse<InputStream> response = executeRestCall(getRequest);
		return searchReader.readValue(response.getRawBody());
	}

	protected HttpResponse<InputStream> executeRestCall(GetRequest getRequest)
			throws UnirestException {
		if (null != login && null != password) {
			getRequest = getRequest.basicAuth(login, password);
		}
		
		getRequest.header("accept", "application/json");
		HttpResponse<InputStream> response = getRequest.asBinary();
		if (response.getCode() != 200) {
			throw new RuntimeException("Error executing call to webservice. ["
					+ response.getCode() + "," + getRequest.getUrl() + "]");
		}

		return response;
	}

	protected String printDate(Date date) {
		if (date == null) {
			return "*";
		} else {
			return SourceForgeConstants.REQUEST_DATE_FORMATTER.withZone(
					DateTimeZone.UTC).print(new DateTime(date));
		}
	}

	public static void main(String[] args) throws URISyntaxException,
			JsonParseException, UnirestException, IOException {
		SourceForgeTrackerRestClient sourceforge = new SourceForgeTrackerRestClient(
				"http://sourceforge.net/rest/p/soapui/bugs");
		DateTime date = new DateTime().withDate(2014, 6, 16);
		// for (SourceForgeTicket ticket : sourceforge.getTickets(date.toDate(),
		// date.plusDays(1).toDate())) {
		// Should return 1 bug, with id 637.
		// System.out.println(ticket.getBugId());
		// System.out.println(ticket.getComments().size());
		// }

		// Should have 1 attachment and 4 labels
		SourceForgeTicket ticket = sourceforge.getTicket(663);
		System.out.println(ticket.getBugId());
		System.out.println(ticket.getComments().size());
		System.out.println(ticket.getAttachments().length);
		System.out.println(ticket.getAttachments().toString());
		System.out.println(ticket.getLabels().length);
		System.out.println(ticket.getLabels().toString());

		sourceforge.shutdown();

	}

}
