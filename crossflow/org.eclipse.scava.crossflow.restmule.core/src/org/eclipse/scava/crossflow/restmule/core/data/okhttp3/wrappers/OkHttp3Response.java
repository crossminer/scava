package org.eclipse.scava.crossflow.restmule.core.data.okhttp3.wrappers;

import java.io.IOException;
import java.io.Serializable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import okhttp3.Protocol;
import okhttp3.Response;

public class OkHttp3Response implements Serializable  {

    private static final Logger LOG = LogManager.getLogger(OkHttp3Response.class);

    public String protocolString;
    public int code;
    public String message;

    public OkHttp3Headers responseHeaders;
    public OkHttp3Handshake handshake;
    public long sentRequestMillis;
    public long receivedResponseMillis;

    public OkHttp3Request request;

    public OkHttp3ResponseBody responseBody;

    public OkHttp3Response networkResponse;
    public OkHttp3Response cacheResponse;
    public OkHttp3Response priorResponse;


    public OkHttp3Headers getResponseHeaders() {
        return responseHeaders;
    }

    public OkHttp3Handshake getHandshake() {
        return handshake;
    }

    public OkHttp3Request getRequest() {
        return request;
    }

    public OkHttp3ResponseBody getResponseBody() {
        return responseBody;
    }

    public OkHttp3Response getNetworkResponse() {
        return networkResponse;
    }

    public OkHttp3Response getPriorResponse() {
        return priorResponse;
    }

    public String getProtocolString() {
        return protocolString;
    }

    public OkHttp3Response getCacheResponse() {
        return cacheResponse;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public long getSentRequestMillis() {
        return sentRequestMillis;
    }

    public long getReceivedResponseMillis() {
        return receivedResponseMillis;
    }

    public OkHttp3Response() {
        // default constructor for json framework
    }

    public OkHttp3Response(Response response) {
        if ( response.request() != null ) {
            this.request = new OkHttp3Request(response.request());
        }

        this.protocolString = response.protocol().toString();
        this.code = response.code();
        this.message = response.message();

        if ( response.handshake() != null ) {
            this.handshake = new OkHttp3Handshake(response.handshake());
        }

        if ( response.headers() != null ) {
            this.responseHeaders = new OkHttp3Headers(response.headers());
        }

        if ( response.body() != null ) {
            this.responseBody = new OkHttp3ResponseBody(response.body());
        }

        if ( response.networkResponse() != null ) {
            this.networkResponse = new OkHttp3Response(response.networkResponse());
        }

        if ( response.cacheResponse() != null ) {
            this.cacheResponse = new OkHttp3Response(response.cacheResponse());
        }

        if ( response.priorResponse() != null ) {
            this.priorResponse = new OkHttp3Response(response.priorResponse());
        }

        this.sentRequestMillis = response.sentRequestAtMillis();
        this.receivedResponseMillis = response.receivedResponseAtMillis();
    }

    public Response get() {
        Response.Builder responseBuilder = new Response.Builder();

        if ( this.getRequest() != null ) {
            responseBuilder.request(this.getRequest().get());
        }

        responseBuilder.protocol(getProtocol());

        responseBuilder.code(getCode());

        responseBuilder.message(this.getMessage());

        if ( this.getHandshake() != null ) {
            responseBuilder.handshake(this.getHandshake().get());
        }

        if ( this.getResponseHeaders() != null ) {
            responseBuilder.headers(this.getResponseHeaders().get());
        }

        if ( this.getResponseBody() != null ) {
            responseBuilder.body(this.getResponseBody().get());
        }

        if ( this.getNetworkResponse() != null ) {
            responseBuilder.networkResponse(this.getNetworkResponse().get());
        }

        if ( this.getCacheResponse() != null ) {
            responseBuilder.cacheResponse(this.getCacheResponse().get());
        }

        if ( this.getPriorResponse() != null ) {
            responseBuilder.priorResponse(this.getPriorResponse().get());
        }

        responseBuilder.sentRequestAtMillis(sentRequestMillis);
        responseBuilder.receivedResponseAtMillis(receivedResponseMillis);

        return responseBuilder.build();
    }

    public Protocol getProtocol() {
        Protocol protocol = null;
        if ( this.getProtocolString() != null ) {
            try {
                protocol = Protocol.get(this.getProtocolString());
            } catch (IOException e) {
                LOG.error("Unable to create valid Protocol instance from: " + this.getProtocolString());
                LOG.error(e.getMessage());
            }
        }
        return protocol;
    }
}
