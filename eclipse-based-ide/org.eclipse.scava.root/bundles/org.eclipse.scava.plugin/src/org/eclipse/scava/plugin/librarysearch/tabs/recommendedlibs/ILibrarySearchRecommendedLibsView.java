package org.eclipse.scava.plugin.librarysearch.tabs.recommendedlibs;

import java.util.List;

import org.eclipse.scava.plugin.mvc.IView;
import org.eclipse.scava.plugin.mvc.event.AbstractEvent;

import io.swagger.client.model.RecommendedLibrary;

public interface ILibrarySearchRecommendedLibsView extends IView {

	void showRecommendedLibraries(List<RecommendedLibrary> libraries);
	
	void showInstalledMessage(String libraryName);

	static class Close extends ViewCloseEvent<ILibrarySearchRecommendedLibsView> {

		public Close(ILibrarySearchRecommendedLibsView view) {
			super(view);
		}

	}

	static class InstallLibrary extends AbstractEvent<ILibrarySearchRecommendedLibsView> {
		private final String library;

		public InstallLibrary(ILibrarySearchRecommendedLibsView sender, String library) {
			super(sender);
			this.library = library;
		}
		
		public String getLibrary() {
			return library;
		}
	}
}
