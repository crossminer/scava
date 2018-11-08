package org.eclipse.scava.crossflow.restmule.core.data.okhttp3.wrappers;

import java.io.Serializable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.RequestBody;

public class OkHttp3Request implements Serializable {

    private static final Logger LOG = LogManager.getLogger(OkHttp3Request.class);

    public RequestBody getBody() {
        if ( body != null ) {
            return body.get();
        }
        LOG.debug("No body available to be returned for this request.");
        return null;
    }

    public Object getTag() {
        if ( tagString != null ) {
            return tagString;
        }
        LOG.debug("No tag available to be returned for this request.");
        return null;
    }

    public Headers getHeaders() {
        if ( headers != null ) {
            return headers.get();
        }
        LOG.debug("No headers available to be returned for this request.");
        return null;
    }

    public String getMethod() {
        return method;
    }

    public HttpUrl getUrl() {
        return HttpUrl.parse(urlString);
    }

    public OkHttp3RequestBody body;
    public String tagString;
    public OkHttp3Headers headers;
    public String method;
    public String urlString;

    public OkHttp3Request() {
        // default constructor for json framework
    }

    public OkHttp3Request(Request request) {
        this.urlString = request.url().toString();
        this.method = request.method();
        this.tagString = request.tag().toString();

        if ( request.headers() != null ) {
            this.headers = new OkHttp3Headers(request.headers());
        }

        if ( request.body() != null ) {
            this.body = new OkHttp3RequestBody(request.body());
        }
    }

    public Request get() {
        Request.Builder requestBuilder = new Request.Builder();

        requestBuilder.tag(tagString);
        requestBuilder.url(urlString);

        if ( headers != null ) {
            requestBuilder.headers(headers.get());
        }

        if ( method.equals("GET") || method.equals("HEAD") ) {
            requestBuilder.method(method, null);
        }

        if ( body != null ) {
            requestBuilder.put(body.get());
            if ( !method.equals("GET") && !method.equals("HEAD") ) {
                requestBuilder.method(method, body.get());
            }
        }

        return requestBuilder.build();
    }

}
