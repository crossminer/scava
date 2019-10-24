/*********************************************************************
* Copyright c 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse PublicLicense 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.libraryversions.apimigration.migration.result;

import org.eclipse.scava.plugin.mvc.controller.Controller;
import org.eclipse.scava.plugin.mvc.controller.ModelViewController;

public abstract class ApiMigrationResultController<ModelType extends ApiMigrationResultModel, ViewType extends ApiMigrationResultView>
		extends ModelViewController<ModelType, ViewType> implements IApiMigrationResultViewEventListener {

	public ApiMigrationResultController(Controller parent, ModelType model, ViewType view) {
		super(parent, model, view);
	}

	@Override
	public void init() {
		super.init();

		loadResults();
	}

	@Override
	public void requestViewClose() {
		dispose();
	}

	@Override
	public void onTryAgainLoadResults() {
		loadResults();
	}

	protected abstract void loadResults();
}
