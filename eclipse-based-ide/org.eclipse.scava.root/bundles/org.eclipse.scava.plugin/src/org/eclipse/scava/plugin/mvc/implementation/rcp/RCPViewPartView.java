package org.eclipse.scava.plugin.mvc.implementation.rcp;

import org.eclipse.scava.plugin.mvc.IView;
import org.eclipse.scava.plugin.mvc.implementation.CloseEventRaiser;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

import com.google.common.eventbus.EventBus;

public abstract class RCPViewPartView extends ViewPart implements IView {
	private boolean disposed;
	private EventBus eventBus;
	
	protected RCPViewPartView() {
		
	}
	
	@Override
	public void init() {
		
	}

	@Override
	public void setEventBus(EventBus eventBus) {
		this.eventBus = eventBus;
	}

	protected EventBus getEventBus() {
		return this.eventBus;
	}

	@Override
	public void dispose() {
		if (!disposed) {
			disposed = true;
			new CloseEventRaiser(this).raiseCloseEvent(getEventBus());
		} else {
			super.dispose();
		}
	}

	public static <ViewType> ViewType requestView(String viewID) throws PartInitException {
		return (ViewType) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView(viewID);
	}
}
