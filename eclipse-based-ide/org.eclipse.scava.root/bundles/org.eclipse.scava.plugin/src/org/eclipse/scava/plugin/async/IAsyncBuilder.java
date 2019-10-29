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

public interface IAsyncBuilder<T, E extends Exception> {

	IAsyncBuilder<T, E> onSuccess(Consumer<T> consumer);

	IAsyncBuilder<T, E> onFail(Consumer<E> consumer);

	IAsyncBuilder<T, E> executeWith(Consumer<Runnable> executor);

	IAsyncExecutor<T> build();

	default IAsyncHandler<T> buildAndExecute() {
		return build().execute();
	}

}
