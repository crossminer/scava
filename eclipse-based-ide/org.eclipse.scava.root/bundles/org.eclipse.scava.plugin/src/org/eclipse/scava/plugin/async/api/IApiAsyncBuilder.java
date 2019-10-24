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

import java.util.function.Consumer;

import org.eclipse.scava.plugin.async.IAsyncBuilder;

import io.swagger.client.ApiException;

public interface IApiAsyncBuilder<T> extends IAsyncBuilder<T, ApiException> {

	IApiAsyncBuilder<T> onSuccess(Consumer<T> consumer);

	IApiAsyncBuilder<T> onFail(Consumer<ApiException> consumer);

	IApiAsyncBuilder<T> executeWith(Consumer<Runnable> executor);

}