/*********************************************************************
* Copyright (c) 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.event;

import java.util.ArrayList;
import java.util.List;

public abstract class Event<ListenerType extends IEventListener> implements IEvent<ListenerType> {
	private List<ListenerType> listeners = new ArrayList<>();
	
	protected Event() {
		
	}
	
	@Override
	public final void subscribe(ListenerType listener) {
		listeners.add(listener);
	}
	
	@Override
	public final void unSubscribe(ListenerType listener) {
		listeners.remove(listener);
	}
	
	protected final List<ListenerType> getListeners() {
		return listeners;
	}
	
	public final void removeAllListeners() {
		listeners.clear();
	}
	
	@Override
	public final void dispose() {
		removeAllListeners();
	}
}
