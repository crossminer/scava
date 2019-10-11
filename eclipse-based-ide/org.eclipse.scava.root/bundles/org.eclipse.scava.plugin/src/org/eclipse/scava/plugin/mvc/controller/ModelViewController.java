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

import org.eclipse.scava.plugin.mvc.model.Model;
import org.eclipse.scava.plugin.mvc.view.IView;
import org.eclipse.scava.plugin.mvc.view.IViewEventListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;

public abstract class ModelViewController<ModelType extends Model, ViewType extends IView<? extends IViewEventListener>>
		extends ModelController<ModelType> {
	private final ViewType view;

	public ModelViewController(Controller parent, ModelType model, ViewType view) {
		super(parent, model);
		this.view = view;

		if (this instanceof IViewEventListener) {
			addEventListener(getView().getEventListenerManager());
		}
	}

	@Override
	public void init() {
		super.init();
		getView().init();
	}

	public ViewType getView() {
		return view;
	}

	@Override
	protected void disposeController() {
		if (getView() != null) {
			try {
				getView().dispose();
			} catch (SWTException e) {
				if( e.code != SWT.ERROR_WIDGET_DISPOSED ) {
					throw e;
				}
			}
		}

		super.disposeController();
	}

}
