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

import org.eclipse.scava.plugin.mvc.IHasEventListenerManager;
import org.eclipse.scava.plugin.mvc.model.IModelEventListener;
import org.eclipse.scava.plugin.mvc.model.Model;

public abstract class ModelController<ModelType extends Model> extends Controller {
	private final ModelType model;

	public ModelController(Controller parent, ModelType model) {
		super(parent);
		this.model = model;

		if (this instanceof IModelEventListener && model instanceof IHasEventListenerManager<?>) {
			addEventListener(((IHasEventListenerManager<?>) getModel()).getEventListenerManager());
		}
	}

	@Override
	public void init() {
		super.init();
		getModel().init();
	}

	public ModelType getModel() {
		return model;
	}

	@Override
	protected void disposeController() {
		if (getModel() != null) {
			getModel().dispose();
		}

		super.disposeController();
	}

}
