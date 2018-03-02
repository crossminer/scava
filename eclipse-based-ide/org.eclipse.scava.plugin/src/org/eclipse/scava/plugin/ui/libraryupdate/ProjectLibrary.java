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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.scava.commons.library.Library;

public class ProjectLibrary {

	private Library library;
	private List<AlternativeLibrary> alternativeLibraries;
	
	
	
	
	public ProjectLibrary(Library library) {
		super();
		this.library = library;
		this.alternativeLibraries = new ArrayList<>();
	}
	
	public ProjectLibrary(Library library,List<AlternativeLibrary> alternativeLibraries) {
		super();
		this.library = library;
		this.alternativeLibraries = new ArrayList<>();
		
		setAlternativeLibraries(alternativeLibraries);
	}
	
	
	public void addAlternativeLibrary(AlternativeLibrary alt) {
		
		
		alternativeLibraries.add(alt);
		alt.setOriginalLibrary(this);
		
	}
	
	public Library getLibrary() {
		return library;
	}
	public void setLibrary(Library library) {
		this.library = library;
	}
	public List<AlternativeLibrary> getAlternativeLibraries() {
		return alternativeLibraries;
	}
	public void setAlternativeLibraries(List<AlternativeLibrary> alternativeLibraries) {	
		alternativeLibraries.forEach(lib -> addAlternativeLibrary(lib));
	}
	
	
	
}
