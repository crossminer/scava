package org.eclipse.scava.plugin.usermonitoring.event.launch;

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

		Activator.getDefault().getMainController().getEventBus().post(new LaunchEvent(launch));

	}

	@Override
	@SuppressWarnings("unused")
	public void launchChanged(ILaunch launch) {

	}

}
