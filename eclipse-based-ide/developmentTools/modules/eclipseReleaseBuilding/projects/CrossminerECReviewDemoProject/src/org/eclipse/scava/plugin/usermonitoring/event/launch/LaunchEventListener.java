package org.eclipse.scava.plugin.usermonitoring.event.launch;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchListener;
import org.eclipse.scava.plugin.usermonitoring.event.EventManager;

public class LaunchEventListener implements ILaunchListener{

	@Override
	@SuppressWarnings("unused")
	public void launchRemoved(ILaunch launch) {
		
	}

	@Override
	public void launchAdded(ILaunch launch) {
		
		EventManager.processEvent(new LaunchEvent(launch));
		
	}

	@Override
	@SuppressWarnings("unused")
	public void launchChanged(ILaunch launch) {
		
	}

}
