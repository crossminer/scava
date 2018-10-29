/*********************************************************************
* Copyright (c) 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.mvc;

import org.eclipse.scava.plugin.mvc.event.AbstractCloseEvent;

import com.google.common.eventbus.EventBus;

public interface IController extends IMvcComponent, IDisposable {
	void init();

	IController getParent();

	EventBus getEventBus();

	IController[] getSubControllers();

	void addSubController(IController subController);

	void removeSubController(IController subController);

	boolean isSubController(IController controller);

	static abstract class ControllerCloseEvent<ControllerType extends IController>
			extends AbstractCloseEvent<ControllerType> {

		public ControllerCloseEvent(ControllerType controller) {
			super(controller);
		}

	}
}
