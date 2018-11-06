package org.eclipse.scava.plugin.librarysearch.details;

import org.eclipse.scava.plugin.knowledgebase.access.SimilarityMethod;
import org.eclipse.scava.plugin.mvc.IController;
import org.eclipse.scava.plugin.mvc.event.AbstractEvent;

import io.swagger.client.model.Artifact;

public interface ILibraryDetailsController extends IController {
	
	void onSimilarsSearchRequest(ILibraryDetailsView.SimilarsRequestEvent e);

	static class SimilarsRequestEvent extends AbstractEvent<ILibraryDetailsController> {
		private final Artifact similarsTo;
		private final SimilarityMethod similarityMethod;

		public SimilarsRequestEvent(ILibraryDetailsController sender, Artifact similarsTo,
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
