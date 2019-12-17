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

import com.squareup.okhttp.Call;

import io.swagger.client.ApiCallback;
import io.swagger.client.ApiException;

@FunctionalInterface
public interface IApiFuntion<T> {
	Call request(ApiCallback<T> apiCallback) throws ApiException;
}