package org.eclipse.scava.plugin.librarysearch.tabs.results;

import org.eclipse.scava.plugin.librarysearch.details.ILibraryDetailsView;
import org.eclipse.scava.plugin.librarysearch.list.ILibraryListView;
import org.eclipse.scava.plugin.mvc.IView;

public interface ILibrarySearchResultView extends IView {
	void setDescription(String description);

	void showLibraries(ILibraryListView view);

	void showDetails(ILibraryDetailsView view);
	
	static class Close extends ViewCloseEvent<ILibrarySearchResultView> {

		public Close(ILibrarySearchResultView view) {
			super(view);
		}
		
	}
}
