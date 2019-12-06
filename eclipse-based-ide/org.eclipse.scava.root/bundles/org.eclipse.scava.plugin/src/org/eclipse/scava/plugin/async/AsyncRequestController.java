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

import java.util.function.Function;

import org.eclipse.scava.plugin.mvc.controller.Controller;

public class AsyncRequestController<T, E extends Exception> extends Controller {
	private IAsyncBuilder<T, E> builder;
	private Function<IAsyncBuilder<T, E>, IAsyncBuilder<T, E>> builderParameterizer = Function.identity();
	private IAsyncHandler<T> current;

	public AsyncRequestController(Controller parent,
			Function<IAsyncBuilder<T, E>, IAsyncBuilder<T, E>> builderParameterizer) {
		super(parent);
		this.builderParameterizer = builderParameterizer;
	}

	public AsyncRequestController(Controller parent) {
		super(parent);
		this.builderParameterizer = Function.identity();
	}

	public AsyncRequestController(Controller parent, IAsyncBuilder<T, E> builder) {
		super(parent);
		this.builder = builder;
	}

	public IAsyncHandler<T> execute() {
		if (builder == null) {
			throw new RuntimeException("This controller was initialized without a concrete builder.");
		}

		if (current != null) {
			current.cancel();
		}

		current = builder.buildAndExecute();
		return current;
	}

	public IAsyncHandler<T> execute(IAsyncBuilder<T, E> builder) {
		if (current != null) {
			current.cancel();
		}

		IAsyncBuilder<T, E> parameterizedBuilder = builderParameterizer.apply(builder);
		current = parameterizedBuilder.buildAndExecute();
		return current;
	}

	public IAsyncHandler<T> getHandler() {
		return current;
	}

	@Override
	protected void disposeController() {
		if (current != null) {
			current.cancel();
		}

		super.disposeController();
	}
}
