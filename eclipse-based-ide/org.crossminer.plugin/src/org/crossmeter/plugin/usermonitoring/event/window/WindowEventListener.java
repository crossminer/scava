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
package org.crossmeter.plugin.usermonitoring.event.window;

import org.crossmeter.plugin.usermonitoring.event.EventManager;
import org.crossmeter.plugin.usermonitoring.event.IEventListener;
import org.crossmeter.plugin.usermonitoring.event.part.PartEventListener;
import org.eclipse.ui.IWindowListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.IWorkbenchWindow;

public class WindowEventListener implements IEventListener, IWindowListener{

	
	@Override
	public void windowActivated(IWorkbenchWindow window) {
		EventManager.triggerEvent(new WindowEvent(window, WindowEventType.ACTIVATED));
		
	}

	
	@Override
	public void windowDeactivated(IWorkbenchWindow window) {
		EventManager.triggerEvent(new WindowEvent(window, WindowEventType.DEACTIVATED));
		
	}

	
	@Override
	public void windowClosed(IWorkbenchWindow window) {
		EventManager.triggerEvent(new WindowEvent(window, WindowEventType.CLOSED));
		
	}

	
	@Override
	public void windowOpened(IWorkbenchWindow window) {
		PartEventListener listener = new PartEventListener();
		window.getPartService().addPartListener(listener);
		IWorkbenchPartReference activePart = window.getActivePage().getActivePartReference();
		if (activePart != null) {
			listener.partOpened(activePart);
		}
		EventManager.triggerEvent(new WindowEvent(window, WindowEventType.OPENED));
	}

	
	

}
