package org.eclipse.scava.plugin.librarysearch.details;

import java.util.List;

import org.eclipse.scava.plugin.librarysearch.list.LibraryListInfo;
import org.eclipse.scava.plugin.mvc.IController;
import org.eclipse.scava.plugin.mvc.implementation.AbstractModelViewController;

import com.google.common.eventbus.Subscribe;

import io.swagger.client.model.Artifact;

public class LibraryDetailsController extends AbstractModelViewController<ILibraryDetailsModel, ILibraryDetailsView>
		implements ILibraryDetailsController {

	public LibraryDetailsController(IController parent, ILibraryDetailsModel model, ILibraryDetailsView view) {
		super(parent, model, view);
	}

	@Override
	public void init() {
		getView().init();
		
		Artifact library = getModel().getLibrary();
		getView().showDetails(library);

		List<LibraryListInfo> similars = getModel().getSimilars();
		getView().showSimilars(similars);
	}

	@Override
	@Subscribe
	public void onSimilarsSearchRequest(ILibraryDetailsView.SimilarsRequestEvent e) {
		if (e.getSender() == getView()) {
			ILibraryDetailsController.SimilarsRequestEvent event = new ILibraryDetailsController.SimilarsRequestEvent(
					this, e.getSimilarsTo(), e.getSimilarityMethod());
			getEventBus().post(event);
		}
	}

}
