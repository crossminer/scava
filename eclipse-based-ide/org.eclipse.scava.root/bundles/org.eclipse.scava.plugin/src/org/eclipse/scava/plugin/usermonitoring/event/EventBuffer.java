/*********************************************************************
* Copyright (c) 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
*
* Contributors:
*    Zsolt János Szamosvölgyi
*    Endre Tamás Váradi
*    Gergõ Balogh
**********************************************************************/
package org.eclipse.scava.plugin.usermonitoring.event;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class EventBuffer {

	private Timer timer;
	private List<IFlushableEvent<?>> bufferedEvents;
	private IEventBufferFlusher bufferFlusher;

	public EventBuffer(IEventBufferFlusher bufferFlusher) {
		bufferedEvents = new ArrayList<>();
		this.bufferFlusher = bufferFlusher;
	}

	public void add(IFlushableEvent event) {

		bufferedEvents.add(event);

		if (bufferedEvents.size() >= event.getMaxBufferedLength()) {
			sendEvent();
		} else {
			if (timer != null) {
				timer.cancel();
			}
			timer = new Timer();
			timer.schedule(new TimerTask() {

				@Override
				public void run() {
					sendEvent();

				}
			}, event.getMaxTimeUntilEventInsertion());
		}

	}

	private void sendEvent() {

		IFlushableEvent<?> aggregatedEvent = bufferedEvents.get(0).aggregate(bufferedEvents);

		bufferFlusher.flush(aggregatedEvent);
		timer.cancel();
		bufferedEvents.clear();
	}

	public void flush() {
		if (bufferedEvents.size() > 0) {
			sendEvent();
		}

	}

}
