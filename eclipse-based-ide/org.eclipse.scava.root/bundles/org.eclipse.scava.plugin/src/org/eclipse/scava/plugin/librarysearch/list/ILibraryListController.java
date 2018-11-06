package org.eclipse.scava.plugin.librarysearch.list;

import org.eclipse.scava.plugin.mvc.IController;
import org.eclipse.scava.plugin.mvc.event.AbstractEvent;

import io.swagger.client.model.Artifact;

public interface ILibraryListController extends IController {
	
	static class LibrarySelectionEvent extends AbstractEvent<ILibraryListController> {
		private final Artifact library;

		public LibrarySelectionEvent(ILibraryListController sender, Artifact library) {
			super(sender);
			this.library = library;
		}

		public Artifact getLibrary() {
			return library;
		}

	}
}
