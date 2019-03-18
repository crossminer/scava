package org.eclipse.scava.plugin.librarysearch.tabs.finish;

import java.util.List;

import org.eclipse.scava.plugin.mvc.IController;
import org.eclipse.scava.plugin.mvc.event.AbstractEvent;

public interface ILibrarySearchFinishController extends IController {

	static class RecommendedSearchRequestEvent extends AbstractEvent<ILibrarySearchFinishController> {
		private final List<String> selectedLibraries;
		private final List<String> currentlyUsedLibraries;

		public RecommendedSearchRequestEvent(ILibrarySearchFinishController sender, List<String> selectedLibraries,
				List<String> currentlyUsedLibraries) {
			super(sender);
			this.selectedLibraries = selectedLibraries;
			this.currentlyUsedLibraries = currentlyUsedLibraries;
		};

		public List<String> getSelectedLibraries() {
			return selectedLibraries;
		}

		public List<String> getCurrentlyUsedLibraries() {
			return currentlyUsedLibraries;
		}

	}

	static class InstallRequestEvent extends AbstractEvent<ILibrarySearchFinishController> {

		public InstallRequestEvent(ILibrarySearchFinishController sender) {
			super(sender);
		}

	}

	static class Close extends ControllerCloseEvent<ILibrarySearchFinishController> {

		public Close(ILibrarySearchFinishController controller) {
			super(controller);
		}

	}
}
