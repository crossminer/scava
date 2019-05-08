/*********************************************************************
* Copyright c 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse PublicLicense 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.mvc.view;

import org.eclipse.scava.plugin.mvc.event.direct.EventManager;
import org.eclipse.scava.plugin.mvc.event.direct.IEventListenerManager;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

public abstract class CompositeView<ViewEventListenerType extends IViewEventListener> extends Composite
		implements IView<ViewEventListenerType> {
	private static Shell PHANTOM_SHELL = new Shell();
	protected final EventManager<ViewEventListenerType> eventManager = new EventManager<>();

	public CompositeView(int style) {
		super(PHANTOM_SHELL, style);

	}

	@Override
	public void init() {

	}

	@Override
	public IEventListenerManager<ViewEventListenerType> getEventListenerManager() {
		return eventManager;
	}

	@Override
	public Composite getComposite() {
		return this;
	}

	@Override
	public void dispose() {
		if (getParent() != null) {
			getParent().requestLayout();
		}

		super.dispose();
		eventManager.dispose();
	}

}
