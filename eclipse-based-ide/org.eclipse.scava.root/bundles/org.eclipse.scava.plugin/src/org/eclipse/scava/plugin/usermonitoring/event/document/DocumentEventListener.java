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
*    Zsolt J�nos Szamosv�lgyi
*    Endre Tam�s V�radi
*    Gerg� Balogh
**********************************************************************/
package org.eclipse.scava.plugin.usermonitoring.event.document;

import java.util.Timer;
import java.util.TimerTask;

import org.eclipse.jface.text.DocumentEvent;
import org.eclipse.jface.text.IDocumentListener;
import org.eclipse.scava.plugin.Activator;
import org.eclipse.scava.plugin.usermonitoring.event.IEventListener;

public class DocumentEventListener implements IEventListener, IDocumentListener {

	/**
	 * @param title
	 */
	private String title;
	private int count;
	private Timer timer;
	private DocumentEvent event;

	public DocumentEventListener(String title) {
		this.title = title;
		count = 0;
	}

	@Override
	public void documentAboutToBeChanged(DocumentEvent event) {

		this.event = event;

		if (timer != null) {
			timer.cancel();
		}
		timer = new Timer();
		timer.schedule(new Reminder(), 3000);

		if (count > 8) {
			sendEvent();
			count = 0;
		} else {
			count++;
		}

	}

	private void sendEvent() {
		Activator.getDefault().getMainController().getEventBus().post(new org.eclipse.scava.plugin.usermonitoring.event.document.DocumentEvent(event, title));
		timer.cancel();
	}

	private class Reminder extends TimerTask {

		@Override
		public void run() {
			count = 0;
			sendEvent();
		}

	}

	@Override
	public void documentChanged(DocumentEvent event) {
		// TODO Auto-generated method stub

	}

}
