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
*    Gergő Balogh
**********************************************************************/
package org.crossmeter.plugin.usermonitoring.event.document;

import org.crossmeter.plugin.usermonitoring.event.EventManager;
import org.crossmeter.plugin.usermonitoring.event.IEventListener;
import org.eclipse.jface.text.DocumentEvent;
import org.eclipse.jface.text.IDocumentListener;

public class DocumentEventListener implements IEventListener, IDocumentListener{

	
	/**
	 * @param title
	 */
	private String title;
	
	public DocumentEventListener(String title) {
		this.title = title;
	}


	@Override
	public void documentAboutToBeChanged(DocumentEvent event) {
		EventManager.triggerEvent(new org.crossmeter.plugin.usermonitoring.event.document.DocumentEvent(event,title));
		
	}

	
	@Override
	public void documentChanged(DocumentEvent event) {
		// TODO Auto-generated method stub
		
	}

}
