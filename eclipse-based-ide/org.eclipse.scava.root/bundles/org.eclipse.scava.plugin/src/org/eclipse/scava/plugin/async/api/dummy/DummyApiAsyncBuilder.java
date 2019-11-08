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

import java.util.function.Consumer;

import org.eclipse.scava.plugin.async.AsyncBuilderBase;
import org.eclipse.scava.plugin.async.IAsyncExecutor;
import org.eclipse.scava.plugin.async.api.IApiAsyncBuilder;

import io.swagger.client.ApiException;

public class DummyApiAsyncBuilder<T> extends AsyncBuilderBase<T, ApiException> implements IApiAsyncBuilder<T> {
	private T response;
	private ApiException exception;

	public static <T> IApiAsyncBuilder<T> buildToSuccess(T response) {
		return new DummyApiAsyncBuilder<T>(response);
	}

	public static <T> IApiAsyncBuilder<T> buildToFail(ApiException exception) {
		return new DummyApiAsyncBuilder<T>(exception);
	}

	private DummyApiAsyncBuilder(T response) {
		this.response = response;
	}

	private DummyApiAsyncBuilder(ApiException exception) {
		this.exception = exception;
	}

	@Override
	public IApiAsyncBuilder<T> onSuccess(Consumer<T> consumer) {
		return (IApiAsyncBuilder<T>) super.onSuccess(consumer);
	}

	@Override
	public IApiAsyncBuilder<T> onFail(Consumer<ApiException> consumer) {
		return (IApiAsyncBuilder<T>) super.onFail(consumer);
	}

	@Override
	public IApiAsyncBuilder<T> executeWith(Consumer<Runnable> executor) {
		return (IApiAsyncBuilder<T>) super.executeWith(executor);
	}

	@Override
	public IAsyncExecutor<T> build() {
		return new DummyApiAsyncRequest<>(this, response, exception);
	}

}