package org.eclipse.scava.plugin.apidocumentation;

import java.util.List;

import org.eclipse.scava.plugin.mvc.IController;
import org.eclipse.scava.plugin.mvc.implementation.AbstractModelViewController;

public class ApiDocumentationController extends AbstractModelViewController<IApiDocumentationModel, IApiDocumentationView>
		implements IApiDocumentationController {

	public ApiDocumentationController(IController parent, IApiDocumentationModel model, IApiDocumentationView view) {
		super(parent, model, view);
	}

	@Override
	public void init() {
		getView().init();
		
		List<WebResult> results = getModel().getResults();
		
		getView().showResults(results);
	}

}
