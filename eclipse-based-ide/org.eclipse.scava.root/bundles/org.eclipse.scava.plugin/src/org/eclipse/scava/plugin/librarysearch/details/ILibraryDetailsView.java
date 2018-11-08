package org.eclipse.scava.plugin.librarysearch.details;

import java.util.List;

import org.eclipse.scava.plugin.knowledgebase.access.SimilarityMethod;
import org.eclipse.scava.plugin.librarysearch.list.LibraryListInfo;
import org.eclipse.scava.plugin.mvc.IView;
import org.eclipse.scava.plugin.mvc.event.AbstractEvent;

import io.swagger.client.model.Artifact;

public interface ILibraryDetailsView extends IView {
	void showDetails(Artifact library);

	void showSimilars(List<LibraryListInfo> similarLibraries);

	static class SimilarsRequestEvent extends AbstractEvent<ILibraryDetailsView> {
		private final Artifact similarsTo;
		private final SimilarityMethod similarityMethod;

		public SimilarsRequestEvent(ILibraryDetailsView sender, Artifact similarsTo,
				SimilarityMethod similarityMethod) {
			super(sender);
			this.similarsTo = similarsTo;
			this.similarityMethod = similarityMethod;
		}

		public Artifact getSimilarsTo() {
			return similarsTo;
		}

		public SimilarityMethod getSimilarityMethod() {
			return similarityMethod;
		}

	}
}
