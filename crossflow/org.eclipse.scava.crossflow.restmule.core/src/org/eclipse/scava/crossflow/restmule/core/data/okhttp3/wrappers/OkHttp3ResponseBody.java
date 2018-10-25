package org.eclipse.scava.crossflow.restmule.core.data.okhttp3.wrappers;

import java.io.IOException;
import java.io.Serializable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import okhttp3.MediaType;
import okhttp3.ResponseBody;

public class OkHttp3ResponseBody implements Serializable {

    // ResponseBody (non serializable; bytes(), byteStream(), ...) <-- okHttp3Response.getResponseBody()
    //      ResponseBody <-- OkHttp3ResponseBody.get()

    private static final Logger LOG = LogManager.getLogger(OkHttp3ResponseBody.class);

    public String contentType;
    public JsonElement content;

    public String getContentType() {
        return contentType;
    }

    public JsonElement getContent() {
        return content;
    }

    public OkHttp3ResponseBody() {
        // default constructor for json framework
    }

    public OkHttp3ResponseBody(ResponseBody responseBody) {
        this.contentType = responseBody.contentType().toString();
        try {
            JsonParser jsonParser = new JsonParser();
            content = jsonParser.parse(responseBody.string());
        } catch (IOException e) {
            LOG.error("Unable to parse response body content.");
            LOG.error(e.getMessage());
        }
    }

    public ResponseBody get() {
        return ResponseBody.create(MediaType.parse(contentType), content.toString());
    }

}
