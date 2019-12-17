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

import java.util.function.Function;

import org.eclipse.scava.plugin.async.AsyncRequestController;
import org.eclipse.scava.plugin.async.IAsyncBuilder;
import org.eclipse.scava.plugin.mvc.controller.Controller;

import io.swagger.client.ApiException;

public class ApiAsyncRequestController<T> extends AsyncRequestController<T, ApiException> {

	public ApiAsyncRequestController(Controller parent,
			Function<IApiAsyncBuilder<T>, IApiAsyncBuilder<T>> builderParameterizer) {
		super(parent, b -> builderParameterizer.apply((IApiAsyncBuilder<T>) b));
	}

	public ApiAsyncRequestController(Controller parent, IAsyncBuilder<T, ApiException> builder) {
		super(parent, builder);
	}

	public ApiAsyncRequestController(Controller parent) {
		super(parent);
	}

}
