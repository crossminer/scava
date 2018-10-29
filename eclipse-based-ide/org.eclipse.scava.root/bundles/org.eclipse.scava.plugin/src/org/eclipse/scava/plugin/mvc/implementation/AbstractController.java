/*********************************************************************
* Copyright (c) 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.mvc.implementation;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.scava.plugin.mvc.IController;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

public abstract class AbstractController implements IController {
	private final IController parent;
	private EventBus eventBus;

	private final Set<IController> subControllers;

	public AbstractController(IController parent) {
		this.parent = parent;

		subControllers = new HashSet<>();

		if (getParent() != null) {
			setEventBus(getParent().getEventBus());
			
			getParent().addSubController(this);
		}
	}

	@Override
	public final IController getParent() {
		return parent;
	}

	protected void setEventBus(EventBus eventBus) {
		if( getEventBus() != null ) {
			getEventBus().unregister(this);
		}
		
		this.eventBus = eventBus;
		
		if( getEventBus() != null ) {
			getEventBus().register(this);
		}
	}

	@Override
	public EventBus getEventBus() {
		return eventBus;
	}

	@Override
	public IController[] getSubControllers() {
		return subControllers.toArray(new IController[subControllers.size()]);
	}

	@Override
	public final void dispose() {
		IController[] copyOfSubControllers = subControllers.toArray(new IController[subControllers.size()]);
		for (IController subController : copyOfSubControllers) {
			subController.dispose();
		}

		disposeController();

		if (getEventBus() != null) {
			getEventBus().unregister(this);
		}

		if (getParent() != null) {
			getParent().removeSubController(this);
		}
	}

	protected void disposeController() {

	}
	
	@Override
	public void addSubController(IController subController) {
		subControllers.add(subController);
	}

	@Override
	public void removeSubController(IController subController) {
		subControllers.remove(subController);
	}

	@Override
	public boolean isSubController(IController controller) {
		return subControllers.contains(controller);
	}
	
	protected void close() {
		new CloseEventRaiser(this).raiseCloseEvent(getEventBus());
	}
	
	@Subscribe
	public void onControllerCloseEvent(ControllerCloseEvent<IController> e) {
		IController closingController = e.getSender();
		if( isSubController(closingController) ) {
			closingController.dispose();
		}
	}
}
