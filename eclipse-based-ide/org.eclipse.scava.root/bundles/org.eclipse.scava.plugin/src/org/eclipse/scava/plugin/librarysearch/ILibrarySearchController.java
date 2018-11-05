package org.eclipse.scava.plugin.librarysearch;

import org.eclipse.scava.plugin.mvc.IController;

public interface ILibrarySearchController extends IController {

	static class Close extends ControllerCloseEvent<ILibrarySearchController> {

		public Close(ILibrarySearchController controller) {
			super(controller);
		}
		
	}
}
