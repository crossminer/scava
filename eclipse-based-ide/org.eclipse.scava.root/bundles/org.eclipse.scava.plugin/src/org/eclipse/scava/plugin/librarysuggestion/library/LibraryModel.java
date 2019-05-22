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

import org.eclipse.scava.plugin.mvc.model.Model;

public class LibraryModel extends Model {
	private final Library library;
	private final LibraryType libraryType;

	public LibraryModel(Library library, LibraryType libraryType) {
		super();
		this.library = library;
		this.libraryType = libraryType;
	}

	public Library getLibrary() {
		return library;
	}

	public LibraryType getLibraryType() {
		return libraryType;
	}

}
