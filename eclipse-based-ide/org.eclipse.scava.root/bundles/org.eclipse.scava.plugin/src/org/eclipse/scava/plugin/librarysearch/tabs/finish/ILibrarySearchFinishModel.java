package org.eclipse.scava.plugin.librarysearch.tabs.finish;

import java.util.List;

import org.eclipse.scava.plugin.mvc.IModel;

public interface ILibrarySearchFinishModel extends IModel {
	List<String> getCurrentlyUsedLibraries();
}
