package org.eclipse.scava.plugin.librarysearch.tabs.finish;

import java.util.List;

import org.eclipse.scava.plugin.mvc.IView;
import org.eclipse.scava.plugin.mvc.event.AbstractEvent;

public interface ILibrarySearchFinishView extends IView {
	void showCurrentlyUsedLibrares(List<String> libraries);

	static class RecommendedSearchRequestEvent extends AbstractEvent<ILibrarySearchFinishView> {
		private final List<String> selectedLibraries;

		public RecommendedSearchRequestEvent(ILibrarySearchFinishView sender, List<String> selectedLibraries) {
			super(sender);
			this.selectedLibraries = selectedLibraries;
		}

		public List<String> getSelectedLibraries() {
			return selectedLibraries;
		}
	}

	static class InstallRequestEvent extends AbstractEvent<ILibrarySearchFinishView> {

		public InstallRequestEvent(ILibrarySearchFinishView sender) {
			super(sender);
		}

	}

	static class Close extends ViewCloseEvent<ILibrarySearchFinishView> {

		public Close(ILibrarySearchFinishView view) {
			super(view);
		}

	}
}
