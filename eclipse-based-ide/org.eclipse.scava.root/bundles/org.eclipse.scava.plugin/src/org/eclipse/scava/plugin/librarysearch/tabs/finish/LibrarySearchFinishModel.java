package org.eclipse.scava.plugin.librarysearch.tabs.finish;

import java.util.Collections;
import java.util.List;

import org.eclipse.scava.plugin.mvc.implementation.AbstractModel;

public class LibrarySearchFinishModel extends AbstractModel implements ILibrarySearchFinishModel {
	private final List<String> currentlyUsedLibraries;

	public LibrarySearchFinishModel(List<String> currentlyUsedLibraries) {
		super();
		this.currentlyUsedLibraries = currentlyUsedLibraries;
	}

	@Override
	public List<String> getCurrentlyUsedLibraries() {
		return currentlyUsedLibraries == null ? null : Collections.unmodifiableList(currentlyUsedLibraries);
	}

}
