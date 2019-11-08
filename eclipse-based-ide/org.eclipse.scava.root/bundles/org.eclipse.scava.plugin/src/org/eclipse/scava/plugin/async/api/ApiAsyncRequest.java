/*********************************************************************
* Copyright c 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse PublicLicense 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.async.api;

import java.util.List;
import java.util.Map;

import org.eclipse.scava.plugin.async.AsyncBase;
import org.eclipse.scava.plugin.async.AsyncRequestBase;
import org.eclipse.scava.plugin.async.IAsyncHandler;

import com.squareup.okhttp.Call;

import io.swagger.client.ApiCallback;
import io.swagger.client.ApiException;

public class ApiAsyncRequest<T> extends AsyncRequestBase<T, ApiException> {
	private Call call;
	private final IApiFuntion<T> apiFunction;

	public ApiAsyncRequest(AsyncBase<T, ApiException> asyncRequestBase, IApiFuntion<T> apiFunction) {
		super(asyncRequestBase);
		this.apiFunction = apiFunction;
	}

	@Override
	public IAsyncHandler<T> execute() {

		try {
			ApiCallback<T> apiCallback = new ApiCallback<T>() {

				@Override
				public void onDownloadProgress(long arg0, long arg1, boolean arg2) {

				}

				@Override
				public void onFailure(ApiException arg0, int arg1, Map<String, List<String>> arg2) {
					finished = true;
					executor.accept(() -> onFail.accept(arg0));
				}

				@Override
				public void onSuccess(T arg0, int arg1, Map<String, List<String>> arg2) {
					finished = true;
					lastResult = arg0;
					executor.accept(() -> onSuccess.accept(arg0));
				}

				@Override
				public void onUploadProgress(long arg0, long arg1, boolean arg2) {

				}
			};

			call = apiFunction.request(apiCallback);
		} catch (ApiException e) {
			finished = true;
			executor.accept(() -> onFail.accept(e));
		}

		return this;
	}

	@Override
	public void cancel() {
		call.cancel();
	}

}
