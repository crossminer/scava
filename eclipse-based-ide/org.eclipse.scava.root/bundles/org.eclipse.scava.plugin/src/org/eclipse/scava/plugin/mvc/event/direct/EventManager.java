/*********************************************************************
* Copyright c 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse PublicLicense 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.mvc.event.direct;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.scava.plugin.mvc.IDisposable;

public class EventManager<ListenerType extends IEventListener>
		implements IDisposable, IEventListenerManager<ListenerType> {
	private final Set<ListenerType> listeners;

	public EventManager() {
		super();
		listeners = new HashSet<>();
	}

	@Override
	public void addListener(ListenerType listener) {
		listeners.add(listener);
	}

	@Override
	public void removeListener(ListenerType listener) {
		listeners.remove(listener);
	}

	public boolean isEmpty() {
		return listeners.isEmpty();
	}

	public void invoke(IEventInvoker<ListenerType> listenerInvoke) {
		for (ListenerType listener : listeners) {
			listenerInvoke.invoke(listener);
		}
	}

	@Override
	public void dispose() {
		listeners.clear();
	}
}
