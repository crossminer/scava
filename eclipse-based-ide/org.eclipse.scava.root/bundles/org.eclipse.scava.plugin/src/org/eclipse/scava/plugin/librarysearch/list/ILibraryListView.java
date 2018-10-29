package org.eclipse.scava.plugin.librarysearch.list;

import java.util.List;

import org.eclipse.scava.plugin.mvc.IView;
import org.eclipse.scava.plugin.mvc.event.AbstractEvent;

import io.swagger.client.model.Artifact;

public interface ILibraryListView extends IView {
	void show(List<LibraryListInfo> libraryInfos);
	
	static class LibrarySelectionEvent extends AbstractEvent<ILibraryListView> {
		private final Artifact library;

		public LibrarySelectionEvent(ILibraryListView sender, Artifact library) {
			super(sender);
			this.library = library;
		}

		public Artifact getLibrary() {
			return library;
		}

	}
}
