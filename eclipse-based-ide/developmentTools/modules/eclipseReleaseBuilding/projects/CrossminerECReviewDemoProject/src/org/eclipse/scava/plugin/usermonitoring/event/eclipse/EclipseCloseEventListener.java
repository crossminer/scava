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
package org.eclipse.scava.plugin.usermonitoring.event.eclipse;

import org.eclipse.scava.plugin.usermonitoring.event.EventManager;
import org.eclipse.scava.plugin.usermonitoring.event.IEventListener;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchListener;

public class EclipseCloseEventListener implements IEventListener, IWorkbenchListener{

	
	@Override
	public boolean preShutdown(IWorkbench workbench, boolean forced) {
		
		return true;
	}

	
	@Override
	public void postShutdown(IWorkbench workbench) {
		EventManager.processEvent(new EclipseCloseEvent());
	}

}
