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
package org.eclipse.scava.plugin.usermonitoring.event.events.document;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jface.text.DocumentEvent;
import org.eclipse.jface.text.IDocumentListener;
import org.eclipse.scava.plugin.Activator;
import org.eclipse.scava.plugin.usermonitoring.event.IEventListener;

public class DocumentEventListener implements IEventListener, IDocumentListener {

	/**
	 * @param title
	 */

	private ICompilationUnit compilationUnit;

	public DocumentEventListener(String title, ICompilationUnit compilationUnit) {
		Activator.getDefault().getEventBus().register(this);

		this.compilationUnit = compilationUnit;

	}

	@Override
	public void documentAboutToBeChanged(DocumentEvent event) {

		Activator.getDefault().getEventBus().post(new org.eclipse.scava.plugin.usermonitoring.event.events.document.DocumentEvent(compilationUnit));
	}

	@Override
	public void documentChanged(DocumentEvent event) {

	}

}
