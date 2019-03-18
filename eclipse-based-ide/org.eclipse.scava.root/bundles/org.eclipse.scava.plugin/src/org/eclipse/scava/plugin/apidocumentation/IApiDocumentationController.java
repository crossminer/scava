package org.eclipse.scava.plugin.apidocumentation;

import org.eclipse.scava.plugin.mvc.IController;

public interface IApiDocumentationController extends IController {

	static class Close extends ControllerCloseEvent<IApiDocumentationController> {

		public Close(IApiDocumentationController controller) {
			super(controller);
		}
		
	}
}
