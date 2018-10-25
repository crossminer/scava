package org.eclipse.scava.crossflow.restmule.client.github.test;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.util.EntityUtils;

public class ValidResourceCallback implements FutureCallback<HttpResponse> {

	private AtomicBoolean booleanResult;
	private String keyword;
	
	public ValidResourceCallback(String keyword){
		this.booleanResult = new AtomicBoolean();
		this.booleanResult.set(false);
		this.keyword = keyword;
	}
	
	@Override public void failed(Exception ex) {}
	@Override public void cancelled() {}
	@Override public void completed(HttpResponse result) {
		try {
			String body = EntityUtils.toString(result.getEntity());
			booleanResult.set(body.contains(keyword));
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	
	
	public boolean isValid(){
		return booleanResult.get();
	}

}