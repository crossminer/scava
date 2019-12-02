/*********************************************************************
* Copyright c 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse PublicLicense 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.async;

import java.util.function.Consumer;

public abstract class AsyncBuilderBase<T, E extends Exception> extends AsyncBase<T, E> implements IAsyncBuilder<T, E> {

	public AsyncBuilderBase() {
		onSuccess = t -> {
		};
		onFail = e -> {
		};
		executor = Runnable::run;
	}

	@Override
	public IAsyncBuilder<T, E> onSuccess(Consumer<T> consumer) {
		onSuccess = consumer;
		return this;
	}

	@Override
	public IAsyncBuilder<T, E> onFail(Consumer<E> consumer) {
		onFail = consumer;
		return this;
	}

	@Override
	public IAsyncBuilder<T, E> executeWith(Consumer<Runnable> executor) {
		this.executor = executor;
		return this;
	}
}