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
import org.eclipse.scava.plugin.mvc.IHasView;
import org.eclipse.scava.plugin.mvc.IModel;
import org.eclipse.scava.plugin.mvc.IView;
import org.eclipse.scava.plugin.mvc.IView.ViewCloseEvent;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

public abstract class AbstractModelViewController<ModelType extends IModel, ViewType extends IView>
		extends AbstractModelController<ModelType> implements IHasView {
	private final ViewType view;

	public AbstractModelViewController(IController parent, ModelType model, ViewType view) {
		super(parent, model);

		this.view = view;
		view.setEventBus(getEventBus());
	}

	@Override
	protected void setEventBus(EventBus eventBus) {
		super.setEventBus(eventBus);

		if (getView() != null) {
			getView().setEventBus(getEventBus());
		}
	}

	@Override
	public final ViewType getView() {
		return view;
	}

	@Override
	protected void disposeController() {
		view.dispose();
		super.disposeController();
	}

	@Subscribe
	public void onViewCloseEvent(ViewCloseEvent<ViewType> e) {
		ViewType closingView = e.getSender();
		if (closingView == getView()) {
			close();
		}
	}
}
