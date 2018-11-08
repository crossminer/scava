package org.eclipse.scava.crossflow.restmule.client.github.test;

import java.util.concurrent.ExecutionException;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;

import org.apache.http.nio.protocol.HttpAsyncResponseConsumer;

public class Utils {

	private static final String BLOB_SANITIZED = "/";
	private static final String BLOB = "/blob/";
	private static final String GITHUB = "https://github.com/";
	private static final String RAW_GITHUB = "https://raw.githubusercontent.com/";
	
	public static boolean isValid(String url, String keyword, CloseableHttpAsyncClient asyncClient){		
		try {
			ValidResourceCallback callback = new ValidResourceCallback(keyword);
			asyncClient.execute(new HttpGet(parse(url)), callback).get();
			return callback.isValid();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static String parse(String url){
		return url.replace(GITHUB, RAW_GITHUB).replace(BLOB, BLOB_SANITIZED);
	}
}
