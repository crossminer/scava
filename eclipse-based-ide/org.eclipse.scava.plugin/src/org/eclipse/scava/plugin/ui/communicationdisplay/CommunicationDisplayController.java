/*********************************************************************
* Copyright (c) 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.ui.communicationdisplay;

import java.util.Iterator;

import org.eclipse.scava.plugin.logger.ILoggerListener;
import org.eclipse.scava.plugin.logger.LoggerMessageKind;
import org.eclipse.scava.plugin.ui.abstractmvc.AbstractController;

public class CommunicationDisplayController extends
		AbstractController<CommunicationDisplayModel, CommunicationDisplayView> implements ILoggerListener {
	
	public CommunicationDisplayController(CommunicationDisplayModel model, CommunicationDisplayView view) {
		super(model, view);
		getModel().getModelChanged().subscribe(this::onModelChanged);
	}
	
	private void onModelChanged() {
		Iterator<CommunicationMessage> messages = getMessages();
		getView().refreshView(messages);
	}
	
	public Iterator<CommunicationMessage> getMessages() {
		return getModel().getMessages();
	}

	@Override
	public void listen(String message, LoggerMessageKind messageKind) {
		CommunicationMessage communicationMessage = new CommunicationMessage(message, messageKind);
		getModel().addMessage(communicationMessage);
	}
}
