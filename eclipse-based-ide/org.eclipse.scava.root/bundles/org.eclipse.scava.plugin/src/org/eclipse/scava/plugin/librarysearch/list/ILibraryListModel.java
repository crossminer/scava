package org.eclipse.scava.plugin.librarysearch.list;

import java.util.List;

import org.eclipse.scava.plugin.mvc.IModel;

public interface ILibraryListModel extends IModel {
	List<LibraryListInfo> getLibraryInfos();
}
