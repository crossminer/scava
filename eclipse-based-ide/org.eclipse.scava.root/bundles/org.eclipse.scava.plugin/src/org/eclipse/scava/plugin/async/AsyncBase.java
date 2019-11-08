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

public abstract class AsyncBase<T, E extends Exception> {
	protected Consumer<T> onSuccess;
	protected Consumer<E> onFail;
	protected Consumer<Runnable> executor;

	public AsyncBase() {

	}

	public AsyncBase(AsyncBase<T, E> asyncBase) {
		this.onSuccess = asyncBase.onSuccess;
		this.onFail = asyncBase.onFail;
		this.executor = asyncBase.executor;
	}
}
