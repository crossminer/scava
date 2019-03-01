package org.eclipse.scava.workflow.restmule.core.cache;

import java.io.IOException;
import java.io.Serializable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.scava.workflow.restmule.core.util.OkHttpUtil;

import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.http.HttpHeaders;

/**
 * 
 * {@link ResponseAdapter}
 * <p>
 * @version 1.0.0
 *
 */
public class ResponseAdapter implements Serializable, IAdapter<Response> {

	private static final Logger LOG = LogManager.getLogger(ResponseAdapter.class);

	private static final long serialVersionUID = 1878277700160292732L;

	private static final String CONTENT_TYPE = org.apache.http.HttpHeaders.CONTENT_TYPE;

	private String contentType;
	private long contentLength;
	private Response response;

	public String getContentType() {
		return this.contentType;
	}

	public long getContentLength() {
		return this.contentLength;
	}

	public ResponseBody getResponseBody() {
		return clonedResponseBody();
	}

	public ResponseAdapter(Response response){
		this.contentLength = HttpHeaders.contentLength(response.headers());
		this.contentType = 	response.headers().get(CONTENT_TYPE);	
		this.response = response;
	}

	@Override
	public String body() {
		try {
			return clonedResponseBody().string();
		} catch (IOException e) {
			LOG.info("Could not read body or convert to string");
			return "";
		}
	}

	@Override
	public Response response(){
		return new Response.Builder()
				.request(response.request())
				.protocol(response.protocol())
				.headers(response.headers())
				.code(response.code())
				.message(response.message())
				.body(clonedResponseBody())
				.build();
	}

	private ResponseBody clonedResponseBody() {
		final ResponseBody body = this.response.body();
		return OkHttpUtil.cloneResponseBody(body);
	}

}
