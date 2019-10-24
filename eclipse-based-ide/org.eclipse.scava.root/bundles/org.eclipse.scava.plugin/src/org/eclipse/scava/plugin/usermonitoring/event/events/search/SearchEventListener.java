/*********************************************************************
* Copyright (c) 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
*
* Contributors:
*    Zsolt János Szamosvölgyi
*    Endre Tamás Váradi
*    Gergõ Balogh
**********************************************************************/
package org.eclipse.scava.plugin.usermonitoring.event.events.search;

import org.eclipse.scava.plugin.Activator;
import org.eclipse.search.internal.ui.text.FileSearchQuery;
import org.eclipse.search.ui.IQueryListener;
import org.eclipse.search.ui.ISearchQuery;

@SuppressWarnings("restriction")
public class SearchEventListener implements IQueryListener {

	@Override
	public void queryAdded(ISearchQuery query) {

		if (query instanceof FileSearchQuery) {
			FileSearchQuery fileSearchQuery = ((FileSearchQuery) query);
			String searchString = fileSearchQuery.getSearchString();
			Activator.getDefault().getEventBus().post(new SearchEvent(searchString));
		}

	}

	@Override
	public void queryRemoved(ISearchQuery query) {

	}

	@Override
	public void queryStarting(ISearchQuery query) {

	}

	@Override
	public void queryFinished(ISearchQuery query) {

	}

}
