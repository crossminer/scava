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

import org.eclipse.scava.commons.library.Library;

public class AlternativeLibrary {
	
	private ProjectLibrary originalLibrary;
	private Library library;
	
	public AlternativeLibrary(Library library) {
		super();
		this.library = library;
		
	}
	
	public void setOriginalLibrary(ProjectLibrary originalLibrary) {
		this.originalLibrary = originalLibrary;
	}
	
	public void setLibrary(Library library) {
		this.library = library;
	}
	
	public Library getLibrary() {
		return library;
	}
	
	public ProjectLibrary getOriginalLibrary() {
		return originalLibrary;
	}
}
