package org.eclipse.scava.plugin.apidocumentation;

import java.util.List;

import org.eclipse.scava.plugin.mvc.IModel;

public interface IApiDocumentationModel extends IModel {
	List<WebResult> getResults();
}
