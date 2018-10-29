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
package org.eclipse.scava.plugin.usermonitoring.event.scava;

import org.eclipse.scava.commons.library.Library;
import org.eclipse.scava.plugin.event.IEventListener;
import org.eclipse.scava.plugin.usermonitoring.event.EventManager;

public class ScavaEventListener implements IEventListener, IScavaEventListener{

	public ScavaEventListener() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void libraryChange(ScavaEventType type) {
		EventManager.processEvent(new ScavaEvent(type));
		
	}

	@Override
	public void libraryInformationRequest(Library library) {
		EventManager.processEvent(new ScavaEvent(library, ScavaEventType.LIBRARY_INFORMATION_REQUEST));
		
	}

	@Override
	public void libraryAlternativeSearch(Library library) {
		EventManager.processEvent(new ScavaEvent(library, ScavaEventType.LIBRARY_ALTERNATIVE_SEARCH));
		
	}
	

	

}
