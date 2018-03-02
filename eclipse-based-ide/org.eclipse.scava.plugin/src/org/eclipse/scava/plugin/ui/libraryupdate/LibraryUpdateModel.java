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

import java.util.Collections;
import java.util.List;

import org.eclipse.scava.plugin.event.notifier.INotifierEvent;
import org.eclipse.scava.plugin.event.notifier.INotifierEventSubscriber;
import org.eclipse.scava.plugin.event.notifier.NotifierEvent;
import org.eclipse.scava.plugin.ui.abstractmvc.AbstractModel;

public class LibraryUpdateModel extends AbstractModel {
	
	private List<ProjectLibrary> libraries;
	private final INotifierEvent modelChanged = registerEvent(new NotifierEvent());
	
	public LibraryUpdateModel(List<ProjectLibrary> libraries) {
		this.libraries = libraries;
	}
	
	public INotifierEventSubscriber getModelChanged() {
		return modelChanged;
	}
	
	public void addLibraries(ProjectLibrary library) {
		this.libraries.add(library);
		modelChanged.invoke();
	}
	
	public void removeLibraries(ProjectLibrary library) {
		this.libraries.remove(library);
		modelChanged.invoke();
	}
	
	protected List<ProjectLibrary> getLibraries() {
		return Collections.unmodifiableList(libraries);
	}
}
