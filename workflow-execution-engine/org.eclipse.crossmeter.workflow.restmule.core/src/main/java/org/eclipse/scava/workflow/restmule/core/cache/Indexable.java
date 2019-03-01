package org.eclipse.scava.workflow.restmule.core.cache;

import java.util.Date;

import org.apache.http.HttpHeaders;

/**
 * 
 * {@link Indexable}
 * <p>
 * @version 1.0.0
 *
 */
public interface Indexable {

	final static String ID = "id";
	final static String TIMEOUT = "timeout";
	final static String CONTENTS = "contents";
	final static String ETAG = HttpHeaders.ETAG;
	final static String LAST_MODIFIED = HttpHeaders.LAST_MODIFIED;
	final static String CONTENT_TYPE = "contentType";
	final static String LINK = "Link"; // FIXME
	
	String getId();
	Date getTimeout();
	String getContents(); 
	String getEtag();
	String getLastModified();
	
	IAdapter<?> getResponseAdapter();
	String contentType(); 
	String link();

}
