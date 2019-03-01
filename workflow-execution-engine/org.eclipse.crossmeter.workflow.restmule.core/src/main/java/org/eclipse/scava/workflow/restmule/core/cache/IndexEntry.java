package org.eclipse.scava.workflow.restmule.core.cache;

import java.io.IOException;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.scava.workflow.restmule.core.session.ISession;
import org.eclipse.scava.workflow.restmule.core.util.OkHttpUtil;

import com.fasterxml.jackson.core.io.JsonStringEncoder;
import com.fasterxml.jackson.databind.ObjectMapper;

import okhttp3.Request;
import okhttp3.Response;;

/**
 * 
 * {@link IndexEntry}
 * <p>
 * @version 1.0.0
 *
 */
@SuppressWarnings("unused")
public class IndexEntry implements Indexable {

	protected String id;
	protected String etag;
	protected String lastModified;
	protected Date timeout; 
	protected String contentType;
	protected String link;
	
	private IAdapter<?> adapter;
	
	protected static final int STALE_DAYS = 5;
	private static final Logger LOG = LogManager.getLogger(IndexEntry.class);
	
	public IndexEntry(Response response, ISession session){
		this.id = IndexEntry.getId(response, session);
		
		String etag = response.header(ETAG);
		this.etag = (etag == null) ? "" : etag;
		
		String lastModif = response.header(LAST_MODIFIED);
		this.lastModified = (lastModif == null) ? "" : lastModif;
		this.timeout = staleTime();
		this.contentType = response.body().contentType().toString();
		this.link = response.header(LINK);
		this.adapter = new ResponseAdapter(response);
	}
	
	private Date staleTime(){
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date()); 
		cal.add(Calendar.DATE, STALE_DAYS);
		return cal.getTime();
	}
	
	/** Getters */
	
	@Override
	public String getLastModified() {
		return this.lastModified;
	}

	@Override
	public String getEtag() {
		return this.etag;
	}
	@Override
	public String getContents() {
		JsonStringEncoder encoder = JsonStringEncoder.getInstance();
		return new String(encoder.quoteAsString(this.adapter.body()));
	}

	@Override
	public String getId() {
		return this.id;
	}

	@Override
	public Date getTimeout() {
		return this.timeout;
	}

	@Override
	public IAdapter<?> getResponseAdapter() {
		return this.adapter;
	}

	@Override
	public String contentType() {
		return this.contentType;
	}

	@Override
	public String link() {
		return this.link;
	}

	/** Static Methods */
	
	public static String getId(Request request, ISession session) {
		return session.hash() + OkHttpUtil.path(request);
	}

	public static String getId(Response response, ISession session) {
		return IndexEntry.getId(response.request(), session); 
	}
}
