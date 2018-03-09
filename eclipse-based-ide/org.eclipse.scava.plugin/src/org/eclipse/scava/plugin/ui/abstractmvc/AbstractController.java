/*********************************************************************
* Copyright (c) 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.ui.abstractmvc;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractController<ModelType extends IModel, ViewType extends IView> extends EventHandler implements IController {
	private ModelType model;
	private ViewType view;
	
	private List<IController> subControllers;
	
	public AbstractController(ModelType model, ViewType view) {
		subControllers = new ArrayList<>();
		this.model = model;
		this.view = view;
	}
	
	protected final ModelType getModel() {
		return model;
	}
	
	protected final ViewType getView() {
		return view;
	}
	
	protected void addSubController(IController controller) {
		subControllers.add(controller);
	}
	
	protected void removeSubController(IController controller) {
		subControllers.remove(controller);
	}
	
	@Override
	public void close() {
		disposeEvents();
		subControllers.forEach(subController -> subController.close());
		view.close();
		model.close();
		dispose();
	}
	
	protected void dispose() {
		
	}
}
