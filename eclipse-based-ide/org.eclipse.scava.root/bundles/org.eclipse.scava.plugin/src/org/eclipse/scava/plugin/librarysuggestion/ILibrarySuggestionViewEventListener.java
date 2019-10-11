/*********************************************************************
* Copyright c 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse PublicLicense 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.librarysuggestion;

import org.eclipse.scava.plugin.mvc.view.IViewEventListener;

public interface ILibrarySuggestionViewEventListener extends IViewEventListener {
	void onUseAllForSearch();

	void onUseNoneForSearch();

	void onPickAllToInstall();

	void onPickNoneToInstall();

	void onInstall();

	void onTryAgainLoadSugestions();
}
