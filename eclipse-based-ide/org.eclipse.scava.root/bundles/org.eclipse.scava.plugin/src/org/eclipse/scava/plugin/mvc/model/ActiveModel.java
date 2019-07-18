/*********************************************************************
* Copyright c 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse PublicLicense 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.mvc.model;

import org.eclipse.scava.plugin.mvc.IHasEventListenerManager;
import org.eclipse.scava.plugin.mvc.event.direct.EventManager;
import org.eclipse.scava.plugin.mvc.event.direct.IEventListenerManager;

public class ActiveModel<ModelEventListenerType extends IModelEventListener> extends Model
		implements IHasEventListenerManager<ModelEventListenerType> {

	protected final EventManager<ModelEventListenerType> eventManager = new EventManager<>();

	@Override
	public IEventListenerManager<ModelEventListenerType> getEventListenerManager() {
		return eventManager;
	}

	@Override
	public void dispose() {
		super.dispose();
		eventManager.dispose();
	}

}
