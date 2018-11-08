package org.eclipse.scava.plugin.librarysearch.tabs.results;

import java.util.List;

import org.eclipse.scava.plugin.knowledgebase.access.SimilarityMethod;
import org.eclipse.scava.plugin.mvc.IController;
import org.eclipse.scava.plugin.mvc.event.AbstractEvent;

import io.swagger.client.model.Artifact;

public interface ILibrarySearchResultController extends IController {
	void excludeLibrariesFromResults(List<Artifact> excludedLibraries);

	static class SimilarsRequestedEvent extends AbstractEvent<ILibrarySearchResultController> {
		private final Artifact library;
		private final SimilarityMethod similarityMethod;

		public SimilarsRequestedEvent(ILibrarySearchResultController sender, Artifact library,
				SimilarityMethod similarityMethod) {
			super(sender);
			this.library = library;
			this.similarityMethod = similarityMethod;
		}

		public Artifact getLibrary() {
			return library;
		}

		public SimilarityMethod getSimilarityMethod() {
			return similarityMethod;
		}

	}

	static class Close extends ControllerCloseEvent<ILibrarySearchResultController> {

		public Close(ILibrarySearchResultController controller) {
			super(controller);
		}

	}
}
