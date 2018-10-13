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

import org.eclipse.scava.plugin.usermonitoring.event.EventManager;
import org.eclipse.scava.plugin.usermonitoring.event.IEventListener;
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
		EventManager.processEvent(new org.eclipse.scava.plugin.usermonitoring.event.document.DocumentEvent(event,title));
		
	}

	
	@Override
	public void documentChanged(DocumentEvent event) {
		// TODO Auto-generated method stub
		
	}

}
