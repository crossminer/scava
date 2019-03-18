package org.eclipse.scava.plugin.apidocumentation;

import java.util.List;

import org.eclipse.scava.plugin.mvc.IView;

public interface IApiDocumentationView extends IView {
	
	void showResults(List<WebResult> results);
	
	static class Close extends ViewCloseEvent<IApiDocumentationView> {

		public Close(IApiDocumentationView view) {
			super(view);
		}
		
	}
}
