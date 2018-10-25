package org.eclipse.scava.crossflow.restmule.core.interceptor;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

public class CacheControlInterceptor {

    public static final Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR = new Interceptor() {
        @Override public Response intercept(Chain chain) throws IOException {
            Response originalResponse = chain.proceed(chain.request());
            return originalResponse.newBuilder().header("Cache-Control", "max-age=" + 7 * 60 * 60 * 24).build();
        }
    };
	
}
