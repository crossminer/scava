/*********************************************************************
* Copyright c 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse PublicLicense 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.mvc.controller;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.scava.plugin.mvc.IInitializable;
import org.eclipse.scava.plugin.mvc.IMvcComponent;
import org.eclipse.scava.plugin.mvc.event.direct.IEventListener;
import org.eclipse.scava.plugin.mvc.event.direct.IEventListenerManager;
import org.eclipse.scava.plugin.mvc.event.routed.IRoutedEvent;

public abstract class Controller implements IMvcComponent, IInitializable {
	private final Controller parent;
	private final Set<Controller> subControllers;
	private boolean disposed;

	public Controller(Controller parent) {
		super();
		this.parent = parent;
		subControllers = new HashSet<>();

		if (getParent() != null) {
			getParent().addSubController(this);
		}
	}

	@Override
	public void init() {

	}

	protected Controller getParent() {
		return parent;
	}

	public Set<Controller> getSubControllers() {
		return Collections.unmodifiableSet(new HashSet<>(subControllers));
	}

	public <T extends Controller> Set<T> getSubControllers(Class<T> controllerClass) {
		return subControllers.stream().filter(controllerClass::isInstance).map(controllerClass::cast)
				.collect(Collectors.toSet());
	}

	private void addSubController(Controller controller) {
		subControllers.add(controller);
	}

	private void removeSubController(Controller controller) {
		subControllers.remove(controller);
	}

	@Override
	public final void dispose() {

		// this is necessary to prevent concurrent modification
		Controller[] cachedSubController = subControllers.toArray(new Controller[0]);
		for (Controller controller : cachedSubController) {
			controller.dispose();
		}

		disposeController();

		if (getParent() != null) {
			getParent().removeSubController(this);
		}

		disposed = true;
	}

	public boolean isDisposed() {
		return disposed;
	}

	protected void disposeController() {

	}

	@SuppressWarnings("unchecked")
	protected <E extends IEventListener> void addEventListener(IEventListenerManager<E> listeners) {
		listeners.addListener((E) this);
	}

	protected void onReceiveRoutedEventFromSubController(IRoutedEvent routedEvent, Controller forwarderController) {
		routeEventToParentController(routedEvent);
	}

	protected final void routeEventToParentController(IRoutedEvent event) {
		if (getParent() != null) {
			getParent().onReceiveRoutedEventFromSubController(event, this);
		}
	}

	protected void onReceiveRoutedEventFromParentController(IRoutedEvent routedEvent) {
		routeEventToSubControllers(routedEvent);
	}

	protected final void routeEventToSubControllers(IRoutedEvent event) {
		// this is necessary to prevent concurrent modification
		Controller[] cachedSubController = subControllers.toArray(new Controller[0]);
		for (Controller controller : cachedSubController) {
			controller.onReceiveRoutedEventFromParentController(event);
		}
	}

}
