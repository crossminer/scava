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
package org.eclipse.scava.plugin.usermonitoring.event.events.launch;

import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchListener;
import org.eclipse.scava.plugin.Activator;

public class LaunchEventListener implements ILaunchListener {

	@Override
	@SuppressWarnings("unused")
	public void launchRemoved(ILaunch launch) {

	}

	@Override
	public void launchAdded(ILaunch launch) {

		Activator.getDefault().getEventBus().post(new LaunchEvent(launch));

	}

	@Override
	@SuppressWarnings("unused")
	public void launchChanged(ILaunch launch) {

	}

}
