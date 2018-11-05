package org.eclipse.scava.plugin.usermonitoring.event.scava;

import org.eclipse.scava.commons.library.Library;

public interface IScavaEventListener {

	public void libraryChange(ScavaEventType type);
	
	public void libraryInformationRequest(Library library);
	
	public void libraryAlternativeSearch(Library library);
	
}
