package org.eclipse.scava.plugin.coderecommendation;

import org.eclipse.scava.plugin.mvc.IController;

public interface ICodeRecommendationController extends IController {

	static class Close extends ControllerCloseEvent<ICodeRecommendationController> {

		public Close(ICodeRecommendationController controller) {
			super(controller);
		}

	}
}
