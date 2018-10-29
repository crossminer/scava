package org.eclipse.scava.plugin.mvc.implementation.swt;

import org.eclipse.scava.plugin.mvc.IView;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

import com.google.common.eventbus.EventBus;

public abstract class SWTCompositeView extends Composite implements IView, IHasComposite {
	private static Shell phantomShell = new Shell();
	private EventBus eventBus;

	public SWTCompositeView(int style) {
		// With this hack, we are able to instantiate one of this without real parent
		super(phantomShell, style);
	}

	@Override
	public void init() {

	}

	@Override
	public void setEventBus(EventBus eventBus) {
		this.eventBus = eventBus;
	}

	protected EventBus getEventBus() {
		return eventBus;
	}

	@Override
	public Composite getComposite() {
		return this;
	}

}
