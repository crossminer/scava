/*********************************************************************
* Copyright (c) 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.ui.libraryupdate;

import java.util.List;

import org.eclipse.scava.commons.library.Library;
import org.eclipse.scava.plugin.event.informative.IInformativeEvent;
import org.eclipse.scava.plugin.event.informative.IInformativeEventSubscriber;
import org.eclipse.scava.plugin.event.informative.InformativeEvent;
import org.eclipse.scava.plugin.event.notifier.INotifierEvent;
import org.eclipse.scava.plugin.event.notifier.INotifierEventSubscriber;
import org.eclipse.scava.plugin.event.notifier.NotifierEvent;
import org.eclipse.scava.plugin.ui.abstractmvc.JFaceWindowView;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

public class LibraryUpdateView extends JFaceWindowView<LibraryUpdateDisplay> {
	private final IInformativeEvent<List<AlternativeLibrary>> librariesAccepted = registerEvent(
			new InformativeEvent<>());
	private final INotifierEvent librariesCancelled = registerEvent(new NotifierEvent());
	private final IInformativeEvent<Library> librarySelected = registerEvent(new InformativeEvent<>());
	
	public LibraryUpdateView(LibraryUpdateDisplay display) {
		super(display);
		
		getDisplay().getLibrariesAccepted().subscribe((libraries) -> librariesAccepted.invoke(libraries));
		getDisplay().getLibrariesCancelled().subscribe(() -> librariesCancelled.invoke());
		getDisplay().getLibrarySelected().subscribe((library) -> librarySelected.invoke(library));
	}
	
	public IInformativeEventSubscriber<List<AlternativeLibrary>> getLibrariesAccepted() {
		return librariesAccepted;
	}
	
	public INotifierEventSubscriber getLibrariesCancelled() {
		return librariesCancelled;
	}
	
	public IInformativeEventSubscriber<Library> getLibrarySelected() {
		return librarySelected;
	}
	
	public Shell getShell() {
		return getDisplay().getShell();
	}
	
	public void showEmptyLibraryListError() {
		MessageBox mb = new MessageBox(getDisplay().getShell(), SWT.ICON_ERROR | SWT.OK);
		mb.setMessage("You shoud choose at least one library.");
		mb.setText("No library selected.");
		mb.open();
	}
	
	public void showUpdateLibraryError(String message) {
		MessageBox mb = new MessageBox(getDisplay().getShell(), SWT.ICON_ERROR | SWT.OK);
		mb.setMessage(message);
		mb.setText("Error during update");
		mb.open();
	}
	
	public void showLibraries(List<ProjectLibrary> libraries) {
		getDisplay().showLibraries(libraries);
	}
	
	public void showDescription(String description) {
		getDisplay().showDescription(description);
	}
	
}
