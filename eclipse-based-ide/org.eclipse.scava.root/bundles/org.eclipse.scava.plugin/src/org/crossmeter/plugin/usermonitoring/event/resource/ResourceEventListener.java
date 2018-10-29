package org.crossmeter.plugin.usermonitoring.event.resource;

import org.crossmeter.plugin.usermonitoring.event.EventManager;
import org.crossmeter.plugin.usermonitoring.event.IEventListener;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.ui.IPartService;

public class ResourceEventListener implements IEventListener, IResourceChangeListener {

	public ResourceEventListener() {
		
	}

	


	@Override
	public void resourceChanged(IResourceChangeEvent event) {
		//EventManager.triggerEvent(new ResourceEvent(event));
	}
	
}
