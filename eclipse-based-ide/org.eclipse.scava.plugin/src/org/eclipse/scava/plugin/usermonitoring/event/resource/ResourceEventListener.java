package org.eclipse.scava.plugin.usermonitoring.event.resource;

import org.eclipse.scava.plugin.usermonitoring.event.EventManager;
import org.eclipse.scava.plugin.usermonitoring.event.IEventListener;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.ui.IPartService;

public class ResourceEventListener implements IEventListener, IResourceChangeListener {

	
	public ResourceEventListener() {
		
	}

	


	@Override
	public void resourceChanged(IResourceChangeEvent event) {
		
		
		if(event.getType() == IResourceChangeEvent.POST_CHANGE) {
			//EventManager.processEvent(new ResourceEvent(event));
		}
		
	}
	
}
