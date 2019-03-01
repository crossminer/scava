package org.eclipse.scava.workflow.restmule.core.util;

import java.io.IOException;

import org.apache.http.HttpStatus;
import org.eclipse.scava.workflow.restmule.core.session.ISession;

import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

/**
 * 
 * {@link OkHttpUtil}
 * <p>
 * @version 1.0.0
 *
 */
public class OkHttpUtil {

	public static ResponseBody cloneResponseBody(final ResponseBody body){		 
		final ResponseBody responseBody = body;
		BufferedSource source = responseBody.source();
		try {
			source.request(Long.MAX_VALUE);
		} catch (IOException e) {
			e.printStackTrace();
		}
		final Buffer bufferClone = source.buffer().clone();
		return ResponseBody.create(responseBody.contentType(), responseBody.contentLength(), bufferClone);
	}

	public static Response response(Request request, String body, String contentType, Headers headers){
		return new Response.Builder()
				.request(request)
				.protocol(Protocol.HTTP_2)
				.code(HttpStatus.SC_OK)
				.headers(headers)
				.message("Loaded from Cache")
				.body(ResponseBody.create(MediaType.parse(contentType), body))
				.build();
	}
	
	public static Response clone(Response response){
		ResponseBody body = OkHttpUtil.cloneResponseBody(response.body());
		return new Response.Builder()
				.request(response.request())
				.protocol(response.protocol())
				.code(response.code())
				.headers(response.headers())
				.message(response.message())
				.body(body)
				.build();
	}

	public static String path(Request request) {
		HttpUrl url = request.url();
		String query = (url.query()!=null) ? "?" + url.encodedQuery() : "";
		return String.valueOf(new String(url.encodedPath() + query).hashCode());
	}
	
	public static Headers headers(ISession session, Request request, Headers headers) {
		Headers.Builder headerBuilder = headers(session, headers);
		for (String n : request.headers().names()) {
			headerBuilder = headerBuilder.add(n, request.header(n));
		}
		return headerBuilder.build();

	}

	public static Headers headers(ISession session, Response response, Headers headers) {
		Headers.Builder headerBuilder = headers(session, headers);
		if (response != null) {
			for (String n : response.headers().names()) {
				headerBuilder = headerBuilder.add(n, response.header(n));
			}
		}
		return headerBuilder.build();

	}

	private static Headers.Builder headers(ISession session, Headers headers) {
		Headers.Builder headerBuilder = new Headers.Builder();
		if (session != null) {
			for (String n : session.getHeaders().names()) {
				headerBuilder = headerBuilder.add(n, session.getHeaders().get(n));
			}
		}
		if (headers != null) {
			for (String n : headers.names()) {
				headerBuilder = headerBuilder.add(n, headers.get(n));
			}
		}
		return headerBuilder;
	}
	
}
