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

import org.eclipse.scava.plugin.ui.abstractmvc.JFaceWindowView;

public class CommunicationDisplayView extends JFaceWindowView<CommunicationDisplayDisplay>{

	public CommunicationDisplayView(CommunicationDisplayDisplay display) {
		super(display);
	}
	
	public void refreshView(Iterator<CommunicationMessage> messages) {
		getDisplay().refreshView(messages);
	}
	
}
