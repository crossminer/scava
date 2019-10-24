/*********************************************************************
* Copyright c 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse PublicLicense 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.async.api.dummy;

import org.eclipse.scava.plugin.async.AsyncBase;
import org.eclipse.scava.plugin.async.AsyncRequestBase;
import org.eclipse.scava.plugin.async.IAsyncHandler;

import io.swagger.client.ApiException;

public class DummyApiAsyncRequest<T> extends AsyncRequestBase<T, ApiException> {
	private final T response;
	private final ApiException exception;

	public DummyApiAsyncRequest(AsyncBase<T, ApiException> asyncRequestBase, T response, ApiException exception) {
		super(asyncRequestBase);
		this.response = response;
		this.exception = exception;
	}

	@Override
	public IAsyncHandler<T> execute() {
		finished = true;

		if (response != null) {
			lastResult = response;
			executor.accept(() -> onSuccess.accept(response));
		}

		if (exception != null) {
			executor.accept(() -> onFail.accept(exception));
		}

		return this;
	}

	@Override
	public void cancel() {

	};
}
