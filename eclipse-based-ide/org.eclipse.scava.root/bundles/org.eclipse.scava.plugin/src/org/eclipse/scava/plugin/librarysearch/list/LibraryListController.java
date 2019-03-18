package org.eclipse.scava.plugin.librarysearch.list;

import java.util.List;

import org.eclipse.scava.plugin.mvc.IController;
import org.eclipse.scava.plugin.mvc.implementation.AbstractModelViewController;

import com.google.common.eventbus.Subscribe;

public class LibraryListController extends AbstractModelViewController<ILibraryListModel, ILibraryListView>
		implements ILibraryListController {

	public LibraryListController(IController parent, ILibraryListModel model, ILibraryListView view) {
		super(parent, model, view);
	}

	@Override
	public void init() {
		getView().init();
		
		List<LibraryListInfo> libraryInfos = getModel().getLibraryInfos();

		getView().show(libraryInfos);
	}

	@Subscribe
	public void onLibrarySelection(ILibraryListView.LibrarySelectionEvent e) {
		if( e.getSender() == getView() ) {
			ILibraryListController.LibrarySelectionEvent event = new ILibraryListController.LibrarySelectionEvent(this,
					e.getLibrary());
			getEventBus().post(event);
		}
	}

}
