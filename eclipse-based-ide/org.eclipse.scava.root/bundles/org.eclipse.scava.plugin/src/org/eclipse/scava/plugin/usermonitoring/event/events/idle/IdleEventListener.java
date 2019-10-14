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
package org.eclipse.scava.plugin.usermonitoring.event.events.idle;

import java.util.Timer;
import java.util.TimerTask;

import org.eclipse.scava.plugin.Activator;
import org.eclipse.scava.plugin.usermonitoring.event.events.IEvent;

public class IdleEventListener {

	private Timer timer;

	public IdleEventListener() {

	}

	public void eventAboutToBeInserted(IEvent event) {

		if (!(event instanceof IdleEvent)) {
			if (timer != null) {
				timer.cancel();
			}
			timer = new Timer();
			timer.schedule(new Reminder(), 30000);
		}

	}

	private void sendEvent() {
		Activator.getDefault().getEventBus().post(new IdleEvent());
	}

	private class Reminder extends TimerTask {

		@Override
		public void run() {
			sendEvent();
		}

	}

}
