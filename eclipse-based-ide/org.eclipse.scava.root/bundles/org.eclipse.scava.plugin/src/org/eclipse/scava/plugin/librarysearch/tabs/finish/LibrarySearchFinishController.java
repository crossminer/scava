package org.eclipse.scava.plugin.librarysearch.tabs.finish;

import java.util.List;

import org.eclipse.scava.plugin.mvc.IController;
import org.eclipse.scava.plugin.mvc.implementation.AbstractModelViewController;

import com.google.common.eventbus.Subscribe;

public class LibrarySearchFinishController
		extends AbstractModelViewController<ILibrarySearchFinishModel, ILibrarySearchFinishView>
		implements ILibrarySearchFinishController {

	public LibrarySearchFinishController(IController parent, ILibrarySearchFinishModel model,
			ILibrarySearchFinishView view) {
		super(parent, model, view);
	}

	@Override
	public void init() {
		getView().init();
		
		List<String> currentlyUsedLibraries = getModel().getCurrentlyUsedLibraries();
		
		if( currentlyUsedLibraries != null  ) {
			getView().showCurrentlyUsedLibrares(currentlyUsedLibraries);
		}
	}
	
	@Subscribe
	public void onAdditionalSearchRequest(ILibrarySearchFinishView.RecommendedSearchRequestEvent e) {
		if( e.getSender() == getView() ) {
			ILibrarySearchFinishController.RecommendedSearchRequestEvent event = new ILibrarySearchFinishController.RecommendedSearchRequestEvent(this, e.getSelectedLibraries());
			getEventBus().post(event);
		}
	}
	
	@Subscribe
	public void onInstallRequest(ILibrarySearchFinishView.InstallRequestEvent e) {
		if( e.getSender() == getView() ) {
			ILibrarySearchFinishController.InstallRequestEvent event = new ILibrarySearchFinishController.InstallRequestEvent(this);
			getEventBus().post(event);
		}
	}
}
