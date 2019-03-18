package org.eclipse.scava.plugin.librarysearch.tabs.recommendedlibs;

import org.eclipse.scava.plugin.mvc.IController;

public interface ILibrarySearchRecommendedLibsController extends IController {
	
	static class Close extends ControllerCloseEvent<ILibrarySearchRecommendedLibsController> {

		public Close(ILibrarySearchRecommendedLibsController controller) {
			super(controller);
		}

	}
}
