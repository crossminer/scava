/*********************************************************************
* Copyright c 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse PublicLicense 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.librarysuggestion.library;

import org.eclipse.scava.plugin.main.OpenInExternalBrowserRequestEvent;
import org.eclipse.scava.plugin.mvc.controller.Controller;
import org.eclipse.scava.plugin.mvc.controller.ModelViewController;
import org.eclipse.scava.plugin.mvc.event.routed.IRoutedEvent;

public class LibraryController extends ModelViewController<LibraryModel, LibraryView>
		implements ILibraryViewEventListener {

	public LibraryController(Controller parent, LibraryModel model, LibraryView view) {
		super(parent, model, view);
	}

	@Override
	public void requestViewClose() {
		dispose();
	}

	@Override
	public void init() {
		super.init();

		getView().setGroupId(getModel().getLibrary().getGroupId());
		getView().setArtifactId(getModel().getLibrary().getArtifactId());
		getView().setVersion(getModel().getLibrary().getVersion());

		LibraryType libraryType = getModel().getLibraryType();
		getView().setLibraryType(libraryType);
	}

	@Override
	public void onOpenInBrowser() {
		String url = getModel().getLibrary().getUrl();

		if (url == null || url.isEmpty()) {
			url = "https://mvnrepository.com/artifact/" + getModel().getLibrary().getGroupId() + "/"
					+ getModel().getLibrary().getArtifactId();
		}

		routeEventToParentController(new OpenInExternalBrowserRequestEvent(this, url));
	}

	@Override
	public void onUseForSearch() {
		routeEventToParentController(new UseLibraryForSearchRequestEvent(this, getModel().getLibrary()));
	}

	@Override
	public void onDoNotUseForSearch() {
		routeEventToParentController(new DoNotUseLibraryForSearchRequestEvent(this, getModel().getLibrary()));
	}

	@Override
	public void onPickForInstall() {
		routeEventToParentController(new PickForInstallLibraryRequestEvent(this, getModel().getLibrary()));
	}

	@Override
	public void onUnpickForInstall() {
		routeEventToParentController(new UnpickForInstallLibraryRequestEvent(this, getModel().getLibrary()));
	}

	@Override
	protected void onReceiveRoutedEventFromParentController(IRoutedEvent routedEvent) {

		if (routedEvent instanceof DisposeLibraryRequestEvent) {
			DisposeLibraryRequestEvent event = (DisposeLibraryRequestEvent) routedEvent;

			if (getModel().getLibrary().equals(event.getLibrary())) {
				dispose();
			}

			return;
		}

		super.onReceiveRoutedEventFromParentController(routedEvent);
	}
}
