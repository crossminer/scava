package org.eclipse.scava.crossflow.restmule.core.data.okhttp3.wrappers;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.BufferedSink;
import okio.Okio;
import okio.Sink;

public class OkHttp3RequestBody implements Serializable {

    // RequestBody (non serializable; bytes(), byteStream(), ...) <-- okHttp3Request.getRequestBody()
    //      RequestBody <-- OkHttp3RequestBody.get()

    private static final Logger LOG = LogManager.getLogger(OkHttp3RequestBody.class);

    String contentType;
    JsonElement content;

    public OkHttp3RequestBody() {
        // default constructor for json framework
    }

    public OkHttp3RequestBody(RequestBody requestBody) {
        this.contentType = requestBody.contentType().toString();
        File tempContentFile = null;
        try {
            // TODO: THIS IS NOT TESTED (Request objects don't have RequestBody objects, at the least in our tested scenarios)!
            tempContentFile = File.createTempFile("OkHttp3RequestBody-", "-content");

            //            RequestBody tempRequestBody = RequestBody.create(MediaType.parse(this.contentType), this.tempContentFile);

            // TODO: write this to a okhttp3 BufferedSink

            try (Sink fileSink = Okio.sink(tempContentFile);
                 BufferedSink bufferedSink = Okio.buffer(fileSink)) {

                requestBody.writeTo(bufferedSink);

                fileSink.flush();
                //                for (Map.Entry<String, String> entry : System.getenv().entrySet()) {
                //                    bufferedSink.writeUtf8(entry.getKey());
                //                    bufferedSink.writeUtf8("=");
                //                    bufferedSink.writeUtf8(entry.getValue());
                //                    bufferedSink.writeUtf8("\n");
                //                }

            }

            //            tempRequestBody.

            //            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            //            BufferedSink bufferedSink = new Bu

            //            this.content = requestBody.writeTo(outputStream);
        } catch (IOException e) {
            LOG.error("Unable to parse response body content.");
            LOG.error(e.getMessage());
        } finally {
            if (tempContentFile != null) {
                try {
                    JsonParser jsonParser = new JsonParser();
                    content = jsonParser.parse(new String(Files.readAllBytes(tempContentFile.toPath())));
                } catch (IOException e) {
                    LOG.error("Failed to read RequestBody content from temporary file.");
                    LOG.error(e.getMessage());
                }
                tempContentFile.delete();
            }
        }
    }

    public RequestBody get() {
        return RequestBody.create(MediaType.parse(this.contentType), content.toString());
    }

}
