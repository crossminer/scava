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

import org.eclipse.scava.plugin.mvc.IController;
import org.eclipse.scava.plugin.mvc.IHasModel;
import org.eclipse.scava.plugin.mvc.IModel;

import com.google.common.eventbus.EventBus;

public abstract class AbstractModelController<ModelType extends IModel> extends AbstractController
		implements IHasModel {
	private final ModelType model;

	public AbstractModelController(IController parent, ModelType model) {
		super(parent);

		this.model = model;
		model.setEventBus(getEventBus());
	}
	
	@Override
	protected void setEventBus(EventBus eventBus) {
		super.setEventBus(eventBus);
		
		if( getModel() != null ) {
			getModel().setEventBus(getEventBus());
		}
	}

	@Override
	public final ModelType getModel() {
		return model;
	}

	@Override
	protected void disposeController() {
		model.dispose();
		super.disposeController();
	}

}
