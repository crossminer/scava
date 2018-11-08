package org.eclipse.scava.crossflow.restmule.core.data.okhttp3.wrappers;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import okhttp3.Headers;

public class OkHttp3Headers implements Serializable {

    // Headers (non serializable; toMultimap()) <-- okHttp3Response.getResponseHeaders()
    // Headers <-- OkHttp3Headers.get()
    //      Map<String, List<String>> <-- Headers <-- Builder.build() <-- Headers.add(String name, String value)

    private static final Logger LOG = LogManager.getLogger(OkHttp3Headers.class);

    public Map<String, List<String>> namesAndValues = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

    public OkHttp3Headers(Headers headers) {
        this.namesAndValues = headers.toMultimap();
    }

    public OkHttp3Headers() {
        // default constructor for json framework
    }

    public Headers get() {
        Headers.Builder headersBuilder = new Headers.Builder();
        for(Map.Entry<String, List<String>> entry : namesAndValues.entrySet()) {
            String key = entry.getKey();
            for (String value : entry.getValue()) {
                headersBuilder.add(key, value);
            }
        }
        return headersBuilder.build();
    }

}
