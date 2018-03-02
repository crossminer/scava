/*********************************************************************
* Copyright (c) 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.ui.abstractmvc;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.scava.plugin.event.IEvent;

public class EventHandler {
	private final List<IEvent<?>> events = new ArrayList<>();
	
	protected final <EventType extends IEvent<?>> EventType registerEvent(EventType event) {
		events.add(event);
		return event;
	}
	
	protected final void unregisterEvent(IEvent<?> event) {
		events.remove(event);
	}
	
	protected final void disposeEvents() {
		events.forEach( event -> event.dispose() );
		events.clear();
	}
}
