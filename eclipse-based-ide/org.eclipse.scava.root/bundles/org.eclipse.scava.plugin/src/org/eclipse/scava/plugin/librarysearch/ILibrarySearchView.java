package org.eclipse.scava.plugin.librarysearch;

import org.eclipse.scava.plugin.librarysearch.list.ILibraryListView;
import org.eclipse.scava.plugin.librarysearch.tabs.finish.ILibrarySearchFinishView;
import org.eclipse.scava.plugin.librarysearch.tabs.recommendedlibs.ILibrarySearchRecommendedLibsView;
import org.eclipse.scava.plugin.librarysearch.tabs.results.ILibrarySearchResultView;
import org.eclipse.scava.plugin.mvc.IView;
import org.eclipse.scava.plugin.mvc.event.AbstractEvent;

public interface ILibrarySearchView extends IView {
	
	void showToBeInstalled(ILibraryListView view);
	
	void showSearchResult(String label, ILibrarySearchResultView view);
	
	void showSuggestedLibraries(String label, ILibrarySearchRecommendedLibsView view);
	
	void showFinish(ILibrarySearchFinishView view);
	
	static class FinishRequestEvent extends AbstractEvent<ILibrarySearchView> {

		public FinishRequestEvent(ILibrarySearchView sender) {
			super(sender);
		}
		
	}
	
	static class SearchRequestEvent extends AbstractEvent<ILibrarySearchView> {
		private final String queryString;

		public SearchRequestEvent(ILibrarySearchView sender, String queryString) {
			super(sender);
			this.queryString = queryString;
		}
		
		public String getQueryString() {
			return queryString;
		}
	}
	
	static class Close extends ViewCloseEvent<ILibrarySearchView> {

		public Close(ILibrarySearchView view) {
			super(view);
		}

	}

}
