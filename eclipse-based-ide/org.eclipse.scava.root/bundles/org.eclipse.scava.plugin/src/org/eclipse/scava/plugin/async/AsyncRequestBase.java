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

public abstract class AsyncRequestBase<T, E extends Exception> extends AsyncBase<T, E>
		implements IAsyncExecutor<T>, IAsyncHandler<T> {

	protected boolean finished;
	protected T lastResult;

	public AsyncRequestBase(AsyncBase<T, E> asyncRequestBase) {
		super(asyncRequestBase);
	}

	@Override
	public boolean hasFinished() {
		return finished;
	}

	@Override
	public T getLastResult() {
		return lastResult;
	}

}
