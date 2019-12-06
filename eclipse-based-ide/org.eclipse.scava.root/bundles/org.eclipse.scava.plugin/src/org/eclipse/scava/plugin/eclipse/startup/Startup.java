package org.eclipse.scava.plugin.eclipse.startup;

import org.eclipse.scava.plugin.Activator;
import org.eclipse.scava.plugin.main.EclipseInterfaceEvent;
import org.eclipse.ui.IStartup;

public class Startup implements IStartup {

	@Override
	public void earlyStartup() {
		Activator.getDefault().getEventBus().post(new EclipseInterfaceEvent.WorkbenchStartup(null));
	}

}
