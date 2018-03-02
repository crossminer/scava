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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.eclipse.scava.plugin.event.notifier.INotifierEvent;
import org.eclipse.scava.plugin.event.notifier.INotifierEventSubscriber;
import org.eclipse.scava.plugin.event.notifier.NotifierEvent;
import org.eclipse.scava.plugin.ui.abstractmvc.AbstractModel;

public class CommunicationDisplayModel extends AbstractModel {
	private List<CommunicationMessage> messages;
	private final INotifierEvent modelChanged = registerEvent(new NotifierEvent());
	
	public CommunicationDisplayModel() {
		messages = new ArrayList<>();
	}
	
	public INotifierEventSubscriber getModelChanged() {
		return modelChanged;
	}
	
	public void addMessage(CommunicationMessage message) {
		messages.add(message);
		modelChanged.invoke();
	}
	
	public Iterator<CommunicationMessage> getMessages() {
		return Collections.unmodifiableList(messages).iterator();
	}
}
