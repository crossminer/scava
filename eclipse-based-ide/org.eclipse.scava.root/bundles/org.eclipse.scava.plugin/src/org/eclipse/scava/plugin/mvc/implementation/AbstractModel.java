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

import org.eclipse.scava.plugin.mvc.IModel;

import com.google.common.eventbus.EventBus;

public abstract class AbstractModel implements IModel {
	private EventBus eventBus;

	@Override
	public void setEventBus(EventBus eventBus) {
		this.eventBus = eventBus;
	}

	protected EventBus getEventBus() {
		return eventBus;
	}

	@Override
	public final void dispose() {
		disposeModel();
	}

	protected void disposeModel() {
		
	}

}
