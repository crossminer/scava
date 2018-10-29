package org.eclipse.scava.plugin.librarysearch.list;

import java.util.List;

import org.eclipse.scava.plugin.mvc.implementation.AbstractModel;

public class LibraryListModel extends AbstractModel implements ILibraryListModel {

	private final List<LibraryListInfo> libraryInfos;

	public LibraryListModel(List<LibraryListInfo> libraryInfos) {
		super();
		this.libraryInfos = libraryInfos;
	}

	@Override
	public List<LibraryListInfo> getLibraryInfos() {
		return libraryInfos;
	}

}
